package com.mobica.mawa.fiszki.quiz;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AlertHelper;
import com.mobica.mawa.fiszki.dao.FiszkiDao;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;
import com.mobica.mawa.fiszki.dao.bean.Word;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import roboguice.activity.RoboActivity;


public class QuizActivity extends RoboActivity implements QuizInterface {

    int correctAnswers = 0;
    int dictionaryId = 0;
    QuestionFragmentInterface quizQuestionFragment = new QuizQuestionFragment();
    List<Word> dbWords = null;

    @Inject
    FiszkiDao fiszkiDao;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fiszkiDao.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new QuizMenuFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_home:
                showMainMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showMainMenu() {
        Intent mainMenuIntent = new Intent(this, MainScreen.class);
        mainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainMenuIntent);
    }

    public void startQuiz(int noOfQuestions, int dictionaryId) {

        this.dictionaryId = dictionaryId;
        PreferencesHelper.setProperty(this, PreferencesHelper.QUIZ_NO_OF_QUESTIONS, noOfQuestions);
        try {
            dbWords = fiszkiDao.getWordDao().enumerate(dictionaryId);
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "startQuiz", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveWords));
        }
        if (dbWords.isEmpty()) {
            AlertHelper.showError(this, getString(R.string.emptyDictionary));
            return;
        }

        List<Word> filteredWords = filterWords(dbWords, noOfQuestions);

        Bundle bundle = new Bundle();
        bundle.putInt(QuestionFragmentInterface.NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(QuestionFragmentInterface.CURRENT_QUESTION_ID, 0);
        bundle.putString(QuestionFragmentInterface.CURRENT_WORD, dbWords.get(0).getBaseWord());

        ((Fragment) quizQuestionFragment).setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, (Fragment) quizQuestionFragment)
                .commit();
    }

    private List<Word> filterWords(List<Word> dbWords, int limit) {

        List<Word> response = new ArrayList<Word>();
        //, noOfQuestions
        Random random = new Random();
        while (dbWords.size() < limit) {
            response.add(dbWords.get(random.nextInt(dbWords.size())));
        }
        return response;
    }


    public void showAnswer(View view) {
        quizQuestionFragment.setCurrentWordResponse(dbWords.get(quizQuestionFragment.getCurrentQuestionId()).getRefWord());

    }

    public void answerKnown(View view) {
        correctAnswers++;
        showNextWord();
    }

    private void showNextWord() {
        int wordId = quizQuestionFragment.getCurrentQuestionId();
        if ((wordId + 1) < dbWords.size()) {
            wordId++;
            quizQuestionFragment.setCurrentQuestionId(wordId);
            quizQuestionFragment.setCurrentWord(dbWords.get(wordId).getBaseWord());
        } else {
            showTestSummary();
        }
    }

    private void showTestSummary() {
        QuizSummary quizSummary = new QuizSummary();
        Bundle bundle = new Bundle();
        bundle.putInt(QuizSummary.CORRECT_ANSWERS, correctAnswers);
        bundle.putInt(QuizSummary.NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(QuizSummary.DICTIONARY_ID, dictionaryId);
        bundle.putInt(QuizSummary.RATIO, (100 * correctAnswers / dbWords.size()));
        quizSummary.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, quizSummary)
                .commit();
    }

    public void answerUnknown(View view) {
        showNextWord();
    }

    public int getBaseLanguage() {
        try {
            return fiszkiDao.getLanguageDao().getBaseLanguage().getId();
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "getBaseLanguage", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveBaseLanguage));
        }
        return 0;
    }

    public int getRefLanguage() {
        try {
            return fiszkiDao.getLanguageDao().getRefLanguage().getId();
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "getRefLanguage", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveRefLanguage));
        }
        return 0;
    }

    public List<Dictionary> getListOfDictionaries() {
        int baseLanguage = getBaseLanguage();
        int refLanguage = getRefLanguage();
        List<Dictionary> dictionaries = new ArrayList<Dictionary>();
        try {
            dictionaries.addAll(fiszkiDao.getDictionaryDao().enumerate(baseLanguage, refLanguage));
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "getListOfDictionaries", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveDictionaries));
        }
        return dictionaries;
    }

    @Override
    public int getNumberOfQuestions() {
        return PreferencesHelper.getNumberOfQuestions(this);
    }
}
