package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.biaoduan.InspectionOneBean;

import java.util.List;

public class GXYJPopAdapter2 extends BaseQuickAdapter<InspectionOneBean, BaseViewHolder> {

    private String lastSelect;

    public GXYJPopAdapter2(@Nullable List<InspectionOneBean> data, String lastSelect) {
        super(R.layout.scsl_list_item, data);
        this.lastSelect = lastSelect;
    }
    public GXYJPopAdapter2(@Nullable List<InspectionOneBean> data) {
        super(R.layout.scsl_list_item, data);
        this.lastSelect = lastSelect;
    }

    private int selectItem = 0;

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public int getSelectItem() {
        return selectItem;
    }

    @Override
    protected void convert(BaseViewHolder helper,InspectionOneBean item) {
        helper.setText(R.id.tv_text, item.getInspectionName());
        if (lastSelect != null && lastSelect.equals(item.getInspectionName())) {
            helper.setTextColor(R.id.tv_text, Color.parseColor("#4678E8"));
            helper.getView(R.id.line).setVisibility(View.VISIBLE);
        } else {
            if (selectItem == helper.getLayoutPosition()) {
                helper.setTextColor(R.id.tv_text, Color.parseColor("#4678E8"));
                helper.getView(R.id.line).setVisibility(View.VISIBLE);
            } else {
                helper.setTextColor(R.id.tv_text, Color.parseColor("#232323"));
                helper.getView(R.id.line).setVisibility(View.GONE);
            }

        }

    }
}
