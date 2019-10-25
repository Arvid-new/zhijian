package com.haozhiyan.zhijian.model;

import com.lzy.okgo.model.HttpParams;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by WangZhenKai on 2019/5/28.
 * Describe: Ydzj_project
 */
public class RequestParams extends HttpParams implements Serializable {

    public LinkedHashMap<String, String> paramsMap;

    public RequestParams() {
        init();
    }

    public RequestParams(String key, String value) {
        init();
        put(key, value, IS_REPLACE);
    }

    private void init() {
        paramsMap = new LinkedHashMap<>();
    }

    public String toJsonString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : paramsMap.entrySet()) {
            if (result.length() > 0)
                result.append("{").append('"');
            result.append(entry.getKey()).append('"').append(":").append(entry.getValue()).append("}");
        }
        return result.toString();
    }
}
