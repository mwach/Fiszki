package com.mobica.mawa.fiszki.quiz;

import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;

import java.util.List;

/**
 * Created by mawa on 2014-11-17.
 */
public interface QuizInterface {
    List<Dictionary> getListOfDictionaries();

    int getNumberOfQuestions();

    void startQuiz(int noOfQuestions, int id);
}
