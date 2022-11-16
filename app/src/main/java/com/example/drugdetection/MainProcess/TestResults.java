package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-10-28 19:39
 * 检测结果
 */
public class TestResults extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_results);
        MyApplication.getApplication().addActivity(this);
        // 获取传过来的流水号
        String serialNumber = getIntent().getStringExtra("serialNumber");
        Log.i("检测结果流水号", serialNumber);
    }
}
