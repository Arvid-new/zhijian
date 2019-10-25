package com.haozhiyan.zhijian.widget.adapterEncapsulation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.haozhiyan.zhijian.utils.ListUtils;

import java.util.List;

/**
 * @author Dana.Kai
 * CreateTime on 2018/2/28.
 * @Describe CommonAdapter
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    public Context mContext;

    public List<T> list;

    public int layoutId;

    public CommonAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    public CommonAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size() == 0 ? 1 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommonViewHolder holder = CommonViewHolder.get(mContext, convertView, position, layoutId, parent);
//        try {
            if(ListUtils.listEmpty(list)){
                convert(holder, list.get(position), position);
                return holder.getConvertView();
            }else{
                return parent.getRootView();
            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    //这个就是留给具体Adapter实现的方法
    public abstract void convert(CommonViewHolder viewHolder, T data, int position);


}
