package com.example.drugdetection.Mian;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.example.drugdetection.Database.LiteDatabase;
import com.example.drugdetection.R;
import com.example.drugdetection.User.Login;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lwf
 * @date 2022-10-25 15:41
 * 进入系统前显示页
 */
public class MainActivity extends Activity {
    private LiteDatabase liteDatabase;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.entering_the_system);
        MyApplication.getApplication().addActivity(this);
        liteDatabase = new LiteDatabase(MainActivity.this);
        sqLiteDatabase = liteDatabase.openDB();
        try {
            if (sqLiteDatabase == null) {
                liteDatabase.CloseDB(null, cursor);
                throw new SQLException();
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sql = "insert into user(user_name, account_number, password, automatic_login, remember_the_password, user_type, create_date) " +
                    "select '管理', 'admin', '123456', 0, 0, 0,'"+ sdf.format(date) + "' where not exists (select * from user where account_number = 'admin')";
            liteDatabase.execSQL(sqLiteDatabase, sql);
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivityForResult(intent, 1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            liteDatabase.CloseDB(sqLiteDatabase, cursor);
        }
    }
}
