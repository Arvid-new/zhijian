package com.haozhiyan.zhijian.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangZhenKai on 2019/7/26.
 * Describe: Ydzj_project
 */
public class XcjcSeverityProblem implements Serializable {

    /**
     * severityId : 1
     * severityName : 一般
     */
    public int severityId;
    public String severityName;

    public XcjcSeverityProblem() {
    }

    public XcjcSeverityProblem(int severityId, String severityName) {
        this.severityId = severityId;
        this.severityName = severityName;
    }

    public List<XcjcSeverityProblem> getInitProblem() {
        List<XcjcSeverityProblem> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                XcjcSeverityProblem bean = new XcjcSeverityProblem(1, "一般");
                list.add(bean);
            } else if (i == 1) {
                XcjcSeverityProblem bean = new XcjcSeverityProblem(2, "重要");
                list.add(bean);
            } else if (i == 2) {
                XcjcSeverityProblem bean = new XcjcSeverityProblem(3, "紧急");
                list.add(bean);
            }else if (i == 3) {
                XcjcSeverityProblem bean = new XcjcSeverityProblem(4, "要紧");
                list.add(bean);
            }
        }
        return list;
    }
}
