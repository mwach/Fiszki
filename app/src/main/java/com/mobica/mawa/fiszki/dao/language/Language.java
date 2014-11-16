package com.mobica.mawa.fiszki.dao.language;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mawa on 2014-10-18.
 */
@DatabaseTable(tableName = "language")
public class Language {

    static final String ID = "id";
    static final String NAME = "name";
    static final String DESC = "desc";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false, columnName = NAME)
    private String name;
    @DatabaseField(columnName = DESC)
    private String description;

    public Language(){
    }

    public Language(int id, String name, String description){
        setId(id);
        setName(name);
        setDescription(description);
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

    @Override
    public String toString() {
        return "Language{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                "name='" + name + '\'' +
                '}';
    }

}
