package com.haozhiyan.zhijian.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by WangZhenKai on 2019/6/27.
 * Describe: Ydzj_project
 */
@SuppressLint("AppCompatCustomView")
public class MyTextView extends TextView {
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTextView(Context context) {
        super(context);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
    }

    public void setText(String text) {
        try {
            setText(text, BufferType.NORMAL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
