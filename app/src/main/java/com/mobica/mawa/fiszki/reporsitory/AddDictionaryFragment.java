package com.mobica.mawa.fiszki.reporsitory;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.bean.Dictionary;

import roboguice.fragment.provided.RoboFragment;
import roboguice.inject.InjectView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddDictionaryFragment extends RoboFragment {

    @InjectView(R.id.dictionaryEditText)
    private EditText dictionaryEditText;
    @InjectView(R.id.descriptionEditText)
    private EditText descriptionEditText;
    @InjectView(R.id.addDirectoryConfirm)
    private ImageButton addDirectoryConfirmButton;
    @InjectView(R.id.addDirectoryCancel)
    private ImageButton addDirectoryCancelButton;

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
        return inflater.inflate(R.layout.fragment_add_dictionary, container, false);

    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null && getActivity().getActionBar() != null) {
            getActivity().getActionBar().setTitle(R.string.add_dictionary);
        }
        addDirectoryConfirmButton.setClickable(false);

        TextWatcher tv = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (dictionaryEditText.getText().toString().length() > 0 &&
                        descriptionEditText.getText().toString().length() > 0) {
                    addDirectoryConfirmButton.setEnabled(true);

                } else {
                    addDirectoryConfirmButton.setEnabled(false);
                }
            }
        };

        dictionaryEditText.addTextChangedListener(tv);
        descriptionEditText.addTextChangedListener(tv);

        addDirectoryConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dictionary dictionary = new Dictionary();
                dictionary.setName(dictionaryEditText.getText().toString());
                dictionary.setDescription(descriptionEditText.getText().toString());
                getRepository().addDictionary(dictionary);
                dictionaryEditText.setText("");
                descriptionEditText.setText("");
                addDirectoryConfirmButton.setClickable(false);
            }
        });

        addDirectoryCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRepository().showDictionaries();
            }
        });
    }
}
