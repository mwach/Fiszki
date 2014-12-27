package com.mobica.mawa.fiszki.quiz;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    int currentQuestion = 0;
    int correctAnswers = 0;
    int dictionaryId = 0;
    int strategy = Strategy.RANDOM;
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

    public void startQuiz(int noOfQuestions, int dictionaryId, int strategy) {

        this.dictionaryId = dictionaryId;
        this.strategy = strategy;
        this.correctAnswers = 0;
        this.currentQuestion = 0;

        PreferencesHelper.setProperty(this, PreferencesHelper.QUIZ_NO_OF_QUESTIONS, noOfQuestions);
        PreferencesHelper.setProperty(this, PreferencesHelper.STRATEGY, strategy);

        try {
            dbWords = fiszkiDao.getWordDao().enumerate(
                    (long) noOfQuestions,
                    dictionaryId == QuizInterface.UNDEFINED_DICTIONARY ? null : dictionaryId,
                    strategy
            );
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "startQuiz", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveWords));
        }
        if (dbWords.isEmpty()) {
            AlertHelper.showError(this, getString(R.string.emptyDictionary));
            return;
        }

        dbWords = filterWords(dbWords, noOfQuestions);

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

        if (dbWords.size() == limit) {
            return dbWords;
        }
        List<Word> response = new ArrayList<Word>();
        //, noOfQuestions
        Random random = new Random();
        while (response.size() < limit) {
            response.add(dbWords.get(random.nextInt(dbWords.size())));
        }
        return response;
    }


    public void answerKnown(View view) {
        correctAnswers++;
        dbWords.get(currentQuestion).incKnown();
        dbWords.get(currentQuestion).setLastKnown(System.currentTimeMillis());
        showNextWord();
    }

    public void answerUnknown(View view) {
        dbWords.get(currentQuestion).incUnknown();
        dbWords.get(currentQuestion).setLastUnknown(System.currentTimeMillis());
        showNextWord();
    }

    private void showNextWord() {
        if (++currentQuestion < dbWords.size()) {
            quizQuestionFragment.setCurrentQuestionId(currentQuestion);
            quizQuestionFragment.setCurrentWord(dbWords.get(currentQuestion).getBaseWord());
            quizQuestionFragment.setCurrentWordResponse(dbWords.get(currentQuestion).getRefWord());
        } else {
            persistQuizResults(dbWords);
            showTestSummary();
        }
    }

    private void persistQuizResults(List<Word> dbWords) {
        try {
            fiszkiDao.getWordDao().update(dbWords);
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "Could not update DB with test results", e);
            AlertHelper.showError(QuizActivity.this, getString(R.string.quizResultSaveFailed));
        }
    }

    private void showTestSummary() {
        QuizSummary quizSummary = new QuizSummary();
        Bundle bundle = new Bundle();
        bundle.putInt(QuizSummary.CORRECT_ANSWERS, correctAnswers);
        bundle.putInt(QuizSummary.NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(QuizSummary.DICTIONARY_ID, dictionaryId);
        bundle.putInt(QuizSummary.STRATEGY, strategy);
        bundle.putInt(QuizSummary.RATIO, (100 * correctAnswers / dbWords.size()));
        quizSummary.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, quizSummary)
                .commit();
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

    @Override
    public List<Dictionary> getListOfDictionaries() {
        int baseLanguage = getBaseLanguage();
        int refLanguage = getRefLanguage();
        List<Dictionary> dictionaries = new ArrayList<Dictionary>();
        try {
            dictionaries.addAll(fiszkiDao.getDictionaryDao().enumerate(baseLanguage, refLanguage));
        } catch (SQLException e) {
            Log.e(QuizActivity.class.getName(), "getDictionaries", e);
            AlertHelper.showError(this, getString(R.string.couldNotRetrieveDictionaries));
        }
        return dictionaries;
    }

    @Override
    public int getNumberOfQuestions() {
        return PreferencesHelper.getNumberOfQuestions(this);
    }
}
