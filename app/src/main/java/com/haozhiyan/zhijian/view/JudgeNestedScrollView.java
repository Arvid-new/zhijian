package com.haozhiyan.zhijian.view;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.haozhiyan.zhijian.utils.LogUtils;

/**
 * Created by WangZhenKai on 2019/5/24.
 * Describe: Ydzj_project
 */
public class JudgeNestedScrollView  extends NestedScrollView {

    private boolean isNeedScroll = true;
    private float xDistance, yDistance, xLast, yLast;
    private int scaledTouchSlop;

    public JudgeNestedScrollView(Context context) {
        super(context, null);
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public JudgeNestedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                LogUtils.e("SiberiaDante", "xDistance ：" + xDistance + "---yDistance:" + yDistance);
                return !(((xDistance >= yDistance) || yDistance < scaledTouchSlop) || (xDistance < 10 && yDistance < 10)) && isNeedScroll;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /*
    该方法用来处理NestedScrollView是否拦截滑动事件
     */
    public void setNeedScroll(boolean isNeedScroll) {
        this.isNeedScroll = isNeedScroll;
    }
}
