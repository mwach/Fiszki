package com.mobica.mawa.fiszki.quiz;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobica.mawa.fiszki.R;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizSummary extends RoboFragment {

    static final String DICTIONARY_ID = "DICTIONARY_ID";
    static final String NO_OF_QUESTIONS = "NO_OF_QUESTIONS";
    static final String CORRECT_ANSWERS = "CORRECT_ANSWERS";
    static final String RATIO = "RATIO";

    @InjectView(R.id.textViewCorrectAnswers)
    TextView textViewCorrectAnswers;
    @InjectView(R.id.textViewNoOfQuestions)
    TextView textViewNoOfQuestions;
    @InjectView(R.id.textViewRatio)
    TextView textViewRatio;
    @InjectView(R.id.reRunQuizButton)
    Button reRunQuizButton;

    private QuizInterface quiz = null;
    private int totalQuestions;


    public QuizSummary() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_summary, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        populateFragment(getArguments());
        reRunQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz.startQuiz(totalQuestions, getArguments().getInt(DICTIONARY_ID));
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        quiz = (QuizInterface) activity;
    }

    private void populateFragment(Bundle arguments) {
        int correctAnswers = arguments.getInt(CORRECT_ANSWERS, 0);
        totalQuestions = arguments.getInt(NO_OF_QUESTIONS, 0);
        int ratio = arguments.getInt(RATIO, 0);

        textViewCorrectAnswers.setText(String.valueOf(correctAnswers));
        textViewNoOfQuestions.setText(String.valueOf(totalQuestions));
        textViewRatio.setText(String.format(getString(R.string.ratio_pct), ratio));
    }
}
