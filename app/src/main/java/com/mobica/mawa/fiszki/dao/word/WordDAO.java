package com.mobica.mawa.fiszki.dao.word;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mawa on 2014-10-18.
 */
public class WordDao extends OrmLiteSqliteOpenHelper {

    public static final int DATABASE_VERSION = 14;
    public static final String DATABASE_NAME = "Word.db";
    private static final AtomicInteger usageCounter = new AtomicInteger(0);
    private static WordDao helper = null;
    private Dao<Word, Integer> wordDao = null;

    public WordDao(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    public static synchronized WordDao getWordDao(Context context) {
        if (helper == null) {
            helper = new WordDao(context);
        }
        usageCounter.incrementAndGet();
        return helper;
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

    @Override
    public void close() {
        if (usageCounter.decrementAndGet() == 0) {
            super.close();
            wordDao = null;
            helper = null;
        }
    }

    public List<Word> queryRandom(long dictionaryId, int limit) {
        // the name field must be equal to "foo"
        List<Word> response = new ArrayList<Word>();
        try {
            QueryBuilder<Word, Integer> qb = getWordDao().queryBuilder();
            Where where = qb.where();
            where.eq(Word.DICT, dictionaryId);

            PreparedQuery<Word> preparedQuery = qb.prepare();
            List<Word> dbWords = wordDao.query(preparedQuery);
            if (dbWords.isEmpty()) {
                throw new RuntimeException("No words in given dictionary");
            }
            Random random = new Random();
            while (response.size() < limit) {
                response.add(dbWords.get(random.nextInt(dbWords.size())));
            }
            return response;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id) {
        try {
            getWordDao().deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(Word word) {
        try {
            getWordDao().create(word);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Word> load(int dictionary) {
        try {
            return getWordDao().queryForEq(Word.DICT, dictionary);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
