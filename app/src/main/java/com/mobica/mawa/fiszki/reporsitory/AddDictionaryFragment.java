package com.mobica.mawa.fiszki.reporsitory;



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
import com.mobica.mawa.fiszki.dao.dictionary.Dictionary;
import com.mobica.mawa.fiszki.dao.dictionary.JdbcDictionaryDAO;
import com.mobica.mawa.fiszki.dao.language.JdbcLanguageDAO;
import com.mobica.mawa.fiszki.helper.PreferencesHelper;

/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddDictionaryFragment extends Fragment {

    public static Fragment newInstance() {
        return new AddDictionaryFragment();
    }

    public AddDictionaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_add_dictionary, container, false);

        final EditText baseWordEditText = (EditText)rootView.findViewById(R.id.dictionaryEditText);
        final EditText refWordEditText = (EditText)rootView.findViewById(R.id.descriptionEditText);
        final ImageButton imageButton = (ImageButton)rootView.findViewById(R.id.addDirectoryConfirm);
        final ImageButton imageButtonCancel = (ImageButton)rootView.findViewById(R.id.addDirectoryCancel);
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
                Dictionary dictionary = new Dictionary();
                dictionary.setBaseLanguage(
                        JdbcLanguageDAO.getInstance(getActivity()).get(
                                PreferencesHelper.getBaseLanguage(getActivity())).getId());
                dictionary.setRefLanguage(
                        JdbcLanguageDAO.getInstance(getActivity()).get(
                                PreferencesHelper.getRefLanguage(getActivity())).getId());
                dictionary.setName(baseWordEditText.getText().toString());
                dictionary.setDescription(refWordEditText.getText().toString());
                JdbcDictionaryDAO.getInstance(rootView.getContext()).insert(dictionary);
                baseWordEditText.setText("");
                refWordEditText.setText("");
                imageButton.setClickable(false);
            }
        });

        imageButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RepositoryActivity)rootView.getContext()).showDictionaries();
            }
        });
        return rootView;
    }
}
