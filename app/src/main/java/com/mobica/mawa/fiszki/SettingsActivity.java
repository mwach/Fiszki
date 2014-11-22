package com.mobica.mawa.fiszki;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobica.mawa.fiszki.dao.language.Language;
import com.mobica.mawa.fiszki.dao.language.LanguageDao;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class SettingsActivity extends RoboActivity {

    @InjectView(R.id.spinnerBaseLanguage)
    private Spinner baseLanguageSpinner;
    @InjectView(R.id.spinnerRefLanguage)
    private Spinner refLanguageSpinner;

    private LanguageDao languageDao = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (languageDao != null) {
            languageDao.close();
            languageDao = null;
        }
    }

    private LanguageDao getLanguageDao() {
        if (languageDao == null) {
            languageDao =
                    LanguageDao.getLanguageDao(this);
        }
        return languageDao;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        List<String> dictionaries = getListOfDictionaries();

        populateSpinner(baseLanguageSpinner, dictionaries);

        String baseLanguage = PreferencesHelper.getBaseLanguage(this);
        baseLanguageSpinner.setSelection(dictionaries.indexOf(baseLanguage));
        baseLanguageSpinner.setOnItemSelectedListener(new SpinnerListener(this, PreferencesHelper.BASE_LANGUAGE));

        populateSpinner(refLanguageSpinner, dictionaries);

        String refLanguage = PreferencesHelper.getRefLanguage(this);
        refLanguageSpinner.setSelection(dictionaries.indexOf(refLanguage));
        refLanguageSpinner.setOnItemSelectedListener(new SpinnerListener(this, PreferencesHelper.REF_LANGUAGE));
    }

    private void populateSpinner(Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private List<String> getListOfDictionaries() {

        List<Language> definedLanguages = getLanguageDao().queryForAll();
        List<String> languages = new ArrayList<String>();
        for (Language lang : definedLanguages) {
            languages.add(lang.getName());
        }
        return languages;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeIntent = new Intent();
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener {

        private Context context;
        private String propertyName;

        public SpinnerListener(Context context, String propertyName) {
            this.context = context;
            this.propertyName = propertyName;
        }

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String selectedLanguage = ((TextView) view).getText().toString();
            PreferencesHelper.setProperty(context, propertyName, selectedLanguage);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
