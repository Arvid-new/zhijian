package com.haozhiyan.zhijian.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.HomeProjectBean;

/**
 * 首页的项目列表
 */
public class HomeProjectAdapter extends BaseQuickAdapter<HomeProjectBean.DataBean, BaseViewHolder> {

    private int selectID = -1;

    public HomeProjectAdapter() {
        super(R.layout.homeprojectitem);
    }

    public void setSelectID(int selectID) {
        this.selectID = selectID;
    }

    @Override
    protected void convert(BaseViewHolder holder, HomeProjectBean.DataBean item) {
        holder.setText(R.id.tv, item.getProject());
        if (holder.getLayoutPosition() == selectID) {
            holder.getView(R.id.img).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.img).setVisibility(View.INVISIBLE);
        }

    }
}
