package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLDetailAdapter;

/**
 * 实测实量bean
 * */
public class FormSCSLTitleBean implements MultiItemEntity {

    public int sectionId;
    public String sectionName;
    public int projectManage;
    public String scope;

    @Override
    public int getItemType() {
        return FormSCSLAdapter.TYPE_TITLE;
    }
}