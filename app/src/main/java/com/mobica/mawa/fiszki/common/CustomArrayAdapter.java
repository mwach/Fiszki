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
public class CustomArrayAdapter extends ArrayAdapter<String> {

    private final Context context;
    private List<Integer> ids;
    private List<String> values;
    private AdapterClickListener adapterClickListener;

    public CustomArrayAdapter(Context context, List<Integer> ids, List<String> values,
                              AdapterClickListener adapterClickListener) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.ids = ids;
        this.values = values;
        this.adapterClickListener = adapterClickListener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
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
        int sdk = Build.VERSION.SDK_INT;
        if (sdk < Build.VERSION_CODES.JELLY_BEAN) {
            //noinspection deprecation
            rowView.setBackgroundDrawable(context.getResources().getDrawable(((position % 2) == 1) ? R.color.white : R.color.lightgray));
        } else {
            rowView.setBackground(context.getResources().getDrawable(((position % 2) == 1) ? R.color.white : R.color.lightgray));
        }
    }

    private class OpenClickListener implements View.OnClickListener {

        private int position;

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

        private int position;

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

