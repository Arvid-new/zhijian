package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.haozhiyan.zhijian.activity.reportforms.SCSLFormDetailActivity;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLContentBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLTitleBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormSCSLTotalBean;
import com.haozhiyan.zhijian.listener.PersonItemClick;
import com.haozhiyan.zhijian.utils.ToastUtils;

import java.util.List;

/**
 * 报表-实测实量列表适配器
 */
public class FormSCSLAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_TOTAL = 3;
    private PersonItemClick itemClick;
    private RecyclerView rcv;
    private String sectionId,sectionName;
    public void setItemClickListener(PersonItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FormSCSLAdapter(@Nullable List<MultiItemEntity> data, Context context) {
        super(data);
        this.context = context;
        addItemType(TYPE_TITLE, R.layout.item_form_scsl_title);
        addItemType(TYPE_CONTENT, R.layout.item_form_scsl_content);
        addItemType(TYPE_TOTAL, R.layout.item_form_scsl_total);
    }

    public void setRcv(RecyclerView rcv) {
        this.rcv = rcv;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_TITLE:
                final FormSCSLTitleBean lv1 = (FormSCSLTitleBean) item;
                holder.setText(R.id.tv_title, lv1.sectionName);
                sectionId = lv1.sectionId+"";
                sectionName= lv1.sectionName;
                break;
            case TYPE_CONTENT:
                final FormSCSLContentBean bean = (FormSCSLContentBean) item;
                holder.setText(R.id.tv_tower, bean.towerName);
                TextView tv_tag01 = holder.getView(R.id.tv_tag01);
                TextView tv_tag02 = holder.getView(R.id.tv_tag02);
                TextView tv_tag03 = holder.getView(R.id.tv_tag03);
                TextView tv_tag001 = holder.getView(R.id.tv_tag001);
                TextView tv_tag002 = holder.getView(R.id.tv_tag002);
                TextView tv_tag003 = holder.getView(R.id.tv_tag003);
                if (TextUtils.isEmpty(bean.shigonghegelv)) {
                    tv_tag001.setText("0");
                    tv_tag01.setVisibility(View.GONE);
                } else {
                    tv_tag001.setText(bean.shigongInsp + "项");
                    tv_tag01.setVisibility(View.VISIBLE);
                    tv_tag01.setText(bean.shigonghegelv);
                    setTextColor(tv_tag01, bean.shigonghegelv);
                }
                if (TextUtils.isEmpty(bean.jianlihegelv)) {
                    tv_tag002.setText("0");
                    tv_tag02.setVisibility(View.GONE);
                } else {
                    tv_tag002.setText(bean.jianliInsp + "项");
                    tv_tag02.setVisibility(View.VISIBLE);
                    tv_tag02.setText(bean.jianlihegelv);
                    setTextColor(tv_tag02, bean.jianlihegelv);
                }
                if (TextUtils.isEmpty(bean.jianshehegelv)) {
                    tv_tag003.setText("0");
                    tv_tag03.setVisibility(View.GONE);
                } else {
                    tv_tag003.setText(bean.jiansheheInsp + "项");
                    tv_tag03.setVisibility(View.VISIBLE);
                    tv_tag03.setText(bean.jianshehegelv);
                    setTextColor(tv_tag03, bean.jianshehegelv);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.putExtra("sectionId",sectionId);
                        intent.putExtra("sectionName",sectionName);
                        intent.putExtra("towerId",bean.towerId+"");
                        intent.putExtra("towerName",bean.towerName);
                        intent.setClass(context, SCSLFormDetailActivity.class);
                        context.startActivity(intent);
                    }
                });
                break;
            case TYPE_TOTAL:
                final FormSCSLTotalBean totalBean = (FormSCSLTotalBean) item;
                TextView tv_tag0 = holder.getView(R.id.tv_tag0);
                TextView tv_tag1 = holder.getView(R.id.tv_tag1);
                TextView tv_tag2 = holder.getView(R.id.tv_tag2);
                if (TextUtils.isEmpty(totalBean.shigongZongji)) {
                    tv_tag0.setText("0");
                } else {
                    tv_tag0.setText(totalBean.shigongZongji);
                    setTextColor(tv_tag0, totalBean.shigongZongji);
                }
                if (TextUtils.isEmpty(totalBean.jianliZongji)) {
                    tv_tag1.setText("0");
                } else {
                    tv_tag1.setText(totalBean.jianliZongji);
                    setTextColor(tv_tag1, totalBean.jianliZongji);
                }
                if (TextUtils.isEmpty(totalBean.jiansheZongji)) {
                    tv_tag2.setText("0");
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
