package com.mobica.mawa.fiszki.reporsitory;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AdapterClickListener;
import com.mobica.mawa.fiszki.common.DefaultArrayAdapter;
import com.mobica.mawa.fiszki.dao.bean.Word;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 */
public class WordsListFragment extends RoboFragment implements AdapterClickListener {
    private static final String DICTIONARY_ID = "DICTIONARY_ID";
    private Repository repository;
    private Context context;

    @InjectView(R.id.wordsList)
    private ListView wordsList;

    private int dictionary;

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
        this.context = activity;
        setRepository((Repository) activity);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.repository_word, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_words_list, container, false);

        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.available_words);
        }
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null && getArguments().containsKey(DICTIONARY_ID)){
            dictionary = getArguments().getInt(DICTIONARY_ID);
            loadDictionary(dictionary);
        }
    }

    public void loadDictionary(int dictionaryId) {
        List<Word> words = getRepository().getWords(dictionaryId);

        List<Integer> ids = new ArrayList<Integer>();
        List<String> values = new ArrayList<String>();

        for (Word word : words) {
            ids.add(word.getId());
            values.add(word.getBaseWord());
        }

        wordsList.setAdapter(new DefaultArrayAdapter(context, ids, values, this));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_word:
                getRepository().showAddWord(dictionary);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void textClicked(int position) {
    }

    @Override
    public void buttonClicked(int position) {
        repository.deleteWord(position);
    }

}
