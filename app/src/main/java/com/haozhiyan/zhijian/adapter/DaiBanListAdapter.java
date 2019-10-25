package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.DaibanBean;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.utils.ImageRequest;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe:待办
 */
public class DaiBanListAdapter extends BaseQuickAdapter<DaibanBean.ModulesDataBean, BaseViewHolder> {

    private Context ctx;

    public DaiBanListAdapter(@Nullable List<DaibanBean.ModulesDataBean> data, Context context) {
        super(R.layout.home_daiban_list_item, data);
        this.ctx = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DaibanBean.ModulesDataBean item) {
        try {
            helper.setBackgroundRes(R.id.ll_item_back, DataTest.daibanBack[helper.getLayoutPosition()]);
            //helper.setImageResource(R.id.iv_type_icon,DataTest.daibanIcon[helper.getLayoutPosition()]);
            new ImageRequest(ctx).get(item.iciconApp, (ImageView) helper.getView(R.id.iv_type_icon));
            helper.setText(R.id.tv_do_type, item.name)
                    .setText(R.id.tv_do_content, getYbCS(item.name, item))
                    .setText(R.id.tv_status, getDB(item.name, item));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getYbCS(String name, DaibanBean.ModulesDataBean bean) {
        String content = "";
        if ("现场检查".equals(name)) {
            content = bean.xcjcyiban + "已办\t" + bean.xcjcchaosong + "抄送";
        } else if ("实测实量".equals(name)) {
            content = bean.yiban + "已办\t" + bean.chaosong + "抄送";
        } else if ("工序移交".equals(name)) {
            content = bean.yiban + "已办\t" + bean.chaosong + "抄送";
        } else if ("材料验收".equals(name)) {
            content = bean.clysyiban + "已办\t" + bean.clyschaosong + "抄送";
        }
        return content;
    }

    private String getDB(String name, DaibanBean.ModulesDataBean bean) {
        String content = "";
        if ("现场检查".equals(name)) {
            content = bean.xcjcdaiban + "待办";
        } else if ("实测实量".equals(name)) {
            content = bean.daiban + "待办";
        } else if ("工序移交".equals(name)) {
            content = bean.daiban + "待办";
        } else if ("材料验收".equals(name)) {
            content = bean.clysdaiban + "待办";
        }
        return content;
    }
}
