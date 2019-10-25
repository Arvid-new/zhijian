package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.xcjc.XCJCSaveBean;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/10.
 * Describe: Ydzj_project
 */
public class JCDraftAdapter extends BaseQuickAdapter<XCJCSaveBean, BaseViewHolder> {
    private Context context;

    public JCDraftAdapter(Context context, @Nullable List<XCJCSaveBean> data) {
        super(R.layout.jian_cha_draft_item, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, XCJCSaveBean item) {
        try {
            String path = "";
            LocalMedia media = item.pics.get(0);
            int mimeType = 0;
            try {
                mimeType = media.getMimeType();
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            try {
                LogUtils.i("原图地址::", media.getPath());
                LogUtils.i("原图剪切地址::", media.getCutPath());
                LogUtils.i("原图压缩地址::", media.getCompressPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mimeType == PictureMimeType.ofAudio()) {
                helper.setImageResource(R.id.imgView, R.drawable.audio_placeholder);
            } else {
                try {
                    new ImageRequest(context).get(path, (ImageView) helper.getView(R.id.imgView));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.setText(R.id.tvName, item.JianChaR)
                .setText(R.id.tvDesc, item.particularsName + "\t" + item.particularsDesc)
                .setText(R.id.tvTime, item.createTime + "\t" + item.local);
    }
}
