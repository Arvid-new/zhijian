package com.haozhiyan.zhijian.utils;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class JsonToBean<T> {


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
