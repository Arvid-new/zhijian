package com.haozhiyan.zhijian.bean.biaoduan;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.TowerBeanDao;
import com.haozhiyan.zhijian.myDao.UnitBeanDao;
@Entity
public class TowerBean {

    @Id()
    @Index(unique = true)
    public String towerId;
    public String tower;
    @ToMany(referencedJoinProperty = "towerId")
    public List<UnitBean> uintChild;
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
    @Generated(hash = 398180213)
    public synchronized void resetUintChild() {
        uintChild = null;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 821303207)
    public List<UnitBean> getUintChild() {
        if (uintChild == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UnitBeanDao targetDao = daoSession.getUnitBeanDao();
            List<UnitBean> uintChildNew = targetDao._queryTowerBean_UintChild(towerId);
            synchronized (this) {
                if(uintChild == null) {
                    uintChild = uintChildNew;
                }
            }
        }
        return uintChild;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 895271227)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTowerBeanDao() : null;
    }
    /** Used for active entity operations. */
    @Generated(hash = 1154988382)
    private transient TowerBeanDao myDao;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    public String getTower() {
        return this.tower;
    }
    public void setTower(String tower) {
        this.tower = tower;
    }
    public String getTowerId() {
        return this.towerId;
    }
    public void setTowerId(String towerId) {
        this.towerId = towerId;
    }
    @Generated(hash = 206165159)
    public TowerBean(String towerId, String tower) {
        this.towerId = towerId;
        this.tower = tower;
    }
    @Generated(hash = 849194740)
    public TowerBean() {
    }
}
