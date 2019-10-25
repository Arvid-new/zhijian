package com.haozhiyan.zhijian.zjInterface;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

/**
 * Created by WangZhenKai on 2019/6/10.
 * Describe: 与js交互接口对象类
 */
public class AJSInterface {

    private static boolean isLogin;
    static Activity context;
    static WebView wb;
    private static AJSInterface instance;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 001:
                    String json = (String) msg.obj;
                    method.getScSlShowData(json);
                    break;
                case 002:
                    String pass = (String) msg.obj;
                    dataMethod.getPass(pass);
                    break;
                case 003:
                    String data = (String) msg.obj;
                    dataMethod.getInspectionData(data);
                    break;
                default:
                    break;
            }
        }
    };


    private AJSInterface() {

    }

    public synchronized static AJSInterface getInstance(Activity context, WebView wb, boolean isLogin) {
        AJSInterface.context = context;
        AJSInterface.wb = wb;
        AJSInterface.isLogin = !isLogin;
        if (instance == null) {
            synchronized (AJSInterface.class) {
                if (instance == null) {
                    instance = new AJSInterface();
                }
            }
        }
        return instance;
    }

    public synchronized static AJSInterface getInstance(Activity context, WebView wb) {
        AJSInterface.context = context;
        AJSInterface.wb = wb;
        if (instance == null) {
            synchronized (AJSInterface.class) {
                if (instance == null) {
                    instance = new AJSInterface();
                }
            }
        }
        return instance;
    }

    //获取H5页面返回的json数据
    @JavascriptInterface
    public void getPointIOS(String json) {
        Message message = new Message();
        message.what = 001;
        message.obj = json;
        handler.sendMessage(message);
//        if (method != null)
//            method.getScSlShowData(json);
    }

    //获取H5页面点坐标数据
    @JavascriptInterface
    public void getPointXBQ(String data) {
        method.getPoint(data);
    }

    //工序移交
    @JavascriptInterface
    public void getPoint(String data) {
        method.getGxYjPoint(data);
    }

    //实测实量接收检查项数据
    @JavascriptInterface
    public void getIdName(Object data) {

    }

    @JavascriptInterface
    public void getIdNameA(String data) {
        Message message = new Message();
        message.what = 003;
        message.obj = data;
        handler.sendMessage(message);
//        if (method != null)
//            dataMethod.getInspectionData(data);
    }

    //实测实量接收合格率
    @JavascriptInterface
    public void getPass(String pass) {
        Message message = new Message();
        message.what = 002;
        message.obj = pass;
        handler.sendMessage(message);
//        if (method != null)
//            dataMethod.getPass(pass);
    }

    private Method method;

    public interface Method {
        void getPoint(String data);

        void getGxYjPoint(String data);

        void getScSlShowData(String json);
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    private DataMethod dataMethod;

    public interface DataMethod {
        void getInspectionData(String object);

        void getPass(String pass);
    }

    public void setDataMethod(DataMethod method) {
        this.dataMethod = method;
    }

    //获取报表-工序移交数据
    @JavascriptInterface
    public void getCheckNameAndIdAndroid(String data) {
        formMethod.getGXYJFormData(data);
    }

    @JavascriptInterface
    public void getCheckNameAndId(String data) {
    }

    private FormMethod formMethod;

    public interface FormMethod {
        void getGXYJFormData(String data);
    }

    public void setFormMethod(FormMethod formMethod) {
        this.formMethod = formMethod;
    }

    /**
     * 网页返回上一级
     */
    @JavascriptInterface
    public void goBack() {

        if (null != wb && wb.canGoBack()) {
            wb.goBack();
        }
    }

    @JavascriptInterface
    public String getInterface() {
        return "AJSInterface";
    }

}
