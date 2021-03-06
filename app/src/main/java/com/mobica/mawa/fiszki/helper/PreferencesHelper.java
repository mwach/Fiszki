package com.mobica.mawa.fiszki.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.quiz.Strategy;

/**
 * Created by mawa on 2014-10-19.
 */
public final class PreferencesHelper {

    public static final String BASE_LANGUAGE = "BASE_LANGUAGE";
    public static final String REF_LANGUAGE = "REF_LANGUAGE";
    public static final String QUIZ_NO_OF_QUESTIONS = "NO_OF_QUESTIONS";
    public static final String SERVER_URL = "SERVER_URL";
    public static final String STRATEGY = "STRATEGY";

    private PreferencesHelper() {
    }

    public static String getBaseLanguage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPref.getString(BASE_LANGUAGE, "");
    }

    public static String getRefLanguage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPref.getString(REF_LANGUAGE, "");
    }

    public static String getServerURL(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        String defaultUrl = context.getString(R.string.endpointURL);
        return sharedPref.getString(SERVER_URL, defaultUrl);
    }

    public static int getStrategy(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        int defStrategy = Strategy.RANDOM;
        return sharedPref.getInt(STRATEGY, defStrategy);
    }

    public static int getNumberOfQuestions(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        int defNoOfQuestions = Integer.parseInt(
                context.getString(R.string.defaultNoOfQuestions));
        return sharedPref.getInt(QUIZ_NO_OF_QUESTIONS, defNoOfQuestions);
    }

    public static void setProperty(Context context, String propertyName, String propertyValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        sharedPref.edit().putString(propertyName, propertyValue).apply();
    }

    public static void setProperty(Context context, String propertyName, int propertyValue) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        sharedPref.edit().putInt(propertyName, propertyValue).apply();
    }
}
