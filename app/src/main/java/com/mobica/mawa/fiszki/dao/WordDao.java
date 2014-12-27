package com.mobica.mawa.fiszki.dao;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.mobica.mawa.fiszki.dao.bean.Word;
import com.mobica.mawa.fiszki.quiz.Strategy;

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

    public List<Word> enumerate(Long limit, Integer dictionaryId, Integer strategy) throws SQLException {
        QueryBuilder<Word, Integer> qb = getDao().queryBuilder();

        if (dictionaryId != null) {
            Where where = qb.where();
            where.eq(Word.DICT, dictionaryId);
        }
        qb.limit(limit);

        switch (strategy) {
            case Strategy.NEWEST:
                qb.orderBy(Word.ADDED, false);
                break;
            case Strategy.LEAST_KNOWN:
                qb.orderByRaw(String.format("%s-%s DESC", Word.UNKNOWN, Word.KNOWN));
                break;
            case Strategy.RANDOM:
            default:
                break;
        }
        PreparedQuery<Word> preparedQuery = qb.prepare();
        return getDao().query(preparedQuery);
    }

    @Override
    public void create(List<Word> dbWords) throws SQLException {
        for (Word word : dbWords) {
            getDao().create(word);
        }
    }

    @Override
    public void update(List<Word> dbWords) throws SQLException {
        for (Word word : dbWords) {
            getDao().update(word);
        }
    }

}
