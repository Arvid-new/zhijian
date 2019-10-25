package com.haozhiyan.zhijian.activity.workScsl;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MainActivity;
import com.haozhiyan.zhijian.adapter.MyListAdapter;
import com.haozhiyan.zhijian.adapter.VHTableAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.UiUtils;
import com.haozhiyan.zhijian.view.vhtableview.VHTableView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

//实测实量汇总数据
public class SCSLCollectActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.iv_close)
    ImageView ivClose;
    @ViewInject(R.id.ll_select)
    LinearLayout ll_select;
    @ViewInject(R.id.vht_table)
    VHTableView vhtTable;
    @ViewInject(R.id.tv_Name)
    TextView tvName;
    @ViewInject(R.id.iv)
    ImageView iv;
    @ViewInject(R.id.ll_list_window)
    RelativeLayout listWindow;
    @ViewInject(R.id.lv01)
    ListView lv01;
    @ViewInject(R.id.lv02)
    ListView lv02;
    private VHTableAdapter tableAdapter;
    private MyListAdapter adapter, adapter2;
    private List<ItemBean> data1 = new ArrayList<>();
    private List<ItemBean> data2 = new ArrayList<>();
    private int mHiddenViewBackHeight;
    private String lv01Str, lv02Str;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_scslcollect;
    }

    @Override
    protected void initView() {
        Bundle bundle = getIntent().getBundleExtra("data");
        String local = bundle.getString("local");
        tvTitle.setText(bundle == null ? "汇总" : (StringUtils.isEmpty(local) ? "汇总" : local));
        ivClose.setVisibility(View.VISIBLE);
        mHiddenViewBackHeight = SystemUtils.getPhoneScreenHight(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        ArrayList<String> titleData = new ArrayList<>();
        titleData.add("数据");
        titleData.add("施工单位");
        titleData.add("监理单位");
        titleData.add("建设单位");
        ArrayList<ArrayList<String>> contentData = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            ArrayList<String> contentRowData = new ArrayList<>();
            if (i < 10) {
                if (i == 1) {
                    contentRowData.add("已测层数");
                } else if (i == 2) {
                    contentRowData.add("总合格率");
                } else {
                    contentRowData.add("0" + i);
                }
            } else {
                contentRowData.add(i + "");
            }
            contentRowData.add(i + "行一列");
            contentRowData.add(i + "行二列");
            contentRowData.add(i + "行三列");
            contentData.add(contentRowData);
        }
        tableAdapter = new VHTableAdapter(this, titleData, contentData);
        vhtTable.setAdapter(tableAdapter);
        tableAdapter.setTowerUnit(UiUtils.getContent(tvTitle));
        for (int i = 0; i < 20; i++) {
            data1.add(new ItemBean("测试一列" + i, "", 0));
        }
        for (int i = 0; i < 20; i++) {
            data2.add(new ItemBean("测试二列描述" + i, "", 1));
        }
        setList(data1);
        setList2(data2);
        lv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelected(position);
                lv01Str = data1.get(position).name;
            }
        });
        lv02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter2.setSelected(position);
                lv02Str = data2.get(position).name;
                tvName.setText(lv01Str + lv02Str);
                tableAdapter.setDialogSelect(lv01Str + lv02Str);
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.iv_close, R.id.ll_select, R.id.ll_list_window})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.iv_close:
                jumpToActivity(MainActivity.class);
                ActivityManager.getInstance().clearAll();
                break;
            case R.id.ll_select:
                if (listWindow.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(listWindow, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv);
                } else {
                    AnimationUtil.getInstance().animateClose(listWindow);
                    AnimationUtil.getInstance().animationIvClose(iv);
                }
                break;
            case R.id.ll_list_window:
                AnimationUtil.getInstance().animateClose(listWindow);
                AnimationUtil.getInstance().animationIvClose(iv);
                break;
            default:
                break;
        }
    }

    private void setList(List<ItemBean> list) {
        adapter = new MyListAdapter(this, list, R.layout.scsl_list_item);
        lv01.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setList2(List<ItemBean> list) {
        adapter2 = new MyListAdapter(this, list, R.layout.scsl_list_item);
        lv02.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
    }
}
