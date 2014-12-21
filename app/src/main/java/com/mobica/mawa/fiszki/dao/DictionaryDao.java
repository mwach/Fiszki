package com.mobica.mawa.fiszki.dao;

import android.content.Context;

import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-12.
 */
class DictionaryDao extends AbstractDao<Dictionary> implements IDictionaryDao {

    public DictionaryDao(Context context) {
        super(context, Dictionary.class);
    }

    @Override
    public List<Dictionary> enumerate(int baseLanguage, int refLanguage) throws SQLException {

        QueryBuilder<Dictionary, Integer> qb = getDao().queryBuilder();
        Where where = qb.where();
        where.eq(Dictionary.BASE_LANG, baseLanguage);
        // and
        where.and();
        // the password field must be equal to "_secret"
        where.eq(Dictionary.REF_LANG, refLanguage);

        qb.orderBy(Dictionary.NAME, true);
        PreparedQuery<Dictionary> preparedQuery = qb.prepare();
        return getDao().query(preparedQuery);
    }
}
