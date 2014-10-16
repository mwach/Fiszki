package com.mobica.mawa.fiszki.dao;

/**
 * Created by mawa on 2014-10-02.
 */
public class Word {

    private long id;
    private String baseWord;
    private String baseLanguage;
    private String refWord;
    private String refLanguage;

    public Word(){
    }

    public Word(String baseWord, String baseLanguage, String refWord, String refLanguage){
        setBaseWord(baseWord);
        setBaseLanguage(baseLanguage);
        setRefWord(refWord);
        setRefLanguage(refLanguage);
    }

    public String getBaseWord() {
        return baseWord;
    }

    public void setBaseWord(String baseWord) {
        this.baseWord = baseWord;
    }

    public String getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(String baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public String getRefWord() {
        return refWord;
    }

    public void setRefWord(String refWord) {
        this.refWord = refWord;
    }

    public String getRefLanguage() {
        return refLanguage;
    }

    public void setRefLanguage(String refLanguage) {
        this.refLanguage = refLanguage;
    }


    @Override
    public String toString() {
        return "Word{" +
                "baseWord='" + baseWord + '\'' +
                ", baseLanguage='" + baseLanguage + '\'' +
                ", refWord='" + refWord + '\'' +
                ", refLanguage='" + refLanguage + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
}
