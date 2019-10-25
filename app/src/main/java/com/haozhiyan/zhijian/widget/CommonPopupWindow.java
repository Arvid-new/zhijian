package com.haozhiyan.zhijian.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.AnimationUtil;

/**
 * 自定义弹窗
 */
public class CommonPopupWindow {
    protected Context context;
    protected View contentView;
    private ViewGroup containerView;
    //遮罩半透明View，点击可关闭DropDownMenu
    private View maskView;
    //遮罩颜色
    private int maskColor = 0x88888888;
    private FrameLayout frameLayout;
    private int[] contentViewSize;
    private boolean isShowing = false;

    private float contentViewHeight = 0f;//弹出布局高度占比 =0时 显示自身高度  >0时 按父布局比例显示
    private float contentViewMaxHeight = 0f;//弹出布局高度最大占父布局占比  显示高度= 自身高>(父高*占比) ？ (父高*占比) : 自身高

    /**
     * 初始化
     *
     * @param c
     * @param anchor    锚点控件
     * @param layoutRes 布局 id
     */
    public CommonPopupWindow(Context c, View anchor, int layoutRes) {
        context = c;
        if (anchor.getParent() instanceof RelativeLayout) {
            containerView = (ViewGroup) anchor.getParent();
        } else if (anchor.getParent() instanceof LinearLayout) {
            try {
                //把锚点view 从它的父viewgroup中摘出来 放到 new RelativeLayout 中 在添加到父viewgroup中进行替换
                LinearLayout parentView = (LinearLayout) anchor.getParent();
                int position = 0;
                for (int i = 0; i < parentView.getChildCount(); i++) {
                    if (parentView.getChildAt(i) == anchor) {
                        position = i;
                        break;
                    }
                }
                parentView.removeViewAt(position);
                containerView = new RelativeLayout(c);
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                containerView.setLayoutParams(params);
                containerView.addView(anchor);
                parentView.addView(containerView, position);
            } catch (Exception e) {
                containerView = (ViewGroup) anchor.getParent();
                e.printStackTrace();
            }
        }
        contentView = LayoutInflater.from(c).inflate(layoutRes, (ViewGroup) anchor.getParent(), false);
        contentView.setVisibility(View.GONE);
        maskView = new View(c);
        maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        maskView.setBackgroundColor(maskColor);
        maskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });
        maskView.setVisibility(View.GONE);

        frameLayout = new FrameLayout(c);
        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.addView(maskView, 0);
        frameLayout.addView(contentView, 1);
        containerView.addView(frameLayout);
    }


    public View getContentView() {
        return contentView;
    }

    public void setContentViewHeight(float contentViewHeight) {
        this.contentViewHeight = contentViewHeight;
    }

    public void setContentViewMaxHeight(float contentViewMaxHeight) {
        this.contentViewMaxHeight = contentViewMaxHeight;
    }

    public boolean isShowing() {
        return isShowing;
    }

    public void show() {
        if (isShowing) {
            closeMenu();
        } else {
            isShowing = true;
            AnimationUtil.getInstance().animateOpen(contentView, getOpenHeight());
            maskView.setVisibility(View.VISIBLE);
            maskView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.dd_mask_in));
        }
    }

    private int getOpenHeight() {
        contentViewSize = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        contentView.measure(width, height);
        contentViewSize[0] = contentView.getMeasuredWidth();
        contentViewSize[1] = contentView.getMeasuredHeight();

        int containerViewHeight = containerView.getBottom();

        if (contentViewMaxHeight > 0) {
            return contentViewSize[1] > (int) (containerViewHeight * contentViewMaxHeight)
                    ? (int) (containerViewHeight * contentViewMaxHeight) : contentViewSize[1];
        }

        if (contentViewHeight <= 0) {
            return contentViewSize[1];
        } else {
            return (int) (containerViewHeight * contentViewHeight);
        }
    }

    public void closeMenu() {
        isShowing = false;
        AnimationUtil.getInstance().animateClose(contentView);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.dd_mask_out);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                maskView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        maskView.setAnimation(animation);
    }


}
