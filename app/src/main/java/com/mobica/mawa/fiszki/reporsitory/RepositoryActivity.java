package com.mobica.mawa.fiszki.reporsitory;

import android.os.Bundle;
import android.util.Log;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AlertHelper;
import com.mobica.mawa.fiszki.dao.FiszkiDao;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;
import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.dao.bean.Word;
import com.mobica.mawa.fiszki.helper.NetworkHelper;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;
import com.mobica.mawa.fiszki.quiz.Strategy;
import com.mobica.mawa.fiszki.rest.DictionariesService;
import com.mobica.mawa.fiszki.rest.RestAdapter;
import com.mobica.mawa.fiszki.rest.dto.Dictionaries;

import java.sql.SQLException;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.RoboActivity;

public class RepositoryActivity extends RoboActivity implements Repository {

    private static final String FRAGMENT = "FRAGMENT";
    private static final String DICTIONARY = "DICTIONARY";
    @Inject
    private FiszkiDao fiszkiDao;
    @Inject
    private RestAdapter restAdapter;
    private RepositoryFragment currentFragment = RepositoryFragment.DictionariesList;
    private int dictionaryId = 0;
    private boolean isSinglePane = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fiszkiDao.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);

        isSinglePane = (findViewById(R.id.container_large) == null);

        if (savedInstanceState != null) {
            currentFragment = RepositoryFragment.valueOf(savedInstanceState.getString(FRAGMENT));
            dictionaryId = savedInstanceState.getInt(DICTIONARY);
        }
        if (isSinglePane) {
            switch (currentFragment) {
                case AddDictionary:
                    showAddDictionary();
                    break;
                case DownloadDictionaries:
                    showDownloadDictionaries();
                    break;
                case WordsList:
                    showWords(dictionaryId);
                    break;
                case AddWord:
                    showAddWord(dictionaryId);
                    break;
                case DictionariesList:
                default:
                    showDictionaries();
                    break;
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(FRAGMENT, currentFragment.name());
        outState.putInt(DICTIONARY, dictionaryId);
        super.onSaveInstanceState(outState);
    }

    public void showDictionaries() {
        currentFragment = RepositoryFragment.DictionariesList;
        if (isSinglePane) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, DictionariesListFragment.newInstance())
                    .commit();
        } else {
            refreshDictionaries();
            showWords(dictionaryId);

        }
    }

    @Override
    public void refreshDictionaries() {
        if (!isSinglePane) {
            DictionariesListFragment dlf = (DictionariesListFragment) getFragmentManager().findFragmentById(R.id.fragment_large_dictionaries);
            dlf.showDictionaries();
        }
    }

    @Override
    public void showDownloadDictionaries() {
        currentFragment = RepositoryFragment.DownloadDictionaries;
        if (NetworkHelper.isNetworkDisabled(this)) {
            AlertHelper.showInfo(RepositoryActivity.this,
                    RepositoryActivity.this.getString(R.string.networkDisabled),
                    RepositoryActivity.this.getString(R.string.pleaseEnableNetwork));
            return;
        }

        DictionariesService ls = restAdapter.create(DictionariesService.class);

        ls.enumerate(
                PreferencesHelper.getBaseLanguage(this),
                PreferencesHelper.getRefLanguage(this),
                new Callback<Dictionaries>() {
                    @Override
                    public void success(Dictionaries dictionaries, Response response) {
                        if (dictionaries != null && dictionaries.dictionaries != null) {
                            showDownloadedDictionaries(dictionaries);
                        } else {
                            AlertHelper.showError(RepositoryActivity.this, RepositoryActivity.this.getString(R.string.noDataAvailableOnServer));
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        AlertHelper.showError(RepositoryActivity.this, String.format(RepositoryActivity.this.getString(R.string.couldNotRetrieveLanguages), error.getLocalizedMessage()));
                    }
                });

    }

    private void showDownloadedDictionaries(Dictionaries dictionaries) {
        getFragmentManager().beginTransaction().
                replace(R.id.container, WebDictionariesFragment.newInstance(dictionaries))
                .commit();
    }

    @Override
    public void showWords(int dictionaryId) {
        currentFragment = RepositoryFragment.WordsList;
        this.dictionaryId = dictionaryId;

        getFragmentManager().beginTransaction().
                replace(R.id.container, WordsListFragment.newInstance(dictionaryId))
                .commit();
    }

    @Override
    public void showAddWord(int dictionary) {
        currentFragment = RepositoryFragment.AddWord;
        this.dictionaryId = dictionary;
        getFragmentManager().beginTransaction().
                replace(R.id.container, AddWordFragment.newInstance(dictionary))
                .commit();
    }

    @Override
    public void showAddDictionary() {
        currentFragment = RepositoryFragment.AddDictionary;
        getFragmentManager().beginTransaction().
                replace(R.id.container, AddDictionaryFragment.newInstance())
                .commit();
    }

    @Override
    public void deleteDictionary(int id) {
        try {
            fiszkiDao.getDictionaryDao().remove(id);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "deleteDictionary", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotDeleteDictionary));
        }
    }

    @Override
    public void deleteWord(int id) {
        try {
            fiszkiDao.getDictionaryDao().remove(id);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "deleteWord", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotDeleteWord));
        }

    }

    @Override
    public void addDictionary(Dictionary dictionary) {
        dictionary.setBaseLanguage(new Language(getBaseLanguage(), null, null));
        dictionary.setRefLanguage(new Language(getRefLanguage(), null, null));
        try {
            dictionaryId = fiszkiDao.getDictionaryDao().create(dictionary).getId();
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "addDictionary", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotCreateDir));
        }
        refreshDictionaries();
    }

    @Override
    public void addWord(Word word) {
        try {
            fiszkiDao.getWordDao().create(word);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "addWord", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotCreateWord));
        }
    }

    @Override
    public int getBaseLanguage() {
        try {
            return fiszkiDao.getLanguageDao().getBaseLanguage().getId();
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "geBaseLanguage", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotRetrieveBaseLanguage));
        }
        return 0;
    }

    @Override
    public int getRefLanguage() {
        try {
            return fiszkiDao.getLanguageDao().getRefLanguage().getId();
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "geRefLanguage", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotRetrieveRefLanguage));
        }
        return 0;
    }

    @Override
    public List<Dictionary> getDictionaries(int baseLanguage, int refLanguage) {
        try {
            return fiszkiDao.getDictionaryDao().enumerate(baseLanguage, refLanguage);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "getDictionaries", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotRetrieveDictionaries));
        }
        return null;
    }

    @Override
    public List<Word> getWords(int dictionary) {
        try {
            return fiszkiDao.getWordDao().enumerate(null, dictionary, Strategy.RANDOM);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "getWords", e);
            AlertHelper.showError(RepositoryActivity.this, getString(R.string.couldNotRetrieveWords));
        }
        return null;
    }

    private enum RepositoryFragment {
        DictionariesList,
        WordsList,
        AddDictionary,
        AddWord,
        DownloadDictionaries
    }

}
