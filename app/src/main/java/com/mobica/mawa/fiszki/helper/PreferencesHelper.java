package com.mobica.mawa.fiszki.helper;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.mobica.mawa.fiszki.R;

/**
 * Created by mawa on 2014-10-19.
 */
public final class PreferencesHelper {

    public static final String BASE_LANGUAGE = "BASE_LANGUAGE";
    public static final String REF_LANGUAGE = "REF_LANGUAGE";

    private PreferencesHelper(){
    }

    public static String getBaseLanguage(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String defAppBaseLanguage = activity.getString(R.string.baseLanguage);
        return sharedPref.getString(BASE_LANGUAGE, defAppBaseLanguage);
    }

    public static String getRefLanguage(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        String defAppBaseLanguage = activity.getString(R.string.refLanguage);
        return sharedPref.getString(REF_LANGUAGE, defAppBaseLanguage);
    }

    public static void setProperty(Activity activity, String propertyName, String propertyValue) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        sharedPref.edit().putString(propertyName, propertyValue).commit();
    }
}
