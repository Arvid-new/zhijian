package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/13.
 * Describe: Ydzj_project
 */
public class ZhengGaiListBean {


    /**
     * msg : success
     * code : 0
     * list : [{"id":30,"appGxyjId":1,"itemsName":"1","position":"1","inspectionName":"1","description":"1","issuePicture":"1","buchongExplain":"1","tijiaoId":1,"tijiaoPeople":"1","abarbeitungTime":"1","accountabilityUnit":"1","zhenggaiId":1,"zhenggaiPeople":"1","zhenggaiTime":"2019-06-13 17:43:24","abarbeitungPicture":"1","abarbeitungExplain":"1","fuyanId":1,"fuyanPeople":"1","chaosongId":"1","chaosongPeople":"1","orderOfSeverity":"1","creatorTime":"2019-06-13 17:43:34","ztCondition":"待整改","closeTime":"2019-06-13 17:43:51","closeCause":"1","sendBackCause":"1","sendBackPicture":"1","sendBackNumber":1,"sectionId":1,"listArbeitungPicture":[],"listIssuePicture":["http://192.168.110.33:8080/ydzj-admin/images/1"],"listSendBackPicture":[],"listTijiaoPeople":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"listZhenggaiPeople":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"listFuyanPeople":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}],"listChaosongPeople":[{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 30
         * appGxyjId : 1
         * itemsName : 1
         * position : 1
         * inspectionName : 1
         * description : 1
         * issuePicture : 1
         * buchongExplain : 1
         * tijiaoId : 1
         * tijiaoPeople : 1
         * abarbeitungTime : 1
         * accountabilityUnit : 1
         * zhenggaiId : 1
         * zhenggaiPeople : 1
         * zhenggaiTime : 2019-06-13 17:43:24
         * abarbeitungPicture : 1
         * abarbeitungExplain : 1
         * fuyanId : 1
         * fuyanPeople : 1
         * chaosongId : 1
         * chaosongPeople : 1
         * orderOfSeverity : 1
         * creatorTime : 2019-06-13 17:43:34
         * ztCondition : 待整改
         * closeTime : 2019-06-13 17:43:51
         * closeCause : 1
         * sendBackCause : 1
         * sendBackPicture : 1
         * sendBackNumber : 1
         * sectionId : 1
         * listArbeitungPicture : []
         * listIssuePicture : ["http://192.168.110.33:8080/ydzj-admin/images/1"]
         * listSendBackPicture : []
         * listTijiaoPeople : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * listZhenggaiPeople : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * listFuyanPeople : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         * listChaosongPeople : [{"userId":1,"peopleuser":"admin","tel":"1111111111111111111"}]
         */

        public int id;
        public int appGxyjId;
        public String itemsName;
        public String position;
        public String inspectionName;
        public String description;
        public String issuePicture;
        public String buchongExplain;
        public int tijiaoId;
        public String tijiaoPeople;
        public String abarbeitungTime;
        public String accountabilityUnit;
        public int zhenggaiId;
        public String zhenggaiPeople;
        public String zhenggaiTime;
        public String abarbeitungPicture;
        public String abarbeitungExplain;
        public int fuyanId;
        public String fuyanPeople;
        public String chaosongId;
        public String chaosongPeople;
        public String orderOfSeverity;
        public String creatorTime;
        public String ztCondition;
        public String closeTime;
        public String closeCause;
        public String sendBackCause;
        public String sendBackPicture;
        public int sendBackNumber;
        public int sectionId;
        public List<String> listArbeitungPicture;
        public List<String> listIssuePicture;
        public List<String> listSendBackPicture;
        public List<ListTijiaoPeopleBean> listTijiaoPeople;
        public List<ListZhenggaiPeopleBean> listZhenggaiPeople;
        public List<ListFuyanPeopleBean> listFuyanPeople;
        public List<ListChaosongPeopleBean> listChaosongPeople;

        public static class ListTijiaoPeopleBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
            public String peopleuser;
            public String tel;
        }

        public static class ListZhenggaiPeopleBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
            public String peopleuser;
            public String tel;
        }

        public static class ListFuyanPeopleBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
            public String peopleuser;
            public String tel;
        }

        public static class ListChaosongPeopleBean {
            /**
             * userId : 1
             * peopleuser : admin
             * tel : 1111111111111111111
             */

            public int userId;
            public String peopleuser;
            public String tel;
        }
    }
}
