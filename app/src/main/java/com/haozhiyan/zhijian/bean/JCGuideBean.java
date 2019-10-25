package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/14.
 * Describe: Ydzj_project
 */
public class JCGuideBean {

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {

        public long inspectionId;
        public long parentId;
        public String inspectionName;
        public String identifying;
        public ChildQuestionBean childQuestion;
        public List<ChildInsBean> childIns;
        public List<?> childParticulars;

        public static class ChildQuestionBean {
            /**
             * inspectionId : null
             * checkGuide : null
             */

            public long inspectionId;
            public String checkGuide;
        }

        public static class ChildInsBean {
            public long inspectionId;
            public long parentId;
            public String inspectionName;
            public String identifying;
            public ChildQuestionBeanX childQuestion;
            public List<?> childIns;
            public List<?> childParticulars;

            public static class ChildQuestionBeanX {
                /**
                 * inspectionId : 2795728686546944
                 * checkGuide : 材料未集中堆放、未码放整齐（型材（如钢管）成垛，散材（如砂）成方）；
                 * 材料堆放区无材料标示牌；
                 * 建筑垃圾砌筑垃圾池，集中堆放；
                 * 易燃易爆物品未分类存放；
                 * 楼层工完场清（施工电梯到达楼层垃圾需清理干净，未到达楼层垃圾归堆）；
                 */

                public long inspectionId;
                public String checkGuide;
            }
        }
    }
}
