package com.haozhiyan.zhijian.bean.gxyjbeans;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.GXYJBuildingsAdapter;

/**
 * Created by luoxw on 2016/8/10.
 */

public class GXYJRoomBean implements MultiItemEntity {

    public GXYJRoomBean() {
    }


    public int roomId;
    public String roomNum;
    public int floorId;
    public String floor;
    public int utiId;//标识ID（如果此房间没有进行修改，此房间ID为0）
    public String identifying;//标识名称（标识有三种状态1.已退回2.待验收3.已验收 没有修改为空）
    public boolean ischeck;


    @Override
    public int getItemType() {
        return GXYJBuildingsAdapter.TYPE_ROOM;
    }
}