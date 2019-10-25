package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.DaiBanTypeDrawerOptionsAdapter;

/**
 */

public class DaiBanDrawerProject extends AbstractExpandableItem<DaiBanDrawerSection> implements MultiItemEntity {
    public String sectionName;
    public boolean isCheck=false;



    @Override
    public int getItemType() {
        return DaiBanTypeDrawerOptionsAdapter.TYPE_PROJECT;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}