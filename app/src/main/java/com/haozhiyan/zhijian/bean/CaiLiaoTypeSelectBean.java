package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SelectTheRectificationPeopleAdapter;

/**
 */

public class CaiLiaoTypeSelectBean implements MultiItemEntity {
    public int sectionId;
    public long inspectionId;
    public String inspectionParentId;
    public String inspectionName;
    public boolean isCheck=false;




    @Override
    public int getItemType() {
        return SelectTheRectificationPeopleAdapter.TYPE_CHECKLIST;
    }
}