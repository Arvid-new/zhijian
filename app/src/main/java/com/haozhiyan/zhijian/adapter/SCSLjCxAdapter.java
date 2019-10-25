package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.scsl.SCSLjCxLevel1;
import com.haozhiyan.zhijian.bean.scsl.SCSLjCxLevel2;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/20.
 * Describe: 实测实量工作台右侧列表
 */
public class SCSLjCxAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context context;
    private String name;
    private OnItemClickListener onItemclickListener;

    public SCSLjCxAdapter(List<MultiItemEntity> data, Context ctx) {
        super(data);
        this.context = ctx;
        addItemType(TYPE_LEVEL_0, R.layout.scsl_jcx_item01);
        addItemType(TYPE_LEVEL_1, R.layout.scsl_jcx_item02);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final SCSLjCxLevel1 level1 = (SCSLjCxLevel1) item;
                helper.setText(R.id.title, level1.inspectionName)
                        .setText(R.id.tv_percent, "100%")
                        .setImageResource(R.id.iv, level1.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_r);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (level1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                        if (onItemclickListener != null) {
                            onItemclickListener.selectParentItem(level1.inspectionName, level1.inspectionId + "");
                        }
                    }
                });

                break;
            case TYPE_LEVEL_1:
                final SCSLjCxLevel2 level2 = (SCSLjCxLevel2) item;
                helper.setText(R.id.title, level2.inspectionName)
                        .setText(R.id.title_child, level2.partsDivision)
                        .setText(R.id.tv_percent, "100%");
                if (level2.inspectionName.equals(name)) {
                    helper.setGone(R.id.tv_select, true);
                } else {
                    helper.setGone(R.id.tv_select, false);
                }
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemclickListener != null) {
                            onItemclickListener.selectChildItem(level2.inspectionName, level2.inspectionId + "",level2.partsDivision);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    public void setOnItemSonClickListener(OnItemClickListener OnItemClickListener) {
        this.onItemclickListener = OnItemClickListener;
    }

    public interface OnItemClickListener {
        void selectParentItem(String parentName, String parentId);

        void selectChildItem(String childName, String childId,String partsDivision);
    }

    public void setSelectedName(String name){
        this.name = name;
        notifyDataSetChanged();
    }
}
