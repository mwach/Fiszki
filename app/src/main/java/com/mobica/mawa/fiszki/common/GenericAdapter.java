package com.mobica.mawa.fiszki.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mawa on 2014-11-24.
 */
public class GenericAdapter extends ArrayAdapter<AdapterItem> {

    public GenericAdapter(Context context, int itemId, List<AdapterItem> items) {
        super(context, itemId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
        }
        ((TextView) convertView).setText(getItem(position).getName());

        return convertView;
    }
}
