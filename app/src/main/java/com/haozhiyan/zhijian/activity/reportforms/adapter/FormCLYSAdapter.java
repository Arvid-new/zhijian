package com.haozhiyan.zhijian.activity.reportforms.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.clys.AcceptanceMaterials;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormCLYSChild;
import com.haozhiyan.zhijian.activity.reportforms.bean.FormCLYSTitle;
import com.haozhiyan.zhijian.listener.PersonItemClick;

import java.util.List;

/**
 * 报表-材料验收列表适配器
 */
public class FormCLYSAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_CONTENT = 2;
    private PersonItemClick itemClick;
    private RecyclerView rcv;

    public void setItemClickListener(PersonItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FormCLYSAdapter(@Nullable List<MultiItemEntity> data, Context context) {
        super(data);
        this.context = context;
        addItemType(TYPE_TITLE, R.layout.item_form_clys_title);
        addItemType(TYPE_CONTENT, R.layout.item_form_clys_subitem);
    }

    public void setRcv(RecyclerView rcv) {
        this.rcv = rcv;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_TITLE:
                final FormCLYSTitle lv1 = (FormCLYSTitle) item;
                holder.setText(R.id.title, lv1.nameInspection);
                holder.setText(R.id.tv_name, "所选时段" + lv1.amount + "批,累计" + lv1.total + "批");
                holder.setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.d2_up_icon : R.mipmap.d2_down_icon);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, true);
                        } else {
                            expand(pos, true);
                        }
                    }
                });
                break;
            case TYPE_CONTENT:
                final FormCLYSChild clysChild = (FormCLYSChild) item;
                holder.setText(R.id.title, clysChild.titleName);
                holder.setText(R.id.tv_date, clysChild.approachDate);
                holder.setText(R.id.tv_state, clysChild.stateName);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(context, AcceptanceMaterials.class);
                        intent.putExtra("id", clysChild.id +"");
                        intent.putExtra("type", "0");
                        intent.putExtra("checkResult", "");
                        intent.putExtra("isdaibanin", false);
                        intent.putExtra("entrance", "form");
                        context.startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
    }

}
