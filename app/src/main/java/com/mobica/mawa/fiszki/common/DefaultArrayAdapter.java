package com.mobica.mawa.fiszki.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.reporsitory.Repository;

import java.util.List;

/**
 */
public class DefaultArrayAdapter extends ArrayAdapter<String> {

    private final Repository repository;
    private List<Integer> ids;
    private List<String> values;

    public DefaultArrayAdapter(Repository repository, List<Integer> ids, List<String> values) {
        super(repository.getContext(), R.layout.rowlayout, values);
        this.repository = repository;
        this.ids = ids;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) repository.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        setRowBackgroundColor(rowView, position);

        TextView tv = (TextView) rowView.findViewById(R.id.rowlayoutTextView);
        tv.setText(values.get(position));
        tv.setOnClickListener(new OpenClickListener(position));
        ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.rowlayoutImageButton);
        imageButton.setOnClickListener(new DeleteClickListener(position));
        return rowView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setRowBackgroundColor(View rowView, int position) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            rowView.setBackgroundDrawable(repository.getContext().getResources().getDrawable(((position % 2) == 1) ? R.color.lightblue : R.color.darklightblue));
        } else {
            rowView.setBackground(repository.getContext().getResources().getDrawable(((position % 2) == 1) ? R.color.lightblue : R.color.darklightblue));
        }
    }

    private class OpenClickListener implements View.OnClickListener {

        private int position;
        public OpenClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            int dictionaryId = ids.get(position);
            repository.loadDictionary(dictionaryId);
        }
    }

    private class DeleteClickListener implements View.OnClickListener {

        private int position;
        public DeleteClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            repository.deleteDictionary(ids.get(position));
            values.remove(position);
            ids.remove(position);
            notifyDataSetChanged();
        }
    }
}

