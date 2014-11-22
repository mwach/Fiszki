package com.mobica.mawa.fiszki.dao.dictionary;

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

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mawa on 2014-11-12.
 */
public class DictionaryDao extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "Dictionary.db";
    private static final AtomicInteger usageCounter = new AtomicInteger(0);
    private static DictionaryDao helper = null;
    private Dao<Dictionary, Integer> dictionaryDao = null;

    public DictionaryDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    public static synchronized DictionaryDao getDictionaryDao(Context context) {
        if (helper == null) {
            helper = new DictionaryDao(context);
        }
        usageCounter.incrementAndGet();
        return helper;
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
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            dictionaryDao = null;
            helper = null;
        }
    }

    public List<Dictionary> getListOfDictionaries(int baseLanguage, int refLanguage) {

        // the name field must be equal to "foo"
        try {
            QueryBuilder<Dictionary, Integer> qb = getDictionaryDao().queryBuilder();
            Where where = qb.where();
            where.eq(Dictionary.BASE_LANG, baseLanguage);
            // and
            where.and();
            // the password field must be equal to "_secret"
            where.eq(Dictionary.REF_LANG, refLanguage);
            PreparedQuery<Dictionary> preparedQuery = qb.prepare();
            return dictionaryDao.query(preparedQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(Dictionary dictionary) {
        try {
            getDictionaryDao().create(dictionary);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            getDictionaryDao().deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
