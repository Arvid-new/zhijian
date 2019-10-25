package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Brand;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Specification;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Type;

import java.util.List;

import static com.haozhiyan.zhijian.model.Constant.TYPE_BRAND;
import static com.haozhiyan.zhijian.model.Constant.TYPE_SPECIfICATION;
import static com.haozhiyan.zhijian.model.Constant.TYPE_TYPE;

public class InspectCaiLiaoAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Activity context;


    public InspectCaiLiaoAdapter(@Nullable List<MultiItemEntity> data, Activity context) {
        super(data);
        this.context = context;
        addItemType(TYPE_BRAND, R.layout.inspect_brand_item);
        addItemType(TYPE_TYPE, R.layout.inspect_type_item);
        addItemType(TYPE_SPECIfICATION, R.layout.inspect_specification_item);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_BRAND: // 品牌 level 1
                final AM_Enter_Brand lv1 = (AM_Enter_Brand) item;
                holder.setText(R.id.brondName, lv1.bandName);
                break;
            case TYPE_TYPE: // 类型 level 2
                final AM_Enter_Type lv2 = (AM_Enter_Type) item;
                holder.setText(R.id.typeName, lv2.typeName);
                break;
            case TYPE_SPECIfICATION: // 规格
                final AM_Enter_Specification lv3 = (AM_Enter_Specification) item;
                holder.setText(R.id.name, lv3.specificationName);
                holder.setText(R.id.unitTV, "组");
                EditText unitET = holder.getView(R.id.unitET);
                unitET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        try {
                            lv3.number = s.toString().trim();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            default:
                break;
        }
    }


}