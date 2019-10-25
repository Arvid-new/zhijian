package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by WangZhenKai on 2019/5/14.
 * Describe: Ydzj_project
 */
public class JCGuide3Level implements MultiItemEntity {

    public long inspectionId;
    public String checkGuide;

    public JCGuide3Level(long inspectionId, String checkGuide) {
        this.inspectionId = inspectionId;
        this.checkGuide = checkGuide;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
