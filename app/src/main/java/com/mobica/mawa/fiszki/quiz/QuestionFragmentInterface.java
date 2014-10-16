package com.mobica.mawa.fiszki.quiz;

/**
 * Created by mawa on 2014-10-06.
 */
public interface QuestionFragmentInterface {

    void setCurrentQuestionId(int currentQuestionId);
    int getCurrentQuestionId();
    void setCurrentWord(String word);
    void setCurrentWordResponse(String wordResponse);
    boolean isAnswerKnown();
}
