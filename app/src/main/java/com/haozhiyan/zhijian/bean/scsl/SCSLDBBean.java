package com.haozhiyan.zhijian.bean.scsl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/18.
 * Describe: Ydzj_project
 */
public class SCSLDBBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * list : {"listResult":[{"scslId":4,"detailsName":"主体工程-梁板综合预埋","scslState":"待整改","towerName":"楼栋名称","unitName":"单元名称","floorName":"楼层名称","handOverPart":"201","creatorTime":"2019-06-21 16:20:14"}],"daibanCount":1,"yibanCount":0,"chaosongCount":0}
     */

    public String msg;
    public int code;
    public ListBean list;

    public static class ListBean implements Serializable {
        /**
         * listResult : [{"scslId":4,"detailsName":"主体工程-梁板综合预埋","scslState":"待整改","towerName":"楼栋名称","unitName":"单元名称","floorName":"楼层名称","handOverPart":"201","creatorTime":"2019-06-21 16:20:14"}]
         * daibanCount : 1
         * yibanCount : 0
         * chaosongCount : 0
         */

        public int daibanCount;
        public int yibanCount;
        public int chaosongCount;
        public List<ListResultBean> listResult;

        public static class ListResultBean implements Serializable {
            /**
             * scslId : 4
             * detailsName : 主体工程-梁板综合预埋
             * scslState : 待整改
             * towerName : 楼栋名称
             * unitName : 单元名称
             * floorName : 楼层名称
             * handOverPart : 201
             * creatorTime : 2019-06-21 16:20:14
             */

            public int scslId;
            public String detailsName;
            public String scslState;
            public String towerName;
            public String unitName;
            public String floorName;
            public String handOverPart;
            public String creatorTime;
            public String roomId;
            public String percentOfPass;
            public String inspectionId;
            public String inspFuId;

            @Override
            public String toString() {
                return "ListResultBean{" +
                        "scslId=" + scslId +
                        ", detailsName='" + detailsName + '\'' +
                        ", scslState='" + scslState + '\'' +
                        ", towerName='" + towerName + '\'' +
                        ", unitName='" + unitName + '\'' +
                        ", floorName='" + floorName + '\'' +
                        ", handOverPart='" + handOverPart + '\'' +
                        ", creatorTime='" + creatorTime + '\'' +
                        '}';
            }
        }
    }
}
