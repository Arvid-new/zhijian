package com.haozhiyan.zhijian.activity.MePackage;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.FeedBackHistoryAdapter;
import com.haozhiyan.zhijian.bean.FeedBackListBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.widget.MyDividerItem;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FeedBackHistory extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.feedBackHistory)
    RecyclerView feedBackHistory;
    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private FeedBackHistoryAdapter adapter;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_feed_back_history;
    }

    @Override
    protected void initView() {
        tvTitle.setText("反馈历史");
        feedBackHistory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        feedBackHistory.addItemDecoration(new MyDividerItem(this, MyDividerItem.VERTICAL));
        adapter = new FeedBackHistoryAdapter(null,this);
        feedBackHistory.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        adapter.setEmptyView(emptyView);
        getData();
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
    private void getData() {
        HttpRequest.get(this).url(ServerInterface.listProblemFeedback).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                LogUtils.i("反馈列表==", result.toString());
                try {
                    JSONObject data = new JSONObject(result.toString());
                    if (data.optInt("code") == 0) {
                        JSONArray listArray = data.optJSONArray("list");
                        if (arrayEmpty(listArray)) {
                            List<FeedBackListBean.ListBean> listBeans = new ArrayList<>();
                            for (int i = 0; i < listArray.length(); i++) {
                                FeedBackListBean.ListBean bean = new FeedBackListBean.ListBean();
                                bean.id = listArray.optJSONObject(i).optInt("id");
                                bean.feedback = listArray.optJSONObject(i).optString("feedback");
                                bean.problemPicture = listArray.optJSONObject(i).optString("problemPicture");
                                bean.userId = listArray.optJSONObject(i).optInt("userId");
                                bean.serviceMessage = listArray.optJSONObject(i).optString("serviceMessage");
                                bean.creatorTime = listArray.optJSONObject(i).optString("creatorTime");
                                JSONArray pathArray = listArray.optJSONObject(i).optJSONArray("childPP");
                                if (arrayEmpty(pathArray)) {
                                    List<String> pathList = new ArrayList<>();
                                    for (int j = 0; j < pathArray.length(); j++) {
                                        pathList.add(pathArray.optString(j));
                                    }
                                    bean.childPP = pathList;
                                }
                                listBeans.add(bean);
                            }
                            adapter.setNewData(listBeans);
                        } else {
                            adapter.setEmptyView(emptyView);
                        }
                    } else {
                        ToastUtils.myToast(act, data.optString("msg"));
                        adapter.setEmptyView(emptyView);
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
