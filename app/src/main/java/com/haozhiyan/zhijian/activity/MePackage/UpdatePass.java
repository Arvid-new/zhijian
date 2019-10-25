package com.haozhiyan.zhijian.activity.MePackage;

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
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

public class UpdatePass extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.et_oldPass)
    EditText etOldPass;
    @ViewInject(R.id.et_newPass)
    EditText etNewPass;
    @ViewInject(R.id.btn_update)
    Button btnUupdate;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_update_pass;
    }

    @Override
    protected void initView() {
        tv_title.setText("修改密码");
    }

    @Override
    protected void initListener() {

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
                if (TextUtils.equals("", UiUtils.getContent(etOldPass))) {
                    ToastUtils.myToast(act, "请输入旧密码");
                } else {
                    if (TextUtils.equals("", UiUtils.getContent(etNewPass))) {
                        ToastUtils.myToast(act, "请输入新密码");
                    } else {
                        updatePass();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void updatePass() {
        showLoadView("提交修改...");
        HttpRequest.get(this).url(ServerInterface.passwordUser)
                .params("userId", userInfo.getUserId())
                .params("password", UiUtils.getContent(etOldPass))
                .params("newPassword", UiUtils.getContent(etNewPass))
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject userObject = new JSONObject(result.toString());
                            if (userObject.optInt("code") == 0) {
                                ToastUtils.myToast(act, "修改成功");
                                ActivityManager.getInstance().removeActivity(act);
                            } else {
                                ToastUtils.myToast(act, "修改失败,重试一次");
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
