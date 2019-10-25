package com.haozhiyan.zhijian.bean.gxyjbeans;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.GXYJBuildingsAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class GXYJFloorBean extends AbstractExpandableItem<GXYJRoomBean> implements MultiItemEntity {
    public String floor;
    public int floorId;
    public int unitId;

    public GXYJFloorBean() {
    }



    @Override
    public int getItemType() {
        return GXYJBuildingsAdapter.TYPE_FLOOR;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}