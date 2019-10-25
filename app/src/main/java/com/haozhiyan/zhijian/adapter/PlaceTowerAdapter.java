package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.PlaceNewBean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/7.
 * Describe: 楼栋适配器
 */
public class PlaceTowerAdapter extends BaseQuickAdapter<PlaceNewBean.ListBean, BaseViewHolder> {

    private int index = -1;
    private String name = "";

    public PlaceTowerAdapter(@Nullable List<PlaceNewBean.ListBean> list, String name) {
        super(R.layout.place_list_item, list);
        this.name = name;
    }

    @Override
    protected void convert(BaseViewHolder helper, PlaceNewBean.ListBean item) {
        TextView itemText = helper.getView(R.id.tv_place);
        itemText.setText(item.tower);

        if (index < 0) {
            if (TextUtils.equals(item.tower, name)) {
                itemText.setEnabled(true);
                itemText.setTextColor(Color.parseColor("#ffffff"));
            }
        } else {
            if (index == helper.getAdapterPosition()) {
                itemText.setEnabled(true);
                itemText.setTextColor(Color.parseColor("#ffffff"));
            } else {
                itemText.setEnabled(false);
                itemText.setTextColor(Color.parseColor("#333333"));
            }
        }
    }

    public void setSelectStatus(int position) {
        this.index = position;
        notifyDataSetChanged();
    }
}
