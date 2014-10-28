package com.mobica.mawa.fiszki.quiz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.dictionary.JdbcDictionaryDAO;
import com.mobica.mawa.fiszki.dao.word.JdbcWordDAO;
import com.mobica.mawa.fiszki.dao.word.Word;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.List;


public class QuizActivity extends Activity {

    int correctAnswers = 0;
    QuestionFragmentInterface quizQuestionFragment = new QuizQuestionFragment();
    List<Word> dbWords = null;

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
        switch (item.getItemId()){
            case R.id.action_home:
                showMainMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void startQuiz(View view) {

        EditText noOfQuestionsEditText = (EditText)findViewById(R.id.editTextNoOfQuestions);
        int noOfQuestions = Integer.parseInt(noOfQuestionsEditText.getText().toString());
        PreferencesHelper.setProperty(this, PreferencesHelper.QUIZ_NO_OF_QUESTIONS, noOfQuestions);

        Spinner dictionarySpinner = (Spinner)findViewById(R.id.spinnerDictionaries);
        Dictionary selectedDict = (Dictionary)dictionarySpinner.getSelectedItem();

        dbWords = JdbcWordDAO.getInstance(this).queryRandom(selectedDict.getId(), noOfQuestions);

        Bundle bundle = new Bundle();
        bundle.putInt(QuestionFragmentInterface.QUIZ_NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(QuestionFragmentInterface.CURRENT_QUESTION_ID, 0);
        bundle.putString(QuestionFragmentInterface.CURRENT_WORD, dbWords.get(0).getBaseWord());


        ((Fragment)quizQuestionFragment).setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, (Fragment)quizQuestionFragment)
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
        bundle.putInt(QuestionFragmentInterface.QUIZ_CORRECT_ANSWERS, correctAnswers);
        bundle.putInt(QuestionFragmentInterface.QUIZ_NO_OF_QUESTIONS, dbWords.size());
        bundle.putInt(QuestionFragmentInterface.QUIZ_RATIO, (100 * correctAnswers / dbWords.size()));
        quizSummary.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(R.id.container, quizSummary)
                .commit();
    }

    public void answerUnknown(View view) {
        showNextWord();
    }
}
