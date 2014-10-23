package com.mobica.mawa.fiszki.quiz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.word.JdbcWordDAO;
import com.mobica.mawa.fiszki.dao.word.Word;
import com.mobica.mawa.fiszki.dto.WordQuiz;
import com.mobica.mawa.fiszki.dto.WordQuizHelper;

import java.util.List;


public class QuizActivity extends Activity {

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
        int id = item.getItemId();
        if (id == R.id.action_home) {
            showMainMenu();
        }else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.mainMenuButton:
                showMainMenu();
                break;
            default:
                break;
        }
    }

    public void showMainMenu(){
        Intent mainMenuIntent = new Intent(this, MainScreen.class);
        mainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainMenuIntent);
    }

    QuestionFragmentInterface quizQuestionFragment = new QuizQuestionFragment();
    List<WordQuiz> dbWords = null;

    public void startQuiz(View view) {

        EditText noOfQuestionsEditText = (EditText)findViewById(R.id.editTextNoOfQuestions);
        Spinner languageSpinner = (Spinner)findViewById(R.id.spinnerDictionaries);

        int noOfQuestions = Integer.parseInt(noOfQuestionsEditText.getText().toString());
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.QUIZ_NO_OF_QUESTIONS, noOfQuestions);
        editor.apply();

        List<Word> dbWords = JdbcWordDAO.getInstance(this).queryRandom(1, noOfQuestions);
        this.dbWords = WordQuizHelper.getList(dbWords);
        Bundle bundle = new Bundle();
        bundle.putInt("noOfQuestions", dbWords.size());
        bundle.putInt("currentQuestionId", 0);
        bundle.putString("currentWord", dbWords.isEmpty() ? "" : dbWords.get(0).getBaseWord());
        ((Fragment)quizQuestionFragment).setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, (Fragment)quizQuestionFragment)
                .commit();
    }


    public void showAnswer(View view) {
        quizQuestionFragment.setCurrentWordResponse(dbWords.get(0).getRefWord());

    }

    public void answerKnown(View view) {
        dbWords.get(quizQuestionFragment.getCurrentQuestionId()).setKnown(true);
        showNextWord();
    }

    private void showNextWord() {
        int wordId = quizQuestionFragment.getCurrentQuestionId();
        if((wordId + 1) < dbWords.size()){
            wordId++;
            quizQuestionFragment.setCurrentQuestionId(wordId);
            quizQuestionFragment.setCurrentWord(dbWords.get(wordId).getBaseWord());
        }else{
            showTestSummary();
        }
    }

    private void showTestSummary() {
        QuizSummary quizSummary = new QuizSummary();
        Bundle bundle = new Bundle();
        bundle.putStringArray(Constants.SUMMARY_LIST_OF_WORDS, getListOfWords(dbWords));
        bundle.putBooleanArray(Constants.SUMMARY_LIST_OF_ANSWERS, getListOfAnswers(dbWords));
        int correctAnswers = getNumberOfCorrectAnswers(dbWords);
        bundle.putInt(Constants.QUIZ_CORRECT_ANSWERS, correctAnswers);
        bundle.putInt(Constants.QUIZ_NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(Constants.QUIZ_RATIO, (100 * correctAnswers / dbWords.size()));
        bundle.putBooleanArray(Constants.SUMMARY_LIST_OF_ANSWERS, getListOfAnswers(dbWords));
        quizSummary.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, quizSummary)
                .commit();
    }

    private int getNumberOfCorrectAnswers(List<WordQuiz> dbWords) {
        int count = 0;
        for(WordQuiz wordQuiz : dbWords){
            count += wordQuiz.isKnown() ? 1 : 0;
        }
        return count;
    }

    private String[] getListOfWords(List<WordQuiz> dbWords) {
        String[] array = new String[dbWords.size()];
        for(int i=0 ; i<dbWords.size(); i++){
            array[i] = dbWords.get(i).getBaseWord();
        }
        return array;
    }

    private boolean[] getListOfAnswers(List<WordQuiz> dbWords) {
        boolean[] array = new boolean[dbWords.size()];
        for(int i=0 ; i<dbWords.size(); i++){
            array[i] = dbWords.get(i).isKnown();
        }
        return array;
    }

    public void answerUnknown(View view) {
        showNextWord();
    }
}
