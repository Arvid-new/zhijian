package com.haozhiyan.zhijian.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.CacheUtil;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.widget.CancelSureDialog;

public class SettingActivity extends BaseActivity2 {

    private SettingActivity activity;
    private LinearLayout ll_clear_cache;
    private TextView tv_cache_size;

    @Override
    protected void init(Bundle savedInstanceState) {
        activity = this;
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_setting;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("设置");
        ll_clear_cache = getOutView(R.id.ll_clear_cache);
        tv_cache_size = getOutView(R.id.tv_cache_size);
        setListenerView(ll_clear_cache);

    }

    @Override
    protected void initData() {
        try {
            tv_cache_size.setText(CacheUtil.getTotalCacheSize(activity));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_clear_cache:
                new CancelSureDialog(this).setOkClick("确定要清除缓存数据吗？", new CancelSureDialog.OkClick() {
                    @Override
                    public void ok() {
                        try {
                            clearCache();
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                });

                break;
            default:
                break;
        }
    }

    /**
     * 清除缓存
     */
    private void clearCache() {
        CacheUtil.clearAllCache(activity);
        try {
            tv_cache_size.setText(CacheUtil.getTotalCacheSize(activity));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
