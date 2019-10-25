package com.haozhiyan.zhijian.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;

/**
 * Create by WangZhenKai at 2019/04/23
 */
public class LoginExceptionDialog extends Dialog {

    public LoginExceptionDialog(Context context, String title) {
        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);
        //指定布局
        try {
            setContentView(R.layout.dialog_login_exception);
            //点击外部不可消失
            setCancelable(false);
            TextView tvTitle = findViewById(R.id.dialog_title_tv);
            tvTitle.setText(title);
            findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View paramView) {
                    //取消
                    cancel();
                }
            });

            findViewById(R.id.ok_tv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View paramView) {
                    cancel();
                    ok();
                }
            });
            show();
        } catch (Exception e) {}
    }

    //确认
    public void ok() {
    }
}
