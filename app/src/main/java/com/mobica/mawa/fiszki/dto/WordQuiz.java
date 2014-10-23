package com.mobica.mawa.fiszki.dto;

import com.mobica.mawa.fiszki.dao.word.Word;

/**
 * Created by mawa on 2014-10-12.
 */
public class WordQuiz extends Word{

    private boolean known = false;

    public WordQuiz(Word word){
        super(word.getId(), word.getDictionaryId(), word.getBaseWord(), word.getRefWord());

    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }
}
