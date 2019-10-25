package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.TextAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

//现场检查非正常关闭原因
public class XcJcBackReason extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.reasonList)
    ListView reasonList;
    @ViewInject(R.id.btnCommit)
    Button btnCommit;
    private TextAdapter textAdapter;
    private List<ItemBean> list;
    private String[] reason = new String[]{"问题登记错误", "已整改并复验通过", "无法整改", "延误整改窗口期", "留待后期整改", "其它"};
    private String reasonSelected="";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_xc_jc_back_reason;
    }

    @Override
    protected void initView() {
        tv_title.setText("选择非正常关闭原因");
        list = new ArrayList<>();
        for (int i = 0; i < reason.length; i++) {
            list.add(new ItemBean(reason[i]));
        }
        textAdapter = new TextAdapter(this, list);
        reasonList.setAdapter(textAdapter);
        textAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initListener() {
        reasonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textAdapter.setSelect(position);
                if (listEmpty(list)) {
                    reasonSelected = list.get(position).name;
                } else {
                    reasonSelected = reason[position];
                }
                LogUtils.i("zhi===",reasonSelected);
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {

    }

    @OnClick({R.id.rl_back, R.id.btnCommit})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.btnCommit:
                Intent intent = new Intent();
                intent.putExtra("reason",reasonSelected);
                setResult(Constant.XCJC_REASON_CODE,intent);
                finish();
                break;
            default:
                break;
        }
    }
}
