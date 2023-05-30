package com.example.tva_projekt;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.checkConnection.MyReceiver;
import com.example.tva_projekt.enterActivity.EnterActivity;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private Button loginPageButton;
    private Button registerPageButton;
    private Button logoutButton;
    private BroadcastReceiver MyReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);
        loginPageButton = findViewById(R.id.loginPage);
        registerPageButton = findViewById(R.id.registerPage);
        logoutButton = findViewById(R.id.logoutButton);

        updateUI();

        // Set the click listener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
        MyReceiver = new MyReceiver();
        broadcastIntent();
    }

    private void updateUI() {
        boolean isLoggedIn = isLoggedIn();

        if (isLoggedIn) {
            String username = getUsername();
            // User is logged in, show username and logout button
            loginPageButton.setVisibility(View.GONE);
            registerPageButton.setVisibility(View.GONE);
            logoutButton.setVisibility(View.VISIBLE);
            logoutButton.setText(getString(R.string.log_out) + " (" + username + ")");
        } else {
            // User is not logged in, show login and register buttons
            loginPageButton.setVisibility(View.VISIBLE);
            registerPageButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.GONE);
        }
    }
    //ob kliku na Enter Activity preusmeri na aktivnost Enter Activity
    public void enterActivity(View view) {
        Intent intent = new Intent(MainActivity.this, EnterActivity.class);
        startActivity(intent);
    }
    public void chooseActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
        startActivity(intent);
    }

    public void historyActivity(View view) {
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }



    public void registerActivity(View view) {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void loginActivity(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void logoutUser() {
        clearSharedPreferences();
        updateUI();
        Log.d("Shared Preferences", "Cleared");
    }

    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }

    private void clearSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

};