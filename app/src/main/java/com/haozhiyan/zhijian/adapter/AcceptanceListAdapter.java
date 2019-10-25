package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.bean.AMEnterAreaBean;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;

import java.util.List;

public class AcceptanceListAdapter extends BaseQuickAdapter<AMEnterAreaBean.ApproachBean.AcceptanceListBean, BaseViewHolder> {
    private Context context;

    public AcceptanceListAdapter(Context context, @Nullable List<AMEnterAreaBean.ApproachBean.AcceptanceListBean> data) {
        super(R.layout.acceptancelist_itam, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final AMEnterAreaBean.ApproachBean.AcceptanceListBean item) {
        helper.setText(R.id.acceptanceNameTV, item.peopleuser + "");
        if (StringUtils.isEmpty(item.acceptanceDate)) {
            helper.getView(R.id.acceptanceDateTV).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.acceptanceDateTV).setVisibility(View.VISIBLE);
            helper.setText(R.id.acceptanceDateTV, item.acceptanceDate + "");
        }
        if (StringUtils.isEmpty(item.acceptanceSupplement)) {
            helper.getView(R.id.acceptanceSupplementTV).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.acceptanceSupplementTV).setVisibility(View.VISIBLE);
            helper.setText(R.id.acceptanceSupplementTV, item.acceptanceSupplement + "");
        }
        if ("0".equals(item.isQualified)) {
            helper.setText(R.id.isQualifiedTV, "不合格");
            helper.setTextColor(R.id.isQualifiedTV, ContextCompat.getColor(context, R.color.red2));
        } else if ("1".equals(item.isQualified)) {
            helper.setText(R.id.isQualifiedTV, "合格");
        } else {
            helper.setText(R.id.isQualifiedTV, "尚未验收");
        }
        if (item.acceptanceImageList != null && item.acceptanceImageList.size() > 0) {
            helper.getView(R.id.acceptanceImageLL).setVisibility(View.VISIBLE);
            setPicRcv((RecyclerView) helper.getView(R.id.acceptanceImageRCV), item.acceptanceImageList);
        } else {
            helper.getView(R.id.acceptanceImageLL).setVisibility(View.GONE);
        }

        if (!StringUtils.isEmpty(item.tel)) {
            helper.getView(R.id.acceptanceTalkImg).setVisibility(View.VISIBLE);
            helper.getView(R.id.acceptanceTalkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        SystemUtils.callPage(item.tel, context);
                    } catch (Exception e) {

                    }
                }
            });
        } else {
            helper.getView(R.id.acceptanceTalkImg).setVisibility(View.GONE);
        }

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
            picShowListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
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
