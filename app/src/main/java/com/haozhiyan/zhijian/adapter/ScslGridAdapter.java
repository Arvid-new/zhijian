package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.scsl.SCSLTowerFRBean;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/16.
 * Describe: Ydzj_project
 */
public class ScslGridAdapter extends CommonAdapter<SCSLTowerFRBean.ListBean.RoomNumChildBean> {

    public ScslGridAdapter(Context context, List<SCSLTowerFRBean.ListBean.RoomNumChildBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(CommonViewHolder viewHolder, SCSLTowerFRBean.ListBean.RoomNumChildBean data, int position) {
        TextView tv = ((TextView) viewHolder.getView(R.id.tv_item));
        tv.setText(data.roomNum);
        switch (data.identifying) {
            case "空":
                tv.setTextColor(UiUtils.getColor(R.color.black_3));
                tv.setBackgroundResource(R.drawable.back2_text_selector);
                break;
            case "待验收":
                tv.setTextColor(UiUtils.getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.yellow_back_shape);
                break;
            case "已退回":
                tv.setTextColor(UiUtils.getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.red_back_shape);
                break;
            case "已通过":
                tv.setTextColor(UiUtils.getColor(R.color.white));
                tv.setBackgroundResource(R.drawable.green_2radius_back);
                break;
            default:
                break;
        }
    }
}
