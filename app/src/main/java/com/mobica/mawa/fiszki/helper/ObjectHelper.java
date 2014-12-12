package com.mobica.mawa.fiszki.helper;

import com.mobica.mawa.fiszki.rest.dto.Dictionary;
import com.mobica.mawa.fiszki.rest.dto.Language;
import com.mobica.mawa.fiszki.rest.dto.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawa on 09/12/14.
 */
public class ObjectHelper {
    public static com.mobica.mawa.fiszki.dao.bean.Language fromLanguageDto(Language language) {
        com.mobica.mawa.fiszki.dao.bean.Language dbLanguage = new com.mobica.mawa.fiszki.dao.bean.Language();
        dbLanguage.setDescription(language.getDescription());
        dbLanguage.setId(language.getId() != null ? language.getId().intValue() : 0);
        dbLanguage.setName(language.getName());
        return dbLanguage;

    }

    public static List<com.mobica.mawa.fiszki.dao.bean.Language> fromLanguageDto(List<Language> languages) {
        List<com.mobica.mawa.fiszki.dao.bean.Language> dbLanguages = new ArrayList<com.mobica.mawa.fiszki.dao.bean.Language>();
        for (Language language : languages) {
            dbLanguages.add(fromLanguageDto(language));
        }
        return dbLanguages;
    }

    public static List<com.mobica.mawa.fiszki.dao.bean.Word> fromWordDto(List<Word> words, com.mobica.mawa.fiszki.dao.bean.Dictionary dictionary) {
        List<com.mobica.mawa.fiszki.dao.bean.Word> dbWords = new ArrayList<com.mobica.mawa.fiszki.dao.bean.Word>();
        for (Word word : words) {
            dbWords.add(fromWordDto(word, dictionary));
        }
        return dbWords;
    }

    public static com.mobica.mawa.fiszki.dao.bean.Word fromWordDto(Word word, com.mobica.mawa.fiszki.dao.bean.Dictionary dictionary) {
        com.mobica.mawa.fiszki.dao.bean.Word dbWord = new com.mobica.mawa.fiszki.dao.bean.Word();
        dbWord.setBaseWord(word.getBaseWord());
        dbWord.setId(word.getId() != null ? word.getId().intValue() : 0);
        dbWord.setRefWord(word.getRefWord());
        dbWord.setDictionary(dictionary);
        return dbWord;

    }

    public static com.mobica.mawa.fiszki.dao.bean.Dictionary fromDictionaryDto(Dictionary dictionary, com.mobica.mawa.fiszki.dao.bean.Language baseLanguage, com.mobica.mawa.fiszki.dao.bean.Language refLanguage) {
        com.mobica.mawa.fiszki.dao.bean.Dictionary dbDictionary = new com.mobica.mawa.fiszki.dao.bean.Dictionary();
        dbDictionary.setDescription(dictionary.getDescription());
        dbDictionary.setId(dictionary.getId() != null ? dictionary.getId().intValue() : 0);
        dbDictionary.setName(dictionary.getName());
        dbDictionary.setUuid(dictionary.getUuid());
        dbDictionary.setBaseLanguage(baseLanguage);
        dbDictionary.setRefLanguage(refLanguage);
        return dbDictionary;
    }
}
