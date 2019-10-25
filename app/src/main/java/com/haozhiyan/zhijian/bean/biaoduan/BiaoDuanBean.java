package com.haozhiyan.zhijian.bean.biaoduan;

import com.haozhiyan.zhijian.myDao.BiaoDuanBeanDao;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.InspectionOneBeanDao;
import com.haozhiyan.zhijian.myDao.TowerBeanDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

@Entity
public class BiaoDuanBean {
    @Id()
    @Index(unique = true)
    public String sectionId;
    public String sectionName;
    public String scope;
    public String dikuaiId;
    @ToMany(referencedJoinProperty = "towerId")
    public List<TowerBean> scopeChild;
    @ToMany(referencedJoinProperty = "sectionId")
    public List<InspectionOneBean> inspection;
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
    @Generated(hash = 1437341365)
    public synchronized void resetInspection() {
        inspection = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1621735759)
    public List<InspectionOneBean> getInspection() {
        if (inspection == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            InspectionOneBeanDao targetDao = daoSession.getInspectionOneBeanDao();
            List<InspectionOneBean> inspectionNew = targetDao._queryBiaoDuanBean_Inspection(sectionId);
            synchronized (this) {
                if(inspection == null) {
                    inspection = inspectionNew;
                }
            }
        }
        return inspection;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 1775485861)
    public synchronized void resetScopeChild() {
        scopeChild = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1431304629)
    public List<TowerBean> getScopeChild() {
        if (scopeChild == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TowerBeanDao targetDao = daoSession.getTowerBeanDao();
            List<TowerBean> scopeChildNew = targetDao._queryBiaoDuanBean_ScopeChild(sectionId);
            synchronized (this) {
                if(scopeChild == null) {
                    scopeChild = scopeChildNew;
                }
            }
        }
        return scopeChild;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1146554210)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getBiaoDuanBeanDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 836501565)
    private transient BiaoDuanBeanDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public String getDikuaiId() {
        return this.dikuaiId;
    }
    public void setDikuaiId(String dikuaiId) {
        this.dikuaiId = dikuaiId;
    }
    public String getScope() {
        return this.scope;
    }
    public void setScope(String scope) {
        this.scope = scope;
    }
    public String getSectionName() {
        return this.sectionName;
    }
    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
    public String getSectionId() {
        return this.sectionId;
    }
    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }
    @Generated(hash = 166294486)
    public BiaoDuanBean(String sectionId, String sectionName, String scope,
            String dikuaiId) {
        this.sectionId = sectionId;
        this.sectionName = sectionName;
        this.scope = scope;
        this.dikuaiId = dikuaiId;
    }
    @Generated(hash = 1206091733)
    public BiaoDuanBean() {
    }
}
