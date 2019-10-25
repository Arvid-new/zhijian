package com.haozhiyan.zhijian.fragment.xcjcFragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.workScsl.JCXRoomDetailActivity;
import com.haozhiyan.zhijian.adapter.SCSLCollectDetailAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/22.
 * Describe: 施工单位
 */
public class SgdwFragment extends BaseFragment {

    private RecyclerView rvDataList;
    private SCSLCollectDetailAdapter adapter;
    private String floorUnit = "";

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_layout2;
    }

    @Override
    public void initView(View view) {
        rvDataList = getOutView(view, R.id.rv_dataList);
        rvDataList.setLayoutManager(new GridLayoutManager(ctx, 3));
        rvDataList.addItemDecoration(new GridSpacingItemDecoration(3, 8, false));
        adapter = new SCSLCollectDetailAdapter(R.layout.scsl_grid_item, null);
        rvDataList.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("room", ((List<ItemBean>) DataTest.getSd()).get(position).name);
                bundle.putString("floorUnit",floorUnit);
                jumpToActivity(JCXRoomDetailActivity.class,bundle);
            }
        });
    }

    @Override
    public void initData(boolean isNetWork) {
        adapter.setNewData(DataTest.getSd());
    }

    @Override
    protected void lazyLoad() {
    }

    public void floorUnitRoom(String s) {
        floorUnit = s;
    }
}
