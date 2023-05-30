package com.example.tva_projekt;

import android.content.SharedPreferences;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegisterActivity extends AppCompatActivity {
    private EditText etUsername, etPassword, etEmail, etGender, etDob;
    private ToggleButton toggleFemale, toggleMale;
    private Button btnRegister;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.editUsername);
        etPassword = findViewById(R.id.editPassword);
        etEmail = findViewById(R.id.editEmail);
        toggleFemale = findViewById(R.id.female);
        toggleMale = findViewById(R.id.male);
        etDob = findViewById(R.id.editDob);
        btnRegister = findViewById(R.id.signInButton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String gender = toggleFemale.isChecked() ? "Female" : "Male";
        int dob = Integer.parseInt(etDob.getText().toString().trim());

        // Create a JSON object with the user data
        JSONObject userData = new JSONObject();
        try {
            userData.put("userName", username);
            userData.put("password", password);
            userData.put("email", email);
            userData.put("gender", gender);
            userData.put("dob", dob);

            Log.d("User Data", userData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the user data to the server using AsyncTask
        new RegisterUserTask().execute(userData.toString());
    }

    private class RegisterUserTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String urlStr = "http://192.168.0.12:3000/user/register";

            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");

                // Send the JSON data to the server
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params[0].getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response from the server
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Registration successful
                    return true;
                } else {
                    // Registration failed
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("e", e.getMessage());
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                saveLoginStatus(true, username);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        }

        private void saveLoginStatus(boolean isLoggedIn, String username) {
            SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLoggedIn", isLoggedIn);
            editor.putString("username", username);
            editor.apply();

            Log.d("Shared Preferences", "Username: " + username);
        }
    }
}
