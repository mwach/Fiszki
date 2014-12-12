package com.mobica.mawa.fiszki.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "word")
public class Word {

    public static final String ID = "id";
    public static final String DICT = "dict";
    public static final String BASE_WORD = "base_word";
    public static final String REF_WORD = "ref_word";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = DICT)
    private Dictionary dictionary;
    @DatabaseField(canBeNull = false, columnName = BASE_WORD)
    private String baseWord;
    @DatabaseField(canBeNull = false, columnName = REF_WORD)
    private String refWord;

    public Word() {
    }

    public Word(int id, Dictionary dictionary, String baseWord, String refWord) {
        setId(id);
        setDictionary(dictionary);
        setBaseWord(baseWord);
        setRefWord(refWord);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String getBaseWord() {
        return baseWord;
    }

    public void setBaseWord(String baseWord) {
        this.baseWord = baseWord;
    }

    public String getRefWord() {
        return refWord;
    }

    public void setRefWord(String refWord) {
        this.refWord = refWord;
    }


    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                "dictionary='" + dictionary + '\'' +
                "baseWord='" + baseWord + '\'' +
                ", refWord='" + refWord + '\'' +
                '}';
    }
}
