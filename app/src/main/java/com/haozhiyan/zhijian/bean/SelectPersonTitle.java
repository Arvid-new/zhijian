package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SelectPeopleAdapter;

/**
 */

public class SelectPersonTitle extends AbstractExpandableItem<SelectPerson> implements MultiItemEntity {
    public int roleId;
    public String roleName;
    public boolean isCheck;
    public boolean isUserChenge = false;//是否主动选中所有

    @Override
    public int getItemType() {
        return SelectPeopleAdapter.TYPE_PROJECT;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}