package com.haozhiyan.zhijian.activity.reportforms.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.FormGXYJWebViewActivity;
import com.haozhiyan.zhijian.activity.reportforms.GXYJTableActivity;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormGXYJResult;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllTowerBean;
import com.haozhiyan.zhijian.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表-工序移交首页适配器
 * */
public class FormGXYJAdapter extends BaseQuickAdapter<FormGXYJResult,BaseViewHolder> {
    private Context context;
    private RecyclerView rcv_tower;
    private RecyclerView rcv_unit;
    private TowerAdapter towerAdapter;
    private UnitAdapter unitAdapter;

    public FormGXYJAdapter(Context context, @Nullable List<FormGXYJResult> data) {
        super(R.layout.item_form_gxyj_bd, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, final FormGXYJResult item) {
        helper.setText(R.id.tv_dikuai,item.getSectionName());
        rcv_tower = helper.getView(R.id.rcv_tower);
        rcv_unit = helper.getView(R.id.rcv_unit);
        rcv_tower.setLayoutManager(new GridLayoutManager(context,3));
        rcv_unit.setLayoutManager(new GridLayoutManager(context,3));

        if(ListUtils.listEmpty(item.getTower())){
            towerAdapter = new TowerAdapter(context,item.getTower());
            rcv_tower.setAdapter(towerAdapter);
            towerAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, final int pos) {
                    towerAdapter.setSelectStatus(pos);
                    final List<FormGXYJResult.TowerBean.UnitBean> units = item.getTower().get(pos).getUnit();
                    if(ListUtils.listEmpty(units)){
                        unitAdapter = new UnitAdapter(context, units);
                        rcv_unit.setAdapter(unitAdapter);
                        unitAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                Intent intent =new Intent(context,FormGXYJWebViewActivity.class);
                                intent.putExtra("sectionId",item.getSectionId()+"");
                                intent.putExtra("sectionName",item.getSectionName());
                                intent.putExtra("unitId",units.get(position).getUnitId()+"");
                                intent.putExtra("unitName",units.get(position).getUnitName());
                                intent.putExtra("towerId",item.getTower().get(pos).getTowerId()+"");
                                intent.putExtra("towerName",item.getTower().get(pos).getTowerName());
                                context.startActivity(intent);
                            }
                        });
                    }
                }
            });
        }
    }
}