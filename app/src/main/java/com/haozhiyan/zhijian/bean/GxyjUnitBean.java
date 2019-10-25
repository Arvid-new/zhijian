package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/30.
 * Describe:
 */
public class GxyjUnitBean implements Serializable{

    /**
     * code : 0
     * list : [{"floorChild":[{"floor":"-1","floorId":1,"roomNumChild":[{"chuangjianId":"1","floorId":1,"identifying":"整改","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"整改","roomId":1,"roomNum":"-101","userRoleType":"整改"},{"chuangjianId":"1","floorId":1,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":2,"roomNum":"-102","userRoleType":"1"}],"roomNumChildSCSL":[],"unitId":1},{"floor":"1","floorId":2,"roomNumChild":[{"chuangjianId":"1","floorId":2,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":3,"roomNum":"101","userRoleType":"1"},{"chuangjianId":"1","floorId":2,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":4,"roomNum":"102","userRoleType":"1"}],"roomNumChildSCSL":[],"unitId":1},{"floor":"2","floorId":3,"roomNumChild":[{"chuangjianId":"1","floorId":3,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":5,"roomNum":"201","userRoleType":"1"},{"chuangjianId":"1","floorId":3,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":6,"roomNum":"202","userRoleType":"1"}],"roomNumChildSCSL":[],"unitId":1}],"towerId":1,"unit":"1","unitId":1}]
     * msg : 成功！
     */

    public int code;
    public String msg;
    public List<ListBean> list;

    public static class ListBean implements Serializable{
        /**
         * floorChild : [{"floor":"-1","floorId":1,"roomNumChild":[{"chuangjianId":"1","floorId":1,"identifying":"整改","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"整改","roomId":1,"roomNum":"-101","userRoleType":"整改"},{"chuangjianId":"1","floorId":1,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":2,"roomNum":"-102","userRoleType":"1"}],"roomNumChildSCSL":[],"unitId":1},{"floor":"1","floorId":2,"roomNumChild":[{"chuangjianId":"1","floorId":2,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":3,"roomNum":"101","userRoleType":"1"},{"chuangjianId":"1","floorId":2,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":4,"roomNum":"102","userRoleType":"1"}],"roomNumChildSCSL":[],"unitId":1},{"floor":"2","floorId":3,"roomNumChild":[{"chuangjianId":"1","floorId":3,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":5,"roomNum":"201","userRoleType":"1"},{"chuangjianId":"1","floorId":3,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":6,"roomNum":"202","userRoleType":"1"}],"roomNumChildSCSL":[],"unitId":1}]
         * towerId : 1
         * unit : 1
         * unitId : 1
         */

        public int towerId;
        public String unit;
        public int unitId;
        public List<FloorChildBean> floorChild;

        public static class FloorChildBean implements Serializable{
            /**
             * floor : -1
             * floorId : 1
             * roomNumChild : [{"chuangjianId":"1","floorId":1,"identifying":"整改","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"整改","roomId":1,"roomNum":"-101","userRoleType":"整改"},{"chuangjianId":"1","floorId":1,"identifying":"1","jianliId":"1","jianliState":"1","jiansheId":"1","jiansheState":"1","roomHouse":"1","roomId":2,"roomNum":"-102","userRoleType":"1"}]
             * roomNumChildSCSL : []
             * unitId : 1
             */

            public String floor;
            public int floorId;
            public int unitId;
            public List<RoomNumChildBean> roomNumChild;
            public List<?> roomNumChildSCSL;

            public static class RoomNumChildBean implements Serializable{
                /**
                 * chuangjianId : 1
                 * floorId : 1
                 * identifying : 整改
                 * jianliId : 1
                 * jianliState : 1
                 * jiansheId : 1
                 * jiansheState : 1
                 * roomHouse : 整改
                 * roomId : 1
                 * roomNum : -101
                 * userRoleType : 整改
                 */

                public String chuangjianId;
                public int floorId;
                public String identifying;
                public String jianliId;
                public String jianliState;
                public String jiansheId;
                public String jiansheState;
                public String roomHouse;
                public int roomId;
                public String roomNum;
                public String userRoleType;
            }
        }
    }
}
