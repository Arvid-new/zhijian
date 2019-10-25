package com.haozhiyan.zhijian.activity.reportforms.bean;

import java.util.List;

public class FormXCJCResult {

    /**
     * msg : success
     * code : 0
     * list : [{"batchName":"测试","daizhenggai":0,"total":1,"yitongguo":0,"guanbi":0,"daifuyanbl":"待复验100.0%","yitongguobl":"已通过0.0%","daifuyan":1,"guanbibl":"非正常关闭0.0%","batchId":"33","yizheggai":"100.0%已整改","daizhenggaibl":"待整改0.0%"},{"batchName":"测试","daizhenggai":0,"total":1,"yitongguo":0,"guanbi":0,"daifuyanbl":"待复验100.0%","yitongguobl":"已通过0.0%","daifuyan":1,"guanbibl":"非正常关闭0.0%","batchId":"34","yizheggai":"100.0%已整改","daizhenggaibl":"待整改0.0%"},{"batchName":"测照片","daizhenggai":1,"total":1,"yitongguo":0,"guanbi":0,"daifuyanbl":"待复验0.0%","yitongguobl":"已通过0.0%","daifuyan":0,"guanbibl":"非正常关闭0.0%","batchId":"37","yizheggai":"0.0%已整改","daizhenggaibl":"待整改100.0%"}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * batchName : 测试
         * daizhenggai : 0
         * total : 1
         * yitongguo : 0
         * guanbi : 0
         * daifuyanbl : 待复验100.0%
         * yitongguobl : 已通过0.0%
         * daifuyan : 1
         * guanbibl : 非正常关闭0.0%
         * batchId : 33
         * yizheggai : 100.0%已整改
         * daizhenggaibl : 待整改0.0%
         */

        private String batchName;
        private int daizhenggai;
        private int total;
        private int yitongguo;
        private int guanbi;
        private String daifuyanbl;
        private String yitongguobl;
        private int daifuyan;
        private String guanbibl;
        private String batchId;
        private String yizheggai;
        private String daizhenggaibl;

        public String getBatchName() {
            return batchName;
        }

        public void setBatchName(String batchName) {
            this.batchName = batchName;
        }

        public int getDaizhenggai() {
            return daizhenggai;
        }

        public void setDaizhenggai(int daizhenggai) {
            this.daizhenggai = daizhenggai;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getYitongguo() {
            return yitongguo;
        }

        public void setYitongguo(int yitongguo) {
            this.yitongguo = yitongguo;
        }

        public int getGuanbi() {
            return guanbi;
        }

        public void setGuanbi(int guanbi) {
            this.guanbi = guanbi;
        }

        public String getDaifuyanbl() {
            return daifuyanbl;
        }

        public void setDaifuyanbl(String daifuyanbl) {
            this.daifuyanbl = daifuyanbl;
        }

        public String getYitongguobl() {
            return yitongguobl;
        }

        public void setYitongguobl(String yitongguobl) {
            this.yitongguobl = yitongguobl;
        }

        public int getDaifuyan() {
            return daifuyan;
        }

        public void setDaifuyan(int daifuyan) {
            this.daifuyan = daifuyan;
        }

        public String getGuanbibl() {
            return guanbibl;
        }

        public void setGuanbibl(String guanbibl) {
            this.guanbibl = guanbibl;
        }

        public String getBatchId() {
            return batchId;
        }

        public void setBatchId(String batchId) {
            this.batchId = batchId;
        }

        public String getYizheggai() {
            return yizheggai;
        }

        public void setYizheggai(String yizheggai) {
            this.yizheggai = yizheggai;
        }

        public String getDaizhenggaibl() {
            return daizhenggaibl;
        }

        public void setDaizhenggaibl(String daizhenggaibl) {
            this.daizhenggaibl = daizhenggaibl;
        }
    }
}
