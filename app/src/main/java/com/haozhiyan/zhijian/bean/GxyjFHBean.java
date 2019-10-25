package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/30.
 * Describe:
 */
public class GxyjFHBean {

    /**
     * code : 0
     * list : [{"floor":"-1","floorId":"1","publicTag":"23","roomDetailed":[]},{"floor":"1","floorId":"2","publicTag":"23","roomDetailed":[{"floor":"楼层","floorId":2,"publicTag":"23","roomId":4,"roomNum":"102","roomNumNo":"23","roomRule":"23","unit":"单元"}]},{"floor":"2","floorId":"3","publicTag":"23","roomDetailed":[]},{"floor":"3","floorId":"4","publicTag":"23","roomDetailed":[]},{"floor":"4","floorId":"5","publicTag":"23","roomDetailed":[]},{"floor":"5","floorId":"6","publicTag":"23","roomDetailed":[]},{"floor":"6","floorId":"7","publicTag":"23","roomDetailed":[]},{"floor":"7","floorId":"8","publicTag":"23","roomDetailed":[]},{"floor":"8","floorId":"9","publicTag":"23","roomDetailed":[]},{"floor":"9","floorId":"10","publicTag":"23","roomDetailed":[]},{"floor":"10","floorId":"11","publicTag":"23","roomDetailed":[]},{"floor":"11","floorId":"12","publicTag":"23","roomDetailed":[]},{"floor":"12","floorId":"13","publicTag":"23","roomDetailed":[]},{"floor":"13","floorId":"14","publicTag":"23","roomDetailed":[]},{"floor":"14","floorId":"15","publicTag":"23","roomDetailed":[]},{"floor":"15","floorId":"16","publicTag":"23","roomDetailed":[]},{"floor":"16","floorId":"17","publicTag":"23","roomDetailed":[]},{"floor":"17","floorId":"18","publicTag":"23","roomDetailed":[]},{"floor":"18","floorId":"19","publicTag":"23","roomDetailed":[]}]
     * msg : 成功！
     */

    public int code;
    public String msg;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * floor : -1
         * floorId : 1
         * publicTag : 23
         * roomDetailed : []
         */

        public String floor;
        public String floorId;
        public String publicTag;
        public List<RoomDetailedBean> roomDetailed;

        public static class RoomDetailedBean {
            public String floor;
            public int floorId;
            public String publicTag;
            public int roomId;
            public String roomNum;
            public String roomNumNo;
            public String roomRule;
            public String unit;
        }
    }
}
