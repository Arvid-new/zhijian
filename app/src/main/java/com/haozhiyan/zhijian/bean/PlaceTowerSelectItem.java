package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/28.
 * Describe: Ydzj_project
 */
public class PlaceTowerSelectItem extends AbstractExpandableItem<PlaceUnitSelectItem> implements MultiItemEntity {

    public String iteamName;
    public int itemId;
    public String tower;
    public int towerId;
    public List<PlaceNewBean.ListBean> towerBeans;
    public List<PlaceNewBean.ListBean.UnitChildBean> unitChild;
    public PlaceTowerSelectItem(String iteamName, int itemId, String tower, int towerId) {
        this.iteamName = iteamName;
        this.itemId = itemId;
        this.tower = tower;
        this.towerId = towerId;
    }

    public PlaceTowerSelectItem(String iteamName, int itemId, String tower, int towerId, List<PlaceNewBean.ListBean> towerBeans, List<PlaceNewBean.ListBean.UnitChildBean> unitChild) {
        this.iteamName = iteamName;
        this.itemId = itemId;
        this.tower = tower;
        this.towerId = towerId;
        this.towerBeans = towerBeans;
        this.unitChild = unitChild;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
