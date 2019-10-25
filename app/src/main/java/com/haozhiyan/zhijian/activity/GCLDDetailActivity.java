package com.haozhiyan.zhijian.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.bean.GCLDListItemBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.DataTest;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ImageRequest;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;

public class GCLDDetailActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.name)
    TextView name;
    @ViewInject(R.id.time)
    TextView time;
    @ViewInject(R.id.theme)
    TextView theme;
    @ViewInject(R.id.lightspotType)
    TextView lightspotType;
    @ViewInject(R.id.projectName)
    TextView projectName;
    @ViewInject(R.id.onlookerNum)
    TextView onlookerNum;
    @ViewInject(R.id.likeNum)
    TextView likeNum;
    @ViewInject(R.id.likeLL)
    LinearLayout likeLL;
    @ViewInject(R.id.replenishExplain)
    TextView replenishExplain;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.sdvHead)
    SimpleDraweeView sdvHead;
    @ViewInject(R.id.zanIV)
    ImageView zanIV;//

    //    @ViewInject(R.id.tabLayout)
//    TableLayout tabLayout;
    @ViewInject(R.id.picRcv)
    RecyclerView picRcv;

    private String id;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gclddetail;
    }

    @Override
    protected void initView() {
        tv_title.setText("亮点详情");
        sdvHead.setImageURI(DataTest.imgUrl);
//        tabLayout.setVisibility(View.GONE);
        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        selectLightspot();
    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.iv_chat, R.id.sdvHead})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_chat:
                ToastUtils.myToast(this, "抱歉，暂无法聊天");
                break;
            case R.id.sdvHead:
                ShowBigImg.build(this, DataTest.imgUrl);
                break;
            default:
                break;
        }
    }

    /**
     * 获取详情
     */
    private void selectLightspot() {
        HttpRequest.get(this).url(ServerInterface.selectLightspot)
                .params("id", id)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        JSONObject jsonObject = JSON.parseObject(result.toString());
                        GCLDListItemBean bean = JSON.parseObject(jsonObject.getString("list"), GCLDListItemBean.class);
                        new ImageRequest(GCLDDetailActivity.this).get(bean.headPortrait, sdvHead);
                        name.setText(bean.peopleuser);
                        time.setText(bean.creatorTime);
                        theme.setText(bean.theme);
                        replenishExplain.setText(TextUtils.isEmpty(bean.replenishExplain) ? "无" : bean.replenishExplain);
                        lightspotType.setText(bean.lightspotType);
                        projectName.setText(bean.projectName);
                        onlookerNum.setText(bean.onlookerNum);
                        likeNum.setText(bean.likeNum);
                        setPicRcv(picRcv, bean.listPicture);

                        if (TextUtils.equals(bean.like, "是")) {
                            likeLL.setBackground(ContextCompat.getDrawable(GCLDDetailActivity.this, R.drawable.islikellback));
                            likeNum.setTextColor(ContextCompat.getColor(GCLDDetailActivity.this, R.color.blue_line));
                            zanIV.setImageResource(R.drawable.img_dianzan_preeesd);
                            likeLL.setOnClickListener(null);
                        } else {
                            likeLL.setBackground(ContextCompat.getDrawable(GCLDDetailActivity.this, R.drawable.blue_10shape));
                            likeNum.setTextColor(ContextCompat.getColor(GCLDDetailActivity.this, R.color.white_));
                            zanIV.setImageResource(R.drawable.img_dianzan_white);
                            likeLL.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    saveLike();
                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    /**
     * 点赞
     */
    private void saveLike() {
        HttpRequest.get(this).url(ServerInterface.saveLike)
                .params("plId", id)
                .params("userId", UserInfo.create(GCLDDetailActivity.this).getUserId())
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        JSONObject jsonObject = JSON.parseObject(result.toString());
                        if (jsonObject.getString("code").equals("0")) {
                            selectLightspot();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


    /**
     * 代办-  照片列表  能复用
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        try {
            picRcv.setLayoutManager(new GridLayoutManager(GCLDDetailActivity.this, 2));
            PicShowListAdapter picShowListAdapter = new PicShowListAdapter(imgs, GCLDDetailActivity.this);
            picRcv.setAdapter(picShowListAdapter);
            picShowListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (PVAUtils.getFileLastType(imgs.get(position)).equals("image/jpeg")) {
                        ShowBigImg.build(GCLDDetailActivity.this, imgs.get(position));
                    } else {
                        ShowVideo.playLineVideo(GCLDDetailActivity.this, imgs.get(position));
                    }
                }
            });
        } catch (Exception e) {
        }
    }
}
