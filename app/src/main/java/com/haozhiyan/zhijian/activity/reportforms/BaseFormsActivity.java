package com.haozhiyan.zhijian.activity.reportforms;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity2;
import com.haozhiyan.zhijian.activity.reportforms.adapter.SelectProjectAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.ProjectListBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.haozhiyan.zhijian.utils.ListUtils.listEmpty;
/**
 * 报表-切换项目基类
 * */
public abstract  class BaseFormsActivity extends BaseActivity2 implements DiKuaiChangeListener {
    private RelativeLayout rl_select_project;
    private TextView tv_select_name;
    private TextView tv_nodata;
    private ImageView iv_select_icon;
    private RelativeLayout rl_hidden;
    private RelativeLayout ll_list_window;
    private LinearLayout linear_close;
    private ListView lv01;
    private ListView lv02;
    private TextView tvNoBD;
    private int mHiddenViewBackHeight;
    private SelectProjectAdapter adapter1, adapter2;
    private List<ItemBean> data1, data2;
    private List<ProjectListBean.ListBean> listBeans;
    private List<ProjectListBean.ListBean.ChildsBean> childsBeans;
    //real_estateName 地产名称   plotName 地块名称
    private String real_estateName, plotName;
    //real_estateId 地产id
    private int real_estateId;
    BaseFormsActivity activity;
    @Override
    protected void init(Bundle savedInstanceState) {
        activity = this;
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_base_forms;
    }

    @Override
    protected int getTitleBarType() {
        return TITLEBAR_DEFAULT;
    }

    @Override
    protected void initView() {
        setTitleText("材料验收");
        rl_select_project = getOutView(R.id.rl_select_project);
        tv_select_name = getOutView(R.id.tv_project_name);
        iv_select_icon = getOutView(R.id.iv_select_icon);
        tv_nodata = getOutView(R.id.tv_nodata);
        rl_hidden = getOutView(R.id.rl_hidden);
        linear_close = getOutView(R.id.linear_close);
        ll_list_window = getOutView(R.id.ll_list_window);
        lv01 = getOutView(R.id.lv01);
        lv02 = getOutView(R.id.lv02);
        tvNoBD = getOutView(R.id.tv_noBD);
        getOutView(R.id.rl_unit).setVisibility(View.GONE);
        //初始化下拉布局参数
        mHiddenViewBackHeight = SystemUtils.getPhoneScreenHight(activity);
        linear_close.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mHiddenViewBackHeight * 2 / 3));
        ll_list_window.setVisibility(View.VISIBLE);
        initListener();
    }

    @Override
    protected void initData() {
        getProject();
    }
    protected void initListener() {
        setListenerView(rl_select_project);
        setListenerView(rl_hidden);
        lv01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listEmpty(listBeans)) {
                    real_estateName = listBeans.get(position).iteamName;
                    real_estateId = listBeans.get(position).pkId;
                    //Constant.parentProjectId = listBeans.get(position).pkId;
                    //Constant.parentProjectName = listBeans.get(position).iteamName;
                    adapter1.setSelectedById(listBeans.get(position).pkId);
                    if (listEmpty(listBeans.get(position).childs)) {
                        tvNoBD.setVisibility(View.GONE);
                        lv02.setVisibility(View.VISIBLE);
                        childsBeans = listBeans.get(position).childs;
                        data2 = new ArrayList<>();
                        for (int i = 0; i < listBeans.get(position).childs.size(); i++) {
                            data2.add(new ItemBean(listBeans.get(position).childs.get(i).iteamName, "", listBeans.get(position).childs.get(i).pkId));
                        }
                        adapter2 = new SelectProjectAdapter(activity, data2, R.layout.scsl_list_item);
                        lv02.setAdapter(adapter2);
                    } else {
                        tvNoBD.setVisibility(View.VISIBLE);
                        lv02.setVisibility(View.GONE);
                    }
                }
            }
        });
        lv02.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listEmpty(childsBeans)) {
                    Constant.projectId = childsBeans.get(position).pkId;
                    Constant.diKuaiName = childsBeans.get(position).iteamName;
                    plotName = childsBeans.get(position).iteamName;
                    Constant.projectName = real_estateName + plotName;
                    Constant.parentProjectId = real_estateId;
                    Constant.parentProjectName = real_estateName;
                    tv_select_name.setText(real_estateName + plotName);
                    AnimationUtil.getInstance().animateClose(rl_hidden);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                    adapter2.setSelectedById(childsBeans.get(position).pkId);
                    setonDiKuaiChangeListener(activity,plotName);
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_select_project:
                if (rl_hidden.getVisibility() == View.GONE) {
                    AnimationUtil.getInstance().animateOpen(rl_hidden, mHiddenViewBackHeight);
                    AnimationUtil.getInstance().animationIvOpen(iv_select_icon);
                } else {
                    AnimationUtil.getInstance().animateClose(rl_hidden);
                    AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                }
                break;
            case R.id.rl_hidden:
                AnimationUtil.getInstance().animateClose(rl_hidden);
                AnimationUtil.getInstance().animationIvClose(iv_select_icon);
                break;
            default:
                break;
        }
    }
    //旧字段 地块id-pkId  新字段 地块id-dikuaiId
    private void getProject() {

        HttpRequest.get(activity).url(ServerInterface.AllItem).params("pkId", "1").doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    ProjectListBean bean = new Gson().fromJson(result.toString(), ProjectListBean.class);
                    if (bean != null) {
                        if (bean.code == 0) {
                            if (listEmpty(bean.list)) {
                                listBeans = bean.list;
                                data1 = new ArrayList<>();
                                for (int i = 0; i < bean.list.size(); i++) {
                                    data1.add(new ItemBean(bean.list.get(i).iteamName, "", bean.list.get(i).pkId));
                                }
                                adapter1 = new SelectProjectAdapter(activity, data1, R.layout.scsl_list_item);
                                lv01.setAdapter(adapter1);
                                adapter1.setSelectedById(Constant.parentProjectId);
                                if(Constant.projectId!=0){
                                    tv_select_name.setText(Constant.projectName);
                                    tvNoBD.setVisibility(View.GONE);
                                    lv02.setVisibility(View.VISIBLE);
                                    for (int i = 0; i <bean.list.size(); i++) {
                                        if(bean.list.get(i).pkId  == Constant.parentProjectId){
                                            childsBeans = bean.list.get(i).childs;
                                            real_estateName = Constant.parentProjectName;
                                            real_estateId = Constant.parentProjectId;
                                        }
                                    }
                                    data2 = new ArrayList<>();
                                    for (int i = 0; i < childsBeans.size(); i++) {
                                        data2.add(new ItemBean(childsBeans.get(i).iteamName, "", childsBeans.get(i).pkId));
                                    }
                                    adapter2 = new SelectProjectAdapter(activity, data2, R.layout.scsl_list_item);
                                    lv02.setAdapter(adapter2);
                                    adapter2.setSelectedById(Constant.projectId);
                                }else{
                                    if (listEmpty(bean.list.get(0).childs)) {
                                        tv_select_name.setText(bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName);
                                        Constant.projectName = bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName;
                                        Constant.parentProjectName = bean.list.get(0).iteamName;
                                        Constant.parentProjectId = bean.list.get(0).pkId;
                                        Constant.projectId = bean.list.get(0).childs.get(0).pkId;
                                    } else {
                                        tv_select_name.setText(bean.list.get(0).iteamName);
                                        Constant.projectName = bean.list.get(0).iteamName;
                                        Constant.parentProjectName = bean.list.get(0).iteamName;
                                        Constant.parentProjectId = bean.list.get(0).pkId;
                                    }
                                }
                            } else {
                                tv_nodata.setVisibility(View.VISIBLE);
                                ll_list_window.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        tv_nodata.setVisibility(View.VISIBLE);
                        ll_list_window.setVisibility(View.GONE);
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(activity, msg);
            }
        });
    }
   public void setonDiKuaiChangeListener(DiKuaiChangeListener diKuaiChangeListener,String dikuai){
        diKuaiChangeListener.onDiKuaiChanged(dikuai);
    }

}
