package com.mobica.mawa.fiszki.quiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.util.List;

/**
 * Created by mawa on 2014-11-24.
 */
public class DictionaryAdapter extends ArrayAdapter<Dictionary> {

    private ViewHolder viewHolder = null;

    public DictionaryAdapter(Context context, int itemId, List<Dictionary> items) {
        super(context, itemId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Dictionary item = getItem(position);
        if (item != null) {
            // My layout has only one TextView
            // do whatever you want with your string and long
            viewHolder.itemView.setText(item.getDescription());
        }

        return convertView;
    }

    private static class ViewHolder {
        private TextView itemView;
    }
}
