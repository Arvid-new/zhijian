package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.gxyj.GxYjPlaceSelect;
import com.haozhiyan.zhijian.activity.gxyj.ProcessOver;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.ServerProblemAdapter;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.bean.NewZhengGaiListBean;
import com.haozhiyan.zhijian.bean.XCJC_TroubleBean;
import com.haozhiyan.zhijian.bean.XcjcSeverityProblem;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.AppUtils;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.SPUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.view.MyRecycleView;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//问题详情
public class XCJCTroubleEdit extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.et_instruct_content)
    EditText etInstructContent;
    @ViewInject(R.id.id_editor_detail_font_count)
    TextView editorFontCount;
    @ViewInject(R.id.noteTroubleRcv)
    RecyclerView noteTroubleRcv;
    @ViewInject(R.id.tv_buWei)
    TextView tvBuWei;//部位
    @ViewInject(R.id.tv_jianChaX)
    TextView tvJianChaX;//检查人
    @ViewInject(R.id.tv_desc)
    TextView tvDesc;//描述
    @ViewInject(R.id.ll_desc)
    LinearLayout llDesc;
    //严重程度
//    @ViewInject(R.id.tv_yiBan)
//    TextView tvYiBan;//一般
//    @ViewInject(R.id.tv_importance)
//    TextView tvImportance;//重要
//    @ViewInject(R.id.tv_jinJi)
//    TextView tvJinJi;//紧急
    @ViewInject(R.id.serverProblemList)
    MyRecycleView serverProblemList;
    //整改时间
    @ViewInject(R.id.tv_reduce)
    TextView tv_reduce;
    @ViewInject(R.id.tv_add)
    TextView tv_add;
    @ViewInject(R.id.tv_num2)
    EditText tv_num;
    @ViewInject(R.id.tv_zhengGaiRen)
    TextView tvZhengGaiRen;//整改人
    @ViewInject(R.id.tv_zeRenDanWei)
    TextView tvZeRenDanWei;//责任人
    @ViewInject(R.id.tv_fuYan)
    TextView tvFuYan;//复验人
    @ViewInject(R.id.tv_chaoSong)
    TextView tvchaoSong;//抄送人
    @ViewInject(R.id.tv_safe_draft)
    TextView tvSafeDraft;//保存草稿
    @ViewInject(R.id.tv_safe_commit)
    TextView tv_safe_commit;//保存提交
    @ViewInject(R.id.tvHuImg)
    TextView tvHuImg;
    @ViewInject(R.id.ll_jcx)
    LinearLayout llJcx;
    @ViewInject(R.id.tv_jcxName)
    TextView tvJcxName;
    @ViewInject(R.id.ll_jcx_select)
    LinearLayout ll_jcx_select;
    private int num = 0, troubleChengDu = 1, draftIndex = 0;
    //private PictureOrVideoListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> photoPaths = new ArrayList<>();
    private List<String> videoPaths = new ArrayList<>();
    private String filePath = "", batchId = "", tower = "", unit = "", floor = "", room = "", inspectionIds = "", inspectionId = "", sectionId,
            particularsId = "", particularsName = "", rectify = "", dutyUnit = "", review = "", cc = "", orderOfSeverity = "", pieceType = "xcJc";
    private String sysTime = AppUtils.getSystemDate("yyyy-MM-dd") + " " + AppUtils.getSystemTime("HH:mm:ss");
    private ACache aCache;
    private List<String> filePaths = new ArrayList<>();
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x123:
                    Intent intent = new Intent(act, XianChangJianChactivity.class);
                    setResult(Constant.NOTE_PROBLEM_RESULT_CODE, intent);
                    finish();
                    break;
                case 223:
                    saveDraft();
                    break;
                case 224:
                    setGXYjDraft();
                    break;
                default:
                    break;
            }
        }
    };
    private String towerName = "", unitName = "", roomName = "", floorName = "", housemap = "", keyId = "", sectionName = "", detailsName = "", inspectionName = "";
    private String towerType = "", rectifyName = "", dutyName = "", reviewName = "", gxyjReview = "", ccName = "";
    //private List<XCJC_TroubleBean> draftList = new ArrayList<>();
    private ServerProblemAdapter serverProblemAdapter;
    private List<XcjcSeverityProblem> severityProblems;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjctrouble_edit;
    }

    @Override
    protected void initView() {
        tv_title.setText("编辑问题");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        selectList.clear();
        photoPaths.clear();
        videoPaths.clear();
        initPhoto();
    }

    @Override
    protected void initListener() {
        etInstructContent.addTextChangedListener(textWatcher);
        tv_num.addTextChangedListener(textWatcher);
        serverProblemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                serverProblemAdapter.selected(position);
                if (listEmpty(severityProblems)) {
                    troubleChengDu = severityProblems.get(position).severityId;
                    orderOfSeverity = severityProblems.get(position).severityName;
                    LogUtils.i("troubleChengDu==", troubleChengDu + "");
                    LogUtils.i("orderOfSeverity==", orderOfSeverity);
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        pieceType = getIntent().getBundleExtra("data").getString("pieceType");
        if ("xcJc".equals(pieceType)) {
            aCache = ACache.get(this, "XCJC_trouble");
            batchId = getIntent().getBundleExtra("data").getString("batchId");
            Bundle bundle = getIntent().getBundleExtra("data");
            XCJC_TroubleBean bean = bundle == null ? null : (XCJC_TroubleBean) bundle.getSerializable("troubleBean");
            draftIndex = bundle.getInt("position");
            sectionId = bundle.getString("sectionId");
            towerName = bundle.getString("towerName");
            unitName = bundle.getString("unitName");
            floorName = bundle.getString("floorName");
            roomName = bundle.getString("roomName");
            tvBuWei.setText(getIntent().getStringExtra("local"));
            inspectionId = getIntent().getStringExtra("inspectionId");
            severityProblems = (List<XcjcSeverityProblem>) getIntent().getSerializableExtra("severityProblems");
            if (listEmpty(severityProblems)) {
                serverProblemAdapter.setNewData(severityProblems);
                serverProblemAdapter.selected(0);
            } else {
                serverProblemAdapter.setNewData(new XcjcSeverityProblem().getInitProblem());
                serverProblemAdapter.selected(0);
            }
            tvSafeDraft.setVisibility(View.VISIBLE);
            ll_jcx_select.setVisibility(View.VISIBLE);
            llJcx.setVisibility(View.GONE);
            setEditData(bean);
        } else if ("gxYj".equals(pieceType)) {
            aCache = ACache.get(this, "GXYJ_trouble");
            Bundle bundle = getIntent().getBundleExtra("data");
            NewZhengGaiListBean.ListBean.ListAbarbeitungBean bean = bundle == null ? null : (NewZhengGaiListBean.ListBean.ListAbarbeitungBean) bundle.getSerializable("troubleBean");
            draftIndex = bundle.getInt("position");
            sectionId = bundle.getString("sectionId");
            keyId = getIntent().getStringExtra("keyId");
            if (StringUtils.isEmpty(keyId)) {
                keyId = SPUtils.getString(this, "keyId", "", "gxYjKeyId");
            }
            sectionName = getIntent().getStringExtra("sectionName");
            detailsName = getIntent().getStringExtra("detailsName");
            inspectionName = getIntent().getStringExtra("inspectionName");
            tower = getIntent().getStringExtra("towerId");
            unit = getIntent().getStringExtra("unitId");
            floor = getIntent().getStringExtra("floorId");
            room = getIntent().getStringExtra("roomId");
            towerType = getIntent().getStringExtra("towerType");
            towerName = getIntent().getStringExtra("towerName");
            unitName = getIntent().getStringExtra("unitName");
            floorName = bundle.getString("floorName");
            roomName = bundle.getString("roomName");
            inspectionId = getIntent().getStringExtra("inspectionId");
            severityProblems = (List<XcjcSeverityProblem>) getIntent().getSerializableExtra("severityProblems");
            if (listEmpty(severityProblems)) {
                serverProblemAdapter.setNewData(severityProblems);
                serverProblemAdapter.selected(0);
            } else {
                serverProblemAdapter.setNewData(new XcjcSeverityProblem().getInitProblem());
                serverProblemAdapter.selected(0);
            }
            tvJcxName.setText(detailsName);
            //tvSafeDraft.setVisibility(View.GONE);
            ll_jcx_select.setVisibility(View.GONE);
            llJcx.setVisibility(View.VISIBLE);
            setGxyJEditData(bean);
        }
    }

    @OnClick({R.id.rl_back, R.id.tv_buWei, R.id.tv_jianChaX, R.id.tv_add, R.id.tv_reduce,
            R.id.tv_zhengGaiRen, R.id.tv_zeRenDanWei, R.id.tv_fuYan, R.id.tv_chaoSong, R.id.tv_safe_draft, R.id.tv_safe_commit, R.id.ll_desc})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_buWei:
                try {
                    if ("gxYj".equals(pieceType)) {
                        Bundle bundle = new Bundle();
                        bundle.putString("towerId", tower);
                        bundle.putString("unitId", unit);
                        bundle.putString("floorId", floor);
                        bundle.putString("roomId", room);
                        bundle.putString("towerType", towerType);
                        bundle.putString("towerName", towerName);
                        bundle.putString("unitName", unitName);
                        bundle.putString("floorName", floorName);
                        bundle.putString("roomName", roomName);
                        bundle.putString("keyId", keyId);
                        jumpActivityForResult(GxYjPlaceSelect.class, Constant.PLACE_CODE, bundle);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString("towerName", towerName);
                        bundle.putString("unitName", unitName);
                        bundle.putString("floorName", floorName);
                        bundle.putString("roomName", roomName);
                        bundle.putString("housemap", housemap);
                        jumpActivityForResult(NewPlaceSelect.class, Constant.PLACE_CODE, bundle);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_jianChaX:
                jumpActivityForResult(JianChaItemSelectActivity.class, Constant.JIAN_CHA_XIANG_CODE);
                break;
//            case R.id.tv_yiBan:
//                troubleChengDu = 1;
//                UiUtils.setBtnStatus(1, tvYiBan, tvImportance, tvJinJi);
//                break;
//            case R.id.tv_importance:
//                troubleChengDu = 2;
//                UiUtils.setBtnStatus(2, tvYiBan, tvImportance, tvJinJi);
//                break;
//            case R.id.tv_jinJi:
//                troubleChengDu = 3;
//                UiUtils.setBtnStatus(3, tvYiBan, tvImportance, tvJinJi);
//                break;
            case R.id.tv_add://添加整改期限
                num++;
                if (num >= 999) {
                    num = 999;
                    tv_num.setText(num + "");
                } else {
                    tv_num.setText(num + "");
                }
                break;
            case R.id.tv_reduce://减少整改期限
                if (num > 0) {
                    num--;
                    tv_num.setText(num + "");
                }
                break;
            case R.id.tv_zhengGaiRen:
                SelectTRPOrAU.select(this, UiUtils.getContent(tvZhengGaiRen), SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.tv_zeRenDanWei:
                SelectTRPOrAU.select(this, UiUtils.getContent(tvZeRenDanWei), batchId, true, SelectTRPOrAU.ACCOUNTABILITY_UNIT);
                break;
            case R.id.tv_fuYan:
                Constant.REN_TYPE = 4;
                jumpActivityForResult(SelectAngle.class, Constant.FU_YAN_REN_CODE);
                break;
            case R.id.tv_chaoSong:
                Constant.REN_TYPE = 3;
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE);
                break;
            case R.id.tv_safe_draft://草稿
                if (pieceType.equals("gxYj")) {
                    myHandler.sendEmptyMessage(224);
                } else if (pieceType.equals("xcJc")) {
                    myHandler.sendEmptyMessage(223);
                }
                break;
            case R.id.tv_safe_commit://提交
                if (StringUtils.isEmpty(UiUtils.getContent(tvBuWei))) {
                    ToastUtils.myToast(act, "请选择楼栋部位");
                } else {
                    if (StringUtils.isEmpty(UiUtils.getContent(tvJianChaX))) {
                        ToastUtils.myToast(act, "请选择检查项");
                    } else {
                        if (isNetWork) {
                            if (selectList.size() > 0) {
                                uploadFile(ListUtils.getFileList(selectList));
                            } else {
                                if ("gxYj".equals(pieceType)) {
                                    addGxYjProblem();
                                } else if ("xcJc".equals(pieceType)) {
                                    addTrouble();
                                }
                            }
                        } else {
                            ToastUtils.myToast(act, "没有网络，数据无法同步");
                        }
                    }
                }
                break;
            case R.id.ll_desc:
                Bundle bundle2 = new Bundle();
                bundle2.putString("inspectionId", inspectionId);
                bundle2.putString("descName", UiUtils.getContent(tvDesc));
                jumpActivityForResult(ProblemDescList.class, Constant.DESC_LIST_CODE, bundle2);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event != null) {
            if (event instanceof ItemValues) {
                ItemValues itemValues = (ItemValues) event;
                housemap = itemValues.houseMap;
                //pieceType = itemValues.pieceType;
                if (StringUtils.isEmpty(housemap)) {
                    tvHuImg.setVisibility(View.GONE);
                } else {
                    tvHuImg.setVisibility(View.VISIBLE);
                }
                tvBuWei.setText(itemValues.towerFloorUnitRoom);
                tower = itemValues.towerId;
                unit = itemValues.unitId;
                floor = itemValues.floorId;
                room = itemValues.roomId;
                roomName = itemValues.roomNum;
                towerName = itemValues.tower;
                unitName = itemValues.unit;
                floorName = itemValues.floor;
                roomName = itemValues.roomNum;
            }
        }
    }

    //保存草稿修改 等选择列表接口对接完，还要保存对应项id
    private void saveDraft() {
        try {
            ToastUtils.longToast(act, R.string.save_doing);
            JSONArray data = aCache.getAsJSONArray("xcJc_trouble");
            if (!arrayEmpty(data)) {
                data = new JSONArray();
            }
            XCJC_TroubleBean bean = new XCJC_TroubleBean();
            bean.images = photoPaths;
            bean.videos = videoPaths;
            bean.buWei = UiUtils.getContent(tvBuWei);
            bean.JianChaR = UiUtils.getContent(tvJianChaX);
            bean.desc = UiUtils.getContent(tvDesc);
            bean.instructContent = UiUtils.getContent(etInstructContent);
            bean.troubleChengDu = troubleChengDu;
            bean.zgQxTimes = UiUtils.getContent(tv_num);
            bean.zhengGaiRen = UiUtils.getContent(tvZhengGaiRen);
            bean.zeRenDanWei = UiUtils.getContent(tvZeRenDanWei);
            bean.fuYanRen = UiUtils.getContent(tvFuYan);
            bean.chaoSongRen = UiUtils.getContent(tvchaoSong);
            bean.createTime = AppUtils.getSystemDate("MM-dd\t") + AppUtils.getSystemTime("HH:mm");
            data.put(draftIndex, bean.setData());
            LogUtils.i("draftIndex==", draftIndex + "");
            LogUtils.i("draft_json==", data.toString());
            aCache.put("xcJc_trouble", data);
            ToastUtils.myToast(act, R.string.save_success);
            myHandler.sendEmptyMessageAtTime(0x123, 2000);
        } catch (Exception e) {
            ToastUtils.myToast(act, R.string.save_fail);
            e.printStackTrace();
        }
    }

    //工序移交草稿
    private void setGXYjDraft() {
        try {
            Toast.makeText(act, R.string.save_doing, Toast.LENGTH_SHORT).show();
            NewZhengGaiListBean.ListBean.ListAbarbeitungBean bean = new NewZhengGaiListBean.ListBean.ListAbarbeitungBean();
            bean.orderOfSeverity = orderOfSeverity;
            bean.itemsName = UiUtils.getContent(tvJcxName);
            bean.description = UiUtils.getContent(tvDesc);
            bean.ztCondition = "草稿";
            bean.creatorTime = sysTime;
            bean.abarbeitungExplain = UiUtils.getContent(etInstructContent);
            if (listEmpty(filePaths)) {
                bean.listArbeitungPicture = filePaths;
            }
            bean.accountabilityUnit = UiUtils.getContent(tvZeRenDanWei);
            bean.chaosongId = cc;
            bean.chaosongPeople = UiUtils.getContent(tvchaoSong);
            bean.dikuaiId = Constant.projectId;
            bean.dikuaiName = Constant.diKuaiName;
            bean.fuyanId = review;
            bean.fuyanPeople = UiUtils.getContent(tvFuYan);
            bean.zhenggaiPeople = UiUtils.getContent(tvZhengGaiRen);
            bean.zhenggaiId = rectify;
            bean.sectionId = sectionId;
            bean.position = UiUtils.getContent(tvBuWei);
            bean.zhenggaiTime = UiUtils.getContent(tv_num);
            aCache.put("gxyj_trouble", (Serializable) bean);
            LogUtils.i("draft_json==", bean.toString());
            ToastUtils.myToast(act, R.string.save_success);
            //setTimer();
            if (pieceType.equals("gxYj")) {
                Intent intent = new Intent(act, ProcessOver.class);
                setResult(Constant.NOTE_PROBLEM_RESULT_CODE, intent);
                EventBus.getDefault().post("gxyj_draft");
                finish();
            }
        } catch (Exception e) {
            ToastUtils.myToast(act, R.string.save_fail);
            e.printStackTrace();
        }
    }

    //初始化媒体
    private void initPhoto() {
        //严重程度列表
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        serverProblemList.setLayoutManager(ms);
        serverProblemList.setNestedScrollingEnabled(true);
        serverProblemList.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        serverProblemAdapter = new ServerProblemAdapter(null);
        serverProblemList.setAdapter(serverProblemAdapter);
        //
        noteTroubleRcv.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        noteTroubleRcv.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));
        noteTroubleRcv.setNestedScrollingEnabled(true);
        adapter = new PictureOrVideoListNewAdapter(this);
        PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, noteTroubleRcv, selectList, 1);
    }

    private void uploadFile(List<File> filePaths) {
        showLoading("上传图片...");
        HttpRequest.get(this).url(ServerInterface.uploadFile).params(ParameterMap.put("fileList", filePaths)).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("uploadJson==", result.toString());
                dialog.dismiss();
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            List<String> list = new ArrayList<>();
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (UiUtils.isEmpty(imageArray.optString(i))) {
                                    list.add(imageArray.optString(i));
                                    filePath = StringUtils.listToStrByChar(list, ',');
                                }
                            }
                            addTrouble();
                        } else {
                            filePath = "";
                        }
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

    private void addTrouble() {
        showLoading("提交...");//userInfo.get(UserInfo.USER_ID)//提交人
        LogUtils.i("新增问题===", ParameterMap.addTrouble(batchId, Constant.projectId + "", "1", sectionId, filePath, tower, unit, floor, room, "", inspectionIds,
                particularsId, UiUtils.getContent(etInstructContent), troubleChengDu + "", userInfo.getUserId(), sysTime, UiUtils.getContent(tv_num),
                rectify, review, cc, dutyUnit).toString());
        HttpRequest.get(this).url(ServerInterface.xcjcAddTrouble)
                .params(ParameterMap.addTrouble(batchId, Constant.projectId + "", sectionId,
                        "1", filePath, tower, unit, floor, room, "",
                        inspectionIds, particularsId,
                        UiUtils.getContent(etInstructContent),
                        troubleChengDu + "", userInfo.getUserId(),
                        sysTime, UiUtils.getContent(tv_num),
                        rectify, review, cc, dutyUnit))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json==", result.toString());
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(act, "添加成功!");
                                setResult(Constant.ADD_PROBLEM_RESULT_CODE);
                                finish();
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

    private void addGxYjProblem() {
        showLoading("提交...");
        HttpRequest.get(this).url(ServerInterface.saveAbarbeitung)
                .params("appGxyjId", keyId)
                .params("itemsName", detailsName)//检查项和子类名称
                .params("siteName", UiUtils.getContent(tvBuWei))//楼栋位置名称
                .params("position", room)//部位-房间
                .params("inspectionName", inspectionName)//检查项名称
                .params("description", UiUtils.getContent(tvDesc))//描述
                .params("issuePicture", StringUtils.medialistToStrByChar(selectList, ','))//问题图片
                .params("buchongExplain", UiUtils.getContent(etInstructContent))//补充说明
                .params("tijiaoId", userInfo.getUserId())//提交人id  当前登录用户
                .params("tijiaoPeople", UserInfo.create(getApplicationContext()).getUserName())//提交人  当前假数据
                .params("abarbeitungTime", UiUtils.getContent(tv_num))//整改期限TimeFormatUitls.LaterData(UiUtils.getContent(tv_num))
                .params("accountabilityUnit", dutyName)//描述责任单位
                .params("zhenggaiId", rectify)//整改人id
                .params("zhenggaiPeople", rectifyName)//描述整改人
                .params("fuyanId", gxyjReview)//复验人id
                .params("fuyanPeople", reviewName)//复验人
                .params("chaosongId", cc)//抄送人id
                .params("chaosongPeople", ccName)//抄送人
                .params("orderOfSeverity", orderOfSeverity)//严重程度
                .params("projectId", Constant.parentProjectId)//项目ID
                .params("projectName", Constant.parentProjectName)//项目名称
                .params("dikuaiId", Constant.projectId)//地块id
                .params("dikuaiName", Constant.diKuaiName)//地块名称
                .params("sectionId", sectionId)//标段id
                .params("sectionName", sectionName)//标段名称
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(act, "添加成功!");
                                setResult(Constant.ADD_PROBLEM_RESULT_CODE);
                                finish();
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
                        ToastUtils.myToast(act, "fail->" + msg);
                    }
                });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!TextUtils.isEmpty(UiUtils.getContent(tv_num))) {
                num = Integer.parseInt(UiUtils.getContent(tv_num));
                if (num > 999) {
                    tv_num.setText("999");
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            int detailLength = etInstructContent.length();
            editorFontCount.setText(detailLength + "/200");
            if (detailLength == 200) {
                ToastUtils.myToast(act, "已输入达到200字界限了");
            }
        }
    };

    private void setEditData(XCJC_TroubleBean bean) {
        if (bean != null) {
            tvBuWei.setText(bean.buWei);
            tvJianChaX.setText(bean.JianChaR);
            tvDesc.setText(bean.desc);
            etInstructContent.setText(bean.instructContent);
            tvZhengGaiRen.setText(bean.zhengGaiRen);
            tvZeRenDanWei.setText(bean.zeRenDanWei);
            tvFuYan.setText(bean.fuYanRen);
            tvchaoSong.setText(bean.chaoSongRen);
            tv_num.setText(bean.zgQxTimes);
            if (bean.troubleChengDu == 1) {
                serverProblemAdapter.selected(0);
                //UiUtils.setBtnStatus(1, tvYiBan, tvImportance, tvJinJi);
            } else if (bean.troubleChengDu == 2) {
                serverProblemAdapter.selected(1);
                //UiUtils.setBtnStatus(2, tvYiBan, tvImportance, tvJinJi);
            } else if (bean.troubleChengDu == 3) {
                serverProblemAdapter.selected(2);
                //UiUtils.setBtnStatus(3, tvYiBan, tvImportance, tvJinJi);
            } else if (bean.troubleChengDu == 4) {
                serverProblemAdapter.selected(3);
                //UiUtils.setBtnStatus(3, tvYiBan, tvImportance, tvJinJi);
            }
            if (listEmpty(bean.images)) {
                for (int i = 0; i < bean.images.size(); i++) {
                    if (UiUtils.isEmpty(bean.images.get(i))) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(bean.images.get(i));
                        localMedia.setPictureType(PictureConfig.TYPE_IMAGE + "");
                        selectList.add(localMedia);
                        photoPaths.add(bean.images.get(i));
                        LogUtils.i("imagesUrl==", bean.images.get(i));
                    } else {
                        LogUtils.i("imagesUrl22==", bean.images.get(i));
                    }
                }
            }
            if (listEmpty(bean.videos)) {
                for (int j = 0; j < bean.videos.size(); j++) {
                    if (UiUtils.isEmpty(bean.videos.get(j))) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(bean.videos.get(j));
                        localMedia.setPictureType(PictureConfig.TYPE_VIDEO + "");
                        selectList.add(localMedia);
                        videoPaths.add(bean.videos.get(j));
                        LogUtils.i("videosUrl==", bean.videos.get(j));
                    } else {
                        LogUtils.i("videosUrl2==", bean.videos.toString());
                    }
                }
            }
            adapter.setNewData(selectList);
            adapter.notifyDataSetChanged();
        }
    }

    private void setGxyJEditData(NewZhengGaiListBean.ListBean.ListAbarbeitungBean bean) {
        if (bean != null) {
            tvBuWei.setText(bean.position);
            tvJianChaX.setText(bean.itemsName);
            tvDesc.setText(bean.description);
            tvZhengGaiRen.setText(bean.zhenggaiPeople);
            tvZeRenDanWei.setText(bean.accountabilityUnit);
            tvFuYan.setText(bean.fuyanPeople);
            tvchaoSong.setText(bean.chaosongPeople);
            tv_num.setText(bean.zhenggaiTime);
            if ("一般".equals(bean.orderOfSeverity)) {
                serverProblemAdapter.selected(0);
            } else if ("重要".equals(bean.orderOfSeverity)) {
                serverProblemAdapter.selected(1);
            } else if ("紧急".equals(bean.orderOfSeverity)) {
                serverProblemAdapter.selected(2);
            } else if ("要紧".equals(bean.orderOfSeverity)) {
                serverProblemAdapter.selected(3);
            }
            if (listEmpty(bean.listIssuePicture)) {
                for (int i = 0; i < bean.listIssuePicture.size(); i++) {
                    if (UiUtils.isEmpty(bean.listIssuePicture.get(i))) {
                        LocalMedia localMedia = new LocalMedia();
                        localMedia.setPath(bean.listIssuePicture.get(i));
                        localMedia.setPictureType(PictureConfig.TYPE_IMAGE + "");
                        selectList.add(localMedia);
                        photoPaths.add(bean.listIssuePicture.get(i));
                        LogUtils.i("imagesUrl==", bean.listIssuePicture.get(i));
                    } else {
                        LogUtils.i("imagesUrl22==", bean.listIssuePicture.get(i));
                    }
                }
            }
            adapter.setNewData(selectList);
            adapter.notifyDataSetChanged();
        }
    }

    private void showLoading(String ss) {
        dialog = new LoadingDialog(this);
        dialog.setTitle(ss);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    //selectList.addAll(selectLi);
                    //adapter.setNewData(selectList);
                    //adapter.notifyDataSetChanged();
                    for (int i = 0; i < selectLi.size(); i++) {
                        //提交处理
                        LocalMedia localMedia = new LocalMedia();
                        String path = "";
                        if (selectLi.get(i).isCut() && !selectLi.get(i).isCompressed()) {
                            // 裁剪过
                            path = selectLi.get(i).getCutPath();
                        } else if (selectLi.get(i).isCompressed() || (selectLi.get(i).isCut() && selectLi.get(i).isCompressed())) {
                            // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                            path = selectLi.get(i).getCompressPath();
                        } else {
                            // 原图
                            path = selectLi.get(i).getPath();
                        }
                        //选择回显集合
                        localMedia.setPath(path);
                        selectList.add(localMedia);
                        //草稿保存
                        int pictureType = PictureMimeType.isPictureType(selectLi.get(i).getPictureType());
                        if (pictureType == PictureConfig.TYPE_IMAGE) {//图片
                            if (selectLi.get(i).isCut()) {
                                filePaths.add(selectLi.get(i).getCutPath());
                                photoPaths.add(selectLi.get(i).getCutPath());
                            } else if (selectLi.get(i).isCompressed()) {
                                filePaths.add(selectLi.get(i).getCompressPath());
                                photoPaths.add(selectLi.get(i).getCompressPath());
                            } else {
                                filePaths.add(selectLi.get(i).getPath());
                                photoPaths.add(selectLi.get(i).getPath());
                            }
                        } else if (pictureType == PictureConfig.TYPE_VIDEO) {//视频
                            videoPaths.add(selectLi.get(i).getPath());
                            filePaths.add(selectLi.get(i).getPath());
                        }
                    }
                    adapter.setNewData(selectList);
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        } else {
            if (data != null) {
                if (resultCode == Constant.PLACE_CODE) {
                    tvBuWei.setText(data.getStringExtra("local"));

                } else if (resultCode == Constant.JIAN_CHA_XIANG_CODE) {
                    tvJianChaX.setText(data.getStringExtra("buWei01"));
                    String desc = data.getStringExtra("buWei02");
                    particularsId = data.getStringExtra("particularsId");
                    inspectionIds = data.getStringExtra("inspectionIds");
                    inspectionId = data.getStringExtra("inspectionId");
                    if (TextUtils.isEmpty(desc)) {
                        llDesc.setVisibility(View.GONE);
                        tvDesc.setText("");
                        particularsName = "";
                    } else {
                        llDesc.setVisibility(View.VISIBLE);
                        tvDesc.setText(desc);
                        particularsName = desc;
                    }
                } else if (resultCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
                    bundle = data.getBundleExtra("bundle");
                    tvZhengGaiRen.setText(bundle.getString("name"));
                    rectify = bundle.getString("id");
                    rectifyName = bundle.getString("name");
                } else if (resultCode == SelectTRPOrAU.ACCOUNTABILITY_UNIT) {
                    bundle = data.getBundleExtra("bundle");
                    tvZeRenDanWei.setText(bundle.getString("name"));
                    dutyName = bundle.getString("name");
                } else if (resultCode == Constant.FU_YAN_REN_CODE) {
                    tvFuYan.setText(data.getStringExtra("selectType"));
                    review = data.getStringExtra("selectId");
                    reviewName = data.getStringExtra("selectType");
                    review = data.getStringExtra("selectId");
                    gxyjReview = data.getStringExtra("selectId");
                } else if (resultCode == Constant.CHAO_SONG_REN_CODE) {
                    tvchaoSong.setText(data.getStringExtra("selectType"));
                    ccName = data.getStringExtra("selectType");
                    cc = data.getStringExtra("selectId");
                } else if (resultCode == Constant.DESC_LIST_CODE) {
                    tvDesc.setText(data.getStringExtra("content"));
                    particularsId = data.getStringExtra("id");
                }
            }
        }
    }
}
