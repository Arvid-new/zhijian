package com.haozhiyan.zhijian.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials;
import com.haozhiyan.zhijian.activity.gxyj.GXYJProblemDetails;
import com.haozhiyan.zhijian.activity.gxyj.ProcessOver;
import com.haozhiyan.zhijian.adapter.DaiBanTypeDrawerOptionsAdapter;
import com.haozhiyan.zhijian.adapter.DaiBanTypeDrawerconditionAdapter;
import com.haozhiyan.zhijian.adapter.DaiBanTypeListAdapter;
import com.haozhiyan.zhijian.adapter.SCSLDBAdapter;
import com.haozhiyan.zhijian.adapter.ViewPagerAdapter;
import com.haozhiyan.zhijian.bean.DaiBanCLYS1;
import com.haozhiyan.zhijian.bean.DaiBanDrawerCheckBean;
import com.haozhiyan.zhijian.bean.DaiBanDrawerProject;
import com.haozhiyan.zhijian.bean.DaiBanDrawerSection;
import com.haozhiyan.zhijian.bean.DaiBanGXYJListBean;
import com.haozhiyan.zhijian.bean.DaiBanTypeListBean2;
import com.haozhiyan.zhijian.bean.scsl.SCSLDBBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.widget.CommonPopupWindow;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DaiBanTypeVPActivity extends BaseActivity2 implements DaiBanTypeDrawerOptionsAdapter.PersonItemClick, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private SwipeRefreshLayout fresh;
    private String[] titles = new String[]{"待办", "已办", "抄送"};
    private List<View> views;
    private DaiBanTypeListAdapter daibanAdapter;
    private DaiBanTypeListAdapter yibanAdapter;
    private DaiBanTypeListAdapter chaosAdapter;
    private String type;
    private String sectionIds;
    private int dateTimeView = 0;
    private boolean isInit = false;
    private CommonPopupWindow popupWindow;
    private DrawerLayout mDrawerLayout; // DrawerLayout组件
    private RecyclerView conditionRcv;
    private RecyclerView optionRcv;
    private TextView drawerCancel;
    private TextView drawerSure;
    private LinearLayout regisTimeLL;
    private LinearLayout selectDateLL1;
    private LinearLayout selectDateLL2;
    private TextView drawerDateTv1;
    private TextView drawerDateTv2;
    private TextView drawerDateWeekTv1;
    private TextView drawerDateWeekTv2;
    private TextView select7Date;
    private TextView select30Date;
    private TextView drawerDateWeekTipTv1;
    private TextView drawerDateWeekTipTv2;
    private ImageView checkimg;
    private LinearLayout selectNoDateLL;
    private String startTime = "", timeDays = "";
    private String doState = "1", identifying = "1", sectionId = "", inspectionName = "", creatorTime = "";
    private String beginTime = "", endTime = "";
    private SCSLDBAdapter scsldbAdapter, scslybAdapter, scslcsAdapter;

    @Override
    protected void init(Bundle savedInstanceState) {
        type = getIntent().getBundleExtra("data").getString("type", "");
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_dai_ban_type_vp;
    }

    @Override
    protected int getTitleBarType() {
        if (this.type.equals("0")) {
            return TITLEBAR_DEFAULT;
        } else {
            return TITLEBAR_SEARCH;
        }
    }

    @Override
    protected void initView() {
        String name = getIntent().getBundleExtra("data").getString("name");
        setTitleText("待办-" + name);
        setAndroidNativeLightStatusBar(true);
        LogUtils.logGGQ("title：" + name);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        fresh = findViewById(R.id.fresh);
        mDrawerLayout = findViewById(R.id.mDrawerLayout);
        conditionRcv = findViewById(R.id.conditionRcv);
        optionRcv = findViewById(R.id.optionRcv);
        drawerCancel = findViewById(R.id.drawerCancel);
        drawerSure = findViewById(R.id.drawerSure);
        regisTimeLL = findViewById(R.id.regisTimeLL);

        setTitleRightmenu();

        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getListData();
                fresh.setRefreshing(false);
            }
        });
        popupWindow = new CommonPopupWindow(getContext(), tabLayout, R.layout.mytestpoplayout);
        popupWindow.getContentView().findViewById(R.id.mr).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.tower_floor_sort).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.time_sortZ).setOnClickListener(this);
        popupWindow.getContentView().findViewById(R.id.time_sortD).setOnClickListener(this);
        setDrawerDateView();
    }

    /**
     * 排序 点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mr:
                if (type.equals("1")) {//实测实量

                } else if (this.type.equals("2")) {//工序移交
                    listBacklog(doState);
                } else if (this.type.equals("3")) {//材料验收
                    doClysList(doState);//排序 点击事件
                } else {
                    doProblemList(doState, "", "", "");//默认排序
                }
                popupWindow.closeMenu();
                break;
            case R.id.tower_floor_sort:
                if (type.equals("1")) {//实测实量

                } else if (this.type.equals("2")) {//工序移交
                    listBacklog(doState);
                } else if (this.type.equals("3")) {//材料验收
                    doClysList(doState);//排序 点击事件
                } else {
                    doProblemList(doState, "", "", "");//楼层排序
                }
                popupWindow.closeMenu();
                break;
            case R.id.time_sortZ:
                if (type.equals("1")) {

                } else if (this.type.equals("2")) {//工序移交
                    listBacklog(doState);
                } else if (this.type.equals("3")) {//材料验收
                    doClysList(doState);//排序 点击事件
                } else {
                    doProblemList(doState, "", "", "2");//时间排序
                }
                popupWindow.closeMenu();
                break;
            case R.id.time_sortD:
                if (type.equals("1")) {//实测实量

                } else if (this.type.equals("2")) {//工序移交
                    listBacklog(doState);
                } else if (this.type.equals("3")) {//材料验收
                    doClysList(doState);////排序 点击事件
                } else {
                    doProblemList(doState, "", "", "3");//时间排序
                }
                popupWindow.closeMenu();
                break;
            default:
                break;
        }
    }

    /**
     * 初始化 tap 栏
     */
    private void initTablayout() {
        pagerAdapter = new ViewPagerAdapter(views, getContext());
        pagerAdapter.setTitles(titles);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    fresh.setEnabled(false);
                } else {
                    fresh.setEnabled(true);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setDoState(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    protected void initData() {
        views = new ArrayList<>();
        getListData();
        setDrawerLayoutView();
    }

    private void getListData() {
        if (this.type.equals("0")) {//现场检查
            doProblemCount();
            doProblemList("1", "", "", "");//初始加载
        } else if (this.type.equals("1")) {//实测实量
            listAbarbeitungBacklog("1", "", "", "");//待办
            listAbarbeitungBacklog("2", "", "", "");//已办
            listAbarbeitungBacklog("3", "", "", "");//抄送
            //实测实量(筛选查询-检查项)
            listScslFiltrate();
        } else if (this.type.equals("2")) {//工序移交
            listBacklog("1");
        } else if (this.type.equals("3")) {//材料验收
            doClysList(doState);////初始加载
        } else {
        }
    }


    private void setDoState(int position) {
        if (position == 0) {
            doState = "1";
            identifying = "1";
        } else if (position == 1) {
            doState = "2";
            identifying = "2";
        } else if (position == 2) {
            doState = "3";
            identifying = "3";
        }
        if (this.type.equals("1")) {//实测实量
            listAbarbeitungBacklog(identifying, sectionId, inspectionName, timeDays);
        } else if (this.type.equals("2")) {//工序移交
            listBacklog(doState);
        } else if (this.type.equals("3")) {//材料验收
            doClysList(doState);// vp  切换
        } else {
            doProblemList(doState, "", "", "");// vp 切换加载
        }
    }

    /**
     * 设置titlebar 右侧菜单
     */
    private void setTitleRightmenu() {
        int dp10px = DensityUtil.dip2px(getContext(), 6);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        ImageView screen1 = new ImageView(getContext());
        screen1.setImageResource(R.mipmap.screen_icon1);
        screen1.setLayoutParams(layoutParams);
        screen1.setPadding(dp10px, 0, dp10px, 0);
        ImageView screen2 = new ImageView(getContext());
        screen2.setPadding(dp10px, 0, dp10px, 0);
        screen2.setLayoutParams(layoutParams);

        getBarRightView().removeAllViews();
        if (this.type.equals("0")) {
            screen2.setImageResource(R.mipmap.screen_icon2);
            screen2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.show();
                    mDrawerLayout.closeDrawers();
                }
            });
            getBarRightView().addView(screen1, 0);
            getBarRightView().addView(screen2, 1);
        } else {
            screen2.setImageResource(R.mipmap.dark_search);
            screen2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSearchBar();
                    mDrawerLayout.closeDrawers();
                }
            });
            findViewById(R.id._barCancelSearchView).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSearchBar();
                }
            });
            getBarRightView().addView(screen1, 0);
//            getBarRightView().addView(screen2, 1);
        }

        screen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(Gravity.END);
                popupWindow.closeMenu();
            }
        });
    }

    /**
     * titlebar  editview 监听
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        LogUtils.d(TAG, s.toString());
    }

    @Override
    public void onMessageEvent(Object event) {
        if (this.type.equals("0")) {//现场检查
            if ("changedXCJCTask".equals(event.toString())) {
                doProblemCount();
                doProblemList("1", "", "", "");//详情中任务被更改状态后重新请求列表
            }
        } else if (this.type.equals("1")) {//实测实量
        } else if (this.type.equals("2")) {//工序移交
            if ("deleteGXYJTask".equals(event.toString())) {
                listBacklog(doState);//任务被删除后重新请求列表
            } else if ("GXYJTaskStateChanged".equals(event.toString())) {
                listBacklog(doState);//任务被删除后重新请求列表
            } else if ("changedGXYJTask".equals(event.toString())) {
                listBacklog(doState);//任务被删除后重新请求列表
            }
        } else if (this.type.equals("3")) {//材料验收
            if (event.toString().equals("clysTaskDelete")) {
                doClysList(doState);//任务被删除后重新请求列表
            } else if ("clysTaskStateChanged".equals(event.toString())) {
                doClysList(doState);//材料验收任务状态改变后重新请求列表
            }
        } else {

        }
    }

    /**
     * 获取列表数据
     */
    private void doProblemCount() {
        HttpRequest.get(getContext())
                .url(ServerInterface.doProblemCount)
                .params("userId", UserInfo.create(getContext()).getUserId())//当前登录用户id
                .params("projectId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.logGGQ("doProblemCount onSuccess:" + result);
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONObject countMap = jsonObject.optJSONObject("countMap");
                                int doneProblemCount = countMap.optInt("doneProblemCount");
                                int todoProblemCount = countMap.optInt("todoProblemCount");
                                int ccProblemCount = countMap.optInt("ccProblemCount");
                                titles[0] = "待办(" + todoProblemCount + ")";
                                titles[1] = "已办(" + doneProblemCount + ")";
                                titles[2] = "抄送(" + ccProblemCount + ")";

                                pagerAdapter.setTitles(titles);
                                pagerAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        LogUtils.logGGQ("doProblemCount onFailure:" + msg);
                    }
                });
    }

    /**
     * 获取列表数据
     */
    private void doClysCount() {
        HttpRequest.get(getContext())
                .url(ServerInterface.doClysCount)
                .params("userId", UserInfo.create(getContext()).getUserId())//当前登录用户id
                .params("projectId", Constant.projectId)//
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("dbxcjc===", result.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONObject countMap = jsonObject.optJSONObject("countMap");
                                int doneProblemCount = countMap.optInt("doneClysCount");
                                int todoProblemCount = countMap.optInt("todoClysCount");
                                int ccProblemCount = countMap.optInt("ccClysCount");
                                titles[0] = "待办(" + todoProblemCount + ")";
                                titles[1] = "已办(" + doneProblemCount + ")";
                                titles[2] = "抄送(" + ccProblemCount + ")";
                                if (pagerAdapter != null) {
                                    pagerAdapter.setTitles(titles);
                                    pagerAdapter.notifyDataSetChanged();
                                }

                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
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

    /**
     * 获取列表数据
     */
    private void doProblemList(final String doState, String startTime, String endTime, String sort) {
        HttpRequest.get(getContext())
                .url(ServerInterface.doProblemList)
                .params("userId", UserInfo.create(getContext()).getUserId())//当前登录用户id
                .params("doState", doState)//1待办，2已办，3抄送
                .params("projectId", Constant.projectId)//项目id
                .params("sectionId", "")//标段id
                .params("inspectionId", "")//检查项id
                .params("startTime", startTime)//提交时间开始
                .params("endTime", endTime)//提交时间结束
                .params("serious", "")//严重程度(1一般，2重要，3紧急)
                .params("state", "")//状态(1待整改，2待复验，3非正常关闭，4已通过)
                .params("sort", sort)//排序(1楼栋楼层,2提交时间正序,3提交时间倒序)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.print("dbxcjcList===" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                setData(doState, result.toString());
                            } else {
                                Toast.makeText(DaiBanTypeVPActivity.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
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

    //查询实测实量待办列表
    private void listAbarbeitungBacklog(final String identifying, String sectionId, String inspectionName, String creatorTime) {
        HttpRequest.get(this).url(ServerInterface.listAbarbeitungBacklog)
                .params("identifying", identifying)
                .params("dikuaiId", Constant.projectId)//地块id
                .params("sectionId", sectionId)//标段id
                .params("inspectionName", inspectionName)//检查项名称
                .params("creatorTime", creatorTime)//时间
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            SCSLDBBean scsldbBean = new Gson().fromJson(result.toString(), SCSLDBBean.class);
                            if (scsldbBean.code == 0) {
                                List<SCSLDBBean.ListBean.ListResultBean> bean = scsldbBean.list.listResult;
                                if (identifying.equals("1")) {
                                    setSCSLData(identifying, bean);
                                    LogUtils.print("scsl-daiban-bean===" + scsldbBean.list.listResult.toString());
                                } else if (identifying.equals("2")) {
                                    setSCSLData(identifying, bean);
                                    LogUtils.print("scsl-yiban-bean===" + scsldbBean.list.listResult.toString());
                                } else if (identifying.equals("3")) {
                                    setSCSLData(identifying, bean);
                                    LogUtils.print("scsl-chaoSong-bean===" + scsldbBean.list.listResult.toString());
                                }
                            } else {
                                //ToastUtils.myToast(DaiBanTypeVPActivity.this, response.body().msg);
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

    private String projectId;

    /**
     * 工序移交 获取列表数据
     */
    private void listBacklog(final String doState) {
        HttpRequest.get(getContext())
                .url(ServerInterface.listBacklog)
                .params("identifying", doState)//1待办，2已办，3抄送
                .params("projectId", projectId)//项目id
                .params("dikuaiId", Constant.projectId)//地块ID
                .params("inspectionName", inspectionName)//检查项名字
                .params("beginTime", beginTime)//时间
                .params("creatorTime", endTime)//时间
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("dbxcjcList===", result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                setData(doState, result.toString());
                            } else {
                                Toast.makeText(DaiBanTypeVPActivity.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
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

    /**
     * 材料验收  获取列表数据
     *
     * @param doState
     */
    private void doClysList(final String doState) {
        doClysCount();
        HttpRequest.get(getContext())
                .url(ServerInterface.doClysList)
                .params("userId", UserInfo.create(getContext()).getUserId())
                .params("doState", doState)
                .params("projectId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                setData(doState, result.toString());
                            } else {
                                Toast.makeText(DaiBanTypeVPActivity.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
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

    private void setSCSLData(String identifying, List<SCSLDBBean.ListBean.ListResultBean> bean) {
        views.clear();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (scsldbAdapter == null) {
            scsldbAdapter = new SCSLDBAdapter(null, this);
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(scsldbAdapter);
            views.add(recyclerView);
        }
        if (scslybAdapter == null) {
            scslybAdapter = new SCSLDBAdapter(null, this);
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(scslybAdapter);
            views.add(recyclerView);
        }
        if (scslcsAdapter == null) {
            scslcsAdapter = new SCSLDBAdapter(null, this);
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(scslcsAdapter);
            views.add(recyclerView);
        }
        if (!isInit) {
            isInit = true;
            initTablayout();
        }
        setDataBySCSL(identifying, bean);
    }

    private void setDataBySCSL(String identifying, final List<SCSLDBBean.ListBean.ListResultBean> list) {
        if (identifying.equals("1")) {
            if (ListUtils.listEmpty(list)) {
                scsldbAdapter.setNewData(list);
                titles[0] = "待办(" + list.size() + ")";
                scsldbAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        LogUtils.print("floorId==" + list.get(position).handOverPart);
                        ItemClick("0", list.get(position).scslState, list.get(position).scslId + "", "", list.get(position).towerName + list.get(position).unitName + list.get(position).handOverPart, list.get(position).handOverPart, list.get(position));
                    }
                });
            } else {
                scsldbAdapter.setEmptyView(emptyView);
                titles[0] = "待办(0)";
            }
        } else if (identifying.equals("2")) {
            if (ListUtils.listEmpty(list)) {
                scslybAdapter.setNewData(list);
                titles[1] = "已办(" + list.size() + ")";
                scslybAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        LogUtils.print("floorId==" + list.get(position).handOverPart);
                        ItemClick("1", list.get(position).scslState, list.get(position).scslId + "", "", list.get(position).towerName + list.get(position).unitName + list.get(position).handOverPart, list.get(position).handOverPart, list.get(position));
                    }
                });
            } else {
                titles[1] = "已办(0)";
            }
        } else if (identifying.equals("3")) {
            if (ListUtils.listEmpty(list)) {
                scslcsAdapter.setNewData(list);
                titles[2] = "抄送(" + list.size() + ")";
                scslcsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        ItemClick("2", list.get(position).scslState, list.get(position).scslId + "", "", list.get(position).towerName + list.get(position).unitName + list.get(position).handOverPart, list.get(position).handOverPart, list.get(position));
                    }
                });
            } else {
                titles[2] = "抄送(0)";
            }
        }
        pagerAdapter.changeTitle(titles);
    }

    private void setData(String doState, String result) {
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (daibanAdapter == null) {
            daibanAdapter = new DaiBanTypeListAdapter(getContext());
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(daibanAdapter);
            views.add(recyclerView);
        }
        if (yibanAdapter == null) {
            yibanAdapter = new DaiBanTypeListAdapter(getContext());
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(yibanAdapter);
            views.add(recyclerView);
            yibanAdapter.addFooterView(getFootView());
        }
        if (chaosAdapter == null) {
            chaosAdapter = new DaiBanTypeListAdapter(getContext());
            RecyclerView recyclerView = new RecyclerView(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setLayoutParams(layoutParams);
            recyclerView.setAdapter(chaosAdapter);
            views.add(recyclerView);
            chaosAdapter.addFooterView(getFootView());
        }
        if (!isInit) {
            isInit = true;
            initTablayout();
        }
        if (doState.equals("1")) {
            setDaiBanRCV(result);
        } else if (doState.equals("2")) {
            setYiBanRcv(result);
        } else if (doState.equals("3")) {
            setChaoSonRcv(result);
        }
    }

    /**
     * 待办的 RCV 列表
     */
    private void setDaiBanRCV(String json) {
        LogUtils.logGGQ("待办列表json：" + json);
        if (this.type.equals("0")) {//现场检查
            final DaiBanTypeListBean2 bean = new Gson().fromJson(json, DaiBanTypeListBean2.class);
            List list = bean.doProblemList;
            daibanAdapter.setNewData(list);
            daibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("0", bean.doProblemList.get(position).state, bean.doProblemList.get(position).id + "", ""
                            , bean.doProblemList.get(position).inspectionName, "", null);
                }
            });
        } else if (this.type.equals("2")) {//工序移交
            try {
                DaiBanGXYJListBean bean = new Gson().fromJson(json, DaiBanGXYJListBean.class);
                titles[0] = "待办(" + (bean.getList().getDaibanCount()) + ")";
                titles[1] = "已办(" + (bean.getList().getYibanCount()) + ")";
                titles[2] = "抄送(" + (bean.getList().getChaosongCount()) + ")";
                List list = bean.getList().getListDaiZhenggai();
                if (bean.getList().getListDaiban().size() > 0)
                    list.addAll(bean.getList().getListDaiban());
                daibanAdapter.setNewData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
            daibanAdapter.notifyDataSetChanged();
            pagerAdapter.setTitles(titles);
            pagerAdapter.notifyDataSetChanged();
            daibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (adapter.getData().get(position) instanceof DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) {
                        DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean daibanBean = (DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) adapter.getData().get(position);
                        ItemClick("0", daibanBean.getZtCondition(), daibanBean.getId() + "", "", daibanBean.getInspectionName(), "", null);
                    } else if (adapter.getData().get(position) instanceof DaiBanGXYJListBean.ListBean.ListDaibanBean) {
                        DaiBanGXYJListBean.ListBean.ListDaibanBean daibanBean = (DaiBanGXYJListBean.ListBean.ListDaibanBean) adapter.getData().get(position);
                        ItemClick("0", daibanBean.getIdentifying(),
                                daibanBean.getId() + "", daibanBean.getInspectionId(), daibanBean.getDetailsName(), daibanBean.getDetailsName(), null);
                    }
                }
            });
        } else if (this.type.equals("3")) {//材料验收
            final DaiBanCLYS1 bean = new Gson().fromJson(json, DaiBanCLYS1.class);
            List list = bean.getDoClysList();
            daibanAdapter.setNewData(list);
            daibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("0", bean.getDoClysList().get(position).getState(),
                            bean.getDoClysList().get(position).getId() + "",
                            bean.getDoClysList().get(position).getCheckResult(),
                            bean.getDoClysList().get(position).getSupervisorResult(),
                            bean.getDoClysList().get(position).getSectionName()
                                    + bean.getDoClysList().get(position).getNameInspection()
                                    + "进场"
                                    + bean.getDoClysList().get(position).getNumber(),
                            null);
                }
            });
        } else {//
            final DaiBanTypeListBean2 bean = new Gson().fromJson(json, DaiBanTypeListBean2.class);
            List list = bean.doProblemList;
            daibanAdapter.setNewData(list);
            daibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("0", bean.doProblemList.get(position).state,
                            bean.doProblemList.get(position).id + "", "", bean.doProblemList.get(position).inspectionName, "", null);
                }
            });
        }
    }

    /**
     * 已办的RCV列表
     */
    private void setYiBanRcv(String json) {
        if (this.type.equals("0")) {//现场检查
            final DaiBanTypeListBean2 bean = new Gson().fromJson(json, DaiBanTypeListBean2.class);
            List list = bean.doProblemList;
            yibanAdapter.setNewData(list);
            yibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("1", bean.doProblemList.get(position).state,
                            bean.doProblemList.get(position).id + "", "", bean.doProblemList.get(position).inspectionName, "", null);
                }
            });
        } else if (this.type.equals("2")) {//工序移交
            try {
                DaiBanGXYJListBean bean = new Gson().fromJson(json, DaiBanGXYJListBean.class);
                titles[0] = "待办(" + (bean.getList().getDaibanCount()) + ")";
                titles[1] = "已办(" + (bean.getList().getYibanCount()) + ")";
                titles[2] = "抄送(" + (bean.getList().getChaosongCount()) + ")";
                List list = bean.getList().getListDaiZhenggai();
                if (bean.getList().getListDaiban().size() > 0)
                    list.addAll(bean.getList().getListDaiban());
                yibanAdapter.setNewData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
            yibanAdapter.notifyDataSetChanged();
            pagerAdapter.setTitles(titles);
            pagerAdapter.notifyDataSetChanged();
            yibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (adapter.getData().get(position) instanceof DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) {
                        DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean daibanBean = (DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) adapter.getData().get(position);
                        ItemClick("1", daibanBean.getZtCondition(), daibanBean.getId() + "", "", daibanBean.getInspectionName(), "", null);
                    } else if (adapter.getData().get(position) instanceof DaiBanGXYJListBean.ListBean.ListDaibanBean) {
                        DaiBanGXYJListBean.ListBean.ListDaibanBean daibanBean = (DaiBanGXYJListBean.ListBean.ListDaibanBean) adapter.getData().get(position);
                        ItemClick("1", daibanBean.getIdentifying(),
                                daibanBean.getId() + "", daibanBean.getInspectionId(), daibanBean.getDetailsName(), daibanBean.getDetailsName(), null);
                    }
                }
            });
        } else if (this.type.equals("3")) {//
            final DaiBanCLYS1 bean = new Gson().fromJson(json, DaiBanCLYS1.class);
            List list = bean.getDoClysList();
            yibanAdapter.setNewData(list);
            yibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("1", bean.getDoClysList().get(position).getState(),
                            bean.getDoClysList().get(position).getId() + "",
                            bean.getDoClysList().get(position).getCheckResult(),
                            bean.getDoClysList().get(position).getSupervisorResult(),
                            bean.getDoClysList().get(position).getSectionName()
                                    + bean.getDoClysList().get(position).getNameInspection()
                                    + "进场"
                                    + bean.getDoClysList().get(position).getNumber(),
                            null);
                }
            });
        } else {//
            final DaiBanTypeListBean2 bean = new Gson().fromJson(json, DaiBanTypeListBean2.class);
            List list = bean.doProblemList;
            yibanAdapter.setNewData(list);
            yibanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("1", bean.doProblemList.get(position).state,
                            bean.doProblemList.get(position).id + "", "", bean.doProblemList.get(position).inspectionName, "", null);
                }
            });
        }
    }

    /**
     * 抄送的RCV列表
     */
    private void setChaoSonRcv(String json) {

        if (this.type.equals("0")) {//现场检查
            final DaiBanTypeListBean2 bean = new Gson().fromJson(json, DaiBanTypeListBean2.class);
            final List list = bean.doProblemList;
            chaosAdapter.setNewData(list);
            chaosAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("2", bean.doProblemList.get(position).state,
                            bean.doProblemList.get(position).id + "", "", bean.doProblemList.get(position).inspectionName, "", null);
                }
            });
        } else if (this.type.equals("2")) {//工序移交
            try {
                DaiBanGXYJListBean bean = new Gson().fromJson(json, DaiBanGXYJListBean.class);
                titles[0] = "待办(" + (bean.getList().getDaibanCount()) + ")";
                titles[1] = "已办(" + (bean.getList().getYibanCount()) + ")";
                titles[2] = "抄送(" + (bean.getList().getChaosongCount()) + ")";
                List list = bean.getList().getListDaiZhenggai();
                if (bean.getList().getListDaiban().size() > 0)
                    list.addAll(bean.getList().getListDaiban());
                chaosAdapter.setNewData(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
            chaosAdapter.notifyDataSetChanged();
            pagerAdapter.setTitles(titles);
            pagerAdapter.notifyDataSetChanged();
            chaosAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (adapter.getData().get(position) instanceof DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) {
                        DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean daibanBean = (DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) adapter.getData().get(position);
                        ItemClick("2", daibanBean.getZtCondition(), daibanBean.getId() + "", "", daibanBean.getInspectionName(), "", null);
                    } else if (adapter.getData().get(position) instanceof DaiBanGXYJListBean.ListBean.ListDaibanBean) {
                        DaiBanGXYJListBean.ListBean.ListDaibanBean daibanBean = (DaiBanGXYJListBean.ListBean.ListDaibanBean) adapter.getData().get(position);
                        ItemClick("2", daibanBean.getIdentifying(),
                                daibanBean.getId() + "", daibanBean.getInspectionId(), daibanBean.getInspectionName(), daibanBean.getDetailsName(), null);
                    }
                }
            });
        } else if (this.type.equals("3")) {//
            final DaiBanCLYS1 bean = new Gson().fromJson(json, DaiBanCLYS1.class);
            List list = bean.getDoClysList();
            chaosAdapter.setNewData(list);
            chaosAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("2", bean.getDoClysList().get(position).getState(),
                            bean.getDoClysList().get(position).getId() + "",
                            bean.getDoClysList().get(position).getCheckResult(),
                            bean.getDoClysList().get(position).getSupervisorResult(),
                            bean.getDoClysList().get(position).getSectionName()
                                    + bean.getDoClysList().get(position).getNameInspection()
                                    + "进场"
                                    + bean.getDoClysList().get(position).getNumber(),
                            null);
                }
            });
        } else {//
            final DaiBanTypeListBean2 bean = new Gson().fromJson(json, DaiBanTypeListBean2.class);
            List list = bean.doProblemList;
            chaosAdapter.setNewData(list);
            chaosAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ItemClick("2", bean.doProblemList.get(position).state,
                            bean.doProblemList.get(position).id + "", "", bean.doProblemList.get(position).inspectionName, "", null);
                }
            });
        }
    }

    private View getFootView() {
        TextView footView = new TextView(getContext());
        ViewGroup.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(getContext(), 50)
        );
        footView.setText("已处理完毕超过3天的问题请在PC端报表查看");
        footView.setTextColor(Color.parseColor("#949494"));
        footView.setTextSize(12);
        footView.setLayoutParams(params);
        footView.setGravity(Gravity.CENTER);
        return footView;
    }

    /**
     * 列表 item 点击统一处理
     *
     * @param type           状态分类  0待办 1已办  2抄送
     * @param state          项目状态  0待整改 1待复验  2已通过
     * @param id
     * @param inspectionId
     * @param inspectionName
     * @param listBean       实测实量对象类
     */
    private void ItemClick(String type, String state, String id, String inspectionId,
                           String inspectionName, String detailsName, SCSLDBBean.ListBean.ListResultBean listBean) {
        LogUtils.logGGQ("ItemClick type:" + type);
        Intent intent = new Intent();
        if (this.type.equals("0")) {//现场检查
            intent.setClass(getContext(), OnSiteInspectionDetails.class);
//          intent.putExtra("sectionId", sectionId);
            intent.putExtra("type", identifying);
            intent.putExtra("state", state);
        } else if (this.type.equals("1")) {//实测实量
            intent.setClass(getContext(), RealQuantityDetails.class);
            intent.putExtra("type", type);
            intent.putExtra("state", state);
            intent.putExtra("id", id);
            intent.putExtra("title", inspectionName);
            intent.putExtra("scslId", listBean.scslId + "");
            intent.putExtra("roomTowerFloorId", detailsName);
            //intent.putExtra("scSlDetail", listBean);
        } else if (this.type.equals("2")) {//工序移交
            switch (state) {
                case "待整改":
                case "待复验":
                case "非正常关闭":
                case "已通过":
                    intent.setClass(getContext(), GXYJProblemDetails.class);
                    break;
                default:
                    intent.setClass(getContext(), ProcessOver.class);
                    break;
            }
            intent.putExtra("dbId", id);
            intent.putExtra("detailsName", detailsName);
            intent.putExtra("inspectionId", inspectionId);
            intent.putExtra("inspectionName", inspectionName);
            intent.putExtra("isDaiBan", true);

        } else if (this.type.equals("3")) {//材料验收
            intent.setClass(getContext(), AcceptanceMaterials.class);
            intent.putExtra("checkResult", inspectionId);
            intent.putExtra("isdaibanin", true);
            intent.putExtra("title", detailsName);
        } else {
        }
        try {
            intent.putExtra("inspectionId", inspectionId);
            intent.putExtra("inspectionName", inspectionName);
            intent.putExtra("id", id);
            intent.putExtra("type", type);
            intent.putExtra("state", state);
            intent.putExtra("entrance", "DB");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Calendar.SELECTDATE) {
            try {
                Object date = data.getBundleExtra("bundle").getSerializable("selectCalendar");
                if (date instanceof com.haibin.calendarview.Calendar) {
                    com.haibin.calendarview.Calendar calendar = (com.haibin.calendarview.Calendar) date;
                    checkimg.setVisibility(View.INVISIBLE);
                    select7Date.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                    select7Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                    select30Date.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                    select30Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                    if (dateTimeView == 0) {
                        drawerDateTv1.setText(String.valueOf(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay()));
                        drawerDateWeekTv1.setText(getWeekString(calendar.getWeek()));
                        drawerDateTv1.setVisibility(View.VISIBLE);
                        drawerDateWeekTv1.setVisibility(View.VISIBLE);
                        drawerDateWeekTipTv1.setVisibility(View.GONE);
                    } else {
                        drawerDateTv2.setText(String.valueOf(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay()));
                        drawerDateWeekTv2.setText(getWeekString(calendar.getWeek()));
                        drawerDateTv2.setVisibility(View.VISIBLE);
                        drawerDateWeekTv2.setVisibility(View.VISIBLE);
                        drawerDateWeekTipTv2.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private String getWeekString(int week) {
        switch (week) {
            case 0:
                return "周日";
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
        }
        return "";
    }

    /**
     * 设置抽屉布局 登记时间 view
     */
    private void setDrawerDateView() {
        selectDateLL1 = findViewById(R.id.selectDateLL1);
        selectDateLL2 = findViewById(R.id.selectDateLL2);
        drawerDateTv1 = findViewById(R.id.drawerDateTv1);
        drawerDateTv2 = findViewById(R.id.drawerDateTv2);
        drawerDateWeekTv1 = findViewById(R.id.drawerDateWeekTv1);
        drawerDateWeekTv2 = findViewById(R.id.drawerDateWeekTv2);
        select7Date = findViewById(R.id.select7Date);
        select30Date = findViewById(R.id.select30Date);
        checkimg = findViewById(R.id.checkimg);
        selectNoDateLL = findViewById(R.id.selectNoDateLL);
        drawerDateWeekTipTv1 = findViewById(R.id.drawerDateWeekTipTv1);
        drawerDateWeekTipTv2 = findViewById(R.id.drawerDateWeekTipTv2);
        selectNoDateLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkimg.setVisibility(View.VISIBLE);
                drawerDateWeekTipTv1.setVisibility(View.VISIBLE);
                drawerDateWeekTipTv2.setVisibility(View.VISIBLE);
                drawerDateTv1.setVisibility(View.GONE);
                drawerDateWeekTv1.setVisibility(View.GONE);
                drawerDateTv2.setVisibility(View.GONE);
                drawerDateWeekTv2.setVisibility(View.GONE);
                select7Date.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                select30Date.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                select7Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                select30Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                drawerDateTv1.setText("");
                drawerDateTv2.setText("");
            }
        });

        select7Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDays = "7";
                checkimg.setVisibility(View.INVISIBLE);
                drawerDateWeekTipTv1.setVisibility(View.GONE);
                drawerDateWeekTipTv2.setVisibility(View.GONE);
                drawerDateTv1.setVisibility(View.VISIBLE);
                drawerDateWeekTv1.setVisibility(View.VISIBLE);
                drawerDateTv2.setVisibility(View.VISIBLE);
                drawerDateWeekTv2.setVisibility(View.VISIBLE);
                select7Date.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                select7Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_2radius_back));
                select30Date.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                select30Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));

                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(java.util.Calendar.DATE);
                int week = calendar.get(java.util.Calendar.DAY_OF_WEEK);
                drawerDateTv2.setText(year + "-" + (month + 1) + "-" + day);
                drawerDateWeekTv2.setText(getWeekString(week - 1));

                if (day - 7 < 1) {
                    if (month - 1 == 1) {
                        calendar.set(year, month - 1, day + 21);
                    } else if (month - 1 < 0) {
                        calendar.set(year - 1, 11, day + 23);
                    } else {
                        calendar.set(year, month - 1, day + 23);
                    }
                } else {
                    calendar.set(year, month, day - 7);
                }
                int year2 = calendar.get(java.util.Calendar.YEAR);
                int month2 = calendar.get(java.util.Calendar.MONTH);
                int day2 = calendar.get(java.util.Calendar.DATE);
                int week2 = calendar.get(java.util.Calendar.DAY_OF_WEEK);
                drawerDateTv1.setText(year2 + "-" + (month2 + 1) + "-" + day2);
                drawerDateWeekTv1.setText(getWeekString(week2 - 1));

            }
        });
        select30Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeDays = "30";
                checkimg.setVisibility(View.INVISIBLE);
                drawerDateWeekTipTv1.setVisibility(View.GONE);
                drawerDateWeekTipTv2.setVisibility(View.GONE);
                drawerDateTv1.setVisibility(View.VISIBLE);
                drawerDateWeekTv1.setVisibility(View.VISIBLE);
                drawerDateTv2.setVisibility(View.VISIBLE);
                drawerDateWeekTv2.setVisibility(View.VISIBLE);
                select7Date.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                select7Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                select30Date.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                select30Date.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_2radius_back));

                java.util.Calendar calendar = java.util.Calendar.getInstance();
                int year = calendar.get(java.util.Calendar.YEAR);
                int month = calendar.get(java.util.Calendar.MONTH);
                int day = calendar.get(java.util.Calendar.DATE);
                int week = calendar.get(java.util.Calendar.DAY_OF_WEEK);
                drawerDateTv2.setText(year + "-" + (month + 1) + "-" + day);
                drawerDateWeekTv2.setText(getWeekString(week - 1));

                if (day - 30 < 1) {
                    if (month - 1 == 1) {
                        calendar.set(year, month - 1, day - 2);
                    } else if (month - 1 < 0) {
                        calendar.set(year - 1, 11, day);
                    } else {
                        calendar.set(year, month - 1, day);
                    }
                } else {
                    calendar.set(year, month, day - 30);
                }
                int year2 = calendar.get(java.util.Calendar.YEAR);
                int month2 = calendar.get(java.util.Calendar.MONTH);
                int day2 = calendar.get(java.util.Calendar.DATE);
                int week2 = calendar.get(java.util.Calendar.DAY_OF_WEEK);
                drawerDateTv1.setText(year2 + "-" + (month2 + 1) + "-" + day2);
                drawerDateWeekTv1.setText(getWeekString(week2 - 1));
            }
        });

        selectDateLL1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeView = 0;
                Calendar.timeModecheck(DaiBanTypeVPActivity.this,
                        drawerDateTv1.getText().toString(),
                        drawerDateTv2.getText().toString(),
                        Calendar.LIMiT_BEFORE, Calendar.SELECTDATE);
            }
        });
        selectDateLL2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateTimeView = 1;
                Calendar.timeModecheck(DaiBanTypeVPActivity.this,
                        drawerDateTv2.getText().toString(),
                        drawerDateTv1.getText().toString(),
                        Calendar.LIMiT_AFTER, Calendar.SELECTDATE);
            }
        });
    }

    /**
     * 设置抽屉布局数据
     */
    private void setDrawerLayoutView() {
        //取消打开抽屉
        drawerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        //关闭抽屉并执行筛选搜索
        drawerSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //目前缺少整体筛选查找接口
                mDrawerLayout.closeDrawers();
                if (type.equals("1")) {//实测实量
                    listAbarbeitungBacklog(identifying, sectionId, inspectionName, timeDays);
                }
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < checklist.size(); i++) {
                    if (checklist.get(i) instanceof DaiBanDrawerCheckBean) {
                        DaiBanDrawerCheckBean checkBean = (DaiBanDrawerCheckBean) checklist.get(i);
                        if (checkBean.isCheck) {
                            if (i > 0) {
                                buffer.append(",");
                            }
                            buffer.append(checkBean.inspectionName);
                        }
                    }
                }
                inspectionName = buffer.toString();
                beginTime = drawerDateTv1.getText().toString().trim();
                endTime = drawerDateTv2.getText().toString().trim();
                List<String> sectionIdList = new ArrayList();
                for (int i = 0; i < level1.size(); i++) {
                    if (level1.get(i) instanceof DaiBanDrawerProject) {
                        DaiBanDrawerProject project = (DaiBanDrawerProject) level1.get(i);
                        if (ListUtils.listEmpty(project.getSubItems())) {
                            for (int j = 0; j < project.getSubItems().size(); j++) {
                                DaiBanDrawerSection section = project.getSubItems().get(j);
                                if (section.isCheck || project.isCheck) {
                                    sectionIdList.add(section.sectionId + "");
                                }
                            }
                        }
                    }
                }
                projectId = StringUtils.listToStrByChar(sectionIdList, ',');
                listBacklog(doState);//筛选
            }
        });
        conditionRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        optionRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new DaiBanTypeDrawerconditionAdapter(getContext());
        List<String> stringList = new ArrayList<>();
        stringList.add("项目标段");
//        stringList.add("检查项");
        stringList.add("登记时间");
        if (this.type.equals("0"))
            stringList.add("严重程度");
        adapter.setNewData(stringList);
        conditionRcv.setAdapter(adapter);

        severityList = severityList();

        optionRcv.setAdapter(optionsAdapter2);
        optionsAdapter2 = new DaiBanTypeDrawerOptionsAdapter(level1, DaiBanTypeVPActivity.this, "");
        optionRcv.setAdapter(optionsAdapter2);
        optionsAdapter2.setMyItemClick(new DaiBanTypeDrawerOptionsAdapter.MyItemClick() {
            @Override
            public void BDSelected(String sectionIdStr) {
                sectionIds = sectionIdStr;
            }

            @Override
            public void BDSection(String id) {
                sectionId = id;
            }

            @Override
            public void jcxSection(String name) {
                inspectionName = name;
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter ad, View view, int position) {
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                regisTimeLL.setVisibility(View.GONE);
                optionRcv.setVisibility(View.VISIBLE);
                if (position == 0) {
                    optionsAdapter2.setNewData(level1);
                } else if (position == 1) {
                    regisTimeLL.setVisibility(View.VISIBLE);
                    optionRcv.setVisibility(View.GONE);
                    if (type.equals("1")) {
                        selectDateLL1.setClickable(false);
                        selectDateLL1.setEnabled(false);
                        selectDateLL2.setClickable(false);
                        selectDateLL2.setEnabled(false);
                    } else {
                        selectDateLL1.setClickable(true);
                        selectDateLL1.setEnabled(true);
                        selectDateLL2.setClickable(true);
                        selectDateLL2.setEnabled(true);
                    }
                } else if (position == 2) {
                    optionsAdapter2.setNewData(severityList);
                } else {
                }
            }
        });
        optionsAdapter2.setItemClickListener(this);
        listFiltrateSection();
    }

    private DaiBanTypeDrawerconditionAdapter adapter;
    private DaiBanTypeDrawerOptionsAdapter optionsAdapter2;
    private ArrayList<MultiItemEntity> level1 = new ArrayList<>();
    private ArrayList<MultiItemEntity> checklist = new ArrayList<>();//检查项
    private ArrayList<MultiItemEntity> scslChecklist = new ArrayList<>();
    private ArrayList<MultiItemEntity> severityList = new ArrayList<>();

    /**
     * 获取筛选 项目标段
     */
    private void listFiltrateSection() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listFiltrateSection)
                .params("pkId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONObject list = object.optJSONObject("list");
                                ArrayList<MultiItemEntity> res = new ArrayList<>();
                                DaiBanDrawerProject lv1 = new DaiBanDrawerProject();
                                lv1.sectionName = list.optString("sectionName");
                                JSONArray listSection = list.optJSONArray("listSection");
                                for (int i = 0; i < listSection.length(); i++) {
                                    JSONObject object1 = listSection.optJSONObject(i);
                                    DaiBanDrawerSection section = new DaiBanDrawerSection();
                                    section.projectId = object1.optInt("projectId");
                                    section.sectionId = object1.optInt("sectionId");
                                    section.sectionName = object1.optString("sectionName");
                                    lv1.addSubItem(section);
                                    if (i == 0) {
                                        listInspectionFiltrate(object1.optInt("sectionId"));
                                    }
                                }
                                try {
                                    sectionId = lv1.getSubItem(0).sectionId + "";
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                res.add(lv1);
                                level1 = res;
                                optionsAdapter2.setNewData(level1);
                                optionsAdapter2.notifyDataSetChanged();

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

    /**
     * 工序移交-获取筛选 检查项
     */
    private void listInspectionFiltrate(int sectionId) {
        HttpRequest.get(getContext())
                .url(ServerInterface.listInspectionFiltrate)
                .params("sectionId", sectionId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray list = object.optJSONArray("list");
                                ArrayList<MultiItemEntity> res = new ArrayList<>();
//                                DaiBanDrawerCheckBean one = new DaiBanDrawerCheckBean();
//                                one.inspectionName = "000";
//                                one.isCheck=true;
//                                res.add(one);
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject object1 = list.optJSONObject(i);
                                    DaiBanDrawerCheckBean section = new DaiBanDrawerCheckBean();
                                    section.sectionId = object1.optInt("sectionId");
                                    section.inspectionId = object1.optInt("inspectionId");
                                    section.inspectionParentId = object1.optString("inspectionParentId");
                                    section.inspectionName = object1.optString("inspectionName");
                                    res.add(section);
                                }
                                checklist = res;
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

    /**
     * 实测实量-获取筛选 检查项
     */
    private void listScslFiltrate() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listScslFiltrate)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("listScslFiltrate==", result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray list = object.optJSONArray("list");
                                ArrayList<MultiItemEntity> res = new ArrayList<>();
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject object1 = list.optJSONObject(i);
                                    DaiBanDrawerCheckBean section = new DaiBanDrawerCheckBean();
                                    section.inspectionId = object1.optInt("inspectionId");
                                    section.inspectionName = object1.optString("inspectionName");
                                    res.add(section);
                                }
                                scslChecklist = res;
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

    /**
     * 严重程度
     *
     * @return
     */
    private ArrayList<MultiItemEntity> severityList() {
        String[] typeList = {"一般", "重要", "紧急"};
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int j = 0; j < typeList.length; j++) {
            DaiBanDrawerCheckBean checkBean = new DaiBanDrawerCheckBean();
            checkBean.inspectionName = typeList[j];
            res.add(checkBean);
        }
        return res;
    }


    @Override
    public void onItemClick(MultiItemEntity itemEntity) {
        if (itemEntity instanceof DaiBanDrawerSection) {
            DaiBanDrawerSection section = (DaiBanDrawerSection) itemEntity;
            listInspectionFiltrate(section.sectionId);
        } else if (itemEntity instanceof DaiBanDrawerCheckBean) {

        } else {

        }
    }

}
