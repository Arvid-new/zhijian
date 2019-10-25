package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/28.
 * Describe: Ydzj_project
 */
public class PlaceFloorSelectItem  extends AbstractExpandableItem<PlaceRoomSelectItem> implements MultiItemEntity {

    public String floor;
    public int floorId;
    public int unitId;
    public List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean> floorChild;
    public List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean.RoomNumChildBean> roomChilds;
    public PlaceFloorSelectItem(String floor, int floorId, int unitId) {
        this.floor = floor;
        this.floorId = floorId;
        this.unitId = unitId;
    }

    public PlaceFloorSelectItem(String floor, int floorId, int unitId, List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean> floorChild, List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean.RoomNumChildBean> roomChilds) {
        this.floor = floor;
        this.floorId = floorId;
        this.unitId = unitId;
        this.floorChild = floorChild;
        this.roomChilds = roomChilds;
    }

    @Override
    public int getLevel() {
        return 3;
    }

    @Override
    public int getItemType() {
        return 3;
    }
}
