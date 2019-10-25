package com.haozhiyan.zhijian.bean.gxyjbeans;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.GXYJTurnOverPartBuildingsAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class GXYJTurnOverPartAllFloorBean implements MultiItemEntity {

    public GXYJTurnOverPartAllFloorBean() {
    }

    public String pkId;//楼栋主键ID
    public String  unitId;//单元ID
    public String floor;//楼层
    public String floorId;//楼栋ID
    public String utiId;//标识ID（如果此房间没有进行修改，此房间ID为0）
    public String  tower;//楼栋名称
    public String  identifying;//标识名称（标识有三种状态1.已退回2.待验收3.已验收 没有修改为空）
    public boolean ischeck;


    @Override
    public int getItemType() {
        return GXYJTurnOverPartBuildingsAdapter.TYPE_ROOM2;
    }
}