package com.example.drugdetection.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.drugdetection.Database.LiteDatabase;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

import java.util.HashMap;
import java.util.Map;


/**
 * @author lwf
 * @date 2022-11-01 10:35
 * 用户编辑
 */
public class EditUser extends Activity {
    private EditText editPassword, editPasswordAgain;
    private TextView editUserName, editUserAccount, initialPassword;
    private Button editUserReturnButton, editUserConfirm;
    private Spinner editUserType;
    private LiteDatabase liteDatabase;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private SharedPreferences sharedPreferences;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_user);
        MyApplication.getApplication().addActivity(this);
        liteDatabase = new LiteDatabase(EditUser.this);
        id = getIntent().getIntExtra("id", 0);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
        // 查询当前用户信息然后给用户名、账号、初始密码赋值
        userAssignment(id);
    }

    /**
    *@param: 用户id用于查询，不能为空
    */
    private void userAssignment(int id) {
        String sql = "select id, user_name, account_number, password from user where id = " + id;
        sqLiteDatabase = liteDatabase.openDB();
        try {
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            cursor = sqLiteDatabase.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                // 用户名
                editUserName.setText(cursor.getString(1));
                // 账号
                editUserAccount.setText(cursor.getString(2));
                // 密码
                initialPassword.setText(cursor.getString(3));
            }
        } catch (Exception e) {
            Log.e("查询当前用户信息", e.getMessage());
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }

    private void listeningToThe() {
        editUserConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = editPassword.getText().toString();
                String passwordAgain = editPasswordAgain.getText().toString();
                try {
                    // 密码和确认密码一致才能修改
                    if (password.equals(passwordAgain)) {
                        String sql = String.format("update user set password = '%s' where id=%s", password, id);
                        sqLiteDatabase = liteDatabase.openDB();
                        if (sqLiteDatabase == null) {
                            liteDatabase.CloseDB(null, cursor);
                            return;
                        }
                        liteDatabase.execSQL(sqLiteDatabase, sql);
                        // 如果修改的密码是当前账号的则返回登录页
                        if (id == sharedPreferences.getInt("id", 0)) {
                            Intent intent = new Intent(EditUser.this, Login.class);
                            startActivityForResult(intent, 1);
                        } else {
                            // 如果不是当前账号则返回用户管理
                            Intent intent = new Intent(EditUser.this, UserManagement.class);
                            startActivityForResult(intent, 1);
                        }
                    } else {
                        prompt("两次密码输入不一致,请重新输入!");
                    }
                } catch (NullPointerException nullPointerException) {
                    prompt("密码不能为空,请重新输入!");
                } finally {
                    liteDatabase.CloseDB(sqLiteDatabase, cursor);
                }
            }
        });

        editUserReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 返回用户管理页
                Intent intent = new Intent(EditUser.this, UserManagement.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 用户名
        editUserName = findViewById(R.id.editUserName);
        // 账号
        editUserAccount = findViewById(R.id.editUserAccount);
        // 初始密码
        initialPassword = findViewById(R.id.initialPassword);
        // 输入密码
        editPassword = findViewById(R.id.editPassword);
        // 再一次输入密码
        editPasswordAgain = findViewById(R.id.editPasswordAgain);
        // 返回按钮
        editUserReturnButton = findViewById(R.id.editUserReturnButton);
        // 确认按钮
        editUserConfirm = findViewById(R.id.editUserConfirm);
        // 用户类型
        editUserType = findViewById(R.id.editUserType);
        editUserType.setSelection(1);
    }

    /**
     * 提示
     *@param: 需要提示的字符串
     */
    private void prompt(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
