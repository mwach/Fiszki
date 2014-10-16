package com.mobica.mawa.fiszki.reporsitory;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.mobica.mawa.fiszki.AddWordFragment;
import com.mobica.mawa.fiszki.Constants;
import com.mobica.mawa.fiszki.MainScreen;
import com.mobica.mawa.fiszki.R;
import com.mobica.mawa.fiszki.dao.Word;
import com.mobica.mawa.fiszki.dao.WordHelper;
import com.mobica.mawa.fiszki.quiz.QuizMenuFragment;

public class RepositoryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new DictionariesListFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.repository, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_home) {
            showMainMenu(null);
        }else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void showMainMenu(View view){
        Intent mainMenuIntent = new Intent(this, MainScreen.class);
        mainMenuIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainMenuIntent);
    }


    private WordsListFragment wordsListFragment = null;
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dictEngPlnTextView:
                if(wordsListFragment == null){
                    wordsListFragment = new WordsListFragment();
                    wordsListFragment.loadDictionary(getResources().getString(R.string.en));
                }
                getFragmentManager().beginTransaction().
                        replace(R.id.container, wordsListFragment)
                        .commit();
                break;

            case R.id.add_word:
                getFragmentManager().beginTransaction().
                        replace(R.id.container, new AddWordFragment())
                        .commit();
                break;

            case R.id.addWordConfirm:
                Word word = new Word();
                word.setBaseLanguage("EN");
                word.setRefLanguage("PL");
                final EditText baseWordEditText = (EditText)findViewById(R.id.baseWordEditText);
                final EditText refWordEditText = (EditText)findViewById(R.id.refWordEditText);
                word.setBaseWord(baseWordEditText.getText().toString());
                word.setRefWord(refWordEditText.getText().toString());

                WordHelper.getInstance(this).insert(word);
                baseWordEditText.setText("");
                refWordEditText.setText("");
                break;

            case R.id.addWordCancel:
                getFragmentManager().beginTransaction().
                        replace(R.id.container, wordsListFragment)
                        .commit();
                break;


            default:
                break;
        }
    }
}
