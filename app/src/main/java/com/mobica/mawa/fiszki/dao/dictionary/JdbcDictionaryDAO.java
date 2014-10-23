package com.mobica.mawa.fiszki.dao.dictionary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.helper.ResourcesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mawa on 2014-10-02.
 */
public class JdbcDictionaryDAO extends SQLiteOpenHelper implements DictionaryDAO{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Dictionary2.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DictionaryDef.TABLE_NAME + " (" +
                    DictionaryDef._ID + INTEGER_TYPE + " PRIMARY KEY," +
                    DictionaryDef.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    DictionaryDef.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    DictionaryDef.COLUMN_BASE_lANGUAGE + INTEGER_TYPE + COMMA_SEP +
                    DictionaryDef.COLUMN_REF_lANGUAGE + INTEGER_TYPE +
            " )";

    private static final String[] selectQuery = {
            DictionaryDef._ID,
            DictionaryDef.COLUMN_NAME,
            DictionaryDef.COLUMN_DESCRIPTION,
            DictionaryDef.COLUMN_BASE_lANGUAGE,
            DictionaryDef.COLUMN_REF_lANGUAGE
    };

    private Context context;
    private Context getContext(){
        return context;
    }
    private JdbcDictionaryDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static DictionaryDAO getInstance(Context context){
        return new JdbcDictionaryDAO(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        String[] dictionaries = getContext().getResources().getStringArray(R.array.dictionaries);
        for(String dictionary : dictionaries){
            insert(ResourcesHelper.getDictionary(dictionary), db);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    private long insert(Dictionary entry, SQLiteDatabase db) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DictionaryDef.COLUMN_NAME, entry.getName());
        values.put(DictionaryDef.COLUMN_DESCRIPTION, entry.getDescription());
        values.put(DictionaryDef.COLUMN_BASE_lANGUAGE, entry.getBaseLanguage());
        values.put(DictionaryDef.COLUMN_REF_lANGUAGE, entry.getRefLanguage());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(
                DictionaryDef.TABLE_NAME,
                null,
                values);
    }

    @Override
    public long insert(Dictionary entry) {
        return insert(entry, getWritableDatabase());
    }

    @Override
    public Dictionary delete(long id) {
        Dictionary dictionary = get(id);
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DictionaryDef.TABLE_NAME, DictionaryDef._ID + " = ?", new String[]{"" + id});
        return dictionary;
    }

    @Override
    public Dictionary get(long entryId) {
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        String whereCondition = DictionaryDef._ID + "=?";
        Cursor c = db.
                query(
                DictionaryDef.TABLE_NAME,
                selectQuery,
                whereCondition,
                new String[]{String.valueOf(entryId)},
                null,
                null,
                null
        );
        c.moveToFirst();
        return getDictionaryFromCursor(c);
    }

    private Cursor rawQuery(int baseDictionary, int refDictionary) {
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        String selection = DictionaryDef.COLUMN_BASE_lANGUAGE + "=? AND " + DictionaryDef.COLUMN_REF_lANGUAGE + "=?";
        return db.
                query(
                        DictionaryDef.TABLE_NAME,
                        selectQuery,
                        selection,
                        new String[]{String.valueOf(baseDictionary), String.valueOf(refDictionary)},
                        null,
                        null,
                        null
                );
    }

    @Override
    public List<Dictionary> query(int baseLanguage, int refLanguage, int limit) {
        Cursor c = rawQuery(baseLanguage, refLanguage);
        c.moveToFirst();
        List<Dictionary> dbDictionaries = new ArrayList<Dictionary>();
        do{
            dbDictionaries.add(getDictionaryFromCursor(c));
        }while(c.moveToNext() && (--limit != 0));
        return dbDictionaries;
    }


    private Dictionary getDictionaryFromCursor(Cursor c) {
        Dictionary dbDictionary = new Dictionary();
        dbDictionary.setId(c.getInt(c.getColumnIndex(DictionaryDef._ID)));
        dbDictionary.setName(c.getString(c.getColumnIndex(DictionaryDef.COLUMN_NAME)));
        dbDictionary.setDescription(c.getString(c.getColumnIndex(DictionaryDef.COLUMN_DESCRIPTION)));
        dbDictionary.setBaseLanguage(c.getInt(c.getColumnIndex(DictionaryDef.COLUMN_BASE_lANGUAGE)));
        dbDictionary.setRefLanguage(c.getInt(c.getColumnIndex(DictionaryDef.COLUMN_REF_lANGUAGE)));
        return dbDictionary;
    }
}
