package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.CaiLiaoIndexListBean;

import java.util.List;


public class WorkClysAdapter extends BaseQuickAdapter<CaiLiaoIndexListBean.ClysListBean, BaseViewHolder> {
    public WorkClysAdapter(@Nullable List<CaiLiaoIndexListBean.ClysListBean> data) {
        super(R.layout.work_clys_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CaiLiaoIndexListBean.ClysListBean item) {
        helper.setText(R.id.title, item.getSectionName() + item.getNameInspection() + "进场" + item.getNumber());
        helper.setText(R.id.time, "创建时间:  " + item.getSubmitDate());
        //状态(1待申请进场，2待验收，3已验收，4待送检，5待上传报告，6待复验，7待退场，8已退场，9送检合格，10复验合格)

        switch (item.getState()) {
            case "1":
                helper.setText(R.id.state, "待申请进场");
                helper.setTextColor(R.id.state, Color.parseColor("#F53737"));
                break;
            case "2":
                helper.setText(R.id.state, "待验收");
                helper.setTextColor(R.id.state, Color.parseColor("#F53737"));
                break;
            case "3":
                helper.setText(R.id.state, "已验收");
                helper.setTextColor(R.id.state, Color.parseColor("#F53737"));
                break;
            case "4":
                helper.setText(R.id.state, "待送检");
                helper.setTextColor(R.id.state, Color.parseColor("#FC8B01"));
                break;
            case "5":
                helper.setText(R.id.state, "待上传报告");
                helper.setTextColor(R.id.state, Color.parseColor("#F53737"));
                break;
            case "6":
                helper.setText(R.id.state, "待复验");
                helper.setTextColor(R.id.state, Color.parseColor("#FC8B01"));
                break;
            case "7":
                helper.setText(R.id.state, "待退场");
                helper.setTextColor(R.id.state, Color.parseColor("#F53737"));
                break;
            case "8":
                helper.setText(R.id.state, "已退场");
                helper.setTextColor(R.id.state, Color.parseColor("#A7A7A7"));
                break;
            case "9":
                helper.setText(R.id.state, "送检合格");
                helper.setTextColor(R.id.state, Color.parseColor("#0BC666"));
                break;
            case "10":
                helper.setText(R.id.state, "复验合格");
                helper.setTextColor(R.id.state, Color.parseColor("#0BC666"));
                break;
        }


    }
}
