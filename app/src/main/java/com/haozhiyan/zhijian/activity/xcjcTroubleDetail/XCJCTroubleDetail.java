package com.haozhiyan.zhijian.activity.xcjcTroubleDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.Calendar;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.workXcjc.SelectAngle;
import com.haozhiyan.zhijian.activity.workXcjc.XCJCShowH5Images;
import com.haozhiyan.zhijian.activity.workXcjc.XcJcBackReason;
import com.haozhiyan.zhijian.activity.workXcjc.XianChangJianChactivity;
import com.haozhiyan.zhijian.adapter.LabelTextAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.PictureVideoShowAdapter;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.ProblemDetailBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.AppUtils;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.PopWindowUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.view.MyListView;
import com.haozhiyan.zhijian.widget.CancelSureDialog;
import com.haozhiyan.zhijian.widget.CustomDialog;
import com.haozhiyan.zhijian.widget.GridSpacingItemDecoration;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//1待整改
public class XCJCTroubleDetail extends BaseActivity {

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
    @ViewInject(R.id.tv_date)
    TextView tvSubmitDate;
    @ViewInject(R.id.tv_zgTime)
    TextView tv_zgTime;
    @ViewInject(R.id.tv_zgRen)
    TextView tv_zgRen;
    @ViewInject(R.id.iv_window)
    ImageView iv_window;
    @ViewInject(R.id.rv_troubleImgs)
    RecyclerView troubleIv;
    @ViewInject(R.id.rv_zhengGaiImgs)
    RecyclerView rvZhengGaiImgs;
    //@ViewInject(R.id.submitList)
    //MyListView submitList;
    @ViewInject(R.id.tv_submit_People)
    TextView tvSubmitPeople;
    @ViewInject(R.id.fuyanList)
    MyListView fuyanList;
    @ViewInject(R.id.csList)
    MyListView csList;
    @ViewInject(R.id.et_instruct_content)
    EditText et_instruct_content;
    @ViewInject(R.id.id_editor_detail_font_count)
    TextView detailFontCount;
    @ViewInject(R.id.tv_zrRen)
    TextView tv_zrRen;
    @ViewInject(R.id.tv_complete_zg)
    TextView tvCompleteZg;
    @ViewInject(R.id.tv_exceptionClose)
    TextView tv_exceptionClose;
    @ViewInject(R.id.ll_selecFuyan)
    LinearLayout ll_selecFuyan;
    @ViewInject(R.id.ll_selecCs)
    LinearLayout ll_selecCs;
    @ViewInject(R.id.ll_selecZr)
    LinearLayout ll_selecZr;
    @ViewInject(R.id.iv_huImg)
    ImageView ivHuImg;
    @ViewInject(R.id.tv_state01)
    TextView tv_state01;
    @ViewInject(R.id.tv_state02)
    TextView tv_state02;
    @ViewInject(R.id.ll_zgImgs)
    LinearLayout llZgImgs;
    @ViewInject(R.id.rl_instruct)
    RelativeLayout rlInstruct;
    private PictureVideoShowAdapter pVShowAdapter;
    //    private PictureOrVideoListAdapter pVListAdapter;
    private PictureOrVideoListNewAdapter pVListAdapter;
    private LabelTextAdapter submitAdapter, fuyanAdapter, chaoSongAdapter;
    private List<LocalMedia> problemList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<ItemBean> submitLi;
    private List<ItemBean> fuyanLi;
    private List<ItemBean> chaoSongLi;
    private String id = "1", dutyName = "", batchId = "", dutyUnit = "", review = "", cc = "", filePath = "", rectify = "", itemId = "", sectionId = "";
    private List<String> fileList = new ArrayList<>();
    private String reason = "", roomId = "", submitToRectifyTag = "", problemId = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjctrouble_detail;
    }

    @Override
    public void initView() {
        tv_title.setText("问题详情");
        ivClose.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getBundleExtra("data");
        id = bundle == null ? getIntent().getStringExtra("id") : bundle.getString("id");
        submitToRectifyTag = bundle == null ? "" : bundle.getString("submitToRectifyTag");
        initRecycle(troubleIv);
        initRecycle(rvZhengGaiImgs);
        initProblemPhoto();
        initZGPic();
        initShowView();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        getDetail(id);
    }

    private void initShowView() {
        if (submitToRectifyTag.equals("1")) {//提交人和整改人相同
            llZgImgs.setVisibility(View.VISIBLE);
            rlInstruct.setVisibility(View.VISIBLE);
            tvCompleteZg.setVisibility(View.VISIBLE);
        } else if (submitToRectifyTag.equals("0")) {//提交人和整改人不相同
            llZgImgs.setVisibility(View.GONE);
            rlInstruct.setVisibility(View.GONE);
            tvCompleteZg.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.tv_zgTime, R.id.iv_window, R.id.ll_selecFuyan, R.id.ll_selecCs, R.id.ll_selecZr, R.id.tv_complete_zg
            , R.id.tv_exceptionClose, R.id.tv_zgRen, R.id.iv_huImg})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                ActivityManager.getInstance().removeActivity(this);
                jumpToActivity(MainActivity.class);
                break;
            case R.id.tv_zgTime:
                Calendar.check(this, tv_zgTime.getText().toString(), Calendar.SELECTDATE);
                break;
            case R.id.iv_window:
                PopWindowUtils.getPopWindow().showPop(act, iv_window, 0, 10).setClickListener(new CustomDialog.OnClickCustomButtonListener() {
                    @Override
                    public void onChatClick() {

                    }

                    @Override
                    public void onCallClick() {

                    }
                });
                break;
            case R.id.ll_selecFuyan:
                Constant.REN_TYPE = 5;
                jumpActivityForResult(SelectAngle.class, Constant.FU_YAN_REN_CODE);
                break;
            case R.id.ll_selecCs:
                Constant.REN_TYPE = 3;
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE);
                break;
            case R.id.ll_selecZr:
                SelectTRPOrAU.select(this, UiUtils.getContent(tv_zrRen), sectionId, SelectTRPOrAU.ACCOUNTABILITY_UNIT);
                break;
            case R.id.tv_zgRen:
                SelectTRPOrAU.select(this, UiUtils.getContent(tv_zgRen), SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.tv_complete_zg:
                if (StringUtils.isEmpty(filePath)) {
                    ToastUtils.myToast(act, "请拍摄照片");
                } else {
                    if (listEmpty(fuyanLi)) {
                        crossZG();
                    } else {
                        ToastUtils.myToast(act, "请选择复验人");
                    }
                }
                break;
            case R.id.tv_exceptionClose:
                if (listEmpty(fuyanLi)) {
                    startActivityForResult(new Intent(act, XcJcBackReason.class), Constant.XCJC_REASON_CODE);
                } else {
                    ToastUtils.myToast(act, "请选择复验人");
                }
                break;
            case R.id.iv_huImg:
                Intent intent = new Intent(act, XCJCShowH5Images.class);
                intent.putExtra("roomId", roomId);
                intent.putExtra("problemId", problemId);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //初始RecyclerView
    private void initRecycle(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));
        recyclerView.setNestedScrollingEnabled(true);
    }

    //初始问题图片
    private void initProblemPhoto() {
        pVShowAdapter = new PictureVideoShowAdapter(this);
        PhotoCameraUtils.init(this).photoCameraAdapter(pVShowAdapter, troubleIv, problemList);
    }

    //初始整改图片
    private void initZGPic() {
        pVListAdapter = new PictureOrVideoListNewAdapter(this);
        //PhotoCameraUtils.init(this).photoCameraListAdapter(pVListAdapter, rvZhengGaiImgs, selectList);
        PhotoCameraUtils.init(this).photoCameraDialogListAdapter(pVListAdapter, rvZhengGaiImgs, selectList, 0);
    }

    private void getDetail(final String id) {
        HttpRequest.get(this).url(ServerInterface.xcjcTroubleDetail).params("id", id).
                doGet(new HttpObjectCallBack<ProblemDetailBean>(ProblemDetailBean.class) {
                    @Override
                    public void onSuccess(BaseBean<ProblemDetailBean> response) {
                        if (response.code.equals("0")) {
                            ToastUtils.myToast(act, response.msg);
                            return;
                        } else {
                            if (response.data.xcjcProblem != null) {
                                ProblemDetailBean.XcjcProblemBean bean = response.data.xcjcProblem;
                                tv_biaoDuan.setText(bean.sectionName);
                                sectionId = bean.sectionId;
                                roomId = bean.room;
                                problemId = bean.id + "";
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
                                if (StringUtils.isEmpty(bean.serious) || "1".equals(bean.serious)) {
                                    tv_state01.setVisibility(View.INVISIBLE);
                                } else {
                                    tv_state01.setVisibility(View.VISIBLE);
                                    if (bean.serious.equals("2")) {
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
                                batchId = bean.batchId;
                                itemId = bean.id + "";
                                tv_buWei.setText(bean.towerName + bean.unitName + "单元" + bean.roomName);
                                tv_jcx.setText(bean.inspectionName);
                                tv_desc.setText(bean.particularsName);
                                //问题照片
                                if (listEmpty(bean.problemImageList)) {
                                    problemList.clear();
                                    for (String path : bean.problemImageList) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        problemList.add(localMedia);
                                    }
                                    pVShowAdapter.setNewData(problemList);
                                    pVShowAdapter.notifyDataSetChanged();
                                }
                                //提交人
                                tvSubmitPeople.setText(bean.submitName);
                        /*if (listEmpty(bean.submitList)) {
                            submitLi = new ArrayList<>();
                            for (int i = 0; i < bean.submitList.size(); i++) {
                                submitLi.add(new ItemBean(bean.submitList.get(i).peopleuser, "", bean.submitList.get(i).mobile));
                            }
                            submitAdapter = new LabelTextAdapter(act, submitLi, R.layout.label_layout_item);
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
                        }*/
                                tvSubmitDate.setText(bean.submitDate);
                                tv_zgTime.setText(String.valueOf(bean.rectifyTimelimit));
                                if (StringUtils.isEmpty(bean.rectifyName)) {
                                    iv_window.setVisibility(View.GONE);
                                } else {
                                    iv_window.setVisibility(View.VISIBLE);
                                }
                                tv_zgRen.setText(bean.rectifyName);
                                //整改照片
                                filePath = bean.rectifyImage;
//                        if (listEmpty(bean.rectifyImageList)) {
//                            for (String path : bean.rectifyImageList) {
//                                LocalMedia localMedia = new LocalMedia();
//                                localMedia.setPath(path);
//                                localMedia.setPictureType(PVAUtils.getFileLastType(path));
//                                selectList.add(localMedia);
//                            }
//                            pVListAdapter.setNewData(selectList);
//                            pVListAdapter.notifyDataSetChanged();
//                        }
                                et_instruct_content.setText(bean.particularsSupplement);
                                if (StringUtils.isEmpty(bean.dutyUnitName)) {
                                    tv_zrRen.setHint("请选择");
                                } else {
                                    tv_zrRen.setText(bean.dutyUnitName);
                                }
                                dutyUnit = bean.dutyUnit;
                                rectify = bean.rectify;
                                //复验人
                                review = bean.review;
                                if (listEmpty(bean.xcjcReviewList)) {
                                    fuyanLi = new ArrayList<>();
                                    for (int i = 0; i < bean.xcjcReviewList.size(); i++) {
                                        fuyanLi.add(new ItemBean(bean.xcjcReviewList.get(i).peopleuser, bean.xcjcReviewList.get(i).id + "", bean.xcjcReviewList.get(i).tel,
                                                bean.xcjcReviewList.get(i).isReview, bean.xcjcReviewList.get(i).reviewDate, bean.xcjcReviewList.get(i).reviewSupplement));
                                    }
                                    fuyanAdapter = new LabelTextAdapter(act, fuyanLi, "xcJc_problemDetail_fyPeople");
                                    fuyanList.setAdapter(fuyanAdapter);
                                    fuyanAdapter.notifyDataSetChanged();
                                    List<String> idList = new ArrayList<>();
                                    for (int i = 0; i < fuyanLi.size(); i++) {
                                        idList.add(fuyanLi.get(i).strId);
                                    }
                                    review = StringUtils.listToStrByStr(idList, ",");
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
                                cc = bean.cc;
                                if (listEmpty(bean.ccList)) {
                                    chaoSongLi = new ArrayList<>();
                                    for (int i = 0; i < bean.ccList.size(); i++) {
                                        chaoSongLi.add(new ItemBean(bean.ccList.get(i).peopleuser, bean.ccList.get(i).tel, bean.ccList.get(i).userId, bean.ccList.get(i).userAppTag + ""));
                                    }
                                    chaoSongAdapter = new LabelTextAdapter(act, chaoSongLi, "xcJc_problemDetail_OtherPeople");
                                    csList.setAdapter(chaoSongAdapter);
                                    chaoSongAdapter.notifyDataSetChanged();
                                    List<String> idList = new ArrayList<>();
                                    for (int i = 0; i < chaoSongLi.size(); i++) {
                                        idList.add(chaoSongLi.get(i).strId);
                                    }
                                    rectify = StringUtils.listToStrByStr(idList, ",");
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
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(act, msg);

                    }

                });
    }

    private void uploadFile(List<File> filePaths) {
        showLoadView("");
        LogUtils.i("uploadFile==", ParameterMap.put("fileList", filePaths).toString());
        HttpRequest.get(this).url(ServerInterface.uploadFile).params(ParameterMap.put("fileList", filePaths)).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoadView();
                try {
                    JSONObject object = new JSONObject(result.toString());
                    if (object.optInt("code") == 0) {
                        JSONArray imageArray = object.optJSONArray("fileName");
                        if (arrayEmpty(imageArray)) {
                            for (int i = 0; i < imageArray.length(); i++) {
                                LocalMedia localMedia = new LocalMedia();
                                if (UiUtils.isEmpty(imageArray.optString(i))) {
                                    fileList.add(imageArray.optString(i));
                                    filePath = StringUtils.listToStrByChar(fileList, ',');
                                    localMedia.setPath(imageArray.optString(i));
                                    localMedia.setPictureType(PVAUtils.getFileLastType(imageArray.optString(i)));
                                }
                                selectList.add(localMedia);
                            }
                            pVListAdapter.setNewData(selectList);
                            pVListAdapter.notifyDataSetChanged();
                        } else {
                            filePath = "";
                        }
                    } else {
                        ToastUtils.myToast(act, object.optString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                hideLoadView();
                ToastUtils.myToast(act, msg);
            }
        });
    }

    //修改数据
    private void updateData() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                .params("id", itemId)
                .params("state", "1")//待整改
                .params("rectifyTimelimit", UiUtils.getContent(tv_zgTime))
                .params("rectify", rectify)
                .params("review", review)
                .params("cc", cc)
                .params("dutyUnit", dutyUnit)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(act, "修改成功!");
                                if (UiUtils.getContent(tvSubmitPeople).equals(UiUtils.getContent(tv_zgRen))) {
                                    llZgImgs.setVisibility(View.VISIBLE);
                                    rlInstruct.setVisibility(View.VISIBLE);
                                    tvCompleteZg.setVisibility(View.VISIBLE);
                                } else {
                                    llZgImgs.setVisibility(View.GONE);
                                    rlInstruct.setVisibility(View.GONE);
                                    tvCompleteZg.setVisibility(View.GONE);
                                }
                            } else {
                                ToastUtils.myToast(act, object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

    private void crossZG() {
        showLoadView("");
        //LogUtils.i("jsonObject==", getObjectUpData("2"));
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("2"))
                .params("id", itemId)
                .params("state", "2")
                .params("rectifyTimelimit", UiUtils.getContent(tv_zgTime))
                .params("rectify", rectify)
                .params("rectifyImage", filePath)
                .params("particularsSupplement", UiUtils.getContent(et_instruct_content))
                .params("review", review)
                .params("cc", cc)
                .params("dutyUnit", dutyUnit)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json==", result.toString());
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(act, object.optString("msg"));
                                setResult();
                            } else {
                                ToastUtils.myToast(act, object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                        ToastUtils.myToast(act, msg);
                    }
                });
    }

    private void closeZG() {
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("3"))
                .params("id", itemId)
                .params("state", "3")
                .params("closeCause", reason)
                .params("rectifyTimelimit", UiUtils.getContent(tv_zgTime))
                .params("rectify", rectify)
                .params("rectifyImage", filePath)
                .params("particularsSupplement", UiUtils.getContent(et_instruct_content))
                .params("review", review)
                .params("cc", cc)
                .params("dutyUnit", dutyUnit)
                .params("closeDate", sysTime)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json==", result.toString());
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                ToastUtils.myToast(act, object.optString("msg"));
                                setResult();
                            } else {
                                ToastUtils.myToast(act, object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                        ToastUtils.myToast(act, msg);
                    }
                });
    }

    private String getObjectUpData(String state) {
        JSONObject object = new JSONObject();
        try {
            object.putOpt("id", itemId);
            object.putOpt("state", state);
            object.putOpt("rectifyTimelimit", UiUtils.getContent(tv_zgTime));
            object.putOpt("rectify", rectify);
            object.putOpt("rectifyImage", filePath);
            object.putOpt("particularsSupplement", UiUtils.getContent(et_instruct_content));
            object.putOpt("review", review);
            object.putOpt("cc", cc);
            object.putOpt("dutyUnit", dutyUnit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    private void setResult() {
        Intent intent = new Intent(act, XianChangJianChactivity.class);
        setResult(Constant.PROBLEM_DETAIL_RESULT_CODE, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    List<File> filePaths = new ArrayList<>();
                    for (int i = 0; i < selectLi.size(); i++) {
                        String path = "";
                        if (selectLi.get(i).isCut() && !selectLi.get(i).isCompressed()) {
                            path = selectLi.get(i).getCutPath();
                        } else if (selectLi.get(i).isCompressed() || (selectLi.get(i).isCut() && selectLi.get(i).isCompressed())) {
                            path = selectLi.get(i).getCompressPath();
                        } else {
                            path = selectLi.get(i).getPath();
                        }
                        filePaths.add(new File(path));
                    }
                    uploadFile(filePaths);
                    break;
                default:
                    break;
            }
        } else if (requestCode == Calendar.SELECTDATE) {
            if (resultCode == 1001) {
                try {
                    Object date = data.getBundleExtra("bundle").getSerializable("selectCalendar");
                    if (date instanceof com.haibin.calendarview.Calendar) {
                        com.haibin.calendarview.Calendar calendar = (com.haibin.calendarview.Calendar) date;
                        tv_zgTime.setText(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay() + " " + AppUtils.getSystemTime("HH:mm:ss"));
                        updateData();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (resultCode == SelectTRPOrAU.ACCOUNTABILITY_UNIT) {
            final Bundle bundle = data.getBundleExtra("bundle");
            new CancelSureDialog(this).setOkClick("确定要修改责任单位吗？", new CancelSureDialog.OkClick() {
                @Override
                public void ok() {
                    tv_zrRen.setText(bundle.getString("name"));
                    dutyName = bundle.getString("name");
                    dutyUnit = bundle.getString("id");
                    tv_zrRen.setText(dutyName);
                    updateData();
                }
            });
        } else if (resultCode == Constant.FU_YAN_REN_CODE) {
            final String fyList = data.getStringExtra("selectType");
            new CancelSureDialog(this).setOkClick("确定要修改复验人吗？", new CancelSureDialog.OkClick() {
                @Override
                public void ok() {
                    review = data.getStringExtra("selectId");
                    if (!StringUtils.isEmpty(fyList)) {
                        fuyanLi = new ArrayList<>();
                        List<String> fyName = ListUtils.stringToList(fyList);
                        List<String> fyId = ListUtils.stringToList(review);
                        for (int i = 0; i < fyName.size(); i++) {
                            fuyanLi.add(new ItemBean(fyName.get(i), fyId.get(i) + "", "18888866666", "0", sysTime, ""));
                        }
                        fuyanAdapter = new LabelTextAdapter(act, fuyanLi, "xcJc_problemDetail_fyPeople");
                        fuyanList.setAdapter(fuyanAdapter);
                        fuyanAdapter.notifyDataSetChanged();
                        updateData();
                    }
                }
            });
        } else if (resultCode == Constant.CHAO_SONG_REN_CODE) {
            final String chaoSong = data.getStringExtra("selectType");
            new CancelSureDialog(this).setOkClick("确定要修改抄送人吗？", new CancelSureDialog.OkClick() {
                @Override
                public void ok() {
                    cc = data.getStringExtra("selectId");
                    if (!StringUtils.isEmpty(chaoSong)) {
                        chaoSongLi = new ArrayList<>();
                        List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                        List<String> chaoSongId = ListUtils.stringToList(cc);
                        for (int i = 0; i < chaoSongName.size(); i++) {
                            chaoSongLi.add(new ItemBean(chaoSongName.get(i), "18888866666", chaoSongId.get(i), "userAppTag"));
                        }
                        chaoSongAdapter = new LabelTextAdapter(act, chaoSongLi, "xcJc_problemDetail_OtherPeople");
                        csList.setAdapter(chaoSongAdapter);
                        chaoSongAdapter.notifyDataSetChanged();
                        updateData();
                    }
                }
            });
        } else if (resultCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
            new CancelSureDialog(this).setOkClick("确定要修改整改人吗？", new CancelSureDialog.OkClick() {
                @Override
                public void ok() {
                    Bundle bundle = data.getBundleExtra("bundle");
                    tv_zgRen.setText(bundle.getString("name"));
                    rectify = bundle.getString("id");
                    updateData();
                }
            });
        } else if (resultCode == Constant.XCJC_REASON_CODE) {
            reason = data.getStringExtra("reason");
            closeZG();
        }
    }
}
