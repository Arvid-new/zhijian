package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/30.
 * Describe: Ydzj_project
 */
public class CSListBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * list : [{"roleId":1,"roleName":"施工单位人员","childIns":[{"userId":1,"peopleuser":"admin","mobile":"1111111111111111111","roleName":"施工单位人员"},{"userId":2,"peopleuser":"张三","mobile":"12345678900","roleName":"施工单位人员"},{"userId":3,"peopleuser":"李四","mobile":"15837039536","roleName":"施工单位人员"},{"userId":4,"peopleuser":"王五","mobile":"123","roleName":"施工单位人员"}]},{"roleId":2,"roleName":"监理单位人员","childIns":[{"userId":1,"peopleuser":"admin","mobile":"1111111111111111111","roleName":"监理单位人员"},{"userId":3,"peopleuser":"李四","mobile":"15837039536","roleName":"监理单位人员"}]},{"roleId":3,"roleName":"建设单位人员","childIns":[{"userId":1,"peopleuser":"admin","mobile":"1111111111111111111","roleName":"建设单位人员"},{"userId":2,"peopleuser":"张三","mobile":"12345678900","roleName":"建设单位人员"}]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean  implements Serializable {
        /**
         * roleId : 1
         * roleName : 施工单位人员
         * childIns : [{"userId":1,"peopleuser":"admin","mobile":"1111111111111111111","roleName":"施工单位人员"},{"userId":2,"peopleuser":"张三","mobile":"12345678900","roleName":"施工单位人员"},{"userId":3,"peopleuser":"李四","mobile":"15837039536","roleName":"施工单位人员"},{"userId":4,"peopleuser":"王五","mobile":"123","roleName":"施工单位人员"}]
         */

        public int roleId;
        public String roleName;
        public List<ChildInsBean> childIns;

        public static class ChildInsBean  implements Serializable {
            /**
             * userId : 1
             * peopleuser : admin
             * mobile : 1111111111111111111
             * roleName : 施工单位人员
             */

            public int userId;
            public String peopleuser;
            public String mobile;
            public String roleName;
        }
    }
}
