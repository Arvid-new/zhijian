package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/23.
 * Describe: Ydzj_project
 */
public class FeedBackImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public FeedBackImageAdapter(@Nullable List<String> data) {
        super(R.layout.image_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ((SimpleDraweeView) helper.getView(R.id.photoImages)).setImageURI(item);
    }
}
