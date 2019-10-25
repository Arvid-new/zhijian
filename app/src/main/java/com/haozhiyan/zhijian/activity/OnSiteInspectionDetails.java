package com.haozhiyan.zhijian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.gxyj.SendBack;
import com.haozhiyan.zhijian.activity.workXcjc.SelectAngle;
import com.haozhiyan.zhijian.activity.workXcjc.XCJCShowH5Images;
import com.haozhiyan.zhijian.adapter.PersonNameListAdapter;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.adapter.XcjcReviewListAdapter;
import com.haozhiyan.zhijian.bean.ProblemDetailBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.AppUtils;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.TimeFormatUitls;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 代办- 条目  现场检查 详情
 */
public class OnSiteInspectionDetails extends BaseActivity2 implements View.OnClickListener {

    private String id;//id
    private String sectionId;//id
    private String state;//待整改 待复验  已通过状态
    private RelativeLayout parentViewGroup;

    private String rectify = "", dutyUnit = "", review = "", cc = "";
    private List<String> fileList = new ArrayList<>();
    private boolean isReview = false;
    private ProblemDetailBean detailBean;
    //    private PictureOrVideoListAdapter adapter;
    private PictureOrVideoListNewAdapter adapter;
    private List<LocalMedia> selectList;
    private TextView taskStateTV;
    private TextView tv_bdName;
    private TextView tv_local;
    private TextView tv_jcx;
    private TextView tv_desc;
    private TextView submitName;
    private TextView submitDate;
    private TextView rectifyTimelimit;
    private TextView rectifyName;
    private TextView rectifySupplement;
    private TextView rectifyDate;
    private TextView backCause;//退回原因
    private TextView dutyUnitName;//责任单位
    private TextView isTimeout;//是否抄送
    private TextView backNumber;//退回次数
    private TextView serious;//重要
    private LinearLayout backCauseLL;
    private ImageView ivHuImg;

    private EditText descET;//补充说明输入框
    private TextView descNumTv;//输入框的问题计数显示

    private RecyclerView problemImageListRcv;//问题照片
    private RecyclerView rectifyImageRcv;//整改照片
    private RecyclerView reviewNameRcv;//复验人
    private RecyclerView reviewNameRcv2;//复验人
    private RecyclerView ccListRcv;//抄送人
    private RecyclerView backImageRcv;//退回照片
    private RecyclerView addPicRcv;//添加照片

    private String inspectionId;//
    private String inspectionName;//
    private String sysTime = AppUtils.getSystemDate("yyyy-MM-dd") + " " + AppUtils.getSystemTime("HH:mm:ss");
    private String roomId, problemId;
    private String entrance;

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            id = getIntent().getExtras().getString("id", "");
            inspectionId = getIntent().getExtras().getString("inspectionId", "");
            inspectionName = getIntent().getExtras().getString("inspectionName", "");
            entrance = getIntent().getExtras().getString("entrance", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.empty_parent;

    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("问题详情");
        setAndroidNativeLightStatusBar(true);
        setTitleRightmenu();
        parentViewGroup = findViewById(R.id.parentViewGroup);
    }

    @Override
    protected void initData() {
        getDetail();
    }

    @Override
    public void onMessageEvent(Object event) {
        if (event instanceof String) {//
            //提交退回成功
            if (event.toString().equals("cendBackSeccess")) {
                getDetail();
                EventBus.getDefault().post("changedXCJCTask");
            }
        }
    }

    private void setTitleRightmenu() {
        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 40);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dp40px, dp40px);
        ImageView close = new ImageView(getContext());
        close.setImageResource(R.mipmap.close_icon);
        close.setLayoutParams(layoutParams);
        close.setPadding(dp10px, 0, dp10px, 0);
        ImageView checktip = new ImageView(getContext());
        checktip.setPadding(dp10px, 0, dp10px, 0);
        checktip.setLayoutParams(layoutParams);
        checktip.setImageResource(R.mipmap.msg_tip_icon);
        getBarLeftView().addView(close, 1);
        getBarRightView().addView(checktip);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
        checktip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckTip.start(getContext(), inspectionId, inspectionName, "", "xcjc");
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.zhengGaiPeopleLL://整改人
                SelectTRPOrAU.select(this, rectify, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.zeRenLL://责任单位
                SelectTRPOrAU.select(this, dutyUnit, sectionId, SelectTRPOrAU.ACCOUNTABILITY_UNIT);
                break;
            case R.id.changeTimeLL://整改时间
                Calendar.check(this, rectifyTimelimit.getText().toString(), Calendar.SELECTDATE);
                break;
            case R.id.fuyanLL://复验人
                Constant.REN_TYPE = 4;
                Bundle bundle4 = new Bundle();
                bundle4.putString("id", review);
                jumpActivityForResult(SelectAngle.class, Constant.FU_YAN_REN_CODE, bundle4);
                break;
            case R.id.chaosongLL://抄送人
                Constant.REN_TYPE = 3;
                Bundle bundle3 = new Bundle();
                bundle3.putString("id", cc);
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE, bundle3);
                break;
            case R.id.commitBT://完成整改
                try {
                    //state:状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                    state = "2";
                    UpLoadUtil.init(this, selectList).Call(new UpLoadCallBack() {
                        @Override
                        public void onComplete(String paths) {
                            xcjcUpdateTrouble(paths);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.passBT://通过
                try {
                    //state:状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                    state = "2";
                    UpLoadUtil.init(this, selectList).Call(new UpLoadCallBack() {
                        @Override
                        public void onComplete(String paths) {
                            addTrouble(paths);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.closeBT://非正常关闭
                try {
                    AbnormalClosing.select(getActivity(), detailBean.xcjcProblem.id + "", AbnormalClosing.SELECT_REASON);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sendBackBT://退回
                try {
                    Intent intent = new Intent(getContext(), SendBack.class);
                    intent.putExtra("type", "0");
                    intent.putExtra("id", id);
                    intent.putExtra("sendBackNumber", detailBean.xcjcProblem.backNumber);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.iv_huImg://户型图
                Intent intent = new Intent(getActivity(), XCJCShowH5Images.class);
                intent.putExtra("roomId", roomId);
                intent.putExtra("problemId", problemId);
                startActivity(intent);
                break;
        }
    }

    private ImageView submitTalkImg;
    private ImageView rectifyTalkImg;

    /**
     * 获取详情数据
     */
    private void getDetail() {
        HttpRequest.get(getContext()).url(ServerInterface.xcjcTroubleDetail)
                .params("id", id)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            detailBean = new Gson().fromJson(result.toString(), ProblemDetailBean.class);
                        } catch (Exception e) {
                            return;
                        }
                        try {
                            selectList.clear();
                        } catch (Exception e) {
                        }
                        try {
                            sectionId = detailBean.xcjcProblem.sectionId;
                        } catch (Exception e) {
                        }
                        try {
                            review = detailBean.xcjcProblem.review;
                        } catch (Exception e) {
                        }
                        if (detailBean.code == 0) {
                            try {
                                state = detailBean.xcjcProblem.state;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                            int layout = 0;
                            switch (state) {
                                case "1": //待整改
                                    if (TextUtils.equals(entrance, "form")) {
                                        layout = R.layout.activity_dai_ban_details_daizhenggai;//俩都不是
                                    } else {
                                        if (UserInfo.create(getContext()).getUserId().equals(detailBean.xcjcProblem.submit)
                                                && UserInfo.create(getContext()).getUserId().equals(detailBean.xcjcProblem.rectify)) {
                                            layout = R.layout.activity_dai_ban_ybdg_details2;// 任务创建人和整改人是自己
                                        } else if (UserInfo.create(getContext()).getUserId().equals(detailBean.xcjcProblem.submit)) {
                                            layout = R.layout.activity_dai_ban_ybdg_details;//任务创建人是自己
                                        } else if (UserInfo.create(getContext()).getUserId().equals(detailBean.xcjcProblem.rectify)) {
                                            layout = R.layout.activity_dai_ban_details;//整改人是自己
                                        } else {
                                            layout = R.layout.activity_dai_ban_details_daizhenggai;//俩都不是
                                        }
                                    }
                                    break;
                                case "2": //待复验
                                    if (TextUtils.equals(entrance, "form")) {
                                        layout = R.layout.activity_dai_ban_details_yiban;
                                    } else {
                                        boolean isMeReviewer = false;
                                        for (int i = 0; i < detailBean.xcjcProblem.xcjcReviewList.size(); i++) {
                                            if (UserInfo.create(getContext()).getUserId()
                                                    .equals(detailBean.xcjcProblem.xcjcReviewList.get(i).review)) {
                                                isReview = "1".equals(detailBean.xcjcProblem.xcjcReviewList.get(i).isReview);
                                                isMeReviewer = true;
                                                break;
                                            }
                                        }
                                        //复验人中自己是否已经复验过  是  就显示布局  不是  就显示编辑布局
                                        if (isMeReviewer) {
                                            if (isReview) {
                                                layout = R.layout.activity_dai_ban_details_yiban;
                                            } else {
                                                layout = R.layout.activity_dai_ban_fuyan_details;
                                            }
                                        } else {
                                            layout = R.layout.activity_dai_ban_details_yiban;
                                        }
                                    }
                                    break;
                                case "3": //3.非正常关闭
                                    layout = R.layout.activity_dai_ban_details_yiban;
                                    break;
                                default: //已通过
                                    layout = R.layout.activity_dai_ban_details_yiban;
                                    break;
                            }

                            View view = LayoutInflater.from(getContext()).
                                    inflate(layout, parentViewGroup,
                                            false);
                            parentViewGroup.removeAllViews();
                            parentViewGroup.addView(view);
                            getView(view);
                            try {
                                if (state.equals("1")) {//待整改
                                    taskStateTV.setText("待整改");
                                    stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red2));
                                    try {
                                        setPictureSelectRcv(addPicRcv);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    setNameRcv(reviewNameRcv, detailBean.xcjcProblem.xcjcReviewList);
                                    StringBuffer ids = new StringBuffer();
                                    for (int i = 0; i < detailBean.xcjcProblem.xcjcReviewList.size(); i++) {
                                        ids.append(detailBean.xcjcProblem.xcjcReviewList.get(i).userId);
                                        ids.append(",");
                                    }
                                    review = ids.toString();
                                } else if (state.equals("2")) {
                                    taskStateTV.setText("待复验");
                                    stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow));
                                    try {
                                        setPictureSelectRcv(addPicRcv);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    if ("0".equals(detailBean.xcjcProblem.isReviewAll)) {
                                        try {
                                            if (reviewNameRcv.getParent().getParent() instanceof ViewGroup) {
                                                ViewGroup gp = (ViewGroup) reviewNameRcv.getParent().getParent();
                                                gp.setVisibility(View.VISIBLE);
                                            }
                                            reviewNameRcv2.setVisibility(View.GONE);
                                            setNameRcv(reviewNameRcv, detailBean.xcjcProblem.xcjcReviewList);
                                        } catch (Exception e) {
                                        }
                                    } else {
                                        reviewNameRcv2.setVisibility(View.VISIBLE);
                                        if (reviewNameRcv.getParent().getParent() instanceof ViewGroup) {
                                            ViewGroup gp = (ViewGroup) reviewNameRcv.getParent().getParent();
                                            gp.setVisibility(View.GONE);
                                        }
                                    }
                                } else if (state.equals("3")) {
                                    taskStateTV.setText("非正常关闭");
                                    stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.cp_gray_deep));
                                } else if (state.equals("4")) {
                                    taskStateTV.setText("已通过");
                                    stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.blue));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                reviewNameRcv2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                                XcjcReviewListAdapter acceptanceListAdapter
                                        = new XcjcReviewListAdapter(getActivity(), detailBean.xcjcProblem.xcjcReviewList);
                                reviewNameRcv2.setAdapter(acceptanceListAdapter);
                            } catch (Exception e) {
                            }
                            try {
                                if ((detailBean.xcjcProblem.rectifyImageList == null || detailBean.xcjcProblem.rectifyImageList.size() <= 0)
                                        && StringUtils.isEmpty(detailBean.xcjcProblem.rectifySupplement)) {
                                    if (rectifyImageRcv.getParent().getParent() instanceof ViewGroup) {
                                        ((ViewGroup) rectifyImageRcv.getParent().getParent()).setVisibility(View.GONE);
                                    }
                                } else {
                                    setPicRcv(rectifyImageRcv, detailBean.xcjcProblem.rectifyImageList);
                                    try {
                                        if (StringUtils.isEmpty(detailBean.xcjcProblem.rectifySupplement)) {
                                            rectifySupplement.setVisibility(View.VISIBLE);
                                        } else {
                                            rectifySupplement.setText(detailBean.xcjcProblem.rectifySupplement);
                                        }
                                    } catch (Exception e) { }
                                }
                            } catch (Exception e) {
                            }
                            try {
                                if (StringUtils.isEmpty(detailBean.xcjcProblem.housemap)) {
                                    ivHuImg.setVisibility(View.GONE);
                                } else {
                                    ivHuImg.setVisibility(View.VISIBLE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            roomId = detailBean.xcjcProblem.room;
                            problemId = detailBean.xcjcProblem.id + "";
                            try {
                                inspectionId = detailBean.xcjcProblem.inspectionId.split(",")[1];
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                tv_bdName.setText(String.valueOf(detailBean.xcjcProblem.sectionName));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (TextUtils.isEmpty(detailBean.xcjcProblem.towerName)
                                    && TextUtils.isEmpty(detailBean.xcjcProblem.unitName)
                                    && TextUtils.isEmpty(detailBean.xcjcProblem.roomName)) {
                                if (tv_local.getParent().getParent() instanceof ViewGroup) {
                                    ((ViewGroup) tv_local.getParent().getParent()).setVisibility(View.GONE);
                                }
                            } else {
                                try {
                                    tv_local.setText(String.valueOf(detailBean.xcjcProblem.towerName +
                                            (TextUtils.isEmpty(detailBean.xcjcProblem.unitName) ? "" : detailBean.xcjcProblem.unitName + "单元")
                                            + detailBean.xcjcProblem.roomName).replace("null", ""));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            try {
                                submitTalkImg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            SystemUtils.callPage(detailBean.xcjcProblem.submitList.get(0).tel, getContext());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                rectifyTalkImg.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        try {
                                            SystemUtils.callPage(detailBean.xcjcProblem.rectifyList.get(0).tel, getContext());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                tv_jcx.setText(String.valueOf(detailBean.xcjcProblem.inspectionName));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                String desc = String.valueOf(detailBean.xcjcProblem.particularsName
                                        + (TextUtils.isEmpty(detailBean.xcjcProblem.particularsName) ? "" : "\n\n")
                                        + detailBean.xcjcProblem.particularsSupplement).replace("null", "");
                                tv_desc.setText(StringUtils.isEmpty(desc)
                                        ? "无" : desc);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                submitName.setText(String.valueOf(detailBean.xcjcProblem.submitName));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                submitDate.setText(String.valueOf(detailBean.xcjcProblem.submitDate));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                rectifyTimelimit.setText(String.valueOf(detailBean.xcjcProblem.rectifyTimelimit));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                rectify = detailBean.xcjcProblem.rectify;
                                rectifyName.setText(String.valueOf(detailBean.xcjcProblem.rectifyName));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (TextUtils.isEmpty(detailBean.xcjcProblem.rectifyDate)) {
                                    rectifyDate.setVisibility(View.GONE);
                                } else {
                                    rectifyDate.setVisibility(View.VISIBLE);
                                    rectifyDate.setText(String.valueOf(detailBean.xcjcProblem.rectifyDate));
                                }
                            } catch (Exception e) {
                            }
                            try {
                                dutyUnit = detailBean.xcjcProblem.dutyUnit;
                            } catch (Exception e) {
                            }
                            if (TextUtils.isEmpty(detailBean.xcjcProblem.dutyUnitName)) {
                                try {
                                    dutyUnitName.setTextColor(ContextCompat.getColor(getContext(), R.color.c6_color));
                                } catch (Exception e) {
                                }
                                switch (state) {
                                    case "1":
                                        if (UserInfo.create(getContext()).getUserId().equals(detailBean.xcjcProblem.submit)) {
                                            try {
                                                dutyUnitName.setText("选填");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            try {
                                                dutyUnitName.setText("无");
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        break;
                                    default:
                                        try {
                                            dutyUnitName.setText("无");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                }
                            } else {
                                try {
                                    try {
                                        dutyUnitName.setTextColor(ContextCompat.getColor(getContext(), R.color.black2));
                                    } catch (Exception e) {
                                    }
                                    dutyUnitName.setText(detailBean.xcjcProblem.dutyUnitName);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            try {
                                if (detailBean.xcjcProblem.isTimeout.startsWith("-")) {
                                    isTimeout.setVisibility(View.VISIBLE);
                                } else {
                                    isTimeout.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                //是否退回
                                if (detailBean.xcjcProblem.backNumber > 0) {
                                    backNumber.setVisibility(View.VISIBLE);
                                    backNumber.setText("退回" + String.valueOf(detailBean.xcjcProblem.backNumber) + "次");
                                    backCauseLL.setVisibility(View.VISIBLE);
                                    if (StringUtils.isEmpty(detailBean.xcjcProblem.backCause)) {
                                        backCause.setVisibility(View.GONE);
                                    } else {
                                        backCause.setVisibility(View.VISIBLE);
                                        backCause.setText(String.valueOf(detailBean.xcjcProblem.backCause));
                                    }
                                    setPicRcv(backImageRcv, detailBean.xcjcProblem.backImageList);
                                } else {
                                    backCauseLL.setVisibility(View.GONE);
                                    backNumber.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                String seriousStatus = detailBean.xcjcProblem.serious;
                                if (TextUtils.isEmpty(seriousStatus) || "一般".equals(seriousStatus)) {
                                    serious.setVisibility(View.GONE);
                                } else {
                                    serious.setVisibility(View.VISIBLE);
                                    serious.setBackgroundResource(R.drawable.daibandetails_serious_tvback);
                                    serious.setText(detailBean.xcjcProblem.serious);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (detailBean.xcjcProblem.problemImageList == null
                                        || detailBean.xcjcProblem.problemImageList.size() <= 0) {
                                    if (problemImageListRcv.getParent() instanceof ViewGroup) {
                                        ((ViewGroup) problemImageListRcv.getParent()).setVisibility(View.GONE);
                                    }
                                } else {
                                    setPicRcv(problemImageListRcv, detailBean.xcjcProblem.problemImageList);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (detailBean.xcjcProblem.ccList == null || detailBean.xcjcProblem.ccList.size() <= 0) {
                                    List<String> aa = new ArrayList<>();
                                    if (state.equals("3") || state.equals("4") || (state.equals("2") && isReview)) {
                                        aa.add("无");
                                    } else {
                                        aa.add("选填");
                                    }
                                    setNameRcv(ccListRcv, aa);
                                } else {
                                    setNameRcv(ccListRcv, detailBean.xcjcProblem.ccList);
                                    StringBuffer ids = new StringBuffer();
                                    for (int i = 0; i < detailBean.xcjcProblem.ccList.size(); i++) {
                                        ids.append(detailBean.xcjcProblem.ccList.get(i).userId);
                                        ids.append(",");
                                    }
                                    cc = ids.toString();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }

    private View stateView;

    private void getView(View view) {
        try {
            tv_bdName = view.findViewById(R.id.tv_bdName);
        } catch (Exception e) {
            tv_bdName = null;
        }
        try {
            rectifyTalkImg = view.findViewById(R.id.rectifyTalkImg);
        } catch (Exception e) {
            rectifyTalkImg = null;
        }
        try {
            submitTalkImg = view.findViewById(R.id.submitTalkImg);
        } catch (Exception e) {
            submitTalkImg = null;
        }
        try {
            taskStateTV = view.findViewById(R.id.taskStateTV);
        } catch (Exception e) {
            taskStateTV = null;
        }
        try {
            tv_local = view.findViewById(R.id.tv_local);
        } catch (Exception e) {
            tv_local = null;
        }
        try {
            tv_jcx = view.findViewById(R.id.tv_jcx);
        } catch (Exception e) {
            tv_jcx = null;
        }
        try {
            tv_desc = view.findViewById(R.id.tv_desc);
        } catch (Exception e) {
            tv_desc = null;
        }
        try {
            submitName = view.findViewById(R.id.submitName);
        } catch (Exception e) {
            submitName = null;
        }
        try {
            submitDate = view.findViewById(R.id.submitDate);
        } catch (Exception e) {
            submitDate = null;
        }
        try {
            rectifyTimelimit = view.findViewById(R.id.rectifyTimelimit);
        } catch (Exception e) {
            rectifyTimelimit = null;
        }
        try {
            rectifyName = view.findViewById(R.id.rectifyName);
        } catch (Exception e) {
            rectifyName = null;
        }
        try {
            dutyUnitName = view.findViewById(R.id.dutyUnitName);
        } catch (Exception e) {
            dutyUnitName = null;
        }
        try {
            stateView = view.findViewById(R.id.stateView);
        } catch (Exception e) {
            stateView = null;
        }
        try {
            descET = view.findViewById(R.id.descET);
        } catch (Exception e) {
            descET = null;
        }
        try {
            ccListRcv = view.findViewById(R.id.ccListRcv);
        } catch (Exception e) {
            ccListRcv = null;
        }
        try {
            reviewNameRcv = view.findViewById(R.id.reviewNameRcv);
        } catch (Exception e) {
            reviewNameRcv = null;
        }
        try {
            reviewNameRcv2 = view.findViewById(R.id.reviewNameRcv2);
        } catch (Exception e) {
            reviewNameRcv2 = null;
        }
        try {
            rectifyDate = view.findViewById(R.id.rectifyDate);
        } catch (Exception e) {
            rectifyDate = null;
        }
        try {
            descNumTv = view.findViewById(R.id.descNumTv);
        } catch (Exception e) {
            descNumTv = null;
        }
        try {
            addPicRcv = view.findViewById(R.id.addPicRcv);
        } catch (Exception e) {
            addPicRcv = null;
        }
        try {
            problemImageListRcv = view.findViewById(R.id.problemImageListRcv);
        } catch (Exception e) {
            problemImageListRcv = null;
        }
        try {
            serious = view.findViewById(R.id.serious);
        } catch (Exception e) {
            serious = null;
        }
        try {
            isTimeout = view.findViewById(R.id.isTimeout);
        } catch (Exception e) {
            isTimeout = null;
        }
        try {
            backNumber = view.findViewById(R.id.backNumber);
        } catch (Exception e) {
            backNumber = null;
        }
        try {
            rectifySupplement = view.findViewById(R.id.rectifySupplement);
        } catch (Exception e) {
            rectifySupplement = null;
        }
        try {
            rectifyImageRcv = view.findViewById(R.id.rectifyImageRcv);
        } catch (Exception e) {
            rectifyImageRcv = null;
        }
        try {
            backCause = view.findViewById(R.id.backCause);
        } catch (Exception e) {
            backCause = null;
        }
        try {
            backCauseLL = view.findViewById(R.id.backCauseLL);
        } catch (Exception e) {
            backCauseLL = null;
        }
        try {
            backImageRcv = view.findViewById(R.id.backImageRcv);
        } catch (Exception e) {
            backImageRcv = null;
        }

        try {
            view.findViewById(R.id.zhengGaiPeopleLL).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.changeTimeLL).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.fuyanLL).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.chaosongLL).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            findViewById(R.id.zeRenLL).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.commitBT).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.passBT).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.closeBT).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            view.findViewById(R.id.sendBackBT).setOnClickListener(this);
        } catch (Exception e) {

        }
        try {
            ivHuImg = view.findViewById(R.id.iv_huImg);
            ivHuImg.setOnClickListener(this);
        } catch (Exception e) {
            ivHuImg = null;
        }

        try {
            setDescET(descET, descNumTv);
        } catch (Exception e) {

        }
    }

    /**
     * 设置 选择照片的那个 RCV 控件设置数据 可复用
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {
        try {
            pictureSelectRcv.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
            pictureSelectRcv.setNestedScrollingEnabled(true);
            if (selectList == null)
                selectList = new ArrayList<>();
            //adapter = new PictureOrVideoListAdapter(getContext());
            adapter = new PictureOrVideoListNewAdapter(getContext());
            PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
        } catch (Exception e) {}
    }

    /**
     * 设置名字列表RCV 不同布局文件可复用
     */
    private void setNameRcv(final RecyclerView nameRcv, List aClass) {
        if (aClass != null && aClass.size() > 0) {
            nameRcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
            PersonNameListAdapter chaosongAdapter = new PersonNameListAdapter(getContext());
            chaosongAdapter.setNewData(aClass);
            nameRcv.setAdapter(chaosongAdapter);
            chaosongAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    if (view.getId() == R.id.talkImg) {
                        ToastUtils.myToast(OnSiteInspectionDetails.this, "");
                    }
                }
            });
            chaosongAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    View parent = (View) nameRcv.getParent();
                    int vid = parent.getId();
                    if (parent instanceof LinearLayout && vid > 0) {
                        onClick(parent);
                    } else {
                        View parent2 = (View) parent.getParent();
                        int vid2 = parent2.getId();
                        if (parent2 instanceof LinearLayout && vid2 > 0) {
                            onClick(parent2);
                        }
                    }
                }
            });
        }
    }

    /**
     * 代办-  照片列表  能复用
     */
    private void setPicRcv(RecyclerView picRcv, final List<String> imgs) {
        try {
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
        } catch (Exception e) {}
    }

    /**
     * 输入框监听方法 可复用
     *
     * @param descET
     * @param descNumTv
     */
    private void setDescET(final EditText descET, final TextView descNumTv) {
        descET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!StringUtils.isEmpty(s.toString())) {
                    int num = 200 - s.length();
                    if (num <= 0) {
                        descET.setText(s);
                    }
                    descNumTv.setText(num + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片、视频、音频选择结果回调
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    //uploadFile(filePaths);
                    break;
            }
        } else if (requestCode == Calendar.SELECTDATE) {
            if (resultCode == 1001) {
                try {
                    Object date = data.getBundleExtra("bundle").getSerializable("selectCalendar");
                    if (date instanceof com.haibin.calendarview.Calendar) {
                        com.haibin.calendarview.Calendar calendar = (com.haibin.calendarview.Calendar) date;
                        rectifyTimelimit.setText(calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
                        xcjcUpdateTrouble("rectifyTimelimit", calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == Constant.FU_YAN_REN_CODE) {
            String chaoSong = data.getStringExtra("selectType");
            review = data.getStringExtra("selectId");
            if (!StringUtils.isEmpty(chaoSong)) {
                List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                setNameRcv(reviewNameRcv, chaoSongName);
                xcjcUpdateTrouble("review", review);
            }
        } else if (requestCode == Constant.CHAO_SONG_REN_CODE) {
            String chaoSong = data.getStringExtra("selectType");
            cc = data.getStringExtra("selectId");
            if (!StringUtils.isEmpty(chaoSong)) {
                List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                setNameRcv(ccListRcv, chaoSongName);
                xcjcUpdateTrouble("cc", cc);
            }
        } else if (requestCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
            try {
                String zhengairen = data.getBundleExtra("bundle").getString("name");
                rectify = data.getBundleExtra("bundle").getString("id");
                rectifyName.setText(zhengairen);
                xcjcUpdateTrouble("rectify", rectify);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SelectTRPOrAU.ACCOUNTABILITY_UNIT) {//责任单位
            try {
                String zeRen = data.getBundleExtra("bundle").getString("name");
                String zeRenID = data.getBundleExtra("bundle").getString("id");
                dutyUnit = zeRenID;
                dutyUnitName.setText(zeRen);
                xcjcUpdateTrouble("dutyUnit", dutyUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == AbnormalClosing.SELECT_REASON) {//非正常关闭
            try {
                String reason = data.getStringExtra("cause");
                String id = data.getStringExtra("id");
                //  state:状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                state = "3";
                taskclose(reason);//非正常关闭
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 更新 参数
     */
    private void xcjcUpdateTrouble(String key, String value) {
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("3"))
                .params("id", id)
                .params(key, value)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json==", result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                getDetail();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    /**
     * 完成整改
     */
    private void xcjcUpdateTrouble(String filePath) {
        showLoadView("请稍等...");
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("3"))
                .params("id", id)
                .params("state", "2")//状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                .params("rectify", userInfo.getUserId())//整改人id
                .params("rectifyDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//整改时间，datetime类型
                .params("rectifyImage", filePath)//整改照片名称，多个以逗号隔开  StringUtils.medialistToStrByChar(selectList, ',')
                .params("rectifySupplement", descET == null ? "" : descET.getText().toString().trim(), true)//整改补充说明
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        LogUtils.i("json==", result.toString());
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                getDetail();
                                EventBus.getDefault().post("changedXCJCTask");
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));

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

    /**
     * 非正常关闭
     */
    private void taskclose(String closeCause) {
        HttpRequest.get(this).url(ServerInterface.xcjcUpdateTrouble)
                //.params("xcjcProblem", getObjectUpData("3"))
                .params("id", id)
                .params("state", "3")//状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                .params("closeCause", closeCause)//非正常关闭原因
                .params("closeDate", TimeFormatUitls.SecondTotimeStr(System.currentTimeMillis()))//关闭时间，datetime类型
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json==", result.toString());
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                getDetail();
                                EventBus.getDefault().post("changedXCJCTask");
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    /**
     * 更改状态-通过
     */
    private void addTrouble(String filePath) {
        showLoadView("....");
        HttpRequest.get(this).url(ServerInterface.reviewProblem)
                .params("problemId", detailBean.xcjcProblem.id)//现场检查问题id
                .params("review", userInfo.getUserId())//当前操作用户id
                .params("reviewDate", sysTime)//复验时间，DateTime类型
                .params("reviewImage", filePath)//复验照片名称，多个以逗号隔开  StringUtils.medialistToStrByChar(selectList, ',')
                .params("reviewSupplement", UiUtils.getContent(descET), true)//复验补充说明
                .params("isReview", "1")//是否复验：0未复验，1已复验
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                getDetail();
                                EventBus.getDefault().post("changedXCJCTask");
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

}
