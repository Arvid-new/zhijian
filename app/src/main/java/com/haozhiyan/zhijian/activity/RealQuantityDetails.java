package com.haozhiyan.zhijian.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.PictureVideoShowAdapter;
import com.haozhiyan.zhijian.bean.scsl.SCSLDBDetailBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.PopWindowUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.haozhiyan.zhijian.widget.CancelSureDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 代办- 条目  实测实量 详情
 */
public class RealQuantityDetails extends BaseActivity2 implements View.OnClickListener {

    private String type;//待办 已办状态
    private String state;//待整改 待复验  已通过状态
    private RecyclerView selectPicRCV;
    private NestedScrollView parentScrollView;
    private RecyclerView showPicRcv;
    private EditText etInstructContent;
    private TextView editorFontCount;
    private LinearLayout zhengGaiPeopleLL;
    private ImageView iv_zgSelected;
    private LinearLayout zhengGaiPeopleLL2;
    private LinearLayout ll_remindZG;//通知整改布局
    private LinearLayout ll_checkRemind;
    private LinearLayout ll_zhengGaiLayout;
    //private TextView tv_dzgBdName;
    //private TextView tv_dzgTFR;
    //private TextView tv_jcxText;
    //private TextView tv_jcxChildText;
    //private TextView tv_percentOfPass;
    // private TextView tv_pointNum;
    private TextView tv_pass_rule;
    private TextView jianChaName;
    private TextView zhengGaiName;
    private TextView zhengGaiName2;
    private Button remindZG;
    //private View viewLine;
    //private TextView tv_ZhengGaiStatus;
    private TextView zhengGaiInstruct;
    private CheckBox checkRemind;
    //private TextView zhengGaiPeople;
    //private PictureOrVideoListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private PictureVideoShowAdapter picShowAdapter;
    private Button completeZG;
    //private RoundWebView myWebView;
    private WebView myWebView;
    private WebSettings settings;
    private List<LocalMedia> zhenGaiPics = new ArrayList<>();
    private List<LocalMedia> xiangGuanPics = new ArrayList<>();
    private TextView time;
    private String title = "", checkGuideContent = "暂无说明", zhengGaiId = "", zhengGaiPerson = "", inspectionPeople = "", scSlId = "";
    private String id = "", rTFloorId = "", sectionId = "", towerId = "", unitId = "", inspectionName = "", inspectionSunName = "", partsDivision = "";
    private PopWindowUtils popWindowUtils;
    private ACache aCache;
    private UserInfo userInfo;
    private SCSLDBDetailBean.ListBean listBean;
    private List<LocalMedia> selectList = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        type = getIntent().getExtras().getString("type", "");
        state = getIntent().getExtras().getString("state", "");
        id = getIntent().getExtras().getString("id", "");
        title = getIntent().getExtras().getString("title", "");
        scSlId = getIntent().getExtras().getString("scslId", "");
        rTFloorId = getIntent().getExtras().getString("roomTowerFloorId", "");
        aCache = ACache.get(this, "cookie");
        userInfo = UserInfo.create(this);
        LogUtils.i("实测实量详情type===", type);
        LogUtils.i("实测实量详情state===", state);

    }

    @Override
    protected int setLayoutResourceID() {
        switch (type) {

            case "0"://代办
                if (state.equals("待整改")) {//待整改
                    return R.layout.the_measured_real_quantity;
                    //return R.layout.the_dai_zheng_gai_jianshe;//建设单位
                } else if (state.equals("待复验")) {//待复验
                    return R.layout.activity_dai_ban_fuyan_details;
                } else if (state.equals("已整改")) {//已整改
//                    return R.layout.activity_dai_ban_fuyan_details;
                    return R.layout.the_scsl_yi_zheng_gai;
                } else if (state.equals("检查完毕")) {
//                    return R.layout.activity_dai_ban_details;
                    return R.layout.the_scsl_jc_complete;
                }
            case "1"://已办
                if (state.equals("待整改")) {//待整改
                    // return R.layout.activity_dai_ban_ybdg_details;
                    return R.layout.the_measured_real_quantity;
                } else if (state.equals("待复验")) {//待复验
                    return R.layout.activity_dai_ban_details_yiban;
                } else if (state.equals("已整改")) {//已整改
//                    return R.layout.activity_dai_ban_details_yiban;
                    return R.layout.the_scsl_yi_zheng_gai;
                } else if (state.equals("检查完毕")) {
//                    return R.layout.activity_dai_ban_details;
                    return R.layout.the_scsl_jc_complete;
                }
            case "2"://抄送
                return R.layout.activity_dai_ban_details_chaosong;
            default:
                return R.layout.the_measured_real_quantity;
        }
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        setTitleText(title);
        setAndroidNativeLightStatusBar(true);
        setTitleRightmenu();
        setStatusBarColor(R.color.white);
        popWindowUtils = PopWindowUtils.getPopWindow();
        parentScrollView = getOutView(R.id.parentScrollView);
        myWebView = getOutView(R.id.myWebView);
        etInstructContent = getOutView(R.id.et_instruct_content);
        editorFontCount = getOutView(R.id.id_editor_detail_font_count);
        zhengGaiPeopleLL = getOutView(R.id.zhengGaiPeopleLL);

        //tv_dzgBdName = getOutView(R.id.tv_dzgBdName);
        // tv_dzgTFR = getOutView(R.id.tv_dzgTFR);
        // tv_jcxText = getOutView(R.id.tv_jcxText);
        // tv_jcxChildText = getOutView(R.id.tv_jcxChildText);
        // tv_ZhengGaiStatus = getOutView(R.id.tv_ZhengGaiStatus);
        // tv_percentOfPass = getOutView(R.id.tv_percentOfPass);
        // viewLine = getOutView(R.id.viewLine);
        showPicRcv = getOutView(R.id.XGselectPicRCV);
        //tv_pointNum = getOutView(R.id.tv_pointNum);
        tv_pass_rule = getOutView(R.id.tv_pass_rule);
        jianChaName = getOutView(R.id.jianChaName);
        zhengGaiName = getOutView(R.id.zhengGaiName);
        selectPicRCV = getOutView(R.id.ZGselectPicRCV);
        time = getOutView(R.id.addTime);
        zhengGaiInstruct = getOutView(R.id.zhengGaiInstruct);
        ll_zhengGaiLayout = getOutView(R.id.ll_zhengGaiLayout);
        completeZG = getOutView(R.id.completeZG);
        iv_zgSelected = getOutView(R.id.iv_zgSelected);
        initListener();
        try {
            setShowPicListRcv(showPicRcv, xiangGuanPics);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (state.equals("待整改")) {//待整改
                setPictureSelectRcvData(selectPicRCV, zhenGaiPics);
                setListenerView(zhengGaiPeopleLL);
                setListenerView(completeZG);
            } else if (state.equals("待复验")) {//待复验
                setPictureSelectRcvData(selectPicRCV, zhenGaiPics);
                setListenerView(zhengGaiPeopleLL);
                setListenerView(completeZG);
            } else if (state.equals("已整改")) {//已整改
                setShowPicListRcv(selectPicRCV, zhenGaiPics);
            } else if (state.equals("检查完毕")) {
                zhengGaiPeopleLL2 = getOutView(R.id.zhengGaiPeopleLL2);
                ll_remindZG = getOutView(R.id.ll_remindZG);
                checkRemind = getOutView(R.id.checkRemind);
                ll_checkRemind = getOutView(R.id.ll_checkRemind);
                zhengGaiName2 = getOutView(R.id.zhengGaiName2);
                remindZG = getOutView(R.id.remindZG);
                setListenerView(zhengGaiPeopleLL2);
                setListenerView(ll_checkRemind);
                setListenerView(remindZG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initListener() {
        try {
            etInstructContent.addTextChangedListener(textWatcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initWebView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initData() {
        queryDetail();
    }

    private void initWebView() {
        settings = myWebView.getSettings();
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
        myWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });
        myWebView.setWebViewClient(webViewClient);
        //myWebView.setWebChromeClient(webChromeClient);
        //myWebView.loadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1558608404&di=51c636058fbf51dac203a4d37a597d71&src=http://img2sz.centainfo.com/images/20180410/110937_49fa5ae5-f1a8-48b8-9a88-c2fbcd5ae6e9_360x260_c.jpg");
    }

    private void setTitleRightmenu() {

        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dp40px, dp40px);
        ImageView close = new ImageView(getContext());
        close.setImageResource(R.mipmap.close_icon);
        close.setLayoutParams(layoutParams);
        close.setPadding(dp10px, 0, dp10px, 0);
        final ImageView checktip = new ImageView(getContext());
        checktip.setPadding(dp10px, 0, dp10px, 0);
        checktip.setLayoutParams(layoutParams);
        checktip.setImageResource(R.mipmap.msg_tip_icon);
        getBarLeftView().addView(close, 1);
        getBarRightView().addView(checktip);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
        checktip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWindowUtils.showJCzy(getActivity(), checktip, -50, 15, inspectionName + "-" + inspectionSunName, checkGuideContent);
            }
        });

    }

    private boolean isCheck = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.completeZG://完成整改
                UpLoadUtil.init(this, selectList).Call(new UpLoadCallBack() {
                    @Override
                    public void onComplete(String paths) {
                        completeZhengGai(paths);
                    }
                });
                break;
            case R.id.zhengGaiPeopleLL://选择整改人
                SelectTRPOrAU.select(this, zhengGaiId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.zhengGaiPeopleLL2://检查完毕后选择整改人
                SelectTRPOrAU.select(this, zhengGaiId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.remindZG://通知整改
                updateAbarbeitungZgPOperation();
                break;
            case R.id.ll_checkRemind:
                if (isCheck) {
                    checkRemind.setBackgroundResource(R.drawable.checked);
                    zhengGaiPeopleLL2.setVisibility(View.VISIBLE);
                    isCheck = false;
                } else {
                    checkRemind.setBackgroundResource(R.drawable.check_null);
                    zhengGaiPeopleLL2.setVisibility(View.GONE);
                    isCheck = true;
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }

        if (requestCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
            final String zhengairen = data.getBundleExtra("bundle").getString("name");
            final String id = data.getBundleExtra("bundle").getString("id");
            zhengGaiId = id;
            zhengGaiPerson = zhengairen;
            if (state.equals("检查完毕")) {
                setText(zhengGaiName2, zhengGaiPerson);
            } else {
                new CancelSureDialog(this).setOkClick("确定要修改整改人吗？", new CancelSureDialog.OkClick() {
                    @Override
                    public void ok() {
                        try {
                            updateZhanggaiPeople(zhengairen, id);
                        } catch (Exception e) {
                            e.printStackTrace();

                        }
                    }
                });
            }
        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    break;
                default:
                    break;
            }
        }
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    };

    /**
     * 设置 选择照片的那个 RCV 控件设置数据 可复用
     */
    private void setPictureSelectRcvData(RecyclerView rlv, List<LocalMedia> list) {
        rlv.setLayoutManager(new GridLayoutManager(getContext(), 3, LinearLayoutManager.VERTICAL, false));
        rlv.setNestedScrollingEnabled(true);
        adapter = new PictureOrVideoListNewAdapter(getContext());
        //PhotoCameraUtils.init(this).photoCameraListAdapter(adapter, rlv, list);
        PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, rlv, list, 1);
    }


    /**
     * 展示照片列表 仅展示图片  能复用
     */
    private void setShowPicListRcv(RecyclerView rlv, List<LocalMedia> list) {
        try {
            rlv.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
            picShowAdapter = new PictureVideoShowAdapter(getContext());
            PhotoCameraUtils.init(this).photoCameraAdapter(picShowAdapter, rlv, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
                ToastUtils.myToast(getActivity(), R.string.string_twoBai_end);
            }
        }
    };

    //查询详情
    private void queryDetail() {
        HttpRequest.get(this).url(ServerInterface.listIssueAbarbeitung)
                .params("id", id)
                .params("roomTowerFloorId", rTFloorId)//房间，楼层，楼栋的主键ID
//                .params("dikuaiId", Constant.projectId)
//                .params("sectionId", sectionId)
//                .params("towerId", towerId)
//                .params("unitId", unitId)
//                .params("inspectionName", inspectionName)
//                .params("inspectionSunName", inspectionSunName)
//                .params("partsDivision", partsDivision)//检查项子名称分类(分户-分单元)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        SCSLDBDetailBean bean = null;
                        try {
                            bean = new Gson().fromJson(result.toString(), SCSLDBDetailBean.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (bean == null) {
                            return;
                        }
                        LogUtils.i("实测实量详情===", result.toString());
                        if (bean.code == 0) {
                            if (bean.list != null) {
                                listBean = bean.list;
                                  /*  if (listBean.scslState.equals("待整改")) {
                                        viewLine.setBackgroundColor(UiUtils.getColor(R.color.red2));
                                    } else if (listBean.scslState.equals("已整改")) {
                                        viewLine.setBackgroundColor(UiUtils.getColor(R.color.blue));
                                    } else if (listBean.scslState.equals("检查完毕")) {
                                        viewLine.setBackgroundColor(UiUtils.getColor(R.color.green2));
                                    } else if (listBean.scslState.equals("待复验")) {
                                        viewLine.setBackgroundColor(UiUtils.getColor(R.color.yellow2));
                                    }*/

                                // setText(tv_ZhengGaiStatus, listBean.scslState);
                                // setText(tv_dzgBdName, listBean.sectionName);
                                // setText(tv_dzgTFR, listBean.towerName + listBean.unitName + listBean.floorName + listBean.handOverPart);
                                // setText(tv_jcxText, listBean.inspectionName);
                                // setText(tv_jcxChildText, listBean.inspectionSunName);
                                //setText(tv_percentOfPass, listBean.percentOfPass);
                                if (StringUtils.isEmpty(scSlId)) {
                                    scSlId = listBean.scslId + "";
                                }
                                // setText(tv_pointNum, listBean.scslCount);
                                setText(tv_pass_rule, listBean.qualifiedStandard);
                                setText(jianChaName, listBean.inspectionPeople);
                                setText(zhengGaiName, listBean.zhenggaiPeople);
                                zhengGaiId = listBean.zhenggaiId + "";
                                zhengGaiPerson = listBean.zhenggaiPeople;
                                inspectionPeople = listBean.inspectionPeople;
                               /* try {
                                    if(!TextUtils.equals(zhengGaiPerson,inspectionPeople)){
                                        zhengGaiPeopleLL.setEnabled(false);
                                        iv_zgSelected.setVisibility(View.INVISIBLE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }*/
                                try {
                                    if (listBean.zhenggaiPeople.equals(userInfo.getUserName())) {
                                        ll_zhengGaiLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        ll_zhengGaiLayout.setVisibility(View.GONE);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                setText(time, listBean.creatorTime);
                                setText(zhengGaiInstruct, listBean.zhenggaiExplain);
                                inspectionName = listBean.inspectionName;
                                inspectionSunName = listBean.inspectionSunName;
                                //相关图片
                                if (ListUtils.listEmpty(listBean.xiangGuanPV)) {
                                    for (String path : listBean.xiangGuanPV) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        xiangGuanPics.add(localMedia);
                                    }
                                    try {
                                        picShowAdapter.setNewData(xiangGuanPics);
                                        picShowAdapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                              /*  if (UiUtils.isEmpty(listBean.scslCorrelationPicture)) {
                                    String[] strs = listBean.scslCorrelationPicture.split(",");
                                    for (int i = 0; i < strs.length; i++) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(strs[i]);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(strs[i]));
                                        xiangGuanPics.add(localMedia);
                                    }
                                    try {
                                        picShowAdapter.setNewData(xiangGuanPics);
                                        picShowAdapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }*/
                                //整改照片
                                if (ListUtils.listEmpty(listBean.zhengGaiPV)) {
                                    for (String path : listBean.zhengGaiPV) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        zhenGaiPics.add(localMedia);
                                    }
                                    try {
                                        adapter.setNewData(zhenGaiPics);
                                        adapter.notifyDataSetChanged();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
//                                if (!listBean.scslState.equals("待整改")) {
//                                    if (UiUtils.isEmpty(listBean.zhenggaiPicture)) {
//                                        String[] strs = listBean.zhenggaiPicture.split(",");
//                                        for (int i = 0; i < strs.length; i++) {
//                                            LocalMedia localMedia = new LocalMedia();
//                                            localMedia.setPath(strs[i]);
//                                            localMedia.setPictureType(PVAUtils.getFileLastType(strs[i]));
//                                            zhenGaiPics.add(localMedia);
//                                        }
//                                        try {
//                                            picShowAdapter.setNewData(zhenGaiPics);
//                                            picShowAdapter.notifyDataSetChanged();
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//                                    }
//                                }
                                //获取检查项详情
                                getJCXDetail(listBean.inspectionId);
                                if (myWebView != null) {
                                    initWebView();
                                }
                                //获取户型图
                                getHtml(listBean.scslId + "",
                                        listBean.roomTowerFloorId, listBean.inspFuId,
                                        listBean.inspectionId);
                            }
                        } else {
                            ToastUtils.myToast(getApplicationContext(), bean.msg);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    //查询检查项详情
    private void getJCXDetail(String inspectionId) {
        HttpRequest.get(this).url(ServerInterface.selectSclsIssueGuidelines).params("inspectionId", inspectionId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONObject checkGuide = object.optJSONObject("list");
                        if (checkGuide != null) {
                            checkGuideContent = checkGuide.optString("checkGuide");
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

    /**
     * 完成整改
     */
    private void completeZhengGai(String filePath) {
        HttpRequest.get(this).url(ServerInterface.updateAbarbeitungZhanggaiPeople)
                .params("scslId", listBean.scslId)
                .params("userAppTag", listBean.userAppTag)
                .params("scslType", listBean.scslType)
                .params("detailsName", listBean.detailsName)
                .params("handOverPart", listBean.handOverPart)
                .params("siteName", listBean.siteName)
                .params("scslState", listBean.scslState)
                .params("percentOfPass", listBean.percentOfPass)
                .params("scslCount", listBean.scslCount)
                .params("scslPicture", listBean.scslPicture)
                .params("scslCorrelationPicture", listBean.scslCorrelationPicture)
                .params("qualifiedStandard", listBean.qualifiedStandard)
                .params("inspectionPeopleId", listBean.inspectionPeopleId)
                .params("inspectionPeople", listBean.inspectionPeople)
                .params("inspectionState", listBean.inspectionState)
                .params("zhenggaiId", zhengGaiId)
                .params("zhenggaiPeople", zhengGaiPerson)
                .params("zhenggaiPicture", filePath)
                .params("zhenggaiExplain", UiUtils.getContent(etInstructContent))
                .params("zhenggaiState", listBean.zhenggaiState)
                .params("zhenggaiTime", listBean.zhenggaiTime)
                .params("roomTowerFloorId", listBean.roomTowerFloorId)
                .params("projectId", listBean.projectId)
                .params("projectName", listBean.projectName)
                .params("dikuaiId", listBean.dikuaiId)
                .params("dikuaiName", listBean.dikuaiName)
                .params("sectionId", listBean.sectionId)
                .params("sectionName", listBean.sectionName)
                .params("towerId", listBean.towerId)
                .params("towerName", listBean.towerName)
                .params("unitId", listBean.unitId)
                .params("unitName", listBean.unitName)
                .params("floorId", listBean.floorId)
                .params("floorName", listBean.floorName)
                .params("inspectionId", listBean.inspectionId)
                .params("inspectionName", listBean.inspectionName)
                .params("inspectionSunName", listBean.inspectionSunName)
                .params("partsDivision", listBean.partsDivision)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(getActivity(), "整改成功");
                                EventBus.getDefault().post("zhengGaiChanged");
                                finish();
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

    //实测试量修改整改人
    private void updateZhanggaiPeople(final String zhengGaiPerson, String id) {
        HttpRequest.get(this).url(ServerInterface.updateZhanggaiPeople)
                .params("scslId", scSlId)
                .params("zhenggaiId", id)
                .params("zhenggaiPeople", zhengGaiPerson)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                setText(zhengGaiName, zhengGaiPerson);
                                if (zhengGaiPerson.equals(userInfo.getUserName())) {
                                    ll_zhengGaiLayout.setVisibility(View.VISIBLE);
                                } else {
                                    ll_zhengGaiLayout.setVisibility(View.GONE);
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
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    //实测试量的检查完毕再添加整改人操作
    private void updateAbarbeitungZgPOperation() {
        HttpRequest.get(this).url(ServerInterface.updateAbarbeitungZgPOperation)
                .params("scslId", scSlId)
                .params("zhenggaiId", zhengGaiId)
                .params("zhenggaiPeople", zhengGaiPerson)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(getActivity(), "操作成功");
                                finish();
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

    /**
     * 展示户型图
     */
    private void getHtml(final String scSlId, final int roomId, final String inspectionFuId,final String inspectionSunId) {
        HttpRequest.get(getActivity()).url(ServerInterface.selectHouseMapHtmlScslTwo)
                .params("roomId", roomId)
                .params("inspectionFuId", inspectionFuId)
                .params("inspectionSunId", inspectionSunId)
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
                                    LogUtils.print("hhh==" + url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&pId=" + scSlId + "&type=" + userInfo.getUserType() + "&id=" + inspectionFuId);
                                    String h5LastUrl = url + "&cookie=" +
                                            StringUtils.H5Cookie(aCache.getAsString("cookie"))
                                            + "&pId=" + inspectionSunId + "&type=" +
                                            userInfo.getUserType() + "&id=" + inspectionFuId;
                                    String newUrl = h5LastUrl.trim().replace(" ", "");
                                    if (myWebView != null) {
                                        myWebView.loadUrl(newUrl);
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
}
