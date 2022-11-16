package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-10-28 10:57
 * 主菜单
 */
public class Menu extends Activity {
    private LinearLayout projectTest, historicalRecord, generalSettings, aboutMachine;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        // 初始化控件
        initControl();
        MyApplication.getApplication().addActivity(this);
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        // 进入测试页
        projectTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, Test.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入历史页
        historicalRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, HistoricalRecords.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入通用设置页
        generalSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, GeneralSettings.class);
                startActivityForResult(intent, 1);
            }
        });

        // 进入关于本机页
        aboutMachine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Menu.this, AboutMachine.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 项目测试
        projectTest = findViewById(R.id.projectTest);
        // 历史记录
        historicalRecord = findViewById(R.id.historicalRecord);
        // 通用设置
        generalSettings = findViewById(R.id.generalSettings);
        // 关于本机
        aboutMachine = findViewById(R.id.aboutMachine);
    }
}
