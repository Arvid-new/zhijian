package com.haozhiyan.zhijian.activity.gxyj;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.CheckTip;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.adapter.AcceptanceMaterialsFGAdapter;
import com.haozhiyan.zhijian.bean.ProcessOverUseIdBean;
import com.haozhiyan.zhijian.fragment.gxyjfragment.GXMsgFragmentNew;
import com.haozhiyan.zhijian.fragment.gxyjfragment.ZhengGaiProblemFragment;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 工序移交
 */
public class ProcessOver extends BaseActivity2 {

    private String partsDivision;//分户  分单元整层  整栋
    private String identifying;//移交状态
    private String inspectionName;//
    private String inspectionSunName;//
    private String biaoduan;//标段
    private String tower;//
    private String selectroom;//
    private String selectIds;//批量验收选中的id
    private String sectionId;//
    private String towerId;//
    private String unitId;//
    private String floorId;//
    private String floorName;//
    private String roomId;//
    private String inspectionId;//
    private String secInsId;//标段id
    private String dbId;//待办参数id
    private String id;//主键id
    private String queryType;//主键id
    private String towerName;//楼栋名称
    private String unitName;//单元名称
    private String detailsName;//检查项和子项
    private String isNeedBuild;//是否需建设单位验收：0否，1是
    private boolean isDaiBan;//是否从待办 跳入
    private ViewPager viewPager;
    public List<String> titles;
    private List<Fragment> fragmentList;
    private TabLayout tabLayout;
    private AcceptanceMaterialsFGAdapter pagerAdapter;
    private ZhengGaiProblemFragment zhengGaiProblemFragment;
    private GXMsgFragmentNew detailFG;
    private ProcessOverUseIdBean useIdBean;

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            partsDivision = getIntent().getExtras().getString("partsDivision", "");
            inspectionName = getIntent().getExtras().getString("inspectionName", "");
            inspectionSunName = getIntent().getExtras().getString("inspectionSunName", "");
            biaoduan = getIntent().getExtras().getString("biaoduan", "");
            tower = getIntent().getExtras().getString("tower", "");
            selectroom = getIntent().getExtras().getString("selectroom", "");
            selectIds = getIntent().getExtras().getString("selectIds", "");
            sectionId = getIntent().getExtras().getString("sectionId", "");
            towerId = getIntent().getExtras().getString("towerId", "");
            unitId = getIntent().getExtras().getString("unitId", "");
            floorId = getIntent().getExtras().getString("floorId", "");
            floorName = getIntent().getExtras().getString("floorName", "");
            roomId = getIntent().getExtras().getString("roomId", "");
            inspectionId = getIntent().getExtras().getString("inspectionId", "");
            identifying = getIntent().getExtras().getString("identifying", "");
            secInsId = getIntent().getExtras().getString("secInsId", "");
            dbId = getIntent().getExtras().getString("dbId", "");
            id = getIntent().getExtras().getString("id", "");
            towerName = getIntent().getExtras().getString("towerName", "");
            unitName = getIntent().getExtras().getString("unitName", "");
            queryType = getIntent().getExtras().getString("entrance", "");
            detailsName = getIntent().getExtras().getString("detailsName", "");
            isNeedBuild = getIntent().getExtras().getString("isNeedBuild", "");
            isDaiBan = getIntent().getExtras().getBoolean("isDaiBan", false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_process_over;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("工序详情");
        setAndroidNativeLightStatusBar(true);
        setTitleRightmenu();
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        titles = new ArrayList<>();
        titles.add("工序信息");
        titles.add("整改问题");
        fragmentList = new ArrayList<>();
        detailFG = new GXMsgFragmentNew();
        zhengGaiProblemFragment = new ZhengGaiProblemFragment();
        useIdBean = new ProcessOverUseIdBean();
        detailFG.useIdBean = useIdBean;
        zhengGaiProblemFragment.useIdBean = useIdBean;


        useIdBean.partsDivision = partsDivision;
        useIdBean.inspectionId = inspectionId;
        useIdBean.inspectionName = inspectionName;
        useIdBean.sectionName = biaoduan;
        useIdBean.tower = tower;
        useIdBean.floorId = floorId;
        useIdBean.floorName = floorName;
        useIdBean.roomId = roomId;
        useIdBean.selectroom = selectroom;
        useIdBean.selectIds = selectIds;
        useIdBean.sectionId = sectionId;
        useIdBean.towerId = towerId;
        useIdBean.unitId = unitId;
        useIdBean.identifying = identifying;
        useIdBean.secInsId = secInsId;
        useIdBean.dbId = dbId;
        useIdBean.towerName = towerName;
        useIdBean.unitName = unitName;
        useIdBean.queryType = queryType;
        useIdBean.isDaiBan = isDaiBan;
        useIdBean.keyId = id;
        useIdBean.detailsName = detailsName;
        useIdBean.inspectionSunName = inspectionSunName;
        useIdBean.isNeedBuild = isNeedBuild;

        fragmentList.add(detailFG);
        fragmentList.add(zhengGaiProblemFragment);

        pagerAdapter = new AcceptanceMaterialsFGAdapter(getSupportFragmentManager());
        pagerAdapter.setmList(fragmentList, titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void refreshFragment(List<String> list) {
        pagerAdapter.refreshTitle(list);
    }

    @Override
    public void onMessageEvent(Object event) {
        if (event instanceof String) {
            if ("deleteGXYJTask".equals(event)) {
                finish();
            } else if ("GXYJTaskIDIsDouble".equals(event)) {
                finish();
            }
        }
    }

    @Override
    protected void initData() {

    }

    private void setTitleRightmenu() {
        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dp40px, dp40px);
        ImageView close = new ImageView(getContext());
        close.setImageResource(R.mipmap.close_icon);
        close.setLayoutParams(layoutParams);
        close.setPadding(dp10px, 0, dp10px, 0);
        ImageView checktip = new ImageView(getContext());
        checktip.setPadding(dp10px, 0, dp10px, 0);
        checktip.setLayoutParams(layoutParams);
        checktip.setImageResource(R.mipmap.msg_tip_icon);
        getBarLeftView().addView(close, 1);
        getBarRightView().addView(checktip);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
        checktip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTip.start(getContext(), inspectionId, inspectionName, inspectionSunName,"gxyj");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.NOTE_PROBLEM_RESULT_CODE) {
            if (tabLayout.getTabCount() > 1) {
                tabLayout.getTabAt(1).select();
                //zhengGaiProblemFragment.getAcahe(aCache);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            fragmentList=null;
            detailFG=null;
            zhengGaiProblemFragment=null;
            useIdBean=null;
            pagerAdapter=null;
            viewPager.setAdapter(pagerAdapter);
            viewPager=null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
