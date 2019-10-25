package com.haozhiyan.zhijian.bean;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by WangZhenKai on 2019/5/13.
 * Describe: Ydzj_project
 */
public class Select1AngleItem extends AbstractExpandableItem<Select2AngleItem> implements MultiItemEntity {

    public String roleId;
    public String roleName;
    public boolean isCheck;

    public Select1AngleItem(String roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    @Override
    public int getLevel() {
        return 1;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
