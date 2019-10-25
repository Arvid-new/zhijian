package com.haozhiyan.zhijian.activity;

import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.FragmentAdapter;
import com.haozhiyan.zhijian.fragment.xcjcFragment.Gcld_new_Fragment;
import com.haozhiyan.zhijian.fragment.xcjcFragment.Gcld_zan_Fragment;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 工程亮点
 */
public class GCLDActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.zxRL)
    RelativeLayout zxRL;
    @ViewInject(R.id.zzRL)
    RelativeLayout zzRL;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.zzTV)
    TextView zzTV;
    @ViewInject(R.id.zxTV)
    TextView zxTV;
    @ViewInject(R.id.zxLine)
    View zxLine;
    @ViewInject(R.id.zzLine)
    View zzLine;
    //    @ViewInject(R.id.tabs)
//    TabLayout mTabLayout;
    @ViewInject(R.id.vp_view)
    ViewPager mViewPager;
    @ViewInject(R.id.float_btn)
    ImageView floatBtn;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gcld;
    }

    @Override
    protected void initView() {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new Gcld_new_Fragment(), "最新");
        adapter.addFragment(new Gcld_zan_Fragment(), "最赞");
        mViewPager.setAdapter(adapter);
        //绑定,会自动绑定
//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        tv_title.setText("工程亮点");

    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @Override
    protected void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    zxTV.setTextColor(ContextCompat.getColor(GCLDActivity.this, R.color.blue5));
                    zxLine.setBackground(ContextCompat.getDrawable(GCLDActivity.this, R.color.blue5));
                    zzTV.setTextColor(ContextCompat.getColor(GCLDActivity.this, R.color.black3));
                    zzLine.setBackground(ContextCompat.getDrawable(GCLDActivity.this, R.color.translate));
                } else {
                    zxTV.setTextColor(ContextCompat.getColor(GCLDActivity.this, R.color.black3));
                    zxLine.setBackground(ContextCompat.getDrawable(GCLDActivity.this, R.color.translate));
                    zzTV.setTextColor(ContextCompat.getColor(GCLDActivity.this, R.color.blue5));
                    zzLine.setBackground(ContextCompat.getDrawable(GCLDActivity.this, R.color.blue5));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.rl_back, R.id.zxRL, R.id.zzRL, R.id.float_btn})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.zxRL://最新
                zxTV.setTextColor(ContextCompat.getColor(this, R.color.blue5));
                zxLine.setBackground(ContextCompat.getDrawable(this, R.color.blue5));
                zzTV.setTextColor(ContextCompat.getColor(this, R.color.black3));
                zzLine.setBackground(ContextCompat.getDrawable(this, R.color.translate));
                mViewPager.setCurrentItem(0);
                break;
            case R.id.zzRL://最赞
                zxTV.setTextColor(ContextCompat.getColor(this, R.color.black3));
                zxLine.setBackground(ContextCompat.getDrawable(this, R.color.translate));
                zzTV.setTextColor(ContextCompat.getColor(this, R.color.blue5));
                zzLine.setBackground(ContextCompat.getDrawable(this, R.color.blue5));
                mViewPager.setCurrentItem(1);
                break;
            case R.id.float_btn:
                jumpToActivity(AddLDActivity.class);
                break;
            default:
                break;
        }
    }

}
