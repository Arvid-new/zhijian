package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/15.
 * Describe: Ydzj_project
 */
public class BackNoteBean {

    /**
     * msg : success
     * code : 0
     * list : [{"backId":1,"appGxyjId":87,"backType":"监理","backUsername":
     * "admin","backCause":"预留洞口不满足节点施工要求,预留位置及尺寸不满足图纸要求",
     * "backExplain":"Jdjjdjdjjdjd\n","constructionUnit":"测试承建商456"
     * ,"constructionDirector":"admin","creationTime":"2019-06-14 17:18:58",
     * "backCreationTime":"2019-06-14 19:17:22","transferRecord":"65016059f75846d580d5599d6fe47b4b.png",
     * "backPictureVideo":"e0c635f6c7734627aa995ab533daca0f.png"
     * ,"childbackPictureVideo":["http://ms.mienre.com/ydzj-admin/images/e0c635f6c7734627aa995ab533daca0f.png"],
     * "childTransferRecord":["http://ms.mienre.com/ydzj-admin/images/65016059f75846d580d5599d6fe47b4b.png"],"backNumber":null}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean {
        /**
         * backId : 1
         * appGxyjId : 87
         * backType : 监理
         * backUsername : admin
         * backCause : 预留洞口不满足节点施工要求,预留位置及尺寸不满足图纸要求
         * backExplain : Jdjjdjdjjdjd
         * <p>
         * constructionUnit : 测试承建商456
         * constructionDirector : admin
         * creationTime : 2019-06-14 17:18:58
         * backCreationTime : 2019-06-14 19:17:22
         * transferRecord : 65016059f75846d580d5599d6fe47b4b.png
         * backPictureVideo : e0c635f6c7734627aa995ab533daca0f.png
         * childbackPictureVideo : ["http://ms.mienre.com/ydzj-admin/images/e0c635f6c7734627aa995ab533daca0f.png"]
         * childTransferRecord : ["http://ms.mienre.com/ydzj-admin/images/65016059f75846d580d5599d6fe47b4b.png"]
         * backNumber : null
         */

        public int backId;
        public int appGxyjId;
        public String backType;
        public String backUsername;
        public String backCause;
        public String backExplain;
        public String constructionUnit;
        public String transferExplain;
        public String constructionDirector;
        public String creationTime;
        public String backCreationTime;
        public String transferRecord;
        public String backPictureVideo;
        public String backNumber;
        public List<String> childbackPictureVideo;
        public List<String> childTransferRecord;
    }
}
