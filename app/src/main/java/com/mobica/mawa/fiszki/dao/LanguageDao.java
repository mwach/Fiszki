package com.mobica.mawa.fiszki.dao;

import android.content.Context;

import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-12.
 */
class LanguageDao extends AbstractDao<Language> implements ILanguageDao {

    private final Context context;

    public LanguageDao(Context context) {
        super(context, Language.class);
        this.context = context;
    }

    public Language getBaseLanguage() throws SQLException {
        String baseLanguageName = PreferencesHelper.getBaseLanguage(context);
        List<Language> languages = getDao().queryForEq(Language.NAME, baseLanguageName);
        if (languages.isEmpty()) {
            throw new SQLException("BaseLanguage not found in the database");
        }
        return languages.get(0);
    }

    public Language getRefLanguage() throws SQLException {
        String refLanguageName = PreferencesHelper.getRefLanguage(context);
        List<Language> languages = getDao().queryForEq(Language.NAME, refLanguageName);
        if (languages.isEmpty()) {
            throw new SQLException("RefLanguage not found in the database");
        }
        return languages.get(0);
    }

    public void createOrUpdate(List<Language> languages) throws SQLException {
        for (Language language : languages) {
            List<Language> dbLanguages = getDao().queryForEq(Language.NAME, language.getName());
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
