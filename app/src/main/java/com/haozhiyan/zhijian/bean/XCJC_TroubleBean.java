package com.haozhiyan.zhijian.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/5/21.
 * Describe: 现场检查登记问题保存的bean
 */
public class XCJC_TroubleBean extends JSONObject implements Serializable {

    public List<String> images;//图片路径
    public List<String> videos;//视频路径
    public String buWei;//楼栋部位
    public String JcxName;//检查项
    public String JianChaR;//检查人
    public String desc;//描述
    public String instructContent;//补充说明
    public int troubleChengDu;//严重程度
    public String zgQxTimes;//整改期限
    public String zhengGaiRen;//整改人
    public String zeRenDanWei;//责任单位
    public String fuYanRen;//复验人
    public String chaoSongRen;//抄送人
    public String createTime;//创建时间
    public String itemType;//条目类型

    public XCJC_TroubleBean() {
    }

    public XCJC_TroubleBean(List<String> images, List<String> videos, String buWei, String JianChaR, String desc, String instructContent, int troubleChengDu, String zgQxTimes, String zhengGaiRen, String zeRenDanWei, String fuYanRen, String chaoSongRen, String createTime) {
        this.images = images;
        this.videos = videos;
        this.buWei = buWei;
        this.JianChaR = JianChaR;
        this.desc = desc;
        this.instructContent = instructContent;
        this.troubleChengDu = troubleChengDu;
        this.zgQxTimes = zgQxTimes;
        this.zhengGaiRen = zhengGaiRen;
        this.zeRenDanWei = zeRenDanWei;
        this.fuYanRen = fuYanRen;
        this.chaoSongRen = chaoSongRen;
        this.createTime = createTime;
    }

    public JSONObject setData() {
        JSONObject jsonObject = this;
        try {
            jsonObject.putOpt("images", images.toString());
            jsonObject.putOpt("videos", videos.toString());
            jsonObject.putOpt("buWei", buWei);
            jsonObject.putOpt("JianChaX", JianChaR);
            jsonObject.putOpt("JcxName", JcxName);
            jsonObject.putOpt("desc", desc);
            jsonObject.putOpt("instructContent", instructContent);
            jsonObject.putOpt("troubleChengDu", troubleChengDu);
            jsonObject.putOpt("zgQxTimes", zgQxTimes);
            jsonObject.putOpt("zhengGaiRen", zhengGaiRen);
            jsonObject.putOpt("zeRenDanWei", zeRenDanWei);
            jsonObject.putOpt("fuYanRen", fuYanRen);
            jsonObject.putOpt("chaoSongRen", chaoSongRen);
            jsonObject.putOpt("createTime", createTime);
            jsonObject.putOpt("itemType", itemType);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
