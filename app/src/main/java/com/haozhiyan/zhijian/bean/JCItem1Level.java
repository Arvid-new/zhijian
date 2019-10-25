package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.JianChaItemSelectAdapter;

/**
 * Created by WangZhenKai on 2019/5/9.
 * Describe: Ydzj_project
 */
public class JCItem1Level extends AbstractExpandableItem<JCItem2Level> implements MultiItemEntity {

    public String inspectionName;
    public String identifying;
    public long inspectionId;
    public long parentId;

    public JCItem1Level(String inspectionName, String identifying, long inspectionId, long parentId) {
        this.inspectionName = inspectionName;
        this.identifying = identifying;
        this.inspectionId = inspectionId;
        this.parentId = parentId;
    }

    @Override
    public int getLevel() {
        return JianChaItemSelectAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return JianChaItemSelectAdapter.TYPE_LEVEL_0;
    }
}
