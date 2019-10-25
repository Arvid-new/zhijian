package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/29.
 * Describe: Ydzj_project
 */
public class ProblemDetailBean implements Serializable {


    /**
     * msg : success
     * xcjcProblem : {"id":8,"batchId":"9","projectId":"0","projectName":null,"sectionId":null,"sectionName":null,"state":"1","problemImage":"2de2cb0ada3e47999115d032efe87ab1.mp4","tower":"8","towerName":"3栋","unit":"15","unitName":"2","floor":"300","room":"1221","roomName":"803","housemap":"","inspectionId":"2795753260974080,2795753386803200","inspectionName":"质量（土建）-主体工程","particularsId":"2795753386803200","particularsName":null,"particularsSupplement":"视频测试","serious":"3","submit":"1","submitName":"admin","submitDate":"2019-06-01 14:34:58","rectifyTimelimit":"1970-01-01 00:00:00","rectify":"3","rectifyName":"李四","rectifyDate":null,"rectifyImage":null,"rectifySupplement":null,"review":"1,1","reviewName":"admin","cc":"1,1","ccName":"admin","dutyUnit":"6","dutyUnitName":"方大（紫园91#）","closeCause":null,"closeDate":null,"backCause":null,"backImage":null,"backNumber":0,"isTimeout":"-18048","xcjcReviewList":[{"id":13,"problemId":"8","review":"1","reviewName":"admin","mobile":"1111111111111111111","reviewDate":null,"reviewImage":null,"reviewSupplement":null,"isReview":"0"},{"id":14,"problemId":"8","review":"1","reviewName":"admin","mobile":"1111111111111111111","reviewDate":null,"reviewImage":null,"reviewSupplement":null,"isReview":"0"}],"submitList":[{"peopleuser":"admin","mobile":"1111111111111111111"}],"rectifyList":[{"peopleuser":"李四","mobile":"15837039536"}],"ccList":[{"peopleuser":"admin","mobile":"1111111111111111111"}],"problemImageList":["http://192.168.110.244:8080/ydzj-admin/images/2de2cb0ada3e47999115d032efe87ab1.mp4"],"rectifyImageList":null,"backImageList":null}
     * code : 0
     */

    public String msg;
    public XcjcProblemBean xcjcProblem;
    public int code;

    public static class XcjcProblemBean implements Serializable {
        /**
         * id : 8
         * batchId : 9
         * projectId : 0
         * projectName : null
         * sectionId : null
         * sectionName : null
         * state : 1
         * problemImage : 2de2cb0ada3e47999115d032efe87ab1.mp4
         * tower : 8
         * towerName : 3栋
         * unit : 15
         * unitName : 2
         * floor : 300
         * room : 1221
         * roomName : 803
         * housemap :
         * inspectionId : 2795753260974080,2795753386803200
         * inspectionName : 质量（土建）-主体工程
         * particularsId : 2795753386803200
         * particularsName : null
         * particularsSupplement : 视频测试
         * serious : 3
         * submit : 1
         * submitName : admin
         * submitDate : 2019-06-01 14:34:58
         * rectifyTimelimit : 1970-01-01 00:00:00
         * rectify : 3
         * rectifyName : 李四
         * rectifyDate : null
         * rectifyImage : null
         * rectifySupplement : null
         * review : 1,1
         * reviewName : admin
         * cc : 1,1
         * ccName : admin
         * dutyUnit : 6
         * dutyUnitName : 方大（紫园91#）
         * closeCause : null
         * closeDate : null
         * backCause : null
         * backImage : null
         * backNumber : 0
         * isTimeout : -18048
         * xcjcReviewList : [{"id":13,"problemId":"8","review":"1","reviewName":"admin","mobile":"1111111111111111111","reviewDate":null,"reviewImage":null,"reviewSupplement":null,"isReview":"0"},{"id":14,"problemId":"8","review":"1","reviewName":"admin","mobile":"1111111111111111111","reviewDate":null,"reviewImage":null,"reviewSupplement":null,"isReview":"0"}]
         * submitList : [{"peopleuser":"admin","mobile":"1111111111111111111"}]
         * rectifyList : [{"peopleuser":"李四","mobile":"15837039536"}]
         * ccList : [{"peopleuser":"admin","mobile":"1111111111111111111"}]
         * problemImageList : ["http://192.168.110.244:8080/ydzj-admin/images/2de2cb0ada3e47999115d032efe87ab1.mp4"]
         * rectifyImageList : null
         * backImageList : null
         */

        public int id;
        public String batchId;
        public String projectId;
        public String projectName;
        public String sectionId;
        public String sectionName;
        public String state;
        public String problemImage;
        public String tower;
        public String towerName;
        public String unit;
        public String unitName;
        public String floor;
        public String room;
        public String roomName;
        public String housemap;
        public String inspectionId;
        public String inspectionName;
        public String particularsId;
        public String particularsName;
        public String particularsSupplement;
        public String serious;
        public String submit;
        public String submitName;
        public String submitDate;
        public String rectifyTimelimit;
        public String rectify;
        public String rectifyName;
        public String rectifyDate;
        public String rectifyImage;
        public String rectifySupplement;
        public String review;
        public String reviewName;
        public String cc;
        public String ccName;
        public String dutyUnit;
        public String dutyUnitName;
        public String closeCause;
        public String closeDate;
        public String backCause;
        public String backImage;
        public int backNumber;
        public String isTimeout;
        public List<String> rectifyImageList;
        public List<String> backImageList;
        public List<XcjcReviewListBean> xcjcReviewList;
        public List<SubmitListBean> submitList;
        public List<RectifyListBean> rectifyList;
        public List<CcListBean> ccList;
        public List<String> problemImageList;
        public String isReviewAll;  // 所有复验人是否复验：0所有人都未复验，1有人已复验

        public static class XcjcReviewListBean  implements Serializable {
            /**
             * id : 13
             * problemId : 8
             * review : 1
             * reviewName : admin
             * mobile : 1111111111111111111
             * reviewDate : null
             * reviewImage : null
             * reviewSupplement : null
             * isReview : 0
             */

            public int id;
            public String problemId;
            public String review;
            public String reviewName;
            public String tel;
            public String reviewDate;
            public String reviewImage;
            public List<String> reviewImageList;
            public String reviewSupplement;
            public String isReview;
            public String userId;
            public String peopleuser;
            public String userAppTag;
        }

        public static class SubmitListBean  implements Serializable {
            /**
             * peopleuser : admin
             * mobile : 1111111111111111111
             */

            public String peopleuser;
            public String tel;
            public String userId;
            public int userAppTag;
        }

        public static class RectifyListBean  implements Serializable {
            /**
             * peopleuser : 李四
             * mobile : 15837039536
             */

            public String peopleuser;
            public String tel;
            public String userId;
            public int userAppTag;
        }

        public static class CcListBean  implements Serializable {
            /**
             * peopleuser : admin
             * mobile : 1111111111111111111
             */
            public String peopleuser;
            public String tel;
            public String userId;
            public int userAppTag;
        }
    }
}
