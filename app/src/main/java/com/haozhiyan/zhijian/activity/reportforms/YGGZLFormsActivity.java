package com.haozhiyan.zhijian.activity.reportforms;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormDateFilterAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormCLYSResult;
import com.haozhiyan.zhijian.model.UserInfo;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.rmondjone.locktableview.LockTableView;

import java.util.ArrayList;
import java.util.List;

/**
 * 报表-员工工作量首页
 */
public class YGGZLFormsActivity extends BaseFormsActivity {
    private TabLayout tabLayout;
    private UserInfo userInfo;
    private String userType;
    private LinearLayout mContentView;
    private RecyclerView rcv_choose_date;
    private FormDateFilterAdapter filterAdapter;
    private List<String> dateList = new ArrayList<>();
    private ArrayList<ArrayList<String>> mTableDatas = new ArrayList<>();
   // private String[] titles = {"现场检查登记问题", "现场检查复验问题", "实测实量点数", "工序移交次数", "材料验收任务"};
    private String[] titles = {"现场检查登记问题", "现场检查复验问题", "实测实量点数","工序移交次数"};

    private List<FormCLYSResult> listBeans = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        setTitleText("员工工作量");
        userInfo = UserInfo.create(this);
        setTabLayout();
        rcv_choose_date = getOutView(R.id.rcv_choose_date);
        rcv_choose_date.setLayoutManager(new GridLayoutManager(activity, 4));
        initChooseDate();
    }

    private void initChooseDate() {
        dateList.add("上周");
        dateList.add("本周");
        dateList.add("上月");
        dateList.add("本月");
        filterAdapter = new FormDateFilterAdapter(activity, dateList);
        rcv_choose_date.setAdapter(filterAdapter);
        filterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                filterAdapter.setSelectStatus(position);
                ToastUtils.myToast(getActivity(), dateList.get(position));
            }
        });
    }

    private void initTable() {

        mContentView = getOutView(R.id.contentView);
        final LockTableView mLockTableView = new LockTableView(this, mContentView, mTableDatas);
        mLockTableView.setLockFristColumn(true) //是否锁定第一列
                .setLockFristRow(true) //是否锁定第一行
                .setMaxColumnWidth(60) //列最大宽度
                .setMinColumnWidth(40) //列最小宽度
                .setColumnWidth(0, 70) //设置指定列文本宽度
                .setTextViewSize(13) //单元格字体大小
                .setFristRowBackGroudColor(R.color.blue6)//表头背景色
                .setTableHeadTextColor(R.color.black2)//表头字体颜色
                .setTableContentTextColor(R.color.black2)//单元格字体颜色
                .setCellPadding(10)//设置单元格内边距(dp)
                .setNullableString("0") //空值替换值
                .setOnItemSeletor(R.color.white_)//设置Item被选中颜色
                .show();
        mLockTableView.getTableScrollView().setPullRefreshEnabled(false);
        mLockTableView.getTableScrollView().setLoadingMoreEnabled(false);
    }

    private void setTabLayout() {
        userType = userInfo.getUserType();
        tabLayout = getOutView(R.id.tabLayout);
        switch (userType) {//3建设人员   2监理人员  1施工人员
            case "1":
                tabLayout.setVisibility(View.GONE);
                break;
            case "2":
                tabLayout.addTab(tabLayout.newTab().setText("施工单位"));
                tabLayout.addTab(tabLayout.newTab().setText("监理单位"));
                break;
            case "3":
                tabLayout.addTab(tabLayout.newTab().setText("施工单位"));
                tabLayout.addTab(tabLayout.newTab().setText("监理单位"));
                tabLayout.addTab(tabLayout.newTab().setText("建设单位"));
                break;
            default:
                break;
        }
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ToastUtils.myToast(activity, tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_yggzlforms;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        ArrayList<String> mfristRowData = new ArrayList<String>();
        mfristRowData.add("姓名");
        for (int i = 0; i < titles.length; i++) {
            mfristRowData.add(titles[i]);
        }
        mTableDatas.add(mfristRowData);
        for (int i = 0; i < 20; i++) {
            ArrayList<String> mRowDatas = new ArrayList<String>();
            mRowDatas.add("小李[名门监理员]" + i);
            for (int j = 0; j < 5; j++) {
                mRowDatas.add("数据" + j);
            }
            mTableDatas.add(mRowDatas);
        }
        if (mTableDatas.size() > 0) {
            initTable();
        }
    }

    @Override
    public void onDiKuaiChanged(String dikuai) {
        ToastUtils.myToast(getActivity(), dikuai);
    }

}
