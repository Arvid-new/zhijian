package com.haozhiyan.zhijian.bean;

import java.io.Serializable;

/**
 * Created by WangZhenKai on 2019/6/29.
 * Describe: Ydzj_project
 */
public class LoginBean implements Serializable {

    /**
     * msg : success
     * userAppTag : 1  用户登录权限身份标识  3建设人员  2监理人员  1施工人员
     * code : 0
     * userTag : 2  登录权限 - 0禁止登录  1登录PC  2登录APP 3双向登录
     * userId : 2  用户id
     * username : "15236411249" 用户账号
     * peopleuser : "张三" 用户名称
     */
    public String msg;
    public String userId;
    public String username;
    public String peopleuser;
    public String headPortrait;
    public String email;
    public String mobile;
    public String userAppTagSg;
    public int userAppTag;
    public int code;
    public int userTag;

}
