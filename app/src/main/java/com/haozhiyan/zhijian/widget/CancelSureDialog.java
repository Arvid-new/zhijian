package com.haozhiyan.zhijian.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.SystemUtils;

/**
 * Create by WangZhenKai at 2019/04/23
 */
public class CancelSureDialog extends Dialog {

    private Activity context;
    private LinearLayout llBack;
    private TextView titleTv;
    private TextView cancel;
    private TextView sure;
    private String titleContent = "确认下一步吗？";
    private OkClick click;

    public CancelSureDialog(@NonNull Activity context) {
        super(context, R.style.custom_dialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_cancel_or_ok);
        //点击外部不可消失
        setCancelable(false);
        initView();
        initWidget();
    }

    private void initView() {
        llBack = (LinearLayout) findViewById(R.id.ll_dialog_back);
        titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        sure = (TextView) findViewById(R.id.ok_tv);
        cancel = (TextView) findViewById(R.id.cancel_tv);
    }

    private void initWidget() {
        //设置标题
        titleTv.setText(titleContent);
        //设置弹窗大小
        setWindowSize(llBack, context);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.ok();
                    cancel();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消
                cancel();
            }
        });
    }

    private void setWindowSize(LinearLayout layout, Activity context) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        int width = SystemUtils.getPhoneScreenWidth(context);
        int height = SystemUtils.getPhoneScreenHight(context);
        params.width = width * 3 / 5;
        params.height = height * 1 / 7;
        layout.setLayoutParams(params);
    }

    public void setOkClick(String content, OkClick okClick) {
        this.titleContent = content;
        this.click = okClick;
        showDialog();
    }

    public interface OkClick {
        void ok();
    }

    private void showDialog() {
        show();
    }
}
