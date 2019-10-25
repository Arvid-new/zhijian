package com.haozhiyan.zhijian.bean;

import java.util.List;

public class clysSpecificationListOneBean {

    private List<ClysSpecificationListBean> clysSpecificationList;

    public List<ClysSpecificationListBean> getClysSpecificationList() {
        return clysSpecificationList;
    }

    public void setClysSpecificationList(List<ClysSpecificationListBean> clysSpecificationList) {
        this.clysSpecificationList = clysSpecificationList;
    }

    public static class ClysSpecificationListBean {
        /**
         * id : 11
         * inspectNumberTwo : 1
         */

        private String id;
        private String inspectNumber;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInspectNumber() {
            return inspectNumber;
        }

        public void setInspectNumber(String inspectNumberTwo) {
            this.inspectNumber = inspectNumberTwo;
        }
    }
}
