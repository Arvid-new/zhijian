package com.haozhiyan.zhijian.bean;

import com.alibaba.fastjson.JSON;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

public class LocalMediaListConvert implements PropertyConverter<List<LocalMedia>, String> {
    @Override
    public List<LocalMedia> convertToEntityProperty(String databaseValue) {
        return JSON.parseArray(databaseValue, LocalMedia.class);
    }
    @Override
    public String convertToDatabaseValue(List<LocalMedia> entityProperty) {
        return JSON.toJSONString(entityProperty);
    }

}
