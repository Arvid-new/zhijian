package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/11.
 * Describe: Ydzj_project
 */
public class NoticeBean {

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {

        public ListBean() {

        }

        public ListBean(int pkId, String newsHead, String news, String createTime, int newsTag, String project) {
            this.pkId = pkId;
            this.newsHead = newsHead;
            this.news = news;
            this.createTime = createTime;
            this.newsTag = newsTag;
            this.project = project;
        }

        /**
         * pkId : 40
         * newsHead : 0508测试
         * news : 浩之言移动质检公告测试！
         * 浩之言移动质检公告修改测试！
         * createTime : 2019-05-09 15:53:42
         * newsTag : 1
         * project : 30
         */

        public int pkId;
        public String newsHead;
        public String news;
        public String createTime;
        public int newsTag;
        public String project;
    }

    public NoticeBean.ListBean getBean() {
        return new NoticeBean.ListBean(0, "暂无公告", "", "", 0, "");
    }

    @Override
    public String toString() {
        return "NoticeBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", list=" + list +
                '}';
    }
}
