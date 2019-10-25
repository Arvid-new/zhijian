package com.haozhiyan.zhijian.activity.reportforms.bean;

import java.io.Serializable;
import java.util.List;

public class ReportFormBean implements Serializable {
    public String msg;
    public int code;
    public List<ModulesDataBean> modulesData;

    public static class ModulesDataBean {
        public int id;
        public String name;
        public String content;
        public int typeIcon;
    }
}
