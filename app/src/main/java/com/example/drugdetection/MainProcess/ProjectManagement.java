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
 * @date 2022-11-01 14:57
 */
public class ProjectManagement extends Activity {
    private ListView projectManagementListItem;
    private Button projectManagementSidebar;
    private List<HashMap<String, Object>> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_management);
        MyApplication.getApplication().addActivity(this);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
        // 查询所有项目
        list = queryProjectAll();
        /**
         * 创建适配器
         *@param: 当前上下文
         *@param: 数据源
         *@param: item布局文件
         *@param: 绑定数据源中map的key
         *@param: 绑定item中控件id
         *@return: 返回适配器
         */
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.project_management_list_tiem,
                new String[]{"projectName"},
                new int[]{R.id.projectName});
        projectManagementListItem.setAdapter(simpleAdapter);
    }

    private List<HashMap<String, Object>> queryProjectAll() {
        List<HashMap<String, Object>> projectList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("projectName", "CDV/CPV/ICH-Ab (8308)");
        map.put("id", 1);
        projectList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("projectName", "QC (7315)");
        map1.put("id", 2);
        projectList.add(map1);
        return projectList;
    }

    private void listeningToThe() {
        // item点击事件
        projectManagementListItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String, Object> itemMap = (HashMap<String, Object>) adapterView.getItemAtPosition(i);
                int id = (int) itemMap.get("id");
                return false;
            }
        });

        // 点击侧边栏
        projectManagementSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProjectManagement.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 列表
        projectManagementListItem = findViewById(R.id.project_management_list_item);
        // 侧边栏
        projectManagementSidebar = findViewById(R.id.projectManagementSidebar);
    }
}
