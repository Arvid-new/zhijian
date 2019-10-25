package com.haozhiyan.zhijian.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJFloorBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJRoomBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJTurnOverPartAllFloorBean;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class GXYJTurnOverPartBuildingsAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_FLOOR = 1;
    public static final int TYPE_ROOM = 2;
    public static final int TYPE_ROOM2 = 3;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public GXYJTurnOverPartBuildingsAdapter(List<MultiItemEntity> data) {
        super(data);
//        addItemType(TYPE_LEVEL_0, R.layout.gxyj_floor_item);
        addItemType(TYPE_FLOOR, R.layout.gxyj_floor_item2);
        addItemType(TYPE_ROOM, R.layout.gxyj_room_item);
        addItemType(TYPE_ROOM2, R.layout.gxyj_room_item2);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_FLOOR:// 层
                final GXYJFloorBean floorBean = (GXYJFloorBean) item;
                holder.setText(R.id.tv_flooer, floorBean.floor);
                break;
            case TYPE_ROOM:// 户
                if (item instanceof GXYJRoomBean) {
                    final GXYJRoomBean roomBean = (GXYJRoomBean) item;
                    holder.setText(R.id.roomName, roomBean.roomNum + "");
                    if ("空".equals(roomBean.identifying)) {
                        if (roomBean.ischeck) {
                            holder.getView(R.id.checkImg).setVisibility(View.VISIBLE);
                            holder.getView(R.id.roomName).setBackgroundResource(R.drawable.blue_deep_2radius_back);
                            holder.setTextColor(R.id.roomName, 0xffffffff);
                        } else {
                            holder.getView(R.id.checkImg).setVisibility(View.GONE);
                            holder.getView(R.id.roomName).setBackgroundResource(R.drawable.gray_wash_2radius_back);
                            holder.setTextColor(R.id.roomName, 0xff232323);
                        }
                        holder.addOnClickListener(R.id.roomName);
                    } else {
                        holder.getView(R.id.roomName).setBackgroundResource(R.drawable.gray_2radius_back);
                        holder.setTextColor(R.id.roomName, 0xff666666);
                    }

                }

                break;

            case TYPE_ROOM2://层
                if (item instanceof GXYJTurnOverPartAllFloorBean) {
                    GXYJTurnOverPartAllFloorBean allFloorBean = (GXYJTurnOverPartAllFloorBean) item;
                    holder.setText(R.id.sectionName, allFloorBean.floor + "");
                    holder.addOnClickListener(R.id.ll);

                    if ("空".equals(allFloorBean.identifying)) {
                        holder.setTextColor(R.id.sectionName, 0xff232323);
                        if (allFloorBean.ischeck) {
                            holder.setImageResource(R.id.checkimg, R.mipmap.xuanze);
                        } else {
                            holder.setImageResource(R.id.checkimg, R.mipmap.gray_yuan);
                        }
                    } else {
                        holder.setImageResource(R.id.checkimg, R.mipmap.gray_yuan);
                        holder.setTextColor(R.id.sectionName, 0xffa0a0a0);
                    }
                }
//
                break;
            default:
                break;
        }
    }
}
