package com.mobica.mawa.fiszki.dao.dictionary;

/**
 * Created by mawa on 2014-10-18.
 */
public class Dictionary {
    private int id;
    private String name;
    private String description;
    private int baseLanguage;
    private int refLanguage;

    public Dictionary(){
    }

    public Dictionary(int id, String name, String description, int baseLanguage, int refLanguage){
        this();
        setId(id);
        setName(name);
        setDescription(description);
        setBaseLanguage(baseLanguage);
        setRefLanguage(refLanguage);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(int baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public int getRefLanguage() {
        return refLanguage;
    }

    public void setRefLanguage(int refLanguage) {
        this.refLanguage = refLanguage;
    }
}
