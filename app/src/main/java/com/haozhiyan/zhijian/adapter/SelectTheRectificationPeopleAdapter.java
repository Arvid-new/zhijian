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
import com.haozhiyan.zhijian.bean.CaiLiaoTypeSelectBean;
import com.haozhiyan.zhijian.bean.ZhengGaiPerson;
import com.haozhiyan.zhijian.bean.ZhengGaiTypeTitle;
import com.haozhiyan.zhijian.listener.PersonItemClick2;

import java.util.List;

public class SelectTheRectificationPeopleAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private Context context;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;
    public static final int TYPE_CHECKLIST = 3;
    private PersonItemClick2 itemClick;
    private String selectID;
    private RecyclerView rcv;

    public void setItemClickListener(PersonItemClick2 itemClick) {
        this.itemClick = itemClick;
    }

    public SelectTheRectificationPeopleAdapter(@Nullable List<MultiItemEntity> data, Context context, String selectID) {
        super(data);
        this.context = context;
        this.selectID = selectID;
        addItemType(TYPE_LEVEL_1, R.layout.selecttherectificationpeopleitem);
        addItemType(TYPE_PERSON, R.layout.item_expandable_lv1);
        addItemType(TYPE_CHECKLIST, R.layout.daibantypedraweroptionslayoutcheckitem);
    }

    public void setRcv(RecyclerView rcv) {
        this.rcv = rcv;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_1:
                final ZhengGaiTypeTitle lv1 = (ZhengGaiTypeTitle) item;
                holder.setText(R.id.title, lv1.roleName)
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
                final ZhengGaiPerson person = (ZhengGaiPerson) item;
                Log.d(TAG, " parent : " + getParentPosition(person));
                holder.setText(R.id.tv, person.peopleuser);
                if (selectID.equals(person.userId + "")) {
                    try {
                        MultiItemEntity parent = getData().get(getParentPositionInAll(holder.getAdapterPosition()));
                        if (parent instanceof IExpandable) {
                            ZhengGaiTypeTitle zhengGaiTypeTitle = (ZhengGaiTypeTitle) parent;
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
                            itemClick.onItemClick(person.peopleuser, person.userId + "", person.mobile);
                    }
                });
                break;
            case TYPE_CHECKLIST: //检查项 /  严重程度
                final CaiLiaoTypeSelectBean check = (CaiLiaoTypeSelectBean) item;
                holder.setText(R.id.sectionName, check.inspectionName);
                if (check.isCheck) {
                    holder.setImageResource(R.id.checkimg, R.mipmap.xuanze);
                } else {
                    holder.setImageResource(R.id.checkimg, R.mipmap.gray_yuan);
                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check.isCheck) {
                            check.isCheck = false;
                        } else {
                            check.isCheck = true;
                        }
                        notifyDataSetChanged();
                    }
                });
                break;
            default:
                break;
        }
    }

}
