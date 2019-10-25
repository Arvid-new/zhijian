package com.haozhiyan.zhijian.bean;

import java.util.List;

public class BaseBean<T> {


    public String code;
    public String msg;
    // 下面两个数据 根据实际使用 用哪个取哪个 判空取值
    public T data;//有些接口返回的是 data  jsonobject
    public List<T> list;//有些接口 返回的是 list   jsonarray
}
