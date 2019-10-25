package com.haozhiyan.zhijian.activity.workXcjc;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class JianChaGuideDetail extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_name)
    TextView tvName;
    @ViewInject(R.id.tv_content)
    TextView tvContent;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jian_cha_guide_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("检查指引");
        Bundle bundle = getIntent().getBundleExtra("data");
        tvName.setText(bundle == null ? "检查指引无标题" : bundle.getString("name"));
        tvContent.setText(bundle == null ? "检查指引无内容" : StringUtils.enterStr(bundle.getString("guide")));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            default:
                break;
        }
    }
}
