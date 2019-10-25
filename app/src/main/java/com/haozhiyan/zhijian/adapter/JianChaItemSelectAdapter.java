package com.haozhiyan.zhijian.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.JCItem1Level;
import com.haozhiyan.zhijian.bean.JCItem2Level;
import com.haozhiyan.zhijian.bean.JCItem3Level;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/9.
 * Describe: Ydzj_project
 */
public class JianChaItemSelectAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private static final String TAG = JianChaItemSelectAdapter.class.getSimpleName();
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_LEVEL_2 = 2;
    private String levelStr01, levelStr02, levelStr03,inspectionId01="",inspectionId02="";
    private OnItemSonClickListener onItemSonclickListener;
    public JianChaItemSelectAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_jiancha_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_jiancha_lv1);
        addItemType(TYPE_LEVEL_2, R.layout.item_jiancha_lv2);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final JCItem1Level lv1 = (JCItem1Level) item;
                helper.setText(R.id.title, lv1.inspectionName)
                            .setImageResource(R.id.iv, lv1.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_r);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                        levelStr01 = lv1.inspectionName;
                        inspectionId01 = lv1.inspectionId+"";
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final JCItem2Level lv2 = (JCItem2Level) item;
                helper.setText(R.id.title, lv2.inspectionName)
                        .setImageResource(R.id.iv, lv2.isExpanded() ? R.mipmap.arrow_down : R.mipmap.arrow_r);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        levelStr02 = lv2.inspectionName;
                        inspectionId02 = lv2.inspectionId+"";
                        JCItem3Level jcItem3Level = lv2.getSubItem(helper.getAdapterPosition());
                        if (jcItem3Level == null) {
                            if (onItemSonclickListener != null) {
                                onItemSonclickListener.lvTwoContent(levelStr01, levelStr02,inspectionId01+","+inspectionId02,Long.parseLong(inspectionId02));
                            }
                        } else {
                            int pos = helper.getAdapterPosition();
                            if (lv2.isExpanded()) {
                                collapse(pos, false);
                            } else {
                                expand(pos, false);
                            }
                        }
                    }
                });
                break;
            case TYPE_LEVEL_2:
                final JCItem3Level lv3 = (JCItem3Level) item;
                helper.setText(R.id.tv, lv3.particularsName);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        levelStr03 = lv3.particularsName;
                        if (onItemSonclickListener != null) {
                            onItemSonclickListener.lvContent(levelStr01 + levelStr02, levelStr03,inspectionId01+","+inspectionId02,lv3.inspectionId+"",Long.parseLong(inspectionId02));
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

    public void setOnItemSonClickListener(OnItemSonClickListener sonClickListener) {
        this.onItemSonclickListener = sonClickListener;
    }

    public interface OnItemSonClickListener {
        void lvContent(String str01, String str02,String inspectionIds,String descId,long inspectionId);
        void lvTwoContent(String str01, String str02,String inspectionIds,long inspectionId);
    }
}
