package com.haozhiyan.zhijian.bean.scsl;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SCSLjCxAdapter;

/**
 * Created by WangZhenKai on 2019/6/20.
 * Describe: Ydzj_project
 */
public class SCSLjCxLevel1 extends AbstractExpandableItem<SCSLjCxLevel2> implements MultiItemEntity {

    public String sectionId;
    public long inspectionId;
    public String inspectionName;
    public long inspectionParentId;

    public SCSLjCxLevel1(String sectionId, long inspectionId, String inspectionName, long inspectionParentId) {
        this.sectionId = sectionId;
        this.inspectionId = inspectionId;
        this.inspectionName = inspectionName;
        this.inspectionParentId = inspectionParentId;
    }

    @Override
    public int getLevel() {
        return SCSLjCxAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return SCSLjCxAdapter.TYPE_LEVEL_0;
    }
}
