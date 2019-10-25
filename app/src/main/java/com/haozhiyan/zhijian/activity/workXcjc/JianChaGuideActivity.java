package com.haozhiyan.zhijian.activity.workXcjc;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.JianChaGuideAdapter;
import com.haozhiyan.zhijian.bean.JCGuide1Level;
import com.haozhiyan.zhijian.bean.JCGuide2Level;
import com.haozhiyan.zhijian.bean.JCGuide3Level;
import com.haozhiyan.zhijian.bean.JCGuideBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/**
 * @name JianChaGuideActivity
 * @Desc 检查指引
 */
public class JianChaGuideActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.jcGuide_list)
    RecyclerView jcGuideList;
    private JianChaGuideAdapter adapter;
    private ArrayList<MultiItemEntity> list;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jian_cha_guide;
    }

    @Override
    protected void initView() {
        tv_title.setText("检查指引");
        jcGuideList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(boolean isNetWork) {
        adapter = new JianChaGuideAdapter(null, this);
        jcGuideList.setAdapter(adapter);
        getJCGuideItem();
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

    private void getJCGuideItem() {
        HttpRequest.get(this).url(ServerInterface.jcZhiYin).doGet(new HttpStringCallBack() {

            @Override
            public void onSuccess(Object result) {
                JCGuideBean jcGuideBean = new Gson().fromJson(result.toString(), JCGuideBean.class);
                if (jcGuideBean != null) {
                    if (listEmpty(jcGuideBean.list)) {
                        list = generateData(jcGuideBean.list);
                        adapter.setNewData(list);
                    } else {
                        adapter.setEmptyView(emptyView);
                    }
                } else {
                    adapter.setEmptyView(emptyView);
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(act, msg);
            }
        });
    }

    private ArrayList<MultiItemEntity> generateData(List<JCGuideBean.ListBean> dataList) {

        ArrayList<MultiItemEntity> als = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            JCGuide1Level guide01 = new JCGuide1Level(dataList.get(i).inspectionId, dataList.get(i).parentId, dataList.get(i).inspectionName, dataList.get(i).identifying);
            if (listEmpty(dataList.get(i).childIns)) {
                JCGuide2Level guide02;
                for (int j = 0; j < dataList.get(i).childIns.size(); j++) {
                    guide02 = new JCGuide2Level(dataList.get(i).childIns.get(j).inspectionId, dataList.get(i).childIns.get(j).parentId, dataList.get(i).childIns.get(j).inspectionName, dataList.get(i).childIns.get(j).identifying);
                    if (dataList.get(i).childIns.get(j).childQuestion != null) {
                        guide02.addSubItem(new JCGuide3Level(dataList.get(i).childIns.get(j).childQuestion.inspectionId, dataList.get(i).childIns.get(j).childQuestion.checkGuide));
                    }
                    guide01.addSubItem(guide02);
                }
            }
            als.add(guide01);
        }
        return als;
    }
}
