package com.haozhiyan.zhijian.utils;

import com.haozhiyan.zhijian.R;
import com.haozhiyan.zhijian.bean.ItemBean;
import com.haozhiyan.zhijian.bean.JCDraftBean;
import com.haozhiyan.zhijian.bean.WorkBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/25.
 * Describe:
 */
public class DataTest {

    public static List<WorkBean> work() {
        List<WorkBean> list = new ArrayList<>();
        String[] ss = new String[]{"现场检查", "实测实量", "工序移交", "材料验收", "报表", "样板管理", "常用文档", "形象进度", "进度管理", "实测巡检", "工程亮点", "待办", "专项巡检"};
        for (int i = 0; i < ss.length; i++) {
            WorkBean bean = new WorkBean();
            bean.setName(ss[i]);
            bean.setIcon(workIcon[i]);
            list.add(bean);
        }
        return list;
    }

    public static List<String> workNotice() {
        List<String> list = new ArrayList<>();
        list.add("证券时报e公司讯");
        list.add("证券时报e公司讯,深圳属国资国企");
        return list;
    }

    public static List<String> newProject() {
        List<String> list = new ArrayList<>();
        list.add("预埋阶段线盒盖板");
        list.add("线管排列");
        list.add("可调拉式悬挂手架");
        list.add("预埋阶段线盒盖板");
        list.add("线管排列");
        list.add("可调拉式悬挂手架");
        list.add("可调拉式悬挂手架");
        list.add("可调拉式悬挂手架");
        list.add("可调拉式悬挂手架");
        list.add("可调拉式悬挂手架");
        return list;
    }

    public static String imgUrl = "https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg";
    public static String imgUr2 = "https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg";
    public static String img = "https://cdn.duitang.com/uploads/item/201406/20/20140620184958_JNKck.thumb.700_0.jpeg";
    public static String[] formsName = new String[]{"现场检查", "员工工作量", "实测实量", "工序移交", "材料验收", "项目综合概览", "专项巡检", "进度管理"};
    public static int[] formsIcon = new int[]{R.drawable.forms_item_xcjc_icon, R.drawable.forms_item_yggzl_icon, R.drawable.forms_item_scsl_icon, R.drawable.forms_item_gxyj_icon
            , R.drawable.forms_item_clys_icon, R.drawable.forms_item_xmzhyl_icon, R.drawable.forms_item_zhuanxiangxj_icon, R.drawable.forms_item_jinduguanli_icon};
    public static int[] formsBack = new int[]{R.drawable.home_forms_item_yellow_back, R.drawable.home_forms_item_red_back, R.drawable.home_forms_item_sky_blue_back,
            R.drawable.home_forms_item_green_back, R.drawable.home_forms_item_pink_back, R.drawable.home_forms_item_orange_back,
            R.drawable.home_forms_item_purple_back, R.drawable.home_forms_item_blue_back};
    public static String[] daibanName = new String[]{"现场检查", "实测实量", "工序移交", "材料验收", "样板管理", "实测巡检", "员工工作量"};
    public static int[] daibanBack = new int[]{R.drawable.daiban_xcjc_d, R.drawable.daiban_scsl_d, R.drawable.daiban_gxyj_d, R.drawable.daiban_clys_d
            , R.drawable.daiban_ybgl_d, R.drawable.daiban_scxc_d, R.drawable.daiban_yggzl_d};
    public static int[] daibanIcon = new int[]{R.drawable.forms_item_xcjc_icon, R.drawable.forms_item_scsl_icon, R.drawable.forms_item_gxyj_icon, R.drawable.forms_item_clys_icon
            , R.drawable.forms_itemybgl_icon, R.drawable.daiban_scxc_icon, R.drawable.forms_item_yggzl_icon};
    public static int[] workIcon = new int[]{R.drawable.work_icon_xcjc, R.drawable.work_icon_scsl, R.drawable.work_icon_gxyj, R.drawable.work_icon_clys,
            R.drawable.work_icon_baobiao, R.drawable.work_icon_ybgl, R.drawable.work_icon_often_word, R.drawable.work_icon_xxjd,
            R.drawable.work_icon_jdgl, R.drawable.work_icon_scxj, R.drawable.work_icon_gcld, R.drawable.work_icon_db, R.drawable.work_icon_zxxj};

    public static String videoUrl = "http://www.jmzsjy.com/UploadFile/微课/地方风味小吃——宫廷香酥牛肉饼.mp4";
    public static String videoUrl2 = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";

    public static List<String> piciList() {
        List<String> list = new ArrayList<>();
        list.add("测试测试");
        list.add("12121212");
        list.add("56565656");
        list.add("小测工程测试");
        return list;
    }

    public static List<String> daibanList() {
        List<String> list = new ArrayList<>();
        for (String value : daibanName) {
            list.add(value);
        }
        return list;
    }

    public static List<String> pici() {
        List<String> list = new ArrayList<>();
        list.add("【乙方】柳岩");
        list.add("何海洋【中天工程师】");
        list.add("焦武侠【中天工程师】");
        return list;
    }

    public static List<String> imageList() {
        List<String> list = new ArrayList<>();
        list.add(DataTest.img);
        list.add(DataTest.img);
        list.add("http://mil.eastday.com/m/20091205/images/01645939.jpg");
        list.add("https://att2.citysbs.com/jiaju/2014/04/02/11/middle_640x1041-114714_14461396410434855_2194d7a1714338e80fb4a488f3b6066e.jpg");
        return list;
    }

    public static ArrayList<String> imageL() {
        ArrayList<String> list = new ArrayList<>();
        for (String url : imageList()) {
            list.add(url);
        }
        return list;
    }

    public static String angleJson = "{\n" +
            "    \"code\": 1000,\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"dateTitle\": \"施工单位人员\",\n" +
            "            \"logDOList\": [\n" +
            "                {\n" +
            "                    \"deviceName\": \"张三【施工单位】\",\n" +
            "                    \"fullName\": \"张三\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"王五【施工单位】\",\n" +
            "                    \"fullName\": \"王五\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"李留【施工单位】\",\n" +
            "                    \"fullName\": \"李留\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"王五【施工单位】\",\n" +
            "                    \"fullName\": \"王五\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"李留【施工单位】\",\n" +
            "                    \"fullName\": \"李留\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"王五【施工单位】\",\n" +
            "                    \"fullName\": \"王五\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20180327/20180327120003_87395.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"dateTitle\": \"监理单位人员\",\n" +
            "            \"logDOList\": [\n" +
            "                {\n" +
            "                    \"deviceName\": \"张虎【监理单位】\",\n" +
            "                    \"fullName\": \"张虎\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"张虎【监理单位】\",\n" +
            "                    \"fullName\": \"张虎\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"张虎【监理单位】\",\n" +
            "                    \"fullName\": \"张虎\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"dateTitle\": \"建设单位人员\",\n" +
            "            \"logDOList\": [\n" +
            "                {\n" +
            "                    \"deviceName\": \"刘全【建设单位】\",\n" +
            "                    \"fullName\": \"刘全\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"刘全【建设单位】\",\n" +
            "                    \"fullName\": \"刘全\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"deviceName\": \"刘全【建设单位】\",\n" +
            "                    \"fullName\": \"刘全\",\n" +
            "                    \"ghsUserId\": 0,\n" +
            "                    \"headUrl\": \"https://img.52z.com/upload/news/image/20181123/20181123144652_33462.jpg\",\n" +
            "                    \"openState\": 0,\n" +
            "                    \"openType\": 1,\n" +
            "                    \"updateTime\": \"10:22\"\n" +
            "                }\n" +
            "            ]\n" +
            "        }\n" +
            "    ],\n" +
            "    \"message\": \"成功\"\n" +
            "}";


    public static String place() {
        return "{\n" +
                "    \"code\": 1000,\n" +
                "    \"data\": [\n" +
                "        {\n" +
                "            \"title\": \"楼栋\",\n" +
                "            \"louList\": [\n" +
                "                {\n" +
                "                    \"name\": \"1栋\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"2栋\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"3栋\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"4栋\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "\t\t{\n" +
                "            \"title\": \"单元\",\n" +
                "            \"louList\": [\n" +
                "                {\n" +
                "                    \"name\": \"1单元\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"2单元\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"3单元\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"4单元\"\n" +
                "                },\n" +
                "\t\t\t\t{\n" +
                "                    \"name\": \"5单元\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"6单元\"\n" +
                "                }\n" +
                "             ]\n" +
                "        },\n" +
                "\t\t{\n" +
                "            \"title\": \"楼层\",\n" +
                "            \"louList\": [\n" +
                "                {\n" +
                "                    \"name\": \"1层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"2层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"3层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"4层\"\n" +
                "                },\n" +
                "\t\t\t\t{\n" +
                "                    \"name\": \"5层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"6层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"7层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"8层\"\n" +
                "                },\n" +
                "\t\t\t\t{\n" +
                "                    \"name\": \"9层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"10层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"11层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"12层\"\n" +
                "                },\n" +
                "\t\t\t\t{\n" +
                "                    \"name\": \"13层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"14层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"15层\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"16层\"\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "\t\t{\n" +
                "            \"title\": \"住户编号\",\n" +
                "            \"louList\": [\n" +
                "                {\n" +
                "                    \"name\": \"2205\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"2206\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"2207\"\n" +
                "                },\n" +
                "                {\n" +
                "                    \"name\": \"2208\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"message\": \"成功\"\n" +
                "}";
    }

    public static List<JCDraftBean> draftList() {
        List<JCDraftBean> draftBeanList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            JCDraftBean bean = new JCDraftBean();
            bean.setThumbUrl("");
            if (i % 2 == 0) {
                bean.setName("安全（土建）-平面布置");
                bean.setDesc("垃圾堆放问题");
            } else {
                bean.setName("未选检查项");
                bean.setDesc("石料堆积严重");
            }
            bean.setTime("2019-05-06 12:0" + i);
            draftBeanList.add(bean);
        }
        return draftBeanList;
    }

    public static String jsonJC = "{\n" +
            "    \"code\": 0,\n" +
            "    \"list\": [\n" +
            "        {\n" +
            "            \"childIns\": [\n" +
            "                {\n" +
            "                    \"childIns\": [\n" +
            "                        {\n" +
            "                            \"identifying\": \"XCJC\",\n" +
            "                            \"inspectionId\": 2795728686546944,\n" +
            "                            \"inspectionName\": \"平面布置子集\",\n" +
            "                            \"parentId\": 2795728543940608\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"identifying\": \"XCJC\",\n" +
            "                            \"inspectionId\": 2795728686546944,\n" +
            "                            \"inspectionName\": \"平面布置子集02\",\n" +
            "                            \"parentId\": 2795728543940608\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795728686546944,\n" +
            "                    \"inspectionName\": \"平面布置\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [\n" +
            "                        {\n" +
            "                            \"identifying\": \"XCJC\",\n" +
            "                            \"inspectionId\": 2795728686546944,\n" +
            "                            \"inspectionName\": \"平面布置子集\",\n" +
            "                            \"parentId\": 2795728543940608\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"identifying\": \"XCJC\",\n" +
            "                            \"inspectionId\": 2795728686546944,\n" +
            "                            \"inspectionName\": \"平面布置子集02\",\n" +
            "                            \"parentId\": 2795728543940608\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795729454104576,\n" +
            "                    \"inspectionName\": \"洞口及临边防护\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795730112610304,\n" +
            "                    \"inspectionName\": \"铭牌标识\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795730683035648,\n" +
            "                    \"inspectionName\": \"安全帽安全带\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795731207323648,\n" +
            "                    \"inspectionName\": \"基坑围护\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795732142653440,\n" +
            "                    \"inspectionName\": \"安全通道及防护棚\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795733170257920,\n" +
            "                    \"inspectionName\": \"工地大门道路交通\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795734009118720,\n" +
            "                    \"inspectionName\": \"脚手架\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795735024140288,\n" +
            "                    \"inspectionName\": \"区域分隔\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795735728783360,\n" +
            "                    \"inspectionName\": \"吊篮\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795735854612480,\n" +
            "                    \"inspectionName\": \"卸料平台\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795736609587200,\n" +
            "                    \"inspectionName\": \"施工现场厕所\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795738538967040,\n" +
            "                    \"inspectionName\": \"其它\",\n" +
            "                    \"parentId\": 2795728543940608\n" +
            "                }\n" +
            "            ],\n" +
            "            \"identifying\": \"XCJC\",\n" +
            "            \"inspectionId\": 2795728543940608,\n" +
            "            \"inspectionName\": \"安全（土建）\",\n" +
            "            \"parentId\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"childIns\": [\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795738803208192,\n" +
            "                    \"inspectionName\": \"塔吊\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795742695522304,\n" +
            "                    \"inspectionName\": \"施工电梯\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795743827984384,\n" +
            "                    \"inspectionName\": \"接地与接零保护系统\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795744452935680,\n" +
            "                    \"inspectionName\": \"配电箱、开关箱\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795745119830016,\n" +
            "                    \"inspectionName\": \"现场照明\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795745501511680,\n" +
            "                    \"inspectionName\": \"配电线路\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795746180988928,\n" +
            "                    \"inspectionName\": \"电器、装置\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795746545893376,\n" +
            "                    \"inspectionName\": \"用电档案\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795748521410560,\n" +
            "                    \"inspectionName\": \"消防设施\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795750698254336,\n" +
            "                    \"inspectionName\": \"生活区环境卫生\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [\n" +
            "                        {\n" +
            "                            \"childIns\": [\n" +
            "                                {\n" +
            "                                    \"childIns\": [],\n" +
            "                                    \"identifying\": \"XCJC\",\n" +
            "                                    \"inspectionId\": 2795751818133504,\n" +
            "                                    \"inspectionName\": \"测试1\",\n" +
            "                                    \"parentId\": 2795751621001216\n" +
            "                                }\n" +
            "                            ],\n" +
            "                            \"identifying\": \"XCJC\",\n" +
            "                            \"inspectionId\": 2795751621001216,\n" +
            "                            \"inspectionName\": \"测试\",\n" +
            "                            \"parentId\": 2795751495172096\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795751495172096,\n" +
            "                    \"inspectionName\": \"其它\",\n" +
            "                    \"parentId\": 2795738664796160\n" +
            "                }\n" +
            "            ],\n" +
            "            \"identifying\": \"XCJC\",\n" +
            "            \"inspectionId\": 2795738664796160,\n" +
            "            \"inspectionName\": \"安全（安装）\",\n" +
            "            \"parentId\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"childIns\": [\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795753386803200,\n" +
            "                    \"inspectionName\": \"主体工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795757451083776,\n" +
            "                    \"inspectionName\": \"二次结构工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795760621977600,\n" +
            "                    \"inspectionName\": \"粉刷工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795767483858944,\n" +
            "                    \"inspectionName\": \"公共区域装饰工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795767731322880,\n" +
            "                    \"inspectionName\": \"外墙保温工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795768108810240,\n" +
            "                    \"inspectionName\": \"外墙涂料工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795768486297600,\n" +
            "                    \"inspectionName\": \"门窗工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795768737955840,\n" +
            "                    \"inspectionName\": \"入户门\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795768989614080,\n" +
            "                    \"inspectionName\": \"单元门\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795769241272320,\n" +
            "                    \"inspectionName\": \"防火门\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795769492930560,\n" +
            "                    \"inspectionName\": \"屋面工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795769744588800,\n" +
            "                    \"inspectionName\": \"石材幕墙工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795770038190080,\n" +
            "                    \"inspectionName\": \"地库划线工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795770289848320,\n" +
            "                    \"inspectionName\": \"金刚砂地坪工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795770541506560,\n" +
            "                    \"inspectionName\": \"防水工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795770667335680,\n" +
            "                    \"inspectionName\": \"回填土工程\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795770839302144,\n" +
            "                    \"inspectionName\": \"其它\",\n" +
            "                    \"parentId\": 2795753260974080\n" +
            "                }\n" +
            "            ],\n" +
            "            \"identifying\": \"XCJC\",\n" +
            "            \"inspectionId\": 2795753260974080,\n" +
            "            \"inspectionName\": \"质量（土建）\",\n" +
            "            \"parentId\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"childIns\": [\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795772357640192,\n" +
            "                    \"inspectionName\": \"水暖安装工程\",\n" +
            "                    \"parentId\": 2795770960936960\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795777382416384,\n" +
            "                    \"inspectionName\": \"地库安装工程\",\n" +
            "                    \"parentId\": 2795770960936960\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795777562771456,\n" +
            "                    \"inspectionName\": \"电气设备工程\",\n" +
            "                    \"parentId\": 2795770960936960\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795780104519680,\n" +
            "                    \"inspectionName\": \"智能化工程\",\n" +
            "                    \"parentId\": 2795770960936960\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795780301651968,\n" +
            "                    \"inspectionName\": \"其它\",\n" +
            "                    \"parentId\": 2795770960936960\n" +
            "                }\n" +
            "            ],\n" +
            "            \"identifying\": \"XCJC\",\n" +
            "            \"inspectionId\": 2795770960936960,\n" +
            "            \"inspectionName\": \"质量（安装）\",\n" +
            "            \"parentId\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"childIns\": [\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795780570087424,\n" +
            "                    \"inspectionName\": \"产品设计缺陷反馈\",\n" +
            "                    \"parentId\": 2795780444258304\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795780859494400,\n" +
            "                    \"inspectionName\": \"施工工艺缺陷反馈\",\n" +
            "                    \"parentId\": 2795780444258304\n" +
            "                }\n" +
            "            ],\n" +
            "            \"identifying\": \"XCJC\",\n" +
            "            \"inspectionId\": 2795780444258304,\n" +
            "            \"inspectionName\": \"工程（质量/设计）缺陷\",\n" +
            "            \"parentId\": 0\n" +
            "        },\n" +
            "        {\n" +
            "            \"childIns\": [\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795781153095680,\n" +
            "                    \"inspectionName\": \"景观基层\",\n" +
            "                    \"parentId\": 2795780997906432\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795783208304640,\n" +
            "                    \"inspectionName\": \"景观土建\",\n" +
            "                    \"parentId\": 2795780997906432\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795785347399680,\n" +
            "                    \"inspectionName\": \"景观面层\",\n" +
            "                    \"parentId\": 2795780997906432\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795787067064320,\n" +
            "                    \"inspectionName\": \"景观装饰\",\n" +
            "                    \"parentId\": 2795780997906432\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795787566186496,\n" +
            "                    \"inspectionName\": \"绿化\",\n" +
            "                    \"parentId\": 2795780997906432\n" +
            "                },\n" +
            "                {\n" +
            "                    \"childIns\": [],\n" +
            "                    \"identifying\": \"XCJC\",\n" +
            "                    \"inspectionId\": 2795788577013760,\n" +
            "                    \"inspectionName\": \"样板展示区\",\n" +
            "                    \"parentId\": 2795780997906432\n" +
            "                }\n" +
            "            ],\n" +
            "            \"identifying\": \"XCJC\",\n" +
            "            \"inspectionId\": 2795780997906432,\n" +
            "            \"inspectionName\": \"质量（景观）\",\n" +
            "            \"parentId\": 0\n" +
            "        }\n" +
            "    ],\n" +
            "    \"msg\": \"success\"\n" +
            "}";


    public static List getSd() {
        List<ItemBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ItemBean bean = new ItemBean((i + 1) + "0" + i, "888", 666);
            list.add(bean);
        }
        return list;
    }

    public static void main(String args[]) {

        testData();
    }

    private static void testData() {

        String fileStr = "http://192.168.110.66:8080/ydzj-admin/images/10d69262c51c4ebba7b7d74884e8edfc.jpg";
        File file = new File(fileStr);
        System.out.println("file==" + file.getName());

//        float size = 100;
//        float size2 = 150;
//        float size3 = 200;
//        LogUtils.print("100px转dp=="+DensityUtil.px2dip(UiUtils.getContext(),size));
//        LogUtils.print("150px转dp=="+DensityUtil.px2dip(UiUtils.getContext(),size2));
//        LogUtils.print("200px转dp=="+DensityUtil.px2dip(UiUtils.getContext(),size3));


        String  json = "{\"status\":\"0\",\"ids\": \"4\",\"top\":\"118\",\"left\":\"210\",\"height_ScNum\":\"5\"\"height_desNum\":\"NaN\"\"width_ScNum\":\"NaN\"\"width_desNum\":\"NaN\"\"passRate\": \"0.00\"};{\"status\":\"0\",\"ids\": \"4\",\"top\":\"79\",\"left\":\"155\",\"height_ScNum\":\"5\"\"height_desNum\":\"NaN\"\"width_ScNum\":\"NaN\"\"width_desNum\":\"NaN\"\"passRate\": \"0.00\"};";
        List<String> list = ListUtils.stringToList(json,";");
//        JSONArray array = null;
//        try {
//            array = new JSONArray(json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        for (int i = 0; i < list.size(); i++) {
            //array.put(list.get(i));
            System.out.println("打印list== "+list.get(i));
        }
//        for (int j = 0; j < array.length(); j++) {
//             System.out.println("打印array== "+array.optJSONObject(j));
//        }

//        String time = "2019-05-28 15:03:50";
//        //Date date = new Date(time);
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//       ParsePosition pos = new ParsePosition(0);
//       Date strtodate = formatter.parse(time, pos);
//        System.out.println("Date==" + strtodate.toString());
//        System.out.println("Date222==" + strtodate.getTime());

//        List<String> ff = new ArrayList<>();
//        ff.add("111");
//        ff.add("222");
//        ff.add("333");
//        ff.add("444");
//        ff.add("555");
//        String[] fd = ListUtils.listToArray(ff);
//        for (int i = 0; i < fd.length; i++) {
//            System.out.println("fd数组值==" + fd[i]);
//        }
//        System.out.println("fd数组值==" + ff.toString());
//
//        List<String> gg = new ArrayList<>();
//        gg.add("/storage/emulated/0/Download/timg-1.jpg");
//        gg.add("/storage/emulated/0/Download/timg.jpg");
//        gg.add("/storage/emulated/0/Pictures/23232.jpeg");
//        gg.add("/storage/emulated/0/Pictures/23232.jpeg");
//        gg.add("/storage/emulated/0/Pictures/23232.jpeg");
//        String [] hh = ListUtils.listToArray(gg);
//        for (int i = 0; i < hh.length; i++) {
//            System.out.println("hh数组值=="+hh[i]);
//        }


//        try {
//            JSONObject wangxiaoer = null;
//            if (wangxiaoer == null) {
//                wangxiaoer = new JSONObject();
//            }
//            wangxiaoer.put("name", "王小二");
//            wangxiaoer.put("age", 25.2);
//            wangxiaoer.put("birthday", "1990-01-01");
//            wangxiaoer.put("school", "蓝翔");
//            wangxiaoer.put("major", new String[]{"理发", "挖掘机"});
//            wangxiaoer.put("has_girlfriend", false);
//            wangxiaoer.put("car", "");
//            wangxiaoer.put("house", "");
//            System.out.println(wangxiaoer.toString());//输出JSON格式的wangxiaoer数据
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    public static List<String> data() {
        List<String> ff = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ff.add(i + "101");
        }
        return ff;
    }

    public static List<String> dataf() {
        List<String> ff = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i == 1) {
                continue;
            }
            ff.add((i + (-1)) + "层");
        }
        return ff;
    }
}
