package com.haozhiyan.zhijian.activity.reportforms;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLContentBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLResult;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLTitleBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLTotalBean;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表-实测实量首页
 */
public class SCSLFormsActivity extends BaseFormsActivity {
    private RecyclerView recyclerView;
    private FormSCSLAdapter scslAdapter;
    private SCSLFormsActivity activity;
    private ArrayList<MultiItemEntity> dataList = new ArrayList<>();

    @Override
    protected void initView() {
        super.initView();
        setTitleText("实测实量");
        recyclerView = getOutView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_scslforms;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        activity = this;
        super.init(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        scslAdapter = new FormSCSLAdapter(dataList, activity);
        recyclerView.setAdapter(scslAdapter);
        getDataFromServer();
    }

    private void getDataFromServer() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.selectMassage)
                .params("dikuaiId", Constant.projectId)
                .doGet(new HttpObjectCallBack<FormSCSLResult>(FormSCSLResult.class) {
                    @Override
                    public void onSuccess(BaseBean<FormSCSLResult> result) {
                        hideLoadView();
                        if (TextUtils.equals("0", result.code)) {
                            dataList.clear();
                            List<FormSCSLResult> beanList = result.list;
                            try {
                                if (ListUtils.listEmpty(result.list)) {
                                    for (int i = 0; i < beanList.size(); i++) {
                                        FormSCSLTitleBean lv1 = new FormSCSLTitleBean();
                                        lv1.projectManage = beanList.get(i).getProjectManage();
                                        lv1.scope = beanList.get(i).getScope();
                                        lv1.sectionId = beanList.get(i).getSectionId();
                                        lv1.sectionName = beanList.get(i).getSectionName();
                                        dataList.add(lv1);
                                        List<FormSCSLResult.TowerBean> towerBeans = beanList.get(i).getTower();
                                        for (int j = 0; j < towerBeans.size(); j++) {
                                            FormSCSLContentBean contentBean = new FormSCSLContentBean();
                                            contentBean.jianlihegelv = towerBeans.get(j).getJianlihegelv();
                                            contentBean.jianliInsp = towerBeans.get(j).getJianliInsp();
                                            contentBean.jianshehegelv = towerBeans.get(j).getJianshehegelv();
                                            contentBean.jiansheheInsp = towerBeans.get(j).getJiansheheInsp();
                                            contentBean.shigonghegelv = towerBeans.get(j).getShigonghegelv();
                                            contentBean.shigongInsp = towerBeans.get(j).getShigongInsp();
                                            contentBean.towerId = towerBeans.get(j).getTowerId();
                                            contentBean.towerName = towerBeans.get(j).getTowerName();
                                            dataList.add(contentBean);
                                        }
                                        FormSCSLTotalBean totalBean = new FormSCSLTotalBean();
                                        totalBean.zongji = beanList.get(i).getZj().getZongji();
                                        totalBean.jiansheZongji = beanList.get(i).getZj().getJiansheZongji();
                                        totalBean.jianliZongji = beanList.get(i).getZj().getJianliZongji();
                                        totalBean.shigongZongji = beanList.get(i).getZj().getShigongZongji();
                                        dataList.add(totalBean);
                                    }
                                    scslAdapter.setNewData(dataList);
                                }else{
                                    scslAdapter.setNewData(dataList);
                                    scslAdapter.setEmptyView(R.layout.app_layout_empty,recyclerView);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
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
    public void onDiKuaiChanged(String dikuai) {
        getDataFromServer();
    }


}
