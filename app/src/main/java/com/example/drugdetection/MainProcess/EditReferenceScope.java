package com.example.drugdetection.MainProcess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.drugdetection.Database.LiteDatabase;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

/**
 * @author lwf
 * @date 2022-11-07 15:29
 * 修改参考范围
 */
public class EditReferenceScope extends Activity {
    private Button editReferenceScopeReturnButton, editReferenceScopeConfirm;
    private EditText modifiedComponentName, changesInLowRange, modifyScopeHighValue, modifyAgeMinimum, modifyAgeHighValue, modifyUnit;
    private RadioButton sex;
    private RadioGroup gender;
    private int id;
    private SQLiteDatabase sqLiteDatabase;
    private LiteDatabase liteDatabase;
    private Cursor cursor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reference_scope);
        MyApplication.getApplication().addActivity(this);
        liteDatabase = new LiteDatabase(EditReferenceScope.this);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
    }

    private void listeningToThe() {
        // 点击确认按钮
        editReferenceScopeConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 子项目名称
                String componentName = modifiedComponentName.getText().toString();
                // 范围最小值
                String lowRange = changesInLowRange.getText().toString();
                // 范围最大值
                String rangeHighValue = modifyScopeHighValue.getText().toString();
                // 范围 格式为0-100
                String referenceRange = lowRange + "-" + rangeHighValue;
                // 年龄最小值
                String ageMin = modifyAgeMinimum.getText().toString();
                // 年龄最大值
                String ageMax = modifyAgeHighValue.getText().toString();
                // 年龄 格式为0-100
                String age = ageMin + "-" + ageMax;
                // 单位
                String unit = modifyUnit.getText().toString();
                // 获取选择的性别
                sex = findViewById(gender.getCheckedRadioButtonId());
                String sexStr = sex.getText().toString();
                // 修改参考范围的sql语句
                String sql = String.format("update reference_range set component_name='%s', reference_range='%s', age='%s', unit='%s', gender='%s' where id=%s",
                        componentName, referenceRange, age, unit, sexStr, id);
                try {
                    sqLiteDatabase = liteDatabase.openDB();
                    if (sqLiteDatabase == null) {
                        liteDatabase.CloseDB(null, cursor);
                        throw new SQLException();
                    }
                    liteDatabase.execSQL(sqLiteDatabase, sql);
                    // 修改成功，返回参考范围管理页
                    Intent intent = new Intent(EditReferenceScope.this, ReferenceRangeManagement.class);
                    startActivityForResult(intent, 1);
                } catch (Exception e) {
                    Log.e("修改参考范围", e.getMessage());
                } finally {
                    liteDatabase.CloseDB(sqLiteDatabase, cursor);
                }
            }
        });

        // 点击返回按钮
        editReferenceScopeReturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditReferenceScope.this, ReferenceRangeManagement.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @SuppressLint("WrongViewCast")
    private void initControl() {
        // 子项目名称
        modifiedComponentName = findViewById(R.id.modifiedComponentName);
        // 范围最小值
        changesInLowRange = findViewById(R.id.changesInLowRange);
        // 范围最大值
        modifyScopeHighValue = findViewById(R.id.modifyScopeHighValue);
        // 年龄最小值
        modifyAgeMinimum = findViewById(R.id.modifyAgeMinimum);
        // 年龄最大值
        modifyAgeHighValue = findViewById(R.id.modifyAgeHighValue);
        // 单位
        modifyUnit = findViewById(R.id.modifyUnit);
        // 性别
        gender = findViewById(R.id.gender);
        // 设置默认选中第一个按钮
        gender.check(0);
        // 返回按钮
        editReferenceScopeReturnButton = findViewById(R.id.editReferenceScopeReturnButton);
        // 确认按钮
        editReferenceScopeConfirm = findViewById(R.id.editReferenceScopeConfirm);
        // 获取传过来的id
        id = getIntent().getIntExtra("id", 0);
    }
}
