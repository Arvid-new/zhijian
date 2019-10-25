package com.haozhiyan.zhijian.bean;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/23.
 * Describe: Ydzj_project
 */
public class FeedBackListBean {

    /**
     * msg : success
     * code : 0
     * list : [{"id":8,"feedback":"123456","problemPicture":"009b34efbb864eef8f22cd1faf814255.jpg,ee4a8a1df50f48efbe57efc02e2cd25e.jpg","userId":212,"serviceMessage":"已收到你的反馈，我们将尽快处理！紧急情况可加客服微信：729509614，53395479","creatorTime":"2019-07-23 16:34:58","childPP":["http://ms.mienre.com/ydzj-admin/images/009b34efbb864eef8f22cd1faf814255.jpg","http://ms.mienre.com/ydzj-admin/images/ee4a8a1df50f48efbe57efc02e2cd25e.jpg"]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;
    public static class ListBean {
        public int id;
        public String feedback;
        public String problemPicture;
        public int userId;
        public String serviceMessage;
        public String creatorTime;
        public List<String> childPP;
    }
}
