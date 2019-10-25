package com.haozhiyan.zhijian.activity.reportforms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.OnSiteInspectionDetails;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormProblemDropListAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormProblemListAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormXCJCAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormXCJCDetailBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.ProblemBean;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

public class XCJCFormsDetailActivity extends BaseActivity2 implements SwipeRefreshLayout.OnRefreshListener {
    private RelativeLayout rl_select_menu;
    private ImageView iv_select_icon;
    private int mHiddenViewBackHeight;
    private RelativeLayout rl_hidden;
    private LinearLayout linear_close;
    private XCJCFormsDetailActivity activity;
    private RecyclerView rcv_problem_list;
    private RecyclerView recyclerView;
    private FormProblemDropListAdapter formProblemListAdapter;
    private FormProblemListAdapter adapter;
    private List<FormXCJCDetailBean> problemBeanList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private String startTime, endTime;
    private String batchId;
    private String inspectionId;
    private TextView tv_inspectionName,tv_number;
    protected void init(Bundle savedInstanceState) {
        activity = this;
        StatusBarUtils.setStatus(this, true);
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");
        batchId = getIntent().getStringExtra("batchId");

    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_xcjcforms_detail;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("详情");
        tv_inspectionName =  getOutView(R.id.tv_project_name);
        tv_number =  getOutView(R.id.tv_number);
        rl_select_menu = getOutView(R.id.rl_select_menu);
        iv_select_icon = getOutView(R.id.iv_select_icon);
        rl_hidden = getOutView(R.id.rl_hidden);
        linear_close = getOutView(R.id.linear_close);
        swipeRefreshLayout = getOutView(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity, R.color.red));
        swipeRefreshLayout.setOnRefreshListener(this);
        //初始化下拉布局参数
        mHiddenViewBackHeight = SystemUtils.getPhoneScreenHight(activity);
        linear_close.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mHiddenViewBackHeight * 1 / 3));
        rcv_problem_list = getOutView(R.id.rcv_problem_list);
        rcv_problem_list.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView = getOutView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setListenerView(rl_select_menu);
    }

    @Override
    protected void initData() {
       /* for (int i = 0; i <10 ; i++) {
            FormXCJCDetailBean bean= new FormXCJCDetailBean();
            problemBeanList.add(bean);
        }
        formProblemListAdapter =new FormProblemDropListAdapter(activity,problemBeanList);
        rcv_problem_list.setAdapter(formProblemListAdapter);
        formProblemListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                formProblemListAdapter.setSelectItem(position);
                AnimationUtil.getInstance().animateClose(rl_hidden);
                AnimationUtil.getInstance().animationIvClose(iv_select_icon);
            }
        });
        adapter =new FormProblemListAdapter(activity,problemBeanList);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent =new Intent(activity,OnSiteInspectionDetails.class);
                intent.putExtra("id","6");
                intent.putExtra("entrance", "form");
                startActivity(intent);
            }
        });*/
        getProblemTypes();
        getDataFromServer();

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_select_menu:
                if (rl_hidden.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(rl_hidden, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv_select_icon);
                } else {
                    AnimationUtil.getInstance().animateClose(rl_hidden);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                }
                break;
            default:
                break;
        }
    }

    private void getProblemTypes() {
        HttpRequest.get(this).url(ServerInterface.inspectionList)
                .params("dikuaiId", Constant.projectId)
                .params("startTime", startTime)
                .params("endTime", endTime)
                .params("batchId", batchId)
                .doGet(new HttpObjectCallBack<ProblemBean>(ProblemBean.class) {
                    @Override
                    public void onSuccess(BaseBean<ProblemBean> result) {
                        final List<ProblemBean> beanList = result.list;
                        if(ListUtils.listEmpty(beanList)){
                            setText(tv_inspectionName,beanList.get(0).getInspectionName());
                            setText(tv_number,beanList.get(0).getShuliang()+"个");
                            formProblemListAdapter = new FormProblemDropListAdapter(activity, beanList);
                            rcv_problem_list.setAdapter(formProblemListAdapter);
                            formProblemListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    formProblemListAdapter.setSelectItem(position);
                                    inspectionId = beanList.get(position).getInspectionId()+"";
                                    setText(tv_inspectionName,beanList.get(position).getInspectionName());
                                    setText(tv_number,beanList.get(position).getShuliang()+"个");
                                    getDataFromServer();
                                    AnimationUtil.getInstance().animateClose(rl_hidden);
                                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                                }
                            });
                        }else{
                            ToastUtils.myToast(activity,"暂无数据");
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

    private void getDataFromServer() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.xcjcDetailList)
                .params("dikuaiId", Constant.projectId)
                .params("startTime", startTime)
                .params("endTime", endTime)
                .params("batchId", batchId)
                .params("inspectionId", inspectionId)

                .doGet(new HttpObjectCallBack<FormXCJCDetailBean>(FormXCJCDetailBean.class) {
                    @Override
                    public void onSuccess(BaseBean<FormXCJCDetailBean> result) {
                        hideLoadView();
                        if (TextUtils.equals("0", result.code)) {
                            final List<FormXCJCDetailBean> beanList = result.list;
                            adapter = new FormProblemListAdapter(activity, beanList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                    Intent intent = new Intent(activity, OnSiteInspectionDetails.class);
                                    intent.putExtra("id", beanList.get(position).getId()+"");
                                    intent.putExtra("entrance", "form");
                                    startActivity(intent);
                                }
                            });
                        }else {
                            ToastUtils.myToast(activity, result.msg);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
