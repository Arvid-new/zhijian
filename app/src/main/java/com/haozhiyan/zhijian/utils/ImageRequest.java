package com.haozhiyan.zhijian.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.model.ServerInterface;

import java.io.File;


@SuppressWarnings("ALL")
public class ImageRequest {

    private Context context;

    public ImageRequest(Context context) {
        this.context = context;
    }

    private String url(String url) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }
        try {
            File file = new File(url);
            if (file.isFile()) {
                return url;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (url.startsWith("http")) {
            return url;
        } else {
            return ServerInterface.PVUrl() + url;
        }
    }

    /**
     * 标准网络请求
     *
     * @param url
     * @param imageView
     */
    @SuppressLint("CheckResult")
    public void get(final String url, final ImageView imageView) {
        final RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.icon_no_img).error(R.drawable.icon_no_url);
        if (TextUtils.isEmpty(url)) {
            Glide.with(context).load(url(url)).apply(options).into(imageView);
            return;
        }

        //options.skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        try {
            File file = new File(url);
            if (file.isFile() && file.exists()) {
                Glide.with(context).load(url(url)).apply(options).into(imageView);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (url.startsWith("http")) {
            if (PVAUtils.getFileLastType(url).equals("image/jpeg")) {
                Glide.with(context).load(url(url)).apply(options).into(imageView);
            } else if (PVAUtils.getFileLastType(url).equals("video/3gp")) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = ImageUtils.createVideoThumbnail(url(url), 0, 0);
                        imageView.post(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (Looper.myLooper() == Looper.getMainLooper()) {
                                        Glide.with(context).load(bitmap).apply(options).into(imageView);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }).start();

            } else {
                Glide.with(context).load(url(url)).apply(options).into(imageView);
            }
        } else {
            Glide.with(context).load(url(url)).apply(options).into(imageView);
        }
    }

    /**
     * 300x压缩请求
     *
     * @param url
     * @param icon
     */
    @SuppressLint("CheckResult")
    public void get300x(String url, ImageView icon) {
        RequestOptions options = new RequestOptions();
//        options.placeholder(R.mipmap.dengdai);
        //options.skipMemoryCache(true).override(300, 300).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(context).load(url(url)).apply(options).into(icon);
    }


    /**
     * 资源文件夹（drawable mipmap）图片加载
     *
     * @Context 传递的上下文
     * @Integer 资源文件（drawable mipmap）对象
     * @ImageView ImageView对象
     */
    public void getDrawable(Integer resourceId, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        //options.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).circleCrop();
        Glide.with(context).load(resourceId).apply(options).into(imageView);
    }

    /**
     * 本地文件路径图片加载
     *
     * @Context 传递的上下文
     * @File 本地文件路径
     * @ImageView ImageView对象
     */
    public void getFile(File file, ImageView imageView) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.icon_no_img);
        Glide.with(context).load(file).apply(options).into(imageView);
    }


    /**
     * Gif动图加载 资源文件
     *  @context       传递的上下文
     *
     * @url 网络地址
     * @imageViewurl ImageView对象
     * @number 0为无限循环其他数未正常循环次数
     */
    public void getGif(int resourceId, final ImageView imageView, final int number) {

        if (number == 0) {
            RequestOptions options = new RequestOptions();
//            options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(context).load(resourceId).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                    return false;
                }

            }).apply(options).into(imageView);

        } else {

            Glide.with(context).load(resourceId).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                    if (drawable instanceof GifDrawable) {
                        GifDrawable gifDrawable = (GifDrawable) drawable;
                        gifDrawable.setLoopCount(number);
                        imageView.setImageDrawable(drawable);
                        gifDrawable.start();
                    }
                }
            });

        }
    }

}
