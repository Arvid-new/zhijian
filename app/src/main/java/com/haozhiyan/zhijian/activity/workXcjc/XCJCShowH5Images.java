package com.haozhiyan.zhijian.activity.workXcjc;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.zjInterface.AJSInterface;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

public class XCJCShowH5Images extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.webView)
    WebView webView;
    @ViewInject(R.id.layout_noImg)
    RelativeLayout layoutNoImg;
    @ViewInject(R.id.tv_remind)
    TextView tvRemind;
    private WebSettings settings;
    private String roomId = "1", problemId = "1";
    private AJSInterface ajs;
    private ACache aCache;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjcshow_h5_images;
    }

    @Override
    protected void initView() {
        tv_title.setText("户型图");
        ajs = AJSInterface.getInstance(this, webView);
        roomId = StringUtils.isEmpty(getIntent().getStringExtra("roomId")) ? "1" : getIntent().getStringExtra("roomId");
        problemId = StringUtils.isEmpty(getIntent().getStringExtra("problemId")) ? "1" : getIntent().getStringExtra("problemId");
        //problemId = getIntent().getStringExtra("problemId");
        aCache = ACache.get(this, "cookie");
        initWebSettings();
        LogUtils.i("roomId====", roomId);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        if (!StringUtils.isEmpty(roomId)) {
            getData(roomId, problemId);
        } else {
            layoutNoImg.setVisibility(View.VISIBLE);
            tvRemind.setText("没有户型图哦");
            webView.setVisibility(View.GONE);
        }
    }

    @JavascriptInterface
    public void addJSInterface(WebView mWebView) {
        //和js约定,包括调用类的对象,以及起的别名
        mWebView.addJavascriptInterface(ajs, ajs.getInterface());
    }

    private void initWebSettings() {
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
        addJSInterface(webView);
    }

    @OnClick({R.id.rl_back, R.id.iv_close})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            default:
                break;
        }
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
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

    };

    private void getData(String roomId, final String problemId) {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.selectHouseMapHtmlXcjc)
                .params("roomId", roomId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        LogUtils.i("json====", result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            String url = "";
                            if (object.optInt("code") == 0) {
                                if (object.optJSONObject("list") != null) {
                                    url = object.optJSONObject("list").optString("defaultMap");
                                    if (StringUtils.isEmpty(url)) {
                                        layoutNoImg.setVisibility(View.VISIBLE);
                                        webView.setVisibility(View.GONE);
                                        tvRemind.setText("没有户型图哦");
                                    } else {
                                        layoutNoImg.setVisibility(View.GONE);
                                        webView.setVisibility(View.VISIBLE);
                                        LogUtils.i("xcjc_url==", url);
                                        LogUtils.i("xcjc_show==", url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&problemId=" + problemId);
                                        webView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&problemId=" + problemId);
                                    }
                                } else {
                                    layoutNoImg.setVisibility(View.VISIBLE);
                                    webView.setVisibility(View.GONE);
                                    tvRemind.setText("没有户型图哦");
                                }
                            }
                        } catch (JSONException e) {
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
