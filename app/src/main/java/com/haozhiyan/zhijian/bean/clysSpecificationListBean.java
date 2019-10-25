package com.haozhiyan.zhijian.bean;

import java.util.List;

public class clysSpecificationListBean {

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
        private String inspectNumberTwo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInspectNumberTwo() {
            return inspectNumberTwo;
        }

        public void setInspectNumberTwo(String inspectNumberTwo) {
            this.inspectNumberTwo = inspectNumberTwo;
        }
    }
}
