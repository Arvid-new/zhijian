package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormCLYSAdapter;

public class FormCLYSChild implements MultiItemEntity {


    public String titleName;
    public String stateName;
    public String approachDate;
    public int id;
    public String state;

    @Override
    public int getItemType() {
        return FormCLYSAdapter.TYPE_CONTENT;
    }
}