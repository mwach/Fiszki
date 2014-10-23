package com.mobica.mawa.fiszki.dto;

import com.mobica.mawa.fiszki.dao.word.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawa on 2014-10-12.
 */
public class WordQuizHelper {

    public static List<WordQuiz> getList(List<Word> words){

        List<WordQuiz> retList = null;
        if(words != null) {
            retList = new ArrayList<WordQuiz>();
            for(Word word : words){
                retList.add(new WordQuiz(word));
            }
        }
        return  retList;
    }
}
