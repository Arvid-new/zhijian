package com.haozhiyan.zhijian.bean;

import java.util.List;

public class DaiBanCLYS1 {

    /**
     * msg : success
     * code : 0
     * doClysList : [{"sectionName":"测试标段0613","number":"第1批","nameInspection":"钢筋","stateName":"待申请进场","submitDate":"06-18","id":3,"state":"1","receiveVehicleImage":""}]
     */

    private String msg;
    private int code;
    private List<DoClysListBean> doClysList;

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

    public List<DoClysListBean> getDoClysList() {
        return doClysList;
    }

    public void setDoClysList(List<DoClysListBean> doClysList) {
        this.doClysList = doClysList;
    }

    public static class DoClysListBean {
        /**
         * sectionName : 测试标段0613
         * number : 第1批
         * nameInspection : 钢筋
         * stateName : 待申请进场
         * submitDate : 06-18
         * id : 3
         * state : 1
         * receiveVehicleImage :
         */

        private String sectionName;
        private String number;
        private String nameInspection;
        private String stateName;
        private String submitDate;
        private int id;
        private String state;
        private String checkResult;
        private String receiveVehicleImage;
        private String supervisorResult;

        public String getSupervisorResult() {
            return supervisorResult;
        }

        public void setSupervisorResult(String supervisorResult) {
            this.supervisorResult = supervisorResult;
        }

        public String getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(String checkResult) {
            this.checkResult = checkResult;
        }

        public String getSectionName() {
            return sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getNameInspection() {
            return nameInspection;
        }

        public void setNameInspection(String nameInspection) {
            this.nameInspection = nameInspection;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getSubmitDate() {
            return submitDate;
        }

        public void setSubmitDate(String submitDate) {
            this.submitDate = submitDate;
        }

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

        public String getReceiveVehicleImage() {
            return receiveVehicleImage;
        }

        public void setReceiveVehicleImage(String receiveVehicleImage) {
            this.receiveVehicleImage = receiveVehicleImage;
        }
    }
}
