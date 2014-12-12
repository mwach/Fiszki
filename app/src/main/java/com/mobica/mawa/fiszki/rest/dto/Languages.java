package com.mobica.mawa.fiszki.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by mawa on 08/12/14.
 */
public class Languages {

    @SerializedName(value = "language")
    public List<Language> languages;

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }
}
