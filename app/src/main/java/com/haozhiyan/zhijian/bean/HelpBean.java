package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/24.
 * Describe:
 */
public class HelpBean  implements Serializable {

    /**
     * msg : success
     * code : 0
     * list : {"CJWT":[{"id":3,"parentId":1,"helpName":"移动质检近期更新了那些功能？","connect":"http://note.youdao.com/noteshare?id=e2c6f544656ea4b0c64b851141c6c694"},{"id":4,"parentId":1,"helpName":"APP各角色操作权限简介","connect":"http://note.youdao.com/noteshare?id=3aaee1e494d1383ec52d7b5dae666735"},{"id":5,"parentId":1,"helpName":"什么是检查批次？","connect":"http://note.youdao.com/noteshare?id=40a65bc36f1e46319f705eefd0dca41b"},{"id":6,"parentId":1,"helpName":"\u201c待办\u201d中各页签包含哪些问题?","connect":"http://note.youdao.com/noteshare?id=eafb86f2f7dfacfb0e8161aa44acf8f9"},{"id":7,"parentId":1,"helpName":"整改期限和超时是怎样计算的？","connect":"http://note.youdao.com/noteshare?id=e05abfcba9f458f8a3231d8535df7703"},{"id":8,"parentId":1,"helpName":"关闭批次有什么用？","connect":"http://note.youdao.com/noteshare?id=be870b7404fdcf125ce6eac00b706f40"},{"id":9,"parentId":1,"helpName":"员工离职，未处理的问题怎么办？","connect":"http://note.youdao.com/noteshare?id=99477f8d45b684bfa44a02afdcf65d48"},{"id":10,"parentId":2,"helpName":"初始化需要那些步骤？","connect":"http://note.youdao.com/noteshare?id=fc8edfdf3e1c56b9a53678706fcd61e0"},{"id":11,"parentId":2,"helpName":"PC各角色操作权限简介","connect":"http://note.youdao.com/noteshare?id=134533c4fef91cf5d0923388a5609c86"},{"id":12,"parentId":2,"helpName":"实测试量各合格率计算方法简介","connect":"http://note.youdao.com/noteshare?id=e65c13c237e9c68699edf6b2b85edd63"},{"id":13,"parentId":2,"helpName":"如何制作自定义报表？","connect":"http://note.youdao.com/noteshare?id=f60b7875124fc0d2dda67def10de11d0"},{"id":14,"parentId":2,"helpName":"\u201c专项巡检\u201d模板说明","connect":"http://note.youdao.com/noteshare?id=a9348e80b5aec986a31dbb52ae9c7be7"}],"QBWT":[{"id":1,"parentId":0,"helpName":"APP相关问题","connect":null,"sun":[{"id":3,"parentId":1,"helpName":"移动质检近期更新了那些功能？","connect":"http://note.youdao.com/noteshare?id=e2c6f544656ea4b0c64b851141c6c694"},{"id":4,"parentId":1,"helpName":"APP各角色操作权限简介","connect":"http://note.youdao.com/noteshare?id=3aaee1e494d1383ec52d7b5dae666735"},{"id":5,"parentId":1,"helpName":"什么是检查批次？","connect":"http://note.youdao.com/noteshare?id=40a65bc36f1e46319f705eefd0dca41b"},{"id":6,"parentId":1,"helpName":"\u201c待办\u201d中各页签包含哪些问题?","connect":"http://note.youdao.com/noteshare?id=eafb86f2f7dfacfb0e8161aa44acf8f9"},{"id":7,"parentId":1,"helpName":"整改期限和超时是怎样计算的？","connect":"http://note.youdao.com/noteshare?id=e05abfcba9f458f8a3231d8535df7703"},{"id":8,"parentId":1,"helpName":"关闭批次有什么用？","connect":"http://note.youdao.com/noteshare?id=be870b7404fdcf125ce6eac00b706f40"},{"id":9,"parentId":1,"helpName":"员工离职，未处理的问题怎么办？","connect":"http://note.youdao.com/noteshare?id=99477f8d45b684bfa44a02afdcf65d48"}]},{"id":2,"parentId":0,"helpName":"PC相关问题","connect":null,"sun":[{"id":10,"parentId":2,"helpName":"初始化需要那些步骤？","connect":"http://note.youdao.com/noteshare?id=fc8edfdf3e1c56b9a53678706fcd61e0"},{"id":11,"parentId":2,"helpName":"PC各角色操作权限简介","connect":"http://note.youdao.com/noteshare?id=134533c4fef91cf5d0923388a5609c86"},{"id":12,"parentId":2,"helpName":"实测试量各合格率计算方法简介","connect":"http://note.youdao.com/noteshare?id=e65c13c237e9c68699edf6b2b85edd63"},{"id":13,"parentId":2,"helpName":"如何制作自定义报表？","connect":"http://note.youdao.com/noteshare?id=f60b7875124fc0d2dda67def10de11d0"},{"id":14,"parentId":2,"helpName":"\u201c专项巡检\u201d模板说明","connect":"http://note.youdao.com/noteshare?id=a9348e80b5aec986a31dbb52ae9c7be7"}]}]}
     */

    public String msg;
    public int code;
    public ListBean list;

    public static class ListBean  implements Serializable{
        public List<CJWTBean> CJWT;//常见问题
        public List<QBWTBean> QBWT;//全部问题

        public static class CJWTBean  implements Serializable{
            /**
             * id : 3
             * parentId : 1
             * helpName : 移动质检近期更新了那些功能？
             * connect : http://note.youdao.com/noteshare?id=e2c6f544656ea4b0c64b851141c6c694
             */

            public int id;
            public int parentId;
            public String helpName;
            public String connect;
        }

        public static class QBWTBean  implements Serializable{
            /**
             * id : 1
             * parentId : 0
             * helpName : APP相关问题
             * connect : null
             * sun : [{"id":3,"parentId":1,"helpName":"移动质检近期更新了那些功能？","connect":"http://note.youdao.com/noteshare?id=e2c6f544656ea4b0c64b851141c6c694"},{"id":4,"parentId":1,"helpName":"APP各角色操作权限简介","connect":"http://note.youdao.com/noteshare?id=3aaee1e494d1383ec52d7b5dae666735"},{"id":5,"parentId":1,"helpName":"什么是检查批次？","connect":"http://note.youdao.com/noteshare?id=40a65bc36f1e46319f705eefd0dca41b"},{"id":6,"parentId":1,"helpName":"\u201c待办\u201d中各页签包含哪些问题?","connect":"http://note.youdao.com/noteshare?id=eafb86f2f7dfacfb0e8161aa44acf8f9"},{"id":7,"parentId":1,"helpName":"整改期限和超时是怎样计算的？","connect":"http://note.youdao.com/noteshare?id=e05abfcba9f458f8a3231d8535df7703"},{"id":8,"parentId":1,"helpName":"关闭批次有什么用？","connect":"http://note.youdao.com/noteshare?id=be870b7404fdcf125ce6eac00b706f40"},{"id":9,"parentId":1,"helpName":"员工离职，未处理的问题怎么办？","connect":"http://note.youdao.com/noteshare?id=99477f8d45b684bfa44a02afdcf65d48"}]
             */

            public int id;
            public int parentId;
            public String helpName;
            public String connect;
            public List<SunBean> sun;

            public static class SunBean  implements Serializable{
                /**
                 * id : 3
                 * parentId : 1
                 * helpName : 移动质检近期更新了那些功能？
                 * connect : http://note.youdao.com/noteshare?id=e2c6f544656ea4b0c64b851141c6c694
                 */

                public int id;
                public int parentId;
                public String helpName;
                public String connect;
            }
        }
    }
}
