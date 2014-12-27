package com.mobica.mawa.fiszki.dao;

import com.mobica.mawa.fiszki.dao.bean.Word;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-23.
 */
public interface IWordDao extends Crud<Word> {

    public List<Word> enumerate(Long limit, Integer dictionaryId, Integer strategy) throws SQLException;

    public void create(List<Word> dbWords) throws SQLException;

    public void update(List<Word> dbWords) throws SQLException;
}
