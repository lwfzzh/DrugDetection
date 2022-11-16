package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-10-28 15:53
 * Lis服务器设置
 */
public class ServerSettings extends Activity {
    private Button serverSettingsReturnButton, serverSettingsConfirm;
    private EditText ipAddress, uploadPort;
    private Spinner uploadWay;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lis_server_setting);
        MyApplication.getApplication().addActivity(this);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        // 点击返回按钮，返回通用设置页
        serverSettingsReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServerSettings.this, GeneralSettings.class);
                startActivityForResult(intent, 1);
            }
        });

        // 点击确认按钮，保存服务器上传信息
        serverSettingsConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("ip", ipAddress.getText().toString()).commit();
                sharedPreferences.edit().putString("port", uploadPort.getText().toString()).commit();
                sharedPreferences.edit().putInt("uploadWay", uploadWay.getSelectedItemPosition()).commit();
            }
        });
    }

    private void initControl() {
        // 确认按钮
        serverSettingsConfirm = findViewById(R.id.serverSettingsConfirm);
        // 返回按钮
        serverSettingsReturnButton = findViewById(R.id.serverSettingsReturnButton);
        // IP地址
        ipAddress = findViewById(R.id.ipAddress);
        ipAddress.setText(sharedPreferences.getString("ip", ""));
        // 端口
        uploadPort = findViewById(R.id.port);
        uploadPort.setText(sharedPreferences.getString("port", ""));
        // 上传方式
        uploadWay = findViewById(R.id.uploadWay);
        uploadWay.setSelection(sharedPreferences.getInt("uploadWay", 0));
    }
}
