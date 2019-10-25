package com.haozhiyan.zhijian.bean.workfg;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 地块 list bean
 */
@Entity
public class WorkPlotBean {
    @Id
    @Index(unique = true)
    public String  pkId;
    public String iteamName;
    public String  itemId;
    public String getItemId() {
        return this.itemId;
    }
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
    public String getIteamName() {
        return this.iteamName;
    }
    public void setIteamName(String iteamName) {
        this.iteamName = iteamName;
    }
    public String getPkId() {
        return this.pkId;
    }
    public void setPkId(String pkId) {
        this.pkId = pkId;
    }
    @Generated(hash = 1043557917)
    public WorkPlotBean(String pkId, String iteamName, String itemId) {
        this.pkId = pkId;
        this.iteamName = iteamName;
        this.itemId = itemId;
    }
    @Generated(hash = 2004478521)
    public WorkPlotBean() {
    }

}
