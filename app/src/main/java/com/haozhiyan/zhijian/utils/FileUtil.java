/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.haozhiyan.zhijian.utils;

import android.content.Context;

import java.io.File;

/**
 * @author wangzhenkai
 *         文件保存
 */
public class FileUtil {

    public static File getSaveFile(Context context) {
        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;
    }
    /**
     * 判断文件是否存在
     * @param path 文件的路径
     * @return
     */
    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
