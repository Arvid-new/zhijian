package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/28.
 * Describe: Ydzj_project
 */
public class PlaceUnitSelectItem  extends AbstractExpandableItem<PlaceFloorSelectItem> implements MultiItemEntity {

    public int towerId;
    public String unit;
    public int unitId;
    public List<PlaceNewBean.ListBean.UnitChildBean> unitChild;
    public List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean> floorChild;
    public PlaceUnitSelectItem(int towerId, String unit, int unitId) {
        this.towerId = towerId;
        this.unit = unit;
        this.unitId = unitId;
    }

    public PlaceUnitSelectItem(int towerId, String unit, int unitId, List<PlaceNewBean.ListBean.UnitChildBean> unitChild) {
        this.towerId = towerId;
        this.unit = unit;
        this.unitId = unitId;
        this.unitChild = unitChild;
    }

    public PlaceUnitSelectItem(int towerId, String unit, int unitId, List<PlaceNewBean.ListBean.UnitChildBean> unitChild, List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean> floorChild) {
        this.towerId = towerId;
        this.unit = unit;
        this.unitId = unitId;
        this.unitChild = unitChild;
        this.floorChild = floorChild;
    }

    @Override
    public int getLevel() {
        return 2;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
