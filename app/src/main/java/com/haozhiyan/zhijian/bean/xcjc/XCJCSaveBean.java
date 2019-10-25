package com.haozhiyan.zhijian.bean.xcjc;

import com.haozhiyan.zhijian.bean.LocalMediaListConvert;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

import java.util.List;

@Entity
public class XCJCSaveBean {
    @Id
    @Index(unique = true)
    public String timeId;//这个id 是以暂存时的时间戳为id
    public String local;
    public String inspectionIds;
    public String particularsName;
    public String particularsDesc;
    public String JianChaR;
    public String batchId;
    public String projectId;
    public String sectionId;
    public String tower;
    public String unit;
    public String floor;
    public String room;
    public String housemap;
    public String particularsId;
    public String serious;
    public String rectifyTimelimit;
    public String rectify;
    public String review;
    public String dutyUnit;
    public String createTime;
    public String troubleChengDu;
    public String orderOfSeverity;
    public String num;
    public String rectifyName;
    public String dutyName;
    public String reviewName;
    public String ccName;
    public String cc;

    @Convert(converter = LocalMediaListConvert.class, columnType = String.class)
    public List<LocalMedia> pics;

    public List<LocalMedia> getPics() {
        return this.pics;
    }

    public void setPics(List<LocalMedia> pics) {
        this.pics = pics;
    }

    public String getCc() {
        return this.cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCcName() {
        return this.ccName;
    }

    public void setCcName(String ccName) {
        this.ccName = ccName;
    }

    public String getReviewName() {
        return this.reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public String getDutyName() {
        return this.dutyName;
    }

    public void setDutyName(String dutyName) {
        this.dutyName = dutyName;
    }

    public String getRectifyName() {
        return this.rectifyName;
    }

    public void setRectifyName(String rectifyName) {
        this.rectifyName = rectifyName;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrderOfSeverity() {
        return this.orderOfSeverity;
    }

    public void setOrderOfSeverity(String orderOfSeverity) {
        this.orderOfSeverity = orderOfSeverity;
    }

    public String getTroubleChengDu() {
        return this.troubleChengDu;
    }

    public void setTroubleChengDu(String troubleChengDu) {
        this.troubleChengDu = troubleChengDu;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDutyUnit() {
        return this.dutyUnit;
    }

    public void setDutyUnit(String dutyUnit) {
        this.dutyUnit = dutyUnit;
    }

    public String getReview() {
        return this.review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRectify() {
        return this.rectify;
    }

    public void setRectify(String rectify) {
        this.rectify = rectify;
    }

    public String getRectifyTimelimit() {
        return this.rectifyTimelimit;
    }

    public void setRectifyTimelimit(String rectifyTimelimit) {
        this.rectifyTimelimit = rectifyTimelimit;
    }

    public String getSerious() {
        return this.serious;
    }

    public void setSerious(String serious) {
        this.serious = serious;
    }

    public String getParticularsId() {
        return this.particularsId;
    }

    public void setParticularsId(String particularsId) {
        this.particularsId = particularsId;
    }

    public String getHousemap() {
        return this.housemap;
    }

    public void setHousemap(String housemap) {
        this.housemap = housemap;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFloor() {
        return this.floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getUnit() {
        return this.unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getTower() {
        return this.tower;
    }

    public void setTower(String tower) {
        this.tower = tower;
    }

    public String getSectionId() {
        return this.sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getProjectId() {
        return this.projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getBatchId() {
        return this.batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getJianChaR() {
        return this.JianChaR;
    }

    public void setJianChaR(String JianChaR) {
        this.JianChaR = JianChaR;
    }

    public String getParticularsDesc() {
        return this.particularsDesc;
    }

    public void setParticularsDesc(String particularsDesc) {
        this.particularsDesc = particularsDesc;
    }

    public String getParticularsName() {
        return this.particularsName;
    }

    public void setParticularsName(String particularsName) {
        this.particularsName = particularsName;
    }

    public String getInspectionIds() {
        return this.inspectionIds;
    }

    public void setInspectionIds(String inspectionIds) {
        this.inspectionIds = inspectionIds;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTimeId() {
        return this.timeId;
    }

    public void setTimeId(String timeId) {
        this.timeId = timeId;
    }

    @Generated(hash = 2069276772)
    public XCJCSaveBean(String timeId, String local, String inspectionIds,
            String particularsName, String particularsDesc, String JianChaR,
            String batchId, String projectId, String sectionId, String tower,
            String unit, String floor, String room, String housemap,
            String particularsId, String serious, String rectifyTimelimit,
            String rectify, String review, String dutyUnit, String createTime,
            String troubleChengDu, String orderOfSeverity, String num,
            String rectifyName, String dutyName, String reviewName, String ccName,
            String cc, List<LocalMedia> pics) {
        this.timeId = timeId;
        this.local = local;
        this.inspectionIds = inspectionIds;
        this.particularsName = particularsName;
        this.particularsDesc = particularsDesc;
        this.JianChaR = JianChaR;
        this.batchId = batchId;
        this.projectId = projectId;
        this.sectionId = sectionId;
        this.tower = tower;
        this.unit = unit;
        this.floor = floor;
        this.room = room;
        this.housemap = housemap;
        this.particularsId = particularsId;
        this.serious = serious;
        this.rectifyTimelimit = rectifyTimelimit;
        this.rectify = rectify;
        this.review = review;
        this.dutyUnit = dutyUnit;
        this.createTime = createTime;
        this.troubleChengDu = troubleChengDu;
        this.orderOfSeverity = orderOfSeverity;
        this.num = num;
        this.rectifyName = rectifyName;
        this.dutyName = dutyName;
        this.reviewName = reviewName;
        this.ccName = ccName;
        this.cc = cc;
        this.pics = pics;
    }

    @Generated(hash = 2076615983)
    public XCJCSaveBean() {
    }

}
