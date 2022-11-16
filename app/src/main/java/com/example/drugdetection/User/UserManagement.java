package com.example.drugdetection.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.drugdetection.MainProcess.ProjectManagement;
import com.example.drugdetection.MainProcess.Sidebar;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lwf
 * @date 2022-11-01 11:00
 * 用户管理
 */
public class UserManagement extends Activity {
    private ListView userManagementListItem;
    private Button userManagementSidebar;
    private List<HashMap<String, Object>> list;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_management);
        MyApplication.getApplication().addActivity(this);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
        // 查询所有用户
        list = queryUserAll();
        /**
         * 创建适配器
         *@param: 当前上下文
         *@param: 数据源
         *@param: item布局文件
         *@param: 绑定数据源中map的key
         *@param: 绑定item中控件id
         *@return: 返回适配器
         */
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.user_management_list_tiem,
                new String[]{"userManagementName", "userManagementAccount"},
                new int[]{R.id.userManagementName, R.id.userManagementAccount});
        userManagementListItem.setAdapter(simpleAdapter);
    }

    private void initControl() {
        // 列表
        userManagementListItem = findViewById(R.id.userManagementListItem);
        // 侧边栏
        userManagementSidebar = findViewById(R.id.userManagementSidebar);
    }

    private void listeningToThe() {
        // item点击事件
        userManagementListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取点击的item并转成map
                HashMap<String, Object> itemMap = list.get(i);
                // 获取点击item的账号
                int id = (int) itemMap.get("id");
                // 判断当前登录账号的类型
                switch (sharedPreferences.getInt("userType", 2)) {
                    case 0:
                        // 普通用户只能修改自己的账号密码
                        if (id == sharedPreferences.getInt("id", 0)) {
                            Intent intent = new Intent(UserManagement.this, EditUser.class);
                            // 将需要修改的id传到编辑页
                            intent.putExtra("id", id);
                            startActivityForResult(intent, 1);
                        } else {
                            Toast.makeText(getApplicationContext(), "只能编辑自己的用户", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case 1:
                        // 管理用户能修改所有人的账号密码
                        Intent intent = new Intent(UserManagement.this, EditUser.class);
                        // 将需要修改的id传到编辑页
                        intent.putExtra("id", id);
                        startActivityForResult(intent, 1);
                }
            }
        });

        // 点击侧边栏
        userManagementSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserManagement.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private List<HashMap<String, Object>> queryUserAll() {
        List<HashMap<String, Object>> UserList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", "1");
        map.put("userManagementName", "测试1");
        map.put("userManagementAccount", "admin");
        UserList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map.put("id", "2");
        map.put("userManagementName", "测试2");
        map.put("userManagementAccount", "healvet");
        UserList.add(map1);
        return UserList;
    }
}
