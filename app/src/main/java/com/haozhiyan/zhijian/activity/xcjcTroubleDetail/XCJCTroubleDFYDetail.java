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
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.workXcjc.SelectAngle;
import com.haozhiyan.zhijian.activity.workXcjc.XCJCShowH5Images;
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
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.view.MyListView;
import com.haozhiyan.zhijian.widget.CancelSureDialog;
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

//2待复验
public class XCJCTroubleDFYDetail extends BaseActivity {

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
    @ViewInject(R.id.rv_troubleImg)
    RecyclerView troubleIv;
    @ViewInject(R.id.tv_date)
    TextView tvSubmitDate;
    @ViewInject(R.id.submitList)
    MyListView submitList;
    @ViewInject(R.id.tv_zgTime)
    TextView tv_zgTime;
    @ViewInject(R.id.tv_zgRen)
    TextView tv_zgRen;
    @ViewInject(R.id.iv_window)
    ImageView iv_window;
    @ViewInject(R.id.rv_zhengGaiImg)
    RecyclerView rvZhengGaiImgs;
    @ViewInject(R.id.rv_fuyanImgs)
    RecyclerView rv_fuyanImgs;
    @ViewInject(R.id.fuyanList)
    MyListView fuyanList;
    @ViewInject(R.id.csList)
    MyListView csList;
    @ViewInject(R.id.ll_cs)
    LinearLayout ll_cs;//选择抄送人(抄送人布局)
    @ViewInject(R.id.cs_img)
    ImageView csImg;//抄送人布局右侧箭头
    @ViewInject(R.id.zrdw_img)
    ImageView zrdwImg;//责任单位布局右侧箭头
    @ViewInject(R.id.et_instruct_content)
    EditText et_instruct_content;
    @ViewInject(R.id.id_editor_detail_font_count)
    TextView id_editor_detail_font_count;
    @ViewInject(R.id.tv_zeRenDanWei)
    TextView tv_zeRenDanWei;//选择责任单位
    @ViewInject(R.id.tv_cross)
    TextView tvCross;//通过按钮
    @ViewInject(R.id.tv_back)
    TextView tvBack;//退回按钮
    @ViewInject(R.id.tv_zhengGaiDate)
    TextView tv_zhengGaiDate;//整改日期
    @ViewInject(R.id.iv_huImg)
    ImageView ivHuImg;
    @ViewInject(R.id.ll_fuyan)
    LinearLayout ll_fuyan;
    @ViewInject(R.id.tv_state01)
    TextView tv_state01;
    @ViewInject(R.id.tv_state02)
    TextView tv_state02;
    @ViewInject(R.id.layout_problemImg_desc)
    LinearLayout layoutProblemImgDesc;//复验照片-描述布局

    private PictureVideoShowAdapter pVShowWTAdapter, pVShowZGAdapter;
    //    private PictureOrVideoListAdapter pVListAdapter;
    private PictureOrVideoListNewAdapter pVListAdapter;
    private LabelTextAdapter submitAdapter, fuyanAdapter, chaoSongAdapter;
    private List<LocalMedia> problemList = new ArrayList<>();
    private List<LocalMedia> zhengGaiList = new ArrayList<>();
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<ItemBean> submitLi, fuyanLi, chaoSongLi;
    private String id = "", itemId = "", rectify = "", review = "", cc = "", dutyUnit = "", filePath = "", dutyName = "", batchId = "", sectionId = "";
    private String userRole = "", roomId = "", submitToReviewTag = "";
    private List<String> fileList = new ArrayList<>();
    private List<ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean> reviewListBean;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xcjctrouble_dfydetail;
    }

    @Override
    protected void initView() {
        tv_title.setText("问题详情");
        ivClose.setVisibility(View.VISIBLE);
        Bundle bundle = getIntent().getBundleExtra("data");
        id = bundle == null ? "1" : bundle.getString("id");
        batchId = getIntent().getStringExtra("batchId");
        submitToReviewTag = bundle == null ? "" : bundle.getString("submitToReviewTag");
        initRecycle(troubleIv);
        initRecycle(rvZhengGaiImgs);
        initRecycle(rv_fuyanImgs);
        initProblemPhoto(1);
        initProblemPhoto(2);
        initFYPic();
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
        if (submitToReviewTag.equals("1")) {//复验人和整改人相同
            layoutProblemImgDesc.setVisibility(View.GONE);
            tvBack.setVisibility(View.GONE);
            tvCross.setVisibility(View.GONE);
        } else if (submitToReviewTag.equals("0")) {//复验人和整改人不相同
            layoutProblemImgDesc.setVisibility(View.VISIBLE);
            tvBack.setVisibility(View.VISIBLE);
            tvCross.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.tv_cross, R.id.tv_back, R.id.ll_fuyan, R.id.ll_cs, R.id.tv_zeRenDanWei, R.id.iv_huImg})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                ActivityManager.getInstance().removeActivity(this);
                jumpToActivity(MainActivity.class);
                break;
            case R.id.tv_cross:
                if (StringUtils.isEmpty(filePath)) {
                    ToastUtils.myToast(act, "请拍摄照片");
                } else {
                    crossZG();
                }
                break;
            case R.id.tv_back:
                if (StringUtils.isEmpty(filePath)) {
                    ToastUtils.myToast(act, "请拍摄照片");
                } else {
                    backZG();
                }
                break;
            case R.id.ll_fuyan:
                Constant.REN_TYPE = 5;
                jumpActivityForResult(SelectAngle.class, Constant.FU_YAN_REN_CODE);
                break;
            case R.id.ll_cs:
                Constant.REN_TYPE = 3;
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE);
                break;
            case R.id.tv_zeRenDanWei:
                SelectTRPOrAU.select(this, UiUtils.getContent(tv_zeRenDanWei), sectionId, SelectTRPOrAU.ACCOUNTABILITY_UNIT);
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

    //初始待复验问题图片
    private void initProblemPhoto(int index) {
        if (index == 1) {//问题1图片
            pVShowWTAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowWTAdapter, troubleIv, problemList);
        } else if (index == 2) {//问题2图片
            pVShowZGAdapter = new PictureVideoShowAdapter(this);
            PhotoCameraUtils.init(this).photoCameraAdapter(pVShowZGAdapter, rvZhengGaiImgs, zhengGaiList);
        }
    }

    //初始复验图片
    private void initFYPic() {
        pVListAdapter = new PictureOrVideoListNewAdapter(this);
        //pVListAdapter.setSelectFileType(3);
        //PhotoCameraUtils.init(this).photoCameraListAdapter(pVListAdapter, rv_fuyanImgs, selectList);
        PhotoCameraUtils.init(this).photoCameraDialogListAdapter(pVListAdapter, rv_fuyanImgs, selectList, 0);
    }

    private void initRecycle(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 5, true));
        recyclerView.setNestedScrollingEnabled(true);
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
                                sectionId = bean.sectionId;
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
                                tv_zhengGaiDate.setText(bean.rectifyDate);
                                //整改照片
                                filePath = bean.rectifyImage;
                                if (listEmpty(bean.rectifyImageList)) {
                                    zhengGaiList.clear();
                                    for (String path : bean.rectifyImageList) {
                                        LocalMedia localMedia = new LocalMedia();
                                        localMedia.setPath(path);
                                        localMedia.setPictureType(PVAUtils.getFileLastType(path));
                                        zhengGaiList.add(localMedia);
                                    }
                                    pVShowZGAdapter.setNewData(zhengGaiList);
                                    pVShowZGAdapter.notifyDataSetChanged();
                                }
                                dutyUnit = bean.dutyUnit;
                                rectify = bean.rectify;
                                review = bean.review;
                                //复验人
                                if (listEmpty(bean.xcjcReviewList)) {
                                    reviewListBean = bean.xcjcReviewList;
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
                                                SystemUtils.callPage(fuyanLi.get(position).mobile, act);
                                            }
                                        }
                                    });
                                    for (int j = 0; j < bean.xcjcReviewList.size(); j++) {
                                        if (StringUtils.isEmpty(bean.xcjcReviewList.get(j).peopleuser)) {
                                            layoutProblemImgDesc.setVisibility(View.GONE);
                                            tvBack.setVisibility(View.GONE);
                                            tvCross.setVisibility(View.GONE);
                                        } else {
                                            if (bean.xcjcReviewList.get(j).peopleuser.equals(UiUtils.getContent(tv_zgRen))) {
                                                layoutProblemImgDesc.setVisibility(View.VISIBLE);
                                                tvBack.setVisibility(View.VISIBLE);
                                                tvCross.setVisibility(View.VISIBLE);
                                                break;
                                            } else {
                                                layoutProblemImgDesc.setVisibility(View.GONE);
                                                tvBack.setVisibility(View.GONE);
                                                tvCross.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                }
                                et_instruct_content.setText(bean.particularsSupplement);
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
                .params("state", "2")//待整改
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
                                getDetail(id);
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
        //LogUtils.i("jsonObject==", getObjectUpData("4"));
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("4"))
                .params("id", itemId)
                .params("state", "4")
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
                        ToastUtils.myToast(act, code + msg);
                    }
                });
    }

    private void backZG() {
        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("1"))
                .params("id", itemId)
                .params("state", "1")
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
        if (data != null) {
            if (resultCode == Constant.FU_YAN_REN_CODE) {
                new CancelSureDialog(this).setOkClick("确认修改复验人吗?", new CancelSureDialog.OkClick() {
                    @Override
                    public void ok() {
                        review = data.getStringExtra("selectId");
                        String fyList = data.getStringExtra("selectType");
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
            } else if (resultCode == RESULT_OK) {
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
            } else if (resultCode == Constant.CHAO_SONG_REN_CODE) {
                final String chaoSong = data.getStringExtra("selectType");
                new CancelSureDialog(this).setOkClick("确认修改抄送人吗？", new CancelSureDialog.OkClick() {
                    @Override
                    public void ok() {
                        cc = data.getStringExtra("selectId");
                        if (!StringUtils.isEmpty(chaoSong)) {
                            chaoSongLi = new ArrayList<>();
                            List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                            List<String> chaoSongId = ListUtils.stringToList(cc);
                            for (int i = 0; i < chaoSongName.size(); i++) {
                                chaoSongLi.add(new ItemBean(chaoSongName.get(i), chaoSongId.get(i), ""));
                            }
                            chaoSongAdapter = new LabelTextAdapter(act, chaoSongLi, "xcJc_problemDetail_OtherPeople");
                            csList.setAdapter(chaoSongAdapter);
                            chaoSongAdapter.notifyDataSetChanged();
                            updateData();
                        }
                    }
                });
            } else if (resultCode == SelectTRPOrAU.ACCOUNTABILITY_UNIT) {
                new CancelSureDialog(this).setOkClick("确认修改责任单位?", new CancelSureDialog.OkClick() {
                    @Override
                    public void ok() {
                        Bundle bundle = data.getBundleExtra("bundle");
                        tv_zeRenDanWei.setText(bundle.getString("name"));
                        dutyName = bundle.getString("name");
                        dutyUnit = bundle.getString("id");
                        updateData();
                    }
                });
            }
        }
    }
}
