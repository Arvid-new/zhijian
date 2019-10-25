package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.AngleJsonBean;
import com.haozhiyan.zhijian.utils.AnimationUtil;
import com.haozhiyan.zhijian.utils.StringUtils;
import com.haozhiyan.zhijian.view.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangZhenKai on 2019/4/30.
 * Describe: Ydzj_project
 */
public class AngleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater inflater;
    private List<AngleJsonBean.DataBean> list;
    private Context context;
    private final int HEAD_ITEM_CODE = 1;
    private final int OTHER_ITEM_CODE = 2;
    private MyOnClickListener listener;
    private String selectStr = "测试人员";
    private List<Map<Integer,Boolean>> checkStatus = new ArrayList<>();
    private Map<Integer,Boolean>  booleanMap = new HashMap<>();
    public AngleAdapter(Context context, List<AngleJsonBean.DataBean> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        for (int i = 0; i < 50; i++) {
             booleanMap.put(i,false);
             checkStatus.add(booleanMap);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 1) {
            return new ViewHolder(inflater.inflate(R.layout.item_header_angle, parent, false));
        } else if (viewType == 2) {
            return new ViewHolder(inflater.inflate(R.layout.angle_item01, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder itemHolder = (ViewHolder) holder;
        if (holder.getItemViewType() == 1) {
            itemHolder.llAngle.setVisibility(View.VISIBLE);
            itemHolder.etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() > 0) {
                        itemHolder.iv_search.setVisibility(View.GONE);
                    } else {
                        itemHolder.iv_search.setVisibility(View.VISIBLE);
                    }
                }
            });
            itemHolder.tvHasAngle.setText(selectStr);
        } else {
            itemHolder.bindHolder(position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);
        if (position == 0) {
            return HEAD_ITEM_CODE;
        } else {
            return OTHER_ITEM_CODE;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_danWei;
        private ImageView iv_arrow;
        private MyListView child_list;
        private LinearLayout ll_back;
        private TextView tvHasAngle;
        private EditText etSearch;
        private ImageView iv_search;
        private LinearLayout llAngle;
        private AngleChildAdapter angleChildAdapter;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_danWei = itemView.findViewById(R.id.tv_danWei);
            iv_arrow = itemView.findViewById(R.id.iv_arrow);
            child_list = itemView.findViewById(R.id.child_list);
            tvHasAngle = itemView.findViewById(R.id.tv_hasAngle);
            etSearch = itemView.findViewById(R.id.et_search);
            ll_back = itemView.findViewById(R.id.ll_back);
            llAngle = itemView.findViewById(R.id.ll_angle);
            iv_search = itemView.findViewById(R.id.iv_search);
        }

        public void bindHolder(int position) {
            tv_danWei.setText(list.get(position).dateTitle);
            final List<AngleJsonBean.DataBean.LogDOListBean> logDOListBeanList = list.get(position).logDOList;
            angleChildAdapter = new AngleChildAdapter(context, logDOListBeanList, R.layout.angle_item02);
            child_list.setAdapter(angleChildAdapter);
            angleChildAdapter.notifyDataSetChanged();
            //外层列表条目点击操作二层列表
            ll_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (child_list.getVisibility() == View.VISIBLE) {
                        child_list.setVisibility(View.GONE);
                        AnimationUtil.getInstance().animationIvClose(iv_arrow);
                    } else {
                        child_list.setVisibility(View.VISIBLE);
                        AnimationUtil.getInstance().animationIvOpen(iv_arrow);
                    }
                }
            });
            //二层列表条目点击和子控件点击
            final List<String> checkList = new ArrayList<>();
            child_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (listener != null) {
                        listener.onItemChildListClickListener(selectStr,checkList.size());
                    }
                }
            });
            //二层列表子控件点击事件
            angleChildAdapter.setChildItemClickListener(new AngleChildAdapter.OnItemClickListener() {
                @Override
                public void onItemChildClickListener(View view, int index) {
                    String user = logDOListBeanList.get(index).deviceName;
                    CompoundButton compoundButton = (CompoundButton) view;
                    if (compoundButton.getId() == R.id.cb_check) {
                        if (compoundButton.isChecked()) {
                            if (listener != null) {
                                checkList.add(user);
                                if (checkList.size() >= 1) {
                                    selectStr = "测试人员、" + StringUtils.listToStrByChar(checkList, '、');
                                } else {
                                    selectStr = "测试人员";
                                }
                                listener.onItemChildChildClickListener(selectStr, checkList.size());
                            }
                        } else {
                            if (listener != null) {
                                if (checkList.size() > 0) {
                                    checkList.remove(index);
                                    if (checkList.size() >=1) {
                                        selectStr = "测试人员、" + StringUtils.listToStrByChar(checkList, '、');
                                    } else {
                                        selectStr = "测试人员";
                                    }
                                    listener.onItemChildChildClickListener(selectStr, checkList.size());
                                }
                            }
                        }
                    }
                    AngleAdapter.this.notifyItemChanged(0);
                }
            });
        }
    }

    public interface MyOnClickListener {
        void onItemChildListClickListener(String user, int size);

        void onItemChildChildClickListener(String user, int size);

    }

    public void setItemClickListener(MyOnClickListener itemClickListener) {
        listener = itemClickListener;
    }

}
