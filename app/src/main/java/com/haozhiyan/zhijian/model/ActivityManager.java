package com.haozhiyan.zhijian.model;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wangzhenkai on 2019/4/22.
 */
public class ActivityManager {

    public static ActivityManager mActivityManager;
    /**
     * 存放Activity的map
     */
    private List<Activity> mActivities = new ArrayList<Activity>();

    //将构造方法私有化，所以不能通构造方法来初始化ActivityManager
    private ActivityManager() {
    }

    ;

    //采用单例模式初始化ActivityManager，使只初始化一次
    public static ActivityManager getInstance() {
        if (mActivityManager == null) {
            mActivityManager = new ActivityManager();
        }
        return mActivityManager;
    }

    //添加activity
    public void addActivity(Activity activity) {
        if (!mActivities.contains(activity)) {
            mActivities.add(activity);
        }
    }

    //移除activity
    public void removeActivity(Activity activity) {
        if (activity != null) {
            if (mActivities.contains(activity)) {
                mActivities.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    //将activity全部关闭掉
    public void clearAll() {
        for (Activity activity : mActivities) {
            activity.finish();
        }
    }

    //将activity全部关闭掉,除掉MainAcitiy
    public void clearOther() {

        for (Activity activity : mActivities) {
            if (activity.getClass().getSimpleName().equals("RealNameCertificationActivity")) {

                continue;
            }
            activity.finish();
        }
    }

    public void removeActivity(Class<? extends Activity> actClass) {
        if (actClass != null) {
            if (mActivities.contains(actClass.getName())) {
                mActivities.remove(actClass);
            }
            try {
                actClass.newInstance().finish();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            actClass = null;
        }
    }
}
