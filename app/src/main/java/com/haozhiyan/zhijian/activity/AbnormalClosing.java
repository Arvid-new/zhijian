package com.haozhiyan.zhijian.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.CloseReasonAdapter;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 非正常关闭
 */
public class AbnormalClosing extends BaseActivity2 {

    public static final int SELECT_REASON = 10191;//
    private int type;
    private String cause;
    private String  id;
    private CloseReasonAdapter adapter;
    private List<String> strings;
    private List<String> ids;
    private RecyclerView rcv;

    public static void select(Activity activity, String  id, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(activity, AbnormalClosing.class);
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        bundle.putInt("type", requestCode);
        intent.putExtras(bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        try {
            type = getIntent().getExtras().getInt("type", 0);
            id = getIntent().getExtras().getString("id", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_abnormal_closing;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("选择非正常关闭原因");
        setAndroidNativeLightStatusBar(true);
    }

    @Override
    protected void initData() {
        rcv = findViewById(R.id.rcv);
        rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        findViewById(R.id.commitBT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateAbnormalClosing();

            }
        });
        getReason();
    }

    private void getReason() {
        HttpRequest.get(getContext())
                .url(ServerInterface.abnormalClosing)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONArray array = object.optJSONArray("list");
                                strings = new ArrayList<>();
                                ids = new ArrayList<>();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.optJSONObject(i);
                                    ids.add(jsonObject.optString("id"));
                                    strings.add(jsonObject.optString("closeCause"));
                                }
                                adapter = new CloseReasonAdapter(strings);
                                rcv.setAdapter(adapter);

                                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter aa, View view, int position) {
                                        adapter.setSelect(position);
                                        adapter.notifyDataSetChanged();
                                        cause = strings.get(position);
//                                        id = ids.get(position);
                                    }
                                });
                            } else {
                                Toast.makeText(AbnormalClosing.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
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

    /**
     * 提交关闭
     */
    private void updateAbnormalClosing() {
        HttpRequest.get(getContext())
                .url(ServerInterface.updateAbnormalClosing)
                .params("closeCause", cause)
                .params("id", id)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                Intent intent = new Intent();
                                intent.putExtra("cause", cause);
                                intent.putExtra("id", id);
                                setResult(type, intent);
                                finish();
                            } else {
                                Toast.makeText(AbnormalClosing.this, object.optString("msg"), Toast.LENGTH_SHORT).show();
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
}
