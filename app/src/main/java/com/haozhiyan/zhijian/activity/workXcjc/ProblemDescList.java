package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.SendBackListAdapter;
import com.haozhiyan.zhijian.adapter.TextAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProblemDescList extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.listData)
    RecyclerView listData;
    @ViewInject(R.id.et_search)
    EditText et_search;
    @ViewInject(R.id.iv_search)
    ImageView ivSearch;
    @ViewInject(R.id.tv_search_function)
    TextView tvSearchFunction;
    private TextAdapter textAdapter;
    private List<ItemBean> stringList;
    private List<ItemBean> searchList = new ArrayList<>();
    private Handler myHandler = new Handler();
    private boolean isSearchList = false;
    private String descName = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_problem_desc_list;
    }

    @Override
    protected void initView() {
        tv_title.setText("选择描述");
    }

    @Override
    protected void initListener() {
//        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                textAdapter.setSelect(position);
//                Intent intent = new Intent();
//                if (isSearchList) {
//                    if (listEmpty(searchList)) {
//                        intent.putExtra("content", searchList.get(position).name);
//                        intent.putExtra("id", searchList.get(position).strId);
//                    }
//                } else {
//                    if (listEmpty(stringList)) {
//                        intent.putExtra("content", stringList.get(position).name);
//                        intent.putExtra("id", stringList.get(position).strId);
//                    }
//                }
//                setResult(Constant.DESC_LIST_CODE, intent);
//                finish();
//            }
//        });
        et_search.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initData(boolean isNetWork) {
        isSearchList = false;
        Bundle bundle = getIntent().getBundleExtra("data");
        String inspectionId = bundle == null ? "" : bundle.getString("inspectionId");
        if (StringUtils.isEmpty(inspectionId)) {
            inspectionId = getIntent().getExtras().getString("inspectionId", "");
        }
        descName = bundle == null ? "" : bundle.getString("descName","");
        LogUtils.i("inspectionId====", inspectionId + "");
//        getJCBD(inspectionId);
        listSendBack(inspectionId);
    }

    @OnClick({R.id.rl_back, R.id.tv_search_function})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_search_function:
                tvSearchFunction.setVisibility(View.GONE);
                et_search.setText("");
                setData(stringList);
                isSearchList = false;
                break;
            default:
                break;
        }
    }

    private void setData(List<ItemBean> list) {
//        textAdapter = new TextAdapter(this, list);
//        listData.setAdapter(textAdapter);
//        textAdapter.notifyDataSetChanged();
    }

    Runnable eChanged = new Runnable() {

        @Override
        public void run() {
            searchList.clear();
            setData(ListUtils.getDataSubs(stringList, searchList, UiUtils.getContent(et_search)));
        }
    };
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                ivSearch.setVisibility(View.VISIBLE);
                tvSearchFunction.setVisibility(View.GONE);
                setData(stringList);
                isSearchList = false;
            } else {
                tvSearchFunction.setVisibility(View.VISIBLE);
                ivSearch.setVisibility(View.GONE);
                myHandler.post(eChanged);
                isSearchList = true;
            }
        }
    };

    private void getJCBD(String inspectionId) {
        HttpRequest.get(this).url(ServerInterface.problemDesc).params("inspectionId", inspectionId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("getJCBD====", result.toString());
                try {
                    JSONObject json = new JSONObject(result.toString());
                    if (json.optInt("code") == 0) {
                        JSONArray data = json.optJSONArray("data");
                        if (arrayEmpty(data)) {
                            stringList = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                if (data.optJSONObject(i).optString("particularsName").equals(descName)) {
                                    stringList.add(new ItemBean(data.optJSONObject(i).optString("particularsName"), data.optJSONObject(i).optLong("inspectionId"), data.optJSONObject(i).optString("xcjcParticularsId"), true));
                                } else {
                                    stringList.add(new ItemBean(data.optJSONObject(i).optString("particularsName"), data.optJSONObject(i).optLong("inspectionId"), data.optJSONObject(i).optString("xcjcParticularsId"), false));
                                }
                            }
                            setData(stringList);
                        }
                    }
                } catch (JSONException e) {
                    LogUtils.i("getJCBDException====", "requestException");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                LogUtils.i("getJCBDError====", "requestError");
                LogUtils.i("msg====", msg);
                ToastUtils.myToast(act, msg);
            }
        });
    }


    /**
     * 获取问题列表
     */
    private void listSendBack(String inspectionId) {
        HttpRequest.get(this)
                .url(ServerInterface.listSendBack)
                .params("inspectionId", inspectionId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                final List<String> strings = new ArrayList<>();
                                final List<String> ids = new ArrayList<>();
                                boolean[] checks = new boolean[array.length()];
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    checks[i] = false;
                                    try {
                                        if (descName.equals(object.optString("particularsName"))) {
                                            checks[i] = true;
                                        }
                                    } catch (Exception e) {
                                    }
                                    strings.add(object.optString("particularsName"));
                                    ids.add(object.optString("gxyjParticularsId"));
                                }
                                final SendBackListAdapter sendBackListAdapter = new SendBackListAdapter(strings, checks);
                                listData.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                                listData.setAdapter(sendBackListAdapter);
                                sendBackListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        if (ListUtils.listEmpty(strings)) {
                                            String supervisorSendBack = strings.get(position);
                                            String id = ids.get(position);
                                            Intent intent = new Intent();
                                            intent.putExtra("content", supervisorSendBack);
                                            intent.putExtra("id", id);
                                            setResult(Constant.DESC_LIST_CODE, intent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                ToastUtils.myToast(getApplicationContext(), "当前部位暂无可选原因");
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
