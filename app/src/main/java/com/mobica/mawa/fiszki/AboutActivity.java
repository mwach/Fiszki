package com.mobica.mawa.fiszki;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;


public class AboutActivity extends RoboActivity {

    @InjectView(R.id.about_email)
    TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.marcinmail), null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        startActivity(Intent.createChooser(emailIntent, getString(R.string.send_email)));
    }
}
