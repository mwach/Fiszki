package com.mobica.mawa.fiszki.quiz;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.dictionary.DictionaryDao;
import com.mobica.mawa.fiszki.dao.language.LanguageDao;
import com.mobica.mawa.fiszki.dao.word.Word;
import com.mobica.mawa.fiszki.dao.word.WordDao;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.List;

import roboguice.activity.RoboActivity;


public class QuizActivity extends RoboActivity implements QuizInterface {

    int correctAnswers = 0;
    int dictionaryId = 0;
    QuestionFragmentInterface quizQuestionFragment = new QuizQuestionFragment();
    List<Word> dbWords = null;

    private LanguageDao languageDao = null;
    private DictionaryDao dictionaryDao = null;
    private WordDao wordDao = null;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (languageDao != null) {
            languageDao.close();
            languageDao = null;
        }
        if (dictionaryDao != null) {
            dictionaryDao.close();
            dictionaryDao = null;
        }
        if (wordDao != null) {
            wordDao.close();
            wordDao = null;
        }
    }

    private LanguageDao getLanguageDao() {
        if (languageDao == null) {
            languageDao =
                    LanguageDao.getLanguageDao(this);
        }
        return languageDao;
    }

    private DictionaryDao getDictionaryDao() {
        if (dictionaryDao == null) {
            dictionaryDao =
                    DictionaryDao.getDictionaryDao(this);
        }
        return dictionaryDao;
    }

    private WordDao getWordDao() {
        if (wordDao == null) {
            wordDao =
                    WordDao.getWordDao(this);
        }
        return wordDao;
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
        dbWords = getWordDao().queryRandom(dictionaryId, noOfQuestions);

        Bundle bundle = new Bundle();
        bundle.putInt(QuestionFragmentInterface.NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(QuestionFragmentInterface.CURRENT_QUESTION_ID, 0);
        bundle.putString(QuestionFragmentInterface.CURRENT_WORD, dbWords.get(0).getBaseWord());

        ((Fragment) quizQuestionFragment).setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, (Fragment) quizQuestionFragment)
                .commit();
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
        return getLanguageDao().getBaseLanguage().getId();
    }

    public int getRefLanguage() {
        return getLanguageDao().getRefLanguage().getId();
    }

    public List<Dictionary> getListOfDictionaries() {
        int baseLanguage = getBaseLanguage();
        int refLanguage = getRefLanguage();
        return getDictionaryDao().getListOfDictionaries(baseLanguage, refLanguage);
    }

    @Override
    public int getNumberOfQuestions() {
        return PreferencesHelper.getNumberOfQuestions(this);
    }
}
