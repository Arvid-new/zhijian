package com.haozhiyan.zhijian.activity.gxyj;

import android.content.Intent;
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
import com.haozhiyan.zhijian.activity.workXcjc.AddTroubleActivity;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.zjInterface.AJSInterface;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

//工序移交户型图-可以操作
public class GxYjH5 extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.webView)
    WebView webView;
    @ViewInject(R.id.layout_noImg)
    RelativeLayout layoutNoImg;
    private WebSettings settings;
    private AJSInterface ajs;
    private ACache aCache;
    private String roomId = "1", towerName = "", unitName = "", towerId = "", floorId = "", unitId = "", roomNum = "";
    private String houseMap = "", keyId = "", houseData = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gx_yj_h5;
    }

    @Override
    protected void initView() {
        tvTitle.setText("户型图");
        ajs = AJSInterface.getInstance(this, webView);
        aCache = ACache.get(this, "cookie");
        roomId = getIntent().getStringExtra("roomId");
        towerName = getIntent().getStringExtra("towerName");
        unitName = getIntent().getStringExtra("unitName");
        towerId = getIntent().getStringExtra("towerId");
        floorId = getIntent().getStringExtra("floorId");
        unitId = getIntent().getStringExtra("unitId");
        roomNum = getIntent().getStringExtra("roomNum");
        houseMap = getIntent().getStringExtra("houseMap");
        keyId = getIntent().getStringExtra("keyId");
        LogUtils.print("data333===" + houseMap);
        initWebSettings();
    }

    @Override
    protected void initListener() {
        ajs.setMethod(new AJSInterface.Method() {
            @Override
            public void getPoint(String data) {
                //---------
            }

            @Override
            public void getGxYjPoint(String data) {
                LogUtils.print("data111===" + data);
                houseData = data;
                ItemValues itemValues = new ItemValues();
                itemValues.houseMap = data;
                itemValues.pieceType = "gxYj";
                itemValues.towerFloorUnitRoom = towerName + unitName + roomNum;
                itemValues.towerId = towerId;
                itemValues.floorId = floorId;
                itemValues.unitId = unitId;
                itemValues.roomId = roomId;
                itemValues.roomNum = roomNum;
                EventBus.getDefault().post(itemValues);
                Intent intent = new Intent(act, AddTroubleActivity.class);
                intent.putExtra("pieceType", "gxYj");
                intent.putExtra("housemap", data);
                setResult(Constant.PLACE_CODE, intent);
                finish();
            }

            @Override
            public void getScSlShowData(String data) {
                //-----------
            }
        });
    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
        }
    }

    @Override
    protected void initData(boolean isNetWork) {
        if (!StringUtils.isEmpty(roomId)) {
            getData(roomId);
        } else {
            layoutNoImg.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
            //webView.loadUrl("javaScript:function(){gg("+roomId+")}");
        }
    }

    @JavascriptInterface
    public void addJSInterface(WebView mWebView) {
        //和js约定,包括调用类的对象,以及起的别名
        mWebView.addJavascriptInterface(ajs, ajs.getInterface());
    }

    private void getData(String roomId) {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.selectHouseMapHtmlGxyj).params("roomId", roomId).doGet(new HttpStringCallBack() {
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
                            } else {
                                layoutNoImg.setVisibility(View.GONE);
                                webView.setVisibility(View.VISIBLE);
                                //Map<String, String> mapHeader = new HashMap<>();
                                //mapHeader.put("Cookie", aCache.getAsString("cookie"));
                                //webView.loadUrl(url, mapHeader);
                                webView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")));
                                //webView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")) + "&keyId=" + keyId);
                            }
                        } else {
                            layoutNoImg.setVisibility(View.VISIBLE);
                            webView.setVisibility(View.GONE);
                            errorRemind.setText("没有户型图哦");
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
            if (!StringUtils.isEmpty(houseData)) {
                webView.loadUrl("javascript:showPoint(" + houseMap + ")");
            }
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

    };
}
