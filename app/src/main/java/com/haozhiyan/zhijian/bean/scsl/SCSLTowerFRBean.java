package com.haozhiyan.zhijian.bean.scsl;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/21.
 * Describe: 实测实量楼栋信息
 */
public class SCSLTowerFRBean {

    public int code;
    public String msg;
    public List<ListBean> list;

    public static class ListBean {
        public String floor;
        public int floorId;
        public int unitId;
        public List<RoomNumChildBean> roomNumChild;

        public static class RoomNumChildBean {
            /**
             * floorId : 171
             * roomId : 640
             * roomNum : 101
             */

            public int floorId;
            public int roomId;
            public int userRoleType;
            public String roomNum;
            public String identifying;
            public List<?> fangjiaState;
        }
    }
}
