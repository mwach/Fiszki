package com.mobica.mawa.fiszki.reporsitory;



import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.word.JdbcWordDAO;
import com.mobica.mawa.fiszki.dao.word.Word;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddWordFragment extends Fragment {

    private static final String DICTIONARY_ID = "DICTIONARY_ID";
    private int dictionaryId;

    public static AddWordFragment newInstance(int dictionaryId){
        AddWordFragment awf = new AddWordFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(DICTIONARY_ID, dictionaryId);
        awf.setArguments(bundle);
        return awf;
    }

    public AddWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_word, container, false);

        dictionaryId = getArguments().getInt(DICTIONARY_ID);

        final EditText baseWordEditText = (EditText)rootView.findViewById(R.id.baseWordEditText);
        final EditText refWordEditText = (EditText)rootView.findViewById(R.id.refWordEditText);
        final ImageButton imageButton = (ImageButton)rootView.findViewById(R.id.addWordConfirm);
        final ImageButton imageButtonCancel = (ImageButton)rootView.findViewById(R.id.addWordCancel);
        imageButton.setClickable(false);

        TextWatcher tv = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) { }

            @Override
            public void afterTextChanged(Editable editable) {
                if(baseWordEditText.getText().toString().length() > 0 &&
                        refWordEditText.getText().toString().length() > 0){
                    imageButton.setClickable(true);
                }else{
                    imageButton.setClickable(false);
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
                word.setDictionaryId(dictionaryId);
                JdbcWordDAO.getInstance(rootView.getContext()).insert(word);
                baseWordEditText.setText("");
                refWordEditText.setText("");
                imageButton.setClickable(false);
            }
        });

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RepositoryActivity)rootView.getContext()).loadDictionary(dictionaryId);
            }
        });
        return rootView;
    }


}
