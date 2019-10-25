package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormCLYSAdapter;


public class FormCLYSTitle extends AbstractExpandableItem<FormCLYSChild> implements MultiItemEntity {
    public int amount;
    public int  total;
    public String nameInspectionId;
    public String nameInspection;



    @Override
    public int getItemType() {
        return FormCLYSAdapter.TYPE_TITLE;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}