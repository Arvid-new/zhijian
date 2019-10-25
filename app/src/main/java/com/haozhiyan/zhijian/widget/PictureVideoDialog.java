package com.haozhiyan.zhijian.widget;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;

/**
 * Created by WangZhenKai on 2019/7/8.
 * Describe: Ydzj_project
 */
public class PictureVideoDialog extends Dialog implements View.OnClickListener {

    private Activity mContext;
    private ItemClickListener listener;
    private TextView fromAlbum;
    private TextView fromCamera;
    private TextView fromCameraVideo;
    private TextView cancel;
    private boolean isHeaders = false;

    public PictureVideoDialog(@NonNull Activity context) {
        super(context);
        this.mContext = context;
    }

    public PictureVideoDialog(@NonNull Activity context, int themeResId) {
        super(context, themeResId);//BottomDialogStyle
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.picture_dialog, null);
        //初始化控件
        fromAlbum = view.findViewById(R.id.from_album);
        fromCamera = view.findViewById(R.id.from_camera);
        fromCameraVideo = view.findViewById(R.id.from_cameraVideo);
        cancel = view.findViewById(R.id.cancel);
        fromAlbum.setOnClickListener(this);
        fromCamera.setOnClickListener(this);
        fromCameraVideo.setOnClickListener(this);
        cancel.setOnClickListener(this);
        //将布局设置给Dialog
        setContentView(view);
        //获取当前Activity所在的窗体
        Window dialogWindow = getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = (int) (SystemUtils.getPhoneScreenWidth(mContext) * 0.95);
        lp.y = 10; //设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp); //将属性设置给窗体

        if(isHeaders){
            setHideFunction(3);
        }else{
            if ("0".equals(Constant.photoTag)) {
                setHideFunction(0);
            }
            LogUtils.print("photoTag------ " + Constant.photoTag);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.from_album://相册
                dismiss();
                if (listener != null) {
                    listener.photo();
                }
                break;
            case R.id.from_camera://相机拍照
                dismiss();
                if (listener != null) {
                    listener.camera();
                }
                break;
            case R.id.from_cameraVideo://相机录视频
                dismiss();
                if (listener != null) {
                    listener.cameraVideo();
                }
                break;
            case R.id.cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    public void setHideFunction(int hideFunction) {
        if (hideFunction == 0) {//隐藏相册
            fromAlbum.setVisibility(View.GONE);
        } else if (hideFunction == 1) {//显示相册
            fromAlbum.setVisibility(View.VISIBLE);
        } else if (hideFunction == 2) {//隐藏相机
            fromCamera.setVisibility(View.GONE);
        } else if (hideFunction == 3) {//隐藏相机视频
            fromCameraVideo.setVisibility(View.GONE);
        } else {
            fromAlbum.setVisibility(View.VISIBLE);
            fromCamera.setVisibility(View.VISIBLE);
            fromCameraVideo.setVisibility(View.VISIBLE);
        }
    }

    public void SetItemClickListener(ItemClickListener itemClickListener) {
        this.listener = itemClickListener;
        showDialog();
    }

    private void showDialog() {
        show();
    }

    public interface ItemClickListener {
        void photo();

        void camera();

        void cameraVideo();
    }

    public void setIsHeader(boolean isHeader) {
        this.isHeaders = isHeader;
    }
}
