package com.haozhiyan.zhijian.activity.clys;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.bean.AMReportInfoBean;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;

import java.util.List;

public class FirstCheckReport extends BaseActivity2 {


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_first_check_report;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("首次送检报告");
        setAndroidNativeLightStatusBar(true);
    }

    @Override
    protected void initData() {
        try {
            final AMReportInfoBean.ClysReportListBean bean = (AMReportInfoBean.ClysReportListBean) getIntent().getSerializableExtra("bean");
            setPicRcv((RecyclerView) findViewById(R.id.inspectReportImageListRCV), bean.getInspectReportImageList());
            TextView reportResultTV = findViewById(R.id.reportResultTV);
            TextView inspectorTV = findViewById(R.id.inspectorTV);
            TextView inspectDateTV = findViewById(R.id.inspectDateTV);
            ImageView inspectorTalkImg = findViewById(R.id.inspectorTalkImg);
            reportResultTV.setText("0".equals(bean.getReportResult()) ? "不合格，需要复验" : "合格");
            try {
                inspectorTV.setText(bean.getInspectorList().get(0).getPeopleuser() + "");
                inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.callPage(bean.getInspectorList().get(0).getTel(), getContext());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
            inspectDateTV.setText(bean.getInspectDate() + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置 仅 展示图片的 RCV
     *
     * @param picRcv
     * @param imgs
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        if (imgs != null && imgs.size() > 0) {
            picRcv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(imgs, getContext());
            picRcv.setAdapter(picShowListAdapter);
            picShowListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (PVAUtils.getFileLastType(imgs.get(position)).equals("image/jpeg")) {
                        ShowBigImg.build(getContext(), imgs.get(position));
                    } else {
                        ShowVideo.playLineVideo(getContext(), imgs.get(position));
                    }
                }
            });
        }
    }


}
