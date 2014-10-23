package com.mobica.mawa.fiszki.dao.word;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.helper.ResourcesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by mawa on 2014-10-02.
 */
public class JdbcWordDAO extends SQLiteOpenHelper implements WordDAO{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Word.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + WordDef.TABLE_NAME + " (" +
                    WordDef._ID + INTEGER_TYPE + " PRIMARY KEY" + COMMA_SEP +
                    WordDef.COLUMN_DICTIONARY_ID + INTEGER_TYPE + COMMA_SEP +
                    WordDef.COLUMN_BASE_WORD + TEXT_TYPE + COMMA_SEP +
                    WordDef.COLUMN_REF_WORD + TEXT_TYPE +
            " )";

    private static final String[] selectQuery = {
            WordDef._ID,
            WordDef.COLUMN_DICTIONARY_ID,
            WordDef.COLUMN_BASE_WORD,
            WordDef.COLUMN_REF_WORD,
    };

    private Context context;
    private Context getContext(){
        return context;
    }

    private JdbcWordDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static WordDAO getInstance(Context context){
        return new JdbcWordDAO(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        String[] words = getContext().getResources().getStringArray(R.array.words);
        for(String word : words){
            insert(ResourcesHelper.getWord(word), db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    private long insert(Word entry, SQLiteDatabase db) {

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(WordDef.COLUMN_DICTIONARY_ID, entry.getDictionaryId());
        values.put(WordDef.COLUMN_BASE_WORD, entry.getBaseWord());
        values.put(WordDef.COLUMN_REF_WORD, entry.getRefWord());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(
                WordDef.TABLE_NAME,
                null,
                values);
    }

    @Override
    public long insert(Word entry) {
        return insert(entry, getWritableDatabase());
    }

    @Override
    public Word delete(long id) {
        Word word = get(id);
        SQLiteDatabase db = getWritableDatabase();
        db.delete(WordDef.TABLE_NAME, WordDef._ID + " = ?", new String[]{"" + id});
        return word;
    }

    @Override
    public Word get(long entryId) {

        SQLiteDatabase db = getReadableDatabase();

        String selection = WordDef._ID + "=?";
        Cursor c = db.
                query(
                        WordDef.TABLE_NAME,
                        selectQuery,
                        selection,
                        new String[]{String.valueOf(entryId)},
                        null,
                        null,
                        null
                );
        c.moveToFirst();
        return getWordFromCursor(c);
    }

    @Override
    public void update(Word item) {

    }

    private Cursor rawQuery(long dictionaryId) {
        // Gets the data repository in read mode
        SQLiteDatabase db = getReadableDatabase();

        String selection = WordDef.COLUMN_DICTIONARY_ID + "=?";
        return db.
                query(
                        WordDef.TABLE_NAME,
                        selectQuery,
                        selection,
                        new String[]{String.valueOf(dictionaryId)},
                        null,
                        null,
                        null
                );
    }

    @Override
    public List<Word> query(long dictionaryId, int limit) {
        Cursor c = rawQuery(dictionaryId);
        List<Word> dbWords = new ArrayList<Word>();

        if(c.moveToFirst()) {
            do {
                dbWords.add(getWordFromCursor(c));
            } while (c.moveToNext() && (--limit != 0));
        }
        return dbWords;
    }

    @Override
    public List<Word> queryRandom(long dictionaryId, int limit) {
        Cursor c = rawQuery(dictionaryId);
        List<Word> dbWords = new ArrayList<Word>();
        List<Long> ids = generateListOfIds(c.getCount(), limit);
        for(Long id : ids){
            c.moveToPosition(id.intValue());
            dbWords.add(getWordFromCursor(c));
        }
        return dbWords;
    }


    private Word getWordFromCursor(Cursor c) {
        Word dbWord = new Word();
        dbWord.setId(c.getInt(c.getColumnIndex(WordDef._ID)));
        dbWord.setDictionaryId(c.getInt(c.getColumnIndex(WordDef.COLUMN_DICTIONARY_ID)));
        dbWord.setBaseWord(c.getString(c.getColumnIndex(WordDef.COLUMN_BASE_WORD)));
        dbWord.setRefWord(c.getString(c.getColumnIndex(WordDef.COLUMN_REF_WORD)));
        return dbWord;
    }

    private List<Long> generateListOfIds(int maxValue, int length) {
        List<Long> array = new ArrayList<Long>();
        Random random = new Random();
        while((length--) > 0){
            array.add((long)random.nextInt(maxValue));
        }
        return  array;
    }
}
