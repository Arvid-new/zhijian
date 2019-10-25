package com.haozhiyan.zhijian.activity.reportforms.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLAdapter;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormSCSLDetailAdapter;

/**
 * 实测实量bean
 * */
public class FormSCSLDetailTotalBean implements MultiItemEntity {
    public String zongji;
    public String shigongZongji;
    public String jianliZongji;
    public String jiansheZongji;

    @Override
    public int getItemType() {
        return FormSCSLDetailAdapter.TYPE_TOTAL;
    }
}