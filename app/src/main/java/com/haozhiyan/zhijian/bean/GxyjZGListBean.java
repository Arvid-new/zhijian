package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/11.
 * Describe: Ydzj_project
 */
public class GxyjZGListBean {

    /**
     * msg : success
     * code : 0
     * list : [{"id":14,"appGxyjId":43,"itemsName":"主体工程-梁板综合预埋","position":"101","inspectionName":"主体工程","description":"管道间距小于粗骨料粒径、三管重叠、加固不牢固","issuePicture":"033105e05a4646a0a6d272054273b648.png","buchongExplain":"Ggcgghhjn","tijiaoId":2,"tijiaoPeople":"admin","abarbeitungTime":"2019-06-20","accountabilityUnit":"翠园A1#一标段","zhenggaiId":1,"zhenggaiPeople":"admin","zhenggaiTime":null,"abarbeitungPicture":null,"abarbeitungExplain":null,"fuyanId":1,"fuyanPeople":"admin","chaosongId":"3","chaosongPeople":"李四","orderOfSeverity":"重要","creatorTime":"2019-06-11 18:08:04","ztCondition":"待整改","closeTime":null,"closeCause":null,"sendBackCause":null,"sendBackPicture":null,"sendBackNumber":0,"listArbeitungPicture":[],"listIssuePicture":["http://192.168.110.8:8080/ydzj-admin/images/033105e05a4646a0a6d272054273b648.png"],"listSendBackPicture":[]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * id : 14
         * appGxyjId : 43
         * itemsName : 主体工程-梁板综合预埋
         * position : 101
         * inspectionName : 主体工程
         * description : 管道间距小于粗骨料粒径、三管重叠、加固不牢固
         * issuePicture : 033105e05a4646a0a6d272054273b648.png
         * buchongExplain : Ggcgghhjn
         * tijiaoId : 2
         * tijiaoPeople : admin
         * abarbeitungTime : 2019-06-20
         * accountabilityUnit : 翠园A1#一标段
         * zhenggaiId : 1
         * zhenggaiPeople : admin
         * zhenggaiTime : null
         * abarbeitungPicture : null
         * abarbeitungExplain : null
         * fuyanId : 1
         * fuyanPeople : admin
         * chaosongId : 3
         * chaosongPeople : 李四
         * orderOfSeverity : 重要
         * creatorTime : 2019-06-11 18:08:04
         * ztCondition : 待整改
         * closeTime : null
         * closeCause : null
         * sendBackCause : null
         * sendBackPicture : null
         * sendBackNumber : 0
         * listArbeitungPicture : []
         * listIssuePicture : ["http://192.168.110.8:8080/ydzj-admin/images/033105e05a4646a0a6d272054273b648.png"]
         * listSendBackPicture : []
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
        public Object zhenggaiTime;
        public Object abarbeitungPicture;
        public Object abarbeitungExplain;
        public int fuyanId;
        public String fuyanPeople;
        public String chaosongId;
        public String chaosongPeople;
        public String orderOfSeverity;
        public String creatorTime;
        public String ztCondition;
        public Object closeTime;
        public Object closeCause;
        public Object sendBackCause;
        public Object sendBackPicture;
        public int sendBackNumber;
        public List<?> listArbeitungPicture;
        public List<String> listIssuePicture;
        public List<?> listSendBackPicture;
    }
}
