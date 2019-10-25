package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/12.
 * Describe: 工序移交状态详情bean
 */
public class GXYJStatusDetail2 implements Serializable {

    /**
     * msg : success
     * code : 0
     * list : [{"id":52,"roomTowerFloorId":"2,","detailsName":"主体工程-褥垫层验收","sectionName":"lzdemo测试1","siteName":"1栋-2单元","handOverPart":"整栋","constructionUnit":"翠园A1#二标段","constructionDirector":"测试","supervisor":"李四","constructionUnitPrincipal":null,"copyPeople":"默认人员,王五","pictureVideo":"8776da1a74cf46e3af936f6231270a66.jpg,a4fecce950984ea680c0f7cadadc2d8c.jpg","explainIssue":"搜索沙发斯蒂芬奋斗","sectionId":28,"towerId":1,"unitId":2,"inspectionName":"主体工程","inspectionSunName":"褥垫层验收","partsDivision":"整栋","secInsId":null,"userId":0,"utiId":null,"supervisorId":3,"constructionId":3,"peopleId":"1,1","identifying":"待验收","supervisorUtiId":null,"supervisorStatus":null,"supervisorPictureVideo":null,"supervisorExplain":null,"constructionUtiId":null,"constructionStatus":null,"constructionPictureVideo":null,"constructionExplain":null,"peopleStatus":null,"backRecord":0,"inspectionId":null,"creationTime":"2019-06-12 20:26:58","creatorPeople":null,"childS":[{"userId":3,"peopleuser":"李四","tel":"15837039536"}],"childC":[{"userId":3,"peopleuser":"李四","tel":"15837039536"}],"childP":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"childPV":["http://192.168.110.8:8080/ydzj-admin/images/8776da1a74cf46e3af936f6231270a66.jpg","http://192.168.110.8:8080/ydzj-admin/images/a4fecce950984ea680c0f7cadadc2d8c.jpg"],"childPVSUP":[],"childPVCON":[],"childBack":[],"listAbarbeitung":[]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean  implements Serializable {
        /**
         * id : 52
         * roomTowerFloorId : 2,
         * detailsName : 主体工程-褥垫层验收
         * sectionName : lzdemo测试1
         * siteName : 1栋-2单元
         * handOverPart : 整栋
         * constructionUnit : 翠园A1#二标段
         * constructionDirector : 测试
         * supervisor : 李四
         * constructionUnitPrincipal : null
         * copyPeople : 默认人员,王五
         * pictureVideo : 8776da1a74cf46e3af936f6231270a66.jpg,a4fecce950984ea680c0f7cadadc2d8c.jpg
         * explainIssue : 搜索沙发斯蒂芬奋斗
         * sectionId : 28
         * towerId : 1
         * unitId : 2
         * inspectionName : 主体工程
         * inspectionSunName : 褥垫层验收
         * partsDivision : 整栋
         * secInsId : null
         * userId : 0
         * utiId : null
         * supervisorId : 3
         * constructionId : 3
         * peopleId : 1,1
         * identifying : 待验收
         * supervisorUtiId : null
         * supervisorStatus : null
         * supervisorPictureVideo : null
         * supervisorExplain : null
         * constructionUtiId : null
         * constructionStatus : null
         * constructionPictureVideo : null
         * constructionExplain : null
         * peopleStatus : null
         * backRecord : 0
         * inspectionId : null
         * creationTime : 2019-06-12 20:26:58
         * creatorPeople : null
         * childS : [{"userId":3,"peopleuser":"李四","tel":"15837039536"}]
         * childC : [{"userId":3,"peopleuser":"李四","tel":"15837039536"}]
         * childP : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * childPV : ["http://192.168.110.8:8080/ydzj-admin/images/8776da1a74cf46e3af936f6231270a66.jpg","http://192.168.110.8:8080/ydzj-admin/images/a4fecce950984ea680c0f7cadadc2d8c.jpg"]
         * childPVSUP : []
         * childPVCON : []
         * childBack : []
         * listAbarbeitung : []
         */

        public int id;
        public String roomTowerFloorId;
        public String detailsName;
        public String sectionName;
        public String siteName;
        public String handOverPart;
        public String constructionUnit;
        public String constructionDirector;
        public String supervisor;
        public String constructionUnitPrincipal;
        public String copyPeople;
        public String pictureVideo;
        public String explainIssue;
        public int sectionId;
        public int towerId;
        public int unitId;
        public String inspectionName;
        public String inspectionSunName;
        public String partsDivision;
        public Object secInsId;
        public int userId;
        public Object utiId;
        public int supervisorId;
        public int constructionId;
        public String peopleId;
        public String identifying;
        public int supervisorUtiId;
        public String supervisorStatus;
        public String supervisorPictureVideo;
        public String supervisorExplain;
        public int constructionUtiId;
        public Object constructionStatus;
        public Object constructionPictureVideo;
        public Object constructionExplain;
        public Object peopleStatus;
        public int backRecord;
        public Object inspectionId;
        public String creationTime;
        public Object creatorPeople;
        public List<ChildSBean> childS;
        public List<ChildCBean> childC;
        public List<ChildPBean> childP;
        public List<String> childPV;
        public List<String> childPVSUP;
        public List<String> childPVCON;
        public List<String> childBack;
        public List<String> listAbarbeitung;

        public static class ChildSBean {
            /**
             * userId : 3
             * peopleuser : 李四
             * tel : 15837039536
             */

            public String userId;
            public String peopleuser;
            public String tel;
        }

        public static class ChildCBean {
            /**
             * userId : 3
             * peopleuser : 李四
             * tel : 15837039536
             */

            public String userId;
            public String peopleuser;
            public String tel;
        }

        public static class ChildPBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
            public String peopleuser;
            public String tel;
        }
    }
}
