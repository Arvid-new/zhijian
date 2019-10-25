package com.haozhiyan.zhijian.bean.workfg;

import com.haozhiyan.zhijian.myDao.DaoSession;
import com.haozhiyan.zhijian.myDao.WorkPlotBeanDao;
import com.haozhiyan.zhijian.myDao.WorkProjectBeanDao;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;

/**
 * 项目list bean
 */
@Entity
public class WorkProjectBean {
    @Id()
    @Index(unique = true)
    public String pkId;
    public String iteamName;
    public String itemId;
    @ToMany(referencedJoinProperty = "itemId")
    public List<WorkPlotBean> childs;

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

    /**
     * Resets a to-many relationship, making the next get call to query for a fresh result.
     */
    @Generated(hash = 894932666)
    public synchronized void resetChilds() {
        childs = null;
    }

    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 1262711349)
    public List<WorkPlotBean> getChilds() {
        if (childs == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            WorkPlotBeanDao targetDao = daoSession.getWorkPlotBeanDao();
            List<WorkPlotBean> childsNew = targetDao._queryWorkProjectBean_Childs(pkId);
            synchronized (this) {
                if (childs == null) {
                    childs = childsNew;
                }
            }
        }
        return childs;
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 2040071989)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getWorkProjectBeanDao() : null;
    }

    /**
     * Used for active entity operations.
     */
    @Generated(hash = 198085028)
    private transient WorkProjectBeanDao myDao;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    public String getItemId() {
        return this.itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getIteamName() {
        return this.iteamName;
    }

    public void setIteamName(String iteamName) {
        this.iteamName = iteamName;
    }

    public String getPkId() {
        return this.pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    @Generated(hash = 2139805939)
    public WorkProjectBean(String pkId, String iteamName, String itemId) {
        this.pkId = pkId;
        this.iteamName = iteamName;
        this.itemId = itemId;
    }

    @Generated(hash = 1366021719)
    public WorkProjectBean() {
    }


}
