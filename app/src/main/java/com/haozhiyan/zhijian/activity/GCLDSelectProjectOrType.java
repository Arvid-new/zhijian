package com.haozhiyan.zhijian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.GCLDSelectAdapter;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.GCLDProjectBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class GCLDSelectProjectOrType extends BaseActivity2 implements BaseQuickAdapter.OnItemClickListener {

    public static final int STAR_TYPE = 10001;//类型
    public static final int PROJECT = 10002;//项目

    public static void select(Activity activity, String selectName, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, GCLDSelectProjectOrType.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void select(android.support.v4.app.Fragment activity, String selectID, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity.getContext(), GCLDSelectProjectOrType.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectID);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_gcldselect_project_or_type;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    private int type;
    private String selectID;
    private RecyclerView rcv;
    private GCLDSelectAdapter selectAdapter;
    private List<GCLDProjectBean> beanList;

    @Override
    protected void initView() {
        type = getIntent().getExtras().getInt("type", 0);
        selectID = getIntent().getExtras().getString("selectID", "");
        switch (type) {
            case STAR_TYPE:
                setTitleText("亮点类型");
                break;
            case PROJECT:
                setTitleText("项目");
                break;
        }
        selectAdapter = new GCLDSelectAdapter(null, selectID);
        rcv = findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rcv.setAdapter(selectAdapter);
        selectAdapter.setOnItemClickListener(this);
    }

    @Override
    protected void initData() {
        switch (type) {
            case STAR_TYPE:
                GCLDProjectBean bean1 = new GCLDProjectBean();
                bean1.project = "质量";
                GCLDProjectBean bean2 = new GCLDProjectBean();
                bean2.project = "安全文明";
                GCLDProjectBean bean3 = new GCLDProjectBean();
                bean3.project = "工艺";
                GCLDProjectBean bean4 = new GCLDProjectBean();
                bean4.project = "综合";
                GCLDProjectBean bean5 = new GCLDProjectBean();
                bean5.project = "其他";
                beanList = new ArrayList<>();
                beanList.add(bean1);
                beanList.add(bean2);
                beanList.add(bean3);
                beanList.add(bean4);
                beanList.add(bean5);
                selectAdapter.setNewData(beanList);
                break;
            case PROJECT:
                getlistProject();
                break;
        }
    }

    private void getlistProject() {
        HttpRequest.get(getContext()).url(ServerInterface.listProject)
                .doGet(new HttpObjectCallBack<GCLDProjectBean>(GCLDProjectBean.class) {
                    @Override
                    public void onSuccess(BaseBean<GCLDProjectBean> result) {
                        beanList = result.list;
                        selectAdapter.setNewData(beanList);
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent();
        intent.putExtra("name", beanList.get(position).project);
//        intent.putExtra("id", id);
        setResult(type, intent);
        finish();
    }
}
