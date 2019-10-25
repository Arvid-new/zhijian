package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/12.
 * Describe: 工序移交状态详情bean
 */
public class GXYJStatusDetail implements Serializable {


    /**
     * msg : success
     * code : 0
     * list : [{"id":116,"roomTowerFloorId":"1018,","detailsName":"主体工程-梁板综合预埋","sectionName":"APP-工序移交-测试专用","siteName":"11栋-1单元","towerName":null,"unitName":null,"handOverPart":"4","constructionUnit":"华水（翠园3#）","constructionDirector":"测试","supervisor":"admin","constructionUnitPrincipal":"admin","copyPeople":"默认人员,admin","pictureVideo":"4bdd532de30346bba9bbcfcaffd8eb27.jpg,56c3b7dc4a5f4625aa71b8d7948e1da3.png,2e81d92dcdfa424abcf3a59ca0e742bc.jpg","explainIssue":"哈哈哈哈","projectId":null,"projectName":null,"pkId":null,"dikuaiName":null,"sectionId":31,"towerId":30,"unitId":56,"inspectionName":"主体工程","inspectionSunName":"梁板综合预埋","partsDivision":"不分单元-整层","secInsId":null,"userId":0,"utiId":null,"supervisorId":1,"constructionId":1,"peopleId":"1,1","identifying":"已验收","supervisorUtiId":null,"supervisorStatus":null,"supervisorPictureVideo":"47a23ef19d6c4f54bd078170e06ad7a8.jpg,cdaf32fd80744d7981568f8bfba71951.jpg","supervisorExplain":"工程合格，做的不错，继续加油！！！","constructionUtiId":null,"constructionStatus":null,"constructionPictureVideo":"4aa743e8bacd4984b1ded49e2e2262fc.png","constructionExplain":"Bshjdjdjdjdjdjjjg ","peopleStatus":null,"backRecord":0,"inspectionId":null,"creationTime":"2019-06-19 14:12:27","creatorPeople":null,"identifyingC":"已验收","identifyingS":"已验收","jianliPeopleTime":"2019-06-19 15:20:59","jianshePeopleTime":"2019-06-19 15:34:16","childS":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"childC":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"childP":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"childPV":["http://ms.mienre.com/ydzj-admin/images/4bdd532de30346bba9bbcfcaffd8eb27.jpg","http://ms.mienre.com/ydzj-admin/images/56c3b7dc4a5f4625aa71b8d7948e1da3.png","http://ms.mienre.com/ydzj-admin/images/2e81d92dcdfa424abcf3a59ca0e742bc.jpg"],"childPVSUP":[],"childPVCON":[],"childBack":[],"listAbarbeitung":[]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 116
         * roomTowerFloorId : 1018,
         * detailsName : 主体工程-梁板综合预埋
         * sectionName : APP-工序移交-测试专用
         * siteName : 11栋-1单元
         * towerName : null
         * unitName : null
         * handOverPart : 4
         * constructionUnit : 华水（翠园3#）
         * constructionDirector : 测试
         * supervisor : admin
         * constructionUnitPrincipal : admin
         * copyPeople : 默认人员,admin
         * pictureVideo : 4bdd532de30346bba9bbcfcaffd8eb27.jpg,56c3b7dc4a5f4625aa71b8d7948e1da3.png,2e81d92dcdfa424abcf3a59ca0e742bc.jpg
         * explainIssue : 哈哈哈哈
         * projectId : null
         * projectName : null
         * pkId : null
         * dikuaiName : null
         * sectionId : 31
         * towerId : 30
         * unitId : 56
         * inspectionName : 主体工程
         * inspectionSunName : 梁板综合预埋
         * partsDivision : 不分单元-整层
         * secInsId : null
         * userId : 0
         * utiId : null
         * supervisorId : 1
         * constructionId : 1
         * peopleId : 1,1
         * identifying : 已验收
         * supervisorUtiId : null
         * supervisorStatus : null
         * supervisorPictureVideo : 47a23ef19d6c4f54bd078170e06ad7a8.jpg,cdaf32fd80744d7981568f8bfba71951.jpg
         * supervisorExplain : 工程合格，做的不错，继续加油！！！
         * constructionUtiId : null
         * constructionStatus : null
         * constructionPictureVideo : 4aa743e8bacd4984b1ded49e2e2262fc.png
         * constructionExplain : Bshjdjdjdjdjdjjjg
         * peopleStatus : null
         * backRecord : 0
         * inspectionId : null
         * creationTime : 2019-06-19 14:12:27
         * creatorPeople : null
         * identifyingC : 已验收
         * identifyingS : 已验收
         * jianliPeopleTime : 2019-06-19 15:20:59
         * jianshePeopleTime : 2019-06-19 15:34:16
         * childS : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * childC : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * childP : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * childPV : ["http://ms.mienre.com/ydzj-admin/images/4bdd532de30346bba9bbcfcaffd8eb27.jpg","http://ms.mienre.com/ydzj-admin/images/56c3b7dc4a5f4625aa71b8d7948e1da3.png","http://ms.mienre.com/ydzj-admin/images/2e81d92dcdfa424abcf3a59ca0e742bc.jpg"]
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
        public String towerName;
        public String unitName;
        public String handOverPart;
        public String constructionUnit;
        public String constructionDirector;
        public String supervisor;
        public String constructionUnitPrincipal;
        public String copyPeople;
        public String pictureVideo;
        public String explainIssue;
        public int projectId;
        public String projectName;
        public int pkId;
        public String dikuaiName;
        public int sectionId;
        public int towerId;
        public int unitId;
        public String inspectionName;
        public String inspectionSunName;
        public String partsDivision;
        public Object secInsId;
        public int userId;
        public int utiId;
        public int supervisorId;
        public int constructionId;
        public String peopleId;
        public String identifying;
        public int supervisorUtiId;
        public String supervisorStatus;
        public String supervisorPictureVideo;
        public String supervisorExplain;
        public Object constructionUtiId;
        public Object constructionStatus;
        public String constructionPictureVideo;
        public String constructionExplain;
        public String peopleStatus;
        public int backRecord;
        public String inspectionId;
        public String creationTime;
        public String creatorPeople;
        public String identifyingC;
        public String identifyingS;
        public String jianliPeopleTime;
        public String jianshePeopleTime;
        public List<ChildSBean> childS;
        public List<ChildCBean> childC;
        public List<ChildPBean> childP;
        public List<String> childPV;
        public List<?> childPVSUP;
        public List<?> childPVCON;
        public List<?> childBack;
        public List<?> listAbarbeitung;

        public static class ChildSBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
            public String peopleuser;
            public String tel;
        }

        public static class ChildCBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
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
