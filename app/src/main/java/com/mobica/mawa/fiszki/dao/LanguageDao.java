package com.mobica.mawa.fiszki.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-12.
 */
class LanguageDao extends OrmLiteSqliteOpenHelper implements ILanguageDao {

    public static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "Language.db";
    private Dao<Language, Integer> languageDao = null;
    private Context context;

    public LanguageDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.context = context;
    }

    private Dao<Language, Integer> getLanguageDao() throws SQLException {
        if (languageDao == null) {
            languageDao = getDao(Language.class);
        }
        return languageDao;
    }

    public Language getBaseLanguage() throws SQLException {
        String baseLanguageName = PreferencesHelper.getBaseLanguage(context);
        return getLanguageDao().queryForEq(Language.NAME, baseLanguageName).get(0);
    }

    public Language getRefLanguage() throws SQLException {
        String refLanguageName = PreferencesHelper.getRefLanguage(context);
        return getLanguageDao().queryForEq(Language.NAME, refLanguageName).get(0);
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
    public Language create(Language object) throws SQLException {
        int languageId = getLanguageDao().create(object);
        object.setId(languageId);
        return object;

    }

    @Override
    public void remove(int objectId) throws SQLException {
        getLanguageDao().deleteById(objectId);
    }

    @Override
    public void update(Language object) throws SQLException {
        int rowsUpdated = getLanguageDao().update(object);
        if (rowsUpdated == 0) {
            throw new SQLException("No rows were updated");
        } else if (rowsUpdated > 1) {
            throw new SQLException("More than one row was updated");
        }

    }

    @Override
    public Language get(int objectId) throws SQLException {
        return getLanguageDao().queryForId(objectId);
    }

    @Override
    public List<Language> enumerate() throws SQLException {
        return getLanguageDao().queryForAll();
    }


    public void createOrUpdate(List<Language> languages) throws SQLException {
        for (Language language : languages) {
            List<Language> dbLanguages = getLanguageDao().queryForEq(Language.NAME, language.getName());
            if (dbLanguages.isEmpty()) {
                create(language);
            } else {
                dbLanguages.get(0).setDescription(language.getDescription());
                dbLanguages.get(0).setName(language.getName());
                update(dbLanguages.get(0));
            }
        }
    }


}
