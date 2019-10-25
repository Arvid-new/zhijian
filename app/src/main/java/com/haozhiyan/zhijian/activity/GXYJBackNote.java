package com.haozhiyan.zhijian.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.AcceptanceMaterialsFGAdapter;
import com.haozhiyan.zhijian.fragment.gxyjBackNoteFragment.BackOneFragment;
import com.haozhiyan.zhijian.fragment.gxyjBackNoteFragment.BackThreeFragment;
import com.haozhiyan.zhijian.fragment.gxyjBackNoteFragment.BackTwoFragment;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.view.ScaleTransitionPagerTitleView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

//退回次数页面展示
public class GXYJBackNote extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.viewPager)
    ViewPager viewPager;
    @ViewInject(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    private List<String> titles;
    private List<Fragment> fragmentList;
    private String backNum = "1";
    private String id = "";
    private BackOneFragment backOneFragment;
    private BackTwoFragment backTwoFragment;
    private BackThreeFragment backThreeFragment;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gxyjback_note;
    }

    @Override
    protected void initView() {
        tv_title.setText("退回记录");
        id = getIntent().getStringExtra("id");
        backNum = getIntent().getStringExtra("backNum");
        initFragmentAdapter(backNum);
        initMagicIndicator();
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

    private void initFragmentAdapter(String num) {
        int number = 1;
        backOneFragment = new BackOneFragment();
        backTwoFragment = new BackTwoFragment();
        backThreeFragment = new BackThreeFragment();
        if (num.equals("1")) {
            number = 1;
            titles = new ArrayList<>();
            titles.add("第1次");
            fragmentList = new ArrayList<>();
            backOneFragment.getId(id);
            fragmentList.add(backOneFragment);
        } else if (num.equals("2")) {
            number = 2;
            titles = new ArrayList<>();
            titles.add("第1次");
            titles.add("第2次");
            fragmentList = new ArrayList<>();
            backOneFragment.getId(id);
            backTwoFragment.getId(id);
            fragmentList.add(backOneFragment);
            fragmentList.add(backTwoFragment);
        } else if (num.equals("3")) {
            number = 3;
            titles = new ArrayList<>();
            titles.add("第1次");
            titles.add("第2次");
            titles.add("第3次");
            fragmentList = new ArrayList<>();
            backOneFragment.getId(id);
            backTwoFragment.getId(id);
            backThreeFragment.getId(id);
            fragmentList.add(backOneFragment);
            fragmentList.add(backTwoFragment);
            fragmentList.add(backThreeFragment);
        }
        AcceptanceMaterialsFGAdapter pagerAdapter = new AcceptanceMaterialsFGAdapter(getSupportFragmentManager());
        pagerAdapter.setmList(fragmentList, titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(number);
    }

    //初始化Indicator
    private void initMagicIndicator() {
        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(titles.get(index));
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setNormalColor(0xff232323);
                simplePagerTitleView.setSelectedColor(0xff4275E8);
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#4275E8"));
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }
}
