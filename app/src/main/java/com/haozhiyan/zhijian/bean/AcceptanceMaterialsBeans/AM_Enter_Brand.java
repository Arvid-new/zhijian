package com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.haozhiyan.zhijian.model.Constant.TYPE_BRAND;

/**
 * 品牌
 */

public class AM_Enter_Brand extends AbstractExpandableItem<AM_Enter_Type> implements MultiItemEntity {
    public String bandName;
    public int position;

    public AM_Enter_Brand(String bandName, int position) {
        this.bandName = bandName;
        this.position = position;
    }

    @Override
    public int getItemType() {
        return TYPE_BRAND;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}