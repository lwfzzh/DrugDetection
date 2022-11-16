package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-10-28 11:55
 * 通用设置
 */
public class GeneralSettings extends Activity {
    private Spinner language, recordKeepingDays, resultType;
    private LinearLayout serverSettings;
    private ToggleButton testCard, scanBarcode, instantPrint, instantUpload;
    private EditText nameInstitution, areaCode;
    private SharedPreferences sharedPreferences;
    private Button generalSettingsSidebar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_settings);
        MyApplication.getApplication().addActivity(this);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        // 选择语言
        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sharedPreferences.edit().putInt("language", language.getSelectedItemPosition()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 选择记录保存天数
        recordKeepingDays.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sharedPreferences.edit().putInt("recordKeepingDays", recordKeepingDays.getSelectedItemPosition()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 选择结果类型
        resultType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sharedPreferences.edit().putInt("resultType", resultType.getSelectedItemPosition()).commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 设置是否检测插卡
        testCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean("testCard", testCard.isChecked()).commit();
            }
        });

        // 设置是否扫描条码
        scanBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean("scanBarcode", scanBarcode.isChecked()).commit();
            }
        });

        // 设置是否即时打印
        instantPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean("instantPrint", instantPrint.isChecked()).commit();
            }
        });

        // 设置是否即时上传
        instantUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putBoolean("instantUpload", instantUpload.isChecked()).commit();
            }
        });

        // 设置机构名
        nameInstitution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("nameInstitution", nameInstitution.getText().toString()).commit();
            }
        });

        // 设置区域码
        areaCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreferences.edit().putString("areaCode", areaCode.getText().toString()).commit();
            }
        });

        // 点击Lis服务器设置
        serverSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralSettings.this, ServerSettings.class);
                startActivityForResult(intent, 1);
            }
        });

        // 点击侧边栏
        generalSettingsSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GeneralSettings.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    private void initControl() {
        // 语言
        language = findViewById(R.id.language);
        language.setSelection(sharedPreferences.getInt("language", 0));
        // 测试时检测插卡
        testCard = findViewById(R.id.testCard);
        testCard.setChecked(sharedPreferences.getBoolean("testCard", true));
        // 测试时扫描条码
        scanBarcode = findViewById(R.id.scanBarcode);
        scanBarcode.setChecked(sharedPreferences.getBoolean("scanBarcode", true));
        // 测试后即时打印
        instantPrint = findViewById(R.id.instantPrint);
        instantPrint.setChecked(sharedPreferences.getBoolean("instantPrint", true));
        // 测试后即时上传
        instantUpload = findViewById(R.id.instantUpload);
        instantUpload.setChecked(sharedPreferences.getBoolean("instantUpload", true));
        // 记录保存天数
        recordKeepingDays = findViewById(R.id.recordKeepingDays);
        recordKeepingDays.setSelection(sharedPreferences.getInt("recordKeepingDays", 0));
        // 结果类型
        resultType = findViewById(R.id.resultType);
        resultType.setSelection(sharedPreferences.getInt("resultType", 0));
        // 机构名
        nameInstitution = findViewById(R.id.nameInstitution);
        nameInstitution.setText(sharedPreferences.getString("nameInstitution", ""));
        // 区域代码
        areaCode = findViewById(R.id.areaCode);
        areaCode.setText(sharedPreferences.getString("areaCode", ""));
        // Lis服务器设置
        serverSettings = findViewById(R.id.serverSettings);
        // 侧边栏
        generalSettingsSidebar = findViewById(R.id.generalSettingsSidebar);
    }
}
