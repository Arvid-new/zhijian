package com.haozhiyan.zhijian.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllFloorBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllTowerBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJFloorBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJRoomBean;
import com.haozhiyan.zhijian.utils.UiUtils;

import java.util.List;

/**
 * Created by luoxw on 2016/8/9.
 */
public class GXYJBuildingsAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_FLOOR = 1;
    public static final int TYPE_ROOM = 2;

    public GXYJBuildingsAdapter(List<MultiItemEntity> data) {
        super(data);
        //addItemType(TYPE_LEVEL_0, R.layout.gxyj_tower_item);
        addItemType(TYPE_FLOOR, R.layout.gxyj_floor_item);
        addItemType(TYPE_ROOM, R.layout.gxyj_room_item);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                break;
            case TYPE_FLOOR:
                GXYJFloorBean floorBean = (GXYJFloorBean) item;
                holder.setText(R.id.tv_flooer, floorBean.floor);
                break;
            case TYPE_ROOM:
                if (item instanceof GXYJRoomBean) {
                    GXYJRoomBean roomBean = (GXYJRoomBean) item;
                    holder.setText(R.id.roomName, roomBean.roomNum + "");
                    holder.addOnClickListener(R.id.roomName);
                    setStatusBack(holder, roomBean.identifying);
                } else if (item instanceof GXYJAllFloorBean) {
                    GXYJAllFloorBean allFloorBean = (GXYJAllFloorBean) item;
                    holder.setText(R.id.roomName, allFloorBean.floor + "");
                    holder.addOnClickListener(R.id.roomName);
                    setStatusBack(holder, allFloorBean.identifying);
                } else if (item instanceof GXYJAllTowerBean) {
                    GXYJAllTowerBean allTowerBean = (GXYJAllTowerBean) item;
                    holder.setText(R.id.roomName, allTowerBean.tower + "");
                    holder.addOnClickListener(R.id.roomName);
                    setStatusBack(holder, allTowerBean.identifying);
                }
                break;
            default:
                break;
        }
    }

    private void setStatusBack(BaseViewHolder holder, String identifying) {
        switch (identifying) {
            case "已退回":
                holder.setBackgroundRes(R.id.roomName, R.drawable.red_2radius_back);
                holder.setTextColor(R.id.roomName, UiUtils.getColor(R.color.white));
                break;
            case "待验收":
                holder.setBackgroundRes(R.id.roomName, R.drawable.orange_2radius_back);
                holder.setTextColor(R.id.roomName, UiUtils.getColor(R.color.white));
                break;
            case "已验收":
                holder.setBackgroundRes(R.id.roomName, R.drawable.green_2radius_back);
                holder.setTextColor(R.id.roomName, UiUtils.getColor(R.color.white));
                break;
            default://申请验收
                holder.setBackgroundRes(R.id.roomName, R.drawable.gray_wash_2radius_back);
                holder.setTextColor(R.id.roomName, UiUtils.getColor(R.color.black_3));
                break;
        }
    }
}
