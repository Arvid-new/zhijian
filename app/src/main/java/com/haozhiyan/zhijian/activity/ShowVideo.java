package com.haozhiyan.zhijian.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.MainConstant;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

public class ShowVideo extends BaseActivity {

    private ImageView back_iv;
    private VideoView videoView;
    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, false);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_show_video;
    }

    @Override
    protected void initView() {
        back_iv = findViewById(R.id.back_iv);
        videoView = findViewById(R.id.videoView);
    }

    @Override
    protected void initListener() {
        back_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置视频控制器
        videoView.setMediaController(new MediaController(this));
        //播放完成回调
        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
    }

    @Override
    protected void initData(boolean isNetWork) {
        String localUrl = getIntent().getStringExtra(MainConstant.LOCAL_VIDEO);
        if (!StringUtils.isEmpty(localUrl)) {
            play(localUrl);
        } else {
            String imgUrl = getIntent().getStringExtra(MainConstant.LINE_VIDEO);
            if (!StringUtils.isEmpty(imgUrl)) {
                play(imgUrl);
            }
        }
    }

    private void play(String localUrl) {
        Uri uri = Uri.parse(localUrl);
        //设置视频路径
        videoView.setVideoURI(uri);
        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始播放视频
                videoView.start();
            }
        });
        //videoView.pause();
        videoView.start();

//        PVAUtils.isPictureType(PVAUtils.fileToType(new File("")));
    }

    //播放本地视频
    public static void playLocalVideo(Context context, String localPath) {
        Intent intent = new Intent(context, ShowVideo.class);
        intent.putExtra(MainConstant.LOCAL_VIDEO, localPath);
        context.startActivity(intent);
    }

    //播放网络视频
    public static void playLineVideo(Context context, String imgUrl) {
        Intent intent = new Intent(context, ShowVideo.class);
        intent.putExtra(MainConstant.LINE_VIDEO, imgUrl);
        context.startActivity(intent);
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            ToastUtils.myToast(act, "播放结束");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }
}