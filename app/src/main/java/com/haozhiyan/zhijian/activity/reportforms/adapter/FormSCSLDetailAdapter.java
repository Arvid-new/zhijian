package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailContentBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailTitleBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLDetailTotalBean;
import com.haozhiyan.zhijian.activity.workScsl.SCSLActivity;
import com.haozhiyan.zhijian.listener.PersonItemClick;

import java.util.List;

/**
 * 报表-实测实量详情列表适配器
 */
public class FormSCSLDetailAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_TOTAL = 3;
    private PersonItemClick itemClick;
    private RecyclerView rcv;
    private String sectionId,sectionName;
    private String towerId,towerName;
    public void setItemClickListener(PersonItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FormSCSLDetailAdapter(@Nullable List<MultiItemEntity> data, Context context,String sectionId,String sectionName,String towerId,String towerName) {
        super(data);
        this.context = context;
        addItemType(TYPE_TITLE, R.layout.item_form_scsl_title);
        addItemType(TYPE_CONTENT, R.layout.item_form_scsl_detail_content);
        addItemType(TYPE_TOTAL, R.layout.item_form_scsl_detail_total);
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.towerId = towerId;
        this.towerName = towerName;
    }

    public void setRcv(RecyclerView rcv) {
        this.rcv = rcv;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_TITLE:
                final FormSCSLDetailTitleBean lv1 = (FormSCSLDetailTitleBean) item;
                holder.setText(R.id.tv_title, lv1.inspctionName);
                break;
            case TYPE_CONTENT:
                final FormSCSLDetailContentBean bean = (FormSCSLDetailContentBean) item;
                holder.setText(R.id.tv_title, bean.inspctionSunName);
                TextView tv_tag01 = holder.getView(R.id.tv_tag01);
                TextView tv_tag02 = holder.getView(R.id.tv_tag02);
                TextView tv_tag03 = holder.getView(R.id.tv_tag03);
                TextView tv_tag001 = holder.getView(R.id.tv_tag001);
                TextView tv_tag002 = holder.getView(R.id.tv_tag002);
                TextView tv_tag003 = holder.getView(R.id.tv_tag003);
                if (TextUtils.isEmpty(bean.sgHgl)) {
                    tv_tag001.setText("0%");
                    tv_tag01.setVisibility(View.GONE);
                } else {
                    tv_tag001.setText(bean.sgHs + "户");
                    tv_tag01.setVisibility(View.VISIBLE);
                    tv_tag01.setText(bean.sgHgl);
                    setTextColor(tv_tag01, bean.sgHgl);
                }
                if (TextUtils.isEmpty(bean.jlHgl)) {
                    tv_tag002.setText("0%");
                    tv_tag02.setVisibility(View.GONE);
                } else {
                    tv_tag002.setText(bean.jlHs + "户");
                    tv_tag02.setVisibility(View.VISIBLE);
                    tv_tag02.setText(bean.jlHgl);
                    setTextColor(tv_tag02, bean.jlHgl);
                }
                if (TextUtils.isEmpty(bean.jsHgl)) {
                    tv_tag003.setText("0%");
                    tv_tag03.setVisibility(View.GONE);
                } else {
                    tv_tag003.setText(bean.jsHu + "户");
                    tv_tag03.setVisibility(View.VISIBLE);
                    tv_tag03.setText(bean.jsHgl);
                    setTextColor(tv_tag03, bean.jsHgl);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("sectionId", sectionId);
                        bundle.putString("sectionName", sectionName);
                        bundle.putString("towerId", towerId);
                        bundle.putString("towerName", towerName);
                        bundle.putString("inspectionSunName", bean.inspctionSunName);
                        bundle.putString("inspectionSunId", bean.inspctionSunId);
                        intent.putExtra("formBundle", bundle);
                        intent.setClass(context, SCSLActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_TOTAL:
                final FormSCSLDetailTotalBean totalBean = (FormSCSLDetailTotalBean) item;
                TextView tv_tag0 = holder.getView(R.id.tv_tag0);
                TextView tv_tag1 = holder.getView(R.id.tv_tag1);
                TextView tv_tag2 = holder.getView(R.id.tv_tag2);
                if (TextUtils.isEmpty(totalBean.shigongZongji)) {
                    tv_tag0.setText("0%");
                } else {
                    tv_tag0.setText(totalBean.shigongZongji);
                    setTextColor(tv_tag0, totalBean.shigongZongji);
                }
                if (TextUtils.isEmpty(totalBean.jianliZongji)) {
                    tv_tag1.setText("0%");
                } else {
                    tv_tag1.setText(totalBean.jianliZongji);
                    setTextColor(tv_tag1, totalBean.jianliZongji);
                }
                if (TextUtils.isEmpty(totalBean.jiansheZongji)) {
                    tv_tag2.setText("0%");
                } else {
                    tv_tag2.setText(totalBean.jiansheZongji);
                    setTextColor(tv_tag2, totalBean.jiansheZongji);
                }
                break;
            default:
                break;
        }
    }

    protected void setTextColor(TextView textView, String value) {
        if (TextUtils.equals("100.0%", value)) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.blue4));
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.red2));
        }

    }
}
