package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/7.
 * Describe: Ydzj_project
 */
public class PlaceBean {

    /**
     * code : 1000
     * data : [{"title":"楼栋","louList":[{"name":"1栋"},{"name":"2栋"},{"name":"3栋"},{"name":"4栋"}]},{"title":"单元","louList":[{"name":"1单元"},{"name":"2单元"},{"name":"3单元"},{"name":"4单元"},{"name":"5单元"},{"name":"6单元"}]},{"title":"楼层","louList":[{"name":"1层"},{"name":"2层"},{"name":"3层"},{"name":"4层"},{"name":"5层"},{"name":"6层"},{"name":"7层"},{"name":"8层"},{"name":"9层"},{"name":"10层"},{"name":"11层"},{"name":"12层"},{"name":"13层"},{"name":"14层"},{"name":"15层"},{"name":"16层"}]},{"title":"住户编号","louList":[{"name":"2205"},{"name":"2206"},{"name":"2207"},{"name":"2208"}]}]
     * message : 成功
     */

    public int code;
    public String message;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * title : 楼栋
         * louList : [{"name":"1栋"},{"name":"2栋"},{"name":"3栋"},{"name":"4栋"}]
         */

        public String title;
        public List<LouListBean> louList;

        public static class LouListBean {
            /**
             * name : 1栋
             */

            public String name;
        }
    }
}
