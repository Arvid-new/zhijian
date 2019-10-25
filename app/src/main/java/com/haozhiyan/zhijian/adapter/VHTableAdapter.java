package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.workScsl.SCSLCollectDetail;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.view.vhtableview.VHBaseAdapter;

import java.util.ArrayList;

/**
 * Created by WangZhenKai on 2019/5/16.
 * 都实现VHBaseAdapter接口
 */
public class VHTableAdapter implements VHBaseAdapter {
    private Context context;
    private ArrayList<String> titleData;
    private ArrayList<ArrayList<String>> dataList;
    private String dialogSelect = "";
    private String towerUnit = "";

    public VHTableAdapter(Context context, ArrayList<String> titleData, ArrayList<ArrayList<String>> dataList) {
        this.context = context;
        this.titleData = titleData;
        this.dataList = dataList;
    }

    //表格内容的行数，不包括标题行
    @Override
    public int getContentRows() {
        return dataList.size();
    }

    //列数
    @Override
    public int getContentColumn() {
        return titleData.size();
    }

    //标题的view，这里从0开始，这里要注意，一定要有view返回去，不能为null，每一行
    // 各列的宽度就等于标题行的列的宽度，且边框的话，自己在这里和下文的表格单元格view里面设置
    @Override
    public View getTitleView(int columnPosition, ViewGroup parent) {

        TextView tv_item = new TextView(context);
        tv_item.setBackgroundResource(R.drawable.bg_shape_blue);
        if (0 == columnPosition) {
            tv_item.setPadding(65, 20, 65, 20);
        } else {
            tv_item.setPadding(25, 20, 25, 20);
        }
        tv_item.setTextSize(15);
        tv_item.setText(titleData.get(columnPosition));
        tv_item.setGravity(Gravity.CENTER_VERTICAL);
        tv_item.setTextColor(UiUtils.getColor(R.color.black2));
        return tv_item;
    }

    //表格正文的view，行和列都从0开始，宽度的话在载入的时候，默认会是以标题行各列的宽度，高度的话自适应
    @Override
    public View getTableCellView(final int contentRow, final int contentColum, View view, ViewGroup parent) {
        if (null == view) {
            view = new TextView(context);

        }
        if (contentColum == 0) {
            if (contentRow == 0 || contentRow == 1) {
                ((TextView) view).setTextColor(UiUtils.getColor(R.color.black_3));
                ((TextView) view).setClickable(false);
            } else {
                ((TextView) view).setTextColor(UiUtils.getColor(R.color.blue));
                ((TextView) view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(context, "点击=第" + (contentRow + 1) + "行,第" + (contentColum + 1) + "列—" + dataList.get(contentRow), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "操作==" + dataList.get(contentRow).get(0), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, SCSLCollectDetail.class);
                        intent.putExtra("floor", dataList.get(contentRow).get(0));
                        intent.putExtra("jcx", getDialogSelect());
                        intent.putExtra("towerUnit", getTowerUnit());
                        context.startActivity(intent);
                    }
                });
            }
        } else {
            ((TextView) view).setClickable(false);
            ((TextView) view).setTextColor(UiUtils.getColor(R.color.black_3));
        }
        view.setBackgroundResource(R.drawable.bg_shape_gray);
        view.setPadding(25, 20, 25, 20);
        ((TextView) view).setText(dataList.get(contentRow).get(contentColum));
        ((TextView) view).setGravity(Gravity.CENTER);
        ((TextView) view).setTextSize(14);
        return view;
    }


    @Override
    public Object getItem(int contentRow) {
        return dataList.get(contentRow);
    }


    //每一行被点击的时候的回调
    @Override
    public void OnClickContentRowItem(int row, View convertView) {

    }

    public String getDialogSelect() {
        return dialogSelect;
    }

    public void setDialogSelect(String dialogSelect) {
        this.dialogSelect = dialogSelect;
    }

    public String getTowerUnit() {
        return towerUnit;
    }
    public void setTowerUnit(String towerUnit) {
        this.towerUnit = towerUnit;
    }
}
