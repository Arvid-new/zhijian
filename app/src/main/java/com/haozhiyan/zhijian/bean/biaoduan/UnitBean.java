package com.haozhiyan.zhijian.bean.biaoduan;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class UnitBean {


    @Id()
    @Index(unique = true)
    public String unitId;
    public String unit;
    public String towerId;
    public String getTowerId() {
        return this.towerId;
    }
    public void setTowerId(String towerId) {
        this.towerId = towerId;
    }
    public String getUnit() {
        return this.unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getUnitId() {
        return this.unitId;
    }
    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }
    @Generated(hash = 1413462729)
    public UnitBean(String unitId, String unit, String towerId) {
        this.unitId = unitId;
        this.unit = unit;
        this.towerId = towerId;
    }
    @Generated(hash = 2132627383)
    public UnitBean() {
    }
}
