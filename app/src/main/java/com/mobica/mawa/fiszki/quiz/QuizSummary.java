package com.mobica.mawa.fiszki.quiz;



import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.R;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class QuizSummary extends Fragment {


    public QuizSummary() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View summaryView = inflater.inflate(R.layout.fragment_quiz_summary, container, false);

        populateFragment(summaryView, getArguments());

        return  summaryView;
    }

    private void populateFragment(View rootView, Bundle arguments) {
        int correctAnswers = arguments.getInt(QuestionFragmentInterface.QUIZ_CORRECT_ANSWERS, 0);
        int totalQuestions = arguments.getInt(QuestionFragmentInterface.QUIZ_NO_OF_QUESTIONS, 0);
        int ratio = arguments.getInt(QuestionFragmentInterface.QUIZ_RATIO, 0);

        ((TextView)rootView.findViewById(R.id.textViewCorrectAnswers)).setText(String.valueOf(correctAnswers));
        ((TextView)rootView.findViewById(R.id.textViewNoOfQuestions)).setText(String.valueOf(totalQuestions));
        ((TextView)rootView.findViewById(R.id.textViewRatio)).setText(String.format(getString(R.string.ratio_pct), ratio));
    }
}
