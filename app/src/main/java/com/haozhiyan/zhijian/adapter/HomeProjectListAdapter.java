package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.HomeProjectListBean;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.DensityUtil;
import com.haozhiyan.zhijian.utils.ToastUtils;

/**
 * 首页的项目列表
 */
public class HomeProjectListAdapter extends BaseQuickAdapter<HomeProjectListBean.DataBean, BaseViewHolder> {

    private Context context;

    public HomeProjectListAdapter(Context context) {
        super(R.layout.homeprojectlistitem);
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder holder, final HomeProjectListBean.DataBean item) {
        final ImageView imageView = holder.getView(R.id.img);
        TextView textView = holder.getView(R.id.tv);
        final RecyclerView childRcv = holder.getView(R.id.childRcv);

        textView.setText(item.getProject());
        AnimationUtil.getInstance().animationIvOpenClockwise(imageView, -90);

        HomeProjectListChildAdapter childAdapter = new HomeProjectListChildAdapter();
        childAdapter.setNewData(item.getList());
        childRcv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        childRcv.setAdapter(childAdapter);
        childAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.myToast((Activity) context, item.getList().get(position).getId());
            }
        });


        holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (childRcv.getVisibility() == View.GONE) {
                    int childheight = (int) (item.getList().size() * (DensityUtil.dip2px(context, 50) + 1.3));//child 数量 * 每个的高度
                    AnimationUtil.getInstance().animateOpen(childRcv, childheight);
                    AnimationUtil.getInstance().animationIvCloseAntiClockwise(imageView, -90);
                } else {
                    AnimationUtil.getInstance().animateClose(childRcv);
                    AnimationUtil.getInstance().animationIvOpenClockwise(imageView, -90);
                }
            }
        });

    }
}
