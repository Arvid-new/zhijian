package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.scsl.SCSLDBBean;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/18.
 * Describe:
 */
public class SCSLDBAdapter extends BaseQuickAdapter<SCSLDBBean.ListBean.ListResultBean, BaseViewHolder> {

    private Context context;

    public SCSLDBAdapter(@Nullable List<SCSLDBBean.ListBean.ListResultBean> data, Context ctx) {
        super(R.layout.scsl_yi_ban_item, data);
        this.context = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper,SCSLDBBean.ListBean.ListResultBean bean) {
        helper.setText(R.id.projectName, bean.detailsName);
        helper.setText(R.id.descrip, bean.towerName + bean.unitName + bean.floorName + bean.handOverPart);
        helper.setText(R.id.date_address, bean.creatorTime);
        try {
            helper.setText(R.id.projectStateTv, bean.scslState);
            switch (bean.scslState) {
                case "待整改":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.red));
                    break;
                case "已整改":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.green2));
                    break;
                case "待复验":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.yellow2));
                    break;
                case "已完成":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.blue));
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPic(SimpleDraweeView sdv, String imgUrl) {
        if (!StringUtils.isEmpty(imgUrl)) {
            new ImageRequest(context).get(imgUrl, sdv);
        } else {
            sdv.setActualImageResource(R.drawable.icon_no_img);
        }
    }
//    private void setLocalInfo(SCSLDBBean.ListBean.ListResultBean data){
//        if(){
//
//        }
//    }
}
