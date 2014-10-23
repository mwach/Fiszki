package com.mobica.mawa.fiszki.dao.word;

/**
 * Created by mawa on 2014-10-02.
 */
public class Word {

    private long id;
    private int dictionaryId;
    private String baseWord;
    private String refWord;

    public Word(){
    }

    public Word(long id, int dictionaryId, String baseWord, String refWord){
        setId(id);
        setDictionaryId(dictionaryId);
        setBaseWord(baseWord);
        setRefWord(refWord);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(int dictionaryId) {
        this.dictionaryId = dictionaryId;
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
                "dictionaryId='" + dictionaryId + '\'' +
                "baseWord='" + baseWord + '\'' +
                ", refWord='" + refWord + '\'' +
                '}';
    }
}
