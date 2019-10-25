package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.CaiLiaoTitle;
import com.haozhiyan.zhijian.bean.CaiLiaoTypeName;
import com.haozhiyan.zhijian.listener.PersonItemClick2;

import java.util.List;

public class MaterialsNameAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;
    private PersonItemClick2 itemClick;
    private String selectID;
    private RecyclerView rcv;

    public void setItemClickListener(PersonItemClick2 itemClick) {
        this.itemClick = itemClick;
    }

    public MaterialsNameAdapter(@Nullable List<MultiItemEntity> data, Context context, String selectID) {
        super(data);
        this.context = context;
        this.selectID = selectID;
        addItemType(TYPE_LEVEL_1, R.layout.selecttherectificationpeopleitem);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv1);
    }

    public void setRcv(RecyclerView rcv) {
        this.rcv = rcv;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_1:
                final CaiLiaoTitle lv1 = (CaiLiaoTitle) item;
                holder.setText(R.id.title, lv1.inspectionName)
                        .setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.d2_up_icon : R.mipmap.d2_down_icon);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                break;
            case TYPE_PERSON:
                final CaiLiaoTypeName person = (CaiLiaoTypeName) item;
                Log.d(TAG, " parent : " + getParentPosition(person));
                holder.setText(R.id.tv, person.inspectionName);
                holder.getView(R.id.headIV).setVisibility(View.GONE);
                if (selectID.equals(person.inspectionId+"")) {
                    try {
                        MultiItemEntity parent = getData().get(getParentPositionInAll(holder.getAdapterPosition()));
                        if (parent instanceof IExpandable) {
                            CaiLiaoTitle zhengGaiTypeTitle = (CaiLiaoTitle) parent;
                            zhengGaiTypeTitle.setExpanded(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    holder.setVisible(R.id.iv, true);
                } else {
                    holder.setVisible(R.id.iv, false);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (itemClick != null)
                            itemClick.onItemClick(person.inspectionName, person.inspectionId + "","");
                    }
                });
                break;
            default:
                break;
        }
    }

}
