package com.haozhiyan.zhijian.utils;

import com.haozhiyan.zhijian.listener.MyObjectCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.HttpParams;

/**
 * Create by WangZhenKai at 2019/3/26
 */
public class OkGoUtil {

    /**
     * 无缓存Get请求
     * 自定义返回类型
     *
     * @param url      请求接口
     * @param tag      标识tag
     * @param map      参数
     * @param callback 封装回调
     * @param <T>
     */
    public static <T> void getRequets(String url, Object tag, HttpParams map, MyObjectCallBack<T> callback) {
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }

//    /**
//     * 无缓存Get请求
//     * 无自定义返回类型
//     *
//     * @param url
//     * @param tag
//     * @param map
//     * @param callback
//     * @param <T>
//     */
//    public static <T> void getRequets(String url, Object tag, HttpParams map, MyStringCallBack callback) {
//        OkGo.<T>get(url)
//                .tag(tag)
//                .params(map)
//                .execute(callback);
//    }

    /**
     * 有缓存Get请求
     *
     * @param url       请求接口
     * @param tag       标识tag
     * @param map       参数
     * @param cache     缓存键值key
     * @param cacheMode 缓存模式
     * @param callback  封装回调
     * @param <T>
     */
    public static <T> void getRequets(String url, Object tag, HttpParams map, String cache, CacheMode cacheMode, MyObjectCallBack<T> callback) {
        OkGo.<T>get(url)
                .tag(tag)
                .params(map)
                .cacheKey(cache)
                .cacheMode(cacheMode)
                .execute(callback);
    }

//    /**
//     * 有缓存Get请求
//     *
//     * @param url
//     * @param tag
//     * @param map
//     * @param cache
//     * @param cacheMode
//     * @param callback
//     * @param <T>
//     */
//    public static <T> void getRequets(String url, Object tag, HttpParams map, String cache, CacheMode cacheMode, MyStringCallBack callback) {
//        OkGo.<T>get(url)
//                .tag(tag)
//                .params(map)
//                .cacheKey(cache)
//                .cacheMode(cacheMode)
//                .execute(callback);
//    }

    /**
     * 有缓存Post请求
     *
     * @param url       请求接口
     * @param tag       标识tag
     * @param map       参数
     * @param cache     缓存键值key
     * @param cacheMode 缓存模式
     * @param callback  封装回调
     * @param <T>
     */
    public static <T> void postRequest(String url, Object tag, HttpParams map, String cache, CacheMode cacheMode, MyObjectCallBack<T> callback) {
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .cacheKey(cache)
                .cacheMode(cacheMode)
                .execute(callback);
    }

    /**
     * 无缓存Post请求
     *
     * @param url      请求接口
     * @param tag      标识tag
     * @param map      参数
     * @param callback 封装回调
     * @param <T>
     */
    public static <T> void postRequest(String url, Object tag, HttpParams map, MyObjectCallBack<T> callback) {
        OkGo.<T>post(url)
                .tag(tag)
                .params(map)
                .execute(callback);
    }
//
//    public static <T> void postRequest(String url, Object tag, HttpParams map, MyStringCallBack callback) {
//        OkGo.<T>post(url)
//                .tag(tag)
//                .params(map)
//                .execute(callback);
//    }
}
