package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.DataTest;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe:报表
 */
public class FormsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;

    public FormsListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.home_forms_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        itemHolder.bindHolder(position);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getItemCount() {
        return DataTest.formsName.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItemBack;
        private TextView tvDoType;
        private TextView tvDoContent;
        private ImageView ivTypeIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            llItemBack = itemView.findViewById(R.id.ll_item_back);
            tvDoType = itemView.findViewById(R.id.tv_do_type);
            tvDoContent = itemView.findViewById(R.id.tv_do_content);
            ivTypeIcon = itemView.findViewById(R.id.iv_type_icon);
        }

        public void bindHolder(int position) {
            llItemBack.setBackgroundResource(DataTest.formsBack[position]);
            ivTypeIcon.setImageResource(DataTest.formsIcon[position]);
            tvDoType.setText(DataTest.formsName[position]);
            tvDoContent.setText("本月新增0个问题，已整改0个");
        }
    }
}
