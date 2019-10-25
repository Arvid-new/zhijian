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
import com.haozhiyan.zhijian.adapter.FuYanProblemAdapter;
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
import com.haozhiyan.zhijian.view.MyRecycleView;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.haozhiyan.zhijian.widget.MyDividerItem;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

//4已验收通过
public class XCJCTroubleTongGuoDetail extends BaseActivity {

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
    @ViewInject(R.id.tv_zgTime)
    TextView tv_zgTime;
    @ViewInject(R.id.tv_zgRen)
    TextView tv_zgRen;
    @ViewInject(R.id.rv_zhengGaiImgs)
    RecyclerView rvZhengGaiImgs;
    @ViewInject(R.id.tv_zgDate)
    TextView tv_zgDate;
    @ViewInject(R.id.fuyanList)
    MyListView fuyanList;
    @ViewInject(R.id.submitList)
    MyListView submitList;
    @ViewInject(R.id.tvfuYanDate)
    TextView tvFuYanDate;
    @ViewInject(R.id.rv_fuyanImgs)
    RecyclerView rv_fuyanImgs;
    @ViewInject(R.id.tv_backReason)
    TextView tv_backReason;
    @ViewInject(R.id.rv_backImgs)
    RecyclerView rv_backImgs;
    @ViewInject(R.id.csList)
    MyListView csList;
    @ViewInject(R.id.tv_zeRenDanWei)
    TextView tv_zeRenDanWei;
    @ViewInject(R.id.tv_date)
    TextView tvSubmitDate;
    @ViewInject(R.id.iv_huImg)
    ImageView ivHuImg;
    @ViewInject(R.id.rv_fuYanList)
    MyRecycleView rvFuYanList;
    private String id = "", roomId = "";
    private PictureVideoShowAdapter pVShowWTAdapter, pVShowZGAdapter, pVShowBACKAdapter;
    private LabelTextAdapter submitAdapter, chaoSongAdapter;
    private FuYanProblemAdapter fuYanProblemAdapter;
    private List<LocalMedia> problemList, zhengGaiList, backList;
    private List<ItemBean> submitLi, chaoSongLi;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjctrouble_tong_guo_detail;
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
        initProblemPhoto(3);
        initRecycle(troubleIv);
        initRecycle(rvZhengGaiImgs);
        initRecycle(rv_backImgs);
        fuYanProblemAdapter = new FuYanProblemAdapter(null, this);
        rvFuYanList.setAdapter(fuYanProblemAdapter);
        rvFuYanList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvFuYanList.addItemDecoration(new MyDividerItem(this, MyDividerItem.VERTICAL));
    }

    @Override
    protected void initData(boolean isNetWork) {
        Bundle bundle = getIntent().getBundleExtra("data");
        id = bundle == null ? "1" : bundle.getString("id");
        getDetail(id);
    }

    //初始化通过问题图片
    private void initProblemPhoto(int index) {
        if (index == 1) {
            pVShowWTAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowWTAdapter, troubleIv, problemList);
        } else if (index == 2) {
            pVShowZGAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowZGAdapter, rvZhengGaiImgs, zhengGaiList);
        } else if (index == 3) {
            pVShowBACKAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowBACKAdapter, rv_backImgs, backList);
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
                    public void onSuccess(BaseBean<ProblemDetailBean> response) {
                        if (response.code.equals("0")) {
                            if (response.data.xcjcProblem != null) {
                                ProblemDetailBean.XcjcProblemBean bean = response.data.xcjcProblem;
                                tv_biaoDuan.setText(bean.sectionName);
                                roomId = bean.room;
                                if (StringUtils.isEmpty(bean.housemap)) {
                                    ivHuImg.setVisibility(View.GONE);
                                } else {
                                    ivHuImg.setVisibility(View.VISIBLE);
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
                                                SystemUtils.callPage(submitLi.get(position).mobile, act);
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
                                tv_zgDate.setText(bean.rectifyDate);
                                //复验
                                if (listEmpty(bean.xcjcReviewList)) {
                                    fuYanProblemAdapter.setNewData(bean.xcjcReviewList);
                                }
                                //退回照片
                                tv_backReason.setText(bean.backCause);
                                if (listEmpty(bean.backImageList)) {
                                    backList = new ArrayList<>();
                                    for (String path : bean.backImageList) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        backList.add(localMedia);
                                    }
                                    pVShowBACKAdapter.setNewData(backList);
                                    pVShowBACKAdapter.notifyDataSetChanged();
                                }
                                //抄送人
                                if (listEmpty(bean.ccList)) {
                                    chaoSongLi = new ArrayList<>();
                                    for (int i = 0; i < bean.ccList.size(); i++) {
                                        chaoSongLi.add(new ItemBean(bean.ccList.get(i).peopleuser, "", bean.ccList.get(i).tel));
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
                                                SystemUtils.callPage(chaoSongLi.get(position).mobile, act);
                                            }
                                        }
                                    });
                                }
                                tv_zeRenDanWei.setText(bean.dutyUnitName);
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }
}
