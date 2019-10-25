package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.JianChaItemSelectAdapter;

/**
 * Created by WangZhenKai on 2019/5/9.
 * Describe: Ydzj_project
 */
public class JCItem3Level implements MultiItemEntity {

    public long inspectionId;
    public String particularsName;

    public JCItem3Level(long inspectionId, String particularsName) {
        this.inspectionId = inspectionId;
        this.particularsName = particularsName;
    }

    @Override
    public int getItemType() {
        return JianChaItemSelectAdapter.TYPE_LEVEL_2;
    }
}
