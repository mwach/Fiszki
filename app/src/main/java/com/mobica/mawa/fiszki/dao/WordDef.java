package com.mobica.mawa.fiszki.dao;

import android.provider.BaseColumns;

/**
 * Created by mawa on 2014-10-02.
 */
public class WordDef implements BaseColumns {
        public static final String TABLE_NAME = "word";
        public static final String COLUMN_BASE_WORD = "base_word";
        public static final String COLUMN_BASE_lANGUAGE = "base_language";
        public static final String COLUMN_REF_WORD = "ref_word";
        public static final String COLUMN_REF_lANGUAGE = "ref_language";
}