package com.haozhiyan.zhijian.model;

import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/4/24.
 * Describe:参数传递方法
 */
public class ParameterMap {

    /**
     * @param key
     * @param value
     * @return params
     */
    public static HttpParams put(String key, String value) {
        HttpParams params = new HttpParams();
        params.put(key, value);
        return params;
    }

    public static HttpParams put(String key, List<File> files) {
        HttpParams params = new HttpParams();
        params.putFileParams(key, files);
        return params;
    }

    /**
     * @param towerId
     * @param unitId
     * @param scslType
     * @return 实测实量楼栋信息参数
     */
    public static HttpParams put(String sectionId,String towerId, String unitId, String scslType) {
        HttpParams params = new HttpParams();
        params.put("sectionId", sectionId);
        params.put("towerId", towerId);
        params.put("unitId", unitId);
        params.put("scslType", scslType);
        return params;
    }

    /**
     * @param dikuaiId
     * @param towerId
     * @param unitId
     * @param inspectionName
     * @param inspectionSunName
     * @param scslType
     * @return 实测实量楼栋信息参数修改
     */
    public static HttpParams put(String dikuaiId,String towerId, String unitId, String inspectionName,
                                 String inspectionSunName,String inspectionSunId,String scslType
            ,String sectionId,String sectionName,String towerName,String unitName) {
        HttpParams params = new HttpParams();
        params.put("dikuaiId", dikuaiId);
        params.put("towerId", towerId);
        params.put("unitId", unitId);
        params.put("inspectionName", inspectionName);
        params.put("inspectionSunName", inspectionSunName);
        params.put("inspectionSunId", inspectionSunId);
        params.put("scslType", scslType);
        params.put("sectionId", sectionId);
        params.put("sectionName", sectionName);
        params.put("towerName", towerName);
        params.put("unitName", unitName);
        return params;
    }
    public static HttpParams put(String towerId, String unitId, String inspectionName,String inspectionSunName,String scslType) {
        HttpParams params = new HttpParams();
        params.put("towerId", towerId);
        params.put("unitId", unitId);
        params.put("inspectionName", inspectionName);
        params.put("inspectionSunName", inspectionSunName);
        params.put("scslType", scslType);
        return params;
    }

    /**
     * @param batchName       批次名称
     * @param tagOfficialTest 标识（正式 1 /测试 2）
     * @param sectionId       所属标段id
     * @param rectifyDate     整改期限
     * @param rectify         检查人id
     * @param review          负责人id
     * @param cc              抄送人id
     * @return
     */
    public static HttpParams putPici(String batchName, int tagOfficialTest, String sectionId, String rectifyDate, String rectify, String review, String cc) {
        HttpParams params = new HttpParams();
        params.put("batchName", batchName);
        params.put("tagOfficialTest", tagOfficialTest);
        params.put("sectionId", sectionId);
        params.put("rectifyDate", rectifyDate);
        params.put("rectify", rectify);
        params.put("review", review);
        params.put("cc", cc);
        params.put("dikuaiId", Constant.projectId);//新增地块id
        return params;
    }

    /**
     * @param batchId     批次Id
     * @param batchName   批次名称
     * @param rectify     检查人id(逗号分隔)
     * @param rectifyDate 整改期限
     * @param review      负责人id
     * @param cc          抄送人id(逗号分隔)
     * @return
     */
    public static HttpParams editPiCi(String batchId, String batchName, String rectify, String rectifyDate, String review, String cc) {
        HttpParams params = new HttpParams();
        params.put("batchId", batchId);
        params.put("batchName", batchName);
        params.put("rectify", rectify);
        params.put("rectifyDate", rectifyDate);
        params.put("review", review);
        params.put("cc", cc);
        params.put("dikuaiId", Constant.projectId);//新增地块id
        return params;
    }

    /**
     * @param batchId               批次id
     * @param projectId             项目id
     * @param projectName           项目名称
     * @param state                 状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
     * @param problemImage          问题图片
     * @param tower                 楼栋
     * @param unit                  单元
     * @param floor                 层
     * @param room                  房间
     * @param housemap              户型图（标记）
     * @param inspectionId          检查项id
     * @param particularsId         描述id
     * @param particularsName       描述
     * @param particularsSupplement 描述补充说明
     * @param serious               严重程度   1.一般，2.重要，3.紧急
     * @param submit                提交人
     * @param submitDate            提交时间
     * @param rectifyTimelimit      整改期限
     * @param rectify               整改人
     * @param reviewName            复验人名称，多个
     * @param ccName                抄送人名称，多个
     * @param dutyUnit              责任单位
     * @return
     */
    public static HttpParams addTrouble(String batchId, String projectId, String projectName, String state, String problemImage
            , String tower, String unit, String floor, String room, String housemap, String inspectionId, String particularsId, String particularsName,
                                        String particularsSupplement, String serious, String submit, String submitDate, String rectifyTimelimit, String rectify,
                                        String reviewName, String ccName, String dutyUnit) {
        HttpParams params = new HttpParams();
        try {
            JSONObject trouble = new JSONObject();
            trouble.put("batchId", batchId);
            trouble.put("projectId", projectId);
            trouble.put("projectName", projectName);
            trouble.put("state", state);
            trouble.put("problemImage", problemImage);
            trouble.put("tower", tower);
            trouble.put("unit", unit);
            trouble.put("floor", floor);
            trouble.put("room", room);
            trouble.put("housemap", housemap);
            trouble.put("inspectionId", inspectionId);
            trouble.put("particularsId", particularsId);
            trouble.put("particularsName", particularsName);
            trouble.put("particularsSupplement", particularsSupplement);
            trouble.put("serious", serious);
            trouble.put("submit", submit);
            trouble.put("submitDate", submitDate);
            trouble.put("rectifyTimelimit", rectifyTimelimit);
            trouble.put("rectify", rectify);
            trouble.put("reviewName", reviewName);
            trouble.put("ccName", ccName);
            trouble.put("dutyUnit", dutyUnit);
            params.put("xcjcProblem", trouble.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * @param batchId               批次id
     * @param projectId             项目id
     * @param state                 状态：1.待整改，2.待复验，3.非正常关闭，4.已通过
     * @param problemImage          问题图片
     * @param tower                 楼栋
     * @param unit                  单元
     * @param floor                 楼层
     * @param room                  房间
     * @param housemap              户型图（标记）
     * @param inspectionId          检查项id
     * @param particularsId         描述id
     * @param particularsSupplement 描述补充说明
     * @param serious               严重程度   1.一般，2.重要，3.紧急
     * @param submit                提交人
     * @param submitDate            提交时间
     * @param rectifyTimelimit      整改期限
     * @param rectify               整改人
     * @param review                复验人
     * @param cc                    抄送人
     * @param dutyUnit              责任单位
     * @return
     */
    public static HttpParams addTrouble(String batchId, String projectId, String sectionId, String state, String problemImage, String tower, String unit, String floor,
                                        String room, String housemap, String inspectionId, String particularsId, String particularsSupplement, String serious,
                                        String submit, String submitDate, String rectifyTimelimit, String rectify, String review, String cc, String dutyUnit) {
        HttpParams params = new HttpParams();
        try {
//            JSONObject trouble = new JSONObject();
//            trouble.put("batchId", batchId);
//            trouble.put("projectId", projectId);
//            trouble.put("sectionId", sectionId);
//            trouble.put("state", state);
//            trouble.put("problemImage", problemImage);
//            trouble.put("tower", tower);
//            trouble.put("unit", unit);
//            trouble.put("floor", floor);
//            trouble.put("room", room);
//            trouble.put("housemap", housemap);
//            trouble.put("inspectionId", inspectionId);
//            trouble.put("particularsId", particularsId);
//            trouble.put("particularsSupplement", particularsSupplement);
//            trouble.put("serious", serious);
//            trouble.put("submit", submit);
//            trouble.put("submitDate", submitDate);
//            trouble.put("rectifyTimelimit", rectifyTimelimit);
//            trouble.put("rectify", rectify);
//            trouble.put("review", review);
//            trouble.put("cc", cc);
//            trouble.put("dutyUnit", dutyUnit);
            //params.put("xcjcProblem", trouble.toString());

            params.put("batchId", batchId);
            params.put("projectId", projectId);
            params.put("sectionId", sectionId);
            params.put("state", state);
            params.put("problemImage", problemImage);
            params.put("tower", tower);
            params.put("unit", unit);
            params.put("floor", floor);
            params.put("room", room);
            params.put("housemap", housemap);
            params.put("inspectionId", inspectionId);
            params.put("particularsId", particularsId);
            params.put("particularsSupplement", particularsSupplement);
            params.put("serious", serious);
            params.put("submit", submit);
            params.put("submitDate", submitDate);
            params.put("rectifyTimelimit", rectifyTimelimit);
            params.put("rectify", rectify);
            params.put("review", review);
            params.put("cc", cc);
            params.put("dutyUnit", dutyUnit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static HttpParams piCiOrTrouble(String userId, String proId, String batchId) {
        HttpParams params = new HttpParams();
        params.put("userId", userId);
        params.put("proId", proId);
        params.put("batchId", batchId);
        return params;
    }
}
