package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
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

//功能选择
public class FunctionActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.listData)
    ListView listData;
    @ViewInject(R.id.et_search)
    EditText et_search;
    @ViewInject(R.id.iv_search)
    ImageView ivSearch;
    @ViewInject(R.id.tv_search_function)
    TextView tvSearchFunction;
    private TextAdapter textAdapter;
    private List<ItemBean> stringList = new ArrayList<>();
    private List<ItemBean> searchList = new ArrayList<>();
    private Handler myHandler = new Handler();
    private boolean isSearchList = false;
    private String bdName = "",bdId="33";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_function;
    }

    @Override
    protected void initView() {
        tv_title.setText(StringUtils.title2());
    }

    @Override
    protected void initListener() {
        listData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textAdapter.setSelect(position);
                Intent intent = new Intent();
                if (isSearchList) {
                    if (listEmpty(searchList)) {
                        intent.putExtra("content", searchList.get(position).name);
                        intent.putExtra("id", searchList.get(position).strId);
                    }
                } else {
                    if (listEmpty(stringList)) {
                        intent.putExtra("content", stringList.get(position).name);
                        intent.putExtra("id", stringList.get(position).strId);
                        LogUtils.i("传递id====",stringList.get(position).strId);
                    }
                }
                setResult(Constant.REQUEST_CODE, intent);
                finish();
            }
        });
        et_search.addTextChangedListener(textWatcher);
    }

    @Override
    protected void initData(boolean isNetWork) {
        isSearchList = false;
        Bundle bundle = getIntent().getBundleExtra("data");
        bdName = bundle == null ? "" : bundle.getString("bdName");
        getJCBD();
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
        textAdapter = new TextAdapter(this, list);
        listData.setAdapter(textAdapter);
        textAdapter.notifyDataSetChanged();
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

    private void getJCBD() {
        HttpRequest.get(this).url(ServerInterface.xcJcBdList).params("itemId", Constant.projectId).doGet(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    JSONObject json = new JSONObject(result.toString());
                    if (json.optInt("code") == 0) {
                        JSONArray data = json.optJSONArray("data");
                        if (arrayEmpty(data)) {
                            stringList.clear();
                            for (int i = 0; i < data.length(); i++) {
                                if (data.optJSONObject(i).optString("sectionName").equals(bdName)) {
                                    stringList.add(new ItemBean(data.optJSONObject(i).optString("sectionName"), data.optJSONObject(i).optString("sectionId"), true));
                                } else {
                                    stringList.add(new ItemBean(data.optJSONObject(i).optString("sectionName"), data.optJSONObject(i).optString("sectionId"), false));
                                }
                            }
                            setData(stringList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(act, msg);
            }
        });
    }
}
