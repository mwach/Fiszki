package com.mobica.mawa.fiszki.quiz;

/**
 * Created by mawa on 2014-10-06.
 */
public interface QuestionFragmentInterface {

    public static final String QUIZ_NO_OF_QUESTIONS = "no_of_questions";
    public static final String QUIZ_CORRECT_ANSWERS = "correct_answers";
    public static final String QUIZ_RATIO  = "quiz_ratio";
    java.lang.String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";
    java.lang.String CURRENT_WORD = "CURRENT_WORD";

    void setCurrentQuestionId(int currentQuestionId);
    int getCurrentQuestionId();
    void setCurrentWord(String word);
    void setCurrentWordResponse(String wordResponse);

    void setNumberOfQuestions(int size);
}
