package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

public class AMReportInfoBean implements Serializable{


    /**
     * clysReportList : [{"id":1,"inspectDate":"2019-06-10","inspectReportImage":"2dff2b24eb9b456890a5b98eab75d93c.jpeg","inspectReportImageList":["http://192.168.110.195:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"],"inspectorList":[{"peopleuser":"admin","tel":"1111111111111111111","userId":1}],"reportResult":"1","taskId":2,"times":"1"}]
     * code : 0
     * msg : success
     */

    private int code;
    private String msg;
    private List<ClysReportListBean> clysReportList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ClysReportListBean> getClysReportList() {
        return clysReportList;
    }

    public void setClysReportList(List<ClysReportListBean> clysReportList) {
        this.clysReportList = clysReportList;
    }

    public static class ClysReportListBean implements Serializable {
        /**
         * id : 1
         * inspectDate : 2019-06-10
         * inspectReportImage : 2dff2b24eb9b456890a5b98eab75d93c.jpeg
         * inspectReportImageList : ["http://192.168.110.195:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
         * inspectorList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * reportResult : 1
         * taskId : 2
         * times : 1
         */

        private int id;
        private String inspectDate;
        private String inspectReportImage;
        private String reportResult;
        private String inspector;
        private int taskId;
        private String times;
        private List<String> inspectReportImageList;
        private List<InspectorListBean> inspectorList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInspectDate() {
            return inspectDate;
        }

        public String getInspector() {
            return inspector;
        }

        public void setInspector(String inspector) {
            this.inspector = inspector;
        }

        public void setInspectDate(String inspectDate) {
            this.inspectDate = inspectDate;
        }

        public String getInspectReportImage() {
            return inspectReportImage;
        }

        public void setInspectReportImage(String inspectReportImage) {
            this.inspectReportImage = inspectReportImage;
        }

        public String getReportResult() {
            return reportResult;
        }

        public void setReportResult(String reportResult) {
            this.reportResult = reportResult;
        }

        public int getTaskId() {
            return taskId;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public List<String> getInspectReportImageList() {
            return inspectReportImageList;
        }

        public void setInspectReportImageList(List<String> inspectReportImageList) {
            this.inspectReportImageList = inspectReportImageList;
        }

        public List<InspectorListBean> getInspectorList() {
            return inspectorList;
        }

        public void setInspectorList(List<InspectorListBean> inspectorList) {
            this.inspectorList = inspectorList;
        }

        public static class InspectorListBean  implements Serializable{
            /**
             * peopleuser : admin
             * tel : 1111111111111111111
             * userId : 1
             */

            private String peopleuser;
            private String tel;
            private int userId;

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
