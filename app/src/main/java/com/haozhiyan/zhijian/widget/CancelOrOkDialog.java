package com.haozhiyan.zhijian.widget;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.SystemUtils;

/**
 * Create by WangZhenKai at 2019/04/23
 */
public class CancelOrOkDialog extends Dialog {

    public CancelOrOkDialog(Activity context, String title) {
        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.dialog_cancel_or_ok);
        //点击外部不可消失
        setCancelable(false);
        LinearLayout llBack = (LinearLayout) findViewById(R.id.ll_dialog_back);
        setWindowSize(llBack,context);
        //设置标题
        TextView titleTv = (TextView) findViewById(R.id.dialog_title_tv);
        titleTv.setText(title);

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
                ok();
            }
        });
        show();
    }
    //确认
    public void ok() {
    }

    private void setWindowSize(LinearLayout layout,Activity context){
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        int width = SystemUtils.getPhoneScreenWidth(context);
        int height = SystemUtils.getPhoneScreenHight(context);
        params.width = width*3/5;
        params.height = height*1/7;
        layout.setLayoutParams(params);
    }
}
