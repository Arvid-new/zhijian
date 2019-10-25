package com.haozhiyan.zhijian.bean.scsl;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SCSLjCxAdapter;

/**
 * Created by WangZhenKai on 2019/6/20.
 * Describe: Ydzj_project
 */
public class SCSLjCxLevel2 implements MultiItemEntity {

    public long inspectionId;
    public String inspectionName;
    public long inspectionParentId;
    public String partsDivision;

    public SCSLjCxLevel2(long inspectionId, String inspectionName, long inspectionParentId, String partsDivision) {
        this.inspectionId = inspectionId;
        this.inspectionName = inspectionName;
        this.inspectionParentId = inspectionParentId;
        this.partsDivision = partsDivision;
    }

    @Override
    public int getItemType() {
        return SCSLjCxAdapter.TYPE_LEVEL_1;
    }
}
