package com.haozhiyan.zhijian.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.HomeProjectListBean;

/**
 * 首页的项目列表
 */
public class HomeProjectListChildAdapter extends BaseQuickAdapter<HomeProjectListBean.DataBean.ListBean, BaseViewHolder> {

    public HomeProjectListChildAdapter() {
        super(R.layout.homeprojectlistitemchild);
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeProjectListBean.DataBean.ListBean item) {
        holder.setText(R.id.tv, item.getName());

    }
}
