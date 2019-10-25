package com.haozhiyan.zhijian.activity.reportforms.adapter;


import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormGXYJResult;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllTowerBean;

import java.util.List;
/**
 * 单元适配器
 * */
public class UnitAdapter extends BaseQuickAdapter<FormGXYJResult.TowerBean.UnitBean,BaseViewHolder> {
    private Context context;

    public UnitAdapter(Context context, @Nullable List<FormGXYJResult.TowerBean.UnitBean> data) {
        super(R.layout.gxyj_room_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FormGXYJResult.TowerBean.UnitBean item) {
        helper.setText(R.id.roomName,item.getUnitName());
    }
}