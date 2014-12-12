package com.mobica.mawa.fiszki.dao;

import com.mobica.mawa.fiszki.dao.bean.Language;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-23.
 */
public interface ILanguageDao extends Crud<Language> {

    public Language getBaseLanguage() throws SQLException;

    public Language getRefLanguage() throws SQLException;

    public void createOrUpdate(List<Language> languages) throws SQLException;

}
