package com.mobica.mawa.fiszki.dao.word;

import com.mobica.mawa.fiszki.dao.CRUD;

import java.util.List;

/**
 * Created by mawa on 2014-10-18.
 */
public interface WordDAO extends CRUD<Word>{

    public List<Word> query(long dictionaryId, int limit);
    public List<Word> queryRandom(long dictionaryId, int limit);

}
