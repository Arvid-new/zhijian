package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/27.
 * Describe: Ydzj_project
 */
public class JianChaPiCiAdapter extends CommonAdapter<ItemBean> {

    private OnChildClickListener listener;
    private Context context;
    private int index = -1;

    public JianChaPiCiAdapter(Context context, List<ItemBean> list, int id) {
        super(context, list, id);
        this.context = context;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ItemBean data, final int position) {
        CheckBox cb = (CheckBox) viewHolder.getView(R.id.cb_select);
        ((TextView) viewHolder.getView(R.id.tv_text)).setText(data.name);
        if (index == position) {
            cb.setChecked(true);
        } else {
            cb.setChecked(false);
        }
        ((TextView) viewHolder.getView(R.id.tv_detail)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemChildClickListener(v, position);
                }
            }
        });
    }

    public interface OnChildClickListener {
        void onItemChildClickListener(View view, int position);
    }

    public void setItemClickListener(OnChildClickListener itemClickListener) {
        listener = itemClickListener;
    }

    public void setCheck(int position) {
        this.index = position;
        notifyDataSetChanged();
    }
}
