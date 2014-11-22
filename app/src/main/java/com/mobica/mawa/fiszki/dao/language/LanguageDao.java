package com.mobica.mawa.fiszki.dao.language;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mawa on 2014-11-12.
 */
public class LanguageDao extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "Language.db";
    private static final AtomicInteger usageCounter = new AtomicInteger(0);
    private static LanguageDao helper = null;
    private Dao<Language, Integer> languageDao = null;
    private Context context;

    public LanguageDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context = context;
    }

    public static synchronized LanguageDao getLanguageDao(Context context) {
        if (helper == null) {
            helper = new LanguageDao(context);
        }
        usageCounter.incrementAndGet();
        return helper;
    }

    private Dao<Language, Integer> getLanguageDao() throws SQLException {
        if (languageDao == null) {
            languageDao = getDao(Language.class);
        }
        return languageDao;
    }

    public Language getBaseLanguage() {
        String baseLanguageName = PreferencesHelper.getBaseLanguage(context);
        try {
            return getLanguageDao().queryForEq(Language.NAME, baseLanguageName).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Language getRefLanguage() {
        String refLanguageName = PreferencesHelper.getRefLanguage(context);
        try {
            return getLanguageDao().queryForEq(Language.NAME, refLanguageName).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(LanguageDao.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Language.class);
        } catch (SQLException e) {
            Log.e(LanguageDao.class.getName(), "Cannot create database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            Log.i(LanguageDao.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Language.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(LanguageDao.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            languageDao = null;
            helper = null;
        }
    }

    public List<Language> queryForAll() {
        try {
            return getLanguageDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
