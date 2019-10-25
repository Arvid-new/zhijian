package com.haozhiyan.zhijian.activity;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyWebView extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rlBback;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.fag_webView)
    WebView webView;
    private String url;
    private WebSettings settings;
    private String titleLabel = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, false);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra("data");
        url = bundle == null ? "" : bundle.getString("url");
        titleLabel = bundle == null ? "页面" : bundle.getString("title");
        tvTitle.setText(titleLabel);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {

        settings = webView.getSettings();
        //打开支持JavaScript开关
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //指定WebView的页面布局显示形式，调用该方法会引起页面重绘。默认LayoutAlgorithm#NARROW_COLUMNS
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);// 布局算法，单列

        //是否启动概述模式浏览界面，当页面宽度超过WebView显示宽度时，缩小页面适应WebView。默认false
//        settings.setLoadWithOverviewMode(true);

        //是否显示窗口悬浮的缩放控制，默认true
        settings.setBuiltInZoomControls(false);

        //设定支持viewport,新型高配手机匹配支持
        settings.setUseWideViewPort(true);
        settings.setAppCacheEnabled(true);

        //允许访问文件
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        //设置网页无缓存

        //解决网页图片不显示问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            webView.setWebContentsDebuggingEnabled(true);
//        }
//        settings.setBlockNetworkImage(false);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);

        webView.loadUrl(url);
//        webView.loadUrl("https://www.baidu.com");
        //webView.loadUrl("file:///android_asset/test.html");
        //webView.loadUrl("file:///android_asset/huXingTu.html");
    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                backDoEvent();
                break;
        }
    }


    private final WebChromeClient webChromeClient = new WebChromeClient() {
        @Override//这是获取谷歌浏览器网页的名字
        public void onReceivedTitle(WebView view, String title) {
            if (StringUtils.isEmpty(url)) {
                tvTitle.setText("页面无效");
            } else if (StringUtils.isEmpty(titleLabel)) {
                tvTitle.setText("页面");
            } else {
                if (url.contains("http") || url.contains("php") || url.contains("https")) {
                    tvTitle.setText(titleLabel);
                }
            }
        }
    };
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;//WebView Brower
            //return false;//System Brower
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
//            super.onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    };

    //返回事件执行动作
    private void backDoEvent() {
//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
            ActivityManager.getInstance().removeActivity(this);
//        }
    }

    @Override
    public void onBackPressed() {
        backDoEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }
}
