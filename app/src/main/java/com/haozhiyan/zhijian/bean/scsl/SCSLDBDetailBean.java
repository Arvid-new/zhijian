package com.haozhiyan.zhijian.bean.scsl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/18.
 * Describe: Ydzj_project
 */
public class SCSLDBDetailBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * list : {"scslId":4,"userAppTag":1,"scslType":"建设","detailsName":"主体工程-梁板综合预埋","handOverPart":"201","siteName":"某标段-某栋-某单元","scslState":"待整改","percentOfPass":"100%","scslCount":"4","scslPicture":"图片编辑","scslCorrelationPicture":"相关照片","qualifiedStandard":"截面尺寸[-5,8]","inspectionPeopleId":1,"inspectionPeople":"检查人","zhenggaiId":1,"zhenggaiPeople":"整改人","zhenggaiPicture":"整改照片","zhenggaiExplain":"整改说明","zhenggaiTime":"2019-06-21 16:18:24","roomTowerFloorId":2,"projectId":1,"projectName":"项目名字","dikuaiId":46,"dikuaiName":"地块名称","sectionId":1,"sectionName":"标段名称 ","towerId":1,"towerName":"楼栋名称","unitId":1,"unitName":"单元名称","floorId":1,"floorName":"楼层名称","inspectionId":"1","inspectionName":"混凝土工程","inspectionSunName":"截面尺寸","partsDivision":"分户/不分单元-整层/分单元-整层","creatorTime":"2019-06-21 16:20:14","zhengGaiMassge":[{"userId":1,"mobile":"18933333333","peopleuser":"admin"}],"bianJiPV":["http://ms.mienre.com/ydzj-admin/images/图片编辑"],"zhengGaiPV":["http://ms.mienre.com/ydzj-admin/images/整改照片"],"xiangGuanPV":["http://ms.mienre.com/ydzj-admin/images/相关照片"]}
     */

    public String msg;
    public int code;
    public ListBean list;

    public static class ListBean implements Serializable{
        /**
         * scslId : 4
         * userAppTag : 1
         * scslType : 建设
         * detailsName : 主体工程-梁板综合预埋
         * handOverPart : 201
         * siteName : 某标段-某栋-某单元
         * scslState : 待整改
         * percentOfPass : 100%
         * scslCount : 4
         * scslPicture : 图片编辑
         * scslCorrelationPicture : 相关照片
         * qualifiedStandard : 截面尺寸[-5,8]
         * inspectionPeopleId : 1
         * inspectionPeople : 检查人
         * zhenggaiId : 1
         * zhenggaiPeople : 整改人
         * zhenggaiPicture : 整改照片
         * zhenggaiExplain : 整改说明
         * zhenggaiTime : 2019-06-21 16:18:24
         * roomTowerFloorId : 2
         * projectId : 1
         * projectName : 项目名字
         * dikuaiId : 46
         * dikuaiName : 地块名称
         * sectionId : 1
         * sectionName : 标段名称
         * towerId : 1
         * towerName : 楼栋名称
         * unitId : 1
         * unitName : 单元名称
         * floorId : 1
         * floorName : 楼层名称
         * inspectionId : 1
         * inspectionName : 混凝土工程
         * inspectionSunName : 截面尺寸
         * partsDivision : 分户/不分单元-整层/分单元-整层
         * creatorTime : 2019-06-21 16:20:14
         * zhengGaiMassge : [{"userId":1,"mobile":"18933333333","peopleuser":"admin"}]
         * bianJiPV : ["http://ms.mienre.com/ydzj-admin/images/图片编辑"]
         * zhengGaiPV : ["http://ms.mienre.com/ydzj-admin/images/整改照片"]
         * xiangGuanPV : ["http://ms.mienre.com/ydzj-admin/images/相关照片"]
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
        public String scslPicture;
        public String scslCorrelationPicture;
        public String qualifiedStandard;
        public int inspectionPeopleId;
        public String inspectionPeople;
        public int zhenggaiId;
        public String zhenggaiPeople;
        public String zhenggaiPicture;
        public String zhenggaiExplain;
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
        public String inspFuId;
        public String inspectionId;
        public String inspectionName;
        public String inspectionSunName;
        public String partsDivision;
        public String creatorTime;
        public List<ZhengGaiMassgeBean> zhengGaiMassge;
        public List<String> bianJiPV;
        public List<String> zhengGaiPV;
        public List<String> xiangGuanPV;
        public String zhenggaiState;
        public String inspectionState;
        public static class ZhengGaiMassgeBean {
            /**
             * userId : 1
             * mobile : 18933333333
             * peopleuser : admin
             */

            public int userId;
            public String mobile;
            public String peopleuser;
        }
    }
}
