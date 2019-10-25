package com.haozhiyan.zhijian.bean;

import java.util.List;

public class DaiBanGXYJListBean {

    private String msg;
    private int code;
    private ListBean list;

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

    public ListBean getList() {
        return list;
    }

    public void setList(ListBean list) {
        this.list = list;
    }

    public static class ListBean {

        private String daibanCount;
        private String yibanCount;
        private String chaosongCount;
        private List<ListDaibanBean> listDaiban;
        private List<ListDaiZhenggaiBean> listDaiZhenggai;


        public String getDaibanCount() {
            return daibanCount;
        }

        public void setDaibanCount(String daibanCount) {
            this.daibanCount = daibanCount;
        }

        public String getYibanCount() {
            return yibanCount;
        }

        public void setYibanCount(String yibanCount) {
            this.yibanCount = yibanCount;
        }

        public String getChaosongCount() {
            return chaosongCount;
        }

        public void setChaosongCount(String chaosongCount) {
            this.chaosongCount = chaosongCount;
        }

        public List<ListDaibanBean> getListDaiban() {
            return listDaiban;
        }

        public void setListDaiban(List<ListDaibanBean> listDaiban) {
            this.listDaiban = listDaiban;
        }

        public List<ListDaiZhenggaiBean> getListDaiZhenggai() {
            return listDaiZhenggai;
        }

        public void setListDaiZhenggai(List<ListDaiZhenggaiBean> listDaiZhenggai) {
            this.listDaiZhenggai = listDaiZhenggai;
        }

        public static class ListDaibanBean {
            /**
             * id : 44
             * detailsName : 主体工程-梁板综合预埋
             * sectionName : 测试(测试环境)
             * siteName : 6栋-1单元
             * handOverPart : 8层
             * pictureVideo : fbcfe436eded4ddd96b3c3c5248afddc.png
             * identifying : 待验收
             * sectionId : 17
             * creationTime : 2019-06-12 15:10:09
             * inspectionId : 2795968130973696
             * inspectionName : 主体工程
             * childPV : ["http://192.168.110.33:8080/ydzj-admin/images/fbcfe436eded4ddd96b3c3c5248afddc.png"]
             */

            private int id;
            private String detailsName;
            private String sectionName;
            private String siteName;
            private String handOverPart;
            private String pictureVideo;
            private String identifying;
            private int sectionId;
            private String creationTime;
            private String inspectionId;
            private String inspectionName;
            private List<String> childPV;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDetailsName() {
                return detailsName;
            }

            public void setDetailsName(String detailsName) {
                this.detailsName = detailsName;
            }

            public String getSectionName() {
                return sectionName;
            }

            public void setSectionName(String sectionName) {
                this.sectionName = sectionName;
            }

            public String getSiteName() {
                return siteName;
            }

            public void setSiteName(String siteName) {
                this.siteName = siteName;
            }

            public String getHandOverPart() {
                return handOverPart;
            }

            public void setHandOverPart(String handOverPart) {
                this.handOverPart = handOverPart;
            }

            public String getPictureVideo() {
                return pictureVideo;
            }

            public void setPictureVideo(String pictureVideo) {
                this.pictureVideo = pictureVideo;
            }

            public String getIdentifying() {
                return identifying;
            }

            public void setIdentifying(String identifying) {
                this.identifying = identifying;
            }

            public int getSectionId() {
                return sectionId;
            }

            public void setSectionId(int sectionId) {
                this.sectionId = sectionId;
            }

            public String getCreationTime() {
                return creationTime;
            }

            public void setCreationTime(String creationTime) {
                this.creationTime = creationTime;
            }

            public String getInspectionId() {
                return inspectionId;
            }

            public void setInspectionId(String inspectionId) {
                this.inspectionId = inspectionId;
            }

            public String getInspectionName() {
                return inspectionName;
            }

            public void setInspectionName(String inspectionName) {
                this.inspectionName = inspectionName;
            }

            public List<String> getChildPV() {
                return childPV;
            }

            public void setChildPV(List<String> childPV) {
                this.childPV = childPV;
            }
        }

        public static class ListDaiZhenggaiBean {

            /**
             * id : 32
             * appGxyjId : 49
             * itemsName : 主体工程-梁板综合预埋
             * position : 101
             * inspectionName : 主体工程
             * issuePicture : 20095a377bcc4375b4fc4fad5f997654.png
             * orderOfSeverity : 重要
             * creatorTime : 2019-06-13 09:51:11
             * ztCondition : 待整改
             * description : 预埋件浇筑时的成品保护不到位
             * tijiaoId : 2
             * tijiaoPeople : admin
             * zhenggaiId : 1
             * zhenggaiPeople : admin
             * fuyanId : 3
             * fuyanPeople : 李四
             * chaosongId : 4
             * chaosongPeople : 王五
             * listIssuePicture : ["http://192.168.110.33:8080/ydzj-admin/images/20095a377bcc4375b4fc4fad5f997654.png"]
             */

            private int id;
            private int appGxyjId;
            private String itemsName;
            private String position;
            private String inspectionName;
            private String issuePicture;
            private String orderOfSeverity;
            private String creatorTime;
            private String ztCondition;
            private String description;
            private int tijiaoId;
            private String tijiaoPeople;
            private int zhenggaiId;
            private String zhenggaiPeople;
            private int fuyanId;
            private String fuyanPeople;
            private String chaosongId;
            private String chaosongPeople;
            private List<String> listIssuePicture;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getAppGxyjId() {
                return appGxyjId;
            }

            public void setAppGxyjId(int appGxyjId) {
                this.appGxyjId = appGxyjId;
            }

            public String getItemsName() {
                return itemsName;
            }

            public void setItemsName(String itemsName) {
                this.itemsName = itemsName;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public String getInspectionName() {
                return inspectionName;
            }

            public void setInspectionName(String inspectionName) {
                this.inspectionName = inspectionName;
            }

            public String getIssuePicture() {
                return issuePicture;
            }

            public void setIssuePicture(String issuePicture) {
                this.issuePicture = issuePicture;
            }

            public String getOrderOfSeverity() {
                return orderOfSeverity;
            }

            public void setOrderOfSeverity(String orderOfSeverity) {
                this.orderOfSeverity = orderOfSeverity;
            }

            public String getCreatorTime() {
                return creatorTime;
            }

            public void setCreatorTime(String creatorTime) {
                this.creatorTime = creatorTime;
            }

            public String getZtCondition() {
                return ztCondition;
            }

            public void setZtCondition(String ztCondition) {
                this.ztCondition = ztCondition;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public int getTijiaoId() {
                return tijiaoId;
            }

            public void setTijiaoId(int tijiaoId) {
                this.tijiaoId = tijiaoId;
            }

            public String getTijiaoPeople() {
                return tijiaoPeople;
            }

            public void setTijiaoPeople(String tijiaoPeople) {
                this.tijiaoPeople = tijiaoPeople;
            }

            public int getZhenggaiId() {
                return zhenggaiId;
            }

            public void setZhenggaiId(int zhenggaiId) {
                this.zhenggaiId = zhenggaiId;
            }

            public String getZhenggaiPeople() {
                return zhenggaiPeople;
            }

            public void setZhenggaiPeople(String zhenggaiPeople) {
                this.zhenggaiPeople = zhenggaiPeople;
            }

            public int getFuyanId() {
                return fuyanId;
            }

            public void setFuyanId(int fuyanId) {
                this.fuyanId = fuyanId;
            }

            public String getFuyanPeople() {
                return fuyanPeople;
            }

            public void setFuyanPeople(String fuyanPeople) {
                this.fuyanPeople = fuyanPeople;
            }

            public String getChaosongId() {
                return chaosongId;
            }

            public void setChaosongId(String chaosongId) {
                this.chaosongId = chaosongId;
            }

            public String getChaosongPeople() {
                return chaosongPeople;
            }

            public void setChaosongPeople(String chaosongPeople) {
                this.chaosongPeople = chaosongPeople;
            }

            public List<String> getListIssuePicture() {
                return listIssuePicture;
            }

            public void setListIssuePicture(List<String> listIssuePicture) {
                this.listIssuePicture = listIssuePicture;
            }
        }
    }
}
