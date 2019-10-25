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
import android.widget.EditText;
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
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
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
public class JsDw_New_SCSlFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout rlParent;//父布局
    private NestedScrollView parentScrollView;
    private RoundWebView myWebView;
    private RecyclerView rvAboutImg;
    private TextView tv_complete_commit;
    private View view_Line;//左侧状态条
    private TextView tv_scSlState;//状态文本
    private TextView tv_inspectionPeople;//检查人
    private TextView tv_CreatorTime;//问题创建时间-检查人检查时间
    private TextView tv_rectify_people;//整改人
    private TextView tv_rectify_time;//整改时间
    private RecyclerView scSlZgRcv;//整改照片
    private EditText etInstructContent;
    private RelativeLayout rl_zgPicInstruct;
    private TextView editorFontCount;
    private WebSettings settings;
    private PictureOrVideoListNewAdapter sgListAdapter, jlListAdapter, jsListAdapter, submitAdapter;
    private PictureVideoShowAdapter showAdapter;
    private List<LocalMedia> sgSelectList = new ArrayList<>();
    private List<LocalMedia> jlSelectList = new ArrayList<>();
    private List<LocalMedia> jsSelectList = new ArrayList<>();
    private List<LocalMedia> submitSelectList = new ArrayList<>();
    private List<LocalMedia> showList = new ArrayList<>();
    /**
     * type  0admin,  1施工， 2监理， 3建设
     */
    private int type;
    private String inspectionId = "", inspectionName = "", inspectionSunName = "", partsDivision = "分户", filePath = "";
    private String sectionId = "32", towerId = "1", unitId = "1", userType = "", identifying = "空";
    private String detailId = "", entrance = "", rectifyId = "", id = "", rectifyName = "", pageType = "";
    private int roomId = 1;
    private List<String> fileList = new ArrayList<>();
    private AJSInterface ajs;
    private ACache aCache;
    private View statusLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_jsdw_scsl_new;
    }

    @Override
    public void initView(View view) {
        rlParent = getOutView(view, R.id.rl_parent);
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
            //getDefaultData();
            //获取户型图
            getHtml();
        } else {
            ToastUtils.myToast(getActivity(), "网络不太好哦，检查一下网络");
        }
    }

    @Override
    protected void lazyLoad() {
    }

    private void initIntent() {

        aCache = ACache.get(ctx, "cookie");
        type = getArguments().getInt("type");
        inspectionId = getArguments().getString("inspectionId");
        inspectionName = getArguments().getString("inspectionName");
        inspectionSunName = getArguments().getString("inspectionSunName");
        partsDivision = getArguments().getString("partsDivision");
        sectionId = getArguments().getString("sectionId");
        towerId = getArguments().getString("towerId");
        unitId = getArguments().getString("unitId");
        roomId = getArguments().getInt("roomId");
        userType = getArguments().getString("userType");
        entrance = getArguments().getString("entrance");
        pageType = getArguments().getString("pageType");

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
        LogUtils.logGGQ("实测实量详情--identifying:" + identifying);

        if (entrance.equals("work")) {
            queryWorkDetail();
        } else {
            queryDaibanDetail();
        }

    }

    private void initLayoutShow(String identifying) {
        switch (type) {
            case 0://admin
                statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_submit_layout, null);
                rlParent.removeAllViews();
                rlParent.addView(statusLayout);
//                if (TextUtils.equals(identifying, "空")) {
//
//                } else if (TextUtils.equals(identifying, "待整改")) {
//
//                } else if (TextUtils.equals(identifying, "待复验")) {
//
//                } else if (TextUtils.equals(identifying, "已通过")) {
//
//                } else {
//
//                }
                break;
            case 1://施工
                if (TextUtils.equals(identifying, "空")) {
                    //施工提交检查数据布局
                    statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_submit_layout, null);
                    rlParent.removeAllViews();
                    rlParent.addView(statusLayout);
                } else if (TextUtils.equals(identifying, "待整改")) {
                    //施工待整改布局
                    statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_dzg_layout, null);
                    rlParent.removeAllViews();
                    rlParent.addView(statusLayout);
                } else if (TextUtils.equals(identifying, "待复验")) {

                } else if (TextUtils.equals(identifying, "已通过")) {

                } else {
                    statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_submit_layout, null);
                    rlParent.removeAllViews();
                    rlParent.addView(statusLayout);
                }
                break;
            case 2://监理
                statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_submit_layout, null);
                rlParent.removeAllViews();
                rlParent.addView(statusLayout);
//                if (TextUtils.equals(identifying, "空")) {
//
//                } else if (TextUtils.equals(identifying, "待整改")) {
//
//                } else if (TextUtils.equals(identifying, "待复验")) {
//
//                } else if (TextUtils.equals(identifying, "已通过")) {
//
//                }
                break;
            case 3://建设
                statusLayout = View.inflate(getActivity(), R.layout.scsl_work_detail_sg_submit_layout, null);
                rlParent.removeAllViews();
                rlParent.addView(statusLayout);
//                if (TextUtils.equals(identifying, "空")) {
//
//                } else if (TextUtils.equals(identifying, "待整改")) {
//
//                } else if (TextUtils.equals(identifying, "待复验")) {
//
//                } else if (TextUtils.equals(identifying, "已通过")) {
//
//                }
                break;
            default:
                break;
        }
    }

    private void setById() {
        parentScrollView = getOutView(statusLayout, R.id.parentScrollView);
        myWebView = getOutView(statusLayout, R.id.webView);
        rvAboutImg = getOutView(statusLayout, R.id.rv_about_img);
        tv_complete_commit = getOutView(statusLayout, R.id.tv_complete_commit);
        view_Line = getOutView(statusLayout, R.id.view_Line);
        tv_scSlState = getOutView(statusLayout, R.id.tv_scSlState);
        tv_inspectionPeople = getOutView(statusLayout, R.id.tv_inspectionPeople);
        tv_CreatorTime = getOutView(statusLayout, R.id.tv_CreatorTime);
        tv_rectify_people = getOutView(statusLayout, R.id.tv_rectify_people);
        tv_rectify_time = getOutView(statusLayout, R.id.tv_rectify_time);
        scSlZgRcv = getOutView(statusLayout, R.id.scSlZgRcv);
        //scJlSlZgRcv = getOutView(statusLayout, R.id.scSlZgRcv);
        //scJsSlZgRcv = getOutView(statusLayout, R.id.scSlZgRcv);
        etInstructContent = getOutView(statusLayout, R.id.et_instruct_content);
        rl_zgPicInstruct = getOutView(statusLayout, R.id.rl_zgPicInstruct);
        editorFontCount = getOutView(statusLayout, R.id.id_editor_detail_font_count);
        sgListAdapter = new PictureOrVideoListNewAdapter(getActivity());
        jlListAdapter = new PictureOrVideoListNewAdapter(getActivity());
        jsListAdapter = new PictureOrVideoListNewAdapter(getActivity());
        submitAdapter = new PictureOrVideoListNewAdapter(getActivity());
        try {
            initWebView(myWebView, parentScrollView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
//            if(pageType){
//
//            }
            initListPhoto(scSlZgRcv, sgListAdapter, sgSelectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //initListPhoto(scJlSlZgRcv, jlListAdapter, jlSelectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            //initListPhoto(scJsSlZgRcv, jsListAdapter, jsSelectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            initListPhoto(rvAboutImg, submitAdapter, submitSelectList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setListenerView(tv_rectify_people);
    }

    private void initWebView(RoundWebView webView, NestedScrollView parentScrollView) {
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
    }

    @JavascriptInterface
    public void addJSInterface(WebView mWebView) {
        //和js约定,包括调用类的对象,以及起的别名
        mWebView.addJavascriptInterface(ajs, ajs.getInterface());
    }

    private void initListPhoto(RecyclerView scSlRcv, PictureOrVideoListNewAdapter pictureAdapter, List<LocalMedia> picList) {
        scSlRcv.setLayoutManager(new GridLayoutManager(ctx, 3, LinearLayoutManager.VERTICAL, false));
        scSlRcv.addItemDecoration(new GridSpacingItemDecoration(3, 2, true));
        scSlRcv.setNestedScrollingEnabled(true);
        PhotoCameraUtils.init(this).photoDialogListAdapter(pictureAdapter, scSlRcv, picList,0);
        //PhotoCameraUtils.init(this).photoDialogListAdapter(sgListAdapter, scSlRcv, sgSelectList);
        //PhotoCameraUtils.init(this).photoDialogListAdapter(jlListAdapter, scSlRcv, jlSelectList);
        //PhotoCameraUtils.init(this).photoDialogListAdapter(jsListAdapter, scSlRcv, jsSelectList);
    }

    private void initShowPhoto(RecyclerView scSlRcv) {
        scSlRcv.setLayoutManager(new GridLayoutManager(ctx, 3, LinearLayoutManager.VERTICAL, false));
        scSlRcv.addItemDecoration(new GridSpacingItemDecoration(3, 2, true));
        scSlRcv.setNestedScrollingEnabled(true);
        showAdapter = new PictureVideoShowAdapter(getActivity());
        PhotoCameraUtils.init(this).photoCameraAdapter(showAdapter, scSlRcv, showList,1);
    }

    //工作台查询整改问题
    private void queryWorkDetail() {
        HttpRequest.get(getContext()).url(ServerInterface.listIssueAbarbeitung)
                .params("roomTowerFloorId", roomId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            LogUtils.logGGQ("scsl查询整改问题result:" + result.toString());
                            SCSLDetailData bean = new Gson().fromJson(result.toString(), SCSLDetailData.class);
                            if (bean != null) {
                                if (bean.code == 0) {
                                    SCSLDetailData.ListBean listBean = bean.list;
                                    if (listBean != null) {
                                        String scSlState = listBean.scslState;
                                        setText(tv_scSlState, scSlState);
                                        try {
                                            identifying = scSlState;
                                            if (scSlState.equals("待整改")) {
                                                view_Line.setBackgroundColor(setColor(R.color.red2));
                                            } else if (scSlState.equals("待复验")) {
                                                view_Line.setBackgroundColor(setColor(R.color.yellow));
                                            } else if (scSlState.equals("完成整改")) {
                                                view_Line.setBackgroundColor(setColor(R.color.green));
                                            } else if (scSlState.equals("验收通过")) {
                                                view_Line.setBackgroundColor(setColor(R.color.green2));
                                            } else {
                                                view_Line.setVisibility(View.GONE);
                                                tv_scSlState.setVisibility(View.GONE);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        if (!StringUtils.isEmpty(identifying)) {
                                            initLayoutShow(identifying);
                                            setById();
                                        }
                                        setText(tv_inspectionPeople, listBean.inspectionPeople);
                                        setText(tv_CreatorTime, listBean.creatorTime);
                                        setText(tv_rectify_people, listBean.zhenggaiPeople);
                                        id = listBean.scslId + "";
                                        rectifyId = listBean.zhenggaiId+"";
                                        rectifyName = listBean.zhenggaiPeople;
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
        HttpRequest.get(getActivity()).url(ServerInterface.updateZhanggaiPeople)
                .params("scslId", id)
                .params("zhenggaiId", rectifyId)
                .params("zhenggaiPeople", rectifyName)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {

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
        if (type == 0 || type == 3) {
            jsSelectList.addAll(selectLi);
            jsListAdapter.setNewData(jsSelectList);
            jsListAdapter.notifyDataSetChanged();
        } else if (type == 1) {
            sgSelectList.addAll(selectLi);
            sgListAdapter.setNewData(sgSelectList);
            sgListAdapter.notifyDataSetChanged();
        } else if (type == 2) {
            jlSelectList.addAll(selectLi);
            jlListAdapter.setNewData(jlSelectList);
            jlListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            LogUtils.i("requestCode==", requestCode + "");
            LogUtils.i("resultCode==", resultCode + "");
            if (resultCode == -1) {
                if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    List<File> filePaths = new ArrayList<>();
                    for (int i = 0; i < selectLi.size(); i++) {
                        filePaths.add(new File(selectLi.get(i).getPath()));
                    }
                    uploadFile(filePaths);
                }
            } else if (resultCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
                Bundle bundle = data.getBundleExtra("bundle");
                tv_rectify_people.setText(bundle.getString("name"));
                rectifyId = bundle.getString("id");
                rectifyName = bundle.getString("name");
            }
        }
    }

    private void uploadFile(List<File> filePaths) {
        showLoadView("");
        LogUtils.i("uploadFile==", ParameterMap.put("fileList", filePaths).toString());
        HttpRequest.get(getActivity()).url(ServerInterface.uploadFile)
                .params(ParameterMap.put("fileList", filePaths))
                .doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoadView();
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            for (int i = 0; i < imageArray.length(); i++) {
                                LocalMedia localMedia = new LocalMedia();
                                if (UiUtils.isEmpty(imageArray.optString(i))) {
                                    fileList.add(imageArray.optString(i));
                                    filePath = StringUtils.listToStrByChar(fileList, ',');
                                }
                                localMedia.setPath(ServerInterface.PVUrl() + imageArray.optString(i));
                                localMedia.setPictureType(PVAUtils.getFileLastType(ServerInterface.PVUrl() + imageArray.optString(i)));
                                if (type == 0 || type == 3) {
                                    jsSelectList.add(localMedia);
                                } else if (type == 1) {
                                    if (identifying.equals("空")) {
                                        submitSelectList.add(localMedia);
                                    } else {
                                        sgSelectList.add(localMedia);
                                    }
                                } else if (type == 2) {
                                    jlSelectList.add(localMedia);
                                }
                            }
                            if (type == 0 || type == 3) {
                                jsListAdapter.setNewData(jsSelectList);
                                jsListAdapter.notifyDataSetChanged();
                            } else if (type == 1) {
                                sgListAdapter.setNewData(sgSelectList);
                                sgListAdapter.notifyDataSetChanged();
                            } else if (type == 2) {
                                jlListAdapter.setNewData(jlSelectList);
                                jlListAdapter.notifyDataSetChanged();
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
                            myWebView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")));
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

    public void refreshHtml(int roomIds) {
        roomId = roomIds;
        getHtml();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhengGaiPeople:
                SelectTRPOrAU.select(this, rectifyId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.tv_rectify_people:
                SelectTRPOrAU.select(this, rectifyId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            default:
                break;
        }
    }

    public void loadJSMethod(String sectionId) {
        myWebView.loadUrl("javascript:getMsg('" + sectionId + "')");
    }

    protected void setListenerView(View view) {
        try {
            view.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
