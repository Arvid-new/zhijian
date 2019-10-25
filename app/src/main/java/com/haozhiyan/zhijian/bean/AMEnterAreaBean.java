package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

public class AMEnterAreaBean implements Serializable {

    public String msg;
    public int code;
    public ApproachBean approach;

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

    public ApproachBean getApproach() {
        return approach;
    }

    public void setApproach(ApproachBean approach) {
        this.approach = approach;
    }

    public static class ApproachBean {
        /**
         * id : 2
         * nameInspectionId : 2796190148067328
         * nameInspection : 钢筋
         * approachDate : 2019-06-10
         * receiveVehicleImage : 2dff2b24eb9b456890a5b98eab75d93c.jpeg
         * receiveMaterialImage : 4c368817f9ea424c93176f0b5b55d9a7.jpeg,50bd3ca74e9d4bf3b6782dd2a06ba405.jpg
         * receiveCertificateImage : 4512fb4382294252ab48e6777063b3d2.jpg
         * receiveTextureImage : 2dff2b24eb9b456890a5b98eab75d93c.jpeg
         * receiveSupplement : 接收人补充说明111
         * receiveUnit : 105
         * receiveUnitName : 翠园A1#一标段
         * receive : 63
         * receiveName :
         * receiveDate : 2019-06-10 09:36:00.0
         * checkResult : 1
         * supervisor : 64
         * supervisorName :
         * supervisorDate : 2019-06-10 11:30:08.0
         * supervisorResult : 1
         * supervisorVehicleImage : 4512fb4382294252ab48e6777063b3d2.jpg
         * supervisorMaterialImage : 2dff2b24eb9b456890a5b98eab75d93c.jpeg
         * supervisorCertificateImage : 4c368817f9ea424c93176f0b5b55d9a7.jpeg,2dff2b24eb9b456890a5b98eab75d93c.jpeg
         * supervisorTextureImage : 50bd3ca74e9d4bf3b6782dd2a06ba405.jpg
         * supervisorSceneImage : 4512fb4382294252ab48e6777063b3d2.jpg
         * supervisorSupplement : 监理补充说明111
         * isInspect : 1
         * inspector : 75
         * inspectorName :
         * cc : 67,68,69
         * ccName :
         * clysBrandList : [{"id":5,"taskId":2,"bandName":"安钢","clysTypeList":[{"id":7,"taskId":2,"brandId":5,"typeName":"盘螺","clysSpecificationList":[{"id":11,"taskId":2,"typeId":7,"specificationName":"8","number":"11","unit":"吨","inspectNumber":"1","inspectNumberTwo":""},{"id":12,"taskId":2,"typeId":7,"specificationName":"6","number":"21","unit":"吨","inspectNumber":"2","inspectNumberTwo":""}],"clysSpecificationListStr":""},{"id":8,"taskId":2,"brandId":5,"typeName":"螺纹8 35.32","clysSpecificationList":[{"id":13,"taskId":2,"typeId":8,"specificationName":"32","number":"31","unit":"吨","inspectNumber":"3","inspectNumberTwo":""},{"id":14,"taskId":2,"typeId":8,"specificationName":"35","number":"41","unit":"吨","inspectNumber":"4","inspectNumberTwo":""}],"clysSpecificationListStr":""}],"clysTypeListStr":""}]
         * receiveList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * supervisorList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * acceptanceList : [{"id":13,"taskId":2,"acceptance":"65","userId":"65","peopleuser":"","tel":"","isAcceptance":"","isQualified":"1","acceptanceDate":"2019-06-10 11:53:43.0","acceptanceImage":"2dff2b24eb9b456890a5b98eab75d93c.jpeg","acceptanceImageList":["http://192.168.110.8:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"],"acceptanceSupplement":"建设单位验收补充说明111"}]
         * ccList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * inspectorList : [{"peopleuser":"admin","tel":"1111111111111111111","userId":1}]
         * receiveVehicleImageList : ["http://192.168.110.8:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
         * receiveMaterialImageList : ["http://192.168.110.8:8080/ydzj-admin/images/4c368817f9ea424c93176f0b5b55d9a7.jpeg","http://192.168.110.8:8080/ydzj-admin/images/50bd3ca74e9d4bf3b6782dd2a06ba405.jpg"]
         * receiveCertificateImageList : ["http://192.168.110.8:8080/ydzj-admin/images/4512fb4382294252ab48e6777063b3d2.jpg"]
         * receiveTextureImageList : ["http://192.168.110.8:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
         * supervisorVehicleImageList : ["http://192.168.110.8:8080/ydzj-admin/images/4512fb4382294252ab48e6777063b3d2.jpg"]
         * supervisorMaterialImageList : ["http://192.168.110.8:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
         * supervisorCertificateImageList : ["http://192.168.110.8:8080/ydzj-admin/images/4c368817f9ea424c93176f0b5b55d9a7.jpeg","http://192.168.110.8:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
         * supervisorTextureImageList : ["http://192.168.110.8:8080/ydzj-admin/images/50bd3ca74e9d4bf3b6782dd2a06ba405.jpg"]
         * supervisorSceneImageList : ["http://192.168.110.8:8080/ydzj-admin/images/4512fb4382294252ab48e6777063b3d2.jpg"]
         */

        public int id;
        public String nameInspectionId;
        public String nameInspection;
        public String approachDate;
        public String receiveVehicleImage;
        public String receiveMaterialImage;
        public String receiveCertificateImage;
        public String receiveTextureImage;
        public String receiveSupplement;
        public String receiveUnit;
        public String receiveUnitName;
        public String receive;
        public String receiveName;
        public String receiveDate;
        public String checkResult;
        public String supervisor;
        public String supervisorName;
        public String supervisorDate;
        public String supervisorResult;
        public String supervisorVehicleImage;
        public String supervisorMaterialImage;
        public String supervisorCertificateImage;
        public String supervisorTextureImage;
        public String supervisorSceneImage;
        public String supervisorSupplement;
        public String isInspect;
        public String inspector;
        public String inspectorName;
        public String receiveImage;
        public String supervisorImage;
        public String cc;
        public String ccName;
        public List<ClysBrandListBean> clysBrandList;
        public List<ReceiveListBean> receiveList;
        public List<SupervisorListBean> supervisorList;
        public List<AcceptanceListBean> acceptanceList;
        public List<CcListBean> ccList;
        public List<InspectorListBean> inspectorList;
        public List<String> receiveVehicleImageList;
        public List<String> receiveMaterialImageList;
        public List<String> receiveCertificateImageList;
        public List<String> receiveTextureImageList;
        public List<String> supervisorVehicleImageList;
        public List<String> supervisorMaterialImageList;
        public List<String> supervisorCertificateImageList;
        public List<String> supervisorTextureImageList;
        public List<String> supervisorSceneImageList;
        public List<String> typeInspectionIdList;
        public List<String> typeInspectionList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNameInspectionId() {
            return nameInspectionId;
        }

        public void setNameInspectionId(String nameInspectionId) {
            this.nameInspectionId = nameInspectionId;
        }


        public String getReceiveImage() {
            return receiveImage;
        }

        public void setReceiveImage(String receiveImage) {
            this.receiveImage = receiveImage;
        }

        public String getSupervisorImage() {
            return supervisorImage;
        }

        public void setSupervisorImage(String supervisorImage) {
            this.supervisorImage = supervisorImage;
        }

        public String getNameInspection() {
            return nameInspection;
        }

        public void setNameInspection(String nameInspection) {
            this.nameInspection = nameInspection;
        }

        public String getApproachDate() {
            return approachDate;
        }

        public void setApproachDate(String approachDate) {
            this.approachDate = approachDate;
        }

        public String getReceiveVehicleImage() {
            return receiveVehicleImage;
        }

        public void setReceiveVehicleImage(String receiveVehicleImage) {
            this.receiveVehicleImage = receiveVehicleImage;
        }

        public String getReceiveMaterialImage() {
            return receiveMaterialImage;
        }

        public void setReceiveMaterialImage(String receiveMaterialImage) {
            this.receiveMaterialImage = receiveMaterialImage;
        }

        public String getReceiveCertificateImage() {
            return receiveCertificateImage;
        }

        public void setReceiveCertificateImage(String receiveCertificateImage) {
            this.receiveCertificateImage = receiveCertificateImage;
        }

        public String getReceiveTextureImage() {
            return receiveTextureImage;
        }

        public void setReceiveTextureImage(String receiveTextureImage) {
            this.receiveTextureImage = receiveTextureImage;
        }

        public String getReceiveSupplement() {
            return receiveSupplement;
        }

        public void setReceiveSupplement(String receiveSupplement) {
            this.receiveSupplement = receiveSupplement;
        }

        public String getReceiveUnit() {
            return receiveUnit;
        }

        public void setReceiveUnit(String receiveUnit) {
            this.receiveUnit = receiveUnit;
        }

        public String getReceiveUnitName() {
            return receiveUnitName;
        }

        public void setReceiveUnitName(String receiveUnitName) {
            this.receiveUnitName = receiveUnitName;
        }

        public String getReceive() {
            return receive;
        }

        public void setReceive(String receive) {
            this.receive = receive;
        }

        public String getReceiveName() {
            return receiveName;
        }

        public void setReceiveName(String receiveName) {
            this.receiveName = receiveName;
        }

        public String getReceiveDate() {
            return receiveDate;
        }

        public void setReceiveDate(String receiveDate) {
            this.receiveDate = receiveDate;
        }

        public String getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(String checkResult) {
            this.checkResult = checkResult;
        }

        public String getSupervisor() {
            return supervisor;
        }

        public void setSupervisor(String supervisor) {
            this.supervisor = supervisor;
        }

        public String getSupervisorName() {
            return supervisorName;
        }

        public void setSupervisorName(String supervisorName) {
            this.supervisorName = supervisorName;
        }

        public String getSupervisorDate() {
            return supervisorDate;
        }

        public void setSupervisorDate(String supervisorDate) {
            this.supervisorDate = supervisorDate;
        }

        public String getSupervisorResult() {
            return supervisorResult;
        }

        public void setSupervisorResult(String supervisorResult) {
            this.supervisorResult = supervisorResult;
        }

        public String getSupervisorVehicleImage() {
            return supervisorVehicleImage;
        }

        public void setSupervisorVehicleImage(String supervisorVehicleImage) {
            this.supervisorVehicleImage = supervisorVehicleImage;
        }

        public String getSupervisorMaterialImage() {
            return supervisorMaterialImage;
        }

        public void setSupervisorMaterialImage(String supervisorMaterialImage) {
            this.supervisorMaterialImage = supervisorMaterialImage;
        }

        public String getSupervisorCertificateImage() {
            return supervisorCertificateImage;
        }

        public void setSupervisorCertificateImage(String supervisorCertificateImage) {
            this.supervisorCertificateImage = supervisorCertificateImage;
        }

        public String getSupervisorTextureImage() {
            return supervisorTextureImage;
        }

        public void setSupervisorTextureImage(String supervisorTextureImage) {
            this.supervisorTextureImage = supervisorTextureImage;
        }

        public String getSupervisorSceneImage() {
            return supervisorSceneImage;
        }

        public void setSupervisorSceneImage(String supervisorSceneImage) {
            this.supervisorSceneImage = supervisorSceneImage;
        }

        public String getSupervisorSupplement() {
            return supervisorSupplement;
        }

        public void setSupervisorSupplement(String supervisorSupplement) {
            this.supervisorSupplement = supervisorSupplement;
        }

        public String getIsInspect() {
            return isInspect;
        }

        public void setIsInspect(String isInspect) {
            this.isInspect = isInspect;
        }

        public String getInspector() {
            return inspector;
        }

        public void setInspector(String inspector) {
            this.inspector = inspector;
        }

        public String getInspectorName() {
            return inspectorName;
        }

        public void setInspectorName(String inspectorName) {
            this.inspectorName = inspectorName;
        }

        public String getCc() {
            return cc;
        }

        public void setCc(String cc) {
            this.cc = cc;
        }

        public String getCcName() {
            return ccName;
        }

        public void setCcName(String ccName) {
            this.ccName = ccName;
        }

        public List<ClysBrandListBean> getClysBrandList() {
            return clysBrandList;
        }

        public void setClysBrandList(List<ClysBrandListBean> clysBrandList) {
            this.clysBrandList = clysBrandList;
        }

        public List<ReceiveListBean> getReceiveList() {
            return receiveList;
        }

        public void setReceiveList(List<ReceiveListBean> receiveList) {
            this.receiveList = receiveList;
        }

        public List<SupervisorListBean> getSupervisorList() {
            return supervisorList;
        }

        public void setSupervisorList(List<SupervisorListBean> supervisorList) {
            this.supervisorList = supervisorList;
        }

        public List<AcceptanceListBean> getAcceptanceList() {
            return acceptanceList;
        }

        public void setAcceptanceList(List<AcceptanceListBean> acceptanceList) {
            this.acceptanceList = acceptanceList;
        }

        public List<CcListBean> getCcList() {
            return ccList;
        }

        public void setCcList(List<CcListBean> ccList) {
            this.ccList = ccList;
        }

        public List<InspectorListBean> getInspectorList() {
            return inspectorList;
        }

        public void setInspectorList(List<InspectorListBean> inspectorList) {
            this.inspectorList = inspectorList;
        }

        public List<String> getReceiveVehicleImageList() {
            return receiveVehicleImageList;
        }

        public void setReceiveVehicleImageList(List<String> receiveVehicleImageList) {
            this.receiveVehicleImageList = receiveVehicleImageList;
        }

        public List<String> getReceiveMaterialImageList() {
            return receiveMaterialImageList;
        }

        public void setReceiveMaterialImageList(List<String> receiveMaterialImageList) {
            this.receiveMaterialImageList = receiveMaterialImageList;
        }

        public List<String> getReceiveCertificateImageList() {
            return receiveCertificateImageList;
        }

        public void setReceiveCertificateImageList(List<String> receiveCertificateImageList) {
            this.receiveCertificateImageList = receiveCertificateImageList;
        }

        public List<String> getReceiveTextureImageList() {
            return receiveTextureImageList;
        }

        public void setReceiveTextureImageList(List<String> receiveTextureImageList) {
            this.receiveTextureImageList = receiveTextureImageList;
        }

        public List<String> getSupervisorVehicleImageList() {
            return supervisorVehicleImageList;
        }

        public void setSupervisorVehicleImageList(List<String> supervisorVehicleImageList) {
            this.supervisorVehicleImageList = supervisorVehicleImageList;
        }

        public List<String> getSupervisorMaterialImageList() {
            return supervisorMaterialImageList;
        }

        public void setSupervisorMaterialImageList(List<String> supervisorMaterialImageList) {
            this.supervisorMaterialImageList = supervisorMaterialImageList;
        }

        public List<String> getSupervisorCertificateImageList() {
            return supervisorCertificateImageList;
        }

        public void setSupervisorCertificateImageList(List<String> supervisorCertificateImageList) {
            this.supervisorCertificateImageList = supervisorCertificateImageList;
        }

        public List<String> getSupervisorTextureImageList() {
            return supervisorTextureImageList;
        }

        public void setSupervisorTextureImageList(List<String> supervisorTextureImageList) {
            this.supervisorTextureImageList = supervisorTextureImageList;
        }

        public List<String> getSupervisorSceneImageList() {
            return supervisorSceneImageList;
        }

        public void setSupervisorSceneImageList(List<String> supervisorSceneImageList) {
            this.supervisorSceneImageList = supervisorSceneImageList;
        }

        public List<String> getTypeInspectionIdList() {
            return typeInspectionIdList;
        }

        public void setTypeInspectionIdList(List<String> typeInspectionIdList) {
            this.typeInspectionIdList = typeInspectionIdList;
        }

        public List<String> getTypeInspectionList() {
            return typeInspectionList;
        }

        public void setTypeInspectionList(List<String> typeInspectionList) {
            this.typeInspectionList = typeInspectionList;
        }

        public static class ClysBrandListBean implements Serializable {
            /**
             * id : 5
             * taskId : 2
             * bandName : 安钢
             * clysTypeList : [{"id":7,"taskId":2,"brandId":5,"typeName":"盘螺","clysSpecificationList":[{"id":11,"taskId":2,"typeId":7,"specificationName":"8","number":"11","unit":"吨","inspectNumber":"1","inspectNumberTwo":""},{"id":12,"taskId":2,"typeId":7,"specificationName":"6","number":"21","unit":"吨","inspectNumber":"2","inspectNumberTwo":""}],"clysSpecificationListStr":""},{"id":8,"taskId":2,"brandId":5,"typeName":"螺纹8 35.32","clysSpecificationList":[{"id":13,"taskId":2,"typeId":8,"specificationName":"32","number":"31","unit":"吨","inspectNumber":"3","inspectNumberTwo":""},{"id":14,"taskId":2,"typeId":8,"specificationName":"35","number":"41","unit":"吨","inspectNumber":"4","inspectNumberTwo":""}],"clysSpecificationListStr":""}]
             * clysTypeListStr :
             */

            public int id;
            public int taskId;
            public String bandName;
            public String clysTypeListStr;
            public List<ClysTypeListBean> clysTypeList;

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

            public String getClysTypeListStr() {
                return clysTypeListStr;
            }

            public void setClysTypeListStr(String clysTypeListStr) {
                this.clysTypeListStr = clysTypeListStr;
            }

            public List<ClysTypeListBean> getClysTypeList() {
                return clysTypeList;
            }

            public void setClysTypeList(List<ClysTypeListBean> clysTypeList) {
                this.clysTypeList = clysTypeList;
            }

            public static class ClysTypeListBean {
                /**
                 * id : 7
                 * taskId : 2
                 * brandId : 5
                 * typeName : 盘螺
                 * clysSpecificationList : [{"id":11,"taskId":2,"typeId":7,"specificationName":"8","number":"11","unit":"吨","inspectNumber":"1","inspectNumberTwo":""},{"id":12,"taskId":2,"typeId":7,"specificationName":"6","number":"21","unit":"吨","inspectNumber":"2","inspectNumberTwo":""}]
                 * clysSpecificationListStr :
                 */

                public int id;
                public int taskId;
                public int brandId;
                public String typeName;
                public String clysSpecificationListStr;
                public List<ClysSpecificationListBean> clysSpecificationList;

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

                public String getClysSpecificationListStr() {
                    return clysSpecificationListStr;
                }

                public void setClysSpecificationListStr(String clysSpecificationListStr) {
                    this.clysSpecificationListStr = clysSpecificationListStr;
                }

                public List<ClysSpecificationListBean> getClysSpecificationList() {
                    return clysSpecificationList;
                }

                public void setClysSpecificationList(List<ClysSpecificationListBean> clysSpecificationList) {
                    this.clysSpecificationList = clysSpecificationList;
                }

                public static class ClysSpecificationListBean {
                    /**
                     * id : 11
                     * taskId : 2
                     * typeId : 7
                     * specificationName : 8
                     * number : 11
                     * unit : 吨
                     * inspectNumber : 1
                     * inspectNumberTwo :
                     */

                    public int id;
                    public int taskId;
                    public int typeId;
                    public String specificationName;
                    public String number;
                    public String unit;
                    public String inspectNumber;
                    public String inspectNumberTwo;

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

        public static class ReceiveListBean {
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

        public static class SupervisorListBean {
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

        public static class AcceptanceListBean {
            /**
             * id : 13
             * taskId : 2
             * acceptance : 65
             * userId : 65
             * peopleuser :
             * tel :
             * isAcceptance :
             * isQualified : 1
             * acceptanceDate : 2019-06-10 11:53:43.0
             * acceptanceImage : 2dff2b24eb9b456890a5b98eab75d93c.jpeg
             * acceptanceImageList : ["http://192.168.110.8:8080/ydzj-admin/images/2dff2b24eb9b456890a5b98eab75d93c.jpeg"]
             * acceptanceSupplement : 建设单位验收补充说明111
             */

            public int id;
            public int taskId;
            public String acceptance;
            public String userId;
            public String peopleuser;
            public String tel;
            public String isAcceptance;
            public String isQualified;
            public String acceptanceDate;
            public String acceptanceImage;
            public String acceptanceSupplement;
            public List<String> acceptanceImageList;

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

            public String getAcceptance() {
                return acceptance;
            }

            public void setAcceptance(String acceptance) {
                this.acceptance = acceptance;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

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

            public String getIsAcceptance() {
                return isAcceptance;
            }

            public void setIsAcceptance(String isAcceptance) {
                this.isAcceptance = isAcceptance;
            }

            public String getIsQualified() {
                return isQualified;
            }

            public void setIsQualified(String isQualified) {
                this.isQualified = isQualified;
            }

            public String getAcceptanceDate() {
                return acceptanceDate;
            }

            public void setAcceptanceDate(String acceptanceDate) {
                this.acceptanceDate = acceptanceDate;
            }

            public String getAcceptanceImage() {
                return acceptanceImage;
            }

            public void setAcceptanceImage(String acceptanceImage) {
                this.acceptanceImage = acceptanceImage;
            }

            public String getAcceptanceSupplement() {
                return acceptanceSupplement;
            }

            public void setAcceptanceSupplement(String acceptanceSupplement) {
                this.acceptanceSupplement = acceptanceSupplement;
            }

            public List<String> getAcceptanceImageList() {
                return acceptanceImageList;
            }

            public void setAcceptanceImageList(List<String> acceptanceImageList) {
                this.acceptanceImageList = acceptanceImageList;
            }
        }

        public static class CcListBean {
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

        public static class InspectorListBean {
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


        public List<ImageListBean> receiveImageList;

        public List<ImageListBean> supervisorImageList;

        public static class ImageListBean {
            public String name;
            public List<String> list;
        }


    }
}
