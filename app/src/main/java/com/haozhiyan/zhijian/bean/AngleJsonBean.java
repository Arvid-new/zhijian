package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/30.
 * Describe: Ydzj_project
 */
public class AngleJsonBean {

    /**
     * code : 1000
     * data : [{"dateTitle":"施工单位人员","logDOList":[{"deviceName":"张三【施工单位】","fullName":"张三","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"王五【施工单位】","fullName":"王五","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"李留【施工单位】","fullName":"李留","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"王五【施工单位】","fullName":"王五","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"李留【施工单位】","fullName":"李留","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"王五【施工单位】","fullName":"王五","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"}]},{"dateTitle":"监理单位人员","logDOList":[{"deviceName":"张虎【监理单位】","fullName":"张虎","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"张虎【监理单位】","fullName":"张虎","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"张虎【监理单位】","fullName":"张虎","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg","openState":0,"openType":1,"updateTime":"10:22"}]},{"dateTitle":"建设单位人员","logDOList":[{"deviceName":"刘全【建设单位】","fullName":"刘全","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"刘全【建设单位】","fullName":"刘全","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"刘全【建设单位】","fullName":"刘全","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg","openState":0,"openType":1,"updateTime":"10:22"}]}]
     * message : 成功
     */

    public int code;
    public String message;
    public List<DataBean> data;

    public static class DataBean {
        /**
         * dateTitle : 施工单位人员
         * logDOList : [{"deviceName":"张三【施工单位】","fullName":"张三","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"王五【施工单位】","fullName":"王五","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"李留【施工单位】","fullName":"李留","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"王五【施工单位】","fullName":"王五","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"李留【施工单位】","fullName":"李留","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"},{"deviceName":"王五【施工单位】","fullName":"王五","ghsUserId":0,"headUrl":"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg","openState":0,"openType":1,"updateTime":"10:22"}]
         */

        public String dateTitle;
        public List<LogDOListBean> logDOList;

        public static class LogDOListBean {
            /**
             * deviceName : 张三【施工单位】
             * fullName : 张三
             * ghsUserId : 0
             * headUrl : https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg
             * openState : 0
             * openType : 1
             * updateTime : 10:22
             */

            public String deviceName;
            public String fullName;
            public int ghsUserId;
            public String headUrl;
            public int openState;
            public int openType;
            public String updateTime;
        }
    }
}
