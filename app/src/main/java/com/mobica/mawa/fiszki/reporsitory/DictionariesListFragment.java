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
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class DictionariesListFragment extends Fragment implements AdapterClickListener {

    private Repository repository;

    public DictionariesListFragment() {
    }

    public static DictionariesListFragment newInstance() {

        return new DictionariesListFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_dictionaries_list, container, false);

        getActivity().getActionBar().setTitle(R.string.available_dictionaries);

        int baseLanguage = repository.getBaseLanguage();
        int refLanguage = repository.getRefLanguage();

        ListView listview = (ListView) rootView.findViewById(R.id.dictionariesList);
        List<Dictionary> dictionaries = getRepository().getListOfDictionaries();

        List<Integer> ids = new ArrayList<Integer>();
        List<String> values = new ArrayList<String>();
        for (Dictionary dict : dictionaries) {
            values.add(dict.getName());
            ids.add(dict.getId());
        }
        DefaultArrayAdapter adapter = new DefaultArrayAdapter(repository, ids, values, this);
        listview.setAdapter(adapter);

        ImageButton addDictButton = (ImageButton) rootView.findViewById(R.id.add_dictionary);
        addDictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().showAddDictionary();
            }
        });
        return rootView;
    }

    @Override
    public void textClicked(int position) {
        repository.loadDictionary(position);
    }

    @Override
    public void buttonClicked(int position) {
        repository.deleteDictionary(position);
    }
}
