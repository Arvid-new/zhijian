package com.haozhiyan.zhijian.activity.reportforms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.Calendar;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormCLYSAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormCLYSChild;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormCLYSResult;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormCLYSTitle;
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
 * 报表-材料验收首页
 */
public class CLYSFormsActivity extends BaseFormsActivity implements SwipeRefreshLayout.OnRefreshListener {
    private TextView tv_startTime;
    private TextView tv_endTime;
    private int dateTimeView = 0;
    private RecyclerView recyclerView;
    private FormCLYSAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<MultiItemEntity> dataList = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        setTitleText("材料验收");
        tv_startTime = getOutView(R.id.tv_startTime);
        tv_endTime = getOutView(R.id.tv_endTime);
        setListenerView(tv_startTime);
        setListenerView(tv_endTime);
        swipeRefreshLayout = getOutView(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(activity, R.color.red));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setEnabled(false);
        recyclerView = getOutView(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_clysforms;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
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
        adapter = new FormCLYSAdapter(dataList, activity);
        recyclerView.setAdapter(adapter);
        getDataFromServer();
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

    private void getDataFromServer() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.list)
                .params("dikuaiId", Constant.projectId)
                .params("startTime", UiUtils.getContent(tv_startTime))
                .params("endTime", UiUtils.getContent(tv_endTime))
                .doGet(new HttpObjectCallBack<FormCLYSResult>(FormCLYSResult.class) {
                    @Override
                    public void onSuccess(BaseBean<FormCLYSResult> result) {
                        hideLoadView();
                        if (TextUtils.equals("0",result.code)) {
                            dataList.clear();
                            List<FormCLYSResult> beanList =result.list;
                            try {
                                if(ListUtils.listEmpty(beanList)){
                                    for (int i = 0; i < beanList.size(); i++) {
                                        FormCLYSTitle lv1 = new FormCLYSTitle();
                                        lv1.nameInspectionId = beanList.get(i).getNameInspectionId();
                                        lv1.nameInspection = beanList.get(i).getNameInspection();
                                        lv1.amount = beanList.get(i).getAmount();
                                        lv1.total = beanList.get(i).getTotal();
                                        List<FormCLYSResult.ClysListBean> clysList = beanList.get(i).getClysList();
                                        for (int j = 0; j < clysList.size(); j++) {
                                            FormCLYSChild clysChild = new FormCLYSChild();
                                            clysChild.id = clysList.get(j).getId();
                                            clysChild.titleName = clysList.get(j).getTitleName();
                                            clysChild.approachDate = clysList.get(j).getApproachDate();
                                            clysChild.state = clysList.get(j).getState();
                                            lv1.addSubItem(clysChild);
                                        }
                                        dataList.add(lv1);
                                    }
                                    adapter.setNewData(dataList);
                                }else{
                                    adapter.setNewData(dataList);
                                    adapter.setEmptyView(R.layout.app_layout_empty,recyclerView);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            ToastUtils.myToast(getContext(), result.msg);
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
