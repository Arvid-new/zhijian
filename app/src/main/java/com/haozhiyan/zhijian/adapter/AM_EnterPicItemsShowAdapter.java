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
import com.haozhiyan.zhijian.bean.AMEnterAreaBean;
import com.haozhiyan.zhijian.utils.PVAUtils;

import java.util.List;

public class AM_EnterPicItemsShowAdapter extends BaseQuickAdapter<AMEnterAreaBean.ApproachBean.ImageListBean, BaseViewHolder> {

    private Context context;

    public AM_EnterPicItemsShowAdapter(Context context, @Nullable List<AMEnterAreaBean.ApproachBean.ImageListBean> data) {
        super(R.layout.am_enter_items_pic_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final AMEnterAreaBean.ApproachBean.ImageListBean item) {

        try {
            helper.setText(R.id.name, item.name);
            RecyclerView picRcv = helper.getView(R.id.ImageRCV);
            GridLayoutManager manager = new GridLayoutManager(context, 2);
            manager.setSmoothScrollbarEnabled(true);
            manager.setAutoMeasureEnabled(true);
            picRcv.setHasFixedSize(true);
            picRcv.setNestedScrollingEnabled(false);
            picRcv.setLayoutManager(manager);

            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(item.list, context);
            picRcv.setAdapter(picShowListAdapter);
            picShowListAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (PVAUtils.getFileLastType(item.list.get(position)).equals("image/jpeg")) {
                        ShowBigImg.build(context, item.list.get(position));
                    } else {
                        ShowVideo.playLineVideo(context, item.list.get(position));
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
