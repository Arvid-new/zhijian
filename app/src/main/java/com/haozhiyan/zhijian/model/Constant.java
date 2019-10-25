package com.haozhiyan.zhijian.model;

import com.haozhiyan.zhijian.bean.NewZhengGaiListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe: 存储静态常量
 */
public class Constant {

    //相册
    public static int PHOTO_RESULT_CODE = 1012;
    //相机
    public static int CAMERA_RESULT_CODE = 1013;
    //视频
    public static int VIDEO_RESULT_CODE = 1014;
    //现场检查批次
    public static int XCJC_PICI_RESULT_CODE = 1015;
    //现场添加批次
    public static int ADD_PICI_RESULT_CODE = 1016;
    //现场登记问题草稿
    public static int NOTE_PROBLEM_RESULT_CODE = 1017;
    //现场登记问题新增
    public static int ADD_PROBLEM_RESULT_CODE = 1019;
    //现场编辑批次
    public static int EDIT_PICI_RESULT_CODE = 1018;
    //问题详情改变更新
    public static int PROBLEM_DETAIL_RESULT_CODE = 1020;

    //请求码值,用于判断请求码类型
    public static int REQUEST_CODE = 0;
    //标段
    public static int BIAO_DUAN_RESULT_CODE = 1018;
    //检查人1   负责人2   抄送人3   建设单位验收人4  复验人5
    public static int REN_TYPE = 1;
    //检查人code
    public static int JIAN_CHA_REN_CODE = 2011;
    //负责人code
    public static int FU_ZE_REN_CODE = 2012;
    //抄送人code
    public static int CHAO_SONG_REN_CODE = 2013;
    //建设单位验收人
    public static int Jian_She = 2016;
    //复验人
    public static int FU_YAN_REN_CODE = 2014;
    //描述列表
    public static int DESC_LIST_CODE = 2015;
    public static int XCJC_REASON_CODE = 2019;

    // 选择描述1   责任单位2   抄送人3  标段4
    public static int OTHER_REN_TYPE = 1;
    //项目部位选择,登记项目问题里面
    public static final int PLACE_CODE = 3011;
    public static final int JIAN_CHA_XIANG_CODE = 3012;
    //photoTag    1是可以使用相册 0是不可以
    public static String photoTag = "0";

    //项目id   (接口返回的地块id是pkId)
    public static int projectId = 0;//地块id
    public static String projectName = "";//项目名称+地块名称
    public static String diKuaiName = "";//地块名称
    public static int parentProjectId = 0;//项目id
    public static String parentProjectName = "";//项目名称

    //材料验收 使用部位是否必填  0选填，1必填
    public static String partWhetherIdentifying = "0";


    public static final int TYPE_BRAND = 1;//  level 1
    public static final int TYPE_TYPE = 2;//  level 2
    public static final int TYPE_SPECIfICATION = 3; //level 3

    public static String userCookie = "";

    public static int PERSONAL_INFO = 3003;

    public static List<NewZhengGaiListBean.ListBean.ListAbarbeitungBean> draftList = new ArrayList<>();

    //工作台选择地块后刷新待办页面
    public static final  String REFRESH_DAIBAN = "refreshDaiban";
}
