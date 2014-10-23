package com.mobica.mawa.fiszki.dao.dictionary;

import com.mobica.mawa.fiszki.dao.CRUD;

import java.util.List;

/**
 * Created by mawa on 2014-10-19.
 */
public interface DictionaryDAO extends CRUD<Dictionary>{

    public List<Dictionary> query(int baseLanguage, int refLanguage, int limit);
}
