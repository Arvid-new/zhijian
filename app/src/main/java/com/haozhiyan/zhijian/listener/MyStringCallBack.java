package com.haozhiyan.zhijian.listener;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;

import okhttp3.Response;

/**
 * Create by WangZhenKai at 2019/1/8 0008
 */
public abstract class MyStringCallBack extends AbsCallback<String> {

    private String mS;

    @Override
    public String convertResponse(Response response) {
        try {
            mS = new StringConvert().convertResponse(response);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        response.close();
        return mS;
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<String> response) {
        super.onError(response);
        MyOnError(response,mS);
    }
    public abstract void MyOnError(com.lzy.okgo.model.Response response,String errorMsg);
}
