package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SelectTheRectificationPeopleAdapter;

/**
 */

public class ZhengGaiTypeTitle extends AbstractExpandableItem<ZhengGaiPerson> implements MultiItemEntity {
    public int roleId;
    public String roleName;
    public boolean isCheck;



    @Override
    public int getItemType() {
        return SelectTheRectificationPeopleAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}