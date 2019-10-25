package com.haozhiyan.zhijian.bean.scsl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/20.
 * Describe: Ydzj_project
 */
public class SCSLldcBean implements Serializable {

    public String msg;
    public int code;
    public List<DataBean> data;

    public static class DataBean implements Serializable {
        public int sectionId;
        public String sectionName;
        public String scope;
        public List<ScopeChildBean> scopeChild;

        public static class ScopeChildBean implements Serializable {

            public Object itemId;
            public int towerId;
            public Object iteamName;
            public String tower;
            public List<UnitChildBean> unitChild;

            public static class UnitChildBean implements Serializable {

                public int unitId;
                public String unit;
                public int towerId;
                public List<FloorChildBean> floorChild;

                public static class FloorChildBean implements Serializable {
                    /**
                     * floor : -1
                     * floorId : 723
                     * unitId : 36
                     * roomNumChild : [{"roomId":2150,"roomNum":"-101","floorId":723,"identifying":null,"userRoleType":null,"fangjiaState":[]},{"roomId":2151,"roomNum":"-102","floorId":723,"identifying":null,"userRoleType":null,"fangjiaState":[]}]
                     */

                    public String floor;
                    public int floorId;
                    public int unitId;
                    public List<RoomNumChildBean> roomNumChild;

                    public static class RoomNumChildBean implements Serializable {
                        /**
                         * roomId : 2150
                         * roomNum : -101
                         * floorId : 723
                         * identifying : null
                         * userRoleType : null
                         * fangjiaState : []
                         */

                        public int roomId;
                        public String roomNum;
                        public int floorId;
                        public String identifying;
                        public String userRoleType;
                        public List<?> fangjiaState;
                    }
                }
            }
        }
    }
}
