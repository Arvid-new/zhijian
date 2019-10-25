package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SelectTheRectificationPeopleAdapter;

/**
 */

public class CaiLiaoTitle extends AbstractExpandableItem<CaiLiaoTypeName> implements MultiItemEntity {
    public int sectionId;
    public long inspectionId;
    public String inspectionName;
    public long inspectionParentId;



    @Override
    public int getItemType() {
        return SelectTheRectificationPeopleAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}