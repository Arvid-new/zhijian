package com.haozhiyan.zhijian.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.haozhiyan.zhijian.R;

import java.net.SocketException;
import java.net.SocketTimeoutException;

import okhttp3.Response;

/**
 * Create by WangZhenKai at 2018/8/10 0010
 */
public class ToastUtils {
    private static Toast toast;

    public static void myToast(Context context, String toastStr) {
        try {
            if (toast == null) {
                toast = new Toast(context);
            }
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LinearLayout toastLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.toast_view, null);
            TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
            txtToast.setText(toastStr);
            toast.setView(toastLayout);
        } catch (Exception e) {

        }
        try {
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void myToast(Activity activity, String toastStr) {
        try {
            if (toast == null) {
                toast = new Toast(activity);
            }
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LayoutInflater inflater = activity.getLayoutInflater();
            LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast_view, null);
            TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
            txtToast.setText(toastStr);
            toast.setView(toastLayout);
        } catch (Exception e) {

        }
        try {
            toast.show();
        } catch (Exception e) { }
    }

    public static void myToast(Activity activity, int toastStr) {
        if (toast == null) {
            toast = new Toast(activity);
        }
        try {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            LayoutInflater inflater = activity.getLayoutInflater();
            LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast_view, null);
            TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
            txtToast.setText(toastStr);
            toast.setView(toastLayout);
            toast.show();
        } catch (Exception e) {

        }
    }

    public static void longToast(Activity activity, int toastStr) {
        if (toast == null) {
            toast = new Toast(activity);
        }
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LayoutInflater inflater = activity.getLayoutInflater();
        LinearLayout toastLayout = (LinearLayout) inflater.inflate(R.layout.toast_view, null);
        TextView txtToast = (TextView) toastLayout.findViewById(R.id.txt_toast);
        txtToast.setText(toastStr);
        toast.setView(toastLayout);
        toast.show();
    }

    public static void serverMsg(Activity ctx, Exception e, Response response, String errorMsg) {
        if (NetUtils.checkNetWork(ctx)) {
            int code = 0;
            try {
                if (response != null)
                    code = response.code();
                else
                    code = 500;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            if (code == 404) {
                myToast(ctx, "404 当前链接不存在或服务器异常");
            } else if (code == 500) {
                myToast(ctx, "服务器异常");
            } else {
                if (e instanceof SocketTimeoutException) {
                    myToast(ctx, "请求超时");
                } else if (e instanceof SocketException) {
                    myToast(ctx, "服务器异常");
                } else {
                    String errMsg = errorMsg == null ? "" : "_" + errorMsg;
                    myToast(ctx, "error" + e.getMessage() + errMsg);
                }
            }
        } else {
            myToast(ctx, "无网络连接,请检查网络");
        }
    }
}
