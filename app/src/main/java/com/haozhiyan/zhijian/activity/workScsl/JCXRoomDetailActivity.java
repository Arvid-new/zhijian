package com.haozhiyan.zhijian.activity.workScsl;

import android.os.Bundle;
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
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.widget.RoundWebView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

//实测实量汇总详情二级楼栋户型详情
public class JCXRoomDetailActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_right)
    TextView tv_right;
    @ViewInject(R.id.webView)
    RoundWebView webView;
    private WebSettings settings;
    String localStr = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jcxroom_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("部位传参");
        Bundle bundle = getIntent().getBundleExtra("data");
        String local = bundle.getString("floorUnit");
        String localR = bundle.getString("room");
        localStr = local + localR;
        tv_title.setText(bundle == null ? "楼部位" : (StringUtils.isEmpty(localStr) ? "楼部位" : localStr));
        settings = webView.getSettings();
        //打开支持JavaScript开关
        settings.setJavaScriptEnabled(true);
        //是否显示窗口悬浮的缩放控制，默认true
        //支持屏幕缩放
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        //不显示webview缩放按钮
        settings.setDisplayZoomControls(false);

        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(webChromeClient);
        webView.loadUrl("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1558608404&di=51c636058fbf51dac203a4d37a597d71&src=http://img2sz.centainfo.com/images/20180410/110937_49fa5ae5-f1a8-48b8-9a88-c2fbcd5ae6e9_360x260_c.jpg");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back, R.id.tv_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_right://

                break;
            default:
                break;
        }
    }

    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return false;
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

    };
}
