package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.utils.PopWindowUtils;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/28.
 * Describe: Ydzj_project
 */
public class LabelTextAdapter extends CommonAdapter<ItemBean> {

    private Activity act;
    private OnChatCallListener onChatCallListener;
    private String types;//批次列表  批次详情  详情复验人  详情其他人

    public LabelTextAdapter(Activity context, List<ItemBean> list, String type) {
        super(context, list, R.layout.label_layout_item);
        this.act = context;
        this.types = type;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, ItemBean data, final int position) {
        if (TextUtils.equals("xcJc_piCi_list", types)) {
            ((TextView) viewHolder.getView(R.id.tv_text)).setText(data.name);
        } else if (TextUtils.equals("xcJc_piCi_detail", types)) {
            ((TextView) viewHolder.getView(R.id.tv_text)).setText(data.peopleuser);
        } else if (TextUtils.equals("xcJc_problemDetail_fyPeople", types)) {
            ((TextView) viewHolder.getView(R.id.tv_text)).setText(data.peopleuser);
        } else if (TextUtils.equals("xcJc_problemDetail_OtherPeople", types)) {
            ((TextView) viewHolder.getView(R.id.tv_text)).setText(data.peopleuser);
        }
        final ImageView iv = viewHolder.getView(R.id.iv_window);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopWindowUtils.getPopWindow().showPopWindow(act, iv, 0, 10);
                PopWindowUtils.getPopWindow().setClickListener(new PopWindowUtils.OnClickButtonListener() {
                    @Override
                    public void chatSay() {
                        if (onChatCallListener != null) {
                            onChatCallListener.chat(position);
                        }
                    }

                    @Override
                    public void callPhone() {
                        if (onChatCallListener != null) {
                            onChatCallListener.call(position);
                        }
                    }
                });
            }
        });
    }

    public interface OnChatCallListener {
        void chat(int position);

        void call(int position);
    }

    public void setOnChatCallListener(OnChatCallListener chatCallListener) {
        this.onChatCallListener = chatCallListener;
    }
}
