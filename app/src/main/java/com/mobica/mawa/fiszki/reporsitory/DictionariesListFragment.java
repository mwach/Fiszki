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
import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 *
 */
public class DictionariesListFragment extends RoboFragment implements AdapterClickListener {

    private Repository repository;
    private Context context;

    @InjectView(R.id.dictionariesList)
    private ListView dictionariesListView;

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
        this.context = activity;
        setRepository((Repository) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_dictionaries_list, container, false);

        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.dictionaries);
        }
        setHasOptionsMenu(true);
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.repository, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showDictionaries();
    }

    public void showDictionaries(){
        int baseLanguage = getRepository().getBaseLanguage();
        int refLanguage = getRepository().getRefLanguage();

        List<Dictionary> dictionaries = getRepository().getDictionaries(baseLanguage, refLanguage);

        List<Integer> ids = new ArrayList<Integer>();
        List<String> values = new ArrayList<String>();
        for (Dictionary dict : dictionaries) {
            values.add(dict.getName());
            ids.add(dict.getId());
        }
        DefaultArrayAdapter adapter = new DefaultArrayAdapter(context, ids, values, this);
        dictionariesListView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_download:
                repository.showDownloadDictionaries();
                return true;
            case R.id.action_add:
                repository.showAddDictionary();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void textClicked(int position) {
        repository.showWords(position);
    }

    @Override
    public void buttonClicked(int position) {
        repository.deleteDictionary(position);
    }

    public void highlightDictionary(int dictionaryId) {
        DefaultArrayAdapter daa = (DefaultArrayAdapter)dictionariesListView.getAdapter();
        for(int i=0; i <daa.getCount() ; i++){
            String item = daa.getItem(i);
        }
    }
}
