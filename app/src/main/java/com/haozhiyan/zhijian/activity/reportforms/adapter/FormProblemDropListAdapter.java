package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormXCJCDetailBean;
import com.haozhiyan.zhijian.activity.reportforms.bean.ProblemBean;

import java.util.List;


/**
 * 报表-现场检查-下拉问题列表适配器
 */
public class FormProblemDropListAdapter extends BaseQuickAdapter<ProblemBean, BaseViewHolder> {
    private Context context;
    private int position = -1;

    public FormProblemDropListAdapter(Context context, @Nullable List<ProblemBean> data) {
        super(R.layout.item_form_xcjc_problem_drop_list, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProblemBean item) {
        if(TextUtils.equals("全部问题",item.getInspectionName())){
            helper.setText(R.id.tv_problem_number, item.getShuliang()+"个");
        }else{
            helper.setText(R.id.tv_problem_number, item.getShuliang()+"个"+","+item.getBili());
        }
        TextView tv_problem_name = helper.getView(R.id.tv_problem_name);
        tv_problem_name.setText(item.getInspectionName());
        if (position == helper.getAdapterPosition()) {
            tv_problem_name.setTextColor(ContextCompat.getColor(context, R.color.blue4));
        } else {
            tv_problem_name.setTextColor(ContextCompat.getColor(context, R.color.black2));
        }
    }

    public void setSelectItem(int position) {
        this.position = position;
        notifyDataSetChanged();
    }
}