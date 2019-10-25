package com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
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
import com.haozhiyan.zhijian.adapter.AM_EnterPicItemsShowAdapter;
import com.haozhiyan.zhijian.adapter.AcceotanceMaterialsEnterAdapter;
import com.haozhiyan.zhijian.adapter.AcceptanceListAdapter;
import com.haozhiyan.zhijian.adapter.EnterLookCaiLiaoAdapter;
import com.haozhiyan.zhijian.adapter.OnlyCameraListAdapter;
import com.haozhiyan.zhijian.adapter.PersonNameListAdapter;
import com.haozhiyan.zhijian.bean.AMEnterAreaBean;
import com.haozhiyan.zhijian.bean.AMEnterBrandBean;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Brand;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Specification;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Type;
import com.haozhiyan.zhijian.bean.ReceiveImageUploadBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.listener.UpLoadListCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PictureSelectorConfig;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.TimeFormatUitls;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials.CLYSState;
import static com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials.ENTRANCE;

/**
 * A simple {@link Fragment} subclass.
 * 进场
 */
public class AM_EnterAreaFragment extends Fragment implements View.OnClickListener,
        BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {
    public String id;
    private AMEnterAreaBean areaBean;
    private RelativeLayout parentViewGroup;

    public String sectionId;
    public String receiveUnitName;//接收单位name
    public String receive;//接收人ID
    public String receiveName;//接收人name
    public String atype;//0待办 1已办 2抄送 从待办传过来的
    private int clickPosition;//被点击的角标值
    private int clickCameraProsition;//被点击进行拍照的控件标记

    private ArrayList<MultiItemEntity> multiItemEntities;
    private AcceotanceMaterialsEnterAdapter enterAdapter;
    private List<String> receptionNames;
    private List<OnlyCameraListAdapter> adapters;
    private List<List<LocalMedia>> selectLists;
    private OnlyCameraListAdapter adapter;
    private List<LocalMedia> selectList;
    private List<String> fileList = new ArrayList<>();
    private int level1Position = 0;
    private AM_Enter_Brand brand;
    private AM_Enter_Type type;
    private AM_Enter_Specification specification;
    private String supervisor;
    private String cc;
    private String inspector;
    private String acceptance;

    private TextView nameInspectionTV;
    private TextView receiveUnitNameTV;
    private TextView receiveNameTV;
    private TextView supervisorNameTV;
    private TextView acceptanceNameTV;
    private TextView ccNameTV;
    private TextView approachDateTV;
    private TextView descNumTv;
    //    private RecyclerView receiveVehicleImageRcv;
//    private RecyclerView receiveMaterialImageRCV;
//    private RecyclerView receiveCertificateImageRCV;
//    private RecyclerView receiveTextureImageRCV;
    private EditText receiveSupplementET;

    private RecyclerView clysBrandListRCV;
    //    private RecyclerView receiveVehicleImageRCV;
    private RecyclerView ccListRcv;
    //    private RecyclerView supervisorVehicleImageRCV;
//    private RecyclerView supervisorMaterialImageRCV;
//    private RecyclerView supervisorCertificateImageRCV;
//    private RecyclerView supervisorTextureImageRCV;
//    private RecyclerView supervisorSceneImageRCV;
    private TextView receiveSupplementTV;
    private TextView submitDateTV;
    private TextView inspectorTV;
    private LinearLayout inspectorLL;
    private Button isInspectBT1;
    private Button isInspectBT2;
    private int isInspect = -1;
    private TextView supervisorDateTV;
    private TextView supervisorSupplementTV;
    private TextView inspectorNameTV;
    private RecyclerView acceptanceImageRCV;
    private EditText acceptanceSupplementET;
    private String isInTaskuserType = "";//在任务中的身份

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);
        if (rootView != null) {
            parentViewGroup = rootView.findViewById(R.id.parentViewGroup);
        }
        try {
            infoApproach();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

    /**
     * 获取进场信息
     */
    private void infoApproach() {
        HttpRequest.get(getContext())
                .url(ServerInterface.infoApproach)
                .params("id", id)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            areaBean = new Gson().fromJson(result.toString(), AMEnterAreaBean.class);
                            try {
                                EventBus.getDefault().post(areaBean.approach.clysBrandList);
                                EventBus.getDefault().post("clysTaskInspectorId=" + areaBean.approach.inspector + ","
                                        + "clysTaskInspectorName=" + areaBean.approach.inspectorName);
                                EventBus.getDefault().post("clysTaskSupervisorId=" + areaBean.approach.supervisor + ","
                                        + "clysTaskSupervisorName=" + areaBean.approach.supervisorName);
                                SPUtil.get(getContext()).save("clysTaskInspectorId=", areaBean.approach.inspector);
                                SPUtil.get(getContext()).save("clysTaskSupervisorId=", areaBean.approach.supervisor);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (areaBean.code == 0) {
                                if (TextUtils.equals(ENTRANCE, "form")) {
                                    if("1".equals(CLYSState)){
                                        noHistoryLayout();
                                    }else{
                                        showLayout();
                                    }
                                } else {


                                    cc = areaBean.approach.cc;
                                    //状态(1待申请进场，2待验收，3已验收，4待送检，5待上传报告，6待复验，7待退场，8已退场，9送检合格，10复验合格)
                                    switch (CLYSState) {
                                        case "1":
                                            //接收人是否是自己 是的话显示可编辑进场  不是的话就显示暂无记录
                                            if (String.valueOf(areaBean.approach.receive).
                                                    equals(UserInfo.create(getContext()).getUserId())) {
                                                jinchangLayout();
                                                getClysImage();
                                            } else {
                                                noHistoryLayout();
                                            }
                                            break;
                                        case "2":
                                            //如果是从待办过来的  并且建设验收人中
                                            // 有自己 说明 当前验收顺序是 监理和建设可以同时验收
                                            // 那么就根据自己在任务中的身份显示 监理或 建设的验收布局
                                            if ("0".equals(atype)) {
                                                try {
                                                    //先判断自己在任务中是否是监理 是的话 显示监理布局
                                                    //不是的话就继续判断自己在任务中是否是建设验收人
                                                    if (String.valueOf(areaBean.approach.supervisor).
                                                            equals(UserInfo.create(getContext()).getUserId())) {
                                                        //未验收 为2
                                                        //自己是监理的话 就判断自己是否已经验收过了
                                                        if ("2".equals(areaBean.approach.supervisorResult)) {
                                                            isInTaskuserType = "2";
                                                            JLyanshouLayout();
                                                            getClysImage();
                                                        } else {
                                                            showLayout();
                                                        }
                                                    } else {
                                                        try {
                                                            for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                                                                if (String.valueOf(areaBean.approach.acceptanceList.
                                                                        get(i).userId).equals(UserInfo.create(getContext()).getUserId())) {
                                                                    // 自己是建设验收人的话 就判断自己是否已经验收过
                                                                    if ("2".equals(areaBean.approach.acceptanceList.
                                                                            get(i).isQualified)) {
                                                                        isInTaskuserType = "3";
                                                                        JianSheyanshouLayout();
                                                                    } else {
                                                                        showLayout();
                                                                    }
                                                                    return;
                                                                }
                                                            }
                                                            showLayout();
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    showLayout();
                                                }

                                            } else {//如果不是从待办过来的 就按照先监理验收 后建设验收的顺序显示
                                                if ("2".equals(areaBean.approach.supervisorResult)) {//监理未验收 为2
                                                    try {
                                                        if (String.valueOf(areaBean.approach.supervisor).
                                                                equals(UserInfo.create(getContext()).getUserId())) {
                                                            isInTaskuserType = "2";
                                                            JLyanshouLayout();
                                                            getClysImage();
                                                        } else {
                                                            showLayout();
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                        showLayout();
                                                    }
                                                } else {
                                                    try {
                                                        for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                                                            if (String.valueOf(areaBean.approach.acceptanceList.
                                                                    get(i).userId).equals(UserInfo.create(getContext()).getUserId())) {
                                                                if (!"2".equals(areaBean.approach.acceptanceList.
                                                                        get(i).isQualified)) {//当前验收人 已验收过 就显示 展示布局
                                                                    showLayout();
                                                                } else {
                                                                    isInTaskuserType = "3";
                                                                    JianSheyanshouLayout();
                                                                }
                                                                return;
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    showLayout();
                                                }
                                            }

                                            break;
                                        case "3":
                                        case "4":
                                        case "5":
                                        case "6":
                                        case "9":
                                        case "10":
                                        case "7":
                                        case "8":
                                            showLayout();
                                            break;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.changeBT://申请进场验收
                final String json = getclysBrandListStrJson();
                if (json != null)
                    UpLoadUtil.initList(getContext(), selectLists, true).Call(new UpLoadListCallBack() {
                        @Override
                        public void onComplete(List<List<String>> paths) {
                            updateClysTask(paths, json);
                        }
                    });
                break;
            case R.id.enterCheckPassBT://进场验收合格
                supervisorResult = true;
                if (isInTaskuserType.equals("2")) {//监理
                    UpLoadUtil.initList(getContext(), selectLists, true).Call(new UpLoadListCallBack() {
                        @Override
                        public void onComplete(List<List<String>> paths) {
                            supervisorQualified(paths);
                        }
                    });
                } else if (isInTaskuserType.equals("3")) {//建设单位验收人
                    UpLoadUtil.init(getContext(), selectList, true).Call(new UpLoadCallBack() {
                        @Override
                        public void onComplete(String paths) {
                            acceptanceQualified(paths);
                        }
                    });
                }
                break;
            case R.id.enterCheckLossBT://进场验收不合格
                supervisorResult = false;
                if (isInTaskuserType.equals("2")) {//监理
                    try {
                        if (StringUtils.isEmpty(receiveSupplementET.getText().toString().trim())) {
                            ToastUtils.myToast(getActivity(), "请请填写说明");
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.myToast(getActivity(), "请请填写说明");
                        return;
                    }
                    UpLoadUtil.initList(getContext(), selectLists, true).Call(new UpLoadListCallBack() {
                        @Override
                        public void onComplete(List<List<String>> paths) {
                            supervisorQualified(paths);
                        }
                    });
                } else if (isInTaskuserType.equals("3")) {//建设单位验收人
                    try {
                        if (StringUtils.isEmpty(acceptanceSupplementET.getText().toString().trim())) {
                            ToastUtils.myToast(getActivity(), "请填写说明");
                            return;
                        }
                    } catch (Exception e) {
                        ToastUtils.myToast(getActivity(), "请填写说明");
                        return;
                    }
                    UpLoadUtil.init(getContext(), selectList, true).Call(new UpLoadCallBack() {
                        @Override
                        public void onComplete(String paths) {
                            acceptanceQualified(paths);
                        }
                    });
                }
                break;
            case R.id.addNewBrand:
                //新增品牌
                level1Position++;
                AM_Enter_Brand lv1 = new AM_Enter_Brand("请选择", level1Position);
                AM_Enter_Type lv2 = new AM_Enter_Type("新增", Integer.MAX_VALUE);
                lv1.addSubItem(lv2);
                multiItemEntities.add(lv1);
                enterAdapter.notifyDataSetChanged();
                enterAdapter.expandAll();
                break;
            case R.id.enterTimeLL://进场时间
                Calendar.check(this, approachDateTV.getText().toString().trim(), Calendar.SELECTDATE);
                break;
            case R.id.chaosongLL://抄送人
                SelectTRPOrAU.select(this, cc, SelectTRPOrAU.COPY_PEOPLE);
                break;
            case R.id.supervisorLL://监理
                SelectTRPOrAU.select(this, supervisor, SelectTRPOrAU.Supervisor);//监理
                break;
            case R.id.inspectorLL://送检人
                SelectTRPOrAU.select(this, inspector, SelectTRPOrAU.inspector);//送检人
                break;
            case R.id.acceptanceLL://建设单位验收人
                SelectTRPOrAU.select(AM_EnterAreaFragment.this, UserInfo.create(getContext()).getUserId(), SelectTRPOrAU.ConstructionDirector);//建设单位
                break;
        }
    }

    protected void jumpActivityForResult(Class<? extends Activity> actClass, int resultCode) {
        Constant.REQUEST_CODE = resultCode;
        startActivityForResult(new Intent(getContext(), actClass), resultCode);
    }

    protected void jumpActivityForResult(Class<? extends Activity> actClass, int resultCode, Bundle bundle) {
        Constant.REQUEST_CODE = resultCode;
        Intent intent = new Intent(getContext(), actClass);
        intent.putExtra("data", bundle);
        startActivityForResult(intent, resultCode);
    }

    /**
     * 添加材料的点击
     *
     * @param ad
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(BaseQuickAdapter ad, View view, int position) {

        if (ad instanceof AcceotanceMaterialsEnterAdapter) {
            clickPosition = position;
            Object o = enterAdapter.getData().get(position);
            if (o instanceof AM_Enter_Type) {
                type = (AM_Enter_Type) o;
                if (type.position == Integer.MAX_VALUE) {
                    MultiItemEntity parent = enterAdapter.getData().get(enterAdapter.getParentPositionInAll(position));
                    if (parent instanceof AM_Enter_Brand) {
                        AM_Enter_Type newLv2 = new AM_Enter_Type("请选择",
                                0);
                        AM_Enter_Specification newLv3 = new AM_Enter_Specification(
                                "", "", "", Integer.MAX_VALUE);
                        newLv2.addSubItem(newLv3);
                        enterAdapter.addData(position, newLv2);
                        ((AM_Enter_Brand) parent).addSubItem(newLv2);
                        enterAdapter.expandAll();
                    }
                } else { //选择材料类型
                    SelectTRPOrAU.select(this, type.typeName, sectionId,
                            areaBean.approach.nameInspectionId, SelectTRPOrAU.SingleMaterialsType);//材料类型
                }
            } else if (o instanceof AM_Enter_Specification) {
                specification = (AM_Enter_Specification) o;
                if (specification.position == Integer.MAX_VALUE) {//添加
                    if (StringUtils.isEmpty(specification.specificationName)) {
                        MultiItemEntity parent = enterAdapter.getData().get(enterAdapter.getParentPositionInAll(position));
                        if (parent instanceof AM_Enter_Type) {//选择规格
                            AM_Enter_Type type = (AM_Enter_Type) parent;
                            if (TextUtils.isEmpty(type.id)) {
                                ToastUtils.myToast(getContext(), "请先选择材料类型");
                                return;
                            }
                            SelectTRPOrAU.select(this, specification.specificationName,
                                    "", type.id, SelectTRPOrAU.BrandSpecifi);//选择规格
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < adapters.size(); i++) {
                if (ad == adapters.get(i)) {
                    if (position == adapters.get(i).getItemCount() - 1) {
                        if (selectLists.get(i).size() >= 6) {
                            ToastUtils.myToast(getContext(), "图片数量已到上限");
                            return;
                        }
                        clickCameraProsition = i;
                        isacceptance = false;
                        PictureSelectorConfig.openCameraImg(AM_EnterAreaFragment.this, PictureConfig.CHOOSE_REQUEST);
                    } else {
                        int pictureType = PictureMimeType.isPictureType(selectLists.get(i).get(position).getPictureType());
                        if (pictureType == PictureConfig.TYPE_VIDEO) {
                            ShowVideo.playLocalVideo(getContext(), selectLists.get(i).get(position).getPath());
                        } else {
                            if (selectLists.get(i).get(position).isCut()) {
                                ShowBigImg.build(getContext(), selectLists.get(i).get(position).getCutPath());
                            } else {
                                ShowBigImg.build(getContext(), selectLists.get(i).get(position).getPath());
                            }
                        }
                    }

                    break;
                }
            }
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        Object o = enterAdapter.getData().get(position);
        switch (view.getId()) {
            case R.id.delete_img:
                enterAdapter.remove(position);
                enterAdapter.notifyDataSetChanged();
                break;
            case R.id.BrandLL://选择品牌
                if (o instanceof AM_Enter_Brand) {
                    brand = (AM_Enter_Brand) o;
                    SelectTRPOrAU.select(this, brand.bandName, "", areaBean.approach.nameInspectionId, SelectTRPOrAU.Brand);//选择品牌
                }
                break;
        }

    }

    private RecyclerView acceptanceListRCV;
    private RecyclerView receiveImageRCV;
    private RecyclerView supervisorImageRCV;
    private LinearLayout inspectorNameLL;
    private ImageView supervisorTalkImg;
    private ImageView inspectorTalkImg;
    private ImageView receiveTalkImg;

    /**
     * 只展示数据布局
     */
    private void showLayout() {
        View normalView = LayoutInflater.from(getContext()).inflate(R.layout.am_enter_fg_layout_show, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(normalView);

        clysBrandListRCV = normalView.findViewById(R.id.clysBrandListRCV);
        receiveSupplementTV = normalView.findViewById(R.id.receiveSupplementTV);
        receiveUnitNameTV = normalView.findViewById(R.id.receiveUnitNameTV);
        receiveNameTV = normalView.findViewById(R.id.receiveNameTV);
        submitDateTV = normalView.findViewById(R.id.submitDateTV);
        supervisorNameTV = normalView.findViewById(R.id.supervisorNameTV);
        ccListRcv = normalView.findViewById(R.id.ccListRcv);
        approachDateTV = normalView.findViewById(R.id.approachDateTV);
        supervisorDateTV = normalView.findViewById(R.id.supervisorDateTV);
        supervisorSupplementTV = normalView.findViewById(R.id.supervisorSupplementTV);
        inspectorNameTV = normalView.findViewById(R.id.inspectorNameTV);
        acceptanceNameTV = normalView.findViewById(R.id.acceptanceNameTV);
        acceptanceImageRCV = normalView.findViewById(R.id.acceptanceImageRCV);
        acceptanceListRCV = normalView.findViewById(R.id.acceptanceListRCV);
        supervisorResultTV = normalView.findViewById(R.id.supervisorResultTV);
        inspectorNameLL = normalView.findViewById(R.id.inspectorNameLL);
        supervisorTalkImg = normalView.findViewById(R.id.supervisorTalkImg);
        inspectorTalkImg = normalView.findViewById(R.id.inspectorTalkImg);
        receiveTalkImg = normalView.findViewById(R.id.receiveTalkImg);
        receiveImageRCV = normalView.findViewById(R.id.receiveImageRCV);
        supervisorImageRCV = normalView.findViewById(R.id.supervisorImageRCV);


        setPicRcv(receiveImageRCV, areaBean.approach.receiveImageList);
        setPicRcv(supervisorImageRCV, areaBean.approach.supervisorImageList);

        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < areaBean.approach.clysBrandList.size(); i++) {
            AMEnterAreaBean.ApproachBean.ClysBrandListBean brandListBean = areaBean.approach.clysBrandList.get(i);
            AM_Enter_Brand brand = new AM_Enter_Brand(brandListBean.bandName, i);
            for (int j = 0; j < brandListBean.clysTypeList.size(); j++) {
                AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean typeListBean = brandListBean.clysTypeList.get(j);
                AM_Enter_Type type = new AM_Enter_Type(typeListBean.typeName, j);
                for (int k = 0; k < typeListBean.clysSpecificationList.size(); k++) {
                    AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean specificationListBean = typeListBean.clysSpecificationList.get(k);
                    AM_Enter_Specification specification = new AM_Enter_Specification(specificationListBean.number, specificationListBean.specificationName, specificationListBean.unit, k);
                    type.addSubItem(specification);
                }
                brand.addSubItem(type);
            }
            data.add(brand);
        }
        EnterLookCaiLiaoAdapter lookCaiLiaoAdapter = new EnterLookCaiLiaoAdapter(data, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        clysBrandListRCV.setAdapter(lookCaiLiaoAdapter);
        clysBrandListRCV.setNestedScrollingEnabled(false);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        clysBrandListRCV.setHasFixedSize(true);
        clysBrandListRCV.setLayoutManager(manager);
        lookCaiLiaoAdapter.expandAll();
        approachDateTV.setText(areaBean.approach.approachDate + "");


        acceptanceListRCV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        AcceptanceListAdapter acceptanceListAdapter = new AcceptanceListAdapter(getActivity(), areaBean.approach.acceptanceList);
        acceptanceListRCV.setAdapter(acceptanceListAdapter);

        try {
            if (StringUtils.isEmpty(areaBean.approach.receiveSupplement)) {
                if (receiveSupplementTV.getParent() instanceof ViewGroup) {
                    ((ViewGroup) receiveSupplementTV.getParent()).setVisibility(View.GONE);
                }
            } else {
                receiveSupplementTV.setText(areaBean.approach.receiveSupplement + "");
            }
        } catch (Exception e) {

        }

        if (StringUtils.isEmpty(areaBean.approach.supervisorSupplement)) {
            if (supervisorSupplementTV.getParent() instanceof ViewGroup) {
                ((ViewGroup) supervisorSupplementTV.getParent()).setVisibility(View.GONE);
            }
        } else {
            supervisorSupplementTV.setText(areaBean.approach.supervisorSupplement);
        }

        if (StringUtils.isEmpty(areaBean.approach.inspectorName)) {
            inspectorNameLL.setVisibility(View.GONE);
        } else {
            inspectorNameTV.setText(areaBean.approach.inspectorName);
        }

        receiveUnitNameTV.setText(receiveUnitName);
        receiveNameTV.setText(receiveName);
        submitDateTV.setText(areaBean.approach.receiveDate);


        supervisorNameTV.setText(areaBean.approach.supervisorName);
        try {
            supervisor = areaBean.approach.supervisor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(areaBean.approach.supervisorDate)) {
            supervisorDateTV.setVisibility(View.GONE);
        } else {
            supervisorDateTV.setVisibility(View.VISIBLE);
            supervisorDateTV.setText(areaBean.approach.supervisorDate);
        }
        if ("0".equals(areaBean.approach.supervisorResult)) {
            supervisorResultTV.setText("不合格");
            supervisorResultTV.setTextColor(ContextCompat.getColor(getContext(), R.color.red2));
        } else if ("1".equals(areaBean.approach.supervisorResult)) {
            supervisorResultTV.setText("合格");
        } else {
            supervisorResultTV.setText("尚未验收");
        }
        supervisorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.supervisorList.size(); i++) {
                        if (String.valueOf(areaBean.approach.supervisor)
                                .equals(String.valueOf(areaBean.approach.supervisorList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.supervisorList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        receiveTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.receiveList.size(); i++) {
                        if (String.valueOf(areaBean.approach.receive)
                                .equals(String.valueOf(areaBean.approach.receiveList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.receiveList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.inspectorList.size(); i++) {
                        if (String.valueOf(areaBean.approach.inspector)
                                .equals(String.valueOf(areaBean.approach.inspectorList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.inspectorList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

        setNameRcv(ccListRcv, areaBean.approach.ccList);
    }

    private TextView supervisorResultTV;

    /**
     * 监理 待验收数据布局
     */
    private void JLyanshouLayout() {
        View normalView = LayoutInflater.from(getContext()).inflate(R.layout.am_enter_fg_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(normalView);
        normalView.findViewById(R.id.supervisorLL).setOnClickListener(this);
        normalView.findViewById(R.id.chaosongLL).setOnClickListener(this);
        normalView.findViewById(R.id.inspectorLL).setOnClickListener(this);
        normalView.findViewById(R.id.enterCheckPassBT).setOnClickListener(this);
        normalView.findViewById(R.id.enterCheckLossBT).setOnClickListener(this);

        clysBrandListRCV = normalView.findViewById(R.id.clysBrandListRCV);
        receiveSupplementTV = normalView.findViewById(R.id.receiveSupplementTV);
        receiveUnitNameTV = normalView.findViewById(R.id.receiveUnitNameTV);
        receiveNameTV = normalView.findViewById(R.id.receiveNameTV);
        submitDateTV = normalView.findViewById(R.id.submitDateTV);
        supervisorNameTV = normalView.findViewById(R.id.supervisorNameTV);
        ccListRcv = normalView.findViewById(R.id.ccListRcv);
        isInspectBT1 = normalView.findViewById(R.id.isInspectBT1);
        isInspectBT2 = normalView.findViewById(R.id.isInspectBT2);
        inspectorLL = normalView.findViewById(R.id.inspectorLL);
        inspectorTV = normalView.findViewById(R.id.inspectorTV);
        receiveSupplementET = normalView.findViewById(R.id.receiveSupplementET);
        descNumTv = normalView.findViewById(R.id.descNumTv);
        approachDateTV = normalView.findViewById(R.id.approachDateTV);
        acceptanceListRCV = normalView.findViewById(R.id.acceptanceListRCV);
        cameraImageLL = normalView.findViewById(R.id.cameraImageLL);
        supervisorTalkImg = normalView.findViewById(R.id.supervisorTalkImg);

        receiveImageRCV = normalView.findViewById(R.id.receiveImageRCV);
        inspectorTalkImg = normalView.findViewById(R.id.inspectorTalkImg);
        receiveTalkImg = normalView.findViewById(R.id.receiveTalkImg);


        setPicRcv(receiveImageRCV, areaBean.approach.receiveImageList);


        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < areaBean.approach.clysBrandList.size(); i++) {
            AMEnterAreaBean.ApproachBean.ClysBrandListBean brandListBean = areaBean.approach.clysBrandList.get(i);
            AM_Enter_Brand brand = new AM_Enter_Brand(brandListBean.bandName, i);
            for (int j = 0; j < brandListBean.clysTypeList.size(); j++) {
                AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean typeListBean = brandListBean.clysTypeList.get(j);
                AM_Enter_Type type = new AM_Enter_Type(typeListBean.typeName, j);
                for (int k = 0; k < typeListBean.clysSpecificationList.size(); k++) {
                    AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean specificationListBean = typeListBean.clysSpecificationList.get(k);
                    AM_Enter_Specification specification = new AM_Enter_Specification(specificationListBean.number, specificationListBean.specificationName, specificationListBean.unit, k);
                    type.addSubItem(specification);
                }
                brand.addSubItem(type);
            }
            data.add(brand);
        }
        EnterLookCaiLiaoAdapter lookCaiLiaoAdapter = new EnterLookCaiLiaoAdapter(data, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        clysBrandListRCV.setAdapter(lookCaiLiaoAdapter);
        clysBrandListRCV.setNestedScrollingEnabled(false);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        clysBrandListRCV.setHasFixedSize(true);
        clysBrandListRCV.setLayoutManager(manager);

        lookCaiLiaoAdapter.expandAll();

        approachDateTV.setText(areaBean.approach.approachDate + "");

        inspectorTV.setText(UserInfo.create(getContext()).getUserName());
        inspector = UserInfo.create(getContext()).getUserId();
        if (areaBean.approach.ccList.size() <= 0) {
            List<String> chaoSongName = new ArrayList<>();
            chaoSongName.add("选填");
            setNameRcv(ccListRcv, chaoSongName);
        } else {
            setNameRcv(ccListRcv, areaBean.approach.ccList);
        }

        supervisorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.supervisorList.size(); i++) {
                        if (String.valueOf(areaBean.approach.supervisor)
                                .equals(String.valueOf(areaBean.approach.supervisorList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.supervisorList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < areaBean.approach.supervisorList.size(); i++) {
                    if (String.valueOf(areaBean.approach.supervisor)
                            .equals(String.valueOf(areaBean.approach.supervisorList.get(i).userId))) {
                        inspectorTel = areaBean.approach.supervisorList.get(i).tel;
                        break;
                    }
                }
            }
        }).start();

        inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SystemUtils.callPage(inspectorTel, getContext());
                } catch (Exception e) {

                }
            }
        });
        receiveTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.receiveList.size(); i++) {
                        if (String.valueOf(areaBean.approach.receive)
                                .equals(String.valueOf(areaBean.approach.receiveList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.receiveList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        try {
            if (StringUtils.isEmpty(areaBean.approach.receiveSupplement)) {
                if (receiveSupplementTV.getParent() instanceof ViewGroup) {
                    ((ViewGroup) receiveSupplementTV.getParent()).setVisibility(View.GONE);
                }
            } else {
                receiveSupplementTV.setText(areaBean.approach.receiveSupplement + "");
            }
        } catch (Exception e) {

        }

        receiveUnitNameTV.setText(receiveUnitName);
        receiveNameTV.setText(receiveName);
        submitDateTV.setText(areaBean.approach.receiveDate);

        supervisorNameTV.setText(areaBean.approach.supervisorName);
        try {
            supervisor = areaBean.approach.supervisor;
        } catch (Exception e) {
            e.printStackTrace();
        }

        isInspectBT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInspect != 1) {
                    isInspect = 1;
                    isInspectBT1.setTextColor(ContextCompat.getColor(getContext(), R.color.blue5));
                    isInspectBT1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_wash_2radius_back));
                    isInspectBT2.setTextColor(ContextCompat.getColor(getContext(), R.color.cp_gray_deep));
                    isInspectBT2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                    inspectorLL.setVisibility(View.VISIBLE);
                }
            }
        });
        isInspectBT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInspect != 0) {
                    isInspect = 0;
                    isInspectBT2.setTextColor(ContextCompat.getColor(getContext(), R.color.blue5));
                    isInspectBT2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.blue_wash_2radius_back));
                    isInspectBT1.setTextColor(ContextCompat.getColor(getContext(), R.color.cp_gray_deep));
                    isInspectBT1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.gray_wash_2radius_back));
                    inspectorLL.setVisibility(View.GONE);
                }
            }
        });


        setDescET(receiveSupplementET, descNumTv);

        setNameRcv(acceptanceListRCV, areaBean.approach.acceptanceList);

    }


    private ImageView acceptanceTalkImg;

    /**
     * 建设单位 待验收数据布局
     */
    private void JianSheyanshouLayout() {
        View normalView = LayoutInflater.from(getContext()).inflate(R.layout.am_enter_fg_layout2, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(normalView);
        normalView.findViewById(R.id.chaosongLL).setOnClickListener(this);
        normalView.findViewById(R.id.enterCheckPassBT).setOnClickListener(this);
        normalView.findViewById(R.id.enterCheckLossBT).setOnClickListener(this);
        normalView.findViewById(R.id.acceptanceLL).setOnClickListener(this);
        clysBrandListRCV = normalView.findViewById(R.id.clysBrandListRCV);
        receiveSupplementTV = normalView.findViewById(R.id.receiveSupplementTV);
        receiveUnitNameTV = normalView.findViewById(R.id.receiveUnitNameTV);
        receiveNameTV = normalView.findViewById(R.id.receiveNameTV);
        submitDateTV = normalView.findViewById(R.id.submitDateTV);
        supervisorNameTV = normalView.findViewById(R.id.supervisorNameTV);
        ccListRcv = normalView.findViewById(R.id.ccListRcv);
        descNumTv = normalView.findViewById(R.id.descNumTv);
        approachDateTV = normalView.findViewById(R.id.approachDateTV);
        supervisorDateTV = normalView.findViewById(R.id.supervisorDateTV);
        supervisorSupplementTV = normalView.findViewById(R.id.supervisorSupplementTV);
        inspectorNameTV = normalView.findViewById(R.id.inspectorNameTV);
        acceptanceNameTV = normalView.findViewById(R.id.acceptanceNameTV);
        acceptanceImageRCV = normalView.findViewById(R.id.acceptanceImageRCV);
        acceptanceSupplementET = normalView.findViewById(R.id.acceptanceSupplementET);
        supervisorResultTV = normalView.findViewById(R.id.supervisorResultTV);
        acceptanceListRCV = normalView.findViewById(R.id.acceptanceListRCV);
        supervisorTalkImg = normalView.findViewById(R.id.supervisorTalkImg);
        inspectorTalkImg = normalView.findViewById(R.id.inspectorTalkImg);
        receiveTalkImg = normalView.findViewById(R.id.receiveTalkImg);
        acceptanceTalkImg = normalView.findViewById(R.id.acceptanceTalkImg);
        receiveImageRCV = normalView.findViewById(R.id.receiveImageRCV);
        supervisorImageRCV = normalView.findViewById(R.id.supervisorImageRCV);
        setPicRcv(receiveImageRCV, areaBean.approach.receiveImageList);
        setPicRcv(supervisorImageRCV, areaBean.approach.supervisorImageList);

        List<MultiItemEntity> data = new ArrayList<>();
        for (int i = 0; i < areaBean.approach.clysBrandList.size(); i++) {
            AMEnterAreaBean.ApproachBean.ClysBrandListBean brandListBean = areaBean.approach.clysBrandList.get(i);
            AM_Enter_Brand brand = new AM_Enter_Brand(brandListBean.bandName, i);
            for (int j = 0; j < brandListBean.clysTypeList.size(); j++) {
                AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean typeListBean = brandListBean.clysTypeList.get(j);
                AM_Enter_Type type = new AM_Enter_Type(typeListBean.typeName, j);
                for (int k = 0; k < typeListBean.clysSpecificationList.size(); k++) {
                    AMEnterAreaBean.ApproachBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean specificationListBean = typeListBean.clysSpecificationList.get(k);
                    AM_Enter_Specification specification = new AM_Enter_Specification(specificationListBean.number, specificationListBean.specificationName, specificationListBean.unit, k);
                    type.addSubItem(specification);
                }
                brand.addSubItem(type);
            }
            data.add(brand);
        }
        EnterLookCaiLiaoAdapter lookCaiLiaoAdapter = new EnterLookCaiLiaoAdapter(data, getActivity());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        clysBrandListRCV.setAdapter(lookCaiLiaoAdapter);
        clysBrandListRCV.setNestedScrollingEnabled(false);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        clysBrandListRCV.setHasFixedSize(true);
        clysBrandListRCV.setLayoutManager(manager);

        lookCaiLiaoAdapter.expandAll();

        approachDateTV.setText(areaBean.approach.approachDate + "");

        supervisorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.supervisorList.size(); i++) {
                        if (String.valueOf(areaBean.approach.supervisor)
                                .equals(String.valueOf(areaBean.approach.supervisorList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.supervisorList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

        inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.inspectorList.size(); i++) {
                        if (String.valueOf(areaBean.approach.inspector)
                                .equals(String.valueOf(areaBean.approach.inspectorList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.inspectorList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        receiveTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.receiveList.size(); i++) {
                        if (String.valueOf(areaBean.approach.receive)
                                .equals(String.valueOf(areaBean.approach.receiveList.get(i).userId))) {
                            SystemUtils.callPage(areaBean.approach.receiveList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        acceptanceTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                        if (UserInfo.create(getContext()).getUserId().equals(areaBean.approach.acceptanceList.get(i).userId)) {
                            SystemUtils.callPage(areaBean.approach.receiveList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

        try {
            if (StringUtils.isEmpty(areaBean.approach.receiveSupplement)) {
                if (receiveSupplementTV.getParent() instanceof ViewGroup) {
                    ((ViewGroup) receiveSupplementTV.getParent()).setVisibility(View.GONE);
                }
            } else {
                receiveSupplementTV.setText(areaBean.approach.receiveSupplement + "");
            }
        } catch (Exception e) {

        }
        try {
            if (TextUtils.isEmpty(areaBean.approach.inspectorName)) {
                normalView.findViewById(R.id.inspectorLL).setVisibility(View.GONE);
            } else {
                inspectorNameTV.setText(areaBean.approach.inspectorName + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        receiveUnitNameTV.setText(receiveUnitName + "");
        receiveNameTV.setText(receiveName + "");
        submitDateTV.setText(areaBean.approach.receiveDate);

        supervisorNameTV.setText(areaBean.approach.supervisorName);
        try {
            supervisor = areaBean.approach.supervisor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(areaBean.approach.supervisorDate)) {
            supervisorDateTV.setVisibility(View.GONE);
        } else {
            supervisorDateTV.setText(areaBean.approach.supervisorDate);
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final AcceptanceListAdapter acceptanceListAdapter;
                    List<AMEnterAreaBean.ApproachBean.AcceptanceListBean> acceptanceList
                            = new ArrayList<>();
                    for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                        if (!UserInfo.create(getContext()).getUserId().equals(areaBean.approach.acceptanceList.get(i).userId)) {
                            acceptanceList.add(areaBean.approach.acceptanceList.get(i));
                        }
                    }
                    acceptanceListRCV.setLayoutManager(new LinearLayoutManager(getActivity(),
                            LinearLayoutManager.VERTICAL, false));
                    acceptanceListAdapter =
                            new AcceptanceListAdapter(getActivity(), acceptanceList);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            acceptanceListRCV.setAdapter(acceptanceListAdapter);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        acceptanceNameTV.setText(UserInfo.create(getContext()).getUserName());
        acceptance = UserInfo.create(getContext()).getUserId();

        try {
            if (StringUtils.isEmpty(areaBean.approach.receiveSupplement)) {
                if (receiveSupplementTV.getParent() instanceof ViewGroup) {
                    ((ViewGroup) receiveSupplementTV.getParent()).setVisibility(View.GONE);
                }
            } else {
                receiveSupplementTV.setText(areaBean.approach.receiveSupplement + "");
            }
        } catch (Exception e) {

        }
        if (StringUtils.isEmpty(areaBean.approach.supervisorSupplement)) {
            if (supervisorSupplementTV.getParent() instanceof ViewGroup) {
                ((ViewGroup) supervisorSupplementTV.getParent()).setVisibility(View.GONE);
            }
        } else {
            supervisorSupplementTV.setText(areaBean.approach.supervisorSupplement + "");
        }
        if ("0".equals(areaBean.approach.supervisorResult)) {
            supervisorResultTV.setText("不合格");
        } else if ("1".equals(areaBean.approach.supervisorResult)) {
            supervisorResultTV.setText("合格");
        } else {
            supervisorResultTV.setText("尚未验收");
        }

        setPictureSelectRcv(acceptanceImageRCV);

        setDescET(acceptanceSupplementET, descNumTv);

        if (areaBean.approach.ccList.size() <= 0) {
            List<String> chaoSongName = new ArrayList<>();
            chaoSongName.add("选填");
            setNameRcv(ccListRcv, chaoSongName);
        } else {
            setNameRcv(ccListRcv, areaBean.approach.ccList);
        }
    }

    private NestedScrollView netScroV;
    private RecyclerView am_enter_materials_rcv;

    /**
     * 获取需要拍照的项数量及名称
     * 动态添加拍照 list
     */
    private void getClysImage() {
        HttpRequest.get(getContext())
                .url(ServerInterface.getClysImage)
                .params("taskId", id)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if ("0".equals(object.optString("code"))) {
                                adapters = new ArrayList<>();
                                receptionNames = new ArrayList<>();
                                selectLists = new ArrayList<>();
                                cameraImageLL.removeAllViews();
                                JSONArray array = object.optJSONArray("list");
                                if (array != null && array.length() > 0) {
                                    for (int i = 0; i < array.length(); i++) {
                                        View picView = LayoutInflater.from(getContext()).inflate(R.layout.am_enter_seleterimg_item,
                                                cameraImageLL, false);
                                        TextView name = picView.findViewById(R.id.name);
                                        RecyclerView imgRcv = picView.findViewById(R.id.imgRcv);
                                        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                                        manager.setSmoothScrollbarEnabled(true);
                                        manager.setAutoMeasureEnabled(true);
                                        imgRcv.setHasFixedSize(true);
                                        imgRcv.setNestedScrollingEnabled(false);
                                        imgRcv.setLayoutManager(manager);
                                        name.setText(array.optJSONObject(i).optString("name"));
                                        List<LocalMedia> localMedia = new ArrayList<>();
                                        OnlyCameraListAdapter onlyCameraListAdapter = new OnlyCameraListAdapter(getContext());
                                        onlyCameraListAdapter.setNewData(localMedia);
                                        onlyCameraListAdapter.setOnItemClickListener(AM_EnterAreaFragment.this);
                                        onlyCameraListAdapter.setOnItemChildClickListener(AM_EnterAreaFragment.this);
                                        imgRcv.setAdapter(onlyCameraListAdapter);
                                        cameraImageLL.addView(picView);
                                        adapters.add(onlyCameraListAdapter);
                                        selectLists.add(localMedia);
                                        receptionNames.add(array.optJSONObject(i).optString("name"));
                                    }
                                }
                            } else {
                                ToastUtils.myToast(getContext(), object.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        LogUtils.d("Tag", msg);
                    }
                });
    }


    private LinearLayout cameraImageLL;

    /**
     * 待申请进场数据布局
     */
    private void jinchangLayout() {
        View changeView = LayoutInflater.from(getContext()).inflate(R.layout.am_enter_change_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);

        changeView.findViewById(R.id.addNewBrand).setOnClickListener(this);
        changeView.findViewById(R.id.enterTimeLL).setOnClickListener(this);
        changeView.findViewById(R.id.chaosongLL).setOnClickListener(this);
        changeView.findViewById(R.id.changeBT).setOnClickListener(this);

        am_enter_materials_rcv = changeView.findViewById(R.id.am_enter_materials_rcv);
        nameInspectionTV = changeView.findViewById(R.id.nameInspectionTV);
        receiveUnitNameTV = changeView.findViewById(R.id.receiveUnitNameTV);
        receiveNameTV = changeView.findViewById(R.id.receiveNameTV);
        supervisorNameTV = changeView.findViewById(R.id.supervisorNameTV);
        acceptanceNameTV = changeView.findViewById(R.id.acceptanceNameTV);
        ccNameTV = changeView.findViewById(R.id.ccNameTV);
        approachDateTV = changeView.findViewById(R.id.approachDateTV);
        receiveSupplementET = changeView.findViewById(R.id.receiveSupplementET);
        descNumTv = changeView.findViewById(R.id.descNumTv);
        netScroV = changeView.findViewById(R.id.netScroV);
        cameraImageLL = changeView.findViewById(R.id.cameraImageLL);

        nameInspectionTV.setText(areaBean.approach.nameInspection + "");
        receiveUnitNameTV.setText(areaBean.approach.receiveUnitName + "");
        receiveNameTV.setText(areaBean.approach.receiveName + "");
        supervisorNameTV.setText(areaBean.approach.supervisorName + "");
        try {
            supervisor = areaBean.approach.supervisor;
        } catch (Exception e) {
            e.printStackTrace();
        }
        netScroV.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                try {
                    am_enter_materials_rcv.clearFocus();
                    receiveSupplementET.clearFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        if (areaBean.approach.acceptanceList != null && areaBean.approach.acceptanceList.size() > 0) {
            acceptanceNameTV.setText("");
            try {
                if (acceptanceNameTV.getParent() instanceof ViewGroup) {
                    ((ViewGroup) acceptanceNameTV.getParent()).setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                if (i > 0) {
                    acceptanceNameTV.append("、");
                }
                acceptanceNameTV.append(areaBean.approach.acceptanceList.get(i).peopleuser + "");
            }
        } else {
            try {
                if (acceptanceNameTV.getParent() instanceof ViewGroup) {
                    ((ViewGroup) acceptanceNameTV.getParent()).setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        ccNameTV.setText(StringUtils.isEmpty(areaBean.approach.ccName) ? "无" : areaBean.approach.ccName + "");
        AM_Enter_Brand lv1 = new AM_Enter_Brand("请选择", 0);
        multiItemEntities = new ArrayList<>();
        try {
            for (int i = 0; i < areaBean.approach.typeInspectionList.size(); i++) {
                AM_Enter_Type lv2;
                if (i == areaBean.approach.typeInspectionList.size()) {
                    lv2 = new AM_Enter_Type("请选择", Integer.MAX_VALUE);
                } else {
                    lv2 = new AM_Enter_Type(areaBean.approach.typeInspectionList.get(i), i);
                    lv2.id = areaBean.approach.typeInspectionIdList.get(i);
                    lv2.addSubItem(new AM_Enter_Specification("", "", "请选择", Integer.MAX_VALUE));
                }
                lv1.addSubItem(lv2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        approachDateTV.setText(calendar.get(java.util.Calendar.YEAR) + "-" + (calendar.get(java.util.Calendar.MONTH) + 1) + "-" + calendar.get(java.util.Calendar.DAY_OF_MONTH));

        multiItemEntities.add(lv1);
        am_enter_materials_rcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        enterAdapter = new AcceotanceMaterialsEnterAdapter(multiItemEntities, getActivity());
        am_enter_materials_rcv.setAdapter(enterAdapter);
        enterAdapter.expandAll();
        enterAdapter.setOnItemClickListener(this);
        enterAdapter.setOnItemChildClickListener(this);
//        setPictureSelectRcv(receiveVehicleImageRcv);
        setDescET(receiveSupplementET, descNumTv);
    }

    private void noHistoryLayout() {
        View changeView = LayoutInflater.from(getContext()).inflate(R.layout.no_history_layout, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
    }


    private boolean isacceptance = false;

    /**
     * 设置 选择照片的那个 RCV 控件设置数据
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        pictureSelectRcv.setHasFixedSize(true);
        pictureSelectRcv.setNestedScrollingEnabled(false);
        pictureSelectRcv.setLayoutManager(manager);

        adapter = new OnlyCameraListAdapter(getContext());
        pictureSelectRcv.setAdapter(adapter);
        selectList = new ArrayList<>();
        adapter.setNewData(selectList);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter dd, View view, int position) {
                if (position == adapter.getItemCount() - 1) {
                    if (selectList.size() >= 6) {
                        ToastUtils.myToast(getContext(), "图片数量已到上限");
                        return;
                    }
                    isacceptance = true;
                    PictureSelectorConfig.openCameraImg(AM_EnterAreaFragment.this, PictureConfig.CHOOSE_REQUEST);
                } else {
                    int pictureType = PictureMimeType.isPictureType(selectList.get(position).getPictureType());
                    if (pictureType == PictureConfig.TYPE_VIDEO) {
                        ShowVideo.playLocalVideo(getContext(), selectList.get(position).getPath());
                    } else {
                        if (selectList.get(position).isCut()) {
                            ShowBigImg.build(getContext(), selectList.get(position).getCutPath());
                        } else {
                            ShowBigImg.build(getContext(), selectList.get(position).getPath());
                        }
                    }
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter dd, View view, int position) {
                if (view.getId() == R.id.ll_del) {
                    try {
                        selectList.remove(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    adapter.setNewData(selectList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

//    /**
//     * 设置 仅 展示图片的 RCV
//     *
//     * @param picRcv
//     * @param imgs
//     */
//    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
//        if (imgs != null && imgs.size() > 0) {
//
//            GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
//            manager.setSmoothScrollbarEnabled(true);
//            manager.setAutoMeasureEnabled(true);
//            picRcv.setHasFixedSize(true);
//            picRcv.setNestedScrollingEnabled(false);
//            picRcv.setLayoutManager(manager);
//
//            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(imgs, getContext());
//            picRcv.setAdapter(picShowListAdapter);
//            picShowListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                    if (PVAUtils.getFileLastType(imgs.get(position)).equals("image/jpeg")) {
//                        ShowBigImg.build(getContext(), imgs.get(position));
//                    } else {
//                        ShowVideo.playLineVideo(getContext(), imgs.get(position));
//                    }
//                }
//            });
//        } else {
//            if (picRcv != null) {
//                if (picRcv.getParent() instanceof ViewGroup) {
//                    ViewGroup group = (ViewGroup) picRcv.getParent();
//                    group.setVisibility(View.GONE);
//                }
//            }
//        }
//    }

    /**
     * 设置 仅 展示图片的 RCV
     *
     * @param picRcv
     * @param imgs
     */
    private void setPicRcv(RecyclerView picRcv, List<AMEnterAreaBean.ApproachBean.ImageListBean> imgs) {

        if (picRcv != null && imgs != null && imgs.size() > 0) {
            try {
//                JSONArray array = new JSONArray(imgs);
//                List<ReceiveImageUploadBean> beanList = new ArrayList<>();
//                for (int i = 0; i < array.length(); i++) {
//                    ReceiveImageUploadBean receiveImageUploadBean = new Gson().fromJson(array.optString(i), ReceiveImageUploadBean.class);
//                    beanList.add(receiveImageUploadBean);
//                }
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                manager.setSmoothScrollbarEnabled(true);
                manager.setAutoMeasureEnabled(true);
                picRcv.setHasFixedSize(true);
                picRcv.setNestedScrollingEnabled(false);
                picRcv.setLayoutManager(manager);
                AM_EnterPicItemsShowAdapter showAdapter = new AM_EnterPicItemsShowAdapter(getContext(), imgs);
                picRcv.setAdapter(showAdapter);
            } catch (Exception e) {
                e.printStackTrace();
            }

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

        if (nameRcv != null && aClass != null && aClass.size() > 0) {
            LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            PersonNameListAdapter chaosongAdapter = new PersonNameListAdapter(getContext());
            chaosongAdapter.setNewData(aClass);
            nameRcv.setAdapter(chaosongAdapter);

            nameRcv.setNestedScrollingEnabled(false);
            manager.setSmoothScrollbarEnabled(true);
            manager.setAutoMeasureEnabled(true);
            nameRcv.setHasFixedSize(true);
            nameRcv.setLayoutManager(manager);


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
        } else {
            if (nameRcv != null) {
                if (nameRcv.getParent() instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) nameRcv.getParent();
                    group.setVisibility(View.GONE);
                }
            }
        }
    }

    private String inspectorTel;

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
                    if (isacceptance) {
                        selectList.addAll(selectLi);
                        adapter.setNewData(selectList);
                    } else {
                        selectLists.get(clickCameraProsition).addAll(selectLi);
                        adapters.get(clickCameraProsition).setNewData(selectLists.get(clickCameraProsition));
                        adapters.get(clickCameraProsition).notifyDataSetChanged();
                    }
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

        } else if (requestCode == SelectTRPOrAU.COPY_PEOPLE) {//抄送人
            String chaoSong = data.getBundleExtra("bundle").getString("name");
            cc = data.getBundleExtra("bundle").getString("id");
            if ("1".equals(CLYSState)) {
                try {
                    ccNameTV.setText(chaoSong);
                } catch (Exception e) {

                }
            } else {
                if (!StringUtils.isEmpty(chaoSong)) {
                    List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                    setNameRcv(ccListRcv, chaoSongName);
                }
            }
            if ("2".equals(CLYSState) || "1".equals(CLYSState)) {//待验收
                updateClysTask("cc", cc);
            }

        } else if (requestCode == SelectTRPOrAU.Brand) {//选择品牌
            bundle = data.getBundleExtra("bundle");
            String brundName = bundle.getString("name");
//            typeInspectionId = bundle.getString("id");
            brand.bandName = brundName;
            enterAdapter.notifyDataSetChanged();
        } else if (requestCode == SelectTRPOrAU.BrandSpecifi) {//选择规格
            bundle = data.getBundleExtra("bundle");
            String specificationName = bundle.getString("name");
            String unitOfQuantity = bundle.getString("id");

            MultiItemEntity parent = enterAdapter.getData().get(enterAdapter.getParentPositionInAll(clickPosition));
            if (parent instanceof AM_Enter_Type) {
                AM_Enter_Specification newLv3 = new AM_Enter_Specification(
                        "", specificationName, unitOfQuantity, 0);
                enterAdapter.addData(clickPosition, newLv3);
                ((AM_Enter_Type) parent).addSubItem(newLv3);
                enterAdapter.expandAll();
            }

//            specification.specificationName = specificationName;
//            if (StringUtils.isEmpty(unitOfQuantity)) {
//
////                specification.unit = areaBean.approach.;
//            }
//            specification.unit = unitOfQuantity;
            enterAdapter.notifyDataSetChanged();
        } else if (resultCode == SelectTRPOrAU.SingleMaterialsType) {//材料类型
            bundle = data.getBundleExtra("bundle");
            String typeName = bundle.getString("name");
            String typeID = bundle.getString("id");
            type.typeName = typeName;
            type.id = typeID;
            List<AM_Enter_Specification> specificationList = type.getSubItems();
            for (int i = 0; i < specificationList.size(); i++) {
                if (specificationList.get(i).position != Integer.MAX_VALUE) {
                    try {
                        AM_Enter_Specification specification = specificationList.get(i);
                        if (enterAdapter.getData().contains(specification)) {
                            enterAdapter.getData().remove(specificationList.get(i));
                        }
                        type.removeSubItem(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            enterAdapter.notifyDataSetChanged();
        } else if (resultCode == SelectTRPOrAU.Supervisor) {//监理
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            String supervi = bundle.getString("id", "");
            if ("2".equals(CLYSState)) {//待验收
                boolean isCanChange = true;
                try {
                    for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                        if (supervi.equals(areaBean.approach.acceptanceList.get(i).userId)) {
                            isCanChange = false;
                            ToastUtils.myToast(getActivity(), "同一个人不能是监理和建设单位验收人");
                            break;
                        }
                    }
                    if (isCanChange) {
                        supervisor = supervi;
                        supervisorNameTV.setText(jianShePerson);
                        updateClysTask("supervisor", supervisor);//监理
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == SelectTRPOrAU.inspector) {///送检人
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            inspectorTel = bundle.getString("tel");
            inspector = bundle.getString("id");
            inspectorTV.setText(jianShePerson);
        } else if (resultCode == SelectTRPOrAU.ConstructionDirector) {//建设单位
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            String accep = bundle.getString("id", "");

            if (accep.equals(areaBean.approach.supervisor)) {
                ToastUtils.myToast(getActivity(), "同一个人不能是监理和建设单位验收人");
                return;
            }
            if ("2".equals(CLYSState)) {
                boolean isCanChange = true;
                StringBuffer acceptances = new StringBuffer();
                //验收人中 不是自己的时候 如果 新修改的验收人与已存在的验收人不重复则 加入到buffer里面
                for (int i = 0; i < areaBean.approach.acceptanceList.size(); i++) {
                    if (!UserInfo.create(getContext()).getUserId().equals(areaBean.approach.acceptanceList.get(i).userId)) {
                        if (!accep.equals(areaBean.approach.acceptanceList.get(i).userId)) {
                            if (acceptances.length() > 0) {
                                acceptances.append(",");
                            }
                            acceptances.append(areaBean.approach.acceptanceList.get(i).userId);
                        } else {
                            isCanChange = false;
                            ToastUtils.myToast(getActivity(), "不能重复选择已在任务中的验收人");
                            break;
                        }
                    }
                }
                if (isCanChange) {
                    if (acceptances.length() > 0) {
                        acceptances.append(",");
                    }
                    acceptance = accep;
                    acceptances.append(acceptance);
                    acceptanceNameTV.setText(jianShePerson);
                    updateClysTask("acceptance", acceptances.toString());//建设单位验收人
                }
            }
        }
    }

    private String getclysBrandListStrJson() {
        List<MultiItemEntity> entities = enterAdapter.getData();
        AMEnterBrandBean brandBean = new AMEnterBrandBean();
        List<AMEnterBrandBean.ClysBrandListBean> clysBrandList = new ArrayList<>();
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof AM_Enter_Brand) {
                AM_Enter_Brand brand = (AM_Enter_Brand) entities.get(i);
                AMEnterBrandBean.ClysBrandListBean brandListBean = new AMEnterBrandBean.ClysBrandListBean();
                if (StringUtils.isEmpty(brand.bandName) || "请选择".equals(brand.bandName)) {
                    ToastUtils.myToast(getActivity(), "请选择品牌");
                    return null;
                }
                brandListBean.setBandName(brand.bandName);
                List<AMEnterBrandBean.ClysBrandListBean.ClysTypeListBean> clysTypeList = new ArrayList<>();
                for (int j = 0; j < brand.getSubItems().size(); j++) {
                    AM_Enter_Type type = brand.getSubItems().get(j);
                    if (type.position != Integer.MAX_VALUE) {
                        AMEnterBrandBean.ClysBrandListBean.ClysTypeListBean typeListBean = new AMEnterBrandBean.ClysBrandListBean.ClysTypeListBean();
                        typeListBean.setTypeName(type.typeName);
                        List<AMEnterBrandBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean> clysSpecificationList = new ArrayList<>();
                        for (int k = 0; k < type.getSubItems().size(); k++) {
                            AM_Enter_Specification specification = type.getSubItems().get(k);
                            if (specification.position != Integer.MAX_VALUE) {
                                AMEnterBrandBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean specificationListBean = new AMEnterBrandBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean();
                                specificationListBean.setNumber(specification.number);
                                if (StringUtils.isEmpty(specification.number) || "请选择".equals(specification.number)) {
                                    ToastUtils.myToast(getActivity(), "请添加所有的数量");
                                    return null;
                                }
                                specificationListBean.setSpecificationName(specification.specificationName);
                                specificationListBean.setUnit(specification.unit);
                                clysSpecificationList.add(specificationListBean);
                            }
                        }
                        if (clysSpecificationList.size() <= 0) {
                            ToastUtils.myToast(getActivity(), "请添加规格");
                            return null;
                        }
                        typeListBean.setClysSpecificationList(clysSpecificationList);
                        clysTypeList.add(typeListBean);
                    }
                }
                brandListBean.setClysTypeList(clysTypeList);
                clysBrandList.add(brandListBean);
            }
        }
        brandBean.setClysBrandList(clysBrandList);
        String obj2 = new Gson().toJson(clysBrandList);
        return obj2;
    }

    /**
     * 材料验收-修改任务，申请进场验收
     */
    private void updateClysTask(List<List<String>> paths, String json) {
        List<ReceiveImageUploadBean> picMaps = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            ReceiveImageUploadBean imageUploadBean = new ReceiveImageUploadBean();
            imageUploadBean.name = receptionNames.get(i);
            imageUploadBean.list = paths.get(i);
            picMaps.add(imageUploadBean);
        }
        String receiveImage = new Gson().toJson(picMaps);
        LogUtils.d("receiveImage", receiveImage);

        HttpRequest.get(getContext())
                .url(ServerInterface.updateClysTask)
                .params("id", id)//主键id
                .params("state", "2")//状态(1待申请进场，2待验收，3已验收，4待送检，5待上传报告，6待复验，7待退场，8已退场，9送检合格，10复验合格)
                .params("approachDate", approachDateTV.getText().toString().trim())//进场时间
                .params("receiveImage", receiveImage)//图片
                .params("receiveSupplement", receiveSupplementET.getText().toString().trim())//接收人补充说明
                .params("receiveDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//接收时间
                .params("clysBrandListStr", json)//品牌列表：包含类型列表、规格列表："clysBrandList":[{"bandName":"安钢","clysTypeList":[{"typeName":"盘螺","clysSpecificationList":[{"specificationName":"8","number":"11","unit":"吨"},{"specificationName":"6","number":"21","unit":"吨"}]},{"typeName":"螺纹8 35.32","clysSpecificationList":[{"specificationName":"32","number":"31","unit":"吨"},{"specificationName":"35","number":"41","unit":"吨"}]}]}] }
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
     * 材料验收-修改任务，
     */
    private void updateClysTask(final String key, String value) {
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

    private boolean supervisorResult;

    /**
     * 材料验收-监理-进场验收合格、进场验收不合格
     */
    private void supervisorQualified(List<List<String>> paths) {

        List<ReceiveImageUploadBean> picMaps = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            ReceiveImageUploadBean imageUploadBean = new ReceiveImageUploadBean();
            imageUploadBean.name = receptionNames.get(i);
            imageUploadBean.list = paths.get(i);
            picMaps.add(imageUploadBean);
        }
        String supervisorImage = new Gson().toJson(picMaps);
        LogUtils.d("supervisorImage", supervisorImage);
        HttpRequest.get(getActivity())
                .url(ServerInterface.supervisorQualified)
                .params("id", id)
                .params("supervisorDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))
                .params("supervisorResult", supervisorResult ? "1" : "0")//监理验收0不合格/1合格
                .params("supervisorImage", supervisorImage)//图片
                .params("supervisorSupplement", receiveSupplementET == null ? "" : receiveSupplementET.getText().toString().trim())//监理补充说明
                .params("isInspect", isInspect == 1 ? 1 : 0)//是否送检：0否，1是
                .params("inspector", inspector)//
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
     * 材料验收-建设单位验收人-进场验收合格、进场验收不合格
     */
    private void acceptanceQualified(String paths) {

        HttpRequest.get(getActivity())
                .url(ServerInterface.acceptanceQualified)
                .params("taskId", id)
                .params("userId", UserInfo.create(getContext()).getUserId())
                .params("isQualified", supervisorResult ? "1" : "0")//验收0不合格/1合格
                .params("acceptanceDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//验收时间
                .params("acceptanceImage", paths)//验收照片：图片
                .params("acceptanceSupplement", acceptanceSupplementET == null ? "" : acceptanceSupplementET.getText().toString().trim())//验收补充说明
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                EventBus.getDefault().post("taskStateChanged");
                                if (!supervisorResult) {
                                    EventBus.getDefault().post("clysTaskcheckResult");
                                }
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
