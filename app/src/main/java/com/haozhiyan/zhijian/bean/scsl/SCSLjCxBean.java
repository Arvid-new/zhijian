package com.haozhiyan.zhijian.bean.scsl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/6/20.
 * Describe: Ydzj_project
 */
public class SCSLjCxBean implements Serializable {


    /**
     * msg : success
     * code : 0
     * list : [{"sectionId":30,"inspectionId":2795792641294336,"inspectionName":"混凝土工程","inspectionParentId":0,"child":[{"inspectionId":2795792767123456,"inspectionName":"截面尺寸","inspectionParentId":2795792641294336,"partsDivision":"不分单元-整层"},{"inspectionId":2795793484349440,"inspectionName":"表面平整度","inspectionParentId":2795792641294336,"partsDivision":"分单元-整层"},{"inspectionId":2795794197381120,"inspectionName":"垂直度","inspectionParentId":2795792641294336,"partsDivision":"分户"},{"inspectionId":2795796189675520,"inspectionName":"净高","inspectionParentId":2795792641294336,"partsDivision":"分户"},{"inspectionId":2795796911095808,"inspectionName":"顶板水平度","inspectionParentId":2795792641294336,"partsDivision":"分户"},{"inspectionId":2795798861447168,"inspectionName":"楼板厚度","inspectionParentId":2795792641294336,"partsDivision":"分户"}]},{"sectionId":30,"inspectionId":2795799566090240,"inspectionName":"抹灰工程","inspectionParentId":0,"child":[{"inspectionId":2795799691919360,"inspectionName":"平整度","inspectionParentId":2795799566090240,"partsDivision":"分单元-整层"},{"inspectionId":2795800434311168,"inspectionName":"垂直度","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795803659730944,"inspectionName":"阴阳角方正","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795804871884800,"inspectionName":"开间进深","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795807027757056,"inspectionName":"外门窗洞口尺寸","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795807728205824,"inspectionName":"墙体厚度","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795808491569152,"inspectionName":"顶板水平度（腻子完成）","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795810571943936,"inspectionName":"净高","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795822605402112,"inspectionName":"裂缝","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795824308289536,"inspectionName":"空鼓","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795824836771840,"inspectionName":"入户门洞口","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795835624521728,"inspectionName":"方正性","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795836362719232,"inspectionName":"地面表面平整度（水泥砂浆地面）","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795843434315776,"inspectionName":"地面表面平整度（细石混凝土）","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795845464358912,"inspectionName":"外墙窗内侧墙体厚度极差","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795848236793856,"inspectionName":"柜体嵌入位尺寸偏差","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795852812779520,"inspectionName":"同户型同厨卫间管井尺寸偏差","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795853618085888,"inspectionName":"同户型同厨卫间窗底框标高偏差","inspectionParentId":2795799566090240,"partsDivision":"分户"},{"inspectionId":2795854415003648,"inspectionName":"同户型同厨卫间窗侧框墙距偏差","inspectionParentId":2795799566090240,"partsDivision":"分户"}]},{"sectionId":30,"inspectionId":2795855899787264,"inspectionName":"外保温工程","inspectionParentId":0,"child":[{"inspectionId":2795856021422080,"inspectionName":"表面平整度","inspectionParentId":2795855899787264,"partsDivision":"分户"},{"inspectionId":2795856776396800,"inspectionName":"垂直度","inspectionParentId":2795855899787264,"partsDivision":"分户"},{"inspectionId":2795857598480384,"inspectionName":"阴阳角方正","inspectionParentId":2795855899787264,"partsDivision":"分户"}]}]
     */

    public String msg;
    public int code;
    public List<ListBean> list;

    public static class ListBean implements Serializable{
        /**
         * sectionId : 30
         * inspectionId : 2795792641294336
         * inspectionName : 混凝土工程
         * inspectionParentId : 0
         * child : [{"inspectionId":2795792767123456,"inspectionName":"截面尺寸","inspectionParentId":2795792641294336,"partsDivision":"不分单元-整层"},{"inspectionId":2795793484349440,"inspectionName":"表面平整度","inspectionParentId":2795792641294336,"partsDivision":"分单元-整层"},{"inspectionId":2795794197381120,"inspectionName":"垂直度","inspectionParentId":2795792641294336,"partsDivision":"分户"},{"inspectionId":2795796189675520,"inspectionName":"净高","inspectionParentId":2795792641294336,"partsDivision":"分户"},{"inspectionId":2795796911095808,"inspectionName":"顶板水平度","inspectionParentId":2795792641294336,"partsDivision":"分户"},{"inspectionId":2795798861447168,"inspectionName":"楼板厚度","inspectionParentId":2795792641294336,"partsDivision":"分户"}]
         */

        public int sectionId;
        public long inspectionId;
        public String inspectionName;
        public long inspectionParentId;
        public List<ChildBean> child;

        public static class ChildBean implements Serializable{
            /**
             * inspectionId : 2795792767123456
             * inspectionName : 截面尺寸
             * inspectionParentId : 2795792641294336
             * partsDivision : 不分单元-整层
             */

            public long inspectionId;
            public String inspectionName;
            public long inspectionParentId;
            public String partsDivision;
        }
    }
}
