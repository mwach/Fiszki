package com.mobica.mawa.fiszki.reporsitory;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.DefaultArrayAdapter;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.dictionary.JdbcDictionaryDAO;
import com.mobica.mawa.fiszki.dao.language.JdbcLanguageDAO;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DictionariesListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DictionariesListFragment extends Fragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static DictionariesListFragment newInstance() {
        return new DictionariesListFragment();
    }

    public DictionariesListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_dictionaries_list, container, false);

        if (savedInstanceState == null) {

            int baseLanguage = JdbcLanguageDAO.getInstance(getActivity())
            .get(PreferencesHelper.getBaseLanguage(getActivity())).getId();
            int refLanguage = JdbcLanguageDAO.getInstance(getActivity())
                    .get(PreferencesHelper.getRefLanguage(getActivity())).getId();

            ListView listview = (ListView) rootView.findViewById(R.id.dictionariesList);
            List<Dictionary> dictionaries = JdbcDictionaryDAO.getInstance(getActivity()).query(baseLanguage, refLanguage, Constants.UNLIMITED);

            List<Integer> ids = new ArrayList<Integer>();
            List<String> values = new ArrayList<String>();
            for (Dictionary dict : dictionaries) {
                values.add(dict.getName());
                ids.add(dict.getId());
            }
            final DefaultArrayAdapter adapter = new DefaultArrayAdapter(getActivity(), ids, values);
            listview.setAdapter(adapter);

            ImageButton addDictButton = (ImageButton) rootView.findViewById(R.id.add_dictionary);
            addDictButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((RepositoryActivity) rootView.getContext()).addDictionary();
                }
            });
        }
        return rootView;
    }
}
