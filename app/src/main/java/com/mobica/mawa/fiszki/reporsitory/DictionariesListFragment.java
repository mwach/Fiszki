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

import roboguice.inject.InjectView;

/**
 *
 */
public class DictionariesListFragment extends Fragment implements AdapterClickListener {

    private Repository repository;

    @InjectView(R.id.dictionariesList)
    private ListView dictionariesListView;
    @InjectView(R.id.add_dictionary)
    private ImageButton addDictButton;

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

        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.available_dictionaries);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int baseLanguage = getRepository().getBaseLanguage();
        int refLanguage = getRepository().getRefLanguage();

        List<Dictionary> dictionaries = getRepository().getListOfDictionaries(baseLanguage, refLanguage);

        List<Integer> ids = new ArrayList<Integer>();
        List<String> values = new ArrayList<String>();
        for (Dictionary dict : dictionaries) {
            values.add(dict.getName());
            ids.add(dict.getId());
        }
        DefaultArrayAdapter adapter = new DefaultArrayAdapter(getRepository(), ids, values, this);
        dictionariesListView.setAdapter(adapter);

        addDictButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().showAddDictionary();
            }
        });

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
