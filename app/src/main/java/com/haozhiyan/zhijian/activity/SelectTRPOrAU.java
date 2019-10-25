package com.haozhiyan.zhijian.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.AccountabilityUnitListAdapter;
import com.haozhiyan.zhijian.adapter.MaterialsNameAdapter;
import com.haozhiyan.zhijian.adapter.MaterialsNameDlgAdapter;
import com.haozhiyan.zhijian.adapter.SelectPeopleAdapter;
import com.haozhiyan.zhijian.adapter.SelectTheRectificationPeopleAdapter;
import com.haozhiyan.zhijian.adapter.SingleCheckAdapter;
import com.haozhiyan.zhijian.bean.CaiLiaoNameListBean;
import com.haozhiyan.zhijian.bean.CaiLiaoTitle;
import com.haozhiyan.zhijian.bean.CaiLiaoTypeName;
import com.haozhiyan.zhijian.bean.CaiLiaoTypeSelectBean;
import com.haozhiyan.zhijian.bean.SelectPerson;
import com.haozhiyan.zhijian.bean.SelectPersonTitle;
import com.haozhiyan.zhijian.bean.ZhengGaiPerson;
import com.haozhiyan.zhijian.bean.ZhengGaiTypeTitle;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.MyItemClickListener;
import com.haozhiyan.zhijian.listener.PersonItemClick2;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 整改人/责任单位
 */
public class SelectTRPOrAU extends BaseActivity2 implements PersonItemClick2, MyItemClickListener {
    private RecyclerView rcv;
    private EditText searchET;
    private SelectTheRectificationPeopleAdapter adapter;
    private SelectPeopleAdapter peopleAdapter;
    private Button commitBT;
    public static final int THE_RECTIFICATION_PEOPLE = 10001;//整改人
    public static final int THE_FUYAN_PEOPLE = 10002;//复验人单选
    public static final int THE_FUYAN_PEOPLE2 = 10003;//复验人多选
    public static final int FU_YAN_REN_CODE = 10006;//整改人
    public static final int ACCOUNTABILITY_UNIT = 10086;//责任单位
    public static final int contractor = 10025;//施工单位
    public static final int Supervisor = 10026;//监理
    public static final int inspector = 10027;//送检人
    public static final int ConstructionDirector = 11103;//建设单位负责人
    public static final int ConstructionDirector2 = 11105;//建设单位负责人 多选
    public static final int MaterialsName = 11114;//材料名称
    public static final int MaterialsType = 11204;//材料类型
    public static final int SingleMaterialsType = 11206;//单选材料类型
    public static final int MaterialsShop = 11215;//材料供应商
    public static final int ReceicvePerson = 11235;//接收人
    public static final int receiveUnit = 11245;//接收单位
    public static final int Brand = 11250;//选择品牌
    public static final int BrandSpecifi = 11251;//选择规格
    public static final int COPY_PEOPLE = 11226;//选择 抄送人

    private boolean isNotCheckShiGong = false;//是否不选择施工 默认选择

    private AccountabilityUnitListAdapter auAdapter;
    private SingleCheckAdapter checkAdapter;
    private MaterialsNameAdapter mnAdapter;

    private EditText selectNamesTV;
    private AlertDialog dlg;
    private CaiLiaoNameListBean clNameBean;
    private RelativeLayout searchRL;


    private List<String> selectIds;
    private List<String> selectNames = new ArrayList<>();

    private String selectID = "";
    private String sectionId;
    private String batchId;
    private String clName;
    private String unitOfQuantity;
    private String inspectionId;
    private boolean isBatchId;
    private int type;

    public static void select(Activity activity, String selectName, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void select(android.support.v4.app.Fragment activity, String selectID, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity.getContext(), SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectID);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void select(android.support.v4.app.Fragment activity, String selectID, boolean isNotCheckShiGong, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity.getContext(), SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectID);
        bundle.putInt("type", requestCode);
        bundle.putBoolean("isNotCheckShiGong", isNotCheckShiGong);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * @param activity
     * @param selectName
     * @param batchId
     * @param isBatchId
     * @param requestCode
     */
    public static void select(Activity activity, String selectName, String batchId, boolean isBatchId, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putString("batchId", batchId);
        bundle.putBoolean("isBatchId", isBatchId);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void select(Activity activity, String selectName, String sectionId, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putString("sectionId", sectionId);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void select(Activity activity, String selectName, String sectionId, String inspectionId, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putString("sectionId", sectionId);
        bundle.putString("inspectionId", inspectionId);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void select(android.support.v4.app.Fragment activity,
                              String selectName, String sectionId, String inspectionId, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity.getContext(), SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putString("sectionId", sectionId);
        bundle.putString("inspectionId", inspectionId);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }


    public static void select(android.support.v4.app.Fragment activity, String selectName, String sectionId, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity.getContext(), SelectTRPOrAU.class);
        Bundle bundle = new Bundle();
        bundle.putString("selectID", selectName);
        bundle.putString("sectionId", sectionId);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_select_the_rectification_people;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        selectID = getIntent().getExtras().getString("selectID", "");
        sectionId = getIntent().getExtras().getString("sectionId", "");
        batchId = getIntent().getExtras().getString("batchId", "");
        inspectionId = getIntent().getExtras().getString("inspectionId", "");
        isBatchId = getIntent().getExtras().getBoolean("isBatchId", false);
        type = getIntent().getExtras().getInt("type", 0);
        isNotCheckShiGong = getIntent().getExtras().getBoolean("isNotCheckShiGong", false);
        setAndroidNativeLightStatusBar(true);
        rcv = findViewById(R.id.rcv);
        searchET = findViewById(R.id.searchET);
        commitBT = findViewById(R.id.commitBT);
        selectNamesTV = findViewById(R.id.selectNamesTV);
        searchRL = findViewById(R.id.searchRL);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));


        if (selectID.contains(",")) {
            selectIds = Arrays.asList(selectID.replace("null", "").split(","));
        } else {
            selectIds = new ArrayList<>();
            if (!TextUtils.isEmpty(selectID))
                selectIds.add(selectID);
        }

        searchET.addTextChangedListener(watcher);

        if (type == THE_RECTIFICATION_PEOPLE) {
            setTitleText("整改人");
            listRoleUser(null);
        } else if (type == THE_FUYAN_PEOPLE) {
            setTitleText("复验人");
            listRoleUser(null);
        } else if (type == THE_FUYAN_PEOPLE2) {
            setTitleText("复验人");
            listRoleUserDoubleSelect(null);
            setTitleSelect();
        } else if (type == FU_YAN_REN_CODE) {
            setTitleText("复验人");
            listRoleUser(null);
        } else if (type == ACCOUNTABILITY_UNIT) {
            setTitleText("责任单位");
            if (isBatchId) {
                //selectContractorBatchId();
                selectContractor(null);
            } else {
                selectContractor(null);
            }
        } else if (type == contractor) {
            setTitleText("施工单位");
            listGXYJConstructionOrganization();
            searchRL.setVisibility(View.GONE);
        } else if (type == Supervisor) {
            setTitleText("监理");
            listRoleUser(null);
        } else if (type == inspector) {
            setTitleText("送检人");
            listRoleUser(null);
        } else if (type == ReceicvePerson) {
            setTitleText("接收人");
            listRoleUser(null);
        } else if (type == ConstructionDirector) {
            setTitleText("建设单位验收人");
            listRoleUser(null);
        } else if (type == ConstructionDirector2) {
            setTitleText("建设单位验收人");
            listRoleUserDoubleSelect(null);
            setTitleSelect();
        } else if (type == COPY_PEOPLE) {
            setTitleText("抄送人");
            listRoleUserDoubleSelect(null);
            setTitleSelect();
        } else if (type == MaterialsName) {
            setTitleText("材料名称");
            selectSCSL(null);
            setTitleMenu();
        } else if (type == MaterialsType) {
            setTitleText("材料类型");
            selectThreeInspection(null);
            setTitleMenu();
            commitBT.setVisibility(View.VISIBLE);
            commitBT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        List<MultiItemEntity> entities = adapter.getData();
                        StringBuffer nameBuffer = new StringBuffer();
                        StringBuffer idsBuffer = new StringBuffer();
                        for (int i = 0; i < entities.size(); i++) {
                            if (entities.get(i) instanceof CaiLiaoTypeSelectBean) {
                                CaiLiaoTypeSelectBean bean = (CaiLiaoTypeSelectBean) entities.get(i);
                                if (bean.isCheck) {
                                    if (nameBuffer.length() > 0) {
                                        nameBuffer.append(",");
                                        idsBuffer.append(",");
                                    }
                                    nameBuffer.append(bean.inspectionName);
                                    idsBuffer.append(bean.inspectionId + "");
                                }
                            }
                        }
                        if (!StringUtils.isEmpty(nameBuffer.toString()))
                            onItemClick(nameBuffer.toString(), idsBuffer.toString(), "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else if (type == MaterialsShop) {
            setTitleText("选择材料供应商");
            selectContractor("");
//            setTitleMenu();
        } else if (type == Brand) {
            setTitleText("品牌");
            selectParticularsClys("");
            setTitleMenu();
        } else if (type == BrandSpecifi) {
            setTitleText("选择规格");
            selectParticularsClys("");
            setTitleMenu();
        } else if (type == SingleMaterialsType) {
            setTitleText("材料类型");
            singleselectcailiao();
            setTitleMenu();
        } else if (type == receiveUnit) {
            setTitleText("接收单位");
            selectContractor("");
        }
    }

    @Override
    protected void initData() {

    }

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            LogUtils.d(TAG, s + "-");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            LogUtils.d(TAG, s + "--");

            if (type == THE_FUYAN_PEOPLE2
                    || type == COPY_PEOPLE
                    || type == ConstructionDirector2) {//复验人
                listRoleUserDoubleSelect(s.toString());
            } else if (type == MaterialsType) {//材料类型列表
                selectThreeInspection(s.toString());
            } else if (type == MaterialsName) {//材料名称
                selectSCSL(s.toString());
            } else if (type == Brand
                    || type == BrandSpecifi) {//品牌//选择规格
                selectParticularsClys(s.toString());
            } else if (type == ACCOUNTABILITY_UNIT
                    || type == receiveUnit
                    || type == MaterialsShop) {//责任单位//接收单位//选择材料供应商
                selectContractor(s.toString());
            } else if (type == THE_RECTIFICATION_PEOPLE
                    || type == ReceicvePerson
                    || type == ConstructionDirector
                    || type == inspector
                    || type == Supervisor
                    || type == FU_YAN_REN_CODE
                    || type == THE_FUYAN_PEOPLE) {
                listRoleUser(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            LogUtils.d(TAG, s + "---");
        }
    };

    /**
     * 材料名称标题栏
     */
    private void setTitleMenu() {
        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp45px = DensityUtil.dip2px(getContext(), 45);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, dp45px);
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(dp10px, 0, dp10px, 0);
        tv.setText("手动添加");
        tv.setTextColor(0xff232323);
        tv.setSingleLine(true);
        tv.setTextSize(15);
        getBarRightView().addView(tv);
        getBarRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (type == MaterialsShop) {
//
//                } else {
                showDlg();
//                }
            }
        });
    }

    private TextView titleRightTV;

    /**
     * 材料名称标题栏
     */
    private void setTitleSelect() {
        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp45px = DensityUtil.dip2px(getContext(), 45);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, dp45px);
        titleRightTV = new TextView(getContext());
        titleRightTV.setLayoutParams(layoutParams);
        titleRightTV.setGravity(Gravity.CENTER);
        titleRightTV.setPadding(dp10px, 0, dp10px, 0);
        titleRightTV.setText("确定");
        titleRightTV.setTextColor(0xff232323);
        titleRightTV.setSingleLine(true);
        titleRightTV.setTextSize(15);
        getBarRightView().addView(titleRightTV);
        getBarRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    List<String> selectIDs = peopleAdapter.getSelectIDs();
                    List<String> selectNames = peopleAdapter.getSelectNames();
                    StringBuffer names = new StringBuffer();
                    StringBuffer ids = new StringBuffer();
                    for (int i = 0; i < selectIDs.size(); i++) {
                        if (names.length() > 0) {
                            names.append(",");
                            ids.append(",");
                        }
                        names.append(selectNames.get(i));
                        ids.append(selectIDs.get(i));
                    }
                    if (names.length() > 0) {
                        onItemClick(names.toString(), ids.toString(), "");
                    } else {
                        onItemClick("", "", "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 添加材料名称 弹窗
     */
    private void showDlg() {

        dlg = new AlertDialog.Builder(getContext(), R.style.dialog).create();
        dlg.show();
        dlg.setCanceledOnTouchOutside(false);
        dlg.setCancelable(true);
        dlg.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.add_materials_name_dlg, null);

        //设置对话框尺寸
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        view.setMinimumWidth((int) (dm.widthPixels * 0.7));
        dlg.setContentView(view);


        RecyclerView titleRCV = view.findViewById(R.id.titleRCV);
        RecyclerView itemRCV = view.findViewById(R.id.itemRCV);


//        titleRCV.setLayoutManager(new StaggeredGridLayoutManager(getContext(),));

        view.findViewById(R.id.cancelBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.dismiss();
            }
        });
        view.findViewById(R.id.okBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == MaterialsName || type == MaterialsShop || type == SingleMaterialsType || type == MaterialsType) {
                    saveInspectionClys();//添加材料名称、材料类型
                } else if (type == BrandSpecifi || type == Brand) {
                    //添加材料品牌规格
                    saveParticularsClys();
                }

            }
        });

        EditText clNameET = view.findViewById(R.id.clNameET);


        if (type == BrandSpecifi || type == Brand) {
            titleRCV.setVisibility(View.GONE);
            itemRCV.setVisibility(View.GONE);
            view.findViewById(R.id.tipTV).setVisibility(View.GONE);
        }


        clNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                clName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        List<String> strings = null;
        try {
            strings = new ArrayList<>();
            if (clNameBean != null) {
                for (int i = 0; i < clNameBean.getList().size(); i++) {
                    strings.add(clNameBean.getList().get(i).getInspectionName());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        List<String> strings2 = new ArrayList<>();
        strings2.add("包");
        strings2.add("卷");
        strings2.add("匹");
        strings2.add("套");
        strings2.add("桶");
        strings2.add("支");
        strings2.add("只");
        strings2.add("组");
        strings2.add("根");
        strings2.add("箱");
        strings2.add("米");
        strings2.add("台");
        strings2.add("个");
        strings2.add("片");
        strings2.add("块");
        strings2.add("吨");
        strings2.add("樘");
        strings2.add("棵");
        strings2.add("株");
        strings2.add("公斤");
        strings2.add("平方米");
        strings2.add("立方米");
        strings2.add("其他");

        if (type == MaterialsName) {
            setDlgListAdapter(titleRCV, strings);
            setDlgListAdapter(itemRCV, strings2);
        } else if (type == MaterialsType) {
            titleRCV.setVisibility(View.GONE);
            setDlgListAdapter(itemRCV, strings2);
        } else if (type == SingleMaterialsType) {
            titleRCV.setVisibility(View.GONE);
            setDlgListAdapter(itemRCV, strings2);
        }
    }


    private void setDlgListAdapter(final RecyclerView recyclerView, final List<String> list) {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
        layoutManager.setFlexWrap(FlexWrap.WRAP); //设置是否换行
        layoutManager.setFlexDirection(FlexDirection.ROW); // 设置主轴排列方式
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
        final MaterialsNameDlgAdapter dlgAdapter = new MaterialsNameDlgAdapter(list);
        recyclerView.setAdapter(dlgAdapter);
        dlgAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dlgAdapter.setSelectItem(position);
                dlgAdapter.notifyDataSetChanged();
                if (recyclerView.getId() == R.id.titleRCV) {
                    try {
                        if (clNameBean != null) {
                            inspectionId = clNameBean.getList().get(position).getInspectionId() + "";
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (recyclerView.getId() == R.id.itemRCV) {
                    unitOfQuantity = list.get(position);
                }
            }
        });
    }

    /**
     * 材料验收-添加材料名称、材料类型
     */
    private void saveInspectionClys() {
        if (StringUtils.isEmpty(clName)) {
            ToastUtils.myToast(getActivity(), "请填写材料名称");
            return;
        }
        if (StringUtils.isEmpty(unitOfQuantity)) {
            ToastUtils.myToast(getActivity(), "请选择数量单位");
            return;
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.saveInspectionClys)
                .params("inspectionId", inspectionId)
                .params("inspectionName", clName)
                .params("unitOfQuantity", unitOfQuantity)
                .params("sectionId", sectionId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                if (dlg != null && dlg.isShowing()) {
                                    dlg.dismiss();
                                }
                                onItemClick(clName, object.optString("inspectionId"), "");
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
     * 材料验收-添加品牌、规格
     */
    private void saveParticularsClys() {
        if (StringUtils.isEmpty(clName)) {
            if (type == Brand) {
                ToastUtils.myToast(getActivity(), "请填写品牌名称");
            } else if (type == BrandSpecifi) {
                ToastUtils.myToast(getActivity(), "请填写规格名称");
            }
            return;
        }
        HttpRequest.get(getContext())
                .url(ServerInterface.saveParticularsClys)
                .params("inspectionId", inspectionId)//检查项id（添加品牌时：是材料名称（钢筋）的值；添加规格时：是材料类型（圆钢）的值）
                .params("specification", clName)//品牌、规格名称
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                if (dlg != null && dlg.isShowing()) {
                                    dlg.dismiss();
                                }
                                selectParticularsClys("");
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
     * 获取三个单位的人 单选
     */
    private void listRoleUser(final String name) {
        HttpRequest.get(getContext())
                .url(ServerInterface.listRoleUser)
                .params("pkId", Constant.projectId)
                .params("peopleuser", name)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                ArrayList<MultiItemEntity> list = new ArrayList<>();
                                List<Integer> expandItem = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    ZhengGaiTypeTitle lv1 = new ZhengGaiTypeTitle();
                                    lv1.roleId = object.optInt("roleId");
                                    lv1.roleName = object.optString("roleName");
                                    if (isNotCheckShiGong && !TextUtils.isEmpty(lv1.roleName) && lv1.roleName.contains("施工")) {
                                        continue;
                                    }
                                    JSONArray childIns = object.optJSONArray("childIns");
                                    for (int j = 0; j < childIns.length(); j++) {
                                        ZhengGaiPerson person = new ZhengGaiPerson();
                                        JSONObject object2 = childIns.optJSONObject(j);
                                        person.userId = object2.optInt("userId");
                                        person.peopleuser = object2.optString("peopleuser");
                                        person.mobile = object2.optString("mobile");
                                        person.roleName = object2.optString("roleName");
                                        lv1.addSubItem(person);
                                        try {
                                            if (!TextUtils.isEmpty(name)
                                                    && !TextUtils.isEmpty(person.peopleuser)
                                                    && person.peopleuser.contains(name))
                                                if (!expandItem.contains(i)) {
                                                    expandItem.add(i);
                                                }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    list.add(lv1);
                                }
                                adapter = new SelectTheRectificationPeopleAdapter(list, getContext(), selectID);
                                adapter.setRcv(rcv);
                                rcv.setAdapter(adapter);
                                adapter.setItemClickListener(SelectTRPOrAU.this);
                                if (expandItem.size() > 0) {
                                    try {
                                        for (int i = expandItem.size(); i >= 1; i--) {
                                            adapter.expand(expandItem.get(i - 1), false);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            List<MultiItemEntity> data = adapter.getData();
                                            for (int i = 0; i < data.size(); i++) {
                                                MultiItemEntity entity = data.get(i);
                                                if (entity instanceof IExpandable) {
                                                    ZhengGaiTypeTitle zhengGaiTypeTitle = (ZhengGaiTypeTitle) entity;
                                                    List<ZhengGaiPerson> personList = zhengGaiTypeTitle.getSubItems();
                                                    try {
                                                        for (int j = 0; j < personList.size(); j++) {
                                                            ZhengGaiPerson person = personList.get(j);
                                                            if (String.valueOf(person.userId).equals(selectID)) {
                                                                adapter.expand(i);
                                                                return;
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        }
                                    }).start();

                                }


                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 获取两个个单位的人 多选
     */
    private void listRoleUserDoubleSelect(final String name) {
        HttpRequest.get(getContext())
                .url(ServerInterface.listRoleUser)
                .params("pkId", Constant.projectId)
                .params("peopleuser", name)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                ArrayList<MultiItemEntity> list = new ArrayList<>();
                                List<Integer> expandItem = new ArrayList<>();
                                StringBuffer names = new StringBuffer();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    SelectPersonTitle lv1 = new SelectPersonTitle();
                                    lv1.roleId = object.optInt("roleId");
                                    lv1.roleName = object.optString("roleName");
                                    if (isNotCheckShiGong && !TextUtils.isEmpty(lv1.roleName) && lv1.roleName.contains("施工")) {
                                        continue;
                                    }
                                    JSONArray childIns = object.optJSONArray("childIns");
                                    for (int j = 0; j < childIns.length(); j++) {
                                        SelectPerson person = new SelectPerson();
                                        JSONObject object2 = childIns.optJSONObject(j);
                                        person.userId = object2.optInt("userId");
                                        person.peopleuser = object2.optString("peopleuser");
                                        person.mobile = object2.optString("mobile");
                                        person.roleName = object2.optString("roleName");
                                        lv1.addSubItem(person);
                                        if (selectIds.contains(person.userId + "")) {
                                            if (names.length() > 0) {
                                                names.append("、");
                                            }
                                            person.isCheck=true;
                                            names.append(person.peopleuser);
                                            if (!selectNames.contains(person.peopleuser))
                                                selectNames.add(person.peopleuser);
                                            if (!expandItem.contains(i)) {
                                                expandItem.add(i);
                                            }
                                        }
                                    }
                                    list.add(lv1);
                                }
                                if (TextUtils.isEmpty(name) || name.length() <= 0) {
                                    if (names.length() > 0) {
                                        titleRightTV.setText("确定(" + selectNames.size() + ")");
                                        selectNamesTV.setVisibility(View.VISIBLE);
                                        selectNamesTV.setText("已选 :  ");
                                        selectNamesTV.append(names.toString());
                                    } else {
                                        selectNamesTV.setVisibility(View.GONE);
                                    }
                                }

                                peopleAdapter = new SelectPeopleAdapter(list, getActivity(), selectIds, selectNames);
                                peopleAdapter.setItemClickListener(SelectTRPOrAU.this);
                                rcv.setAdapter(peopleAdapter);
                                if (!TextUtils.isEmpty(name) && name.length() > 0) {
                                    peopleAdapter.expandAll();
                                } else if (expandItem.size() > 0) {
                                    for (int i = expandItem.size(); i >= 1; i--) {
                                        peopleAdapter.expand(expandItem.get(i - 1));
                                    }
                                }

                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 获取责任单位
     */
    private void selectContractor(String name) {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectContractor)
                .params("sectionId", sectionId)
                .params("name", name)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("data");
                                final List<String> strings = new ArrayList<>();
                                final List<String> contractorManageId = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    strings.add(object.optString("contractorForShort"));
                                    contractorManageId.add(object.optString("contractorManageId"));
                                }
                                auAdapter = new AccountabilityUnitListAdapter(strings, contractorManageId, selectID);
                                rcv.setAdapter(auAdapter);
                                auAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        SelectTRPOrAU.this.onItemClick(strings.get(position),
                                                contractorManageId.get(position),
                                                "");
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
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }


    /**
     * 获取责任单位 2
     */
    private void selectContractorBatchId() {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectContractorBatchId)
                .params("batchId", batchId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("data");
                                final List<String> strings = new ArrayList<>();
                                final List<String> contractorManageId = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    strings.add(object.optString("contractorForShort"));
                                    contractorManageId.add(object.optString("contractorManageId"));
                                }
                                auAdapter = new AccountabilityUnitListAdapter(strings, contractorManageId, selectID);
                                rcv.setAdapter(auAdapter);
                                auAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("name", strings.get(position));
                                        bundle.putString("id", contractorManageId.get(position));
                                        intent.putExtra("bundle", bundle);
                                        setResult(ACCOUNTABILITY_UNIT, intent);
                                        finish();
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
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }


    /**
     * 获取监理 /送检人
     */
    private void listGXYJSupervisor() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listGXYJSupervisor)
                .params("pkId", Constant.projectId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                ArrayList<MultiItemEntity> list = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    ZhengGaiTypeTitle lv1 = new ZhengGaiTypeTitle();
                                    lv1.roleId = object.optInt("roleId");
                                    lv1.roleName = object.optString("roleName");
                                    JSONArray childIns = object.optJSONArray("childIns");
                                    for (int j = 0; j < childIns.length(); j++) {
                                        ZhengGaiPerson person = new ZhengGaiPerson();
                                        JSONObject object2 = childIns.optJSONObject(j);
                                        person.userId = object2.optInt("userId");
                                        person.peopleuser = object2.optString("peopleuser");
                                        person.mobile = object2.optString("mobile");
                                        person.roleName = object2.optString("roleName");
                                        lv1.addSubItem(person);
                                    }
                                    list.add(lv1);
                                }

                                adapter = new SelectTheRectificationPeopleAdapter(list, getContext(), selectID);
                                adapter.setRcv(rcv);
                                rcv.setAdapter(adapter);
                                adapter.setItemClickListener(SelectTRPOrAU.this);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<MultiItemEntity> data = adapter.getData();
                                        for (int i = 0; i < data.size(); i++) {
                                            MultiItemEntity entity = data.get(i);
                                            if (entity instanceof IExpandable) {
                                                ZhengGaiTypeTitle zhengGaiTypeTitle = (ZhengGaiTypeTitle) entity;
                                                List<ZhengGaiPerson> personList = zhengGaiTypeTitle.getSubItems();
                                                try {
                                                    for (int j = 0; j < personList.size(); j++) {
                                                        ZhengGaiPerson person = personList.get(j);
                                                        try {
                                                            if (String.valueOf(person.userId).equals(selectID)) {
                                                                adapter.expand(i);
                                                                return;
                                                            }
                                                        } catch (Exception e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }).start();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 获取建设单位负责人
     */
    private void listConstructionDirector() {

        HttpRequest.get(getContext())
                .url(ServerInterface.listConstructionDirector)
                .params("pkId", Constant.projectId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                ArrayList<MultiItemEntity> list = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    ZhengGaiTypeTitle lv1 = new ZhengGaiTypeTitle();
                                    lv1.roleId = object.optInt("roleId");
                                    lv1.roleName = object.optString("roleName");
                                    JSONArray childIns = object.optJSONArray("childIns");
                                    for (int j = 0; j < childIns.length(); j++) {
                                        ZhengGaiPerson person = new ZhengGaiPerson();
                                        JSONObject object2 = childIns.optJSONObject(j);
                                        person.userId = object2.optInt("userId");
                                        person.peopleuser = object2.optString("peopleuser");
                                        person.mobile = object2.optString("mobile");
                                        person.roleName = object2.optString("roleName");
                                        lv1.addSubItem(person);
                                    }
                                    list.add(lv1);
                                }

                                adapter = new SelectTheRectificationPeopleAdapter(list, getContext(), selectID);
                                adapter.setRcv(rcv);
                                rcv.setAdapter(adapter);
                                adapter.setItemClickListener(SelectTRPOrAU.this);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<MultiItemEntity> data = adapter.getData();
                                        for (int i = 0; i < data.size(); i++) {
                                            MultiItemEntity entity = data.get(i);
                                            if (entity instanceof IExpandable) {
                                                ZhengGaiTypeTitle zhengGaiTypeTitle = (ZhengGaiTypeTitle) entity;
                                                List<ZhengGaiPerson> personList = zhengGaiTypeTitle.getSubItems();
                                                try {
                                                    if (ListUtils.listEmpty(personList)) {
                                                        for (int j = 0; j < personList.size(); j++) {
                                                            ZhengGaiPerson person = personList.get(j);
                                                            try {
                                                                if (String.valueOf(person.userId).equals(selectID)) {
                                                                    adapter.expand(i);
                                                                    return;
                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }).start();


                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 获取材料 材料验收-（材料名称）查询材料验收检查项第一级和第二级
     */
    private void selectSCSL(String name) {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectCLYS)
                .params("sectionId", sectionId)
                .params("type", "CLYS")
                .params("name", name)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            clNameBean = new Gson().fromJson(result.toString(), CaiLiaoNameListBean.class);
                            if (clNameBean.getCode() == 0) {
                                ArrayList<MultiItemEntity> list = new ArrayList<>();
                                for (int i = 0; i < clNameBean.getList().size(); i++) {
                                    CaiLiaoTitle lv1 = new CaiLiaoTitle();
                                    lv1.sectionId = clNameBean.getList().get(i).getSectionId();
                                    lv1.inspectionId = clNameBean.getList().get(i).getInspectionId();
                                    lv1.inspectionName = clNameBean.getList().get(i).getInspectionName();
                                    lv1.inspectionParentId = clNameBean.getList().get(i).getInspectionParentId();
                                    for (int j = 0; j < clNameBean.getList().get(i).getChild().size(); j++) {
                                        CaiLiaoTypeName person = new CaiLiaoTypeName();
                                        person.inspectionId = clNameBean.getList().get(i).getChild().get(j).getInspectionId();
                                        person.inspectionName = clNameBean.getList().get(i).getChild().get(j).getInspectionName();
                                        person.inspectionParentId = clNameBean.getList().get(i).getChild().get(j).getInspectionParentId();
                                        person.partsDivision = clNameBean.getList().get(i).getChild().get(j).getPartsDivision();
                                        lv1.addSubItem(person);
                                    }
                                    list.add(lv1);
                                }

                                mnAdapter = new MaterialsNameAdapter(list, getContext(), selectID);
                                mnAdapter.setRcv(rcv);
                                rcv.setAdapter(mnAdapter);
                                mnAdapter.setItemClickListener(SelectTRPOrAU.this);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<MultiItemEntity> data = mnAdapter.getData();
                                        for (int i = 0; i < data.size(); i++) {
                                            MultiItemEntity entity = data.get(i);
                                            if (entity instanceof IExpandable) {
                                                CaiLiaoTitle zhengGaiTypeTitle = (CaiLiaoTitle) entity;
                                                List<CaiLiaoTypeName> personList = zhengGaiTypeTitle.getSubItems();
                                                try {
                                                    if (ListUtils.listEmpty(personList)) {
                                                        for (int j = 0; j < personList.size(); j++) {
                                                            CaiLiaoTypeName person = personList.get(j);
                                                            try {
                                                                if (String.valueOf(person.inspectionId).equals(selectID)) {
                                                                    mnAdapter.expand(i, false);
                                                                    return;
                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }).start();


                            } else {
                                ToastUtils.myToast(getActivity(), clNameBean.getMsg());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 材料验收-（材料类型）根据标段id、检查项类型、检查项父id查询子集(第三级检查项list)
     */
    private void selectThreeInspection(String name) {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectThreeInspection)
                .params("sectionId", sectionId)
                .params("type", "CLYS")
                .params("name", name)
                .params("inspectionParentId", inspectionId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("inspectionList");
                                ArrayList<MultiItemEntity> list = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    CaiLiaoTypeSelectBean lv1 = new CaiLiaoTypeSelectBean();
                                    lv1.sectionId = object.optInt("sectionId");
                                    lv1.inspectionId = object.optLong("inspectionId");
                                    lv1.inspectionParentId = object.optString("inspectionParentId");
                                    lv1.inspectionName = object.optString("inspectionName");
                                    try {
                                        if (selectID.equals(String.valueOf(lv1.inspectionId))) {
                                            lv1.isCheck = true;
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    list.add(lv1);
                                }
                                adapter = new SelectTheRectificationPeopleAdapter(list, getContext(), selectID);
                                adapter.setRcv(rcv);
                                rcv.setAdapter(adapter);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        List<MultiItemEntity> data = adapter.getData();
                                        for (int i = 0; i < data.size(); i++) {
                                            MultiItemEntity entity = data.get(i);
                                            if (entity instanceof IExpandable) {
                                                ZhengGaiTypeTitle zhengGaiTypeTitle = (ZhengGaiTypeTitle) entity;
                                                List<ZhengGaiPerson> personList = zhengGaiTypeTitle.getSubItems();
                                                try {
                                                    if (ListUtils.listEmpty(personList)) {
                                                        for (int j = 0; j < personList.size(); j++) {
                                                            ZhengGaiPerson person = personList.get(j);
                                                            try {
                                                                if (String.valueOf(person.userId).equals(selectID)) {
                                                                    adapter.expand(i);
                                                                    return;
                                                                }
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }).start();


                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 获取施工单位
     */
    private void listGXYJConstructionOrganization() {
        HttpRequest.get(getContext())
                .url(ServerInterface.listGXYJConstructionOrganization)
                .params("sectionId", sectionId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                final List<String> strings = new ArrayList<>();
                                final List<String> contractorManageId = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    strings.add(object.optString("contractorForShort"));
                                    contractorManageId.add(object.optString("contractorManageId"));
                                }
                                for (int i = 0; i < strings.size(); i++) {
                                    System.out.println("是多少哈哈===" + strings.get(i));
                                }
                                auAdapter = new AccountabilityUnitListAdapter(strings, contractorManageId, selectID);
                                rcv.setAdapter(auAdapter);
                                auAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent();
                                        Bundle bundle = new Bundle();
                                        bundle.putString("name", strings.get(position));
                                        bundle.putString("id", contractorManageId.get(position));
                                        intent.putExtra("bundle", bundle);
                                        setResult(contractor, intent);
                                        finish();
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
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 材料验收-根据检查项id查询品牌、规格
     */
    private void selectParticularsClys(String name) {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectParticularsClys)
                .params("inspectionId", inspectionId)
                .params("name", name)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            final JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONObject("particularsClys").optJSONArray("specificationList");
                                final List<String> strings = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    String names = array.optString(i);
                                    strings.add(names);
                                }
                                checkAdapter = new SingleCheckAdapter(strings, strings, selectID);
                                rcv.setAdapter(checkAdapter);
                                checkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        try {
                                            if (!selectID.contains(strings.get(position))) {
                                                SelectTRPOrAU.this.onItemClick(strings.get(position),
                                                        jsonObject.optJSONObject("particularsClys").optString("unitOfQuantity"),
                                                        ""
                                                );
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
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    /**
     * 材料验收-单选材料类型
     */
    private void singleselectcailiao() {
        HttpRequest.get(getContext())
                .url(ServerInterface.selectThreeInspection)
                .params("sectionId", sectionId)
                .params("type", "CLYS")
                .params("inspectionParentId", inspectionId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("inspectionList");
                                final List<String> strings = new ArrayList<>();
                                final List<String> ids = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    strings.add(object.optString("inspectionName"));
                                    ids.add(object.optString("inspectionId"));
                                }
                                checkAdapter = new SingleCheckAdapter(strings, ids, selectID);
                                rcv.setAdapter(checkAdapter);
                                checkAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        try {
                                            if (!selectID.equals(ids.get(position))) {
                                                SelectTRPOrAU.this.onItemClick(strings.get(position), ids.get(position), "");
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
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    @Override
    public void onItemClick(String name, String id, String tel) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("name", name);
        bundle.putString("id", id);
        bundle.putString("tel", tel);
        intent.putExtra("bundle", bundle);
        setResult(type, intent);
        finish();
    }

    @Override
    public void onItemClick(String data, int position) {
        try {
            List<String> selectNames = peopleAdapter.getSelectNames();
            titleRightTV.setText("确定(" + selectNames.size() + ")");
            this.selectNames = selectNames;
            selectIds = peopleAdapter.getSelectIDs();
            StringBuffer names = new StringBuffer();
            for (int i = 0; i < selectNames.size(); i++) {
                if (names.length() > 0) {
                    names.append("、");
                }
                names.append(selectNames.get(i));
            }
            if (names.length() > 0) {
                selectNamesTV.setVisibility(View.VISIBLE);
                selectNamesTV.setText("已选 :  ");
                selectNamesTV.append(names.toString());
            } else {
                selectNamesTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
