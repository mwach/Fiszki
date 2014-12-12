package com.mobica.mawa.fiszki.rest;

import android.content.Context;

import com.mobica.mawa.fiszki.R;

import javax.inject.Inject;

/**
 * Created by mawa on 09/12/14.
 */
public class RestAdapter {

    @Inject
    Context context;
    private retrofit.RestAdapter restAdapter;

    public synchronized <T> T create(Class<T> service) {
        String endpointUrl = context.getString(R.string.endpointURL);

        if (restAdapter == null) {
            restAdapter = new retrofit.RestAdapter.Builder()
                    .setEndpoint(endpointUrl).build();
        }

        return restAdapter.create(service);
    }
}
