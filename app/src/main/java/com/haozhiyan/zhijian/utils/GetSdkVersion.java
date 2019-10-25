package com.haozhiyan.zhijian.utils;

import android.os.Build;

/**
 * Created by WangZhenKai on 2017/8/16.
 * 获取应用的SDK版本号
 */
public class GetSdkVersion {

    public static int Version() {
        int osVersion = Build.VERSION.SDK_INT;
        return osVersion;
    }

    public static int sdkVersion() {
        int osVersion;
        try {
            osVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            return 0;
        }
        return osVersion;
    }
}
