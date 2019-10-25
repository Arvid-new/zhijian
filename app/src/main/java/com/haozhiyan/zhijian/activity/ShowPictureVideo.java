package com.haozhiyan.zhijian.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.MainConstant;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ShowPictureVideo extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private TextView positionTv;
    private ArrayList<String> imgList;
    private String videoUrl = "", fileType = "";
    private int mPosition = 0;
    private ViewPagerAdapter mAdapter;
    private boolean mShowDelete = false;
    private ImageView delete_iv;
    private VideoView mVideoView;
    private LinearLayout ll_video;
    private LinearLayout ll_image;

    public static void build(Context context, String fileUrl) {
        Intent intent = new Intent(context, ShowPictureVideo.class);
        if (PVAUtils.getFileLastType(fileUrl).equals("image/jpeg")) {
            intent.putExtra(MainConstant.PIC_PATH, fileUrl);
            intent.putExtra(MainConstant.FILE_TYPE, "image");
        } else if (PVAUtils.getFileLastType(fileUrl).equals("video/3gp")) {
            intent.putExtra(MainConstant.TYPE_VIDEO, fileUrl);
            intent.putExtra(MainConstant.FILE_TYPE, "video");
        } else {
            intent.putExtra(MainConstant.PIC_PATH, fileUrl);
            intent.putExtra(MainConstant.FILE_TYPE, "image");
        }
        context.startActivity(intent);
    }

    public static void build(Context context, ArrayList<String> urlList) {
        Intent intent = new Intent(context, ShowPictureVideo.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, urlList);
        intent.putExtra(MainConstant.FILE_TYPE, "image");
        context.startActivity(intent);
    }

    //播放视频
    public static void playVideo(Context context, String imgUrl) {
        Intent intent = new Intent(context, ShowPictureVideo.class);
        intent.putExtra(MainConstant.TYPE_VIDEO, imgUrl);
        intent.putExtra(MainConstant.FILE_TYPE, "video");
        context.startActivity(intent);
    }

    public static void playVideo(Context context, ArrayList<String> videoUrl) {
        Intent intent = new Intent(context, ShowPictureVideo.class);
        intent.putStringArrayListExtra(MainConstant.TYPE_VIDEO_LIST, videoUrl);
        intent.putExtra(MainConstant.FILE_TYPE, "video");
        context.startActivity(intent);
    }

    public static void build(Context context, ArrayList<String> urlList, int selectPage) {
        Intent intent = new Intent(context, ShowPictureVideo.class);
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, urlList);
        intent.putExtra(MainConstant.POSITION, selectPage);
        intent.putExtra(MainConstant.FILE_TYPE, "image");
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture_video);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_video = findViewById(R.id.ll_video);
        ll_image = findViewById(R.id.ll_image);
        delete_iv = (ImageView) findViewById(R.id.delete_iv);
        mVideoView = (VideoView) findViewById(R.id.videoView);
        positionTv = (TextView) findViewById(R.id.position_tv);
        findViewById(R.id.back_iv).setOnClickListener(this);
        delete_iv.setOnClickListener(this);
        //设置视频控制器
        mVideoView.setMediaController(new MediaController(ShowPictureVideo.this));
        //播放完成回调
        mVideoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

        fileType = getIntent().getStringExtra(MainConstant.FILE_TYPE);
        if (fileType.equals("image")) {
            ll_video.setVisibility(View.GONE);
            ll_image.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            try {
                imgList = getIntent().getStringArrayListExtra(MainConstant.IMG_LIST);
                if (imgList == null) {
                    imgList = new ArrayList<>();
                    String img = getIntent().getStringExtra(MainConstant.PIC_PATH);
                    imgList.add(img);
                }
                mPosition = getIntent().getIntExtra(MainConstant.POSITION, 0);
            } catch (Exception e) {
                mPosition = 0;
            }
            try {
                mShowDelete = getIntent().getBooleanExtra(MainConstant.SHOWDELETE, false);
            } catch (Exception e) {
                mShowDelete = false;
            }
            if (mShowDelete) {
                delete_iv.setVisibility(View.VISIBLE);
            } else {
                delete_iv.setVisibility(View.GONE);
            }
            viewPager.addOnPageChangeListener(this);
            mAdapter = new ViewPagerAdapter(this, imgList);
            viewPager.setAdapter(mAdapter);
            positionTv.setText((mPosition + 1) + "/" + imgList.size());
            viewPager.setCurrentItem(mPosition);
            System.out.println("img===" + imgList.size());
        } else if (fileType.equals("video")) {
            ll_video.setVisibility(View.VISIBLE);
            ll_image.setVisibility(View.GONE);
            videoUrl = getIntent().getStringExtra(MainConstant.TYPE_VIDEO);
            System.out.println("video===" + videoUrl);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            if (!StringUtils.isEmpty(videoUrl)) {
                play(videoUrl);
            }
        }

    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_iv:
                //返回
                back();
                break;
            case R.id.delete_iv:
                //删除图片
                //deletePic();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //按下了返回键
            back();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回上一个页面
    private void back() {
        Intent intent = new Intent();
        intent.putStringArrayListExtra(MainConstant.IMG_LIST, imgList);
        setResult(MainConstant.RESULT_CODE_VIEW_IMG, intent);
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        positionTv.setText((position + 1) + "/" + imgList.size());
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    class ViewPagerAdapter extends PagerAdapter {
        private Context context;
        private List<String> imgList;

        public ViewPagerAdapter(Context context, List<String> imgList) {
            this.context = context;
            this.imgList = imgList;
        }

        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getItemView(R.layout.view_pager_img);
            new ImageRequest(context).get(imgList.get(position), (ImageView) view.findViewById(R.id.img_iv));
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return imgList != null ? imgList.size() : 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        private View getItemView(int layoutID) {
            return LayoutInflater.from(context).inflate(layoutID, null, false);

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void play(String imageUrl) {
        mVideoView.setVideoPath(imageUrl);
        mVideoView.start();
        mVideoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始播放视频
                mVideoView.start();
            }
        });
        mVideoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }
}
