package com.rvai.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class InfoSliderActivity extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addFragment(new Step.Builder().setTitle("Get All updates for Corona")
                .setContent("Corona ko china bhe denge")
                .setBackgroundColor(Color.parseColor("#04bcf4"))
                .setDrawable(R.drawable.ic_access_time)
                .build());

        addFragment(new Step.Builder().setTitle("Get all the advisories")
                .setContent("Get all the govt issued advisaries in one place")
                .setBackgroundColor(Color.parseColor("#153f87"))
                .setDrawable(R.drawable.ic_access_time)
                .build());

        addFragment(new Step.Builder().setTitle("Scan to Service")
                .setContent("Easily scan Equipment's qr code to perform service")
                .setBackgroundColor(Color.parseColor("#FF4081"))
                .setDrawable(R.drawable.ic_access_time)
                .build());
    }
    @Override
    public void finishTutorial() {
        super.finishTutorial();
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        SharedPreferences preferences = getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putBoolean(Constants.WASRUNBEFORE, true);
        editor.apply();

        finish();
    }
}
