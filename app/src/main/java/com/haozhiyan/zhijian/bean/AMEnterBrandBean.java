package com.haozhiyan.zhijian.bean;

import java.util.List;

public class AMEnterBrandBean {

    private List<ClysBrandListBean> clysBrandList;

    public List<ClysBrandListBean> getClysBrandList() {
        return clysBrandList;
    }

    public void setClysBrandList(List<ClysBrandListBean> clysBrandList) {
        this.clysBrandList = clysBrandList;
    }

    public static class ClysBrandListBean {
        /**
         * bandName : 安钢
         * clysTypeList : [{"clysSpecificationList":[{"number":"11","specificationName":"8","unit":"吨"},{"number":"21","specificationName":"6","unit":"吨"}],"typeName":"盘螺"},{"clysSpecificationList":[{"number":"31","specificationName":"32","unit":"吨"},{"number":"41","specificationName":"35","unit":"吨"}],"typeName":"螺纹8 35.32"}]
         */

        private String bandName;
        private List<ClysTypeListBean> clysTypeList;

        public String getBandName() {
            return bandName;
        }

        public void setBandName(String bandName) {
            this.bandName = bandName;
        }

        public List<ClysTypeListBean> getClysTypeList() {
            return clysTypeList;
        }

        public void setClysTypeList(List<ClysTypeListBean> clysTypeList) {
            this.clysTypeList = clysTypeList;
        }

        public static class ClysTypeListBean {
            /**
             * clysSpecificationList : [{"number":"11","specificationName":"8","unit":"吨"},{"number":"21","specificationName":"6","unit":"吨"}]
             * typeName : 盘螺
             */

            private String typeName;
            private List<ClysSpecificationListBean> clysSpecificationList;

            public String getTypeName() {
                return typeName;
            }

            public void setTypeName(String typeName) {
                this.typeName = typeName;
            }

            public List<ClysSpecificationListBean> getClysSpecificationList() {
                return clysSpecificationList;
            }

            public void setClysSpecificationList(List<ClysSpecificationListBean> clysSpecificationList) {
                this.clysSpecificationList = clysSpecificationList;
            }

            public static class ClysSpecificationListBean {
                /**
                 * number : 11
                 * specificationName : 8
                 * unit : 吨
                 */

                private String number;
                private String specificationName;
                private String unit;

                public String getNumber() {
                    return number;
                }

                public void setNumber(String number) {
                    this.number = number;
                }

                public String getSpecificationName() {
                    return specificationName;
                }

                public void setSpecificationName(String specificationName) {
                    this.specificationName = specificationName;
                }

                public String getUnit() {
                    return unit;
                }

                public void setUnit(String unit) {
                    this.unit = unit;
                }
            }
        }
    }
}
