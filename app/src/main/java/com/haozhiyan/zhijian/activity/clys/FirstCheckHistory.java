package com.haozhiyan.zhijian.activity.clys;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.adapter.InspectLookCaiLiaoAdapter;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Brand;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Specification;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Type;
import com.haozhiyan.zhijian.bean.InspectInfoBean;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 首次送检记录
 */
public class FirstCheckHistory extends BaseActivity2 {
    private RecyclerView inspectImageListRCV;
    private TextView inspectDateTV;
    private TextView inspectDateTV2;

    private TextView inspectorTV;
    private RecyclerView clysBrandListRCV;
    private ImageView inspectorTalkImg;


    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_first_check_history;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("首次送检记录");
        setAndroidNativeLightStatusBar(true);
    }

    @Override
    protected void initData() {
        inspectorTV = findViewById(R.id.inspectorTV);
        inspectorTalkImg = findViewById(R.id.inspectorTalkImg);
        inspectDateTV = findViewById(R.id.inspectDateTV);
        inspectDateTV2 = findViewById(R.id.inspectDateTV2);
        inspectImageListRCV = findViewById(R.id.inspectImageListRCV);
        clysBrandListRCV = findViewById(R.id.clysBrandListRCV);
        clysBrandListRCV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        List<MultiItemEntity> data = new ArrayList<>();
        try {
           final InspectInfoBean.ClysInspectListBean clysBrandListBean = (InspectInfoBean.ClysInspectListBean) getIntent().getSerializableExtra("bean");
            setPicRcv(inspectImageListRCV, clysBrandListBean.getInspectImageList());
            inspectDateTV.setText(clysBrandListBean.getInspectDate() + "");
            inspectDateTV2.setText(clysBrandListBean.getInspectTime() + "");

            try {
                inspectorTV.setText(clysBrandListBean.getInspectorList().get(0).peopleuser + "");
                inspectorTalkImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SystemUtils.callPage(clysBrandListBean.getInspectorList().get(0).tel,getContext());
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < clysBrandListBean.getClysBrandList().size(); i++) {
                InspectInfoBean.ClysInspectListBean.ClysBrandListBean brandListBean = clysBrandListBean.getClysBrandList().get(i);
                AM_Enter_Brand brand = new AM_Enter_Brand(brandListBean.getBandName(), i);
                for (int j = 0; j < brandListBean.getClysTypeList().size(); j++) {
                    InspectInfoBean.ClysInspectListBean.ClysBrandListBean.ClysTypeListBean typeListBean = brandListBean.getClysTypeList().get(j);
                    AM_Enter_Type type = new AM_Enter_Type(typeListBean.getTypeName(), j);
                    for (int k = 0; k < typeListBean.getClysSpecificationList().size(); k++) {
                        InspectInfoBean.ClysInspectListBean.ClysBrandListBean.ClysTypeListBean.ClysSpecificationListBean
                                specificationListBean = typeListBean.getClysSpecificationList().get(k);
                        AM_Enter_Specification specification = new AM_Enter_Specification(specificationListBean.getInspectNumber(),
                                specificationListBean.getSpecificationName(), specificationListBean.getUnit(), k);
                        type.addSubItem(specification);
                    }
                    brand.addSubItem(type);
                }
                data.add(brand);
            }
            InspectLookCaiLiaoAdapter lookCaiLiaoAdapter = new InspectLookCaiLiaoAdapter(data, getActivity());
            clysBrandListRCV.setAdapter(lookCaiLiaoAdapter);
            lookCaiLiaoAdapter.expandAll();
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
