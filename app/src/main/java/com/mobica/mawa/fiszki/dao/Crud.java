package com.mobica.mawa.fiszki.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by mawa on 2014-11-23.
 */
public interface Crud<T> {

    /**
     * Persists an object in the database
     *
     * @param object an object to be persisted
     * @return persisted object
     * @throws SQLException create failed
     */
    public T create(T object) throws SQLException;

    /**
     * Removed an object identified by its ID from database
     *
     * @param objectId ID of the object to be removed
     * @throws SQLException remove failed
     */
    public void remove(int objectId) throws SQLException;

    /**
     * Updates given object in the database
     *
     * @param object object to be updated
     * @throws SQLException update failed
     */
    public void update(T object) throws SQLException;

    /**
     * Returns a persisted object identified by its ID
     *
     * @param objectId ID of the object to be returned
     * @return object identified by its ID
     * @throws SQLException get failed
     */
    public T get(int objectId) throws SQLException;

    /**
     * Returns a list of persisted objects from database
     *
     * @return list of objects
     * @throws SQLException enumerate failed
     */
    public List<T> enumerate() throws SQLException;
}
