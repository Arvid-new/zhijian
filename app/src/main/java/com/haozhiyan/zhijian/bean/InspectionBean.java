package com.haozhiyan.zhijian.bean;

import java.util.List;

public class InspectionBean {


    /**
     * msg : success
     * code : 0
     * list : [{"sectionId":18,"inspectionId":2795979791138816,"parentId":0,"inspectionName":"抹灰工程","partsDivision":null,"childIns":[{"sectionId":18,"inspectionId":2795979916967936,"parentId":2795979791138816,"inspectionName":"基层处理","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795981804404736,"parentId":2795979791138816,"inspectionName":"防开裂网验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795982253195264,"parentId":2795979791138816,"inspectionName":"甩浆验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795982710374400,"parentId":2795979791138816,"inspectionName":"灰饼验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795983356297216,"parentId":2795979791138816,"inspectionName":"冲筋验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795983742173184,"parentId":2795979791138816,"inspectionName":"内粉验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795984400678912,"parentId":2795979791138816,"inspectionName":"养护","partsDivision":"分户","childIns":[]}]}]
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
         * sectionId : 18
         * inspectionId : 2795979791138816
         * parentId : 0
         * inspectionName : 抹灰工程
         * partsDivision : null
         * childIns : [{"sectionId":18,"inspectionId":2795979916967936,"parentId":2795979791138816,"inspectionName":"基层处理","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795981804404736,"parentId":2795979791138816,"inspectionName":"防开裂网验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795982253195264,"parentId":2795979791138816,"inspectionName":"甩浆验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795982710374400,"parentId":2795979791138816,"inspectionName":"灰饼验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795983356297216,"parentId":2795979791138816,"inspectionName":"冲筋验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795983742173184,"parentId":2795979791138816,"inspectionName":"内粉验收","partsDivision":"分户","childIns":[]},{"sectionId":18,"inspectionId":2795984400678912,"parentId":2795979791138816,"inspectionName":"养护","partsDivision":"分户","childIns":[]}]
         */

        private String secInsId;
        private String sectionId;
        private String inspectionId;
        private String parentId;
        private String inspectionName;
        private String  partsDivision;
        private List<ChildInsBean> childIns;

        public String getSecInsId() {
            return secInsId;
        }

        public void setSecInsId(String secInsId) {
            this.secInsId = secInsId;
        }

        public String getSectionId() {
            return sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getInspectionId() {
            return inspectionId;
        }

        public void setInspectionId(String inspectionId) {
            this.inspectionId = inspectionId;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getInspectionName() {
            return inspectionName;
        }

        public void setInspectionName(String inspectionName) {
            this.inspectionName = inspectionName;
        }

        public String  getPartsDivision() {
            return partsDivision;
        }

        public void setPartsDivision(String  partsDivision) {
            this.partsDivision = partsDivision;
        }

        public List<ChildInsBean> getChildIns() {
            return childIns;
        }

        public void setChildIns(List<ChildInsBean> childIns) {
            this.childIns = childIns;
        }

        public static class ChildInsBean {
            /**
             * sectionId : 18
             * inspectionId : 2795979916967936
             * parentId : 2795979791138816
             * inspectionName : 基层处理
             * partsDivision : 分户
             * childIns : []
             */

            private String secInsId;
            private String sectionId;
            private String  inspectionId;
            private String parentId;
            private String inspectionName;
            private String partsDivision;
            private String  isNeedBuild;
            private List<?> childIns;



            public String getIsNeedBuild() {
                return isNeedBuild;
            }

            public void setIsNeedBuild(String isNeedBuild) {
                this.isNeedBuild = isNeedBuild;
            }

            public String getSecInsId() {
                return secInsId;
            }

            public void setSecInsId(String secInsId) {
                this.secInsId = secInsId;
            }

            public String getSectionId() {
                return sectionId;
            }

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public String getInspectionId() {
                return inspectionId;
            }

            public void setInspectionId(String inspectionId) {
                this.inspectionId = inspectionId;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
            }

            public String getInspectionName() {
                return inspectionName;
            }

            public void setInspectionName(String inspectionName) {
                this.inspectionName = inspectionName;
            }

            public String getPartsDivision() {
                return partsDivision;
            }

            public void setPartsDivision(String partsDivision) {
                this.partsDivision = partsDivision;
            }

            public List<?> getChildIns() {
                return childIns;
            }

            public void setChildIns(List<?> childIns) {
                this.childIns = childIns;
            }
        }
    }
}
