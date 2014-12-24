package com.mobica.mawa.fiszki;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mobica.mawa.fiszki.common.AlertHelper;
import com.mobica.mawa.fiszki.dao.FiszkiDao;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;
import com.mobica.mawa.fiszki.quiz.QuizActivity;
import com.mobica.mawa.fiszki.reporsitory.RepositoryActivity;

import java.sql.SQLException;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

public class MainActivity extends RoboActivity {

    @InjectView(R.id.quizImageButton)
    private ImageButton quizImageButton;

    @InjectView(R.id.dictImageButton)
    private ImageButton dictImageButton;

    @Inject
    FiszkiDao fiszkiDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        if(!isLanguageDefined()){
            AlertHelper.showInfo(MainActivity.this, getString(R.string.dbNotPopulated), getString(R.string.dbNotPopulatedMessage));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean languagesDownloaded = isLanguageDefined();
        quizImageButton.setEnabled(languagesDownloaded);
        dictImageButton.setEnabled(languagesDownloaded);
        quizImageButton.setImageResource(languagesDownloaded ? R.drawable.quiz : R.drawable.quiz_bw);
        dictImageButton.setImageResource(languagesDownloaded ? R.drawable.dict : R.drawable.dict_bw);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.menu_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quizImageButton:
            case R.id.quizTextView:
                Intent startQuizIntent = new Intent(this, QuizActivity.class);
                startActivity(startQuizIntent);
                break;
            case R.id.dictImageButton:
            case R.id.dictTextView:
                Intent startRepositoryIntent = new Intent(this, RepositoryActivity.class);
                startActivity(startRepositoryIntent);
                break;
            case R.id.statsImageButton:
            case R.id.statsTextView:
                break;
            case R.id.exitImageButton:
            case R.id.exitTextView:
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                break;
        }
    }

    private boolean isLanguageDefined(){
        try {
            return !PreferencesHelper.getBaseLanguage(this).equals("") && !fiszkiDao.getLanguageDao().enumerate().isEmpty();
        } catch (SQLException e) {
            AlertHelper.showError(MainActivity.this, getString(R.string.couldNotRetrieveBaseLanguage));
            Log.e(MainActivity.class.getName(), "isLanguageDefined", e);
        }
        return false;
    }
}
