package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.SelectPerson;
import com.haozhiyan.zhijian.bean.SelectPersonTitle;
import com.haozhiyan.zhijian.listener.MyItemClickListener;
import com.haozhiyan.zhijian.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class SelectPeopleAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Activity context;
    public static final int TYPE_PROJECT = 1;//  level 1
    public static final int TYPE_SECTION = 2;//  level 2
    private List<String> selectIds;
    //    private boolean isUserChenge = false;//是否主动选中所有
    private List<String> selectIDs = new ArrayList<>();
    private List<String> selectNames = new ArrayList<>();
    private boolean isFrist = true;
    private MyItemClickListener itemClickListener;

    public SelectPeopleAdapter(@Nullable List<MultiItemEntity> data, Activity context, List<String> selectIds, List<String> selectnames) {
        super(data);
        this.context = context;
        if (selectIds != null) {
            selectIDs.addAll(selectIds);
        }
        if (selectnames != null) {
            selectNames.addAll(selectnames);
        }
        addItemType(TYPE_PROJECT, R.layout.double_select_title_item);
        addItemType(TYPE_SECTION, R.layout.double_select_person_item);
    }

    public void setItemClickListener(MyItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public List<String> getSelectIDs() {
        return selectIDs;
    }

    public List<String> getSelectNames() {
        return selectNames;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_PROJECT: // 项目标段 level 1
                final SelectPersonTitle lv1 = (SelectPersonTitle) item;
                holder.setText(R.id.projectName, lv1.roleName)
                        .setImageResource(R.id.updownImg, lv1.isExpanded() ? R.mipmap.d2_up_icon : R.mipmap.d2_down_icon);
                try {
                    if (lv1.isCheck) {
                        holder.setImageResource(R.id.checkImg, R.mipmap.xuanze);
                    } else {
                        holder.setImageResource(R.id.checkImg, R.mipmap.gray_yuan);
                    }
                } catch (Exception e) {
                    holder.setImageResource(R.id.checkImg, R.mipmap.gray_yuan);
                }
                holder.getView(R.id.checkImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lv1.isUserChenge = true;
                        isFrist = false;
                        try {
                            if (lv1.isCheck) {
                                lv1.isCheck = false;
                                try {
                                    if (ListUtils.listEmpty(lv1.getSubItems())) {
                                        for (int i = 0; i < lv1.getSubItems().size(); i++) {
                                            selectIDs.remove(lv1.getSubItems().get(i).userId+"");
                                            selectNames.remove(lv1.getSubItems().get(i).peopleuser);
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lv1.isCheck = true;
                                if (ListUtils.listEmpty(lv1.getSubItems())) {
                                    for (int i = 0; i < lv1.getSubItems().size(); i++) {
                                        if (!selectIDs.contains(lv1.getSubItems().get(i).userId + "")) {
                                            selectIDs.add(lv1.getSubItems().get(i).userId + "");
                                            selectNames.add(lv1.getSubItems().get(i).peopleuser);
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            lv1.isCheck = true;
                        }
                        if (itemClickListener != null) {
                            itemClickListener.onItemClick("", 0);
                        }
                        notifyDataSetChanged();
                    }
                });
                holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
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
            case TYPE_SECTION: // 项目标段 level 2
                final SelectPerson person = (SelectPerson) item;
                holder.setText(R.id.tv, person.peopleuser);
                ImageView checkimg = holder.getView(R.id.personCheckImg);

//                if (isFrist && selectIDs != null && selectIDs.contains(person.userId + "")) {
//                    person.isCheck = true;
//                }
                MultiItemEntity parent = getData().get(getParentPositionInAll(holder.getAdapterPosition()));
                if (parent instanceof IExpandable) {
                    final SelectPersonTitle lvOne = (SelectPersonTitle) parent;
                    try {
                        if (lvOne.isCheck) {
                            if (lvOne.isUserChenge) {
                                person.isCheck = true;
                            }
                            checkimg.setImageResource(R.mipmap.xuanze);
                        } else {
                            if (lvOne.isUserChenge) {
                                person.isCheck = false;
                            }
                            if (person.isCheck) {
                                checkimg.setImageResource(R.mipmap.xuanze);
                            } else {
                                checkimg.setImageResource(R.mipmap.gray_yuan);
                            }
                        }
                    } catch (Exception e) {
                        checkimg.setImageResource(R.mipmap.gray_yuan);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            lvOne.isUserChenge = false;
                            isFrist = false;
                            if (person.isCheck) {
                                person.isCheck = false;
                                lvOne.isCheck = false;
                                try {
                                    List<SelectPerson> sectionList = lvOne.getSubItems();
                                    for (int i = 0; i < sectionList.size(); i++) {
                                        if (sectionList.get(i).isCheck == false) {
//                                            if (selectIDs.contains(sectionList.get(i).userId + "")) {
                                            String id = sectionList.get(i).userId + "";
                                            selectIDs.remove(id);
                                            selectNames.remove(sectionList.get(i).peopleuser);
//                                            }
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                if (itemClickListener != null) {
                                    itemClickListener.onItemClick("", 0);
                                }
                                notifyDataSetChanged();
                            } else {
                                person.isCheck = true;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean isAlltrue = true;
                                        List<SelectPerson> sectionList = lvOne.getSubItems();
                                        for (SelectPerson section :
                                                sectionList) {
                                            if (!section.isCheck) {
                                                isAlltrue = false;
                                            }
                                        }
                                        lvOne.isCheck = isAlltrue;
                                        try {
                                            for (int i = 0; i < sectionList.size(); i++) {
                                                if (sectionList.get(i).isCheck == true) {
                                                    if (!selectIDs.contains(sectionList.get(i).userId + "")) {
                                                        selectIDs.add(sectionList.get(i).userId + "");
                                                        selectNames.add(sectionList.get(i).peopleuser);
                                                    }
                                                }
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (itemClickListener != null) {
                                                    itemClickListener.onItemClick("", 0);
                                                }
                                                notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }).start();
                            }
                        }
                    });
                }

                break;

            default:
                break;
        }
    }

}