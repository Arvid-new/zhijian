package com.haozhiyan.zhijian.bean;

import java.util.List;

public class CaiLiaoIndexListBean {

    /**
     * msg : success
     * code : 0
     * clysList : [{"sectionName":"测试标段0613","number":"第1批","nameInspection":"钢筋","stateName":"待申请进场","submitDate":"2019-06-18","id":3,"state":"1"}]
     */

    private String msg;
    private int code;
    private List<ClysListBean> clysList;

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

    public List<ClysListBean> getClysList() {
        return clysList;
    }

    public void setClysList(List<ClysListBean> clysList) {
        this.clysList = clysList;
    }

    public static class ClysListBean {
        /**
         * sectionName : 测试标段0613
         * number : 第1批
         * nameInspection : 钢筋
         * stateName : 待申请进场
         * submitDate : 2019-06-18
         * id : 3
         * state : 1
         * supervisorResult : 1  监理未验收 为2
         */

        private String sectionName;
        private String number;
        private String nameInspection;
        private String stateName;
        private String submitDate;
        private int id;
        private String state;
        private String supervisorResult;

        public String getSupervisorResult() {
            return supervisorResult;
        }

        public void setSupervisorResult(String supervisorResult) {
            this.supervisorResult = supervisorResult;
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
    }
}
