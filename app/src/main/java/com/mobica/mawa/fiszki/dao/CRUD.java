package com.mobica.mawa.fiszki.dao;

/**
 * Created by mawa on 2014-10-02.
 */
public interface CRUD<T> {

    public long insert(T item);

    public T delete(long id);

    public T get(long id);
}