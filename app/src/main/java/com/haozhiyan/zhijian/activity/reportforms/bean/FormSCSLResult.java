package com.haozhiyan.zhijian.activity.reportforms.bean;

import java.util.List;


public class FormSCSLResult {
    /**
     * sectionId : 59
     * sectionName : Ⅰ 标段
     * projectManage : 77
     * scope : 214642,214643,214644,214645,214646,214647
     * tower : [{"towerId":214642,"towerName":"1栋","shigonghegelv":"54.5%","shigongInsp":"1","jianlihegelv":"100.0%","jianliInsp":"1","jianshehegelv":"33.3%","jiansheheInsp":"1"},{"towerId":214643,"towerName":"2栋","shigonghegelv":null,"shigongInsp":"0","jianlihegelv":null,"jianliInsp":"0","jianshehegelv":null,"jiansheheInsp":"0"},{"towerId":214644,"towerName":"3栋","shigonghegelv":null,"shigongInsp":"0","jianlihegelv":null,"jianliInsp":"0","jianshehegelv":null,"jiansheheInsp":"0"},{"towerId":214645,"towerName":"4栋","shigonghegelv":null,"shigongInsp":"0","jianlihegelv":null,"jianliInsp":"0","jianshehegelv":null,"jiansheheInsp":"0"},{"towerId":214646,"towerName":"5栋","shigonghegelv":null,"shigongInsp":"0","jianlihegelv":null,"jianliInsp":"0","jianshehegelv":null,"jiansheheInsp":"0"},{"towerId":214647,"towerName":"6栋","shigonghegelv":null,"shigongInsp":"0","jianlihegelv":null,"jianliInsp":"0","jianshehegelv":null,"jiansheheInsp":"0"}]
     * zj : {"zongji":"总计","shigongZongji":"54.5%","jianliZongji":"100.0%","jiansheZongji":"33.3%"}
     */

    private int sectionId;
    private String sectionName;
    private int projectManage;
    private String scope;
    private ZjBean zj;
    private List<TowerBean> tower;

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getProjectManage() {
        return projectManage;
    }

    public void setProjectManage(int projectManage) {
        this.projectManage = projectManage;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public ZjBean getZj() {
        return zj;
    }

    public void setZj(ZjBean zj) {
        this.zj = zj;
    }

    public List<TowerBean> getTower() {
        return tower;
    }

    public void setTower(List<TowerBean> tower) {
        this.tower = tower;
    }

    public static class ZjBean {
        /**
         * zongji : 总计
         * shigongZongji : 54.5%
         * jianliZongji : 100.0%
         * jiansheZongji : 33.3%
         */

        private String zongji;
        private String shigongZongji;
        private String jianliZongji;
        private String jiansheZongji;

        public String getZongji() {
            return zongji;
        }

        public void setZongji(String zongji) {
            this.zongji = zongji;
        }

        public String getShigongZongji() {
            return shigongZongji;
        }

        public void setShigongZongji(String shigongZongji) {
            this.shigongZongji = shigongZongji;
        }

        public String getJianliZongji() {
            return jianliZongji;
        }

        public void setJianliZongji(String jianliZongji) {
            this.jianliZongji = jianliZongji;
        }

        public String getJiansheZongji() {
            return jiansheZongji;
        }

        public void setJiansheZongji(String jiansheZongji) {
            this.jiansheZongji = jiansheZongji;
        }
    }

    public static class TowerBean {
        /**
         * towerId : 214642
         * towerName : 1栋
         * shigonghegelv : 54.5%
         * shigongInsp : 1
         * jianlihegelv : 100.0%
         * jianliInsp : 1
         * jianshehegelv : 33.3%
         * jiansheheInsp : 1
         */

        private int towerId;
        private String towerName;
        private String shigonghegelv;
        private String shigongInsp;
        private String jianlihegelv;
        private String jianliInsp;
        private String jianshehegelv;
        private String jiansheheInsp;

        public int getTowerId() {
            return towerId;
        }

        public void setTowerId(int towerId) {
            this.towerId = towerId;
        }

        public String getTowerName() {
            return towerName;
        }

        public void setTowerName(String towerName) {
            this.towerName = towerName;
        }

        public String getShigonghegelv() {
            return shigonghegelv;
        }

        public void setShigonghegelv(String shigonghegelv) {
            this.shigonghegelv = shigonghegelv;
        }

        public String getShigongInsp() {
            return shigongInsp;
        }

        public void setShigongInsp(String shigongInsp) {
            this.shigongInsp = shigongInsp;
        }

        public String getJianlihegelv() {
            return jianlihegelv;
        }

        public void setJianlihegelv(String jianlihegelv) {
            this.jianlihegelv = jianlihegelv;
        }

        public String getJianliInsp() {
            return jianliInsp;
        }

        public void setJianliInsp(String jianliInsp) {
            this.jianliInsp = jianliInsp;
        }

        public String getJianshehegelv() {
            return jianshehegelv;
        }

        public void setJianshehegelv(String jianshehegelv) {
            this.jianshehegelv = jianshehegelv;
        }

        public String getJiansheheInsp() {
            return jiansheheInsp;
        }

        public void setJiansheheInsp(String jiansheheInsp) {
            this.jiansheheInsp = jiansheheInsp;
        }
    }
}

