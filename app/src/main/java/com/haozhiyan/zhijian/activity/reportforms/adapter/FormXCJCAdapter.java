package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormXCJCBean;
import com.haozhiyan.zhijian.utils.DensityUtil;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 * 报表-现场检查适配器
 */
public class FormXCJCAdapter extends BaseQuickAdapter<FormXCJCBean, BaseViewHolder> {
    private Context context;

    public FormXCJCAdapter(Context context, @Nullable List<FormXCJCBean> data) {
        super(R.layout.item_form_xcjc, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FormXCJCBean item) {
        View view_rate = helper.getView(R.id.view_rate);
        View view_tag0 = helper.getView(R.id.view_tag0);
        View view_tag1 = helper.getView(R.id.view_tag1);
        View view_tag2 = helper.getView(R.id.view_tag2);
        View view_tag3 = helper.getView(R.id.view_tag3);
        LinearLayout.LayoutParams tag0LayoutParams = (LinearLayout.LayoutParams) view_tag0.getLayoutParams();
        LinearLayout.LayoutParams tag1LayoutParams = (LinearLayout.LayoutParams) view_tag1.getLayoutParams();
        LinearLayout.LayoutParams tag2LayoutParams = (LinearLayout.LayoutParams) view_tag2.getLayoutParams();
        LinearLayout.LayoutParams tag3LayoutParams = (LinearLayout.LayoutParams) view_tag3.getLayoutParams();
        int total = 200;//进度条总长度200dp
        tag0LayoutParams.width = DensityUtil.dip2px(context, getDivide(item.getYitongguo(), item.getTotal()) * total);
        tag1LayoutParams.width = DensityUtil.dip2px(context, getDivide(item.getDaifuyan(), item.getTotal()) * total);
        tag2LayoutParams.width = DensityUtil.dip2px(context, getDivide(item.getDaizhenggai(), item.getTotal()) * total);
        tag3LayoutParams.width = DensityUtil.dip2px(context, getDivide(item.getGuanbi(), item.getTotal()) * total);
        if (item.getGuanbi() == 0) {
            view_tag2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_orange_right_corner));
            if (item.getDaizhenggai() == 0) {
                view_tag1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_green_deep_right_corner));
                if (item.getDaifuyan() == 0) {
                    view_tag0.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_green_corner));
                }
            }
        }
        if (item.getYitongguo() == 0) {
            view_tag1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_green_deep_left_corner));
            if (item.getDaifuyan() == 0) {
                view_tag2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_orange_left_corner));
                if (item.getDaizhenggai() == 0) {
                    view_tag3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_gray));
                }
            }
        }
        if (item.getYitongguo() == item.getTotal()) {
            view_tag0.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_green_corner));
        }
        if (item.getDaifuyan() == item.getTotal()) {
            view_tag1.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_green_deep));
        }
        if (item.getDaizhenggai() == item.getTotal()) {
            view_tag2.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_orange));
        }
        if (item.getGuanbi() == item.getTotal()) {
            view_tag3.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_gray));
        }
        RelativeLayout.LayoutParams rateParams = (RelativeLayout.LayoutParams) view_rate.getLayoutParams();
        rateParams.setMargins(DensityUtil.dip2px(context, getDivide(item.getYitongguo() + item.getDaifuyan(), item.getTotal()) * total), 0, 0, 0);
        view_tag0.setLayoutParams(tag0LayoutParams);
        view_tag1.setLayoutParams(tag1LayoutParams);
        view_tag2.setLayoutParams(tag2LayoutParams);
        view_tag3.setLayoutParams(tag3LayoutParams);
        TextView tv_yizhenggai = helper.getView(R.id.tv_yizhenggai);
        //tv_yizhenggai.setText(getPercent(item.getYitongguo() + item.getDaifuyan(), item.getTotal()) + "已整改");
        tv_yizhenggai.setText(item.getYizheggai());
        helper.setText(R.id.tv_ytg_number, item.getYitongguo() + "");
        helper.setText(R.id.tv_dfy_number, item.getDaifuyan() + "");
        helper.setText(R.id.tv_dzg_number, item.getDaizhenggai() + "");
        helper.setText(R.id.tv_fzcgb_number, item.getGuanbi() + "");
//        helper.setText(R.id.tv_ytg_percent, "已通过" + getPercent(item.getYitongguo(), item.getTotal()));
//        helper.setText(R.id.tv_dfy_percent, "待复验" + getPercent(item.getDaifuyan(), item.getTotal()));
//        helper.setText(R.id.tv_dzg_percent, "待整改" + getPercent(item.getDaizhenggai(), item.getTotal()));
//        helper.setText(R.id.tv_fzcgb_percent, "非正常关闭" + getPercent(item.getGuanbi(), item.getTotal()));
        helper.setText(R.id.tv_ytg_percent, item.getYitongguobl());
        helper.setText(R.id.tv_dfy_percent, item.getDaifuyanbl());
        helper.setText(R.id.tv_dzg_percent, item.getDaizhenggaibl());
        helper.setText(R.id.tv_fzcgb_percent, item.getGuanbibl());
        helper.setText(R.id.tv_total, item.getTotal() + "");

    }

    /**
     * 计算百分比
     */
    private String getPercent(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal divide = b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP);
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format(divide.doubleValue());
        return result;
    }

    /**
     * 除法
     */
    private float getDivide(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal divide = b1.divide(b2, 3, BigDecimal.ROUND_HALF_UP);
        return divide.floatValue();
    }
}
