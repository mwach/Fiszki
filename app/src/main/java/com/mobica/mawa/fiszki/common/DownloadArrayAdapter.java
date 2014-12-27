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
import com.mobica.mawa.fiszki.rest.dto.Dictionary;

import java.util.List;

/**
 */
public class DownloadArrayAdapter extends ArrayAdapter<Dictionary> {

    private final Context context;
    private final List<Dictionary> dictionaries;
    private final DownloadAdapterClickListener adapterClickListener;

    public DownloadArrayAdapter(Context context, List<Dictionary> dictionaries,
                                DownloadAdapterClickListener adapterClickListener) {
        super(context, R.layout.download_row_layout, dictionaries);
        this.context = context;
        this.dictionaries = dictionaries;
        this.adapterClickListener = adapterClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.download_row_layout, parent, false);
            setRowBackgroundColor(convertView, position);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.rowlayoutTextView);
            viewHolder.textView.setText(dictionaries.get(position).getName());
            viewHolder.downloadImageButton = (ImageButton) convertView.findViewById(R.id.rowlayoutDownloadButton);
            viewHolder.downloadImageButton.setOnClickListener(new DownloadClickListener(dictionaries.get(position)));
            viewHolder.infoImageButton = (ImageButton) convertView.findViewById(R.id.rowlayoutInfoButton);
            viewHolder.infoImageButton.setOnClickListener(new InfoClickListener(dictionaries.get(position)));
        }

        return convertView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setRowBackgroundColor(View rowView, int position) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            rowView.setBackgroundDrawable(context.getResources().getDrawable(((position % 2) == 1) ? R.color.white : R.color.lightgray));
        } else {
            rowView.setBackground(context.getResources().getDrawable(((position % 2) == 1) ? R.color.white : R.color.lightgray));
        }
    }

    static class ViewHolder {
        TextView textView;
        ImageButton downloadImageButton;
        ImageButton infoImageButton;
    }

    private class DownloadClickListener implements View.OnClickListener {

        private final Dictionary dictionary;

        public DownloadClickListener(Dictionary dictionary) {
            this.dictionary = dictionary;
        }

        @Override
        public void onClick(View view) {
            adapterClickListener.downloadDictionary(dictionary);
        }
    }

    private class InfoClickListener implements View.OnClickListener {

        private final Dictionary dictionary;

        public InfoClickListener(Dictionary dictionary) {
            this.dictionary = dictionary;
        }

        @Override
        public void onClick(View view) {
            adapterClickListener.displayDictionaryDetails(dictionary);
        }
    }

}

