package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.gxyj.GxYjPlaceSelect;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.ServerProblemAdapter;
import com.haozhiyan.zhijian.application.MyApp;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.bean.XCJC_TroubleBean;
import com.haozhiyan.zhijian.bean.XcjcSeverityProblem;
import com.haozhiyan.zhijian.bean.xcjc.XCJCSaveBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.XCJCSaveBeanDao;
import com.haozhiyan.zhijian.utils.AppUtils;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.SPUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.TimeFormatUitls;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.haozhiyan.zhijian.view.MyRecycleView;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.haozhiyan.zhijian.widget.LoadingDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//登记问题
public class AddTroubleActivity extends BaseActivity {

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
    @ViewInject(R.id.tv_jianChaR)
    TextView tvJianChaR;//检查人
    @ViewInject(R.id.tv_desc)
    TextView tvDesc;//描述
    @ViewInject(R.id.desc_rightimg)
    ImageView desc_rightimg;//描述
    @ViewInject(R.id.ll_desc)
    LinearLayout llDesc;
    //严重程度
    @ViewInject(R.id.serverProblemList)
    MyRecycleView serverProblemList;
    //    @ViewInject(R.id.tv_yiBan)
//    TextView tvYiBan;//一般
//    @ViewInject(R.id.tv_importance)
//    TextView tvImportance;//重要
//    @ViewInject(R.id.tv_jinJi)
//    TextView tvJinJi;//紧急
//    @ViewInject(R.id.tv_yaoJin)
//    TextView tvYaoJin;//要紧
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
    TextView tvChaoSong;//抄送人
    @ViewInject(R.id.tv_safe_draft)
    TextView tvSafeDraft;//保存草稿
    @ViewInject(R.id.tv_safe_commit)
    TextView tv_safe_commit;//保存提交
    @ViewInject(R.id.ll_jcx)
    LinearLayout llJcx;
    @ViewInject(R.id.ll_jcx_select)
    LinearLayout ll_jcx_select;
    @ViewInject(R.id.tv_jcxName)
    TextView tvJcxName;
    @ViewInject(R.id.tv_HuImg)
    TextView tvHuImg;
    private int num = 7;
    private int serous = 0;
    private String troubleChengDu = "1";
    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();
    private String batchId = "", rectifyDate = "", tower = "", unit = "", floor = "", room = "", housemap = "", inspectionIds = "", particularsId = "", particularsName = "",
            rectify = "", rectifyName = "", dutyUnit = "", review = "", gxyjReview = "", reviewName = "", cc = null, ccName = null, dutyName = "", sectionId = "";
    private String pieceType = "xcJc", keyId = "", sectionName = "", detailsName = "", inspectionName = "", orderOfSeverity = "",
            towerType = "", towerName = "", unitName = "", roomName = "", floorName = "", roomNum = "", local = "";
    private boolean iscaogao;//是否是从草稿进来的
    private String timeId;//草稿的id
    private String inspectionId;
    private ACache aCache;
    private String sysTime = AppUtils.getSystemDate("yyyy-MM-dd") + " " + AppUtils.getSystemTime("HH:mm:ss");

    private List<XcjcSeverityProblem> severityProblems;
    private ServerProblemAdapter serverProblemAdapter;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_add_trouble;
    }

    @Override
    protected void initView() {
        tv_title.setText("添加问题");
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        tvFuYan.setText(userInfo.getUserName());
        reviewName = userInfo.getUserName();
        review = userInfo.getUserId();
        gxyjReview = userInfo.getUserId();
    }

    @Override
    protected void initListener() {
        etInstructContent.addTextChangedListener(textWatcher);
        tv_num.addTextChangedListener(textWatcher);
        tv_num.setText(num + "");
        initPhoto();
        serverProblemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                serverProblemAdapter.selected(position);
                serous = position;
                if (listEmpty(severityProblems)) {
                    troubleChengDu = severityProblems.get(position).severityId + "";
                    orderOfSeverity = severityProblems.get(position).severityName;
                    LogUtils.i("troubleChengDu==", troubleChengDu + "");
                    LogUtils.i("orderOfSeverity==", orderOfSeverity);
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        pieceType = getIntent().getStringExtra("pieceType");
        sectionId = getIntent().getStringExtra("sectionId");
        iscaogao = getIntent().getBooleanExtra("iscaogao", false);
        timeId = getIntent().getStringExtra("timeId");
        if (TextUtils.equals("xcJc", pieceType)) {
//            getZanCunData();

            aCache = ACache.get(this, "XCJC_trouble");
            batchId = getIntent().getStringExtra("batchId");
            rectifyDate = getIntent().getStringExtra("rectifyDate");
            tvBuWei.setText(getIntent().getStringExtra("local"));
            tower = getIntent().getStringExtra("tower");
            unit = getIntent().getStringExtra("unit");
            floor = getIntent().getStringExtra("floor");
            room = getIntent().getStringExtra("room");
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
            llDesc.setVisibility(View.GONE);
            desc_rightimg.setVisibility(View.GONE);
            if (iscaogao) {
                getxcJcSave(timeId);
            }
            try {
                num=Integer.parseInt(rectifyDate);
                tv_num.setText(rectifyDate);
            } catch (Exception e) {}
        } else if (TextUtils.equals("gxYj", pieceType)) {
            aCache = ACache.get(this, "GXYJ_trouble");
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
            tvSafeDraft.setVisibility(View.GONE);
            ll_jcx_select.setVisibility(View.GONE);
            llJcx.setVisibility(View.VISIBLE);
            llDesc.setVisibility(View.VISIBLE);
        }
    }

    private void getZanCunData() {
        try {
            XCJC_TroubleBean bean = (XCJC_TroubleBean) getIntent().getSerializableExtra("troubleBean");
//            setEditData(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void setEditData(XCJC_TroubleBean bean) {
//        if (bean != null) {
//            tvBuWei.setText(bean.buWei);
//            tvJianChaX.setText(bean.JianChaR);
//            tvDesc.setText(bean.desc);
//            etInstructContent.setText(bean.instructContent);
//            tvZhengGaiRen.setText(bean.zhengGaiRen);
//            tvZeRenDanWei.setText(bean.zeRenDanWei);
//            tvFuYan.setText(bean.fuYanRen);
//            ccName=bean.chaoSongRen;
//            tvChaoSong.setText(bean.chaoSongRen);
//            tv_num.setText(bean.zgQxTimes);
//            if (bean.troubleChengDu == 1) {
//                serverProblemAdapter.selected(0);
//                //UiUtils.setBtnStatus(1, tvYiBan, tvImportance, tvJinJi);
//            } else if (bean.troubleChengDu == 2) {
//                serverProblemAdapter.selected(1);
//                //UiUtils.setBtnStatus(2, tvYiBan, tvImportance, tvJinJi);
//            } else if (bean.troubleChengDu == 3) {
//                serverProblemAdapter.selected(2);
//                //UiUtils.setBtnStatus(3, tvYiBan, tvImportance, tvJinJi);
//            } else if (bean.troubleChengDu == 4) {
//                serverProblemAdapter.selected(3);
//                //UiUtils.setBtnStatus(3, tvYiBan, tvImportance, tvJinJi);
//            }
//            if (listEmpty(bean.images)) {
//                for (int i = 0; i < bean.images.size(); i++) {
//                    if (UiUtils.isEmpty(bean.images.get(i))) {
//                        LocalMedia localMedia = new LocalMedia();
//                        localMedia.setPath(bean.images.get(i));
//                        localMedia.setPictureType(PictureConfig.TYPE_IMAGE + "");
//                        selectList.add(localMedia);
//                        photoPaths.add(bean.images.get(i));
//                        LogUtils.i("imagesUrl==", bean.images.get(i));
//                    } else {
//                        LogUtils.i("imagesUrl22==", bean.images.get(i));
//                    }
//                }
//            }
//            if (listEmpty(bean.videos)) {
//                for (int j = 0; j < bean.videos.size(); j++) {
//                    if (UiUtils.isEmpty(bean.videos.get(j))) {
//                        LocalMedia localMedia = new LocalMedia();
//                        localMedia.setPath(bean.videos.get(j));
//                        localMedia.setPictureType(PictureConfig.TYPE_VIDEO + "");
//                        selectList.add(localMedia);
//                        videoPaths.add(bean.videos.get(j));
//                        LogUtils.i("videosUrl==", bean.videos.get(j));
//                    } else {
//                        LogUtils.i("videosUrl2==", bean.videos.toString());
//                    }
//                }
//            }
//            adapter.setNewData(selectList);
//            adapter.notifyDataSetChanged();
//        }
//    }


    @OnClick({R.id.rl_back, R.id.tv_buWei, R.id.tv_jianChaR, R.id.tv_add, R.id.tv_reduce, R.id.tv_zhengGaiRen, R.id.tv_zeRenDanWei,
            R.id.tv_fuYan, R.id.tv_chaoSong, R.id.tv_safe_draft, R.id.tv_safe_commit, R.id.ll_desc, R.id.tv_HuImg})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_buWei:
                try {
                    if (TextUtils.equals("gxYj", pieceType)) {
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
                    } else if (TextUtils.equals("xcJc", pieceType)) {
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
            case R.id.tv_jianChaR:
                jumpActivityForResult(JianChaItemSelectActivity.class, Constant.JIAN_CHA_XIANG_CODE);
                break;
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
                SelectTRPOrAU.select(this, dutyName, sectionId, SelectTRPOrAU.ACCOUNTABILITY_UNIT);
                break;
            case R.id.tv_fuYan:
                if (TextUtils.equals("xcJc", pieceType)) {
                    Constant.REN_TYPE = 5;
                    Bundle bundle = new Bundle();
                    bundle.putString("name", UiUtils.getContent(tvFuYan));
                    bundle.putString("id", gxyjReview);
                    jumpActivityForResult(SelectAngle.class, Constant.FU_YAN_REN_CODE, bundle);
                } else if (TextUtils.equals("gxYj", pieceType)) {
                    SelectTRPOrAU.select(this, UiUtils.getContent(tvFuYan), SelectTRPOrAU.FU_YAN_REN_CODE);
                }
                break;
            case R.id.tv_chaoSong:
                Constant.REN_TYPE = 3;
                Bundle bundle = new Bundle();
                bundle.putString("name", UiUtils.getContent(tvChaoSong));
                bundle.putString("id", cc);
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE, bundle);
                break;
            case R.id.tv_safe_draft://草稿
                if (TextUtils.equals("gxYj", pieceType)) {
                    gxYjSave();
                } else if (TextUtils.equals("xcJc", pieceType)) {
                    xcJcSave();
                }
                break;
            case R.id.tv_safe_commit://提交
                if (StringUtils.isEmpty(UiUtils.getContent(tvJianChaR)) && TextUtils.equals("xcJc", pieceType)) {
                    tvJianChaR.setHintTextColor(setColor(R.color.red));
                    ToastUtils.myToast(act, "请选择检查项");
                } else {
                    if (StringUtils.isEmpty(UiUtils.getContent(tvZhengGaiRen))) {
                        tvZhengGaiRen.setHintTextColor(setColor(R.color.red));
                        ToastUtils.myToast(act, "请选择整改人");
                    } else {
                        if (StringUtils.isEmpty(UiUtils.getContent(tvFuYan))) {
                            tvFuYan.setHintTextColor(setColor(R.color.red));
                            ToastUtils.myToast(act, "请选择复验人");
                        } else {
                            if (isNetWork) {
                                UpLoadUtil.init(act, selectList, false).Call(new UpLoadCallBack() {
                                    @Override
                                    public void onComplete(String paths) {
                                        if (TextUtils.equals("xcJc", pieceType)) {
                                            addTrouble(paths);
                                        } else if (TextUtils.equals("gxYj", pieceType)) {
                                            addGxYjProblem(paths);
                                        }
                                    }
                                });
                            } else {
                                ToastUtils.myToast(act, "没有网络，数据无法同步");
                            }
                        }
                    }
                }
                break;
            case R.id.ll_desc:
                if (TextUtils.equals("xcJc", pieceType)) {
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putString("inspectionId", inspectionId);
//                    bundle2.putString("descName", UiUtils.getContent(tvDesc));
//                    jumpActivityForResult(ProblemDescList.class, Constant.DESC_LIST_CODE, bundle2);
                } else if (TextUtils.equals("gxYj", pieceType)) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("inspectionId", inspectionId);
                    bundle2.putString("descName", UiUtils.getContent(tvDesc));
                    jumpActivityForResult(ProblemDescList.class, Constant.DESC_LIST_CODE, bundle2);
                }
                break;
            case R.id.tv_HuImg:
                if (TextUtils.equals("xcJc", pieceType)) {
                    Intent intent = new Intent(act, XCJCImagesActivity.class);
                    intent.putExtra("roomId", room);
                    intent.putExtra("towerId", tower);
                    intent.putExtra("unitId", unit);
                    intent.putExtra("floorId", floor);
                    intent.putExtra("towerFloorUnitRoom", UiUtils.getContent(tvBuWei));
                    intent.putExtra("housemap", housemap);
                    startActivity(intent);
                } else if (TextUtils.equals("gxYj", pieceType)) {
//                    Intent intent = new Intent(act, GxYjH5.class);
//                    intent.putExtra("roomId", room);
//                    intent.putExtra("roomNum", roomNum);
//                    intent.putExtra("towerName", towerName);
//                    intent.putExtra("unitName", unitName);
//                    intent.putExtra("towerId", tower);
//                    intent.putExtra("unitId", unit);
//                    intent.putExtra("floorId", floor);
//                    intent.putExtra("houseMap", housemap);
//                    LogUtils.print("data222==="+housemap);
//                    startActivity(intent);
                }
                break;
            default:
                break;
        }
    }

    private void addTrouble(String filePath) {
        showLoading("提交...");//userInfo.get(UserInfo.USER_ID)//提交人
        HttpRequest.get(this).url(ServerInterface.xcjcAddTrouble)
                .params(ParameterMap.addTrouble(batchId,
                        Constant.projectId + "", sectionId,
                        "1", filePath, tower, unit, floor, room,
                        housemap, inspectionIds, particularsId,
                        UiUtils.getContent(etInstructContent),
                        orderOfSeverity, userInfo.getUserId(),
                        sysTime, TimeFormatUitls.LaterData(UiUtils.getContent(tv_num)),
                        rectify, review, cc, dutyUnit))
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json==", result.toString());
                        dialog.dismiss();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                try {
                                    if (iscaogao && saveBean != null) {
                                        DaoSession daoSession = MyApp.getInstance().getDaoSession();
                                        daoSession.delete(saveBean);
                                        EventBus.getDefault().post("zancunxcjcBeanSuccess");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Intent intent = new Intent(act, XianChangJianChactivity.class);
                                setResult(Constant.ADD_PROBLEM_RESULT_CODE, intent);
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

    private void addGxYjProblem(String filePath) {
        showLoading("提交...");
        HttpRequest.get(this)
                .url(ServerInterface.saveAbarbeitung)
                .params("appGxyjId", keyId)
                .params("itemsName", detailsName)//检查项和子类名称
                .params("siteName", UiUtils.getContent(tvBuWei))//楼栋位置名称   UiUtils.getContent(tvBuWei)
                .params("position", room)//部位-房间
                .params("inspectionName", inspectionName)//检查项名称
                .params("description", UiUtils.getContent(tvDesc))//描述
                .params("issuePicture", filePath)//问题图片 StringUtils.medialistToStrByChar(selectList, ',')
                .params("buchongExplain", UiUtils.getContent(etInstructContent))//补充说明
                .params("tijiaoId", userInfo.getUserId())//提交人id  当前登录用户
                .params("tijiaoPeople", UserInfo.create(getApplicationContext()).getUserName())//提交人  当前假数据
                .params("abarbeitungTime", UiUtils.getContent(tv_num))//整改期限  TimeFormatUitls.LaterData(UiUtils.getContent(tv_num))
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        dialog.dismiss();
                        ToastUtils.myToast(act, "fail->" + msg);
                    }
                });
        ;
    }


    private void xcJcSave() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DaoSession daoSession = MyApp.getInstance().getDaoSession();
                    XCJCSaveBean saveBean = new XCJCSaveBean();
                    if (iscaogao) {
                        saveBean.timeId = timeId;
                    } else {
                        saveBean.timeId = System.currentTimeMillis() + "";
                    }
                    saveBean.particularsId = particularsId;
                    saveBean.inspectionIds = inspectionIds;
                    saveBean.particularsName = particularsName;
                    saveBean.particularsDesc = UiUtils.getContent(etInstructContent);
                    saveBean.JianChaR = UiUtils.getContent(tvJianChaR);
                    saveBean.batchId = batchId;
                    saveBean.projectId = Constant.projectId + "";
                    saveBean.sectionId = sectionId;
                    saveBean.pics = selectList;
                    saveBean.tower = tower;
                    saveBean.unit = unit;
                    saveBean.floor = floor;
                    saveBean.room = room;
                    saveBean.housemap = housemap;
                    saveBean.local = local;
                    saveBean.inspectionIds = inspectionIds;
                    saveBean.particularsId = particularsId;
                    saveBean.serious = serous + "";
                    saveBean.troubleChengDu = troubleChengDu;
                    saveBean.orderOfSeverity = orderOfSeverity;
                    saveBean.num = num + "";
                    saveBean.rectifyName = rectifyName;
                    saveBean.dutyName = dutyName;
                    saveBean.dutyUnit = dutyUnit;

                    saveBean.reviewName = reviewName;
                    saveBean.review = review;
                    saveBean.ccName = ccName;
                    saveBean.cc = cc;

                    saveBean.rectifyTimelimit = UiUtils.getContent(tv_num);
                    saveBean.rectify = rectify;
                    saveBean.createTime = sysTime;
                    daoSession.insertOrReplace(saveBean);
                    ToastUtils.myToast(AddTroubleActivity.this, "暂存完成");
                    EventBus.getDefault().post("zancunxcjcBeanSuccess");
                } catch (Exception e) {
                    ToastUtils.myToast(AddTroubleActivity.this, "暂存失败");
                }
            }
        }).start();
    }

    private XCJCSaveBean saveBean;

    private void getxcJcSave(String timeId) {
        DaoSession daoSession = MyApp.getInstance().getDaoSession();
        QueryBuilder<XCJCSaveBean> qb = daoSession.queryBuilder(XCJCSaveBean.class);
        Query<XCJCSaveBean> query = qb.where(XCJCSaveBeanDao.Properties.TimeId.eq(timeId)).build();
        List<XCJCSaveBean> list = query.list();
        if (list != null && list.size() > 0) {
            saveBean = list.get(0);
            batchId = saveBean.batchId;
            sectionId = saveBean.sectionId;
            selectList = saveBean.pics;
            if (adapter != null) {
                adapter.setNewData(selectList);
                adapter.notifyDataSetChanged();
            }
            tvBuWei.setText(saveBean.local);
            tvJianChaR.setText(saveBean.JianChaR);
            particularsName = saveBean.particularsName;
            tvDesc.setText(particularsName);
            if (TextUtils.isEmpty(particularsName)) {
                llDesc.setVisibility(View.GONE);
                tvDesc.setText("");
                particularsName = "";
            } else {
                llDesc.setVisibility(View.VISIBLE);
                tvDesc.setText(particularsName);
            }
            etInstructContent.setText(saveBean.particularsDesc);
            try {
                serous = Integer.parseInt(saveBean.serious);
                serverProblemAdapter.selected(serous);
            } catch (Exception e) {
            }
            troubleChengDu = saveBean.troubleChengDu;
            orderOfSeverity = saveBean.orderOfSeverity;
            try {
                num = Integer.parseInt(saveBean.num);
                tv_num.setText(saveBean.num);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tvZhengGaiRen.setText(saveBean.rectifyName);
            rectify = saveBean.rectify;
            tvZeRenDanWei.setText(saveBean.dutyName);
            dutyUnit = saveBean.dutyUnit;
            tvFuYan.setText(saveBean.reviewName);
            review = saveBean.review;
            tvChaoSong.setText(saveBean.ccName);
            cc = saveBean.cc;
            ccName = saveBean.ccName;
            tower = saveBean.tower;
            unit = saveBean.unit;
            floor = saveBean.floor;
            local = saveBean.local;
            room = saveBean.room;
            housemap = saveBean.housemap;
            dutyName = saveBean.dutyName;
            inspectionIds = saveBean.inspectionIds;
            particularsId = saveBean.particularsId;
            rectifyName = saveBean.rectifyName;
            if (StringUtils.isEmpty(housemap)) {
                tvHuImg.setVisibility(View.GONE);
            } else {
                tvHuImg.setVisibility(View.VISIBLE);
            }

        }
    }


    private void gxYjSave() {

    }


    private void initPhoto() {

        //严重程度列表
        LinearLayoutManager ms = new LinearLayoutManager(this);
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        serverProblemList.setLayoutManager(ms);
        serverProblemList.setNestedScrollingEnabled(true);
        serverProblemList.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
        serverProblemAdapter = new ServerProblemAdapter(null);
        serverProblemList.setAdapter(serverProblemAdapter);
        //图片选择
        noteTroubleRcv.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        noteTroubleRcv.addItemDecoration(new GridSpacingItemDecoration(3, 1, true));
        noteTroubleRcv.setNestedScrollingEnabled(true);
        adapter = new PictureOrVideoListNewAdapter(this);
        PhotoCameraUtils.init(AddTroubleActivity.this).photoCameraDialogListAdapter(adapter, noteTroubleRcv, selectList, 1);
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
                ToastUtils.myToast(act, R.string.string_twoBai_end);
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event != null) {
            if (event instanceof ItemValues) {
                ItemValues itemValues = (ItemValues) event;
                housemap = itemValues.houseMap;
                pieceType = itemValues.pieceType;
                if (StringUtils.isEmpty(housemap)) {
                    tvHuImg.setVisibility(View.GONE);
                } else {
                    tvHuImg.setVisibility(View.VISIBLE);
                }
                local = itemValues.towerFloorUnitRoom;
                tvBuWei.setText(itemValues.towerFloorUnitRoom);
                tower = itemValues.towerId;
                unit = itemValues.unitId;
                floor = itemValues.floorId;
                room = itemValues.roomId;
                roomNum = itemValues.roomNum;
                towerName = itemValues.tower;
                unitName = itemValues.unit;
                floorName = itemValues.floor;
                roomName = itemValues.roomNum;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    //选择处理
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    break;
                default:
                    break;
            }
        } else {
            if (data != null) {
                if (resultCode == Constant.PLACE_CODE) {
                    pieceType = data.getStringExtra("pieceType");
                    local = data.getStringExtra("local");
                    tvBuWei.setText(local);
                    tower = data.getStringExtra("tower");
                    unit = data.getStringExtra("unit");
                    floor = data.getStringExtra("floor");
                    room = data.getStringExtra("room");
                    housemap = data.getStringExtra("housemap");
                    if (StringUtils.isEmpty(housemap)) {
                        tvHuImg.setVisibility(View.GONE);
                    } else {
                        tvHuImg.setVisibility(View.VISIBLE);
                    }
                    towerName = data.getStringExtra("towerName");
                    unitName = data.getStringExtra("unitName");
                    floorName = data.getStringExtra("floorName");
                    roomName = data.getStringExtra("roomName");
                } else if (resultCode == Constant.JIAN_CHA_XIANG_CODE) {
                    tvJianChaR.setText(data.getStringExtra("buWei01"));
                    String desc = data.getStringExtra("buWei02");
                    particularsId = data.getStringExtra("particularsId");
                    inspectionIds = data.getStringExtra("inspectionIds");
//                    inspectionId = data.getLongExtra("inspectionId", 0L);
                    if (TextUtils.isEmpty(desc)) {
                        llDesc.setVisibility(View.GONE);
                        tvDesc.setText("");
                        particularsName = "";
                    } else {
                        llDesc.setVisibility(View.VISIBLE);
                        tvDesc.setText(desc);
                        particularsName = desc;
                    }
                } else if (resultCode == SelectTRPOrAU.FU_YAN_REN_CODE) {
                    bundle = data.getBundleExtra("bundle");
                    tvFuYan.setText(bundle.getString("name"));
                    reviewName = bundle.getString("name");
                    gxyjReview = bundle.getString("id");
                } else if (resultCode == Constant.FU_YAN_REN_CODE) {
                    tvFuYan.setText(data.getStringExtra("selectType"));
                    gxyjReview = data.getStringExtra("selectId");
                    reviewName = data.getStringExtra("selectType");
                    review = data.getStringExtra("selectId");
                } else if (resultCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
                    bundle = data.getBundleExtra("bundle");
                    tvZhengGaiRen.setText(bundle.getString("name"));
                    rectifyName = bundle.getString("name");
                    rectify = bundle.getString("id");
                } else if (resultCode == SelectTRPOrAU.ACCOUNTABILITY_UNIT) {
                    bundle = data.getBundleExtra("bundle");
                    tvZeRenDanWei.setText(bundle.getString("name"));
                    dutyName = bundle.getString("name");
                    dutyUnit = bundle.getString("id");
                } else if (resultCode == Constant.CHAO_SONG_REN_CODE) {
                    tvChaoSong.setText(data.getStringExtra("selectType"));
                    ccName = data.getStringExtra("selectType");
                    cc = data.getStringExtra("selectId");
                } else if (resultCode == Constant.DESC_LIST_CODE) {
                    tvDesc.setText(data.getStringExtra("content"));
                    particularsId = data.getStringExtra("id");
                }
            }
        }
    }

    private void showLoading(String ss) {
        dialog = new LoadingDialog(this);
        dialog.setTitle(ss);
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
