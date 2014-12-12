package com.mobica.mawa.fiszki.rest;

import com.mobica.mawa.fiszki.rest.dto.Languages;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by mawa on 08/12/14.
 */
public interface LanguagesService {

    @GET("/languages")
    void enumerate(Callback<Languages> callback);
}
