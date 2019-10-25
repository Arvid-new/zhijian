package com.haozhiyan.zhijian.activity.reportforms;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLDetailAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailContentBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailResult;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailTitleBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailTotalBean;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表-实测实量详情页
 */
public class SCSLFormDetailActivity extends BaseActivity2 {
    private RecyclerView recyclerView;
    private FormSCSLDetailAdapter scslAdapter;
    private SCSLFormDetailActivity activity;
    private String sectionId, sectionName;
    private String towerId, towerName;
    private ArrayList<MultiItemEntity> dataList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        activity = this;
        StatusBarUtils.setStatus(this, true);
        sectionId = getIntent().getStringExtra("sectionId");
        sectionName = getIntent().getStringExtra("sectionName");
        towerId = getIntent().getStringExtra("towerId");
        towerName = getIntent().getStringExtra("towerName");
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_scslform_detail;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("详情");
        recyclerView = getOutView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void initData() {
        /*scslAdapter =new FormSCSLDetailAdapter(list, activity);
        for (int i = 0; i <5; i++) {
            FormSCSLTitleBean titleBean = new FormSCSLTitleBean();
            titleBean.sectionName="抹灰工程"+i;
            list.add(titleBean);
            for (int k = 0; k < 5; k++) {
                FormSCSLContentBean contentBean = new FormSCSLContentBean();
                contentBean.towerName="截面尺寸"+k;
                list.add(contentBean);
            }
            FormSCSLTotalBean totalBean = new FormSCSLTotalBean();
            list.add(totalBean);
        }
        recyclerView.setAdapter(scslAdapter);*/
        scslAdapter = new FormSCSLDetailAdapter(dataList, activity, sectionId, sectionName, towerId, towerName);
        recyclerView.setAdapter(scslAdapter);
        getDataFromServer();
    }

    private void getDataFromServer() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.listTowerInspMassage)
                .params("towerId", towerId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            FormSCSLDetailResult formSCSLDetailResult = new Gson().fromJson(result.toString(), FormSCSLDetailResult.class);
                            if (formSCSLDetailResult != null) {
                                if (formSCSLDetailResult.getCode() == 0) {
                                    dataList.clear();
                                    FormSCSLDetailResult.FormSCSLDetailBean formSCSLDetailBean = formSCSLDetailResult.getFormSCSLDetailBean();
                                    if (formSCSLDetailBean != null) {
                                        List<FormSCSLDetailResult.FormSCSLDetailBean.InspFuBean> beanList = formSCSLDetailBean.getInspFu();
                                        if (ListUtils.listEmpty(beanList)) {
                                            for (int i = 0; i < beanList.size(); i++) {
                                                FormSCSLDetailTitleBean lv1 = new FormSCSLDetailTitleBean();
                                                lv1.towerName = beanList.get(i).getTowerName();
                                                lv1.inspctionId = beanList.get(i).getInspctionId();
                                                lv1.inspctionName = beanList.get(i).getInspctionName();
                                                dataList.add(lv1);
                                                List<FormSCSLDetailResult.FormSCSLDetailBean.InspFuBean.InspSunBean> beans = beanList.get(i).getInspSun();
                                                for (int j = 0; j < beans.size(); j++) {
                                                    FormSCSLDetailContentBean contentBean = new FormSCSLDetailContentBean();
                                                    contentBean.inspctionSunId = beans.get(j).getInspctionSunId();
                                                    contentBean.inspctionSunName = beans.get(j).getInspctionSunName();
                                                    contentBean.jlHgl = beans.get(j).getJlHgl();
                                                    contentBean.jlHs = beans.get(j).getJlHs();
                                                    contentBean.jsHgl = beans.get(j).getJsHgl();
                                                    contentBean.jsHu = beans.get(j).getJsHu();
                                                    contentBean.sgHgl = beans.get(j).getSgHgl();
                                                    contentBean.sgHs = beans.get(j).getSgHs();
                                                    dataList.add(contentBean);
                                                }
                                                FormSCSLDetailTotalBean totalBean = new FormSCSLDetailTotalBean();
                                                totalBean.zongji = beanList.get(i).getZj().getZongji();
                                                totalBean.jiansheZongji = beanList.get(i).getZj().getJiansheZongji();
                                                totalBean.jianliZongji = beanList.get(i).getZj().getJianliZongji();
                                                totalBean.shigongZongji = beanList.get(i).getZj().getShigongZongji();
                                                dataList.add(totalBean);
                                            }
                                            scslAdapter.setNewData(dataList);
                                        } else {
                                            scslAdapter.setNewData(dataList);
                                            scslAdapter.setEmptyView(R.layout.app_layout_empty, recyclerView);
                                        }
                                    }
                                }
                            }
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

}
