package com.mobica.mawa.fiszki.dao;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.language.Language;
import com.mobica.mawa.fiszki.dao.word.Word;

import java.io.File;

/**
 * Created by mawa on 2014-11-11.
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final String ORMLITE_CONFIGURATION_FILE_NAME = "ormlite_config.txt";

    private static final Class<?>[] classes = new Class[]{
            Language.class, Dictionary.class, Word.class
    };

    public static void main(String[] args) throws Exception {

        File configFile = new File(new File("").getAbsolutePath().split("app" + File.separator + "build")[0] +
                File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "res" + File.separator +
                "raw" + File.separator +
                ORMLITE_CONFIGURATION_FILE_NAME);

        writeConfigFile(configFile, classes);
    }
}