package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.HelpProblemItem;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/24.
 * Describe:
 */
public class HelpTextAdapter extends CommonAdapter<HelpProblemItem>{

    public HelpTextAdapter(Context context, List<HelpProblemItem> list) {
        super(context, list, R.layout.help_item_child);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, HelpProblemItem data, int position) {
        ((TextView)viewHolder.getView(R.id.tv_child)).setText(data.helpName);
    }
}
