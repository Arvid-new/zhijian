package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.SelectPeopleAdapter;
import com.haozhiyan.zhijian.bean.SelectPerson;
import com.haozhiyan.zhijian.bean.SelectPersonTitle;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.listener.MyItemClickListener;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectAngle extends BaseActivity implements MyItemClickListener{
    @ViewInject(R.id.searchET)
    private EditText searchET;
    @ViewInject(R.id.tv_title)
    private TextView tv_title;
    @ViewInject(R.id.tv_right)
    private TextView tvRight;
    @ViewInject(R.id.tv_hasAngle)
    private EditText tvHasAngle;
    @ViewInject(R.id.ll_angle)
    private LinearLayout llAngle;
    @ViewInject(R.id.ll_content)
    private LinearLayout ll_content;
    @ViewInject(R.id.rlv_angleList)
    private RecyclerView rlvAngleList;
    private String selectId = "";
    private SelectPeopleAdapter adapter;
    private Bundle bundle;
    private List<String> selectIds = new ArrayList<>();
    private List<String> selectNames = new ArrayList<>();
    ArrayList<MultiItemEntity> listDatas = new ArrayList<>();
    ArrayList<MultiItemEntity> searchList = new ArrayList<>();;
    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_select_angle;
    }

    @Override
    protected void initView() {
        tv_title.setText(StringUtils.title());
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("确定(" + 0 + ")");
        llAngle.setVisibility(View.GONE);
        bundle = getIntent().getBundleExtra("data");

        selectId = bundle == null ? "" : bundle.getString("id");
        if(!TextUtils.isEmpty(selectId)){
            if (selectId.contains(",")) {
                selectIds = Arrays.asList(selectId.split(","));
            } else {
                selectIds = new ArrayList<>();
                if (!TextUtils.isEmpty(selectId))
                    selectIds.add(selectId);
            }
        }
    }

    @Override
    protected void initListener() {
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //ll_content.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchList.clear();
                List<Integer> expandItem = new ArrayList<>();
                for (int i = 0; i < listDatas.size(); i++) {
                    if(listDatas.get(i) instanceof  SelectPersonTitle){
                        SelectPersonTitle lv1 = (SelectPersonTitle) listDatas.get(i);
                        List<SelectPerson> subItems = lv1.getSubItems();
                        SelectPersonTitle title = new SelectPersonTitle();
                        for (int j = 0; j <lv1.getSubItems().size() ; j++) {
                            if(!TextUtils.isEmpty(subItems.get(j).peopleuser)){
                                if(subItems.get(j).peopleuser.contains(s.toString())){
                                    if (!expandItem.contains(i)) {
                                        expandItem.add(i);
                                    }
                                    title.roleId = lv1.roleId;
                                    title.roleName = lv1.roleName;
                                    title.addSubItem(subItems.get(j));
                                    if(!searchList.contains(title)){
                                        searchList.add(title);
                                    }
                                }
                            }
                        }
                    }
                }
                if(s.length()==0){
                    adapter.setNewData(listDatas);
                }else{
                    adapter.setNewData(searchList);
                    adapter.expandAll();
                }
            }
        });
    }

    @Override
    protected void initData(boolean isNetWork) {
        rlvAngleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        getPeople("");
    }

    @OnClick({R.id.rl_back, R.id.tv_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_right:
                setResult();
                break;
            default:
                break;
        }
    }
    private  void setResult(){
        try {
            List<String> selectIDs = adapter.getSelectIDs();
            List<String> selectNames = adapter.getSelectNames();
            StringBuffer names = new StringBuffer();
            StringBuffer ids = new StringBuffer();
            for (int i = 0; i < selectIDs.size(); i++) {
                if (names.length() > 0) {
                    names.append(",");
                    ids.append(",");
                }
                names.append(selectNames.get(i));
                ids.append(selectIDs.get(i));
            }
           // if (ids.length() > 0) {
                Intent intent = new Intent();
                intent.putExtra("selectType", names.toString());
                intent.putExtra("selectId", ids.toString());
                setResult(Constant.REQUEST_CODE, intent);
                finish();
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取人员列表
     */
    private void getPeople(final String name) {
        HttpRequest.get(this)
                .url(ServerInterface.listRoleUser)
                .params("pkId",Constant.projectId)
                .params("peopleuser", name)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        try {
                            JSONObject jsonObject = new JSONObject(result.toString());
                            if (jsonObject.optInt("code") == 0) {
                                JSONArray array = jsonObject.optJSONArray("list");
                                listDatas.clear();
                                List<Integer> expandItem = new ArrayList<>();
                                StringBuffer names = new StringBuffer();
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.optJSONObject(i);
                                    SelectPersonTitle lv1 = new SelectPersonTitle();
                                    lv1.roleId = object.optInt("roleId");
                                    lv1.roleName = object.optString("roleName");
                                    JSONArray childIns = object.optJSONArray("childIns");
                                    for (int j = 0; j < childIns.length(); j++) {
                                        SelectPerson person = new SelectPerson();
                                        JSONObject object2 = childIns.optJSONObject(j);
                                        person.userId = object2.optInt("userId");
                                        person.peopleuser = object2.optString("peopleuser");
                                        person.mobile = object2.optString("mobile");
                                        person.roleName = object2.optString("roleName");
                                        lv1.addSubItem(person);
                                        if (selectIds.contains(person.userId + "")) {
                                            if (names.length() > 0) {
                                                names.append(",");
                                            }
                                            person.isCheck=true;
                                            names.append(person.peopleuser);
                                            if (!selectNames.contains(person.peopleuser))
                                                selectNames.add(person.peopleuser);
                                            if (!expandItem.contains(i)) {
                                                expandItem.add(i);
                                            }
                                        }
                                    }
                                    listDatas.add(lv1);
                                }
                                if (TextUtils.isEmpty(name) || name.length() <= 0) {
                                    if (names.length() > 0) {
                                        tvRight.setText("确定(" + selectNames.size() + ")");
                                        llAngle.setVisibility(View.VISIBLE);
                                        tvHasAngle.setText("");
                                        tvHasAngle.append(names.toString());
                                    } else {
                                        llAngle.setVisibility(View.GONE);
                                    }
                                }
                                adapter = new SelectPeopleAdapter(listDatas, act,selectIds, selectNames);
                                adapter.setItemClickListener(SelectAngle.this);
                                rlvAngleList.setAdapter(adapter);
                                if (!TextUtils.isEmpty(name) && name.length() > 0) {
                                    adapter.expandAll();
                                } else if (expandItem.size() > 0) {
                                    for (int i = expandItem.size(); i >= 1; i--) {
                                        adapter.expand(expandItem.get(i - 1));
                                    }
                                }

                            } else {
                                ToastUtils.myToast(act, jsonObject.optString("msg"));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(act, msg);
                    }
                });
    }

    @Override
    public void onItemClick(String data, int position) {
        try {
            List<String> list = adapter.getSelectNames();
            selectNames = list;
            tvRight.setText("确定(" + selectNames.size() + ")");
            selectIds = adapter.getSelectIDs();
            StringBuffer names = new StringBuffer();
            for (int i = 0; i < selectNames.size(); i++) {
                if (names.length() > 0) {
                    names.append(",");
                }
                names.append(selectNames.get(i));
            }
            if (names.length() > 0) {
                llAngle.setVisibility(View.VISIBLE);
                tvHasAngle.setText("");
                tvHasAngle.append(names.toString());
            } else {
                llAngle.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
