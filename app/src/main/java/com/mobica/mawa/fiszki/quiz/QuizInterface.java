package com.mobica.mawa.fiszki.quiz;

import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.util.List;

/**
 * Created by mawa on 2014-11-17.
 */
interface QuizInterface {

    static final int UNDEFINED_DICTIONARY = -1;

    List<Dictionary> getListOfDictionaries();

    int getNumberOfQuestions();

    void startQuiz(int noOfQuestions, int dictionaryId, int strategy);
}
