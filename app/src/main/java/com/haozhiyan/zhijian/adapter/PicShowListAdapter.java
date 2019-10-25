package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.PVAUtils;

import java.util.List;

public class PicShowListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context context;

    public PicShowListAdapter(@Nullable List<String> data, Context context) {
        super(R.layout.zhenggaipicitem, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        if (PVAUtils.getFileLastType(item).equals("image/jpeg")) {
            helper.getView(R.id.play_img).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.play_img).setVisibility(View.VISIBLE);
        }
        new ImageRequest(context).get((String) item, (ImageView) helper.getView(R.id.img));

    }
}
