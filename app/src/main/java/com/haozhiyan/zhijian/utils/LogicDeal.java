package com.haozhiyan.zhijian.utils;

import android.app.Activity;
import android.text.TextUtils;

/**
 * Created by WangZhenKai on 2019/3/27.
 * Describe:
 */
public class LogicDeal {
    public static LogicDeal logicDeal;
    private static Activity act;

    public static LogicDeal getInstence(Activity actx) {
        if (logicDeal == null) {
            logicDeal = new LogicDeal();
        }
        act = actx;
        return logicDeal;
    }

    /**
     * 判断当前输入框有输入,并且输入的是手机号码则返回true
     *
     * @param number
     * @param isShowNullMessage
     * @param isShowErrorMessage
     * @return
     */
    public boolean isPhoneNumber(String number, boolean isShowNullMessage, boolean isShowErrorMessage) {

        boolean isReal = false;
        if (TextUtils.isEmpty(number)) {//说明手机号码没有填写
            if (isShowNullMessage) {
                ToastUtils.myToast(act, "请输入手机号码");
                return false;
            }
        } else {
            if (StringUtils.checkPhoneNumber(number)) {//说明是手机号码
                isReal = true;
            } else {
                if (isShowErrorMessage) {
                    ToastUtils.myToast(act, "请输入正确的手机号码");
                }
                isReal = false;
            }
        }
        return isReal;
    }

    /**
     * 判断当前输入框有输入,并且输入的验证码正确则返回true
     *
     * @param smsStr
     * @param isShowNullMessage
     * @param isShowErrorMessage
     * @return
     */
    public boolean isSmsNumber(String smsStr, boolean isShowNullMessage, boolean isShowErrorMessage) {
        if (TextUtils.isEmpty(smsStr)) {//手机号码和验证码都不为空的时候
            if (isShowNullMessage) {
                ToastUtils.myToast(act, "请输入手机验证码");
            }
            return false;
        } else {
            if (smsStr.length() < 6 || smsStr.length() > 6) {
                if (isShowErrorMessage) {
                    ToastUtils.myToast(act, "验证码只能是6位");
                }
                return false;
            } else {
                return true;
            }
        }
    }

    /**
     * 判断当前输入框有输入,并且输入的验证码正确则返回true
     *
     * @param pwd
     * @param isShowNullMessage
     * @param isShowErrorMessage
     * @return
     */
    public boolean isPassword(String pwd, boolean isShowNullMessage, boolean isShowErrorMessage) {
        boolean isFlags = false;
        if (TextUtils.isEmpty(pwd)) {//手机号码和验证码都不为空的时候
            if (isShowNullMessage) {
                ToastUtils.myToast(act, "请输入密码");
            }
            return false;
        } else {
            //纯数字或纯字母
            if (StringUtils.isNumberOrLetter(pwd)) {
                isFlags = true;
            } else if (StringUtils.isNumberLetter(pwd)) {
                isFlags = true;
            } else {
                if (isShowErrorMessage) {
                    ToastUtils.myToast(act, "密码格式错误,仅支持数字字母");
                }
                isFlags = false;
            }
        }
        return isFlags;
    }
}
