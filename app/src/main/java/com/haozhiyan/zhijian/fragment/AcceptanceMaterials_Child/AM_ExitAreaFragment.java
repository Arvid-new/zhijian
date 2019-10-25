package com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.Calendar;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.activity.workXcjc.SelectAngle;
import com.haozhiyan.zhijian.adapter.PersonNameListAdapter;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.bean.AMExitInfoBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials.CLYSState;
import static com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials.ENTRANCE;
import static com.haozhiyan.zhijian.utils.ListUtils.arrayEmpty;

/**
 * A simple {@link Fragment} subclass.
 * 退场
 */
public class AM_ExitAreaFragment extends Fragment implements View.OnClickListener {
    private View normalView;
    private View changeView;
    private RelativeLayout parentViewGroup;
    public String id;
    //    public String state;
    private LinearLayout exitDateLL;
    private LinearLayout supervisorLL;
    private TextView exitDateTV;
    private RecyclerView exitImageRCV;
    private EditText exitSupplementET;
    private TextView descNumTv;
    private TextView supervisorNameTV;
    private LinearLayout ccLL;
    private TextView ccNameTV;
    private Button commitBT;
    private String cc;
    private List<LocalMedia> selectList;
    //    private OnlyCameraListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private String ccName;
    private String supervisorName;


    public static AM_ExitAreaFragment build(String id, String supervisor, String cc) {
        AM_ExitAreaFragment fragment = new AM_ExitAreaFragment();
//        fragment.state = state;
        fragment.id = id;
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
//        bundle.putString("state", state);
        bundle.putString("supervisor", supervisor);
        bundle.putString("cc", cc);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (getArguments() != null) {
            id = getArguments().getString("id", "");
//            state = getArguments().getString("state", "");
            supervisorName = getArguments().getString("supervisor", "");
            ccName = getArguments().getString("cc", "");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);
        if (rootView != null) {
            parentViewGroup = rootView.findViewById(R.id.parentViewGroup);
        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        try {
            if (StringUtils.isEmpty(supervisor)) {
                supervisor = SPUtil.get(getContext()).get("clysTaskSupervisorId=");
                if (!StringUtils.isEmpty(supervisor)) {
                    infoClysExit();
                }
            } else {
                infoClysExit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event != null) {
            if (event.startsWith("clysTaskSupervisorId=") && StringUtils.isEmpty(supervisor)) {
                String[] Supervisor = event.split(",");
                supervisor = Supervisor[0].replace("clysTaskSupervisorId=", "");
                supervisorName = Supervisor[1].replace("clysTaskSupervisorName=", "");
                try {
                    if (supervisorNameTV != null) {
                        supervisorNameTV.setText(supervisorName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    infoClysExit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void infoClysExit() {
        HttpRequest.get(getContext())
                .url(ServerInterface.infoClysExit)
                .params("taskId", id)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            AMExitInfoBean infoBean = new Gson().fromJson(result.toString(), AMExitInfoBean.class);
                            if (infoBean.getCode() == 0) {
                                if (TextUtils.equals(ENTRANCE, "form")) {
                                    if (CLYSState.equals("7")) {
                                        noHistoryLayout();
                                    } else {
                                        initView(infoBean);
                                    }
                                } else {
                                    if (CLYSState.equals("7")) {
                                        if (UserInfo.create(getContext()).getUserId().equals(supervisor)) {
                                            changeLayout(infoBean);
                                        } else {
                                            noHistoryLayout();
                                        }
                                    } else {
                                        initView(infoBean);
                                    }
                                }

                            } else {
                                ToastUtils.myToast(getActivity(), infoBean.getMsg());
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

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.exitDateLL://时间
                Calendar.check(this, exitDateTV.getText().toString().trim(), Calendar.SELECTDATE);
                break;
            case R.id.supervisorLL://监理
                SelectTRPOrAU.select(this, supervisor, SelectTRPOrAU.Supervisor);//监理
                break;
            case R.id.ccLL:
                Constant.REN_TYPE = 3;//抄送人
                startActivityForResult(new Intent(getContext(), SelectAngle.class), Constant.CHAO_SONG_REN_CODE);
                break;
            case R.id.commitBT://提交
                UpLoadUtil.init(getContext(), selectList, true).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        exit(paths);
                    }
                });
                break;
        }
    }

    private RecyclerView exitImageListRCV;
    private RecyclerView supervisorListRCV;
    private RecyclerView ccNameRCV;
    private TextView exitSupplementTV;

    private void initView(AMExitInfoBean infoBean) {
        normalView = LayoutInflater.from(getContext()).inflate(R.layout.am_exit_fg_layout, parentViewGroup, false);
//        normalView.findViewById(R.id.changeBT).setOnClickListener(this);
        parentViewGroup.addView(normalView);

        exitDateTV = normalView.findViewById(R.id.exitDateTV);
        exitImageListRCV = normalView.findViewById(R.id.exitImageListRCV);
        supervisorListRCV = normalView.findViewById(R.id.supervisorListRCV);
        ccNameRCV = normalView.findViewById(R.id.ccNameRCV);
        exitSupplementTV = normalView.findViewById(R.id.exitSupplementTV);

        exitDateTV.setText(infoBean.getClysExit().getExitDate());
        setPicRcv(exitImageListRCV, infoBean.getClysExit().getExitImageList());

        if (StringUtils.isEmpty(infoBean.getClysExit().getExitSupplement())) {
            exitSupplementTV.setText("无");
        } else {
            exitSupplementTV.setText(infoBean.getClysExit().getExitSupplement());
        }

        setNameRcv(supervisorListRCV, infoBean.getClysExit().getSupervisorList());

        if (infoBean.getClysExit().getCcList() == null || infoBean.getClysExit().getCcList().size() <= 0) {
            List<String> strings = new ArrayList<>();
            strings.add("无");
            setNameRcv(ccNameRCV, strings);
        } else {
            setNameRcv(ccNameRCV, infoBean.getClysExit().getCcList());
        }
    }


    private String supervisor;

    private void changeLayout(AMExitInfoBean infoBean) {
        changeView = LayoutInflater.from(getContext()).inflate(R.layout.am_exit_fragment, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
        exitDateLL = changeView.findViewById(R.id.exitDateLL);
        exitDateTV = changeView.findViewById(R.id.exitDateTV);
        exitImageRCV = changeView.findViewById(R.id.exitImageRCV);
        exitSupplementET = changeView.findViewById(R.id.exitSupplementET);
        descNumTv = changeView.findViewById(R.id.descNumTv);
        supervisorLL = changeView.findViewById(R.id.supervisorLL);
        supervisorNameTV = changeView.findViewById(R.id.supervisorNameTV);
        ccLL = changeView.findViewById(R.id.ccLL);
        ccNameTV = changeView.findViewById(R.id.ccNameTV);
        commitBT = changeView.findViewById(R.id.commitBT);
        commitBT.setOnClickListener(this);
        ccLL.setOnClickListener(this);
        supervisorLL.setOnClickListener(this);
        exitDateLL.setOnClickListener(this);
        setPictureSelectRcv(exitImageRCV);
        setDescET(exitSupplementET, descNumTv);
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        exitDateTV.setText(calendar.get(java.util.Calendar.YEAR) + "-" +
                (calendar.get(java.util.Calendar.MONTH) + 1) + "-" +
                calendar.get(java.util.Calendar.DAY_OF_MONTH));
        if (!StringUtils.isEmpty(ccName)) {
            ccNameTV.setText(ccName);
        } else {
            ccNameTV.setText("选填");
            ccNameTV.setTextColor(ContextCompat.getColor(getContext(), R.color.c6_color));
        }
        if (!StringUtils.isEmpty(supervisorName)) {
            supervisorNameTV.setText(supervisorName);
        }
    }

    private void noHistoryLayout() {
        View changeView = LayoutInflater.from(getContext()).inflate(R.layout.no_history_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Bundle bundle;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
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
        } else if (requestCode == Calendar.SELECTDATE) {//日期
            try {
                Object date = data.getBundleExtra("bundle").getSerializable("selectCalendar");
                if (date instanceof com.haibin.calendarview.Calendar) {
                    com.haibin.calendarview.Calendar calendar = (com.haibin.calendarview.Calendar) date;
                    exitDateTV.setText(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == Constant.CHAO_SONG_REN_CODE) {//抄送人
            String chaoSong = data.getStringExtra("selectType");
            cc = data.getStringExtra("selectId");
            if (!StringUtils.isEmpty(chaoSong)) {
//                chaoSongLi = new ArrayList<>();
                List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                List<String> chaoSongId = ListUtils.stringToList(cc);
//                for (int i = 0; i < chaoSongName.size(); i++) {
//                    chaoSongLi.add(new ItemBean(chaoSongName.get(i), chaoSongId.get(i), ""));
//                }
//                setNameRcv(ccListRcv, chaoSongName);
                ccNameTV.setText(chaoSong);
            }
            updateClysTask("cc", cc);
        } else if (resultCode == SelectTRPOrAU.Supervisor) {//监理
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            String supervisor = bundle.getString("id");
            supervisorNameTV.setText(jianShePerson);
            updateClysTask("supervisor", supervisor);
        }
    }

    /**
     * 上传图片
     *
     * @param filePaths
     */
    private void uploadFile(List<File> filePaths) {
        showLoadView("上传中");
        HttpRequest.get(getContext()).url(ServerInterface.uploadFile).params(ParameterMap.put("fileList", filePaths)).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoadView();
                LogUtils.i("uploadJson==", result.toString());
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            LocalMedia localMedia = new LocalMedia();
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (UiUtils.isEmpty(imageArray.optString(i))) {
//                                    filePath = StringUtils.listToStrByChar(fileList, ',');
                                    localMedia.setPath(imageArray.optString(i));
                                    localMedia.setPictureType(PVAUtils.getFileLastType(
                                            imageArray.optString(i)));
                                    selectList.add(localMedia);
                                }
                            }
                            adapter.setNewData(selectList);
                            adapter.notifyDataSetChanged();
                        } else {

                        }
                    } else {
                        LogUtils.i("upload_error==", "请求错误");
                        ToastUtils.myToast(getActivity(), object.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtils.i("upload_fail==", "请求失败");
                ToastUtils.myToast(getActivity(), msg);
                hideLoadView();
            }
        });
    }


    /**
     * 设置 选择照片的那个 RCV 控件设置数据
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {

        try {
            pictureSelectRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            pictureSelectRcv.setNestedScrollingEnabled(true);
            selectList = new ArrayList<>();
            //adapter = new PictureOrVideoListAdapter(getContext());
            adapter = new PictureOrVideoListNewAdapter(getContext());
            PhotoCameraUtils.init(AM_ExitAreaFragment.this).photoDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        pictureSelectRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        pictureSelectRcv.setNestedScrollingEnabled(true);
//        adapter = new OnlyCameraListAdapter(getContext());
//        pictureSelectRcv.setAdapter(adapter);
//        selectList = new ArrayList<>();
//        adapter.setNewData(selectList);
//        adapter.notifyDataSetChanged();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter dd, View view, int position) {
//                if (position == adapter.getItemCount() - 1) {
//                    PictureSelectorConfig.openCameraImg(AM_ExitAreaFragment.this, PictureConfig.CHOOSE_REQUEST);
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
    }

    /**
     * 设置 仅 展示图片的 RCV
     *
     * @param picRcv
     * @param imgs
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        if (imgs != null && imgs.size() > 0) {
            picRcv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(imgs, getContext());
            picRcv.setAdapter(picShowListAdapter);
            picShowListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (PVAUtils.getFileLastType(imgs.get(position)).equals("image/jpeg")) {
                        ShowBigImg.build(getContext(), imgs.get(position));
                    } else {
                        ShowVideo.playLineVideo(getContext(), imgs.get(position));
                    }
                }
            });
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
     * 设置名字列表RCV 不同布局文件可复用
     */
    private void setNameRcv(final RecyclerView nameRcv, List aClass) {
        if (aClass != null && aClass.size() > 0) {
            nameRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            PersonNameListAdapter chaosongAdapter = new PersonNameListAdapter(getContext());
            chaosongAdapter.setNewData(aClass);
            nameRcv.setAdapter(chaosongAdapter);
            chaosongAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.talkImg) {
                        ToastUtils.myToast(getActivity(), "");
                    }
                }
            });
            chaosongAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    View parent = (View) nameRcv.getParent();
                    int vid = parent.getId();
                    if (parent instanceof LinearLayout && vid > 0) {
                        onClick(parent);
                    } else {
                        View parent2 = (View) parent.getParent();
                        int vid2 = parent2.getId();
                        if (parent2 instanceof LinearLayout && vid2 > 0) {
                            onClick(parent2);
                        }
                    }

                }
            });
        }
    }

    /**
     * 材料验收-修改任务，
     */
    private void updateClysTask(String key, String value) {
        HttpRequest.get(getContext())
                .url(ServerInterface.updateClysTask)
                .params("id", id)//主键id
                .params(key, value)//
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
//                                infoClysTask();
//                                EventBus.getDefault().post("taskStateChanged");
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
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

    public LoadingDialog dialog;

    protected void showLoadView(String str) {
        try {
            if (dialog == null) {
                dialog = new LoadingDialog(getContext());
            }
            dialog.setTitle(str);
            dialog.show();
        } catch (Exception e) {

        }
    }

    protected void hideLoadView() {
        try {
            dialog.dismiss();
        } catch (Exception e) {

        }
    }

    /**
     * 材料验收-提交退场
     */
    private void exit(String paths) {
        HttpRequest.get(getContext())
                .url(ServerInterface.exit)
                .params("taskId", id)//主键id
                .params("exitDate", exitDateTV.getText().toString().trim())//
                .params("exitImage", paths)//
                .params("exitSupplement", exitSupplementET.getText().toString().trim())//
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
//                                infoClysTask();
                                EventBus.getDefault().post("taskStateChanged");
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
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


}
