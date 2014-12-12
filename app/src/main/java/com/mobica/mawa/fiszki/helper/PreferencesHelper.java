package com.mobica.mawa.fiszki.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobica.mawa.fiszki.R;

/**
 * Created by mawa on 2014-10-19.
 */
public final class PreferencesHelper {

    public static final String BASE_LANGUAGE = "BASE_LANGUAGE";
    public static final String REF_LANGUAGE = "REF_LANGUAGE";
    public static final String QUIZ_NO_OF_QUESTIONS = "NO_OF_QUESTIONS";

    private PreferencesHelper() {
    }

    public static String getBaseLanguage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        String defAppBaseLanguage = context.getString(R.string.baseLanguage);
        return sharedPref.getString(BASE_LANGUAGE, defAppBaseLanguage);
    }

    public static String getRefLanguage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        String defAppBaseLanguage = context.getString(R.string.refLanguage);
        return sharedPref.getString(REF_LANGUAGE, defAppBaseLanguage);
    }

    public static void setProperty(Context activity, String propertyName, String propertyValue) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE);
        sharedPref.edit().putString(propertyName, propertyValue).commit();
    }

    public static void setProperty(Context activity, String propertyName, int propertyValue) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE);
        sharedPref.edit().putInt(propertyName, propertyValue).commit();
    }

    public static int getNumberOfQuestions(Context activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(activity.getString(R.string.app_name), Context.MODE_PRIVATE);
        int defNoOfQuestions = Integer.parseInt(
                activity.getString(R.string.noOfQuestions));
        return sharedPref.getInt(QUIZ_NO_OF_QUESTIONS, defNoOfQuestions);
    }
}
