package com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.activity.clys.FirstCheckReport;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.bean.AMReportInfoBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.TimeFormatUitls;
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
 * 报告
 */
public class AM_ReportFragment extends Fragment implements View.OnClickListener {
    private View normalView;
    private View changeView;
    private RelativeLayout parentViewGroup;
    public String id;
    private String times;
    private AMReportInfoBean infoBean;
    //    private List<String> fileList = new ArrayList<>();
    //    private OnlyCameraListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList;

    private String inspector;
    private String inspectorName;

    private RecyclerView inspectReportImageListRCV;
    private TextView reportResultTV;
    private TextView inspectorTV;
    private TextView inspectDateTV;
    private RecyclerView inspectReportImageRCV;

    public static AM_ReportFragment build(String id) {
        AM_ReportFragment fragment = new AM_ReportFragment();
//        fragment.state = state;
        fragment.id = id;
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
//        bundle.putString("state", state);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (rootView != null) {
            parentViewGroup = rootView.findViewById(R.id.parentViewGroup);
//            initView();
        }
        try {
            if (StringUtils.isEmpty(inspector)) {
                inspector = SPUtil.get(getContext()).get("clysTaskInspectorId=");
                if (!StringUtils.isEmpty(inspector)) {
                    infoClysReport();
                }
            } else {
                infoClysReport();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.inspectorLL:
                SelectTRPOrAU.select(this, inspector, SelectTRPOrAU.inspector);//送检人
                break;
            case R.id.secussBT:
                UpLoadUtil.init(getContext(), selectList,true).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        report(true, paths);
                    }
                });
                break;
            case R.id.reportCheckLossBT:
                UpLoadUtil.init(getContext(), selectList, true).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        report(false, paths);
                    }
                });
                break;
            case R.id.inspectReportLL://首次送检记录
                try {
                    Intent intent = new Intent(getContext(), FirstCheckReport.class);
                    intent.putExtra("bean", infoBean.getClysReportList().get(0));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event != null) {
            if (event.startsWith("clysTaskInspectorId=") && StringUtils.isEmpty(inspector)) {
                try {
                    String[] Inspector = event.split(",");
                    inspector = Inspector[0].replace("clysTaskInspectorId=", "");
                    inspectorName = Inspector[1].replace("clysTaskInspectorName=", "");
                    infoClysReport();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private ImageView inspectorTalkImg;

    private void initView() {
        normalView = LayoutInflater.from(getContext()).inflate(R.layout.am_report_fg_layout, parentViewGroup, false);
        parentViewGroup.addView(normalView);

        inspectReportImageListRCV = normalView.findViewById(R.id.inspectReportImageListRCV);
        reportResultTV = normalView.findViewById(R.id.reportResultTV);
        inspectDateTV = normalView.findViewById(R.id.inspectDateTV);
        inspectorTV = normalView.findViewById(R.id.inspectorTV);
        inspectorTalkImg = normalView.findViewById(R.id.inspectorTalkImg);

        normalView.findViewById(R.id.inspectReportLL).setOnClickListener(this);
        if (times.equals("2")) {
            normalView.findViewById(R.id.inspectReportLL).setVisibility(View.VISIBLE);
        }
        try {
            final AMReportInfoBean.ClysReportListBean bean = infoBean.getClysReportList().get(infoBean.getClysReportList().size() - 1);
            setPicRcv(inspectReportImageListRCV, bean.getInspectReportImageList());
            reportResultTV.setText("0".equals(bean.getReportResult()) ? "不合格" : "合格");
            inspectDateTV.setText(bean.getInspectDate() + "");

            try {
                inspectorTV.setText(bean.getInspectorList().get(0).getPeopleuser() + "");
            } catch (Exception e) {
                e.printStackTrace();
            }

            inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        for (int i = 0; i < bean.getInspectorList().size(); i++) {
                            if (String.valueOf(bean.getInspector())
                                    .equals(String.valueOf(bean.getInspectorList().get(i).getUserId()))) {
                                SystemUtils.callPage(bean.getInspectorList().get(i).getTel(), getContext());
                                break;
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Button secussBT;
    private Button reportCheckLossBT;

    private void changeLayout() {
        changeView = LayoutInflater.from(getContext()).inflate(R.layout.am_report_change_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
        inspectReportImageRCV = changeView.findViewById(R.id.inspectReportImageRCV);
        inspectorTV = changeView.findViewById(R.id.inspectorTV);
        changeView.findViewById(R.id.inspectorLL).setOnClickListener(this);
        reportCheckLossBT = changeView.findViewById(R.id.reportCheckLossBT);
        reportCheckLossBT.setOnClickListener(this);
        changeView.findViewById(R.id.inspectReportLL).setOnClickListener(this);
        secussBT = changeView.findViewById(R.id.secussBT);
        secussBT.setOnClickListener(this);

        if (times.equals("1") || times.equals("2")) {
            changeView.findViewById(R.id.inspectReportLL).setVisibility(View.VISIBLE);
            secussBT.setText("复验合格");
            reportCheckLossBT.setText("复验不合格");
        }
        inspectorTV.setText(UserInfo.create(getContext()).getUserName());

        setPictureSelectRcv(inspectReportImageRCV);
    }

    /**
     * 设置 选择照片的那个 RCV 控件设置数据
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {

        try {
            pictureSelectRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            pictureSelectRcv.setNestedScrollingEnabled(true);
            if (selectList == null)
                selectList = new ArrayList<>();
            //adapter = new PictureOrVideoListAdapter(getContext());
            adapter = new PictureOrVideoListNewAdapter(getContext());
            PhotoCameraUtils.init(AM_ReportFragment.this).photoDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

//
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
//                    PictureSelectorConfig.openCameraImg(AM_ReportFragment.this, PictureConfig.CHOOSE_REQUEST);
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
        } else if (resultCode == SelectTRPOrAU.inspector) {///送检人
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            inspector = bundle.getString("id");
            inspectorTV.setText(jianShePerson);
            updateClysTask();
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
                } catch (Exception e) {
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
     * 材料验收-修改任务，
     */
    private void updateClysTask() {
        HttpRequest.get(getContext())
                .url(ServerInterface.updateClysTask)
                .params("id", id)//主键id
                .params("inspector", inspector)//送检人id
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

    /**
     * 材料验收-根据任务id查询送检记录详情
     */
    private void infoClysReport() {
        HttpRequest.get(getContext())
                .url(ServerInterface.infoClysReport)
                .params("taskId", id)//任务id
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            infoBean = new Gson().fromJson(result.toString(), AMReportInfoBean.class);
                            boolean isMe = false;
                            try {
                                if (infoBean.getClysReportList().size() >= 2) {
                                    times = "2";
                                } else {
                                    times = infoBean.getClysReportList().size() + "";
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                times = "0";
                            }
                            if (String.valueOf(inspector).
                                    equals(UserInfo.create(getContext()).getUserId())) {
                                isMe = true;
                            }
                            if (TextUtils.equals(ENTRANCE, "form")) {
                                if (CLYSState.equals("5")) {
                                    // 如果第一次复验 显示暂无记录
                                    if ("0".equals(times)) {
                                        noHistoryLayout();
                                    } else {
                                        initView();
                                    }
                                } else {
                                    // 如果第一次复验 显示暂无记录
                                    if ("0".equals(times)) {
                                        noHistoryLayout();
                                    } else {
                                        initView();
                                    }
                                }
                            } else {
                                //待上传报告 需要编辑
                                //送检人是自己 是的话显示可编辑进场  不是的话就显示暂无记录
                                if (CLYSState.equals("5")) {
                                    if (isMe) {
                                        changeLayout();
                                    } else {
                                        // 如果第一次复验 显示暂无记录
                                        if ("0".equals(times)) {
                                            noHistoryLayout();
                                        } else {
                                            initView();
                                        }
                                    }
                                } else {
                                    // 如果第一次复验 显示暂无记录
                                    if ("0".equals(times)) {
                                        noHistoryLayout();
                                    } else {
                                        initView();
                                    }
                                }
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

    private void noHistoryLayout() {
        View changeView = LayoutInflater.from(getContext()).inflate(R.layout.no_history_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);

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
     * 材料验收-上传报告
     */
    private void report(boolean reportResult, String paths) {
        int time = 1;
        if (times.equals("0")) {
            time = 1;
        } else if (times.equals("1")) {
            time = 2;
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.report)
                .params("taskId", id)//任务id
                .params("inspectReportImage", paths)//送检照片：图片
                .params("inspectDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//送检时间
                .params("times", time + "")//第几次送检：第一次为1，第二次为2
                .params("reportResult", reportResult ? "1" : "0")//报告结果：1合格、0不合格，需要复验
                .params("inspector", inspector)//报告结果：1合格、0不合格，需要复验
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
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
