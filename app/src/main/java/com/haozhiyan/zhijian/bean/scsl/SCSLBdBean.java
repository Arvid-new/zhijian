package com.haozhiyan.zhijian.bean.scsl;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/20.
 * Describe: Ydzj_project
 */
public class SCSLBdBean {

    public int code;
    public String msg;
    public List<ListBean> list;

    public static class ListBean {
        public String scope;
        public int sectionId;
        public String sectionName;
        public List<ScopeChildBean> scopeChild;

        public static class ScopeChildBean {
            /**
             * tower : 1栋
             * towerId : 6
             * uintChild : [{"towerId":6,"unit":"1单元","unitId":12},{"towerId":6,"unit":"2单元","unitId":13}]
             */

            public String tower;
            public int towerId;
            public List<UintChildBean> uintChild;

            public static class UintChildBean {
                /**
                 * towerId : 6
                 * unit : 1单元
                 * unitId : 12
                 */

                public int towerId;
                public String unit;
                public int unitId;
            }
        }
    }
}
