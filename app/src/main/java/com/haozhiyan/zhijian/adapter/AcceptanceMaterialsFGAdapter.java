package com.haozhiyan.zhijian.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AcceptanceMaterialsFGAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList = new ArrayList<Fragment>();
    private List<String> titles = new ArrayList();

    public AcceptanceMaterialsFGAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setmList(List<Fragment> mList, List<String> titles) {
        this.mList = mList;
        this.titles = titles;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }



    public void refreshTitle(List<String> titleList) {
        titles = titleList;
        notifyDataSetChanged();
    }

}
