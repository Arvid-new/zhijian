package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

public class GXYJPopAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public GXYJPopAdapter(@Nullable List<String> data) {
        super(R.layout.scsl_list_item, data);
    }

    private int selectItem = -1;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public int getSelectItem() {
        return selectItem;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_text, item);
        if (selectItem == helper.getLayoutPosition()) {
            helper.setTextColor(R.id.tv_text, Color.parseColor("#4678E8"));
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        } else {
            helper.setTextColor(R.id.tv_text, Color.parseColor("#232323"));
            helper.getView(R.id.line).setVisibility(View.GONE);
        }

    }
}
