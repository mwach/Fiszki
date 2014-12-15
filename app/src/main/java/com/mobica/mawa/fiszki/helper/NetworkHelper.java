package com.mobica.mawa.fiszki.helper;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by mawa on 15/12/14.
 */
public final class NetworkHelper {

    private NetworkHelper() {
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable() && cm.getActiveNetworkInfo().isConnected());
    }

}
