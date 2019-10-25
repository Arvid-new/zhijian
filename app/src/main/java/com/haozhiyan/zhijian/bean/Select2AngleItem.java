package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by WangZhenKai on 2019/5/13.
 * Describe: Ydzj_project
 */
public class Select2AngleItem implements MultiItemEntity {

    public String userId;
    public String peopleuser;
    public String mobile;
    public String roleName;
    public String thumbUrl;
    public boolean isCheck;

    public Select2AngleItem(String userId, String peopleuser, String mobile, String roleName) {
        this.userId = userId;
        this.peopleuser = peopleuser;
        this.mobile = mobile;
        this.roleName = roleName;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
