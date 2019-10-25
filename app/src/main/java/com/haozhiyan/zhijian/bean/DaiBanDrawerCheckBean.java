package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.DaiBanTypeDrawerOptionsAdapter;

/**
 */

public class DaiBanDrawerCheckBean implements MultiItemEntity {
    public int sectionId;
    public int inspectionId;
    public String inspectionParentId;
    public String inspectionName;
    public boolean isCheck=false;




    @Override
    public int getItemType() {
        return DaiBanTypeDrawerOptionsAdapter.TYPE_CHECKLIST;
    }
}