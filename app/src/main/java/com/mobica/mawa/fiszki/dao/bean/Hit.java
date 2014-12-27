package com.mobica.mawa.fiszki.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by mawa on 27/12/14.
 */
@DatabaseTable(tableName = "hits")
public class Hit {
    private static final String ID = "id";
    private static final String WORD = "word";
    private static final String DATE = "date";
    private static final String KNOWN = "known";

    @DatabaseField(generatedId = true, columnName = ID)
    private int id;

    @DatabaseField(canBeNull = false, columnName = WORD)
    private Word word;

    @DatabaseField(canBeNull = false, columnName = DATE)
    private long date;

    @DatabaseField(canBeNull = false, columnName = KNOWN)
    private boolean known;

    public Hit() {
        setDate(System.currentTimeMillis());
    }

    public Hit(Word word, boolean known) {
        this();
        setWord(word);
        setKnown(known);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id='" + id + '\'' +
                ", word='" + word + '\'' +
                ", known='" + known + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

}
