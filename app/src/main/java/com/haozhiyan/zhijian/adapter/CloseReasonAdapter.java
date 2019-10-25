package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

public class CloseReasonAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int select = -1;

    public CloseReasonAdapter(@Nullable List<String> data) {
        super(R.layout.close_reason_item, data);
    }

    public void setSelect(int select) {
        this.select = select;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (select == helper.getLayoutPosition()) {
            helper.getView(R.id.checkImg).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.checkImg).setVisibility(View.GONE);
        }
        helper.setText(R.id.tv, item);

    }
}
