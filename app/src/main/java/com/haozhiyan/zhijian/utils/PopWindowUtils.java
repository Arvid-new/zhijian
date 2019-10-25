package com.haozhiyan.zhijian.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.widget.CustomDialog;
import com.haozhiyan.zhijian.widget.CustomJCzYDialog;
import com.xujiaji.happybubble.BubbleLayout;

/**
 * Created by WangZhenKai on 2019/4/28.
 * Describe: Ydzj_project
 */
public class PopWindowUtils {

    private OnClickButtonListener mListener;
    private static PopWindowUtils popWindowUtils;

    public static PopWindowUtils getPopWindow() {
        if (popWindowUtils == null) {
            popWindowUtils = new PopWindowUtils();
        }
        return popWindowUtils;
    }

    public void showPopWindow(Context context, View view, int offsetX, int offsetY) {
        BubbleLayout bl = new BubbleLayout(context);
        //气泡背景颜色
        bl.setBubbleColor(UiUtils.getColor(R.color.translate_ban3));
        //气泡边框颜色
        //bl.setShadowColor(Color.RED);
        //气泡拉伸长度
        //bl.setLookLength(Util.dpToPx(this, 54));
        //气泡拉伸宽度
        //bl.setLookWidth(Util.dpToPx(this, 48));
        CustomDialog customDialog = new CustomDialog(context);
        customDialog.setClickedView(view);
        customDialog.setBubbleLayout(bl);
        customDialog.setOffsetX(offsetX);
        customDialog.setOffsetY(offsetY);
        customDialog.setClickListener(new CustomDialog.OnClickCustomButtonListener() {

            @Override
            public void onChatClick() {
                if (mListener != null) {
                    mListener.chatSay();
                }
            }

            @Override
            public void onCallClick() {
                if (mListener != null) {
                    mListener.callPhone();
                }
            }
        });
        customDialog.show();
    }

    public CustomDialog showPop(Context context, View view, int offsetX, int offsetY) {
        BubbleLayout bl = new BubbleLayout(context);
        bl.setBubbleColor(context.getResources().getColor(R.color.translate_ban3));
        CustomDialog customDialog = new CustomDialog(context);
        customDialog.setClickedView(view);
        customDialog.setBubbleLayout(bl);
        customDialog.setOffsetX(offsetX);
        customDialog.setOffsetY(offsetY);
        return customDialog;
    }

    public void setClickListener(OnClickButtonListener l) {
        this.mListener = l;
    }

    public interface OnClickButtonListener {
        void chatSay();

        void callPhone();
    }

    /**
     * @param activity
     * @param view     被点击的控件view
     * @param offsetX  左偏移量
     * @param offsetY  右偏移量
     * @param name
     * @param content
     * @return
     */
    public void showJCzy(Activity activity, View view, int offsetX, int offsetY, String name, String content) {
        BubbleLayout bl = new BubbleLayout(activity);
        bl.setBubbleColor(UiUtils.getColor(R.color.white));
        final CustomJCzYDialog customDialog = new CustomJCzYDialog(activity, name, content);
        customDialog.setClickedView(view);
        customDialog.setBubbleLayout(bl);
        customDialog.setOffsetX(offsetX);
        customDialog.setOffsetY(offsetY);
        customDialog.setClickListener(new CustomJCzYDialog.OnClickBackListener() {
            @Override
            public void onClickBack() {
                customDialog.dismiss();
            }
        });
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.cancel();
        } else {
            customDialog.show();
        }
    }
}
