package com.mobica.mawa.fiszki;



import android.os.Bundle;
import android.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class AddWordFragment extends Fragment {


    public AddWordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_add_word, container, false);

        final EditText baseWordEditText = (EditText)rootView.findViewById(R.id.baseWordEditText);
        final EditText refWordEditText = (EditText)rootView.findViewById(R.id.refWordEditText);
        final ImageButton imageButton = (ImageButton)rootView.findViewById(R.id.addWordConfirm);
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
        return rootView;
    }


}
