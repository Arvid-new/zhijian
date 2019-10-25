package com.haozhiyan.zhijian.bean;

import java.io.Serializable;

/**
 * Created by WangZhenKai on 2019/5/10.
 * Describe: Ydzj_project
 */
public class JCDraftBean implements Serializable {

    private String thumbUrl;
    private String name;
    private String desc;
    private String instruct;
    private String time;

    public JCDraftBean() {
    }

    public JCDraftBean(String thumbUrl, String name, String desc, String instruct, String time) {
        this.thumbUrl = thumbUrl;
        this.name = name;
        this.desc = desc;
        this.instruct = instruct;
        this.time = time;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getInstruct() {
        return instruct;
    }

    public void setInstruct(String instruct) {
        this.instruct = instruct;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
