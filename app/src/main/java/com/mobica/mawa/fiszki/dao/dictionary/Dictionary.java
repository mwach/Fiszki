package com.mobica.mawa.fiszki.dao.dictionary;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.mobica.mawa.fiszki.dao.language.Language;

/**
 * Created by mawa on 2014-10-18.
 */
@DatabaseTable(tableName = "dictionary")
public class Dictionary {

    static final String ID = "id";
    static final String NAME = "name";
    static final String DESC = "desc";
    static final String BASE_LANG = "base_lang";
    static final String REF_LANG = "ref_lang";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false, columnName = NAME)
    private String name;
    @DatabaseField(columnName = DESC)
    private String description;
    @DatabaseField(canBeNull = false, foreign = true, columnName = BASE_LANG)
    private Language baseLanguage;
    @DatabaseField(canBeNull = false, foreign = true, columnName = REF_LANG)
    private Language refLanguage;

    public Dictionary() {
    }

    public Dictionary(int id, String name, String description, Language baseLanguage, Language refLanguage) {
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

    public Language getBaseLanguage() {
        return baseLanguage;
    }

    public void setBaseLanguage(Language baseLanguage) {
        this.baseLanguage = baseLanguage;
    }

    public Language getRefLanguage() {
        return refLanguage;
    }

    public void setRefLanguage(Language refLanguage) {
        this.refLanguage = refLanguage;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                "name='" + name + '\'' +
                "baseLanguage='" + baseLanguage + '\'' +
                "refLanguage='" + refLanguage + '\'' +
                '}';
    }
}
