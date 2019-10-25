package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.GCLDListItemBean;
import com.haozhiyan.zhijian.utils.ImageRequest;

import java.util.List;

public class GcldNewAdapter extends BaseQuickAdapter<GCLDListItemBean, BaseViewHolder> {
    private Context context;

    public GcldNewAdapter(Context context, @Nullable List<GCLDListItemBean> data) {
        super(R.layout.new_project_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GCLDListItemBean item) {
        new ImageRequest(context).get(item.listPicture.get(0), (ImageView) helper.getView(R.id.itemView));
        new ImageRequest(context).get(item.headPortrait, (ImageView) helper.getView(R.id.userHead));
        helper.setText(R.id.tv_name, item.theme)
                .setText(R.id.tv_local, item.lightspotType + item.projectName)
                .setText(R.id.tv_fuZeRenInfo, item.peopleuser + item.creatorTime)
                .setText(R.id.tv_brower, item.onlookerNum)
                .setText(R.id.tv_dianzanLiang, item.likeNum);
        if (TextUtils.equals(item.like, "是")) {
            helper.setImageResource(R.id.iv_dianZan, R.drawable.img_dianzan_preeesd);
        } else {
            helper.setImageResource(R.id.iv_dianZan, R.drawable.img_dianzan_normal);
            helper.addOnClickListener(R.id.iv_dianZan);
        }

    }


}
