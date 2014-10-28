package com.mobica.mawa.fiszki.quiz;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;

import java.util.List;

/**
 * Created by mawa on 2014-10-28.
 */
public class DictionariesArrayAdapter extends ArrayAdapter<Dictionary>{

    public DictionariesArrayAdapter(Context context, int resource, List<Dictionary> objects) {
        super(context, resource, objects);
    }
}
