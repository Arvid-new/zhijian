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
import com.haozhiyan.zhijian.bean.scsl.SCSLTowerFRBean;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.view.MyGridView;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/16.
 * Describe: Ydzj_project
 */
public class SCSLLocalAdapter extends BaseQuickAdapter<SCSLTowerFRBean.ListBean, BaseViewHolder> {

    private Context context;
    private ScslGridAdapter scslGridAdapter;
    private String inspectionId = "", inspectionName = "", inspectionSunName = "";

    public SCSLLocalAdapter(Context context, int layoutResId, @Nullable List<SCSLTowerFRBean.ListBean> data) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, SCSLTowerFRBean.ListBean item) {
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

    private void setChildGrid(MyGridView myGridView, final String floor, final List<SCSLTowerFRBean.ListBean.RoomNumChildBean> roomNumChildBeans) {
        scslGridAdapter = new ScslGridAdapter(context, roomNumChildBeans, R.layout.scsl_grid_item);
        myGridView.setAdapter(scslGridAdapter);
        myGridView.setPressed(true);
        myGridView.setEnabled(true);
        myGridView.setClickable(true);
        myGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SCSLDetail.class);
                intent.putExtra("inspectionId", inspectionId);
                intent.putExtra("floor", floor);
                intent.putExtra("identifying", roomNumChildBeans.get(position).identifying);
                context.startActivity(intent);
            }
        });
    }

    public void setInspectionId(String inspectionName) {
        this.inspectionName = inspectionName;
    }

    public void setInspectionId(String myInspectionId, String inspectionSunName) {
        this.inspectionId = myInspectionId;
        this.inspectionSunName = inspectionSunName;
    }
}
