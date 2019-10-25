package com.haozhiyan.zhijian.bean.scsl;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/18.
 * Describe: Ydzj_project
 */
public class SCSLInitBean {
    public int code;
    public ListBean list;
    public String msg;

    public static class ListBean {

        public String inspectionName;
        public String inspectionSunName;
        public String partsDivision;
        public String sectionName;
        public String towerName;
        public String floorName;
        public String unitName;
        public String towerUnitName;
        public String sectionId;
        public String inspectionSunId;
        public int towerId;
        public int unitId;
        public List<MessagesBean> messages;

        public static class MessagesBean {
            /**
             * floor : 1层
             * floorId : 1
             * roomNumChild : [{"fangjiaState":[],"floorId":1,"identifying":"1","roomId":1,"roomNum":"101","userRoleType":1},{"fangjiaState":[],"floorId":1,"identifying":"空","roomId":2,"roomNum":"102","userRoleType":0},{"fangjiaState":[],"floorId":1,"identifying":"空","roomId":3,"roomNum":"103","userRoleType":0},{"fangjiaState":[],"floorId":1,"identifying":"空","roomId":4,"roomNum":"104","userRoleType":0}]
             * unitId : 1
             */

            public String floor;
            public int floorId;
            public int unitId;
            public List<RoomNumChildBean> roomNumChild;

            public static class RoomNumChildBean {
                /**
                 * fangjiaState : []
                 * floorId : 1
                 * identifying : 1
                 * roomId : 1
                 * roomNum : 101
                 * userRoleType : 1
                 * shigongState : 1   有编辑状态位1没有为null
                 * jianliState : 2    有编辑状态位2没有为null
                 * jiansheState : 3   有编辑状态位3没有为null
                 */

                public int floorId;
                public int roomId;
                public String roomNum;
                public String roomHouse;
                public String shigongState;
                public String jianliState;
                public String jiansheState;
            }
        }
    }
}
