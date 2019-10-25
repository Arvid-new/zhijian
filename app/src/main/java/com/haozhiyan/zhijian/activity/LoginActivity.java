package com.haozhiyan.zhijian.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.bean.LoginBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SPUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.model.Response;

import cn.jpush.android.api.JPushInterface;

public class LoginActivity extends BaseActivity {

    @ViewInject(R.id.et_companyCode)
    EditText etCompanyCode;
    @ViewInject(R.id.et_userName)
    EditText etUserName;
    @ViewInject(R.id.et_userPass)
    EditText etUserPass;
    @ViewInject(R.id.btn_login)
    Button btn_login;
    @ViewInject(R.id.tv_meet_problem)
    TextView tv_meet_problem;
    @ViewInject(R.id.tv_found_pass)
    TextView tv_found_pass;
    @ViewInject(R.id.iv_see_pass)
    ImageView ivSeePass;
    private ACache aCache;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        if (StringUtils.isEmpty(SPUtils.getCompanyCode(this))) {
//            etCompanyCode.setText("mienre");
        } else {
            etCompanyCode.setText(SPUtils.getCompanyCode(this));
        }
//        etUserPass.setText("123");
        etUserName.setText(SPUtils.getUserName(this));
        aCache = ACache.get(this, "cookie");
    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @Override
    protected void initListener() {

    }

    @OnClick({R.id.btn_login, R.id.tv_meet_problem, R.id.tv_found_pass, R.id.iv_see_pass})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (StringUtils.isEmpty(UiUtils.getContent(etCompanyCode))) {
                    ToastUtils.myToast(this, "请输入企业代码");
                } else {
                    if (StringUtils.isEmpty(UiUtils.getContent(etUserName))) {
                        ToastUtils.myToast(this, "请输入用户名");
                    } else {
                        if (StringUtils.isEmpty(UiUtils.getContent(etUserPass))) {
                            ToastUtils.myToast(this, "请输入登录密码");
                        } else {
                            loginAccount();
                        }
                    }
                }
                break;
            case R.id.tv_meet_problem:
//                Bundle bundle = new Bundle();
//                bundle.putString("url", "http://192.168.110.66:8080/ydzj-admin/html/scsl-app/indexIos.html?roomId=1");
//                jumpToActivity(GxYjShowH5.class);
                break;
            case R.id.tv_found_pass:
                break;
            case R.id.iv_see_pass:
                if (etUserPass.getInputType() == 128) {
                    etUserPass.setInputType(129);
                    ivSeePass.setImageResource(R.drawable.icon_close_eye);
                } else {
                    etUserPass.setInputType(128);
                    ivSeePass.setImageResource(R.drawable.icon_open_eye);
                }
                //设置光标的位置到末尾
                etUserPass.setSelection(etUserPass.getText().length());
                break;
            default:
                break;
        }
    }

    private void loginAccount() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.loginApp)
                .params("tenantCode", UiUtils.getContent(etCompanyCode))
                .params("username", UiUtils.getContent(etUserName))
                .params("password", UiUtils.getContent(etUserPass))
                .params("regId", JPushInterface.getRegistrationID(act))
                .params("versionType", "Android")
                .params("param", "8147a648488a1cf928ae9f99")//mienre
                //.params("param", "99b5febecec3003b6180343e")//hzy
                //.params("param", "ed2dcb56cd5d91174d093c56")//qianwu
                .doPostLogin(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LoginBean loginBean = new Gson().fromJson(result.toString(), LoginBean.class);
                        try {
                            CacheManager.getInstance().clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (JPushInterface.isPushStopped(MyApp.getInstance())) {
                            JPushInterface.resumePush(MyApp.getInstance());
                        }

                        hideLoadView();
                        try {
                            if (loginBean.code == 0) {
//                                getCookie(loginBean);
                                userInfo.saveUserData(loginBean);
//                                int userType = loginBean.userAppTag;
//                                userInfo.putLoginStatus(true);
//                                userInfo.putUserId(loginBean.userId);
//                                userInfo.putUserName(loginBean.peopleuser);
//                                userInfo.putUserHeader(loginBean.headPortrait);
//                                userInfo.putMobile(loginBean.mobile);
//                                userInfo.putUserAppTagSg(loginBean.userAppTagSg);
                                SPUtils.putUserName(act, UiUtils.getContent(etUserName));
                                SPUtils.putCompanyCode(act, UiUtils.getContent(etCompanyCode));
//                                if (userType == 0) {//admin
//                                    userInfo.putUserType("0");
//                                } else if (userType == 1) {//施工
//                                    userInfo.putUserType("1");
//                                } else if (userType == 2) {//监理
//                                    userInfo.putUserType("2");
//                                } else if (userType == 3) {// 建设
//                                    userInfo.putUserType("3");
//                                }
                                jumpToActivity(MainActivity.class);
                                ActivityManager.getInstance().removeActivity(act);
                            } else {
                                ToastUtils.myToast(act, loginBean.msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                        userInfo.putLoginStatus(false);
                    }
                });

    }

    private void getCookie(Response response) {
        //结果 JSESSIONID=7427269d-7fb3-48d5-a061-fead50fd006e; Path=/ydzj-admin; HttpOnly
        LogUtils.print("onSuccessSet-Cookie==" + response.headers().get("Set-Cookie"));
        String header = response.headers().get("Set-Cookie");
        if (header != null) {
            String cookies[] = header.split(";");
            String cookie = cookies[0].replace("JSESSIONID=", "");
            aCache.put("cookie", header);
            SPUtils.putCookie(this, header);
            LogUtils.print("Login-Cookie11==" + header);
            LogUtils.print("保存的-Cookie22==" + cookie);
        } else {
        }
    }
}
