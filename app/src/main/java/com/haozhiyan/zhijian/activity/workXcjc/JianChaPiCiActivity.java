package com.haozhiyan.zhijian.activity.workXcjc;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.BaseActivity;
import com.haozhiyan.zhijian.adapter.JianChaPiCiAdapter;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.ActivityManager;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SPUtils;
import com.haozhiyan.zhijian.utils.StatusBarUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//检查批次
public class JianChaPiCiActivity extends BaseActivity {

    @ViewInject(R.id.rl_back)
    RelativeLayout rl_back;
    @ViewInject(R.id.tv_title)
    TextView tv_title;
    @ViewInject(R.id.tv_right)
    TextView tv_right;
    @ViewInject(R.id.list)
    ListView myList;
    private List<ItemBean> dataList = new ArrayList<>();
    private JianChaPiCiAdapter adapter;
    private Bundle bundle;
    private int createPiCiTag = 0;
    private String batchId = null;

    @Override
    public void getThemeStyle() {
        StatusBarUtils.setStatus(this, true);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.activity_jian_cha_pi_ci;
    }

    @Override
    protected void initView() {
        tv_title.setText("检查批次");
        tv_right.setText("新建批次");
        bundle = getIntent().getBundleExtra("data");
        tv_right.setVisibility(bundle == null ? setPiCi(false) : setPiCi(true));
    }

    @Override
    protected void initListener() {
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setCheck(position);
                SPUtils.saveInt(act, "index", position);
                Intent intent = new Intent(act, XianChangJianChactivity.class);
                intent.putExtra("name", dataList.get(position).name);
                intent.putExtra("batchId", dataList.get(position).batchId);
                intent.putExtra("sectionId", dataList.get(position).sectionId);
                setResult(Constant.XCJC_PICI_RESULT_CODE, intent);
                ActivityManager.getInstance().removeActivity(act);
            }
        });

    }

    @Override
    protected void initData(boolean isNetWork) {
        getPiciList();
    }

    @OnClick({R.id.rl_back, R.id.tv_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                ActivityManager.getInstance().removeActivity(this);
                break;
            case R.id.tv_right://添加批次
                AddOrEditPiCi.doPiCi(this, Constant.ADD_PICI_RESULT_CODE);
                break;
            default:
                break;
        }
    }

    private int setPiCi(boolean isVisible) {
        if (isVisible) {
            createPiCiTag = bundle.getInt("createPiCiTag", 0);
            batchId = bundle.getString("batchId", null);
            if (createPiCiTag == 0) {
                return View.GONE;
            }
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }

    private void getPiciList() {
        dataList.clear();
        HttpRequest.get(this).url(ServerInterface.queryPici)
                .params("userId", userInfo.getUserId())
                .params("proId", Constant.projectId)
                .doGet(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        //LogUtils.print("批次列表==" + result.toString());
                        try {
                            JSONObject object = new JSONObject(result.toString());
                            if (object != null) {
                                JSONArray list = object.optJSONArray("data");
                                if (arrayEmpty(list)) {
                                    int selectItem = -1;
                                    for (int i = 0; i < list.length(); i++) {
                                        ItemBean bean = new ItemBean(list.optJSONObject(i).optString("batchName"), list.optJSONObject(i).optString("batchId"), list.optJSONObject(i).optString("sectionId"), false);
                                        if (!TextUtils.isEmpty(batchId) && batchId.equals(bean.batchId)) {
                                            selectItem = i;
                                        }
                                        dataList.add(bean);
                                    }
                                    adapter = new JianChaPiCiAdapter(act, dataList, R.layout.pici_list_item);
                                    myList.setAdapter(adapter);
                                    adapter.setCheck(selectItem);
                                    adapter.notifyDataSetChanged();
                                    adapter.setItemClickListener(new JianChaPiCiAdapter.OnChildClickListener() {
                                        @Override
                                        public void onItemChildClickListener(View view, int position) {
                                            if (view.getId() == R.id.tv_detail) {
                                                if (listEmpty(dataList)) {
                                                    Bundle bundle = new Bundle();
                                                    bundle.putString("name", dataList.get(position).name);
                                                    bundle.putString("batchId", dataList.get(position).batchId + "");
                                                    LogUtils.i("batchId===", dataList.get(position).batchId);
                                                    jumpActivityForResult(PiCiDetailActivity.class, Constant.ADD_PICI_RESULT_CODE, bundle);
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.ADD_PICI_RESULT_CODE) {
            getPiciList();
        } else if (requestCode == Constant.EDIT_PICI_RESULT_CODE) {
            getPiciList();
        }
    }

}
