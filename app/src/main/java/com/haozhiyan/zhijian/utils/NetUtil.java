package com.haozhiyan.zhijian.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

/**
 * @author WangZhenkai
 *         Created by WangZhenkai on 2019/3/26.
 *         AppUtils
 */
public class NetUtil {
    /**
     * WAP网络
     */
    private static final int CMWAP = 2;
    /**
     * 没有连接网络
     */
    private static final int NETWORK_MOBILE_NO = 0;
    /**
     * 无线网络
     */
    private static final int NETWORK_WIFI = 1;
    /**
     * NET网络
     */
    private static final int CMNET = 3;

    /**
     * 判断当前是否有可用的网络以及网络类型 0：无网络 1：WIFI 2：CMWAP 3：CMNET
     *
     * @param context
     * @return
     */
    public static int isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return NETWORK_MOBILE_NO;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        NetworkInfo netWorkInfo = info[i];
                        if (netWorkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            return NETWORK_WIFI;
                        } else if (netWorkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                            String extraInfo = netWorkInfo.getExtraInfo();
                            if ("cmwap".equalsIgnoreCase(extraInfo)
                                    || "cmwap:gsm".equalsIgnoreCase(extraInfo)) {
                                return CMWAP;
                            }
                            return CMNET;
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }


    public static void TiShiNetStatus(Activity context) {
        if (isNetworkAvailable(context) == 0) {//无网络
            new AlertDialog.Builder(context)
                    .setMessage("没有网络,请检查网络")
                    .setTitle("工程云提醒")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else if (isNetworkAvailable(context) == 1) {//wifi网络
            if (!isWifiConnected(context)) {
                ToastUtils.myToast(context, "无线网络状况差或不可用");
            }
        } else {//移动网络
            if (isNetworkAvailable(context) == 2) {
                if (!isMobileConnected(context)) {
                    ToastUtils.myToast(context, "移动网络状况差或不可用");
                }
            } else if (isNetworkAvailable(context) == 3) {
                if (!isMobileConnected(context)) {
                    ToastUtils.myToast(context, "移动网络状况差或不可用");
                }
            }
        }
    }
}

