package com.haozhiyan.zhijian.activity.gxyj;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class GCYJCollectDetail extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.webView)
    WebView webView;
    private WebSettings settings;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gcyjcollect_detail;
    }

    @Override
    protected void initView() {
        tvTitle.setText("汇总");
        initWebSettings();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        webView.loadUrl("https://dpic.tiankong.com/rs/tc/QJ7116938261.jpg?x-oss-process=style/670ws");
    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            default:
                break;
        }
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
}
