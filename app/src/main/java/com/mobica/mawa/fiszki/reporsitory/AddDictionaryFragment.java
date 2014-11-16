package com.mobica.mawa.fiszki.reporsitory;


import android.app.Activity;
import android.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDictionaryFragment extends Fragment {

    private Repository repository;

    public AddDictionaryFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new AddDictionaryFragment();
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
        final View rootView = inflater.inflate(R.layout.fragment_add_dictionary, container, false);

        getActivity().getActionBar().setTitle(R.string.add_dictionary);

        final EditText baseWordEditText = (EditText) rootView.findViewById(R.id.dictionaryEditText);
        final EditText refWordEditText = (EditText) rootView.findViewById(R.id.descriptionEditText);
        final ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.addDirectoryConfirm);
        final ImageButton imageButtonCancel = (ImageButton) rootView.findViewById(R.id.addDirectoryCancel);
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
                Dictionary dictionary = new Dictionary();
                dictionary.setName(baseWordEditText.getText().toString());
                dictionary.setDescription(refWordEditText.getText().toString());
                getRepository().addDictionary(dictionary);
                baseWordEditText.setText("");
                refWordEditText.setText("");
                imageButton.setClickable(false);
            }
        });

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().showDictionaries();
            }
        });
        return rootView;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

}
