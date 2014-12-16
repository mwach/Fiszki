package com.mobica.mawa.fiszki.reporsitory;

import com.mobica.mawa.fiszki.dao.bean.Dictionary;
import com.mobica.mawa.fiszki.dao.bean.Word;

import java.util.List;

/**
 * Repository interface
 * Used by the repository part of the application
 * Created by mawa on 2014-10-26.
 */
public interface Repository {

    public void showWords(int dictionaryId);

    public void showDictionaries();

    public void showAddWord(int dictionary);

    public void showAddDictionary();

    void showDownloadDictionaries();

    public void deleteDictionary(int integer);

    public void deleteWord(int position);

    public void addDictionary(Dictionary dictionary);

    public void addWord(Word word);

    int getBaseLanguage();

    int getRefLanguage();

    List<Dictionary> getDictionaries(int baseLanguage, int refLanguage);

    List<Word> getWords(int dictionary);
}
