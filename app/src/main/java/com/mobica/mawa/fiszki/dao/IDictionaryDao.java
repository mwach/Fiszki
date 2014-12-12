package com.mobica.mawa.fiszki.dao;

import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-23.
 */
public interface IDictionaryDao extends Crud<Dictionary> {

    public List<Dictionary> enumerate(int baseLanguage, int refLanguage) throws SQLException;
}
