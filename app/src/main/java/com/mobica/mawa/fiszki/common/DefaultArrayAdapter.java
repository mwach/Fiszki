package com.mobica.mawa.fiszki.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.JdbcDictionaryDAO;
import com.mobica.mawa.fiszki.reporsitory.RepositoryActivity;

import java.util.List;

/**
 * Created by mawa on 2014-10-20.
 */
public class DefaultArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private List<Integer> ids;
    private List<String> values;


    public DefaultArrayAdapter(Context context, List<Integer> ids, List<String> values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.ids = ids;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);

        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            rowView.setBackgroundDrawable(context.getResources().getDrawable(((position % 2) == 1) ? R.color.lightblue : R.color.darklightblue));
        } else {
            rowView.setBackground(context.getResources().getDrawable(((position % 2) == 1) ? R.color.lightblue : R.color.darklightblue));
        }

        TextView tv = (TextView) rowView.findViewById(R.id.rowlayoutTextView);
        tv.setText(values.get(position));
        tv.setOnClickListener(new OpenClickListener(position));
        ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.rowlayoutImageButton);
        imageButton.setOnClickListener(new DeleteClickListener(position));
        return rowView;
    }

    private class OpenClickListener implements View.OnClickListener {

        private int position;
        public OpenClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            int dictionaryId = ids.get(position);
            ((RepositoryActivity)context).loadDictionary(dictionaryId);
        }
    }

    private class DeleteClickListener implements View.OnClickListener {

        private int position;
        public DeleteClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            JdbcDictionaryDAO.getInstance(getContext()).delete(ids.get(position));
            values.remove(position);
            ids.remove(position);
            notifyDataSetChanged();
        }
    }
}

