package com.haozhiyan.zhijian.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.DaiBanActivity;
import com.haozhiyan.zhijian.activity.GCLDActivity;
import com.haozhiyan.zhijian.activity.NoticeDetail;
import com.haozhiyan.zhijian.activity.clys.WorkClys;
import com.haozhiyan.zhijian.activity.gxyj.GXYJActivity;
import com.haozhiyan.zhijian.activity.reportforms.ReportFormsIndexActivity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.SelectProjectAdapter;
import com.haozhiyan.zhijian.activity.workScsl.SCSLActivity;
import com.haozhiyan.zhijian.activity.workXcjc.XianChangJianChactivity;
import com.haozhiyan.zhijian.adapter.WorkAdapter;
import com.haozhiyan.zhijian.adapter.WorkNoticeAdapter;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.ProjectListBean;
import com.haozhiyan.zhijian.bean.WorkPageBean;
import com.haozhiyan.zhijian.bean.biaoduan.BiaoDuanBean;
import com.haozhiyan.zhijian.bean.workfg.WorkPlotBean;
import com.haozhiyan.zhijian.bean.workfg.WorkProjectBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 工作台
 */
public class WorkFragment extends BaseFragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private TextView tv_select_name;
    private TextView tv_nodata;
    private ImageView iv_select_icon;
    private LinearLayout ll_select;
    private RelativeLayout rl_hidden;
    private RelativeLayout ll_list_window;
    private LinearLayout linear_close;
    private RecyclerView rv_noticeList;
    private RecyclerView rv_workList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv01;
    private ListView lv02;
    private TextView tvNoBD;
    private View header;
    private int mHiddenViewBackHeight;
    private WorkAdapter workAdapter;
    private WorkNoticeAdapter workNoticeAdapter;
    private List<WorkPageBean.NewsDataBean> noticeList = new ArrayList<>();
    private SelectProjectAdapter adapter1, adapter2;
    private List<ItemBean> data1, data2;
    private List<ProjectListBean.ListBean> listBeans;
    private List<ProjectListBean.ListBean.ChildsBean> childsBeans;
    //real_estateName 地产名称   plotName 地块名称
    private String real_estateName, plotName;
    //real_estateId 地产id
    private int real_estateId;
    private boolean isCanGetProject = true;
    private List<WorkPageBean.ModulesDataBean> modulesData;
    private boolean isRefreshNotice = false;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_work;
    }

    @Override
    public void initView(View view) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tv_select_name = getOutView(view, R.id.tv_select_name);
        tv_nodata = getOutView(view, R.id.tv_nodata);
        iv_select_icon = getOutView(view, R.id.iv_select_icon);
        ll_select = getOutView(view, R.id.ll_select);
        rl_hidden = getOutView(view, R.id.rl_hidden);
        linear_close = getOutView(view, R.id.linear_close);
        ll_list_window = getOutView(view, R.id.ll_list_window);
        rv_noticeList = getOutView(view, R.id.rv_noticeList);
        swipeRefreshLayout = getOutView(view, R.id.swipeRefreshLayout);
        lv01 = getOutView(view, R.id.lv01);
        lv02 = getOutView(view, R.id.lv02);
        tvNoBD = getOutView(view, R.id.tv_noBD);
        header = View.inflate(ctx, R.layout.work_header_layout, null);
        rv_workList = getOutView(header, R.id.rv_workList);
        //初始化下拉布局参数
        mHiddenViewBackHeight = SystemUtils.getPhoneScreenHight(ctx);
        linear_close.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mHiddenViewBackHeight * 2 / 3));
        //初始化公告列表
        rv_noticeList.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
        //初始化工作台列表
        rv_workList.setLayoutManager(new GridLayoutManager(ctx, 4, GridLayoutManager.VERTICAL, false));
        rv_workList.addItemDecoration(new GridSpacingItemDecoration(2, 13, true));
        swipeRefreshLayout.setColorSchemeColors(setColor(R.color.red));
        ll_list_window.setVisibility(View.VISIBLE);
        getOutView(view, R.id.rl_unit).setVisibility(View.GONE);
    }

    @Override
    public void initListener() {

        ll_select.setOnClickListener(this);
        rl_hidden.setOnClickListener(this);
        //工作台
        workAdapter = new WorkAdapter(null, ctx);
        rv_workList.setAdapter(workAdapter);
        //公告
        workNoticeAdapter = new WorkNoticeAdapter(null);
        rv_noticeList.setAdapter(workNoticeAdapter);
        workNoticeAdapter.removeAllHeaderView();
        workNoticeAdapter.setHeaderView(header);
        workAdapter.setOnItemClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                noticeList.clear();
                getWork(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        workNoticeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (listEmpty(noticeList)) {
                    if (!StringUtils.isEmpty(noticeList.get(position).news) && !"暂无公告".equals(noticeList.get(position).news)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("noticeId", noticeList.get(position).newsId + "");
                        jumpToActivity(NoticeDetail.class, bundle);
                    }
                }
            }
        });
        lv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listEmpty(listBeans)) {
                    real_estateName = listBeans.get(position).iteamName;
                    real_estateId = listBeans.get(position).pkId;
                    //Constant.parentProjectId = listBeans.get(position).pkId;
                    // Constant.parentProjectName = listBeans.get(position).iteamName;
                    adapter1.setSelectedById(listBeans.get(position).pkId);
                    if (listEmpty(listBeans.get(position).childs)) {
                        tvNoBD.setVisibility(View.GONE);
                        lv02.setVisibility(View.VISIBLE);
                        childsBeans = listBeans.get(position).childs;
                        data2 = new ArrayList<>();
                        for (int i = 0; i < listBeans.get(position).childs.size(); i++) {
                            data2.add(new ItemBean(listBeans.get(position).childs.get(i).iteamName, "", listBeans.get(position).childs.get(i).pkId));
                        }
                        adapter2 = new SelectProjectAdapter(ctx, data2, R.layout.scsl_list_item);
                        lv02.setAdapter(adapter2);
                    } else {
                        tvNoBD.setVisibility(View.VISIBLE);
                        lv02.setVisibility(View.GONE);
                    }
                }
            }
        });
        lv02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                isRefreshNotice = true;
                if (listEmpty(childsBeans)) {
                    Constant.projectId = childsBeans.get(position).pkId;
                    Constant.diKuaiName = childsBeans.get(position).iteamName;
                    plotName = childsBeans.get(position).iteamName;
                    Constant.projectName = real_estateName + plotName;
                    Constant.parentProjectId = real_estateId;
                    Constant.parentProjectName = real_estateName;
                    tv_select_name.setText(real_estateName + plotName);
//                    SPUtil.get(getContext()).savePKID(pkid);
                    AnimationUtil.getInstance().animateClose(rl_hidden);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                    adapter2.setSelectedById(childsBeans.get(position).pkId);
                    getWork(true);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getProject();
        getWork(false);
    }

    @Override
    public void initData(boolean isNetWork) {

    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select:
                if (rl_hidden.getVisibility() == View.GONE) {
                   /* if (isCanGetProject) {
                        getProject();
                    }*/
                    AnimationUtil.getInstance().animateOpen(rl_hidden, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv_select_icon);
                } else {
                    AnimationUtil.getInstance().animateClose(rl_hidden);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                }
                break;
            case R.id.rl_hidden:
                AnimationUtil.getInstance().animateClose(rl_hidden);
                AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                break;
            default:
                break;
        }
    }

    //初始化查询项目地块 false:初始化调用 true:切换地块后调用
    private void getWork(final boolean isSelect) {
        LogUtils.i("dikuaiId==", Constant.projectId + "");
        HttpRequest.get(ctx).url(ServerInterface.setting)
                .params("dikuaiId", Constant.projectId)
                .doPost(new HttpStringCallBack() {

                    @Override
                    public void onSuccess(Object result) {
                        try {
                            WorkPageBean pageBean = new Gson().fromJson(result.toString(), WorkPageBean.class);
                            if (pageBean.code == 0) {
                                Constant.partWhetherIdentifying = pageBean.partWhetherIdentifying;
                                if (isRefreshNotice) {
                                    //设置公告
                                    if (listEmpty(pageBean.newsData)) {
                                        noticeList = pageBean.newsData;
                                        workNoticeAdapter.setNewData(pageBean.newsData);
                                    } else {
                                        setNoticeEmptyData(pageBean);
                                    }
                                    isRefreshNotice = false;
                                } else {
                                    //设置默认项目地块
                                   /* if ( response.data.proData != null) {
                                        tv_select_name.setText( response.data.proData.iteamName +  response.data.proData.dikuaiName);
                                        Constant.projectId =  response.data.proData.dikuaiId;
                                        Constant.parentProjectId =  response.data.proData.iteamId;
                                        Constant.parentProjectName =  response.data.proData.iteamName;
                                        Constant.projectName =  response.data.proData.iteamName+ response.data.proData.dikuaiName;
                                        Constant.diKuaiName =  response.data.proData.dikuaiName;
                                    }*/
                                    //设置功能模块显示
                                    if (listEmpty(pageBean.modulesData)) {
                                        modulesData = pageBean.modulesData;
                                        workAdapter.setNewData(pageBean.modulesData);
                                    } else {
                                        setWorkEmptyData(pageBean);
                                    }
                                    //设置公告
                                    if (listEmpty(pageBean.newsData)) {
                                        noticeList = pageBean.newsData;
                                        workNoticeAdapter.setNewData(pageBean.newsData);
                                    } else {
                                        setNoticeEmptyData(pageBean);
                                    }
                                }
                                if (isSelect) {//切换地块
                                    EventBus.getDefault().post(Constant.REFRESH_DAIBAN);
                                }
                            } else {
                                ToastUtils.myToast(ctx, pageBean.msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(ctx, "请求错误，服务器异常");

                    }
                });
    }

    //旧字段 地块id-pkId  新字段 地块id-dikuaiId
    //搜索查询项目地块
    /*private void getProject() {
        HttpRequest.get(getContext()).url(ServerInterface.AllItem).params("pkId", "1").doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    ProjectListBean bean = new Gson().fromJson(result.toString(), ProjectListBean.class);
                    if (bean != null) {
                        if (bean.code == 0) {
                            if (listEmpty(bean.list)) {
                                listBeans = bean.list;
                                data1 = new ArrayList<>();
                                for (int i = 0; i < bean.list.size(); i++) {
                                    data1.add(new ItemBean(bean.list.get(i).iteamName, "", bean.list.get(i).pkId));
                                }
                                adapter1 = new MyListAdapter(ctx, data1, R.layout.scsl_list_item);
                                lv01.setAdapter(adapter1);
                                if (listEmpty(bean.list.get(0).childs)) {
                                    pkid = bean.list.get(0).childs.get(0).pkId + "";
                                    tv_select_name.setText(bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName);
                                    Constant.projectName = bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName;
                                    Constant.parentProjectName = bean.list.get(0).iteamName;
                                    Constant.parentProjectId = bean.list.get(0).pkId;
                                    Constant.projectId = bean.list.get(0).childs.get(0).pkId;
                                } else {
                                    tv_select_name.setText(bean.list.get(0).iteamName);
                                    Constant.projectName = bean.list.get(0).iteamName;
                                    Constant.parentProjectName = bean.list.get(0).iteamName;
                                    Constant.parentProjectId = bean.list.get(0).pkId;
                                }
//                                SPUtil.get(getContext()).savePKID(pkid);
                                isCanGetProject = false;
                            } else {
                                isCanGetProject = true;
                                tv_nodata.setVisibility(View.VISIBLE);
                                ll_list_window.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        isCanGetProject = true;
                        tv_nodata.setVisibility(View.VISIBLE);
                        ll_list_window.setVisibility(View.GONE);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(ctx, msg);
                isCanGetProject = true;
            }
        });
    }*/
    private void saveDao(final BaseBean<WorkProjectBean> result) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DaoSession session = MyApp.getInstance().getDaoSession();
                    if (result.list != null) {
                        List<WorkProjectBean> workProjectBeans = result.list;
                        for (int i = 0; i < workProjectBeans.size(); i++) {
                            WorkProjectBean projectBean = workProjectBeans.get(i);
                            if (projectBean != null) {
                                session.insertOrReplace(workProjectBeans.get(i));
                                for (WorkPlotBean workPageBean : workProjectBeans.get(i).childs) {
                                    session.insertOrReplace(workPageBean);
                                }
                            }
                        }
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProject() {
        HttpRequest.get(ctx).url(ServerInterface.AllItem).params("pkId", "1").doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    ProjectListBean bean = new Gson().fromJson(result.toString(), ProjectListBean.class);
                    if (bean != null) {
                        if (bean.code == 0) {
                            if (listEmpty(bean.list)) {
                                listBeans = bean.list;
                                if (!UserInfo.create(getContext()).getIsSaveFirstLoginData()) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                StringBuffer buffer = new StringBuffer();
                                                for (ProjectListBean.ListBean projectBean : listBeans) {
                                                    for (ProjectListBean.ListBean.ChildsBean workPlotBean : projectBean.childs) {
                                                        if (buffer.length() > 0) {
                                                            buffer.append(",");
                                                        }
                                                        buffer.append(workPlotBean.pkId);
                                                    }
                                                }
                                                getdikuaiSectionlist(buffer.toString());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }

                                data1 = new ArrayList<>();
                                for (int i = 0; i < bean.list.size(); i++) {
                                    data1.add(new ItemBean(bean.list.get(i).iteamName, "", bean.list.get(i).pkId));
                                }
                                adapter1 = new SelectProjectAdapter(ctx, data1, R.layout.scsl_list_item);
                                lv01.setAdapter(adapter1);
                                adapter1.setSelectedById(Constant.parentProjectId);
                                if (Constant.projectId != 0) {
                                    tv_select_name.setText(Constant.projectName);
                                    tvNoBD.setVisibility(View.GONE);
                                    lv02.setVisibility(View.VISIBLE);
                                    for (int i = 0; i < bean.list.size(); i++) {
                                        if (bean.list.get(i).pkId == Constant.parentProjectId) {
                                            childsBeans = bean.list.get(i).childs;
                                            real_estateName = Constant.parentProjectName;
                                            real_estateId = Constant.parentProjectId;
                                        }
                                    }
                                    data2 = new ArrayList<>();
                                    for (int i = 0; i < childsBeans.size(); i++) {
                                        data2.add(new ItemBean(childsBeans.get(i).iteamName, "", childsBeans.get(i).pkId));
                                    }
                                    adapter2 = new SelectProjectAdapter(ctx, data2, R.layout.scsl_list_item);
                                    lv02.setAdapter(adapter2);
                                    adapter2.setSelectedById(Constant.projectId);
                                } else {
                                    if (listEmpty(bean.list.get(0).childs)) {
                                        tv_select_name.setText(bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName);
                                        Constant.projectName = bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName;
                                        Constant.parentProjectName = bean.list.get(0).iteamName;
                                        Constant.parentProjectId = bean.list.get(0).pkId;
                                        Constant.projectId = bean.list.get(0).childs.get(0).pkId;
                                        tvNoBD.setVisibility(View.GONE);
                                        lv02.setVisibility(View.VISIBLE);
                                        childsBeans = bean.list.get(0).childs;
                                        data2 = new ArrayList<>();
                                        for (int i = 0; i < listBeans.get(0).childs.size(); i++) {
                                            data2.add(new ItemBean(listBeans.get(0).childs.get(i).iteamName, "", listBeans.get(0).childs.get(i).pkId));
                                        }
                                        adapter2 = new SelectProjectAdapter(ctx, data2, R.layout.scsl_list_item);
                                        lv02.setAdapter(adapter2);
                                    } else {
                                        tv_select_name.setText(bean.list.get(0).iteamName);
                                        Constant.projectName = bean.list.get(0).iteamName;
                                        Constant.parentProjectName = bean.list.get(0).iteamName;
                                        Constant.parentProjectId = bean.list.get(0).pkId;
                                    }
                                }
                            } else {
                                tv_nodata.setVisibility(View.VISIBLE);
                                ll_list_window.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        tv_nodata.setVisibility(View.VISIBLE);
                        ll_list_window.setVisibility(View.GONE);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(ctx, msg);
            }
        });
    }

    /**
     * 批量获取 地块 检查项 存数据库
     *
     * @param dikuaiId
     */
    private void getdikuaiSectionlist(String dikuaiId) {
        HttpRequest.get(ctx).url(ServerInterface.dikuaiSectionlist).params("dikuaiId", dikuaiId).doGet(
                new HttpObjectCallBack<BiaoDuanBean>(BiaoDuanBean.class) {
                    @Override
                    public void onSuccess(final BaseBean<BiaoDuanBean> result) {
                        if ("0".equals(result.code)) {
                            try {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            DaoSession session = MyApp.getInstance().getDaoSession();
                                            if (result.list != null) {
                                                for (int i = 0; i < result.list.size(); i++) {
                                                    if (result.list.get(i) != null) {
                                                        session.insertOrReplace(result.list.get(i));
                                                        for (int j = 0; j < result.list.get(i).inspection.size(); j++) {
                                                            session.insertOrReplace(result.list.get(i).inspection.get(j));
                                                        }
                                                        for (int k = 0; k < result.list.get(i).scopeChild.size(); k++) {
                                                            session.insertOrReplace(result.list.get(i).scopeChild.get(k));
                                                        }
                                                        UserInfo.create(getContext()).putIsSaveFirstLoginData(true);
                                                    }
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
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    //获取公告
    private void setNoticeEmptyData(WorkPageBean noticeBean) {
        noticeList.clear();
        noticeList.add(noticeBean.getNewsBean());
        workNoticeAdapter.setNewData(noticeList);
    }

    private void setWorkEmptyData(WorkPageBean noticeBean) {
        noticeList.clear();
        workAdapter.setNewData(noticeBean.getWorkBean());
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (listEmpty(modulesData)) {
            try {
                Constant.photoTag = modulesData.get(position).photoTag;
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (modulesData.get(position).name) {
                case "现场检查"://现场检查
                    jumpToActivity(XianChangJianChactivity.class);
                    break;
                case "实测实量"://实测实量
                    jumpToActivity(SCSLActivity.class);
                    break;
                case "工序移交"://工序移交
                    Bundle bundle = new Bundle();
                    bundle.putString("pkid", Constant.projectId + "");
                    jumpToActivity(GXYJActivity.class, bundle);
                    break;
                case "材料验收"://材料验收
                    jumpToActivity(WorkClys.class);
                    break;
                case "待办"://待办-管理员有的功能
                    jumpToActivity(DaiBanActivity.class);
                    break;
                case "工程亮点"://工程亮点
                    jumpToActivity(GCLDActivity.class);
                    break;
                case "工序验收"://工序验收

                    break;
                case "样板管理"://样板管理

                    break;
                case "常用文档"://常用文档

                    break;
                case "形象进度"://形象进度

                    break;
                case "进度管理"://进度管理

                    break;
                case "专项巡检"://专项巡检

                    break;
                case "实测巡检"://实测巡检

                    break;
                case "报表"://报表
                    jumpToActivity(ReportFormsIndexActivity.class);
                    break;
                default:
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
