package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.JianChaGuideAdapter;

/**
 * Created by WangZhenKai on 2019/5/10.
 * Describe: Ydzj_project
 */
public class JCGuide2Level extends AbstractExpandableItem<JCGuide3Level> implements MultiItemEntity {

    public Long inspectionId;
    public Long parentId;
    public String inspectionName;
    public String identifying;

    public JCGuide2Level(Long inspectionId, Long parentId, String inspectionName, String identifying) {
        this.inspectionId = inspectionId;
        this.parentId = parentId;
        this.inspectionName = inspectionName;
        this.identifying = identifying;
    }

    @Override
    public int getItemType() {
        return JianChaGuideAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getLevel() {
        return JianChaGuideAdapter.TYPE_LEVEL_1;
    }
}
