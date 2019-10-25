package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.JianChaItemSelectAdapter;
import com.haozhiyan.zhijian.bean.JCItem1Level;
import com.haozhiyan.zhijian.bean.JCItem2Level;
import com.haozhiyan.zhijian.bean.JCItem3Level;
import com.haozhiyan.zhijian.bean.JCItemBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

public class JianChaItemSelectActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.iv_left_icon)
    ImageView ivRightIcon;
    @ViewInject(R.id.et_search)
    EditText et_search;
    @ViewInject(R.id.iv_search)
    ImageView ivSearch;
    @ViewInject(R.id.tv_search_function)
    TextView tvSearchFunction;
    @ViewInject(R.id.jcIS_list)
    RecyclerView jcISList;
    private ArrayList<MultiItemEntity> list;
    private JianChaItemSelectAdapter adapter;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jian_cha_item_select;
    }

    @Override
    protected void initView() {
        tv_title.setText("选择检查项");
        ivRightIcon.setVisibility(View.VISIBLE);
        ivRightIcon.setImageResource(R.drawable.icon_gantan_img);
        jcISList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initListener() {
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    ivSearch.setVisibility(View.VISIBLE);
                    tvSearchFunction.setVisibility(View.GONE);
                } else {
                    tvSearchFunction.setVisibility(View.VISIBLE);
                    ivSearch.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        adapter = new JianChaItemSelectAdapter(null);
        jcISList.setAdapter(adapter);
        //adapter.expandAll();
        adapter.setOnItemSonClickListener(new JianChaItemSelectAdapter.OnItemSonClickListener() {
            @Override
            public void lvContent(String str01, String str02, String inspectionIds, String descId, long inspectionId) {
                Intent intent = new Intent();
                intent.putExtra("buWei01", str01);
                intent.putExtra("buWei02", str02);
                intent.putExtra("particularsId", descId);
                intent.putExtra("inspectionIds", inspectionIds);
                intent.putExtra("inspectionId", inspectionId);
                LogUtils.i("xcjcParticularsId====", descId);
                setResult(Constant.REQUEST_CODE, intent);
                finish();
            }

            @Override
            public void lvTwoContent(String str01, String str02, String inspectionIds, long inspectionId) {
                Intent intent = new Intent();
                intent.putExtra("buWei01", str01 + str02);
                intent.putExtra("inspectionIds", inspectionIds);
                intent.putExtra("inspectionId", inspectionId);
                setResult(Constant.REQUEST_CODE, intent);
                finish();
            }
        });
        getJCItem();
    }

    @OnClick({R.id.rl_back, R.id.tv_search_function, R.id.iv_left_icon})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_search_function:
                tvSearchFunction.setVisibility(View.GONE);
                et_search.setText("");
                break;
            case R.id.iv_left_icon:
                jumpToActivity(JianChaGuideActivity.class);
                break;
            default:
                break;
        }
    }

    private void getJCItem() {
        HttpRequest.get(this).url(ServerInterface.inspectionWtms).doGet(new HttpStringCallBack() {

            @Override
            public void onSuccess(Object result) {
                JCItemBean jcItemBean = new Gson().fromJson(result.toString(), JCItemBean.class);
                if (jcItemBean != null) {
                    if (listEmpty(jcItemBean.list)) {
                        list = generateData(jcItemBean.list);
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

    private ArrayList<MultiItemEntity> generateData(List<JCItemBean.ListBean> list) {
        ArrayList<MultiItemEntity> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            JCItem1Level lv1 = new JCItem1Level(list.get(i).inspectionName, list.get(i).identifying, list.get(i).inspectionId, list.get(i).parentId);
            if (listEmpty(list.get(i).childIns)) {
                for (int j = 0; j < list.get(i).childIns.size(); j++) {
                    JCItem2Level lv2 = new JCItem2Level(list.get(i).childIns.get(j).inspectionName, list.get(i).childIns.get(j).identifying, list.get(i).childIns.get(j).inspectionId, list.get(i).childIns.get(j).parentId);
                    if (listEmpty(list.get(i).childIns.get(j).childParticulars)) {
                        for (int k = 0; k < list.get(i).childIns.get(j).childParticulars.size(); k++) {
                            lv2.addSubItem(new JCItem3Level(list.get(i).childIns.get(j).childParticulars.get(k).xcjcParticularsId, list.get(i).childIns.get(j).childParticulars.get(k).particularsName));
                        }
                        lv1.addSubItem(lv2);
                    } else {
                        lv1.addSubItem(lv2);
                    }
                }
                res.add(lv1);
            }
        }
        return res;
    }
}
