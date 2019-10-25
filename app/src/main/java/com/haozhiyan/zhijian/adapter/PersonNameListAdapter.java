package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.AMEnterAreaBean;
import com.haozhiyan.zhijian.bean.AMExitInfoBean;
import com.haozhiyan.zhijian.bean.GXYJDetailsBean;
import com.haozhiyan.zhijian.bean.GXYJProblemDetailBean;
import com.haozhiyan.zhijian.bean.MaterialsTaskBean;
import com.haozhiyan.zhijian.bean.ProblemDetailBean;
import com.haozhiyan.zhijian.listener.TalkClickLinstener;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.SystemUtils;

public class PersonNameListAdapter extends BaseQuickAdapter<Object, BaseViewHolder> implements TalkClickLinstener {
    private Context context;

    public PersonNameListAdapter(Context context) {
        super(R.layout.personname_list_item);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        if (item instanceof ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean) {
            final ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean ListBean = (ProblemDetailBean.XcjcProblemBean.XcjcReviewListBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof ProblemDetailBean.XcjcProblemBean.CcListBean) {
            final ProblemDetailBean.XcjcProblemBean.CcListBean ListBean = (ProblemDetailBean.XcjcProblemBean.CcListBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof GXYJDetailsBean.ChildPBean) {
            final GXYJDetailsBean.ChildPBean ListBean = (GXYJDetailsBean.ChildPBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
            helper.addOnClickListener(R.id.talkImg);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof MaterialsTaskBean.ClysTaskBean.AcceptanceListBean) {
            final MaterialsTaskBean.ClysTaskBean.AcceptanceListBean ListBean = (MaterialsTaskBean.ClysTaskBean.AcceptanceListBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof MaterialsTaskBean.ClysTaskBean.CcListBean) {
            final MaterialsTaskBean.ClysTaskBean.CcListBean ListBean = (MaterialsTaskBean.ClysTaskBean.CcListBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof GXYJProblemDetailBean.ListChaosongPeopleBean) {
            final GXYJProblemDetailBean.ListChaosongPeopleBean ListBean = (GXYJProblemDetailBean.ListChaosongPeopleBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof GXYJProblemDetailBean.ListFuyanPeopleBean) {
            final GXYJProblemDetailBean.ListFuyanPeopleBean ListBean = (GXYJProblemDetailBean.ListFuyanPeopleBean) item;
            helper.setText(R.id.Name, ListBean.peopleuser);
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof String) {
            if (item.equals("选填") || item.toString().equals("无")) {
                helper.getView(R.id.talkImg).setVisibility(View.GONE);
                helper.setTextColor(R.id.Name, Color.parseColor("#666666"));
            } else {
                helper.getView(R.id.talkImg).setVisibility(View.VISIBLE);
                helper.setTextColor(R.id.Name, Color.parseColor("#232323"));
            }
            helper.setText(R.id.Name, item.toString());
            helper.addOnClickListener(R.id.talkImg);
        } else if (item instanceof AMExitInfoBean.ClysExit.CcListBean) {
            final AMExitInfoBean.ClysExit.CcListBean ListBean = (AMExitInfoBean.ClysExit.CcListBean) item;
            helper.setText(R.id.Name, ListBean.getPeopleuser());
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.getTel());
                }
            });
        } else if (item instanceof AMExitInfoBean.ClysExit.SupervisorListBean) {
            final AMExitInfoBean.ClysExit.SupervisorListBean ListBean = (AMExitInfoBean.ClysExit.SupervisorListBean) item;
            helper.setText(R.id.Name, ListBean.getPeopleuser());
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.getTel());
                }
            });
        } else if (item instanceof AMEnterAreaBean.ApproachBean.CcListBean) {
            final AMEnterAreaBean.ApproachBean.CcListBean ListBean = (AMEnterAreaBean.ApproachBean.CcListBean) item;
            helper.setText(R.id.Name, ListBean.getPeopleuser());
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        } else if (item instanceof AMEnterAreaBean.ApproachBean.AcceptanceListBean) {
            final AMEnterAreaBean.ApproachBean.AcceptanceListBean ListBean
                    = (AMEnterAreaBean.ApproachBean.AcceptanceListBean) item;
            if ("0".equals(ListBean.isQualified)) {
                helper.setText(R.id.isQualifiedTV, "不合格");
            } else if ("1".equals(ListBean.isQualified)) {
                helper.setText(R.id.isQualifiedTV, "合格");
            } else {
                helper.setText(R.id.isQualifiedTV, "尚未验收");
            }
            helper.setText(R.id.Name, ListBean.getPeopleuser());
//            helper.addOnClickListener(R.id.talkImg);
            helper.getView(R.id.talkImg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onTalkClick(ListBean.tel);
                }
            });
        }
    }

    @Override
    public void onTalkClick(String tel) {
        LogUtils.d("    tel=    ", tel+"");
        try {
            SystemUtils.callPage(tel, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
