package com.haozhiyan.zhijian.myDao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.haozhiyan.zhijian.bean.biaoduan.BiaoDuanBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "BIAO_DUAN_BEAN".
*/
public class BiaoDuanBeanDao extends AbstractDao<BiaoDuanBean, String> {

    public static final String TABLENAME = "BIAO_DUAN_BEAN";

    /**
     * Properties of entity BiaoDuanBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property SectionId = new Property(0, String.class, "sectionId", true, "SECTION_ID");
        public final static Property SectionName = new Property(1, String.class, "sectionName", false, "SECTION_NAME");
        public final static Property Scope = new Property(2, String.class, "scope", false, "SCOPE");
        public final static Property DikuaiId = new Property(3, String.class, "dikuaiId", false, "DIKUAI_ID");
    };

    private DaoSession daoSession;


    public BiaoDuanBeanDao(DaoConfig config) {
        super(config);
    }
    
    public BiaoDuanBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"BIAO_DUAN_BEAN\" (" + //
                "\"SECTION_ID\" TEXT PRIMARY KEY NOT NULL ," + // 0: sectionId
                "\"SECTION_NAME\" TEXT," + // 1: sectionName
                "\"SCOPE\" TEXT," + // 2: scope
                "\"DIKUAI_ID\" TEXT);"); // 3: dikuaiId
        // Add Indexes
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_BIAO_DUAN_BEAN_SECTION_ID ON BIAO_DUAN_BEAN" +
                " (\"SECTION_ID\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"BIAO_DUAN_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, BiaoDuanBean entity) {
        stmt.clearBindings();
 
        String sectionId = entity.getSectionId();
        if (sectionId != null) {
            stmt.bindString(1, sectionId);
        }
 
        String sectionName = entity.getSectionName();
        if (sectionName != null) {
            stmt.bindString(2, sectionName);
        }
 
        String scope = entity.getScope();
        if (scope != null) {
            stmt.bindString(3, scope);
        }
 
        String dikuaiId = entity.getDikuaiId();
        if (dikuaiId != null) {
            stmt.bindString(4, dikuaiId);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, BiaoDuanBean entity) {
        stmt.clearBindings();
 
        String sectionId = entity.getSectionId();
        if (sectionId != null) {
            stmt.bindString(1, sectionId);
        }
 
        String sectionName = entity.getSectionName();
        if (sectionName != null) {
            stmt.bindString(2, sectionName);
        }
 
        String scope = entity.getScope();
        if (scope != null) {
            stmt.bindString(3, scope);
        }
 
        String dikuaiId = entity.getDikuaiId();
        if (dikuaiId != null) {
            stmt.bindString(4, dikuaiId);
        }
    }

    @Override
    protected final void attachEntity(BiaoDuanBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public String readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0);
    }    

    @Override
    public BiaoDuanBean readEntity(Cursor cursor, int offset) {
        BiaoDuanBean entity = new BiaoDuanBean( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // sectionId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // sectionName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // scope
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // dikuaiId
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, BiaoDuanBean entity, int offset) {
        entity.setSectionId(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSectionName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setScope(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setDikuaiId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final String updateKeyAfterInsert(BiaoDuanBean entity, long rowId) {
        return entity.getSectionId();
    }
    
    @Override
    public String getKey(BiaoDuanBean entity) {
        if(entity != null) {
            return entity.getSectionId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
