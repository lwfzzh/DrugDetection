package com.example.drugdetection.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.drugdetection.Database.LiteDatabase;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lwf
 * @date 2022-10-26 20:31
 * 注册|新建用户
 */
public class CreateUser extends Activity {
    private Cursor cursor;
    private SQLiteDatabase sqLiteDatabase;
    private LiteDatabase liteDatabase;
    private EditText enterUserName, enterAccount, enterPassword, enterPasswordAgain;
    private Button createUserReturnButton, createUserConfirm;
    private Spinner userType;
    private String mode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_user);
        MyApplication.getApplication().addActivity(this);
        // 获取是由哪个页面进来的，以此决定为注册还是新建用户
        mode = getIntent().getStringExtra("mode");
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        createUserConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = enterUserName.getText().toString();
                String account = enterAccount.getText().toString();
                String password = enterPassword.getText().toString();
                String passwordAgain = enterPasswordAgain.getText().toString();
                try {
                    // 密码和确认密码一致才能注册
                    if (password.equals(passwordAgain)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String sql = String.format("insert into user(user_name, account_number, password, automatic_login, remember_the_password, user_type, create_date) " +
                                        "select '%s', '%s', '%s', 0, 0, 1,'"+ sdf.format(new Date()) + "' where not exists (select * from user where account_number = '%s')",
                                userName, account, password, account);
                        liteDatabase = new LiteDatabase(CreateUser.this);
                        sqLiteDatabase = liteDatabase.openDB();
                        if (sqLiteDatabase == null) {
                            liteDatabase.CloseDB(null, cursor);
                        }
                        liteDatabase.execSQL(sqLiteDatabase, sql);
                        if ("login".equals(mode)) {
                            // 判断此为注册页，注册成功后返回登录页
                            // 保存账号,返回登录自动填充账号
                            sharedPreferences.edit().putString("userName", account).commit();
                            Intent intent = new Intent(CreateUser.this, Login.class);
                            startActivityForResult(intent, 1);
                        } else {
                            // 此为新建用户页，新建成功后返回用户管理页
                            Intent intent = new Intent(CreateUser.this, UserManagement.class);
                            startActivityForResult(intent, 1);
                        }
                    } else {
                        prompt("两次密码输入不一致,请重新输入!");
                    }
                } catch (NullPointerException nullPointerException) {
                    prompt("两次密码输入不一致,请重新输入!");
                } finally {
                    liteDatabase.CloseDB(sqLiteDatabase, cursor);
                }
            }
        });

        createUserReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initControl() {
        // 输入用户名
        enterUserName = findViewById(R.id.enterUserName);
        // 输入账号
        enterAccount = findViewById(R.id.enterAccount);
        // 输入密码
        enterPassword = findViewById(R.id.enterPassword);
        // 再一次输入密码
        enterPasswordAgain = findViewById(R.id.enterPasswordAgain);
        // 返回按钮
        createUserReturnButton = findViewById(R.id.createUserReturnButton);
        // 确认按钮
        createUserConfirm = findViewById(R.id.createUserConfirm);
        // 用户类型
        userType = findViewById(R.id.userType);
        userType.setSelection(1);
    }

    /**
    * 提示
    *@param: 需要提示的字符串
    */
    private void prompt(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
