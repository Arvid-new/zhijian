package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.PlaceFloorAdapter;
import com.haozhiyan.zhijian.adapter.PlaceRoomAdapter;
import com.haozhiyan.zhijian.adapter.PlaceTowerAdapter;
import com.haozhiyan.zhijian.adapter.PlaceUnitAdapter;
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

import java.util.List;

public class NewPlaceSelect extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.rv_tower)
    MyRecycleView rvTower;
    @ViewInject(R.id.rv_unit)
    MyRecycleView rvUnit;
    @ViewInject(R.id.rv_floor)
    MyRecycleView rvFloor;
    @ViewInject(R.id.rv_room)
    MyRecycleView rvRoom;
    @ViewInject(R.id.btn_sure)
    Button btnSure;
    @ViewInject(R.id.main_empty)
    RelativeLayout empty;
    @ViewInject(R.id.ll_list)
    LinearLayout llList;
    @ViewInject(R.id.tv_remind)
    TextView tvRemind;
    private String tower = "", unit = "", floor = "", room = "", selectContent = "";
    private String towerId = "", unitId = "", floorId = "", roomId = "";
    private PlaceTowerAdapter placeTowerAdapter;
    private PlaceUnitAdapter placeUnitAdapter;
    private PlaceFloorAdapter placeFloorAdapter;
    private PlaceRoomAdapter placeRoomAdapter;
    private List<PlaceNewBean.ListBean> towerList;
    private List<PlaceNewBean.ListBean.UnitChildBean> unitList;
    private List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean> floorList;
    private List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean.RoomNumChildBean> roomList;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_new_place_select;
    }

    @Override
    protected void initView() {
        tv_title.setText("选择检查部位");
        try {
            Bundle bundle = getIntent().getBundleExtra("data");
            tower = bundle.getString("towerName");
            unit = bundle.getString("unitName");
            floor = bundle.getString("floorName");
            room = bundle.getString("roomName");
        } catch (Exception e) {
            e.printStackTrace();
        }
        initRecycle(rvTower);
        initRecycle(rvUnit);
        initRecycle(rvFloor);
        initRecycle(rvRoom);
        initAdapter();
    }

    @Override
    protected void initListener() {
        placeTowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                placeTowerAdapter.setSelectStatus(position);
                if (listEmpty(towerList)) {
                    tower = towerList.get(position).tower;
                    towerId = towerList.get(position).towerId + "";
                    unitList = towerList.get(position).unitChild;
                    if (listEmpty(unitList)) {
                        placeUnitAdapter.setNewData(unitList);
                        placeFloorAdapter.setNewData(null);
                        placeRoomAdapter.setNewData(null);
                    } else {
                        placeUnitAdapter.setNewData(null);
                        placeFloorAdapter.setNewData(null);
                        placeRoomAdapter.setNewData(null);
                    }
                }
            }
        });
        placeUnitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                placeUnitAdapter.setSelectStatus(position);
                if (listEmpty(unitList)) {
                    unit = unitList.get(position).unit;
                    unitId = unitList.get(position).unitId + "";
                    floorList = unitList.get(position).floorChild;
                    if (listEmpty(floorList)) {
                        placeFloorAdapter.setNewData(floorList);
                        placeRoomAdapter.setNewData(null);
                    } else {
                        placeFloorAdapter.setNewData(null);
                        placeRoomAdapter.setNewData(null);
                    }
                }
            }
        });
        placeFloorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                placeFloorAdapter.setSelectStatus(position);
                if (listEmpty(floorList)) {
                    floor = floorList.get(position).floor;
                    floorId = floorList.get(position).floorId + "";
                    roomList = floorList.get(position).roomNumChild;
                    if (listEmpty(roomList)) {
                        placeRoomAdapter.setNewData(roomList);
                    } else {
                        placeRoomAdapter.setNewData(null);
                    }
                }
            }
        });
        placeRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                placeRoomAdapter.setSelectStatus(position);
                if (listEmpty(roomList)) {
                    room = roomList.get(position).roomNum;
                    roomId = roomList.get(position).roomId + "";
                    //ToastUtils.myToast(act, "选择->>\n" + tower + unit + floor + room + "\n" + towerId + unitId + floorId + roomId);
                    if (StringUtils.isEmpty(roomList.get(position).roomHouse)) {
                        ToastUtils.myToast(act, "当前房间户型图错误,请选择其它房间");
                    } else {
                        if (roomList.get(position).roomHouse.equals("0")) {
                            setResult();
                        } else {
                            Intent intent = new Intent(act, XCJCImagesActivity.class);
                            intent.putExtra("towerId", towerId);
                            intent.putExtra("unitId", unitId);
                            intent.putExtra("floorId", floorId);
                            intent.putExtra("roomId", roomList.get(position).roomId + "");
                            intent.putExtra("towerFloorUnitRoom", tower + unit + floor + room);
                            intent.putExtra("towerName", tower);
                            intent.putExtra("unitName", unit);
                            intent.putExtra("floorName", floor);
                            intent.putExtra("roomName", room);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        tvRemind.setText("当前地块下没有楼栋信息");
        getPlace();
    }

    private void initRecycle(RecyclerView rlv) {
        rlv.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
    }

    private void initAdapter() {
        placeTowerAdapter = new PlaceTowerAdapter(null, tower);
        rvTower.setAdapter(placeTowerAdapter);
        placeUnitAdapter = new PlaceUnitAdapter(null, unit);
        rvUnit.setAdapter(placeUnitAdapter);
        placeFloorAdapter = new PlaceFloorAdapter(null, floor);
        rvFloor.setAdapter(placeFloorAdapter);
        placeRoomAdapter = new PlaceRoomAdapter(null, room);
        rvRoom.setAdapter(placeRoomAdapter);
    }

    @OnClick({R.id.rl_back, R.id.btn_sure})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.btn_sure:
                selectContent = tower + unit + floor + room;
                if (StringUtils.isEmpty(selectContent)) {
                    ToastUtils.myToast(act, "请选择检查部位");
                } else {
                    setResult();
                }
                break;
            default:
                break;
        }
    }

    private void getPlace() {
        showLoadView("...");
        HttpRequest.get(this).url(ServerInterface.projectManager).params("pkId", Constant.projectId).doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                hideLoadView();
                PlaceNewBean placeNewBean = new Gson().fromJson(result.toString(), PlaceNewBean.class);
                if (placeNewBean.code == 0) {//400
                    if (placeNewBean != null) {
                        if (listEmpty(placeNewBean.list)) {
                            empty.setVisibility(View.GONE);
                            llList.setVisibility(View.VISIBLE);
                            towerList = placeNewBean.list;
                            placeTowerAdapter.setNewData(placeNewBean.list);
                            if (listEmpty(towerList)) {
                                for (int i = 0; i < towerList.size(); i++) {
                                    if (listEmpty(towerList.get(i).unitChild)) {
                                        for (int j = 0; j < towerList.get(i).unitChild.size(); j++) {
                                            if (TextUtils.equals(unit, towerList.get(i).unitChild.get(j).unit)) {
                                                placeUnitAdapter.setNewData(towerList.get(i).unitChild);
                                                //break;
                                            }
                                            if (listEmpty(towerList.get(i).unitChild.get(j).floorChild)) {
                                                floorList = towerList.get(i).unitChild.get(j).floorChild;
                                                for (int k = 0; k < floorList.size(); k++) {
                                                    if (TextUtils.equals(floor, floorList.get(k).floor)) {
                                                        placeFloorAdapter.setNewData(floorList);
                                                        //break;
                                                    }
                                                    if (listEmpty(towerList.get(i).unitChild.get(j).floorChild.get(k).roomNumChild)) {
                                                        roomList = towerList.get(i).unitChild.get(j).floorChild.get(k).roomNumChild;
                                                        for (int l = 0; l < roomList.size(); l++) {
                                                            if (TextUtils.equals(room, roomList.get(l).roomNum)) {
                                                                placeRoomAdapter.setNewData(roomList);
                                                                //break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            empty.setVisibility(View.VISIBLE);
                            llList.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtils.myToast(act, "服务器错误");
                    }
                } else if (placeNewBean.code == 400) {
                    empty.setVisibility(View.VISIBLE);
                    llList.setVisibility(View.GONE);
                } else {
                    ToastUtils.myToast(act, "服务器错误");
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                hideLoadView();
                ToastUtils.myToast(act, msg);
            }
        });
    }

    private void setResult() {
        Intent intent = new Intent(this, AddTroubleActivity.class);
        //intent.putExtra("pieceType", "xcJc");
        intent.putExtra("local", tower + unit + floor + room);
        intent.putExtra("tower", towerId);
        intent.putExtra("unit", unitId);
        intent.putExtra("floor", floorId);
        intent.putExtra("room", roomId);
        intent.putExtra("towerName", tower);
        intent.putExtra("unitName", unit);
        intent.putExtra("floorName", floor);
        intent.putExtra("roomName", room);
        setResult(Constant.REQUEST_CODE, intent);
        finish();
    }
}
