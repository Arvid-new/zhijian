package com.haozhiyan.zhijian.bean;

import java.util.List;

public class MaterialsTaskBean  {

    /**
     * msg : success
     * code : 0
     * clysTask : {"id":3,"state":"1","projectId":"46","sectionId":"30","sectionName":"测试标段0613","nameInspectionId":"2796190148067328","nameInspection":"钢筋","number":"第1批","typeInspectionId":"2796190148067328,2796192815644672","typeInspection":"钢筋,保温材料","supplierId":"6","supplierName":"方大（紫园91#）","applypart":"破哦婆婆说","supplement":"阿婆婆老婆婆婆 Mr","receive":"1","receiveName":"admin","receiveUnit":"109","receiveUnitName":"测试承建商456","supervisor":"1","supervisorName":"admin","isneedAcceptance":"1","acceptance":"1","acceptanceName":"admin","cc":"1","ccName":"admin","submit":"1","submitName":"admin","submitDate":"2019-06-18 16:06:28.0","receiveList":[{"peopleuser":"admin","tel":"1111111111111111111","userId":1}],"supervisorList":[{"peopleuser":"admin","tel":"1111111111111111111","userId":1}],"acceptanceList":[{"id":15,"taskId":3,"acceptance":"1","userId":"1","peopleuser":"admin","tel":"1111111111111111111","isAcceptance":true,"isQualified":"ture","acceptanceDate":"","acceptanceImage":"","acceptanceImageList":"","acceptanceSupplement":""}],"ccList":[{"peopleuser":"admin","tel":"1111111111111111111","userId":1}],"submitList":[{"peopleuser":"admin","tel":"1111111111111111111","userId":1}],"typeInspectionIdList":["2796190148067328","2796192815644672"],"typeInspectionList":["钢筋","保温材料"]}
     */

    public String msg;
    public int code;
    public ClysTaskBean clysTask;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ClysTaskBean getClysTask() {
        return clysTask;
    }

    public void setClysTask(ClysTaskBean clysTask) {
        this.clysTask = clysTask;
    }

    public static class ClysTaskBean  {
        /**
         * id : 3
         * state : 1
         * projectId : 46
         * sectionId : 30
         * sectionName : 测试标段0613
         * nameInspectionId : 2796190148067328
         * nameInspection : 钢筋
         * number : 第1批
         * typeInspectionId : 2796190148067328,2796192815644672
         * typeInspection : 钢筋,保温材料
         * supplierId : 6
         * supplierName : 方大（紫园91#）
         * applypart : 破哦婆婆说
         * supplement : 阿婆婆老婆婆婆 Mr
         * receive : 1
         * receiveName : admin
         * receiveUnit : 109
         * receiveUnitName : 测试承建商456
         * supervisor : 1
         * supervisorName : admin
         * isneedAcceptance : 1
         * acceptance : 1
         * acceptanceName : admin
         * cc : 1
         * ccName : admin
         * submit : 1
         * submitName : admin
         * submitDate : 2019-06-18 16:06:28.0
         * receiveList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * supervisorList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * acceptanceList : [{"id":15,"taskId":3,"acceptance":"1","userId":"1","peopleuser":"admin","tel":"1111111111111111111","isAcceptance":true,"isQualified":"ture","acceptanceDate":"","acceptanceImage":"","acceptanceImageList":"","acceptanceSupplement":""}]
         * ccList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * submitList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * typeInspectionIdList : ["2796190148067328","2796192815644672"]
         * typeInspectionList : ["钢筋","保温材料"]
         */

        public int id;
        public String state;
        public String projectId;
        public String sectionId;
        public String sectionName;
        public String nameInspectionId;
        public String nameInspection;
        public String number;
        public String typeInspectionId;
        public String typeInspection;
        public String supplierId;
        public String supplierName;
        public String applypart;
        public String supplement;
        public String receive;
        public String receiveName;
        public String receiveUnit;
        public String receiveUnitName;
        public String supervisor;
        public String supervisorName;
        public String isneedAcceptance;
        public String acceptance;
        public String acceptanceName;
        public String cc;
        public String ccName;
        public String submit;
        public String submitName;
        public String submitDate;
        public List<ReceiveListBean> receiveList;
        public List<SupervisorListBean> supervisorList;
        public List<AcceptanceListBean> acceptanceList;
        public List<CcListBean> ccList;
        public List<SubmitListBean> submitList;
        public List<String> typeInspectionIdList;
        public List<String> typeInspectionList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getProjectId() {
            return projectId;
        }

        public void setProjectId(String projectId) {
            this.projectId = projectId;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getNameInspectionId() {
            return nameInspectionId;
        }

        public void setNameInspectionId(String nameInspectionId) {
            this.nameInspectionId = nameInspectionId;
        }

        public String getNameInspection() {
            return nameInspection;
        }

        public void setNameInspection(String nameInspection) {
            this.nameInspection = nameInspection;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getTypeInspectionId() {
            return typeInspectionId;
        }

        public void setTypeInspectionId(String typeInspectionId) {
            this.typeInspectionId = typeInspectionId;
        }

        public String getTypeInspection() {
            return typeInspection;
        }

        public void setTypeInspection(String typeInspection) {
            this.typeInspection = typeInspection;
        }

        public String getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(String supplierId) {
            this.supplierId = supplierId;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public void setSupplierName(String supplierName) {
            this.supplierName = supplierName;
        }

        public String getApplypart() {
            return applypart;
        }

        public void setApplypart(String applypart) {
            this.applypart = applypart;
        }

        public String getSupplement() {
            return supplement;
        }

        public void setSupplement(String supplement) {
            this.supplement = supplement;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getReceiveUnit() {
            return receiveUnit;
        }

        public void setReceiveUnit(String receiveUnit) {
            this.receiveUnit = receiveUnit;
        }

        public String getReceiveUnitName() {
            return receiveUnitName;
        }

        public void setReceiveUnitName(String receiveUnitName) {
            this.receiveUnitName = receiveUnitName;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }

        public String getSupervisorName() {
            return supervisorName;
        }

        public void setSupervisorName(String supervisorName) {
            this.supervisorName = supervisorName;
        }

        public String getIsneedAcceptance() {
            return isneedAcceptance;
        }

        public void setIsneedAcceptance(String isneedAcceptance) {
            this.isneedAcceptance = isneedAcceptance;
        }

        public String getAcceptance() {
            return acceptance;
        }

        public void setAcceptance(String acceptance) {
            this.acceptance = acceptance;
        }

        public String getAcceptanceName() {
            return acceptanceName;
        }

        public void setAcceptanceName(String acceptanceName) {
            this.acceptanceName = acceptanceName;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        public String getCcName() {
            return ccName;
        }

        public void setCcName(String ccName) {
            this.ccName = ccName;
        }

        public String getSubmit() {
            return submit;
        }

        public void setSubmit(String submit) {
            this.submit = submit;
        }

        public String getSubmitName() {
            return submitName;
        }

        public void setSubmitName(String submitName) {
            this.submitName = submitName;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }

        public List<ReceiveListBean> getReceiveList() {
            return receiveList;
        }

        public void setReceiveList(List<ReceiveListBean> receiveList) {
            this.receiveList = receiveList;
        }

        public List<SupervisorListBean> getSupervisorList() {
            return supervisorList;
        }

        public void setSupervisorList(List<SupervisorListBean> supervisorList) {
            this.supervisorList = supervisorList;
        }

        public List<AcceptanceListBean> getAcceptanceList() {
            return acceptanceList;
        }

        public void setAcceptanceList(List<AcceptanceListBean> acceptanceList) {
            this.acceptanceList = acceptanceList;
        }

        public List<CcListBean> getCcList() {
            return ccList;
        }

        public void setCcList(List<CcListBean> ccList) {
            this.ccList = ccList;
        }

        public List<SubmitListBean> getSubmitList() {
            return submitList;
        }

        public void setSubmitList(List<SubmitListBean> submitList) {
            this.submitList = submitList;
        }

        public List<String> getTypeInspectionIdList() {
            return typeInspectionIdList;
        }

        public void setTypeInspectionIdList(List<String> typeInspectionIdList) {
            this.typeInspectionIdList = typeInspectionIdList;
        }

        public List<String> getTypeInspectionList() {
            return typeInspectionList;
        }

        public void setTypeInspectionList(List<String> typeInspectionList) {
            this.typeInspectionList = typeInspectionList;
        }

        public static class ReceiveListBean  {
            /**
             * peopleuser : admin
             * tel : 1111111111111111111
             * userId : 1
             */

            public String peopleuser;
            public String tel;
            public int userId;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }

        public static class SupervisorListBean  {
            /**
             * peopleuser : admin
             * tel : 1111111111111111111
             * userId : 1
             */

            public String peopleuser;
            public String tel;
            public int userId;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }

        public static class AcceptanceListBean  {
            /**
             * id : 15
             * taskId : 3
             * acceptance : 1
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             * isAcceptance : true
             * isQualified : ture
             * acceptanceDate :
             * acceptanceImage :
             * acceptanceImageList :
             * acceptanceSupplement :
             */

            public int id;
            public int taskId;
            public String acceptance;
            public String userId;
            public String peopleuser;
            public String tel;
            public boolean isAcceptance;
            public String isQualified;
            public String acceptanceDate;
            public String acceptanceImage;
            public List<String> acceptanceImageList;
            public String acceptanceSupplement;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }

            public String getAcceptance() {
                return acceptance;
            }

            public void setAcceptance(String acceptance) {
                this.acceptance = acceptance;
            }

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

            public boolean isAcceptance() {
                return isAcceptance;
            }

            public void setAcceptance(boolean acceptance) {
                isAcceptance = acceptance;
            }

            public String getIsQualified() {
                return isQualified;
            }

            public void setIsQualified(String isQualified) {
                this.isQualified = isQualified;
            }

            public String getAcceptanceDate() {
                return acceptanceDate;
            }

            public void setAcceptanceDate(String acceptanceDate) {
                this.acceptanceDate = acceptanceDate;
            }

            public String getAcceptanceImage() {
                return acceptanceImage;
            }

            public void setAcceptanceImage(String acceptanceImage) {
                this.acceptanceImage = acceptanceImage;
            }

            public List<String> getAcceptanceImageList() {
                return acceptanceImageList;
            }

            public void setAcceptanceImageList(List<String> acceptanceImageList) {
                this.acceptanceImageList = acceptanceImageList;
            }

            public String getAcceptanceSupplement() {
                return acceptanceSupplement;
            }

            public void setAcceptanceSupplement(String acceptanceSupplement) {
                this.acceptanceSupplement = acceptanceSupplement;
            }
        }

        public static class CcListBean  {
            /**
             * peopleuser : admin
             * tel : 1111111111111111111
             * userId : 1
             */

            public String peopleuser;
            public String tel;
            public int userId;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }

        public static class SubmitListBean  {
            /**
             * peopleuser : admin
             * tel : 1111111111111111111
             * userId : 1
             */

            public String peopleuser;
            public String tel;
            public int userId;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
