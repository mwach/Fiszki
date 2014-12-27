package com.mobica.mawa.fiszki.rest;

import android.content.Context;

import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import javax.inject.Inject;

/**
 * Created by mawa on 09/12/14.
 */
public class RestAdapter {

    @Inject
    private Context context;
    private retrofit.RestAdapter restAdapter;

    public synchronized <T> T create(Class<T> service) {
        String endpointUrl = PreferencesHelper.getServerURL(context);

        if (restAdapter == null) {
            restAdapter = new retrofit.RestAdapter.Builder()
                    .setEndpoint(endpointUrl).build();
        }

        return restAdapter.create(service);
    }
}
