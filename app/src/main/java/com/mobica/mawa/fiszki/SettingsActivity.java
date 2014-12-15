package com.mobica.mawa.fiszki;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobica.mawa.fiszki.common.AlertHelper;
import com.mobica.mawa.fiszki.dao.FiszkiDao;
import com.mobica.mawa.fiszki.dao.bean.Language;
import com.mobica.mawa.fiszki.helper.NetworkHelper;
import com.mobica.mawa.fiszki.helper.ObjectHelper;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;
import com.mobica.mawa.fiszki.rest.LanguagesService;
import com.mobica.mawa.fiszki.rest.RestAdapter;
import com.mobica.mawa.fiszki.rest.dto.Languages;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class SettingsActivity extends RoboActivity {

    @Inject
    FiszkiDao fiszkiDao;
    @Inject
    RestAdapter restAdapter;
    @InjectView(R.id.spinnerBaseLanguage)
    private Spinner baseLanguageSpinner;
    @InjectView(R.id.spinnerRefLanguage)
    private Spinner refLanguageSpinner;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fiszkiDao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fillForm();
    }

    private void fillForm() {

        List<String> languages = getListOfLanguages();

        populateSpinner(baseLanguageSpinner, languages);

        String baseLanguage = PreferencesHelper.getBaseLanguage(this);
        baseLanguageSpinner.setSelection(languages.indexOf(baseLanguage));
        baseLanguageSpinner.setOnItemSelectedListener(new SpinnerListener(this, PreferencesHelper.BASE_LANGUAGE));

        populateSpinner(refLanguageSpinner, languages);

        String refLanguage = PreferencesHelper.getRefLanguage(this);
        refLanguageSpinner.setSelection(languages.indexOf(refLanguage));
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

    private List<String> getListOfLanguages() {

        List<Language> definedLanguages = new ArrayList<Language>();
        try {
            definedLanguages.addAll(fiszkiDao.getLanguageDao().enumerate());
        } catch (SQLException e) {
            Log.e(SettingsActivity.class.getName(), "getListOfDictionaries", e);
            AlertHelper.showError(SettingsActivity.this, getString(R.string.couldNotRetrieveDictionaries));
        }
        List<String> languages = new ArrayList<String>();
        for (Language lang : definedLanguages) {
            languages.add(lang.getName());
        }
        return languages;
    }

    private void refresh() {

        if (!NetworkHelper.isNetworkAvailable(this)) {
            AlertHelper.showInfo(SettingsActivity.this,
                    SettingsActivity.this.getString(R.string.networkDisabled),
                    SettingsActivity.this.getString(R.string.pleaseEnableNetwork));
            return;
        }
        LanguagesService ls = restAdapter.create(LanguagesService.class);
        ls.enumerate(new Callback<Languages>() {
            @Override
            public void success(Languages languages, Response response) {
                if (languages != null && languages.languages != null) {
                    List<Language> dbLanguages = ObjectHelper.fromLanguageDto(languages.languages);

                    try {
                        fiszkiDao.getLanguageDao().createOrUpdate(dbLanguages);
                    } catch (SQLException e) {
                        AlertHelper.showError(SettingsActivity.this, SettingsActivity.this.getString(R.string.couldNotUpdateDatabase));
                    }
                    fillForm();
                } else {
                    AlertHelper.showError(SettingsActivity.this, SettingsActivity.this.getString(R.string.noDataAvailableOnServer));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                AlertHelper.showError(SettingsActivity.this, String.format(SettingsActivity.this.getString(R.string.couldNotRetrieveLanguages), error.getLocalizedMessage()));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                Intent homeIntent = new Intent(this, MainScreen.class);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                return true;
            case R.id.action_refresh:
                refresh();
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
            TextView selectedItem = (TextView) view;
            if (selectedItem != null && selectedItem.getText() != null) {
                String selectedLanguage = selectedItem.getText().toString();
                PreferencesHelper.setProperty(context, propertyName, selectedLanguage);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
