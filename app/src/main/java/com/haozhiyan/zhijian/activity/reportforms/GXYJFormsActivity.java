package com.haozhiyan.zhijian.activity.reportforms;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.reportforms.adapter.FormGXYJAdapter;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormGXYJResult;
import com.haozhiyan.zhijian.bean.BaseBean;
import com.haozhiyan.zhijian.listener.HttpObjectCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;

import java.util.List;

/**
 * 报表-工序移交首页
 */
public class GXYJFormsActivity extends BaseFormsActivity {
    private RecyclerView recyclerView;
    private FormGXYJAdapter formGXYJAdapter;

    @Override
    protected void initView() {
        super.initView();
        setTitleText("工序移交");
        recyclerView = getOutView(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_gxyjforms;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        getDataFromServer();

    }

    @Override
    public void onDiKuaiChanged(String dikuai) {
        getDataFromServer();
    }

    private void getDataFromServer() {

        showLoadView("");
        HttpRequest.get(this).url(ServerInterface.listTowerUnit)
                .params("dikuaiId", Constant.projectId)
                .doGet(new HttpObjectCallBack<FormGXYJResult>(FormGXYJResult.class) {
                    @Override
                    public void onSuccess(BaseBean<FormGXYJResult> result) {
                        hideLoadView();
                        if (TextUtils.equals("0", result.code)) {
                            if (ListUtils.listEmpty(result.list)) {
                                List<FormGXYJResult> listBeans = result.list;
                                formGXYJAdapter = new FormGXYJAdapter(activity, listBeans);
                                recyclerView.setAdapter(formGXYJAdapter);
                            }
                        } else {
                            ToastUtils.myToast(activity, result.msg);
                        }
                    }

                    @Override
                    public void onFailure(int code, String msg) {
                        hideLoadView();
                    }
                });
    }

}
