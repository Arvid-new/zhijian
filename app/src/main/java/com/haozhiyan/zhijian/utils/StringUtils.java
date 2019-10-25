package com.haozhiyan.zhijian.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.model.Constant;
import com.luck.picture.lib.entity.LocalMedia;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by Wangzhenkai at 2019/3/26
 */
public class StringUtils {

    /**
     * 验证手机号码
     *
     * @param phoneNumber 手机号码
     * @return boolean
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        Pattern pattern = Pattern.compile("^1[0-9]{10}$");
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }


    /**
     * 将分转成整数
     */
    public static String Inte(String money) {
        if (isNumer(money)) {
            double a = Double.parseDouble(money);
            double b = a / 100;
            DecimalFormat fnum = new DecimalFormat("#");
            String dd = fnum.format(b);
            return dd;
        }
        return "";
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumer(String str) {
        if (null != str) {
            for (int i = str.length(); --i >= 0; ) {
                if (!Character.isDigit(str.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 将分转成元
     */
    public static String fen(String money) {
        if (isNumer(money)) {
            double a = Double.parseDouble(money);
            double b = a / 100;
            DecimalFormat fnum = new DecimalFormat("##0.00");
            String dd = fnum.format(b);
            return dd;
        }
        return "";
    }

    /**
     * 将毫克转成克
     */
    public static String weight(String money) {
        if (isNumer(money)) {
            double a = Double.parseDouble(money);
            double b = a / 1000;
            DecimalFormat fnum = new DecimalFormat("##0.000");
            String dd = fnum.format(b);
            return dd;
        }
        return "";
    }


    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        try {
            if (value != null && !"".equalsIgnoreCase(value.trim()) && !"null".equalsIgnoreCase(value.trim())) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断多个字符串是否相等，如果其中有一个为空字符串或者null，则返回false，只有全相等才返回true
     */
    public static boolean isEquals(String... agrs) {
        String last = null;
        for (int i = 0; i < agrs.length; i++) {
            String str = agrs[i];
            if (isEmpty(str)) {
                return false;
            }
            if (last != null && !str.equalsIgnoreCase(last)) {
                return false;
            }
            last = str;
        }
        return true;
    }

    // 校验Tag Alias 只能是数�?,英文字母和中�? ^[0-9a-zA-Z_!@#$&*+=.|]+$
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 验证密码是否是纯数字或纯字母
     *
     * @param s
     * @return
     */
    public static boolean isNumberOrLetter(String s) {
        Pattern p = Pattern.compile("^[0-9A-Za-z]{6,16}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 验证是否是数字字母组合
     *
     * @param s
     * @return
     */
    public static boolean isNumberLetter(String s) {
        Pattern p = Pattern.compile("^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,})$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * 格式化金额
     *
     * @param f
     * @return
     */
    public static String priceFormat(double f) {
        String str = "";
        // 构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        // format 返回的是字符串
        str = decimalFormat.format(f);
        return str;
    }

    //把String转化为float
    public static float convertToFloat(String number, float defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Float.valueOf(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    //把String转化为double
    public static double convertToDouble(String number, double defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(number);
        } catch (Exception e) {
            return defaultValue;
        }

    }

    //把String转化为double
    public static double convertToDouble(String number) {
        if (TextUtils.isEmpty(number)) {
            return 0.00;
        }
        try {
//            DecimalFormat df = new DecimalFormat("0.00");
//            df.format(number);
            return Double.parseDouble(number);
        } catch (Exception e) {
            return 0.00;
        }
    }

    public static String doubleFormat(double number) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            String s = df.format(number);
            if (s.endsWith(".00")) {
                s = s.replace(".00", "");
            }
            return s;
        } catch (Exception e) {
            return Double.toString(number);
        }
    }


    /**
     * double 精准加法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double DoubleAdd(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * double 精准减法
     *
     * @param d1
     * @param d2
     * @return
     */
    public static double DoubleSub(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    //把String转化为int
    public static int convertToInt(String number, int defaultValue) {
        if (TextUtils.isEmpty(number)) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(number);
        } catch (Exception e) {
            return defaultValue;
        }
    }

//    public static String getString(String str){
//        //Pattern pat = Pattern.compile("#.*?#");
//        Pattern pat = Pattern.compile("#[\\p{Print}\\p{InCJKUnifiedIdeographs}&&[^#]]+#");
//        Matcher mat = pat.matcher(str);
//        if(mat.matches()){
//            mat.group(1);
//            return mat.toMatchResult().toString();
//        }
//        return "no Result";
//    }

    /**
     * 加密手机号
     *
     * @param phone
     * @return
     */
    public static String encryptionPhone(String phone) {
        String newPhone = "";
        if (phone != null && !phone.equals("")) {
            if (checkPhoneNumber(phone)) {
                String s1 = phone.substring(0, 3);
                String s2 = phone.substring(7, 11);
                newPhone = s1 + "****" + s2;
            } else {
                newPhone = phone;
            }
            return newPhone;
        } else {
            return "";
        }
    }

    /**
     * 加密银行卡号
     *
     * @param idCard
     * @return
     */
    public static String encryptionIdCard(String idCard) {
        if (idCard != null && !idCard.equals("")) {
            String s1 = "", s2 = "", newCard = "";
            if (idCard.length() == 18) {
                s1 = idCard.substring(0, 3);
                s2 = idCard.substring(14, 18);
                newCard = s1 + "***********" + s2;
            } else if (idCard.length() == 19) {
                s1 = idCard.substring(0, 3);
                s2 = idCard.substring(15, 19);
                newCard = s1 + "************" + s2;
            }
            return newCard;
        } else {
            return "";
        }
    }

    /**
     * 格式化时间戳-时、分、秒
     *
     * @param l
     * @return
     */
    public static String formatLongToTime(Long l) {
        int hour = 0;
        int minute = 0;
        int second = 0;
        String time = "";
        String hourStr = "";
        String minuteStr = "";
        String secondStr = "";
        second = l.intValue();
        if (second > 60) {
            minute = second / 60;         //取整
            second = second % 60;         //取余
        }

        if (minute > 60) {
            hour = minute / 60;
            minute = minute % 60;
        }

        //String
        if (hour >= 0 && hour < 10) {
            hourStr = "0" + hour;
            time = hourStr + "：" + minuteStr + "：" + secondStr;
        } else if (minute >= 0 && minute < 10) {
            minuteStr = "0" + minute;
            time = hourStr + "：" + minuteStr + "：" + secondStr;
        } else if (second >= 0 && second < 10) {
            secondStr = "0" + second;
            time = hourStr + "：" + minuteStr + "：" + secondStr;
        } else {
            time = hour + "：" + minute + "：" + second;
        }
        return time;
    }

    /**
     * 格式化时间   "yyyy-MM-dd HH:mm:ss"
     *
     * @param time
     * @return
     */
    public static String time(Long time, String format) {
        SimpleDateFormat sFormat = new SimpleDateFormat(format);
        Date d1 = new Date(time);
        String result = sFormat.format(d1);
        return result;
    }


    public static String json(String response) {
        if (response != null && !response.equals("")) {
            if (response.contains("data")) {//有数据对象
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject objectData = object.getJSONObject("data");
                    if (objectData != null && !objectData.toString().equals("")) {
                        return response;
                    } else {
                        return "status" + ":";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            return "";
        }

        return "";
    }

    public static boolean myJson(String response) {
        if (response != null && !response.equals("")) {
            try {
                JSONObject object = new JSONObject(response);
                if (object != null) {
                    JSONArray objectData = object.optJSONArray("list");
                    if (ListUtils.arrayEmpty(objectData)) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            return false;
        }
        return false;
    }


    public static String getUnitMonet(String money) {
        if (money != null) {
            if (isNumer(money)) {
                int ml = Integer.valueOf(money);
                if (ml >= 1000 && ml < 10000) {
                    return ml / 1000 + "千";
                } else if (ml >= 10000 && ml < 100000000) {
                    return ml / 10000 + "万";
                } else {
                    return money;
                }
            }
        } else {
            return "0";
        }
        return money;
    }

    public static String format(int time) {
        if (time > 0 && time <= 9) {
            return "0" + time;
        } else {
            return time + "";
        }
    }

    /**
     * 加密银行卡号
     *
     * @param bankCardNo
     * @return
     */
    public static String encryptionBankCard(String bankCardNo) {
        bankCardNo = bankCardNo.replaceAll(" ", "");
        String s, newCard = "";
        if (bankCardNo != null && !bankCardNo.equals("") && bankCardNo.length() > 0) {
            if (bankCardNo.length() == 16) {
                s = bankCardNo.substring(12, 15);
                newCard = "****\t****\t****\t" + s;
            } else if (bankCardNo.length() == 17) {
                s = bankCardNo.substring(13, 16);
                newCard = "****\t****\t****\t*\t" + s;
            } else if (bankCardNo.length() == 18) {
                s = bankCardNo.substring(13, 17);
                newCard = "****\t****\t****\t*\t" + s;
            } else if (bankCardNo.length() == 19) {
                s = bankCardNo.substring(14, 18);
                newCard = "****\t****\t****\t*\t" + s;
            }
            return newCard;
        } else {
            return bankCardNo;
        }
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    /**
     * 获取assets中json文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 提取出城市或者县
     *
     * @param city
     * @param district
     * @return
     */
    public static String extractLocation(final String city, final String district) {
        return district.contains("县") ? district.substring(0, district.length() - 1) : city.substring(0, city.length() - 1);
    }

    public static String title() {
        if (Constant.REN_TYPE == 1) {
            return "检查人";
        }
        if (Constant.REN_TYPE == 2) {
            return "负责人";
        }
        if (Constant.REN_TYPE == 3) {
            return "抄送人";
        }
        if (Constant.REN_TYPE == 4) {
            return "建设单位验收人";
        }
        if (Constant.REN_TYPE == 5) {
            return "复验人";
        }
        return "选择";
    }

    public static String title2() {
        if (Constant.OTHER_REN_TYPE == 1) {
            return "选择描述";
        }
        if (Constant.OTHER_REN_TYPE == 2) {
            return "责任单位";
        }
        if (Constant.OTHER_REN_TYPE == 3) {
            return "抄送人";
        }
        if (Constant.OTHER_REN_TYPE == 4) {
            return "选择标段";
        }
        if (Constant.OTHER_REN_TYPE == 5) {
            return "建设单位验收人";
        }
        return "选择";
    }

    /**
     * @param list
     * @param separator
     * @return 返回字符char分割拼接后的字符串
     */
    public static String listToStrByChar(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(separator);
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }
    /**
     * @param list
     * @param separator
     * @return 返回字符char分割拼接后的字符串
     */
    public static String medialistToStrByChar(List<LocalMedia> list, char separator) {
        try {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                sb.append(list.get(i).getPath()).append(separator);
            }
            return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param list
     * @param separator
     * @return 返回字符串String分割拼接后的字符串
     */
    public static String listToStrByStr(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == null || list.get(i).equals("null")) {
                continue;
            }
            sb.append(list.get(i));
            if (i < list.size() - 1) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static String listToStr(List<ItemBean> list, int type) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (type == 1) {
                sb.append(list.get(i).peopleuser).append(',');
            }
            if (type == 2) {
                sb.append(list.get(i).userId).append(',');
            }
        }
        return list.isEmpty() ? "" : sb.toString().substring(0, sb.toString().length() - 1);
    }

    /**
     * @param content
     * @return 换行的数据
     */
    public static String enterStr(String content) {
        String result;
        if (isEmpty(content)) {
            return "暂无内容";
        } else {
            if (content.contains("\n") || content.contains("'/\n'")) {
                result = content.replaceAll("\n", "\n\n\n");
            } else {
                result = content;
            }
            return result;
        }
    }

    public static String getStatus(String s) {
        if (s.equals("1")) {
            return "待整改";
        } else if (s.equals("2")) {
            return "待复验";
        } else if (s.equals("3")) {
            return "异常关闭";
        } else if (s.equals("4")) {
            return "已通过";
        }
        return "错误";
    }

    public static int getTextColor(String s) {
        if (s.equals("1")) {
            return UiUtils.getColor(R.color.red2);
        } else if (s.equals("2")) {
            return UiUtils.getColor(R.color.yellow);
        } else if (s.equals("3")) {
            return UiUtils.getColor(R.color.gray8);
        } else if (s.equals("4")) {
            return UiUtils.getColor(R.color.green2);
        }
        return UiUtils.getColor(R.color.black2);
    }

    //去重
    public static List removeDuplicate(List list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = list.size() - 1; j > i; j--) {
                if (list.get(j).equals(list.get(i))) {
                    list.remove(j);
                }
            }
        }
        return list;
    }

    //删除ArrayList中重复元素，保持顺序
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
            Object element = iterator.next();
            if (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    public static String H5Cookie(String cacheCookie){
        String cookies[] = cacheCookie.split(";");
        String cookie = cookies[0].replace("JSESSIONID=", "");
        return cookie;
    }
}
