package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.LoginActivity;
import com.haozhiyan.zhijian.listener.HttpFileCallBack;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.widget.LoginExceptionDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;

public class HttpRequest {

    private Context context;
    private String url;
    private HttpParams params;
    private HttpHeaders headers;
    private ACache aCache;
    private UserInfo userInfo;

    private HttpRequest(Context context) {
        this.context = context;
        try {
            aCache = ACache.get(context, "cookie");
        } catch (Exception e) {
            e.printStackTrace();
        }
        userInfo = UserInfo.create(context);
    }

    public static HttpRequest get(Context context) {
        HttpRequest request = new HttpRequest(context);
        return request;
    }

    public HttpRequest url(String url) {
        this.url = url;
        return this;
    }

    /**
     * 传参
     *
     * @param key   键值
     * @param value
     * @return
     */
    public HttpRequest params(String key, String value) {
        if (params == null) {
            params = new HttpParams();
        }
        if (!TextUtils.isEmpty(value))
            params.put(key, value);
        return this;
    }

    /**
     * 传参
     *
     * @param key   键值
     * @param value
     * @return
     */
    public HttpRequest params(String key, String value, boolean emptyEnable) {
        if (params == null) {
            params = new HttpParams();
        }
        if (!emptyEnable) {
            if (!StringUtils.isEmpty(value))
                params.put(key, value);
        } else {
            params.put(key, value);
        }

        return this;
    }

    public HttpRequest params(HttpParams params) {
        if (this.params == null) {
            this.params = new HttpParams();
        }
        this.params.put(params);
        return this;
    }

    public HttpRequest params(Map<String, String> map) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(map);
        return this;
    }

    public HttpRequest params(String key, int value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        LogUtils.print("key==" + key + "===" + value);
        return this;
    }

    public HttpRequest params(String key, float value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public HttpRequest params(String key, long value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public HttpRequest params(String key, double value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public HttpRequest params(String key, boolean value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public HttpRequest params(String key, @NonNull File value) {
        if (params == null) {
            params = new HttpParams();
        }
        params.put(key, value);
        return this;
    }

    public HttpRequest params(String key, @NonNull File value, String fileName, MediaType mediaType) {
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
    public HttpRequest headers(String key, String value) {
        if (headers == null) {
            headers = new HttpHeaders();
        }
        headers.put(key, value);
        return this;
    }

    /**
     * 执行普通的get请求 返回 string
     *
     * @param
     * @param mCallBack 回调结果
     */

    public void doGet(final HttpStringCallBack mCallBack) {
        try {
            if (params == null) {
                params = new HttpParams();
            }
            if (headers == null) {
                headers = new HttpHeaders();
            }
            headers.put("Cookie", aCache.getAsString("cookie"));
            LogUtils.print("doGet传递的-Cookie==" + headers.get("Cookie"));
            OkGo.<String>get(url).tag(context)
                    .cacheKey(url + params.toString())
                    .params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        if (mCallBack != null)
                            mCallBack.onSuccess(body);
                    }
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (mCallBack != null)
                        mCallBack.onSuccess(body);
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        if (mCallBack != null)
                            mCallBack.onFailure(response.code(), "" + response.message());
                    }
                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(0, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * post 请求 返回string
     *
     * @param mCallBack
     */
    public void doPost(@NonNull final HttpStringCallBack mCallBack) {
        try {
            if (params == null) {
                params = new HttpParams();
            }
            if (headers == null) {
                headers = new HttpHeaders();
            }
            try {
                headers.put("Cookie", aCache.getAsString("cookie"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            OkGo.<String>post(url).tag(context)
                    .cacheKey(url + params.toString())
                    .params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        if (mCallBack != null)
                            mCallBack.onSuccess(body);
                    }
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (mCallBack != null)
                        mCallBack.onSuccess(body);
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        if (mCallBack != null)
                            mCallBack.onFailure(response.code(), response.message());
                    }
                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(0, "500" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * post 请求 返回string
     *
     * @param mCallBack
     */
    public void doPostLogin(@NonNull final HttpStringCallBack mCallBack) {
        try {
            if (params == null) {
                params = new HttpParams();
            }
            if (headers == null) {
                headers = new HttpHeaders();
            }
            OkGo.<String>post(url).tag(context)
                    .params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        String body = response.body();
                        String header = response.headers().get("Set-Cookie");
                        if (header != null) {
                            String cookies[] = header.split(";");
                            String cookie = cookies[0].replace("JSESSIONID=", "");
                            aCache.put("cookie", header);
                            SPUtils.putCookie(context, header);
                            LogUtils.print("Login-Cookie11==" + header);
                            LogUtils.print("保存的-Cookie22==" + cookie);
                        }
                        if (!TextUtils.isEmpty(body)) {
                            body = body.replace("\"success\"", "\"成功\"");
                        }
                        if (remindLoginException(body)) {
                            return;
                        } else {
                            if (mCallBack != null)
                                mCallBack.onSuccess(body);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        if (mCallBack != null)
                            mCallBack.onFailure(response.code(), response.message());
                    }
                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(0, "500" + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * 执行普通的get请求 返回 bean
     *
     * @param
     * @param mCallBack 回调结果
     */

    public <T> void doGet(@NonNull final HttpObjectCallBack<T> mCallBack) {
        try {
            if (params == null) {
                params = new HttpParams();
            }
            if (headers == null) {
                headers = new HttpHeaders();
            }
            try {
                headers.put("Cookie", aCache.getAsString("cookie"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            OkGo.<String>get(url).tag(context)
                    .cacheKey(url + params.toString())
                    .params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (remindLoginException(body)) {
                        return;
                    } else {
                        if (mCallBack != null)
                            mCallBack.JsonToBean(body);
                    }
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    String body = response.body();
                    if (!TextUtils.isEmpty(body)) {
                        body = body.replace("\"success\"", "\"成功\"");
                    }
                    if (mCallBack != null)
                        mCallBack.JsonToBean(body);
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    if (mCallBack != null)
                        mCallBack.onFailure(response.code(), "" + response.message());

                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(0, "" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * post 请求 返回  bean
     *
     * @param mCallBack
     */
    public <T> void doPost(@NonNull final HttpObjectCallBack<T> mCallBack) {
        try {
            if (params == null) {
                params = new HttpParams();
            }
            if (headers == null) {
                headers = new HttpHeaders();
            }
            headers.put("Cookie", aCache.getAsString("cookie"));
            OkGo.<String>post(url).tag(context)
                    .cacheKey(url + params.toString())
                    .params(params).headers(headers).execute(new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        String body = response.body();
                        if (!TextUtils.isEmpty(body)) {
                            body = body.replace("\"success\"", "\"成功\"");
                        }
                        if (remindLoginException(body)) {
                            return;
                        } else {
                            if (mCallBack != null)
                                mCallBack.JsonToBean(body);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        CrashReport.postCatchedException(e);
                    }
                }

                @Override
                public void onCacheSuccess(Response<String> response) {
                    super.onCacheSuccess(response);
                    try {
                        String body = response.body();
                        if (!TextUtils.isEmpty(body)) {
                            body = body.replace("\"success\"", "\"成功\"");
                        }
                        if (mCallBack != null)
                            mCallBack.JsonToBean(body);
                    } catch (Exception e) {
                        e.printStackTrace();
                        CrashReport.postCatchedException(e);
                    }
                }

                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                    if (mCallBack != null)
                        mCallBack.onFailure(response.code(), response.message());

                }
            });
        } catch (Exception e) {
            mCallBack.onFailure(0, "500" + e.getMessage());
            e.printStackTrace();
            CrashReport.postCatchedException(e);
        }
    }

    private boolean remindLoginException(String body) {
        try {
            if (body.startsWith("<!DOCTYPE html>") || (body.contains("<html>") || body.contains("<head>") || body.contains("<body"))) {
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

    /**
     * 执行普通的文件下载请求
     *
     * @param path      文件保存路径
     * @param name      文件名称
     * @param url       文件下载地址
     * @param mCallBack 回调结果
     */
    public void doDownloadFile(String path, String name,
                               String url, final HttpFileCallBack mCallBack) {
        try {
            OkGo.<File>get((url)).tag(context).execute(new FileCallback() {
                @Override
                public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                    File body = response.body();
                    mCallBack.onSuccess(body);
                }

                @Override
                public void downloadProgress(Progress progress) {

                    /**
                     *@param fraction       下载的进度，0-1
                     *@param totalSize      总字节长度, byte
                     *@param currentSize    本次下载的大小, byte
                     *@param speed;         网速，byte/s
                     */
                    mCallBack.inProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
                }

                @Override
                public void onError(com.lzy.okgo.model.Response<File> response) {
                    super.onError(response);
                    mCallBack.onFailure(1, "接口异常" + response.code());
                }
            });

        } catch (Exception e) {
            mCallBack.onFailure(1, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 多文件上传
     *
     * @param
     * @param files 文件集合
     */
    public void formUpload(String key, List<File> files, final HttpFileCallBack mCallBack) {
//        ArrayList<File> files = new ArrayList<>();
//        if (imageItems != null && imageItems.size() > 0) {
//            for (int i = 0; i < imageItems.size(); i++) {
//                files.add(new File(imageItems.get(i).path));
//            }
//        }
        if (headers == null) {
            headers = new HttpHeaders();
        }
        LogUtils.i("doPostParamsString==", params.toString());
        headers.put("Cookie", aCache.getAsString("cookie"));
        LogUtils.print("doPost传递的-Cookie==" + headers.get("Cookie"));
        //拼接参数
        OkGo.<String>post(url)//
                .tag(context)//
                .headers(headers)//
//                .headers("header2", "headerValue2")//
//                .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
//                .params("file3",new File("文件路径"))
//                .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                .addFileParams(key, files)           // 这种方式为同一个key，上传多个文件
                .execute(new StringCallback() {

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        mCallBack.onSuccess(body);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        mCallBack.onFailure(1, "接口异常" + response.code());
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
//                        String downloadLength = Formatter.formatFileSize(context, progress.currentSize);
//                        String totalLength = Formatter.formatFileSize(context, progress.totalSize);
                        /**
                         *@param fraction       下载的进度，0-1
                         *@param totalSize      总字节长度, byte
                         *@param currentSize    本次下载的大小, byte
                         *@param speed;         网速，byte/s
                         */
                        mCallBack.inProgress(progress.currentSize, progress.totalSize, progress.fraction, progress.speed);
                    }
                });
    }

//    public class MyObjectCallBack<T> extends AbsCallback<T> {
//        public Activity act;
//
//        public MyObjectCallBack(Activity act) {
//            this.act = act;
//        }
//
//        @Override
//        public void onSuccess(Response<T> response) {
//            LogUtils.i("Response<T>==", response.toString());
//        }
//
//        @Override
//        public T convertResponse(okhttp3.Response response) {
//            //获取传递过来的泛型
//            T Result = null;
//            try {
//                Type type = getClass().getGenericSuperclass();
//                Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
//                //拿到响应内容
//                String string = new StringConvert().convertResponse(response);
//                response.close();
//                LogUtils.i("beanJson(Response)==", string);
//                Result = new Gson().fromJson(string, type2);
//            } catch (Throwable throwable) {
//                throwable.printStackTrace();
//            }
//            return Result;
//        }
//
//        @Override
//        public void onError(Response<T> response) {
//            super.onError(response);
//            if (NetUtils.checkNetWork(act)) {
//                int code = 0;
//                try {
//                    code = response.code();
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//                if (code == 404) {
//                    ToastUtils.myToast(act, "404 当前链接不存在或服务器异常");
//                } else {
//                    ToastUtils.myToast(act, response.code()+":"+response.message());
//                }
//            } else {
//                ToastUtils.myToast(act, "无网络连接,请检查网络");
//            }
//        }
//    }


//    private T parseString2List(String json, Class clazz) {
//        Type type = new ParameterizedTypeImpl(clazz);
//        Type type1 = ((ParameterizedTypeImpl) type).getActualTypeArguments()[0];
//        T list = new Gson().fromJson(json, type1);
//        return list;
//    }

    private class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
