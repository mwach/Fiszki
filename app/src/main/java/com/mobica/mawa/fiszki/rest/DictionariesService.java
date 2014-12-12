package com.mobica.mawa.fiszki.rest;

import com.mobica.mawa.fiszki.rest.dto.Dictionaries;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by mawa on 08/12/14.
 */
public interface DictionariesService {

    @GET("/dictionaries")
    void enumerate(@Query("baseLanguage") String baseLanguage, @Query("refLanguage") String refLanguage, Callback<Dictionaries> callback);
}
