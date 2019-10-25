package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/22.
 * Describe: Ydzj_project
 */
public class SCSLCollectDetailAdapter extends BaseQuickAdapter<ItemBean,BaseViewHolder> {

    public SCSLCollectDetailAdapter(int layoutResId, @Nullable List<ItemBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, ItemBean item) {
        helper.setText(R.id.tv_item,item.getName());
    }
}
