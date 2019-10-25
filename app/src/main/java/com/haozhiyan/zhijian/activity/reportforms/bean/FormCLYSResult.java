package com.haozhiyan.zhijian.activity.reportforms.bean;

import java.io.Serializable;
import java.util.List;


public class FormCLYSResult {
    /**
     * amount : 1
     * total : 1
     * nameInspectionId : 828
     * nameInspection : 衬塑复合钢管
     * clysList : [{"titleName":"Ⅰ 标段衬塑复合钢管进场第1批","stateName":"待验收","approachDate":"2019-08-22","id":1,"state":"2"}]
     */

    private int amount;
    private int total;
    private String nameInspectionId;
    private String nameInspection;
    private List<ClysListBean> clysList;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public List<ClysListBean> getClysList() {
        return clysList;
    }

    public void setClysList(List<ClysListBean> clysList) {
        this.clysList = clysList;
    }

    public static class ClysListBean {
        /**
         * titleName : Ⅰ 标段衬塑复合钢管进场第1批
         * stateName : 待验收
         * approachDate : 2019-08-22
         * id : 1
         * state : 2
         */

        private String titleName;
        private String stateName;
        private String approachDate;
        private int id;
        private String state;

        public String getTitleName() {
            return titleName;
        }

        public void setTitleName(String titleName) {
            this.titleName = titleName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getApproachDate() {
            return approachDate;
        }

        public void setApproachDate(String approachDate) {
            this.approachDate = approachDate;
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

