package com.haozhiyan.zhijian.activity.workScsl;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.adapter.FragmentAdapter;
import com.haozhiyan.zhijian.fragment.xcjcFragment.JldwFragment;
import com.haozhiyan.zhijian.fragment.xcjcFragment.JsdwFragment;
import com.haozhiyan.zhijian.fragment.xcjcFragment.SgdwFragment;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

//实测实量汇总数据详情
public class SCSLCollectDetail extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.tabs)
    TabLayout mTabLayout;
    @ViewInject(R.id.vp_view)
    ViewPager mViewPager;
    @ViewInject(R.id.tv_jcx)
    TextView tvJcx;
    private String towerUnit;
    private SgdwFragment sgdwFragment;
    private JldwFragment jldwFragment;
    private JsdwFragment jsdwFragment;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_scslcollect_detail;
    }

    @Override
    protected void initView() {
        ivClose.setVisibility(View.VISIBLE);
        String floor = getIntent().getStringExtra("floor");
        String jcx = getIntent().getStringExtra("jcx");
        towerUnit = getIntent().getStringExtra("towerUnit");
        tv_title.setText(StringUtils.isEmpty(floor) ? "检查层" : floor + "层");
        tvJcx.setText(StringUtils.isEmpty(floor) ? "暂无检查项" : jcx);
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        sgdwFragment = new SgdwFragment();
        jldwFragment = new JldwFragment();
        jsdwFragment = new JsdwFragment();
        adapter.addFragment(sgdwFragment, "施工单位");
        adapter.addFragment(jldwFragment, "监理单位");
        adapter.addFragment(jsdwFragment, "建设单位");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void initListener() {
        sgdwFragment.floorUnitRoom(towerUnit);
        jldwFragment.floorUnitRoom(towerUnit);
        jsdwFragment.floorUnitRoom(towerUnit);
    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back, R.id.iv_close})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                jumpToActivity(MainActivity.class);
                ActivityManager.getInstance().clearAll();
                break;
            default:
                break;
        }
    }
}
