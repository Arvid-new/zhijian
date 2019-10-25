package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.model.ACache;

/**
 * Create by Wangzhenkai at 2018.8.8
 */
public class UiUtils {

    public static void setTextMargin(TextView textView, int left, int top, int right, int bottom) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(left, top, right, bottom);
        textView.setLayoutParams(lp);
    }

    public static String getContent(TextView textView) {
        String content;
        if (textView != null) {
            content = textView.getText().toString().trim();
        } else {
            content = "";
        }
        return content;
    }

    public static String getContent(EditText editText) {
        String content;
        if (editText != null) {
            content = editText.getText().toString().trim();
        } else {
            content = "";
        }
        return content;
    }


    public static Context getContext() {
        return BaseActivity.getForegroundActivity();
    }

    public static Thread getMainThread() {
        return Thread.currentThread();
    }

    public static long getMainThreadId() {
        return android.os.Process.myTid();
    }

    /**
     * dip转换px
     */
    public static int dip2px(int dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * pxz转换dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取主线程的handler
     */
    public static Handler getHandler() {
        return new Handler();
    }

    /**
     * 延时在主线程执行runnable
     */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /**
     * 在主线程执行runnable
     */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /**
     * 从主线程looper里面移除runnable
     */
    public static void removeCallbacks(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    public static String getString(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        return str;
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return getResources().getDrawable(resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }

    //判断当前的线程是不是在主线程
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            post(runnable);
        }
    }

    public static void startActivity(Intent intent) {
        BaseActivity activity = BaseActivity.getForegroundActivity();
        if (activity != null) {
            activity.startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(getString(resId));
    }

    /**
     * 对toast的简易封装。线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (isRunInMainThread()) {
            showToast(str);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static void showToast(String str) {
        BaseActivity frontActivity = BaseActivity.getForegroundActivity();
        if (frontActivity != null) {
            ToastUtils.myToast(frontActivity, str);
        }
    }


    /**
     * 银行卡四位加空格
     *
     * @param mEditText
     */
    public static void bankCardNumSplit(final EditText mEditText) {
        mEditText.addTextChangedListener(new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = mEditText.getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                    mEditText.setText(str);
                    Editable etable = mEditText.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    public static void setTabLayout(int index, TextView tv1, TextView tv2, TextView tv3, TextView tv4, View view01, View view02, View view03, View view04) {
        if (index == 0) {
            tv1.setTextColor(Color.parseColor("#6298FC"));
            tv2.setTextColor(Color.parseColor("#989898"));
            tv3.setTextColor(Color.parseColor("#989898"));
            tv4.setTextColor(Color.parseColor("#989898"));
            view01.setVisibility(View.VISIBLE);
            view02.setVisibility(View.INVISIBLE);
            view03.setVisibility(View.INVISIBLE);
            view04.setVisibility(View.INVISIBLE);
        } else if (index == 1) {
            tv1.setTextColor(Color.parseColor("#989898"));
            tv2.setTextColor(Color.parseColor("#6298FC"));
            tv3.setTextColor(Color.parseColor("#989898"));
            tv4.setTextColor(Color.parseColor("#989898"));
            view01.setVisibility(View.INVISIBLE);
            view02.setVisibility(View.VISIBLE);
            view03.setVisibility(View.INVISIBLE);
            view04.setVisibility(View.INVISIBLE);
        } else if (index == 2) {
            tv1.setTextColor(Color.parseColor("#989898"));
            tv2.setTextColor(Color.parseColor("#989898"));
            tv3.setTextColor(Color.parseColor("#6298FC"));
            tv4.setTextColor(Color.parseColor("#989898"));
            view01.setVisibility(View.INVISIBLE);
            view02.setVisibility(View.INVISIBLE);
            view03.setVisibility(View.VISIBLE);
            view04.setVisibility(View.INVISIBLE);
        } else if (index == 3) {
            tv1.setTextColor(Color.parseColor("#989898"));
            tv2.setTextColor(Color.parseColor("#989898"));
            tv3.setTextColor(Color.parseColor("#989898"));
            tv4.setTextColor(Color.parseColor("#6298FC"));
            view01.setVisibility(View.INVISIBLE);
            view02.setVisibility(View.INVISIBLE);
            view03.setVisibility(View.INVISIBLE);
            view04.setVisibility(View.VISIBLE);
        }
    }

    public static void setBtnStatus(int index, TextView tv1, TextView tv2, TextView tv3) {

        if (index == 1) {
            tv1.setSelected(true);
            tv1.setTextColor(getColor(R.color.white));
            tv2.setSelected(false);
            tv2.setTextColor(getColor(R.color.black_3));
            tv3.setSelected(false);
            tv3.setTextColor(getColor(R.color.black_3));
        } else if (index == 2) {
            tv1.setSelected(false);
            tv1.setTextColor(getColor(R.color.black_3));
            tv2.setSelected(true);
            tv2.setTextColor(getColor(R.color.white));
            tv3.setSelected(false);
            tv3.setTextColor(getColor(R.color.black_3));
        } else if (index == 3) {
            tv1.setSelected(false);
            tv1.setTextColor(getColor(R.color.black_3));
            tv2.setSelected(false);
            tv2.setTextColor(getColor(R.color.black_3));
            tv3.setSelected(true);
            tv3.setTextColor(getColor(R.color.white));
        } else if (index == 4) {
            tv1.setSelected(false);
            tv1.setTextColor(getColor(R.color.black_3));
            tv2.setSelected(false);
            tv2.setTextColor(getColor(R.color.black_3));
            tv3.setSelected(false);
            tv3.setTextColor(getColor(R.color.black_3));
        }
    }
    public static void setBtnStatus(int index, TextView tv1, TextView tv2, TextView tv3, TextView tv4) {

        if (index == 1) {
            tv1.setSelected(true);
            tv1.setTextColor(getColor(R.color.white));
            tv2.setSelected(false);
            tv2.setTextColor(getColor(R.color.black_3));
            tv3.setSelected(false);
            tv3.setTextColor(getColor(R.color.black_3));
            tv4.setSelected(false);
            tv4.setTextColor(getColor(R.color.black_3));
        } else if (index == 2) {
            tv1.setSelected(false);
            tv1.setTextColor(getColor(R.color.black_3));
            tv2.setSelected(true);
            tv2.setTextColor(getColor(R.color.white));
            tv3.setSelected(false);
            tv3.setTextColor(getColor(R.color.black_3));
            tv4.setSelected(false);
            tv4.setTextColor(getColor(R.color.black_3));
        } else if (index == 3) {
            tv1.setSelected(false);
            tv1.setTextColor(getColor(R.color.black_3));
            tv2.setSelected(false);
            tv2.setTextColor(getColor(R.color.black_3));
            tv3.setSelected(true);
            tv3.setTextColor(getColor(R.color.white));
            tv4.setSelected(false);
            tv4.setTextColor(getColor(R.color.black_3));
        } else if (index == 4) {
            tv1.setSelected(false);
            tv1.setTextColor(getColor(R.color.black_3));
            tv2.setSelected(false);
            tv2.setTextColor(getColor(R.color.black_3));
            tv3.setSelected(false);
            tv3.setTextColor(getColor(R.color.black_3));
            tv4.setSelected(true);
            tv4.setTextColor(getColor(R.color.white));
        }
    }

    public static boolean isEmpty(String value) {
        try {
            if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim()) && value.length() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static int troubleType(String state) {
        if (state.equals("待整改")) {
            return 1;
        } else if (state.equals("待复验")) {
            return 2;
        } else if (state.equals("异常关闭") || state.equals("非正常关闭")) {
            return 3;
        } else if (state.equals("已通过")) {
            return 4;
        } else {
            return 0;
        }
    }

    public static String getDanWeiName(String userType) {
        if (TextUtils.equals(userType, "0")) {
            return "Admin";
        } else if (TextUtils.equals(userType, "1")) {
            return "施工单位";
        } else if (TextUtils.equals(userType, "2")) {
            return "监理单位";
        } else if (TextUtils.equals(userType, "3")) {
            return "建设单位";
        } else {
            return "其它用户";
        }
    }
    //工序移交使用
    public static ACache getACache(){
        ACache aCache = ACache.get(MyApp.getInstance(), "GXYJ_trouble");
        return aCache;
    }
}
