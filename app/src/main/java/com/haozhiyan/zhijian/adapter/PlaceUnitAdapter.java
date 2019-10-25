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
 * Describe: 单元适配器
 */
public class PlaceUnitAdapter extends BaseQuickAdapter<PlaceNewBean.ListBean.UnitChildBean, BaseViewHolder> {

    private int index = -1;
    private String name = "";
    public PlaceUnitAdapter(@Nullable List<PlaceNewBean.ListBean.UnitChildBean> list,String name) {
        super(R.layout.place_list_item, list);
        this.name = name;
    }

    @Override
    protected void convert(BaseViewHolder helper, PlaceNewBean.ListBean.UnitChildBean item) {
        TextView itemText = helper.getView(R.id.tv_place);
        itemText.setText(item.unit);
        if (index < 0) {
            if (TextUtils.equals(item.unit, name)) {
                itemText.setEnabled(true);
                itemText.setTextColor(Color.parseColor("#ffffff"));
            }
        }else{
            changeText(helper.getAdapterPosition(), itemText);
        }
    }

    private void changeText(int position, TextView itemText) {
        if (index == position) {
            itemText.setEnabled(true);
            itemText.setTextColor(Color.parseColor("#ffffff"));
        } else {
            itemText.setEnabled(false);
            itemText.setTextColor(Color.parseColor("#333333"));
        }
    }

    public void setSelectStatus(int position) {
        this.index = position;
        notifyDataSetChanged();
    }
//    @Override
//    public void convert(CommonViewHolder viewHolder, PlaceNewBean.ListBean.UnitChildBean data, int position) {
//        TextView itemText = viewHolder.getView(R.id.tv_place);
//        itemText.setText(data.unit);
//        changeText(position, itemText);
//    }
}
