package com.haozhiyan.zhijian.bean.biaoduan;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.InspectionOneBeanDao;
import com.haozhiyan.zhijian.myDao.InspectionTwoBeanDao;

@Entity
public class InspectionOneBean {

    public String secInsId;
    public String sectionId;
    @Id()
    @Index(unique = true)
    public String inspectionId;
    public String parentId;
    public String inspectionName;
    public String partsDivision;
    public String isNeedBuild;
    @ToMany(referencedJoinProperty = "parentId")
    public List<InspectionTwoBean> childIns;
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 692386843)
    public synchronized void resetChildIns() {
        childIns = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 623015418)
    public List<InspectionTwoBean> getChildIns() {
        if (childIns == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectionTwoBeanDao targetDao = daoSession.getInspectionTwoBeanDao();
            List<InspectionTwoBean> childInsNew = targetDao._queryInspectionOneBean_ChildIns(inspectionId);
            synchronized (this) {
                if(childIns == null) {
                    childIns = childInsNew;
                }
            }
        }
        return childIns;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1078616891)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getInspectionOneBeanDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 2010966478)
    private transient InspectionOneBeanDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public String getIsNeedBuild() {
        return this.isNeedBuild;
    }
    public void setIsNeedBuild(String isNeedBuild) {
        this.isNeedBuild = isNeedBuild;
    }
    public String getPartsDivision() {
        return this.partsDivision;
    }
    public void setPartsDivision(String partsDivision) {
        this.partsDivision = partsDivision;
    }
    public String getInspectionName() {
        return this.inspectionName;
    }
    public void setInspectionName(String inspectionName) {
        this.inspectionName = inspectionName;
    }
    public String getParentId() {
        return this.parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public String getInspectionId() {
        return this.inspectionId;
    }
    public void setInspectionId(String inspectionId) {
        this.inspectionId = inspectionId;
    }
    public String getSectionId() {
        return this.sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    public String getSecInsId() {
        return this.secInsId;
    }
    public void setSecInsId(String secInsId) {
        this.secInsId = secInsId;
    }
    @Generated(hash = 279782539)
    public InspectionOneBean(String secInsId, String sectionId,
            String inspectionId, String parentId, String inspectionName,
            String partsDivision, String isNeedBuild) {
        this.secInsId = secInsId;
        this.sectionId = sectionId;
        this.inspectionId = inspectionId;
        this.parentId = parentId;
        this.inspectionName = inspectionName;
        this.partsDivision = partsDivision;
        this.isNeedBuild = isNeedBuild;
    }
    @Generated(hash = 1044368873)
    public InspectionOneBean() {
    }
}
