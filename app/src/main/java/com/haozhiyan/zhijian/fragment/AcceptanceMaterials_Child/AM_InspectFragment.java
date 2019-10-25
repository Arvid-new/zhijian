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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.Calendar;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.activity.clys.FirstCheckHistory;
import com.haozhiyan.zhijian.adapter.InspectCaiLiaoAdapter;
import com.haozhiyan.zhijian.adapter.InspectLookCaiLiaoAdapter;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.bean.AMEnterAreaBean;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Brand;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Specification;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Type;
import com.haozhiyan.zhijian.bean.InspectInfoBean;
import com.haozhiyan.zhijian.bean.clysSpecificationListBean;
import com.haozhiyan.zhijian.bean.clysSpecificationListOneBean;
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
 */
public class AM_InspectFragment extends Fragment implements View.OnClickListener {
    private View normalView;
    private View changeView;
    private RelativeLayout parentViewGroup;
    public String id;
    private String times;
    private InspectInfoBean infoBean;
    private RecyclerView clysBrandListRCV;
    private RecyclerView inspectImageRCV;
    private List<AMEnterAreaBean.ApproachBean.ClysBrandListBean> clysBrandList;

    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList;
    private String inspector;
    private String inspectorName;
    private TextView inspectorTV;
    private TextView approachDateTV;
    private InspectCaiLiaoAdapter caiLiaoAdapter;
    private LinearLayout inspectHistoryLL;


    public static AM_InspectFragment build(String id) {
        AM_InspectFragment fragment = new AM_InspectFragment();
        fragment.id = id;
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
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
                    infoClysInspect();
                }
            } else {
                infoClysInspect();
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
            case R.id.approachDateLL://送检时间
                Calendar.check(this, approachDateTV.getText().toString().trim(), Calendar.SELECTDATE);
                break;
            case R.id.inspectorLL://送检人
                SelectTRPOrAU.select(this, inspector, SelectTRPOrAU.inspector);//送检人
                break;
            case R.id.inspectHistoryLL://首次送检记录
                try {
                    InspectInfoBean.ClysInspectListBean clysBrandListBean = null;
                    try {
                        o:
                        for (int i = 0; i < infoBean.getClysInspectList().size(); i++) {
                            if (infoBean.getClysInspectList().get(i).getTimes().equals("1")) {
                                clysBrandListBean = infoBean.getClysInspectList().get(i);
                                break o;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(getContext(), FirstCheckHistory.class);
                    intent.putExtra("bean", clysBrandListBean);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.commitBT:
                UpLoadUtil.init(getContext(), selectList,true).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        inspect(paths);
                    }
                });
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<AMEnterAreaBean.ApproachBean.ClysBrandListBean> event) {
        if (event != null) {
            clysBrandList = event;
            if (clysBrandListRCV != null && (CLYSState.equals("4") || CLYSState.equals("6"))) {
                setClysBrandListRCV();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if (event != null) {
            if (event.startsWith("clysTaskInspectorId=") && StringUtils.isEmpty(inspector)) {
                try {
                    String[] Inspector = event.split(",");
                    inspector = Inspector[0].replace("clysTaskInspectorId=", "");
                    inspectorName = Inspector[1].replace("clysTaskInspectorName=", "");
                    infoClysInspect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private RecyclerView inspectImageListRCV;
    private TextView inspectDateTV;
    private TextView inspectTimeTV;
    private ImageView inspectorTalkImg;

    private void initView() {
        normalView = LayoutInflater.from(getContext()).inflate(R.layout.am_inspect_fg_layout, parentViewGroup, false);
//        normalView.findViewById(R.id.changeBT).setOnClickListener(this);
        parentViewGroup.addView(normalView);
        inspectHistoryLL = normalView.findViewById(R.id.inspectHistoryLL);
        inspectHistoryLL.setOnClickListener(this);
        if (times.equals("2")) {
            inspectHistoryLL.setVisibility(View.VISIBLE);
        }
        inspectorTV = normalView.findViewById(R.id.inspectorTV);
        inspectDateTV = normalView.findViewById(R.id.inspectDateTV);
        inspectTimeTV = normalView.findViewById(R.id.inspectTimeTV);
        inspectImageListRCV = normalView.findViewById(R.id.inspectImageListRCV);
        clysBrandListRCV = normalView.findViewById(R.id.clysBrandListRCV);
        inspectorTalkImg = normalView.findViewById(R.id.inspectorTalkImg);
        clysBrandListRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<MultiItemEntity> data = new ArrayList<>();

        InspectInfoBean.ClysInspectListBean clysBrandListBean = null;
        try {
            o:
            for (int i = 0; i < infoBean.getClysInspectList().size(); i++) {
                if (infoBean.getClysInspectList().get(i).getTimes().equals((infoBean.getClysInspectList().size() + ""))) {
                    clysBrandListBean = infoBean.getClysInspectList().get(i);
                    break o;
                }
            }
            setPicRcv(inspectImageListRCV, clysBrandListBean.getInspectImageList());
            inspectDateTV.setText(clysBrandListBean.getInspectDate() + "");
            inspectTimeTV.setText(clysBrandListBean.getInspectTime() + "");

            final InspectInfoBean.ClysInspectListBean finalClysBrandListBean = clysBrandListBean;

            inspectorTV.setText(finalClysBrandListBean.getInspectorList().get(0).peopleuser + "");

            inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        for (int i = 0; i < finalClysBrandListBean.getInspectorList().size(); i++) {
                            if (String.valueOf(finalClysBrandListBean.getInspector())
                                    .equals(String.valueOf(finalClysBrandListBean.getInspectorList().get(i).userId))) {
                                SystemUtils.callPage(finalClysBrandListBean.getInspectorList().get(i).tel, getContext());
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
        try {
            for (int i = 0; i < clysBrandListBean.getClysBrandList().size(); i++) {
                InspectInfoBean.ClysInspectListBean.ClysBrandListBean brandListBean = clysBrandListBean.getClysBrandList().get(i);
                AM_Enter_Brand brand = new AM_Enter_Brand(brandListBean.getBandName(), i);
                for (int j = 0; j < brandListBean.getClysTypeList().size(); j++) {
                    InspectInfoBean.ClysInspectListBean.ClysBrandListBean.ClysTypeListBean typeListBean = brandListBean.getClysTypeList().get(j);
                    AM_Enter_Type type = new AM_Enter_Type(typeListBean.getTypeName(), j);
                    for (int k = 0; k < typeListBean.getClysSpecificationList().size(); k++) {
                        InspectInfoBean.ClysInspectListBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean
                                specificationListBean = typeListBean.getClysSpecificationList().get(k);
                        AM_Enter_Specification specification = new AM_Enter_Specification(
                                times.equals("2") ? specificationListBean.getInspectNumberTwo() : specificationListBean.getInspectNumber(),
                                specificationListBean.getSpecificationName(), specificationListBean.getUnit(), k);
                        type.addSubItem(specification);
                    }
                    brand.addSubItem(type);
                }
                data.add(brand);
            }
            InspectLookCaiLiaoAdapter lookCaiLiaoAdapter = new InspectLookCaiLiaoAdapter(data, getActivity());
            clysBrandListRCV.setAdapter(lookCaiLiaoAdapter);
            lookCaiLiaoAdapter.expandAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void changeLayout() {
        changeView = LayoutInflater.from(getContext()).inflate(R.layout.am_inspect_change_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
        changeView.findViewById(R.id.approachDateLL).setOnClickListener(this);
        changeView.findViewById(R.id.inspectorLL).setOnClickListener(this);
        changeView.findViewById(R.id.commitBT).setOnClickListener(this);
        clysBrandListRCV = changeView.findViewById(R.id.clysBrandListRCV);
        clysBrandListRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        inspectImageRCV = changeView.findViewById(R.id.inspectImageRCV);
        approachDateTV = changeView.findViewById(R.id.approachDateTV);
        inspectorTV = changeView.findViewById(R.id.inspectorTV);
        inspectHistoryLL = changeView.findViewById(R.id.inspectHistoryLL);
        inspectHistoryLL.setOnClickListener(this);

        if (times.equals("1") || times.equals("2")) {
            inspectHistoryLL.setVisibility(View.VISIBLE);
        }

        if (clysBrandList != null) {
            setClysBrandListRCV();
        }
        inspectorTV.setText(UserInfo.create(getContext()).getUserName());

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        approachDateTV.setText(calendar.get(java.util.Calendar.YEAR)
                + "-" + (calendar.get(java.util.Calendar.MONTH) + 1)
                + "-" + (calendar.get(java.util.Calendar.DAY_OF_MONTH)));

        setPictureSelectRcv(inspectImageRCV);
    }

    /**
     * 材料验收-根据任务id查询送检记录详情
     */
    private void infoClysInspect() {
        HttpRequest.get(getContext())
                .url(ServerInterface.infoClysInspect)
                .params("taskId", id)//任务id
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            infoBean = new Gson().fromJson(result.toString(), InspectInfoBean.class);
                            boolean isMe = false;
                            try {

                                if (infoBean.getClysInspectList().size() >= 2) {
                                    times = "2";
                                } else {
                                    times = infoBean.getClysInspectList().size() + "";
                                }

                                if (String.valueOf(inspector).
                                        equals(UserInfo.create(getContext()).getUserId())) {
                                    isMe = true;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                times = "0";
                            }
                            if (TextUtils.equals(ENTRANCE, "form")) {
                                if(CLYSState.equals("4") || CLYSState.equals("6")){
                                    // 如果第一次复验 显示暂无记录
                                    if ("0".equals(times)) {
                                        noHistoryLayout();
                                    } else {
                                        initView();
                                    }
                                }else{
                                    initView();
                                }
                            } else {
                                //待送检状态和 待复验状态 需要编辑
                                //送检人是自己 是的话显示可编辑进场  不是的话就显示暂无记录
                                if (CLYSState.equals("4") || CLYSState.equals("6")) {
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
                    break;
            }
        } else if (requestCode == Calendar.SELECTDATE) {//日期
            try {
                Object date = data.getBundleExtra("bundle").getSerializable("selectCalendar");
                if (date instanceof com.haibin.calendarview.Calendar) {
                    com.haibin.calendarview.Calendar calendar = (com.haibin.calendarview.Calendar) date;
                    approachDateTV.setText(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == SelectTRPOrAU.inspector) {///送检人
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            inspector = bundle.getString("id");
            inspectorTV.setText(jianShePerson);
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
                            for (int i = 0; i < imageArray.length(); i++) {
                                LocalMedia localMedia = new LocalMedia();
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
     * 编辑送检 材料数量
     */
    private void setClysBrandListRCV() {
        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < clysBrandList.size(); i++) {
            AMEnterAreaBean.ApproachBean.ClysBrandListBean brandListBean = clysBrandList.get(i);
            AM_Enter_Brand brand = new AM_Enter_Brand(brandListBean.bandName, i);
            for (int j = 0; j < brandListBean.clysTypeList.size(); j++) {
                AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean typeListBean = brandListBean.clysTypeList.get(j);
                AM_Enter_Type type = new AM_Enter_Type(typeListBean.typeName, j);
                for (int k = 0; k < typeListBean.clysSpecificationList.size(); k++) {
                    AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean
                            specificationListBean = typeListBean.clysSpecificationList.get(k);
                    AM_Enter_Specification specification
                            = new AM_Enter_Specification("",
                            specificationListBean.specificationName, "组", k);
                    specification.id = specificationListBean.id + "";
                    type.addSubItem(specification);
                }
                brand.addSubItem(type);
            }
            data.add(brand);
        }
        caiLiaoAdapter = new InspectCaiLiaoAdapter(data, getActivity());
        clysBrandListRCV.setAdapter(caiLiaoAdapter);
        caiLiaoAdapter.expandAll();
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
            PhotoCameraUtils.init(AM_InspectFragment.this).photoDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 材料验收-提交送检记录
     */
    private void inspect(String paths) {

        String obj2;
        if (times.equals("0")) {
            obj2 = getclysSpecificationListJson();
        } else {
            obj2 = getclysSpecificationListJson2();
        }
        if (StringUtils.isEmpty(obj2)) {
            return;
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.inspect)
                .params("taskId", id)//任务id
                .params("inspectImage", paths)//送检照片：图片
                .params("inspectDate", approachDateTV.getText().toString().trim())//送检时间
                .params("times", times.equals("0") ? "1" : "2")//第几次送检：第一次为1，第二次为2
                .params("clysSpecificationListStr", obj2)//规格list：第一次送检
                .params("inspector", inspector)//送检
                .params("inspectTime", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//送检shijian
                // "clysSpecificationList":[{"id":"11","inspectNumber":"1"},{"id":"12","inspectNumber":"2"}
                // ,{"id":"13","inspectNumber":"3"},{"id":"14","inspectNumber":"4"}]，
                // 第二次送检"clysSpecificationList":[{"id":"11","inspectNumberTwo":"1"},{"id":"12","inspectNumberTwo"
                // :"2"},{"id":"13","inspectNumberTwo":"3"},{"id":"14","inspectNumberTwo":"4"}]
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

    /**
     * 对象转 json
     *
     * @return
     */
    private String getclysSpecificationListJson() {
        try {
            List<MultiItemEntity> entities = caiLiaoAdapter.getData();
//            clysSpecificationListOneBean brandBean = new clysSpecificationListOneBean();
            List<clysSpecificationListOneBean.ClysSpecificationListBean> clysBrandList = new ArrayList<>();
            o:
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i) instanceof AM_Enter_Brand) {
                    AM_Enter_Brand brand = (AM_Enter_Brand) entities.get(i);
                    for (int j = 0; j < brand.getSubItems().size(); j++) {
                        AM_Enter_Type type = brand.getSubItems().get(j);
                        if (type.position != Integer.MAX_VALUE) {
                            for (int k = 0; k < type.getSubItems().size(); k++) {
                                AM_Enter_Specification specification = type.getSubItems().get(k);
                                if (specification.position != Integer.MAX_VALUE) {
                                    if (StringUtils.isEmpty(specification.number)) {
                                        ToastUtils.myToast(getContext(), "请填写数量");
                                        break o;
                                    } else {
                                        clysSpecificationListOneBean.ClysSpecificationListBean clysSpecificationListBean = new clysSpecificationListOneBean.ClysSpecificationListBean();
                                        clysSpecificationListBean.setId(specification.id);
                                        clysSpecificationListBean.setInspectNumber(specification.number);
                                        clysBrandList.add(clysSpecificationListBean);
                                    }
                                }
                            }
                        }
                    }
                }
            }
//            brandBean.setClysSpecificationList(clysBrandList);
            if (clysBrandList.size() > 0) {
                Gson gson = new Gson();
                return gson.toJson(clysBrandList);
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 对象转 json
     *
     * @return
     */
    private String getclysSpecificationListJson2() {
        try {
            List<MultiItemEntity> entities = caiLiaoAdapter.getData();
//            clysSpecificationListBean brandBean = new clysSpecificationListBean();
            List<clysSpecificationListBean.ClysSpecificationListBean> clysBrandList = new ArrayList<>();
            o:
            for (int i = 0; i < entities.size(); i++) {
                if (entities.get(i) instanceof AM_Enter_Brand) {
                    AM_Enter_Brand brand = (AM_Enter_Brand) entities.get(i);
                    for (int j = 0; j < brand.getSubItems().size(); j++) {
                        AM_Enter_Type type = brand.getSubItems().get(j);
                        if (type.position != Integer.MAX_VALUE) {
                            for (int k = 0; k < type.getSubItems().size(); k++) {
                                AM_Enter_Specification specification = type.getSubItems().get(k);
                                if (specification.position != Integer.MAX_VALUE) {
                                    if (StringUtils.isEmpty(specification.number)) {
                                        ToastUtils.myToast(getActivity(), "请填写数量");
                                        break o;
                                    } else {
                                        clysSpecificationListBean.ClysSpecificationListBean clysSpecificationListBean = new clysSpecificationListBean.ClysSpecificationListBean();
                                        clysSpecificationListBean.setId(specification.id);
                                        clysSpecificationListBean.setInspectNumberTwo(specification.number);
                                        clysBrandList.add(clysSpecificationListBean);
                                    }

                                }
                            }
                        }
                    }
                }
            }
//            brandBean.setClysSpecificationList(clysBrandList);
            Gson gson = new Gson();
            return gson.toJson(clysBrandList);
        } catch (Exception e) {
            return "";
        }
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

}
