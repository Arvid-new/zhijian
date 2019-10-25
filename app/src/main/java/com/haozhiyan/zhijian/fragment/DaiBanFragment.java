package com.haozhiyan.zhijian.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.DaiBanTypeVPActivity;
import com.haozhiyan.zhijian.adapter.DaiBanListAdapter;
import com.haozhiyan.zhijian.bean.DaibanBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 待办
 */
public class DaiBanFragment extends BaseFragment {

    private RelativeLayout rl_back;
    private TextView tv_title;
    private ImageView ivRightIcon;
    private RecyclerView rv_formsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DaiBanListAdapter adapter;
    private List<DaibanBean.ModulesDataBean> modulesDataBeans;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_daiban;
    }

    @Override
    public void initView(View view) {
        rl_back = getOutView(view, R.id.rl_back);
        tv_title = getOutView(view, R.id.tv_title);
        ivRightIcon = getOutView(view, R.id.iv_right_icon);
        rv_formsList = getOutView(view, R.id.rv_formsList);
        swipeRefreshLayout = getOutView(view, R.id.swipeRefreshLayout);
        tv_title.setText("待办");
        rl_back.setVisibility(View.GONE);
        ivRightIcon.setImageResource(R.drawable.icon_map);
        swipeRefreshLayout.setColorSchemeColors(setColor(R.color.red));
        rv_formsList.setLayoutManager(new GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false));
        rv_formsList.addItemDecoration(new GridSpacingItemDecoration(2, 30, true));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                getDaiBan();
            }
        });
        adapter = new DaiBanListAdapter(null, ctx);
        rv_formsList.setAdapter(adapter);
    }

    @Override
    public void initData(boolean isNetWork) {
        //getDaiBan();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    Constant.photoTag = modulesDataBeans.get(position).photoTag;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String name = DataTest.daibanName[position];
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("type", position + "");
                jumpToActivity(DaiBanTypeVPActivity.class, bundle);
            }
        });
    }

    @Override
    protected void lazyLoad() {

    }

    private void getDaiBan() {
        HttpRequest.get(ctx).url(ServerInterface.daiBanSetting)
                .params("dikuaiId", Constant.projectId)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            DaibanBean daibanBean = new Gson().fromJson(result.toString(), DaibanBean.class);
                            if (UiUtils.isEmpty(daibanBean.toString())) {
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
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(ctx, "请求失败-服务器错误");

                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (TextUtils.equals(event.toString(), Constant.REFRESH_DAIBAN)) {
            getDaiBan();
        }
    }

}
