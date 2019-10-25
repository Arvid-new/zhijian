package com.haozhiyan.zhijian.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.NetUtils;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.lidroid.xutils.ViewUtils;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.List;

/**
 * @author Wangzhenkai
 * @Date 2019.3.26
 */
public abstract class BaseFragment extends Fragment {

    public static boolean isNetWork = false;
    public boolean isVisible;//Fragment当前状态是否可见
    private View view = null;
    public Activity ctx;
    public LoadingDialog dialog;
    public RelativeLayout emptyView;
    public ImageView emptyImage;
    public TextView errorRemind;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = View.inflate(getActivity(), getLayoutResId(), null);
        isNetWork = NetUtils.checkNetWork(getActivity());
        ctx = this.getActivity();
        ViewUtils.inject(ctx);
        initView(view);
        initViewBundle(savedInstanceState);
        initConfigEmptyView();
        initListener();
        initData(isNetWork);
        return view;
    }

    public abstract int getLayoutResId();

    public abstract void initView(View view);

    public void initViewBundle(Bundle savedInstanceState) {

    }

    public abstract void initListener();

    public abstract void initData(boolean isNetWork);

    protected <T extends View> T getView(int id) {
        return (T) view.findViewById(id);
    }

    protected <T extends View> T getOutView(View view, int id) {
        try {
            return (T) view.findViewById(id);
        } catch (Exception e) {
            return null;
        }
    }

    protected void setText(TextView textView, String text) {
        try {
            textView.setText(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * setUserVisibleHint是在onCreateView之前调用的
     * 设置Fragment可见状态
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /**
         * 判断是否可见
         */
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    private void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    private void onInvisible() {
    }

    /**
     * 延迟加载 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    //设置全局空布局显示
    private void initConfigEmptyView() {
        View view = LayoutInflater.from(this.getActivity()).inflate(R.layout.app_layout_empty, null);
        emptyView = getOutView(view, R.id.main_empty);
        emptyView.setVisibility(View.VISIBLE);
        emptyImage = getOutView(view, R.id.img);
        errorRemind = getOutView(view, R.id.tv_remind);
    }

    protected void jumpActivityForResult(Class<? extends Activity> actClass, int resultCode, Bundle bundle) {
        Constant.REQUEST_CODE = resultCode;
        Intent intent = new Intent(getActivity(), actClass);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, resultCode);
        getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    /**
     * 抽取积累的方法跳转界面
     */
    protected void jumpToActivity(Class<? extends Activity> actClass) {
        Intent intent = new Intent(getActivity(), actClass);
        startActivity(intent);
    }

    protected void jumpToActivity(Class<? extends Activity> actClass, Bundle bundle) {
        Intent intent = new Intent(getActivity(), actClass);
        intent.putExtra("data", bundle);
        startActivity(intent);
    }

    protected void jumpToActivity(Intent intent) {
        startActivity(intent);
    }

    protected void jumpToActivity(Class<? extends Activity> actClass, List list) {
        Intent intent = new Intent(getActivity(), actClass);
        intent.putExtra("list", (Serializable) list);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected int setColor(int color) {
        Resources resource = getActivity().getResources();
        return resource.getColor(color);
    }

    protected void showLoadView(String str) {
        dialog = new LoadingDialog(ctx);
        dialog.setTitle(str);
        dialog.show();
    }

    protected void hideLoadView() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        }
        return false;
    }

    /**
     * 打印log
     *
     * @param msg
     */
    public void logInfo(String msg) {
        LogUtils.e(getClass(), msg);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
