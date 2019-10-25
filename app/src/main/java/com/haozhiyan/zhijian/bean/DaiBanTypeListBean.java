package com.haozhiyan.zhijian.bean;

import java.util.List;

public class DaiBanTypeListBean {


    /**
     * status : 1
     * msg : aa
     * data : [{"img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2940606656,3811047262&fm=26&gp=0.jpg","title":"质量（土建）-防水检查","descrip":"描述-*****不符合要求-------","date":"01-20","address":"4栋2单元202","state":"0","statemsg":"待整改","chao":"0","tui":"1"},{"img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2940606656,3811047262&fm=26&gp=0.jpg","title":"质量（土建）-防水检查","descrip":"描述-*****不符合要求-------","date":"01-20","address":"4栋2单元202","state":"0","statemsg":"待整改","chao":"1","tui":"1"},{"img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2940606656,3811047262&fm=26&gp=0.jpg","title":"质量（土建）-防水检查","descrip":"描述-*****不符合要求-------","date":"01-20","address":"4栋2单元202","state":"1","statemsg":"待复验","chao":"1","tui":"2"},{"img":"https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2940606656,3811047262&fm=26&gp=0.jpg","title":"质量（土建）-防水检查","descrip":"描述-*****不符合要求-------","date":"01-20","address":"4栋2单元202","state":"2","statemsg":"已通过","chao":"0","tui":"1"}]
     */

    private String status;
    private String msg;
    private List<DataBean> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * img : https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2940606656,3811047262&fm=26&gp=0.jpg
         * title : 质量（土建）-防水检查
         * descrip : 描述-*****不符合要求-------
         * date : 01-20
         * address : 4栋2单元202
         * state : 0
         * statemsg : 待整改
         * chao : 0
         * tui : 1
         */

        private String img;
        private String title;
        private String descrip;
        private String date;
        private String address;
        private String state;
        private String statemsg;
        private String chao;
        private String tui;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescrip() {
            return descrip;
        }

        public void setDescrip(String descrip) {
            this.descrip = descrip;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getStatemsg() {
            return statemsg;
        }

        public void setStatemsg(String statemsg) {
            this.statemsg = statemsg;
        }

        public String getChao() {
            return chao;
        }

        public void setChao(String chao) {
            this.chao = chao;
        }

        public String getTui() {
            return tui;
        }

        public void setTui(String tui) {
            this.tui = tui;
        }
    }
}
