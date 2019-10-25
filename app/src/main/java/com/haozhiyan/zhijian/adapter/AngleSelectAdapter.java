package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.Select1AngleItem;
import com.haozhiyan.zhijian.bean.Select2AngleItem;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.utils.LogUtils;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/13.
 * Describe:  各单位负责人选择
 */
public class AngleSelectAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private static final int TYPE_DAN_WEI = 1;//单位类型
    private static final int TYPE_DAN_WEI_FU_ZE_REN = 2;//单位负责人
    private boolean isCheckAll = false;//是选中当前子集所有
    private Activity context;
    private List<String> selectItemContent;
    private List<String> selectItemId;
    private MyOnClickListener listener;
    private String dwName;
    private String dwId;

    public AngleSelectAdapter(List<MultiItemEntity> data, Activity ctx, String name, String id) {
        super(data);
        this.context = ctx;
        selectItemContent = new ArrayList<>();
        selectItemId = new ArrayList<>();
        this.dwName = name;
        this.dwId = id;
        if (!StringUtils.isEmpty(dwName)) {
            selectItemContent.add(dwName);
            if (listener != null) {
                listener.onItemChildListClickListener(dwName, selectItemContent.size(), dwId);
            }
        } else {
            selectItemContent.clear();
        }
        addItemType(TYPE_DAN_WEI, R.layout.angle_item01);
        addItemType(TYPE_DAN_WEI_FU_ZE_REN, R.layout.angle_item02);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (helper.getItemViewType()) {
            case TYPE_DAN_WEI:
                final Select1AngleItem item1 = (Select1AngleItem) item;
                helper.setText(R.id.tv_danWei, item1.roleName)
                        .setChecked(R.id.cb_check, item1.isCheck)
                        .setImageResource(R.id.iv_arrow, item1.isExpanded() ? R.drawable.arrow_up_gray : R.drawable.arrow_down_gray);
                final CheckBox parentBox = helper.getView(R.id.cb_check);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (item1.isExpanded()) {
                            collapse(pos, false);
                        } else {
                            expand(pos, false);
                        }
                    }
                });
                parentBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            isCheckAll = true;
                            if (ListUtils.listEmpty(item1.getSubItems())) {
                                List<Select2AngleItem> angle2Items = item1.getSubItems();
                                if (item1.isCheck) {
                                    item1.isCheck = false;
                                    //操作名称
                                    if (selectItemContent.size() > 0) {
                                        int k = 0;
                                        for (int i = 0; i < selectItemContent.size(); i++) {
                                            for (int j = 0; j < angle2Items.size(); j++) {
                                                if (selectItemContent.get(i) == angle2Items.get(j).peopleuser) {
                                                    k = i;
                                                }
                                            }
                                        }
                                        LogUtils.i("删除000Name===", k + "");
                                        selectItemContent.remove(k);
                                    }
                                    //操作id
                                    if (selectItemId.size() > 0) {
                                        int kk = 0;
                                        for (int m = 0; m < selectItemId.size(); m++) {
                                            for (int n = 0; n < angle2Items.size(); n++) {
                                                if (selectItemId.get(m) == angle2Items.get(n).userId) {
                                                    kk = m;
                                                }
                                            }
                                        }
                                        LogUtils.i("删除000index===", kk + "");
                                        selectItemId.remove(kk);
                                    }
                                } else {
                                    for (int i = 0; i < angle2Items.size(); i++) {
                                        selectItemContent.add(angle2Items.get(i).peopleuser);
                                        selectItemId.add(angle2Items.get(i).userId);
                                    }
                                    item1.isCheck = true;
                                }
                            }
                            try {
                                notifyDataSetChanged();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (listener != null) {
                                listener.onItemChildListClickListener(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(selectItemContent), ',')
                                        , selectItemContent.size(), StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(selectItemId), ','));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case TYPE_DAN_WEI_FU_ZE_REN:
                final Select2AngleItem itemChild = (Select2AngleItem) item;
                MultiItemEntity parent = getData().get(getParentPositionInAll(helper.getAdapterPosition()));
                helper.setText(R.id.name, itemChild.peopleuser).setChecked(R.id.cb_check, itemChild.isCheck);
                ((SimpleDraweeView) helper.getView(R.id.angleView)).setImageURI(itemChild.thumbUrl);
                final CheckBox childBox = helper.getView(R.id.cb_check);
                if (parent instanceof IExpandable) {
                    final Select1AngleItem itemParent = (Select1AngleItem) parent;
                    if (itemParent.isCheck) {
                        if (isCheckAll) {
                            itemChild.isCheck = true;
                        }
                        childBox.setChecked(itemChild.isCheck);
                    } else {
                        if (isCheckAll) {
                            itemChild.isCheck = false;
                        }

                        if (TextUtils.equals(itemChild.peopleuser, dwName)) {
                            childBox.setChecked(true);
                        } else {
                            childBox.setChecked(itemChild.isCheck);
                        }
                    }
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isCheckAll = false;
                            if (itemChild.isCheck) {
                                //操作名称
                                if (selectItemContent.size() > 0) {
                                    int k = 0;
                                    for (int i = 0; i < selectItemContent.size(); i++) {
                                        if (selectItemContent.get(i) == itemChild.peopleuser) {
                                            k = i;
                                        }
                                    }
                                    LogUtils.i("子条目删除Name===", k + "");
                                    selectItemContent.remove(k);
                                }
                                //操作id
                                if (selectItemId.size() > 0) {
                                    int j = 0;
                                    for (int i = 0; i < selectItemId.size(); i++) {
                                        if (selectItemId.get(i) == itemChild.userId) {
                                            j = i;
                                        }
                                    }
                                    LogUtils.i("子条目删除id===", j + "");
                                    selectItemId.remove(j);
                                }
                                itemChild.isCheck = false;
                                itemParent.isCheck = false;
                                try {
                                    notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                selectItemContent.add(itemChild.peopleuser);
                                selectItemId.add(itemChild.userId);
                                addSelectStatus(itemChild, itemParent);
                            }
                            if (listener != null) {
                                listener.onItemChildListClickListener(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(selectItemContent), ',')
                                        , selectItemContent.size(), StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(selectItemId), ','));
                            }
                        }
                    });
                    childBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            isCheckAll = false;
                            if (itemChild.isCheck) {
                                if (selectItemContent.size() > 0) {
                                    int k = 0;
                                    for (int i = 0; i < selectItemContent.size(); i++) {
                                        if (selectItemContent.get(i) == itemChild.peopleuser) {
                                            k = i;
                                        }
                                    }
                                    LogUtils.i("子条目删除Name===", k + "");
                                    selectItemContent.remove(k);
                                }
                                //操作id
                                if (selectItemId.size() > 0) {
                                    int j = 0;
                                    for (int i = 0; i < selectItemId.size(); i++) {
                                        if (selectItemId.get(i) == itemChild.userId) {
                                            j = i;
                                        }
                                    }
                                    //LogUtils.i("子条目删除id===", j + "");
                                    selectItemId.remove(j);
                                }
                                itemChild.isCheck = false;
                                itemParent.isCheck = false;
                                childBox.setChecked(itemChild.isCheck);
                                try {
                                    notifyDataSetChanged();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                selectItemContent.add(itemChild.peopleuser);
                                selectItemId.add(itemChild.userId);
                                itemChild.isCheck = true;
                                childBox.setChecked(itemChild.isCheck);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        boolean isAllTrue = true;
                                        List<Select2AngleItem> sectionList = itemParent.getSubItems();
                                        for (Select2AngleItem section :
                                                sectionList) {
                                            if (!section.isCheck) {
                                                isAllTrue = false;
                                            }
                                        }
                                        itemParent.isCheck = isAllTrue;
                                        context.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    notifyDataSetChanged();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                }).start();
                            }
                            if (listener != null) {
                                listener.onItemChildListClickListener(StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(selectItemContent), ',')
                                        , selectItemContent.size()
                                        , StringUtils.listToStrByChar(StringUtils.removeDuplicateWithOrder(selectItemId), ','));
                            }
                        }
                    });
                }
                break;
            default:
                break;
        }
    }

    private void addSelectStatus(Select2AngleItem itemChild, final Select1AngleItem itemParent) {
        itemChild.isCheck = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean isAllTrue = true;
                List<Select2AngleItem> sectionList = itemParent.getSubItems();
                for (Select2AngleItem section :
                        sectionList) {
                    if (!section.isCheck) {
                        isAllTrue = false;
                    }
                }
                itemParent.isCheck = isAllTrue;
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
    }

    public interface MyOnClickListener {
        void onItemChildListClickListener(String user, int size, String id);
    }

    public void setItemClickListener(MyOnClickListener itemClickListener) {
        listener = itemClickListener;
    }

}
