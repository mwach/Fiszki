package com.mobica.mawa.fiszki.quiz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizMenuFragment extends Fragment {


    private QuizActivity activity = null;

    public QuizMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (QuizActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quiz_menu, container, false);

        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinnerDictionaries);
        populateSpinner(rootView.getContext(), spinner, getListOfDictionaries());

        int noOfQuestions = PreferencesHelper.getNumberOfQuestions(getActivity());
        EditText et = (EditText) rootView.findViewById(R.id.editTextNoOfQuestions);
        et.setText(String.valueOf(noOfQuestions));

        return rootView;
    }

    private void populateSpinner(Context rootItem, Spinner spinner, List<Dictionary> items) {
        ArrayAdapter<Dictionary> adapter =
                new DictionariesArrayAdapter(rootItem,
                        android.R.layout.simple_spinner_item, items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private List<Dictionary> getListOfDictionaries() {

        return activity.getListOfDictionaries();
    }

}
