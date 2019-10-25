package com.haozhiyan.zhijian.utils;

import android.util.Log;

/**
 * Create by WangZhenkai at 2019.3.26
 *
 * @desc 日志打印工具类
 */
public class LogUtils {

    /**
     * 是否是调试模式
     */

    private static final boolean IS_DEBUG = true;

    /**
     * 自定义标记
     *
     * @param tag
     * @param msg
     */

    public static void e(String tag, String msg) {

        if (IS_DEBUG) {
            Log.e(tag, msg);
        }
    }

    /**
     * 用当前类名标记
     *
     * @param cls
     * @param msg
     */

    public static void e(Class<?> cls, String msg) {
        if (IS_DEBUG) {
            Log.e("HaoZhiYan--->" + cls.getSimpleName(), msg);
        }
    }

    public static void v(String tag, String msg) {

        if (IS_DEBUG) {
            Log.v(tag, msg);
        }
    }

    public static void v(Class<?> cls, String msg) {

        if (IS_DEBUG) {
            Log.v("HaoZhiYan--->" + cls.getSimpleName(), msg);
        }
    }

    public static void i(String tag, String msg) {

        if (IS_DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(Class<?> cls, String msg) {

        if (IS_DEBUG) {
            Log.i("HaoZhiYan--->" + cls.getSimpleName(), msg);
        }
    }

    public static void d(String tag, String msg) {

        if (IS_DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void d(Class<?> cls, String msg) {

        if (IS_DEBUG) {
            Log.d("HaoZhiYan--->" + cls.getSimpleName(), msg);
        }
    }

    public static void w(String tag, String msg) {

        if (IS_DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void w(Class<?> cls, String msg) {

        if (IS_DEBUG) {
            Log.w("HaoZhiYan--->" + cls.getSimpleName(), msg);
        }
    }

    public static void print(String info){
        if (IS_DEBUG) {
            System.out.println(info);
        }
    }
    public static void print(Class<?> cls,String info){
        if (IS_DEBUG) {
            System.out.println(cls.getSimpleName()+"--->"+info);
        }
    }

    public static void logGGQ(String msg){
        if(IS_DEBUG){
            Log.i("GGQ", msg);
        }
    }
}
