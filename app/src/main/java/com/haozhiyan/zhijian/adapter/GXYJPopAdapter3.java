package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.biaoduan.InspectionTwoBean;

import java.util.List;

public class GXYJPopAdapter3 extends BaseQuickAdapter<InspectionTwoBean, BaseViewHolder> {


    public GXYJPopAdapter3(@Nullable List<InspectionTwoBean> data) {
        super(R.layout.scsl_list_item, data);
    }

    private int selectItem = 0;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public int getSelectItem() {
        return selectItem;
    }

    @Override
    protected void convert(BaseViewHolder helper, InspectionTwoBean item) {
        helper.setText(R.id.tv_text, item.getInspectionName());
        helper.setText(R.id.tv_type, item.getPartsDivision());
        helper.getView(R.id.tv_type).setVisibility(View.VISIBLE);
        if (selectItem == helper.getLayoutPosition()) {
            helper.setTextColor(R.id.tv_text, Color.parseColor("#4678E8"));
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        } else {
            helper.setTextColor(R.id.tv_text, Color.parseColor("#232323"));
            helper.getView(R.id.line).setVisibility(View.GONE);
        }

    }
}
