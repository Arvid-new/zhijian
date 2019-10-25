package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.JCCommitBean;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/28.
 * Describe: Ydzj_project
 */
public class JCCommitAdapter extends BaseQuickAdapter<JCCommitBean.DataBean.ProblemListBean, BaseViewHolder> {

    private Context context;

    public JCCommitAdapter(int layoutResId, @Nullable List<JCCommitBean.DataBean.ProblemListBean> data, Context ctx) {
        super(layoutResId, data);
        context = ctx;
    }

    @Override
    protected void convert(BaseViewHolder helper, JCCommitBean.DataBean.ProblemListBean item) {
        new ImageRequest(context).get(item.problemImage, ((ImageView) helper.getView(R.id.imgView)));
//        String statusStr = "";
//        int resId = R.drawable.red_back_shape;
//        if (item.serious.equals("重要")) {
//            statusStr = "重";
//            resId = R.drawable.yellow_back_shape;
//        } else if (item.serious.equals("紧急")) {
//            statusStr = "急";
//            resId = R.drawable.red_back_shape;
//        } else if (item.serious.equals("要紧")) {
//            statusStr = "紧";
//            resId = R.drawable.red_back_shape;
//        }
//        helper.getView(R.id.tvBack).setVisibility(View.GONE);

//        helper.setGone(R.id.tvStatus, item.serious.equals("") || item.serious.equals("一般") ? false : true)
//                .setText(R.id.tvStatus, statusStr)
//                .setBackgroundRes(R.id.tvStatus, resId)
        helper.setText(R.id.tvName, item.inspectionName)
                .setText(R.id.tvDesc, item.particularsName + " " + item.particularsSupplement)
                .setText(R.id.tvTime, item.submitDate + " " + getTowerUnitInfo(item))
                .setText(R.id.tvJCStatus, StringUtils.getStatus(item.state))
                .setTextColor(R.id.tvJCStatus, StringUtils.getTextColor(item.state));
    }

    private String getTowerUnitInfo(JCCommitBean.DataBean.ProblemListBean bean) {
        if (StringUtils.isEmpty(bean.towerName) && StringUtils.isEmpty(bean.unitName) && StringUtils.isEmpty(bean.floorName)) {
            return "";
        } else if (!StringUtils.isEmpty(bean.towerName) && !StringUtils.isEmpty(bean.unitName) && !StringUtils.isEmpty(bean.floorName)) {
            return bean.towerName + bean.unitName + "单元" + bean.floorName + "层" + bean.roomName;
        } else if (!StringUtils.isEmpty(bean.towerName) && !StringUtils.isEmpty(bean.unitName) && StringUtils.isEmpty(bean.floorName)) {
            return bean.towerName + bean.unitName + "单元" + bean.roomName;
        } else if (!StringUtils.isEmpty(bean.towerName) && StringUtils.isEmpty(bean.unitName) && StringUtils.isEmpty(bean.floorName)) {
            return bean.towerName;
        } else if (StringUtils.isEmpty(bean.towerName)) {
            return "";
        } else {
            return "";
        }
    }
}
