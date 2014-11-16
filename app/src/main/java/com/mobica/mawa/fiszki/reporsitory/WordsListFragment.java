package com.mobica.mawa.fiszki.reporsitory;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AdapterClickListener;
import com.mobica.mawa.fiszki.common.DefaultArrayAdapter;
import com.mobica.mawa.fiszki.dao.word.Word;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class WordsListFragment extends Fragment implements AdapterClickListener {
    private static final String DICTIONARY_ID = "DICTIONARY_ID";
    private Repository repository;

    public WordsListFragment() {
        // Required empty public constructor
    }

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

    private Repository getRepository() {
        return repository;
    }

    private void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRepository((Repository) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_words_list, container, false);

        getActivity().getActionBar().setTitle(R.string.available_words);

        final ListView listview = (ListView) rootView.findViewById(R.id.wordsList);

        final int dictionary = getArguments().getInt(DICTIONARY_ID);
        List<Word> words = getRepository().loadDictWords(dictionary);

        List<Integer> ids = new ArrayList<Integer>();
        List<String> values = new ArrayList<String>();

        for (Word word : words) {
            ids.add(word.getId());
            values.add(word.getBaseWord());
        }

        final DefaultArrayAdapter adapter = new DefaultArrayAdapter(repository, ids, values, this);
        listview.setAdapter(adapter);

        ImageButton button = (ImageButton) rootView.findViewById(R.id.add_word);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().showAddWord(dictionary);
            }
        });
        return rootView;
    }

    @Override
    public void textClicked(int position) {

    }

    @Override
    public void buttonClicked(int position) {
        repository.deleteWord(position);
    }

}
