package com.haozhiyan.zhijian.activity.xcjcTroubleDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.activity.workXcjc.XCJCShowH5Images;
import com.haozhiyan.zhijian.adapter.LabelTextAdapter;
import com.haozhiyan.zhijian.adapter.PictureVideoShowAdapter;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.ProblemDetailBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.view.MyListView;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

//3异常关闭
public class XCJCTroubleCloseDetail extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.tv_biaoDuan)
    TextView tv_biaoDuan;
    @ViewInject(R.id.tv_buWei)
    TextView tv_buWei;
    @ViewInject(R.id.tv_jcx)
    TextView tv_jcx;
    @ViewInject(R.id.tv_desc)
    TextView tv_desc;
    @ViewInject(R.id.rv_troubleImgs)
    RecyclerView troubleIv;
    @ViewInject(R.id.tv_date)
    TextView tvSubmitDate;
    @ViewInject(R.id.submitList)
    MyListView submitList;
    @ViewInject(R.id.fuyanList)
    MyListView fuyanList;
    @ViewInject(R.id.csList)
    MyListView csList;
    @ViewInject(R.id.tv_zgTime)
    TextView tv_zgTime;
    @ViewInject(R.id.tv_zgRen)
    TextView tv_zgRen;
    @ViewInject(R.id.tv_closeTime)
    TextView tv_closeTime;
    @ViewInject(R.id.tv_closeReason)
    TextView tv_closeReason;
    @ViewInject(R.id.rv_zhengGaiImgs)
    RecyclerView rvZhengGaiImgs;
    @ViewInject(R.id.iv_huImg)
    ImageView ivHuImg;
    @ViewInject(R.id.tv_state01)
    TextView tv_state01;
    @ViewInject(R.id.tv_state02)
    TextView tv_state02;
    private String id = "", roomId = "";
    private PictureVideoShowAdapter pVShowWTAdapter, pVShowZGAdapter;
    private LabelTextAdapter submitAdapter, fuyanAdapter, chaoSongAdapter;
    private List<LocalMedia> problemList, zhengGaiList;
    private List<ItemBean> submitLi, fuyanLi, chaoSongLi;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjctrouble_close_detail;
    }

    @Override
    protected void initView() {
        tv_title.setText("问题详情");
        ivClose.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initListener() {
        initProblemPhoto(1);
        initProblemPhoto(2);
        initRecycle(troubleIv);
        initRecycle(rvZhengGaiImgs);
    }

    @Override
    protected void initData(boolean isNetWork) {
        Bundle bundle = getIntent().getBundleExtra("data");
        id = bundle == null ? "1" : bundle.getString("id");
        getDetail(id);
    }

    //初始关闭问题图片
    private void initProblemPhoto(int index) {
        if (index == 1) {
            pVShowWTAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowWTAdapter, troubleIv, problemList);
        } else if (index == 2) {
            pVShowZGAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowZGAdapter, rvZhengGaiImgs, zhengGaiList);
        }
    }

    private void initRecycle(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));
        recyclerView.setNestedScrollingEnabled(true);
    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.iv_huImg})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                ActivityManager.getInstance().removeActivity(this);
                jumpToActivity(MainActivity.class);
                break;
            case R.id.iv_huImg:
                Intent intent = new Intent(act, XCJCShowH5Images.class);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getDetail(String id) {
        HttpRequest.get(this).url(ServerInterface.xcjcTroubleDetail).params("id", id).
                doGet(new HttpObjectCallBack<ProblemDetailBean>(ProblemDetailBean.class) {
                    @Override
                    public void onSuccess(BaseBean<ProblemDetailBean> result) {
                        if (result.code.equals("0")) {
                            if (result.data.xcjcProblem != null) {
                                ProblemDetailBean.XcjcProblemBean bean = result.data.xcjcProblem;
                                tv_biaoDuan.setText(bean.sectionName);
                                roomId = bean.room;
                                if (StringUtils.isEmpty(bean.housemap)) {
                                    ivHuImg.setVisibility(View.GONE);
                                } else {
                                    ivHuImg.setVisibility(View.VISIBLE);
                                }
                                if (bean.isTimeout != null) {
                                    if (Integer.valueOf(bean.isTimeout) < 0) {
                                        tv_state02.setVisibility(View.VISIBLE);
                                        tv_state02.setText("超时");
                                        tv_state02.setBackgroundColor(setColor(R.color.purple));
                                    } else {
                                        if (bean.backNumber == 0) {
                                            tv_state02.setVisibility(View.INVISIBLE);
                                        } else {
                                            tv_state02.setVisibility(View.VISIBLE);
                                            tv_state02.setText("退回" + bean.backNumber + "次");
                                        }
                                    }
                                }
                                if (StringUtils.isEmpty(bean.serious)) {
                                    tv_state01.setVisibility(View.INVISIBLE);
                                } else {
                                    tv_state01.setVisibility(View.VISIBLE);
                                    if (bean.serious.equals("1")) {
                                        tv_state01.setText("一般");
                                        tv_state01.setBackgroundColor(setColor(R.color.green));
                                    } else if (bean.serious.equals("2")) {
                                        tv_state01.setText("重要");
                                        tv_state01.setBackgroundColor(setColor(R.color.yellow));
                                    } else if (bean.serious.equals("3")) {
                                        tv_state01.setText("紧急");
                                        tv_state01.setBackgroundColor(setColor(R.color.red2));
                                    } else if (bean.serious.equals("4")) {
                                        tv_state01.setText("要紧");
                                        tv_state01.setBackgroundColor(setColor(R.color.red2));
                                    }
                                }
                                tv_buWei.setText(bean.towerName + bean.unitName + "单元" + bean.roomName);
                                tv_jcx.setText(bean.inspectionName);
                                tv_desc.setText(bean.particularsName);
                                //问题照片
                                if (listEmpty(bean.problemImageList)) {
                                    problemList = new ArrayList<>();
                                    for (String path : bean.problemImageList) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        problemList.add(localMedia);
                                    }
                                    pVShowWTAdapter.setNewData(problemList);
                                    pVShowWTAdapter.notifyDataSetChanged();
                                }
                                //提交人
                                if (listEmpty(bean.submitList)) {
                                    submitLi = new ArrayList<>();
                                    for (int i = 0; i < bean.submitList.size(); i++) {
                                        submitLi.add(new ItemBean(bean.submitList.get(i).peopleuser, "", bean.submitList.get(i).tel));
                                    }
                                    submitAdapter = new LabelTextAdapter(act, submitLi, "xcJc_problemDetail_OtherPeople");
                                    submitList.setAdapter(submitAdapter);
                                    submitAdapter.notifyDataSetChanged();
                                    submitAdapter.setOnChatCallListener(new LabelTextAdapter.OnChatCallListener() {
                                        @Override
                                        public void chat(int position) {

                                        }

                                        @Override
                                        public void call(int position) {
                                            if (listEmpty(submitLi)) {
                                                try {
                                                    SystemUtils.callPage(submitLi.get(position).mobile, act);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                }
                                tvSubmitDate.setText(bean.submitDate);
                                tv_zgTime.setText(bean.rectifyTimelimit);
                                tv_zgRen.setText(bean.rectifyName);
                                //整改照片
                                if (listEmpty(bean.rectifyImageList)) {
                                    zhengGaiList = new ArrayList<>();
                                    for (String path : bean.rectifyImageList) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        zhengGaiList.add(localMedia);
                                    }
                                    pVShowZGAdapter.setNewData(zhengGaiList);
                                    pVShowZGAdapter.notifyDataSetChanged();
                                }
                                //复验人
                                if (listEmpty(bean.xcjcReviewList)) {
                                    fuyanLi = new ArrayList<>();
                                    for (int i = 0; i < bean.xcjcReviewList.size(); i++) {
                                        fuyanLi.add(new ItemBean(bean.xcjcReviewList.get(i).peopleuser, bean.xcjcReviewList.get(i).id + "", bean.xcjcReviewList.get(i).tel,
                                                bean.xcjcReviewList.get(i).isReview, bean.xcjcReviewList.get(i).reviewDate, bean.xcjcReviewList.get(i).reviewSupplement));
                                    }
                                    fuyanAdapter = new LabelTextAdapter(act, fuyanLi, "xcJc_problemDetail_fyPeople");
                                    fuyanList.setAdapter(fuyanAdapter);
                                    fuyanAdapter.notifyDataSetChanged();
                                    fuyanAdapter.setOnChatCallListener(new LabelTextAdapter.OnChatCallListener() {
                                        @Override
                                        public void chat(int position) {

                                        }

                                        @Override
                                        public void call(int position) {
                                            if (listEmpty(fuyanLi)) {
                                                try {
                                                    SystemUtils.callPage(fuyanLi.get(position).mobile, act);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                }
                                //抄送人
                                if (listEmpty(bean.ccList)) {
                                    chaoSongLi = new ArrayList<>();
                                    for (int i = 0; i < bean.ccList.size(); i++) {
                                        chaoSongLi.add(new ItemBean(bean.ccList.get(i).peopleuser, bean.ccList.get(i).tel, bean.ccList.get(i).userId, bean.ccList.get(i).userAppTag + ""));
                                    }
                                    chaoSongAdapter = new LabelTextAdapter(act, chaoSongLi, "xcJc_problemDetail_OtherPeople");
                                    csList.setAdapter(chaoSongAdapter);
                                    chaoSongAdapter.notifyDataSetChanged();
                                    chaoSongAdapter.setOnChatCallListener(new LabelTextAdapter.OnChatCallListener() {
                                        @Override
                                        public void chat(int position) {

                                        }

                                        @Override
                                        public void call(int position) {
                                            if (listEmpty(chaoSongLi)) {
                                                try {
                                                    SystemUtils.callPage(chaoSongLi.get(position).mobile, act);
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    });
                                }
                                tv_closeTime.setText(bean.closeDate);
                                tv_closeReason.setText(bean.closeCause);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }


}
