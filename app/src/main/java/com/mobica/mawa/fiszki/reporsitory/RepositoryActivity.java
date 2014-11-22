package com.mobica.mawa.fiszki.reporsitory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.dictionary.DictionaryDao;
import com.mobica.mawa.fiszki.dao.language.Language;
import com.mobica.mawa.fiszki.dao.language.LanguageDao;
import com.mobica.mawa.fiszki.dao.word.Word;
import com.mobica.mawa.fiszki.dao.word.WordDao;

import java.util.List;

public class RepositoryActivity extends Activity implements Repository {

    private LanguageDao languageDao = null;
    private DictionaryDao dictionaryDao = null;
    private WordDao wordDao = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    private LanguageDao getLanguageDao() {
        if (languageDao == null) {
            languageDao =
                    LanguageDao.getLanguageDao(this);
        }
        return languageDao;
    }

    private DictionaryDao getDictionaryDao() {
        if (dictionaryDao == null) {
            dictionaryDao =
                    DictionaryDao.getDictionaryDao(this);
        }
        return dictionaryDao;
    }

    private WordDao getWordDao() {
        if (wordDao == null) {
            wordDao =
                    WordDao.getWordDao(this);
        }
        return wordDao;
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
            default:
                return super.onOptionsItemSelected(item);
        }
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
        getDictionaryDao().delete(id);
    }

    @Override
    public void deleteWord(int id) {
        getWordDao().delete(id);

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
        getDictionaryDao().add(dictionary);
    }

    @Override
    public void addWord(Word word) {
        getWordDao().add(word);
    }

    @Override
    public int getBaseLanguage() {
        return getLanguageDao().getBaseLanguage().getId();
    }

    @Override
    public int getRefLanguage() {
        return getLanguageDao().getRefLanguage().getId();
    }

    @Override
    public List<Dictionary> getListOfDictionaries(int baseLanguage, int refLanguage) {
        return getDictionaryDao().getListOfDictionaries(baseLanguage, refLanguage);
    }

    @Override
    public List<Word> showWords(int dictionary) {
        return getWordDao().load(dictionary);
    }

}
