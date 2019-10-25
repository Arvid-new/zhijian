package com.haozhiyan.zhijian.activity.MePackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.activity.MyWebView;
import com.haozhiyan.zhijian.adapter.HelpTextAdapter;
import com.haozhiyan.zhijian.bean.HelpBean;
import com.haozhiyan.zhijian.bean.HelpProblemItem;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

//帮助
public class HelpActTwo extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tvTitle;
    @ViewInject(R.id.tv_Type)
    TextView tvType;
    @ViewInject(R.id.problemList_two)
    ListView problemListTwo;
    private HelpTextAdapter textAdapter;
    private List<HelpBean.ListBean.QBWTBean.SunBean> sunBeans;
    private List<HelpProblemItem> list;
    private String problemTitle = "";

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_help_two;
    }

    @Override
    protected void initView() {
        tvTitle.setText("帮助");
    }

    @Override
    protected void initListener() {
        problemListTwo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listEmpty(list)) {
                    Intent intent = new Intent(act, MyWebView.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("url", list.get(position).connect);
                    bundle.putString("title", "帮助");
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        problemTitle = getIntent().getStringExtra("problemName");
        tvType.setText(StringUtils.isEmpty(problemTitle) ? "问题" : problemTitle);
        try {
            sunBeans = (List<HelpBean.ListBean.QBWTBean.SunBean>) getIntent().getSerializableExtra("QBwtBean");
            if (listEmpty(sunBeans)) {
                list = new ArrayList<>();
                for (int i = 0; i < sunBeans.size(); i++) {
                    list.add(new HelpProblemItem(sunBeans.get(i).id, sunBeans.get(i).parentId, sunBeans.get(i).helpName, sunBeans.get(i).connect));
                }
                textAdapter = new HelpTextAdapter(this, list);
                problemListTwo.setAdapter(textAdapter);
                textAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_back})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            default:
                break;
        }
    }
}
