package com.haozhiyan.zhijian.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.haozhiyan.zhijian.R;
import com.xujiaji.happybubble.BubbleDialog;

/**
 * Created by WangZhenKai on 2019/4/28.
 * Describe: Ydzj_project
 */
public class CustomDialog extends BubbleDialog implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private OnClickCustomButtonListener mListener;

    public CustomDialog(Context context) {
        super(context);
        calBar(true);
        setTransParentBackground();
        setPosition(Position.TOP);
        View rootView = LayoutInflater.from(context).inflate(R.layout.label_window, null);
        mViewHolder = new ViewHolder(rootView);
        addContentView(rootView);
        mViewHolder.ivChat.setOnClickListener(this);
        mViewHolder.ivCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_chat:
                if (mListener != null) {
                    mListener.onChatClick();
                }
                break;
            case R.id.iv_call:
                if (mListener != null) {
                    mListener.onCallClick();
                }
                break;
                default:
                    break;
        }
    }

    private static class ViewHolder {
        ImageView ivChat, ivCall;

        public ViewHolder(View rootView) {
            ivChat = rootView.findViewById(R.id.iv_chat);
            ivCall = rootView.findViewById(R.id.iv_call);
        }
    }

    public void setClickListener(OnClickCustomButtonListener l) {
        this.mListener = l;
    }

    public interface OnClickCustomButtonListener {
        void onChatClick();
        void onCallClick();
    }
}
