package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lwf
 * @date 2022-11-01 14:59
 */
public class ReferenceRangeManagement extends Activity {
    private ListView referenceRangeList;
    private Button referenceScopeManagementSidebar;
    private List<HashMap<String, Object>> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reference_scope_management);
        MyApplication.getApplication().addActivity(this);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
        // 查询所有项目
        list = queryReferenceAll();
        /**
         * 创建适配器
         *@param: 当前上下文
         *@param: 数据源
         *@param: item布局文件
         *@param: 绑定数据源中map的key
         *@param: 绑定item中控件id
         *@return: 返回适配器
         */
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.reference_scope_management_list_tiem,
                new String[]{"referenceProjectName", "reference"},
                new int[]{R.id.referenceProjectName, R.id.reference});
        referenceRangeList.setAdapter(simpleAdapter);
    }

    private List<HashMap<String, Object>> queryReferenceAll() {
        List<HashMap<String, Object>> itemList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("referenceProjectName", "CDV/CPV/ICH-Ab (8308)");
        map.put("reference", "0-0.2");
        map.put("id", 1);
        itemList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("referenceProjectName", "QC (7315)");
        map1.put("reference", "20-100");
        map1.put("id", 22);
        itemList.add(map1);
        return itemList;
    }

    private void listeningToThe() {
        // item点击事件
        referenceRangeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取点击的item并转成map
                HashMap<String, Object> hashMap = (HashMap<String, Object>) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(ReferenceRangeManagement.this, EditReferenceScope.class);
                // 将id传到修改参考范围页
                intent.putExtra("id", (int) hashMap.get("id"));
                startActivityForResult(intent, 1);

            }
        });
        // 点击侧边栏
        referenceScopeManagementSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReferenceRangeManagement.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 列表
        referenceRangeList = findViewById(R.id.referenceRangeList);
        // 侧边栏
        referenceScopeManagementSidebar = findViewById(R.id.referenceScopeManagementSidebar);
    }
}
