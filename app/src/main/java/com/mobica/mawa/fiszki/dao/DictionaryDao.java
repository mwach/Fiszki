package com.mobica.mawa.fiszki.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-12.
 */
class DictionaryDao extends OrmLiteSqliteOpenHelper implements IDictionaryDao {

    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "Dictionary.db";
    private Dao<Dictionary, Integer> dictionaryDao = null;

    public DictionaryDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(DictionaryDao.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Dictionary.class);
        } catch (SQLException e) {
            Log.e(DictionaryDao.class.getName(), "Cannot create database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            Log.i(DictionaryDao.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Dictionary.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(DictionaryDao.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    private Dao<Dictionary, Integer> getDictionaryDao() throws SQLException {
        if (dictionaryDao == null) {
            dictionaryDao = getDao(Dictionary.class);
        }
        return dictionaryDao;
    }

    @Override
    public List<Dictionary> enumerate(int baseLanguage, int refLanguage) throws SQLException {

        QueryBuilder<Dictionary, Integer> qb = getDictionaryDao().queryBuilder();
        Where where = qb.where();
        where.eq(Dictionary.BASE_LANG, baseLanguage);
        // and
        where.and();
        // the password field must be equal to "_secret"
        where.eq(Dictionary.REF_LANG, refLanguage);

        qb.orderBy(Dictionary.DESC, false);
        PreparedQuery<Dictionary> preparedQuery = qb.prepare();
        return dictionaryDao.query(preparedQuery);
    }

    @Override
    public Dictionary create(Dictionary dictionary) throws SQLException {
        int dictionaryId = getDictionaryDao().create(dictionary);
        dictionary.setId(dictionaryId);
        return dictionary;
    }

    @Override
    public void remove(int objectId) throws SQLException {
        getDictionaryDao().deleteById(objectId);
    }

    @Override
    public void update(Dictionary object) throws SQLException {
        int rowsUpdated = getDictionaryDao().update(object);
        if (rowsUpdated == 0) {
            throw new SQLException("No rows were updated");
        } else if (rowsUpdated > 1) {
            throw new SQLException("More than one row was updated");
        }
    }

    @Override
    public Dictionary get(int objectId) throws SQLException {
        return getDictionaryDao().queryForId(objectId);
    }

    @Override
    public List<Dictionary> enumerate() throws SQLException {
        return getDictionaryDao().queryForAll();
    }
}