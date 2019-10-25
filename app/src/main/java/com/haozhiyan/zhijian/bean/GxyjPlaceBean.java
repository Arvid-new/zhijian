package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/3.
 * Describe: Ydzj_project
 */
public class GxyjPlaceBean implements Serializable {


    /**
     * msg : success
     * code : 0
     * list : [{"floorId":"1","floor":"-1","roomDetailed":[{"unit":null,"floor":null,"floorId":1,"roomNum":"-101","roomNumNo":null,"roomRule":null,"roomId":1}]},{"floorId":"2","floor":"1","roomDetailed":[]},{"floorId":"3","floor":"2","roomDetailed":[]},{"floorId":"4","floor":"3","roomDetailed":[]},{"floorId":"5","floor":"4","roomDetailed":[]},{"floorId":"6","floor":"5","roomDetailed":[]},{"floorId":"7","floor":"6","roomDetailed":[]},{"floorId":"8","floor":"7","roomDetailed":[]},{"floorId":"9","floor":"8","roomDetailed":[]},{"floorId":"10","floor":"9","roomDetailed":[]},{"floorId":"11","floor":"10","roomDetailed":[]},{"floorId":"12","floor":"11","roomDetailed":[]},{"floorId":"13","floor":"12","roomDetailed":[]},{"floorId":"14","floor":"13","roomDetailed":[]},{"floorId":"15","floor":"14","roomDetailed":[]},{"floorId":"16","floor":"15","roomDetailed":[]},{"floorId":"17","floor":"16","roomDetailed":[]},{"floorId":"18","floor":"17","roomDetailed":[]},{"floorId":"19","floor":"18","roomDetailed":[]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean  implements Serializable {
        /**
         * floorId : 1
         * floor : -1
         * roomDetailed : [{"unit":null,"floor":null,"floorId":1,"roomNum":"-101","roomNumNo":null,"roomRule":null,"roomId":1}]
         */

        public String floorId;
        public String floor;
        public List<RoomDetailedBean> roomDetailed;

        public static class RoomDetailedBean  implements Serializable {
            /**
             * unit : null
             * floor : null
             * floorId : 1
             * roomNum : -101
             * roomNumNo : null
             * roomRule : null
             * roomId : 1
             */

            public String unit;
            public String floor;
            public int floorId;
            public String roomNum;
            public String roomNumNo;
            public String roomRule;
            public int roomId;
        }
    }
}
