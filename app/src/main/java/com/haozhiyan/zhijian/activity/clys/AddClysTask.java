package com.haozhiyan.zhijian.activity.clys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.workXcjc.FunctionActivity;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.TimeFormatUitls;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.List;

/**
 * 新建材料验收任务
 */
public class AddClysTask extends BaseActivity2 implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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


    private String sectionName;
    private String sectionId;
    private String receive;
    private String acceptance;
    private String ccName;
    private String applypart = "";
    private String supplement = "";
    private String cc;
    private String nameInspection;
    private String nameInspectionId;
    private String typeInspection;
    private String typeInspectionId;
    private int isneedAcceptance = 1;


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_add_clys_task;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("新建材料验收任务");
        setAndroidNativeLightStatusBar(true);
        setTitleRightmenu();

        bdTV = (TextView) findViewById(R.id.bdTV);
        bdll = (LinearLayout) findViewById(R.id.bdll);
        clNameTV = (TextView) findViewById(R.id.clNameTV);
        clNameLL = (LinearLayout) findViewById(R.id.clNameLL);
        clTypeTV = (TextView) findViewById(R.id.clTypeTV);
        clTypeLL = (LinearLayout) findViewById(R.id.clTypeLL);
        clShopTV = (TextView) findViewById(R.id.clShopTV);
        clShopLL = (LinearLayout) findViewById(R.id.clShopLL);
        usePlaceET = (EditText) findViewById(R.id.usePlaceET);
        descET = (EditText) findViewById(R.id.descET);
        descNumTv = (TextView) findViewById(R.id.descNumTv);
        receiveTV = (TextView) findViewById(R.id.receiveTV);
        receiveLL = (LinearLayout) findViewById(R.id.receiveLL);
        receivePartTV = (TextView) findViewById(R.id.receivePartTV);
        receivePartLL = (LinearLayout) findViewById(R.id.receivePartLL);
        supervisorTV = (TextView) findViewById(R.id.supervisorTV);
        supervisorLL = (LinearLayout) findViewById(R.id.supervisorLL);
        acceptSW = (Switch) findViewById(R.id.acceptSW);
        buildAcceptTV = (TextView) findViewById(R.id.buildAcceptTV);
        buildAcceptLL = (LinearLayout) findViewById(R.id.buildAcceptLL);
        copyTV = (TextView) findViewById(R.id.copyTV);
        copyLL = (LinearLayout) findViewById(R.id.copyLL);
        commitBT = (Button) findViewById(R.id.commitBT);


        try {
            receiveTV.setText(UserInfo.create(getContext()).getUserName());
            receive = UserInfo.create(getContext()).getUserId();
            String tag = UserInfo.create(getContext()).getUserType();
            if ("2".equals(tag)) {//监理
                String userName = UserInfo.create(getContext()).getUserName();
                supervisorTV.setText(userName);
                supervisor = UserInfo.create(getContext()).getUserId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {

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
        acceptSW.setOnCheckedChangeListener(this);

        setDescET(descET, descNumTv);

        if ("1".equals(Constant.partWhetherIdentifying)) {
            usePlaceET.setHint("必填");
        } else {
            usePlaceET.setHint("选填");
        }
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

    private void setTitleRightmenu() {

        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dp40px, dp40px);
        ImageView close = new ImageView(getContext());
        close.setImageResource(R.mipmap.close_icon);
        close.setLayoutParams(layoutParams);
        close.setPadding(dp10px, 0, dp10px, 0);
        getBarLeftView().addView(close, 1);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
    }


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
                if (StringUtils.isEmpty(sectionId)) {
                    ToastUtils.myToast(getActivity(), "请先选择标段");
                    return;
                }
                SelectTRPOrAU.select(AddClysTask.this, nameInspectionId, sectionId, SelectTRPOrAU.MaterialsName);//材料名称
                break;
            case R.id.clTypeLL://材料类型
                if (StringUtils.isEmpty(sectionId)) {
                    ToastUtils.myToast(getActivity(), "请先选择标段");
                    return;
                }
                SelectTRPOrAU.select(AddClysTask.this, typeInspectionId, sectionId, nameInspectionId, SelectTRPOrAU.MaterialsType);//材料类型
                break;
            case R.id.clShopLL://材料供应商
                if (StringUtils.isEmpty(sectionId)) {
                    ToastUtils.myToast(getActivity(), "请先选择标段");
                    return;
                }
                SelectTRPOrAU.select(AddClysTask.this, supplierId, sectionId, SelectTRPOrAU.MaterialsShop);//材料供应商
                break;
            case R.id.receiveLL://接收人
                SelectTRPOrAU.select(AddClysTask.this, receive, SelectTRPOrAU.ReceicvePerson);//接收人
                break;
            case R.id.receivePartLL://接收单位
                if (StringUtils.isEmpty(sectionId)) {
                    ToastUtils.myToast(getActivity(), "请先选择标段");
                    return;
                }
                SelectTRPOrAU.select(AddClysTask.this, receiveUnit, sectionId, SelectTRPOrAU.receiveUnit);//接收单位
                break;
            case R.id.supervisorLL://监理
                SelectTRPOrAU.select(AddClysTask.this, supervisor, SelectTRPOrAU.Supervisor);//监理
                break;
            case R.id.buildAcceptLL://建设单位验收人
//                Constant.REN_TYPE = 4;
//                jumpActivityForResult(SelectAngle.class, Constant.Jian_She);
                SelectTRPOrAU.select(this, acceptance, SelectTRPOrAU.ConstructionDirector2);
//                SelectTRPOrAU.select(AddClysTask.this, buildAcceptTV.getText().toString(), SelectTRPOrAU.ConstructionDirector);//建设单位
                break;
            case R.id.copyLL://抄送人
//                Constant.REN_TYPE = 3;
//                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE);

                SelectTRPOrAU.select(this, cc, SelectTRPOrAU.COPY_PEOPLE);
                break;
            case R.id.commitBT:
                saveClysTask();
                break;
        }
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        buildAcceptLL.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        isneedAcceptance = isChecked ? 1 : 0;
        try {
            if (!isChecked) {
                buildAcceptTV.setText("");
                acceptance = "";
            }
        } catch (Exception e) {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        Bundle bundle;
        if (requestCode == -123310) {

        }
//        else if (requestCode == Constant.CHAO_SONG_REN_CODE) {//抄送人
//            ccName = data.getStringExtra("selectType");
//            cc = data.getStringExtra("selectId");
//            LogUtils.d(TAG, "cc==" + cc);
//            if (!StringUtils.isEmpty(ccName)) {
//                List<String> chaoSongName = ListUtils.stringToList(ccName);
//                List<String> chaoSongId = ListUtils.stringToList(cc);
//            }
//            copyTV.setText(ccName + "");
//            copyTV.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
//        }
        else if (requestCode == Constant.BIAO_DUAN_RESULT_CODE) {//标段
            sectionName = data.getStringExtra("content");
            sectionId = data.getStringExtra("id");
            bdTV.setText(sectionName);
        } else if (requestCode == SelectTRPOrAU.ConstructionDirector2) {//建设单位
            String fuyan = data.getBundleExtra("bundle").getString("name");
            acceptance = data.getBundleExtra("bundle").getString("id");
            buildAcceptTV.setText(fuyan + "");
        } else if (requestCode == SelectTRPOrAU.COPY_PEOPLE) {//抄送人
            ccName = data.getBundleExtra("bundle").getString("name");
            cc = data.getBundleExtra("bundle").getString("id");
            copyTV.setText(ccName + "");
            copyTV.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));

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

    private String supplierId;
    private String supervisor;
    private String receiveUnit;

    /**
     * 新增任务
     */
    private void saveClysTask() {


        if (StringUtils.isEmpty(nameInspectionId)) {
            ToastUtils.myToast(getActivity(), "请选择材料");
            return;
        }
        if (StringUtils.isEmpty(typeInspectionId)) {
            ToastUtils.myToast(getActivity(), "请选择材料类型");
            return;
        }
        if (StringUtils.isEmpty(supplierId)) {
            ToastUtils.myToast(getActivity(), "请选择供应商");
            return;
        }
        if ("1".equals(Constant.partWhetherIdentifying) && TextUtils.isEmpty(applypart)) {
            ToastUtils.myToast(getActivity(), "请填写使用部位");
            return;
        }
        if (StringUtils.isEmpty(receiveUnit)) {
            ToastUtils.myToast(getActivity(), "请选择接收单位");
            return;
        }
        if (StringUtils.isEmpty(supervisor)) {
            ToastUtils.myToast(getActivity(), "请选择监理");
            return;
        }

        if (isneedAcceptance == 1) {
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
        showLoadView("加载中...");
        HttpRequest.get(getContext())
                .url(ServerInterface.saveClysTask)
//                .params("id", "")//主键id
                .params("state", "1")//状态(1待申请进场，2待验收，3已验收，4待送检，5待上传报告，6待复验，7待退场，8已退场，9送检合格，10复验合格)
                .params("projectId", Constant.projectId)//项目id
                .params("sectionId", sectionId)//所属标段：标段id
//                .params("sectionName", sectionName)//
                .params("nameInspectionId", nameInspectionId)//材料名称：第二级名称id
                .params("nameInspection", nameInspection)//材料名称:第二级名称
//                .params("number", "")//第几批
                .params("typeInspectionId", typeInspectionId)//材料类型：第三级名称id
                .params("typeInspection", typeInspection)//材料类型：第三级名称
                .params("supplierId", supplierId)//材料供应商id
//                .params("supplierName", "")//
                .params("applypart", applypart.trim())//适用部位
                .params("supplement", supplement.trim())//补充说明
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
                .params("submit", userInfo.getUserId())//创建人id
//                .params("submitName", "")//
                .params("submitDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//创建时间
//                .params("approachDate", "")//进场时间
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            hideLoadView();
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                finish();
                                EventBus.getDefault().post("addTaskSucess");
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });

    }


}
