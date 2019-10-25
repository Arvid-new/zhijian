package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.workScsl.SCSLDetail;
import com.haozhiyan.zhijian.bean.scsl.SCSLInitBean;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.view.MyGridView;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/16.
 * Describe: Ydzj_project
 */
public class SCSLLocalInitAdapter extends BaseQuickAdapter<SCSLInitBean.ListBean.MessagesBean, BaseViewHolder> {

    private Context context;
    private ScslGridInitAdapter scslGridAdapter;
    private String inspectionId = "", inspectionSunName = "";
    private String inspectionParentId = "", inspectionName = "";
    private String towerId = "", towerName = "";
    private String unitId = "", unitName = "";
    private String sectionName = "", sectionId = "";
    private String userType = "";

    public SCSLLocalInitAdapter(Context context, @Nullable List<SCSLInitBean.ListBean.MessagesBean> data, String userType) {
        super(R.layout.scsl_tower_item, data);
        this.context = context;
        this.userType = userType;
    }

    @Override
    protected void convert(BaseViewHolder helper, SCSLInitBean.ListBean.MessagesBean item) {
        helper.setText(R.id.tv_flooer, item.floor);
        if (getData().size() - 1 == helper.getAdapterPosition()) {
            helper.setGone(R.id.view_footer, true);
        } else {
            helper.setGone(R.id.view_footer, false);
        }
        MyGridView myGridView = helper.getView(R.id.mgv_list);
        if (ListUtils.listEmpty(item.roomNumChild)) {
            setChildGrid(myGridView, item.floor, item.roomNumChild);
        }
    }

    private void setChildGrid(MyGridView myGridView, final String floorName,
                              final List<SCSLInitBean.ListBean.MessagesBean.RoomNumChildBean> roomNumChildBeans) {
        scslGridAdapter = new ScslGridInitAdapter(context, roomNumChildBeans, userType);
        myGridView.setAdapter(scslGridAdapter);
        myGridView.setPressed(true);
        myGridView.setEnabled(true);
        myGridView.setClickable(true);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.print("楼层==="+floorName);
                Intent intent = new Intent(context, SCSLDetail.class);
                intent.putExtra("inspectionId", inspectionId);
                intent.putExtra("inspectionParentId", inspectionParentId);
                intent.putExtra("inspectionName", inspectionName);
                intent.putExtra("inspectionSunName", inspectionSunName);
                intent.putExtra("sectionId", sectionId);
                intent.putExtra("sectionName", sectionName);
                intent.putExtra("towerId", towerId);
                intent.putExtra("towerName", towerName);
                intent.putExtra("unitId", unitId);
                intent.putExtra("unitName", unitName);
                intent.putExtra("roomId", roomNumChildBeans.get(position).roomId);
                intent.putExtra("roomNum", roomNumChildBeans.get(position).roomNum);
                intent.putExtra("floorName", floorName);
                intent.putExtra("floorId", roomNumChildBeans.get(position).floorId+"");
                intent.putExtra("towerUnitFloorRoom", sectionName + towerName + unitName);
                intent.putExtra("entrance", "work");
                intent.putExtra("shiGongStatus", roomNumChildBeans.get(position).shigongState);
                intent.putExtra("jianLiStatus", roomNumChildBeans.get(position).jianliState);
                intent.putExtra("jianSheStatus", roomNumChildBeans.get(position).jiansheState);
                context.startActivity(intent);
            }
        });
    }

    public void setInspectionName(String inspectionParentId, String inspectionName) {
        this.inspectionName = inspectionName;
        this.inspectionParentId = inspectionParentId;
    }

    public void setInspectionId(String myInspectionId, String inspectionSunName) {
        this.inspectionId = myInspectionId;
        this.inspectionSunName = inspectionSunName;
    }

    public void setSection(String sectionId,String sectionName) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
    }

    public void setTower(String towerId,String towerName) {
        this.towerId = towerId;
        this.towerName = towerName;
    }

    public void setUnit(String unitId,String unitName) {
        this.unitId = unitId;
        this.unitName = unitName;
    }
}
