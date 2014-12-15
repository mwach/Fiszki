package com.mobica.mawa.fiszki.dao;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.mobica.mawa.fiszki.dao.bean.Word;

import java.sql.SQLException;
import java.util.List;

/**
 * DAO class for @Word
 * Created by mawa on 2014-10-18.
 */
class WordDao extends AbstractDao<Word> implements IWordDao {

    public WordDao(Context context) {
        super(context, Word.class);
    }

    public List<Word> enumerate(int dictionaryId) throws SQLException {
        QueryBuilder<Word, Integer> qb = getDao().queryBuilder();
        Where where = qb.where();
        where.eq(Word.DICT, dictionaryId);

        PreparedQuery<Word> preparedQuery = qb.prepare();
        List<Word> dbWords = getDao().query(preparedQuery);
        return dbWords;
    }

    @Override
    public void create(List<Word> dbWords) throws SQLException {
        for (Word word : dbWords) {
            getDao().create(word);
        }
    }
}
