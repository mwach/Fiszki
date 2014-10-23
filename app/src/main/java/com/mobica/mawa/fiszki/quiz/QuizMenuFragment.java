package com.mobica.mawa.fiszki.quiz;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.dictionary.JdbcDictionaryDAO;
import com.mobica.mawa.fiszki.dao.language.JdbcLanguageDAO;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class QuizMenuFragment extends Fragment {


    public QuizMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz_menu, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerDictionaries);
        populateSpinner(rootView.getContext(), spinner, getListOfDictionaries());

        loadFormDefaults(rootView);

        return rootView;
    }

    private void loadFormDefaults(View rootView) {

        SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        int noOfQuestions = preferences.getInt(Constants.QUIZ_NO_OF_QUESTIONS
                , 0);
        if(noOfQuestions == 0){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(Constants.QUIZ_NO_OF_QUESTIONS, Constants.DEFAULT_NO_OF_QUESTIONS);
            editor.apply();
            noOfQuestions = preferences.getInt(Constants.QUIZ_NO_OF_QUESTIONS, 0);
        }

        EditText et = (EditText)rootView.findViewById(R.id.editTextNoOfQuestions);
        et.setText(String.valueOf(noOfQuestions));
    }

    private void populateSpinner(Context rootItem, Spinner spinner, List<String> items) {
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(rootItem,
                        android.R.layout.simple_spinner_item, items);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private List<String> getListOfDictionaries() {
        String baseLanguageName = PreferencesHelper.getBaseLanguage(getActivity());
        String refLanguageName = PreferencesHelper.getRefLanguage(getActivity());

        int baseLanguage = JdbcLanguageDAO.getInstance(getActivity()).get(baseLanguageName).getId();
        int refLanguage = JdbcLanguageDAO.getInstance(getActivity()).get(refLanguageName).getId();
        List<Dictionary> dictionaries = JdbcDictionaryDAO.getInstance(getActivity()).query(baseLanguage, refLanguage, Constants.UNLIMITED);
        List<String> dicts = new ArrayList<String>();
        for (Dictionary dict : dictionaries){
            dicts.add(dict.getName());
        }
        return dicts;
    }

}
