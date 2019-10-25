package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.workXcjc.JianChaGuideDetail;
import com.haozhiyan.zhijian.bean.JCGuide1Level;
import com.haozhiyan.zhijian.bean.JCGuide2Level;
import com.haozhiyan.zhijian.bean.JCGuide3Level;

import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/10.
 * Describe: Ydzj_project
 */
public class JianChaGuideAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    private Context context;
    private String oneName;
    public JianChaGuideAdapter(List<MultiItemEntity> data, Context ctx) {
        super(data);
        context = ctx;
        addItemType(TYPE_LEVEL_0, R.layout.jian_cha_guide01item);
        addItemType(TYPE_LEVEL_1, R.layout.jian_cha_guide02item);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_LEVEL_0:
                final JCGuide1Level lv1 = (JCGuide1Level) item;
                helper.setText(R.id.title, lv1.inspectionName)
                        .setImageResource(R.id.iv, lv1.isExpanded() ? R.drawable.arrow_up_gray : R.drawable.arrow_down_gray);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                        oneName = lv1.inspectionName;
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final JCGuide2Level lv2 = (JCGuide2Level) item;
                helper.setText(R.id.title, lv2.inspectionName);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String guide="";
                        try {
                            JCGuide3Level lv3 = lv2.getSubItems().get(0);
                            guide = lv3.checkGuide;
                        } catch (Exception e) {
                            guide="";
                        } finally {
                            //跳转
                            Intent intent = new Intent(context, JianChaGuideDetail.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name", oneName+"-"+lv2.inspectionName);
                            bundle.putString("guide", guide);
                            intent.putExtra("data", bundle);
                            context.startActivity(intent);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }
}
