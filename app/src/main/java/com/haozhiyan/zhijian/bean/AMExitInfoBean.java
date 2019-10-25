package com.haozhiyan.zhijian.bean;

import java.util.List;

public class AMExitInfoBean {

    /**
     * msg : success
     * code : 0
     * clysExit : {"id":5,"taskId":25,"exitDate":"2019-06-24","exitImage":"6d064aa036a84c629364423ab9f4e324.png","exitSupplement":"经济科技","supervisor":"","cc":"","exitImageList":["http://192.168.110.195:8080/ydzj-admin/images/6d064aa036a84c629364423ab9f4e324.png"],"supervisorList":[{"peopleuser":"admin","tel":"18933333333","userId":1}],"ccList":[{"peopleuser":"admin","tel":"18933333333","userId":1}]}
     */

    private String msg;
    private int code;
    private ClysExit clysExit;

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

    public ClysExit getClysExit() {
        return clysExit;
    }

    public void setClysExit(ClysExit clysExit) {
        this.clysExit = clysExit;
    }

    public static class ClysExit {
        /**
         * id : 5
         * taskId : 25
         * exitDate : 2019-06-24
         * exitImage : 6d064aa036a84c629364423ab9f4e324.png
         * exitSupplement : 经济科技
         * supervisor :
         * cc :
         * exitImageList : ["http://192.168.110.195:8080/ydzj-admin/images/6d064aa036a84c629364423ab9f4e324.png"]
         * supervisorList : [{"peopleuser":"admin","tel":"18933333333","userId":1}]
         * ccList : [{"peopleuser":"admin","tel":"18933333333","userId":1}]
         */

        private int id;
        private int taskId;
        private String exitDate;
        private String exitImage;
        private String exitSupplement;
        private String supervisor;
        private String cc;
        private List<String> exitImageList;
        private List<SupervisorListBean> supervisorList;
        private List<CcListBean> ccList;

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

        public String getExitDate() {
            return exitDate;
        }

        public void setExitDate(String exitDate) {
            this.exitDate = exitDate;
        }

        public String getExitImage() {
            return exitImage;
        }

        public void setExitImage(String exitImage) {
            this.exitImage = exitImage;
        }

        public String getExitSupplement() {
            return exitSupplement;
        }

        public void setExitSupplement(String exitSupplement) {
            this.exitSupplement = exitSupplement;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        public List<String> getExitImageList() {
            return exitImageList;
        }

        public void setExitImageList(List<String> exitImageList) {
            this.exitImageList = exitImageList;
        }

        public List<SupervisorListBean> getSupervisorList() {
            return supervisorList;
        }

        public void setSupervisorList(List<SupervisorListBean> supervisorList) {
            this.supervisorList = supervisorList;
        }

        public List<CcListBean> getCcList() {
            return ccList;
        }

        public void setCcList(List<CcListBean> ccList) {
            this.ccList = ccList;
        }

        public static class SupervisorListBean {
            /**
             * peopleuser : admin
             * tel : 18933333333
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

        public static class CcListBean {
            /**
             * peopleuser : admin
             * tel : 18933333333
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
