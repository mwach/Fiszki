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

import java.util.List;

/**
 */
public class DefaultArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final List<Integer> ids;
    private final List<String> values;
    private final AdapterClickListener adapterClickListener;

    public DefaultArrayAdapter(Context context, List<Integer> ids, List<String> values,
                               AdapterClickListener adapterClickListener) {
        super(context, R.layout.dictionary_row_layout, values);
        this.context = context;
        this.ids = ids;
        this.values = values;
        this.adapterClickListener = adapterClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dictionary_row_layout, parent, false);
            setRowBackgroundColor(convertView, position);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.rowlayoutTextView);
            viewHolder.textView.setText(values.get(position));
            viewHolder.textView.setOnClickListener(new OpenClickListener(position));
            viewHolder.imageButton = (ImageButton) convertView.findViewById(R.id.rowlayoutImageButton);
            viewHolder.imageButton.setOnClickListener(new DeleteClickListener(position));
            convertView.setTag(viewHolder);
        }

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setRowBackgroundColor(View rowView, int position) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            rowView.setBackgroundDrawable(context.getResources().getDrawable(((position % 2) == 1) ? R.color.white : R.color.lightgray));
        } else {
            rowView.setBackground(context.getResources().getDrawable(((position % 2) == 1) ? R.color.white : R.color.lightgray));
        }
    }

    static class ViewHolder {
        TextView textView;
        ImageButton imageButton;
    }

    private class OpenClickListener implements View.OnClickListener {

        private final int position;

        public OpenClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            int itemId = ids.get(position);
            adapterClickListener.textClicked(itemId);
        }
    }

    private class DeleteClickListener implements View.OnClickListener {

        private final int position;

        public DeleteClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View view) {
            adapterClickListener.buttonClicked(ids.get(position));
            values.remove(position);
            ids.remove(position);
            notifyDataSetChanged();
        }
    }
}

