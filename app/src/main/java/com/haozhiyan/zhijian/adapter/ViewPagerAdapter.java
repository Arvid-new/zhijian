package com.haozhiyan.zhijian.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private List<View> views;
    private String[] titles;
    private Context context;

    public ViewPagerAdapter(List<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return titles[position];
        } catch (Exception e) {
            return "";
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        try {
            container.addView(views.get(position));
            return views.get(position);
        } catch (Exception e) {
            return super.instantiateItem(container, position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        try {
            //container.removeView(views.get(position));
            container.removeView((View) object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeTitle(String[] titleArray) {
        this.titles = titleArray;
        notifyDataSetChanged();
    }
}
