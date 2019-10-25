package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.LoginActivity;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.MyObjectCallBack;
import com.haozhiyan.zhijian.listener.MyStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.widget.LoginExceptionDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;

/**
 * Create by WangZhenKai at 2019/4/11
 */
public class OkGoRequest {

    private Context context;
    private String url;
    private HttpParams params;
    private HttpHeaders headers;
    private CacheMode cacheMode;
    private String cacheKey;
    private ACache aCache;
    private UserInfo userInfo;

    private OkGoRequest(Context context) {
        this.context = context;
        aCache = ACache.get(context, "cookie");
        userInfo = UserInfo.create(context);
    }

    public static OkGoRequest get(Context context) {
        OkGoRequest request = new OkGoRequest(context);
        return request;
    }

    public OkGoRequest url(String url) {
        this.url = url;
        return this;
    }

    public OkGoRequest params(String key, String value) {
        if (params == null) {
            params = new HttpParams();
        }
        if (!TextUtils.isEmpty(value))
            params.put(key, value);
        return this;
    }

    public OkGoRequest params(HttpParams params) {
        if (this.params == null) {
            this.params = new HttpParams();
        }
        this.params.put(params);
        return this;
    }

    public OkGoRequest params(Map<String, String> map) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(map);
        return this;
    }

    public OkGoRequest params(String key, int value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public OkGoRequest params(String key, float value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public OkGoRequest params(String key, long value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public OkGoRequest params(String key, double value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public OkGoRequest params(String key, boolean value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public OkGoRequest params(String key, @NonNull File value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public OkGoRequest params(String key, @NonNull File value, String fileName, MediaType mediaType) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value, fileName, mediaType);
        return this;
    }

    /**
     * 添加头部
     *
     * @param key
     * @param value
     * @return
     */
    public OkGoRequest headers(String key, String value) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put(key, value);
        return this;
    }

    public OkGoRequest cacheModel(CacheMode model) {
        cacheMode = model;
        return this;
    }

    public OkGoRequest cacheKey(String cache) {
        if (StringUtils.isEmpty(cacheKey)) {
            cacheKey = cache;
        } else {
            cacheKey = "";
        }
        return this;
    }

    //get请求返回String类型json
    public void doGet(final HttpStringCallBack mCallBack) {
        try {
            if (headers == null) {
                headers = new HttpHeaders();
            }
            headers.put("Cookie", aCache.getAsString("cookie"));
            LogUtils.print("doGet传递的-Cookie==" + headers.get("cookie"));
            if (params != null) {
                LogUtils.print("doGet传递的-params==" + params.toString());
            }
            OkGo.<String>get(url).tag(context).params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "成功");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        mCallBack.onSuccess(body);
                    }
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "成功");
                    }
                    mCallBack.onSuccess(body);
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "成功");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        mCallBack.onFailure(response.code(), "" + response.message());
                    }
                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(600, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    //post请求返回String类型json
    public void doPost(final HttpStringCallBack mCallBack) {
        if (params != null) {
            LogUtils.print("params==" + params.toString());
        }
        try {
            if (headers == null) {
                headers = new HttpHeaders();
            }
            headers.put("Cookie", aCache.getAsString("cookie"));
            LogUtils.print("doPost传递的-Cookie==" + headers.get("Cookie"));
            OkGo.<String>post(url).tag(context).params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "成功");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        mCallBack.onSuccess(body);
                    }
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "成功");
                    }
                    mCallBack.onSuccess(body);
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "成功");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        mCallBack.onFailure(response.code(), "" + response.message());
                    }
                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(600, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean remindLoginException(String body) {
        try {
            if (body.startsWith("<!DOCTYPE html>") && (body.contains("<html>") || body.contains("<head>") || body.contains("<body"))) {
                new LoginExceptionDialog(context, UiUtils.getString(R.string.login_exception_str)) {
                    @Override
                    public void ok() {
                        userInfo.putLoginStatus(false);
                        userInfo.ChangeLoginState(false);
                        ActivityManager.getInstance().clearAll();
                        context.startActivity(new Intent(context, LoginActivity.class));
                    }
                };
                return true;
            } else if (StringUtils.isEmpty(body)) {
                Toast.makeText(context, "服务器数据错误", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    //无缓存Get请求
    public <T> void doGetRequest(MyObjectCallBack<T> callback) {
        if (params != null) {
            LogUtils.print("params==" + params.toString());
        }
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put("Cookie", aCache.getAsString("cookie"));
        OkGo.<T>get(url)
                .headers(headers)
                .tag(context)
                .params(params)
                .execute(callback);
    }

    //有缓存Get请求
    public <T> void doGetCache(MyObjectCallBack<T> callback) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put("Cookie", aCache.getAsString("cookie"));
        OkGo.<T>get(url)
                .headers(headers)
                .tag(context)
                .params(params)
                .cacheKey(cacheKey)
                .cacheMode(cacheMode)
                .execute(callback);
    }

    //无缓存Post请求
    public <T> void doPostRequest(MyObjectCallBack<T> callback) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put("Cookie", aCache.getAsString("cookie"));
        OkGo.<T>post(url)
                .headers(headers)
                .tag(context)
                .params(params)
                .execute(callback);
    }

    public void doPostRequest(final MyStringCallBack myStringCallBack) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put("Cookie", aCache.getAsString("cookie"));
        OkGo.<String>post(url)
                .headers(headers)
                .tag(context)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!TextUtils.isEmpty(body)) {
                            body = body.replace("\"success\"", "成功");
                        }
                        if (remindLoginException(body)) {
                            return;
                        } else {
                            myStringCallBack.onSuccess(response);
                        }
                    }

                    @Override
                    public void onCacheSuccess(Response<String> response) {
                        super.onCacheSuccess(response);
                        myStringCallBack.onSuccess(response);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        String body = response.body();
                        if (!TextUtils.isEmpty(body)) {
                            body = body.replace("\"success\"", "成功");
                        }
                        if (remindLoginException(body)) {
                            return;
                        } else {
                            myStringCallBack.MyOnError(response, "请求错误,请检查重试");
                        }
                    }
                });
    }

    //有缓存Post请求
    public <T> void doPostCache(MyObjectCallBack<T> callback) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put("Cookie", aCache.getAsString("cookie"));
        OkGo.<T>post(url)
                .headers(headers)
                .tag(context)
                .params(params)
                .cacheKey(cacheKey)
                .cacheMode(cacheMode)
                .execute(callback);
    }

    public void postRequest(final AbsCallback mCallBack) {
        OkGo.post(url)
                .tag(context)
                .params(params)
                .execute(mCallBack);
    }
}
