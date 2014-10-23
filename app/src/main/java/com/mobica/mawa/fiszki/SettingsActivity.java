package com.mobica.mawa.fiszki;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobica.mawa.fiszki.dao.language.JdbcLanguageDAO;
import com.mobica.mawa.fiszki.dao.language.Language;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        if(savedInstanceState == null){

            List<String> dictionaries = getListOfDictionaries();
            Spinner baseLanguageSpinner = (Spinner) findViewById(R.id.spinnerBaseLanguage);
            populateSpinner(baseLanguageSpinner, dictionaries);

            String baseLanguage = PreferencesHelper.getBaseLanguage(this);
            baseLanguageSpinner.setSelection(dictionaries.indexOf(baseLanguage));
            baseLanguageSpinner.setOnItemSelectedListener(new SpinnerListener(this, PreferencesHelper.BASE_LANGUAGE));

            Spinner refLanguageSpinner = (Spinner) findViewById(R.id.spinnerRefLanguage);
            populateSpinner(refLanguageSpinner, dictionaries);

            String refLanguage = PreferencesHelper.getRefLanguage(this);
            refLanguageSpinner.setSelection(dictionaries.indexOf(refLanguage));
            refLanguageSpinner.setOnItemSelectedListener(new SpinnerListener(this, PreferencesHelper.REF_LANGUAGE));
        }
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

        List<Language> languages = JdbcLanguageDAO.getInstance(this).query(Constants.UNLIMITED);
        List<String> langs = new ArrayList<String>();
        for (Language lang : languages){
            langs.add(lang.getName());
        }
        return langs;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    private class SpinnerListener implements AdapterView.OnItemSelectedListener{

        private Activity activity;
        private String propertyName;

        public SpinnerListener(Activity activity, String propertyName){
            this.activity = activity;
            this.propertyName = propertyName;
        }
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            PreferencesHelper.setProperty(activity, propertyName, ((TextView)view).getText().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

}
