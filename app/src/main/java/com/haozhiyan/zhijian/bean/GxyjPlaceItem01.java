package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.GxyjPlaceSelectAdapter;

/**
 * Created by WangZhenKai on 2019/7/2.
 * Describe: Ydzj_project
 */
public class GxyjPlaceItem01 extends AbstractExpandableItem<GxyjPlaceItem02> implements MultiItemEntity {

    public String floorId;
    public String floor;

    public GxyjPlaceItem01(String floorId, String floor) {
        this.floorId = floorId;
        this.floor = floor;
    }

    @Override
    public int getLevel() {
        return GxyjPlaceSelectAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return GxyjPlaceSelectAdapter.TYPE_LEVEL_0;
    }
}
