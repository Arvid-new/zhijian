package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.GxyjUnitBean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/20.
 * Describe: 单元适配器
 */
public class GxyjUnitAdapter extends BaseQuickAdapter<GxyjUnitBean.ListBean, BaseViewHolder> {

    private int index = -1;
    private String name = "";
    public GxyjUnitAdapter(@Nullable List<GxyjUnitBean.ListBean> list, String name) {
        super(R.layout.place_list_item, list);
        this.name = name;
    }

    @Override
    protected void convert(BaseViewHolder helper, GxyjUnitBean.ListBean item) {
        TextView itemText = helper.getView(R.id.tv_place);
        itemText.setText(item.unit+"单元");
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
}
