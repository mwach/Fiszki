package com.mobica.mawa.fiszki.common;

/**
 * Created by mawa on 26/12/14.
 */
public class AdapterItem {

    private int id;
    private String name;

    public AdapterItem(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
