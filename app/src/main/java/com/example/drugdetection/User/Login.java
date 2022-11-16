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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.example.drugdetection.MainProcess.Menu;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;
import com.example.drugdetection.Database.LiteDatabase;

/**
 * @author lwf
 * @date 2022-10-26 17:57
 * 登录
 */
public class Login extends Activity {
    private Cursor cursor;
    private LiteDatabase liteDatabase;
    private SQLiteDatabase sqLiteDatabase;
    private EditText userName, password;
    private CheckBox rememberThePassword, automaticLogin;
    private Button login, register;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initControl();
        MyApplication.getApplication().addActivity(this);
        liteDatabase = new LiteDatabase(Login.this);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 查询是否自动登录或者记住密码，如果自动登录就直接进入系统，记住密码就自动填充账号密码
        queryUserStatus();
        // 读取上次登录的账号
        userName.setText(sharedPreferences.getString("userName", ""));
        // 登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = userName.getText().toString();
                String pass = password.getText().toString();
                // 保存账号
                sharedPreferences.edit().putString("userName", user).commit();
                login(user, pass);
            }
        });
        // 注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, CreateUser.class);
                intent.putExtra("mode", "login");
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 账号
        userName = findViewById(R.id.userName);
        // 密码
        password = findViewById(R.id.password);
        // 记住密码
        rememberThePassword = findViewById(R.id.rememberThePassword);
        // 自动登录
        automaticLogin = findViewById(R.id.automaticLogin);
        // 登录按钮
        login = findViewById(R.id.login);
        // 注册按钮
        register = findViewById(R.id.register);
    }

    // 查询用户是否自动登录或记住密码
    private void queryUserStatus() {
        String sql = "select id, user_type from user where automatic_login = 1";
        try {
            sqLiteDatabase = liteDatabase.openDB();
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            cursor = sqLiteDatabase.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                // 当前登录账号的id
                sharedPreferences.edit().putInt("id", cursor.getInt(0)).commit();
                // 当前登录账号的类型
                sharedPreferences.edit().putInt("userType", cursor.getInt(1)).commit();
                // 如果有自动登录用户就直接进入主菜单界面
                Intent intent = new Intent(Login.this, Menu.class);
                startActivityForResult(intent, 1);
            }
            // 没有自动登录用户就查询是否有保存密码的用户
            sql = "select account_number, password from user where remember_the_password = 1";
            cursor = sqLiteDatabase.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                // 查询到了填充账号密码
                userName.setText(cursor.getString(0));
                password.setText(cursor.getString(1));
                rememberThePassword.setChecked(true);
                return;
            }
            rememberThePassword.setChecked(false);
        } catch (Exception e) {
            Log.e("自动登录或保存密码", e.getMessage());
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }

    /**
    * 修改用户自动登录或记住密码状态
    *@param: 记住密码或自动登录数据库字段名
    *@param: 设置记住密码或自动登录的状态1:是 0:否
    */
    private void updateUser(String s, int status) {
        String sql = String.format("update user set '%s' = '%s' where id = " + sharedPreferences.getInt("id", 0), s, status);
        try {
            sqLiteDatabase = liteDatabase.openDB();
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            liteDatabase.execSQL(sqLiteDatabase, sql);
        } catch (Exception e) {
            Log.e("登录保存密码或自动登录", e.getMessage());
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }

    /**
     * 登录
     *@param: 账号
     *@param: 密码
     */
    private void login(String user, String pass) {
        String sql = String.format("select id, account_number, password, user_type from user where account_number='%s' and password='%s'", user, pass);
        try {
            sqLiteDatabase = liteDatabase.openDB();
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            cursor = sqLiteDatabase.rawQuery(sql, null);
            int n = 0;
            while (cursor.moveToNext()) {
                n++;
                // 当前登录账号的id
                sharedPreferences.edit().putInt("id", cursor.getInt(0)).commit();
                // 当前登录账号的类型
                sharedPreferences.edit().putInt("userType", cursor.getInt(3)).commit();
                // 如果有设置自动登录或记住密码则修改数据库状态
                if (rememberThePassword.isChecked()) {
                    updateUser("remember_the_password", 1);
                } else {
                    updateUser("remember_the_password", 0);
                }
                if (automaticLogin.isChecked()) {
                    updateUser("automatic_login", 1);
                } else {
                    updateUser("automatic_login", 0);
                }
                // 账号密码正确，进入系统
                Intent intent = new Intent(Login.this, Menu.class);
                startActivityForResult(intent, 1);
            }
            if (n == 0) {
                Toast.makeText(getApplicationContext(), "账号或密码错误，请重新输入!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("登录判断账号密码是否正确", e.getMessage());
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }
}
