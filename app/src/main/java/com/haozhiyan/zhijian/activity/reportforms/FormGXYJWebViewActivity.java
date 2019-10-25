package com.haozhiyan.zhijian.activity.reportforms;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.gxyj.GXYJActivity;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.zjInterface.AJSInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class FormGXYJWebViewActivity extends BaseActivity2 {
    private WebView webView;
    private WebSettings settings;
    private ACache aCache;
    private String sectionId,sectionName;//标段
    private String towerId, towerName;//楼栋
    private String unitId, unitName;//单元
    private String inspectionId, inspectionName;//检查项子项
    private AJSInterface ajs;

    @Override
    protected void init(Bundle savedInstanceState) {
        aCache = ACache.get(this, "cookie");
        sectionId = getIntent().getStringExtra("sectionId");
        sectionName = getIntent().getStringExtra("sectionName");
        towerId = getIntent().getStringExtra("towerId");
        towerName = getIntent().getStringExtra("towerName");
        unitId = getIntent().getStringExtra("unitId");
        unitName = getIntent().getStringExtra("unitName");
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_form_gxyjweb_view;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        webView = getOutView(R.id.myWebView);
        initWebView();
    }

    @Override
    protected void initData() {
        setTitleText(towerName +"工序移交进度");
        getHtml();
    }

    private void initWebView() {
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
      /*  webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((WebView) v).requestDisallowInterceptTouchEvent(false);
                return false;
            }
        });*/
        ajs = AJSInterface.getInstance(FormGXYJWebViewActivity.this, webView);
        addJSInterface(webView);
        ajs.setFormMethod(new AJSInterface.FormMethod() {
            @Override
            public void getGXYJFormData(String data) {
                Log.e("newUrl", data.toString());
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    inspectionName = jsonObject.optString("inspectionName");
                    inspectionId = jsonObject.optString("inspectionId");
                    Intent intent = new Intent(FormGXYJWebViewActivity.this, GXYJActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("sectionId", sectionId);
                    bundle.putString("sectionName", sectionName);
                    bundle.putString("towerId", towerId);
                    bundle.putString("towerName", towerName);
                    bundle.putString("unitId", unitId);
                    bundle.putString("unitName", unitName);
                    bundle.putString("inspectionSunName", inspectionName);
                    bundle.putString("inspectionSunId", inspectionId);
                    intent.putExtra("formBundle", bundle);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private WebChromeClient webChromeClient = new WebChromeClient() {

    };
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    };

    private void getHtml() {
        HttpRequest.get(getActivity()).url(ServerInterface.listTowerUnitMassage2)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject data = new JSONObject(result.toString());
                            if (data.optInt("code") == 0) {
                                JSONObject list = data.optJSONObject("list");
                                if (list != null) {
                                    String url = list.optString("html");
                                    String h5LastUrl = url + "?&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&sId=" + sectionId + "&id=" + unitId;
                                    String newUrl = h5LastUrl.trim().replace(" ", "");
                                    if (webView != null) {
                                        webView.loadUrl(newUrl);
                                    }
                                    Log.e("newUrl", newUrl);
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

    @JavascriptInterface
    public void addJSInterface(WebView mWebView) {
        //和js约定,包括调用类的对象,以及起的别名
        mWebView.addJavascriptInterface(ajs, ajs.getInterface());
    }
}
