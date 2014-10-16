package com.mobica.mawa.fiszki.dao;

/**
 * Created by mawa on 2014-10-02.
 */
public interface CRUD<T> {

    public long insert(T entry);

    public T get(long entryId);
}
