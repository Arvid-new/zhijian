package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.DaiBanTypeDrawerOptionsAdapter;

/**
 */

public class DaiBanDrawerSection implements MultiItemEntity {
    public String sectionName;
    public int projectId;
    public int sectionId;
    public boolean isCheck=false;



    @Override
    public int getItemType() {
        return DaiBanTypeDrawerOptionsAdapter.TYPE_SECTION;
    }
}