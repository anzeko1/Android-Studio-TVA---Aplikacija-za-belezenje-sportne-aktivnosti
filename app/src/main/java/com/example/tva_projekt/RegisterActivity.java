package com.example.tva_projekt;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText username = null;
    EditText email = null;
    EditText password = null;
    EditText gender = null;
    EditText age = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void loginInput(View view) {
        //Pridobitev podatkov iz obrazca
        username = (EditText) findViewById(R.id.editUsername);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);

        //preberi vrednost vsakega buttona
        //gender = (EditText) findViewById(R.id.editPassword);

        age = (EditText) findViewById(R.id.editAge);

        //sprememba podatkov v string
        String stringUsername = username.getText().toString();
        String stringPassword = password.getText().toString();
    }
}

