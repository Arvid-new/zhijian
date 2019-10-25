package com.haozhiyan.zhijian.activity.gxyj;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.GxyjPlaceSelectAdapter;
import com.haozhiyan.zhijian.adapter.GxyjUnitAdapter;
import com.haozhiyan.zhijian.bean.GxyjFHBean;
import com.haozhiyan.zhijian.bean.GxyjPlaceBean;
import com.haozhiyan.zhijian.bean.GxyjPlaceItem01;
import com.haozhiyan.zhijian.bean.GxyjPlaceItem02;
import com.haozhiyan.zhijian.bean.GxyjUnitBean;
import com.haozhiyan.zhijian.bean.ItemValues;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//工序移交楼栋部位选择
public class GxYjPlaceSelect extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rlBack;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.tv_right)
    TextView tvRight;
    @ViewInject(R.id.tv_tower)
    TextView tvTower;
    @ViewInject(R.id.tv_unit)
    TextView tvUnit;
    @ViewInject(R.id.tfr_list)
    RecyclerView tfrList;//楼层房间View
    @ViewInject(R.id.rv_unit)
    RecyclerView rvUnit;//单元View
    private String towerId = "", floorName = "", floorId = "", towerType = "", unitId = "", roomId = "",
            towerName = "", unitName = "", roomName = "", keyId = "";
    private ArrayList<MultiItemEntity> list;
    private GxyjPlaceSelectAdapter myAdapter;
    private GxyjUnitAdapter gxyjUnitAdapter;
    private List<GxyjUnitBean.ListBean> listBeans;
    private boolean isSelectUnit = false;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_gxyj_place_select;
    }

    @Override
    protected void initView() {
        tvTitle.setText("选择部位");
        tvRight.setText("确定");
        Bundle bundle = getIntent().getBundleExtra("data");
        if (bundle != null) {
            towerId = bundle.getString("towerId");
            unitId = bundle.getString("unitId");
            floorId = bundle.getString("floorId");
            roomId = bundle.getString("roomId");
            towerType = bundle.getString("towerType");
            towerName = bundle.getString("towerName");
            unitName = bundle.getString("unitName");
            roomName = bundle.getString("roomName");
            keyId = bundle.getString("keyId");
            if(StringUtils.isEmpty(towerName)||StringUtils.isEmpty(unitName)){
                tvTower.setVisibility(View.GONE);
                tvUnit.setVisibility(View.GONE);
                tvRight.setVisibility(View.GONE);
            }else{
                tvTower.setText(towerName);
                tvUnit.setText(unitName);
                tvRight.setVisibility(View.VISIBLE);
                if (TextUtils.equals("整栋", towerType)) {
                    rvUnit.setVisibility(View.VISIBLE);
                    tvUnit.setVisibility(View.GONE);
                } else {
                    rvUnit.setVisibility(View.GONE);
                    tvUnit.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    protected void initListener() {
        gxyjUnitAdapter = new GxyjUnitAdapter(null, unitName);
        rvUnit.setLayoutManager(new GridLayoutManager(this, 4));
        rvUnit.setAdapter(gxyjUnitAdapter);
        myAdapter = new GxyjPlaceSelectAdapter(null, roomName);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return myAdapter.getItemViewType(position) == GxyjPlaceSelectAdapter.TYPE_LEVEL_1 ? 1 : manager.getSpanCount();
            }
        });
        tfrList.setAdapter(myAdapter);
        tfrList.setLayoutManager(manager);
        gxyjUnitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gxyjUnitAdapter.setSelectStatus(position);
                if (listEmpty(listBeans)) {
                    unitId = listBeans.get(position).unitId + "";
                    myAdapter.setNewData(generateDataUnit(listBeans.get(position).floorChild));
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
        myAdapter.setOnItemSonClickListener(new GxyjPlaceSelectAdapter.OnItemSonClickListener() {
            @Override
            public void room(String roomNum, String roomId) {
                Intent intent = new Intent(act, GxYjH5.class);
                intent.putExtra("roomId", roomId);
                intent.putExtra("roomNum", roomNum);
                intent.putExtra("towerName", towerName);
                intent.putExtra("unitName", unitName);
                intent.putExtra("towerId", towerId);
                intent.putExtra("unitId", unitId);
                intent.putExtra("floorId", floorId);
                intent.putExtra("keyId", keyId);
                startActivity(intent);
                finish();
            }

            @Override
            public void floor(String floor, String floorIds) {
                floorName = floor;
                floorId = floorIds;
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        LogUtils.i("towerType-->", towerType+"");
        LogUtils.i("towerId-->", towerId+"");
        LogUtils.i("unitId-->", unitId+"");
        LogUtils.i("floorId-->", floorId+"");
        LogUtils.i("roomId-->", roomId+"");
        switch (towerType) {
            case "分户":
                getUnitRoom();
                break;
            case "不分单元-整层":
                getFloor("不分单元整层");
                break;
            case "分单元-整层":
                getFloor("分单元整层");
                break;
            case "整栋":
                getTower();
                break;
            default:
                getUnitRoom();
                break;
        }
        //getData();
    }

    @OnClick({R.id.rl_back, R.id.tv_tower, R.id.tv_unit, R.id.tv_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_tower:
                if (!TextUtils.equals("整栋", towerType)) {
                    if (isSelectUnit) {
                        tvUnit.setBackgroundResource(R.drawable.gray_border_shape);
                        tvUnit.setTextColor(setColor(R.color.black_3));
                        tfrList.setVisibility(View.GONE);
                        isSelectUnit = false;
                    }
                }
                break;
            case R.id.tv_unit:
                if (!TextUtils.equals("整栋", towerType)) {
                    isSelectUnit = true;
                    tvUnit.setBackgroundResource(R.drawable.blue_5radius_back);
                    tvUnit.setTextColor(setColor(R.color.white));
                    tfrList.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_right:
                ItemValues itemValues = new ItemValues();
                itemValues.houseMap = null;
                itemValues.pieceType = "gxYj";
                itemValues.towerFloorUnitRoom = towerName + unitName + floorName+"层" + roomName;
                itemValues.towerId = towerId;
                itemValues.floorId = floorId;
                itemValues.unitId = unitId;
                itemValues.roomId = roomId;
                itemValues.roomNum = roomName;
                EventBus.getDefault().post(itemValues);
                ActivityManager.getInstance().removeActivity(act);
                break;
            default:
                break;
        }
    }

    //整栋
    private void getTower() {
        HttpRequest.get(this).url(ServerInterface.selectGXYJPart)
                .params("towerId", towerId)
                .params("towerType", "整栋")//楼栋标识
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.print("json整栋====" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                if (arrayEmpty(object.optJSONArray("list"))) {
                                    rvUnit.setVisibility(View.VISIBLE);
                                    GxyjUnitBean bean = new Gson().fromJson(result.toString(), GxyjUnitBean.class);
                                    gxyjUnitAdapter.setNewData(bean.list);
                                    gxyjUnitAdapter.setSelectStatus(0);
                                    gxyjUnitAdapter.notifyDataSetChanged();
                                    listBeans = bean.list;
                                    if (listEmpty(bean.list.get(0).floorChild)) {
                                        list = generateDataUnit(bean.list.get(0).floorChild);
                                        myAdapter.setNewData(list);
                                    } else {
                                        myAdapter.setEmptyView(emptyView);
                                    }
                                } else {
                                    myAdapter.setEmptyView(emptyView);
                                    rvUnit.setVisibility(View.GONE);
                                }
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

    //分户
    private void getUnitRoom() {
        HttpRequest.get(this).url(ServerInterface.selectGXYJPart)
                .params("unitId", unitId)
                .params("roomId", roomId)
                .params("towerType", "分户")
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.print("json分户====" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                if (arrayEmpty(object.optJSONArray("list"))) {
                                    GxyjFHBean bean = new Gson().fromJson(result.toString(), GxyjFHBean.class);
                                    if (listEmpty(bean.list)) {
                                        list = generateFenHuData(bean.list);
                                        myAdapter.setNewData(list);
                                        myAdapter.notifyDataSetChanged();
                                    } else {
                                        myAdapter.setEmptyView(emptyView);
                                    }
                                } else {
                                    myAdapter.setEmptyView(emptyView);
                                }
                            }else {
                                myAdapter.setEmptyView(emptyView);
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

    //分单元/不分单元整层
    private void getFloor(final String towerType) {
        HttpRequest.get(this).url(ServerInterface.selectGXYJPart)
                .params("floorId", floorId)
                .params("towerType", towerType)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.print("json" + towerType + "===" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object.optInt("code") == 0) {
                                if (arrayEmpty(object.optJSONArray("list"))) {
                                    GxyjFHBean bean = new Gson().fromJson(result.toString(), GxyjFHBean.class);
                                    if (listEmpty(bean.list)) {
                                        list = generateFenHuData(bean.list);
                                        myAdapter.setNewData(list);
                                        myAdapter.notifyDataSetChanged();
                                    } else {
                                        myAdapter.setEmptyView(emptyView);
                                    }
                                } else {
                                    myAdapter.setEmptyView(emptyView);
                                }
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
//
//    private void getData() {
//        OkGoRequest.get(this).url(ServerInterface.selectGXYJPart)
//                .params("towerId", towerId)
//                .params("floorId", floorId)
//                .params("towerType", towerType)//楼栋标识 分层
//                .params("unitId", unitId)
//                .params("roomId", roomId)
//                .doGetRequest(new MyObjectCallBack<GxyjPlaceBean>(this) {
//                    @Override
//                    public void onSuccess(Response<GxyjPlaceBean> response) {
//                        LogUtils.print("jsonData====" + response.body());
//                        if (response.body().code == 0) {
//                            if (listEmpty(response.body().list)) {
//                                list = generateData(response.body().list);
//                                myAdapter.setNewData(list);
//                            } else {
//                                myAdapter.setEmptyView(emptyView);
//                            }
//                        } else {
//                            myAdapter.setEmptyView(emptyView);
//                        }
//                    }
//                });
//    }

    private ArrayList<MultiItemEntity> generateData(List<GxyjPlaceBean.ListBean> list) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GxyjPlaceItem01 item01 = new GxyjPlaceItem01(list.get(i).floorId, list.get(i).floor);
            List<GxyjPlaceBean.ListBean.RoomDetailedBean> roomDetailedBeans = list.get(i).roomDetailed;
            if (listEmpty(roomDetailedBeans)) {
                GxyjPlaceItem02 item02;
                for (int j = 0; j < roomDetailedBeans.size(); j++) {
                    item02 = new GxyjPlaceItem02(roomDetailedBeans.get(j).unit, roomDetailedBeans.get(j).floor, roomDetailedBeans.get(j).floorId, roomDetailedBeans.get(j).roomNum,
                            roomDetailedBeans.get(j).roomNumNo, roomDetailedBeans.get(j).roomRule, roomDetailedBeans.get(j).roomId);
                    item01.addSubItem(item02);
                }
                res.add(item01);
            }
        }
        return res;
    }

    private ArrayList<MultiItemEntity> generateFenHuData(List<GxyjFHBean.ListBean> list) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GxyjPlaceItem01 item01 = new GxyjPlaceItem01(list.get(i).floorId, list.get(i).floor);
            List<GxyjFHBean.ListBean.RoomDetailedBean> roomDetailedBeans = list.get(i).roomDetailed;
            if (listEmpty(roomDetailedBeans)) {
                GxyjPlaceItem02 item02;
                for (int j = 0; j < roomDetailedBeans.size(); j++) {
                    item02 = new GxyjPlaceItem02(roomDetailedBeans.get(j).unit, roomDetailedBeans.get(j).floor, roomDetailedBeans.get(j).floorId, roomDetailedBeans.get(j).roomNum,
                            roomDetailedBeans.get(j).roomNumNo, roomDetailedBeans.get(j).roomRule, roomDetailedBeans.get(j).roomId);
                    item01.addSubItem(item02);
                }
                res.add(item01);
            }
        }
        return res;
    }

    private ArrayList<MultiItemEntity> generateDataUnit(List<GxyjUnitBean.ListBean.FloorChildBean> list) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            GxyjPlaceItem01 item01 = new GxyjPlaceItem01(list.get(i).floorId + "", list.get(i).floor);
            List<GxyjUnitBean.ListBean.FloorChildBean.RoomNumChildBean> roomDetailedBeans = list.get(i).roomNumChild;
            if (listEmpty(roomDetailedBeans)) {
                GxyjPlaceItem02 item02;
                for (int j = 0; j < roomDetailedBeans.size(); j++) {
                    item02 = new GxyjPlaceItem02("", "", roomDetailedBeans.get(j).floorId, roomDetailedBeans.get(j).roomNum,
                            "", roomDetailedBeans.get(j).roomHouse, roomDetailedBeans.get(j).roomId);
                    item01.addSubItem(item02);
                }
                res.add(item01);
            }
        }
        return res;
    }
}
