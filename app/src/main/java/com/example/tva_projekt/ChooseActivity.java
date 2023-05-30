package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);
    }
    public void closeChooseActivity(View view) {
        finish();
    }

    public void enterActivity(View view) {
        Intent intent = new Intent(this, StartActivity.class);

        if (view.getId()== (R.id.cyclingActivity) || view.getId() == R.id.cyclingImage) {
            intent.putExtra(StartActivity.ACTIVITY_TYPE, getString(R.string.cycling));
        } else if (view.getId() == R.id.walkingActivity || view.getId() == R.id.walkingImage) {
            intent.putExtra(StartActivity.ACTIVITY_TYPE, getString(R.string.walking));
        } else if (view.getId() == R.id.runningActivity || view.getId() == R.id.runningImage) {
            intent.putExtra(StartActivity.ACTIVITY_TYPE, getString(R.string.running));
        }
        startActivity(intent);
    }
}