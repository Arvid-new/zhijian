package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Create by Wangzhenkai at 2018.8.8
 */
public class NetUtils {
    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean checkNetWork(Context context) {
        boolean flag = false;
        ConnectivityManager cwjManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cwjManager.getActiveNetworkInfo() != null) {
            flag = cwjManager.getActiveNetworkInfo().isAvailable();
            if (flag) {
                flag = true;
            } else {
                flag = false;
                Toast.makeText(context, "无网络连接,请检查网络", Toast.LENGTH_SHORT).show();
            }
        }
        return flag;
    }

}
