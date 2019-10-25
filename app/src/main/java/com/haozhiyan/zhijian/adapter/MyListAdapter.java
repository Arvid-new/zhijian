package com.haozhiyan.zhijian.adapter;

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

/**
 * Created by WangZhenKai on 2019/5/15.
 * Describe: Ydzj_project
 */
public class MyListAdapter extends CommonAdapter<ItemBean> {

    //type0—list1  type1—list2  type2—list3
    private int index = -1;

    public MyListAdapter(Context context, List<ItemBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder holder, ItemBean data, int position) {
        TextView tv = holder.getView(R.id.tv_text);
        tv.setText(data.name);
        if (index == position) {
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

    public void setSelected(int position) {
        this.index = position;
        notifyDataSetChanged();
    }
    public int getSelected() {
        return index;
    }
}
