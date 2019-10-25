package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.FeedBackListBean;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/23.
 * Describe: Ydzj_project
 */
public class FeedBackHistoryAdapter extends BaseQuickAdapter<FeedBackListBean.ListBean, BaseViewHolder> {

    private Context context;

    public FeedBackHistoryAdapter(@Nullable List<FeedBackListBean.ListBean> data, Context context) {
        super(R.layout.feed_back_list_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FeedBackListBean.ListBean data) {
        RecyclerView childRv = helper.getView(R.id.iv_feedBack);
        if (childRv.getLayoutManager() == null) {
            childRv.setLayoutManager(new GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL, false));
            childRv.addItemDecoration(new GridSpacingItemDecoration(4, 1, false));
        }
        if (ListUtils.listEmpty(data.childPP)) {
            childRv.setAdapter(new FeedBackImageAdapter(data.childPP));
        } else {
            helper.setGone(R.id.iv_feedBack, false);
        }
        helper.setText(R.id.tv_feedBackContent, data.feedback)
                .setText(R.id.tv_feedBackTime, data.creatorTime)
                .setText(R.id.tv_CustomerServiceFeedBack, data.serviceMessage)
                .setText(R.id.tv_CustomerServiceFeedBackTime, data.creatorTime)
                .setGone(R.id.ll_CustomerService, getServiceMess(data));
    }

    private boolean getServiceMess(FeedBackListBean.ListBean item) {
        if (StringUtils.isEmpty(item.serviceMessage)) {
            return false;
        } else {
            return true;
        }
    }
}
