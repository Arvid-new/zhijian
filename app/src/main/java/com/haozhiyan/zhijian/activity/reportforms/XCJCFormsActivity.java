package com.haozhiyan.zhijian.activity.reportforms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.Calendar;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormXCJCAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormXCJCBean;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 报表-现场检查首页
 */
public class XCJCFormsActivity extends BaseFormsActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_startTime;
    private TextView tv_endTime;
    private int dateTimeView = 0;
    private RecyclerView recyclerView;
    private XCJCFormsActivity activity;
    private FormXCJCAdapter xcjcAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<FormXCJCBean> mList = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        setTitleText("现场检查");
        tv_startTime = getOutView(R.id.tv_startTime);
        tv_endTime = getOutView(R.id.tv_endTime);
        setListenerView(tv_startTime);
        setListenerView(tv_endTime);
        swipeRefreshLayout =getOutView(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity,R.color.red));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
        recyclerView = getOutView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayout.VERTICAL, false));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_startTime:
                dateTimeView = 0;
                Calendar.timeModecheck(activity,
                        tv_startTime.getText().toString(),
                        tv_endTime.getText().toString(),
                        Calendar.LIMiT_BEFORE, Calendar.SELECTDATE);
                break;
            case R.id.tv_endTime:
                dateTimeView = 1;
                Calendar.timeModecheck(activity,
                        tv_endTime.getText().toString(),
                        tv_startTime.getText().toString(),
                        Calendar.LIMiT_AFTER, Calendar.SELECTDATE);
                break;
            default:
                break;
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
                    if (dateTimeView == 0) {
                        tv_startTime.setText(String.valueOf(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay()));
                    } else {
                        tv_endTime.setText(String.valueOf(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay()));
                    }
                    getDataFromServer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_xcjcforms;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        activity = this;
    }

    @Override
    protected void initData() {
        super.initData();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.set(java.util.Calendar.DATE, 1);
        String firstDay = new SimpleDateFormat("yyyy-M-d").format(calendar.getTime());
        tv_startTime.setText(String.valueOf(firstDay));
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy-M-d").format(date);
        tv_endTime.setText(String.valueOf(nowDate));
        getDataFromServer();
      /*  xcjcAdapter = new FormXCJCAdapter(activity, list);
        FormXCJCBean titleBean1 = new FormXCJCBean();
        titleBean1.total = 10;
        titleBean1.ytg = 3;
        titleBean1.dfy = 1;
        titleBean1.dzg = 5;
        titleBean1.fzcgb = 1;
        list.add(titleBean1);
        FormXCJCBean titleBean2 = new FormXCJCBean();
        titleBean2.total = 6;
        titleBean2.ytg = 1;
        titleBean2.dfy = 1;
        titleBean2.dzg = 3;
        titleBean2.fzcgb = 1;
        list.add(titleBean2);
        FormXCJCBean titleBean3 = new FormXCJCBean();
        titleBean3.total = 8;
        titleBean3.ytg = 0;
        titleBean3.dfy = 0;
        titleBean3.dzg = 0;
        titleBean3.fzcgb = 8;
        list.add(titleBean3);
        recyclerView.setAdapter(xcjcAdapter);
        xcjcAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                jumpToActivity(XCJCFormsDetailActivity.class);
            }
        });*/
        xcjcAdapter = new FormXCJCAdapter(activity, mList);
        recyclerView.setAdapter(xcjcAdapter);
        xcjcAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent =new Intent(activity,XCJCFormsDetailActivity.class);
                intent.putExtra("batchId",mList.get(position).getBatchId());
                intent.putExtra("startTime",UiUtils.getContent(tv_startTime));
                intent.putExtra("endTime",UiUtils.getContent(tv_endTime));
                startActivity(intent);
            }
        });
    }

    private void getDataFromServer() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.formXcjc)
                .params("dikuaiId", Constant.projectId)
                .params("startTime", UiUtils.getContent(tv_startTime))
                .params("endTime", UiUtils.getContent(tv_endTime))
                .doGet(new HttpObjectCallBack<FormXCJCBean>(FormXCJCBean.class) {
                    @Override
                    public void onSuccess(BaseBean<FormXCJCBean> result) {
                        hideLoadView();
                        mList.clear();
                        if(TextUtils.equals("0",result.code)){
                            if(ListUtils.listEmpty(result.list)){
                                mList.addAll(result.list);
                                xcjcAdapter.setNewData(mList);
                            }else{
                                xcjcAdapter.setNewData(mList);
                                xcjcAdapter.setEmptyView(R.layout.app_layout_empty,recyclerView);
                            }
                        }else{
                            ToastUtils.myToast(activity,result.msg);
                        }
                    }
                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }
    @Override
    public void onDiKuaiChanged(String dikuai) {
        getDataFromServer();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}
