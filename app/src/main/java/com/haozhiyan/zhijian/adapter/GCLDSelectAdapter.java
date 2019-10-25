package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.GCLDProjectBean;

import java.util.List;

public class GCLDSelectAdapter extends BaseQuickAdapter<GCLDProjectBean, BaseViewHolder> {
    private String selectName;
    private int selectItem = -1;

    public GCLDSelectAdapter(@Nullable List<GCLDProjectBean> data, String selectName) {
        super(R.layout.gcld_select_item, data);
        this.selectName = selectName;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    protected void convert(BaseViewHolder helper, GCLDProjectBean item) {

        helper.setText(R.id.tv_content, item.project);
        if (!TextUtils.isEmpty(selectName) && selectName.equals(item.project)) {
            helper.getView(R.id.ivDui).setVisibility(View.VISIBLE);
        } else {
            if (selectItem == helper.getLayoutPosition()) {
                helper.getView(R.id.ivDui).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.ivDui).setVisibility(View.GONE);
            }
        }

    }
}
