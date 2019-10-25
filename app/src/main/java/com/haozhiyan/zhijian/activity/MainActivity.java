package com.haozhiyan.zhijian.activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ProjectListBean;
import com.haozhiyan.zhijian.fragment.DaiBanFragment;
import com.haozhiyan.zhijian.fragment.FormsFragment;
import com.haozhiyan.zhijian.fragment.HomeFragment;
import com.haozhiyan.zhijian.fragment.MeFragment;
import com.haozhiyan.zhijian.fragment.WorkFragment;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 主类
 */
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {

    @ViewInject(R.id.bottomNavigationBar)
    BottomNavigationBar bottomNavigationBar;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;
    private FormsFragment formsFragment;
    private DaiBanFragment daiBanFragment;
    private WorkFragment workFragment;
    private MeFragment meFragment;
    private String userType;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        if (userInfo.getLoginStatus()) {
            userType = userInfo.getUserType();
            fragmentManager = getSupportFragmentManager();
            bottomNavigationBar.clearAll();
            if (userType.equals("0")) {//Admin
                bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.tab_01_selector, "首页"))
                        .addItem(new BottomNavigationItem(R.drawable.tab_02_selector, "报表"))
                        .addItem(new BottomNavigationItem(R.drawable.tab_03_selector, "工作台"))
                        .addItem(new BottomNavigationItem(R.drawable.tab_05_selector, "我"))
                        .setActiveColor("#6298FC")
                        .setFirstSelectedPosition(0)
                        .setBarBackgroundColor(R.color.white)
                        .initialise();
                initHomeFragment();
            } else if (userType.equals("1") || userType.equals("2") || userType.equals("3")) {//甲方和乙方、监理一样三个模块
                bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
                bottomNavigationBar.refreshDrawableState();
                bottomNavigationBar
                        .addItem(new BottomNavigationItem(R.drawable.tab_03_selector, "待办"))
                        .addItem(new BottomNavigationItem(R.drawable.tab_04_selector, "工作台"))
                        .addItem(new BottomNavigationItem(R.drawable.tab_05_selector, "我"))
                        .setActiveColor("#6298FC")
                        .setFirstSelectedPosition(0)
                        .setBarBackgroundColor(R.color.white)
                        .initialise();
                initDaiBanFragment();
            }
            bottomNavigationBar.setTabSelectedListener(this);
        } else {
            finish();
            jumpToActivity(LoginActivity.class);
        }
    }

    private void setDefaultFragment() {
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.layFrame, new HomeFragment());
        transaction.commit();
    }

    @Override
    protected void initData(boolean isNetWork) {
        if (userInfo.getLoginStatus()) {
            getProject();//获取项目
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onTabSelected(int position) {
        if (userType.equals("0")) {
            switch (position) {
                case 0:
                    initHomeFragment();
                    break;
                case 1:
                    initFormsFragment();
                    break;
                case 2:
                    initWorkFragment();
                    break;
                case 3:
                    initMeFragment();
                    break;
                default:
                    break;
            }
        } else {
            switch (position) {
                case 0:
                    initDaiBanFragment();
                    break;
                case 1:
                    initWorkFragment();
                    break;
                case 2:
                    initMeFragment();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    private void initHomeFragment() {
        transaction = fragmentManager.beginTransaction();
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
            transaction.add(R.id.layFrame, homeFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示首页fragment
        transaction.show(homeFragment);
        transaction.commit();
    }

    private void initFormsFragment() {
        transaction = fragmentManager.beginTransaction();
        if (formsFragment == null) {
            formsFragment = new FormsFragment();
            transaction.add(R.id.layFrame, formsFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示报表fragment
        transaction.show(formsFragment);
        transaction.commit();
    }

    private void initDaiBanFragment() {
        transaction = fragmentManager.beginTransaction();
        if (daiBanFragment == null) {
            daiBanFragment = new DaiBanFragment();
            transaction.add(R.id.layFrame, daiBanFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示待办fragment
        transaction.show(daiBanFragment);
        transaction.commit();
    }

    private void initWorkFragment() {
        transaction = fragmentManager.beginTransaction();
        if (workFragment == null) {
            workFragment = new WorkFragment();
            transaction.add(R.id.layFrame, workFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示工作台fragment
        transaction.show(workFragment);
        transaction.commit();
    }

    private void initMeFragment() {
        transaction = fragmentManager.beginTransaction();
        if (meFragment == null) {
            meFragment = new MeFragment();
            transaction.add(R.id.layFrame, meFragment);
        }
        //隐藏所有fragment
        hideFragment(transaction);
        //显示我的fragment
        transaction.show(meFragment);
        transaction.commit();
    }


    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (formsFragment != null) {
            transaction.hide(formsFragment);
        }
        if (daiBanFragment != null) {
            transaction.hide(daiBanFragment);
        }
        if (workFragment != null) {
            transaction.hide(workFragment);
        }
        if (meFragment != null) {
            transaction.hide(meFragment);
        }
    }

    private void getProject() {

        HttpRequest.get(this).url(ServerInterface.AllItem).params("pkId", "1").doPost(new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                try {
                    ProjectListBean bean = new Gson().fromJson(result.toString(), ProjectListBean.class);
                    if (bean != null) {
                        if (bean.code == 0) {
                            if (listEmpty(bean.list)) {
                                String pkId = "";
                                if (listEmpty(bean.list.get(0).childs)) {
                                    pkId = bean.list.get(0).childs.get(0).pkId + "";
                                    Constant.projectId = bean.list.get(0).childs.get(0).pkId;
                                    Constant.projectName = bean.list.get(0).iteamName + bean.list.get(0).childs.get(0).iteamName;
                                    Constant.parentProjectId = bean.list.get(0).pkId;
                                    Constant.parentProjectName = bean.list.get(0).iteamName;
                                    Constant.diKuaiName = bean.list.get(0).childs.get(0).iteamName;
                                } else {
                                    Constant.projectName = bean.list.get(0).iteamName;
                                    Constant.parentProjectId = bean.list.get(0).pkId;
                                    Constant.parentProjectName = bean.list.get(0).iteamName;
                                }
//                                SPUtil.get(getBaseContext()).savePKID(pkId);
                                EventBus.getDefault().post(Constant.REFRESH_DAIBAN);
                            }

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                ToastUtils.myToast(act, "项目获取错误");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* try {
            if (0 != PushReceiver.count) {
                //角标清空
                PushReceiver.count = 0;
                AppShortCutUtil.setCount(PushReceiver.count, MainActivity.this);
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(!TextUtils.isEmpty(intent.getStringExtra("selection"))){
            if(bottomNavigationBar!=null)
                bottomNavigationBar.selectTab(0);
        }
    }
}
