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

    private Context context;
    private List<Dictionary> ids;
    private DownloadAdapterClickListener adapterClickListener;

    public DownloadArrayAdapter(Context context, List<Dictionary> ids,
                                DownloadAdapterClickListener adapterClickListener) {
        super(context, R.layout.downloadrowlayout, ids);
        this.context = context;
        this.ids = ids;
        this.adapterClickListener = adapterClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.downloadrowlayout, parent, false);

        setRowBackgroundColor(rowView, position);

        TextView tv = (TextView) rowView.findViewById(R.id.rowlayoutTextView);
        tv.setText(ids.get(position).getName());
        ImageButton downloadImageButton = (ImageButton) rowView.findViewById(R.id.rowlayoutDownloadButton);
        downloadImageButton.setOnClickListener(new DownloadClickListener(ids.get(position)));
        ImageButton infoImageButton = (ImageButton) rowView.findViewById(R.id.rowlayoutInfoButton);
        infoImageButton.setOnClickListener(new InfoClickListener(ids.get(position)));
        return rowView;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setRowBackgroundColor(View rowView, int position) {
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            rowView.setBackgroundDrawable(context.getResources().getDrawable(((position % 2) == 1) ? R.color.lightblue : R.color.darklightblue));
        } else {
            rowView.setBackground(context.getResources().getDrawable(((position % 2) == 1) ? R.color.lightblue : R.color.darklightblue));
        }
    }

    private class DownloadClickListener implements View.OnClickListener {

        private Dictionary dictionary;

        public DownloadClickListener(Dictionary dictionary) {
            this.dictionary = dictionary;
        }

        @Override
        public void onClick(View view) {
            adapterClickListener.downloadDictionary(dictionary);
        }
    }

    private class InfoClickListener implements View.OnClickListener {

        private Dictionary dictionary;

        public InfoClickListener(Dictionary dictionary) {
            this.dictionary = dictionary;
        }

        @Override
        public void onClick(View view) {
            adapterClickListener.displayDictionaryDetails(dictionary);
        }
    }

}

