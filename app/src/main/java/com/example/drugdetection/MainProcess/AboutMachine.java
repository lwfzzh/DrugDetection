package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import com.example.drugdetection.Database.LiteDatabase;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-10-28 11:57
 * 关于本机
 */
public class AboutMachine extends Activity {
    private Button factoryDataReset, upgrade, aboutThisComputerSidebar;
    private LiteDatabase liteDatabase;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_this_computer);
        liteDatabase = new LiteDatabase(AboutMachine.this);
        MyApplication.getApplication().addActivity(this);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        // 点击恢复出厂设置
        factoryDataReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liteDatabase.deleteDB();
                sharedPreferences.edit().clear().commit();
            }
        });

        //  点击升级
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        // 点击侧边栏
        aboutThisComputerSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutMachine.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 恢复出厂设置
        factoryDataReset = findViewById(R.id.factoryDataReset);
        // 升级
        upgrade = findViewById(R.id.upgrade);
        // 侧边栏
        aboutThisComputerSidebar = findViewById(R.id.aboutThisComputerSidebar);
    }
}
