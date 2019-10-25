package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

public class MaterialsNameDlgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int selectItem = -1;

    public MaterialsNameDlgAdapter(@Nullable List<String> data) {
        super(R.layout.materials_name_dlg_item, data);
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.roomName, item);
        if (selectItem == holder.getLayoutPosition()) {
            holder.getView(R.id.checkImg).setVisibility(View.VISIBLE);
            holder.getView(R.id.roomName).setBackgroundResource(R.drawable.blue_wash_2radius_back);
            holder.setTextColor(R.id.roomName, 0xff3e8efa);
        } else {
            holder.getView(R.id.checkImg).setVisibility(View.GONE);
            holder.getView(R.id.roomName).setBackgroundResource(R.drawable.gray_wash_2radius_back);
            holder.setTextColor(R.id.roomName, 0xff959595);
        }
    }
}
