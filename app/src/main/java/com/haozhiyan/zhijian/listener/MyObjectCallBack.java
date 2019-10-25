package com.haozhiyan.zhijian.listener;

import android.app.Activity;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.NetUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;

/**
 * Created by WangZhenKai on 2019/5/27.
 * Describe: Ydzj_project
 */
public abstract class MyObjectCallBack<T> extends AbsCallback<T> {

    private Activity act;

    public MyObjectCallBack(Activity act) {
        this.act = act;
    }

    @Override
    public T convertResponse(Response response) {
        T result = null;
        try {
            //获取传递过来的泛型
            Type type = getClass().getGenericSuperclass();
            Type type1 = ((ParameterizedType) type).getActualTypeArguments()[0];
            //拿到响应内容
            String string = new StringConvert().convertResponse(response);
            response.close();
            LogUtils.i("beanJson(Response)==\n", string);
            result = new Gson().fromJson(string, type1);
        } catch (Exception exception) {
            LogUtils.i("JsonBack==", "请求数据->" + response.body().toString());
            LogUtils.i("JsonException==", "数据异常->" + exception.getMessage());
            LogUtils.i("JsonError==", "服务器数据错误");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return result;
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        if (NetUtils.checkNetWork(act)) {
            int code = 0;
            try {
                code = response.code();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (code == 404) {
                ToastUtils.myToast(act, "404 当前链接不存在或服务器异常");
            } else {
                ToastUtils.myToast(act, response.code() + response.message());
            }
        } else {
            ToastUtils.myToast(act, "无网络连接,请检查网络");
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();

    }
}
