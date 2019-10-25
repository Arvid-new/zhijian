package com.haozhiyan.zhijian.activity.MePackage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.HelpAdapter;
import com.haozhiyan.zhijian.bean.HelpBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

//帮助
public class HelpAct extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.helpList)
    RecyclerView helpList;
    private HelpAdapter helpAdapter;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_help;
    }

    @Override
    protected void initView() {
        tvTitle.setText("帮助");
        helpList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        getHelp();
    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            default:
                break;
        }
    }

    //private List<HelpBean.ListBean.QBWTBean.SunBean> sunBeans;
    private void getHelp() {
        HttpRequest.get(this).url(ServerInterface.listHelp)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            HelpBean helpBean = new Gson().fromJson(result.toString(), HelpBean.class);
                            if (helpBean.code == 0) {
                                helpAdapter = new HelpAdapter(act, helpBean.list);
                                helpList.setAdapter(helpAdapter);
                                helpAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.myToast(act, helpBean.msg);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {

                    }
                });
    }
}
