package com.haozhiyan.zhijian.activity.gxyj;

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
import com.haozhiyan.zhijian.activity.AbnormalClosing;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.Calendar;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.activity.SelectTRPOrAU;
import com.haozhiyan.zhijian.activity.ShowBigImg;
import com.haozhiyan.zhijian.activity.ShowVideo;
import com.haozhiyan.zhijian.activity.workXcjc.SelectAngle;
import com.haozhiyan.zhijian.adapter.PersonNameListAdapter;
import com.haozhiyan.zhijian.adapter.PicShowListAdapter;
import com.haozhiyan.zhijian.adapter.PictureOrVideoListNewAdapter;
import com.haozhiyan.zhijian.bean.GXYJProblemDetailBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.UpLoadCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.PVAUtils;
import com.haozhiyan.zhijian.utils.PhotoCameraUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UpLoadUtil;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代办- 条目  详情
 */
public class GXYJProblemDetails extends BaseActivity2 implements View.OnClickListener {

    private String id;//id
    private RelativeLayout parentViewGroup;

    private String rectify = "", dutyUnit = "", review = "", cc = "";
    private List<String> fileList = new ArrayList<>();
    private List<String> photoPaths = new ArrayList<>();
    private List<String> vedioPaths = new ArrayList<>();
    private boolean isReview = false;
    private GXYJProblemDetailBean detailBean;
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

    private EditText descET;//补充说明输入框
    private TextView descNumTv;//输入框的问题计数显示

    private RecyclerView problemImageListRcv;//问题照片
    private RecyclerView rectifyImageRcv;//整改照片
    private RecyclerView reviewNameRcv;//复验人
    private RecyclerView ccListRcv;//抄送人
    private RecyclerView backImageRcv;//退回照片
    private RecyclerView addPicRcv;//添加照片

    private String sectionId;
//    private String sysTime = AppUtils.getSystemDate("yyyy-MM-dd") + " " + AppUtils.getSystemTime("HH:mm:ss");

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            id = getIntent().getExtras().getString("id", "");
            sectionId = getIntent().getExtras().getString("sectionId", "");
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
                EventBus.getDefault().post("changedGXYJTask");
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
//        ImageView checktip = new ImageView(getContext());
//        checktip.setPadding(dp10px, 0, dp10px, 0);
//        checktip.setLayoutParams(layoutParams);
//        checktip.setImageResource(R.mipmap.msg_tip_icon);
        getBarLeftView().addView(close, 1);
//        getBarRightView().addView(checktip);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(MainActivity.class);
            }
        });
//        checktip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                CheckTip.start(getContext(), inspectionId, inspectionName, "");
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.abarbeitungLL://整改人
                SelectTRPOrAU.select(this, detailBean.zhenggaiId, SelectTRPOrAU.THE_RECTIFICATION_PEOPLE);
                break;
            case R.id.zeRenLL://责任单位
                SelectTRPOrAU.select(this, dutyUnitName.getText().toString(), sectionId, SelectTRPOrAU.ACCOUNTABILITY_UNIT);
                break;
            case R.id.changeTimeLL://整改时间
                Calendar.check(this, rectifyTimelimit.getText().toString(), Calendar.SELECTDATE);
                break;
            case R.id.fuyanLL://复验人
                SelectTRPOrAU.select(this, detailBean.fuyanId, SelectTRPOrAU.THE_FUYAN_PEOPLE2);
                break;
            case R.id.chaosongLL://抄送人
                Constant.REN_TYPE = 3;
                jumpActivityForResult(SelectAngle.class, Constant.CHAO_SONG_REN_CODE);
                break;
            case R.id.commitBT://完成整改
                try {

                    //state:状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
//                        state = "2";
                    UpLoadUtil.init(getActivity(), selectList).Call(new UpLoadCallBack() {
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
//                        state = "2";
                    updateAbarbeitungPass();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.closeBT://非正常关闭
                try {
                    AbnormalClosing.select(getActivity(), detailBean.id, AbnormalClosing.SELECT_REASON);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.sendBackBT://退回
                try {
                    Intent intent = new Intent(getContext(), SendBack.class);
                    intent.putExtra("type", "2");
                    intent.putExtra("id", id);
                    intent.putExtra("appGxyjId", detailBean.appGxyjId);
                    intent.putExtra("roomId", detailBean.roomId);
                    intent.putExtra("sendBackNumber", detailBean.sendBackNumber);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    /**
     * 获取详情数据
     */
    private void getDetail() {
        HttpRequest.get(getContext()).url(ServerInterface.selectAbarbeitung)
                .params("id", id)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(result.toString());
                        } catch (Exception e) {
                            return;
                        }
                        if (jsonObject.optString("code").equals("0")) {
//                            JSONArray jsonArray = jsonObject.optJSONArray("list");
//                            try {
//                                if (jsonArray.length() <= 0) {
//                                    return;
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                                return;
//                            }
                            detailBean = new Gson().fromJson(jsonObject.optString("list"), GXYJProblemDetailBean.class);

                            //状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
                            int layout = 0;
                            isReview = false;
                            switch (detailBean.ztCondition) {
                                case "待整改": //
                                    if (UserInfo.create(getContext()).getUserId().equals(detailBean.tijiaoId)
                                            && UserInfo.create(getContext()).getUserId().equals(detailBean.zhenggaiId)) {
                                        layout = R.layout.activity_gxyj_problem_detail1;// 任务创建人和整改人是自己
                                    } else if (UserInfo.create(getContext()).getUserId().equals(detailBean.tijiaoId)) {
                                        layout = R.layout.activity_gxyj_problem_details2;//任务创建人是自己
                                    } else if (UserInfo.create(getContext()).getUserId().equals(detailBean.zhenggaiId)) {
                                        layout = R.layout.activity_gxyj_problem_details3;//整改人是自己
                                    } else {
                                        layout = R.layout.activity_gxyj_problem_details4;//俩都不是
                                    }
                                    break;
                                case "待复验": //
//                                    for (int i = 0; i < detailBean.xcjcProblem.xcjcReviewList.size(); i++) {
                                    if (UserInfo.create(getContext()).getUserId()
                                            .equals(detailBean.fuyanId)) {
                                        isReview = "待复验".equals(detailBean.fuyanState);
                                    }
//                                    }
                                    //复验人中自己是否已经复验过  是  就显示布局  不是  就显示编辑布局
                                    if (isReview) {
                                        layout = R.layout.activity_gxyj_problem_details5;
                                    } else {
                                        layout = R.layout.activity_gxyj_problem_details7;

                                    }
                                    break;
                                case "非正常关闭": //3.
                                    layout = R.layout.activity_gxyj_problem_details6;
                                    break;
                                default: //已通过
                                    layout = R.layout.activity_gxyj_problem_details7;
                                    break;
                            }
                            View view = LayoutInflater.from(getContext()).
                                    inflate(layout, parentViewGroup,
                                            false);
                            parentViewGroup.addView(view);
                            getView(view);
                            if ("待整改".equals(detailBean.ztCondition)) {//待整改
                                taskStateTV.setText("待整改");
                                try {
                                    setPictureSelectRcv(addPicRcv);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.red2));
                            } else if ("待复验".equals(detailBean.ztCondition)) {
                                taskStateTV.setText("待复验");
                                stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.yellow));
                            } else if ("非正常关闭".equals(detailBean.ztCondition)) {
                                taskStateTV.setText("非正常关闭");
                                stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.cp_gray_deep));
                            } else if ("已通过".equals(detailBean.ztCondition)) {
                                taskStateTV.setText("已通过");
                                stateView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.green2));
                            }
                            try {
                                if (detailBean.listIssuePicture == null
                                        || detailBean.listIssuePicture.size() <= 0) {
                                    if (problemImageListRcv.getParent() instanceof ViewGroup) {
                                        ((ViewGroup) problemImageListRcv.getParent()).setVisibility(View.GONE);
                                    }
                                } else {
                                    setPicRcv(problemImageListRcv, detailBean.listIssuePicture);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                tv_bdName.setText(detailBean.projectName + detailBean.dikuaiName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (StringUtils.isEmpty(detailBean.pointSite)) {
                                    pointSiteImg.setVisibility(View.GONE);
                                } else {
                                    pointSiteImg.setVisibility(View.VISIBLE);
                                    pointSiteImg.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(getActivity(), GxYjH5.class);
                                            intent.putExtra("roomId", detailBean.roomId);
                                            intent.putExtra("keyId", detailBean.appGxyjId);
                                            intent.putExtra("houseMap", detailBean.pointSite);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            } catch (Exception e) {}
                            try {
                                if (!TextUtils.isEmpty(detailBean.siteName)) {
                                    tv_local.setText(detailBean.siteName);
                                } else {
                                    if (tv_local.getParent().getParent() instanceof ViewGroup) {
                                        ((ViewGroup) tv_local.getParent().getParent()).setVisibility(View.GONE);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                tv_jcx.setText(detailBean.itemsName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                String desc = String.valueOf(detailBean.description
                                        + (StringUtils.isEmpty(detailBean.buchongExplain) ? "" : "\n\n")
                                        + detailBean.buchongExplain).replace("null", "");
                                tv_desc.setText(StringUtils.isEmpty(desc)
                                        ? "无" : desc);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                submitName.setText(detailBean.tijiaoPeople);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ((TextView) view.findViewById(R.id.closeTimeTV))
                                        .setText(detailBean.closeTime);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                ((TextView) view.findViewById(R.id.closeCauseTV))
                                        .setText(detailBean.closeCause);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                view.findViewById(R.id.creatorTalkImg).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SystemUtils.callPage(detailBean.listTijiaoPeople.get(0).tel, getContext());
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                submitDate.setText(detailBean.creatorTime);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                rectifyTimelimit.setText(detailBean.abarbeitungTime);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                if (TextUtils.isEmpty(detailBean.accountabilityUnit)) {
                                    dutyUnitName.setText("无");
                                } else {
                                    dutyUnitName.setText(detailBean.accountabilityUnit);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                rectifyName.setText(detailBean.zhenggaiPeople);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                view.findViewById(R.id.zhenggaiTalkImg).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SystemUtils.callPage(detailBean.listZhenggaiPeople.get(0).tel, getContext());
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            setNameRcv(reviewNameRcv, detailBean.listFuyanPeople);
                            try {
                                if (detailBean.listArbeitungPicture == null || detailBean.listArbeitungPicture.size() <= 0) {
                                    if (rectifyImageRcv.getParent() instanceof ViewGroup) {
                                        ((ViewGroup) rectifyImageRcv.getParent()).setVisibility(View.GONE);
                                    }
                                } else {
                                    setPicRcv(rectifyImageRcv, detailBean.listArbeitungPicture);
                                }
                            } catch (Exception e) {}

                            try {
                                if (StringUtils.isEmpty(detailBean.abarbeitungExplain)) {
                                    if (rectifySupplement.getParent() instanceof ViewGroup) {
                                        ((ViewGroup) rectifySupplement.getParent()).setVisibility(View.GONE);
                                    }
                                } else {
                                    rectifySupplement.setText(detailBean.abarbeitungExplain);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                //是否退回
                                if (detailBean.sendBackNumber > 0) {
                                    backNumber.setVisibility(View.VISIBLE);
                                    backNumber.setText("退回" + detailBean.sendBackNumber + "次");
                                    backCauseLL.setVisibility(View.VISIBLE);
                                    if (StringUtils.isEmpty(detailBean.sendBackCause)) {
                                        backCause.setVisibility(View.GONE);
                                    } else {
                                        backCause.setVisibility(View.VISIBLE);
                                        backCause.setText(detailBean.sendBackCause);
                                    }
                                    setPicRcv(backImageRcv, detailBean.listSendBackPicture);
                                } else {
                                    backCauseLL.setVisibility(View.GONE);
                                    backNumber.setVisibility(View.GONE);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (detailBean.listChaosongPeople == null || detailBean.listChaosongPeople.size() <= 0) {
                                    List<String> aa = new ArrayList<>();
                                    if (detailBean.ztCondition.equals("非正常关闭")
                                            || detailBean.ztCondition.equals("已通过")
                                            || (detailBean.ztCondition.equals("待复验") && isReview)) {
                                        aa.add("无");
                                    } else {
                                        aa.add("选填");
                                    }
                                    setNameRcv(ccListRcv, aa);
                                } else {
                                    setNameRcv(ccListRcv, detailBean.listChaosongPeople);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {
                                if (StringUtils.isEmpty(detailBean.zhenggaiTime)) {
                                    rectifyDate.setVisibility(View.GONE);
                                } else {
                                    rectifyDate.setText(detailBean.zhenggaiTime);
                                }
                            } catch (Exception e) {}
                            try {
                                if (TextUtils.isEmpty(detailBean.orderOfSeverity) || "一般".equals(detailBean.orderOfSeverity)) {
                                    serious.setVisibility(View.GONE);
                                } else {
                                    serious.setVisibility(View.VISIBLE);
                                    serious.setText(detailBean.orderOfSeverity);
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
    private ImageView pointSiteImg;

    private void getView(View view) {
        try {
            tv_bdName = view.findViewById(R.id.tv_bdName);
        } catch (Exception e) {
            tv_bdName = null;
        }
        try {
            taskStateTV = view.findViewById(R.id.taskStateTV);
        } catch (Exception e) {
            taskStateTV = null;
        }
        try {
            pointSiteImg = view.findViewById(R.id.pointSiteImg);
        } catch (Exception e) {
            pointSiteImg = null;
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
            view.findViewById(R.id.abarbeitungLL).setOnClickListener(this);
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
            setDescET(descET, descNumTv);
        } catch (Exception e) {

        }
    }

    /**
     * 设置 选择照片的那个 RCV 控件设置数据 可复用
     */
    private void setPictureSelectRcv(RecyclerView pictureSelectRcv) {
        pictureSelectRcv.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));
        pictureSelectRcv.setNestedScrollingEnabled(true);
        selectList = new ArrayList<>();
        //adapter = new PictureOrVideoListAdapter(getContext());
        adapter = new PictureOrVideoListNewAdapter(getContext());
        PhotoCameraUtils.init(this).photoCameraDialogListAdapter(adapter, pictureSelectRcv, selectList, 1);
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
                        ToastUtils.myToast(GXYJProblemDetails.this, "");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    //int ss = selectList.size();
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true  注意：音视频除外
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true  注意：音视频除外
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    List<LocalMedia> selectLi = PictureSelector.obtainMultipleResult(data);
                    selectList.addAll(selectLi);
                    adapter.setNewData(selectList);
                    for (int i = 0; i < selectLi.size(); i++) {
                        int pictureType = PictureMimeType.isPictureType(selectLi.get(i).getPictureType());
                        if (pictureType == PictureConfig.TYPE_IMAGE) {//图片
                            if (selectLi.get(i).isCut()) {
                                photoPaths.add(selectLi.get(i).getCutPath());
                            } else {
                                photoPaths.add(selectLi.get(i).getPath());
                            }
                        } else if (pictureType == PictureConfig.TYPE_VIDEO) {//视频
                            vedioPaths.add(selectLi.get(i).getPath());
                        }
                    }
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
//                        xcjcUpdateTrouble("rectifyTimelimit",
//                                calendar.getYear() + "-" + calendar.getMonth() + "-" + calendar.getDay());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if (requestCode == Constant.CHAO_SONG_REN_CODE) {
            String chaoSong = data.getStringExtra("selectType");
            cc = data.getStringExtra("selectId");
            if (!StringUtils.isEmpty(chaoSong)) {
//                chaoSongLi = new ArrayList<>();
                List<String> chaoSongName = ListUtils.stringToList(chaoSong);
                setNameRcv(ccListRcv, chaoSongName);
                updateZGWT("chaosongPeople", chaoSong, "chaosongId", cc);
            }
        } else if (requestCode == SelectTRPOrAU.THE_RECTIFICATION_PEOPLE) {
            try {
                String zhengairen = data.getBundleExtra("bundle").getString("name");
                rectify = data.getBundleExtra("bundle").getString("id");
                rectifyName.setText(zhengairen);
                updateZGWT("zhenggaiPeople", zhengairen, "zhenggaiId", rectify);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == SelectTRPOrAU.THE_FUYAN_PEOPLE2) {
            try {
                String fuyan = data.getBundleExtra("bundle").getString("name");
                review = data.getBundleExtra("bundle").getString("id");
                List<String> fuyans = new ArrayList<>();
                if (fuyan.contains(",")) {
                    fuyans = Arrays.asList(fuyan.split(","));
                } else {
                    fuyans.add(fuyan);
                }
                setNameRcv(reviewNameRcv, fuyans);
                updateZGWT("fuyanPeople", fuyan, "fuyanId", review);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == SelectTRPOrAU.ACCOUNTABILITY_UNIT) {//责任单位
            try {
                String zeRen = data.getBundleExtra("bundle").getString("name");
                dutyUnit = zeRen;
                dutyUnitName.setText(zeRen);
//                xcjcUpdateTrouble("dutyUnit", dutyUnit);

//                updateZGWT("zhenggaiPeople", zhengairen,"zhenggaiId",rectify);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == AbnormalClosing.SELECT_REASON) {//非正常关闭
            try {
                String reason = data.getStringExtra("cause");
                String id = data.getStringExtra("id");
                //  state:状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
//                state = "3";
                taskclose(reason);//非正常关闭
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 完成整改
     */
    private void xcjcUpdateTrouble(String filePath) {
        HttpRequest.get(this).url(ServerInterface.updateAbarbeitung)
                //.params("xcjcProblem", getObjectUpData("3"))
                .params("id", detailBean.id)
                .params("roomId", detailBean.roomId)//
                .params("appGxyjId", detailBean.appGxyjId)//
                .params("itemsName", detailBean.itemsName)//
                .params("siteName", detailBean.siteName)//
                .params("position", detailBean.position)//
                .params("inspectionName", detailBean.inspectionName)//
                .params("description", detailBean.description)//
                .params("abarbeitungPicture", filePath)//整改照片名称，多个以逗号隔开 StringUtils.medialistToStrByChar(selectList, ',')
                .params("abarbeitungExplain", descET == null ? "" : descET.getText().toString().trim(), true)//整改补充说明
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
     * 非正常关闭
     */
    private void taskclose(String closeCause) {
        HttpRequest.get(this).url(ServerInterface.updateAbnormalClosing)
                .params("id", detailBean.id)
                .params("roomId", detailBean.roomId)//
                .params("appGxyjId", detailBean.appGxyjId)//
                .params("itemsName", detailBean.itemsName)//
                .params("siteName", detailBean.siteName)//
                .params("position", detailBean.position)//
                .params("inspectionName", detailBean.inspectionName)//
                .params("description", detailBean.description)//
                .params("closeCause", closeCause)//非正常关闭原因
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
    private void updateAbarbeitungPass() {
        showLoadView("....");
        HttpRequest.get(this).url(ServerInterface.updateAbarbeitungPass)
//                .params("problemId", detailBean.xcjcProblem.id)//现场检查问题id
                .params("id", detailBean.id)
                .params("roomId", detailBean.roomId)//
                .params("appGxyjId", detailBean.appGxyjId)//
                .params("itemsName", detailBean.itemsName)//
                .params("siteName", detailBean.siteName)//
                .params("position", detailBean.position)//
                .params("inspectionName", detailBean.inspectionName)//
                .params("description", detailBean.description)//
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        hideLoadView();
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                getDetail();
                                EventBus.getDefault().post("changedGXYJTask");
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


    private void updateZGWT(String Namekey, String Namevalue, String key, String value) {
        HttpRequest.get(getContext())
                .url(ServerInterface.updateZGWT)
                .params("id", id)
                .params(Namekey, Namevalue)
                .params(key, value)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optString("code").equals("0")) {
                                EventBus.getDefault().post("GXYJTaskStateChanged");
                                getDetail();
                            } else {
                                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
                            }
                        } catch (Exception ignored) {
                            ignored.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }
}
