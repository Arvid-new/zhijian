package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/2.
 * Describe: Ydzj_project
 */
public class DaibanBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * modulesData : [{"iconAppDb":"xcjc_db.png","xcjcdaiban":0,"iconApp":"xcjc.png","xcjcchaosong":23,"name":"现场检查","menuId":128,"xcjcyiban":7,"iciconApp":"http://192.168.110.66:8080/ydzj-admin/iconApps/xcjc_db.png","parentId":127},{"iconAppDb":"scsl_db.png","iconApp":"scsl.png","yiban":8,"name":"实测实量","menuId":129,"chaosong":0,"iciconApp":"http://192.168.110.66:8080/ydzj-admin/iconApps/scsl_db.png","parentId":127,"daiban":3},{"iconAppDb":"gxyj_db.png","iconApp":"gxyj.png","yiban":3,"name":"工序移交","menuId":130,"chaosong":3,"iciconApp":"http://192.168.110.66:8080/ydzj-admin/iconApps/gxyj_db.png","parentId":127,"daiban":1},{"iconAppDb":"clys_db.png","clysyiban":4,"iconApp":"clys.png","name":"材料验收","menuId":132,"clysdaiban":3,"clyschaosong":6,"iciconApp":"http://192.168.110.66:8080/ydzj-admin/iconApps/clys_db.png","parentId":127}]
     */

    public String msg;
    public int code;
    public List<ModulesDataBean> modulesData;

    public static class ModulesDataBean implements Serializable {
        /**
         * iconAppDb : xcjc_db.png
         * xcjcdaiban : 0
         * iconApp : xcjc.png
         * xcjcchaosong : 23
         * name : 现场检查
         * menuId : 128
         * xcjcyiban : 7
         * iciconApp : http://192.168.110.66:8080/ydzj-admin/iconApps/xcjc_db.png
         * parentId : 127
         * yiban : 8
         * chaosong : 0
         * daiban : 3
         * clysyiban : 4
         * clysdaiban : 3
         * clyschaosong : 6
         */

        public String iconAppDb;
        public int xcjcdaiban;
        public String iconApp;
        public int xcjcchaosong;
        public String name;
        public int menuId;
        public int xcjcyiban;
        public String iciconApp;
        public String photoTag;
        public int parentId;
        public int yiban;
        public int chaosong;
        public int daiban;
        public int clysyiban;
        public int clysdaiban;
        public int clyschaosong;
    }
}
