package com.mobica.mawa.fiszki.dao;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;
import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.dao.bean.Word;

import java.sql.SQLException;
import java.util.List;

/**
 * Base abstract class for all DAOs
 * Created by mawa on 14/12/14.
 */
public class AbstractDao<T> extends OrmLiteSqliteOpenHelper implements Crud<T> {

    private static final int DATABASE_VERSION = 9;
    private static final String DATABASE_NAME = "Fiszki.db";

    private Class<T> type;
    private Dao<T, Integer> dao = null;

    public AbstractDao(Context context, Class<T> type) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
        this.type = type;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(AbstractDao.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Language.class);
            TableUtils.createTable(connectionSource, Dictionary.class);
            TableUtils.createTable(connectionSource, Word.class);
        } catch (SQLException e) {
            Log.e(AbstractDao.class.getName(), "Cannot create database");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(AbstractDao.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Word.class, true);
            TableUtils.dropTable(connectionSource, Dictionary.class, true);
            TableUtils.dropTable(connectionSource, Language.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(AbstractDao.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (!db.isReadOnly()) {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }

    protected Dao<T, Integer> getDao() throws SQLException {
        if (dao == null) {
            dao = getDao(type);
        }
        return dao;
    }

    @Override
    public T create(T object) throws SQLException {
        int createCount = getDao().create(object);
        if (createCount == 0) {
            throw new SQLException("No rows were created");
        }
        return object;
    }

    @Override
    public void remove(int objectId) throws SQLException {

        int deleteCount = getDao().deleteById(objectId);
        if (deleteCount == 0) {
            throw new SQLException("No rows were removed");
        }
    }

    @Override
    public void update(T object) throws SQLException {
        int rowsUpdated = getDao().update(object);
        if (rowsUpdated == 0) {
            throw new SQLException("No rows were updated");
        } else if (rowsUpdated > 1) {
            throw new SQLException("More than one row was updated");
        }
    }

    @Override
    public T get(int objectId) throws SQLException {
        T returnedObject = getDao().queryForId(objectId);
        if (returnedObject == null) {
            throw new SQLException(String.format("No rows were returned for ID=%d", objectId));
        }
        return returnedObject;
    }

    @Override
    public List<T> enumerate() throws SQLException {
        return getDao().queryForAll();
    }
}
