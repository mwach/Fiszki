package com.mobica.mawa.fiszki.reporsitory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AlertHelper;
import com.mobica.mawa.fiszki.dao.FiszkiDao;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;
import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.dao.bean.Word;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;
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

    @Inject
    FiszkiDao fiszkiDao;

    @Inject
    RestAdapter restAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fiszkiDao.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        showDictionaries();
    }

    public void showDictionaries() {
        getFragmentManager().beginTransaction()
                .replace(R.id.container, DictionariesListFragment.newInstance())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repository, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_home:
                showMainMenu();
                return true;
            case R.id.action_download:
                downloadDictionaries();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void downloadDictionaries() {
        DictionariesService ls = restAdapter.create(DictionariesService.class);

        ls.enumerate(
                PreferencesHelper.getBaseLanguage(this),
                PreferencesHelper.getRefLanguage(this),
                new Callback<Dictionaries>() {
                    @Override
                    public void success(Dictionaries dictionaries, Response response) {
                        if (dictionaries != null && dictionaries.dictionaries != null) {
                            showDownloadDictionaries(dictionaries);
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

    private void showDownloadDictionaries(Dictionaries dictionaries) {
        getFragmentManager().beginTransaction().
                replace(R.id.container, WebDictionariesFragment.newInstance(dictionaries))
                .commit();
    }

    private void showMainMenu() {
        Intent mainMenuIntent = new Intent(this, MainScreen.class);
        mainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainMenuIntent);
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void loadDictionary(int dictionaryId) {
        getFragmentManager().beginTransaction().
                replace(R.id.container, WordsListFragment.newInstance(dictionaryId))
                .commit();
    }

    @Override
    public void showAddWord(int dictionary) {
        getFragmentManager().beginTransaction().
                replace(R.id.container, AddWordFragment.newInstance(dictionary))
                .commit();
    }

    @Override
    public void deleteDictionary(int id) {
        try {
            fiszkiDao.getWordDao().removeDictionary(id);
            fiszkiDao.getDictionaryDao().remove(id);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "deleteDictionary", e);
            AlertHelper.showError(this, getString(R.string.couldNotDeleteDictionary));
        }
    }

    @Override
    public void deleteWord(int id) {
        try {
            fiszkiDao.getDictionaryDao().remove(id);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "deleteWord", e);
            AlertHelper.showError(this, getString(R.string.couldNotDeleteWord));
        }

    }

    @Override
    public void showAddDictionary() {
        getFragmentManager().beginTransaction().
                replace(R.id.container, AddDictionaryFragment.newInstance())
                .commit();
    }

    @Override
    public void addDictionary(Dictionary dictionary) {
        dictionary.setBaseLanguage(new Language(getBaseLanguage(), null, null));
        dictionary.setRefLanguage(new Language(getRefLanguage(), null, null));
        try {
            fiszkiDao.getDictionaryDao().create(dictionary);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "addDictionary", e);
            AlertHelper.showError(this, getString(R.string.couldNotCreateDir));
        }
    }

    @Override
    public void addWord(Word word) {
        try {
            fiszkiDao.getWordDao().create(word);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "addWord", e);
            AlertHelper.showError(this, getString(R.string.couldNotCreateWord));
        }
    }

    @Override
    public int getBaseLanguage() {
        try {
            return fiszkiDao.getLanguageDao().getBaseLanguage().getId();
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "geBaseLanguage", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveBaseLanguage));
        }
        return 0;
    }

    @Override
    public int getRefLanguage() {
        try {
            return fiszkiDao.getLanguageDao().getRefLanguage().getId();
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "geRefLanguage", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveRefLanguage));
        }
        return 0;
    }

    @Override
    public List<Dictionary> getListOfDictionaries(int baseLanguage, int refLanguage) {
        try {
            return fiszkiDao.getDictionaryDao().enumerate(baseLanguage, refLanguage);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "getListOfDictionaries", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveDictionaries));
        }
        return null;
    }

    @Override
    public List<Word> showWords(int dictionary) {
        try {
            return fiszkiDao.getWordDao().enumerate(dictionary);
        } catch (SQLException e) {
            Log.e(RepositoryActivity.class.getName(), "showWords", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveWords));
        }
        return null;
    }

}
