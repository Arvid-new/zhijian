package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.activity.MePackage.HelpActTwo;
import com.haozhiyan.zhijian.activity.MyWebView;
import com.haozhiyan.zhijian.bean.HelpBean;
import com.haozhiyan.zhijian.bean.HelpProblemItem;
import com.haozhiyan.zhijian.utils.ListUtils;
import com.haozhiyan.zhijian.view.MyListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/24.
 * Describe:
 */
public class HelpAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //常见问题
    private final int PROBLEM_TYPE_CODE = 1;
    //全部问题
    private final int PROBLEM_TITLE_CODE = 2;

    private LayoutInflater inflater;
    private HelpBean.ListBean listBeans;
    private List<HelpBean.ListBean.CJWTBean> cjwtBeans;
    private List<HelpBean.ListBean.QBWTBean> qbwtBeans;
    private Context ctx;
    private HelpTextAdapter adapter;

    public HelpAdapter(Context context, HelpBean.ListBean listBeans) {
        this.ctx = context;
        this.listBeans = listBeans;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        if (viewType == PROBLEM_TYPE_CODE) {
            holder = new ViewHolder(inflater.inflate(R.layout.help_item_one, parent, false));
        } else if (viewType == PROBLEM_TITLE_CODE) {
            holder = new ViewHolder(inflater.inflate(R.layout.help_item_two, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder itemHolder = (ViewHolder) holder;
        itemHolder.bingHolder(itemHolder.getItemViewType());
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return PROBLEM_TYPE_CODE;
        } else if (position == 1) {
            return PROBLEM_TITLE_CODE;
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvType;
        TextView tvType2;
        MyListView oftenProblemList;
        MyListView problemList;

        public ViewHolder(View itemView) {
            super(itemView);
            tvType = itemView.findViewById(R.id.tv_type);
            tvType2 = itemView.findViewById(R.id.tv_type2);
            problemList = itemView.findViewById(R.id.problemList);
            oftenProblemList = itemView.findViewById(R.id.oftenProblemList);
        }

        public void bingHolder(final int itemType) {
            if (listBeans != null) {
                if (itemType == PROBLEM_TYPE_CODE) {
                    cjwtBeans = listBeans.CJWT;
                    if (ListUtils.listEmpty(cjwtBeans)) {
                        tvType.setText("常见问题");
                        List<HelpProblemItem> helpProblemList = new ArrayList<>();
                        for (int i = 0; i < cjwtBeans.size(); i++) {
                            helpProblemList.add(new HelpProblemItem(cjwtBeans.get(i).id, cjwtBeans.get(i).parentId, cjwtBeans.get(i).helpName, cjwtBeans.get(i).connect));
                        }
                        adapter = new HelpTextAdapter(ctx, helpProblemList);
                        oftenProblemList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        oftenProblemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(ctx, MyWebView.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("url", cjwtBeans.get(position).connect);
                                bundle.putString("title", "帮助");
                                intent.putExtra("data", bundle);
                                ctx.startActivity(intent);
                            }
                        });
                    }
                } else if (itemType == PROBLEM_TITLE_CODE) {
                    qbwtBeans = listBeans.QBWT;
                    if (ListUtils.listEmpty(qbwtBeans)) {
                        tvType2.setText("全部问题");
                        List<HelpProblemItem> helpProblemList = new ArrayList<>();
                        for (int i = 0; i < qbwtBeans.size(); i++) {
                            helpProblemList.add(new HelpProblemItem(qbwtBeans.get(i).id, qbwtBeans.get(i).parentId, qbwtBeans.get(i).helpName, qbwtBeans.get(i).connect));
                        }
                        adapter = new HelpTextAdapter(ctx, helpProblemList);
                        problemList.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        problemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(ctx, HelpActTwo.class);
                                intent.putExtra("problemName", qbwtBeans.get(position).helpName);
                                intent.putExtra("QBwtBean", (Serializable) qbwtBeans.get(position).sun);
                                ctx.startActivity(intent);
                            }
                        });
                    }
                }
            }
        }
    }
}
