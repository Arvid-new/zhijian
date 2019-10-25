package com.haozhiyan.zhijian.bean.biaoduan;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class InspectionTwoBean {

    public String secInsId;
    public String sectionId;
    @Id()
    @Index(unique = true)
    public String inspectionId;
    public String parentId;
    public String inspectionName;
    public String partsDivision;
    public String isNeedBuild;
    public String getIsNeedBuild() {
        return this.isNeedBuild;
    }
    public void setIsNeedBuild(String isNeedBuild) {
        this.isNeedBuild = isNeedBuild;
    }
    public String getPartsDivision() {
        return this.partsDivision;
    }
    public void setPartsDivision(String partsDivision) {
        this.partsDivision = partsDivision;
    }
    public String getInspectionName() {
        return this.inspectionName;
    }
    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }
    public String getParentId() {
        return this.parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getInspectionId() {
        return this.inspectionId;
    }
    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }
    public String getSectionId() {
        return this.sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getSecInsId() {
        return this.secInsId;
    }
    public void setSecInsId(String secInsId) {
        this.secInsId = secInsId;
    }
    @Generated(hash = 617621776)
    public InspectionTwoBean(String secInsId, String sectionId,
            String inspectionId, String parentId, String inspectionName,
            String partsDivision, String isNeedBuild) {
        this.secInsId = secInsId;
        this.sectionId = sectionId;
        this.inspectionId = inspectionId;
        this.parentId = parentId;
        this.inspectionName = inspectionName;
        this.partsDivision = partsDivision;
        this.isNeedBuild = isNeedBuild;
    }
    @Generated(hash = 1307538626)
    public InspectionTwoBean() {
    }
}
