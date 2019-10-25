package com.haozhiyan.zhijian.fragment.scslFragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.PictureVideoShowAdapter;
import com.haozhiyan.zhijian.bean.SCSLDetailData;
import com.haozhiyan.zhijian.bean.scsl.SCSLDefaultBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.haozhiyan.zhijian.widget.CancelSureDialog;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.haozhiyan.zhijian.zjInterface.AJSInterface;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/24.
 * Describe: 单位布局
 */
public class JsDw_JS_SCSlFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rlParent;//父布局
    //提交数据布局
    private NestedScrollView parentScrollView;
    private WebView myWebView;
    private RecyclerView rvAboutImg;//相关图片
    private LinearLayout ll_remindZG;//通知整改布局-当验收合格的时候才显示该布局，合格率为100%是合格
    private LinearLayout ll_checkRemind;//是否通知整改点击
    private CheckBox checkRemind;//是否通知整改
    private LinearLayout llZgLayout;
    private TextView zhengGaiPeople;//选择设置整改人
    private TextView tv_complete_commit;//提交检查问题
    private Button remindZG;//通知整改按钮-就安插完毕时用到

    //待整改
    private TextView tv_percent_pass;//合格率
    private TextView tv_point_count;//点数
    private TextView tv_pass_rule;//合格标准
    private TextView tv_inspectionPeople;//检查人
    private TextView tv_CreatorTime;//问题创建时间-检查人检查时间
    private TextView tv_rectify_people;//整改人
    private TextView tv_rectify_time;//整改时间
    private RelativeLayout rl_zgPicInstruct;//整改照片和描述布局
    private RecyclerView scSlZgRcv;//整改照片
    private EditText etInstructContent;
    private TextView editorFontCount;
    private TextView tv_completeZG;//完成整改
    private LinearLayout ll_zgStatus;//整改状态布局

    //已整改
    private TextView tv_rectify_date;//整改日期
    private RecyclerView scSlRcvShow;

    private WebSettings settings;
    private PictureOrVideoListNewAdapter jsListAdapter;
    private PictureVideoShowAdapter showAdapter;
    private List<LocalMedia> jsSelectList = new ArrayList<>();
    private List<LocalMedia> showList = new ArrayList<>();
    private List<LocalMedia> zgShowList = new ArrayList<>();
    /**
     * type  0admin,  1施工， 2监理， 3建设
     */
    private int type;
    private String inspectionId = "", inspectionParentId = "", inspectionName = "", inspectionSunName = "", partsDivision = "分户";
    private String sectionId = "32", towerId = "1", unitId = "1", userType = "", identifying = "空", sectionName = "";
    private String entrance = "", rectifyId = "", id = "", rectifyName = "";
    private String detailsName = "", handOverPart = "", siteName = "", scslPictureData = "";
    private int roomId = 1;
    private List<String> fileList = new ArrayList<>();
    private AJSInterface ajs;
    private ACache aCache;
    private View statusLayout;
    private String shiGongStatus = "", jianLiStatus = "", jianSheStatus = "";
    private SCSLDetailData.ListBean scslDataBean;
    private UserInfo userInfo;
    private String percentOfPass;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_jsdw_scsl_new;
    }

    @Override
    public void initView(View view) {
        rlParent = getOutView(view, R.id.rl_parent);
        tv_complete_commit = getOutView(view, R.id.tv_complete_commit);
        //初始获取数据
        initIntent();
        //初始状态布局显示
        initLayoutShow(identifying);
        //绑定id
        setById();
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData(boolean isNetWork) {
        if (isNetWork) {
            //查询整改问题
            if (entrance.equals("work")) {
                if (TextUtils.equals(jianSheStatus, null)) {
                    showHtml(inspectionId, roomId);
                } else {
                    if (jianSheStatus.equals("3")) {
                        queryWorkDetail();
                    }
                }
            } else {
                queryDaibanDetail();
            }
        } else {
            ToastUtils.myToast(getActivity(), "网络不太好哦，检查一下网络");
        }
    }

    @Override
    protected void lazyLoad() {
    }

    private void initIntent() {
        userInfo = UserInfo.create(ctx);
        aCache = ACache.get(ctx, "cookie");
        type = getArguments().getInt("type");
        inspectionId = getArguments().getString("inspectionId");
        inspectionParentId = getArguments().getString("inspectionParentId");
        inspectionName = getArguments().getString("inspectionName");
        inspectionSunName = getArguments().getString("inspectionSunName");
        partsDivision = getArguments().getString("partsDivision");
        sectionId = getArguments().getString("sectionId");
        sectionName = getArguments().getString("sectionName");
        towerId = getArguments().getString("towerId");
        towerName = getArguments().getString("towerName");
        unitId = getArguments().getString("unitId");
        unitName = getArguments().getString("unitName");
        roomId = getArguments().getInt("roomId");
        userType = getArguments().getString("userType");
        entrance = getArguments().getString("entrance");
        shiGongStatus = getArguments().getString("shiGongStatus");
        jianLiStatus = getArguments().getString("jianLiStatus");
        jianSheStatus = getArguments().getString("jianSheStatus");
        handOverPart = getArguments().getString("roomNum");
        siteName = getArguments().getString("towerUnitFloorRoom");
        floorName = getArguments().getString("floorName");
        floorId = getArguments().getString("floorId");

        LogUtils.logGGQ("实测实量详情--type:" + type);
        LogUtils.logGGQ("实测实量详情--inspectionId:" + inspectionId);
        LogUtils.logGGQ("实测实量详情--inspectionName:" + inspectionName);
        LogUtils.logGGQ("实测实量详情--inspectionSunName:" + inspectionSunName);
        LogUtils.logGGQ("实测实量详情--partsDivision:" + partsDivision);
        LogUtils.logGGQ("实测实量详情--sectionId:" + sectionId);
        LogUtils.logGGQ("实测实量详情--towerId:" + towerId);
        LogUtils.logGGQ("实测实量详情--unitId:" + unitId);
        LogUtils.logGGQ("实测实量详情--roomId:" + roomId);
        LogUtils.logGGQ("实测实量详情--userType:" + userType);
        LogUtils.logGGQ("实测实量详情--identifying:" + identifying);
        LogUtils.logGGQ("实测实量详情--shiGongStatus:" + shiGongStatus);
        LogUtils.logGGQ("实测实量详情--jianLiStatus:" + jianLiStatus);
        LogUtils.logGGQ("实测实量详情--jianSheStatus:" + jianSheStatus);
    }

    private void initLayoutShow(String identifying) {
        switch (type) {
            case 3://建设
                if (TextUtils.equals(jianSheStatus, null)) {
                    if (userType.equals("3") || userType.equals("0")) {
                        tv_complete_commit.setVisibility(View.VISIBLE);
                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_submit_layout, null);
                    } else {
                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_empty_layout, null);
                    }
                } else {
                    tv_complete_commit.setVisibility(View.GONE);
                    if (TextUtils.equals(identifying, "待整改")) {
                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_dzg_layout, null);
                    } else if (TextUtils.equals(identifying, "已整改")) {
                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_yzg_layout, null);
                    } else if (TextUtils.equals(identifying, "检查完毕")) {
                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_jcwb_layout, null);
                    } else {
                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_empty_layout, null);
                    }
                }
                rlParent.removeAllViews();
                statusLayout.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                rlParent.addView(statusLayout);
                rlParent.addView(tv_complete_commit);
                break;
//            case 1://施工
//                if (TextUtils.equals(shiGongStatus, "1")) {//默认提交检查
//                    if (TextUtils.equals(identifying, "待整改")) {
//                        //施工待整改布局
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_dzg_layout, null);
//                    } else if (TextUtils.equals(identifying, "已整改")) {
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_yzg_layout, null);
//                    } else if (TextUtils.equals(identifying, "检查完毕")) {
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_jcwb_layout, null);
//                    } else {
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_empty_layout, null);
//                    }
//                }
//                rlParent.removeAllViews();
//                rlParent.addView(statusLayout);
//                break;
//            case 2://监理
//                if (TextUtils.equals(jianLiStatus, "2")) {
//                    if (TextUtils.equals(identifying, "待整改")) {
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_dzg_layout, null);
//                    } else if (TextUtils.equals(identifying, "已整改")) {
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_yzg_layout, null);
//                    } else if (TextUtils.equals(identifying, "检查完毕")) {
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_jcwb_layout, null);
//                    } else {//默认提交检查
//                        statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_empty_layout, null);
//                    }
//                    rlParent.removeAllViews();
//                    rlParent.addView(statusLayout);
//                }
//                break;
            default:
                break;
        }
    }

    private void setById() {
        parentScrollView = getOutView(statusLayout, R.id.parentScrollView);
        myWebView = getOutView(statusLayout, R.id.webView);
        rvAboutImg = getOutView(statusLayout, R.id.scSlRcv);
        checkRemind = getOutView(statusLayout, R.id.checkRemind);
        ll_remindZG = getOutView(statusLayout, R.id.ll_remindZG);
        llZgLayout = getOutView(statusLayout, R.id.ll_zg_layout);
        zhengGaiPeople = getOutView(statusLayout, R.id.zhengGaiPeople);
        //tv_complete_commit = getOutView(statusLayout, R.id.tv_complete_commit);
        tv_inspectionPeople = getOutView(statusLayout, R.id.tv_inspectionPeople);
        tv_CreatorTime = getOutView(statusLayout, R.id.tv_CreatorTime);
        tv_rectify_people = getOutView(statusLayout, R.id.tv_rectify_people);
        tv_rectify_time = getOutView(statusLayout, R.id.tv_rectify_time);
        scSlZgRcv = getOutView(statusLayout, R.id.scSlZgRcv);
        tv_rectify_date = getOutView(statusLayout, R.id.tv_rectify_date);
        scSlRcvShow = getOutView(statusLayout, R.id.scSlRcvShow);
        etInstructContent = getOutView(statusLayout, R.id.et_instruct_content);
        rl_zgPicInstruct = getOutView(statusLayout, R.id.rl_zgPicInstruct);
        editorFontCount = getOutView(statusLayout, R.id.id_editor_detail_font_count);
        tv_percent_pass = getOutView(statusLayout, R.id.tv_percent_pass);
        tv_point_count = getOutView(statusLayout, R.id.tv_point_count);
        tv_pass_rule = getOutView(statusLayout, R.id.tv_pass_rule);
        ll_checkRemind = getOutView(statusLayout, R.id.ll_checkRemind);
        tv_completeZG = getOutView(statusLayout, R.id.tv_completeZG);
        ll_zgStatus = getOutView(statusLayout, R.id.ll_zgStatus);
        remindZG = getOutView(statusLayout, R.id.remindZG);
        try {
            initWebView(myWebView, parentScrollView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initListPhoto(scSlZgRcv);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initShowPhoto(rvAboutImg, showList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initShowPhoto(scSlRcvShow, zgShowList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListenerView(tv_rectify_people);
        setListenerView(tv_complete_commit);
        setListenerView(zhengGaiPeople);
        setListenerView(ll_checkRemind);
        setListenerView(tv_completeZG);
        setListenerView(remindZG);
        if (checkRemind != null) {
            checkRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        llZgLayout.setVisibility(View.VISIBLE);
                        remindZG.setVisibility(View.VISIBLE);
                        setText(zhengGaiPeople, rectifyName);
                    } else {
                        llZgLayout.setVisibility(View.GONE);
                        remindZG.setVisibility(View.GONE);
                        setText(zhengGaiPeople, "");
                    }
                }
            });
        }
        if (etInstructContent != null) {
            etInstructContent.addTextChangedListener(textWatcher);
        }
    }

    private void initWebView(WebView webView, NestedScrollView parentScrollView) {
        settings = webView.getSettings();
        //打开支持JavaScript开关
        settings.setJavaScriptEnabled(true);
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setDisplayZoomControls(false);
        settings.setBlockNetworkImage(false);
        //设定支持viewport,新型高配手机匹配支持
        settings.setUseWideViewPort(true);
        //允许访问文件
        settings.setAllowFileAccess(true);
        //解决网页图片不显示问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        //webView.setParentScrollView(parentScrollView);
        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setWebContentsDebuggingEnabled(true);
//        }
        if (userInfo.getUserType().equals("0") || userInfo.getUserType().equals("3")) {
            if (TextUtils.equals(jianSheStatus, "3")) {

            } else {
                ajs = AJSInterface.getInstance(ctx, webView);
                addJSInterface(webView);
                ajs.setMethod(new AJSInterface.Method() {
                    @Override
                    public void getPoint(String data) {
                        //-----------------
                        LogUtils.d("", data);
                    }

                    @Override
                    public void getGxYjPoint(String data) {
                        //----------------
                        LogUtils.d("", data);
                    }

                    @Override
                    public void getScSlShowData(String json) {
                        scslPictureData = json;
//                JSONArray list = ListUtils.saveDataToJSONArray(json);
//                if (arrayEmpty(list)) {
//                    String status = list.optJSONObject(list.length() - 1).optString("status");
//                    if (status.equals("0")) {//合格
//                        ll_remindZG.setVisibility(View.VISIBLE);
//                    } else if (status.equals("1")) {//不合格
//                        ll_remindZG.setVisibility(View.GONE);
//                    }
//                }
                    }
                });
            }
        }

        //获取检查项相关数据
        ajs.setDataMethod(new AJSInterface.DataMethod() {
            @Override
            public void getInspectionData(String datas) {
                LogUtils.print("datas33==" + datas + "");
                if (!StringUtils.isEmpty(datas)) {
                    try {
                        JSONObject data = new JSONObject(datas);
                        inspectionId = data.optString("id");
                        inspectionParentId = data.optString("pId");
                        inspectionName = data.optString("pIdName");
                        inspectionSunName = data.optString("idName");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void getPass(String pass) {
                percentOfPass = pass;
                LogUtils.print("===合格率333===" + pass);
                if ("100%".equals(pass)) {//合格
                    ll_remindZG.setVisibility(View.GONE);
                } else {//不合格
                    ll_remindZG.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @JavascriptInterface
    public void addJSInterface(WebView mWebView) {
        //和js约定,包括调用类的对象,以及起的别名
        mWebView.addJavascriptInterface(ajs, ajs.getInterface());
    }

    private void initListPhoto(RecyclerView scSlRcv) {
        scSlRcv.setLayoutManager(new GridLayoutManager(ctx, 3, LinearLayoutManager.VERTICAL, false));
        scSlRcv.addItemDecoration(new GridSpacingItemDecoration(3, 2, true));
        scSlRcv.setNestedScrollingEnabled(true);
        jsListAdapter = new PictureOrVideoListNewAdapter(getActivity());
        PhotoCameraUtils.init(this).photoDialogListAdapter(jsListAdapter, scSlRcv, jsSelectList, 1);
    }

    private void initShowPhoto(RecyclerView scSlRcv, List<LocalMedia> list) {
        scSlRcv.setLayoutManager(new GridLayoutManager(ctx, 3, LinearLayoutManager.VERTICAL, false));
        scSlRcv.addItemDecoration(new GridSpacingItemDecoration(3, 2, true));
        scSlRcv.setNestedScrollingEnabled(true);
        showAdapter = new PictureVideoShowAdapter(getActivity());
        PhotoCameraUtils.init(JsDw_JS_SCSlFragment.this).photoCameraAdapter(showAdapter, scSlRcv, list, 1);
    }

    //工作台查询整改问题
    private void queryWorkDetail() {
        HttpRequest.get(getContext()).url(ServerInterface.selectScslType)
                .params("roomTowerFloorId", roomId)
                .params("inspectionSunId", inspectionId)
                .params("scslType", "建设")//实测试量类型：监理，施工，建设
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            LogUtils.logGGQ("scsl建设查询整改问题result:" + result.toString());
                            SCSLDetailData bean = new Gson().fromJson(result.toString(), SCSLDetailData.class);
                            if (bean != null) {
                                if (bean.code == 0) {
                                    SCSLDetailData.ListBean listBean = bean.list;
                                    if (listBean != null) {
                                        scslDataBean = listBean;
                                        identifying = listBean.scslState;
                                        if (!StringUtils.isEmpty(identifying)) {
                                            initLayoutShow(identifying);
                                            setById();
                                        }
                                        //获取户型图
                                        if (TextUtils.equals("100%", listBean.percentOfPass)) {
                                            ll_remindZG.setVisibility(View.GONE);
                                        } else {
                                            ll_remindZG.setVisibility(View.VISIBLE);
                                        }
                                        showHtml(listBean.inspectionId, listBean.roomTowerFloorId);
                                        setText(tv_inspectionPeople, listBean.inspectionPeople);
                                        setText(tv_CreatorTime, listBean.creatorTime);
                                        setText(tv_rectify_people, listBean.zhenggaiPeople);
                                        setText(zhengGaiPeople, listBean.zhenggaiPeople);
                                        // setText(tv_percent_pass, listBean.percentOfPass);
                                        setText(tv_point_count, listBean.scslCount);
                                        setText(tv_pass_rule, listBean.qualifiedStandard);
                                        setText(tv_inspectionPeople, listBean.inspectionPeople);
                                        setText(tv_CreatorTime, listBean.creatorTime);
                                        setText(tv_rectify_time, listBean.zhenggaiTime);
                                        setText(tv_rectify_date, listBean.zhenggaiTime);
                                        id = listBean.scslId + "";
                                        rectifyId = listBean.zhenggaiId + "";
                                        rectifyName = listBean.zhenggaiPeople;
                                        LogUtils.print("scslscslState555===" + listBean.scslState);
                                        LogUtils.print("scslId666===" + scslDataBean.scslId);
                                        //设置待整改状态布局
                                        try {
                                            if (TextUtils.equals(identifying, "待整改")) {
                                                if (TextUtils.equals(UiUtils.getContent(tv_rectify_people), userInfo.getUserName())) {
                                                    rl_zgPicInstruct.setVisibility(View.VISIBLE);
                                                    tv_completeZG.setVisibility(View.VISIBLE);
                                                } else {
                                                    rl_zgPicInstruct.setVisibility(View.GONE);
                                                    tv_completeZG.setVisibility(View.GONE);
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        //相关图片显示
                                        if (listEmpty(listBean.xiangGuanPV)) {
                                            for (String path : listBean.xiangGuanPV) {
                                                LocalMedia localMedia = new LocalMedia();
                                                localMedia.setPath(path);
                                                localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                                showList.add(localMedia);
                                            }
                                            showAdapter.setNewData(showList);
                                            showAdapter.notifyDataSetChanged();
                                        }
                                        //整改图片显示
                                        if (listEmpty(listBean.zhengGaiPV)) {
                                            for (String path : listBean.zhengGaiPV) {
                                                LocalMedia localMedia = new LocalMedia();
                                                localMedia.setPath(path);
                                                localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                                zgShowList.add(localMedia);
                                            }
                                            showAdapter.setNewData(zgShowList);
                                            showAdapter.notifyDataSetChanged();
                                        }
                                    } else {
                                        identifying = "空";
                                        initLayoutShow(identifying);
                                        setById();
                                        showHtml(inspectionId, 0);
                                    }
                                } else {
                                    identifying = "空";
                                    initLayoutShow(identifying);
                                    setById();
                                    showHtml(inspectionId, 0);
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

    //添加整改问题
    private void AddZhengGaiProblem(String filePath) {
        HttpRequest.get(ctx).url(ServerInterface.saveIssueAbarbeitung)
                .params("handOverPart", handOverPart)//移交部位-房间或楼层 例：101/12层
                .params("siteName", siteName)//楼栋位置名称 例： 某标段-某栋-某单元
                .params("percentOfPass", percentOfPass)//合格率 例：100%
                //.params("scslCount",scslCount)//点数 例： 4
                .params("scslPicture", scslPictureData)//图片编辑 前端的问题
                .params("scslCorrelationPicture", filePath)//相关照片
                //.params("qualifiedStandard", qualifiedStandard)//合格标准 例子：截面尺寸[-5,8]
                .params("zhenggaiId", rectifyId)//整改人ID
                .params("zhenggaiPeople", rectifyName)//整改人
                //.params("zhenggaiPicture", filePath)//整改照片
                .params("zhenggaiExplain", UiUtils.getContent(etInstructContent))//整改说明
                .params("roomTowerFloorId", roomId)//房间/楼层的ID 例：
                .params("projectId", Constant.parentProjectId)//项目ID
                .params("projectName", Constant.parentProjectName)//项目名字 例子：名门紫苑
                .params("dikuaiId", Constant.projectId)//地块ID
                .params("dikuaiName", Constant.diKuaiName)//地块名称 例子：1#地块
                .params("sectionId", sectionId)//标段ID
                .params("sectionName", sectionName)//标段名称 例子： 测试标段
                .params("towerId", towerId)
                .params("towerName", towerName)
                .params("unitId", unitId)
                .params("unitName", unitName)
                .params("floorId", floorId)
                .params("floorName", floorName)
                .params("inspFuId", inspectionParentId)
                .params("inspectionId", inspectionId)//检查项子名称ID
                .params("inspectionName", inspectionName)//检查项名称 例子：混凝土工程
                .params("inspectionSunName", inspectionSunName)//检查项子名称 例子：截面尺寸
                .params("detailsName", inspectionName + inspectionSunName)//详情报表中的标段和检查项名称 例：主体工程-梁板综合预埋
                .params("partsDivision", partsDivision)//检查项子名称分类 例子：分户/不分单元-整层/分单元-整层
                .params("userAppTag", userType)//角色
                .params("scslType", "建设")//实测试量类型：监理，施工，建设
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                                //queryWorkDetail();
                                EventBus.getDefault().post("scslsuccess");
                                ctx.finish();
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    //完成整改
    private void completeZG(String filePath) {
        if (scslDataBean != null) {
            String fh = "";
            if (StringUtils.isEmpty(partsDivision)) {
                fh = scslDataBean.partsDivision;
            } else {
                fh = partsDivision;
            }
            HttpRequest.get(ctx).url(ServerInterface.updateAbarbeitungZhanggaiPeople)
                    .params("scslId", scslDataBean.scslId)
                    .params("roomTowerFloorId", scslDataBean.roomTowerFloorId)
                    .params("userAppTag", userInfo.getUserType())
                    .params("partsDivision", fh)
                    .params("zhenggaiPicture", filePath)
                    .params("zhenggaiExplain", UiUtils.getContent(etInstructContent))
                    .doPost(new HttpStringCallBack() {
                        @Override
                        public void onSuccess(Object result) {
                            try {
                                JSONObject object = new JSONObject(result.toString());
                                if (object.optInt("code") == 0) {
                                    ToastUtils.myToast(ctx, "整改成功");
                                    ctx.finish();
                                } else {
                                    ToastUtils.myToast(ctx, "整改失败");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                            ToastUtils.myToast(ctx, msg);
                        }
                    });
        }
    }

    //待办查询整改问题
    private void queryDaibanDetail() {
        HttpRequest.get(getContext()).url(ServerInterface.listIssueAbarbeitung)
                .params("id", "1")
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            LogUtils.logGGQ("scsl查询待办整改问题result:" + result.toString());
                            SCSLDefaultBean bean = new Gson().fromJson(result.toString(), SCSLDefaultBean.class);
                            if (bean != null) {
                                if (bean.getCode() == 0) {
                                    List<SCSLDefaultBean.ListBean> list = bean.getList();
                                    if (list != null && !list.isEmpty()) {
                                        SCSLDefaultBean.ListBean itemBean = list.get(0);
                                        if (itemBean != null) {
                                            String scSlState = itemBean.getScslState();
                                        }
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

    //修改信息
    private void updateZhanggaiPeople() {
        int scId;
        if (scslDataBean == null) {
            scId = 1;
        } else {
            scId = scslDataBean.scslId;
        }
        HttpRequest.get(getActivity()).url(ServerInterface.updateZhanggaiPeople)
                .params("scslId", scId)
                .params("zhenggaiId", rectifyId)
                .params("zhenggaiPeople", rectifyName)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(getActivity(), "修改成功!");
                                if (TextUtils.equals(identifying, "待整改")) {
                                    if (TextUtils.equals(UiUtils.getContent(tv_rectify_people), userInfo.getUserName())) {
                                        rl_zgPicInstruct.setVisibility(View.VISIBLE);
                                        tv_completeZG.setVisibility(View.VISIBLE);
                                    } else {
                                        rl_zgPicInstruct.setVisibility(View.GONE);
                                        tv_completeZG.setVisibility(View.GONE);
                                    }
                                }
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), "error==" + msg);
                    }
                });
    }

    //实测试量的检查完毕再添加整改人操作
    private void updateAbarbeitungZgPOperation() {
        int scslId;
        if (scslDataBean != null) {
            scslId = scslDataBean.scslId;
        } else {
            scslId = 0;
        }
        HttpRequest.get(ctx).url(ServerInterface.updateAbarbeitungZgPOperation)
                .params("scslId", scslId)
                .params("zhenggaiId", rectifyId)
                .params("zhenggaiPeople", rectifyName)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(getActivity(), "操作成功");
                                ctx.finish();
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            //return true;//WebView Brower
            return false;//System Brower
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //webView.loadUrl("javascript:(function(){" + "try{getHouseMap()}catch(e){}" + "})()");
            //webView.loadUrl("javascript:getHouseMap()");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

    };
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            int detailLength = etInstructContent.length();
            editorFontCount.setText(detailLength + "/200");
            if (detailLength == 200) {
                ToastUtils.myToast(ctx, "已输入达到200字界限了");
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (data != null) {
            LogUtils.i("requestCode==", requestCode + "");
            LogUtils.i("resultCode==", resultCode + "");
            if (resultCode == -1) {
                if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    jsSelectList.addAll(selectLi);
                    jsListAdapter.setNewData(jsSelectList);
                    //uploadFile(filePaths);
                }
            } else if (resultCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
                final Bundle bundle = data.getBundleExtra("bundle");
                if (isSubmit) {
                    rectifyId = bundle.getString("id");
                    rectifyName = bundle.getString("name");
                    setText(zhengGaiPeople, bundle.getString("name"));
                } else {
                    new CancelSureDialog(getActivity()).setOkClick("确认修改整改人吗？", new CancelSureDialog.OkClick() {
                        @Override
                        public void ok() {
                            setText(tv_rectify_people, bundle.getString("name"));
                            rectifyId = bundle.getString("id");
                            rectifyName = bundle.getString("name");
                            updateZhanggaiPeople();
                        }
                    });
                }
            }
        }
    }

//    private void uploadFile(List<File> filePaths) {
//        showLoadView("");
//        LogUtils.i("uploadFile==", ParameterMap.put("fileList", filePaths).toString());
//        HttpRequest.get(getActivity()).url(ServerInterface.uploadFile)
//                .params(ParameterMap.put("fileList", filePaths))
//                .doPost(new HttpStringCallBack() {
//                    @Override
//                    public void onSuccess(Object result) {
//                        hideLoadView();
//                        try {
//                            JSONObject object = new JSONObject(result.toString());
//                            if (object.optInt("code") == 0) {
//                                JSONArray imageArray = object.optJSONArray("fileName");
//                                if (arrayEmpty(imageArray)) {
//                                    for (int i = 0; i < imageArray.length(); i++) {
//                                        LocalMedia localMedia = new LocalMedia();
//                                        if (UiUtils.isEmpty(imageArray.optString(i))) {
//                                            fileList.add(imageArray.optString(i));
//                                            filePath = StringUtils.listToStrByChar(fileList, ',');
//                                            localMedia.setPath(ServerInterface.PVUrl() + imageArray.optString(i));
//                                            localMedia.setPictureType(PVAUtils.getFileLastType(ServerInterface.PVUrl() + imageArray.optString(i)));
//                                            sgSelectList.add(localMedia);
//                                        }
//                                    }
//                                    sgListAdapter.setNewData(sgSelectList);
//                                    sgListAdapter.notifyDataSetChanged();
//                                } else {
//                                    filePath = "";
//                                }
//                            } else {
//                                ToastUtils.myToast(getActivity(), object.optString("msg"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        hideLoadView();
//                        ToastUtils.myToast(getActivity(), msg);
//                    }
//                });
//    }

    private void getHtml(final String scSlId, final int roomId) {
        isDoH5 = false;
        HttpRequest.get(getActivity()).url(ServerInterface.selectHouseMapHtmlScslTwo)
                .params("roomId", roomId)
                .params("inspectionFuId", inspectionParentId)
                .params("inspectionSunId", inspectionId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("scsl-html5==", result.toString());
                        try {
                            JSONObject data = new JSONObject(result.toString());
                            if (data.optInt("code") == 0) {
                                JSONObject list = data.optJSONObject("list");
                                if (list != null) {
                                    String url = list.optString("defaultMap");
                                    LogUtils.print("hhh==" + url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&pId=" + scSlId);
                                    String h5LastUrl = url + "&cookie=" +
                                            StringUtils.H5Cookie(aCache.getAsString("cookie"))
                                            + "&pId=" + scSlId + "&type=" + userType +
                                            "&id=" + inspectionParentId;
                                    //String newUrl = h5LastUrl.trim().replace(" ", "");
                                    if (myWebView != null) {
                                        myWebView.loadUrl(h5LastUrl);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    //    private String dikuaiName = "";
//    private String imgHuUrl = "";
//    //展示户型图
//    private void getHtml() {
//        OkGoRequest.get(getActivity()).url(ServerInterface.selectHouseMapHtmlGxyjAD).params("roomId", roomId).doGet(new HttpStringCallBack() {
//            @Override
//            public void onSuccess(Object result) {
//                LogUtils.i("scsl-html5==", result.toString());
//                try {
//                    JSONObject data = new JSONObject(result.toString());
//                    if (data.optInt("code") == 0) {
//                        JSONObject list = data.optJSONObject("list");
//                        if (list != null) {
//                            dikuaiName = list.optString("houseName");
//                            String url = list.optString("defaultMap");
//                            int roomId = list.optInt("roomId");
//                            imgHuUrl = url;
//                            if (myWebView != null) {
//                                myWebView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")));
//                            }
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(int code, String msg) {
//
//            }
//        });
//    }
    //操作户型图  selectHouseMapHtmlGxyjAD 不带列表头
    //操作户型图  更换为带列表头 selectHouseMapHtmlScsl
    private String doH5Url = "";

    private void getDoHtml() {
        isDoH5 = true;
        HttpRequest.get(getActivity()).url(ServerInterface.selectHouseMapHtmlScsl).params("roomId", roomId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("scsl-html55==", result.toString());
                try {
                    JSONObject data = new JSONObject(result.toString());
                    if (data.optInt("code") == 0) {
                        JSONObject list = data.optJSONObject("list");
                        if (list != null) {
                            //dikuaiName = list.optString("houseName");
                            //int roomId = list.optInt("roomId");
                            String url = list.optString("defaultMap");
                            LogUtils.i("scsl-url5==", url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&pId="
                                    + inspectionId + "&id=" + inspectionParentId + "&sectionId=" + sectionId + "&type=" + userType);
                            doH5Url = url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&pId="
                                    + inspectionId + "&id=" +
                                    inspectionParentId + "&sectionId=" +
                                    sectionId + "&type=" + userType;
                            if (myWebView != null) {
                                myWebView.loadUrl(doH5Url);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
    }

    public void refreshHtml(String inspectionId) {
        showHtml(inspectionId, roomId);
    }

    private boolean isCheck = true;
    private boolean isSubmit = false;
    private boolean isDoH5 = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhengGaiPeople:
                isSubmit = true;
                SelectTRPOrAU.select(this, rectifyId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.tv_rectify_people:
                isSubmit = false;
                SelectTRPOrAU.select(this, rectifyId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.tv_complete_commit:
                //提交问题
                if (StringUtils.isEmpty(scslPictureData)) {
                    ToastUtils.myToast(ctx, "请点击户型图录入测量数据");
                } else {
                    new CancelSureDialog(ctx).setOkClick("确认提交检查？", new CancelSureDialog.OkClick() {
                        @Override
                        public void ok() {
                            UpLoadUtil.init(ctx, jsSelectList).Call(new UpLoadCallBack() {
                                @Override
                                public void onComplete(String paths) {
                                    AddZhengGaiProblem(paths);
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.ll_checkRemind:
                if (isCheck) {
                    checkRemind.setBackgroundResource(R.drawable.checked);
                    llZgLayout.setVisibility(View.VISIBLE);
                    remindZG.setVisibility(View.VISIBLE);
                    setText(zhengGaiPeople, rectifyName);
                    isCheck = false;
                } else {
                    checkRemind.setBackgroundResource(R.drawable.check_null);
                    llZgLayout.setVisibility(View.GONE);
                    remindZG.setVisibility(View.GONE);
                    setText(zhengGaiPeople, "");
                    isCheck = true;
                }
                break;
            case R.id.tv_completeZG://完成整改
                UpLoadUtil.init(ctx, jsSelectList).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        completeZG(paths);
                    }
                });
                break;
            case R.id.remindZG://通知整改
                new CancelSureDialog(ctx).setOkClick("确认通知整改？", new CancelSureDialog.OkClick() {
                    @Override
                    public void ok() {
                        updateAbarbeitungZgPOperation();
                    }
                });
                break;
            default:
                break;
        }
    }

    public void loadJSMethod(String inspectionIds) {
        LogUtils.print("inspectionIds===" + inspectionIds);
        LogUtils.print("doH5Url===" + doH5Url);
        inspectionId = inspectionIds;
        if (myWebView != null) {
            //myWebView.loadUrl("javascript:getMsg('" + inspectionId + "')");
            myWebView.loadUrl(doH5Url);
        }
    }

    protected void setListenerView(View view) {
        try {
            view.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String unitName, floorId, floorName, towerName;

    public void setSectionData(String sectionId, String sectionName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }

    public void setTowerUnitData(String towerId, String towerName, String unitId, String unitName) {
        this.towerId = towerId;
        this.towerName = towerName;
        this.unitId = unitId;
        this.unitName = unitName;
    }

    public void setFloorData(String floorId, String floorName) {
        this.floorId = floorId;
        this.floorName = floorName;
    }

    public void setInspectionData(String inspectionParentId, String inspectionName) {
        this.inspectionParentId = inspectionParentId;
        this.inspectionName = inspectionName;
    }

    public void setInspectionSunData(String inspectionId, String inspectionSunName, String partsDivision) {
        this.inspectionId = inspectionId;
        this.inspectionSunName = inspectionSunName;
        this.partsDivision = partsDivision;
    }

    public void setDetailsName(String detailsName) {
        this.detailsName = detailsName;
    }

    private void showHtml(String scSlId, int roomId) {
        if (userInfo.getUserType().equals("0") || userInfo.getUserType().equals("3")) {
            if (TextUtils.equals(jianSheStatus, "3")) {
                try {
                    ll_zgStatus.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getHtml(scSlId, roomId);
            } else {
                try {
                    ll_zgStatus.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getDoHtml();
            }
        }
    }

    public boolean isDoH5() {
        return isDoH5;
    }
}
