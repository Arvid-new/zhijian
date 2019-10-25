package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.DaiBanDrawerCheckBean;
import com.haozhiyan.zhijian.bean.DaiBanDrawerProject;
import com.haozhiyan.zhijian.bean.DaiBanDrawerSection;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class DaiBanTypeDrawerOptionsAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Activity context;
    public static final int TYPE_PROJECT = 1;// 项目标段 level 1
    public static final int TYPE_SECTION = 2;// 项目标段 level 2
    public static final int TYPE_CHECKLIST = 3; //检查项 /  严重程度
    private PersonItemClick itemClick;
    private String selectName;
    private boolean isUserChenge = false;//是否主动选中所有
    private List<String> sectionIdList = new ArrayList<>();
    ;

    public void setItemClickListener(DaiBanTypeDrawerOptionsAdapter.PersonItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public DaiBanTypeDrawerOptionsAdapter(@Nullable List<MultiItemEntity> data, Activity context, String selectName) {
        super(data);
        this.context = context;
        this.selectName = selectName;
        addItemType(TYPE_PROJECT, R.layout.daibantypedraweroptionslayoutitemone);
        addItemType(TYPE_SECTION, R.layout.daibantypedraweroptionslayoutitemchild);
        addItemType(TYPE_CHECKLIST, R.layout.daibantypedraweroptionslayoutcheckitem);
    }


    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_PROJECT: // 项目标段 level 1
                final DaiBanDrawerProject lv1 = (DaiBanDrawerProject) item;
                holder.setText(R.id.projectName, lv1.sectionName)
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
                holder.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isUserChenge = true;
                        try {
                            if (lv1.isCheck) {
                                lv1.isCheck = false;
                                try {
                                    if (ListUtils.listEmpty(lv1.getSubItems())) {
                                        for (int i = 0; i < lv1.getSubItems().size(); i++) {
                                            sectionIdList.remove(i);
                                        }
                                        if (myItemClick != null) {
                                            myItemClick.BDSelected(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(sectionIdList), ','));
                                        }
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                lv1.isCheck = true;
                                if (ListUtils.listEmpty(lv1.getSubItems())) {
                                    for (int i = 0; i < lv1.getSubItems().size(); i++) {
                                        sectionIdList.add(lv1.getSubItems().get(i).sectionId + "");
                                    }
                                    if (myItemClick != null) {
                                        myItemClick.BDSelected(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(sectionIdList), ','));
                                    }
                                }
                            }
                        } catch (Exception e) {
                            lv1.isCheck = true;
                        }
                        notifyDataSetChanged();
                    }
                });
                holder.getView(R.id.updownImg).setOnClickListener(new View.OnClickListener() {
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
                final DaiBanDrawerSection person = (DaiBanDrawerSection) item;
                holder.setText(R.id.sectionName, person.sectionName);
                MultiItemEntity parent = getData().get(getParentPositionInAll(holder.getAdapterPosition()));
                if (parent instanceof IExpandable) {
                    final DaiBanDrawerProject lvOne = (DaiBanDrawerProject) parent;
                    try {
                        if (lvOne.isCheck) {
                            if (isUserChenge) {
                                person.isCheck = true;
                            }
                            holder.setImageResource(R.id.checkimg, R.mipmap.xuanze);
                        } else {
                            if (isUserChenge) {
                                person.isCheck = false;
                            }
                            if (person.isCheck) {
                                holder.setImageResource(R.id.checkimg, R.mipmap.xuanze);
                            } else {
                                holder.setImageResource(R.id.checkimg, R.mipmap.gray_yuan);
                            }
                        }
                    } catch (Exception e) {
                        holder.setImageResource(R.id.checkimg, R.mipmap.gray_yuan);
                    }
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            isUserChenge = false;
                            if (person.isCheck) {
                                try {
                                    List<DaiBanDrawerSection> sectionList = lvOne.getSubItems();
                                    for (int i = 0; i < sectionList.size(); i++) {
                                        if (sectionList.get(i).isCheck == false) {
                                            sectionIdList.remove(sectionList.get(i).sectionId + "");
                                        }
                                    }
                                    if (myItemClick != null) {
                                        myItemClick.BDSelected(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(sectionIdList), ','));
                                        myItemClick.BDSection(person.sectionId + "");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                person.isCheck = false;
                                lvOne.isCheck = false;
                                notifyDataSetChanged();
                            } else {
                                person.isCheck = true;
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean isAlltrue = true;
                                        List<DaiBanDrawerSection> sectionList = lvOne.getSubItems();
                                        for (DaiBanDrawerSection section :
                                                sectionList) {
                                            if (!section.isCheck) {
                                                isAlltrue = false;
                                            }
                                        }
                                        lvOne.isCheck = isAlltrue;
                                        try {
                                            for (int i = 0; i < sectionList.size(); i++) {
                                                if (sectionList.get(i).isCheck == true) {
                                                    sectionIdList.add(sectionList.get(i).sectionId + "");
                                                }
                                            }
                                            if (myItemClick != null) {
                                                myItemClick.BDSelected(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(sectionIdList), ','));
                                                myItemClick.BDSection(person.sectionId + "");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
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
            case TYPE_CHECKLIST: //检查项 /  严重程度
                final DaiBanDrawerCheckBean check = (DaiBanDrawerCheckBean) item;
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
                            if (myItemClick != null) {
                                myItemClick.jcxSection("");
                            }
                        } else {
                            check.isCheck = true;
                            if (myItemClick != null) {
                                myItemClick.jcxSection(check.inspectionName);
                            }
                        }
                        notifyDataSetChanged();
                    }
                });
                break;
            default:
                break;
        }
    }

    public interface PersonItemClick {
        void onItemClick(MultiItemEntity itemEntity);
    }

    private MyItemClick myItemClick;

    public void setMyItemClick(MyItemClick itemClick) {
        myItemClick = itemClick;
    }

    public interface MyItemClick {
        void BDSelected(String sectionIdStr);

        void BDSection(String id);

        void jcxSection(String name);
    }
}