package com.haozhiyan.zhijian.fragment.xcjcFragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.OnSiteInspectionDetails;
import com.haozhiyan.zhijian.adapter.JCCommitAdapter;
import com.haozhiyan.zhijian.bean.JCCommitBean;
import com.haozhiyan.zhijian.fragment.BaseFragment;
import com.haozhiyan.zhijian.model.Constant;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.widget.MyDividerItem;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/26.
 * Describe: 已提交
 */
public class Xcjc_commit_Fragment extends BaseFragment {

    private RecyclerView rvDataList;
    private JCCommitAdapter jcCommitAdapter;
    private List<JCCommitBean.DataBean.ProblemListBean> list;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_layout;
    }

    @Override
    public void initView(View view) {
        rvDataList = getOutView(view, R.id.rv_dataList);
        rvDataList.setLayoutManager(new LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false));
        rvDataList.addItemDecoration(new MyDividerItem(ctx, MyDividerItem.VERTICAL));
    }

    @Override
    public void initListener() {
        jcCommitAdapter = new JCCommitAdapter(R.layout.jian_cha_commit_item, null, ctx);
        rvDataList.setAdapter(jcCommitAdapter);
        setEmpty();
        jcCommitAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (listEmpty(list)) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), OnSiteInspectionDetails.class);
                    intent.putExtra("id", list.get(position).id + "");
                    intent.putExtra("inspectionName", list.get(position).inspectionName);
//                    intent.putExtra("sectionId", list.get(position).sectionId);
                    startActivityForResult(intent,Constant.PROBLEM_DETAIL_RESULT_CODE);

//                    Bundle bundle = new Bundle();
//                    bundle.putString("id", list.get(position).id + "");
//                    bundle.putString("batchId", list.get(position).batchId + "");
//                    bundle.putString("submitToRectifyTag", list.get(position).submitTorectifyTag);
//                    bundle.putString("submitToReviewTag", list.get(position).submitToreviewTag);
//                    switch (list.get(position).state) {
//                        case "1":
//                            jumpActivityForResult(XCJCTroubleDetail.class, Constant.PROBLEM_DETAIL_RESULT_CODE, bundle);
//                            break;
//                        case "2":
//                            jumpActivityForResult(XCJCTroubleDFYDetail.class, Constant.PROBLEM_DETAIL_RESULT_CODE, bundle);
//                            break;
//                        case "3":
//                            jumpActivityForResult(XCJCTroubleCloseDetail.class, Constant.PROBLEM_DETAIL_RESULT_CODE, bundle);
//                            break;
//                        case "4":
//                            jumpActivityForResult(XCJCTroubleTongGuoDetail.class, Constant.PROBLEM_DETAIL_RESULT_CODE, bundle);
//                            break;
//                        default:
//                            break;
//                    }
                }
            }
        });

    }

    @Override
    public void initData(boolean isNetWork) {

    }

    @Override
    protected void lazyLoad() {

    }

    public void setEmpty() {
        try {
            jcCommitAdapter.setNewData(null);
            jcCommitAdapter.setEmptyView(emptyView);
            jcCommitAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setJCData(JSONArray problemList) {
        list = new ArrayList<>();
        for (int i = 0; i < problemList.length(); i++) {
            JCCommitBean.DataBean.ProblemListBean bean = new JCCommitBean.DataBean.ProblemListBean();
            bean.batchId = problemList.optJSONObject(i).optString("batchId");
            bean.id = problemList.optJSONObject(i).optInt("id");
            bean.inspectionName = problemList.optJSONObject(i).optString("inspectionName");
            if (StringUtils.isEmpty(problemList.optJSONObject(i).optString("particularsName"))) {
                bean.particularsName = "";
            } else {
                bean.particularsName = problemList.optJSONObject(i).optString("particularsName");
            }
            if (StringUtils.isEmpty(problemList.optJSONObject(i).optString("particularsSupplement"))) {
                bean.particularsSupplement = " ";
            } else {
                bean.particularsSupplement = problemList.optJSONObject(i).optString("particularsSupplement");
            }
            bean.problemImage = problemList.optJSONObject(i).optString("problemImage");
            bean.towerName = problemList.optJSONObject(i).optString("towerName");
            bean.unitName = problemList.optJSONObject(i).optString("unitName");
            bean.roomName = problemList.optJSONObject(i).optInt("roomName");
            bean.state = problemList.optJSONObject(i).optString("state");
            bean.submitDate = problemList.optJSONObject(i).optString("submitDate");
            bean.floorName = problemList.optJSONObject(i).optString("floorName");
            bean.serious = problemList.optJSONObject(i).optString("serious");
            bean.submit = problemList.optJSONObject(i).optString("submit");
            bean.rectify = problemList.optJSONObject(i).optString("rectify");
            bean.submitTorectifyTag = problemList.optJSONObject(i).optString("submitTorectifyTag");
            bean.submitToreviewTag = problemList.optJSONObject(i).optString("submitToreviewTag");
            list.add(bean);
        }
        jcCommitAdapter.setNewData(list);
        jcCommitAdapter.notifyDataSetChanged();
    }
}
