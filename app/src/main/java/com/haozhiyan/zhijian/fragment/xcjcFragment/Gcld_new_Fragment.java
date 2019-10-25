package com.haozhiyan.zhijian.fragment.xcjcFragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.GCLDDetailActivity;
import com.haozhiyan.zhijian.adapter.GcldNewAdapter;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.GCLDListItemBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.widget.MyDividerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 最新列表
 */
public class Gcld_new_Fragment extends BaseFragment {

    private RecyclerView rvDataList;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private GcldNewAdapter adapter;
    private int page = 1;
    private SwipeRefreshLayout swipLayout;

    @Override
    public int getLayoutResId() {
        return R.layout.gcld_new_layout;
    }

    @Override
    public void initView(View view) {
        rvDataList = getOutView(view, R.id.rv_dataList);
        swipLayout = getOutView(view, R.id.swipLayout);
//        swipeRefreshLayout.setColorSchemeColors(setColor(R.color.red));
        rvDataList.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
        rvDataList.addItemDecoration(new MyDividerItem(ctx, MyDividerItem.VERTICAL));
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if ("addGCLDSuccess".equals(event)) {
            page = 1;
            listLightspotZx();
        }

    }


    @Override
    public void initListener() {
        swipLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                listLightspotZx();
            }
        });
    }

    private int zanItem;

    @Override
    public void initData(boolean isNetWork) {
        adapter = new GcldNewAdapter(getContext(), null);
        rvDataList.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.iv_dianZan) {
                    if (adapter.getData().get(position) instanceof GCLDListItemBean) {
                        zanItem = position;
                        GCLDListItemBean listItemBean = (GCLDListItemBean) adapter.getData().get(position);
                        saveLike(listItemBean.id);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), GCLDDetailActivity.class);
                if (adapter.getData().get(position) instanceof GCLDListItemBean) {
                    GCLDListItemBean listItemBean = (GCLDListItemBean) adapter.getData().get(position);
                    intent.putExtra("id", listItemBean.id);
                }
                jumpToActivity(intent);
            }
        });
        adapter.setEnableLoadMore(true);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                listLightspotZx();
            }
        }, rvDataList);

        listLightspotZx();
    }

    @Override
    protected void lazyLoad() {

    }


    /**
     * 获取最新列表
     */
    private void listLightspotZx() {
        HttpRequest.get(getActivity()).url(ServerInterface.listLightspotZx)
                .params("pageSize", "10")
                .params("pageNo", page)
                .doGet(new HttpObjectCallBack<GCLDListItemBean>(GCLDListItemBean.class) {
                           @Override
                           public void onSuccess(BaseBean<GCLDListItemBean> result) {
                               try {
                                   swipLayout.setRefreshing(false);
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                               if (result.code.equals("0")) {
                                   if (result.list != null && result.list.size() > 0) {
                                       if (page == 1) {
                                           adapter.setNewData(result.list);
                                       } else {
                                           adapter.addData(result.list);
                                       }
                                       adapter.loadMoreComplete();
                                   } else {
                                       adapter.loadMoreEnd();
                                   }

                               } else {
                                   ToastUtils.myToast(getContext(), result.msg);
                               }
                           }

                           @Override
                           public void onFailure(int code, String msg) {
                               try {
                                   swipLayout.setRefreshing(false);
                               } catch (Exception e) {
                                   e.printStackTrace();
                               }
                           }
                       }
                );
    }

    /**
     * 点赞
     */
    private void saveLike(String id) {
        HttpRequest.get(getContext()).url(ServerInterface.saveLike)
                .params("plId", id)
                .params("userId", UserInfo.create(getContext()).getUserId())
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        JSONObject jsonObject = JSON.parseObject(result.toString());
                        if (jsonObject.getString("code").equals("0")) {

                            try {
                                if (adapter.getData().get(zanItem) instanceof GCLDListItemBean) {
                                    try {
                                        GCLDListItemBean listItemBean = (GCLDListItemBean) adapter.getData().get(zanItem);
                                        int likeNum = Integer.parseInt(listItemBean.likeNum);
                                        listItemBean.likeNum = (likeNum + 1) + "";
                                        listItemBean.like = "是";
                                        adapter.notifyItemChanged(zanItem);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }
}
