package com.haozhiyan.zhijian.fragment.AcceptanceMaterials_Child;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.workXcjc.FunctionActivity;
import com.haozhiyan.zhijian.activity.workXcjc.SelectAngle;
import com.haozhiyan.zhijian.adapter.PersonNameListAdapter;
import com.haozhiyan.zhijian.bean.MaterialsTaskBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.TimeFormatUitls;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

import static com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials.CLYSState;
import static com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials.ENTRANCE;

/**
 * A simple {@link Fragment} subclass.
 * 材料验收- 任务
 */
public class AM_TaskFragment extends Fragment implements View.OnClickListener {
    private View normalView;
    private View changeView;
    private RelativeLayout parentViewGroup;
    public String id;
//    public String state;

    private MaterialsTaskBean taskBean;

    private TextView bdTV;
    private LinearLayout bdll;
    private TextView clNameTV;
    private LinearLayout clNameLL;
    private TextView clTypeTV;
    private LinearLayout clTypeLL;
    private TextView clShopTV;
    private LinearLayout clShopLL;
    private EditText usePlaceET;
    private EditText descET;
    private TextView descNumTv;
    private TextView receiveTV;
    private LinearLayout receiveLL;
    private TextView receivePartTV;
    private LinearLayout receivePartLL;
    private TextView supervisorTV;
    private LinearLayout supervisorLL;
    private Switch acceptSW;
    private TextView buildAcceptTV;
    private LinearLayout buildAcceptLL;
    private TextView copyTV;
    private LinearLayout copyLL;
    private Button commitBT;
    private TextView supplierName;
    private TextView receiveName;
    private TextView receiveUnitName;
    private TextView supervisorName;
    private RecyclerView acceptanceList;
    private RecyclerView ccList;
    private TextView submitName;
    private TextView submitDate;
    private Button deleteBT;
    private Button changeBT;

    private TextView sectionNameTV;
    private TextView nameInspectionTV;
    private TextView typeInspectionTV;
    private TextView applypartTV;
    private TextView supplierNameTV;
    private TextView supplementTV;
    private TextView receiveNameTV;
    private TextView receiveUnitNameTV;
    private TextView supervisorNameTV;
    private TextView submitNameTV;
    private TextView submitDateTV;
    private TextView ccListTipTV;
    private RecyclerView acceptanceListRCV;
    private RecyclerView ccListRCV;


    private String sectionName;
    private String sectionId;
    private String receive;
    private String acceptance;
    private String ccName;
    private String applypart;
    private String supplement;
    private String cc;
    private String nameInspection;
    private String nameInspectionId;
    private String typeInspection;
    private String typeInspectionId;
    private String isneedAcceptance = "0";
    private String supplierId;
    private String supervisor;
    private String receiveUnit;

//    public static synchronized AM_TaskFragment build(String id, String state) {
//        AM_TaskFragment fragment = new AM_TaskFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("id", id);
//        bundle.putString("state", state);
//        fragment.setArguments(bundle);
//        return fragment;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            id = getArguments().getString("id", "");
//            state = getArguments().getString("state", "");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_parent, container, false);
        if (rootView != null) {
            parentViewGroup = rootView.findViewById(R.id.parentViewGroup);
        }
        try {
            String data = SPUtil.get(getContext()).get(getClass().getSimpleName());
            taskBean = new Gson().fromJson(data, MaterialsTaskBean.class);
            if (taskBean.code == 0) {
                initView();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }

//    /**
//     * 材料验收-任务详情
//     */
//    private void infoClysTask() {
//        HttpRequest.get(getContext())
//                .url(ServerInterface.infoClysTask)
//                .params("id", id)
//                .doGet(new HttpStringCallBack() {
//                    @Override
//                    public void onSuccess(Object result) {
//                        try {
//                            taskBean = new Gson().fromJson(result.toString(), MaterialsTaskBean.class);
//                            if (taskBean.code == 0) {
//                                initView();
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//
//                    }
//                });
//    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bdll://标段
                Constant.OTHER_REN_TYPE = 4;
                Bundle bundle = new Bundle();
                bundle.putString("bdName", UiUtils.getContent(bdTV));
                jumpActivityForResult(FunctionActivity.class, Constant.BIAO_DUAN_RESULT_CODE, bundle);
                break;
            case R.id.clNameLL://材料名称
                SelectTRPOrAU.select(this, nameInspectionId, sectionId, SelectTRPOrAU.MaterialsName);//材料名称
                break;
            case R.id.clTypeLL://材料类型
                SelectTRPOrAU.select(this, typeInspectionId, sectionId, nameInspectionId, SelectTRPOrAU.MaterialsType);//材料类型
                break;
            case R.id.clShopLL://材料供应商
                SelectTRPOrAU.select(this, supplierId, sectionId, SelectTRPOrAU.MaterialsShop);//材料供应商
                break;
            case R.id.receiveLL://接收人
                SelectTRPOrAU.select(this, receive, SelectTRPOrAU.ReceicvePerson);//接收人
                break;
            case R.id.receivePartLL://接收单位
                SelectTRPOrAU.select(this, receiveUnit, sectionId, SelectTRPOrAU.receiveUnit);//接收单位
                break;
            case R.id.supervisorLL://监理
                SelectTRPOrAU.select(this, supervisor, SelectTRPOrAU.Supervisor);//监理
                break;
            case R.id.buildAcceptLL://建设单位验收人
                Constant.REN_TYPE = 4;
                Bundle bundle2 = new Bundle();
                bundle2.putString("id", acceptance);
                jumpActivityForResult(SelectAngle.class, Constant.Jian_She, bundle2);
                break;
            case R.id.copyLL://抄送人
                Constant.REN_TYPE = 3;
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", cc);
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE, bundle3);
                break;
            case R.id.changeBT:
                if ("2".equals(CLYSState)) {//待验收
                    changeLayoutState2();
                } else {
                    changeLayout();
                }
                break;
            case R.id.commitBT:
                if ("2".equals(CLYSState)) {//待验收
                    updateClysTask2();
                } else {
                    updateClysTask();
                }

                break;
            case R.id.deleteBT:
                delClysTask();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Bundle bundle;
        if (requestCode == -123310) {

        } else if (requestCode == Constant.CHAO_SONG_REN_CODE) {//抄送人
            ccName = data.getStringExtra("selectType");
            cc = data.getStringExtra("selectId");
//            if (!StringUtils.isEmpty(ccName)) {
//                List<String> chaoSongName = ListUtils.stringToList(ccName);
//                List<String> chaoSongId = ListUtils.stringToList(cc);
//            }
            copyTV.setText(ccName + "");

        } else if (requestCode == Constant.BIAO_DUAN_RESULT_CODE) {//标段
            sectionName = data.getStringExtra("content");
            sectionId = data.getStringExtra("id");
            bdTV.setText(sectionName);
        } else if (resultCode == Constant.Jian_She) {//建设单位
            String jianShePerson = data.getStringExtra("selectType");
            acceptance = data.getStringExtra("selectId");
//            if (!StringUtils.isEmpty(jianShePerson)) {
//                List<String> chaoSongName = ListUtils.stringToList(jianShePerson);
//                List<String> chaoSongId = ListUtils.stringToList(acceptance);
//            }
            buildAcceptTV.setText(jianShePerson + "");
        } else if (resultCode == SelectTRPOrAU.Supervisor) {//监理
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            supervisor = bundle.getString("id");
            supervisorTV.setText(jianShePerson);
        } else if (resultCode == SelectTRPOrAU.MaterialsName) {//材料名称
            bundle = data.getBundleExtra("bundle");
            nameInspection = bundle.getString("name");
            nameInspectionId = bundle.getString("id");
            clNameTV.setText(nameInspection);
            typeInspectionId = "";
            typeInspection = "";
            clTypeTV.setText("");
            clTypeLL.setVisibility(View.VISIBLE);
        } else if (resultCode == SelectTRPOrAU.MaterialsType) {//材料类型
            bundle = data.getBundleExtra("bundle");
            typeInspection = bundle.getString("name");
            typeInspectionId = bundle.getString("id");
            clTypeTV.setText(typeInspection);
        } else if (resultCode == SelectTRPOrAU.MaterialsShop) {//材料供应商
            bundle = data.getBundleExtra("bundle");
            String shopName = bundle.getString("name");
            supplierId = bundle.getString("id");
            clShopTV.setText(shopName);
        } else if (resultCode == SelectTRPOrAU.ReceicvePerson) {//接收人
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            receive = bundle.getString("id");
            receiveTV.setText(jianShePerson);
        } else if (resultCode == SelectTRPOrAU.receiveUnit) {//接收单位
            bundle = data.getBundleExtra("bundle");
            String jianShePerson = bundle.getString("name");
            receiveUnit = bundle.getString("id");
            receivePartTV.setText(jianShePerson);
        }
    }

    private ImageView receiveTalkImg;
    private ImageView supervisorTalkImg;
    private ImageView submitTalkImg;

    /**
     * 展示布局
     */
    private void initView() {
        normalView = LayoutInflater.from(getContext()).inflate(R.layout.cailiaoyanshou_task_show, parentViewGroup, false);
        normalView.findViewById(R.id.deleteBT).setOnClickListener(this);
        normalView.findViewById(R.id.changeBT).setOnClickListener(this);
        parentViewGroup.addView(normalView);
        sectionNameTV = (TextView) normalView.findViewById(R.id.sectionNameTV);
        typeInspectionTV = (TextView) normalView.findViewById(R.id.typeInspectionTV);
        nameInspectionTV = (TextView) normalView.findViewById(R.id.nameInspectionTV);
        typeInspectionTV = (TextView) normalView.findViewById(R.id.typeInspectionTV);
        applypartTV = (TextView) normalView.findViewById(R.id.applypartTV);
        supplierNameTV = (TextView) normalView.findViewById(R.id.supplierNameTV);
        supplementTV = (TextView) normalView.findViewById(R.id.supplementTV);
        receiveNameTV = (TextView) normalView.findViewById(R.id.receiveNameTV);
        receiveUnitNameTV = (TextView) normalView.findViewById(R.id.receiveUnitNameTV);
        supervisorNameTV = (TextView) normalView.findViewById(R.id.supervisorNameTV);
        submitNameTV = (TextView) normalView.findViewById(R.id.submitNameTV);
        submitDateTV = (TextView) normalView.findViewById(R.id.submitDateTV);
        ccListTipTV = (TextView) normalView.findViewById(R.id.ccListTipTV);
        acceptanceListRCV = (RecyclerView) normalView.findViewById(R.id.acceptanceListRCV);
        ccListRCV = (RecyclerView) normalView.findViewById(R.id.ccListRCV);
        receiveTalkImg = (ImageView) normalView.findViewById(R.id.receiveTalkImg);
        supervisorTalkImg = (ImageView) normalView.findViewById(R.id.supervisorTalkImg);
        submitTalkImg = (ImageView) normalView.findViewById(R.id.submitTalkImg);

        deleteBT = (Button) normalView.findViewById(R.id.deleteBT);
        deleteBT.setOnClickListener(this);
        changeBT = (Button) normalView.findViewById(R.id.changeBT);
        changeBT.setOnClickListener(this);

        receiveTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < taskBean.clysTask.receiveList.size(); i++) {
                        if (String.valueOf(taskBean.clysTask.receive)
                                .equals(String.valueOf(taskBean.clysTask.receiveList.get(i).userId))) {
                            SystemUtils.callPage(taskBean.clysTask.receiveList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        supervisorTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < taskBean.clysTask.supervisorList.size(); i++) {
                        if (String.valueOf(taskBean.clysTask.supervisor)
                                .equals(String.valueOf(taskBean.clysTask.supervisorList.get(i).userId))) {
                            SystemUtils.callPage(taskBean.clysTask.supervisorList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });
        submitTalkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < taskBean.clysTask.submitList.size(); i++) {
                        if (String.valueOf(taskBean.clysTask.submit)
                                .equals(String.valueOf(taskBean.clysTask.submitList.get(i).userId))) {
                            SystemUtils.callPage(taskBean.clysTask.submitList.get(i).tel, getContext());
                            break;
                        }
                    }
                } catch (Exception e) {

                }
            }
        });

        switch (CLYSState) {
            case "1"://待申请进场状态  如果任务的创建者是自己就显示 删除和修改按钮
                if (taskBean.clysTask.submit.equals(UserInfo.create(getContext()).getUserId())
                        && !TextUtils.equals(ENTRANCE, "form")) {
                    deleteBT.setVisibility(View.VISIBLE);
                    changeBT.setVisibility(View.VISIBLE);
                }
                break;
        }

        sectionNameTV.setText(taskBean.clysTask.sectionName + "");
        nameInspectionTV.setText(taskBean.clysTask.nameInspection + "");
        typeInspectionTV.setText(taskBean.clysTask.typeInspection + "");
        if (StringUtils.isEmpty(taskBean.clysTask.applypart)) {
            applypartTV.setText("无");
        } else {
            applypartTV.setText(taskBean.clysTask.applypart + "");
        }
        supplierNameTV.setText(taskBean.clysTask.supplierName + "");


        if (StringUtils.isEmpty(taskBean.clysTask.supplement)) {
            normalView.findViewById(R.id.supplementLL).setVisibility(View.GONE);
        } else {
            normalView.findViewById(R.id.supplementLL).setVisibility(View.VISIBLE);
            supplementTV.setText(taskBean.clysTask.supplement + "");
        }


        receiveNameTV.setText(taskBean.clysTask.receiveName + "");
        receiveUnitNameTV.setText(taskBean.clysTask.receiveUnitName + "");
        supervisorNameTV.setText(taskBean.clysTask.supervisorName + "");
        submitNameTV.setText(taskBean.clysTask.submitName + "");
        submitDateTV.setText(taskBean.clysTask.submitDate + "");

        sectionId = taskBean.clysTask.sectionId;
        nameInspectionId = taskBean.clysTask.nameInspectionId;
        nameInspection = taskBean.clysTask.nameInspection;
        typeInspection = taskBean.clysTask.typeInspection;
        typeInspectionId = taskBean.clysTask.typeInspectionId;
        supplierId = taskBean.clysTask.supplierId;
        applypart = taskBean.clysTask.applypart;
        supplement = taskBean.clysTask.supplement;
        receive = taskBean.clysTask.receive;
        receiveUnit = taskBean.clysTask.receiveUnit;
        supervisor = taskBean.clysTask.supervisor;
        isneedAcceptance = taskBean.clysTask.isneedAcceptance;
        acceptance = taskBean.clysTask.acceptance;
        cc = taskBean.clysTask.cc;
        ccName = taskBean.clysTask.ccName;

        try {
            if (taskBean.clysTask.acceptanceList == null || taskBean.clysTask.acceptanceList.size() <= 0) {
                normalView.findViewById(R.id.acceptanceLL).setVisibility(View.GONE);
            } else {
                setNameRcv(acceptanceListRCV, taskBean.clysTask.acceptanceList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (taskBean.clysTask.ccList.size() <= 0) {
                ccListTipTV.setVisibility(View.VISIBLE);
            } else {
                ccListTipTV.setVisibility(View.GONE);
                setNameRcv(ccListRCV, taskBean.clysTask.ccList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑布局
     */
    private void changeLayout() {
        changeView = LayoutInflater.from(getContext()).inflate(R.layout.cailiaoyanshou_task_edit, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
        bdTV = (TextView) changeView.findViewById(R.id.bdTV);
        bdll = (LinearLayout) changeView.findViewById(R.id.bdll);
        clNameTV = (TextView) changeView.findViewById(R.id.clNameTV);
        clNameLL = (LinearLayout) changeView.findViewById(R.id.clNameLL);
        clTypeTV = (TextView) changeView.findViewById(R.id.clTypeTV);
        clTypeLL = (LinearLayout) changeView.findViewById(R.id.clTypeLL);
        clShopTV = (TextView) changeView.findViewById(R.id.clShopTV);
        clShopLL = (LinearLayout) changeView.findViewById(R.id.clShopLL);
        usePlaceET = (EditText) changeView.findViewById(R.id.usePlaceET);
        descET = (EditText) changeView.findViewById(R.id.descET);
        descNumTv = (TextView) changeView.findViewById(R.id.descNumTv);
        receiveTV = (TextView) changeView.findViewById(R.id.receiveTV);
        receiveLL = (LinearLayout) changeView.findViewById(R.id.receiveLL);
        receivePartTV = (TextView) changeView.findViewById(R.id.receivePartTV);
        receivePartLL = (LinearLayout) changeView.findViewById(R.id.receivePartLL);
        supervisorTV = (TextView) changeView.findViewById(R.id.supervisorTV);
        supervisorLL = (LinearLayout) changeView.findViewById(R.id.supervisorLL);
        acceptSW = (Switch) changeView.findViewById(R.id.acceptSW);
        buildAcceptTV = (TextView) changeView.findViewById(R.id.buildAcceptTV);
        buildAcceptLL = (LinearLayout) changeView.findViewById(R.id.buildAcceptLL);
        copyTV = (TextView) changeView.findViewById(R.id.copyTV);
        copyLL = (LinearLayout) changeView.findViewById(R.id.copyLL);
        commitBT = (Button) changeView.findViewById(R.id.commitBT);

        bdTV.setText(taskBean.clysTask.sectionName + "");
        clNameTV.setText(taskBean.clysTask.nameInspection + "");
        clTypeTV.setText(taskBean.clysTask.typeInspection + "");
        clShopTV.setText(taskBean.clysTask.supplierName + "");

        if (!StringUtils.isEmpty(taskBean.clysTask.applypart)) {
            usePlaceET.setText(taskBean.clysTask.applypart + "");
        }
        if (!StringUtils.isEmpty(taskBean.clysTask.supplement)) {
            descET.setText(taskBean.clysTask.supplement + "");
        }
        try {
            descNumTv.setText((200 - taskBean.clysTask.supplement.length()) + "");
        } catch (Exception e) {

        }
        try {
            setDescET(descET, descNumTv);
        } catch (Exception e) {

        }


        receiveTV.setText(taskBean.clysTask.receiveName + "");
        receivePartTV.setText(taskBean.clysTask.receiveUnitName + "");
        supervisorTV.setText(taskBean.clysTask.supervisorName + "");
        submitNameTV.setText(taskBean.clysTask.submitName + "");
        boolean isChecked = taskBean.clysTask.isneedAcceptance.equals("1");
        acceptSW.setChecked(isChecked);
        buildAcceptLL.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        isneedAcceptance = isChecked ? "1" : "0";
        buildAcceptTV.setText("");
        for (int i = 0; i < taskBean.clysTask.acceptanceList.size(); i++) {
            if (i > 0) {
                buildAcceptTV.append(",");
            }
            buildAcceptTV.append(taskBean.clysTask.acceptanceList.get(i).peopleuser);
        }

        copyTV.setText("");
        for (int i = 0; i < taskBean.clysTask.ccList.size(); i++) {
            if (i > 0) {
                copyTV.append(",");
            }
            copyTV.append(taskBean.clysTask.ccList.get(i).peopleuser);
        }

        bdll.setOnClickListener(this);
        clNameLL.setOnClickListener(this);
        clTypeLL.setOnClickListener(this);
        clShopLL.setOnClickListener(this);
        receiveLL.setOnClickListener(this);
        receivePartLL.setOnClickListener(this);
        supervisorLL.setOnClickListener(this);
        buildAcceptLL.setOnClickListener(this);
        copyLL.setOnClickListener(this);
        commitBT.setOnClickListener(this);
        acceptSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buildAcceptLL.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                isneedAcceptance = isChecked ? "1" : "0";
            }
        });

        setDescET(descET, descNumTv);

        usePlaceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applypart = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 待验收状态时 编辑布局
     */
    private void changeLayoutState2() {
        changeView = LayoutInflater.from(getContext()).inflate(R.layout.cailiaoyanshou_task_edit2, parentViewGroup, false);
        parentViewGroup.removeAllViews();
        parentViewGroup.addView(changeView);
        bdTV = (TextView) changeView.findViewById(R.id.bdTV);
        bdll = (LinearLayout) changeView.findViewById(R.id.bdll);
        clNameTV = (TextView) changeView.findViewById(R.id.clNameTV);
        clNameLL = (LinearLayout) changeView.findViewById(R.id.clNameLL);
        clTypeTV = (TextView) changeView.findViewById(R.id.clTypeTV);
        clTypeLL = (LinearLayout) changeView.findViewById(R.id.clTypeLL);
        clShopTV = (TextView) changeView.findViewById(R.id.clShopTV);
        clShopLL = (LinearLayout) changeView.findViewById(R.id.clShopLL);
        usePlaceET = (EditText) changeView.findViewById(R.id.usePlaceET);
        receiveTV = (TextView) changeView.findViewById(R.id.receiveTV);
        receiveLL = (LinearLayout) changeView.findViewById(R.id.receiveLL);
        receivePartTV = (TextView) changeView.findViewById(R.id.receivePartTV);
        receivePartLL = (LinearLayout) changeView.findViewById(R.id.receivePartLL);
        supervisorTV = (TextView) changeView.findViewById(R.id.supervisorTV);
        supervisorLL = (LinearLayout) changeView.findViewById(R.id.supervisorLL);
        acceptSW = (Switch) changeView.findViewById(R.id.acceptSW);
        buildAcceptTV = (TextView) changeView.findViewById(R.id.buildAcceptTV);
        buildAcceptLL = (LinearLayout) changeView.findViewById(R.id.buildAcceptLL);
        copyTV = (TextView) changeView.findViewById(R.id.copyTV);
        copyLL = (LinearLayout) changeView.findViewById(R.id.copyLL);
        commitBT = (Button) changeView.findViewById(R.id.commitBT);
        supplementTV = changeView.findViewById(R.id.supplementTV);

        bdTV.setText(taskBean.clysTask.sectionName + "");
        clNameTV.setText(taskBean.clysTask.nameInspection + "");
        clTypeTV.setText(taskBean.clysTask.typeInspection + "");
        clShopTV.setText(taskBean.clysTask.supplierName + "");
        usePlaceET.setText(taskBean.clysTask.applypart + "");
        supplementTV.setText(taskBean.clysTask.supplement + "");


        receiveTV.setText(taskBean.clysTask.receiveName + "");
        receivePartTV.setText(taskBean.clysTask.receiveUnitName + "");
        supervisorTV.setText(taskBean.clysTask.supervisorName + "");
        submitNameTV.setText(taskBean.clysTask.submitName + "");
        boolean isChecked = taskBean.clysTask.isneedAcceptance.equals("1");
        acceptSW.setChecked(isChecked);
        buildAcceptLL.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        isneedAcceptance = isChecked ? "1" : "0";
        buildAcceptTV.setText("");
        for (int i = 0; i < taskBean.clysTask.acceptanceList.size(); i++) {
            if (i > 0) {
                buildAcceptTV.append(",");
            }
            buildAcceptTV.append(taskBean.clysTask.acceptanceList.get(i).peopleuser);
        }

        if (taskBean.clysTask.ccList.size() <= 0) {
            copyTV.setText("无");
        } else {
            copyTV.setText("");
            for (int i = 0; i < taskBean.clysTask.ccList.size(); i++) {
                if (i > 0) {
                    copyTV.append(",");
                }
                copyTV.append(taskBean.clysTask.ccList.get(i).peopleuser);
            }
        }


//        bdll.setOnClickListener(this);
//        clNameLL.setOnClickListener(this);
//        clTypeLL.setOnClickListener(this);
        clShopLL.setOnClickListener(this);
//        receiveLL.setOnClickListener(this);
        receivePartLL.setOnClickListener(this);
//        supervisorLL.setOnClickListener(this);
//        buildAcceptLL.setOnClickListener(this);
//        copyLL.setOnClickListener(this);
//        commitBT.setOnClickListener(this);
        acceptSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                buildAcceptLL.setVisibility(isChecked ? View.VISIBLE : View.GONE);
                isneedAcceptance = isChecked ? "1" : "0";
            }
        });

        setDescET(descET, descNumTv);

        usePlaceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applypart = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                supplement = s.toString();
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
     * 材料验收-修改任务，申请进场验收
     */
    private void updateClysTask() {
        if (StringUtils.isEmpty(typeInspectionId)) {
            ToastUtils.myToast(getActivity(), "请选择材料类型");
            return;
        }
        if (isneedAcceptance.equals("1")) {
            if (StringUtils.isEmpty(acceptance)) {
                ToastUtils.myToast(getActivity(), "请选择建设单位验收人");
                return;
            }
            try {
                List<String> acceptancelist = ListUtils.stringToList(acceptance);
                if (acceptancelist.contains(supervisor)) {
                    ToastUtils.myToast(getActivity(), "同一个人不能是监理和建设单位验收人");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.updateClysTask)
                .params("id", id)//主键id
//                .params("state", "1")//状态(1待申请进场，2待验收，3已验收，4待送检，5待上传报告，6待复验，7待退场，8已退场，9送检合格，10复验合格)
//                .params("projectId", id)//项目id
                .params("sectionId", sectionId)//所属标段：标段id
//                .params("sectionName", sectionName)//
                .params("nameInspectionId", nameInspectionId)//材料名称：第二级名称id
                .params("nameInspection", nameInspection)//材料名称:第二级名称
//                .params("number", "")//第几批
                .params("typeInspectionId", typeInspectionId)//材料类型：第三级名称id
                .params("typeInspection", typeInspection)//材料类型：第三级名称
                .params("supplierId", supplierId)//材料供应商id
//                .params("supplierName", "")//
                .params("applypart", applypart)//适用部位
                .params("supplement", supplement)//补充说明
                .params("receive", receive)//接收人id
//                .params("receiveName", "")//
                .params("receiveUnit", receiveUnit)//接收单位id
//                .params("receiveUnitName", "")//
                .params("supervisor", supervisor)//监理id
//                .params("supervisorName", "")//
                .params("isneedAcceptance", isneedAcceptance)//需建设单位验收：0否，1是
                .params("acceptance", acceptance)//建设单位验收人id
//                .params("acceptanceName", "")//
                .params("cc", cc)//抄送人id
                .params("ccName", ccName)//
                .params("submit", UserInfo.create(getContext()).getUserId())//创建人id
//                .params("submitName", "")//
                .params("submitDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//创建时间
//                .params("approachDate", "")//进场时间
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

    /**
     * 材料验收-修改任务，待验收
     */
    private void updateClysTask2() {
        HttpRequest.get(getContext())
                .url(ServerInterface.updateClysTask)
                .params("id", id)//主键id
                .params("supplierId", supplierId)//材料供应商id
                .params("applypart", applypart)//适用部位
                .params("receiveUnit", receiveUnit)//接收单位id
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

    /**
     * 删除任务
     */
    private void delClysTask() {
        HttpRequest.get(getContext())
                .url(ServerInterface.delClysTask)
                .params("id", id)//主键id
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                EventBus.getDefault().post("clysTaskDelete");
                                setArguments(null);
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
