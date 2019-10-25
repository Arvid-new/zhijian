package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.XcjcSeverityProblem;
import com.haozhiyan.zhijian.utils.UiUtils;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/26.
 * Describe: Ydzj_project
 */
public class ServerProblemAdapter extends BaseQuickAdapter<XcjcSeverityProblem, BaseViewHolder> {

    private int index = -1;

    public ServerProblemAdapter(@Nullable List<XcjcSeverityProblem> data) {
        super(R.layout.server_problem_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, XcjcSeverityProblem item) {
        TextView tv = helper.getView(R.id.tv_status);
        tv.setText(item.severityName);
        if (index == helper.getAdapterPosition()) {
            tv.setSelected(true);
            tv.setTextColor(UiUtils.getColor(R.color.white));
        } else {
            tv.setSelected(false);
            tv.setTextColor(UiUtils.getColor(R.color.black_3));
        }
    }

    public void selected(int position) {
        this.index = position;
        notifyDataSetChanged();
    }
}
