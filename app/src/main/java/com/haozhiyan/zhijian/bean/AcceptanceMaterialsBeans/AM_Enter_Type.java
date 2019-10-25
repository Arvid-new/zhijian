package com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.haozhiyan.zhijian.model.Constant.TYPE_TYPE;

/**
 * 类型
 */

public class AM_Enter_Type extends AbstractExpandableItem<AM_Enter_Specification> implements MultiItemEntity {
    public String typeName;
    public String id;
    public int position;

    public AM_Enter_Type(String typeName,int position) {
        this.typeName = typeName;
        this.position = position;
    }

    @Override
    public int getItemType() {
        return TYPE_TYPE;
    }

    @Override
    public int getLevel() {
        return 1;
    }
}