package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/5.
 * Describe: Ydzj_project
 */
public class AngleSearchAdapter extends CommonAdapter<String> {

    public AngleSearchAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, String data, int position) {
        ((SimpleDraweeView)viewHolder.getView(R.id.angleView)).setImageURI(DataTest.imgUrl);
        ((TextView)viewHolder.getView(R.id.name)).setText(data);
    }
}
