package com.mobica.mawa.fiszki.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobica.mawa.fiszki.R;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

public class QuizQuestionFragment extends RoboFragment implements QuestionFragmentInterface {

    @InjectView(R.id.quizQuestionStatusTextView)
    TextView quizQuestionStatusTextView;
    @InjectView(R.id.quizResponseTextView)
    TextView quizResponseTextView;
    @InjectView(R.id.quizResponseButton)
    Button quizResponseButton;
    @InjectView(R.id.quizWordTextView)
    TextView quizWordTextView;

    private int numberOfQuestions = 0;

    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_question, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            numberOfQuestions = getArguments().getInt(QuestionFragmentInterface.NO_OF_QUESTIONS);
            setCurrentQuestionId(getArguments().getInt(QuestionFragmentInterface.CURRENT_QUESTION_ID));
            setCurrentWord(getArguments().getString(QuestionFragmentInterface.CURRENT_WORD));
        }
        quizResponseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showResponse();
            }
        });
    }

    private void showResponse() {
        quizResponseTextView.setVisibility(View.VISIBLE);
        quizResponseButton.setVisibility(View.GONE);
    }

    private void hideResponse() {
        quizResponseTextView.setVisibility(View.GONE);
        quizResponseButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCurrentQuestionId(int currentQuestionId) {
        quizQuestionStatusTextView.setText(String.format("Question %d of %d", (currentQuestionId + 1), numberOfQuestions));
        hideResponse();
    }

    @Override
    public void setCurrentWord(String word) {
        quizWordTextView.setText(word);
    }

    @Override
    public void setCurrentWordResponse(String wordResponse) {
        quizResponseTextView.setText(wordResponse);
    }
}
