package com.haozhiyan.zhijian.activity.reportforms.bean;

import java.util.List;

public class FormSCSLDetailResult {
    private String msg;
    private int code;

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

    public FormSCSLDetailBean getFormSCSLDetailBean() {
        return list;
    }

    public void setFormSCSLDetailBean(FormSCSLDetailBean formSCSLDetailBean) {
        this.list = formSCSLDetailBean;
    }

    private FormSCSLDetailBean list;

    public static class FormSCSLDetailBean {

        /**
         * towerId : 214642
         * towerName : 1栋
         * inspFu : [{"towerName":"1栋","inspctionId":"421","inspctionName":"混凝土工程","inspSun":[{"inspctionSunId":"424","inspctionSunName":"垂直度","sgHgl":null,"sgHs":null,"jlHgl":"66.7%","jlHs":"1","jsHgl":"50.0%","jsHu":"2"}],"zj":{"zongji":"总计","shigongZongji":null,"jianliZongji":"66.7%","jiansheZongji":"50.0%"}}]
         */

        private int towerId;
        private String towerName;
        private List<InspFuBean> inspFu;

        public int getTowerId() {
            return towerId;
        }

        public void setTowerId(int towerId) {
            this.towerId = towerId;
        }

        public String getTowerName() {
            return towerName;
        }

        public void setTowerName(String towerName) {
            this.towerName = towerName;
        }

        public List<InspFuBean> getInspFu() {
            return inspFu;
        }

        public void setInspFu(List<InspFuBean> inspFu) {
            this.inspFu = inspFu;
        }

        public static class InspFuBean {
            /**
             * towerName : 1栋
             * inspctionId : 421
             * inspctionName : 混凝土工程
             * inspSun : [{"inspctionSunId":"424","inspctionSunName":"垂直度","sgHgl":null,"sgHs":null,"jlHgl":"66.7%","jlHs":"1","jsHgl":"50.0%","jsHu":"2"}]
             * zj : {"zongji":"总计","shigongZongji":null,"jianliZongji":"66.7%","jiansheZongji":"50.0%"}
             */

            private String towerName;
            private String inspctionId;
            private String inspctionName;
            private ZjBean zj;
            private List<InspSunBean> inspSun;

            public String getTowerName() {
                return towerName;
            }

            public void setTowerName(String towerName) {
                this.towerName = towerName;
            }

            public String getInspctionId() {
                return inspctionId;
            }

            public void setInspctionId(String inspctionId) {
                this.inspctionId = inspctionId;
            }

            public String getInspctionName() {
                return inspctionName;
            }

            public void setInspctionName(String inspctionName) {
                this.inspctionName = inspctionName;
            }

            public ZjBean getZj() {
                return zj;
            }

            public void setZj(ZjBean zj) {
                this.zj = zj;
            }

            public List<InspSunBean> getInspSun() {
                return inspSun;
            }

            public void setInspSun(List<InspSunBean> inspSun) {
                this.inspSun = inspSun;
            }

            public static class ZjBean {
                /**
                 * zongji : 总计
                 * shigongZongji : null
                 * jianliZongji : 66.7%
                 * jiansheZongji : 50.0%
                 */

                private String zongji;
                private String shigongZongji;
                private String jianliZongji;
                private String jiansheZongji;

                public String getZongji() {
                    return zongji;
                }

                public void setZongji(String zongji) {
                    this.zongji = zongji;
                }

                public String getShigongZongji() {
                    return shigongZongji;
                }

                public void setShigongZongji(String shigongZongji) {
                    this.shigongZongji = shigongZongji;
                }

                public String getJianliZongji() {
                    return jianliZongji;
                }

                public void setJianliZongji(String jianliZongji) {
                    this.jianliZongji = jianliZongji;
                }

                public String getJiansheZongji() {
                    return jiansheZongji;
                }

                public void setJiansheZongji(String jiansheZongji) {
                    this.jiansheZongji = jiansheZongji;
                }
            }

            public static class InspSunBean {
                /**
                 * inspctionSunId : 424
                 * inspctionSunName : 垂直度
                 * sgHgl : null
                 * sgHs : null
                 * jlHgl : 66.7%
                 * jlHs : 1
                 * jsHgl : 50.0%
                 * jsHu : 2
                 */

                private String inspctionSunId;
                private String inspctionSunName;
                private String sgHgl;
                private String sgHs;
                private String jlHgl;
                private String jlHs;
                private String jsHgl;
                private String jsHu;

                public String getInspctionSunId() {
                    return inspctionSunId;
                }

                public void setInspctionSunId(String inspctionSunId) {
                    this.inspctionSunId = inspctionSunId;
                }

                public String getInspctionSunName() {
                    return inspctionSunName;
                }

                public void setInspctionSunName(String inspctionSunName) {
                    this.inspctionSunName = inspctionSunName;
                }

                public String getSgHgl() {
                    return sgHgl;
                }

                public void setSgHgl(String sgHgl) {
                    this.sgHgl = sgHgl;
                }

                public String getSgHs() {
                    return sgHs;
                }

                public void setSgHs(String sgHs) {
                    this.sgHs = sgHs;
                }

                public String getJlHgl() {
                    return jlHgl;
                }

                public void setJlHgl(String jlHgl) {
                    this.jlHgl = jlHgl;
                }

                public String getJlHs() {
                    return jlHs;
                }

                public void setJlHs(String jlHs) {
                    this.jlHs = jlHs;
                }

                public String getJsHgl() {
                    return jsHgl;
                }

                public void setJsHgl(String jsHgl) {
                    this.jsHgl = jsHgl;
                }

                public String getJsHu() {
                    return jsHu;
                }

                public void setJsHu(String jsHu) {
                    this.jsHu = jsHu;
                }
            }
        }
    }
}

