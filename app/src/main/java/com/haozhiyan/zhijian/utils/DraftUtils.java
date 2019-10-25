package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/31.
 * Describe:
 */
public class DraftUtils<T> {

    private static Context ctx;
    private Class<T> entityClass;

    public DraftUtils(Class<T> entityClass, Context context) {
        this.entityClass = entityClass;
        ctx = context;
    }

    public void saveObject(String key, T infoData) {
        String json = new Gson().toJson(infoData);
        SPUtil.get(ctx).save(key, json);
        Toast.makeText(ctx, "保存成功!", Toast.LENGTH_SHORT).show();
    }

    public T getObject(String key) {
        String json = SPUtil.get(ctx).get(key);
        return new Gson().fromJson(json, entityClass);
    }

    public void saveList(String key, List<T> infoList) {

        String json = new Gson().toJson(infoList);
        SPUtil.get(ctx).save(key, json);
    }

    public JSONArray getList(String key) {
        String json = SPUtil.get(ctx).get(key);
        try {
            JSONArray array = new JSONArray(json);
            if (ListUtils.JSONArrayEmpty(array)) {
                return array;
            } else {
                return null;
            }
        } catch (JSONException e) {
            return null;
        }
    }

    public List<T> getArr(String key) {
        String value = SPUtil.get(ctx).get(key);
        if (!TextUtils.isEmpty(value)) {
            List<T> ts = new ArrayList<>();
            try {
                JSONArray array = new JSONArray(value);
                for (int i = 0; i < array.length(); i++) {
                    String vaSt = array.getString(i);
                    T va = new Gson().fromJson(vaSt, entityClass);
                    ts.add(va);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ts;
        } else {
            return null;
        }

    }
}
