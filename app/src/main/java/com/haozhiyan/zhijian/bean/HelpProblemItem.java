package com.haozhiyan.zhijian.bean;

import java.io.Serializable;

/**
 * Created by WangZhenKai on 2019/7/24.
 * Describe:
 */
public class HelpProblemItem implements Serializable{

    public int id;
    public int parentId;
    public String helpName;
    public String connect;

    public HelpProblemItem() {
    }

    public HelpProblemItem(int id, int parentId, String helpName, String connect) {
        this.id = id;
        this.parentId = parentId;
        this.helpName = helpName;
        this.connect = connect;
    }
}
