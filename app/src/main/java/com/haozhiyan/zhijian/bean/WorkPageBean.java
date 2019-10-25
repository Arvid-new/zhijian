package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/2.
 * Describe: Ydzj_project
 */
public class WorkPageBean implements Serializable {

    /**
     * msg : success
     * newsData : [{"news":"测试高蛋白采购部","newsId":16,"createTime":"2019-04-01 10:55:52","newsHead":"测试1","newsTag":0},{"news":"wwww","newsId":43,"createTime":"2019-06-19 16:42:42","newsHead":"www","newsTag":1},{"news":"ces545","newsId":44,"createTime":"2019-06-21 16:28:04","newsHead":"ces","newsTag":1}]
     * code : 0
     * proData : {"name":"91#地块-名门紫园","dikuaiName":"91#地块","dikuaiId":46,"iteamName":"名门紫园","iteamId":2}
     * modulesData : [{"name":"现场检查","icon":"fa fa-building-o","menuId":128,"parentId":127},{"name":"实测实量","icon":"fa fa-building-o","menuId":129,"parentId":127},{"name":"工序移交","icon":"fa fa-building-o","menuId":130,"parentId":127},{"name":"工序验收v2","icon":"fa fa-building-o","menuId":131,"parentId":127},{"name":"材料验收","icon":"fa fa-building-o","menuId":132,"parentId":127},{"name":"样板管理","icon":"fa fa-building-o","menuId":134,"parentId":127},{"name":"常用文档","icon":"fa fa-building-o","menuId":135,"parentId":127},{"name":"形象进度","icon":"fa fa-building-o","menuId":136,"parentId":127},{"name":"进度管理","icon":"fa fa-building-o","menuId":137,"parentId":127},{"name":"专项巡检","icon":"fa fa-building-o","menuId":138,"parentId":127},{"name":"实测巡检","icon":"fa fa-building-o","menuId":139,"parentId":127},{"name":"报表","icon":"fa fa-building-o","menuId":140,"parentId":127},{"name":"工程亮点","icon":"fa fa-building-o","menuId":141,"parentId":127}]
     */

    public String msg;
    public String partWhetherIdentifying;
    public int code;
    public ProDataBean proData;
    public List<NewsDataBean> newsData;
    public List<ModulesDataBean> modulesData;

    public static class ProDataBean implements Serializable {
        /**
         * name : 91#地块-名门紫园
         * dikuaiName : 91#地块
         * dikuaiId : 46
         * iteamName : 名门紫园
         * iteamId : 2
         */

        public String name;
        public String dikuaiName;
        public int dikuaiId;
        public String iteamName;
        public int iteamId;
    }

    public static class NewsDataBean implements Serializable {
        /**
         * news : 测试高蛋白采购部
         * newsId : 16
         * createTime : 2019-04-01 10:55:52
         * newsHead : 测试1
         * newsTag : 0
         */

        public String news;
        public int newsId;
        public String createTime;
        public String newsHead;
        public int newsTag;

        public NewsDataBean(String news, int newsId, String createTime, String newsHead, int newsTag) {
            this.news = news;
            this.newsId = newsId;
            this.createTime = createTime;
            this.newsHead = newsHead;
            this.newsTag = newsTag;
        }
    }

    public static class ModulesDataBean implements Serializable {
        /**
         * name : 现场检查
         * icon : fa fa-building-o
         * menuId : 128
         * parentId : 127
         */

        public String name;
        public String iconApp;
        public String photoTag;
        public int menuId;
        public int parentId;

        public ModulesDataBean(String name, String icon, int menuId, int parentId) {
            this.name = name;
            this.iconApp = icon;
            this.menuId = menuId;
            this.parentId = parentId;
        }
    }

    public WorkPageBean.NewsDataBean getNewsBean() {
        return new WorkPageBean.NewsDataBean("暂无公告", 0, "", "", 0);
    }

    public List<WorkPageBean.ModulesDataBean> getWorkBean() {
        List<WorkPageBean.ModulesDataBean> emptyList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            emptyList.add(new WorkPageBean.ModulesDataBean("无数据", "", i, i));
        }
        return emptyList;
    }
}
