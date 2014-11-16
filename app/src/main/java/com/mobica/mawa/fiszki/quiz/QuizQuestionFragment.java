package com.mobica.mawa.fiszki.quiz;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mobica.mawa.fiszki.R;

public class QuizQuestionFragment extends Fragment implements QuestionFragmentInterface {

    private int numberOfQuestions = 0;
    private View view = null;
    private int currentQuestionId = 0;

    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        if (getArguments() != null) {
            numberOfQuestions = getArguments().getInt(QuestionFragmentInterface.QUIZ_NO_OF_QUESTIONS);
            setCurrentQuestionId(getArguments().getInt(QuestionFragmentInterface.CURRENT_QUESTION_ID));
            setCurrentWord(getArguments().getString(QuestionFragmentInterface.CURRENT_WORD));
        }

        return view;
    }

    @Override
    public int getCurrentQuestionId() {
        return currentQuestionId;
    }

    @Override
    public void setCurrentQuestionId(int currentQuestionId) {
        this.currentQuestionId = currentQuestionId;
        TextView tv = (TextView) view.findViewById(R.id.quizQuestionStatusTextView);
        tv.setText(String.format("Question %d of %d", (currentQuestionId + 1), numberOfQuestions));

        TextView tv2 = (TextView) view.findViewById(R.id.quizResponseTextView);
        tv2.setVisibility(View.INVISIBLE);

        Button button = (Button) view.findViewById(R.id.quizResponseButton);
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void setCurrentWord(String word) {
        TextView tv = (TextView) view.findViewById(R.id.quizWordTextView);
        tv.setText(word);
    }

    @Override
    public void setCurrentWordResponse(String wordResponse) {
        TextView tv = (TextView) view.findViewById(R.id.quizResponseTextView);
        tv.setText(wordResponse);
        tv.setVisibility(View.VISIBLE);

        Button button = (Button) view.findViewById(R.id.quizResponseButton);
        button.setVisibility(View.GONE);
    }

    @Override
    public void setNumberOfQuestions(int size) {

    }

}
