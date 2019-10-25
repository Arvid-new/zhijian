package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.workXcjc.XCJCImagesActivity;
import com.haozhiyan.zhijian.bean.PlaceNewBean;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/7.
 * Describe: Ydzj_project 检查部位适配器
 */
public class PlaceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //楼栋
    private final int RIDGEPOLE_ITEM_CODE = 1;
    //楼单元
    private final int UNIT_ITEM_CODE = 2;
    //楼层
    private final int FLOOR_ITEM_CODE = 3;
    //楼住户编号
    private final int ROOM_ITEM_CODE = 4;
    //空布局
    private final int EMPTY_VIEW_ITEM_CODE = 5;
    private int typeViewId = R.layout.app_layout_empty;
    //private int childViewId = R.layout.place_list_item;
    private LayoutInflater inflater;
    private PlaceTowerAdapter placeTowerAdapter;
    private PlaceUnitAdapter placeUnitAdapter;
    private PlaceFloorAdapter placeFloorAdapter;
    private PlaceRoomAdapter placeRoomAdapter;
    private List<PlaceNewBean.ListBean> dataBeans;
    private List<PlaceNewBean.ListBean.UnitChildBean> unitList;
    private List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean> floorList;
    private List<PlaceNewBean.ListBean.UnitChildBean.FloorChildBean.RoomNumChildBean> roomList;
    private Activity context;
    private String selectContent = "";
    //选中楼栋单元标签对应存放
    private String[] label = new String[4];
    //选中楼栋单元id对应存放
    private String[] labelId = new String[4];
    //选中数据保存集合
    private List<String> placeList = new ArrayList<>();

    public PlaceAdapter(Activity context, List<PlaceNewBean.ListBean> list) {
        this.dataBeans = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        placeTowerAdapter = new PlaceTowerAdapter(null,"");
        placeUnitAdapter = new PlaceUnitAdapter(null,"");
        placeFloorAdapter = new PlaceFloorAdapter(null,"");
        placeRoomAdapter = new PlaceRoomAdapter(null,"");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RIDGEPOLE_ITEM_CODE) {
            typeViewId = R.layout.rvlist_ridgepole_item;
        } else if (viewType == UNIT_ITEM_CODE) {
            typeViewId = R.layout.rvlist_unit_item;
        } else if (viewType == FLOOR_ITEM_CODE) {
            typeViewId = R.layout.rvlist_floor_item;
        } else if (viewType == ROOM_ITEM_CODE) {
            typeViewId = R.layout.rvlist_room_item;
        } else {
            typeViewId = R.layout.app_layout_empty;
        }
        ViewHolder holder = new ViewHolder(inflater.inflate(typeViewId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder itemHolder = (ViewHolder) holder;
        itemHolder.bingHolder(position, itemHolder.getItemViewType());
        dataSet();
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return RIDGEPOLE_ITEM_CODE;
        } else if (position == 1) {
            return UNIT_ITEM_CODE;
        } else if (position == 2) {
            return FLOOR_ITEM_CODE;
        } else if (position == 3) {
            return ROOM_ITEM_CODE;
        } else {
            return EMPTY_VIEW_ITEM_CODE;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView ridgepole_list;
        private RecyclerView unit_list;
        private RecyclerView floor_list;
        private RecyclerView room_list;

        public ViewHolder(View itemView) {
            super(itemView);
            ridgepole_list = itemView.findViewById(R.id.ridgepole_list);
            unit_list = itemView.findViewById(R.id.unit_list);
            floor_list = itemView.findViewById(R.id.floor_list);
            room_list = itemView.findViewById(R.id.room_list);
        }

        public void bingHolder(int position, final int itemType) {
            if (ListUtils.listEmpty(dataBeans)) {

                if (itemType == RIDGEPOLE_ITEM_CODE) {
                    initChildRecycleView(ridgepole_list);
                    ridgepole_list.setAdapter(placeTowerAdapter);
                    placeTowerAdapter.setNewData(dataBeans);
                    placeTowerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            placeTowerAdapter.setSelectStatus(position);
                            label[0] = dataBeans.get(position).tower;
                            labelId[0] = dataBeans.get(position).towerId + "";
                            dataSet();
                            if (ListUtils.listEmpty(dataBeans.get(position).unitChild)) {
                                unitList = dataBeans.get(position).unitChild;
                                placeUnitAdapter.setNewData(unitList);
                                placeFloorAdapter.setNewData(null);
                                placeRoomAdapter.setNewData(null);
//                                if(ListUtils.listEmpty(unitList)){
//                                    floorList = unitList.get(0).floorChild;
//                                    placeFloorAdapter.setNewData(floorList);
//                                    if(ListUtils.listEmpty(floorList)){
//                                        roomList = floorList.get(0).roomNumChild;
//                                        placeRoomAdapter.setNewData(roomList);
//                                    }
//                                }
                                refreshData(1);
                            } else {
                                refreshAdapter();
                            }
                        }
                    });
                } else if (itemType == UNIT_ITEM_CODE) {
                    initChildRecycleView(unit_list);
                    unit_list.setAdapter(placeUnitAdapter);
                    placeUnitAdapter.setNewData(unitList);
                    placeUnitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            placeUnitAdapter.setSelectStatus(position);
                            label[1] = unitList.get(position).unit;
                            labelId[1] = unitList.get(position).unitId + "";
                            dataSet();
                            if (ListUtils.listEmpty(unitList.get(position).floorChild)) {
                                floorList = unitList.get(position).floorChild;
                                refreshData(2);
                            } else {
                                refreshAdapter();
                            }
                        }
                    });
                } else if (itemType == FLOOR_ITEM_CODE) {
                    initChildRecycleView(floor_list);
                    floor_list.setAdapter(placeFloorAdapter);
                    placeFloorAdapter.setNewData(floorList);
                    placeFloorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            placeFloorAdapter.setSelectStatus(position);
                            label[2] = floorList.get(position).floor;
                            labelId[2] = floorList.get(position).floorId + "";
                            dataSet();
                            if (ListUtils.listEmpty(floorList.get(position).roomNumChild)) {
                                roomList = floorList.get(position).roomNumChild;
                                refreshData(3);
                            } else {
                                refreshAdapter();
                            }
                        }
                    });
                } else if (itemType == ROOM_ITEM_CODE) {
                    initChildRecycleView(room_list);
                    room_list.setAdapter(placeRoomAdapter);
                    placeRoomAdapter.setNewData(roomList);
                    placeRoomAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                            placeRoomAdapter.setSelectStatus(position);
                            label[3] = roomList.get(position).roomNum;
                            labelId[3] = roomList.get(position).roomId + "";
                            dataSet();
                            if (StringUtils.isEmpty(roomList.get(position).roomHouse)) {
                                Toast.makeText(context, "当前房间户型图错误,请选择其它房间", Toast.LENGTH_SHORT).show();
                            } else {
                                if (roomList.get(position).roomHouse.equals("0")) {
                                    if (listener != null) {
                                        listener.roomBack();
                                    }
                                } else {
                                    Intent intent = new Intent(context, XCJCImagesActivity.class);
                                    intent.putExtra("towerId", labelId[0]);
                                    intent.putExtra("unitId", labelId[1]);
                                    intent.putExtra("floorId", labelId[2]);
                                    intent.putExtra("roomId", roomList.get(position).roomId + "");
                                    intent.putExtra("towerFloorUnitRoom", label[0]+label[1]+label[2]+label[3]);
                                    context.startActivity(intent);
                                    context.finish();
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    public void dataSet() {
        placeList.clear();
        if (label.length > 0 && label != null) {
            for (String labelStr : label) {
                placeList.add(labelStr);
                selectContent = StringUtils.listToStrByStr(placeList, "");
            }
        }
    }

    private void refreshAdapter() {
        try {
            notifyDataSetChanged();
            notifyItemChanged(1);
            notifyItemChanged(2);
            notifyItemChanged(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void refreshData(int type) {
        if (type == 1) {//楼栋
            placeUnitAdapter.setNewData(unitList);
            placeFloorAdapter.setNewData(null);
            placeRoomAdapter.setNewData(null);
        } else if (type == 2) {//单元
            placeFloorAdapter.setNewData(floorList);
            placeRoomAdapter.setNewData(null);
        } else if (type == 3) {//楼层
            placeRoomAdapter.setNewData(roomList);
        }
        refreshAdapter();
    }

    private void initChildRecycleView(RecyclerView rlv) {
        rlv.setLayoutManager(new GridLayoutManager(context, 4, LinearLayoutManager.VERTICAL, false));
        //rlv.addItemDecoration(new GridSpacingItemDecoration(4, 5, true));
    }

    public String getSelectPlace() {
        return selectContent;
    }
    public String[] getSelectPlaceArray() {
        return label;
    }

    public String[] house() {
        return labelId;
    }

    private OnItemRoomClickListener listener;

    public void setItemRoomClickListener(OnItemRoomClickListener clickListener) {
        this.listener = clickListener;
    }

    public interface OnItemRoomClickListener {
        void roomBack();
    }
}
