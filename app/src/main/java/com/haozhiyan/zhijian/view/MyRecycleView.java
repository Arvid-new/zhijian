package com.haozhiyan.zhijian.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Create by WangZhenKai at 2019/3/29
 */
public class MyRecycleView extends RecyclerView {

    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec
                , MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST));
    }
}
