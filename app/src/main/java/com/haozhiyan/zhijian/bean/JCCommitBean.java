package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/28.
 * Describe: Ydzj_project
 */
public class JCCommitBean {

    public String msg;
    public int code;
    public DataBean data;

    public static class DataBean {
        public MapBatchBean mapBatch;
        public int batchTag;
        public List<ProblemListBean> problemList;

        public static class MapBatchBean {
            /**
             * batchName : 测试批次3
             * batchId : 3
             */

            public String batchName;
            public int batchId;
            public int sectionId;
        }

        public static class ProblemListBean {
            /**
             * unitName : 2
             * submitDate : 05-23 14:35
             * problemImage : null
             * id : 1
             * state : 待整改
             * towerName : 1#楼
             * inspectionName : 安全（土建）-平面布置
             * batchId : 3
             * roomName : 102
             * particularsName : 材料堆放不符合要求
             */

            public String unitName;
            public String submitDate;
            public String problemImage;
            public String serious;
            public int id;
            public String state;
            public String towerName;
            public String floorName;
            public String inspectionName;
            public String batchId;
            public String sectionId;
            public int roomName;
            public String particularsName;
            public String particularsSupplement;
            public String rectify;
            public String submit;
            public String submitTorectifyTag;//对比整改人1是相同 0不相同
            public String submitToreviewTag;//对比复验人1是相同 0不相同
        }
    }
}
