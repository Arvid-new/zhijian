package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLDetailAdapter;

/**
 * 实测实量bean
 * */
public class FormSCSLDetailTitleBean implements MultiItemEntity {

    public String towerName;
    public String inspctionId;
    public String inspctionName;
    @Override
    public int getItemType() {
        return FormSCSLDetailAdapter.TYPE_TITLE;
    }
}