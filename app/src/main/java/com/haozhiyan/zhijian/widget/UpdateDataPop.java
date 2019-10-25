package com.haozhiyan.zhijian.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.UiUtils;

/**
 * Created by WangZhenKai on 2019/7/18.
 * 当前类在其它地方使用直接调用即可（公共类提示弹窗）
 */
public class UpdateDataPop extends Dialog {

    private TextView tvCancel; //取消按钮
    private TextView tvSure; //确定按钮
    private TextView textTitle;//提示标题，可设置
    private TextView etupdateContent;
    public String titleStr = "修改";//外界弹窗提示内容设置
    private String yesStr, noStr;//确定文本和取消文本的显示内容
    private int yesBackColor, noBackColor;//确定取消安妮背景色设置
    private OnclickListener listener;//取消按钮被点击了的监听器

    public void setOnclickListener(String yesTitle,String noTitle, OnclickListener clickListener) {
        this.listener = clickListener;
        this.yesStr = yesTitle;
        this.noStr = noTitle;
    }

    public UpdateDataPop(Context context) {
        super(context);
    }

    public UpdateDataPop(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected UpdateDataPop(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.wfh_input_update_xml);
        getWindow().findViewById(R.id.rl_dialog).getBackground().setAlpha(150);
        //初始化界面控件
        initView();
        //初始化界面展示数据
        initData();
        //初始化界面控件的事件
        initEvent();
    }

    private void initView() {
        tvSure = (TextView) findViewById(R.id.tv_sure);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        textTitle = (TextView) findViewById(R.id.text_title);
        etupdateContent = (EditText) findViewById(R.id.et_update_Content);
        setYesBackColor(R.drawable.xk_dialog_btn_yellow_shape);
        setNoBackColor(R.drawable.xk_dialog_btn_gray_shape);
        setYesBtnText("确定");
        setNoBtnText("取消");
    }

    private void initData() {
        setTitle();
        setYesBtnText();
        setNoBtnText();
        setYesBackColor();
        setNoBackColor();
    }

    private void initEvent() {
        getWindow().findViewById(R.id.rl_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        //设置确定按钮被点击后，向外界提供监听
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onYesClick(UiUtils.getContent(etupdateContent));
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();
            }
        });
    }


    //设置弹窗提示内容
    private void setTitle() {
        //如果用户自定了title
        if (titleStr != null) {
            textTitle.setText(titleStr);
        }
    }

    //设置弹窗按钮1文本
    private void setYesBtnText() {
        if (yesStr != null) {
            tvSure.setText(yesStr);
        }
    }

    //设置弹窗按钮2文本
    private void setNoBtnText() {
        if (noStr != null) {
            tvCancel.setText(noStr);
        }
    }

    //设置弹窗按钮1背景色
    private void setYesBackColor() {
        if (yesBackColor >= 0) {
            tvSure.setBackgroundResource(yesBackColor);
        }
    }

    //设置弹窗按钮2背景色
    private void setNoBackColor() {
        if (yesBackColor >= 0) {
            tvCancel.setBackgroundResource(noBackColor);
        }
    }

    /**
     * 从外界Activity为Dialog设置标题
     *
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 设置确定按钮的文本
     *
     * @param yesText
     */
    public void setYesBtnText(String yesText) {
        yesStr = yesText;
    }

    /**
     * 设置取消按钮的文本
     *
     * @param noText
     */
    public void setNoBtnText(String noText) {
        noStr = noText;
    }

    /**
     * 设置确定按钮颜色
     *
     * @param color
     */
    public void setYesBackColor(int color) {
        yesBackColor = color;
    }

    /**
     * 设置取消按钮颜色
     *
     * @param color
     */
    public void setNoBackColor(int color) {
        noBackColor = color;
    }

    /**
     * 设置确定按钮被点击的接口
     */
    public interface OnclickListener {
        void onYesClick(String inputContent);
    }
}
