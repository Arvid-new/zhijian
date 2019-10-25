package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.WorkPageBean;
import com.haozhiyan.zhijian.utils.UiUtils;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/25.
 * Describe:
 */
public class WorkNoticeAdapter extends BaseQuickAdapter<WorkPageBean.NewsDataBean, BaseViewHolder> {

    public WorkNoticeAdapter(@Nullable List<WorkPageBean.NewsDataBean> data) {
        super(R.layout.work_notice_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkPageBean.NewsDataBean item) {
        helper.setText(R.id.tv_label, item.newsHead)
                .setText(R.id.tv_date, item.createTime)
                .setText(R.id.tv_content, item.news)
                .setGone(R.id.tv_label, UiUtils.isEmpty(item.newsHead))
                .setGone(R.id.tv_date, UiUtils.isEmpty(item.createTime));
    }
}
