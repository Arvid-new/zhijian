package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by WangZhenKai on 2019/6/28.
 * Describe: Ydzj_project
 */
public class PlaceRoomSelectItem  implements MultiItemEntity {

    public int floorId;
    public int roomId;
    public String roomNum;

    public PlaceRoomSelectItem(int floorId, int roomId, String roomNum) {
        this.floorId = floorId;
        this.roomId = roomId;
        this.roomNum = roomNum;
    }

    @Override
    public int getItemType() {
        return 4;
    }
}
