package com.mobica.mawa.fiszki.rest.dto;

/**
 * Created by mawa on 08/12/14.
 */
public class Word {

    private Long id;
    private Long dictionary;
    private String baseWord;
    private String refWord;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDictionary() {
        return dictionary;
    }

    public void setDictionary(Long dictionary) {
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

}
