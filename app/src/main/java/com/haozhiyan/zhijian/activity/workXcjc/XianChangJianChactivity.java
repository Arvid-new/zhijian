package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.FragmentAdapter;
import com.haozhiyan.zhijian.bean.XcjcSeverityProblem;
import com.haozhiyan.zhijian.fragment.xcjcFragment.Xcjc_commit_Fragment;
import com.haozhiyan.zhijian.fragment.xcjcFragment.Xcjc_drafts_Fragment;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ACache;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ParameterMap;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//现场检查
public class XianChangJianChactivity extends BaseActivity implements View.OnClickListener {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.iv_right_icon)
    ImageView ivRightIcon;
    @ViewInject(R.id.tabs)
    TabLayout mTabLayout;
    @ViewInject(R.id.vp_view)
    ViewPager mViewPager;
    @ViewInject(R.id.tv_switch_piCi)
    TextView tvSwitchPiCi;
    @ViewInject(R.id.tv_piCiName)
    TextView tv_piCiName;//批次名称
    @ViewInject(R.id.tv_noPiCi)
    TextView tvNoPiCi;
    @ViewInject(R.id.ll_has_piCi)
    LinearLayout llHasPiCi;
    @ViewInject(R.id.btn_add_problem)
    Button btnAddProblem;//添加问题
    @ViewInject(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Xcjc_drafts_Fragment xcjcDraftsFragment;
    private Xcjc_commit_Fragment xcjcCommitFragment;
    private ACache aCache;
    private String batchId = "", sectionId = "", rectifyDate = "7";
    //isNewPiCiOrNoPiCi 0暂无与你相关的批次  1需要新建批次
    //createPiCiTag  0不可以创建批次，只能显示默认批次和切换批次   不为0可以创建批次，亦可以切换批次
    private int fragmentIndex, createPiCiTag = 0, isNewPiCiOrNoPiCi = -1;
    private List<XcjcSeverityProblem> severityProblems;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xian_chang_jian_chactivity;
    }

    @Override
    protected void initView() {
        tv_title.setText("现场检查");
        ivRightIcon.setVisibility(View.VISIBLE);
        ivRightIcon.setImageResource(R.drawable.icon_gantan_img);
        xcjcDraftsFragment = new Xcjc_drafts_Fragment();
        xcjcCommitFragment = new Xcjc_commit_Fragment();
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(xcjcDraftsFragment, "草稿");
        adapter.addFragment(xcjcCommitFragment, "已提交");
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void initListener() {
        rl_back.setOnClickListener(this);
        ivRightIcon.setOnClickListener(this);
        tvSwitchPiCi.setOnClickListener(this);
        btnAddProblem.setOnClickListener(this);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                fragmentIndex = position;
            }

            @Override
            public void onPageSelected(int position) {
                fragmentIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPiCiTrouble();
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        aCache = ACache.get(this, "XCJC_trouble");
        getPiCiTrouble();
    }

    private void getPiCiTrouble() {
        LogUtils.i("value==", ParameterMap.piCiOrTrouble(userInfo.getUserId(), Constant.projectId + "", batchId).toString());
        HttpRequest.get(this).url(ServerInterface.queryPiCiOrTrouble)
                .params(ParameterMap.piCiOrTrouble(userInfo.getUserId(), Constant.projectId + "", batchId))
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("result===", result.toString());
                        try {
                            JSONObject json = new JSONObject(result.toString());
                            if (json.optInt("code") == 0) {
                                JSONObject data = json.optJSONObject("data");
                                if (data != null) {
                                    //设置默认批次的权限操作
                                    JSONObject mapBatch = data.optJSONObject("mapBatch");
                                    //batchTag为0的时候不可以创建批次，只能显示默认批次和切换批次
                                    //batchTag不为0的时候可以创建批次，亦可以切换批次
                                    int batchTag = data.optInt("batchTag");
                                    createPiCiTag = batchTag;
                                    if (batchTag == 0 && mapBatch == null) {
                                        llHasPiCi.setVisibility(View.GONE);
                                        tvNoPiCi.setVisibility(View.VISIBLE);
                                        isNewPiCiOrNoPiCi = 0;
                                    } else if (batchTag == 0 && mapBatch != null) {
                                        llHasPiCi.setVisibility(View.VISIBLE);
                                        tvNoPiCi.setVisibility(View.GONE);
                                        tv_piCiName.setText(mapBatch.optString("batchName"));
                                        tvSwitchPiCi.setVisibility(View.GONE);
                                        tv_piCiName.setOnClickListener(XianChangJianChactivity.this);
                                        isNewPiCiOrNoPiCi = 2;
                                    } else if (batchTag != 0 && mapBatch == null) {
                                        llHasPiCi.setVisibility(View.VISIBLE);
                                        tvNoPiCi.setVisibility(View.GONE);
                                        tv_piCiName.setText("请先新建一个批次");
                                        tvSwitchPiCi.setVisibility(View.GONE);
                                        tv_piCiName.setOnClickListener(XianChangJianChactivity.this);
                                        isNewPiCiOrNoPiCi = 1;
                                    } else if (batchTag != 0 && mapBatch != null) {
                                        llHasPiCi.setVisibility(View.VISIBLE);
                                        tvNoPiCi.setVisibility(View.GONE);
                                        tv_piCiName.setText(mapBatch.optString("batchName"));
                                        tvSwitchPiCi.setVisibility(View.GONE);
                                        tv_piCiName.setOnClickListener(XianChangJianChactivity.this);
                                        isNewPiCiOrNoPiCi = 2;
                                    }
                                    if (mapBatch != null) {
                                        batchId = mapBatch.optString("batchId");
                                        rectifyDate = mapBatch.optString("rectifyDate");
                                        sectionId = mapBatch.optString("sectionId");
                                    }
                                    JSONArray problemList = data.optJSONArray("problemList");
                                    if (arrayEmpty(problemList)) {
                                        xcjcCommitFragment.setJCData(problemList);
                                    } else {
                                        xcjcCommitFragment.setEmpty();
                                    }
                                    JSONArray severityProblem = data.optJSONArray("severityProblem");
                                    if (arrayEmpty(severityProblem)) {
                                        severityProblems = new ArrayList<>();
                                        for (int i = 0; i < severityProblem.length(); i++) {
                                            severityProblems.add(new XcjcSeverityProblem(severityProblem.optJSONObject(i).optInt("severityId"),
                                                    severityProblem.optJSONObject(i).optString("severityName")));
                                        }
                                    } else {
                                        severityProblems = null;
                                    }
                                } else {
                                    xcjcCommitFragment.setEmpty();
                                    ToastUtils.myToast(act, "没有数据");
                                }
                            } else {
                                xcjcCommitFragment.setEmpty();
                            }
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        swipeRefreshLayout.setRefreshing(false);
                        //ToastUtils.myToast(act, msg);
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (resultCode == Constant.XCJC_PICI_RESULT_CODE) {
                if (StringUtils.isEmpty(UiUtils.getContent(tv_piCiName))) {
                    isNewPiCiOrNoPiCi = 1;
                } else {
                    isNewPiCiOrNoPiCi = 2;
                    tv_piCiName.setText(data.getStringExtra("name"));
                    batchId = data.getStringExtra("batchId");
                    sectionId = data.getStringExtra("sectionId");
                    LogUtils.i("sectionId232===", sectionId);
                    getPiCiTrouble();
                }
            } else if (resultCode == Constant.NOTE_PROBLEM_RESULT_CODE) {
//                xcjcDraftsFragment.setDraftList();
            } else if (resultCode == Constant.ADD_PROBLEM_RESULT_CODE) {
                if (mTabLayout.getTabCount() > 1) {
                    mTabLayout.getTabAt(1).select();
                }
                getPiCiTrouble();
            } else if (resultCode == Constant.PROBLEM_DETAIL_RESULT_CODE) {
                if (mTabLayout.getTabCount() > 1) {
                    mTabLayout.getTabAt(0).select();
                }
                getPiCiTrouble();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_switch_piCi://切换批次
                startActivityForResult(new Intent(this, JianChaPiCiActivity.class), Constant.XCJC_PICI_RESULT_CODE);
                break;
            case R.id.btn_add_problem://登记问题
                if (isNewPiCiOrNoPiCi == 0) {
                    ToastUtils.myToast(act, "请先选择检查批次");
                } else if (isNewPiCiOrNoPiCi == 1) {
                    ToastUtils.myToast(act, "请先添加检查批次");
                } else {
                    Intent intent = new Intent(this, AddTroubleActivity.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra("sectionId", sectionId);
                    intent.putExtra("pieceType", "xcJc");
                    intent.putExtra("rectifyDate", rectifyDate);
                    intent.putExtra("severityProblems", (Serializable) severityProblems);
                    if (fragmentIndex == 0) {
                        startActivityForResult(intent, Constant.NOTE_PROBLEM_RESULT_CODE);
                    } else if (fragmentIndex == 1) {
                        startActivityForResult(intent, Constant.ADD_PROBLEM_RESULT_CODE);
                    }
                }
                break;
            case R.id.iv_right_icon:
                jumpToActivity(JianChaGuideActivity.class);
                break;
            case R.id.tv_piCiName:
                Bundle bundle = new Bundle();
                bundle.putInt("createPiCiTag", createPiCiTag);
                bundle.putString("batchId", batchId);
                jumpActivityForResult(JianChaPiCiActivity.class, Constant.XCJC_PICI_RESULT_CODE, bundle);
                //startActivityForResult(new Intent(this, JianChaPiCiActivity.class), Constant.XCJC_PICI_RESULT_CODE);
                break;
            default:
                break;
        }
    }
}
