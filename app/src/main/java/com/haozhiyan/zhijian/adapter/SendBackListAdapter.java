package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.haozhiyan.zhijian.R;

import java.util.List;

public class SendBackListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private boolean[] checks;

    public SendBackListAdapter(@Nullable List<String> data, boolean[] checks) {
        super(R.layout.sendback_problem_item, data);
        this.checks = checks;
    }

    public boolean[] getChecks() {
        return checks;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (checks[helper.getLayoutPosition()]) {
            helper.setImageResource(R.id.checkImg, R.mipmap.xuanze);
        } else {
            helper.setImageResource(R.id.checkImg, R.mipmap.gray_yuan);
        }
        helper.setText(R.id.projectName, item);


    }
}
