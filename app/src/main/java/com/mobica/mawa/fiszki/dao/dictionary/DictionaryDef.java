package com.mobica.mawa.fiszki.dao.dictionary;

import android.provider.BaseColumns;

/**
 * Created by mawa on 2014-10-02.
 */
public class DictionaryDef implements BaseColumns {
        public static final String TABLE_NAME = "dictionary";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_BASE_lANGUAGE = "base_language";
        public static final String COLUMN_REF_lANGUAGE = "ref_language";
}