package com.haozhiyan.zhijian.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by WangZhenKai on 2019/5/24.
 * Describe: Ydzj_project
 */
public class ScrollRecycleView  extends RecyclerView {

    private float lastX, lastY;

    public ScrollRecycleView(Context context) {
        super(context);
    }
    public ScrollRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        boolean intercept = super.onInterceptTouchEvent(e);

        switch (e.getAction()) {

            case MotionEvent.ACTION_DOWN:
                lastX = e.getX();
                lastY = e.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                // 只要横向大于竖向，就拦截掉事件
                float slopX = Math.abs(e.getX() - lastX);
                float slopY = Math.abs(e.getY() - lastY);
                if((slopX > 0 || slopY > 0) && slopX >= slopY){
                    requestDisallowInterceptTouchEvent(true);
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercept = false;
                break;
        }
        return intercept;
    }
}
