package com.example.drugdetection.MainProcess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.Nullable;
import com.example.drugdetection.Mian.MyApplication;
import com.example.drugdetection.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lwf
 * @date 2022-10-28 11:57
 * 历史
 */
public class HistoricalRecords extends Activity {
    private ListView listView;
    private Button delete, upload, print, historicalSidebar;
    private CheckBox historicalCheckBox;
    private int mode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.historical_records);
        MyApplication.getApplication().addActivity(this);
        // 初始化控件
        initControl();
        // 监听
        listeningToThe();
        // 查询所有历史记录
        List<HashMap<String, Object>> list = queryHistorical();
        /**
        * 创建适配器
        *@param: 当前上下文
        *@param: 数据源
        *@param: item布局文件
        *@param: 绑定数据源中map的key
        *@param: 绑定item中控件id
        *@return: 返回适配器
        */
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.list_item,
                new String[]{"historySerialNumber", "historyTestTime", "historyProjectName", "historySampleType"},
                new int[]{R.id.historySerialNumber, R.id.historyTestTime, R.id.historyProjectName, R.id.historySampleType});
        // 为ListView设置适配器
        listView.setAdapter(simpleAdapter);
    }

    private void listeningToThe() {
        // item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 获取点击的item并转成map
                HashMap<String, Object> itemMap = (HashMap<String, Object>) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(HistoricalRecords.this, TestResults.class);
                // 将获取到的流水号一起传过去
                intent.putExtra("serialNumber", (String) itemMap.get("historySerialNumber"));
                startActivityForResult(intent, 1);
            }
        });

        // item长按事件
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                mode = 1;
                delete.setText("删除");
                upload.setText("上传");
                print.setText("打印");
                for (int j = 0; j < listView.getChildCount(); j++) {
                    LinearLayout childAt = (LinearLayout) listView.getChildAt(i);
                    historicalCheckBox = childAt.findViewById(R.id.historicalRecord);
                }
                historicalCheckBox.setVisibility(View.VISIBLE);
                return true;
            }
        });

        // 点击侧边栏
        historicalSidebar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoricalRecords.this, Sidebar.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initControl() {
        // 列表
        listView = findViewById(R.id.listItem);
        // 删除
        delete = findViewById(R.id.delete);
        // 上传
        upload = findViewById(R.id.upload);
        // 打印
        print = findViewById(R.id.print);
        // 侧边栏
        historicalSidebar = findViewById(R.id.historicalSidebar);
    }

    /**
    * 查询历史记录
    *@return: 返回所有历史记录
    */
    private List<HashMap<String, Object>> queryHistorical() {
        List<HashMap<String, Object>> historicalList = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("historySerialNumber", "202210141111");
        map.put("historyTestTime", "2022-10-14 15:00:00");
        map.put("historyProjectName", "T4");
        map.put("historySampleType", "血液");
        historicalList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("historySerialNumber", "202210142222");
        map1.put("historyTestTime", "2022-10-14 16:00:00");
        map1.put("historyProjectName", "CCV");
        map1.put("historySampleType", "毛发");
        historicalList.add(map1);
        return historicalList;
    }
}
