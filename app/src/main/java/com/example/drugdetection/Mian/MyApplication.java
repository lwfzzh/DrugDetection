package com.example.drugdetection.Mian;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lwf
 * @date 2022-10-28 11:35
 */
public class MyApplication extends Application {
    private static List<Activity> activities;
    private static MyApplication application;

    public MyApplication() {
        activities = new LinkedList<>();
    }

    /**
     * 单例模式中获取唯一的MyApplication实例
     * @return MyApplication实例化对象
     */
    public static MyApplication getApplication() {
        if (null == application) {
            application = new MyApplication();
        }
        return application;
    }

    // 添加Activity到容器中
    public void addActivity(Activity activity) {
        if (activities != null && activities.size() > 0) {
            if (!activities.contains(activity)) {
                activities.add(activity);
            }
        } else {
            activities.add(activity);
        }
    }

    // 遍历所有Activity并finish
    public static void exit() {
        try {
            if (activities != null && activities.size() > 0) {
                for (Activity activity : activities) {
                    activity.finish();
                }
            }

            activities.clear();
        } catch (Exception e) {
            System.exit(0);
        }
    }
}
