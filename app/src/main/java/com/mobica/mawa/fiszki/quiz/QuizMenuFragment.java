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
import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import java.util.List;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizMenuFragment extends RoboFragment {

    @InjectView(R.id.spinnerDictionaries)
    Spinner spinner;
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
        populateSpinner(view.getContext(), spinner, dictionaries);
        editTextNoOfQuestions.setText(String.valueOf(quiz.getNumberOfQuestions()));

        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuiz();
            }
        });
        if (dictionaries.isEmpty()) {
            startQuizButton.setEnabled(false);
        }

    }

    private void populateSpinner(Context rootItem, Spinner spinner, List<Dictionary> items) {

        ArrayAdapter<Dictionary> adapter = new DictionaryAdapter(rootItem,
                android.R.layout.simple_spinner_item, items);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    private void startQuiz() {
        int noOfQuestions = Integer.parseInt(editTextNoOfQuestions.getText().toString());
        Dictionary selectedDict = (Dictionary) spinner.getSelectedItem();
        quiz.startQuiz(noOfQuestions, selectedDict.getId());
    }
}
