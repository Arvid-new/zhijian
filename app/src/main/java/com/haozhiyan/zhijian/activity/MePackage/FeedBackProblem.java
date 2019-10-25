package com.haozhiyan.zhijian.activity.MePackage;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FeedBackProblem extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.tv_right)
    TextView tvRight;
    @ViewInject(R.id.et_instruct_content)
    EditText etInstructContent;
    @ViewInject(R.id.id_editor_detail_font_count)
    TextView editorDetailFontCount;
    @ViewInject(R.id.feedBackRcv)
    RecyclerView feedBackRcv;
    @ViewInject(R.id.btn_commit)
    Button btnCommit;
    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> fileList = new ArrayList<>();
    private String filePath = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_feed_back_problem;
    }

    @Override
    protected void initView() {
        tvTitle.setText("反馈问题");
        tvRight.setText("历史");
        tvRight.setVisibility(View.VISIBLE);
        initPhoto();
    }

    @Override
    protected void initListener() {
        etInstructContent.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    private void initPhoto() {
        feedBackRcv.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        feedBackRcv.addItemDecoration(new GridSpacingItemDecoration(3, 1, true));
        adapter = new PictureOrVideoListNewAdapter(this);
        PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, feedBackRcv, selectList,0);
    }

    @OnClick({R.id.rl_back, R.id.tv_right,R.id.btn_commit})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                fileList.clear();
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_right:
                jumpToActivity(FeedBackHistory.class);
                break;
            case R.id.btn_commit:
                if (TextUtils.equals("", UiUtils.getContent(etInstructContent))) {
                    ToastUtils.myToast(act, "请输入您的描述");
                } else {
                    commitFeedBack();
                }
                break;
            default:
                break;
        }
    }

    private void commitFeedBack() {
        showLoad("提交反馈..");
        HttpRequest.get(this).url(ServerInterface.saveProblemFeedback)
                .params("feedback", UiUtils.getContent(etInstructContent))
                .params("problemPicture", filePath)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                       dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(act, object.optString("msg"));
                                ActivityManager.getInstance().removeActivity(act);
                                fileList.clear();
                            } else {
                                ToastUtils.myToast(act, object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        dialog.dismiss();
                        ToastUtils.myToast(act, msg);
                    }
                });
    }

    private void uploadFile(List<File> filePaths) {
        showLoad("...");
        HttpRequest.get(this).url(ServerInterface.uploadFile)
                .params(ParameterMap.put("fileList", filePaths))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("uploadJson==", result.toString());
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray imageArray = object.optJSONArray("fileName");
                                if (arrayEmpty(imageArray)) {
                                    for (int i = 0; i < imageArray.length(); i++) {
                                        LocalMedia localMedia = new LocalMedia();
                                        if (UiUtils.isEmpty(imageArray.optString(i))) {
                                            fileList.add(imageArray.optString(i));
                                            localMedia.setPath(imageArray.optString(i));
                                            localMedia.setPictureType(PVAUtils.getFileLastType(imageArray.optString(i)));
                                        }
                                        selectList.add(localMedia);
                                    }
                                    filePath = StringUtils.listToStrByChar(fileList, ',');
                                    adapter.setNewData(selectList);
                                    adapter.notifyDataSetChanged();
                                } else {
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
                        dialog.dismiss();
                        ToastUtils.myToast(act, msg);
                    }
                });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int detailLength = etInstructContent.length();
            editorDetailFontCount.setText(detailLength + "/200");
            if (detailLength == 200) {
                ToastUtils.myToast(act, R.string.string_twoBai_end);
            }
        }
    };

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
                    uploadFile(filePaths);
                    break;
                default:
                    break;
            }
        }
    }

    private void showLoad(String content){
        dialog = new LoadingDialog(this);
        dialog.setTitle(content);
        dialog.show();
    }
}
