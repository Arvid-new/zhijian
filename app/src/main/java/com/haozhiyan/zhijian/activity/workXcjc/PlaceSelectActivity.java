package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.PlaceAdapter;
import com.haozhiyan.zhijian.bean.PlaceNewBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.view.MyRecycleView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class PlaceSelectActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.rv_list)
    MyRecycleView rvList;
    @ViewInject(R.id.btn_sure)
    Button btnSure;
    private PlaceAdapter placeAdapter;
    private String towerName = "", unitName = "", floorName = "", roomName = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_place_select;
    }

    @Override
    protected void initView() {
        tv_title.setText("选择检查部位");
        try {
            Bundle bundle = getIntent().getBundleExtra("data");
            towerName = bundle.getString("towerName");
            unitName = bundle.getString("unitName");
            floorName = bundle.getString("floorName");
            roomName = bundle.getString("roomName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        getPlace();
    }

    @OnClick({R.id.rl_back, R.id.btn_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                //jumpToActivity(AddTroubleActivity.class);
                break;
            case R.id.btn_sure:
                if (placeAdapter != null) {
                    if (StringUtils.isEmpty(placeAdapter.getSelectPlace())) {
                        ToastUtils.myToast(act, "请选择检查部位");
                    } else {
                        setResult();
                    }
                }
                break;
            default:
                break;
        }
    }

    private void setResult() {
        Intent intent = new Intent(this,AddTroubleActivity.class);
        //intent.putExtra("pieceType", "xcJc");
        intent.putExtra("local", placeAdapter.getSelectPlace());
        intent.putExtra("tower", placeAdapter.house().length > 0 ? placeAdapter.house()[0] : "");
        intent.putExtra("unit", placeAdapter.house().length > 1 ? placeAdapter.house()[1] : "");
        intent.putExtra("floor", placeAdapter.house().length > 2 ? placeAdapter.house()[2] : "");
        intent.putExtra("room", placeAdapter.house().length > 3 ? placeAdapter.house()[3] : "");
        intent.putExtra("towerName", placeAdapter.getSelectPlaceArray()[0]);
        intent.putExtra("unitName", placeAdapter.getSelectPlaceArray()[1]);
        intent.putExtra("floorName", placeAdapter.getSelectPlaceArray()[2]);
        intent.putExtra("roomName", placeAdapter.getSelectPlaceArray()[3]);
         //startActivity(intent);
        setResult(Constant.REQUEST_CODE, intent);
        finish();
    }

    private void getPlace() {
        HttpRequest.get(this).url(ServerInterface.projectManager).params("pkId", Constant.projectId).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                PlaceNewBean placeNewBean = new Gson().fromJson(result.toString(), PlaceNewBean.class);
                if (placeNewBean != null) {
                    if (listEmpty(placeNewBean.list)) {
                        placeAdapter = new PlaceAdapter(act, placeNewBean.list);
                        rvList.setAdapter(placeAdapter);
                        try {
                            placeAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        placeAdapter.setItemRoomClickListener(new PlaceAdapter.OnItemRoomClickListener() {
                            @Override
                            public void roomBack() {
                                setResult();
                            }
                        });
                    } else {
                        ToastUtils.myToast(act, "暂无相关部位信息");
                    }
                } else {
                    ToastUtils.myToast(act, "服务器错误");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(act, msg);
            }
        });
    }
}
