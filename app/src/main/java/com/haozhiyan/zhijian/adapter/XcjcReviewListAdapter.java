package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.bean.ProblemDetailBean;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;

import java.util.List;

public class XcjcReviewListAdapter extends BaseQuickAdapter<ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean, BaseViewHolder> {
    private Context context;

    public XcjcReviewListAdapter(Context context, @Nullable List<ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean> data) {
        super(R.layout.reviewlist_itam, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean item) {
        helper.setText(R.id.reviewNameTV, item.peopleuser + "");

        if (StringUtils.isEmpty(item.reviewDate)) {
            helper.getView(R.id.acceptanceDateTV).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.acceptanceDateTV).setVisibility(View.VISIBLE);
            helper.setText(R.id.acceptanceDateTV, item.reviewDate + "");
        }

        if (StringUtils.isEmpty(item.reviewSupplement)) {
            helper.getView(R.id.reviewSupplementTV).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.reviewSupplementTV).setVisibility(View.VISIBLE);
            helper.setText(R.id.reviewSupplementTV, item.reviewSupplement + "");
        }
        if ("0".equals(item.isReview)) {
            helper.setText(R.id.isReviewTV, "尚未复验");
        } else {
            helper.setText(R.id.isReviewTV, "");
        }
        if (item.reviewImageList != null && item.reviewImageList.size() > 0) {
            helper.getView(R.id.acceptanceImageLL).setVisibility(View.VISIBLE);
            setPicRcv((RecyclerView) helper.getView(R.id.reviewImageListRCV), item.reviewImageList);
        } else {
            helper.getView(R.id.acceptanceImageLL).setVisibility(View.GONE);
        }
        helper.getView(R.id.reviewNameTalkImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SystemUtils.callPage(item.tel, context);
            }
        });
    }


    /**
     * 设置 仅 展示图片的 RCV
     *
     * @param picRcv
     * @param imgs
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        if (imgs != null && imgs.size() > 0) {
            picRcv.setLayoutManager(new GridLayoutManager(context, 2));
            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(imgs, context);
            picRcv.setAdapter(picShowListAdapter);
            picShowListAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (PVAUtils.getFileLastType(imgs.get(position)).equals("image/jpeg")) {
                        ShowBigImg.build(context, imgs.get(position));
                    } else {
                        ShowVideo.playLineVideo(context, imgs.get(position));
                    }
                }
            });
        }
    }
}
