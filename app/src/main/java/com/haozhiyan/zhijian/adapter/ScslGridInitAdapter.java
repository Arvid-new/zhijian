package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.scsl.SCSLInitBean;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonAdapter;
import com.haozhiyan.zhijian.widget.adapterEncapsulation.CommonViewHolder;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/16.
 * Describe: Ydzj_project
 */
public class ScslGridInitAdapter extends CommonAdapter<SCSLInitBean.ListBean.MessagesBean.RoomNumChildBean> {

    private String userType = "";

    public ScslGridInitAdapter(Context context, List<SCSLInitBean.ListBean.MessagesBean.RoomNumChildBean> list, String userType) {
        super(context, list, R.layout.scsl_grid_item);
        this.userType = userType;
    }

    @Override
    public void convert(CommonViewHolder viewHolder, SCSLInitBean.ListBean.MessagesBean.RoomNumChildBean data, int position) {
        TextView tv = ((TextView) viewHolder.getView(R.id.tv_item));
        tv.setText(data.roomNum);
        //"空"  "待验收" "已退回"  "已通过"
        switch (userType) {
            case "0":
            case "3":
                if (TextUtils.equals(data.jiansheState, null)) {
                    if (TextUtils.equals(data.jianliState, null)) {
                        if (TextUtils.equals(data.shigongState, null)) {
                            tv.setTextColor(UiUtils.getColor(R.color.black_3));
                            tv.setBackgroundResource(R.drawable.back2_text_selector);
                        } else {
                            if (TextUtils.equals(data.shigongState, "1")) {
                                tv.setTextColor(UiUtils.getColor(R.color.white));
                                tv.setBackgroundResource(R.drawable.green_2radius_back);
                            }
                        }
                    } else {
                        if (TextUtils.equals(data.jianliState, "2")) {
                            tv.setTextColor(UiUtils.getColor(R.color.white));
                            tv.setBackgroundResource(R.drawable.yellow_back_shape);
                        }
                    }
                } else {
                    if (TextUtils.equals(data.jiansheState, "3")) {
                        tv.setTextColor(UiUtils.getColor(R.color.white));
                        tv.setBackgroundResource(R.drawable.blue_2radius_back);
                    }
                }
                break;
            case "1":
                if (TextUtils.equals(data.shigongState, null)) {
                    tv.setTextColor(UiUtils.getColor(R.color.black_3));
                    tv.setBackgroundResource(R.drawable.back2_text_selector);
                } else {
                    if (TextUtils.equals(data.shigongState, "1")) {
                        tv.setTextColor(UiUtils.getColor(R.color.white));
                        tv.setBackgroundResource(R.drawable.green_2radius_back);
                    }
                }
                break;
            case "2":
                if (TextUtils.equals(data.jianliState, "2")) {
                    tv.setTextColor(UiUtils.getColor(R.color.white));
                    tv.setBackgroundResource(R.drawable.yellow_back_shape);
                }else if(TextUtils.equals(data.shigongState, "1")){
                    tv.setTextColor(UiUtils.getColor(R.color.white));
                    tv.setBackgroundResource(R.drawable.green_2radius_back);
                }else{
                    tv.setTextColor(UiUtils.getColor(R.color.black_3));
                    tv.setBackgroundResource(R.drawable.back2_text_selector);
                }
                break;
            default:
                tv.setTextColor(UiUtils.getColor(R.color.black_3));
                tv.setBackgroundResource(R.drawable.back2_text_selector);
                break;
        }
    }
}
