package com.haozhiyan.zhijian.widget.adapterEncapsulation;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

/**
 * @author WangZhenKai
 * CreateTime on 2018/2/28.
 * @Describe CommonViewHolder 封装
 */
public class CommonViewHolder {

    public HashMap<Integer, View> map;
    public View mConvertView;

    public CommonViewHolder(Context context, int position, int layoutId, ViewGroup parent) {
        map = new HashMap<Integer, View>();
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
    }

    public static CommonViewHolder get(Context context, View convertView, int position, int layoutId, ViewGroup parent) {
        if (convertView == null) {
            return new CommonViewHolder(context, position, layoutId, parent);
        } else {
            return (CommonViewHolder) convertView.getTag();
        }
    }

    public <T extends View> T getView(int viewId) {
        View view = map.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            map.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return mConvertView;
    }
}
