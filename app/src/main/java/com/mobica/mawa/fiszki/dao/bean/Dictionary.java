package com.mobica.mawa.fiszki.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mawa on 2014-10-18.
 */
@DatabaseTable(tableName = "dictionary")
public class Dictionary {

    public static final String NAME = "name";
    public static final String BASE_LANG = "base_lang";
    public static final String REF_LANG = "ref_lang";
    private static final String ID = "id";
    private static final String DESC = "desc";
    private static final String UUID = "uuid";
    private static final String ADDED = "added";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;
    @DatabaseField(canBeNull = false, columnName = NAME, unique = true)
    private String name;
    @DatabaseField(columnName = DESC, canBeNull = false)
    private String description;
    @DatabaseField(columnName = UUID, canBeNull = false, unique = true)
    private String uuid;
    @DatabaseField(canBeNull = false, foreign = true, columnName = BASE_LANG, columnDefinition = "integer references language(id) on delete cascade")
    private Language baseLanguage;
    @DatabaseField(canBeNull = false, foreign = true, columnName = REF_LANG, columnDefinition = "integer references language(id) on delete cascade")
    private Language refLanguage;
    @DatabaseField(columnName = ADDED, canBeNull = false)
    private long added;

    public Dictionary() {
        setAdded(System.currentTimeMillis());
    }

    public Dictionary(int id, String name, String uuid, String description, Language baseLanguage, Language refLanguage) {
        this();
        setId(id);
        setName(name);
        setUuid(uuid);
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public long getAdded() {
        return added;
    }

    void setAdded(long added) {
        this.added = added;
    }

    @Override
    public String toString() {
        return "Dictionary{" +
                "id='" + id + '\'' +
                "description='" + description + '\'' +
                "name='" + name + '\'' +
                "uuid='" + uuid + '\'' +
                "baseLanguage='" + baseLanguage + '\'' +
                "refLanguage='" + refLanguage + '\'' +
                "added='" + added + '\'' +
                '}';
    }
}
