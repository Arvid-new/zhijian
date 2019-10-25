package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/16.
 * Describe: Ydzj_project
 */
public class ProjectListBean {

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {
        public int pkId;
        public String iteamName;
        public int itemId;
        public List<ChildsBean> childs;
        public static class ChildsBean {
            /**
             * pkId : 17
             * iteamName : 6#块地
             * itemId : 2
             * childs : []
             */
            public int pkId;
            public String iteamName;
            public int itemId;
            public List<?> childs;
        }
    }
}
