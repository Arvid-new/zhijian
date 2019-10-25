package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.DaiBanCLYS1;
import com.haozhiyan.zhijian.bean.DaiBanGXYJListBean;
import com.haozhiyan.zhijian.bean.DaiBanTypeListBean2;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.StringUtils;

public class DaiBanTypeListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> {

    private Context context;

    public DaiBanTypeListAdapter(Context context) {
        super(R.layout.daibantypeitem);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        if (item instanceof DaiBanTypeListBean2.DoProblemListBean) {
            DaiBanTypeListBean2.DoProblemListBean bean = (DaiBanTypeListBean2.DoProblemListBean) item;
            SimpleDraweeView sdv = helper.getView(R.id.img);
            setPic(sdv, bean.problemImage);
            helper.setText(R.id.projectName, bean.inspectionName);
            helper.setText(R.id.descrip, bean.particularsName);
            String part = (bean.towerName + bean.unitName + "单元" + bean.roomName).replace("null", "").trim();
            String datadd = String.valueOf(bean.submitDate + "\t\t" +
                    (part.equals("单元") ? "" : part)).replace("null", "");
            helper.setText(R.id.date_address, datadd);

            if (bean.isTimeout < 0) {
                helper.getView(R.id.chaoImg).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.chaoImg).setVisibility(View.GONE);
            }
            if (bean.backNumber > 0) {
                helper.getView(R.id.tuiImg).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.tuiImg).setVisibility(View.GONE);
            }
//            if ("2".equals(bean.serious) || "3".equals(bean.serious) || "4".equals(bean.serious)) {
//                helper.getView(R.id.jiImg).setVisibility(View.VISIBLE);
//            } else {
//                helper.getView(R.id.jiImg).setVisibility(View.GONE);
//            }
            if (TextUtils.isEmpty(bean.serious) || "一般".equals(bean.serious)) {
                helper.getView(R.id.jiImg).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.jiImg).setVisibility(View.VISIBLE);
            }
            try {
                switch (bean.state) {
                    case "1":
                        helper.setText(R.id.projectStateTv, "待整改");
                        helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.red));
                        break;
                    case "2":
                        helper.setText(R.id.projectStateTv, "待复验");
                        helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.yellow));
                        break;
                    case "3":
                        helper.setText(R.id.projectStateTv, "非正常关闭");
                        helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.text_color2));
                        break;
                    case "4":
                        helper.setText(R.id.projectStateTv, "已通过");
                        helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.blue));
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (item instanceof DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) {
            DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean bean = (DaiBanGXYJListBean.ListBean.ListDaiZhenggaiBean) item;
            SimpleDraweeView sdv = helper.getView(R.id.img);
            setPic(sdv, bean.getIssuePicture());
            helper.setText(R.id.projectName, bean.getItemsName());
            helper.setText(R.id.descrip, bean.getDescription());
            helper.setText(R.id.date_address, bean.getCreatorTime());
            helper.setText(R.id.projectStateTv, bean.getZtCondition());
            switch (bean.getZtCondition()) {
                case "已退回":
                case "待整改":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.red));
                    break;
                case "待验收":
                case "待复验":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.yellow));
                    break;
                case "已完成":
                case "已验收":
                case "已通过":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.blue));
                    break;
            }

        } else if (item instanceof DaiBanGXYJListBean.ListBean.ListDaibanBean) {
            DaiBanGXYJListBean.ListBean.ListDaibanBean bean = (DaiBanGXYJListBean.ListBean.ListDaibanBean) item;
            SimpleDraweeView sdv = helper.getView(R.id.img);
            setPic(sdv, bean.getPictureVideo());
            helper.setText(R.id.projectName, bean.getDetailsName());
            helper.setText(R.id.descrip, bean.getSiteName() + bean.getHandOverPart());
            helper.setText(R.id.date_address, bean.getCreationTime());
            helper.setText(R.id.projectStateTv, bean.getIdentifying());
            switch (bean.getIdentifying()) {
                case "已退回":
                case "待整改":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.red));
                    break;
                case "待验收":
                case "待复验":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.yellow));
                    break;
                case "已完成":
                case "已验收":
                case "已通过":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.blue));
                    break;
            }
        } else if (item instanceof DaiBanCLYS1.DoClysListBean) {
            DaiBanCLYS1.DoClysListBean bean = (DaiBanCLYS1.DoClysListBean) item;
            SimpleDraweeView sdv = helper.getView(R.id.img);
            setPic(sdv, bean.getReceiveVehicleImage());
            helper.setText(R.id.projectName, bean.getNameInspection());
            helper.setText(R.id.descrip, bean.getSectionName() + bean.getNumber() + "进场");
            helper.setText(R.id.date_address, bean.getSubmitDate());
            helper.setText(R.id.projectStateTv, bean.getStateName());
            //"状态(，3已验收，5待上传报告，")
            switch (bean.getState()) {
                case "1"://1待申请进场，2待验收，7待退场
                case "2"://
                case "7"://
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.red));
                    break;
                case "4"://4待送检，，6待复验
                case "6"://
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.yellow));
                    break;
                case "8"://8已退场，9送检合格，10复验合格)
                    helper.setTextColor(R.id.projectStateTv, Color.parseColor("#A7A7A7"));
                    break;
                case "9":
                case "10":
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.blue));
                    break;
                default:
                    helper.setTextColor(R.id.projectStateTv, ContextCompat.getColor(context, R.color.red));
                    break;
            }
        }
    }

    private void setPic(SimpleDraweeView sdv, String imgUrl) {
        try {
            if (!StringUtils.isEmpty(imgUrl)) {
                new ImageRequest(context).get(imgUrl, sdv);
            } else {
                sdv.setActualImageResource(R.drawable.icon_no_img);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
