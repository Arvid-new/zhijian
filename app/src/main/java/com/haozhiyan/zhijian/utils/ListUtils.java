package com.haozhiyan.zhijian.utils;

import com.haozhiyan.zhijian.bean.ItemBean;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/5.
 * Describe: Ydzj_project
 */
public class ListUtils {

    /**
     * @param yuanShiList 被搜索列表
     * @param data        搜索的字符串
     * @return 返回搜索结果列表数据
     */
    public static List<String> getDataSub(List<String> yuanShiList, List<String> searchList, String data) {
        for (int i = 0; i < yuanShiList.size(); ++i) {
            if (yuanShiList.get(i).contains(data)) {
                searchList.add(yuanShiList.get(i));
            }
        }
        return searchList;
    }

    public static List<ItemBean> getDataSubs(List<ItemBean> yuanShiList, List<ItemBean> searchList, String data) {
        for (int i = 0; i < yuanShiList.size(); ++i) {
            if (yuanShiList.get(i).name.contains(data)) {
                searchList.add(yuanShiList.get(i));
            }
        }
        return searchList;
    }

    /**
     * @param list
     * @return 判断List集合是否为空
     */
    public static boolean listEmpty(List list) {
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * @param array
     * @return 判断json数组是否为空
     */
    public static boolean JSONArrayEmpty(JSONArray array) {
        if (array != null && array.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //list转数组之for循环(类型单一)
    public static String[] listToArrayByFor(List<String> list) {
        if (listEmpty(list)) {
            String[] array = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = list.get(i);
            }
            return array;
        }
        return null;
    }

    //list转数组之toArray()
    public static String[] listToArray(List<String> list) {
        if (listEmpty(list)) {
            String[] array = list.toArray(new String[list.size()]);
            return array;
        }
        return null;
    }

    //数组转List
    public static List arrayToListByFor(String[] arrays) {
        if (arrays != null && arrays.length > 0) {
            List<String> list = new ArrayList<>();
            for (String str : arrays) {
                list.add(str);
            }
            return list;
        }
        return null;
    }

    //数组转List2
    public static List arrayToList(String[] arrays) {
        if (arrays != null && arrays.length > 0) {
            ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arrays));
            return arrayList;
        }
        return null;
    }

    public static List<String> stringToList(String string) {
        if (!StringUtils.isEmpty(string)) {
            if (string.contains(",")) {
                String[] result = string.split(",");
                List<String> list = new ArrayList<>();
                for (String value : result) {
                    list.add(value);
                }
                return list;
            } else {
                List<String> list = new ArrayList<>();
                list.add(string);
                return list;
            }
        }
        return null;
    }

    public static List<String> stringToList(String string, String splitChar) {
        if (!StringUtils.isEmpty(string)) {
            String[] result = string.split(splitChar);
            List<String> list = new ArrayList<>();
            for (String value : result) {
                list.add(value);
            }
            return list;
        }
        return null;
    }

    public static boolean arrayEmpty(JSONArray array) {
        if (array != null && array.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static JSONArray saveDataToJSONArray(String json) {
        if (!StringUtils.isEmpty(json)) {
            List<String> list = ListUtils.stringToList(json, ";");
            JSONArray array = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                try {
                    JSONObject object = new JSONObject(list.get(i));
                    array.put(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            for (int j = 0; j < array.length(); j++) {
                LogUtils.print("打印array== " + array.optJSONObject(j));
            }
            return array;
        } else {
            return null;
        }
    }
    public static List<File> getFileList(List<LocalMedia> list) {
        List<File> newList = new ArrayList<>();
        if (listEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                newList.add(new File(list.get(i).getPath()));
            }
        }
        return newList;
    }
}
