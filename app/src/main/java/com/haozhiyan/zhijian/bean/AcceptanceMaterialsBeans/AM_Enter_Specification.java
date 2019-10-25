package com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import static com.haozhiyan.zhijian.model.Constant.TYPE_SPECIfICATION;

/**
 * 规格
 */

public class AM_Enter_Specification implements MultiItemEntity {
    public String number;
    public String specificationName;
    public String unit;
    public String id;
    public int position;


    public AM_Enter_Specification(String number, String specificationName, String unit, int position) {
        this.number = number;
        this.specificationName = specificationName;
        this.unit = unit;
        this.position = position;
    }

    @Override
    public int getItemType() {
        return TYPE_SPECIfICATION;
    }
}