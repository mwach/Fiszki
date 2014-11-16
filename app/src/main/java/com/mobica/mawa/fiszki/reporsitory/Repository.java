package com.mobica.mawa.fiszki.reporsitory;

import android.content.Context;

import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.word.Word;

import java.util.List;

/**
 * Created by mawa on 2014-10-26.
 */
public interface Repository {

    public Context getContext();

    public void loadDictionary(int dictionaryId);

    public void showDictionaries();

    public void showAddWord(int dictionary);

    public void deleteDictionary(int integer);

    public void deleteWord(int position);

    public void showAddDictionary();

    public void addDictionary(Dictionary dictionary);

    public void addWord(Word word);

    int getBaseLanguage();

    int getRefLanguage();

    List<Dictionary> getListOfDictionaries();

    List<Word> loadDictWords(int dictionary);
}
