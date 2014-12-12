package com.mobica.mawa.fiszki.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-23.
 */
public interface Crud<T> {

    public T create(T object) throws SQLException;

    public void remove(int objectId) throws SQLException;

    public void update(T object) throws SQLException;

    public T get(int objectId) throws SQLException;

    public List<T> enumerate() throws SQLException;
}
