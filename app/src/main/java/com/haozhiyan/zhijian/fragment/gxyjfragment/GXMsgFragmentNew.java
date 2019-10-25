package com.haozhiyan.zhijian.fragment.gxyjfragment;


import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.GXYJBackNote;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.activity.gxyj.SendBack;
import com.haozhiyan.zhijian.activity.gxyj.TurnOverPart;
import com.haozhiyan.zhijian.adapter.PersonNameListAdapter;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.bean.GXYJDetailsBean;
import com.haozhiyan.zhijian.bean.GXYJZanCunBean;
import com.haozhiyan.zhijian.bean.ProcessOverUseIdBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.haozhiyan.zhijian.utils.ZanCunUtil;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * 工序信息 新编辑
 */
public class GXMsgFragmentNew extends Fragment implements View.OnClickListener {

    private android.widget.TextView projectNameTV;
    private android.widget.TextView sectionNameTV;
    private android.widget.TextView siteNameTV;
    private android.widget.TextView handOverPartTV;
    private android.widget.ImageView handOverPartImg;
    private android.widget.LinearLayout handOverPartLL;
    private android.widget.TextView constructionUnitTV;
    private android.widget.LinearLayout constructionUnitLL;
    private android.widget.TextView constructionDirectorTV;
    private android.widget.TextView jianSheTV;
    private android.widget.TextView jianSheTV2;
    private android.widget.LinearLayout jiaSheLL;
    private android.widget.TextView supervisorTV;
    private android.widget.TextView supervisorTV2;
    private android.widget.LinearLayout supervisorLL;
    private android.widget.TextView copyPeopleTv;
    private android.widget.LinearLayout copyPeopleLL;
    private android.support.v7.widget.RecyclerView pictureVideoRCV;
    private android.widget.EditText explainET;
    private android.widget.TextView descNumTv;
    private android.widget.Button deleteBT;
    private android.widget.TextView btnback;
    private android.widget.TextView commitBT;
    private View stateView;
    private TextView taskStateTV;
    private TextView sendBackNumberTV;
    private LinearLayout sendBackLL;
    private TextView creatorTimeTV;
    private TextView explainIssueTV;
    private TextView supervisorExplainTV;
    private RecyclerView supervisorPictureVideoRCV;
    private RecyclerView copyPeopleRCV;
    private TextView bacTipTV;
    private TextView reCommitBT;
    private TextView sendBackBT;
    private TextView passBT;
    private String id;

    private String supervisorID;
    private RelativeLayout parentViewGroup;

    public ProcessOverUseIdBean useIdBean;

    //GXYJBackNote
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.empty_parent, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        parentViewGroup = view.findViewById(R.id.parentViewGroup);
        try {
            oldSelectIds = useIdBean.selectIds;

        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        if ("空".equals(useIdBean.identifying)) {
            setEditView();
        } else if (useIdBean.isDaiBan) {
            selectDBDetails();
        } else {
            selectDetails();
        }
        return view;
    }

    private String constructionId;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handOverPartLL://移交部位
                Intent intent = new Intent(getContext(), TurnOverPart.class);
                intent.putExtra("partsDivision", useIdBean.partsDivision);
                intent.putExtra("selectroom", useIdBean.selectroom);
                startActivityForResult(intent, 203);
                break;
            case R.id.constructionUnitLL://施工单位
                SelectTRPOrAU.select(GXMsgFragmentNew.this,
                        constructionUnitTV == null ? "" : constructionUnitTV.getText().toString().trim()
                        , useIdBean.sectionId, SelectTRPOrAU.contractor);//施工单位
                break;
            case R.id.supervisorLL://监理
                SelectTRPOrAU.select(GXMsgFragmentNew.this, supervisorID, true, SelectTRPOrAU.Supervisor);//监理
                break;
            case R.id.jiaSheLL2://建设单位
                SelectTRPOrAU.select(GXMsgFragmentNew.this, constructionId, true, SelectTRPOrAU.ConstructionDirector);//建设单位
                break;
            case R.id.copyPeopleLL://抄送人
                SelectTRPOrAU.select(this, peopleId, SelectTRPOrAU.COPY_PEOPLE);
                break;
            case R.id.sendBackLL://退回记录
                try {
                    Intent intentYys = new Intent(getActivity(), GXYJBackNote.class);
                    intentYys.putExtra("id", id);
                    intentYys.putExtra("backNum", detailsBean.backRecord);
                    startActivity(intentYys);
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
                break;
            case R.id.commitBT://提交验收
                UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        saveDetails(paths);
                    }
                });
                break;
            case R.id.btn_back:// 暂存
                save();
                break;
            case R.id.updateBT://提交验收
                UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        updateDetails(paths);
                    }
                });
                break;
            case R.id.reCommitBT://再次提交验收
                setReEditView();
                break;
            case R.id.sendBackBT://监理 退回
                Intent intent0 = new Intent(getActivity(), SendBack.class);
                intent0.putExtra("type", "1");
                intent0.putExtra("id", id);
                intent0.putExtra("inspectionId", useIdBean.inspectionId);
                startActivity(intent0);
                break;
            case R.id.passBT://监理/建设单位验收人验收通过
                if (!"已验收".equals(detailsBean.jianliState)) {
                    UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                        @Override
                        public void onComplete(String paths) {
                            JlCrossYs(paths);
                        }
                    });
                } else {
                    UpLoadUtil.init(getContext(), selectList).Call(new UpLoadCallBack() {
                        @Override
                        public void onComplete(String paths) {
                            JSCrossYs(paths);
                        }
                    });
                }

                break;
        }
    }

    private String roomTowerFloorId;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if ("GXYJTaskStateChanged".equals(event)) {
            if (useIdBean.isDaiBan) {
                selectDBDetails();
            } else {
                selectDetails();
            }
        }
    }

    private void save() {
        GXYJZanCunBean zanCunBean = new GXYJZanCunBean();
        zanCunBean.isZanCun = true;
        zanCunBean.selectrooms = useIdBean.selectIds;
        zanCunBean.selectroom = useIdBean.selectroom;
        zanCunBean.constructionUnit = constructionUnit;
        zanCunBean.constructionId = constructionId;
        zanCunBean.jianShePerson = jianShePerson;
        zanCunBean.supervisorID = supervisorID;
        zanCunBean.supervisor = supervisor;
        zanCunBean.peopleId = peopleId;
        zanCunBean.copyPeople = copyPeople;
        zanCunBean.explainIssue = explainET == null ? "" : explainET.getText().toString().trim();
        zanCunBean.selectList = selectList;
        if (!oldSelectIds.equals(useIdBean.selectIds)) {
            if (oldSelectIds.contains(",")) {
                String[] ids = oldSelectIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    String key = Constant.projectId + UserInfo.create(getContext()).getUserId() +
                            useIdBean.towerId + useIdBean.unitId + ids[i] + useIdBean.inspectionId;
                    ZanCunUtil.creat(getContext()).remove(key);
                }
            } else {
                String key = Constant.projectId + UserInfo.create(getContext()).getUserId() + useIdBean.towerId +
                        useIdBean.unitId + oldSelectIds + useIdBean.inspectionId;
                ZanCunUtil.creat(getContext()).remove(key);
            }
        }
        if (useIdBean.selectIds.contains(",")) {
            String[] ids = useIdBean.selectIds.split(",");
            for (int i = 0; i < ids.length; i++) {
                String key = Constant.projectId + UserInfo.create(getContext()).getUserId() + useIdBean.towerId +
                        useIdBean.unitId + ids[i] + useIdBean.inspectionId;
                ZanCunUtil.creat(getContext()).save(key, zanCunBean);
            }
        } else {
            String key = Constant.projectId + UserInfo.create(getContext()).getUserId() + useIdBean.towerId +
                    useIdBean.unitId + useIdBean.selectIds + useIdBean.inspectionId;
            ZanCunUtil.creat(getContext()).save(key, zanCunBean);
        }
        oldSelectIds = useIdBean.selectIds;
        ToastUtils.myToast(getContext(), "暂存完成");
    }

    private void getSave() throws Exception {
        String key = Constant.projectId + UserInfo.create(getContext()).getUserId() + useIdBean.towerId +
                useIdBean.unitId + useIdBean.selectIds + useIdBean.inspectionId;
        String value = SPUtil.get(getContext()).get(key);
        GXYJZanCunBean zanCunBean = (GXYJZanCunBean) ZanCunUtil.creat(getContext()).get(key, GXYJZanCunBean.class);
//        GXYJZanCunBean zanCunBean = new Gson().fromJson(value, GXYJZanCunBean.class);
        if (zanCunBean.isZanCun) {
            useIdBean.selectroom = zanCunBean.selectroom;
            useIdBean.selectIds = zanCunBean.selectrooms;
            constructionUnit = zanCunBean.constructionUnit;
            constructionId = zanCunBean.constructionId;
            jianShePerson = zanCunBean.jianShePerson;
            supervisorID = zanCunBean.supervisorID;
            supervisor = zanCunBean.supervisor;
            peopleId = zanCunBean.peopleId;
            copyPeople = zanCunBean.copyPeople;
            selectList = zanCunBean.selectList;

            oldSelectIds = useIdBean.selectIds;
            setPictureSelectRcv(addPicRcv);
            try {
                if (!TextUtils.isEmpty(useIdBean.selectroom)) {
                    handOverPartTV.setText(useIdBean.selectroom);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(constructionUnit)) {
                    constructionUnitTV.setText(constructionUnit);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(jianShePerson)) {
                    jianSheTV.setText(jianShePerson);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(supervisor)) {
                    supervisorTV.setText(supervisor);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(copyPeople)) {
                    copyPeopleTv.setText(copyPeople);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (!TextUtils.isEmpty(zanCunBean.explainIssue)) {
                    explainET.setText(zanCunBean.explainIssue);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onDestroy() {
        try {
            super.onDestroy();
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        try {
            OkGo.getInstance().cancelTag(this);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * 申请验收
     */
    private void setEditView() {
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.gxyj_edit_layout, parentViewGroup,
                        false);
        getView(view);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(view);
        try {
            projectNameTV.setText((useIdBean.inspectionName + "-" + useIdBean.inspectionSunName));
            sectionNameTV.setText("标段:   " + useIdBean.sectionName + "(" + Constant.parentProjectName
                    + "-" + Constant.diKuaiName + ")");
            siteNameTV.setText("楼栋:   " + ("整栋".equals(useIdBean.partsDivision) ? useIdBean.towerName : (useIdBean.towerName + "-" + useIdBean.unitName)));
        } catch (Exception ignored) {
        }

        try {
            btnback.setVisibility(View.VISIBLE);
            btnback.setText("暂存");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (useIdBean.partsDivision.equals("整栋")) {
                handOverPartTV.setText("整栋");
                handOverPartTV.setTextColor(0xff9D9D9D);
                handOverPartImg.setVisibility(View.INVISIBLE);
                handOverPartLL.setOnClickListener(null);
            } else {
                handOverPartTV.setText(useIdBean.selectroom);
                handOverPartLL.setOnClickListener(this);
                handOverPartTV.setSelected(true);
                handOverPartImg.setVisibility(View.VISIBLE);
            }
        } catch (Exception ignored) {
        }
        constructionDirectorTV.setText(UserInfo.create(getContext()).getUserName());
        setPictureSelectRcv(addPicRcv);
        setDescET(explainET, descNumTv);
        try {
            getSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if ("0".equals(useIdBean.isNeedBuild)) {
            jiaSheLL2.setVisibility(View.GONE);
        }
    }

    private GXYJDetailsBean detailsBean;

    /**
     * 查看详情
     */
    private void selectDetails() {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectDetails)
                .params("pkId", useIdBean.selectIds)//  主键ID
                .params("sectionId", useIdBean.sectionId)  //   标段ID
                .params("towerId", useIdBean.towerId)  //  楼栋ID
                .params("unitId", useIdBean.unitId)  //   单元Id
                .params("inspectionName", useIdBean.inspectionName)  //   检查项名称 =主体工程
                .params("inspectionSunName", useIdBean.inspectionSunName)  //  检查项子名称 =模板验收
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray array = object.optJSONArray("list");
                                if (array != null && array.length() > 0) {
                                    try {
                                        detailsBean =
                                                new Gson().fromJson(array.getString(0), GXYJDetailsBean.class);
                                    } catch (Exception ignored) {
                                        ignored.printStackTrace();
                                    }
                                    if (detailsBean != null)
                                        setDifferentView();
                                }
                            }
                        } catch (JSONException ignored) {
                            ignored.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    /**
     * 从待办查看详情
     */
    private void selectDBDetails() {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectDaiban)
                .params("id", useIdBean.dbId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray array = object.optJSONArray("list");
                                if (array != null && array.length() > 0) {
                                    try {
                                        detailsBean =
                                                new Gson().fromJson(array.getString(0), GXYJDetailsBean.class);
                                    } catch (Exception ignored) {
                                        ignored.printStackTrace();
                                    }
                                    if (detailsBean != null)
                                        setDifferentView();
                                }
                            }
                        } catch (JSONException ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    /**
     * 根据条件 加载 不同布局
     *
     * @param
     */
    private void setDifferentView() {
        isReEditView = false;
        int layout = 0;
        String identify = "";
        if (detailsBean != null) {
            identify = detailsBean.identifying;
        }
        if (detailsBean != null) {
            id = detailsBean.id;
            peopleId = detailsBean.peopleId;
            useIdBean.towerId = detailsBean.towerId;
            useIdBean.unitId = detailsBean.unitId;
            useIdBean.partsDivision = detailsBean.partsDivision;
            useIdBean.towerName = detailsBean.towerName;
            useIdBean.unitName = detailsBean.unitName;
            useIdBean.sectionName = detailsBean.sectionName;
            useIdBean.sectionId = detailsBean.sectionId;
//            if ("分户".equals(detailsBean.partsDivision)) {
//                useIdBean.roomId = detailsBean.roomTowerFloorId;
//            } else if ("分单元-整层".equals(detailsBean.partsDivision)
//                    || "不分单元-整层".equals(detailsBean.partsDivision)) {
//
//            }
            useIdBean.floorId = detailsBean.floorId;
            useIdBean.floorName = detailsBean.floorName;
        }

        SPUtil.get(getContext()).save("gxyjTaskID", id);
        constructionId = detailsBean.constructionId;
        switch (identify) {
            case "待验收":
                if (UserInfo.create(getContext()).getUserId().equals(detailsBean.getSupervisorId())
                        && "待验收".equals(detailsBean.jianliState)) {
                    //验收的监理 是自己
                    layout = R.layout.gxyj_supervisor_edit_layout;
                } else if (UserInfo.create(getContext()).getUserId().equals(detailsBean.constructionId)
                        && "待验收".equals(detailsBean.jiansheState)) {
                    //验收的建设单位验收人 是自己
                    layout = R.layout.gxyj_construction_edit_layout;
                } else {
                    if (UserInfo.create(getContext()).getUserId().equals(detailsBean.getCreatorId())) {
                        //   但创建人是自己
                        layout = R.layout.gxyj_show2_layout;
                    } else {
                        //都不是自己
                        layout = R.layout.gxyj_show_layout;
                    }
                }
                break;
            case "已退回":
                layout = R.layout.gxyj_back_show_layout;
                break;
            case "已验收":
                layout = R.layout.gxyj_success_layout;
                break;
            default:
                layout = R.layout.gxyj_edit_layout;
                break;
        }

        parentViewGroup.removeAllViews();
        View view = LayoutInflater.from(getContext()).
                inflate(layout, parentViewGroup,
                        false);
        getView(view);
        parentViewGroup.addView(view);
        setView();
    }

    private boolean isReEditView = false;

    /**
     * 被退回后再次申请验收
     */
    private void setReEditView() {
        View view = LayoutInflater.from(getContext()).
                inflate(R.layout.gxyj_reedit_layout, parentViewGroup,
                        false);
        getView(view);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(view);
        isReEditView = true;
        setView();
    }


    private TextView jianliPeopleTimeTV;
    private TextView jianliStateTV;
    private TextView backTypeTV;

    private void setView() {
        String identify = "";
        if (detailsBean != null) {
            identify = detailsBean.getIdentifying();
        }
        roomTowerFloorId = detailsBean.getRoomTowerFloorId();

        projectNameTV.setText(detailsBean.detailsName);
        sectionNameTV.setText("标段:   " + detailsBean.sectionDikuaiName);
        siteNameTV.setText("楼栋:   " + detailsBean.siteName);

        if (detailsBean.childBack != null && detailsBean.childBack.size() > 0) {
            sendBackLL.setVisibility(View.VISIBLE);
            sendBackNumberTV.setText(detailsBean.backRecord + "次");
        } else {
            sendBackLL.setVisibility(View.GONE);
        }
        if ("整栋".equals(detailsBean.handOverPart)) {
            handOverPartTV.setText("整栋");
            handOverPartTV.setTextColor(0xff9D9D9D);
            try {
                handOverPartImg.setVisibility(View.INVISIBLE);
            } catch (Exception ignored) {

            }
        } else {
            handOverPartTV.setText(detailsBean.handOverPart);
            try {
                handOverPartImg.setVisibility(View.VISIBLE);
            } catch (Exception ignored) {

            }
        }

        constructionUnitTV.setText(detailsBean.constructionUnit);

        constructionDirectorTV.setText(detailsBean.constructionDirector);

        try {
            creatorTimeTV.setText(detailsBean.creationTime);
        } catch (Exception ignored) {

        }
        if (StringUtils.isEmpty(detailsBean.explainIssue) && (detailsBean.childPV == null || detailsBean.childPV.size() <= 0)) {
            try {
                explainIssueTV.setText("无");
            } catch (Exception e) {

            }
        } else {
            try {
                if (!StringUtils.isEmpty(detailsBean.explainIssue)) {
                    explainIssueTV.setVisibility(View.VISIBLE);
                    explainIssueTV.setText(detailsBean.explainIssue);
                } else {
                    explainIssueTV.setVisibility(View.GONE);
                }
            } catch (Exception ignored) {
            }
            try {
                setPicRcv(pictureVideoRCV, detailsBean.childPV);
            } catch (Exception ignored) {

            }
        }


        try {
            supervisorTV.setText(detailsBean.supervisor);
        } catch (Exception ignored) {

        }
        try {
            supervisorTV2.setText(detailsBean.supervisor);
        } catch (Exception ignored) {

        }
        supervisorID = detailsBean.supervisorId;
        try {
            if (!StringUtils.isEmpty(detailsBean.supervisorExplain)) {
                supervisorExplainTV.setVisibility(View.VISIBLE);
                supervisorExplainTV.setText(detailsBean.supervisorExplain);
            } else {
                supervisorExplainTV.setVisibility(View.GONE);
            }
        } catch (Exception ignored) {
        }
        try {
            if (!StringUtils.isEmpty(detailsBean.jianliPeopleTime))
                jianliPeopleTimeTV.setText(detailsBean.jianliPeopleTime + "   ");
        } catch (Exception ignored) {

        }


        try {
            if (StringUtils.isEmpty(detailsBean.copyPeople)) {
                copyPeopleTv.setText("无");
            } else {
                copyPeopleTv.setText(detailsBean.copyPeople);
            }
        } catch (Exception ignored) {

        }
        try {
            jianliStateTV.setText(detailsBean.jianliState);
            if ("待验收".equals(detailsBean.jianliState)) {
                jianliStateTV.setTextColor(ContextCompat.getColor(getContext(), R.color.red2));
            } else if ("已验收".equals(detailsBean.jianliState)) {
                jianliStateTV.setTextColor(ContextCompat.getColor(getContext(), R.color.green2));
            }
        } catch (Exception ignored) {

        }
        try {
            if (detailsBean.childP == null || detailsBean.childP.size() <= 0) {
                List<String> strings = new ArrayList<>();
                strings.add("无");
                setNameRcv(copyPeopleRCV, strings);
            } else {
                setNameRcv(copyPeopleRCV, detailsBean.childP);
            }
        } catch (Exception ignored) {

        }

        try {
            jianSheTV.setText(detailsBean.constructionUnitPrincipal);
        } catch (Exception ignored) {

        }
        try {
            jianSheTV2.setText(detailsBean.constructionUnitPrincipal);
        } catch (Exception ignored) {

        }
        try {
            if (!StringUtils.isEmpty(detailsBean.jianshePeopleTime))
                jianshePeopleTimeTV.setText(detailsBean.jianshePeopleTime + "     ");
        } catch (Exception ignored) {

        }
        try {
            jiansheStateTV.setText(TextUtils.isEmpty(detailsBean.jiansheState) ? "待验收" : detailsBean.jiansheState);
            if (TextUtils.isEmpty(detailsBean.jiansheState) || "待验收".equals(detailsBean.jiansheState) || "空".equals(detailsBean.jiansheState)) {
                jiansheStateTV.setTextColor(ContextCompat.getColor(getContext(), R.color.red2));
            } else if ("已验收".equals(detailsBean.jiansheState)) {
                jiansheStateTV.setTextColor(ContextCompat.getColor(getContext(), R.color.green2));
            }
        } catch (Exception ignored) {

        }
        try {
            constructionExplainTV.setText(detailsBean.constructionExplain);
        } catch (Exception ignored) {

        }


        try {
            taskStateTV.setText(identify);
        } catch (Exception ignored) {

        }
        setPictureSelectRcv(addPicRcv);

        try {
            if (!TextUtils.isEmpty(detailsBean.constructionId)) {
                if ("空".equals(detailsBean.jiansheState)
                        || StringUtils.isEmpty(detailsBean.jiansheState)) {
                    try {
                        jiasheLL.setVisibility(View.VISIBLE);
                    } catch (Exception ignored) {

                    }
                    try {
                        jiaSheLL2.setVisibility(View.VISIBLE);
                    } catch (Exception ignored) {
                    }
                } else {
                    try {
                        jiasheLL.setVisibility(View.VISIBLE);
                    } catch (Exception ignored) {
                    }
                    try {
                        jiaSheLL2.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            } else {
                try {
                    jiasheLL.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    jiaSheLL2.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception ignored) {

        }

        try {
//            if (!"空".equals(detailsBean.jianliState) && !"待验收".equals(detailsBean.jianliState)
//                    && !StringUtils.isEmpty(detailsBean.jianliState)) {
//                try {
//                    jianliLL.setVisibility(View.VISIBLE);
//                } catch (Exception ignored) {
//                }
//                if (!isReEditView) {
//                    try {
//                        supervisorLL.setVisibility(View.GONE);
//                    } catch (Exception ignored) {
//                    }
//                }
//            } else {
//                try {
//                    jianliLL.setVisibility(View.GONE);
//                } catch (Exception ignored) {
//                }
//                try {
//                    supervisorLL.setVisibility(View.VISIBLE);
//                } catch (Exception ignored) {
//                }
//            }
        } catch (Exception ignored) {

        }

        try {
            if (StringUtils.isEmpty(detailsBean.constructionExplain) && (detailsBean.childPVCON == null || detailsBean.childPVCON.size() <= 0)) {
                if (constructionPictureVideoRCV.getParent().getParent() instanceof ViewGroup) {
                    ((ViewGroup) constructionPictureVideoRCV.getParent().getParent()).setVisibility(View.GONE);
                }
            }
        } catch (Exception ignored) {

        }

        try {
            if (StringUtils.isEmpty(detailsBean.supervisorExplain) && (detailsBean.childPVSUP == null || detailsBean.childPVSUP.size() <= 0)) {
                if (supervisorPictureVideoRCV.getParent().getParent() instanceof ViewGroup) {
                    ((ViewGroup) supervisorPictureVideoRCV.getParent().getParent()).setVisibility(View.GONE);
                }
            }
        } catch (Exception ignored) {

        }
        try {
            if (StringUtils.isEmpty(detailsBean.constructionExplain) && (detailsBean.childPVCON == null || detailsBean.childPVCON.size() <= 0)) {
                if (childPVCONRCV.getParent().getParent() instanceof ViewGroup) {
                    ((ViewGroup) childPVCONRCV.getParent().getParent()).setVisibility(View.GONE);
                }
            }
        } catch (Exception ignored) {

        }

        setPicRcv(childPVCONRCV, detailsBean.childPVCON);
        setPicRcv(supervisorPictureVideoRCV, detailsBean.childPVSUP);
        setPicRcv(constructionPictureVideoRCV, detailsBean.childPVCON);

        switch (identify) {
            case "待验收":
                if (UserInfo.create(getContext()).getUserId().equals(detailsBean.getSupervisorId())
                        && "待验收".equals(detailsBean.jianliState)) {
                    //验收的监理 是自己
                    try {
                        constructionExplainTV.setText(detailsBean.constructionExplain);
                    } catch (Exception ignored) {

                    }
                } else if (UserInfo.create(getContext()).getUserId().equals(detailsBean.constructionId)
                        && "待验收".equals(detailsBean.jiansheState)) {
                    //验收的建设单位验收人 是自己
                    try {
                        supervisorExplainTV.setText(detailsBean.supervisorExplain);
                    } catch (Exception ignored) {
                    }
                } else {
                    if (UserInfo.create(getContext()).getUserId().equals(detailsBean.getCreatorId())) {
                        //   但创建人是自己
                    } else {
                        //都不是自己
                    }
                }
                stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.orange));

                break;
            case "已退回":
                try {
                    bacTipTV.setVisibility(View.VISIBLE);
                    bacTipTV.setText("需要原施工负责人" + detailsBean.constructionDirector + "重新申请验收");
                } catch (Exception ignored) {

                }
                try {
                    if (UserInfo.create(getContext()).getUserId().equals(detailsBean.creatorId)) {
                        if (reCommitBT.getParent() instanceof ViewGroup) {
                            ((ViewGroup) reCommitBT.getParent()).setVisibility(View.VISIBLE);
                        }
                    }
                } catch (Exception ignored) {

                }
                try {
                    stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red2));
                } catch (Exception ignored) {

                }
                if (detailsBean.childBack != null && detailsBean.childBack.size() > 0) {
                    try {
                        backLL.setVisibility(View.VISIBLE);
                    } catch (Exception ignored) {

                    }
                    try {
                        backUsernameTV.setText(detailsBean.childBack.get(0).backUsername);
                    } catch (Exception ignored) {
                    }
                    try {
                        backCreationTimeTV.setText(detailsBean.childBack.get(0).backCreationTime);
                    } catch (Exception ignored) {
                    }
                    try {
                        backTypeTV.setText(detailsBean.childBack.get(0).backType);
                    } catch (Exception ignored) {
                    }
                    try {
                        backCauseTV.setText(detailsBean.childBack.get(0).backCause + "\n" + detailsBean.childBack.get(0).backExplain);
                    } catch (Exception ignored) {
                    }
                    try {
                        setPicRcv(childbackPictureVideoRCV, detailsBean.childBack.get(0).childbackPictureVideo);
                    } catch (Exception ignored) {
                    }
                } else {
                    try {
                        backLL.setVisibility(View.GONE);
                    } catch (Exception ignored) {

                    }
                }
                break;
            case "已验收":
                setPicRcv(supervisorPictureVideoRCV, detailsBean.childPVSUP);
                stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green2));
                break;
            default:
                break;
        }

        try {
            creatorTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.callPage(detailsBean.childCreator.get(0).tel, getContext());
                }
            });
        } catch (Exception ignored) {

        }
        try {
            jiansheTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.callPage(detailsBean.childC.get(0).tel, getContext());
                }
            });
        } catch (Exception ignored) {

        }
        try {
            supervisorTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.callPage(detailsBean.childS.get(0).tel, getContext());
                }
            });
        } catch (Exception ignored) {

        }
        try {
            backUserTalkImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SystemUtils.callPage(detailsBean.childS.get(0).tel, getContext());
                }
            });
        } catch (Exception ignored) {

        }
        try {
            if (UserInfo.create(getContext()).getUserId().equals(detailsBean.getCreatorId())) {
                deleteTV.setVisibility(View.VISIBLE);
                deleteTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        delateAbnormal();
                    }
                });
            }
        } catch (Exception ignored) {

        }

        setDescET(explainET, descNumTv);

    }

    //    private PictureOrVideoListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList;
    private TextView backUsernameTV;
    private TextView backCreationTimeTV;
    private TextView backCauseTV;
    private RecyclerView childbackPictureVideoRCV;
    private RecyclerView childPVCONRCV;
    private RecyclerView addPicRcv;
    private RecyclerView constructionPictureVideoRCV;
    private LinearLayout backLL;
    private LinearLayout jiasheLL;
    private LinearLayout jiaSheLL2;
    private LinearLayout jianliLL;
    private TextView jianshePeopleTimeTV;
    private TextView jiansheStateTV;
    private TextView constructionExplainTV;
    private TextView updateBT;
    private TextView deleteTV;
    private ImageView creatorTalkImg;
    private ImageView jiansheTalkImg;
    private ImageView supervisorTalkImg;
    private ImageView backUserTalkImg;

    private void getView(View view) {
        try {
            this.jianliLL = (LinearLayout) view.findViewById(R.id.jianliLL);
        } catch (Exception ignored) {

        }
        try {
            this.creatorTalkImg = (ImageView) view.findViewById(R.id.creatorTalkImg);
        } catch (Exception ignored) {

        }
        try {
            this.backUserTalkImg = (ImageView) view.findViewById(R.id.backUserTalkImg);
        } catch (Exception ignored) {

        }
        try {
            this.supervisorTalkImg = (ImageView) view.findViewById(R.id.supervisorTalkImg);
        } catch (Exception ignored) {

        }
        try {
            this.jiansheTalkImg = (ImageView) view.findViewById(R.id.jiansheTalkImg);
        } catch (Exception ignored) {

        }
        try {
            this.jiasheLL = (LinearLayout) view.findViewById(R.id.jiaSheLL);
        } catch (Exception ignored) {

        }
        try {
            this.jiaSheLL2 = (LinearLayout) view.findViewById(R.id.jiaSheLL2);
        } catch (Exception ignored) {

        }
        try {
            this.addPicRcv = (RecyclerView) view.findViewById(R.id.addPicRcv);
        } catch (Exception ignored) {

        }
        try {
            this.constructionPictureVideoRCV = (RecyclerView) view.findViewById(R.id.constructionPictureVideoRCV);
        } catch (Exception ignored) {

        }
        try {
            this.deleteTV = view.findViewById(R.id.deleteTV);
        } catch (Exception ignored) {

        }
        try {
            this.updateBT = view.findViewById(R.id.updateBT);
        } catch (Exception ignored) {

        }
        try {
            this.childPVCONRCV = (RecyclerView) view.findViewById(R.id.childPVCONRCV);
        } catch (Exception ignored) {

        }
        try {
            this.backTypeTV = view.findViewById(R.id.backTypeTV);
        } catch (Exception ignored) {

        }
        try {
            this.constructionExplainTV = view.findViewById(R.id.constructionExplainTV);
        } catch (Exception ignored) {

        }
        try {
            this.jiansheStateTV = view.findViewById(R.id.jiansheStateTV);
        } catch (Exception ignored) {

        }
        try {
            this.jianshePeopleTimeTV = view.findViewById(R.id.jianshePeopleTimeTV);
        } catch (Exception ignored) {

        }
        try {
            this.backLL = (LinearLayout) view.findViewById(R.id.backLL);
        } catch (Exception ignored) {

        }
        try {
            this.backCreationTimeTV = view.findViewById(R.id.backCreationTimeTV);
        } catch (Exception ignored) {

        }
        try {
            this.childbackPictureVideoRCV = (RecyclerView) view.findViewById(R.id.childbackPictureVideoRCV);
        } catch (Exception ignored) {

        }
        try {
            this.backCauseTV = view.findViewById(R.id.backCauseTV);
        } catch (Exception ignored) {

        }
        try {
            this.commitBT = view.findViewById(R.id.commitBT);
        } catch (Exception ignored) {

        }
        try {
            this.backUsernameTV = view.findViewById(R.id.backUsernameTV);
        } catch (Exception ignored) {

        }
        try {
            this.jianliStateTV = view.findViewById(R.id.jianliStateTV);
        } catch (Exception ignored) {

        }
        try {
            this.jianliPeopleTimeTV = view.findViewById(R.id.jianliPeopleTimeTV);
        } catch (Exception ignored) {

        }
        try {
            this.descNumTv = view.findViewById(R.id.descNumTv);
        } catch (Exception ignored) {

        }
        try {
            this.deleteBT = (Button) view.findViewById(R.id.deleteBT);
        } catch (Exception ignored) {

        }
        try {
            this.explainET = (EditText) view.findViewById(R.id.explainET);
        } catch (Exception ignored) {

        }
        try {
            this.pictureVideoRCV = (RecyclerView) view.findViewById(R.id.pictureVideoRCV);
        } catch (Exception ignored) {

        }
        try {
            this.copyPeopleLL = (LinearLayout) view.findViewById(R.id.copyPeopleLL);
        } catch (Exception ignored) {

        }
        try {
            this.copyPeopleTv = view.findViewById(R.id.copyPeopleTv);
        } catch (Exception ignored) {

        }
        try {
            this.supervisorLL = (LinearLayout) view.findViewById(R.id.supervisorLL);
        } catch (Exception ignored) {

        }
        try {
            this.supervisorTV = view.findViewById(R.id.supervisorTV);
        } catch (Exception ignored) {

        }
        try {
            this.supervisorTV2 = view.findViewById(R.id.supervisorTV2);
        } catch (Exception ignored) {

        }
        try {
            this.jiaSheLL = (LinearLayout) view.findViewById(R.id.jiaSheLL);
        } catch (Exception ignored) {

        }
        try {
            this.jianSheTV = view.findViewById(R.id.jianSheTV);
        } catch (Exception ignored) {

        }
        try {
            this.jianSheTV2 = view.findViewById(R.id.jianSheTV2);
        } catch (Exception ignored) {

        }
        try {
            this.constructionDirectorTV = view.findViewById(R.id.constructionDirectorTV);
        } catch (Exception ignored) {

        }
        try {
            this.constructionUnitTV = view.findViewById(R.id.constructionUnitTV);
        } catch (Exception ignored) {

        }
        try {
            this.handOverPartTV = view.findViewById(R.id.handOverPartTV);
        } catch (Exception ignored) {

        }
        try {
            this.sendBackLL = (LinearLayout) view.findViewById(R.id.sendBackLL);
        } catch (Exception ignored) {

        }
        try {
            this.sendBackNumberTV = view.findViewById(R.id.sendBackNumberTV);
        } catch (Exception ignored) {

        }
        try {
            this.siteNameTV = view.findViewById(R.id.siteNameTV);
        } catch (Exception ignored) {

        }
        try {
            this.sectionNameTV = view.findViewById(R.id.sectionNameTV);
        } catch (Exception ignored) {

        }
        try {
            this.projectNameTV = view.findViewById(R.id.projectNameTV);
        } catch (Exception ignored) {

        }
        try {
            this.passBT = view.findViewById(R.id.passBT);
        } catch (Exception ignored) {

        }
        try {
            this.sendBackBT = view.findViewById(R.id.sendBackBT);
        } catch (Exception ignored) {

        }
        try {
            this.supervisorPictureVideoRCV = (RecyclerView) view.findViewById(R.id.supervisorPictureVideoRCV);
        } catch (Exception ignored) {

        }
        try {
            this.explainIssueTV = view.findViewById(R.id.explainIssueTV);
        } catch (Exception ignored) {

        }
        try {
            this.creatorTimeTV = view.findViewById(R.id.creatorTimeTV);
        } catch (Exception ignored) {

        }
        try {
            this.constructionUnitLL = (LinearLayout) view.findViewById(R.id.constructionUnitLL);
        } catch (Exception ignored) {

        }
        try {
            this.handOverPartLL = (LinearLayout) view.findViewById(R.id.handOverPartLL);
        } catch (Exception ignored) {

        }
        try {
            this.taskStateTV = view.findViewById(R.id.taskStateTV);
        } catch (Exception ignored) {

        }
        try {
            this.stateView = (View) view.findViewById(R.id.stateView);
        } catch (Exception ignored) {

        }
        try {
            this.btnback = view.findViewById(R.id.btn_back);
        } catch (Exception ignored) {

        }
        try {
            this.handOverPartImg = (ImageView) view.findViewById(R.id.handOverPartImg);
        } catch (Exception ignored) {

        }
        try {
            this.copyPeopleRCV = (RecyclerView) view.findViewById(R.id.copyPeopleRCV);
        } catch (Exception ignored) {

        }
        try {
            this.supervisorExplainTV = view.findViewById(R.id.supervisorExplainTV);
        } catch (Exception ignored) {

        }
        try {
            this.deleteBT = (Button) view.findViewById(R.id.deleteBT);
        } catch (Exception ignored) {

        }
        try {
            this.reCommitBT = view.findViewById(R.id.reCommitBT);
        } catch (Exception ignored) {

        }
        try {
            this.bacTipTV = view.findViewById(R.id.bacTipTV);
        } catch (Exception ignored) {

        }

        try {
            handOverPartLL.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            btnback.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            constructionUnitLL.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            updateBT.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            supervisorLL.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            copyPeopleLL.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            commitBT.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            reCommitBT.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            sendBackBT.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            jiaSheLL.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            passBT.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            sendBackLL.setOnClickListener(this);
        } catch (Exception ignored) {

        }
        try {
            jiaSheLL2.setOnClickListener(this);
        } catch (Exception ignored) {

        }
    }

    /**
     * 输入框监听方法 可复用
     *
     * @param descET
     * @param descNumTv
     */
    private void setDescET(final EditText descET, final TextView descNumTv) {
        try {
            descET.setText("");
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
        } catch (Exception e) {

        }
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
     * 代办-  照片列表  能复用
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        try {
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
        } catch (Exception ignored) {
        }
    }

    /**
     * 设置 选择照片的那个 RCV 控件设置数据 可复用
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {
        try {

            pictureSelectRcv.setLayoutManager(new GridLayoutManager(getContext(),
                    4, LinearLayoutManager.VERTICAL, false));
            pictureSelectRcv.setNestedScrollingEnabled(true);
            if (selectList == null)
                selectList = new ArrayList<>();
            //adapter = new PictureOrVideoListAdapter(getContext());
            adapter = new PictureOrVideoListNewAdapter(getContext());
            PhotoCameraUtils.init(GXMsgFragmentNew.this).photoDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
        } catch (Exception ignored) {

        }
    }

    private String oldSelectIds;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Bundle bundle;
        if (requestCode == 203) {
            useIdBean.selectroom = data.getStringExtra("selectRoom");
            useIdBean.selectIds = data.getStringExtra("selectIds");
            handOverPartTV.setText(useIdBean.selectroom);
        }
        if (requestCode == SelectTRPOrAU.COPY_PEOPLE) {//抄送人
            copyPeople = data.getBundleExtra("bundle").getString("name");
            peopleId = data.getBundleExtra("bundle").getString("id");
            try {
                copyPeopleTv.setText(copyPeople);
            } catch (Exception ignored) {
            }
            if (!"空".equals(useIdBean.identifying)) {
                updateSQWT("copyPeople", copyPeople, "peopleId", peopleId);
            }
        }
        if (resultCode == SelectTRPOrAU.contractor) {//施工单位
            bundle = data.getBundleExtra("bundle");
            constructionUnit = bundle.getString("name");
            try {
                constructionUnitTV.setText(constructionUnit);
            } catch (Exception ignored) {
            }
        }
        if (resultCode == SelectTRPOrAU.Supervisor) {//监理
            bundle = data.getBundleExtra("bundle");
            supervisor = bundle.getString("name");
            supervisorTV.setText(supervisor);
            supervisorID = bundle.getString("id");

            if (!"空".equals(useIdBean.identifying)) {
                if (supervisorID.equals(constructionId)) {
                    ToastUtils.myToast(getActivity(), "同一个人不能是监理和建设单位验收人");
                    return;
                }
                if (!isReEditView) {
                    updateSQWT("supervisor", supervisor, "supervisorId", supervisorID);
                }
            }
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    int ss = selectList.size();
//                    selectList.clear();
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    break;
            }
        }

        if (resultCode == SelectTRPOrAU.ConstructionDirector) {//建设单位
            bundle = data.getBundleExtra("bundle");
            jianShePerson = bundle.getString("name");
            jianSheTV.setText(jianShePerson);
            constructionId = bundle.getString("id");
            if (!"空".equals(useIdBean.identifying)) {
                if (constructionId.equals(supervisorID)) {
                    ToastUtils.myToast(getActivity(), "同一个人不能是监理和建设单位验收人");
                    return;
                }
                if (!isReEditView) {
                    updateSQWT("constructionUnitPrincipal", jianShePerson, "constructionId", constructionId);
                }
            }
        }
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
            e.printStackTrace();
        }
    }

    protected void hideLoadView() {
        try {
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String copyPeople;
    private String constructionUnit;
    private String supervisor;
    private String peopleId;
    private String jianShePerson;

    /**
     * 申请验收
     */
    private void saveDetails(String picPath) {
        if (StringUtils.isEmpty(constructionUnit)) {
            ToastUtils.myToast(getActivity(), "未选择施工单位");
            return;
        }

        if ("1".equals(useIdBean.isNeedBuild) && StringUtils.isEmpty(constructionId)) {
            ToastUtils.myToast(getActivity(), "未选择建设单位验收人");
            return;
        }
        if (StringUtils.isEmpty(supervisor)) {
            ToastUtils.myToast(getActivity(), "未选择监理");
            return;
        }
        if (supervisorID.equals(constructionId)) {
            ToastUtils.myToast(getActivity(), "建设单位负责人和监理不能是同一个人");
            return;
        }
        showLoadView("请稍等...");
        HttpRequest.get(getContext())
                .url(ServerInterface.saveDetails)
                .params("roomTowerFloorId", useIdBean.selectIds)//  房间楼栋层次的id（多个拼接成字符串类型传输）
                .params("detailsName", (useIdBean.inspectionName + "-" + useIdBean.inspectionSunName))  //    详情报表中的标段和检查项名称
                .params("handOverPart", useIdBean.selectroom)  //    移交部位
                .params("sectionDikuaiName", useIdBean.sectionName + "(" + Constant.parentProjectName
                        + "-" + Constant.diKuaiName + ")")  // /标段地块拼接
                .params("siteName", "整栋".equals(useIdBean.partsDivision) ? useIdBean.towerName : (useIdBean.towerName + "-" + useIdBean.unitName))  //楼栋位置名称
                .params("sectionName", useIdBean.sectionName)  //标段名称
                .params("constructionUnit", constructionUnit)  //    施工单位
                .params("creatorId", UserInfo.create(getContext()).getUserId())  //    施工负责人
                .params("constructionDirector", UserInfo.create(getContext()).getUserName())  //    施工负责人
                .params("supervisor", supervisor)  //    监理
                .params("constructionUnitPrincipal", jianShePerson)  //    建设单位负责人
                .params("constructionId", constructionId)//建设单位负责人ID
                .params("copyPeople", copyPeople)  //    抄送人
                .params("pictureVideo", picPath)  //    图片视频
                .params("explainIssue", explainET.getText().toString().trim())  //    说明
                .params("sectionId", useIdBean.sectionId)  //    标段id
                .params("towerId", useIdBean.towerId)  //    楼栋ID
                .params("unitId", useIdBean.unitId)  //    单元Id
                .params("floorId", useIdBean.floorId)  //    单元Id
                .params("floorName", useIdBean.floorName)  //    单元Id
                .params("inspectionId", useIdBean.inspectionId)  // 检查项ID子名称
                .params("inspectionName", useIdBean.inspectionName)  //    检查项名称
                .params("inspectionSunName", useIdBean.inspectionSunName)  //    检查项子名称
                .params("secInsId", useIdBean.secInsId)//工序移交列表信息主键id
                .params("partsDivision", useIdBean.partsDivision)//同时做查询条件，需要进行添加数据；部位类型 整栋--分户--分单元-整层----不分单元-整层
                .params("supervisorId", supervisorID)//监理人ID
                .params("peopleId", peopleId)//抄送人ID-String类型
                .params("pkId", Constant.projectId)
                .params("towerName", useIdBean.towerName)
                .params("unitName", useIdBean.unitName)
                .params("projectId", Constant.parentProjectId)
                .params("projectName", Constant.parentProjectName)
                .params("dikuaiName", Constant.diKuaiName)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("保存验收==", result.toString());
                        try {
                            hideLoadView();
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                if (useIdBean.selectIds.contains(",")) {
                                    String[] ids = useIdBean.selectIds.split(",");
                                    for (int i = 0; i < ids.length; i++) {
                                        String key = UserInfo.create(getContext()).getUserId() + useIdBean.towerId + useIdBean.unitId + ids[i] + useIdBean.inspectionId;
                                        ZanCunUtil.creat(getContext()).remove(key);
                                    }
                                } else {
                                    String key = UserInfo.create(getContext()).getUserId()
                                            + useIdBean.towerId + useIdBean.unitId + useIdBean.selectIds + useIdBean.inspectionId;
                                    ZanCunUtil.creat(getContext()).remove(key);
                                }

                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                EventBus.getDefault().post("GXYJTaskStateChangedidentifying");
                                if (useIdBean.selectroom.contains(",")) {
                                    EventBus.getDefault().post("GXYJTaskIDIsDouble");
                                }
                                selectList.clear();
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                        hideLoadView();
                    }
                });
    }

    /**
     * 更新 再次 验收
     */
    private void updateDetails(String picPath) {
        try {
            if (supervisorID.equals(constructionId)) {
                ToastUtils.myToast(getActivity(), "同一个人不能是监理和建设单位验收人");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        showLoadView("请求中...");
        HttpRequest.get(getContext())
                .url(ServerInterface.updateDetails)
                .params("roomTowerFloorId", detailsBean.roomTowerFloorId)//  房间楼栋层次的id（多个拼接成字符串类型传输）
                .params("detailsName", detailsBean.detailsName)  //    详情报表中的标段和检查项名称
                .params("handOverPart", detailsBean.handOverPart)  //    移交部位
                .params("siteName", detailsBean.siteName)  //楼栋位置名称
                .params("sectionName", detailsBean.sectionName)  //标段名称
                .params("constructionUnit", detailsBean.constructionUnit)  //    施工单位
                .params("creatorId", detailsBean.creatorId)  //    施工负责人
                .params("constructionDirector", detailsBean.constructionDirector)  //    施工负责人
                .params("supervisor", supervisor)  //    监理
                .params("constructionUnitPrincipal", jianShePerson)  //    建设单位负责人
                .params("copyPeople", TextUtils.isEmpty(copyPeople) ? "null" : copyPeople)  //    抄送人
                .params("pictureVideo", picPath)  //    图片视频
                .params("explainIssue", explainET.getText().toString().trim())  //    说明
                .params("sectionId", detailsBean.sectionId)  //    标段id
                .params("towerId", detailsBean.towerId)  //    楼栋ID
                .params("floorId", useIdBean.floorId)  //    单元Id
                .params("floorName", useIdBean.floorName)  //    单元Id
                .params("unitId", detailsBean.unitId)  //    单元Id
                .params("inspectionId", detailsBean.inspectionId)  // 检查项ID子名称
                .params("inspectionName", detailsBean.inspectionName)  //    检查项名称
                .params("inspectionSunName", detailsBean.inspectionSunName)  //    检查项子名称
                .params("secInsId", detailsBean.secInsId)//工序移交列表信息主键id
                .params("partsDivision", detailsBean.partsDivision)//同时做查询条件，需要进行添加数据；部位类型 整栋--分户--分单元-整层----不分单元-整层
                .params("supervisorId", supervisorID)//监理人ID
                .params("constructionId", constructionId)//建设单位负责人ID
                .params("peopleId", TextUtils.isEmpty(peopleId) ? "null" : peopleId)//抄送人ID-String类型
                .params("pkId", detailsBean.pkId)
                .params("towerName", detailsBean.towerName)
                .params("unitName", detailsBean.unitName)
                .params("projectId", detailsBean.projectId)
                .params("projectName", detailsBean.projectName)
                .params("dikuaiName", detailsBean.dikuaiName)
                .params("identifying", "待验收")
                .params("id", id)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("保存验收==", result.toString());
                        try {
                            hideLoadView();
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                selectList.clear();
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                        hideLoadView();
                    }
                });
    }

    //监理验收通过
    private void JlCrossYs(String picPath) {
        showLoadView("操作中...");
        HttpRequest.get(getActivity()).url(ServerInterface.updateVerificationBy)
                .params("id", id)
                .params("supervisorPictureVideo", picPath)
                .params("supervisorExplain", UiUtils.getContent(explainET))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("验收通过====", result.toString());
                        try {
                            hideLoadView();
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optString("code").equals("0")) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                selectList.clear();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                        hideLoadView();
                    }
                });
    }

    //建设单位验收通过
    private void JSCrossYs(String picPath) {
        showLoadView("操作中...");
        HttpRequest.get(getActivity()).url(ServerInterface.updateVBConstruction)
                .params("id", id)
                .params("constructionPictureVideo", picPath)
                .params("constructionExplain", UiUtils.getContent(explainET))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        LogUtils.i("验收通过====", result.toString());
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optString("code").equals("0")) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                selectList.clear();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                        hideLoadView();
                    }
                });
    }


    private void updateSQWT(String Namekey, String Namevalue, String key, String value) {
        showLoadView("操作中...");
        HttpRequest.get(getContext())
                .url(ServerInterface.updateSQWT)
                .params("id", id)
                .params(Namekey, Namevalue)
                .params(key, value)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            hideLoadView();
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optString("code").equals("0")) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();

                    }
                });
    }

    /**
     * 删除工序移交详情问题
     */
    private void delateAbnormal() {
        showLoadView("操作中...");
        HttpRequest.get(getContext())
                .url(ServerInterface.delateAbnormal)
                .params("gxyjId", detailsBean.id)//  主键ID
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            hideLoadView();
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                EventBus.getDefault().post("deleteGXYJTask");
                            }
                        } catch (JSONException ignored) {
                            ignored.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

}
