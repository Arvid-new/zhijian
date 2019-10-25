package com.haozhiyan.zhijian.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.Toast;

import com.haozhiyan.zhijian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenkai on 2019/3/26.
 * 这个工具类，主要是获取系统的一些属性和参数
 */
public class SystemUtils {

    /**
     * 获取当前应用包名
     *
     * @param context
     * @return
     */
    public static String getPackageName(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = activityManager.getRunningTasks(1);
        ComponentName componentInfo = taskInfo.get(0).topActivity;
        //System.out.println("包名----"+componentInfo.getPackageName());
        return componentInfo.getPackageName();
    }

    /**
     * 推断当前界面是否是桌面
     */
    private boolean isHome(Activity ctx) {
        ActivityManager mActivityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes(ctx).contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获取android系统版本 如：Android5.1.1
     */
    public static String getOSVersion() {
        String release = Build.VERSION.RELEASE; // android系统版本号
        release = "Android " + release;
        return release;
    }


    /**
     * 获取Gridle配置里边 app的版本号   如 ：versionCode 1
     *
     * @return
     */
    public static String getVersionCodeStr(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        int version = 0;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);

            version = info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version + "";

    }
    public static int getVersionCode(Context context) {
        PackageManager manager = context.getPackageManager();
        PackageInfo info = null;
        int version = 0;
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);

            version = info.versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;

    }


    /**
     * 获取手机型号  如：vivo X6S A
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }


    /**
     * 获取Gridle配置里边 app版本名字    如: versionName 2.0
     *
     * @return
     */
    public static String getVersionName(Context context) {
        PackageManager manager = context.getPackageManager();
        try {
            //第二个参数代表额外的信息，例如获取当前应用中的所有的Activity
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES
            );
            ActivityInfo[] activities = packageInfo.activities;
            //showActivities(activities);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void showActivities(ActivityInfo[] activities) {
        for (ActivityInfo activity : activities) {
            //Log.i("activity=========", activity.name);
        }
    }

    /**
     * 获取系统序列号  ，手机的唯一标识
     */
    public static String getAndroidId(Context context) {
        String pValue = null;
        try {
            pValue = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } catch (Exception e) {

        }
        return pValue;
    }


    /**
     * 修改UserAgent
     *
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        //Log.e("UserAgent",sb.toString());
        return sb.toString() + "YUEBAO" + "/" + "Android" + "_" + PackageUtils.getVersionName(context);
    }

    //右侧进入显示View
    public static void showImgAnimation(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right_in);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

    //右侧退出隐藏View
    public static void hideImgAnimation(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_right_out);
        view.setVisibility(View.GONE);
        view.startAnimation(animation);
    }

    //显示View
    public static void showAnimation(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.show_anim);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

    //隐藏View
    public static void hideAnimation(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.hide_anim);
        view.setVisibility(View.GONE);
        view.startAnimation(animation);
    }

    //隐藏image的动画一种方式
    public static void HiddenView(ImageView imageView) {
        ScaleAnimation animation = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(2000);
        imageView.startAnimation(animation);
        imageView.setVisibility(View.GONE);
    }

    public static void showAnimationBottom(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_bottom_in);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }
    public static void showAnimationTop(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_open);
        view.setVisibility(View.VISIBLE);
        view.startAnimation(animation);
    }

    public static void hideAnimationBottom(Context context, View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.push_bottom_out);
        view.setVisibility(View.GONE);
        view.startAnimation(animation);
    }

    /**
     * 跳转到权限设置界面
     */
    public static void getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }

    /**
     * 获取屏幕宽度
     *
     * @param ctx
     * @return
     */
    public static int getPhoneScreenWidth(Activity ctx) {
        WindowManager manager = ctx.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        return width;
    }

    /**
     * 获取屏幕高度
     *
     * @param ctx
     * @return
     */
    public static int getPhoneScreenHight(Activity ctx) {
        WindowManager manager = ctx.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int height = outMetrics.heightPixels;
        return height;
    }

    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');  //这边+'0'，不是拼接，在Java和C#中是8+0的ASCII码得到8在ASCII中的编码值，然后通过(char)转成字符'8'
    }

    /**
     * 调用拨号界面,用户决定是否拨打
     *
     * @param phone 电话号码
     */
    public static void callPage(String phone, Context context) {
        try {
            Intent intent = null;
            if (phone != null && !phone.equals("")) {
                if (phone.contains("tel:")) {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse(phone));
                } else {
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                }
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "电话号码不能为空!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 直接拨打电话
     *
     * @param phone
     * @param context
     */
    public static void callPhone(String phone, Context context) {
        Intent intent = null;
        if (phone != null && !phone.equals("")) {
            if (phone.contains("tel:")) {
                intent = new Intent(Intent.ACTION_CALL, Uri.parse(phone));
            } else {
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "电话号码不能为空!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 调用系统邮箱发送邮件(无附件发送)
     *
     * @param email   接收人邮箱
     * @param title   邮件标题
     * @param content 邮件内容
     * @param context 上下文
     */
    public static void sendEmail(String email, String title, String content, Context context) {
        if (email.equals("")) {
            Toast.makeText(context, "没有接收人邮箱,不能发送邮件", Toast.LENGTH_SHORT).show();
        } else {
            if (title == null || title.equals("")) {
                title = "标题";
            } else if (content == null || content.equals("")) {
                content = "请编辑内容";
            }
            Intent data = new Intent((Intent.ACTION_SENDTO));
            data.setData(Uri.parse("mailto:" + email));
            data.putExtra(Intent.EXTRA_SUBJECT, title);
            data.putExtra(Intent.EXTRA_TEXT, content);
            context.startActivity(data);
        }
    }

    /**
     * 返回分享的平台名称
     *
     * @param share_media
     * @return
     */
    public static String ShareTypeName(String share_media) {

        if (share_media != null && !share_media.toString().equals("") && share_media.toString() != null) {
            if (share_media.equals("QQ")) {
                return "QQ";
            } else if (share_media.equals("QZONE")) {
                return "QQ空间";
            } else if (share_media.equals("WEIXIN")) {
                return "微信";
            } else if (share_media.equals("WEIXIN_CIRCLE")) {
                return "微信朋友圈";
            } else if (share_media.equals("WEIXIN_FAVORITE")) {
                return "微信收藏";
            } else if (share_media.equals("SINA")) {
                return "新浪微博";
            } else if (share_media.equals("SMS")) {
                return "短信";
            } else if (share_media.equals("EMAIL")) {
                return "邮箱";
            } else {
                return "";
            }
        } else {
            return null;
        }

    }

    public static String getAsciiChar(int ascllValue) {
        char c = (char) ascllValue;
        return c + "";
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包括全部包名的字符串列表
     */
    private List<String> getHomes(Activity ctx) {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = ctx.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }
}
