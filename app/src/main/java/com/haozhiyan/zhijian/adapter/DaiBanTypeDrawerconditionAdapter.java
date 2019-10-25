package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

public class DaiBanTypeDrawerconditionAdapter extends BaseQuickAdapter<String , BaseViewHolder> {

    private int selectItem = 0;
    private Context context;

    public DaiBanTypeDrawerconditionAdapter(Context context) {
        super(R.layout.daibantypedrawerconditionitem);
        this.context = context;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    protected void convert(BaseViewHolder helper, String  item) {
        helper.setText(R.id.tv, item);

        if (selectItem == helper.getLayoutPosition()) {
            helper.setVisible(R.id.rightLine, false);
            helper.setBackgroundColor(R.id.backlayout, ContextCompat.getColor(context, R.color.white));
        } else {
            helper.setVisible(R.id.rightLine, true);
            helper.setBackgroundColor(R.id.backlayout, ContextCompat.getColor(context, R.color.gray_f7));
        }
    }
}
