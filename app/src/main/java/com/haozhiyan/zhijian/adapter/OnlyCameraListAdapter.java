package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OnlyCameraListAdapter extends BaseQuickAdapter<LocalMedia, BaseViewHolder> {

    private int[] ints = {R.drawable.img_blue_add_file};

    private Context context;
    private List<LocalMedia> mediaList;

    public OnlyCameraListAdapter(Context context) {
        super(R.layout.gv_filter_image);
        this.context = context;
    }


    public void setNewData(@Nullable List<LocalMedia> data) {
        if (mediaList == null) {
            mediaList = new ArrayList<>();
        }
        mediaList.clear();
        mediaList.addAll(data);
        mediaList.add(new LocalMedia());
        super.setNewData(mediaList);
    }


    @Override
    protected void convert(BaseViewHolder helper, LocalMedia media) {

        helper.addOnClickListener(R.id.ll_del);
        if (helper.getLayoutPosition() == getItemCount() - 1) {
            new ImageRequest(context).getDrawable(ints[0], (ImageView) helper.getView(R.id.fiv));
            helper.setVisible(R.id.ll_del, false);
            helper.getView(R.id.play_img).setVisibility(View.GONE);
        } else {
            helper.setVisible(R.id.ll_del, true);

            int mimeType = 0;
            try {
                mimeType = media.getMimeType();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String path = "";
            if (media.isCut() && !media.isCompressed()) {
                // 裁剪过
                path = media.getCutPath();
            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
                path = media.getCompressPath();
            } else {
                // 原图
                path = media.getPath();
            }
            // 图片
            if (media.isCompressed()) {
                LogUtils.i("compress image result:", new File(media.getCompressPath()).length() / 1024 + "k");
                LogUtils.i("压缩地址::", media.getCompressPath());
            }

            try {
                LogUtils.i("原图地址::", media.getPath());
                LogUtils.i("原图剪切地址::", media.getCutPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            int pictureType = PictureMimeType.isPictureType(media.getPictureType());
            if (media.isCut()) {
                try {
                    LogUtils.i("裁剪地址::", media.getCutPath());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            long duration = media.getDuration();
            helper.getView(R.id.play_img).setVisibility(pictureType == PictureConfig.TYPE_VIDEO
                    ? View.VISIBLE : View.GONE);
            if (pictureType == PictureMimeType.ofAudio()) {
                helper.getView(R.id.play_img).setVisibility(View.VISIBLE);
            } else if (pictureType == PictureMimeType.ofVideo()) {
                helper.getView(R.id.play_img).setVisibility(View.VISIBLE);
            } else {
                helper.getView(R.id.play_img).setVisibility(View.GONE);
            }

            if (mimeType == PictureMimeType.ofAudio()) {
                helper.setImageResource(R.id.fiv, R.drawable.audio_placeholder);
            } else {
                try {
                    new ImageRequest(context).getFile(new File(path), (ImageView) helper.getView(R.id.fiv));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
