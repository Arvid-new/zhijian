package com.haozhiyan.zhijian.activity.gxyj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.adapter.GXYJBuildingsAdapter;
import com.haozhiyan.zhijian.adapter.GXYJPopAdapter;
import com.haozhiyan.zhijian.adapter.GXYJPopAdapter2;
import com.haozhiyan.zhijian.adapter.GXYJPopAdapter3;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.biaoduan.BiaoDuanBean;
import com.haozhiyan.zhijian.bean.biaoduan.InspectionOneBean;
import com.haozhiyan.zhijian.bean.biaoduan.InspectionTwoBean;
import com.haozhiyan.zhijian.bean.biaoduan.TowerBean;
import com.haozhiyan.zhijian.bean.biaoduan.UnitBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllFloorBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllTowerBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJFloorBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJRoomBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.myDao.BiaoDuanBeanDao;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.InspectionOneBeanDao;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.widget.CommonPopupWindow;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/26.
 * Describe:工序移交
 */
public class GXYJActivity extends BaseActivity2 implements View.OnClickListener {
    private CommonPopupWindow popupWindow;
    private Button commitBT;
    private TextView tv_biaoDuan;
    private TextView tv_tower;
    private TextView projectName;
    private TextView projectType;
    private RecyclerView rcv1;
    private RecyclerView rcv2;
    private RecyclerView rcv3;
    private RecyclerView buildRcv;
    private SwipeRefreshLayout swipeRefreshLayout;
    // private String pkid;
    private String sectionId = "";//标段Id
    private String sectionName = "";//标段名称
    private String towerId = "";
    private String unitId = "";
    private String floorId;
    private String roomId;
    private String partsDivision = "";
    //private String inspectionId = "";
    private String secInsId = "";
    //    private SCSLBdBean bean;
    private List<BiaoDuanBean> bean;
    private GXYJPopAdapter popAdapter1, popAdapter2, popAdapter3;
    private GXYJPopAdapter2 popAdapter4;
    private GXYJPopAdapter3 popAdapter5;
    // private String selectIds = "", towerD = "", floorD = "", roomD = "";
    private String towerName = "", unitName = "";
    private String inspectionName = "";
    private String inspectionSunName = "";
    private String inspectionSunId = "";
    private String detailsName = "";
    private String userTag;
    private Bundle formBundle;
    private int pos1, pos2, pos3, pos4, pos5;
    /**
     * 获取工程列表及分类 弹窗
     */
    private List<InspectionTwoBean> childInsBeanList;


    @Override
    protected void init(Bundle savedInstanceState) {
        //  pkid = getIntent().getBundleExtra("data").getString("pkid");
        formBundle = getIntent().getBundleExtra("formBundle");
        if (formBundle != null) {
            sectionId = formBundle.getString("sectionId");
            sectionName = formBundle.getString("sectionName");
            towerId = formBundle.getString("towerId");
            towerName = formBundle.getString("towerName");
            unitId = formBundle.getString("unitId");
            unitName = formBundle.getString("unitName");
            inspectionSunName = formBundle.getString("inspectionSunName");
            inspectionSunId = formBundle.getString("inspectionSunId");
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_gxyj;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("工序移交");
        setAndroidNativeLightStatusBar(true);

        findViewById(R.id.leftLL).setOnClickListener(this);
        findViewById(R.id.rightLL).setOnClickListener(this);

//        setTitleRightmenu();

        buildRcv = findViewById(R.id.buildRcv);
        tv_biaoDuan = findViewById(R.id.tv_biaoDuan);
        tv_tower = findViewById(R.id.tv_tower);
        projectName = findViewById(R.id.projectName);
        projectType = findViewById(R.id.projectType);
        commitBT = findViewById(R.id.commitBT);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        commitBT.setOnClickListener(this);

        userTag = UserInfo.create(getContext()).getUserType();

        if (popupWindow == null) {
            popupWindow = new CommonPopupWindow(getContext(), findViewById(R.id.ntslv), R.layout.gxyj_pop_layout);
//            popupWindow.setContentViewMaxHeight(0.6f);
            popupWindow.setContentViewHeight(0.5f);
            rcv1 = popupWindow.getContentView().findViewById(R.id.rcv1);
            rcv2 = popupWindow.getContentView().findViewById(R.id.rcv2);
            rcv3 = popupWindow.getContentView().findViewById(R.id.rcv3);
            rcv1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rcv2.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            rcv3.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (bean == null) {

//                    getbd();
                    getBDList();
                }
                getBuildList();//下拉记载
            }
        });
    }

    @Override
    protected void initData() {
//        getbd();
        getBDList();
    }

    @Override
    public void onClick(View v) {
        int vid = v.getId();

        if (vid == R.id.leftLL) {//选择标段 楼栋
            if (!popupWindow.isShowing()) {
                rcv1.setAdapter(popAdapter1);
                rcv2.setAdapter(popAdapter2);
                rcv3.setAdapter(popAdapter3);
                rcv3.setVisibility(View.VISIBLE);
                popupWindow.show();
            } else {
                popupWindow.closeMenu();
            }
        }
        if (vid == R.id.rightLL) {//选择工程 类型
            if (!popupWindow.isShowing()) {
                rcv1.setAdapter(popAdapter4);
                rcv2.setAdapter(popAdapter5);
//            rcv3.setAdapter(popAdapter3);
                rcv3.setVisibility(View.GONE);
                popupWindow.show();
            } else {
                popupWindow.closeMenu();
            }
        }
        if (vid == R.id.commitBT) {//批量验收
            Intent intent = new Intent(getContext(), TurnOverPart.class);
            intent.putExtra("partsDivision", partsDivision);
            intent.putExtra("selectroom", "");
            startActivityForResult(intent, 203);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 203) {
                String selectroom = data.getStringExtra("selectRoom");
                String selectIds = data.getStringExtra("selectIds");
                String floorIds = data.getStringExtra("floorIds");
                String floorNames = data.getStringExtra("floorNames");
                Intent intent = new Intent();
                intent.setClass(getContext(), ProcessOver.class);
                intent.putExtra("partsDivision", partsDivision);
                intent.putExtra("detailsName", detailsName);
                intent.putExtra("inspectionName", projectName.getText().toString());
                intent.putExtra("inspectionSunName", projectType.getText().toString());
                intent.putExtra("biaoduan", tv_biaoDuan.getText().toString());
                intent.putExtra("tower", tv_tower.getText().toString());
                intent.putExtra("towerId", towerId);
                intent.putExtra("unitId", unitId);
//                intent.putExtra("floorId", floorId);
                intent.putExtra("floorId", floorIds);
                intent.putExtra("floorName", floorNames);
                intent.putExtra("roomId", roomId);
                intent.putExtra("sectionId", sectionId);
                intent.putExtra("inspectionId", inspectionSunId);
                intent.putExtra("selectroom", selectroom);
                intent.putExtra("selectIds", selectIds);
                intent.putExtra("secInsId", secInsId);
                intent.putExtra("towerName", towerName);
                intent.putExtra("isNeedBuild", isNeedBuild);
                intent.putExtra("unitName", unitName);
                intent.putExtra("identifying", "空");
                intent.putExtra("entrance", "WZT");
                startActivity(intent);
            }
        }
    }

    /**
     * eventbus 监听
     *
     * @param event
     */
    @Override
    public void onMessageEvent(Object event) {
        if (event instanceof String) {
            if ("deleteGXYJTask".equals(event)) {
                getBuildList();//任务被删除后再次请求接口
            } else if ("GXYJTaskStateChanged".equals(event)) {
                getBuildList();//任务添加成功后再次请求接口
            }
        }
    }

    /**
     * 右上角 按钮
     */
    private void setTitleRightmenu() {

        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 60);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView commitTv = new TextView(getContext());
        commitTv.setLayoutParams(layoutParams);
        commitTv.setGravity(Gravity.CENTER);
        commitTv.setPadding(dp10px, 0, dp10px, 0);
        commitTv.setText("进度汇总");
        commitTv.setTextColor(0xff232323);
        commitTv.setTextSize(15);
        getBarRightView().addView(commitTv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GXYJActivity.this, GCYJCollectDetail.class));
            }
        });
    }

    /**
     * 存到数据库
     */
    private void saveBD() {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        DaoSession daoSession = MyApp.getInstance().getDaoSession();
                        for (BiaoDuanBean duanBean : bean) {
                            daoSession.insertOrReplace(duanBean);
                            for (TowerBean towerBean : duanBean.scopeChild) {
                                daoSession.insertOrReplace(towerBean);
                            }
                            for (InspectionOneBean towerBean : duanBean.inspection) {
                                daoSession.insertOrReplace(towerBean);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 标段 楼栋   单元数据 弹窗
     * 旧字段 地块id-pkId  新字段 地块id-dikuaiId
     */
    private void getBDList() {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectSection)
                .params("dikuaiId", Constant.projectId)
                .doGet(new HttpObjectCallBack<BiaoDuanBean>(BiaoDuanBean.class, true) {
                    @Override
                    public void onSuccess(BaseBean<BiaoDuanBean> result) {
                        swipeRefreshLayout.setRefreshing(false);
                        if (result.code.equals("0")) {
                            bean = result.list;
                            saveBD();
                            List<String> sections = new ArrayList<>();
                            List<String> tower = new ArrayList<>();
                            List<String> unit = new ArrayList<>();
                            if (formBundle != null) {//从报表跳转过来
                                SPUtil.get(getContext()).saveSectionId(sectionId);
                                tv_biaoDuan.setText(sectionName);
                                tv_tower.setText(towerName);
                                //第一层列表
                                for (int i = 0; i < bean.size(); i++) {
                                    sections.add(bean.get(i).sectionName);
                                    if (TextUtils.equals(sectionId, bean.get(i).sectionId + "")) {
                                        pos1 = i;
                                        //第二层列表
                                        List<TowerBean> tempTowerList = bean.get(i).scopeChild;
                                        for (int j = 0; j < tempTowerList.size(); j++) {
                                            tower.add(tempTowerList.get(j).tower);
                                            if (TextUtils.equals(towerId, tempTowerList.get(j).towerId + "")) {
                                                pos2 = j;
                                                //第三层列表
                                                List<UnitBean> tempUnitList
                                                        = tempTowerList.get(j).uintChild;
                                                for (int k = 0; k < tempUnitList.size(); k++) {
                                                    unit.add(tempUnitList.get(k).unit);
                                                    if (TextUtils.equals(unitId, tempUnitList.get(k).unitId + "")) {
                                                        pos3 = k;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {//工作台跳转过来
                                try {
                                    sectionId = bean.get(0).sectionId + "";
                                    sectionName = bean.get(0).sectionName;
                                    SPUtil.get(getContext()).saveSectionId(sectionId);
                                    try {
                                        towerId = bean.get(0).scopeChild.get(0).uintChild.get(0).towerId + "";
                                        towerName = bean.get(0).scopeChild.get(0).tower;
                                        unitId = bean.get(0).scopeChild.get(0).uintChild.get(0).unitId + "";
                                        unitName = bean.get(0).scopeChild.get(0).uintChild.get(0).unit;
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    tv_biaoDuan.setText(bean.get(0).sectionName);
                                    tv_tower.setText(bean.get(0).scopeChild.get(0).tower + "-" + bean.get(0).scopeChild.get(0).uintChild.get(0).unit);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    for (int i = 0; i < bean.size(); i++) {
                                        sections.add(bean.get(i).sectionName);
                                        if (i == 0) {
                                            for (int j = 0; j < bean.get(0).scopeChild.size(); j++) {
                                                tower.add(bean.get(0).scopeChild.get(j).tower);
                                                if (j == 0) {
                                                    for (int k = 0; k < bean.get(0).scopeChild.get(0).uintChild.size(); k++) {
                                                        unit.add(bean.get(0).scopeChild.get(0).uintChild.get(k).unit);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            if (popAdapter1 == null) {
                                popAdapter1 = new GXYJPopAdapter(sections);
                                popAdapter1.setSelectItem(pos1);
                                popAdapter2 = new GXYJPopAdapter(tower);
                                popAdapter2.setSelectItem(pos2);
                                popAdapter3 = new GXYJPopAdapter(unit);
                                popAdapter3.setSelectItem(pos3);
                                rcv1.setAdapter(popAdapter1);
                                rcv2.setAdapter(popAdapter2);
                                rcv3.setAdapter(popAdapter3);
                            }
                            popAdapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    popAdapter1.setSelectItem(position);
                                    sectionId = bean.get(position).sectionId + "";
                                    SPUtil.get(getContext()).saveSectionId(sectionId);
                                    popAdapter1.notifyDataSetChanged();
                                    List<String> tower = new ArrayList<>();
                                    List<String> unit = new ArrayList<>();
                                    try {
                                        for (int i = 0; i < bean.get(position).scopeChild.size(); i++) {
                                            tower.add(bean.get(position).scopeChild.get(i).tower);
                                            if (i == 0) {
                                                for (int k = 0; k < bean.get(position).scopeChild.get(i).uintChild.size(); k++) {
                                                    unit.add(bean.get(position).scopeChild.get(i).uintChild.get(k).unit);
                                                }
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
//                            listBean = bean.list.get(position).scopeChild;
                                    popAdapter2.setNewData(tower);
                                    popAdapter2.setSelectItem(0);
                                    popAdapter2.notifyDataSetChanged();
                                    popAdapter3.setNewData(unit);
                                    popAdapter3.setSelectItem(0);
                                    popAdapter3.notifyDataSetChanged();

                                }
                            });

                            popAdapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    popAdapter2.setSelectItem(position);
                                    popAdapter2.notifyDataSetChanged();
                                    List<String> unit = new ArrayList<>();
                                    for (int i = 0; i < bean.get(popAdapter1.getSelectItem()).scopeChild.get(position).uintChild.size(); i++) {
                                        unit.add(bean.get(popAdapter1.getSelectItem()).scopeChild.get(position).uintChild.get(i).unit);
                                    }
                                    popAdapter3.setNewData(unit);
                                    popAdapter3.setSelectItem(0);
                                    popAdapter3.notifyDataSetChanged();
//                            if (ListUtils.listEmpty(listBean)) {
//                                towerId = listBean.get(position).towerId + "";
//                                towerName = listBean.get(position).tower;
//                                unitList = listBean.get(position).uintChild;
//                            }
                                }
                            });
                            popAdapter3.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    popAdapter3.setSelectItem(position);
                                    popAdapter3.notifyDataSetChanged();
                                    towerName = bean.get(popAdapter1.getSelectItem()).scopeChild.get(popAdapter2.
                                            getSelectItem()).tower + "";
                                    towerId = bean.get(popAdapter1.getSelectItem()).scopeChild.get(popAdapter2.
                                            getSelectItem()).towerId + "";

                                    unitName = bean.get(popAdapter1.getSelectItem()).scopeChild.
                                            get(popAdapter2.getSelectItem()).uintChild.get(position).unit + "";
                                    unitId = bean.get(popAdapter1.getSelectItem()).scopeChild.
                                            get(popAdapter2.getSelectItem()).uintChild.get(position).unitId + "";

                                    popupWindow.closeMenu();
                                    tv_biaoDuan.setText(bean.get(popAdapter1.getSelectItem()).sectionName);
                                    tv_tower.setText(bean.get(popAdapter1.getSelectItem()).scopeChild.get(popAdapter2.getSelectItem()).tower + "-"
                                            + bean.get(popAdapter1.getSelectItem()).scopeChild.get(popAdapter2.getSelectItem()).uintChild.get(position).unit);
//                            if (ListUtils.listEmpty(unitList)) {
//                                unitName = unitList.get(position).unit;
//                                unitId = unitList.get(position).unitId + "";
//                            }
//                            if (ListUtils.listEmpty(listBean)) {
//                                towerId = listBean.get(towerCheck).towerId + "";
//                                towerName = listBean.get(towerCheck).tower;
//                            }

//                                    getlistpo();
                                    getInspection();//选择单元后 获取检查项
//                            getBuildList();//选择单元
                                }
                            });
//                            getlistpo();//默认加载  获取检查项
                            getInspection();//默认加载  获取检查项
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        try {
                            DaoSession daoSession = MyApp.getInstance().getDaoSession();
                            QueryBuilder<BiaoDuanBean> qb = daoSession.queryBuilder(BiaoDuanBean.class);
                            Query<BiaoDuanBean> query = qb.where(BiaoDuanBeanDao.Properties.DikuaiId.eq(Constant.projectId)).build();
                            List<BiaoDuanBean> list = query.list();
                            BaseBean<BiaoDuanBean> baseBean = new BaseBean<>();
                            baseBean.list = list;
                            baseBean.code = "0";
                            onSuccess(baseBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /**
     * 获取检查项列表//TODO
     */
    private void getInspection() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listProcessOver)
                .params("sectionId", sectionId)
                .doPost(new HttpObjectCallBack<InspectionOneBean>(InspectionOneBean.class) {
                    @Override
                    public void onSuccess(final BaseBean<InspectionOneBean> result) {
                        if (!result.code.equals("0")) {
                            ToastUtils.myToast(GXYJActivity.this, result.msg);
                            return;
                        }
                        List<InspectionOneBean> inspectionList = new ArrayList<>();
                        List<InspectionTwoBean> inspectionSunList = new ArrayList<>();
                        if (formBundle != null) {//从报表跳转过来
                            //projectName.setText(inspectionName);
                            // projectType.setText(inspectionSunName);
                            inspectionList.addAll(result.list);
                            for (int i = 0; i < result.list.size(); i++) {
                                List<InspectionTwoBean> tempList = result.list.get(i).getChildIns();
                                for (int j = 0; j < tempList.size(); j++) {
                                    if (TextUtils.equals(inspectionSunId, tempList.get(j).getInspectionId())) {
                                        pos4 = i;
                                        pos5 = j;
                                        inspectionSunList.addAll(tempList);
                                        projectName.setText(result.list.get(i).getInspectionName());
                                        inspectionName = result.list.get(i).getInspectionName();
                                        projectType.setText(tempList.get(j).getInspectionName());
                                        partsDivision = tempList.get(j).getPartsDivision();
                                        secInsId = tempList.get(j).getSecInsId();
                                        detailsName = inspectionName + "-" + inspectionSunName;
                                        if ("1".equals(userTag) || "1".equals(userInfo.getUserAppTagSg())) {//
                                            if (partsDivision.equals("整栋")) {
                                                commitBT.setVisibility(View.GONE);
                                            } else {
                                                commitBT.setVisibility(View.VISIBLE);
                                            }
                                        } else {
                                            commitBT.setVisibility(View.GONE);
                                        }
                                    }
                                }
                            }

                        } else {//从工作台跳转过来
                            try {
                                projectName.setText(result.list.get(0).getInspectionName());
                                inspectionName = result.list.get(0).getInspectionName();
                                try {
                                    projectType.setText(result.list.get(0).getChildIns().get(0).getInspectionName());
                                    partsDivision = result.list.get(0).getChildIns().get(0).getPartsDivision();
                                    secInsId = result.list.get(0).getChildIns().get(0).getSecInsId() + "";
                                    inspectionSunId = result.list.get(0).getChildIns().get(0).getInspectionId();
                                    inspectionSunName = result.list.get(0).getChildIns().get(0).getInspectionName();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                detailsName = inspectionName + "-" + inspectionSunName;

                                if ("1".equals(userTag) || "1".equals(userInfo.getUserAppTagSg())) {//
                                    if (partsDivision.equals("整栋")) {
                                        commitBT.setVisibility(View.GONE);
                                    } else {
                                        commitBT.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    commitBT.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            inspectionList.addAll(result.list);
                            inspectionSunList.addAll(result.list.get(0).getChildIns());
                        }

                        popAdapter4 = new GXYJPopAdapter2(inspectionList);
                        popAdapter5 = new GXYJPopAdapter3(inspectionSunList);
                        popAdapter4.setNewData(inspectionList);
                        popAdapter4.setSelectItem(pos4);
                        popAdapter5.setNewData(inspectionSunList);
                        popAdapter5.setSelectItem(pos5);

                        popAdapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                popAdapter4.setSelectItem(position);
                                popAdapter4.notifyDataSetChanged();
                                popAdapter5.setNewData(result.list.get(position).getChildIns());
                                popAdapter5.setSelectItem(0);
                                popAdapter5.notifyDataSetChanged();
                                childInsBeanList = result.list.get(position).getChildIns();
                            }
                        });
                        popAdapter5.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                popAdapter5.setSelectItem(position);
                                popAdapter5.notifyDataSetChanged();
                                partsDivision = result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getPartsDivision();
                                secInsId = result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getSecInsId() + "";
                                if (ListUtils.listEmpty(childInsBeanList)) {
                                    partsDivision = childInsBeanList.get(position).getPartsDivision();
                                }
                                commitBT.setVisibility(View.VISIBLE);
                                if ("1".equals(userTag) || "1".equals(userInfo.getUserAppTagSg())) {//
                                    if (partsDivision.equals("整栋")) {
                                        commitBT.setVisibility(View.GONE);
                                    } else {
                                        commitBT.setVisibility(View.VISIBLE);
                                    }
                                } else {
                                    commitBT.setVisibility(View.GONE);
                                }
                                popupWindow.closeMenu();
                                projectName.setText(result.list.get(popAdapter4.getSelectItem()).getInspectionName());
                                projectType.setText(result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionName());
//                        inspectionId = bean.getList().get(popAdapter4.getSelectItem()).getInspectionId() + "";
                                inspectionSunId = result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionId() + "";
                                inspectionName = result.list.get(popAdapter4.getSelectItem()).getInspectionName();
                                inspectionSunName = result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionName();
                                isNeedBuild = result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getIsNeedBuild();
                                detailsName = result.list.get(popAdapter4.getSelectItem()).getInspectionName()
                                        + "-" + result.list.get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionName();

                                getBuildList();//选择工程检查项类型 获取房间 楼层 栋 列表
                            }
                        });
                        getBuildList();//默认加载获取房间 楼层 栋 列表
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        try {
                            DaoSession daoSession = MyApp.getInstance().getDaoSession();
                            QueryBuilder<InspectionOneBean> qb = daoSession.queryBuilder(InspectionOneBean.class);
                            Query<InspectionOneBean> query = qb.where(InspectionOneBeanDao.Properties.SectionId.eq(sectionId)).build();
                            List<InspectionOneBean> list = query.list();
                            BaseBean<InspectionOneBean> baseBean = new BaseBean<>();
                            baseBean.list = list;
                            baseBean.code = "0";
                            onSuccess(baseBean);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }


//    /**
//     * 获取检查项列表//TODO
//     */
//    private void getlistpo() {
//        HttpRequest.get(getContext()).url(ServerInterface.listProcessOver).params("sectionId", sectionId).doPost(new HttpStringCallBack() {
//            @Override
//            public void onSuccess(Object result) {
//                LogUtils.i("getlistpo===", result.toString());
//                final InspectionBean bean = new Gson().fromJson(result.toString(), InspectionBean.class);
//                if (bean.getCode() != 0) {
//                    ToastUtils.myToast(GXYJActivity.this, bean.getMsg());
//                    return;
//                }
//                List<InspectionBean.ListBean> inspectionList = new ArrayList<>();
//                List<InspectionBean.ListBean.ChildInsBean> inspectionSunList = new ArrayList<>();
//                if (formBundle != null) {//从报表跳转过来
//                    //projectName.setText(inspectionName);
//                    // projectType.setText(inspectionSunName);
//                    inspectionList.addAll(bean.getList());
//                    for (int i = 0; i < bean.getList().size(); i++) {
//                        List<InspectionBean.ListBean.ChildInsBean> tempList = bean.getList().get(i).getChildIns();
//                        for (int j = 0; j < tempList.size(); j++) {
//                            if (TextUtils.equals(inspectionSunId, tempList.get(j).getInspectionId())) {
//                                pos4 = i;
//                                pos5 = j;
//                                inspectionSunList.addAll(tempList);
//                                projectName.setText(bean.getList().get(i).getInspectionName());
//                                inspectionName = bean.getList().get(i).getInspectionName();
//                                projectType.setText(tempList.get(j).getInspectionName());
//                                partsDivision = tempList.get(j).getPartsDivision();
//                                secInsId = tempList.get(j).getSecInsId();
//                                detailsName = inspectionName + "-" + inspectionSunName;
//                                if ("1".equals(userTag) || "1".equals(userInfo.getUserAppTagSg())) {//
//                                    if (partsDivision.equals("整栋")) {
//                                        commitBT.setVisibility(View.GONE);
//                                    } else {
//                                        commitBT.setVisibility(View.VISIBLE);
//                                    }
//                                } else {
//                                    commitBT.setVisibility(View.GONE);
//                                }
//                            }
//                        }
//                    }
//
//                } else {//从工作台跳转过来
//                    try {
//                        projectName.setText(bean.getList().get(0).getInspectionName());
//                        inspectionName = bean.getList().get(0).getInspectionName();
//                        try {
//                            projectType.setText(bean.getList().get(0).getChildIns().get(0).getInspectionName());
//                            partsDivision = bean.getList().get(0).getChildIns().get(0).getPartsDivision();
//                            secInsId = bean.getList().get(0).getChildIns().get(0).getSecInsId() + "";
//                            inspectionSunId = bean.getList().get(0).getChildIns().get(0).getInspectionId();
//                            inspectionSunName = bean.getList().get(0).getChildIns().get(0).getInspectionName();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        detailsName = inspectionName + "-" + inspectionSunName;
//
//                        if ("1".equals(userTag) || "1".equals(userInfo.getUserAppTagSg())) {//
//                            if (partsDivision.equals("整栋")) {
//                                commitBT.setVisibility(View.GONE);
//                            } else {
//                                commitBT.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            commitBT.setVisibility(View.GONE);
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    inspectionList.addAll(bean.getList());
//                    inspectionSunList.addAll(bean.getList().get(0).getChildIns());
//                }
//
//                popAdapter4 = new GXYJPopAdapter2(inspectionList);
//                popAdapter5 = new GXYJPopAdapter3(inspectionSunList);
//                popAdapter4.setNewData(inspectionList);
//                popAdapter4.setSelectItem(pos4);
//                popAdapter5.setNewData(inspectionSunList);
//                popAdapter5.setSelectItem(pos5);
//
//                popAdapter4.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        popAdapter4.setSelectItem(position);
//                        popAdapter4.notifyDataSetChanged();
//                        popAdapter5.setNewData(bean.getList().get(position).getChildIns());
//                        popAdapter5.setSelectItem(0);
//                        popAdapter5.notifyDataSetChanged();
//                        childInsBeanList = bean.getList().get(position).getChildIns();
//                    }
//                });
//                popAdapter5.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        popAdapter5.setSelectItem(position);
//                        popAdapter5.notifyDataSetChanged();
//                        partsDivision = bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getPartsDivision();
//                        secInsId = bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getSecInsId() + "";
//                        if (ListUtils.listEmpty(childInsBeanList)) {
//                            partsDivision = childInsBeanList.get(position).getPartsDivision();
//                        }
//                        commitBT.setVisibility(View.VISIBLE);
//                        if ("1".equals(userTag) || "1".equals(userInfo.getUserAppTagSg())) {//
//                            if (partsDivision.equals("整栋")) {
//                                commitBT.setVisibility(View.GONE);
//                            } else {
//                                commitBT.setVisibility(View.VISIBLE);
//                            }
//                        } else {
//                            commitBT.setVisibility(View.GONE);
//                        }
//                        popupWindow.closeMenu();
//                        projectName.setText(bean.getList().get(popAdapter4.getSelectItem()).getInspectionName());
//                        projectType.setText(bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionName());
////                        inspectionId = bean.getList().get(popAdapter4.getSelectItem()).getInspectionId() + "";
//                        inspectionSunId = bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionId() + "";
//                        inspectionName = bean.getList().get(popAdapter4.getSelectItem()).getInspectionName();
//                        inspectionSunName = bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionName();
//                        isNeedBuild = bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getIsNeedBuild();
//                        detailsName = bean.getList().get(popAdapter4.getSelectItem()).getInspectionName() + "-" + bean.getList().get(popAdapter4.getSelectItem()).getChildIns().get(position).getInspectionName();
//
//                        getBuildList();//选择工程检查项类型 获取房间 楼层 栋 列表
//                    }
//                });
//                getBuildList();//默认加载获取房间 楼层 栋 列表
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//
//            }
//        });
//    }

    private String isNeedBuild;//是否需建设单位验收：0否，1是

    /**
     * 获取 中间 房间列表数据
     */
    private void getBuildList() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listPOTowerFloorUnit)
                .params("towerId", towerId)
                .params("unitId", unitId)
                .params("partsDivision", partsDivision)
                .params("inspectionName", inspectionName)
                .params("inspectionSunName", inspectionSunName)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            swipeRefreshLayout.setRefreshing(false);
                            String s = result.toString();
                            SharedPreferences sp = getActivity().getSharedPreferences("GXYJBuildList", Context.MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString("listPOTowerFloorUnit", s);
                            ed.commit();
                            switch (partsDivision) {
                                case "分户":
                                    room(s);
                                    break;
                                case "整栋":
                                    tower(s);
                                    break;
                                case "不分单元-整层":
                                case "分单元-整层":
                                    floor(s);
                                    break;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 分户数据类型
     *
     * @param s
     */
    private void room(String s) {
        LogUtils.i("gxyj==json=room==", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.optInt("code") == 0) {
                JSONArray array = jsonObject.optJSONArray("list");
                ArrayList<MultiItemEntity> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject floor = array.optJSONObject(i);
                    GXYJFloorBean floorBean = new GXYJFloorBean();
                    floorBean.floor = floor.optString("floor");
                    floorBean.floorId = floor.optInt("floorId");
                    floorBean.unitId = floor.optInt("unitId");
                    JSONArray roomNumChildarr = floor.optJSONArray("roomNumChild");
                    for (int j = 0; j < roomNumChildarr.length(); j++) {
                        JSONObject room = roomNumChildarr.optJSONObject(j);
                        GXYJRoomBean roomBean = new GXYJRoomBean();
                        roomBean.floor = floorBean.floor;
                        roomBean.floorId = room.optInt("floorId");
                        roomBean.roomId = room.optInt("roomId");
                        roomBean.roomNum = room.optString("roomNum");
                        roomBean.utiId = room.optInt("utiId");
                        roomBean.identifying = room.optString("identifying");
                        floorBean.addSubItem(roomBean);
                    }
                    list.add(floorBean);
                }
                setBuildRcv(list);
            } else {
                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分单元-整层
     *
     * @param s
     */
    private void floor(String s) {
        LogUtils.i("gxyj==json=floor==", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.optInt("code") == 0) {
                JSONArray array = jsonObject.optJSONArray("list");
                ArrayList<MultiItemEntity> list = new ArrayList<>();
                list.add(new GXYJFloorBean());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    GXYJAllFloorBean allFloorBean = new GXYJAllFloorBean();
                    allFloorBean.unitId = object.optString("unitId");
                    allFloorBean.floor = object.optString("floor") + "层";
                    allFloorBean.floorId = object.optString("floorId");
                    allFloorBean.utiId = object.optString("utiId");
                    allFloorBean.tower = object.optString("tower");
                    allFloorBean.identifying = object.optString("identifying");
                    list.add(allFloorBean);
                }
                setBuildRcv(list);
            } else {
                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 整栋
     *
     * @param s
     */
    private void tower(String s) {
        LogUtils.i("gxyj==json=tower==", s);
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.optInt("code") == 0) {
                JSONArray array = jsonObject.optJSONArray("list");
                ArrayList<MultiItemEntity> list = new ArrayList<>();
                list.add(new GXYJFloorBean());
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    GXYJAllTowerBean allTowerBean = new GXYJAllTowerBean();
                    allTowerBean.pkId = object.optInt("pkId");
                    allTowerBean.floor = object.optInt("floor");
                    allTowerBean.towerId = object.optInt("towerId");
                    allTowerBean.unitId = object.optInt("unitId");
                    allTowerBean.utiId = object.optInt("utiId");
                    allTowerBean.tower = object.optString("tower");
                    allTowerBean.identifying = object.optString("identifying");
                    list.add(allTowerBean);
                }
                setBuildRcv(list);
            } else {
                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置数据到 rcv
     *
     * @param list
     */
    private void setBuildRcv(ArrayList<MultiItemEntity> list) {
        final GXYJBuildingsAdapter gxyjBuildingsAdapter = new GXYJBuildingsAdapter(list);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return gxyjBuildingsAdapter.getItemViewType(position) == GXYJBuildingsAdapter.TYPE_ROOM ? 1 : manager.getSpanCount();
            }
        });
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        buildRcv.setHasFixedSize(true);
        buildRcv.setNestedScrollingEnabled(false);
        buildRcv.setAdapter(gxyjBuildingsAdapter);
        buildRcv.setLayoutManager(manager);
        gxyjBuildingsAdapter.expandAll();
        gxyjBuildingsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.roomName) {

                    String identifying = "";
                    Intent intent = new Intent();
                    intent.setClass(getContext(), ProcessOver.class);
                    intent.putExtra("partsDivision", partsDivision);
                    intent.putExtra("inspectionName", projectName.getText().toString());
                    intent.putExtra("inspectionSunName", projectType.getText().toString());
                    intent.putExtra("biaoduan", tv_biaoDuan.getText().toString());
                    intent.putExtra("tower", tv_tower.getText().toString());
                    intent.putExtra("towerId", towerId);
                    intent.putExtra("unitId", unitId);
                    intent.putExtra("sectionId", sectionId);
                    intent.putExtra("inspectionId", inspectionSunId);
                    intent.putExtra("isNeedBuild", isNeedBuild);
//                    intent.putExtra("isNeedBuild", "1");
                    intent.putExtra("detailsName", detailsName);
                    if (gxyjBuildingsAdapter.getData().get(position) instanceof GXYJRoomBean) {
                        GXYJRoomBean roomBean = (GXYJRoomBean) gxyjBuildingsAdapter.getData().get(position);
                        intent.putExtra("selectroom", roomBean.roomNum);
                        intent.putExtra("selectIds", roomBean.roomId + "");
                        intent.putExtra("roomId", roomBean.roomId + "");
                        intent.putExtra("floorId", roomBean.floorId + "");
                        intent.putExtra("floorName", roomBean.floor + "");
                        intent.putExtra("identifying", roomBean.identifying);
                        identifying = roomBean.identifying;
                        System.out.println("selectIds111===" + roomBean.roomId + "");
                    } else if (gxyjBuildingsAdapter.getData().get(position) instanceof GXYJAllFloorBean) {
                        GXYJAllFloorBean roomBean = (GXYJAllFloorBean) gxyjBuildingsAdapter.getData().get(position);
                        intent.putExtra("selectroom", roomBean.floor + "");
                        intent.putExtra("selectIds", roomBean.floorId + "");
                        intent.putExtra("floorId", roomBean.floorId + "");
                        intent.putExtra("floorName", roomBean.floor + "");
                        intent.putExtra("identifying", roomBean.identifying);
                        identifying = roomBean.identifying;
                        System.out.println("selectIds222===" + roomBean.floorId + "");
                    } else if (gxyjBuildingsAdapter.getData().get(position) instanceof GXYJAllTowerBean) {
                        GXYJAllTowerBean allTowerBean = (GXYJAllTowerBean) gxyjBuildingsAdapter.getData().get(position);
                        intent.putExtra("selectroom", "整栋");
                        intent.putExtra("selectIds", allTowerBean.towerId + "");
                        intent.putExtra("towerId", allTowerBean.towerId + "");
                        intent.putExtra("identifying", allTowerBean.identifying);
                        identifying = allTowerBean.identifying;
                        System.out.println("selectIds333===" + allTowerBean.towerId + "");
                    } else {
                        //ToastUtils.myToast(getActivity(), position + "");
                    }
                    if (("2".equals(userTag) || "3".equals(userTag)) && "0".equals(userInfo.getUserAppTagSg())) {
                        if ("空".equals(identifying)) {
                            ToastUtils.myToast(getActivity(), "施工单位尚未申请验收");
                            return;
                        }
                    }
                    intent.putExtra("secInsId", secInsId);
                    intent.putExtra("towerName", towerName);
                    intent.putExtra("unitName", unitName);
                    intent.putExtra("entrance", "WZT");
                    startActivity(intent);
                }
            }
        });
    }

}
