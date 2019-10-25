package com.haozhiyan.zhijian.model;

/**
 * Created by WangZhenKai on 2019/4/22.
 * Describe: Zhijian
 */
public class ServerInterface {

    private static boolean isBaseUrl = true;
    private static String fileUrl;
    private static String serverLocal;

    private static String BaseUrl() {
        if (isBaseUrl) {//formal environment
//        
        } else {//test environment
//              serverLocal = "http://192.168.110.66:8080/ydzj-admin/";//王晨星
//            serverLocal = "http://192.168.110.248:8080/ydzj-admin/";//张艳辉
//            serverLocal = "http://192.168.110.110:8091/ydzj-admin/";
            serverLocal = "http://192.168.110.127:8080/ydzj-admin/";//李昂
//            serverLocal = "http://114.115.152.159:8083/ydzj-admin/";//测试
        }
        return serverLocal;
    }

    public static String PVUrl() {
        if (isBaseUrl) { //formal environment images
//            fileUrl = "http://ms.mienre.com/ydzj-admin/images/";
            fileUrl = "http://zj.mienre.com/images/";
        } else {  //test environment images
//             fileUrl = "http://192.168.110.66:8080/ydzj-admin/images/";
//            fileUrl = "http://192.168.110.248:8080/ydzj-admin/images/";
//            fileUrl = "http://192.168.110.110:8091/ydzj-admin/images/";
            fileUrl = "http://192.168.110.127:8080/ydzj-admin/images/";
//            fileUrl = "http://114.115.152.159:8083/ydzj-admin/images/";
        }
        return fileUrl;
    }

    //登录接口
    public static String loginApp = BaseUrl() + "sys/loginApp";
    //退出登录
    public static String logoutApp = BaseUrl() + "logoutApp";
    //工作台接口-默认显示接口
    public static String setting = BaseUrl() + "api/sys/tem/setting";
    //待办模块主页接口
    public static String daiBanSetting = BaseUrl() + "api/sys/tem/daiBanSetting";
    //现场检查检查项
    public static String inspection = BaseUrl() + "api/sys/inspection/listXCJC";
    //现场检查检查项更新接口
    public static String inspectionWtms = BaseUrl() + "api/sys/inspection/selectWTMS";
    //公告
    public static String notice = BaseUrl() + "api/news/news/list";
    //项目管理模块(楼栋信息)
    public static String projectManager = BaseUrl() + "api/pro/projectmanage/selectLandAll";
    //检查指引,包含详情数据
    public static String jcZhiYin = BaseUrl() + "api/sys/inspection/selectJCZY";
    //首页对应地产公司项目
    public static String AllItem = BaseUrl() + "api/pro/projectmanage/AllItem";
    //实测实量标段列表
    public static String selectSection = BaseUrl() + "api/sys/inspection/selectSection";
    //实测实量具体问题列表
    public static String selectSCSL = BaseUrl() + "api/sys/inspection/selectSCSL";
    //查询(实测实量)楼栋 楼层 房间信息
    public static String selectSCSLType = BaseUrl() + "api/sys/inspection/selectSCSLType";
    //新建现场检查批次
    public static String createPici = BaseUrl() + "api/sys/inspection/saveXCJCbatch";
    //查询所有现场检查批次
    //public static String queryPici = BaseUrl() + "api/sys/inspection/selectAllXCJCbatch";
    public static String queryPici = BaseUrl() + "api/sys/inspection/defaultBatchList";
    //查询现场检查批次详情
    public static String queryPiciDetail = BaseUrl() + "api/sys/inspection/selectXCJCbatchParticulars";
    //现场检查批次修改
    public static String updatePiciDetail = BaseUrl() + "api/sys/inspection/updateXCJCbatch";
    //现场检查批次删除
    public static String deletePiCi = BaseUrl() + "api/sys/inspection/deleteXCJCbatch";
    //现场检查批次关闭
    public static String closePici = BaseUrl() + "api/sys/inspection/closeXCJCbatch";
    //现场检查列表
    public static String xcjcList = BaseUrl() + "api/sys/inspection/listXCJC";
    //现场检查新增问题
    public static String xcjcAddTrouble = BaseUrl() + "api/sys/inspection/saveProblem";
    //现场检查修改问题
    public static String xcjcUpdateTrouble = BaseUrl() + "api/sys/inspection/updateProblem";
    //现场检查详情
    public static String xcjcTroubleDetail = BaseUrl() + "api/sys/inspection/infoProblem";
    //现场检查待办复验
    public static String reviewProblem = BaseUrl() + "api/sys/inspection/reviewProblem";
    //上传文件
    public static String uploadFile = BaseUrl() + "api/sys/inspection/uploadFile";
    //public static String uploadFile = "http://192.168.110.195:8080/ydzj-admin/api/sys/inspection/uploadFile";
    //查询现场检查默认批次和问题列表
    public static String queryPiCiOrTrouble = BaseUrl() + "api/sys/inspection/defaultBatch";
    //查询标段下的工序移交列表信息
    public static String listProcessOver = BaseUrl() + "api/process/processOver/listProcessOver";
    //查询工序移交下-整栋-层次-单元
    public static String listPOTowerFloorUnit = BaseUrl() + "api/process/processOver/listPOTowerFloorUnit";
    //工序移交- 提交验收申请
    public static String saveDetails = BaseUrl() + "api/gxyj/gxyjdetails/saveDetails";
    //public static String saveDetails = "http://192.168.110.33:8080/ydzj-admin/api/gxyj/gxyjdetails/saveDetails";
    //工序移交- 验收通过
    public static String saveVerificationBy = BaseUrl() + "api/gxyj/gxyjdetails/saveVerificationBy";
    //工序移交- 查看详情
    public static String selectDetails = BaseUrl() + "api/gxyj/gxyjdetails/selectDetails";
    //工序移交- 查询某一条整改问题
    public static String selectAbarbeitung = BaseUrl() + "api/gxyj/gxyjdetails/selectAbarbeitung";
    //工序移交-整改问题-完成整改后-复验人通过
    public static String updateAbarbeitungPass = BaseUrl() + "api/gxyj/gxyjdetails/updateAbarbeitungPass";
    //工序移交-整改问题-完成整改后-复验人退回
    public static String updateAbarbeitungSendBack = BaseUrl() + "api/gxyj/gxyjdetails/updateAbarbeitungSendBack";
    //工序移交-整改问题-非正常关闭
    public static String updateAbnormalClosing = BaseUrl() + "api/gxyj/gxyjdetails/updateAbnormalClosing";
    //查看退回原因列表
    public static String listSendBack = BaseUrl() + "api/gxyj/gxyjdetails/listSendBack";
    //提交退回-添加退回原因-监理
    public static String saveSendBack = BaseUrl() + "api/gxyj/gxyjdetails/saveSendBack";
    //提交退回-添加退回原因-建设单位验收人
    public static String saveSendBackCon = BaseUrl() + "api/gxyj/gxyjdetails/saveSendBackCon";
    //工序移交 待办-退回后的重新申请
    public static String updateDetails = BaseUrl() + "api/gxyj/gxyjdetails/updateDetails";
    //工序移交- 检查指引
    public static String listCheckTheGuide = BaseUrl() + "api/process/processOver/listCheckTheGuide";
    //现场检查 指引 根据id
    public static String listJCZY = BaseUrl() + "/api/sys/inspection/listJCZY";
    //工序移交- 施工单位
    public static String listGXYJConstructionOrganization = BaseUrl() + "api/RoleAut/RoleUser/listGXYJConstructionOrganization";
    //工序移交- 抄送人
    public static String listRoleUser = BaseUrl() + "api/RoleAut/RoleUser/listRoleUser";
    //工序移交- 监理
    public static String listGXYJSupervisor = BaseUrl() + "api/RoleAut/RoleUser/listGXYJSupervisor";
    //工序移交- 建设单位负责人
    public static String listConstructionDirector = BaseUrl() + "api/RoleAut/RoleUser/listConstructionDirector ";
    //现场检查 代办 查询代办-已办-抄送的数量
    public static String doProblemCount = BaseUrl() + "api/sys/inspection/doProblemCount";
    //责任单位
    public static String selectContractor = BaseUrl() + "api/sys/inspection/selectContractor";
    //现场检查 代办 查询列表
    public static String doProblemList = BaseUrl() + "api/sys/inspection/doProblemList";
    //工序移交 代办 查询列表
    public static String listBacklog = BaseUrl() + "api/gxyj/gxyjdetails/listBacklog";
    //工序移交 修改某一条申请问题：
    public static String updateSQWT = BaseUrl() + "api/gxyj/gxyjdetails/updateSQWT";
    //工序移交 修改 整改问题：
    public static String updateZGWT = BaseUrl() + "api/gxyj/gxyjdetails/updateZGWT";
    //查询责任单位
    public static String selectContractorBatchId = BaseUrl() + "api/sys/inspection/selectContractorBatchId";
    //现场检查 标段
    public static String xcJcBdList = BaseUrl() + "api/sys/inspection/selectSectionId";
    //现场检查-检查项描述
    public static String problemDesc = BaseUrl() + "api/sys/inspection/selectProblemDescription";
    //现场检查户型图
    public static String huXingTu = BaseUrl() + "api/sys/inspection/html/selectHouseMapHtml";
    //工序移交问题列表
    public static String gxyjProblemList = BaseUrl() + "api/gxyj/gxyjdetails/listAbarbeitung";
    //查询非正常关闭原因
    public static String abnormalClosing = BaseUrl() + "api/gxyj/gxyjdetails/abnormalClosing";
    //退回条件
    public static String listSendBackZG = BaseUrl() + "api/gxyj/gxyjdetails/listSendBackZG";
    //材料验收-1待办、2已办、3抄送
    public static String doClysList = BaseUrl() + "api/sys/clys/doClysList";
    //监理验收通过的接口
    public static String updateVerificationBy = BaseUrl() + "api/gxyj/gxyjdetails/updateVerificationBy";
    //建设单位的人验收通过
    public static String updateVBConstruction = BaseUrl() + "api/gxyj/gxyjdetails/updateVBConstruction";
    //工序移交 查询标段的筛选条件
    public static String listFiltrateSection = BaseUrl() + "api/gxyj/filtrate/listFiltrateSection";
    //工序移交 查询检查项的筛选条件
    public static String listInspectionFiltrate = BaseUrl() + "api/gxyj/filtrate/listInspectionFiltrate";
    //工序移交-添加整改问题
    public static String saveAbarbeitung = BaseUrl() + "api/gxyj/gxyjdetails/saveAbarbeitung";
    //退回记录查询
    public static String listBackToTheRecord = BaseUrl() + "api/gxyj/gxyjdetails/listBackToTheRecord";
    //待办入口查询工序详情
    public static String selectDaiban = BaseUrl() + "api/gxyj/gxyjdetails/selectDaiban";
    //材料验收-列表list
    public static String clysList = BaseUrl() + "api/sys/clys/clysList";
    //查询待办中-实测试量的待办,已办,抄送
    public static String listAbarbeitungBacklog = BaseUrl() + "api/scsl/issueAbarbeitung/listAbarbeitungBacklog";
    //材料验收-（材料名称）查询材料验收检查项第一级和第二级
    public static String selectCLYS = BaseUrl() + "api/sys/inspection/selectSCSL";
    //材料验收-新增任务
    public static String saveClysTask = BaseUrl() + "api/sys/clys/saveClysTask";
    //材料验收-（材料类型）根据标段id、检查项类型、检查项父id查询子集(第三级检查项list)
    public static String selectThreeInspection = BaseUrl() + "api/sys/clys/selectThreeInspection";
    //材料验收-添加材料名称、材料类型
    public static String saveInspectionClys = BaseUrl() + "api/sys/clys/saveInspectionClys";
    //材料验收-待办、已办、抄送数量
    public static String doClysCount = BaseUrl() + "api/sys/clys/doClysCount";
    //材料验收-修改任务，申请进场验收
    public static String updateClysTask = BaseUrl() + "api/sys/clys/updateClysTask";
    //材料验收-任务详情
    public static String infoClysTask = BaseUrl() + "api/sys/clys/infoClysTask";
    //材料验收-进场详情
    public static String infoApproach = BaseUrl() + "api/sys/clys/infoApproach";
    //材料验收-删除任务
    public static String delClysTask = BaseUrl() + "api/sys/clys/delClysTask";
    //材料验收-根据检查项id查询品牌、规格
    public static String selectParticularsClys = BaseUrl() + "api/sys/clys/selectParticularsClys";
    //材料验收-添加品牌、规格
    public static String saveParticularsClys = BaseUrl() + "api/sys/clys/saveParticularsClys";
    //实测实量-查询整改问题详情(待办-工作台都可查)
    public static String listIssueAbarbeitung = BaseUrl() + "api/scsl/issueAbarbeitung/listIssueAbarbeitung";
    //实测实量-添加整改问题
    public static String saveIssueAbarbeitung = BaseUrl() + "api/scsl/issueAbarbeitung/saveIssueAbarbeitung";
    //实测实量-查询标段单元
    public static String selectRoomIdSection = BaseUrl() + "api/pro/projectmanage/selectRoomIdSection";
    //实测实量-检查指引详情
    public static String selectSclsIssueGuidelines = BaseUrl() + "api/sys/inspection/selectSclsIssueGuidelines";
    //实测实量-筛选条件-查询检查项筛选条件
    public static String listScslFiltrate = BaseUrl() + "api/scsl/Filtrate/listScslFiltrate";
    //实测试量的检查完毕再添加整改人操作
    public static String updateAbarbeitungZgPOperation = BaseUrl() + "api/scsl/issueAbarbeitung/updateAbarbeitungZgPOperation";
    //实测试量的待办中-完成整改-整改人操作
    public static String updateAbarbeitungZhanggaiPeople = BaseUrl() + "api/scsl/issueAbarbeitung/updateAbarbeitungZhanggaiPeople";
    //材料验收-监理-进场验收合格、进场验收不合格
    public static String supervisorQualified = BaseUrl() + "api//sys/clys/supervisorQualified";
    //材料验收-建设单位验收人-进场验收合格、进场验收不合格
    public static String acceptanceQualified = BaseUrl() + "api/sys/clys/acceptanceQualified";
    //材料验收-提交送检记录
    public static String inspect = BaseUrl() + "api/sys/clys/inspect";
    //材料验收-根据任务id查询送检记录详情
    public static String infoClysInspect = BaseUrl() + "api/sys/clys/infoClysInspect";
    //材料验收-根据任务id查询上传报告详情
    public static String infoClysReport = BaseUrl() + "api/sys/clys/infoClysReport";
    //材料验收-上传报告
    public static String report = BaseUrl() + "api/sys/clys/report";
    //材料验收-根据任务id查询退场详情
    public static String infoClysExit = BaseUrl() + "api/sys/clys/infoClysExit";
    //材料验收-提交退场
    public static String exit = BaseUrl() + "api/sys/clys/exit";
    //工序移交 已办-待办-抄送 的数量
    public static String listBacklogNunber = BaseUrl() + "api/gxyj/gxyjdetails/listBacklogNunber";
    //工序移交
    //删除工序移交详情问题
    public static String delateAbnormal = BaseUrl() + "api/gxyj/gxyjdetails/delateAbnormal";
    //工序移交楼栋信息
    public static String selectGXYJPart = BaseUrl() + "api/pro/projectmanage/selectGXYJPart";
    //实测实量户型图-操作不带头
    public static String selectHouseMapHtmlGxyjAD = BaseUrl() + "api/sys/inspection/html/selectHouseMapHtmlGxyjAD";
    //实测实量户型图-操作带头
    public static String selectHouseMapHtmlScsl = BaseUrl() + "api/sys/inspection/html/selectHouseMapHtmlScsl";
    //工序移交  完成整改-整改人
    public static String updateAbarbeitung = BaseUrl() + "api/gxyj/gxyjdetails/updateAbarbeitung";
    //工序移交户型图
    public static String selectHouseMapHtmlGxyj = BaseUrl() + "api/sys/inspection/html/selectHouseMapHtmlGxyj";
    //现场检查展示户型图
    public static String selectHouseMapHtmlXcjc = BaseUrl() + "api/sys/inspection/html/selectHouseMapHtmlXcjc";
    //文件删除(图片，视频文件)
    public static String deleteFile = BaseUrl() + "api/sys/inspection/deleteFile";
    //实测实量修改信息
    public static String updateZhanggaiPeople = BaseUrl() + "api/scsl/issueAbarbeitung/updateZhanggaiPeople";
    //实测实量待办展示户型图
    public static String selectHouseMapHtmlScslTwo = BaseUrl() + "api/sys/inspection/html/selectHouseMapHtmlScslTwo";
    //实测实量查询详情信息
    public static String selectScslType = BaseUrl() + "api/scsl/issueAbarbeitung/selectScslType";
    //修改用户密码
    public static String passwordUser = BaseUrl() + "sys/user/passwordUser";
    //修改用信息 Post
    //public static String update = BaseUrl() + "sys/user/update";
    public static String update = BaseUrl() + "sys/user/updateApp";
    //用户信息-个人中心 Get
    //public static String userInfo = BaseUrl() + "sys/user/info";
    public static String userInfo = BaseUrl() + "sys/user/infoAPP";
    //反馈问题
    public static String saveProblemFeedback = BaseUrl() + "api/Problem/Feedback/saveProblemFeedback";
    //查询问题列表
    public static String listProblemFeedback = BaseUrl() + "api/Problem/Feedback/listProblemFeedback";
    //帮助
    public static String listHelp = BaseUrl() + "api/help/listHelp";
    //材料验收 图片类型列表
    public static String getClysImage = BaseUrl() + "api/sys/clys/getClysImage";
    //公告详情
    public static String noticeDetail = BaseUrl() + "news/news/APPinfo";
    //报表中心-实测试量
    public static String selectMassage = BaseUrl() + "api/bbzx/scsl/selectMassage";
    //报表中心-材料验收
    public static String list = BaseUrl() + "api/bbzx/clys/list";
    //报表中心-工序移交-查询楼栋单元
    public static String listTowerUnit = BaseUrl() + "api/bbzx/gxyj/listTowerUnit";
    //报表中心-工序移交-查询此楼栋单元检查项信息H5
    public static String listTowerUnitMassage = BaseUrl() + "api/bbzx/gxyj/listTowerUnitMassage";
    //报表中心-工序移交-H5链接
    public static String listTowerUnitMassage2 = BaseUrl() + "api/bbzx/gxyj/listTowerUnitMassage2";
    //批量获取标段、检查项
    public static String dikuaiSectionlist = BaseUrl() + "api/cahce/port/dikuaiSectionlist";
    //报表中心-实测试量-楼栋信息查询
    public static String listTowerInspMassage = BaseUrl() + "api/bbzx/scsl/listTowerInspMassage";
    //报表中心-现场检查
    public static String formXcjc = BaseUrl() + "api/bbzx/xcjc/list";
    //报表中心-现场检查问题
    public static String inspectionList = BaseUrl() + "api/bbzx/xcjc/inspectionList";
    //报表中心-现场检查详情
    public static String xcjcDetailList = BaseUrl() + "api/bbzx/xcjc/xcjcList";
    //添加工程量点
    public static String saveLightspot = BaseUrl() + "/api/project/lightspot/saveLightspot";
    //添加工程量点
    public static String listLightspotZx = BaseUrl() + "/api/project/lightspot/listLightspotZx";
    //添加工程量点
    public static String listLightspotZz = BaseUrl() + "/api/project/lightspot/listLightspotZz";
    //添加工程量点
    public static String selectLightspot = BaseUrl() + "/api/project/lightspot/selectLightspot";
    //添加工程量点
    public static String saveLike = BaseUrl() + "/api/project/lightspot/saveLike";

    public static String listProject = BaseUrl() + "api/project/lightspot/listProject";


}
