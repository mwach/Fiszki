package com.mobica.mawa.fiszki.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobica.mawa.fiszki.dto.WordQuizHelper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mawa on 2014-10-02.
 */
public class WordHelper extends SQLiteOpenHelper implements CRUD<Word>{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WordReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WordDef.TABLE_NAME + " (" +
                    WordDef._ID + " INTEGER PRIMARY KEY," +
                    WordDef.COLUMN_BASE_WORD + TEXT_TYPE + COMMA_SEP +
                    WordDef.COLUMN_BASE_lANGUAGE + TEXT_TYPE + COMMA_SEP +
                    WordDef.COLUMN_REF_WORD + TEXT_TYPE + COMMA_SEP +
                    WordDef.COLUMN_REF_lANGUAGE + TEXT_TYPE +
            " )";

    private WordHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static WordHelper getInstance(Context context){
        return new WordHelper(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    @Override
    public long insert(Word entry) {
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WordDef.COLUMN_BASE_WORD, entry.getBaseWord());
        values.put(WordDef.COLUMN_BASE_lANGUAGE, entry.getBaseLanguage());
        values.put(WordDef.COLUMN_REF_WORD, entry.getRefWord());
        values.put(WordDef.COLUMN_REF_lANGUAGE, entry.getRefLanguage());

// Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                WordDef.TABLE_NAME,
                null,
                values);
        return newRowId;
    }

    @Override
    public Word get(long entryId) {
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                WordDef._ID,
                WordDef.COLUMN_BASE_WORD,
                WordDef.COLUMN_BASE_lANGUAGE,
                WordDef.COLUMN_REF_WORD,
                WordDef.COLUMN_REF_lANGUAGE,
        };
        Cursor c = db.
                query(
                WordDef.TABLE_NAME,
                projection,
                null,
                null,
                        null,
                null,
                null
        );
        Word dbWord = null;
        if(c.moveToFirst()){
            dbWord = new Word();
            dbWord.setId(c.getInt(c.getColumnIndex(WordDef._ID)));
            dbWord.setBaseWord(c.getString(c.getColumnIndex(WordDef.COLUMN_BASE_WORD)));
            dbWord.setBaseLanguage(c.getString(c.getColumnIndex(WordDef.COLUMN_BASE_lANGUAGE)));
            dbWord.setRefWord(c.getString(c.getColumnIndex(WordDef.COLUMN_REF_WORD)));
            dbWord.setRefLanguage(c.getString(c.getColumnIndex(WordDef.COLUMN_REF_lANGUAGE)));
        }

        return dbWord;
    }

    private Cursor query(String language){
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                WordDef._ID,
                WordDef.COLUMN_BASE_WORD,
                WordDef.COLUMN_BASE_lANGUAGE,
                WordDef.COLUMN_REF_WORD,
                WordDef.COLUMN_REF_lANGUAGE,
        };
//        public Cursor query (String table, String[] columns, String selection,
// String[] selectionArgs, String groupBy, String having, String orderBy)
        String selection = WordDef.COLUMN_BASE_lANGUAGE + "=?";
        return db.
                query(
                        WordDef.TABLE_NAME,
                        projection,
                        selection,
                        new String[]{language},
                        null,
                        null,
                        null
                );
    }
    public List<Word> getListOfQuestions(int noOfQuestions, String language) {
        Cursor c = query(language);
        List<Word> dbWords = new ArrayList<Word>();
        if(c.getCount() > 0) {
            List<Integer> ids = generateListOfIds(c.getCount(), noOfQuestions);
            for (int wordId : ids) {
                c.moveToPosition(wordId);
                dbWords.add(getWordFromCursor(c));
            }
        }
        return dbWords;
    }

    private Word getWordFromCursor(Cursor c) {
        Word dbWord = new Word();
        dbWord.setId(c.getInt(c.getColumnIndex(WordDef._ID)));
        dbWord.setBaseWord(c.getString(c.getColumnIndex(WordDef.COLUMN_BASE_WORD)));
        dbWord.setBaseLanguage(c.getString(c.getColumnIndex(WordDef.COLUMN_BASE_lANGUAGE)));
        dbWord.setRefWord(c.getString(c.getColumnIndex(WordDef.COLUMN_REF_WORD)));
        dbWord.setRefLanguage(c.getString(c.getColumnIndex(WordDef.COLUMN_REF_lANGUAGE)));
        return dbWord;
    }

    private List<Integer> generateListOfIds(int maxValue, int length) {
        List<Integer> array = new ArrayList<Integer>();
        Random random = new Random();
        while((length--) > 0){
            array.add(random.nextInt(maxValue));
        }
        return  array;
    }


    public List<Word> loadDictionary(String dictionary) {
        Cursor c = query(dictionary);

        c.moveToFirst();

        List<Word> words = new ArrayList<Word>();
        while(c.moveToNext()){
            words.add(getWordFromCursor(c));
        }
        return words;
    }

    public Word remove(long i) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(WordDef.TABLE_NAME, WordDef._ID + " = ?", new String[]{"" + i});

        return null;
    }
}
