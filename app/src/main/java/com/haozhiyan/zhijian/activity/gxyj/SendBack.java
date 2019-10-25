package com.haozhiyan.zhijian.activity.gxyj;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.adapter.CloseReasonAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.SendBackListAdapter;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
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

/**
 * 退回 原因页面
 */
public class SendBack extends BaseActivity2 implements View.OnClickListener {
    private String inspectionId;
    private String id;
    private String type;
    private int sendBackNumber;
    private RecyclerView sendBackRcv;
    private RecyclerView picRcv;
    private Button commitBT;
    //    private PictureOrVideoListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private CloseReasonAdapter reasonAdapter;
    private List<LocalMedia> selectList;
    private RelativeLayout etLL;
    private String string;
    private EditText editView;
    private TextView editViewTV;
    private String supervisorSendBack;

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            type = getIntent().getExtras().getString("type", "");
            inspectionId = getIntent().getExtras().getString("inspectionId", "");
            id = getIntent().getExtras().getString("id", "");
            roomId = getIntent().getExtras().getString("roomId", "");
            appGxyjId = getIntent().getExtras().getString("appGxyjId", "");
            sendBackNumber = getIntent().getExtras().getInt("sendBackNumber", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_send_back;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("退回原因");
        setAndroidNativeLightStatusBar(true);
        sendBackRcv = findViewById(R.id.sendBackRcv);
        picRcv = findViewById(R.id.picRcv);
        commitBT = findViewById(R.id.commitBT);
        etLL = findViewById(R.id.etLL);
        editView = findViewById(R.id.editView);
        editViewTV = findViewById(R.id.editViewTV);
        sendBackRcv.setNestedScrollingEnabled(true);
        commitBT.setOnClickListener(this);
        sendBackRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        setPictureSelectRcvData();
    }

    @Override
    protected void initData() {
        switch (type) {
            case "0"://现场检查
                etLL.setVisibility(View.GONE);
                listSendBackZG();
                break;
            case "1"://工序移交
                listSendBack();
                break;
            case "2"://工序移交
                listSendBackZG();
//                final List<String> strings = new ArrayList<>();
//                boolean[] checks = {false, false};
//                strings.add("整改不合格");
//                strings.add("其他");
//                final SendBackListAdapter sendBackListAdapter = new SendBackListAdapter(strings, checks);
//                sendBackRcv.setAdapter(sendBackListAdapter);
//                sendBackListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                        if (sendBackListAdapter.getChecks()[position]) {
//                            sendBackListAdapter.getChecks()[position] = false;
//                        } else {
//                            sendBackListAdapter.getChecks()[position] = true;
//                        }
//                        sendBackListAdapter.notifyDataSetChanged();
//                        if (ListUtils.listEmpty(strings)) {
//                            supervisorSendBack = strings.get(position);
//                        }
//                    }
//                });
                break;
        }
        setDescET(editView, editViewTV);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commitBT:
                switch (type) {
                    case "0"://现场检查
                        if (TextUtils.isEmpty(string)) {
                            ToastUtils.myToast(getContext(), "请选择退回原因");
                            return;
                        }
                        UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                            @Override
                            public void onComplete(String paths) {
                                xcjcUpdateTrouble(paths);
                            }
                        });
                        break;
                    case "1"://工序移交
                        String tag = UserInfo.create(getContext()).getUserType();
                        if (tag.equals("2")) {
                            UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                                @Override
                                public void onComplete(String paths) {
                                    saveSendBack(paths);
                                }
                            });
                        } else if (tag.equals("3")) {
                            UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                                @Override
                                public void onComplete(String paths) {
                                    saveSendBackCon(paths);
                                }
                            });
                        }
                        break;
                    case "2"://工序移交 整改问题
                        UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                            @Override
                            public void onComplete(String paths) {
                                updateAbarbeitungSendBack(paths);
                            }
                        });
                        break;
                }

                break;
        }
    }

    /**
     * 输入框监听方法 可复用
     *
     * @param descET
     * @param descNumTv
     */
    private void setDescET(final EditText descET, final TextView descNumTv) {
        descET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(s.toString())) {
                    int num = 200 - s.length();
                    if (num <= 0) {
                        descET.setText(s);
                    }
                    descNumTv.setText(num + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    /**
     * 获取问题列表
     */
    private void listSendBack() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listSendBack)
                .params("inspectionId", inspectionId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                final List<String> strings = new ArrayList<>();
                                List<String> ids = new ArrayList<>();
                                boolean[] checks = new boolean[array.length()];
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    checks[i] = false;
                                    strings.add(object.optString("particularsName"));
                                    ids.add(object.optString("gxyjParticularsId"));
                                }
                                final SendBackListAdapter sendBackListAdapter = new SendBackListAdapter(strings, checks);
                                sendBackRcv.setAdapter(sendBackListAdapter);
                                sendBackListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        if (sendBackListAdapter.getChecks()[position]) {
                                            sendBackListAdapter.getChecks()[position] = false;
                                        } else {
                                            sendBackListAdapter.getChecks()[position] = true;
                                        }
                                        sendBackListAdapter.notifyDataSetChanged();
                                        if (ListUtils.listEmpty(strings)) {
                                            supervisorSendBack = strings.get(position);
                                        }
                                    }
                                });

                            } else {
                                ToastUtils.myToast(getActivity(), "当前部位暂无可选原因");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    /**
     * 获取问题列表
     */
    private void listSendBackZG() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listSendBackZG)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                final List<String> strings = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    strings.add(array.optString(i));
                                }
                                reasonAdapter = new CloseReasonAdapter(strings);
                                sendBackRcv.setAdapter(reasonAdapter);
                                reasonAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter aa, View view, int position) {
                                        reasonAdapter.setSelect(position);
                                        reasonAdapter.notifyDataSetChanged();
                                        string = strings.get(position);
                                        try {
                                            if (string.equals("其它")) {
                                                etLL.setVisibility(View.VISIBLE);
                                            } else {
                                                etLL.setVisibility(View.GONE);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    /**
     * 现场检查 退回 更新 参数
     */
    private void xcjcUpdateTrouble(String paths) {
        try {
            if ("其它".equals(string)) {
                String Cause = editView.getText().toString().trim();
                if (!TextUtils.isEmpty(Cause)) {
                    string = Cause;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoadView("请稍等");
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("3"))
                .params("id", id)
                .params("state", "1")//状态：1.待整改，2.待复验，3.非正常关闭，4.已通过S
                .params("backCause", string, true)
                .params("backImage", paths)
                .params("backNumber", "1")
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            hideLoadView();
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                EventBus.getDefault().post("cendBackSeccess");
                                finish();
                            } else {
                                Toast.makeText(SendBack.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideLoadView();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

    private String roomId;
    private String appGxyjId;

    /**
     * 提交退回
     */
    private void updateAbarbeitungSendBack(String paths) {
        try {
            if ("其它".equals(string)) {
                String Cause = editView.getText().toString().trim();
                if (!TextUtils.isEmpty(Cause)) {
                    string = Cause;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.updateAbarbeitungSendBack)
                .params("id", id)
                .params("roomId", roomId)//
                .params("appGxyjId", appGxyjId)//
                .params("sendBackCause", string)
                .params("sendBackPicture", paths)
                .params("sendBackNumber", sendBackNumber)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                EventBus.getDefault().post("cendBackSeccess");
                                finish();
                            } else {
                                Toast.makeText(SendBack.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    /**
     * 上传文件
     *
     * @param filePaths
     */
    private void uploadFile(List<File> filePaths) {
        showLoadView("上传中....");
        HttpRequest.get(this).url(ServerInterface.uploadFile).params(ParameterMap.put("fileList", filePaths)).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("uploadJson==", result.toString());
                hideLoadView();
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            List<String> list = new ArrayList<>();
                            LocalMedia localMedia = new LocalMedia();
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (UiUtils.isEmpty(imageArray.optString(i))) {
                                    list.add(imageArray.optString(i));
//                                    filePath = StringUtils.listToStrByChar(list, ',');
                                    localMedia.setPath(imageArray.optString(i));
                                    localMedia.setPictureType(PVAUtils.getFileLastType(imageArray.optString(i)));
                                    selectList.add(localMedia);
                                }
                            }
                            adapter.setNewData(selectList);
                            adapter.notifyDataSetChanged();
                        } else {
//                            filePath = "";
                        }
                        //saveSendBack();
                    } else {
                        ToastUtils.myToast(getActivity(), object.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                hideLoadView();
                ToastUtils.myToast(getActivity(), msg);
            }
        });
    }


    /**
     * 提交退回 监理  工序移交
     */
    private void saveSendBack(String paths) {
        LogUtils.i("id===", id);
        if (StringUtils.isEmpty(supervisorSendBack)) {
            ToastUtils.myToast(getActivity(), "请填写退回原因");
            return;
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.saveSendBack)
                .params("appGxyjId", id)//申请问题信息的主键ID
                .params("backPictureVideo", paths)//监理图片视频已退回
                .params("backExplain", UiUtils.getContent(editView), true)//监理已退回说明
                .params("backCause", supervisorSendBack)//监理退回原因-问题选项
                .params("backUsername", UserInfo.create(getContext()).getUserName())//退回人姓名
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                finish();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    /**
     * 提交退回 建设单位验收人 工序移交
     */
    private void saveSendBackCon(String paths) {
        LogUtils.i("id===", id);

        if (StringUtils.isEmpty(supervisorSendBack)) {
            ToastUtils.myToast(getActivity(), "请填写退回原因");
            return;
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.saveSendBackCon)
                .params("appGxyjId", id)//申请问题信息的主键ID
                .params("backPictureVideo", paths)//监理图片视频已退回
                .params("backExplain", UiUtils.getContent(editView), true)//监理已退回说明
                .params("backCause", supervisorSendBack)//监理退回原因-问题选项
                .params("backUsername", UserInfo.create(getContext()).getUserName())//退回人姓名
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                finish();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    /**
     * 设置 选择照片的那个 RCV 控件设置数据 可复用
     */
    private void setPictureSelectRcvData() {
//        picRcv.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
//        picRcv.setNestedScrollingEnabled(true);
//        adapter = new PictureOrVideoListAdapter(getContext());
//        picRcv.setAdapter(adapter);
//        selectList = new ArrayList<>();
//        adapter.setNewData(selectList);
//        adapter.notifyDataSetChanged();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter dd, View view, int position) {
//                if (position == adapter.getItemCount() - 3) {
//                    PictureSelectorConfig.openCameraImg(getActivity(), PictureConfig.CHOOSE_REQUEST);
//                } else if (position == adapter.getItemCount() - 2) {
//                    PictureSelectorConfig.openCameraVideo(getActivity(), PictureConfig.CHOOSE_REQUEST);
//                } else if (position == adapter.getItemCount() - 1) {
//                    PictureSelectorConfig.initSingleAllConfig(getActivity(), PictureConfig.CHOOSE_REQUEST, null);
//                } else {
//                    int pictureType = PictureMimeType.isPictureType(selectList.get(position).getPictureType());
//                    if (pictureType == PictureConfig.TYPE_VIDEO) {
//                        ShowVideo.playLocalVideo(getContext(), selectList.get(position).getPath());
//                    } else {
//                        if (selectList.get(position).isCut()) {
//                            ShowBigImg.build(getContext(), selectList.get(position).getCutPath());
//                        } else {
//                            ShowBigImg.build(getContext(), selectList.get(position).getPath());
//                        }
//                    }
//                }
//            }
//        });
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter dd, View view, int position) {
//                if (view.getId() == R.id.ll_del) {
//                    try {
//                        selectList.remove(position);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
////                    adapter.remove(position);
//                    adapter.setNewData(selectList);
//                    adapter.notifyDataSetChanged();
//                }
//            }
//        });

        try {
            picRcv.setLayoutManager(new GridLayoutManager(getContext(), 4, LinearLayoutManager.VERTICAL, false));
            picRcv.setNestedScrollingEnabled(true);
            if (selectList == null)
                selectList = new ArrayList<>();
            //adapter = new PictureOrVideoListAdapter(getContext());
            adapter = new PictureOrVideoListNewAdapter(getContext());
            PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, picRcv, selectList, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    int ss = selectList.size();
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
//                    adapter.notifyDataSetChanged();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
//                    List<File> filePaths = new ArrayList<>();
//                    for (int i = 0; i < selectLi.size(); i++) {
//                        int pictureType = PictureMimeType.isPictureType(selectLi.get(i).getPictureType());
//                        if (pictureType == PictureConfig.TYPE_IMAGE) {//图片
//                            if (selectLi.get(i).isCut()) {
//                                filePaths.add(new File(selectLi.get(i).getCutPath()));
//                            } else {
//                                filePaths.add(new File(selectLi.get(i).getPath()));
//                            }
//                        } else if (pictureType == PictureConfig.TYPE_VIDEO) {//视频
//                            filePaths.add(new File(selectLi.get(i).getPath()));
//                        }
//                    }
//                    uploadFile(filePaths);
                    break;
            }
        }
    }

}
