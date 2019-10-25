package com.haozhiyan.zhijian.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Brand;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Specification;
import com.haozhiyan.zhijian.bean.AcceptanceMaterialsBeans.AM_Enter_Type;
import com.haozhiyan.zhijian.utils.StringUtils;

import java.util.List;

import static com.haozhiyan.zhijian.model.Constant.TYPE_BRAND;
import static com.haozhiyan.zhijian.model.Constant.TYPE_SPECIfICATION;
import static com.haozhiyan.zhijian.model.Constant.TYPE_TYPE;

public class AcceotanceMaterialsEnterAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    private Activity context;
    private boolean isFocus;

    public AcceotanceMaterialsEnterAdapter(@Nullable List<MultiItemEntity> data, Activity context) {
        super(data);
        this.context = context;
        addItemType(TYPE_BRAND, R.layout.am_enterarea_level_brand);
        addItemType(TYPE_TYPE, R.layout.am_enterarea_level_type);
        addItemType(TYPE_SPECIfICATION, R.layout.am_enterarea_level_specification);
    }

    @Override
    protected void convert(final BaseViewHolder holder, final MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_BRAND: // 品牌 level 1
                final AM_Enter_Brand lv1 = (AM_Enter_Brand) item;
                if (lv1.position == 0) {
                    holder.getView(R.id.delete_img).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.delete_img).setVisibility(View.VISIBLE);
                }
                if (lv1.bandName.equals("请选择")) {
                    holder.setTextColor(R.id.name, Color.parseColor("#656565"));
                } else {
                    holder.setTextColor(R.id.name, Color.parseColor("#232323"));
                }
                holder.setText(R.id.name, lv1.bandName);

                holder.addOnClickListener(R.id.delete_img);
                holder.addOnClickListener(R.id.BrandLL);
                //删除品牌
//                holder.getView(R.id.delete_img).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos = holder.getAdapterPosition();
//                        remove(pos);
//                    }
//                });

                break;
            case TYPE_TYPE: // 类型 level 2
                final AM_Enter_Type lv2 = (AM_Enter_Type) item;
                holder.setText(R.id.name, lv2.typeName);
                if (lv2.position == Integer.MAX_VALUE) {
                    holder.getView(R.id.delete_img).setVisibility(View.GONE);
                    holder.getView(R.id.TypeLL).setVisibility(View.GONE);
                    holder.getView(R.id.addType).setVisibility(View.VISIBLE);
                } else {
                    holder.getView(R.id.delete_img).setVisibility(View.VISIBLE);
                    holder.getView(R.id.TypeLL).setVisibility(View.VISIBLE);
                    holder.getView(R.id.addType).setVisibility(View.GONE);
                    if (lv2.typeName.equals("请选择")) {
                        holder.setTextColor(R.id.name, Color.parseColor("#656565"));
                    } else {
                        holder.setTextColor(R.id.name, Color.parseColor("#232323"));
                    }
                    holder.setText(R.id.name, lv2.typeName);
                }
//                //添加类型 / 选择类型
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (lv2.position == Integer.MAX_VALUE) {
//                            MultiItemEntity parent = getData().get(getParentPositionInAll(holder.getAdapterPosition()));
//                            if (parent instanceof AM_Enter_Brand) {
//                                AM_Enter_Type newLv2 = new AM_Enter_Type("请选择",
//                                        0);
//                                AM_Enter_Specification newLv3 = new AM_Enter_Specification(
//                                        "请选择", "规格","" , Integer.MAX_VALUE);
//                                newLv2.addSubItem(newLv3);
//                                addData(holder.getAdapterPosition(), newLv2);
////                                addData(holder.getAdapterPosition(), newLv3);
//                                ((AM_Enter_Brand) parent).addSubItem(newLv2);
//                                List<MultiItemEntity> data = getData();
//                                data.size();
////                                expandAll();
//                            }
//                        } else {
//
//                        }
//                    }
//                });
                holder.addOnClickListener(R.id.delete_img);
//                //删除类型
//                holder.getView(R.id.delete_img).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos = holder.getAdapterPosition();
//                        remove(pos);
//                    }
//                });

                break;
            case TYPE_SPECIfICATION: // 规格
                AM_Enter_Specification lv3 = (AM_Enter_Specification) item;

                if (lv3.position == Integer.MAX_VALUE) {
                    holder.getView(R.id.delete_img).setVisibility(View.GONE);
                    holder.getView(R.id.name).setVisibility(View.GONE);
                    holder.getView(R.id.right_icon).setVisibility(View.GONE);
                    holder.setText(R.id.addSP, "+   新增规格");
                    holder.setTextColor(R.id.addSP, ContextCompat.getColor(context, R.color.blue5));
                    holder.getView(R.id.unitRL).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.delete_img).setVisibility(View.VISIBLE);
                    holder.getView(R.id.name).setVisibility(View.VISIBLE);
                    holder.getView(R.id.right_icon).setVisibility(View.VISIBLE);
                    holder.setText(R.id.addSP, "规格");
                    if (lv3.unit.equals("请选择")) {
                        holder.setTextColor(R.id.name, Color.parseColor("#656565"));
                    } else {
                        holder.setTextColor(R.id.name, Color.parseColor("#232323"));
                    }
                    holder.setText(R.id.name, lv3.unit);
                    holder.setTextColor(R.id.addSP, ContextCompat.getColor(context, R.color.black2));
                    if (!StringUtils.isEmpty(lv3.specificationName)) {
                        holder.setText(R.id.addSP, lv3.specificationName);
                        holder.getView(R.id.unitRL).setVisibility(View.VISIBLE);
                        EditText editText = holder.getView(R.id.unitET);
                        if (!StringUtils.isEmpty(lv3.number)) {
                            editText.setText(lv3.number);
                        } else {
                            editText.setText("");
                        }
                        holder.setText(R.id.unitTV, lv3.unit);
                        holder.getView(R.id.name).setVisibility(View.GONE);
                        holder.getView(R.id.right_icon).setVisibility(View.GONE);
                        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View v, boolean hasFocus) {
                                if (v instanceof EditText) {
                                    EditText editText = (EditText) v;
                                    if (null != editText.getTag()) {
                                        editText.removeTextChangedListener((TextWatcher) editText.getTag());
                                    }
                                    if (hasFocus) {
                                        TextWatcher watcher = new TextWatcher() {
                                            @Override
                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                            }

                                            @Override
                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                AM_Enter_Specification lv3 = (AM_Enter_Specification) item;
                                                lv3.number = s.toString();
                                            }

                                            @Override
                                            public void afterTextChanged(Editable s) {

                                            }
                                        };
                                        editText.setTag(watcher);
                                        editText.addTextChangedListener(watcher);
                                    }
                                }

                            }
                        });
                    } else {
                        holder.getView(R.id.unitRL).setVisibility(View.GONE);
                    }
                }

                //添加规格 / 选择规格
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (lv3.position == Integer.MAX_VALUE) {
//                            MultiItemEntity parent = getData().get(getParentPositionInAll(holder.getAdapterPosition()));
//                            if (parent instanceof AM_Enter_Type) {
//                                AM_Enter_Specification newLv3 = new AM_Enter_Specification(
//                                        "规格", "请选择", "", 0);
//                                addData(holder.getAdapterPosition(), newLv3);
//                                ((AM_Enter_Type) parent).addSubItem(newLv3);
//                                List<MultiItemEntity> data = getData();
//                                data.size();
////                                expandAll();
//                            }
//                        } else {
//
//                        }
//                    }
//                });
                holder.addOnClickListener(R.id.delete_img);
//                //删除规格
//                holder.getView(R.id.delete_img).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        remove(holder.getAdapterPosition());
//                    }
//                });

                break;
            default:
                break;
        }
    }


}