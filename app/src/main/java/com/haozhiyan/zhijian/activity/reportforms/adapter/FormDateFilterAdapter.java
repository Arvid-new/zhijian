package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

/**
 * 时间筛选适配器
 * */
public class FormDateFilterAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    private Context context;
    private int position = -1;
    public FormDateFilterAdapter(Context context, @Nullable List<String> data) {
        super(R.layout.item_date_filter, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        TextView textView = helper.getView(R.id.tv_name);
        textView.setText(item);
        if(position == helper.getAdapterPosition() ){
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_blue_selected_20));
            textView.setTextColor(ContextCompat.getColor(context,R.color.blue4));
        }else{
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_gray_corner));
            textView.setTextColor(ContextCompat.getColor(context,R.color.black3));
        }
    }
    public void setSelectStatus(int position) {
        this.position = position;
        notifyDataSetChanged();
    }
}
