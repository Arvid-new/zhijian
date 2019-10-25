package com.haozhiyan.zhijian.fragment.gxyjfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.gxyj.GXYJProblemDetails;
import com.haozhiyan.zhijian.activity.gxyj.ProcessOver;
import com.haozhiyan.zhijian.activity.workXcjc.AddTroubleActivity;
import com.haozhiyan.zhijian.adapter.ZhengGaiProblemAdapter;
import com.haozhiyan.zhijian.bean.NewZhengGaiListBean;
import com.haozhiyan.zhijian.bean.ProcessOverUseIdBean;
import com.haozhiyan.zhijian.bean.XcjcSeverityProblem;
import com.haozhiyan.zhijian.listener.HttpStringCallBack;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.model.ServerInterface;
import com.haozhiyan.zhijian.utils.HttpRequest;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SPUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.utils.ToastUtils;
import com.haozhiyan.zhijian.utils.UiUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 整改问题
 */
public class ZhengGaiProblemFragment extends Fragment implements View.OnClickListener {

    private RecyclerView zhenggaiRcv;
    private Button addProblemBT;
    private ZhengGaiProblemAdapter adapter;
    private List<NewZhengGaiListBean.ListBean.ListAbarbeitungBean> listBean;
    private List<XcjcSeverityProblem> severityProblems;
    public ProcessOverUseIdBean useIdBean;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zheng_gai_problem, container, false);
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        initView(view);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        if ("changedGXYJTask".equals(event)) {
            getData(useIdBean.keyId);
        } else if ("GXYJTaskStateChangedidentifying".equals(event)) {
            useIdBean.identifying = "待验收";
            if ("空".equals(useIdBean.identifying)) {
                addProblemBT.setBackgroundResource(R.drawable.gray_shape);
                addProblemBT.setTextColor(UiUtils.getColor(R.color.gray_back));
            } else {
                addProblemBT.setBackgroundResource(R.drawable.lightbluetodeepblue_radius50);
                addProblemBT.setTextColor(UiUtils.getColor(R.color.white));
                addProblemBT.setOnClickListener(this);
            }

        } else if ("gxyj_draft".equals(event)) {
            getData(useIdBean.keyId);
        }
    }

    private void initView(View view) {
        zhenggaiRcv = view.findViewById(R.id.zhenggaiRcv);
        addProblemBT = view.findViewById(R.id.addProblemBT);
        adapter = new ZhengGaiProblemAdapter(getActivity(), null);
        zhenggaiRcv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        zhenggaiRcv.setAdapter(adapter);
        try {
            if ("空".equals(useIdBean.identifying)) {
                addProblemBT.setBackgroundResource(R.drawable.gray_shape);
                addProblemBT.setTextColor(UiUtils.getColor(R.color.gray_back));
            } else {
                addProblemBT.setBackgroundResource(R.drawable.lightbluetodeepblue_radius50);
                addProblemBT.setTextColor(UiUtils.getColor(R.color.white));
                addProblemBT.setOnClickListener(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (ListUtils.listEmpty(listBean)) {
                    if ("草稿".equals(listBean.get(position).ztCondition)) {
//                        Intent intent = new Intent(getActivity(), XCJCTroubleEdit.class);
                        Intent intent = new Intent(getActivity(), AddTroubleActivity.class);
//                        Bundle bundle = new Bundle();
                        intent.putExtra("position", position);
                        intent.putExtra("troubleBean", listBean.get(position));
                        intent.putExtra("sectionId", useIdBean.sectionId);
                        intent.putExtra("towerName", useIdBean.towerName);
                        intent.putExtra("unitName", useIdBean.unitName);
                        intent.putExtra("floorName", useIdBean.floorName);
                        intent.putExtra("keyId", useIdBean.keyId);
                        intent.putExtra("towerType", useIdBean.partsDivision);
                        intent.putExtra("pieceType", "gxYj");
                        intent.putExtra("iscaogao", true);
//                        intent.putExtra("data", bundle);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), GXYJProblemDetails.class);
                        intent.putExtra("id", listBean.get(position).id + "");
                        intent.putExtra("sectionId", useIdBean.sectionId + "");
                        startActivity(intent);
                    }
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        int vid = v.getId();
        switch (vid) {
            case R.id.addProblemBT:
                Intent intent = new Intent(getContext(), AddTroubleActivity.class);
                intent.putExtra("keyId", useIdBean.keyId);
                intent.putExtra("sectionId", useIdBean.sectionId);
                intent.putExtra("sectionName", useIdBean.sectionName);
                intent.putExtra("detailsName", useIdBean.detailsName);
                intent.putExtra("inspectionName", useIdBean.inspectionName);
                intent.putExtra("inspectionId", useIdBean.inspectionId);
                intent.putExtra("pieceType", "gxYj");
                intent.putExtra("towerType", useIdBean.partsDivision);
                intent.putExtra("towerId", useIdBean.towerId);
                intent.putExtra("unitId", useIdBean.unitId);
                intent.putExtra("floorId", useIdBean.floorId);
                intent.putExtra("roomId", useIdBean.roomId);
                intent.putExtra("towerName", useIdBean.towerName);
                intent.putExtra("unitName", useIdBean.unitName);
                intent.putExtra("severityProblems", (Serializable) severityProblems);
                startActivityForResult(intent, Constant.ADD_PROBLEM_RESULT_CODE);
                break;
        }
    }

    private void changeProblemNum(int num) {
        List<String> titles = new ArrayList<>();
        titles.add("工序信息");
        titles.add("整改问题(" + num + ")");
        ((ProcessOver) getActivity()).refreshFragment(titles);
    }

    private void getData(String id) {
        LogUtils.i("请求id==", useIdBean.keyId);
        HttpRequest.get(getActivity())
                .url(ServerInterface.gxyjProblemList)
                .params("id", id)
                .doPost(new HttpStringCallBack() {
                    @Override
                    public void onSuccess(Object result) {
                        LogUtils.i("json-zgwt===", result.toString());
                        try {
                            NewZhengGaiListBean zhengGaiListBean = new Gson().fromJson(result.toString(), NewZhengGaiListBean.class);
                            List<NewZhengGaiListBean.ListBean.ListAbarbeitungBean> listBeans = zhengGaiListBean.list.listAbarbeitung;
                            if (zhengGaiListBean.code == 0) {
                                if (zhengGaiListBean.list != null) {
                                    if (ListUtils.listEmpty(listBeans)) {
                                        listBean = listBeans;
                                        changeProblemNum(listBeans.size());
                                        adapter.setNewData(listBeans);
                                        adapter.notifyDataSetChanged();
                                    } else {
                                        changeProblemNum(0);
                                    }
                                    List<NewZhengGaiListBean.ListBean.SeverityproblemBean> beanList = zhengGaiListBean.list.severityproblem;
                                    if (ListUtils.listEmpty(beanList)) {
                                        severityProblems = new ArrayList<>();
                                        for (int i = 0; i < beanList.size(); i++) {
                                            severityProblems.add(new XcjcSeverityProblem(beanList.get(i).severityId, beanList.get(i).severityName));
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onFailure(int code, String msg) {
                        ToastUtils.myToast(getActivity(), msg);
                    }
                });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (StringUtils.isEmpty(useIdBean.keyId))
                useIdBean.keyId = SPUtil.get(getContext()).get("gxyjTaskID");
            getData(useIdBean.keyId);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constant.ADD_PROBLEM_RESULT_CODE) {
            getData(useIdBean.keyId);
        }
    }

}
