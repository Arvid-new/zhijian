package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;


public class SelectProjectAdapter extends CommonAdapter<ItemBean> {

    private int id = -1;
    public SelectProjectAdapter(Context context, List<ItemBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, ItemBean data, int position) {
        TextView tv = holder.getView(R.id.tv_text);
        tv.setText(data.name);
        if (id  == data.id) {
            tv.setTextColor(UiUtils.getColor(R.color.blue_line));
            holder.getView(R.id.line).setVisibility(View.VISIBLE);
        } else {
            tv.setTextColor(UiUtils.getColor(R.color.black_3));
            holder.getView(R.id.line).setVisibility(View.INVISIBLE);
        }
        if (StringUtils.isEmpty(data.type)) {
            holder.getView(R.id.tv_type).setVisibility(View.GONE);
        } else {
            holder.getView(R.id.tv_type).setVisibility(View.VISIBLE);
            ((TextView) holder.getView(R.id.tv_type)).setText(data.type);
        }
    }
    public void setSelectedById(int id) {
        this.id = id;
        notifyDataSetChanged();
    }
}