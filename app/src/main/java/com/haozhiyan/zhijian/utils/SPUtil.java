package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtil {
    private Context context;
    private static final String GXYj_DATA = "gongxuyijiao";

    private SPUtil(Context context) {
        this.context = context;
    }

    public synchronized static SPUtil get(Context context) {
        SPUtil spUtil = new SPUtil(context);
        return spUtil;
    }

    public void save(String key, String value) {
        SharedPreferences sp = context.getSharedPreferences(GXYj_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(key, value);
        ed.commit();
    }
    public void remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(GXYj_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.remove(key);
        ed.commit();
    }
    public void clear() {
        SharedPreferences sp = context.getSharedPreferences(GXYj_DATA, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.clear();
        ed.commit();
    }

    public String get(String key) {
        SharedPreferences sp = context.getSharedPreferences(GXYj_DATA, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public void saveSectionId(String sectionId) {
        try {
            SharedPreferences sp = context.getSharedPreferences(GXYj_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.putString("sectionId", sectionId);
            ed.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSectionId() {
        SharedPreferences sp = context.getSharedPreferences(GXYj_DATA, Context.MODE_PRIVATE);
        return sp.getString("sectionId", "");
    }


}
