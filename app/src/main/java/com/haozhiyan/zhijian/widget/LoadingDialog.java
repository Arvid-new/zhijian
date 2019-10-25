package com.haozhiyan.zhijian.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;

/**
 * Create by WangZhenKai at 2019/04/23
 */
public class LoadingDialog extends Dialog {

    private TextView tv;
    private String titleTv = "请稍后...";

    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉默认的title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //仅点击外部不可取消
        setCanceledOnTouchOutside(false);
        //点击返回键和外部都不可取消
        setCancelable(true);
        //去掉白色边角 小米手机在xml里设置 android:background="@android:color/transparent"不生效
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.load);
        tv = (TextView) findViewById(R.id.tv);
        if (titleTv.equals("")) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setVisibility(View.VISIBLE);
            tv.setText(titleTv);
        }
    }

    public void setTitle(String title) {
        this.titleTv = title;
    }
}
