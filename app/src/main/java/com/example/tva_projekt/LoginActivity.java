package com.example.tva_projekt;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText username = null;
    EditText password = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginInput(View view) {
        //Pridobitev podatkov iz obrazca
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);

        //sprememba podatkov v string
        String stringUsername = username.getText().toString();
        String stringPassword = password.getText().toString();
    }
}

