package com.mobica.mawa.fiszki.reporsitory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.word.JdbcWordDAO;
import com.mobica.mawa.fiszki.dao.word.Word;
import com.mobica.mawa.fiszki.dao.word.WordDAO;

import java.util.ArrayList;
import java.util.List;

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
    public static WordsListFragment newInstance(int dictionaryId) {
        WordsListFragment wlf = new WordsListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DICTIONARY_ID, dictionaryId);
        wlf.setArguments(bundle);
        return wlf;
    }

    private Repository repository;
    public void setRepository(Repository repository){
        this.repository = repository;
    }
    public Repository getRepository(){
        return repository;
    }
    private static final String DICTIONARY_ID = "DICTIONARY_ID";

    public WordsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRepository((Repository)activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_words_list, container, false);

        final ListView listview = (ListView) rootView.findViewById(R.id.wordsList);

        final int dictionary = getArguments().getInt(DICTIONARY_ID);
        List<Word> dict = loadDictionaryFromDb(dictionary);
        populateTable(dict);

        adapter = new StableArrayAdapter(rootView.getContext(), ids, values);
        listview.setAdapter(adapter);

        ImageButton button = (ImageButton)rootView.findViewById(R.id.add_word);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().addWord(dictionary);
            }
        });
        return rootView;
}

    StableArrayAdapter adapter = null;

    private List<Word> loadDictionaryFromDb(int dictionary) {
        WordDAO wordDao = JdbcWordDAO.getInstance(getActivity());
        return wordDao.query(dictionary, Constants.MAX_NO_OF_QUESTIONS);
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

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView tv = (TextView)rowView.findViewById(R.id.rowlayoutTextView);
            tv.setText(values.get(position));
            ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.rowlayoutImageButton);
            imageButton.setOnClickListener(new ClickListener(position));
            return rowView;
        }
        private class ClickListener implements View.OnClickListener{

            private int position;
            public ClickListener(int position){
                this.position = position;
            }
            @Override
            public void onClick(View view) {
                JdbcWordDAO.getInstance(getContext()).delete(ids.get(position));
                values.remove(position);
                ids.remove(position);
                notifyDataSetChanged();
            }
        }
    }
}
