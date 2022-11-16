package com.example.drugdetection.MainProcess;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.example.drugdetection.Database.LiteDatabase;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;
import com.example.drugdetection.User.Login;
import com.example.drugdetection.User.UserManagement;

/**
 * @author lwf
 * @date 2022-11-01 14:22
 * 导航
 */
public class Sidebar extends Activity {
    private TextView sidebarUserName, sidebarAccount;
    private LinearLayout sidebarTest, sidebarHistorical, sidebarProjectManagement, sidebarReferenceRange, sidebarUserManagement, sidebarGeneralSettings, sidebarThisMachine;
    private Button logOut;
    private SharedPreferences sharedPreferences;
    private LiteDatabase liteDatabase;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidebar);
        MyApplication.getApplication().addActivity(this);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        liteDatabase = new LiteDatabase(Sidebar.this);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
        // 查询当前登录账号
        queryCurrentAccount();
    }

    private void queryCurrentAccount() {
        String sql = "select id, user_name, account_number from user where id = " + sharedPreferences.getInt("id", 0);
        try {
            sqLiteDatabase = liteDatabase.openDB();
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            cursor = sqLiteDatabase.rawQuery(sql, null);
            while (cursor.moveToNext()) {
                sidebarUserName.setText("用户名" + cursor.getString(1));
                sidebarAccount.setText("账户" + cursor.getString(2));
            }
        } catch (Exception e){
            Log.e("导航查询当前登录账号", e.getMessage());
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }

    private void listeningToThe() {
        // 进入测试页
        sidebarTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, EditPersonalInformation.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入历史记录页
        sidebarHistorical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, HistoricalRecords.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入项目管理页
        sidebarProjectManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, ProjectManagement.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入参考范围管理页
        sidebarReferenceRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, ReferenceRangeManagement.class);
                startActivityForResult(intent, 1);
            }
        });

        // 用户管理
        sidebarUserManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, UserManagement.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入通用设置页
        sidebarGeneralSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, GeneralSettings.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入关于本机页
        sidebarThisMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sidebar.this, AboutMachine.class);
                startActivityForResult(intent, 1);
            }
        });

        // 退出登录
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserStatus();
                Intent intent = new Intent(Sidebar.this, Login.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 用户名
        sidebarUserName = findViewById(R.id.sidebarUserName);
        // 账号
        sidebarAccount = findViewById(R.id.sidebarAccount);
        // 测试
        sidebarTest = findViewById(R.id.sidebarTest);
        // 记录管理
        sidebarHistorical = findViewById(R.id.sidebarHistorical);
        // 项目管理
        sidebarProjectManagement = findViewById(R.id.sidebarProjectManagement);
        // 参考范围管理
        sidebarReferenceRange = findViewById(R.id.sidebarReferenceRange);
        // 用户管理
        sidebarUserManagement = findViewById(R.id.sidebarUserManagement);
        // 通用设置
        sidebarGeneralSettings = findViewById(R.id.sidebarGeneralSettings);
        // 关于本机
        sidebarThisMachine = findViewById(R.id.sidebarThisMachine);
        // 退出登录
        logOut = findViewById(R.id.logOut);
    }

    /**
    * 修改用户登录状态
    */
    private void updateUserStatus() {
        // 获取当前登录的账号id
        int id = sharedPreferences.getInt("id", 0);
        // 修改自动登录状态为否
        String sql = "update user set automatic_login = 0 where id = " + id;
        try {
            sqLiteDatabase = liteDatabase.openDB();
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            liteDatabase.execSQL(sqLiteDatabase, sql);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("修改自动登录状态", e.getMessage());
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }
}
