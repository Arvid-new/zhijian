package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/26.
 * Describe: Ydzj_project
 */
public class NewZhengGaiListBean  implements Serializable {

    /**
     * code : 0
     * list : {"listAbarbeitung":[{"abarbeitungExplain":"哈哈呵呵呵呵五花肉","abarbeitungPicture":"swaredfdgkdsf232dsd.png","abarbeitungTime":"2019-08-03","accountabilityUnit":"金隅保温（翠园A1#一标段）2","appGxyjId":275,"buchongExplain":"Bxbxbxn","chaosongId":"206,211,","chaosongPeople":"监理人员,监理-小红","closeCause":"不合适","closeTime":"2019-12-08","creatorTime":"2019-07-26 11:49:07","description":"剪力墙加固不到位","dikuaiId":46,"dikuaiName":"91#地块","fuyanId":206,"fuyanPeople":"监理人员","fuyanState":"1","id":101,"inspectionName":"主体工程","issuePicture":"9dc08e0d3fac4ebfbe995c8e4e4f846b.png","itemsName":"主体工程-模板验收","listArbeitungPicture":[],"listChaosongPeople":[{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}],"listFuyanPeople":[{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}],"listIssuePicture":["http://192.168.110.66:8080/ydzj-admin/images/9dc08e0d3fac4ebfbe995c8e4e4f846b.png"],"listSendBackPicture":[],"listTijiaoPeople":[{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}],"listZhenggaiPeople":[{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}],"orderOfSeverity":"要紧","pointSite":"swfdrwrererer3","position":"1栋1单元","projectId":2,"projectName":"名门紫园","roomId":220,"sectionId":30,"sectionName":"测试标段0613","sendBackCause":"不行","sendBackNumber":0,"sendBackPicture":"dfdgfgf546fgffrr5tr45b.jpg","siteName":"1栋","tijiaoId":206,"tijiaoPeople":"监理人员","zhenggaiId":206,"zhenggaiPeople":"监理人员","zhenggaiState":"待整改","zhenggaiTime":"2019-12-08","ztCondition":"待整改"}],"severityproblem":[{"severityId":1,"severityName":"一般"},{"severityId":2,"severityName":"重要"},{"severityId":3,"severityName":"紧急"},{"severityId":4,"severityName":"要紧"}]}
     * msg : success
     */

    public int code;
    public ListBean list;
    public String msg;


    public static class ListBean  implements Serializable {
        public List<ListAbarbeitungBean> listAbarbeitung;
        public List<SeverityproblemBean> severityproblem;

        public static class ListAbarbeitungBean  implements Serializable {
            /**
             * abarbeitungExplain : 哈哈呵呵呵呵五花肉
             * abarbeitungPicture : swaredfdgkdsf232dsd.png
             * abarbeitungTime : 2019-08-03
             * accountabilityUnit : 金隅保温（翠园A1#一标段）2
             * appGxyjId : 275
             * buchongExplain : Bxbxbxn
             * chaosongId : 206,211,
             * chaosongPeople : 监理人员,监理-小红
             * closeCause : 不合适
             * closeTime : 2019-12-08
             * creatorTime : 2019-07-26 11:49:07
             * description : 剪力墙加固不到位
             * dikuaiId : 46
             * dikuaiName : 91#地块
             * fuyanId : 206
             * fuyanPeople : 监理人员
             * fuyanState : 1
             * id : 101
             * inspectionName : 主体工程
             * issuePicture : 9dc08e0d3fac4ebfbe995c8e4e4f846b.png
             * itemsName : 主体工程-模板验收
             * listArbeitungPicture : []
             * listChaosongPeople : [{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}]
             * listFuyanPeople : [{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}]
             * listIssuePicture : ["http://192.168.110.66:8080/ydzj-admin/images/9dc08e0d3fac4ebfbe995c8e4e4f846b.png"]
             * listSendBackPicture : []
             * listTijiaoPeople : [{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}]
             * listZhenggaiPeople : [{"peopleuser":"监理人员","tel":"15937085623","userAppTag":3,"userId":206}]
             * orderOfSeverity : 要紧
             * pointSite : swfdrwrererer3
             * position : 1栋1单元
             * projectId : 2
             * projectName : 名门紫园
             * roomId : 220
             * sectionId : 30
             * sectionName : 测试标段0613
             * sendBackCause : 不行
             * sendBackNumber : 0
             * sendBackPicture : dfdgfgf546fgffrr5tr45b.jpg
             * siteName : 1栋
             * tijiaoId : 206
             * tijiaoPeople : 监理人员
             * zhenggaiId : 206
             * zhenggaiPeople : 监理人员
             * zhenggaiState : 待整改
             * zhenggaiTime : 2019-12-08
             * ztCondition : 待整改
             */

            public String abarbeitungExplain;
            public String abarbeitungPicture;
            public String abarbeitungTime;
            public String accountabilityUnit;
            public int appGxyjId;
            public String buchongExplain;
            public String chaosongId;
            public String chaosongPeople;
            public String closeCause;
            public String closeTime;
            public String creatorTime;
            public String description;
            public int dikuaiId;
            public String dikuaiName;
            public String fuyanId;
            public String fuyanPeople;
            public String fuyanState;
            public int id;
            public String inspectionName;
            public String issuePicture;
            public String itemsName;
            public String orderOfSeverity;
            public String pointSite;
            public String position;
            public int projectId;
            public String projectName;
            public int roomId;
            public String sectionId;
            public String sectionName;
            public String sendBackCause;
            public int sendBackNumber;
            public String sendBackPicture;
            public String siteName;
            public int tijiaoId;
            public String tijiaoPeople;
            public String zhenggaiId;
            public String zhenggaiPeople;
            public String zhenggaiState;
            public String zhenggaiTime;
            public String ztCondition;
            public List<String> listArbeitungPicture;
            public List<ListChaosongPeopleBean> listChaosongPeople;
            public List<ListFuyanPeopleBean> listFuyanPeople;
            public List<String> listIssuePicture;
            public List<String> listSendBackPicture;
            public List<ListTijiaoPeopleBean> listTijiaoPeople;
            public List<ListZhenggaiPeopleBean> listZhenggaiPeople;

            @Override
            public String toString() {
                return "ListAbarbeitungBean{" +
                        "abarbeitungExplain='" + abarbeitungExplain + '\'' +
                        ", abarbeitungPicture='" + abarbeitungPicture + '\'' +
                        ", abarbeitungTime='" + abarbeitungTime + '\'' +
                        ", accountabilityUnit='" + accountabilityUnit + '\'' +
                        ", appGxyjId=" + appGxyjId +
                        ", buchongExplain='" + buchongExplain + '\'' +
                        ", chaosongId='" + chaosongId + '\'' +
                        ", chaosongPeople='" + chaosongPeople + '\'' +
                        ", closeCause='" + closeCause + '\'' +
                        ", closeTime='" + closeTime + '\'' +
                        ", creatorTime='" + creatorTime + '\'' +
                        ", description='" + description + '\'' +
                        ", dikuaiId=" + dikuaiId +
                        ", dikuaiName='" + dikuaiName + '\'' +
                        ", fuyanId='" + fuyanId + '\'' +
                        ", fuyanPeople='" + fuyanPeople + '\'' +
                        ", fuyanState='" + fuyanState + '\'' +
                        ", id=" + id +
                        ", inspectionName='" + inspectionName + '\'' +
                        ", issuePicture='" + issuePicture + '\'' +
                        ", itemsName='" + itemsName + '\'' +
                        ", orderOfSeverity='" + orderOfSeverity + '\'' +
                        ", pointSite='" + pointSite + '\'' +
                        ", position='" + position + '\'' +
                        ", projectId=" + projectId +
                        ", projectName='" + projectName + '\'' +
                        ", roomId=" + roomId +
                        ", sectionId=" + sectionId +
                        ", sectionName='" + sectionName + '\'' +
                        ", sendBackCause='" + sendBackCause + '\'' +
                        ", sendBackNumber=" + sendBackNumber +
                        ", sendBackPicture='" + sendBackPicture + '\'' +
                        ", siteName='" + siteName + '\'' +
                        ", tijiaoId=" + tijiaoId +
                        ", tijiaoPeople='" + tijiaoPeople + '\'' +
                        ", zhenggaiId='" + zhenggaiId + '\'' +
                        ", zhenggaiPeople='" + zhenggaiPeople + '\'' +
                        ", zhenggaiState='" + zhenggaiState + '\'' +
                        ", zhenggaiTime='" + zhenggaiTime + '\'' +
                        ", ztCondition='" + ztCondition + '\'' +
                        ", listArbeitungPicture=" + listArbeitungPicture +
                        ", listChaosongPeople=" + listChaosongPeople +
                        ", listFuyanPeople=" + listFuyanPeople +
                        ", listIssuePicture=" + listIssuePicture +
                        ", listSendBackPicture=" + listSendBackPicture +
                        ", listTijiaoPeople=" + listTijiaoPeople +
                        ", listZhenggaiPeople=" + listZhenggaiPeople +
                        '}';
            }

            public static class ListChaosongPeopleBean  implements Serializable {
                /**
                 * peopleuser : 监理人员
                 * tel : 15937085623
                 * userAppTag : 3
                 * userId : 206
                 */

                public String peopleuser;
                public String tel;
                public int userAppTag;
                public int userId;
            }

            public static class ListFuyanPeopleBean  implements Serializable {
                /**
                 * peopleuser : 监理人员
                 * tel : 15937085623
                 * userAppTag : 3
                 * userId : 206
                 */

                public String peopleuser;
                public String tel;
                public int userAppTag;
                public int userId;
            }

            public static class ListTijiaoPeopleBean  implements Serializable {
                /**
                 * peopleuser : 监理人员
                 * tel : 15937085623
                 * userAppTag : 3
                 * userId : 206
                 */

                public String peopleuser;
                public String tel;
                public int userAppTag;
                public int userId;
            }

            public static class ListZhenggaiPeopleBean implements Serializable {
                /**
                 * peopleuser : 监理人员
                 * tel : 15937085623
                 * userAppTag : 3
                 * userId : 206
                 */

                public String peopleuser;
                public String tel;
                public int userAppTag;
                public int userId;
            }
        }

        public static class SeverityproblemBean implements Serializable {
            /**
             * severityId : 1
             * severityName : 一般
             */

            public int severityId;
            public String severityName;
        }
    }
}
