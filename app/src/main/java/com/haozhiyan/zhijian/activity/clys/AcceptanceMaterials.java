package com.haozhiyan.zhijian.activity.clys;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.adapter.AcceptanceMaterialsFGAdapter;
import com.haozhiyan.zhijian.bean.MaterialsTaskBean;
import com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child.AM_EnterAreaFragment;
import com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child.AM_ExitAreaFragment;
import com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child.AM_InspectFragment;
import com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child.AM_ReportFragment;
import com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child.AM_TaskFragment;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.view.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 材料验收 详情页
 */
public class AcceptanceMaterials extends BaseActivity2 {
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private String type;//待办 已办状态
    private String id;//
    private String title;//
    public static String CLYSState;
    private String checkResult;//
    private List<String> titles;
    private List<Fragment> fragmentList;
    private MaterialsTaskBean taskBean;
    private boolean isdaibanin;
    private AcceptanceMaterialsFGAdapter pagerAdapter;
    public static String ENTRANCE;

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            type = getIntent().getExtras().getString("type", "");
            id = getIntent().getExtras().getString("id", "");
            checkResult = getIntent().getExtras().getString("checkResult", "");
            title = getIntent().getExtras().getString("title", "验收详情");
            isdaibanin = getIntent().getExtras().getBoolean("isdaibanin", false);
            ENTRANCE = getIntent().getExtras().getString("entrance", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_acceptance_materials;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText(title);
        setAndroidNativeLightStatusBar(true);

        ButterKnife.bind(this);
        setTitleRightmenu();
        viewPager = findViewById(R.id.viewPager);
        titles = new ArrayList<>();
        fragmentList = new ArrayList<>();
    }

    @Override
    protected void initData() {
        infoClysTask();
    }

    @Override
    public void onMessageEvent(Object event) {
        if (event instanceof String) {
            if (event.equals("clysTaskDelete")) {
                finish();
            } else if (event.equals("clysTaskcheckResult")) {
                checkResult = "0";
            } else if (event.equals("taskStateChanged")) {
                if (isdaibanin) {
                    EventBus.getDefault().post("clysTaskStateChanged");
                }
                infoClysTask();
            }
        }
    }

    private void setTitleRightmenu() {
        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dp40px, dp40px);
        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(
                dp40px, dp40px);
        ImageView close = new ImageView(getContext());
        close.setImageResource(R.mipmap.close_icon);
        close.setLayoutParams(layoutParams);
        close.setPadding(dp10px, 0, dp10px, 0);

        ImageView close3 = new ImageView(getContext());
        close3.setImageResource(R.mipmap.close_icon);
        close3.setLayoutParams(layoutParams3);
        close3.setPadding(dp10px, 0, dp10px, 0);
        close3.setVisibility(View.INVISIBLE);

        getBarLeftView().addView(close, 1);
//        getBarRightView().addView(close3,0);
        getTitleTextView().setTextSize(15);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
    }

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

    /**
     * 材料验收-任务详情
     */
    private void infoClysTask() {
        HttpRequest.get(getContext())
                .url(ServerInterface.infoClysTask)
                .params("id", id)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            int CurrentItem = 0;
                            if (object.optInt("code") == 0) {
                                fragmentList.clear();
                                titles.clear();
                                taskBean = new Gson().fromJson(result.toString(), MaterialsTaskBean.class);
                                CLYSState = taskBean.clysTask.state;
                                titles.add("任务");
                                AM_TaskFragment taskFragment = new AM_TaskFragment();
                                taskFragment.id = id;
                                SPUtil.get(getContext()).save(taskFragment.getClass().getSimpleName(), result.toString());
                                fragmentList.add(taskFragment);
                                AM_EnterAreaFragment areaFragment = new AM_EnterAreaFragment();
                                //状态(1待申请进场，2待验收，3已验收，4待送检，5待上传报告，6待复验，7待退场，8已退场，9送检合格，10复验合格)
                                switch (CLYSState) {
                                    case "1":
                                    case "2":
                                    case "3":
                                        titles.add("进场");
                                        fragmentList.add(areaFragment);
                                        areaFragment.id = id;
                                        areaFragment.sectionId = taskBean.clysTask.sectionId;
                                        areaFragment.receiveUnitName = taskBean.clysTask.receiveUnitName;
                                        areaFragment.receiveName = taskBean.clysTask.receiveName;
                                        areaFragment.receive = taskBean.clysTask.receive;
                                        areaFragment.atype = type;
                                        if (CurrentItem == 0) {
                                            CurrentItem = 1;
                                        }
                                        break;
                                    case "4":
                                        if (CurrentItem == 0) {
                                            CurrentItem = 2;
                                        }
                                    case "5":
                                        if (CurrentItem == 0) {
                                            CurrentItem = 3;
                                        }
                                    case "6":
                                        if (CurrentItem == 0) {
                                            CurrentItem = 2;
                                        }
                                    case "9":
                                    case "10":
                                        if (CurrentItem == 0) {
                                            CurrentItem = 3;
                                        }
                                        titles.add("进场");
                                        fragmentList.add(areaFragment);
                                        areaFragment.id = id;
                                        areaFragment.sectionId = taskBean.clysTask.sectionId;
                                        areaFragment.receiveUnitName = taskBean.clysTask.receiveUnitName;
                                        areaFragment.receiveName = taskBean.clysTask.receiveName;
                                        areaFragment.receive = taskBean.clysTask.receive;
                                        titles.add("送检");
                                        fragmentList.add(AM_InspectFragment.build(id));
                                        titles.add("报告");
                                        fragmentList.add(AM_ReportFragment.build(id));
                                        break;
                                    case "7":
                                    case "8":
                                        //checkResult：为0（任务、进场、退场），为1（全部）
                                        if (StringUtils.isEmpty(checkResult) || checkResult.equals("0")) {
                                            titles.add("进场");
                                            fragmentList.add(areaFragment);
                                            areaFragment.id = id;
                                            areaFragment.sectionId = taskBean.clysTask.sectionId;
                                            areaFragment.receiveUnitName = taskBean.clysTask.receiveUnitName;
                                            areaFragment.receiveName = taskBean.clysTask.receiveName;
                                            areaFragment.receive = taskBean.clysTask.receive;
                                            titles.add("退场");
                                            fragmentList.add(AM_ExitAreaFragment.build(id,
                                                    taskBean.clysTask.supervisorName, taskBean.clysTask.ccName));
                                            if (CurrentItem == 0) {
                                                CurrentItem = 2;
                                            }
                                        } else {
                                            titles.add("进场");
                                            fragmentList.add(areaFragment);
                                            areaFragment.id = id;
                                            areaFragment.sectionId = taskBean.clysTask.sectionId;
                                            areaFragment.receiveUnitName = taskBean.clysTask.receiveUnitName;
                                            areaFragment.receiveName = taskBean.clysTask.receiveName;
                                            areaFragment.receive = taskBean.clysTask.receive;
                                            titles.add("送检");
                                            fragmentList.add(AM_InspectFragment.build(id));
                                            titles.add("报告");
                                            fragmentList.add(AM_ReportFragment.build(id));
                                            titles.add("退场");
                                            fragmentList.add(AM_ExitAreaFragment.build(id,
                                                    taskBean.clysTask.supervisorName, taskBean.clysTask.ccName));
                                            if (CurrentItem == 0) {
                                                CurrentItem = 4;
                                            }
                                        }
                                        break;
                                }
                                pagerAdapter =
                                        new AcceptanceMaterialsFGAdapter(getSupportFragmentManager());
                                pagerAdapter.setmList(fragmentList, titles);
                                viewPager.setAdapter(pagerAdapter);
                                viewPager.setOffscreenPageLimit(5);
                                initMagicIndicator();
                                try {
                                    viewPager.setCurrentItem(CurrentItem);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SPUtil.get(getContext()).save("clysTaskInspectorId=", "");
        SPUtil.get(getContext()).save("clysTaskSupervisorId=", "");
    }
}
