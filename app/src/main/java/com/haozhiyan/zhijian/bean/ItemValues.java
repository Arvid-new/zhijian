package com.haozhiyan.zhijian.bean;

import java.io.Serializable;

/**
 * Created by WangZhenKai on 2019/7/8.
 * Describe: Ydzj_project
 */
public class ItemValues implements Serializable {

    public String houseMap;
    public String pieceType;
    public String towerFloorUnitRoom;
    public String tower;
    public String unit;
    public String floor;
    public String room;
    public String towerId;
    public String unitId;
    public String floorId;
    public String roomId;
    public String roomNum;
    public boolean isUpdate;

    public ItemValues() {
    }

    public ItemValues(String houseMap, String pieceType) {
        this.houseMap = houseMap;
        this.pieceType = pieceType;
    }

    public ItemValues(String tower,String unit, String floor, String room, String towerId, String unitId, String floorId, String roomId,String roomNum) {
        this.tower = tower;
        this.unit = unit;
        this.floor = floor;
        this.room = room;
        this.towerId = towerId;
        this.unitId = unitId;
        this.floorId = floorId;
        this.roomId = roomId;
        this.roomNum = roomNum;
    }
}
