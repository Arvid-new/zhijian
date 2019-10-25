package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

public class AccountabilityUnitListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private String selectID;
    private List<String> contractorManageId;

    public AccountabilityUnitListAdapter(@Nullable List<String> data, List<String> contractorManageId, String selectID) {
        super(R.layout.accountabilityunit_item, data);
        this.selectID = selectID;
        this.contractorManageId = contractorManageId;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        try {
            if (contractorManageId.get(helper.getLayoutPosition()).equals(selectID)) {
                helper.setVisible(R.id.iv, true);
            } else {
                helper.setVisible(R.id.iv, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        helper.setText(R.id.tv, item);


    }
}
