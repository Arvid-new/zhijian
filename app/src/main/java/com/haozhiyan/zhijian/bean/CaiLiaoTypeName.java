package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SelectTheRectificationPeopleAdapter;

/**
 *
 */

public class CaiLiaoTypeName implements MultiItemEntity {


    public String inspectionName;
    public String partsDivision;
    public long inspectionId;
    public long inspectionParentId;

    @Override
    public int getItemType() {
        return SelectTheRectificationPeopleAdapter.TYPE_PERSON;
    }
}