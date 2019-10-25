package com.haozhiyan.zhijian.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.GxyjPlaceItem01;
import com.haozhiyan.zhijian.bean.GxyjPlaceItem02;
import com.haozhiyan.zhijian.utils.UiUtils;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/2.
 * Describe: Ydzj_project
 */
public class GxyjPlaceSelectAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    private String roomName;

    public GxyjPlaceSelectAdapter(List<MultiItemEntity> data, String room) {
        super(data);
        this.roomName = room;
        addItemType(TYPE_LEVEL_0, R.layout.place_item_01);
        addItemType(TYPE_LEVEL_1, R.layout.place_item_02);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final GxyjPlaceItem01 item01 = (GxyjPlaceItem01) item;
                helper.setText(R.id.tvFloor, item01.floor+"层")
                        .setImageResource(R.id.iv, item01.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_r);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (item01.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                        if (listener != null) {
                            listener.floor(item01.floor, item01.floorId + "");
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final GxyjPlaceItem02 item02 = (GxyjPlaceItem02) item;
                helper.setText(R.id.tv, item02.roomNum);
                if (roomName.equals(item02.roomNum)) {
                    helper.setGone(R.id.iv, true)
                            .setBackgroundRes(R.id.ll_item, R.drawable.blue_5radius_back)
                            .setTextColor(R.id.tv, UiUtils.getColor(R.color.white_));
                } else {
                    helper.setGone(R.id.iv, false)
                            .setBackgroundRes(R.id.ll_item, R.drawable.gray_border_shape)
                            .setTextColor(R.id.tv, UiUtils.getColor(R.color.black_3));
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        helper.setGone(R.id.iv, true)
                                .setBackgroundRes(R.id.ll_item, R.drawable.blue_5radius_back)
                                .setTextColor(R.id.tv, UiUtils.getColor(R.color.white_));
                        if (listener != null) {
                            listener.room(item02.roomNum, item02.roomId + "");
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    private OnItemSonClickListener listener;

    public void setOnItemSonClickListener(OnItemSonClickListener sonClickListener) {
        this.listener = sonClickListener;
    }

    public interface OnItemSonClickListener {
        void room(String roomNum, String roomId);

        void floor(String floorName, String floorIds);
    }
}
