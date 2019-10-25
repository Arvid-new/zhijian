package com.haozhiyan.zhijian.activity.reportforms;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.reportforms.widget.MyLockTableView;
import com.haozhiyan.zhijian.utils.StatusBarUtils;

import java.util.ArrayList;

public class GXYJTableActivity extends BaseActivity2 {

    private LinearLayout mContentView;
    protected void init(Bundle savedInstanceState) {
        StatusBarUtils.setStatus(this, true);
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_gxyjtable;
    }

    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }


    @Override
    protected void initView() {
        setTitleText("1栋1单元报表");
        mContentView = (LinearLayout) findViewById(R.id.contentView);
        //构造假数据
        ArrayList<ArrayList<String>> mTableDatas = new ArrayList<ArrayList<String>>();
        ArrayList<String> mfristData = new ArrayList<String>();
        mfristData.add("楼层\n工序");
        for (int i = 0; i < 25; i++) {
            mfristData.add("地下室及车库底板");
        }
        mTableDatas.add(mfristData);
        for (int i = 32; i >=-2 ; i--) {
            ArrayList<String> mRowDatas = new ArrayList<String>();
            mRowDatas.add(i+"");
            for (int j = 0; j < 25; j++) {
                mRowDatas.add(j+"");
            }
            mTableDatas.add(mRowDatas);
        }
        final MyLockTableView mMyLockTableView = new MyLockTableView(GXYJTableActivity.this, mContentView, mTableDatas);
        mMyLockTableView.show();
    }

    @Override
    protected void initData() {

    }
}
