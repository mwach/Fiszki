package com.mobica.mawa.fiszki;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobica.mawa.fiszki.quiz.QuizActivity;
import com.mobica.mawa.fiszki.reporsitory.RepositoryActivity;


public class MainScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void gotoQuiz(View view) {
        Intent startQuizIntent = new Intent(this, QuizActivity.class);
        startActivity(startQuizIntent);
    }

    public void gotoRepository(View view) {
        Intent startRepositoryIntent = new Intent(this, RepositoryActivity.class);
        startActivity(startRepositoryIntent);
    }

    public void gotoStats(View view) {
    }

    public void gotoQuit(View view) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
