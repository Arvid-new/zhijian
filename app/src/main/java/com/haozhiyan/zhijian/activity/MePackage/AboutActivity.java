package com.haozhiyan.zhijian.activity.MePackage;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity2 {
    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_ios)
    TextView tv_ios;
    @BindView(R.id.tv_android)
    TextView tv_android;
    @BindView(R.id.tv_both)
    TextView tv_both;
    @BindView(R.id.tv_qq)
    TextView tv_qq;
    private ClipboardManager clipboardManager;
    private ClipData mClipData;
    private PopupWindow popupWindow;
    AboutActivity activity;
    @Override
    protected void init(Bundle savedInstanceState) {
        StatusBarUtils.setStatus(this, true);
        activity = this;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_about;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        setTitleText("关于");
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        View popupView= View.inflate(this,R.layout.layout_qrcode,null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.BottomDialogAnimation);
        popupWindow.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    @Override
    protected void initData() {
        tv_version.setText(SystemUtils.getVersionName(getApplicationContext()));
    }

    @OnClick({R.id.tv_ios, R.id.tv_android, R.id.tv_both, R.id.tv_qq})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ios:
                mClipData = ClipData.newPlainText("Label", "https://www.pgyer.com/qXIk");
                clipboardManager.setPrimaryClip(mClipData);
                ToastUtils.myToast(AboutActivity.this,"已复制到粘贴板");
                break;
            case R.id.tv_android:
                mClipData = ClipData.newPlainText("Label", "https://www.pgyer.com/Ads8");
                clipboardManager.setPrimaryClip(mClipData);
                ToastUtils.myToast(AboutActivity.this, "已复制到粘贴板");
                break;
            case R.id.tv_both:
                popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;
            case R.id.tv_qq:
                mClipData = ClipData.newPlainText("Label", "qq");
                //clipboardManager.setPrimaryClip(mClipData);
                //ToastUtils.myToast(AboutActivity.this, "已复制到粘贴板");
                SystemUtils.callPage("037155157798",this);
                break;
            default:
                break;
        }
    }
}
