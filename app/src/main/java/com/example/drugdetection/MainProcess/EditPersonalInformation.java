package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-11-09 19:28
 * 编辑个人信息
 */
public class EditPersonalInformation extends Activity {
    private Button editUserInfoReturnButton, editPersonalInformationConfirm;
    private LinearLayout photoCollection;
    private ImageView picture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_personal_information);
        MyApplication.getApplication().addActivity(this);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        // 点击照片采集
        photoCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // 点击确认按钮
        editPersonalInformationConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPersonalInformation.this, Test.class);
                startActivityForResult(intent, 1);
            }
        });

        // 点击返回按钮
        editUserInfoReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditPersonalInformation.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 确认按钮
        editPersonalInformationConfirm = findViewById(R.id.editPersonalInformationConfirm);
        // 返回按钮
        editUserInfoReturnButton = findViewById(R.id.editUserInfoReturnButton);
        // 照片采集
        photoCollection = findViewById(R.id.photoCollection);
        // 图片显示
        picture = findViewById(R.id.picture);
    }
}
