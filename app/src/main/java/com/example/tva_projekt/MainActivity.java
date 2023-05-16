package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void enterActivity(View view) {
        Intent intent = new Intent(MainActivity.this, EnterActivity.class);
        startActivity(intent);
    }

}