package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormGXYJResult;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJAllTowerBean;

import java.util.List;
/**
 * 楼层适配器
 * */
public class TowerAdapter extends BaseQuickAdapter<FormGXYJResult.TowerBean,BaseViewHolder> {
    private Context context;
    private int position = -1;
    public TowerAdapter(Context context, @Nullable List<FormGXYJResult.TowerBean> data) {
        super(R.layout.gxyj_room_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, FormGXYJResult.TowerBean item) {
        TextView textView = helper.getView(R.id.roomName);
        textView.setText(item.getTowerName());
        if(position == helper.getAdapterPosition() ){
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.shape_selected_blue));
            textView.setTextColor(ContextCompat.getColor(context,R.color.white_));
        }else{
            textView.setBackground(ContextCompat.getDrawable(context,R.drawable.gray_wash_2radius_back));
            textView.setTextColor(ContextCompat.getColor(context,R.color.black2));
        }
    }
    public void setSelectStatus(int position) {
        this.position = position;
        notifyDataSetChanged();
    }
}
