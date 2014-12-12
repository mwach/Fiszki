package com.mobica.mawa.fiszki.dao;

import android.app.Application;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Created by mawa on 2014-11-23.
 */
@Singleton
public class FiszkiDao {

    @Inject
    Application application;

    private LanguageDao languageDao = null;
    private WordDao wordDao = null;
    private DictionaryDao dictionaryDao = null;

    public synchronized ILanguageDao getLanguageDao() {
        if (languageDao == null) {
            languageDao = new LanguageDao(application);
        }
        return languageDao;
    }

    public synchronized IWordDao getWordDao() {
        if (wordDao == null) {
            wordDao = new WordDao(application);
        }
        return wordDao;
    }

    public synchronized IDictionaryDao getDictionaryDao() {
        if (dictionaryDao == null) {
            dictionaryDao = new DictionaryDao(application);
        }
        return dictionaryDao;
    }

    public synchronized void close() {
        if (languageDao != null) {
            languageDao.close();
            languageDao = null;
        }
        if (dictionaryDao != null) {
            dictionaryDao.close();
            dictionaryDao = null;
        }
        if (wordDao != null) {
            wordDao.close();
            wordDao = null;
        }
    }
}
