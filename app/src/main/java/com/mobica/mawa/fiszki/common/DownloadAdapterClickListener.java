package com.mobica.mawa.fiszki.common;

import com.mobica.mawa.fiszki.rest.dto.Dictionary;

/**
 */
public interface DownloadAdapterClickListener {

    public void downloadDictionary(Dictionary dictionary);

    public void displayDictionaryDetails(Dictionary dictionary);
}
