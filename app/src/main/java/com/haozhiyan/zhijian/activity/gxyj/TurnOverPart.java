package com.haozhiyan.zhijian.activity.gxyj;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.adapter.GXYJBuildingsAdapter;
import com.haozhiyan.zhijian.adapter.GXYJTurnOverPartBuildingsAdapter;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJFloorBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJRoomBean;
import com.haozhiyan.zhijian.bean.gxyjbeans.GXYJTurnOverPartAllFloorBean;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.ToastUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 移交部位
 */
public class TurnOverPart extends BaseActivity2 {
    private RecyclerView buildRcv;

    private String sectionId;
    private String towerId;
    private String unitId;
    private String partsDivision = "";
    private String[] selectroom;
    private GXYJTurnOverPartBuildingsAdapter gxyjBuildingsAdapter;
    private List<MultiItemEntity> checkedItems = new ArrayList<>();

    @Override
    protected void init(Bundle savedInstanceState) {
        try {
            partsDivision = getIntent().getExtras().getString("partsDivision", "");
            String selectr = getIntent().getExtras().getString("selectroom", "");
            if (selectr.contains(",")) {
                selectroom = selectr.split(",");
            } else {
                selectroom = new String[]{selectr};
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_turn_over_part;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("移交部位");
        setAndroidNativeLightStatusBar(true);
        setTitleRightmenu();

        buildRcv = findViewById(R.id.buildRcv);

        getBuildList();
    }

    @Override
    protected void initData() {

    }

    /**
     * 右上角确定按钮
     */
    private void setTitleRightmenu() {

        int dp10px = DensityUtil.dip2px(getContext(), 10);
        int dp40px = DensityUtil.dip2px(getContext(), 60);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                dp40px, LinearLayout.LayoutParams.MATCH_PARENT);
        TextView commitTv = new TextView(getContext());
        commitTv.setLayoutParams(layoutParams);
        commitTv.setGravity(Gravity.CENTER);
        commitTv.setPadding(dp10px, 0, dp10px, 0);
        commitTv.setText("确定");
        commitTv.setTextColor(0xff232323);
        commitTv.setTextSize(15);
        getBarRightView().addView(commitTv);
        commitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedItems != null && checkedItems.size() > 0) {
                    StringBuffer stringBuffer = new StringBuffer();
                    StringBuffer ids = new StringBuffer();
                    StringBuffer floorIds = new StringBuffer();
                    StringBuffer floorNames = new StringBuffer();
                    for (int i = 0; i < checkedItems.size(); i++) {
                        if (i > 0) {
                            stringBuffer.append(",");
                            ids.append(",");
                            if (floorIds.length() > 0)
                                floorIds.append(",");
                            if (floorNames.length() > 0)
                                floorNames.append(",");
                        }
                        if (checkedItems.get(i) instanceof GXYJTurnOverPartAllFloorBean) {
                            GXYJTurnOverPartAllFloorBean floorBean = (GXYJTurnOverPartAllFloorBean) checkedItems.get(i);
                            stringBuffer.append(floorBean.floor);
                            ids.append(floorBean.floorId);
                            if (!floorIds.toString().contains(floorBean.floorId)) {
                                floorIds.append(floorBean.floorId);
                                floorNames.append(floorBean.floor);
                            }
                        } else if (checkedItems.get(i) instanceof GXYJRoomBean) {
                            GXYJRoomBean roomBean = (GXYJRoomBean) checkedItems.get(i);
                            stringBuffer.append(roomBean.roomNum);
                            ids.append(roomBean.roomId);
                            if (!floorIds.toString().contains(roomBean.floorId + "")) {
                                floorIds.append(roomBean.floorId);
                                floorNames.append(roomBean.floor);
                            }
                        } else if (checkedItems.get(i) instanceof GXYJFloorBean) {
                            GXYJFloorBean roomBean = (GXYJFloorBean) checkedItems.get(i);
                            if (!floorIds.toString().contains(roomBean.floorId + "")) {
                                floorIds.append(roomBean.floorId);
                                floorNames.append(roomBean.floor);
                            }
                            List<GXYJRoomBean> roomBeans = roomBean.getSubItems();
                            boolean isAllCheck = true;
                            StringBuffer roomBuffer = new StringBuffer();
                            for (int j = 0; j < roomBeans.size(); j++) {
                                GXYJRoomBean gxyjRoomBean = roomBeans.get(j);
                                if (gxyjRoomBean.ischeck) {
                                    if (!"全选".equals(gxyjRoomBean.roomNum)) {
                                        if (roomBuffer.length() > 0) {
                                            roomBuffer.append(",");
                                            ids.append(",");
                                        }
                                        roomBuffer.append(gxyjRoomBean.roomNum);
                                        ids.append(gxyjRoomBean.roomId);
                                    }
                                } else {
                                    isAllCheck = false;
                                }
                            }
                            if (isAllCheck) {
                                roomBuffer.delete(0, roomBuffer.length());
                                roomBuffer.append(roomBean.floor);
                            }
                            stringBuffer.append(roomBuffer.toString());
                        }
                    }
                    if (stringBuffer.toString().endsWith(",")) {
                        stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
                    }
                    if (ids.toString().endsWith(",")) {
                        ids.delete(ids.length() - 1, ids.length());
                    }
                    if (floorIds.toString().endsWith(",")) {
                        floorIds.delete(floorIds.length() - 1, floorIds.length());
                    }
                    if (floorNames.toString().endsWith(",")) {
                        floorNames.delete(floorNames.length() - 1, floorNames.length());
                    }
                    Intent intent = new Intent();
                    intent.putExtra("selectRoom", stringBuffer.toString());
                    intent.putExtra("selectIds", ids.toString());
                    intent.putExtra("floorIds", floorIds.toString());
                    intent.putExtra("floorNames", floorNames.toString());
                    setResult(203, intent);
                    finish();
                }
            }
        });
    }


    /**
     * 获取  房间列表数据
     */
    private void getBuildList() {
//        HttpRequest.get(getContext())
//                .url(ServerInterface.listPOTowerFloorUnit)
//                .params("towerId", towerId)
//                .params("unitId", unitId)
//                .params("partsDivision", partsDivision)
//                .doPost(new HttpStringCallBack() {
//                    @Override
//                    public void onSuccess(Object result) {
//                        switch (partsDivision) {
//                            case "分户":
//                                room(result.toString());
//                                break;
//                            case "分单元-整层":
//                                floor(result.toString());
//                                break;
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(int code, String msg) {
//                        ToastUtils.myToast(getActivity(), msg);
//                    }
//                });

        SharedPreferences sp = getActivity().getSharedPreferences("GXYJBuildList", Context.MODE_PRIVATE);
        final String s = sp.getString("listPOTowerFloorUnit", "");
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (partsDivision) {
                    case "分户":
                        room(s);
                        break;
                    case "分单元-整层":
                    case "不分单元-整层":
                        floor(s);
                        break;
                }

            }
        }).start();
    }


    /**
     * 分户数据类型
     *
     * @param s
     */
    private void room(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.optInt("code") == 0) {
                JSONArray array = jsonObject.optJSONArray("list");
                final ArrayList<MultiItemEntity> list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject floor = array.optJSONObject(i);
                    GXYJFloorBean floorBean = new GXYJFloorBean();
                    floorBean.floor = floor.optString("floor");
                    floorBean.floorId = floor.optInt("floorId");
                    floorBean.unitId = floor.optInt("unitId");
                    JSONArray roomNumChildarr = floor.optJSONArray("roomNumChild");
                    GXYJRoomBean roomBeanCheckAll = new GXYJRoomBean();
                    roomBeanCheckAll.roomNum = "全选";

                    roomBeanCheckAll.ischeck = false;
                    for (String aSelectroom : selectroom) {
                        if (floorBean.floor.equals(aSelectroom)) {
                            roomBeanCheckAll.ischeck = true;
                            checkedItems.add(floorBean);
                            break;
                        }
                    }

                    floorBean.addSubItem(roomBeanCheckAll);
                    boolean Allidentifying = false;
                    for (int j = 0; j < roomNumChildarr.length(); j++) {
                        JSONObject room = roomNumChildarr.optJSONObject(j);
                        GXYJRoomBean roomBean = new GXYJRoomBean();
                        roomBean.floorId = room.optInt("floorId");
                        roomBean.roomId = room.optInt("roomId");
                        roomBean.roomNum = room.optString("roomNum");
                        roomBean.utiId = room.optInt("utiId");
                        roomBean.floor = floorBean.floor;
                        roomBean.identifying = room.optString("identifying");
                        if ("空".equals(roomBean.identifying) && !Allidentifying) {
                            Allidentifying = true;
                        }
                        if (roomBeanCheckAll.ischeck) {
                            roomBean.ischeck = true;
                        } else {
                            for (String aSelectroom : selectroom) {
                                if (roomBean.roomNum.equals(aSelectroom)) {
                                    roomBean.ischeck = true;
                                    checkedItems.add(roomBean);
                                    break;
                                }
                            }
                        }
                        floorBean.addSubItem(roomBean);
                    }
                    roomBeanCheckAll.identifying = Allidentifying ? "空" : "都被验收了";
                    list.add(floorBean);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setBuildRcv(list);
                    }
                });

            } else {
                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分单元-整层
     *
     * @param s
     */
    private void floor(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.optInt("code") == 0) {
                JSONArray array = jsonObject.optJSONArray("list");
                final ArrayList<MultiItemEntity> list = new ArrayList<>();
                GXYJFloorBean floorBean = new GXYJFloorBean();
                floorBean.floor = "全部";
                list.add(floorBean);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.optJSONObject(i);
                    GXYJTurnOverPartAllFloorBean allFloorBean = new GXYJTurnOverPartAllFloorBean();
                    allFloorBean.pkId = object.optString("pkId");
                    allFloorBean.unitId = object.optString("unitId");
                    allFloorBean.floor = object.optString("floor") + "层";
                    allFloorBean.floorId = object.optString("floorId");
                    allFloorBean.utiId = object.optString("utiId");
                    allFloorBean.tower = object.optString("tower");
                    allFloorBean.identifying = object.optString("identifying");

                    for (String aSelectroom : selectroom) {
                        if ((allFloorBean.floor + "").equals(aSelectroom)) {
                            allFloorBean.ischeck = true;
                            checkedItems.add(allFloorBean);
                            break;
                        }
                    }
                    list.add(allFloorBean);
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setBuildRcv(list);
                    }
                });
            } else {
                ToastUtils.myToast(getActivity(), jsonObject.optString("msg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置数据到 rcv
     *
     * @param list
     */
    private void setBuildRcv(ArrayList<MultiItemEntity> list) {
        gxyjBuildingsAdapter = new GXYJTurnOverPartBuildingsAdapter(list);
        final GridLayoutManager manager = new GridLayoutManager(this, 4);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return gxyjBuildingsAdapter.getItemViewType(position) == GXYJBuildingsAdapter.TYPE_ROOM ? 1 : manager.getSpanCount();
            }
        });
        buildRcv.setAdapter(gxyjBuildingsAdapter);
        buildRcv.setLayoutManager(manager);
        gxyjBuildingsAdapter.expandAll();
        gxyjBuildingsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (gxyjBuildingsAdapter.getData().get(position) instanceof GXYJRoomBean) {
                    GXYJRoomBean roomBean = (GXYJRoomBean) gxyjBuildingsAdapter.getData().get(position);
                    if (roomBean.roomNum.equals("全选")) {

                        MultiItemEntity parent = gxyjBuildingsAdapter.getData().get(gxyjBuildingsAdapter.getParentPositionInAll(position));
                        if (parent instanceof GXYJFloorBean) {
                            final GXYJFloorBean floorParent = (GXYJFloorBean) parent;
                            if (roomBean.ischeck) {
                                roomBean.ischeck = false;
                                checkedItems.remove(floorParent);
                            } else {
                                roomBean.ischeck = true;
                                checkedItems.add(floorParent);
                            }
                            for (int i = 1; i < floorParent.getSubItems().size(); i++) {
                                if ("空".equals(roomBean.identifying)) {
                                    if (roomBean.ischeck) {
                                        if ("空".equals(floorParent.getSubItem(i).identifying)) {
                                            floorParent.getSubItem(i).ischeck = true;
                                        }
                                    } else {
                                        if ("空".equals(floorParent.getSubItem(i).identifying)) {
                                            floorParent.getSubItem(i).ischeck = false;
                                        }
                                    }
                                    checkedItems.remove(floorParent.getSubItem(i));
                                }
                            }
                        }
                    } else {
                        if (roomBean.ischeck) {
                            roomBean.ischeck = false;
                        } else {
                            roomBean.ischeck = true;
                        }
                        MultiItemEntity parent = gxyjBuildingsAdapter.getData().get(gxyjBuildingsAdapter.getParentPositionInAll(position));
                        if (parent instanceof GXYJFloorBean) {
                            final GXYJFloorBean floorParent = (GXYJFloorBean) parent;
                            boolean allCheck = true;
                            for (int i = 1; i < floorParent.getSubItems().size(); i++) {
                                if (floorParent.getSubItem(i).ischeck) {
                                    allCheck = true;
                                } else {
                                    allCheck = false;
                                    break;
                                }
                            }
                            floorParent.getSubItem(0).ischeck = allCheck;
                            if (allCheck) {
                                for (int i = 1; i < floorParent.getSubItems().size(); i++) {
                                    checkedItems.remove(floorParent.getSubItem(i));
                                }
                            } else {
                                checkedItems.remove(floorParent);
                                for (int i = 1; i < floorParent.getSubItems().size(); i++) {
                                    if (floorParent.getSubItem(i).ischeck) {
                                        if (!checkedItems.contains(floorParent.getSubItem(i))) {
                                            checkedItems.add(floorParent.getSubItem(i));
                                        }
                                    } else {
                                        if (checkedItems.contains(floorParent.getSubItem(i))) {
                                            checkedItems.remove(floorParent.getSubItem(i));
                                        }
                                    }
                                }
                            }
                        }
                    }
                    gxyjBuildingsAdapter.notifyDataSetChanged();
                } else if (gxyjBuildingsAdapter.getData().get(position) instanceof GXYJTurnOverPartAllFloorBean) {
                    GXYJTurnOverPartAllFloorBean roomBean = (GXYJTurnOverPartAllFloorBean) gxyjBuildingsAdapter.getData().get(position);
//                    ToastUtils.myToast(getActivity(), position + "--" + roomBean.floor);
                    if ("空".equals(roomBean.identifying)) {
                        if (roomBean.ischeck) {
                            roomBean.ischeck = false;
                            checkedItems.remove(roomBean);
                        } else {
                            roomBean.ischeck = true;
                            if (!checkedItems.contains(roomBean)) {
                                checkedItems.add(roomBean);
                            }
                        }
                        gxyjBuildingsAdapter.notifyDataSetChanged();
                    }

                } else {
                    ToastUtils.myToast(getActivity(), position + "");
                }
            }
        });
    }


}
