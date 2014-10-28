package com.mobica.mawa.fiszki.reporsitory;

import android.content.Context;

/**
 * Created by mawa on 2014-10-26.
 */
public interface Repository {

    public Context getContext();
    public void loadDictionary(int dictionaryId);
    public void showDictionaries();
    public void addWord(int dictionary);

    public void deleteDictionary(int integer);
}
