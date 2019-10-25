package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.GxyjPlaceSelectAdapter;

/**
 * Created by WangZhenKai on 2019/7/2.
 * Describe: Ydzj_project
 */
public class GxyjPlaceItem02  implements MultiItemEntity {

    public String unit;
    public String floor;
    public int floorId;
    public String roomNum;
    public String roomNumNo;
    public String roomRule;
    public int roomId;

    public GxyjPlaceItem02(String unit, String floor, int floorId, String roomNum, String roomNumNo, String roomRule, int roomId) {
        this.unit = unit;
        this.floor = floor;
        this.floorId = floorId;
        this.roomNum = roomNum;
        this.roomNumNo = roomNumNo;
        this.roomRule = roomRule;
        this.roomId = roomId;
    }

    @Override
    public int getItemType() {
        return GxyjPlaceSelectAdapter.TYPE_LEVEL_1;
    }
}
