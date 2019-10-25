package com.haozhiyan.zhijian.bean;

import java.util.List;

public class HomeProjectListBean {



    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * project : 名门地产
         * list : [{"id":"0","name":"名门紫园"},{"id":"0","name":"名门紫园"},{"id":"0","name":"名门紫园"},{"id":"0","name":"名门紫园"}]
         */

        private String project;
        private List<ListBean> list;

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 0
             * name : 名门紫园
             */

            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
