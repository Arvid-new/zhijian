package com.haozhiyan.zhijian.activity.MePackage;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.LoginActivity;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PictureSelectorConfig;
import com.haozhiyan.zhijian.utils.SPUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.widget.CancelOrOkDialog;
import com.haozhiyan.zhijian.widget.PictureVideoDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

import static com.haozhiyan.zhijian.utils.UiUtils.getContext;

/**
 *
 */
public class PersonalInfoActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.headView)
    SimpleDraweeView headView;
    @ViewInject(R.id.tv_exit)
    TextView tv_exit;
    @ViewInject(R.id.tv_name)
    TextView tvName;
    @ViewInject(R.id.tv_login_account)
    TextView tvLoginAccount;
    @ViewInject(R.id.tv_mobile)
    TextView tv_mobile;
    @ViewInject(R.id.ll_header)
    LinearLayout llHeader;
    @ViewInject(R.id.ll_updatePass)
    LinearLayout llUpdatePass;
    @ViewInject(R.id.ll_updatePhone)
    LinearLayout llUpdatePhone;

    private Dialog dialog;
    private UserInfo userInfo;
    //private UpdateDataPop updateDataPop;
    private String filePath = "";
    private static PictureVideoDialog pictureVideoDialog;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            if (msg.what == 2008) {
//                getUserInfo();
//            }
//        }
//    };

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void initView() {
        tv_title.setText("个人信息");
        userInfo = UserInfo.create(getContext());
        tvName.setText(userInfo.getUserName());
        tvLoginAccount.setText(SPUtils.getUserName(this));
        pictureVideoDialog = new PictureVideoDialog(this, R.style.BottomDialogStyle);
        pictureVideoDialog.setIsHeader(true);
        //headView.setImageURI(DataTest.imgUrl);
    }

    @Override
    protected void initListener() {

    }

    private void showDialog() {
        pictureVideoDialog.SetItemClickListener(new PictureVideoDialog.ItemClickListener() {
            @Override
            public void photo() {
                try {
                    PictureSelectorConfig.initSingleImgConfig(act, PictureConfig.CHOOSE_REQUEST);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void camera() {
                PictureSelectorConfig.CameraImg(act, PictureConfig.CHOOSE_REQUEST);
            }

            @Override
            public void cameraVideo() {
                PictureSelectorConfig.CameraVideo(act, PictureConfig.CHOOSE_REQUEST);
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        if (userInfo.getLoginStatus()) {
            getUserInfo();
        }
    }

    @OnClick({R.id.rl_back, R.id.tv_exit, R.id.ll_header, R.id.ll_updatePass, R.id.ll_updatePhone})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                sendUpdateData();
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_exit:
                dialog = new CancelOrOkDialog(this, getString(R.string.exit_login_str)) {
                    @Override
                    public void ok() {
                        super.ok();
                        dialog.dismiss();
                        logoutApp();

                    }
                };
                break;
            case R.id.ll_header:
                showDialog();
                break;
            case R.id.ll_updatePass:
                jumpToActivity(UpdatePass.class);
                break;
            case R.id.ll_updatePhone:
                jumpActivityForResult(UpdateMobile.class, 5026);
                break;
            default:
                break;
        }
    }

//    private void updateData(String hint, final int type) {
//        updateDataPop = new UpdateDataPop(this, R.style.Transparent);
//        updateDataPop.setTitle(hint);
//        updateDataPop.show();
//        updateDataPop.setOnclickListener("确认修改", "取消", new UpdateDataPop.OnclickListener() {
//            @Override
//            public void onYesClick(String inputContent) {
//                if (TextUtils.isEmpty(inputContent)) {
//                    ToastUtils.myToast(act, "请输入修改内容");
//                } else {
//                    if (type == 1) {//
//                        updateInfo(inputContent);
//                        updateDataPop.dismiss();
//                    }
//                }
//            }
//        });
//    }

    //获取个人信息
    //{"msg":"success","code":0,"user":{"userId":211,"peopleuser":"监理-小红","username":"xh","salt":"eXiretZo2mPqsBJXleri","email":"123@qq.com","mobile":"15937089523","status":1,"roleIdList":null,"createTime":"2019-07-03 14:56:48",
    // "deptId":3,"deptName":null,"tenantCode":"mienre","contractorManageId":null,
    // "contractorForShort":null,"userTag":3,"userAppTag":2,"headPortrait":"http://192.168.110.66:8080/ydzj-admin/images/"}}
    private void getUserInfo() {
        long value;
        if(StringUtils.isEmpty(userInfo.getUserId())){
            value = 10000;
        }else{
            value = Long.valueOf(userInfo.getUserId());
        }
        HttpRequest.get(this).url(ServerInterface.userInfo)
                .params("userId",value)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("print=userInfo===", result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONObject user = object.optJSONObject("list");
                                if (user != null) {
                                    headView.setImageURI(user.optString("headPortrait"));
                                    tvName.setText(user.optString("peopleuser"));
                                    tvLoginAccount.setText(user.optString("username"));
                                    tv_mobile.setText(user.optString("mobile"));
                                    userInfo.putUserHeader(user.optString("headPortrait"));
                                    userInfo.putMobile(user.optString("mobile"));
                                    userInfo.putUserName(user.optString("peopleuser"));
                                    userInfo.putUserId(user.optString("userId"));
                                    userInfo.putUserType(user.optString("userAppTag"));
                                    userInfo.putCompanyCode(user.optString("tenantCode"));
                                }
                                userInfo.putUserAppTagSg(object.optString("userAppTagSg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(act, msg);
                    }
                });
    }

    private void updateHeadInfo(String headPortrait) {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.update)
                .params("userId", userInfo.getUserId())
                .params("headPortrait", headPortrait)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject userObject = new JSONObject(result.toString());
                            if (userObject.optInt("code") == 0) {
                                ToastUtils.myToast(act, "修改成功");
                                getUserInfo();
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

    private void uploadHeader(List<File> filePaths) {
        showLoadView("上传中....");
        HttpRequest.get(this).url(ServerInterface.uploadFile)
                .params(ParameterMap.put("fileList", filePaths))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("uploadJson==", result.toString());
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray imageArray = object.optJSONArray("fileName");
                                if (arrayEmpty(imageArray)) {
                                    filePath = imageArray.optString(0);
                                    updateHeadInfo(filePath);
                                }
                            } else {
                                LogUtils.i("upload_error==", "请求错误");
                                ToastUtils.myToast(act, object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        LogUtils.i("upload_fail==", "请求失败");
                        hideLoadView();
                        ToastUtils.myToast(act, msg);
                    }
                });
    }
    private void logoutApp() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.logoutApp)
                .params("userId", userInfo.getUserId())
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                userInfo.putLoginStatus(false);
                                userInfo.ChangeLoginState(false);
                                ActivityManager.getInstance().clearAll();
                                JPushInterface.stopPush(MyApp.getInstance());
                                jumpToActivity(LoginActivity.class);
                            } else {
                                ToastUtils.myToast(act, object.optString("msg"));
                            }
                        } catch (Exception e) {
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    List<File> filePaths = new ArrayList<>();
                    for (int i = 0; i < selectLi.size(); i++) {
                        filePaths.add(new File(selectLi.get(i).getPath()));
                    }
                    uploadHeader(filePaths);
                    break;
                default:
                    break;
            }
        } else {
            if (resultCode == 5026) {
                if (data != null) {
                    tv_mobile.setText(data.getStringExtra("mobile"));
                }
            }
        }
    }

    private void sendUpdateData(){
        ItemValues item = new ItemValues();
        item.isUpdate = true;
        EventBus.getDefault().post(item);
    }
}
