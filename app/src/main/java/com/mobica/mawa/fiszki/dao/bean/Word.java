package com.mobica.mawa.fiszki.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "word")
public class Word {

    public static final String ID = "id";
    public static final String DICT = "dict";
    public static final String BASE_WORD = "base_word";
    public static final String REF_WORD = "ref_word";
    public static final String ADDED = "added";
    public static final String KNOWN = "known";
    public static final String UNKNOWN = "unknown";
    public static final String LAST_KNOWN = "last_known";
    public static final String LAST_UNKNOWN = "last_unknown";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false, foreign = true, columnName = DICT, columnDefinition = "integer references dictionary(id) on delete cascade")
    private Dictionary dictionary;
    @DatabaseField(canBeNull = false, columnName = BASE_WORD)
    private String baseWord;
    @DatabaseField(canBeNull = false, columnName = REF_WORD)
    private String refWord;

    @DatabaseField(canBeNull = false, columnName = ADDED)
    private long added;
    @DatabaseField(columnName = KNOWN)
    private int known;
    @DatabaseField(columnName = UNKNOWN)
    private int unknown;
    @DatabaseField(columnName = LAST_KNOWN)
    private long lastKnown;
    @DatabaseField(columnName = LAST_UNKNOWN)
    private long lastUnknown;

    public Word() {
        setAdded(System.currentTimeMillis());
        setKnown(0);
        setUnknown(0);
        setLastKnown(0);
        setLastUnknown(0);
    }

    public Word(int id, Dictionary dictionary, String baseWord, String refWord) {
        this();
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

    public long getAdded() {
        return added;
    }

    public void setAdded(long added) {
        this.added = added;
    }

    public int getKnown() {
        return known;
    }

    public void setKnown(int known) {
        this.known = known;
    }

    public void incKnown() {
        this.known += 1;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public void incUnknown() {
        this.unknown += 1;
    }

    public long getLastKnown() {
        return lastKnown;
    }

    public void setLastKnown(long lastKnown) {
        this.lastKnown = lastKnown;
    }

    public long getLastUnknown() {
        return lastUnknown;
    }

    public void setLastUnknown(long lastUnknown) {
        this.lastUnknown = lastUnknown;
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
