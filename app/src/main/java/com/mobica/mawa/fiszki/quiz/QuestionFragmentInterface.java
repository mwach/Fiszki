package com.mobica.mawa.fiszki.quiz;

/**
 * Created by mawa on 2014-10-06.
 */
public interface QuestionFragmentInterface {

    static final String NO_OF_QUESTIONS = "NO_OF_QUESTIONS";
    static final String CURRENT_QUESTION_ID = "CURRENT_QUESTION_ID";
    static final String CURRENT_WORD = "CURRENT_WORD";

    void setCurrentQuestionId(int currentQuestionId);

    void setCurrentWord(String word);

    void setCurrentWordResponse(String wordResponse);
}
