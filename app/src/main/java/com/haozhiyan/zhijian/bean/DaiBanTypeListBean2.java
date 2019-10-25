package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/5.
 * Describe: Ydzj_project
 */
public class DaiBanTypeListBean2 {


    /**
     * msg : success
     * code : 0
     * doProblemList : [{"unitName":"2","submitDate":"06-04 17:06","backNumber":0,"batchId":"10","roomName":"101","isTimeout":-18052,"serious":"1","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/f33be170689047b295f125238e434be1.jpg","id":14,"state":"待整改","towerName":"6栋","inspectionName":"安全（安装）-塔吊","particularsName":null},{"unitName":"2","submitDate":"06-04 17:06","backNumber":0,"batchId":"10","roomName":"101","isTimeout":-18052,"serious":"1","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/f33be170689047b295f125238e434be1.jpg","id":14,"state":"待整改","towerName":"6栋","inspectionName":"安全（安装）-塔吊","particularsName":null},{"unitName":"3","submitDate":"06-04 17:17","backNumber":0,"batchId":"10","roomName":"201","isTimeout":-18052,"serious":"1","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/996387c2e2084139bacf8bc90c22cbf1.jpg","id":15,"state":"待整改","towerName":"6栋","inspectionName":"质量（安装）-水暖安装工程","particularsName":null},{"unitName":"3","submitDate":"06-04 17:17","backNumber":0,"batchId":"10","roomName":"201","isTimeout":-18052,"serious":"1","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/996387c2e2084139bacf8bc90c22cbf1.jpg","id":15,"state":"待整改","towerName":"6栋","inspectionName":"质量（安装）-水暖安装工程","particularsName":null},{"unitName":"3","submitDate":"06-04 17:17","backNumber":0,"batchId":"10","roomName":"201","isTimeout":-18052,"serious":"1","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/996387c2e2084139bacf8bc90c22cbf1.jpg","id":15,"state":"待整改","towerName":"6栋","inspectionName":"质量（安装）-水暖安装工程","particularsName":null},{"unitName":"2","submitDate":"06-04 19:50","backNumber":0,"batchId":"12","roomName":"202","isTimeout":-18052,"serious":"3","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/6c766191c84a4fec89ebcb4b4f7d3ede.jpg","id":21,"state":"待整改","towerName":"6栋","inspectionName":"质量（安装）-水暖安装工程","particularsName":null},{"unitName":"2","submitDate":"06-04 19:50","backNumber":0,"batchId":"12","roomName":"202","isTimeout":-18052,"serious":"3","problemImage":"http://192.168.110.8:8080/ydzj-admin/images/6c766191c84a4fec89ebcb4b4f7d3ede.jpg","id":21,"state":"待整改","towerName":"6栋","inspectionName":"质量（安装）-水暖安装工程","particularsName":null}]
     */

    public String msg;
    public int code;
    public List<DoProblemListBean> doProblemList;

    public static class DoProblemListBean {
        /**
         * unitName : 2
         * submitDate : 06-04 17:06
         * backNumber : 0
         * batchId : 10
         * roomName : 101
         * isTimeout : -18052
         * serious : 1
         * problemImage : http://192.168.110.8:8080/ydzj-admin/images/f33be170689047b295f125238e434be1.jpg
         * id : 14
         * state : 待整改
         * towerName : 6栋
         * inspectionName : 安全（安装）-塔吊
         * particularsName : null
         */

        public String unitName;
        public String submitDate;
        public int backNumber;
        public String batchId;
        public String roomName;
        public int isTimeout;
        public String serious;
        public String problemImage;
        public int id;
        public String state;
        public String towerName;
        public String inspectionName;
        public String particularsName;
    }
}
