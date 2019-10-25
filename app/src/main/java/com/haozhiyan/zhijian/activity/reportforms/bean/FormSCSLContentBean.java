package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLDetailAdapter;

/**
 * 实测实量bean
 */
public class FormSCSLContentBean implements MultiItemEntity {

    public int towerId;
    public String towerName;
    public String shigonghegelv;
    public String shigongInsp;
    public String jianlihegelv;
    public String jianliInsp;
    public String jianshehegelv;
    public String jiansheheInsp;

    @Override
    public int getItemType() {
        return FormSCSLAdapter.TYPE_CONTENT;
    }
}