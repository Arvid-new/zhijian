package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ZanCunUtil<T> {
    private Context context;

    private ZanCunUtil(Context context) {
        this.context = context;
    }

    public static ZanCunUtil creat(Context context) {
        return new ZanCunUtil(context);
    }

    public void save(String key, T a) {
        Gson gson = new Gson();
        String value = gson.toJson(a);
        SPUtil.get(context).save(key, value);
    }
    public void remove(String key) {
        SPUtil.get(context).remove(key);
    }

    public T get(String key, Class a) {
        String value = SPUtil.get(context).get(key);

        if (!TextUtils.isEmpty(value)) {
            if (value.startsWith("{") && value.endsWith("}")) {
                return parseString2List(value, a);
            } else if (value.startsWith("[") && value.endsWith("]")) {
                return getArr(value, a).get(0);
            }
        }
        return parseString2List(value, a);
    }

    public List<T> getArr(String key, Class a) {
        String value = SPUtil.get(context).get(key);
        if (!TextUtils.isEmpty(value)) {
            List<T> ts = new ArrayList<>();
            if (value.startsWith("{") && value.endsWith("}")) {
                T va = parseString2List(value, a);
                ts.add(va);
            } else if (value.startsWith("[") && value.endsWith("]")) {
                try {
                    JSONArray array = new JSONArray(value);
                    for (int i = 0; i < array.length(); i++) {
                        String vaSt = array.getString(i);
                        T va = parseString2List(vaSt, a);
                        ts.add(va);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return ts;
        } else {
            return null;
        }
    }

    private T parseString2List(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        Type type1 = ((ParameterizedTypeImpl) type).getActualTypeArguments()[0];
        T list = new Gson().fromJson(json, type1);
        return list;
    }

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
