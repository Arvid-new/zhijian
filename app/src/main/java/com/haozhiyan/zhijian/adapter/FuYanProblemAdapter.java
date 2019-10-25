package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.bean.ProblemDetailBean;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PopWindowUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.view.MyRecycleView;
import com.haozhiyan.zhijian.widget.CustomDialog;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/1.
 * Describe: Ydzj_project
 */
public class FuYanProblemAdapter extends BaseQuickAdapter<ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean, BaseViewHolder> {

    private PictureVideoShowAdapter pVShowFYAdapter;
    private Context context;

    public FuYanProblemAdapter(@Nullable List<ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean> data, Context context) {
        super(R.layout.fuyan_item_layout, data);
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean item) {
        helper.setText(R.id.tv_fuYan, item.peopleuser)
                .setText(R.id.tv_fuYanDate, item.reviewDate)
                .setGone(R.id.iv, StringUtils.isEmpty(item.peopleuser) ? true : false);
        helper.getView(R.id.iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopWindowUtils.getPopWindow().showPop(context, ((ImageView) helper.getView(R.id.iv)), 0, 10).setClickListener(new CustomDialog.OnClickCustomButtonListener() {
                    @Override
                    public void onChatClick() {

                    }

                    @Override
                    public void onCallClick() {
                        LogUtils.i("call==", "打电话");
                        //SystemUtils.callPage(item.mobile, context);
                    }
                });
            }
        });
        MyRecycleView rvFuYanImgs = helper.getView(R.id.rv_fuyanImgs);
        initFuYan(rvFuYanImgs, item);
    }

    private void initFuYan(MyRecycleView imgRev, ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean bean) {
        pVShowFYAdapter = new PictureVideoShowAdapter(context);
        imgRev.setAdapter(pVShowFYAdapter);
        List<LocalMedia> fuyanLi = null;
        if (ListUtils.listEmpty(bean.reviewImageList)) {
            fuyanLi = new ArrayList<>();
            for (String path : bean.reviewImageList) {
                LocalMedia localMedia = new LocalMedia();
                localMedia.setPath(path);
                localMedia.setPictureType(PVAUtils.getFileLastType(path));
                fuyanLi.add(localMedia);
            }
            pVShowFYAdapter.setNewData(fuyanLi);
        }
        final List<LocalMedia> finalFuYanLi = fuyanLi;
        pVShowFYAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (ListUtils.listEmpty(finalFuYanLi)) {
                    if (PictureMimeType.isPictureType(finalFuYanLi.get(position).getPictureType()) == 1) {
                        ShowBigImg.build(context, finalFuYanLi.get(position).getCutPath());
                    } else if (PictureMimeType.isPictureType(finalFuYanLi.get(position).getPictureType()) == 2) {
                        ShowVideo.playLineVideo(context, finalFuYanLi.get(position).getPath());
                    }
                }
            }
        });
    }
}
