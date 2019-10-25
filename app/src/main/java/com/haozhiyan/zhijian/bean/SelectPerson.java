package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.adapter.SelectPeopleAdapter;

/**
 *
 */

public class SelectPerson implements MultiItemEntity {


    public String peopleuser;
    public String roleName;
    public String mobile;
    public int userId;
    public boolean isCheck;

    @Override
    public int getItemType() {
        return SelectPeopleAdapter.TYPE_SECTION ;
    }
}