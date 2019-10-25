package com.haozhiyan.zhijian.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.StatusBarUtil;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * BaseActivity
 */

public abstract class BaseActivity2 extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    //常量部分 标题栏类型
    protected final int TITLEBAR_NULL = -1, TITLEBAR_DEFAULT = 0, TITLEBAR_SEARCH = 1;
    /**
     * 是否沉浸状态栏
     **/
    private boolean isSetStatusBar = true;
    /**
     * 日志输出标志-activity名称
     **/
    protected final String TAG = this.getClass().getSimpleName();

    private LinearLayout parentLinearLayout;
    private LinearLayout _barRightView;
    private LinearLayout _barLeftView;
    private TextView _barTitle;
    private ImageView _barTvBack;
    private ViewGroup _barToolbar; //仅当做 RelativeLayout 来使用
    private View _barCancelSearchView;
    private View searchRL;
    private EditText searchET;
    private Context context;
    private Activity activity;
    public RelativeLayout emptyView;
    public ImageView emptyImage;
    public UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        activity = this;
//        if (isSetStatusBar) {
//            try {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        init(savedInstanceState);
        switch (getTitleBarType()) {
            case TITLEBAR_NULL:
                //无标题栏
                break;
            default:
            case TITLEBAR_DEFAULT:
                initContentView(R.layout.layout_simple_toolbar);
                initTitleBarView();
                break;
            case TITLEBAR_SEARCH:
                initContentView(R.layout.layout_search_toolbar);
                initSearchBarView();
                break;
        }
        setContentView(setLayoutResourceID());

        userInfo = UserInfo.create(this);
        //初始化控件
        initConfigEmptyView();
        initView();
        //设置数据
        initData();
    }

    private void initTitleBarView() {
        this._barRightView = getOutView(R.id._barRightView);
        this._barLeftView = getOutView(R.id._barLeftView);
        this._barTitle = getOutView(R.id._barTitle);
        this._barTvBack = getOutView(R.id._barTvBack);
        this._barToolbar = getOutView(R.id._barToolbar);
    }

    private void initSearchBarView() {
        this._barRightView = getOutView(R.id._barRightView);
        this._barLeftView = getOutView(R.id._barLeftView);
        this._barTitle = getOutView(R.id._barTitle);
        this._barTvBack = getOutView(R.id._barTvBack);
        this._barToolbar = getOutView(R.id._barToolbar);
        this._barCancelSearchView = getOutView(R.id._barCancelSearchView);
        this.searchRL = getOutView(R.id.searchRL);
        this.searchET = getOutView(R.id.searchET);
        _barCancelSearchView.setOnClickListener(this);
        searchET.addTextChangedListener(this);
    }

    protected <T extends View> T getOutView(int id) {
        try {
            return (T) findViewById(id);
        } catch (Exception e) {
            return null;
        }
    }

    protected void setListenerView(View view) {
        try {
            view.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void setText(TextView textView, String text) {
        try {
            textView.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();
        if (vid == R.id._barCancelSearchView) {//隐藏 search布局
            searchET.setText("");
            searchRL.setVisibility(View.GONE);
            _barLeftView.setVisibility(View.VISIBLE);
            _barTitle.setVisibility(View.VISIBLE);
            _barRightView.setVisibility(View.VISIBLE);
            try {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 显示search 布局
     */
    protected void showSearchBar() {
        searchRL.setVisibility(View.VISIBLE);
        _barLeftView.setVisibility(View.GONE);
        _barTitle.setVisibility(View.GONE);
        _barRightView.setVisibility(View.GONE);
        searchET.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchET, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏search 布局
     */
    protected void hideSearchBar() {
        searchRL.setVisibility(View.GONE);
        _barLeftView.setVisibility(View.VISIBLE);
        _barTitle.setVisibility(View.VISIBLE);
        _barRightView.setVisibility(View.VISIBLE);
        searchET.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(searchET, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 子类在需要时可直接重写此方法
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    public LoadingDialog dialog;

    protected void showLoadView(String str) {
        try {
            if (dialog == null) {
                dialog = new LoadingDialog(this);
            }
            dialog.setTitle(str);
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void hideLoadView() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean arrayEmpty(JSONArray array) {
        if (array != null && array.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 自定义 toolbar 布局 加入到 viewgroup
     *
     * @param layoutResID
     */
    private void initContentView(@LayoutRes int layoutResID) {
        try {
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            viewGroup.removeAllViews();
            parentLinearLayout = new LinearLayout(this);
            parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
            viewGroup.addView(parentLinearLayout);
            //  add the layout of BaseActivity in parentLinearLayout
            LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinearLayout getParentLinearLayout() {
        return parentLinearLayout;
    }

    /**
     * 重写该方法 用来使 子 Activity 复用 toolbar
     *
     * @param layoutResID
     */
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //  added the sub-activity layout id in parentLinearLayout
        if (parentLinearLayout == null) {
            super.setContentView(layoutResID);
        } else {
            LayoutInflater.from(this).inflate(layoutResID, parentLinearLayout, true);
        }

    }

    protected abstract void init(Bundle savedInstanceState);


    protected abstract int setLayoutResourceID();

    /**
     * 获取要使用的标题栏样式(此函数由子类重写)
     *
     * @return
     */
    protected abstract int getTitleBarType();

    protected abstract void initView();

    protected abstract void initData();


    /**
     * 修改状态栏颜色
     *
     * @param colorId
     */
    protected void setStatusBarColor(@ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(getContext(), colorId));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 修改状态栏文字颜色
     *
     * @param isdark true 黑色  false白色
     */
    protected void setAndroidNativeLightStatusBar(boolean isdark) {
        try {
            if (isdark) {//黑色状态栏字体
                StatusBarUtil.setLightMode(this);
            } else {//白色状态栏字体
                StatusBarUtil.setDarkMode(this);
            }
//            View decor = getWindow().getDecorView();
//            if (isdark) {
//                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//            } else {
//                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Glide.with(this).resumeRequests();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Glide.with(this).pauseRequests();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                finishAfterTransition();
//            }
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            OkGo.getInstance().cancelTag(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return activity;
    }


    public void startActivity(Class<?> clz) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        startActivity(intent);
    }


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
     * [含有Bundle通过Class打开编辑界面]
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 传递任意数据
     *
     * @param actClass
     * @param bundle
     */
    protected void jumpToActivity(Class<? extends Activity> actClass, Bundle bundle) {
        Intent intent = new Intent(context, actClass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    //抽取积累的方法跳转界面
    protected void jumpToActivity(Class<? extends Activity> actClass) {
        Intent intent = new Intent(context, actClass);
        startActivity(intent);
    }

    /**
     * 传递集合数据
     *
     * @param actClass
     * @param list
     */
    protected void jumpToActivity(Class<? extends Activity> actClass, List list) {
        Intent intent = new Intent(context, actClass);
        intent.putExtra("list", (Serializable) list);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    /**
     * 标题栏后退按钮点击事件 在xml中调用
     *
     * @param v
     */
    public void onTitleBarBackBtnClick(View v) {
        setResult(Constant.PROBLEM_DETAIL_RESULT_CODE);
        this.finish();//结束当前Activity，并退出窗口
    }

    /**
     * [是否设置沉浸状态栏]
     *
     * @param isSetStatusBar
     */
    public void setSteepStatusBar(boolean isSetStatusBar) {
        this.isSetStatusBar = isSetStatusBar;
    }


    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitleText(@StringRes int text) {
        try {
            _barTitle.setText(getString(text));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置返回按钮图标
     *
     * @param imgid
     */
    @SuppressLint("ResourceType")
    public void setTitleFinishImg(int imgid) {
        try {
            _barTvBack.setImageResource(imgid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题
     *
     * @param text
     */
    public void setTitleText(String text) {
        try {
            _barTitle.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题背景颜色
     *
     * @param
     */
    public void setTitleBackColor(@ColorRes int colorRes) {
        try {
            _barToolbar.setBackground(ContextCompat.getDrawable(this, colorRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题背景颜色
     *
     * @param
     */
    public void setTitleBackDrawable(@DrawableRes int drawableRes) {
        try {
            _barToolbar.setBackground(ContextCompat.getDrawable(this, drawableRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置标题文字颜色
     *
     * @param colorRes
     */
    public void setTitleTextColor(@ColorRes int colorRes) {
        try {
            _barTitle.setTextColor(ContextCompat.getColor(this, colorRes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public ViewGroup getToolbar() {
        return _barToolbar;
    }

    /**
     * 获取titlebar  右边的 viewgroup
     *
     * @return
     */
    public LinearLayout getBarRightView() {
        return _barRightView;
    }

    /**
     * 获取title
     *
     * @return
     */
    public TextView getTitleTextView() {
        return _barTitle;
    }

    /**
     * 获取titlebar  左边的 viewgroup
     *
     * @return
     */
    public LinearLayout getBarLeftView() {
        return _barLeftView;
    }

    private void initConfigEmptyView() {
        View view = LayoutInflater.from(this).inflate(R.layout.app_layout_empty, null);
        emptyView = view.findViewById(R.id.main_empty);
        emptyView.setVisibility(View.VISIBLE);
        emptyImage = view.findViewById(R.id.img);
    }
}
