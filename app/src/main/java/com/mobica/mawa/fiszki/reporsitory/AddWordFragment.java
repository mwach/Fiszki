package com.mobica.mawa.fiszki.reporsitory;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.word.Word;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;


/**
 *
 */
public class AddWordFragment extends RoboFragment {

    private static final String DICTIONARY_ID = "DICTIONARY_ID";
    private int dictionaryId;
    private Repository repository;

    @InjectView(R.id.baseWordEditText)
    private EditText baseWordEditText;
    @InjectView(R.id.refWordEditText)
    private EditText refWordEditText;
    @InjectView(R.id.addWordConfirm)
    private ImageButton imageButton;
    @InjectView(R.id.addWordCancel)
    private ImageButton imageButtonCancel;

    public AddWordFragment() {
        // Required empty public constructor
    }

    public static AddWordFragment newInstance(int dictionaryId) {
        AddWordFragment awf = new AddWordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DICTIONARY_ID, dictionaryId);
        awf.setArguments(bundle);
        return awf;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setRepository((Repository) activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_word, container, false);

        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.add_word);
        }
        dictionaryId = getArguments().getInt(DICTIONARY_ID);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageButton.setClickable(false);

        TextWatcher tv = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (baseWordEditText.getText().toString().length() > 0 &&
                        refWordEditText.getText().toString().length() > 0) {
                    imageButton.setEnabled(true);
                } else {
                    imageButton.setEnabled(false);
                }
            }
        };

        baseWordEditText.addTextChangedListener(tv);
        refWordEditText.addTextChangedListener(tv);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Word word = new Word();
                word.setBaseWord(baseWordEditText.getText().toString());
                word.setRefWord(refWordEditText.getText().toString());
                word.setDictionary(new Dictionary(dictionaryId, null, null, null, null));
                getRepository().addWord(word);
                baseWordEditText.setText("");
                refWordEditText.setText("");
                imageButton.setEnabled(false);
            }
        });

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().loadDictionary(dictionaryId);
            }
        });

    }

    private Repository getRepository() {
        return repository;
    }

    private void setRepository(Repository repository) {
        this.repository = repository;
    }

}
