package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.ReportFormBean;

import java.util.List;
/**
 * 报表适配器
 * */
public class ReportFormAdapter extends BaseQuickAdapter<ReportFormBean.ModulesDataBean,BaseViewHolder> {
    private Context context;

    public ReportFormAdapter(Context context, @Nullable List<ReportFormBean.ModulesDataBean> data) {
        super(R.layout.item_report_form, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ReportFormBean.ModulesDataBean item) {
        helper.setText(R.id.tv_do_type,item.name);
       // helper.setText(R.id.tv_do_content,item.content);
        ImageView iv_type_icon = helper.getView(R.id.iv_type_icon);
        iv_type_icon.setImageResource(item.typeIcon);
    }
}
