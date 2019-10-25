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
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListAdapter;
import com.haozhiyan.zhijian.bean.scsl.SCSLDefaultBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.PopWindowUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.haozhiyan.zhijian.widget.RoundWebView;
import com.haozhiyan.zhijian.zjInterface.AJSInterface;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/24.
 * Describe: 单位布局
 */
public class Jsdw_SCSlFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llRectifyPeople;
    private LinearLayout llRectifyImg;
    private LinearLayout llAboutImg;
    private RelativeLayout rlReadme;
    private ImageView ivCheckPeople;
    private ImageView ivRectifyPeople;
    private RoundWebView webView;
    private RecyclerView scSlRcv;//整改照片
    private EditText etInstructContent;
    private TextView editorFontCount;
    private NestedScrollView parentScrollView;
    private TextView tvScslState, tvPercentOfPass,
            tvScslCount, tvQualifiedStandard, tvInspectionPeople,
            tvCreatorTime, tvRectifyPeople, tvRectifyTime;
    private LinearLayout llRemindZG;//根据合格率来显示隐藏通知整改布局
    private CheckBox checkRemind;//选择是否整改-选择整改人
    private LinearLayout ll_zg_layout;//选择整改人布局
    private TextView zhengGaiPeople;//设置TextView之整改人
    //private Button remindZG;//整改按钮
    private WebSettings settings;
    private PictureOrVideoListAdapter adapter;
    private List<LocalMedia> selectList = new ArrayList<>();

    /**
     * type 1 施工，2 监理，3 建设
     */
    private int type;
    private String inspectionId = "", inspectionName = "", inspectionSunName = "", partsDivision = "分户";
    private String sectionId = "32", towerId = "1", unitId = "1", userType = "";
    private List<String> fileList = new ArrayList<>();
    private String filePath = "", roomId = "";
    private AJSInterface ajs;
    private ACache aCache;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_jsdw_scsl;
    }

    @Override
    public void initView(View view) {
        type = getArguments().getInt("type");
        inspectionId = getArguments().getString("inspectionId");
        inspectionName = getArguments().getString("inspectionName");
        inspectionSunName = getArguments().getString("inspectionSunName");
        partsDivision = getArguments().getString("partsDivision");
        sectionId = getArguments().getString("sectionId");
        towerId = getArguments().getString("towerId");
        unitId = getArguments().getString("unitId");
        roomId = getArguments().getString("roomId");
        userType = getArguments().getString("userType");

        LogUtils.logGGQ("实测实量详情--type:" + type);
        LogUtils.logGGQ("实测实量详情--inspectionId:" + inspectionId);
        LogUtils.logGGQ("实测实量详情--inspectionName:" + inspectionName);
        LogUtils.logGGQ("实测实量详情--inspectionSunName:" + inspectionSunName);
        LogUtils.logGGQ("实测实量详情--partsDivision:" + partsDivision);
        LogUtils.logGGQ("实测实量详情--sectionId:" + sectionId);
        LogUtils.logGGQ("实测实量详情--towerId:" + towerId);
        LogUtils.logGGQ("实测实量详情--roomId:" + unitId);
        LogUtils.logGGQ("实测实量详情--roomId:" + roomId);
        LogUtils.logGGQ("实测实量详情--userType:" + userType);

        webView = getOutView(view, R.id.webView);
        scSlRcv = getOutView(view, R.id.scSlRcv);
        etInstructContent = getOutView(view, R.id.et_instruct_content);
        editorFontCount = getOutView(view, R.id.id_editor_detail_font_count);
        parentScrollView = getOutView(view, R.id.parentScrollView);

        llRectifyPeople = getOutView(view, R.id.ll_rectify_people);
        llRectifyImg = getOutView(view, R.id.ll_rectify_img);
        rlReadme = getOutView(view, R.id.rl_readme);
        llAboutImg = getOutView(view, R.id.ll_about_img);
        tvRectifyTime = getOutView(view, R.id.tv_rectify_time);
        ivCheckPeople = getOutView(view, R.id.iv_check_people);
        ivRectifyPeople = getOutView(view, R.id.iv_rectify_people);
        tvScslState = getOutView(view, R.id.tv_scsl_state);
        //tvPercentOfPass = getOutView(view, R.id.tv_percent_of_pass);
        //tvScslCount = getOutView(view, R.id.tv_scslCount);
        //tvQualifiedStandard = getOutView(view, R.id.tv_qualifiedStandard);
        tvInspectionPeople = getOutView(view, R.id.tv_inspectionPeople);
        tvCreatorTime = getOutView(view, R.id.tv_CreatorTime);
        tvRectifyPeople = getOutView(view, R.id.tv_rectify_people);
        llRemindZG = getOutView(view, R.id.ll_remindZG);
        checkRemind = getOutView(view, R.id.checkRemind);
        ll_zg_layout = getOutView(view, R.id.ll_zg_layout);
        zhengGaiPeople = getOutView(view, R.id.zhengGaiPeople);
        aCache = ACache.get(ctx, "cookie");
    }

    @Override
    public void initListener() {
        etInstructContent.addTextChangedListener(textWatcher);
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
        webView.setParentScrollView(parentScrollView);
        ajs = AJSInterface.getInstance(ctx, webView);
        addJSInterface(webView);
        initPhoto();
        //检查人
        ivCheckPeople.setOnClickListener(this);
        //整改人
        ivRectifyPeople.setOnClickListener(this);
        checkRemind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ll_zg_layout.setVisibility(View.VISIBLE);
                } else {
                    ll_zg_layout.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void initData(boolean isNetWork) {
        //查询整改问题
        //getDefaultData();
        //获取户型图
        getHtml();
    }

    @JavascriptInterface
    public void addJSInterface(WebView mWebView) {
        //和js约定,包括调用类的对象,以及起的别名
        mWebView.addJavascriptInterface(ajs, ajs.getInterface());
    }

    //查询整改问题
    private void getDefaultData() {
        HttpRequest.get(getContext()).url(ServerInterface.listIssueAbarbeitung)
                .params("id", "1")
                .params("roomTowerFloorId", "1")
                .params("dikuaiId", Constant.projectId)
                .params("sectionId", sectionId)
                .params("towerId", towerId)
                .params("unitId", unitId)
                .params("inspectionName", inspectionName)
                .params("inspectionSunName", inspectionSunName)
                .params("partsDivision", partsDivision)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            LogUtils.logGGQ("scsl查询整改问题result:" + result.toString());
                            SCSLDefaultBean bean = new Gson().fromJson(result.toString(), SCSLDefaultBean.class);
                            if (bean != null) {
                                if (bean.getCode() == 0) {
                                    List<SCSLDefaultBean.ListBean> list = bean.getList();
                                    if (list != null && !list.isEmpty()) {
                                        SCSLDefaultBean.ListBean itemBean = list.get(0);
                                        if (itemBean != null) {
                                            String scslState = itemBean.getScslState();
                                            if (!TextUtils.isEmpty(scslState)) {
                                                tvScslState.setText(scslState);
                                                //tvPercentOfPass.setText(itemBean.getPercentOfPass());
                                                //tvScslCount.setText(itemBean.getScslCount());
                                                //tvQualifiedStandard.setText(itemBean.getQualifiedStandard());
                                                tvInspectionPeople.setText(itemBean.getInspectionPeople());
                                                tvCreatorTime.setText(itemBean.getCreatorTime());
                                                if (type == 1) { // 施工
                                                    llRectifyPeople.setVisibility(View.VISIBLE);
                                                    llRectifyImg.setVisibility(View.VISIBLE);
                                                    rlReadme.setVisibility(View.VISIBLE);
                                                    llAboutImg.setVisibility(View.VISIBLE);

                                                } else if (type == 2) { //監理
                                                    llRectifyPeople.setVisibility(View.VISIBLE);
                                                    llRectifyImg.setVisibility(View.VISIBLE);
                                                    rlReadme.setVisibility(View.VISIBLE);
                                                    llAboutImg.setVisibility(View.VISIBLE);

                                                } else {  //建设
                                                    llRectifyPeople.setVisibility(View.GONE);
                                                    llRectifyImg.setVisibility(View.GONE);
                                                    rlReadme.setVisibility(View.GONE);
                                                    llAboutImg.setVisibility(View.GONE);
                                                    if (TextUtils.equals("待整改", scslState)) {
                                                        tvRectifyTime.setVisibility(View.GONE);
                                                        llRectifyPeople.setVisibility(View.VISIBLE);
                                                        tvRectifyPeople.setText(itemBean.getZhenggaiPeople());
                                                    } else if (TextUtils.equals("已整改", scslState)) {
                                                        tvRectifyTime.setVisibility(View.VISIBLE);
                                                        llRectifyPeople.setVisibility(View.VISIBLE);
                                                        tvRectifyPeople.setText(itemBean.getZhenggaiPeople());
                                                        tvRectifyTime.setText(itemBean.getZhenggaiTime());
                                                    } else if (TextUtils.equals("检查完毕", scslState)) {
                                                        tvRectifyTime.setVisibility(View.GONE);
                                                        llRectifyPeople.setVisibility(View.VISIBLE);
                                                        tvRectifyPeople.setText(itemBean.getZhenggaiPeople());
                                                    }
                                                }
                                            }
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

    @Override
    protected void lazyLoad() {
    }


    private void initPhoto() {
        scSlRcv.setLayoutManager(new GridLayoutManager(ctx, 3, LinearLayoutManager.VERTICAL, false));
        scSlRcv.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));
        scSlRcv.setNestedScrollingEnabled(true);
        adapter = new PictureOrVideoListAdapter(getActivity());
        PhotoCameraUtils.init(this).photoCameraListAdapter(adapter, scSlRcv, selectList, 0);
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

    public void setPicData(List<LocalMedia> selectLi) {
        selectList.addAll(selectLi);
        adapter.setNewData(selectList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            LogUtils.i("requestCode==", requestCode + "");
            LogUtils.i("resultCode==", resultCode + "");
            if (resultCode == -1) {
                if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    adapter.notifyDataSetChanged();
                    List<File> filePaths = new ArrayList<>();
                    for (int i = 0; i < selectLi.size(); i++) {
                        filePaths.add(new File(selectLi.get(i).getPath()));
                    }
                    uploadFile(filePaths);
                }
            } else if (resultCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
                Bundle bundle = data.getBundleExtra("bundle");
                zhengGaiPeople.setText(bundle.getString("name"));
            }
        }
    }

    private void uploadFile(List<File> filePaths) {
        showLoadView("");
        LogUtils.i("uploadFile==", ParameterMap.put("fileList", filePaths).toString());
        HttpRequest.get(getActivity()).url(ServerInterface.uploadFile).params(ParameterMap.put("fileList", filePaths)).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoadView();
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            for (int i = 0; i < imageArray.length(); i++) {
                                if (UiUtils.isEmpty(imageArray.optString(i))) {
                                    fileList.add(imageArray.optString(i));
                                    filePath = StringUtils.listToStrByChar(fileList, ',');
                                }
                            }
                        } else {
                            filePath = "";
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
                hideLoadView();
                ToastUtils.myToast(getActivity(), msg);
            }
        });
    }

    private String dikuaiName = "";

    private void getHtml() {
        HttpRequest.get(getActivity()).url(ServerInterface.selectHouseMapHtmlScsl).params("roomId", "1").doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("scsl-html5==", result.toString());
                try {
                    JSONObject data = new JSONObject(result.toString());
                    if (data.optInt("code") == 0) {
                        JSONObject list = data.optJSONObject("list");
                        if (list != null) {
                            dikuaiName = list.optString("houseName");
                            String url = list.optString("defaultMap");
                            int roomId = list.optInt("roomId");
                            webView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")));
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

    public void refreshHtml(String roomIds) {
        roomId = roomIds;
        getHtml();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhengGaiPeople:
                SelectTRPOrAU.select(this, UiUtils.getContent(zhengGaiPeople), SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.iv_check_people:
                PopWindowUtils.getPopWindow().showPopWindow(getActivity(), ivCheckPeople, 0, 10);
                PopWindowUtils.getPopWindow().setClickListener(new PopWindowUtils.OnClickButtonListener() {
                    @Override
                    public void chatSay() {

                    }

                    @Override
                    public void callPhone() {
                        SystemUtils.callPage("10086", getContext());
                    }
                });
                break;
            case R.id.iv_rectify_people:
                PopWindowUtils.getPopWindow().showPopWindow(getActivity(), ivRectifyPeople, 0, 10);
                PopWindowUtils.getPopWindow().setClickListener(new PopWindowUtils.OnClickButtonListener() {
                    @Override
                    public void chatSay() {

                    }

                    @Override
                    public void callPhone() {
                        SystemUtils.callPage("10086", getContext());
                    }
                });
                break;
        }
    }

    public void loadJSMethod(String sectionId){
        webView.loadUrl("javascript:getMsg('" + sectionId + "')");
    }
}
