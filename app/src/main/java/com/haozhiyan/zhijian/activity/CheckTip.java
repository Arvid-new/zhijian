package com.haozhiyan.zhijian.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import org.json.JSONObject;

public class CheckTip extends BaseActivity2 {
    private String inspectionId;
    private String inspectionName;
    private String inspectionSunName;
    private String from;

    private TextView titile;
    private TextView body;

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            inspectionId = getIntent().getExtras().getString("inspectionId", "");
            inspectionName = getIntent().getExtras().getString("inspectionName", "");
            inspectionSunName = getIntent().getExtras().getString("inspectionSunName", "");
            from = getIntent().getExtras().getString("from", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void start(Context context
            , String inspectionId
            , String inspectionName
            , String inspectionSunName
            , String from) {
        Intent intent = new Intent(context, CheckTip.class);
        intent.putExtra("inspectionId", inspectionId);
        intent.putExtra("inspectionName", inspectionName);
        intent.putExtra("inspectionSunName", inspectionSunName);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_check_tip;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("检查指引");
        setAndroidNativeLightStatusBar(true);

        titile = findViewById(R.id.titile);
        body = findViewById(R.id.body);


        titile.setText(inspectionName);
        if (!StringUtils.isEmpty(inspectionSunName)) {
            titile.append("-");
            titile.append(inspectionSunName);
        }

        listCheckTheGuide();
    }

    @Override
    protected void initData() {

    }

    private void listCheckTheGuide() {
        if (from.equals("gxyj")) {
            HttpRequest.get(getContext())
                    .url(ServerInterface.listCheckTheGuide)
                    .params("inspectionId", inspectionId)
                    .doPost(new HttpStringCallBack() {
                        @Override
                        public void onSuccess(Object result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result.toString());
                                if (jsonObject.optInt("code") == 0) {
                                    String tip = jsonObject.optJSONObject("list").optString("checkGuide");
                                    if (TextUtils.isEmpty(tip)) {
                                        body.setText("无");
                                    } else {
                                        body.setText(tip);
                                    }
                                } else {
                                    ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int code, String msg) {

                        }
                    });
        } else if (from.equals("xcjc")) {
            HttpRequest.get(getContext())
                    .url(ServerInterface.listJCZY)
                    .params("inspectionId", inspectionId)
                    .doPost(new HttpStringCallBack() {
                        @Override
                        public void onSuccess(Object result) {
                            try {
                                JSONObject jsonObject = new JSONObject(result.toString());
                                if (jsonObject.optInt("code") == 0) {
                                    String tip = jsonObject.optJSONArray("list").optJSONObject(0).optString("checkGuide");
                                    if (TextUtils.isEmpty(tip)) {
                                        body.setText("无");
                                    } else {
                                        body.setText(tip);
                                    }
                                } else {
                                    ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
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


    }


}
