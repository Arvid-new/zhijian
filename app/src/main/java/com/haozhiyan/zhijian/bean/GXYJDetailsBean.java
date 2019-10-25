package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

public class GXYJDetailsBean implements Serializable {


    /**
     * id :
     * roomTowerFloorId : 3,,,
     * detailsName : 主体工程-钢筋验收
     * sectionDikuaiName : 测试标段0613(91#地块)
     * sectionName : 测试标段0613
     * siteName : 1栋
     * towerName : 1栋
     * unitName : 1单元
     * handOverPart : 2层
     * constructionUnit : 金隅保温（翠园A1#一标段）2
     * creatorId : 206
     * constructionDirector : 监理人员
     * supervisorId : 206
     * supervisor : 监理人员
     * jianliState : 待验收
     * supervisorPictureVideo : b38dec83bf2c4e84bed5c32986af8168.png
     * jianliPeopleTime : 2019-07-09 19:39:06
     * constructionId : 208
     * constructionUnitPrincipal : 建设人员
     * jiansheState :
     * constructionPictureVideo :
     * constructionExplain :
     * jianshePeopleTime : 2019-07-09 19:39:42
     * copyPeople : 施工人员
     * pictureVideo : 4a8f3fc0e72f465885d5b6ccf5f837ac.png
     * explainIssue : Bxbxbxb
     * projectId : 2
     * projectName : 名门紫园
     * pkId :
     * dikuaiName : 91#地块
     * sectionId :
     * towerId :
     * unitId :
     * inspectionName : 主体工程
     * inspectionSunName : 钢筋验收
     * partsDivision : 分单元-整层
     * secInsId :
     * utiId :
     * peopleId : 207,
     * identifying : 待验收
     * supervisorUtiId :
     * supervisorStatus :
     * supervisorExplain : Hxxcjj
     * constructionUtiId :
     * constructionStatus :
     * peopleStatus :
     * backRecord :
     * inspectionId : 85
     * creationTime : 2019-07-09 19:37:43
     * creatorPeople :
     * deleteButton : 0
     * childCreator : [{"userId":"","peopleuser":"监理人员","tel":"15937085623","userAppTag":""}]
     * childS : [{"userId":"","peopleuser":"监理人员","tel":"15937085623","userAppTag":""}]
     * childC : [{"userId":"","peopleuser":"建设人员","tel":"15937089523","userAppTag":""}]
     * childP : [{"userId":"","peopleuser":"施工人员","tel":"15937089523","userAppTag":""}]
     * childPV : ["http://192.168.110.66:8080/ydzj-admin/images/4a8f3fc0e72f465885d5b6ccf5f837ac.png"]
     * childPVSUP : ["http://192.168.110.66:8080/ydzj-admin/images/b38dec83bf2c4e84bed5c32986af8168.png"]
     * childPVCON : []
     * childBack : [{"backId":42,"appGxyjId":223,"backType":"建设人","backUsername":"建设人员","backCause":"梁箍筋规格、尺寸、数量、加密箍区不满足图纸及规范要求","backExplain":"Hxhxhxbbx","constructionUnit":"金隅保温（翠园A1#一标段）2","constructionDirector":"监理人员","creationTime":"2019-07-09 19:37:43","backCreationTime":"2019-07-09 19:39:42","transferRecord":"09e94f96496d44b5aa2b2b0a552883ff.png","transferExplain":"","backPictureVideo":"97d17f85fca74079a8351d4ec575469d.png","childbackPictureVideo":["http://192.168.110.66:8080/ydzj-admin/images/97d17f85fca74079a8351d4ec575469d.png"],"childTransferRecord":["http://192.168.110.66:8080/ydzj-admin/images/09e94f96496d44b5aa2b2b0a552883ff.png"],"backNumber":2}]
     * listAbarbeitung : []
     */

    public String id;
    public String roomTowerFloorId;
    public String detailsName;
    public String sectionDikuaiName;
    public String sectionName;
    public String siteName;
    public String towerName;
    public String unitName;
    public String handOverPart;
    public String constructionUnit;
    public String creatorId;
    public String constructionDirector;
    public String supervisorId;
    public String supervisor;
    public String jianliState;
    public String supervisorPictureVideo;
    public String jianliPeopleTime;
    public String constructionId;
    public String constructionUnitPrincipal;
    public String jiansheState;
    public String constructionPictureVideo;
    public String constructionExplain;
    public String jianshePeopleTime;
    public String copyPeople;
    public String pictureVideo;
    public String explainIssue;
    public String projectId;
    public String projectName;
    public String pkId;
    public String dikuaiName;
    public String sectionId;
    public String towerId;
    public String floorId;
    public String unitId;
    public String floorName;
    public String inspectionName;
    public String inspectionSunName;
    public String partsDivision;
    public String secInsId;
    public String utiId;
    public String peopleId;
    public String identifying;
    public String supervisorUtiId;
    public String supervisorStatus;
    public String supervisorExplain;
    public String constructionUtiId;
    public String constructionStatus;
    public String peopleStatus;
    public String backRecord;
    public String inspectionId;
    public String creationTime;
    public String creatorPeople;
    public String deleteButton;
    public List<ChildCreatorBean> childCreator;
    public List<ChildSBean> childS;
    public List<ChildCBean> childC;
    public List<ChildPBean> childP;
    public List<String> childPV;
    public List<String> childPVSUP;
    public List<String > childPVCON;
    public List<ChildBackBean> childBack;
    public List<String > listAbarbeitung;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomTowerFloorId() {
        return roomTowerFloorId;
    }

    public void setRoomTowerFloorId(String roomTowerFloorId) {
        this.roomTowerFloorId = roomTowerFloorId;
    }

    public String getDetailsName() {
        return detailsName;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    public String getSectionDikuaiName() {
        return sectionDikuaiName;
    }

    public void setSectionDikuaiName(String sectionDikuaiName) {
        this.sectionDikuaiName = sectionDikuaiName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTowerName() {
        return towerName;
    }

    public void setTowerName(String towerName) {
        this.towerName = towerName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getHandOverPart() {
        return handOverPart;
    }

    public void setHandOverPart(String handOverPart) {
        this.handOverPart = handOverPart;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getConstructionDirector() {
        return constructionDirector;
    }

    public void setConstructionDirector(String constructionDirector) {
        this.constructionDirector = constructionDirector;
    }

    public String getSupervisorId() {
        return supervisorId;
    }

    public void setSupervisorId(String supervisorId) {
        this.supervisorId = supervisorId;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getJianliState() {
        return jianliState;
    }

    public void setJianliState(String jianliState) {
        this.jianliState = jianliState;
    }

    public String getSupervisorPictureVideo() {
        return supervisorPictureVideo;
    }

    public void setSupervisorPictureVideo(String supervisorPictureVideo) {
        this.supervisorPictureVideo = supervisorPictureVideo;
    }

    public String getJianliPeopleTime() {
        return jianliPeopleTime;
    }

    public void setJianliPeopleTime(String jianliPeopleTime) {
        this.jianliPeopleTime = jianliPeopleTime;
    }

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
    }

    public String getConstructionUnitPrincipal() {
        return constructionUnitPrincipal;
    }

    public void setConstructionUnitPrincipal(String constructionUnitPrincipal) {
        this.constructionUnitPrincipal = constructionUnitPrincipal;
    }

    public String getJiansheState() {
        return jiansheState;
    }

    public void setJiansheState(String jiansheState) {
        this.jiansheState = jiansheState;
    }

    public String getConstructionPictureVideo() {
        return constructionPictureVideo;
    }

    public void setConstructionPictureVideo(String constructionPictureVideo) {
        this.constructionPictureVideo = constructionPictureVideo;
    }

    public String getConstructionExplain() {
        return constructionExplain;
    }

    public void setConstructionExplain(String constructionExplain) {
        this.constructionExplain = constructionExplain;
    }

    public String getJianshePeopleTime() {
        return jianshePeopleTime;
    }

    public void setJianshePeopleTime(String jianshePeopleTime) {
        this.jianshePeopleTime = jianshePeopleTime;
    }

    public String getCopyPeople() {
        return copyPeople;
    }

    public void setCopyPeople(String copyPeople) {
        this.copyPeople = copyPeople;
    }

    public String getPictureVideo() {
        return pictureVideo;
    }

    public void setPictureVideo(String pictureVideo) {
        this.pictureVideo = pictureVideo;
    }

    public String getExplainIssue() {
        return explainIssue;
    }

    public void setExplainIssue(String explainIssue) {
        this.explainIssue = explainIssue;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getDikuaiName() {
        return dikuaiName;
    }

    public void setDikuaiName(String dikuaiName) {
        this.dikuaiName = dikuaiName;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getTowerId() {
        return towerId;
    }

    public void setTowerId(String towerId) {
        this.towerId = towerId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getInspectionName() {
        return inspectionName;
    }

    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public String getInspectionSunName() {
        return inspectionSunName;
    }

    public void setInspectionSunName(String inspectionSunName) {
        this.inspectionSunName = inspectionSunName;
    }

    public String getPartsDivision() {
        return partsDivision;
    }

    public void setPartsDivision(String partsDivision) {
        this.partsDivision = partsDivision;
    }

    public String getSecInsId() {
        return secInsId;
    }

    public void setSecInsId(String secInsId) {
        this.secInsId = secInsId;
    }

    public String getUtiId() {
        return utiId;
    }

    public void setUtiId(String utiId) {
        this.utiId = utiId;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }

    public String getSupervisorUtiId() {
        return supervisorUtiId;
    }

    public void setSupervisorUtiId(String supervisorUtiId) {
        this.supervisorUtiId = supervisorUtiId;
    }

    public String getSupervisorStatus() {
        return supervisorStatus;
    }

    public void setSupervisorStatus(String supervisorStatus) {
        this.supervisorStatus = supervisorStatus;
    }

    public String getSupervisorExplain() {
        return supervisorExplain;
    }

    public void setSupervisorExplain(String supervisorExplain) {
        this.supervisorExplain = supervisorExplain;
    }

    public String getConstructionUtiId() {
        return constructionUtiId;
    }

    public void setConstructionUtiId(String constructionUtiId) {
        this.constructionUtiId = constructionUtiId;
    }

    public String getConstructionStatus() {
        return constructionStatus;
    }

    public void setConstructionStatus(String constructionStatus) {
        this.constructionStatus = constructionStatus;
    }

    public String getPeopleStatus() {
        return peopleStatus;
    }

    public void setPeopleStatus(String peopleStatus) {
        this.peopleStatus = peopleStatus;
    }

    public String getBackRecord() {
        return backRecord;
    }

    public void setBackRecord(String backRecord) {
        this.backRecord = backRecord;
    }

    public String getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorPeople() {
        return creatorPeople;
    }

    public void setCreatorPeople(String creatorPeople) {
        this.creatorPeople = creatorPeople;
    }

    public String getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(String deleteButton) {
        this.deleteButton = deleteButton;
    }

    public List<ChildCreatorBean> getChildCreator() {
        return childCreator;
    }

    public void setChildCreator(List<ChildCreatorBean> childCreator) {
        this.childCreator = childCreator;
    }

    public List<ChildSBean> getChildS() {
        return childS;
    }

    public void setChildS(List<ChildSBean> childS) {
        this.childS = childS;
    }

    public List<ChildCBean> getChildC() {
        return childC;
    }

    public void setChildC(List<ChildCBean> childC) {
        this.childC = childC;
    }

    public List<ChildPBean> getChildP() {
        return childP;
    }

    public void setChildP(List<ChildPBean> childP) {
        this.childP = childP;
    }

    public List<String> getChildPV() {
        return childPV;
    }

    public void setChildPV(List<String> childPV) {
        this.childPV = childPV;
    }

    public List<String> getChildPVSUP() {
        return childPVSUP;
    }

    public void setChildPVSUP(List<String> childPVSUP) {
        this.childPVSUP = childPVSUP;
    }

    public List<?> getChildPVCON() {
        return childPVCON;
    }

    public void setChildPVCON(List<String > childPVCON) {
        this.childPVCON = childPVCON;
    }

    public List<ChildBackBean> getChildBack() {
        return childBack;
    }

    public void setChildBack(List<ChildBackBean> childBack) {
        this.childBack = childBack;
    }

    public List<?> getListAbarbeitung() {
        return listAbarbeitung;
    }

    public void setListAbarbeitung(List<String > listAbarbeitung) {
        this.listAbarbeitung = listAbarbeitung;
    }

    public static class ChildCreatorBean {
        /**
         * userId :
         * peopleuser : 监理人员
         * tel : 15937085623
         * userAppTag :
         */

        public String userId;
        public String peopleuser;
        public String tel;
        public String userAppTag;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPeopleuser() {
            return peopleuser;
        }

        public void setPeopleuser(String peopleuser) {
            this.peopleuser = peopleuser;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUserAppTag() {
            return userAppTag;
        }

        public void setUserAppTag(String userAppTag) {
            this.userAppTag = userAppTag;
        }
    }

    public static class ChildSBean {
        /**
         * userId :
         * peopleuser : 监理人员
         * tel : 15937085623
         * userAppTag :
         */

        public String userId;
        public String peopleuser;
        public String tel;
        public String userAppTag;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPeopleuser() {
            return peopleuser;
        }

        public void setPeopleuser(String peopleuser) {
            this.peopleuser = peopleuser;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUserAppTag() {
            return userAppTag;
        }

        public void setUserAppTag(String userAppTag) {
            this.userAppTag = userAppTag;
        }
    }

    public static class ChildCBean {
        /**
         * userId :
         * peopleuser : 建设人员
         * tel : 15937089523
         * userAppTag :
         */

        public String userId;
        public String peopleuser;
        public String tel;
        public String userAppTag;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPeopleuser() {
            return peopleuser;
        }

        public void setPeopleuser(String peopleuser) {
            this.peopleuser = peopleuser;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUserAppTag() {
            return userAppTag;
        }

        public void setUserAppTag(String userAppTag) {
            this.userAppTag = userAppTag;
        }
    }

    public static class ChildPBean {
        /**
         * userId :
         * peopleuser : 施工人员
         * tel : 15937089523
         * userAppTag :
         */

        public String userId;
        public String peopleuser;
        public String tel;
        public String userAppTag;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPeopleuser() {
            return peopleuser;
        }

        public void setPeopleuser(String peopleuser) {
            this.peopleuser = peopleuser;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getUserAppTag() {
            return userAppTag;
        }

        public void setUserAppTag(String userAppTag) {
            this.userAppTag = userAppTag;
        }
    }

    public static class ChildBackBean implements Serializable {
        /**
         * backId : 42
         * appGxyjId : 223
         * backType : 建设人
         * backUsername : 建设人员
         * backCause : 梁箍筋规格、尺寸、数量、加密箍区不满足图纸及规范要求
         * backExplain : Hxhxhxbbx
         * constructionUnit : 金隅保温（翠园A1#一标段）2
         * constructionDirector : 监理人员
         * creationTime : 2019-07-09 19:37:43
         * backCreationTime : 2019-07-09 19:39:42
         * transferRecord : 09e94f96496d44b5aa2b2b0a552883ff.png
         * transferExplain :
         * backPictureVideo : 97d17f85fca74079a8351d4ec575469d.png
         * childbackPictureVideo : ["http://192.168.110.66:8080/ydzj-admin/images/97d17f85fca74079a8351d4ec575469d.png"]
         * childTransferRecord : ["http://192.168.110.66:8080/ydzj-admin/images/09e94f96496d44b5aa2b2b0a552883ff.png"]
         * backNumber : 2
         */

        public String backId;
        public String appGxyjId;
        public String backType;
        public String backUsername;
        public String backCause;
        public String backExplain;
        public String constructionUnit;
        public String constructionDirector;
        public String creationTime;
        public String backCreationTime;
        public String transferRecord;
        public String transferExplain;
        public String backPictureVideo;
        public String backNumber;
        public List<String> childbackPictureVideo;
        public List<String> childTransferRecord;

        public String getBackId() {
            return backId;
        }

        public void setBackId(String backId) {
            this.backId = backId;
        }

        public String getAppGxyjId() {
            return appGxyjId;
        }

        public void setAppGxyjId(String appGxyjId) {
            this.appGxyjId = appGxyjId;
        }

        public String getBackType() {
            return backType;
        }

        public void setBackType(String backType) {
            this.backType = backType;
        }

        public String getBackUsername() {
            return backUsername;
        }

        public void setBackUsername(String backUsername) {
            this.backUsername = backUsername;
        }

        public String getBackCause() {
            return backCause;
        }

        public void setBackCause(String backCause) {
            this.backCause = backCause;
        }

        public String getBackExplain() {
            return backExplain;
        }

        public void setBackExplain(String backExplain) {
            this.backExplain = backExplain;
        }

        public String getConstructionUnit() {
            return constructionUnit;
        }

        public void setConstructionUnit(String constructionUnit) {
            this.constructionUnit = constructionUnit;
        }

        public String getConstructionDirector() {
            return constructionDirector;
        }

        public void setConstructionDirector(String constructionDirector) {
            this.constructionDirector = constructionDirector;
        }

        public String getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(String creationTime) {
            this.creationTime = creationTime;
        }

        public String getBackCreationTime() {
            return backCreationTime;
        }

        public void setBackCreationTime(String backCreationTime) {
            this.backCreationTime = backCreationTime;
        }

        public String getTransferRecord() {
            return transferRecord;
        }

        public void setTransferRecord(String transferRecord) {
            this.transferRecord = transferRecord;
        }

        public String getTransferExplain() {
            return transferExplain;
        }

        public void setTransferExplain(String transferExplain) {
            this.transferExplain = transferExplain;
        }

        public String getBackPictureVideo() {
            return backPictureVideo;
        }

        public void setBackPictureVideo(String backPictureVideo) {
            this.backPictureVideo = backPictureVideo;
        }

        public String getBackNumber() {
            return backNumber;
        }

        public void setBackNumber(String backNumber) {
            this.backNumber = backNumber;
        }

        public List<String> getChildbackPictureVideo() {
            return childbackPictureVideo;
        }

        public void setChildbackPictureVideo(List<String> childbackPictureVideo) {
            this.childbackPictureVideo = childbackPictureVideo;
        }

        public List<String> getChildTransferRecord() {
            return childTransferRecord;
        }

        public void setChildTransferRecord(List<String> childTransferRecord) {
            this.childTransferRecord = childTransferRecord;
        }
    }
}
