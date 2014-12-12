package com.mobica.mawa.fiszki.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mawa on 08/12/14.
 */
public class Words {

    @SerializedName(value = "word")
    public List<Word> words;

    public void setWord(List<Word> words) {
        this.words = words;
    }
}
