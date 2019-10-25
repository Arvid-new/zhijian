package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLDetailAdapter;

/**
 * 实测实量bean
 */
public class FormSCSLDetailContentBean implements MultiItemEntity {
    public String inspctionSunId;
    public String inspctionSunName;
    public String sgHgl;
    public String sgHs;
    public String jlHgl;
    public String jlHs;
    public String jsHgl;
    public String jsHu;

    @Override
    public int getItemType() {
        return FormSCSLDetailAdapter.TYPE_CONTENT;
    }
}