package com.haozhiyan.zhijian.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.DaiBanListAdapter;
import com.haozhiyan.zhijian.bean.DaibanBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

//待办
public class DaiBanActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.iv_right_icon)
    ImageView ivRightIcon;
    @ViewInject(R.id.rv_formsList)
    RecyclerView rvFormsList;
    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private DaiBanListAdapter adapter;
    private List<DaibanBean.ModulesDataBean> modulesDataBeans;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_dai_ban;
    }

    @Override
    protected void initView() {
        tvTitle.setText("待办");
        ivRightIcon.setImageResource(R.drawable.icon_map);
        swipeRefreshLayout.setColorSchemeColors(setColor(R.color.red));
        rvFormsList.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        rvFormsList.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
    }

    @Override
    protected void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getDaiBan();
            }
        });
        adapter = new DaiBanListAdapter(null, this);
        rvFormsList.setAdapter(adapter);
    }

    @Override
    protected void initData(boolean isNetWork) {
        getDaiBan();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String name = DataTest.daibanName[position];
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("type", position + "");
                jumpToActivity(DaiBanTypeVPActivity.class, bundle);
            }
        });
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

    private void getDaiBan() {
        HttpRequest.get(this).url(ServerInterface.daiBanSetting)
                .params("dikuaiId", Constant.projectId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            DaibanBean daibanBean = new Gson().fromJson(result.toString(), DaibanBean.class);
                            if (daibanBean.code == 0) {
                                if (listEmpty(daibanBean.modulesData)) {
                                    modulesDataBeans = daibanBean.modulesData;
                                    adapter.setNewData(modulesDataBeans);
                                } else {
                                    errorRemind.setText("暂无待办功能数据");
                                    adapter.setEmptyView(emptyView);
                                }
                            } else {
                                errorRemind.setText("暂无待办功能数据");
                                adapter.setEmptyView(emptyView);
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
