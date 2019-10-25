package com.haozhiyan.zhijian.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.AppUtils;
import com.haozhiyan.zhijian.utils.NetUtils;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.lidroid.xutils.ViewUtils;
import com.lzy.okgo.OkGo;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

//窗口基类
public abstract class BaseActivity extends AppCompatActivity {

    public static boolean isNetWork = false;
    protected Activity act;
    public static BaseActivity baseAct;
    private ActivityManager activityManager;
    public RelativeLayout emptyView;
    public TextView errorRemind;
    public ImageView emptyImage;
    public LoadingDialog dialog;
    public UserInfo userInfo;
    public String sysTime = AppUtils.getSystemDate("yyyy-MM-dd") + " " + AppUtils.getSystemTime("HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        baseAct = this;
        getThemeStyle();
        isNetWork = NetUtils.checkNetWork(getApplication());
        setContentView(getLayoutResId());
        act = this;
        ViewUtils.inject(this);
        activityManager = ActivityManager.getInstance();
        activityManager.addActivity(this);
        dialog = new LoadingDialog(this);
        userInfo = UserInfo.create(this);
        initView();
        initConfigEmptyView();
        initListener();
        initData(isNetWork);
    }

    public abstract void getThemeStyle();

    public abstract int getLayoutResId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData(boolean isNetWork);

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    protected <T extends View> T getOutView(View view, int id) {
        return (T) view.findViewById(id);
    }

    public static BaseActivity getForegroundActivity() {
        return baseAct;
    }

    protected void jumpToActivity(Class<? extends Activity> actClass) {
        Intent intent = new Intent(act, actClass);
        startActivity(intent);
    }

    protected void jumpToActivityFinish(Class<? extends Activity> actClass) {
        jumpToActivity(actClass);
        finish();
    }

    //右侧滑入
    protected void jumpActivityForResult(Class<? extends Activity> actClass, int resultCode) {
        Constant.REQUEST_CODE = resultCode;
        startActivityForResult(new Intent(this, actClass), resultCode);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    protected void jumpActivityForResult(Class<? extends Activity> actClass, int resultCode, Bundle bundle) {
        Constant.REQUEST_CODE = resultCode;
        Intent intent = new Intent(this, actClass);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, resultCode);
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * 传递任意数据
     *
     * @param actClass
     * @param bundle
     */
    protected void jumpToActivity(Class<? extends Activity> actClass, Bundle bundle) {
        Intent intent = new Intent(act, actClass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    //传递集合数据
    protected void jumpToActivity(Class<? extends Activity> actClass, List list) {
        Intent intent = new Intent(act, actClass);
        intent.putExtra("list", (Serializable) list);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected void showLoadView(String str) {
        dialog.setTitle(str);
        dialog.show();
    }

    protected void hideLoadView() {
        dialog.dismiss();
    }

    protected int setColor(int color) {
        Resources resource = act.getResources();
        return resource.getColor(color);
    }

    public boolean listEmpty(List list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public boolean arrayEmpty(JSONArray array) {
        if (array != null && array.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //设置全局空布局显示
    private void initConfigEmptyView() {
        View view = LayoutInflater.from(this).inflate(R.layout.app_layout_empty, null);
        emptyView = getOutView(view, R.id.main_empty);
        emptyView.setVisibility(View.VISIBLE);
        emptyImage = getOutView(view, R.id.img);
        errorRemind = getOutView(view, R.id.tv_remind);
    }

    //用来控制应用前后台切换的逻辑
    private boolean isCurrentRunningForeground = true;

    public boolean isRunningForeground() {
        android.app.ActivityManager activityManager = (android.app.ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<android.app.ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        //枚举进程
        for (android.app.ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            System.gc();
            OkGo.getInstance().cancelTag(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
        }
    }

}
