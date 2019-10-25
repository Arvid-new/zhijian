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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.zjInterface.AJSInterface;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class XCJCImagesActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.webView)
    WebView webView;
    @ViewInject(R.id.layout_noImg)
    RelativeLayout layoutNoImg;
    @ViewInject(R.id.tv_remind)
    TextView tvRemind;
    private WebSettings settings;
    private String roomId = "1", towerName = "", unitName = "", roomName = "", floorName = "";
    private AJSInterface ajs;
    private ACache aCache;
    private String towerId, unitId, floorId, towerFloorUnitRoom, housemap;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjcimages_acyivity;
    }

    @Override
    protected void initView() {
        tv_title.setText("户型图");
        ivClose.setVisibility(View.VISIBLE);
        ajs = AJSInterface.getInstance(this, webView);
        roomId = getIntent().getStringExtra("roomId");
        towerId = getIntent().getStringExtra("towerId");
        unitId = getIntent().getStringExtra("unitId");
        floorId = getIntent().getStringExtra("floorId");
        towerFloorUnitRoom = getIntent().getStringExtra("towerFloorUnitRoom");
        towerName = getIntent().getStringExtra("towerName");
        unitName = getIntent().getStringExtra("unitName");
        floorName = getIntent().getStringExtra("floorName");
        roomName = getIntent().getStringExtra("roomName");
        housemap = getIntent().getStringExtra("housemap");
        LogUtils.i("roomId====", roomId);
        aCache = ACache.get(this, "cookie");
        initWebSettings();
    }

    @Override
    protected void initListener() {
        ajs.setMethod(new AJSInterface.Method() {
            @Override
            public void getPoint(String data) {
                LogUtils.i("setData====", data);
                if (data.equals("null")) {
                    ToastUtils.myToast(act, "请先添加标记");
                } else {
                    LogUtils.print("获取点数据" + data);//data, "xcJc"
                    ItemValues itemValues = new ItemValues();
                    itemValues.houseMap = data;
                    itemValues.pieceType = "xcJc";
                    itemValues.towerFloorUnitRoom = towerFloorUnitRoom;
                    itemValues.towerId = towerId;
                    itemValues.floorId = floorId;
                    itemValues.unitId = unitId;
                    itemValues.roomId = roomId;
                    itemValues.tower = towerName;
                    itemValues.unit = unitName;
                    itemValues.floor = floorName;
                    itemValues.roomNum = roomName;
                    EventBus.getDefault().post(itemValues);
                    finish();
                }
            }

            @Override
            public void getGxYjPoint(String data) {
                //-----------
            }

            @Override
            public void getScSlShowData(String data) {
                //-----------
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        if (!StringUtils.isEmpty(roomId)) {
            getData(roomId);
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
        //Android 加载本地H5页面：
        //webView.loadUrl("file:///android_asset/huXingTu.html");
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
            case R.id.iv_close:
                ActivityManager.getInstance().removeActivity(this);
                jumpToActivity(MainActivity.class);
                break;
            default:
                break;
        }
    }

    private void getData(String roomId) {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.huXingTu).params("roomId", roomId).doGet(new HttpStringCallBack() {
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
                                LogUtils.i("Urls====", url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")));
                                webView.loadUrl(url + "&cookie=" + StringUtils.H5Cookie(aCache.getAsString("cookie")));
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
            //webView.loadUrl("javaScript:function(){gg("+roomId+")}");
            //webView.loadUrl("javascript:getHouseMap('" + housemap + "')");
            webView.loadUrl("javascript:showPointXBQ('" + housemap + "')");
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient webChromeClient = new WebChromeClient() {

    };

}
