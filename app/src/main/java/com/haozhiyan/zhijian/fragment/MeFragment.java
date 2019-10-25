package com.haozhiyan.zhijian.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.MePackage.AboutActivity;
import com.haozhiyan.zhijian.activity.MePackage.FeedBackProblem;
import com.haozhiyan.zhijian.activity.MePackage.HelpAct;
import com.haozhiyan.zhijian.activity.MePackage.PersonalInfoActivity;
import com.haozhiyan.zhijian.activity.SettingActivity;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 我的
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout ll_left;
    private LinearLayout ll_right;
    private LinearLayout ll_set;
    private LinearLayout ll_online_customer;
    private LinearLayout ll_return_problem;
    private LinearLayout ll_WX_receive;
    private LinearLayout ll_inspect_renewal;
    private LinearLayout ll_about;
    private LinearLayout ll_help;
    private SimpleDraweeView headView;
    private TextView tvUserName;
    private TextView tvDanWei;
    private SwipeRefreshLayout swipeRefreshLayout;
    private UserInfo userInfo;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView(View view) {
        ll_left = getOutView(view, R.id.ll_left);
        ll_right = getOutView(view, R.id.ll_right);
        ll_set = getOutView(view, R.id.ll_set);
        ll_online_customer = getOutView(view, R.id.ll_online_customer);
        ll_return_problem = getOutView(view, R.id.ll_return_problem);
        ll_WX_receive = getOutView(view, R.id.ll_WX_receive);
        ll_inspect_renewal = getOutView(view, R.id.ll_inspect_renewal);
        ll_about = getOutView(view, R.id.ll_about);
        ll_help = getOutView(view, R.id.ll_help);
        headView = getOutView(view, R.id.headView);
        tvUserName = getOutView(view, R.id.tv_UserName);
        tvDanWei = getOutView(view, R.id.tv_danWei);
        swipeRefreshLayout = getOutView(view, R.id.swipeRefreshLayout);
        userInfo = UserInfo.create(getContext());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void initListener() {
        ll_left.setOnClickListener(this);
        ll_right.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        ll_online_customer.setOnClickListener(this);
        ll_return_problem.setOnClickListener(this);
        ll_WX_receive.setOnClickListener(this);
        ll_inspect_renewal.setOnClickListener(this);
        ll_about.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUserInfo();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void initData(boolean isNetWork) {
        tvUserName.setText(userInfo.getUserName());
        tvDanWei.setText(UiUtils.getDanWeiName(userInfo.getUserType()));
        headView.setImageURI(userInfo.getUserHeader());

        //headView.setImageURI(DataTest.imgUrl);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_left:
                jumpToActivity(PersonalInfoActivity.class);
                break;
            case R.id.ll_right:
                jumpToActivity(PersonalInfoActivity.class);
                break;
            case R.id.ll_set:
                jumpToActivity(SettingActivity.class);
                break;
            case R.id.ll_online_customer:

                break;
            case R.id.ll_return_problem:
                jumpToActivity(FeedBackProblem.class);
                break;
            case R.id.ll_WX_receive:

                break;
            case R.id.ll_inspect_renewal://检查更新
                ToastUtils.myToast(ctx, "当前已是最新版本！");
                break;
            case R.id.ll_about://关于
                jumpToActivity(AboutActivity.class);
                //jumpToActivity(ReportFormsIndexActivity.class);
                break;
            case R.id.ll_help:
                jumpToActivity(HelpAct.class);
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Object event) {
        if (event != null) {
            try {
                ItemValues item = (ItemValues) event;
                if (item.isUpdate) {
                    headView.setImageURI(userInfo.getUserHeader());
                    tvUserName.setText(userInfo.getUserName());
                    tvDanWei.setText(UiUtils.getDanWeiName(userInfo.getUserType()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void getUserInfo() {
        long value;
        if (StringUtils.isEmpty(userInfo.getUserId())) {
            value = 10000;
        } else {
            value = Long.valueOf(userInfo.getUserId());
        }
        HttpRequest.get(ctx).url(ServerInterface.userInfo)
                .params("userId", value)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("print=userInfo===", result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                JSONObject user = object.optJSONObject("list");
                                if (user != null) {
                                    headView.setImageURI(user.optString("headPortrait"));
                                    tvUserName.setText(user.optString("peopleuser"));
                                    tvDanWei.setText(UiUtils.getDanWeiName(user.optString("userAppTag")));
                                    userInfo.putUserHeader(user.optString("headPortrait"));
                                    userInfo.putMobile(user.optString("mobile"));
                                    userInfo.putUserName(user.optString("peopleuser"));
                                    userInfo.putUserId(user.optString("userId"));
                                    userInfo.putUserType(user.optString("userAppTag"));
                                    userInfo.putCompanyCode(user.optString("tenantCode"));
                                }
                                userInfo.putUserAppTagSg(object.optString("userAppTagSg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(ctx, msg);
                    }
                });
    }
}
