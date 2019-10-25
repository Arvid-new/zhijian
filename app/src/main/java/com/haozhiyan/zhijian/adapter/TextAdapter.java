package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/29.
 * Describe: Ydzj_project
 */
public class TextAdapter extends CommonAdapter<ItemBean> {

    private int index = -1;

    public TextAdapter(Context context, List<ItemBean> list) {
        super(context, list, R.layout.function_item);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ItemBean data, int position) {
        ((TextView) viewHolder.getView(R.id.tv_content)).setText(data.name);
        ImageView ivDui = viewHolder.getView(R.id.ivDui);
        ImageView ivDui2 = viewHolder.getView(R.id.ivDui2);
        if (index == position) {
            ivDui.setVisibility(View.VISIBLE);
        } else {
            ivDui.setVisibility(View.GONE);
        }
        if (data.isCheck) {
            ivDui2.setVisibility(View.VISIBLE);
        } else {
            ivDui2.setVisibility(View.GONE);
        }
    }

    public void setSelect(int position) {
        index = position;
        notifyDataSetChanged();
    }
}
