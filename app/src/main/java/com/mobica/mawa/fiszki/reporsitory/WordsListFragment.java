package com.mobica.mawa.fiszki.reporsitory;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.Word;
import com.mobica.mawa.fiszki.dao.WordHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class WordsListFragment extends Fragment {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WordsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WordsListFragment newInstance(String param1, String param2) {
        WordsListFragment fragment = new WordsListFragment();
        return fragment;
    }
    public WordsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_words_list, container, false);


        final ListView listview = (ListView) rootView.findViewById(R.id.dictionariesList);

        adapter = new StableArrayAdapter(rootView.getContext(), ids, values);
        listview.setAdapter(adapter);


        return rootView;
}

    StableArrayAdapter adapter = null;

    private WordHelper wordHelper = null;
    private List<Word> loadDictionaryFromDb(String dictionary) {
        wordHelper = WordHelper.getInstance(getActivity());
        return wordHelper.loadDictionary(dictionary);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        List<Word> dict = loadDictionaryFromDb(dictionary);
        populateTable(dict);

    }

    private String dictionary = null;
    public void loadDictionary(String dictionary) {
        this.dictionary = dictionary;
    }

    private void populateTable(List<Word> dict) {

        values = new ArrayList<String>();
        ids = new ArrayList<Long>();

        for(Word word : dict){
            ids.add(word.getId());
            values.add(word.getBaseWord());
        }
    }
    private List<String> values = null;
    private List<Long> ids = null;

    private class StableArrayAdapter extends ArrayAdapter<String> {

        private final Context context;
        private List<Long> ids;
        private List<String> values;


        public StableArrayAdapter(Context context, List<Long> ids, List<String> values) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.ids = ids;
            this.values = values;
        }

        public void setValues(List<String> values){
            this.values = values;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView tv = (TextView)rowView.findViewById(R.id.rowlayoutTextView);
            tv.setText(values.get(position));
            ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.rowlayoutImageButton);
            imageButton.setOnClickListener(new ClickListener(position, rowView));
            return rowView;
        }
        private class ClickListener implements View.OnClickListener{

            private int position;
            private View rowView;
            public ClickListener(int position, View rowView){
                this.position = position;
                this.rowView = rowView;
            }
            @Override
            public void onClick(View view) {
                WordHelper.getInstance(getContext()).remove(ids.get(position));
                values.remove(position);
                ids.remove(position);
                notifyDataSetChanged();
            }
        }
    }
}
