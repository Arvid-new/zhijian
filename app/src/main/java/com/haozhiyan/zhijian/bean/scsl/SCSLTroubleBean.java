package com.haozhiyan.zhijian.bean.scsl;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/20.
 * Describe: Ydzj_project
 */
public class SCSLTroubleBean {

    public int code;
    public String msg;
    public List<ListBean> list;

    public static class ListBean {
        public long inspectionId;
        public String inspectionName;
        public int inspectionParentId;
        public int sectionId;
        public List<ChildBean> child;

        public static class ChildBean {
            /**
             * inspectionId : 2795792767123456
             * inspectionName : 截面尺寸
             * inspectionParentId : 2795792641294336
             * partsDivision : 不分单元-整层
             */

            public long inspectionId;
            public String inspectionName;
            public long inspectionParentId;
            public String partsDivision;
        }
    }
}
