package com.haozhiyan.zhijian.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * 等宽的 imgview
 */
public class AequilateImgView extends android.support.v7.widget.AppCompatImageView {
    public AequilateImgView(Context context) {
        super(context);
    }

    public AequilateImgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AequilateImgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
