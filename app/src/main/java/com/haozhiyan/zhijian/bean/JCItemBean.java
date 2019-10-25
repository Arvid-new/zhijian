package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/14.
 * Describe: Ydzj_project
 */
public class JCItemBean {

    public int code;
    public String msg;
    public List<ListBean> list;

    public static class ListBean {
        public String identifying;
        public long inspectionId;
        public String inspectionName;
        public int parentId;
        public List<ChildInsBean> childIns;
        public List<?> childParticulars;
        public List<?> childQuestion;

        public static class ChildInsBean {
            public String identifying;
            public long inspectionId;
            public String inspectionName;
            public long parentId;
            public List<ListBean.ChildInsBean.Child2InsBean> childIns;
            public List<ChildParticularsBean> childParticulars;
            public List<ListBean.ChildInsBean.ChildQuestion> childQuestion;

            public static class Child2InsBean {

                public long inspectionId;
                public long parentId;
                public String inspectionName;
                public String identifying;
            }

            public static class ChildQuestion {

            }

            public static class ChildParticularsBean {
                /**
                 * inspectionId : 2795728686546944
                 * particularsName : 材料堆放不符合要求
                 */
                public long inspectionId;
                public long xcjcParticularsId;
                public String particularsName;
            }
        }
    }
}
