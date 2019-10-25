package com.haozhiyan.zhijian.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 等宽 防止滑动冲突的 webview
 */
public class RoundWebView extends WebView {

    public NestedScrollView parentScrollView;
    private boolean isParentScrollEnable = false;// true 焦点在本view  false 焦点在父view

    private boolean isParentViewUp = false;//父view 上次滑动状态结束时的状态
    private boolean isTop = false;
    private boolean isBottom = false;


    public RoundWebView(Context context) {
        super(context);
    }

    public RoundWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public RoundWebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setParentScrollView(NestedScrollView parentScrollView) {
        this.parentScrollView = parentScrollView;
        if (parentScrollView != null) {
            parentScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    //上划 还是下滑
                    if (scrollY > oldScrollY) {
                        isParentViewUp = true;
                    } else {
                        isParentViewUp = false;
                    }
//                    LogUtils.d("parentScrollView", RoundWebView.this.parentScrollView.getMeasuredHeight() + "----" + scrollY + "-------" + oldScrollY);
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        LogUtils.d("event", event.getX() + "--" + event.getY());
        //当两根手指缩放 或 手指刚放到屏幕上的时候 拦截焦点
        if (event.getPointerCount() >= 2 || event.getAction() == MotionEvent.ACTION_DOWN) {
            isParentScrollEnable = true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            //当父view 上次滑动状态结束时的状态上划的时候
            if (!isParentViewUp) {
                //本view 在顶部 就拦截焦点
                if (isTop) {
                    isParentScrollEnable = true;
                }
                //本view在底部 就释放焦点
                if (isBottom) {
                    isParentScrollEnable = false;
                }
            } else {  //当父view 下划的时候
                //本view 在顶部 就释放焦点
                if (isTop) {
                    isParentScrollEnable = false;
                }
                //本view在底部 就拦截焦点
                if (isBottom) {
                    isParentScrollEnable = true;
                }
            }

            //当 同时在顶部 和在底部时 说明 webview 被缩放在最小的状态 释放焦点
            if (isTop && isBottom) {
                isParentScrollEnable = false;
            }
        }
//        LogUtils.d("touch--------isParentViewUp", isParentViewUp +
//                "  isTop-" + isTop + "  isBottom-" + isBottom + "  isParentScrollEnable-" + isParentScrollEnable);
        if (parentScrollView != null) {
            parentScrollView.requestDisallowInterceptTouchEvent(isParentScrollEnable);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // webview的高度
        int webcontent = (int) (getContentHeight() * getScale());
        // 当前webview的高度
        int webnowheight = getHeight() + getScrollY();

        isTop = t <= 0;
        isBottom = Math.abs(webcontent - webnowheight) <= 2;
    }


}
