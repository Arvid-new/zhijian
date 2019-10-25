package com.haozhiyan.zhijian.activity.reportforms.bean;

import java.util.List;


public class FormGXYJResult {
    /**
     * sectionId : 59
     * sectionName : Ⅰ 标段
     * projectManage : 77
     * scope : 214642,214643,214644,214645,214646,214647
     * tower : [{"towerId":214642,"towerName":"1栋","unit":[{"unitId":667,"unitName":"1"},{"unitId":668,"unitName":"2"}]},{"towerId":214643,"towerName":"2栋","unit":[{"unitId":669,"unitName":"1"}]},{"towerId":214644,"towerName":"3栋","unit":[{"unitId":670,"unitName":"1"},{"unitId":671,"unitName":"2"}]},{"towerId":214645,"towerName":"4栋","unit":[{"unitId":672,"unitName":"1"},{"unitId":673,"unitName":"2"}]},{"towerId":214646,"towerName":"5栋","unit":[{"unitId":674,"unitName":"1"},{"unitId":675,"unitName":"2"}]},{"towerId":214647,"towerName":"6栋","unit":[{"unitId":676,"unitName":"1"},{"unitId":677,"unitName":"2"},{"unitId":678,"unitName":"3"}]}]
     * zj : null
     */

    private int sectionId;
    private String sectionName;
    private int projectManage;
    private String scope;
    private Object zj;
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

    public Object getZj() {
        return zj;
    }

    public void setZj(Object zj) {
        this.zj = zj;
    }

    public List<TowerBean> getTower() {
        return tower;
    }

    public void setTower(List<TowerBean> tower) {
        this.tower = tower;
    }

    public static class TowerBean {
        /**
         * towerId : 214642
         * towerName : 1栋
         * unit : [{"unitId":667,"unitName":"1"},{"unitId":668,"unitName":"2"}]
         */

        private int towerId;
        private String towerName;
        private List<UnitBean> unit;

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

        public List<UnitBean> getUnit() {
            return unit;
        }

        public void setUnit(List<UnitBean> unit) {
            this.unit = unit;
        }

        public static class UnitBean {
            /**
             * unitId : 667
             * unitName : 1
             */

            private int unitId;
            private String unitName;

            public int getUnitId() {
                return unitId;
            }

            public void setUnitId(int unitId) {
                this.unitId = unitId;
            }

            public String getUnitName() {
                return unitName;
            }

            public void setUnitName(String unitName) {
                this.unitName = unitName;
            }
        }
    }
}

