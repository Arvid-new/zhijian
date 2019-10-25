package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormXCJCDetailBean;
import com.haozhiyan.zhijian.utils.ImageRequest;

import java.util.List;


/**
 * 报表-现场检查-详情列表适配器
 */
public class FormProblemListAdapter extends BaseQuickAdapter<FormXCJCDetailBean, BaseViewHolder> {
    private Context context;

    public FormProblemListAdapter(Context context, @Nullable List<FormXCJCDetailBean> data) {
        super(R.layout.item_form_xcjc_problem_list, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FormXCJCDetailBean item) {
        //state:状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
        helper.setText(R.id.tv_title, item.getInspectionName());
        helper.setText(R.id.tv_content, item.getParticularsName());
        helper.setText(R.id.tv_date, item.getSubmitDate());
        ImageView iv_chao = helper.getView(R.id.iv_chao);
        TextView tv_ji = helper.getView(R.id.tv_ji);
        ImageView iv_tui = helper.getView(R.id.iv_tui);
        new ImageRequest(context).get(item.getProblemImage(), (ImageView) helper.getView(R.id.iv_picture));
        TextView tv_status = helper.getView(R.id.tv_status);
        String state = item.getState();
        switch (state) {
            case "1":
                tv_status.setText("待整改");
                tv_status.setTextColor(ContextCompat.getColor(context,R.color.red2));
                break;
            case "2":
                tv_status.setText("待复验");
                tv_status.setTextColor(ContextCompat.getColor(context,R.color.orange));
                break;
            case "3":
                tv_status.setText("非正常关闭");
                tv_status.setTextColor(ContextCompat.getColor(context,R.color.c6_color));
                break;
            case "4":
                tv_status.setText("已通过");
                tv_status.setTextColor(ContextCompat.getColor(context,R.color.green2));
                break;
            default:
                break;
        }
        if (TextUtils.equals("一般", item.getSerious())) {
            tv_ji.setVisibility(View.GONE);
        } else  {
            tv_ji.setVisibility(View.VISIBLE);
        }
        String timeTout = item.getIsTimeout()+"";
        if (timeTout.startsWith("-")) {
            iv_chao.setVisibility(View.VISIBLE);
        } else  {
            iv_chao.setVisibility(View.GONE);
        }
        if (item.getBackNumber()>0) {
            iv_tui.setVisibility(View.VISIBLE);
        } else  {
            iv_tui.setVisibility(View.GONE);
        }
    }

}