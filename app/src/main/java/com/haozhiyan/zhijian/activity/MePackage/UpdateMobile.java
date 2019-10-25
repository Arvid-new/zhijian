package com.haozhiyan.zhijian.activity.MePackage;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdateMobile extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_yuan_mobile)
    TextView tvYuanMobile;
    @ViewInject(R.id.et_oldPass)
    EditText etOldPass;
    @ViewInject(R.id.et_newMobile)
    EditText etNewMobile;
    @ViewInject(R.id.btn_update)
    Button btnUupdate;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_update_mobile;
    }

    @Override
    protected void initView() {
        tv_title.setText("修改手机号");
    }

    @Override
    protected void initListener() {
        if (StringUtils.isEmpty(userInfo.getMobile())) {
            tvYuanMobile.setVisibility(View.GONE);
        } else {
            tvYuanMobile.setVisibility(View.VISIBLE);
            tvYuanMobile.setText("原手机号：" + userInfo.getMobile());
        }
    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back, R.id.btn_update})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.btn_update:
                if (TextUtils.equals("", UiUtils.getContent(etNewMobile))) {
                    ToastUtils.myToast(act, "请输入新手机号");
                } else {
                    updateInfo(UiUtils.getContent(etNewMobile));
                }
                break;
            default:
                break;
        }
    }

    private void updateInfo(final String mobile) {
        showLoadView("提交修改...");
        HttpRequest.get(this).url(ServerInterface.update)
                .params("userId", userInfo.getUserId())
                .params("mobile", mobile)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject userObject = new JSONObject(result.toString());
                            if (userObject.optInt("code") == 0) {
                                ToastUtils.myToast(act, "修改成功");
                                Intent intent = new Intent(act, PersonalInfoActivity.class);
                                intent.putExtra("mobile", mobile);
                                setResult(5026, intent);
                                ActivityManager.getInstance().removeActivity(act);
                            } else {
                                ToastUtils.myToast(act, userObject.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                        ToastUtils.myToast(act, msg);
                    }
                });
    }
}
