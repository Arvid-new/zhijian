package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sunshine on 2019/3/26.
 */

public class SPUtils {

    private static String tag = SPUtils.class.getSimpleName();
    private final static String SP_NAME = "config";
    private final static String SP_NAME_LOGIN = "loginInfo";
    private final static String COOKIE = "cookie";
    private final static String USER_NAME = "user_name";
    private final static String COMPANY_CODE = "company_code";
    private final static String USER_APP_TYPE = "user_app_type";
    private static SharedPreferences sp;

    //-------------------------------公共保存数据方法---------------------------------------

    /**
     * 保存布尔值
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveBoolean(Context context, String key, boolean value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putBoolean(key, value).commit();

    }

    /**
     * 保存字符串
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveString(Context context, String key, String value, String sp_name) {
        if (sp == null)
            sp = context.getSharedPreferences(sp_name, 0);
        sp.edit().putString(key, value).commit();

    }

    /**
     * 保存long型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveLong(Context context, String key, long value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putLong(key, value).commit();
    }

    /**
     * 保存int型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveInt(Context context, String key, int value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 保存float型
     *
     * @param context
     * @param key
     * @param value
     */
    public static void saveFloat(Context context, String key, float value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        sp.edit().putFloat(key, value).commit();
    }

    /**
     * 获取字符值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue, String sp_name) {
        if (sp == null)
            sp = context.getSharedPreferences(sp_name, 0);
        return sp.getString(key, defValue);
    }

    /**
     * 获取int值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static int getInt(Context context, String key, int defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取long值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static long getLong(Context context, String key, long defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取float值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static float getFloat(Context context, String key, float defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);

        return sp.getFloat(key, defValue);
    }

    /**
     * 获取布尔值
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static boolean getBoolean(Context context, String key,
                                     boolean defValue) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        return sp.getBoolean(key, defValue);
    }

    /**
     * @param context
     * @param key
     * @param list    保存list
     */
    public static void saveList(Context context, String key, List<String> list) {
        Set<String> stringSet = null;
        if (list != null && list.size() > 0) {
            stringSet = new HashSet<>();
            for (String value : list) {
                stringSet.add(value);
            }
        } else {
            stringSet = null;
        }
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, 0);
            sp.edit().putStringSet(key, stringSet).commit();
        } else {
            sp.edit().putStringSet(key, stringSet).commit();
        }
    }

    /**
     * @param context
     * @param key
     * @return 获取list集合
     */
    public static List<String> getList(Context context, String key) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME, 0);
        List<String> list = new ArrayList<>();
        try {
            for (String value : sp.getStringSet(key, null)) {
                list.add(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (list != null && list.size() > 0) {
            return list;
        }
        return null;
    }

    //--------------------------------------登录信息保存-----------------------------------------

    /**
     * @param context
     * @param value   保存cookie
     */
    public static void putCookie(Context context, String value) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        sp.edit().putString(COOKIE, value).commit();
    }

    /**
     * @param context
     * @return 获取cookie
     */
    public static String getCookie(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        return sp.getString(COOKIE, "");
    }

    /**
     * @param context
     * @param userName 保存用户名
     */
    public static void putUserName(Context context, String userName) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        sp.edit().putString(USER_NAME, userName).commit();
    }

    public static String getUserName(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        return sp.getString(USER_NAME, "");
    }

    /**
     * @param context
     * @param companyCode 保存企业代码
     */
    public static void putCompanyCode(Context context, String companyCode) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        sp.edit().putString(COMPANY_CODE, companyCode).commit();
    }

    public static String getCompanyCode(Context context) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        return sp.getString(COMPANY_CODE, "");
    }

    public static void putUserType(Context context, String type) {
        if (sp == null)
            sp = context.getSharedPreferences(SP_NAME_LOGIN, 0);
        if (StringUtils.isEmpty(type)) {
            sp.edit().putString(USER_APP_TYPE, "").commit();
        } else {
            sp.edit().putString(USER_APP_TYPE, type).commit();
        }
    }

    public static String getUserType(Context context) {
        try {
            return SPUtils.getString(context, USER_APP_TYPE, "", SP_NAME_LOGIN);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param context
     * @param sp_name 清除数据
     */
    public static void clear(Context context, String sp_name) {
        if (sp == null)
            sp = context.getSharedPreferences(sp_name, 0);
        sp.edit().clear().commit();
    }
}