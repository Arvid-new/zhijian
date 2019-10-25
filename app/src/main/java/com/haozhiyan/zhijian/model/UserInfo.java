package com.haozhiyan.zhijian.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.haozhiyan.zhijian.bean.LoginBean;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SPUtils;
import com.haozhiyan.zhijian.utils.StringUtils;

//用户信息封装类
public class UserInfo {

    private Context context;
    private static UserInfo UD = null;//会员业务逻辑类 对象
    private final String QUALITY_FILE_NAME = "quality_info";
    private final String LOGIN_STATE = "isLogin";//登录状态
    public static final String USER_ID = "userId";//用户id
    public static final String USERNAME = "name";//用户名称
    public static final String COMPANY_CODE = "company_code";//企业代码
    public static final String IS_APP_COOKIE = "is_app_cookie";//判断cookie
    public static final String USER_INFO_COOKIE = "user_info_cookie";//cookie
    public static final String USER_HEADER_PIC = "user_headPic";//用户头像
    public static final String USER_APP_TAG = "userAppTag";//用户身份  3建设人员   2监理人员  1施工人员
    public static final String USER_TAG = "userTag";//用户登录权限标识  0 禁止登录 1 登录PC 2 登录APP 3双向登录
    public static final String MOBILE = "userTag";//手机号
    public static final String USERAPPTAGSG = "userAppTagSg";//是否有施工权限  1 是 0 否

    private UserInfo(Context context) {
        this.context = context;
    }

    /**
     * 单利模式
     *
     * @param context
     * @return
     */
    public static UserInfo create(Context context) {
        if (UD == null)
            UD = new UserInfo(context);
        return UD;
    }

    /**
     * 是否登陆
     *
     * @return
     */
    public boolean isLogin() {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(LOGIN_STATE, false);
    }

    /**
     * 改变登陆状态  清空保存数据
     *
     * @param isLogin
     * @return
     */
    public UserInfo ChangeLoginState(boolean isLogin) {
        //退出登陆
        if (!isLogin) {
            clearAll();
        }
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putBoolean(LOGIN_STATE, isLogin);
        ed.commit();
        return UD;
    }

    private void clearAll() {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.commit();
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public String get(String key) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    //用户id
    public void putUserId(String userId) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        if (StringUtils.isEmpty(userId)) {
            sp.edit().putString(USER_ID, "").commit();
        } else {
            sp.edit().putString(USER_ID, userId).commit();
        }
    }

    public String getUserId() {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_ID, "");
    }

    //用户名称
    public void putUserName(String userName) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        if (StringUtils.isEmpty(userName)) {
            sp.edit().putString(USERNAME, "").commit();
        } else {
            sp.edit().putString(USERNAME, userName).commit();
        }
    }

    public String getUserName() {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USERNAME, "");
    }

    //用户头像
    public void putUserHeader(String headerPic) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        if (StringUtils.isEmpty(headerPic)) {
            sp.edit().putString(USER_HEADER_PIC, "").commit();
        } else {
            sp.edit().putString(USER_HEADER_PIC, headerPic).commit();
        }
    }

    public String getUserHeader() {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(USER_HEADER_PIC, "");
    }

    //用户头像
    public void putMobile(String mobile) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        if (StringUtils.isEmpty(mobile)) {
            sp.edit().putString(MOBILE, "").commit();
        } else {
            sp.edit().putString(MOBILE, mobile).commit();
        }
    }

    public String getMobile() {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString(MOBILE, "");
    }

    /**
     * 是否已经保存了 首次登陆应该缓存的数据
     * @param bos
     */
    public void putIsSaveFirstLoginData(boolean bos) {
        SharedPreferences sp = context.getSharedPreferences("first_login_data", Context.MODE_PRIVATE);
        sp.edit().putBoolean("IsSaveFirstLoginData", bos).commit();
    }

    public boolean getIsSaveFirstLoginData() {
        SharedPreferences sp = context.getSharedPreferences("first_login_data", Context.MODE_PRIVATE);
        return sp.getBoolean("IsSaveFirstLoginData", false);
    }

    /**
     * 保存数据
     *
     * @param
     * @return
     */
    public void saveUserData(LoginBean loginBean) {
        int userType = loginBean.userAppTag;
        putLoginStatus(true);
        putUserId(loginBean.userId);
        putUserName(loginBean.peopleuser);
        putUserHeader(loginBean.headPortrait);
        putMobile(loginBean.mobile);
        putUserAppTagSg(loginBean.userAppTagSg);
        putUserType(userType + "");
        putIsSaveFirstLoginData(false);
//        if (userType == 0) {//admin
//            putUserType("0");
//        } else if (userType == 1) {//施工
//            putUserType("1");
//        } else if (userType == 2) {//监理
//            putUserType("2");
//        } else if (userType == 3) {// 建设
//            putUserType("3");
//        }
    }

    //操作登录状态
    public void putLoginStatus(boolean isLogin) {
        SPUtils.saveBoolean(context, LOGIN_STATE, isLogin);
    }

    public boolean getLoginStatus() {
        return SPUtils.getBoolean(context, LOGIN_STATE, false);
    }

    //操作用户登录权限
    public void putUserTag(String userAppTag) {
        if (StringUtils.isEmpty(userAppTag)) {
            SPUtils.saveString(context, USER_TAG, "", QUALITY_FILE_NAME);
        } else {
            SPUtils.saveString(context, USER_TAG, userAppTag, QUALITY_FILE_NAME);
        }
    }

//    public String getUserTag() {
//        try {
//            return SPUtils.getString(context, USER_TAG, "", QUALITY_FILE_NAME);
//        } catch (Exception e) {
//            return "";
//        }
//    }

    //操作cookie
    public void putUserCookie(String userCookie) {
        if (StringUtils.isEmpty(userCookie)) {
            SPUtils.saveString(context, USER_INFO_COOKIE, "", QUALITY_FILE_NAME);
        } else {
            SPUtils.saveString(context, USER_INFO_COOKIE, userCookie, QUALITY_FILE_NAME);
        }
    }

    public String getUserCookie() {
        try {
            return SPUtils.getString(context, USER_INFO_COOKIE, "", QUALITY_FILE_NAME);
        } catch (Exception e) {
            return "";
        }
    }

    //操作用户类型
    public void putUserType(String type) {
        if (StringUtils.isEmpty(type)) {
            SPUtils.saveString(context, USER_APP_TAG, "1", QUALITY_FILE_NAME);
        } else {
            SPUtils.saveString(context, USER_APP_TAG, type, QUALITY_FILE_NAME);
        }
    }

    public String getUserType() {
        try {
            return SPUtils.getString(context, USER_APP_TAG, "", QUALITY_FILE_NAME);
        } catch (Exception e) {
            return "";
        }
    }

    //操作企业代码
    public void putCompanyCode(String companyCode) {
        if (StringUtils.isEmpty(companyCode)) {
            SPUtils.saveString(context, COMPANY_CODE, "", QUALITY_FILE_NAME);
        } else {
            SPUtils.saveString(context, COMPANY_CODE, companyCode, QUALITY_FILE_NAME);
        }
    }

    public String getCompanyCode() {
        try {
            return SPUtils.getString(context, COMPANY_CODE, "", QUALITY_FILE_NAME);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 改变数据
     *
     * @param key
     * @param value
     * @return
     */
    public boolean changeUserData(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        try {
            ed.putString(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ed.commit();
    }

    //是否有施工的权限
    public void putUserAppTagSg(String userAppTagSg) {
        SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
        if (StringUtils.isEmpty(userAppTagSg)) {
            sp.edit().putString(USERAPPTAGSG, "").commit();
        } else {
            sp.edit().putString(USERAPPTAGSG, userAppTagSg).commit();
        }
    }

    public String getUserAppTagSg() {
        try {
            SharedPreferences sp = context.getSharedPreferences(QUALITY_FILE_NAME, Context.MODE_PRIVATE);
            String tagsg = sp.getString(USERAPPTAGSG, "");
            LogUtils.d(USERAPPTAGSG, tagsg);
            return tagsg;
        } catch (Exception e) {
            return "";
        }
    }

}
