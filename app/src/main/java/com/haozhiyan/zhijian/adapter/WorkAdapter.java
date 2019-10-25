package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.WorkPageBean;
import com.haozhiyan.zhijian.utils.ImageRequest;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/25.
 * Describe:
 */
public class WorkAdapter extends BaseQuickAdapter<WorkPageBean.ModulesDataBean, BaseViewHolder> {

    private Context ctx;

    public WorkAdapter(@Nullable List<WorkPageBean.ModulesDataBean> data, Context context) {
        super(R.layout.work_layout_item, data);
        this.ctx = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, WorkPageBean.ModulesDataBean item) {
        helper.setText(R.id.tv_name, item.name);
        new ImageRequest(ctx).get(item.iconApp, (ImageView) helper.getView(R.id.iv_icon));
    }
}
