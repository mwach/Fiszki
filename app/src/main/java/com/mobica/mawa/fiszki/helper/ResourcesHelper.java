package com.mobica.mawa.fiszki.helper;

import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.language.Language;
import com.mobica.mawa.fiszki.dao.word.Word;

/**
 * Created by mawa on 2014-10-19.
 */
public class ResourcesHelper {
    public static Language getLanguage(String language) {
        String[] tokens = language.split(";");
        return new Language(Integer.parseInt(tokens[0]), tokens[1], tokens[2]);
    }

    public static Dictionary getDictionary(String dictionary) {
        String[] tokens = dictionary.split(";");
        return new Dictionary(Integer.parseInt(tokens[0]), tokens[1], tokens[2],
                new Language(Integer.parseInt(tokens[3]), null, null),
                new Language(Integer.parseInt(tokens[4]), null, null));
    }

    public static Word getWord(String word) {
        String[] tokens = word.split(";");
        return new Word(Integer.parseInt(tokens[0]),
                new Dictionary(Integer.parseInt(tokens[1]), null, null, null, null), tokens[2], tokens[3]);
    }
}
