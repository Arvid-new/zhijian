package com.haozhiyan.zhijian.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

public class SingleCheckAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private String selectID;
    private List<String> ids;

    public SingleCheckAdapter(@Nullable List<String> data, List<String> ids, String selectID) {
        super(R.layout.accountabilityunit_item, data);
        this.selectID = selectID;
        this.ids = ids;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            if (ids.get(helper.getLayoutPosition()).equals(selectID)) {
                helper.setVisible(R.id.iv, true);
                helper.setTextColor(R.id.tv, Color.parseColor("#666666"));
            } else {
                helper.setTextColor(R.id.tv, Color.parseColor("#232323"));
                helper.setVisible(R.id.iv, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            helper.setTextColor(R.id.tv, Color.parseColor("#232323"));
            helper.setVisible(R.id.iv, false);
        }
        helper.setText(R.id.tv, item);
    }
}
