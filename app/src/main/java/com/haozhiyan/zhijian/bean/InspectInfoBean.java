package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

public class InspectInfoBean implements Serializable {

    /**
     * msg : success
     * code : 0
     * clysInspectList : [{"id":1,"taskId":2,"inspectImage":"2dff2b24eb9b456890a5b98eab75d93c.jpeg","inspectDate":"2019-06-10","inspector":"","times":"1","clysSpecificationList":null,"clysSpecificationListStr":null,"inspectImageList":["http://192.168.110.195:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"],"inspectorList":[],"clysBrandList":[{"id":5,"taskId":2,"bandName":"安钢","clysTypeList":[{"id":8,"taskId":2,"brandId":5,"typeName":"螺纹8 35.32","clysSpecificationList":[{"id":13,"taskId":2,"typeId":8,"specificationName":"32","number":"31","unit":"吨","inspectNumber":"3","inspectNumberTwo":""}]}]}]}]
     */

    private String msg;
    private int code;
    private List<ClysInspectListBean> clysInspectList;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ClysInspectListBean> getClysInspectList() {
        return clysInspectList;
    }

    public void setClysInspectList(List<ClysInspectListBean> clysInspectList) {
        this.clysInspectList = clysInspectList;
    }

    public static class ClysInspectListBean implements Serializable {
        /**
         * id : 1
         * taskId : 2
         * inspectImage : 2dff2b24eb9b456890a5b98eab75d93c.jpeg
         * inspectDate : 2019-06-10
         * inspector :
         * times : 1
         * clysSpecificationList : null
         * clysSpecificationListStr : null
         * inspectImageList : ["http://192.168.110.195:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
         * inspectorList : []
         * clysBrandList : [{"id":5,"taskId":2,"bandName":"安钢","clysTypeList":[{"id":8,"taskId":2,"brandId":5,"typeName":"螺纹8 35.32","clysSpecificationList":[{"id":13,"taskId":2,"typeId":8,"specificationName":"32","number":"31","unit":"吨","inspectNumber":"3","inspectNumberTwo":""}]}]}]
         */

        private int id;
        private int taskId;
        private String inspectImage;
        private String inspectDate;
        private String inspectTime;
        private String inspector;
        private String times;
        private Object clysSpecificationList;
        private Object clysSpecificationListStr;
        private List<String> inspectImageList;
        private List<InspectorListBean> inspectorList;
        private List<ClysBrandListBean> clysBrandList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getTaskId() {
            return taskId;
        }

        public String getInspectTime() {
            return inspectTime;
        }

        public void setInspectTime(String inspectTime) {
            this.inspectTime = inspectTime;
        }

        public void setTaskId(int taskId) {
            this.taskId = taskId;
        }

        public String getInspectImage() {
            return inspectImage;
        }

        public void setInspectImage(String inspectImage) {
            this.inspectImage = inspectImage;
        }

        public String getInspectDate() {
            return inspectDate;
        }

        public void setInspectDate(String inspectDate) {
            this.inspectDate = inspectDate;
        }

        public String getInspector() {
            return inspector;
        }

        public void setInspector(String inspector) {
            this.inspector = inspector;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public Object getClysSpecificationList() {
            return clysSpecificationList;
        }

        public void setClysSpecificationList(Object clysSpecificationList) {
            this.clysSpecificationList = clysSpecificationList;
        }

        public Object getClysSpecificationListStr() {
            return clysSpecificationListStr;
        }

        public void setClysSpecificationListStr(Object clysSpecificationListStr) {
            this.clysSpecificationListStr = clysSpecificationListStr;
        }

        public List<String> getInspectImageList() {
            return inspectImageList;
        }

        public void setInspectImageList(List<String> inspectImageList) {
            this.inspectImageList = inspectImageList;
        }

        public List<InspectorListBean> getInspectorList() {
            return inspectorList;
        }

        public void setInspectorList(List<InspectorListBean> inspectorList) {
            this.inspectorList = inspectorList;
        }

        public List<ClysBrandListBean> getClysBrandList() {
            return clysBrandList;
        }

        public void setClysBrandList(List<ClysBrandListBean> clysBrandList) {
            this.clysBrandList = clysBrandList;
        }
        public static class InspectorListBean implements Serializable {
            /**
             * peopleuser : admin
             * tel : 1111111111111111111
             * userId : 1
             */

            public String peopleuser;
            public String tel;
            public int userId;

            public String getPeopleuser() {
                return peopleuser;
            }

            public void setPeopleuser(String peopleuser) {
                this.peopleuser = peopleuser;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
        public static class ClysBrandListBean implements Serializable {
            /**
             * id : 5
             * taskId : 2
             * bandName : 安钢
             * clysTypeList : [{"id":8,"taskId":2,"brandId":5,"typeName":"螺纹8 35.32","clysSpecificationList":[{"id":13,"taskId":2,"typeId":8,"specificationName":"32","number":"31","unit":"吨","inspectNumber":"3","inspectNumberTwo":""}]}]
             */

            private int id;
            private int taskId;
            private String bandName;
            private List<ClysTypeListBean> clysTypeList;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getTaskId() {
                return taskId;
            }

            public void setTaskId(int taskId) {
                this.taskId = taskId;
            }

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

            public static class ClysTypeListBean implements Serializable {
                /**
                 * id : 8
                 * taskId : 2
                 * brandId : 5
                 * typeName : 螺纹8 35.32
                 * clysSpecificationList : [{"id":13,"taskId":2,"typeId":8,"specificationName":"32","number":"31","unit":"吨","inspectNumber":"3","inspectNumberTwo":""}]
                 */

                private int id;
                private int taskId;
                private int brandId;
                private String typeName;
                private List<ClysSpecificationListBean> clysSpecificationList;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getTaskId() {
                    return taskId;
                }

                public void setTaskId(int taskId) {
                    this.taskId = taskId;
                }

                public int getBrandId() {
                    return brandId;
                }

                public void setBrandId(int brandId) {
                    this.brandId = brandId;
                }

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

                public static class ClysSpecificationListBean implements Serializable{
                    /**
                     * id : 13
                     * taskId : 2
                     * typeId : 8
                     * specificationName : 32
                     * number : 31
                     * unit : 吨
                     * inspectNumber : 3
                     * inspectNumberTwo :
                     */

                    private int id;
                    private int taskId;
                    private int typeId;
                    private String specificationName;
                    private String number;
                    private String unit;
                    private String inspectNumber;
                    private String inspectNumberTwo;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public int getTaskId() {
                        return taskId;
                    }

                    public void setTaskId(int taskId) {
                        this.taskId = taskId;
                    }

                    public int getTypeId() {
                        return typeId;
                    }

                    public void setTypeId(int typeId) {
                        this.typeId = typeId;
                    }

                    public String getSpecificationName() {
                        return specificationName;
                    }

                    public void setSpecificationName(String specificationName) {
                        this.specificationName = specificationName;
                    }

                    public String getNumber() {
                        return number;
                    }

                    public void setNumber(String number) {
                        this.number = number;
                    }

                    public String getUnit() {
                        return unit;
                    }

                    public void setUnit(String unit) {
                        this.unit = unit;
                    }

                    public String getInspectNumber() {
                        return inspectNumber;
                    }

                    public void setInspectNumber(String inspectNumber) {
                        this.inspectNumber = inspectNumber;
                    }

                    public String getInspectNumberTwo() {
                        return inspectNumberTwo;
                    }

                    public void setInspectNumberTwo(String inspectNumberTwo) {
                        this.inspectNumberTwo = inspectNumberTwo;
                    }
                }
            }
        }
    }
}
