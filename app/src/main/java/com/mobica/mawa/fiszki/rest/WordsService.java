package com.mobica.mawa.fiszki.rest;

import com.mobica.mawa.fiszki.rest.dto.Words;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by mawa on 08/12/14.
 */
public interface WordsService {

    @GET("/words")
    void enumerate(@Query("dictionaryId") Long dictionaryId, Callback<Words> callback);
}
