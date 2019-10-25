package com.haozhiyan.zhijian.widget;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.xujiaji.happybubble.BubbleDialog;

/**
 * Created by WangZhenKai on 2019/4/28.
 * Describe: 检查项详情弹窗实现
 */
public class CustomJCzYDialog extends BubbleDialog {

    private ViewHolder mViewHolder;
    private OnClickBackListener mListener;

    public CustomJCzYDialog(Activity context, String name, String content) {
        super(context);
        calBar(true);
        setTransParentBackground();
        setPosition(Position.BOTTOM);
        View rootView = LayoutInflater.from(context).inflate(R.layout.jczy_pop_window, null);
        mViewHolder = new ViewHolder(rootView);
        addContentView(rootView);
        setData(mViewHolder, name, content);
        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(SystemUtils.getPhoneScreenWidth(context), SystemUtils.getPhoneScreenHight(context) * 2 / 3);
        mViewHolder.llWindow.setLayoutParams(params2);
        mViewHolder.llWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClickBack();
                }
            }
        });
    }

    private static class ViewHolder {
        TextView tvName, tvTitle, tvContent;
        RelativeLayout llWindow;
        LinearLayout llWindowChild;

        public ViewHolder(View rootView) {
            tvName = rootView.findViewById(R.id.tvName);
            tvTitle = rootView.findViewById(R.id.tvTitle);
            tvContent = rootView.findViewById(R.id.tvContent);
            llWindow = rootView.findViewById(R.id.llWindow);
            llWindowChild = rootView.findViewById(R.id.llWindowChild);
        }
    }

    public void setClickListener(OnClickBackListener l) {
        this.mListener = l;
    }

    public interface OnClickBackListener {
        void onClickBack();
    }

    public void setData(ViewHolder viewHolder, String name, String content) {
        viewHolder.tvName.setText(name);
        viewHolder.tvContent.setText(content);
        viewHolder.tvTitle.setVisibility(View.GONE);
    }
}
