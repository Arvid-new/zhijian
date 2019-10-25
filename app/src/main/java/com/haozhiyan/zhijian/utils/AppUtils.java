package com.haozhiyan.zhijian.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.BuildConfig;
import com.lzy.okgo.model.HttpHeaders;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author WangZhenkai
 *         Created by WangZhenkai on 2019/3/26.
 *         AppUtils
 */
public class AppUtils {

    private static URL url;
    private static HttpURLConnection con;
    private static int state = -1;

    /**
     * 判断应用是否正在运行
     *
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isRunning(Context context, String packageName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : list) {
            String processName = appProcess.processName;
            if (processName != null && processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return display.getRotation();返回0或1，0表示竖屏，1表示横屏
     */
    public static int getScreenWidth(Activity context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();

        return width;
    }

    /**
     * 获取屏幕宽度-像素
     *
     * @param context
     * @return
     */
    public static int getScreenWidthPixels(Activity context) {
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenheight(Activity context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 生成32位编码
     *
     * @return string
     */
    public static String getUUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        return uuid;
    }

    //判断应用App是否安装
    public static boolean isPkgInstalled(String pkgName, Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(pkgName, 0);
        } catch (Exception e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;

        } else {
            return true;
        }
    }

    /**
     * 功能：检测当前URL是否可连接或是否有效,
     * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
     *
     * @param urlStr 指定URL网络地址
     * @return URL
     */
    public static synchronized boolean isConnect(String urlStr) {
        int counts = 0;
        boolean isCanUse = false;
        if (urlStr == null || urlStr.length() <= 0) {
            return false;
        }
        while (counts < 5) {
            try {
                url = new URL(urlStr);
                con = (HttpURLConnection) url.openConnection();
                state = con.getResponseCode();
                System.out.println(counts + "= " + state);
                if (state == 200) {
                    System.out.println("URL可用！");
                    return true;
                }
                break;
            } catch (Exception ex) {
                counts++;
                System.out.println("URL不可用，连接第 " + counts + " 次");
                urlStr = null;
                continue;
            }
        }
        return isCanUse;
    }

    /**
     * 判断是否安装了微信客户端
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

//    /**
//     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
//     * @param listView
//     */
//    public static void setListViewHeight(ListView listView) {
//
//        // 获取ListView对应的Adapter
//
//        IListAdapter listAdapter = listView.getAdapter();
//
//        if (listAdapter == null) {
//            return;
//        }
//        int totalHeight = 0;
//        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
//            View listItem = listAdapter.getView(i, null, listView);
//            listItem.measure(0, 0); // 计算子项View 的宽高
//            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
//        }
//
//        ViewGroup.LayoutParams params = listView.getLayoutParams();
//        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
//        listView.setLayoutParams(params);
//    }

    /**
     * 设置时间
     *
     * @param view
     */
    @SuppressLint("SimpleDateFormat")
    public static void setRefreshTime(TextView view) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日   HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());
        // 获取当前时间
        String str = formatter.format(curDate);
        view.setText(str);
    }

    /**
     * 获取想要的当前的自定义的时间和格式
     *
     * @param formatChar
     * @return yyyy年MM月dd日 HH:mm:ss
     */
    public static String getSystemTime(String formatChar) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatChar);
        Date curDate = new Date(System.currentTimeMillis());
        //获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    public static String getSystemDate(String formatChar) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatChar);
        //获取当前日期
        String str = formatter.format(new Date());
        return str;
    }

    //读取assets文件夹下文件
    String Result = "";

    public String getFromAssets(Context ctx, String fileName) {

        try {
            InputStreamReader inputReader = new InputStreamReader(ctx.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result;
    }

    public String getDataFromAssets(Context ctx, String fileName) {

        InputStream inputStream = null;
        try {
            InputStreamReader inputReader = new InputStreamReader(ctx.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result += line;
                inputStream.read();
            }

            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Result;
    }

    /**
     * 判断SDCard是否存在 [当没有外挂SD卡时，内置ROM也被识别为存在sd卡]
     *
     * @return
     */
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录路径
     *
     * @return
     */
    public static String getSdCardPath() {
        boolean exist = isSdCardExist();
        String sdpath = "";
        if (exist) {
            sdpath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
        } else {
            sdpath = "不适用";
        }
        return sdpath;

    }

    /**
     * 判断是否是闰年
     */
    public static boolean isLeapYear(String str) {
        int year = Integer.parseInt(str);
        return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
    }

    public static int getXY(LinearLayout linearLayout, String xy) {
        //数组长度必须为2
        int[] locations = new int[2];
        linearLayout.getLocationOnScreen(locations);
        int x = locations[0];//获取组件当前位置的横坐标
        int y = locations[1];//获取组件当前位置的纵坐标
        if (xy.equals("X")) {
            return x;
        } else if (xy.equals("y")) {
            return y;
        }
        return 0;
    }

    /**
     * @param context
     * 添加请求头部
     */
    public static HttpHeaders header(Context context){
        String token = SPUtils.getString(context,"token","","user");
        HttpHeaders headers = new HttpHeaders();
        headers.put("Authorization",token);
        return headers;
    }
    public static void openAPK(String fileSavePath, Activity ctx) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是Android N以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(ctx, BuildConfig.APPLICATION_ID + ".fileProvider", new File("file://" + fileSavePath));
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.parse("file://" + fileSavePath), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        ctx.startActivity(intent);
    }
}