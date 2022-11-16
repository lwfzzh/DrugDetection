package com.example.drugdetection.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import androidx.annotation.NonNull;

/**
 * @author lwf
 * @date 2022-10-26 20:42
 */
public class LiteDatabase {
    private Cursor cursor = null;
    private static final String DATABASE_NAME = "DRUG.db";
    private static final int VERSION = 1;
    private Context context;

    public LiteDatabase(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDB() {
        @SuppressLint("WrongConstant") SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase(DATABASE_NAME, SQLiteDatabase.CREATE_IF_NECESSARY, null);
        createTable(sqLiteDatabase);
        return sqLiteDatabase;
    }

    // 创建表
    public void createTable(@NonNull SQLiteDatabase sqLiteDatabase) {
        // 用户表
        sqLiteDatabase.execSQL("create table if not exists user(id integer primary key autoincrement, " +
                "user_name varchar(255), account_number varchar(255), password varchar(255), automatic_login integer," +
                "remember_the_password integer, user_type integer, create_date varchar(255));");
    }

    /**
    * 执行sql语句
    * 这一个方法没什么用,可以直接使用sqLiteDatabase.execSQL(sql), 这里完全就是多此一举
    *@param1: SQLiteDatabase对象
    *@param2: 需要执行的sql语句
    *@return: 没任何返回
    *@throws: 通常来说不会有异常
    */
    public void execSQL(@NonNull SQLiteDatabase sqLiteDatabase, @NonNull String sql) {
        sqLiteDatabase.execSQL(sql);
    }

    // 关闭数据库
    public void CloseDB(@NonNull SQLiteDatabase sqLiteDatabase,@NonNull Cursor c) {
        if (c != null) {
            if (!c.isClosed())
                c.close();
            c = null;
        }
        if (sqLiteDatabase != null) {
            if (sqLiteDatabase.isOpen()) {
                sqLiteDatabase.close();
            }
            sqLiteDatabase = null;
        }
    }

    /**
    * 删除数据库
    *@return: 返回是否删除成功
    */
    public boolean deleteDB() {
        try {
            String[] arr = context.databaseList();
            for (int i = 0; i < arr.length; i++) {
                context.deleteDatabase(arr[i]);
            }
            return true;
        } catch (Exception e) {
            Log.e("删除数据库", e.getMessage());
            return false;
        }
    }
}
