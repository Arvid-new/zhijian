package com.haozhiyan.zhijian.activity.clys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.adapter.WorkClysAdapter;
import com.haozhiyan.zhijian.bean.CaiLiaoIndexListBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ToastUtils;

import org.json.JSONObject;

public class WorkClys extends BaseActivity2 {

    private RecyclerView work_clys_rcv;

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_work_clys;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("材料验收");
        setAndroidNativeLightStatusBar(true);
        setTitleMenu();
        work_clys_rcv = findViewById(R.id.work_clys_rcv);
        work_clys_rcv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void initData() {
        clysList();
    }

    @Override
    public void onMessageEvent(Object event) {
        if (event instanceof String) {
            String s = event.toString();
            if (s.equals("addTaskSucess") || s.equals("taskDelete") || s.equals("taskStateChanged")) {
                clysList();
            }
        }
    }

    /**
     * 标题栏
     */
    private void setTitleMenu() {
        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp45px = DensityUtil.dip2px(getContext(), 45);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, dp45px);
        TextView tv = new TextView(getContext());
        tv.setLayoutParams(layoutParams);
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(dp10px, 0, dp10px, 0);
        tv.setText("新建");
        tv.setTextColor(0xff232323);
        tv.setSingleLine(true);
        tv.setTextSize(15);
        getBarRightView().addView(tv);
        getBarRightView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddClysTask.class);
            }
        });
    }


    /**
     * 获取列表
     */
    private void clysList() {
        HttpRequest.get(getContext())
                .url(ServerInterface.clysList)
                .params("userId", UserInfo.create(getContext()).getUserId())
                .params("projectId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                final CaiLiaoIndexListBean bean = new Gson().fromJson(result.toString(), CaiLiaoIndexListBean.class);
                                WorkClysAdapter workClysAdapter = new WorkClysAdapter(bean.getClysList());
                                work_clys_rcv.setAdapter(workClysAdapter);
                                workClysAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                        Intent intent = new Intent();
                                        intent.setClass(getContext(), AcceptanceMaterials.class);
                                        intent.putExtra("id", bean.getClysList().get(position).getId() + "");
                                        intent.putExtra("state", bean.getClysList().get(position).getState() + "");
                                        intent.putExtra("title", bean.getClysList().get(position).getSectionName()
                                                + bean.getClysList().get(position).getNameInspection()
                                                + "进场"
                                                + bean.getClysList().get(position).getNumber());
                                        startActivity(intent);
                                    }
                                });
                            } else {
                                ToastUtils.myToast(getActivity(), object.optString("msg"));
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
