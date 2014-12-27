package com.mobica.mawa.fiszki.quiz;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.common.AdapterItem;
import com.mobica.mawa.fiszki.common.GenericAdapter;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

import java.util.ArrayList;
import java.util.List;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizMenuFragment extends RoboFragment {

    @InjectView(R.id.spinnerDictionaries)
    Spinner spinnerDictionaries;
    @InjectView(R.id.spinnerStrategy)
    Spinner spinnerStrategy;
    @InjectView(R.id.editTextNoOfQuestions)
    EditText editTextNoOfQuestions;
    @InjectView(R.id.startQuizButton)
    Button startQuizButton;

    private QuizInterface quiz = null;

    public QuizMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.quiz = (QuizInterface) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_quiz_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Dictionary> dictionaries = quiz.getListOfDictionaries();
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });
        startQuizButton.setEnabled(!dictionaries.isEmpty());
        populateDictionariesSpinner(view.getContext(), spinnerDictionaries, dictionaries);

        populateStrategySpinner(view.getContext(), spinnerStrategy, dictionaries);

        editTextNoOfQuestions.setText(String.valueOf(PreferencesHelper.getNumberOfQuestions(getActivity())));
        spinnerStrategy.setSelection(PreferencesHelper.getStrategy(getActivity()) - 1);

    }

    private void populateDictionariesSpinner(Context rootItem, Spinner spinner, List<Dictionary> items) {

        List<AdapterItem> adapterItems = new ArrayList<AdapterItem>();
        if (!items.isEmpty()) {
            adapterItems.add(new AdapterItem(QuizInterface.UNDEFINED_DICTIONARY, getString(R.string.dictionaries_all)));
        }
        adapterItems.addAll(getListOfItems(items));
        ArrayAdapter<AdapterItem> adapter = new GenericAdapter(rootItem,
                android.R.layout.simple_spinner_item, adapterItems);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinnerDictionaries
        spinner.setAdapter(adapter);
    }

    private List<AdapterItem> getListOfItems(Iterable<Dictionary> items) {

        List<AdapterItem> adapterItems = new ArrayList<AdapterItem>();
        for (Dictionary dictionary : items) {
            adapterItems.add(new AdapterItem(dictionary.getId(), dictionary.getName()));
        }
        return adapterItems;
    }

    private void populateStrategySpinner(Context rootItem, Spinner spinner, List<Dictionary> items) {

        List<AdapterItem> adapterItems = getListOfStrategies();
        ArrayAdapter<AdapterItem> adapter = new GenericAdapter(rootItem,
                android.R.layout.simple_spinner_item, adapterItems);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinnerDictionaries
        spinner.setAdapter(adapter);
    }

    private List<AdapterItem> getListOfStrategies() {
        List<AdapterItem> items = new ArrayList<AdapterItem>();
        items.add(new AdapterItem(Strategy.RANDOM, getString(R.string.strategy_random)));
        items.add(new AdapterItem(Strategy.NEWEST, getString(R.string.strategy_newest)));
        items.add(new AdapterItem(Strategy.LEAST_KNOWN, getString(R.string.strategy_least_known)));
        return items;
    }

    private void startQuiz() {
        int noOfQuestions = Integer.parseInt(editTextNoOfQuestions.getText().toString());
        AdapterItem selectedDict = (AdapterItem) spinnerDictionaries.getSelectedItem();
        AdapterItem selectedStrategy = (AdapterItem) spinnerStrategy.getSelectedItem();
        quiz.startQuiz(noOfQuestions, selectedDict.getId(), selectedStrategy.getId());
    }
}
