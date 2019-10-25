package com.haozhiyan.zhijian.utils;

import android.app.Activity;

/**
 * Created by WangZhenKai on 2019/3/27.
 * Describe:
 */
public class VerifyUtil {
    /**
     * @param act
     * @param phone
     * @param password
     */
    public static boolean pwdLogin(final Activity act, String phone, String password) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.myToast(act, "请输入您的手机号");
            return false;
        } else {
            if (StringUtils.isEmpty(password)) {
                ToastUtils.myToast(act, "请输入您的密码");
                return false;
            } else {
                if (LogicDeal.getInstence(act).isPhoneNumber(phone, true, true)) {
                    return true;
                } else {
                    ToastUtils.myToast(act, "请输入正确的手机号");
                    return false;
                }
            }
        }
    }

    /**
     * @param act
     * @param phone
     * @param code  验证码
     * @return
     */
    public static boolean codeLogin(final Activity act, String phone, String code) {
        if (StringUtils.isEmpty(phone)) {
            ToastUtils.myToast(act, "请输入您的手机号");
            return false;
        } else {
            if (StringUtils.isEmpty(code)) {
                ToastUtils.myToast(act, "请输入验证码");
                return false;
            } else {
                if (LogicDeal.getInstence(act).isPhoneNumber(phone, true, true)) {
                    return true;
                } else {
                    ToastUtils.myToast(act, "请输入正确的手机号");
                    return false;
                }
            }
        }
    }

    public static boolean updatePass(final Activity act, String tel, String code, String pass) {
        if (StringUtils.isEmpty(tel)) {
            ToastUtils.myToast(act, "请输入您的手机号");
            return false;
        } else {
            if (StringUtils.isEmpty(code)) {
                ToastUtils.myToast(act, "请输入验证码");
                return false;
            } else {
                if (StringUtils.isEmpty(pass)) {
                    ToastUtils.myToast(act, "输入新密码");
                    return false;
                } else {
                    if (LogicDeal.getInstence(act).isPhoneNumber(tel, true, true)) {
                        return true;
                    } else {
                        ToastUtils.myToast(act, "请输入正确的手机号");
                        return false;
                    }
                }
            }
        }
    }

    public static boolean addPiCi(final Activity act, String piciName, String biaoduan, String jianChaRen, String responsiblePeople) {
        if (StringUtils.isEmpty(piciName)) {
            ToastUtils.myToast(act, "请填写批次");
            return false;
        } else {
            if (StringUtils.isEmpty(biaoduan)) {
                ToastUtils.myToast(act, "请选择标段");
                return false;
            } else {
                if (StringUtils.isEmpty(jianChaRen)) {
                    ToastUtils.myToast(act, "请选择检查人");
                    return false;
                } else {
                    if (StringUtils.isEmpty(responsiblePeople)) {
                        ToastUtils.myToast(act, "请选择负责人");
                        return false;
                    } else {
                        return true;
                    }
                }
            }
        }
    }
}
