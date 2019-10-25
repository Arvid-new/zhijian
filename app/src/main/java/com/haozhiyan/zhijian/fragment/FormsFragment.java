package com.haozhiyan.zhijian.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.FormsListAdapter;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.SystemUtils;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 报表
 */
public class FormsFragment extends BaseFragment implements View.OnClickListener {

    private TextView tv_select_name;
    private ImageView iv_select_icon;
    private LinearLayout ll_select;
    private RelativeLayout rl_hidden;
    private LinearLayout linear_close;
    private RecyclerView rv_formsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int mHiddenViewMeasuredHeight;
    private FormsListAdapter adapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_forms;
    }

    @Override
    public void initView(View view) {
        tv_select_name = getOutView(view, R.id.tv_select_name);
        iv_select_icon = getOutView(view, R.id.iv_select_icon);
        rv_formsList = getOutView(view, R.id.rv_formsList);
        rl_hidden = getOutView(view, R.id.rl_hidden);
        linear_close = getOutView(view, R.id.linear_close);
        swipeRefreshLayout = getOutView(view, R.id.swipeRefreshLayout);
        ll_select = getOutView(view, R.id.ll_select);
        mHiddenViewMeasuredHeight = SystemUtils.getPhoneScreenHight(ctx);
        linear_close.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,mHiddenViewMeasuredHeight*2/3));
        rv_formsList.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
        swipeRefreshLayout.setColorSchemeColors(setColor(R.color.red));
    }

    @Override
    public void initListener() {
        ll_select.setOnClickListener(this);
        rl_hidden.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData(boolean isNetWork) {
        adapter = new FormsListAdapter(ctx);
        rv_formsList.setAdapter(adapter);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select:
                if (rl_hidden.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(rl_hidden, mHiddenViewMeasuredHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv_select_icon);
                } else {
                    AnimationUtil.getInstance().animateClose(rl_hidden);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                }
                break;
            case R.id.rl_hidden:
                AnimationUtil.getInstance().animateClose(rl_hidden);
                AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                break;
            default:
                break;
        }
    }
}
