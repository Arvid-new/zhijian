package com.haozhiyan.zhijian.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.SystemUtils;

/**
 * 上传文件提示 dlg
 */
public class UpLoadFileDialog extends Dialog {

    public UpLoadFileDialog(Context context) {
        //使用自定义Dialog样式
        super(context, R.style.custom_dialog);
        //指定布局
        setContentView(R.layout.loading_dlg_layout);
        //点击外部不可消失
        setCancelable(false);
        ImageView loading_img = findViewById(R.id.loading_img);
        new ImageRequest(getContext()).getGif(R.mipmap.blue_loading, loading_img, 0);

    }

    private void setWindowSize(LinearLayout layout, Activity context) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) layout.getLayoutParams();
        int width = SystemUtils.getPhoneScreenWidth(context);
        int height = SystemUtils.getPhoneScreenHight(context);
        params.width = width * 3 / 5;
        //params.height = height*1/7;
        layout.setLayoutParams(params);
    }
}
