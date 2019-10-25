package com.haozhiyan.zhijian.bean;

import java.io.Serializable;

/**
 * Created by WangZhenKai on 2019/5/21.
 * Describe: Ydzj_project
 */
public class ItemBean implements Serializable {

    public String name;
    public String type;
    public long id;
    public String twoId;
    public String batchId;
    public String sectionId;
    public String strId;
    public String mobile;
    public boolean isCheck;

    //现场检查批次
    public String username;
    public String userId;
    public String peopleuser;
    public String userAppTag;

    //现场检查详情
    public String reviewId;
    public String tel;
    public String isReview;
    public String reviewDate;
    public String reviewSupplement;

    public ItemBean(String name) {
        this.name = name;
    }

    public ItemBean(String name, long id, String strId, boolean isCheck) {
        this.name = name;
        this.id = id;
        this.strId = strId;
        this.isCheck = isCheck;
    }

    public ItemBean(String name, String type, long id) {
        this.name = name;
        this.type = type;
        this.id = id;
    }

    public ItemBean(String name, String strId, boolean isCheck) {
        this.name = name;
        this.strId = strId;
        this.isCheck = isCheck;
    }

    public ItemBean(String name, String strId, String mobile) {
        this.name = name;
        this.strId = strId;
        this.mobile = mobile;
    }

    //现场检查详情复验人使用
    public ItemBean(String peopleuser, String reviewId, String tel, String isReview, String reviewDate, String reviewSupplement) {
        this.peopleuser = peopleuser;
        this.reviewId = reviewId;
        this.tel = tel;
        this.isReview = isReview;
        this.reviewDate = reviewDate;
        this.reviewSupplement = reviewSupplement;
    }

    //现场检查详情其他人使用
    public ItemBean(String peopleuser, String tel, String userId, String userAppTag) {
        this.peopleuser = peopleuser;
        this.tel = tel;
        this.userId = userId;
        this.userAppTag = userAppTag;
    }

    //现场检查批次列表使用
    public ItemBean(String name, String batchId, String sectionId, boolean isCheck) {
        this.name = name;
        this.batchId = batchId;
        this.sectionId = sectionId;
        this.isCheck = isCheck;
    }

    //现场检查批次详情使用
    public ItemBean(String username, String userId, String mobile, String peopleuser, String userAppTag, boolean isCheck) {
        this.username = username;
        this.userId = userId;
        this.mobile = mobile;
        this.peopleuser = peopleuser;
        this.userAppTag = userAppTag;
        this.isCheck = isCheck;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
