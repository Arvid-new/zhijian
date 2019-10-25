package com.haozhiyan.zhijian.activity.workScsl;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.adapter.FragmentAdapter;
import com.haozhiyan.zhijian.adapter.MyListAdapter;
import com.haozhiyan.zhijian.adapter.SCSLjCxAdapter;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.scsl.SCSLjCxBean;
import com.haozhiyan.zhijian.bean.scsl.SCSLjCxLevel1;
import com.haozhiyan.zhijian.bean.scsl.SCSLjCxLevel2;
import com.haozhiyan.zhijian.bean.scsl.SCSLldcBean;
import com.haozhiyan.zhijian.fragment.scslFragment.JsDw_JL_SCSlFragment;
import com.haozhiyan.zhijian.fragment.scslFragment.JsDw_JS_SCSlFragment;
import com.haozhiyan.zhijian.fragment.scslFragment.JsDw_SG_SCSlFragment;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PopWindowUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.haozhiyan.zhijian.utils.UiUtils.getContext;

public class SCSLDetail extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.iv_right_icon)
    ImageView ivRightIcon;
    @ViewInject(R.id.tabs)
    TabLayout mTabLayout;
    @ViewInject(R.id.vp_view)
    ViewPager mViewPager;

    @ViewInject(R.id.tv_bdName)
    TextView tv_bdName;
    @ViewInject(R.id.tv_local)
    TextView tv_local;
    @ViewInject(R.id.tv_desc_title)
    TextView tvDescTitle;
    @ViewInject(R.id.tv_desc_content)
    TextView tvDescContent;

    @ViewInject(R.id.rl_backClick)
    RelativeLayout rlBackClick;
    @ViewInject(R.id.iv01)
    ImageView iv01;
    @ViewInject(R.id.iv02)
    ImageView iv02;
    @ViewInject(R.id.ll_bd)
    LinearLayout ll_bd;
    @ViewInject(R.id.ll_bd_desc)
    LinearLayout ll_bd_desc;
    @ViewInject(R.id.ll_four)
    LinearLayout llFour;
    @ViewInject(R.id.ll_two)
    LinearLayout llTwo;
    @ViewInject(R.id.lv01)
    ListView lv01;
    @ViewInject(R.id.lv02)
    ListView lv02;
    @ViewInject(R.id.lv03)
    ListView lv03;
    @ViewInject(R.id.lv04)
    ListView lv04;
    @ViewInject(R.id.scsl_jcxList)
    RecyclerView scslJcxList;
    @ViewInject(R.id.ll_window)
    LinearLayout llWindow;

    private JsDw_SG_SCSlFragment mSGFragment; // 施工
    private JsDw_JL_SCSlFragment mJLFragment; // 监理
    private JsDw_JS_SCSlFragment mJSFragment; // 建设
    private String inspectionId = "", inspectionParentId = "";
    private Bundle mBundle;
    private String pageType = "施工";
    private String bdName1 = "", bdNameTower = "", bdNameUnit = "", bdName3 = "", bdName4 = "", bdDesc1 = "", bdDesc2 = "";
    private MyListAdapter adapter1, adapter2, adapter3, adapter4;
    private SCSLjCxAdapter scsLjCxAdapter;
    private List<ItemBean> data1 = new ArrayList<>();
    private List<ItemBean> data2 = new ArrayList<>();
    private List<ItemBean> data3 = new ArrayList<>();
    private List<ItemBean> data4 = new ArrayList<>();
    private int mHiddenViewBackHeight, bdRoomId = 1;
    private String sectionId = "", towerId = "", unitId = "", scslType = "分户", towerUnitFloorRoom = "",floorName="",floorId = "";
    private String sectionName="",inspectionName = "", inspectionSunName = "", checkGuideContent = "暂无说明", entrance = "";
    private List<SCSLldcBean.DataBean> dataBeans;
    private List<SCSLldcBean.DataBean.ScopeChildBean> myScopeChildBeans;
    private List<SCSLldcBean.DataBean.ScopeChildBean.UnitChildBean> myUnitChildBeans;
    private List<SCSLldcBean.DataBean.ScopeChildBean.UnitChildBean.FloorChildBean> myFloorChildBeans;
    private List<SCSLldcBean.DataBean.ScopeChildBean.UnitChildBean.FloorChildBean.RoomNumChildBean> myRoomNumChildBeans;
    private ArrayList<MultiItemEntity> list;
    private PopWindowUtils popWindowUtils;
    private String shiGongStatus = "", jianLiStatus = "", jianSheStatus = "", roomNum = "";
    private String userRole = "", towerName = "", unitName = "", roomName = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_scsldetail;
    }


    @Override
    protected void initView() {
        bdRoomId = getIntent().getIntExtra("roomId", 0);
        inspectionId = getIntent().getStringExtra("inspectionId");
        inspectionParentId = getIntent().getStringExtra("inspectionParentId");
        inspectionName = getIntent().getStringExtra("inspectionName");
        inspectionSunName = getIntent().getStringExtra("inspectionSunName");
        towerUnitFloorRoom = getIntent().getStringExtra("towerUnitFloorRoom");
        floorName = getIntent().getStringExtra("floorName");
        floorId = getIntent().getStringExtra("floorId");
        entrance = getIntent().getStringExtra("entrance");
        shiGongStatus = getIntent().getStringExtra("shiGongStatus");
        jianLiStatus = getIntent().getStringExtra("jianLiStatus");
        jianSheStatus = getIntent().getStringExtra("jianSheStatus");
        roomNum = getIntent().getStringExtra("roomNum");
        sectionId = getIntent().getStringExtra("sectionId");
        sectionName = getIntent().getStringExtra("sectionName");
        towerId = getIntent().getStringExtra("towerId");
        towerName = getIntent().getStringExtra("towerName");
        unitId = getIntent().getStringExtra("unitId");
        unitName = getIntent().getStringExtra("unitName");
        userRole = userInfo.getUserType();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        mHiddenViewBackHeight = SystemUtils.getPhoneScreenHight(this);
        if ("0".equals(userRole) || "3".equals(userRole)) {//admin
            mSGFragment = new JsDw_SG_SCSlFragment();
            getFragmentData(mSGFragment, 1);
            adapter.addFragment(mSGFragment, "施工");

            mJLFragment = new JsDw_JL_SCSlFragment();
            getFragmentData(mJLFragment, 2);
            adapter.addFragment(mJLFragment, "监理");

            mJSFragment = new JsDw_JS_SCSlFragment();
            getFragmentData(mJSFragment, 3);
            adapter.addFragment(mJSFragment, "建设");
        } else if ("1".equals(userRole)) {//施工
            mSGFragment = new JsDw_SG_SCSlFragment();
            getFragmentData(mSGFragment, 1);
            adapter.addFragment(mSGFragment, towerUnitFloorRoom);
        } else if ("2".equals(userRole)) {//监理
            mSGFragment = new JsDw_SG_SCSlFragment();
            getFragmentData(mSGFragment, 1);
            adapter.addFragment(mSGFragment, "施工");

            mJLFragment = new JsDw_JL_SCSlFragment();
            getFragmentData(mJLFragment, 2);
            adapter.addFragment(mJLFragment, "监理");
        }
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        LogUtils.print("userRole==" + userRole);
        if (mTabLayout.getTabCount() > 1) {
            mTabLayout.getTabAt(mTabLayout.getTabCount() - 1).select();
            //initFragmentHeader(mTabLayout.getTabCount());
        } else {
            mTabLayout.getTabAt(0).select();
            //initFragmentHeader(1);
        }
        popWindowUtils = PopWindowUtils.getPopWindow();
        scslJcxList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        scsLjCxAdapter = new SCSLjCxAdapter(null, this);
        scslJcxList.setAdapter(scsLjCxAdapter);
        //getScsljCx(sectionId);
        llWindow.setVisibility(View.GONE);
    }

    //设置bundle数据
    private Fragment getFragmentData(Fragment fragment, int type) {
        mBundle = new Bundle();
        mBundle.putInt("type", type);
        mBundle.putString("userType", userRole);
        mBundle.putString("inspectionId", inspectionId);
        mBundle.putString("inspectionParentId", inspectionParentId);
        mBundle.putString("inspectionName", inspectionName);
        mBundle.putString("inspectionSunName", inspectionSunName);
        mBundle.putString("partsDivision", scslType);
        mBundle.putString("sectionId", sectionId);
        mBundle.putString("towerId", towerId);
        mBundle.putString("unitId", unitId);
        mBundle.putString("sectionName", sectionName);
        mBundle.putString("towerName", towerName);
        mBundle.putString("unitName", unitName);
        mBundle.putInt("roomId", bdRoomId);
        mBundle.putString("towerUnitFloorRoom", towerUnitFloorRoom);
        mBundle.putString("entrance", entrance);
        mBundle.putString("pageType", pageType);
        mBundle.putString("shiGongStatus", shiGongStatus);
        mBundle.putString("jianLiStatus", jianLiStatus);
        mBundle.putString("jianSheStatus", jianSheStatus);
        mBundle.putString("roomNum", roomNum);
        mBundle.putString("floorName", floorName);
        mBundle.putString("floorId", floorId);
        //mBundle.putString("detailsName", bdDesc1 + bdDesc2);
        //mBundle.putString("siteName", towerName + unitName + floorName + roomName);
        fragment.setArguments(mBundle);
        return fragment;
    }

    @Override
    protected void initListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.logGGQ("实测实量详情下标==" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            /**
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                int tabCount = mTabLayout.getTabCount();
                if (tabCount == 1) {
                    pageType = "施工";
                    if ("1".equals(userRole) && "1".equals(shiGongStatus)) {
                        llWindow.setVisibility(View.GONE);
                    } else {
                        //llWindow.setVisibility(View.VISIBLE);
                        llWindow.setVisibility(View.GONE);
                    }
                } else if (tabCount == 2) {
                    if (position == 0) {
                        pageType = "施工";
                        if ("1".equals(userRole) && "1".equals(shiGongStatus)) {
                            llWindow.setVisibility(View.GONE);
                        } else {
                            //llWindow.setVisibility(View.VISIBLE);
                            llWindow.setVisibility(View.GONE);
                        }
                        //mSGFragment.refreshData();
                    }
                    if (position == 1) {
                        pageType = "监理";
                        if ("2".equals(userRole) && "2".equals(jianLiStatus)) {
                            llWindow.setVisibility(View.GONE);
                        } else {
                            //llWindow.setVisibility(View.VISIBLE);
                            llWindow.setVisibility(View.GONE);
                        }
                    }
                } else if (tabCount == 3) {
                    if (position == 0) {
                        pageType = "施工";
                        if ("1".equals(userRole) && "1".equals(shiGongStatus)) {
                            llWindow.setVisibility(View.GONE);
                        } else {
                            //llWindow.setVisibility(View.VISIBLE);
                            llWindow.setVisibility(View.GONE);
                        }
                        //mSGFragment.refreshData();
                    } else if (position == 1) {
                        pageType = "监理";
                        if ("2".equals(userRole) && "2".equals(jianLiStatus)) {
                            llWindow.setVisibility(View.GONE);
                        } else {
                            //llWindow.setVisibility(View.VISIBLE);
                            llWindow.setVisibility(View.GONE);
                        }
                        //mJLFragment.refreshData();
                    } else if (position == 2) {
                        pageType = "建设";
                        if (("3".equals(userRole) || "0".equals(userRole)) && "3".equals(jianSheStatus)) {
                            llWindow.setVisibility(View.GONE);
                        } else {
                            //llWindow.setVisibility(View.VISIBLE);
                            llWindow.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        lv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter1.setSelected(position);
                try {
                    if (ListUtils.listEmpty(dataBeans)) {
                        sectionId = dataBeans.get(position).sectionId + "";
                        if (userRole.equals("0") || userRole.equals("3")) {
                            mJSFragment.setSectionData(sectionId, dataBeans.get(position).sectionName);
                        } else if (userRole.equals("1")) {
                            mSGFragment.setSectionData(sectionId, dataBeans.get(position).sectionName);
                        } else if (userRole.equals("2")) {
                            mJLFragment.setSectionData(sectionId, dataBeans.get(position).sectionName);
                        }
                        getScsljCx(sectionId);
                        bdName1 = dataBeans.get(position).sectionName;
                        myScopeChildBeans = dataBeans.get(position).scopeChild;
                        data2.clear();
                        if (ListUtils.listEmpty(myScopeChildBeans)) {
                            for (int i = 0; i < myScopeChildBeans.size(); i++) {
                                myUnitChildBeans = myScopeChildBeans.get(i).unitChild;
                                if (ListUtils.listEmpty(myUnitChildBeans)) {
                                    for (int j = 0; j < myUnitChildBeans.size(); j++) {
                                        data2.add(new ItemBean(myScopeChildBeans.get(i).tower + "-" + myUnitChildBeans.get(j).unit + "单元", myScopeChildBeans.get(i).towerId + "", myUnitChildBeans.get(j).unitId + ""));
                                    }
                                }
                            }
                            adapter2 = new MyListAdapter(act, data2, R.layout.scsl_list_item);
                            lv02.setAdapter(adapter2);
                            adapter2.notifyDataSetChanged();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lv02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.setSelected(position);
                try {
                    if (ListUtils.listEmpty(myUnitChildBeans)) {
                        data3.clear();
                        tv_bdName.setText(bdName1);
                        bdNameTower = myScopeChildBeans.get(position).tower;
                        bdNameUnit = myUnitChildBeans.get(position).unit + "单元";
                        towerId = myScopeChildBeans.get(position).towerId + "";
                        unitId = myUnitChildBeans.get(position).unitId + "";
                        towerName = bdNameTower;
                        unitName = bdNameUnit;
                        if (userRole.equals("0") || userRole.equals("3")) {
                            mJSFragment.setTowerUnitData(towerId, bdNameTower, unitId, bdNameUnit);
                        } else if (userRole.equals("1")) {
                            mSGFragment.setTowerUnitData(towerId, bdNameTower, unitId, bdNameUnit);
                        } else if (userRole.equals("2")) {
                            mJLFragment.setTowerUnitData(towerId, bdNameTower, unitId, bdNameUnit);
                        }
                        myFloorChildBeans = myUnitChildBeans.get(position).floorChild;
                        for (int i = 0; i < myFloorChildBeans.size(); i++) {
                            data3.add(new ItemBean(myFloorChildBeans.get(i).floor + "层", myFloorChildBeans.get(i).floorId + "", ""));
                        }
                        adapter3 = new MyListAdapter(act, data3, R.layout.scsl_list_item);
                        lv03.setAdapter(adapter3);
                        adapter3.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lv03.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter3.setSelected(position);
                if (ListUtils.listEmpty(myFloorChildBeans)) {
                    if (userRole.equals("0") || userRole.equals("3")) {
                        mJSFragment.setFloorData(myFloorChildBeans.get(position).floorId + "", myFloorChildBeans.get(position).floor);
                    } else if (userRole.equals("1")) {
                        mSGFragment.setFloorData(myFloorChildBeans.get(position).floorId + "", myFloorChildBeans.get(position).floor);
                    } else if (userRole.equals("2")) {
                        mJLFragment.setFloorData(myFloorChildBeans.get(position).floorId + "", myFloorChildBeans.get(position).floor);
                    }
                    floorName = myFloorChildBeans.get(position).floor;
                    myRoomNumChildBeans = myFloorChildBeans.get(position).roomNumChild;
                    for (int i = 0; i < myRoomNumChildBeans.size(); i++) {
                        data4.add(new ItemBean(myRoomNumChildBeans.get(i).roomNum, myRoomNumChildBeans.get(i).roomId + "", myRoomNumChildBeans.get(i).identifying));
                    }
                    adapter4 = new MyListAdapter(act, data4, R.layout.scsl_list_item);
                    lv04.setAdapter(adapter4);
                    bdName3 = myFloorChildBeans.get(position).floor + "层";
                }
            }
        });

        lv04.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter4.setSelected(position);
                try {
                    if (ListUtils.listEmpty(myRoomNumChildBeans)) {
                        bdName4 = myRoomNumChildBeans.get(position).roomNum;
                        bdRoomId = myRoomNumChildBeans.get(position).roomId;
                        if (userRole.equals("0") || userRole.equals("3")) {
                            // mJSFragment.refreshHtml(bdRoomId);
                        } else if (userRole.equals("1")) {
                            //mSGFragment.refreshHtml(bdRoomId);
                        } else if (userRole.equals("2")) {
                            //mJLFragment.refreshHtml(bdRoomId);
                        }
                    }
                    roomName = myRoomNumChildBeans.get(position).roomNum;
                    tv_local.setText(bdNameTower + bdNameUnit + "-" + bdName4);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                AnimationUtil.getInstance().animateClose(rlBackClick);
                AnimationUtil.getInstance().animationIvClose(iv01);
            }
        });

        scsLjCxAdapter.setOnItemSonClickListener(new SCSLjCxAdapter.OnItemClickListener() {
            @Override
            public void selectParentItem(String parentName, String parentId) {
                tvDescTitle.setText(parentName);
                inspectionName = parentName;
                inspectionParentId = parentId;
                if (userRole.equals("0") || userRole.equals("3")) {
                    mJSFragment.setInspectionData(inspectionParentId, parentName);
                } else if (userRole.equals("1")) {
                    mSGFragment.setInspectionData(inspectionParentId, parentName);
                } else if (userRole.equals("2")) {
                    mJLFragment.setInspectionData(inspectionParentId, parentName);
                }
                bdDesc1 = inspectionName;
            }

            @Override
            public void selectChildItem(String childName, String childId, String partsDivision) {
                tvDescContent.setText(childName);
                inspectionSunName = childName;
                inspectionId = childId;
                scslType = partsDivision;
                bdDesc2 = inspectionSunName;
                getJCXDetail(inspectionId);
                if (userRole.equals("0") || userRole.equals("3")) {
                    if (mJSFragment.isDoH5()) {
                        mJSFragment.loadJSMethod(childId);
                    } else {
                        mJSFragment.refreshHtml(inspectionId);
                    }
                    mJSFragment.setInspectionSunData(inspectionId,childName, partsDivision);
                } else if (userRole.equals("1")) {
                    if (mSGFragment.isDoH5()) {
                        mSGFragment.loadJSMethod(childId);
                    } else {
                        mSGFragment.refreshHtml(inspectionId);
                    }
                    mSGFragment.setInspectionSunData(inspectionId,childName, partsDivision);
                } else if (userRole.equals("2")) {
                    if (mJLFragment.isDoH5()) {
                        mJLFragment.loadJSMethod(childId);
                    } else {
                        mJLFragment.refreshHtml(inspectionId);
                    }
                    mJLFragment.setInspectionSunData(inspectionId,childName, partsDivision);
                }
                if (rlBackClick.getVisibility() == View.VISIBLE) {
                    AnimationUtil.getInstance().animateClose(rlBackClick);
                    AnimationUtil.getInstance().animationIvClose(iv02);
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        getSCSL_Bd();
    }

    //旧字段 地块id-pkId  新字段 地块id-dikuaiId
    private void getSCSL_Bd() {
        HttpRequest.get(getContext()).url(ServerInterface.selectRoomIdSection).params("dikuaiId", Constant.projectId)
                .doGet(new HttpObjectCallBack<SCSLldcBean>(SCSLldcBean.class) {
                    @Override
                    public void onSuccess(BaseBean<SCSLldcBean> result) {
                        try {
                            if (ListUtils.listEmpty(result.data.data)) {
                                dataBeans = result.data.data;
                                if (ListUtils.listEmpty(dataBeans)) {
                                    tv_bdName.setText(dataBeans.get(0).sectionName);
                                    bdName1 = dataBeans.get(0).sectionName;
                                    sectionId = dataBeans.get(0).sectionId + "";
                                    LogUtils.i("默认sectionId==", sectionId);
                                    if (userRole.equals("0") || userRole.equals("3")) {
                                        mJSFragment.setSectionData(sectionId, dataBeans.get(0).sectionName);
                                    } else if (userRole.equals("1")) {
                                        mSGFragment.setSectionData(sectionId, dataBeans.get(0).sectionName);
                                    } else if (userRole.equals("2")) {
                                        mJLFragment.setSectionData(sectionId, dataBeans.get(0).sectionName);
                                    }
                                    //设置第一列
                                    for (int i = 0; i < dataBeans.size(); i++) {
                                        data1.add(new ItemBean(dataBeans.get(i).sectionName, dataBeans.get(i).sectionId + "", ""));
                                    }
                                    adapter1 = new MyListAdapter(act, data1, R.layout.scsl_list_item);
                                    lv01.setAdapter(adapter1);
                                    adapter1.notifyDataSetChanged();
                                    //设置第二列
                                    List<SCSLldcBean.DataBean.ScopeChildBean> scopeChildBeans = dataBeans.get(0).scopeChild;
                                    if (ListUtils.listEmpty(scopeChildBeans)) {
                                        myScopeChildBeans = scopeChildBeans;
                                        List<SCSLldcBean.DataBean.ScopeChildBean.UnitChildBean> unitChildBeans = dataBeans.get(0).scopeChild.get(0).unitChild;
                                        if (ListUtils.listEmpty(unitChildBeans)) {
                                            myUnitChildBeans = unitChildBeans;
                                            bdNameTower = scopeChildBeans.get(0).tower;
                                            bdNameUnit = unitChildBeans.get(0).unit + "单元";
                                            towerId = scopeChildBeans.get(0).towerId + "";
                                            unitId = unitChildBeans.get(0).unitId + "";
                                            towerName = bdNameTower;
                                            unitName = bdNameUnit;
                                            if (userRole.equals("0") || userRole.equals("3")) {
                                                mJSFragment.setTowerUnitData(towerId, bdNameTower, unitId, bdNameUnit);
                                            } else if (userRole.equals("1")) {
                                                mSGFragment.setTowerUnitData(towerId, bdNameTower, unitId, bdNameUnit);
                                            } else if (userRole.equals("2")) {
                                                mJLFragment.setTowerUnitData(towerId, bdNameTower, unitId, bdNameUnit);
                                            }
                                            for (int i = 0; i < scopeChildBeans.size(); i++) {
                                                for (int j = 0; j < unitChildBeans.size(); j++) {
                                                    data2.add(new ItemBean(scopeChildBeans.get(i).tower + "-" + unitChildBeans.get(j).unit + "单元", scopeChildBeans.get(i).towerId + "", unitChildBeans.get(j).unitId + ""));
                                                }
                                            }
                                            adapter2 = new MyListAdapter(act, data2, R.layout.scsl_list_item);
                                            lv02.setAdapter(adapter2);
                                            adapter2.notifyDataSetChanged();
                                        }
                                    }
                                    //设置第三列
                                    List<SCSLldcBean.DataBean.ScopeChildBean.UnitChildBean.FloorChildBean> floorChildBeans = dataBeans.get(0).scopeChild.get(0).unitChild.get(0).floorChild;
                                    if (ListUtils.listEmpty(floorChildBeans)) {
                                        myFloorChildBeans = floorChildBeans;
                                        bdName3 = floorChildBeans.get(0).floor + "层";
                                        for (int i = 0; i < floorChildBeans.size(); i++) {
                                            data3.add(new ItemBean(floorChildBeans.get(i).floor + "层", floorChildBeans.get(i).floorId + "", ""));
                                        }
                                        adapter3 = new MyListAdapter(act, data3, R.layout.scsl_list_item);
                                        lv03.setAdapter(adapter3);
                                        adapter3.notifyDataSetChanged();
                                    }
                                    //设置第四列
                                    List<SCSLldcBean.DataBean.ScopeChildBean.UnitChildBean.FloorChildBean.RoomNumChildBean> roomNumChildBeans = dataBeans.get(0).scopeChild.get(0).unitChild.get(0).floorChild.get(0).roomNumChild;
                                    if (ListUtils.listEmpty(roomNumChildBeans)) {
                                        bdName4 = roomNumChildBeans.get(0).roomNum;
                                        bdRoomId = roomNumChildBeans.get(0).roomId;
                                        roomName = bdName4;
                                        myRoomNumChildBeans = roomNumChildBeans;
                                        for (int i = 0; i < roomNumChildBeans.size(); i++) {
                                            data4.add(new ItemBean(roomNumChildBeans.get(i).roomNum, roomNumChildBeans.get(i).roomId + "", roomNumChildBeans.get(i).identifying));
                                        }
                                        adapter4 = new MyListAdapter(act, data4, R.layout.scsl_list_item);
                                        lv04.setAdapter(adapter4);
                                        adapter4.notifyDataSetChanged();
                                    }
                                    tv_local.setText(bdNameTower + bdNameUnit + "-" + bdName4);
                                }
                            }
                            getScsljCx(sectionId);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(act, "错误：" + msg);
                    }
                });
    }

    private void getScsljCx(String sectionId) {
        HttpRequest.get(getContext()).url(ServerInterface.selectSCSL)
                .params("sectionId", sectionId)
                .params("type", "SCSL")
                .doGet(new HttpObjectCallBack<SCSLjCxBean>(SCSLjCxBean.class) {
                    @Override
                    public void onSuccess(BaseBean<SCSLjCxBean> result) {
                        if (result.code.equals("0")) {
                            if (ListUtils.listEmpty(result.data.list)) {
                                List<SCSLjCxBean.ListBean> listBeans = result.data.list;
                                try {
                                    inspectionName = listBeans.get(0).inspectionName;
                                    inspectionSunName = listBeans.get(0).child.get(0).inspectionName;
                                    tvDescTitle.setText(listBeans.get(0).inspectionName);
                                    tvDescContent.setText(listBeans.get(0).child.get(0).inspectionName);
                                    inspectionId = listBeans.get(0).child.get(0).inspectionId + "";
                                    if (userRole.equals("0") || userRole.equals("3")) {
                                        mJSFragment.setInspectionData(listBeans.get(0).inspectionId + "", inspectionName);
                                        mJSFragment.setInspectionSunData(inspectionId,inspectionSunName, listBeans.get(0).child.get(0).partsDivision);
                                        mJSFragment.setDetailsName(inspectionName + inspectionSunName);
                                    } else if (userRole.equals("1")) {
                                        mSGFragment.setInspectionData(listBeans.get(0).inspectionId + "", inspectionName);
                                        mSGFragment.setInspectionSunData(inspectionId,inspectionSunName, listBeans.get(0).child.get(0).partsDivision);
                                        mSGFragment.setDetailsName(inspectionName + inspectionSunName);
                                    } else if (userRole.equals("2")) {
                                        mJLFragment.setInspectionData(listBeans.get(0).inspectionId + "", inspectionName);
                                        mJLFragment.setInspectionSunData(inspectionId,inspectionSunName, listBeans.get(0).child.get(0).partsDivision);
                                        mJLFragment.setDetailsName(inspectionName + inspectionSunName);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                list = generateData(listBeans);
                                scsLjCxAdapter.setNewData(list);
                                getJCXDetail(inspectionId);
                            } else {
                                scsLjCxAdapter.setEmptyView(emptyView);
                            }
                        } else {
                            scsLjCxAdapter.setEmptyView(emptyView);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(act, "错误：" + msg);
                    }
                });
    }

    private void getJCXDetail(String inspectionId) {
        HttpRequest.get(this).url(ServerInterface.selectSclsIssueGuidelines).params("inspectionId", inspectionId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONObject checkGuide = object.optJSONObject("list");
                        if (checkGuide != null) {
                            checkGuideContent = checkGuide.optString("checkGuide");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    //, R.id.ll_bd
    @OnClick({R.id.rl_back, R.id.iv_close, R.id.iv_right_icon, R.id.ll_bd_desc, R.id.rl_backClick})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                ActivityManager.getInstance().clearAll();
                jumpToActivity(MainActivity.class);
                break;
            case R.id.iv_right_icon:
                popWindowUtils.showJCzy(act, ivRightIcon, -50, 2, inspectionName + "-" + inspectionSunName, checkGuideContent);
                break;
//            case R.id.ll_bd:
//                llFour.setVisibility(View.VISIBLE);
//                llTwo.setVisibility(View.GONE);
//                if (rlBackClick.getVisibility() == View.GONE) {
//                    AnimationUtil.getInstance().animateOpen(rlBackClick, mHiddenViewBackHeight);
//                    AnimationUtil.getInstance().animationIvOpen(iv01);
//                } else {
//                    AnimationUtil.getInstance().animateClose(rlBackClick);
//                    AnimationUtil.getInstance().animationIvClose(iv01);
//                }
//                break;
            case R.id.ll_bd_desc:
                llFour.setVisibility(View.GONE);
                llTwo.setVisibility(View.VISIBLE);
                if (rlBackClick.getVisibility() == View.GONE) {
                    scsLjCxAdapter.setSelectedName(inspectionSunName);
                    AnimationUtil.getInstance().animateOpen(rlBackClick, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv02);
                } else {
                    AnimationUtil.getInstance().animateClose(rlBackClick);
                    AnimationUtil.getInstance().animationIvClose(iv02);
                }
                break;
            case R.id.rl_backClick:
                AnimationUtil.getInstance().animateClose(rlBackClick);
                AnimationUtil.getInstance().animationIvClose(iv01);
                break;
            default:
                break;
        }
    }

    private ArrayList<MultiItemEntity> generateData(List<SCSLjCxBean.ListBean> dataList) {
        ArrayList<MultiItemEntity> als = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            SCSLjCxLevel1 level1 = new SCSLjCxLevel1(dataList.get(i).sectionId + "", dataList.get(i).inspectionId, dataList.get(i).inspectionName, dataList.get(i).inspectionParentId);
            if (listEmpty(dataList.get(i).child)) {
                for (int j = 0; j < dataList.get(i).child.size(); j++) {
                    level1.addSubItem(new SCSLjCxLevel2(dataList.get(i).child.get(j).inspectionId, dataList.get(i).child.get(j).inspectionName, dataList.get(i).child.get(j).inspectionParentId, dataList.get(i).child.get(j).partsDivision));
                }
            }
            als.add(level1);
        }
        return als;
    }

    private void initFragmentHeader(int count) {
        if (count > 0) {
            if (count == 1) {
                if ("1".equals(userRole) && "1".equals(shiGongStatus)) {
                    llWindow.setVisibility(View.GONE);
                } else {
                    llWindow.setVisibility(View.VISIBLE);
                }
            } else if (count == 2) {
                if ("2".equals(userRole) && "2".equals(jianLiStatus)) {
                    llWindow.setVisibility(View.GONE);
                } else {
                    llWindow.setVisibility(View.VISIBLE);
                }
            } else if (count == 3) {
                if (("3".equals(userRole) || "0".equals(userRole)) && "3".equals(jianSheStatus)) {
                    llWindow.setVisibility(View.GONE);
                } else {
                    llWindow.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
