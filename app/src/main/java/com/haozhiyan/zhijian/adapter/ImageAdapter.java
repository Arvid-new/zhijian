package com.haozhiyan.zhijian.adapter;

import android.content.Context;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/29.
 * Describe: Ydzj_project
 */
public class ImageAdapter extends CommonAdapter<String> {

    public ImageAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, String data, int position) {
        ((SimpleDraweeView)viewHolder.getView(R.id.photoImages)).setImageURI(data);
    }
}
