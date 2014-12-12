package com.mobica.mawa.fiszki.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.bean.Word;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-10-18.
 */
class WordDao extends OrmLiteSqliteOpenHelper implements IWordDao {

    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "Word.db";
    private Dao<Word, Integer> wordDao = null;

    public WordDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    private Dao<Word, Integer> getWordDao() throws SQLException {
        if (wordDao == null) {
            wordDao = getDao(Word.class);
        }
        return wordDao;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            Log.i(WordDao.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Word.class);
        } catch (SQLException e) {
            Log.e(WordDao.class.getName(), "Cannot create database");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            Log.i(WordDao.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Word.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(WordDao.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public List<Word> enumerate(int dictionaryId) throws SQLException {
        QueryBuilder<Word, Integer> qb = getWordDao().queryBuilder();
        Where where = qb.where();
        where.eq(Word.DICT, dictionaryId);

        PreparedQuery<Word> preparedQuery = qb.prepare();
        List<Word> dbWords = wordDao.query(preparedQuery);
        return dbWords;
    }

    @Override
    public void removeDictionary(int id) throws SQLException {
        DeleteBuilder<Word, Integer> db = getWordDao().deleteBuilder();
        Where where = db.where();
        where.eq(Word.DICT, id);

        getWordDao().delete(db.prepare());
    }

    @Override
    public void create(List<Word> dbWords) throws SQLException {
        for (Word word : dbWords) {
            getWordDao().create(word);
        }
    }

    @Override
    public Word create(Word object) throws SQLException {
        int wordId = getWordDao().create(object);
        object.setId(wordId);
        return object;
    }

    @Override
    public void remove(int objectId) throws SQLException {
        getWordDao().deleteById(objectId);
    }

    @Override
    public void update(Word object) throws SQLException {
        int rowsUpdated = getWordDao().update(object);
        if (rowsUpdated == 0) {
            throw new SQLException("No rows were updated");
        } else if (rowsUpdated > 1) {
            throw new SQLException("More than one row was updated");
        }
    }

    @Override
    public Word get(int objectId) throws SQLException {
        return getWordDao().queryForId(objectId);
    }

    @Override
    public List<Word> enumerate() throws SQLException {
        return getWordDao().queryForAll();
    }
}
