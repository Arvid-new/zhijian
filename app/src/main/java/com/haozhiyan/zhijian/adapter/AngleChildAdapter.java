
package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.AngleJsonBean;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/30.
 * Describe: Ydzj_project
 */
public class AngleChildAdapter extends CommonAdapter<AngleJsonBean.DataBean.LogDOListBean> {

    private OnItemClickListener listener;

    public AngleChildAdapter(Context context, List<AngleJsonBean.DataBean.LogDOListBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, AngleJsonBean.DataBean.LogDOListBean data, final int position) {
        ((SimpleDraweeView) viewHolder.getView(R.id.angleView)).setImageURI(DataTest.imgUrl);
        ((TextView) viewHolder.getView(R.id.name)).setText(data.deviceName);
        ((CheckBox) viewHolder.getView(R.id.cb_check)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onItemChildClickListener(buttonView, position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemChildClickListener(View view, int index);
    }

    public void setChildItemClickListener(OnItemClickListener itemClickListener) {
        listener = itemClickListener;
    }
}
