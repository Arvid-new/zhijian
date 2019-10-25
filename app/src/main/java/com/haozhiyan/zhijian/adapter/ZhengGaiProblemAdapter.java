package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.NewZhengGaiListBean;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.ListUtils;

import java.io.File;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/13.
 * Describe: Ydzj_project
 */
public class ZhengGaiProblemAdapter extends BaseQuickAdapter<NewZhengGaiListBean.ListBean.ListAbarbeitungBean, BaseViewHolder> {
    private Context context;

    public ZhengGaiProblemAdapter(Context context, @Nullable List<NewZhengGaiListBean.ListBean.ListAbarbeitungBean> data) {
        super(R.layout.jian_cha_commit_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, NewZhengGaiListBean.ListBean.ListAbarbeitungBean item) {
        if ("草稿".equals(item.ztCondition)) {
            if (ListUtils.listEmpty(item.listArbeitungPicture)) {
                new ImageRequest(context).getFile(new File(item.listArbeitungPicture.get(0)), (ImageView) helper.getView(R.id.imgView));
            }
        } else {
            if (ListUtils.listEmpty(item.listIssuePicture)) {
                new ImageRequest(context).get(item.listIssuePicture.get(0), (ImageView) helper.getView(R.id.imgView));
            }
        }
        String problemStatus = "";
        if ("一般".equals(item.orderOfSeverity) || TextUtils.isEmpty(item.orderOfSeverity)) {
            helper.getView(R.id.tvStatus).setVisibility(View.GONE);
        } else {
            problemStatus = "急";
            helper.getView(R.id.tvStatus).setVisibility(View.VISIBLE);
            helper.getView(R.id.tvStatus).setBackgroundResource(R.drawable.red_back_shape);
        }
        try {
            if (item.sendBackNumber > 0) {
                helper.getView(R.id.tvBack).setVisibility(View.VISIBLE);
                helper.getView(R.id.tvBack).setBackgroundResource(R.drawable.yellow_back_shape);
            } else {
                helper.getView(R.id.tvBack).setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        helper.setText(R.id.tvName, item.itemsName)
                .setText(R.id.tvDesc, item.description)
                .setText(R.id.tvJCStatus, item.ztCondition)
                .setText(R.id.tvTime, item.creatorTime + "  " + (item.siteName == null ? "" : item.siteName))
                .setText(R.id.tvStatus, problemStatus);
        switch (item.ztCondition) {
            case "待整改":
                helper.setTextColor(R.id.tvJCStatus, ContextCompat.getColor(context, R.color.red2));
                break;
            case "非正常关闭":
                helper.setTextColor(R.id.tvJCStatus, ContextCompat.getColor(context, R.color.c6_color));
                break;
            case "待复验":
                helper.setTextColor(R.id.tvJCStatus, ContextCompat.getColor(context, R.color.orange));
                break;
            case "已通过":
                helper.setTextColor(R.id.tvJCStatus, ContextCompat.getColor(context, R.color.green2));
                break;
            case "草稿":
                helper.setTextColor(R.id.tvJCStatus, ContextCompat.getColor(context, R.color.gray8));
                break;
            default:
                break;
        }
    }
}
