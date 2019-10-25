package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/11.
 * Describe: Ydzj_project
 */
public class SCSLDetailData {

    /**
     * msg : success
     * code : 0
     * list : {"scslId":35,"userAppTag":1,"scslType":"施工","detailsName":"混凝土工程-截面尺寸","handOverPart":"102","siteName":"测试标段0613-1栋-1","scslState":"待整改","percentOfPass":null,"scslCount":null,"scslPicture":null,"scslCorrelationPicture":"afecc4c5c255438988be427f8631ede8.png,","qualifiedStandard":null,"inspectionPeopleId":214,"inspectionPeople":"施工-老王","inspectionState":"已整改","zhenggaiId":214,"zhenggaiPeople":"施工-老王","zhenggaiPicture":null,"zhenggaiExplain":null,"zhenggaiState":"待整改","zhenggaiTime":null,"roomTowerFloorId":4,"projectId":2,"projectName":"名门紫园","dikuaiId":46,"dikuaiName":"91#地块","sectionId":30,"sectionName":"测试标段0613","towerId":1,"towerName":"1栋","unitId":1,"unitName":"1","floorId":2,"floorName":"1层","inspectionId":"2","inspectionName":"混凝土工程","inspectionSunName":"截面尺寸","partsDivision":"分户","creatorTime":"2019-07-05 14:49:40","zhengGaiMassge":[{"userId":214,"mobile":"15937089523","peopleuser":"施工-老王"}],"xiangGuanPV":["http://192.168.110.66:8080/ydzj-admin/images/afecc4c5c255438988be427f8631ede8.png"],"zhengGaiPV":[]}
     */

    public String msg;
    public int code;
    public ListBean list;

    public static class ListBean {
        /**
         * scslId : 35
         * userAppTag : 1
         * scslType : 施工
         * detailsName : 混凝土工程-截面尺寸
         * handOverPart : 102
         * siteName : 测试标段0613-1栋-1
         * scslState : 待整改
         * percentOfPass : null
         * scslCount : null
         * scslPicture : null
         * scslCorrelationPicture : afecc4c5c255438988be427f8631ede8.png,
         * qualifiedStandard : null
         * inspectionPeopleId : 214
         * inspectionPeople : 施工-老王
         * inspectionState : 已整改
         * zhenggaiId : 214
         * zhenggaiPeople : 施工-老王
         * zhenggaiPicture : null
         * zhenggaiExplain : null
         * zhenggaiState : 待整改
         * zhenggaiTime : null
         * roomTowerFloorId : 4
         * projectId : 2
         * projectName : 名门紫园
         * dikuaiId : 46
         * dikuaiName : 91#地块
         * sectionId : 30
         * sectionName : 测试标段0613
         * towerId : 1
         * towerName : 1栋
         * unitId : 1
         * unitName : 1
         * floorId : 2
         * floorName : 1层
         * inspectionId : 2
         * inspectionName : 混凝土工程
         * inspectionSunName : 截面尺寸
         * partsDivision : 分户
         * creatorTime : 2019-07-05 14:49:40
         * zhengGaiMassge : [{"userId":214,"mobile":"15937089523","peopleuser":"施工-老王"}]
         * xiangGuanPV : ["http://192.168.110.66:8080/ydzj-admin/images/afecc4c5c255438988be427f8631ede8.png"]
         * zhengGaiPV : []
         */

        public int scslId;
        public int userAppTag;
        public String scslType;
        public String detailsName;
        public String handOverPart;
        public String siteName;
        public String scslState;
        public String percentOfPass;
        public String scslCount;
        public Object scslPicture;
        public String scslCorrelationPicture;
        public String qualifiedStandard;
        public int inspectionPeopleId;
        public String inspectionPeople;
        public String inspectionState;
        public int zhenggaiId;
        public String zhenggaiPeople;
        public Object zhenggaiPicture;
        public String zhenggaiExplain;
        public String zhenggaiState;
        public String zhenggaiTime;
        public int roomTowerFloorId;
        public int projectId;
        public String projectName;
        public int dikuaiId;
        public String dikuaiName;
        public int sectionId;
        public String sectionName;
        public int towerId;
        public String towerName;
        public int unitId;
        public String unitName;
        public int floorId;
        public String floorName;
        public String inspectionId;
        public String inspectionName;
        public String inspectionSunName;
        public String partsDivision;
        public String creatorTime;
        public List<ZhengGaiMassgeBean> zhengGaiMassge;
        public List<String> xiangGuanPV;
        public List<String> zhengGaiPV;

        public static class ZhengGaiMassgeBean {
            /**
             * userId : 214
             * mobile : 15937089523
             * peopleuser : 施工-老王
             */
            public int userId;
            public String mobile;
            public String peopleuser;
        }
    }
}
