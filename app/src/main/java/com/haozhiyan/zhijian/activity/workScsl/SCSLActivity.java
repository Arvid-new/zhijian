package com.haozhiyan.zhijian.activity.workScsl;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.MyListAdapter;
import com.haozhiyan.zhijian.adapter.SCSLLocalInitAdapter;
import com.haozhiyan.zhijian.bean.InspectionBean;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.scsl.SCSLBdBean;
import com.haozhiyan.zhijian.bean.scsl.SCSLInitBean;
import com.haozhiyan.zhijian.bean.scsl.SCSLTroubleBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.haozhiyan.zhijian.utils.UiUtils.getContext;

public class SCSLActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_right)
    TextView tv_right;
    @ViewInject(R.id.tv_bdName)
    TextView tv_bdName;
    @ViewInject(R.id.tv_local)
    TextView tv_local;
    @ViewInject(R.id.tv_desc_title)
    TextView tv_desc_title;
    @ViewInject(R.id.tv_desc_content)
    TextView tv_desc_content;
    @ViewInject(R.id.tv_shiGongPercent)
    TextView tvShiGongPercent;
    @ViewInject(R.id.tv_jianLiPercent)
    TextView tvJianLiPercent;
    @ViewInject(R.id.tv_jianShePercent)
    TextView tvJianShePercent;
    @ViewInject(R.id.iv01)
    ImageView iv01;
    @ViewInject(R.id.iv02)
    ImageView iv02;
    @ViewInject(R.id.sc_sl_list)
    RecyclerView scSlList;
    @ViewInject(R.id.ll_bd)
    LinearLayout ll_bd;
    @ViewInject(R.id.ll_bd_desc)
    LinearLayout ll_bd_desc;
    @ViewInject(R.id.ll_list_window)
    RelativeLayout listWindow;
    @ViewInject(R.id.ll_twoList)
    LinearLayout llTwoList;
    @ViewInject(R.id.ll_threeList)
    LinearLayout llThreeList;
    @ViewInject(R.id.lv01)
    ListView lv01;
    @ViewInject(R.id.lv001)
    ListView lv001;
    @ViewInject(R.id.lv02)
    ListView lv02;
    @ViewInject(R.id.lv002)
    ListView lv002;
    @ViewInject(R.id.lv03)
    ListView lv03;
    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewInject(R.id.ll_SG)
    LinearLayout ll_SG;
    @ViewInject(R.id.ll_JL)
    LinearLayout ll_JL;
    @ViewInject(R.id.ll_JS)
    LinearLayout ll_JS;
    @ViewInject(R.id.tv_noUnit)
    TextView tv_noUnit;
    private MyListAdapter adapter1, adapter2, adapter3, adapter4, adapter5;
    //private List<ItemBean> data1 = new ArrayList<>();
    private List<ItemBean> data2 = new ArrayList<>();
    private int mHiddenViewBackHeight;
    //private SCSLLocalAdapter scslLocalAdapter;
    private SCSLLocalInitAdapter scslLocalInitAdapter;
    private List<SCSLBdBean.ListBean> listBeans = new ArrayList<>();
    private List<SCSLBdBean.ListBean.ScopeChildBean> scopeChildBeans = new ArrayList<>();
    private List<SCSLBdBean.ListBean.ScopeChildBean.UintChildBean> unitList = new ArrayList<>();
    private List<SCSLTroubleBean.ListBean> troubleList;
    private List<SCSLTroubleBean.ListBean.ChildBean> troubleChildList;
    private String sectionName = "";
    private boolean isRefresh = false;
    private String towerId = "6", unitId = "12", scslType = "";
    private String unitName = "", towerName = "";
    private String inspectionName = "", inspectionSunName = "", inspectionSunId = "", sectionId = "16", inspectionParentId = "";
    private Bundle formBundle;
    private int pos1, pos2, pos3, pos4, pos5;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_scsl;
    }

    @Override
    protected void initView() {
        tv_title.setText("实测实量");
        //tv_right.setVisibility(View.VISIBLE);
        //tv_right.setText("汇总");
        initWidget();
    }

    private void initWidget() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (TextUtils.equals(userInfo.getUserType(), "0")) {
            ll_SG.setVisibility(View.VISIBLE);
            ll_JL.setVisibility(View.VISIBLE);
            ll_JS.setVisibility(View.VISIBLE);
        } else if (TextUtils.equals(userInfo.getUserType(), "1")) {
            ll_SG.setVisibility(View.VISIBLE);
            ll_JL.setVisibility(View.GONE);
            ll_JS.setVisibility(View.GONE);
        } else if (TextUtils.equals(userInfo.getUserType(), "2")) {
            ll_SG.setVisibility(View.VISIBLE);
            ll_JL.setVisibility(View.VISIBLE);
            ll_JS.setVisibility(View.GONE);
        } else if (TextUtils.equals(userInfo.getUserType(), "3")) {
            ll_SG.setVisibility(View.VISIBLE);
            ll_JL.setVisibility(View.VISIBLE);
            ll_JS.setVisibility(View.VISIBLE);
        }
        mHiddenViewBackHeight = SystemUtils.getPhoneScreenHight(this);
        scSlList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        scSlList.setFocusableInTouchMode(false);
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                getChangeTfRoom(scslType);
            }
        });
        initAdapter();
        lv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter1.setSelected(position);
                if (listEmpty(listBeans)) {
                    sectionId = listBeans.get(position).sectionId + "";
                    sectionName = listBeans.get(position).sectionName;
                    tv_bdName.setText(sectionName);
                    if (listEmpty(listBeans.get(position).scopeChild)) {
                        scopeChildBeans = listBeans.get(position).scopeChild;
                        List<ItemBean> towerList = new ArrayList<>();
                        List<ItemBean> tempUnitList = new ArrayList<>();
                        List<SCSLBdBean.ListBean.ScopeChildBean> towerBeans = listBeans.get(position).scopeChild;
                        //设置楼栋
                        for (int i = 0; i < towerBeans.size(); i++) {
                            towerList.add(new ItemBean(towerBeans.get(i).tower, "", towerBeans.get(i).towerId));
                            if (i == 0) {
                                //设置单元
                                if (listEmpty(towerBeans.get(0).uintChild)) {
                                    unitList = towerBeans.get(0).uintChild;
                                    List<SCSLBdBean.ListBean.ScopeChildBean.UintChildBean> uintChildBeans = towerBeans.get(0).uintChild;
                                    for (int k = 0; k < uintChildBeans.size(); k++) {
                                        tempUnitList.add(new ItemBean(uintChildBeans.get(k).unit, "", uintChildBeans.get(k).unitId));
                                    }

                                }
                            }
                        }
                        if (towerList.size() > 0) {
                            adapter2 = new MyListAdapter(act, towerList, R.layout.scsl_list_item);
                            lv02.setAdapter(adapter2);
                            adapter2.setSelected(0);
                        }
                        if (tempUnitList.size() > 0) {
                            tv_noUnit.setVisibility(View.GONE);
                            lv03.setVisibility(View.VISIBLE);
                            adapter3 = new MyListAdapter(act, tempUnitList, R.layout.scsl_list_item);
                            lv03.setAdapter(adapter3);
                            adapter3.setSelected(0);
                        } else {
                            tv_noUnit.setVisibility(View.VISIBLE);
                            lv03.setVisibility(View.GONE);
                        }
                    }
                    getScslTrouble(sectionId + "");
                    //getScslTrouble("32");
                }
            }
        });
        lv02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.setSelected(position);
                if (listEmpty(scopeChildBeans)) {
                    // bdName2 = scopeChildBeans.get(position).tower;
                    towerId = scopeChildBeans.get(position).towerId + "";
                    towerName = scopeChildBeans.get(position).tower;
                    scslLocalInitAdapter.setTower(scopeChildBeans.get(position).towerId + "", scopeChildBeans.get(position) + towerName);
                    if (listEmpty(scopeChildBeans.get(position).uintChild)) {
                        unitList = scopeChildBeans.get(position).uintChild;
                        List<ItemBean> unitList = new ArrayList<>();
                        for (int i = 0; i < scopeChildBeans.get(position).uintChild.size(); i++) {
                            unitList.add(new ItemBean(scopeChildBeans.get(position).uintChild.get(i).unit, "", scopeChildBeans.get(position).uintChild.get(i).unitId));
                        }
                        adapter3 = new MyListAdapter(act, unitList, R.layout.scsl_list_item);
                        lv03.setAdapter(adapter3);
                        adapter3.setSelected(0);
                        //adapter3.notifyDataSetChanged();
                    }
                }
            }
        });
        lv03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter3.setSelected(position);
                if (listEmpty(unitList)) {
                    // bdName3 = unitList.get(position).unit;
                    unitId = unitList.get(position).unitId + "";
                    unitName = unitList.get(position).unit;
                    tv_local.setText(towerName + unitName);
                    scslLocalInitAdapter.setUnit(unitList.get(position).unitId + "", unitList.get(position).unit);
                } else {
                    tv_local.setText(towerName);
                }
                AnimationUtil.getInstance().animateClose(listWindow);
                AnimationUtil.getInstance().animationIvClose(iv01);
                getChangeTfRoom(scslType);
            }
        });

        lv001.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter4.setSelected(position);
                if (listEmpty(troubleList)) {
                    //tv_desc_title.setText(troubleList.get(position).inspectionName);
                    // inspectionName = troubleList.get(position).inspectionName;
                    //scslLocalInitAdapter.setInspectionName(troubleList.get(position).inspectionId + "", inspectionName);
                    //inspectionParentId = troubleList.get(position).inspectionId + "";
                    if (listEmpty(troubleList.get(position).child)) {
                        troubleChildList = troubleList.get(position).child;
                        List<ItemBean> childList = new ArrayList<>();
                        for (int i = 0; i < troubleList.get(position).child.size(); i++) {
                            LogUtils.print("partsDivision===" + troubleList.get(position).child.get(i).partsDivision);
                            childList.add(new ItemBean(troubleList.get(position).child.get(i).inspectionName, troubleList.get(position).child.get(i).partsDivision, troubleList.get(position).child.get(i).inspectionId));
                        }
                        adapter5 = new MyListAdapter(act, childList, R.layout.scsl_list_item);
                        lv002.setAdapter(adapter5);
                        adapter5.setSelected(0);
                    }
                }
            }
        });
        lv002.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter5.setSelected(position);
                if (listEmpty(troubleChildList)) {
                    tv_desc_content.setText(troubleChildList.get(position).inspectionName);
                    inspectionSunName = troubleChildList.get(position).inspectionName;
                    inspectionSunId = String.valueOf(troubleChildList.get(position).inspectionId);
                    scslType = troubleChildList.get(position).partsDivision;
                    scslLocalInitAdapter.setInspectionId(troubleChildList.get(position).inspectionId + "", troubleChildList.get(position).inspectionName);

                    tv_desc_title.setText(troubleList.get(adapter4.getSelected()).inspectionName);
                    inspectionName = troubleList.get(adapter4.getSelected()).inspectionName;
                    scslLocalInitAdapter.setInspectionName(troubleList.get(adapter4.getSelected()).inspectionId + "", inspectionName);
                    inspectionParentId = troubleList.get(adapter4.getSelected()).inspectionId + "";
                    getChangeTfRoom(scslType);
                }
                AnimationUtil.getInstance().animateClose(listWindow);
                AnimationUtil.getInstance().animationIvClose(iv01);
            }
        });
    }

    private void initAdapter() {
        scslLocalInitAdapter = new SCSLLocalInitAdapter(this, null, userInfo.getUserType());
        scSlList.setAdapter(scslLocalInitAdapter);
        scslLocalInitAdapter.setEmptyView(emptyView);
    }

    @Override
    protected void initData(boolean isNetWork) {
        getScslBd();
        formBundle = getIntent().getBundleExtra("formBundle");
        if (formBundle != null) {
            sectionId = formBundle.getString("sectionId");
            sectionName = formBundle.getString("sectionName");
            towerId = formBundle.getString("towerId");
            towerName = formBundle.getString("towerName");
            inspectionSunName = formBundle.getString("inspectionSunName");
            inspectionSunId = formBundle.getString("inspectionSunId");
        } else {
            getInitTfRoom();
        }
    }

    @OnClick({R.id.rl_back, R.id.ll_bd, R.id.ll_bd_desc, R.id.tv_right, R.id.ll_list_window})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.ll_bd:
                llTwoList.setVisibility(View.GONE);
                llThreeList.setVisibility(View.VISIBLE);
                lv03.setVisibility(View.VISIBLE);
                if (listWindow.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(listWindow, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv01);
                } else {
                    AnimationUtil.getInstance().animateClose(listWindow);
                    AnimationUtil.getInstance().animationIvClose(iv01);
                }
                break;
            case R.id.ll_bd_desc:
                llTwoList.setVisibility(View.VISIBLE);
                llThreeList.setVisibility(View.GONE);
                if (listWindow.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(listWindow, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv02);
                } else {
                    AnimationUtil.getInstance().animateClose(listWindow);
                    AnimationUtil.getInstance().animationIvClose(iv02);
                }
                break;
//            case R.id.tv_right:
//                Bundle bundle = new Bundle();
//                bundle.putString("local", bdName2 + bdName3);
//                jumpToActivity(SCSLCollectActivity.class, bundle);
//                break;
            case R.id.ll_list_window:
                AnimationUtil.getInstance().animateClose(listWindow);
                AnimationUtil.getInstance().animationIvClose(iv01);
                break;
            default:
                break;
        }
    }

    //旧字段 地块id-pkId  新字段 地块id-dikuaiId
    private void getScslBd() {
        HttpRequest.get(getContext()).url(ServerInterface.selectSection)
                .params("dikuaiId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            SCSLBdBean bean = new Gson().fromJson(result.toString(), SCSLBdBean.class);
                            if (listEmpty(bean.list)) {
                                listBeans = bean.list;
                                List<ItemBean> sectionList = new ArrayList<>();
                                List<ItemBean> towerList = new ArrayList<>();
                                List<ItemBean> tempUnitList = new ArrayList<>();
                                if (formBundle != null) {//从报表跳转过来
                                    tv_bdName.setText(sectionName);
                                    tv_local.setText(towerName + "1单元");
                                    getScslTrouble(sectionId);
                                    scslLocalInitAdapter.setSection(sectionId, sectionName);
                                    scslLocalInitAdapter.setTower(towerId,towerName);
                                    //第一层列表
                                    List<SCSLBdBean.ListBean> tempSectionList = bean.list;
                                    for (int i = 0; i < tempSectionList.size(); i++) {
                                        sectionList.add(new ItemBean(tempSectionList.get(i).sectionName, "", tempSectionList.get(i).sectionId));
                                        if (TextUtils.equals(sectionId, tempSectionList.get(i).sectionId + "")) {
                                            pos1 = i;
                                            scopeChildBeans = tempSectionList.get(i).scopeChild;
                                            //第二层列表
                                            List<SCSLBdBean.ListBean.ScopeChildBean> tempTowerList = scopeChildBeans;
                                            for (int j = 0; j < tempTowerList.size(); j++) {
                                                towerList.add(new ItemBean(tempTowerList.get(j).tower, "", tempTowerList.get(j).towerId));
                                                if (TextUtils.equals(towerId, tempTowerList.get(j).towerId + "")) {
                                                    pos2 = j;
                                                    pos3 = 0;
                                                    //第三层列表
                                                    unitList = tempTowerList.get(j).uintChild;
                                                    unitName = unitList.get(0).unit;
                                                    unitId = unitList.get(0).unitId + "";
                                                    scslLocalInitAdapter.setUnit(unitList.get(0).unitId + "",unitList.get(0).unit);
                                                    for (int k = 0; k < unitList.size(); k++) {
                                                        tempUnitList.add(new ItemBean(unitList.get(k).unit, "", unitList.get(k).unitId));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {//从工作台跳转过来,设置默认显示
                                    tv_bdName.setText(bean.list.get(0).sectionName);
                                    if (listEmpty(bean.list.get(0).scopeChild) && listEmpty(bean.list.get(0).scopeChild.get(0).uintChild)) {
                                        tv_local.setText(bean.list.get(0).scopeChild.get(0).tower + bean.list.get(0).scopeChild.get(0).uintChild.get(0).unit);
                                    }
                                    getScslTrouble(bean.list.get(0).sectionId + "");
                                    scslLocalInitAdapter.setSection(bean.list.get(0).sectionId + "", bean.list.get(0).sectionName);

                                    //设置第一层列表
                                    for (int i = 0; i < bean.list.size(); i++) {
                                        sectionList.add(new ItemBean(bean.list.get(i).sectionName, "", bean.list.get(i).sectionId));
                                        //设置第二层列表
                                        if (i == 0) {
                                            if (listEmpty(bean.list.get(0).scopeChild)) {
                                                scopeChildBeans = bean.list.get(0).scopeChild;
                                                towerId = scopeChildBeans.get(0).towerId + "";
                                                towerName = scopeChildBeans.get(0).tower;
                                                for (int j = 0; j < scopeChildBeans.size(); j++) {
                                                    towerList.add(new ItemBean(scopeChildBeans.get(j).tower, "", scopeChildBeans.get(j).towerId));
                                                    //设置第三层列表
                                                    if (j == 0) {
                                                        if (listEmpty(bean.list.get(0).scopeChild.get(0).uintChild)) {
                                                            unitList = bean.list.get(0).scopeChild.get(0).uintChild;
                                                            unitName = unitList.get(0).unit;
                                                            unitId = unitList.get(0).unitId + "";
                                                            for (int k = 0; k < unitList.size(); k++) {
                                                                tempUnitList.add(new ItemBean(unitList.get(k).unit, "", unitList.get(k).unitId));
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                adapter1 = new MyListAdapter(act, sectionList, R.layout.scsl_list_item);
                                lv01.setAdapter(adapter1);
                                adapter1.setSelected(pos1);
                                adapter2 = new MyListAdapter(act, towerList, R.layout.scsl_list_item);
                                lv02.setAdapter(adapter2);
                                adapter2.setSelected(pos2);
                                adapter3 = new MyListAdapter(act, tempUnitList, R.layout.scsl_list_item);
                                lv03.setAdapter(adapter3);
                                adapter3.setSelected(pos3);

                            } else {
                                ToastUtils.myToast(act, "暂无标段数据");
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    /**
     * 检查项列表
     */
    private void getScslTrouble(String sectionId) {
        HttpRequest.get(getContext()).url(ServerInterface.selectSCSL)
                .params("sectionId", sectionId)
                .params("type", "SCSL")
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("doGet检查项==", result.toString());
                        SCSLTroubleBean bean = new Gson().fromJson(result.toString(), SCSLTroubleBean.class);
                        if (listEmpty(bean.list)) {
                            troubleList = bean.list;
                            List<ItemBean> inspectionList = new ArrayList<>();
                            List<ItemBean> inspectionSunList = new ArrayList<>();
                            if (formBundle != null) {//从报表跳转过来
                                //设置第一层列表
                                for (int i = 0; i < troubleList.size(); i++) {
                                    inspectionList.add(new ItemBean(troubleList.get(i).inspectionName, "", troubleList.get(i).inspectionId));
                                    List<SCSLTroubleBean.ListBean.ChildBean> tempChilds = troubleList.get(i).child;
                                    for (int j = 0; j < tempChilds.size(); j++) {
                                        if (TextUtils.equals(inspectionSunId, tempChilds.get(j).inspectionId + "")) {
                                            troubleChildList = tempChilds;
                                            pos4 = i;
                                            pos5 = j;
                                            tv_desc_title.setText(troubleList.get(i).inspectionName);
                                            inspectionParentId = troubleList.get(i).inspectionId + "";
                                            inspectionName = troubleList.get(i).inspectionName;
                                            scslLocalInitAdapter.setInspectionName(troubleList.get(i).inspectionId + "", troubleList.get(i).inspectionName);
                                            tv_desc_content.setText(tempChilds.get(j).inspectionName);
                                            scslLocalInitAdapter.setInspectionId(tempChilds.get(j).inspectionId + "", tempChilds.get(j).inspectionName);
                                            scslType = troubleChildList.get(i).partsDivision;
                                            for (int k = 0; k < tempChilds.size(); k++) {
                                                inspectionSunList.add(new ItemBean(tempChilds.get(k).inspectionName, tempChilds.get(k).partsDivision, tempChilds.get(k).inspectionId));
                                            }
                                            getChangeTfRoom(scslType);
                                        }
                                    }
                                }

                            } else {
                                tv_desc_title.setText(bean.list.get(0).inspectionName);
                                inspectionParentId = bean.list.get(0).inspectionId + "";
                                scslLocalInitAdapter.setInspectionName(bean.list.get(0).inspectionId + "", bean.list.get(0).inspectionName);
                                if (listEmpty(bean.list.get(0).child)) {
                                    tv_desc_content.setText(bean.list.get(0).child.get(0).inspectionName);
                                    scslLocalInitAdapter.setInspectionId(bean.list.get(0).child.get(0).inspectionId + "", bean.list.get(0).child.get(0).inspectionName);
                                }

                                //设置第一层列表
                                for (int i = 0; i < bean.list.size(); i++) {
                                    inspectionList.add(new ItemBean(bean.list.get(i).inspectionName, "", bean.list.get(i).inspectionId));
                                    if (listEmpty(bean.list.get(0).child)) {
                                        troubleChildList = bean.list.get(0).child;
                                        //设置第二层列表
                                        if (i == 0) {
                                            for (int j = 0; j < bean.list.get(0).child.size(); j++) {
                                                inspectionSunList.add(new ItemBean(bean.list.get(0).child.get(j).inspectionName, bean.list.get(0).child.get(j).partsDivision, bean.list.get(0).child.get(j).inspectionId));
                                            }
                                        }
                                    }
                                }
                            }

                            try {
                                adapter4 = new MyListAdapter(act, inspectionList, R.layout.scsl_list_item);
                                lv001.setAdapter(adapter4);
                                adapter4.setSelected(pos4);
                                adapter5 = new MyListAdapter(act, inspectionSunList, R.layout.scsl_list_item);
                                lv002.setAdapter(adapter5);
                                adapter5.setSelected(pos5);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.myToast(act, "暂无检查问题数据");
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    private void getInitTfRoom() {
        HttpRequest.get(getContext()).url(ServerInterface.selectSCSLType)
                .params("dikuaiId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("getInitTfRoom-resultJson==", result.toString());
                        SCSLInitBean bean = new Gson().fromJson(result.toString(), SCSLInitBean.class);
                        if (bean.list != null) {
                            scslLocalInitAdapter.setInspectionId(bean.list.inspectionSunId, bean.list.inspectionSunName);
                            scslLocalInitAdapter.setSection(bean.list.sectionId, bean.list.sectionName);
                            scslLocalInitAdapter.setTower(bean.list.towerId + "", bean.list.towerName);
                            scslLocalInitAdapter.setUnit(bean.list.unitId + "", bean.list.unitName);
                            unitId = bean.list.unitId + "";
                            towerId = bean.list.towerId + "";
                            unitName = bean.list.unitName;
                            towerName = bean.list.towerName;
                            sectionName = bean.list.sectionName;
                            sectionId = bean.list.sectionId + "";
                            inspectionName = bean.list.inspectionName;
                            inspectionSunName = bean.list.inspectionSunName;
                            inspectionSunId = bean.list.inspectionSunId;
                            scslType = bean.list.partsDivision;
                            if (listEmpty(bean.list.messages)) {
                                scslLocalInitAdapter.setNewData(bean.list.messages);
                            } else {
                                scslLocalInitAdapter.setEmptyView(emptyView);
                            }
                        }
                        if (isRefresh) {
                            swipeRefreshLayout.setRefreshing(false);
                            isRefresh = false;
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        if (isRefresh) {
                            swipeRefreshLayout.setRefreshing(false);
                            isRefresh = false;
                        }
                    }
                });
    }

    private void getChangeTfRoom(String scslType) {
        showLoadView("加载中...");
        //LogUtils.i("getChangeTfRoom--upload==", ParameterMap.put(Constant.projectId + "", towerId, unitId, inspectionName, inspectionSunName, scslType).toString());
        HttpRequest.get(getContext()).url(ServerInterface.selectSCSLType)
                //.params("dikuaiId", Constant.projectId)
                .params("towerId", towerId)
                .params("unitId", unitId)
                .params("inspectionName", inspectionName)
                .params("inspectionSunName", inspectionSunName)
                .params("inspectionSunId", inspectionSunId)
                .params("scslType", scslType)
                .params("sectionId", sectionId)
                .params("sectionName", sectionName)
                .params("towerName", towerName)
                .params("unitName", unitName)
                .params("inspFuId", inspectionParentId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        SCSLInitBean bean = new Gson().fromJson(result.toString(), SCSLInitBean.class);
                        if (isRefresh) {
                            swipeRefreshLayout.setRefreshing(false);
                            isRefresh = false;
                        }
                        LogUtils.i("getChangeTfRoomJson==", result.toString());
                        if (bean.list != null) {
                            scslLocalInitAdapter.setNewData(bean.list.messages);
                        } else {
                            scslLocalInitAdapter.setEmptyView(emptyView);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                        if (isRefresh) {
                            swipeRefreshLayout.setRefreshing(false);
                            isRefresh = false;
                        }
                    }
                });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (!StringUtils.isEmpty(event) && "scslsuccess".equals(event)) {
            isRefresh = true;
            //getChangeTfRoom(scslType);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getChangeTfRoom(scslType);
                }
            },1000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
