package com.example.tva_projekt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.common.TVAapplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String username;

    private TVAapplication app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app = (TVAapplication)getApplication();

        etUsername = findViewById(R.id.editUsername);
        etPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.loginButton);

        //Button btnSignUp = findViewById(R.id.registerPage);
        //Button btnLogout = findViewById(R.id.logoutButton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Create a JSON object with the login data
        JSONObject loginData = new JSONObject();
        try {
            loginData.put("userName", username);
            loginData.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Send the login data to the server using AsyncTask
        new LoginTask().execute(loginData.toString());
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            String urlStr = "http://164.8.223.94:3000/user/login";
            //String urlStr = "http://192.168.0.12:3000/user/login";

            try {
                URL url = new URL(urlStr);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                // Send the login data to the server
                OutputStream outputStream = connection.getOutputStream();
                outputStream.write(params[0].getBytes());
                outputStream.flush();
                outputStream.close();

                // Get the response from the server
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Successful login
                    // Handle the response here (e.g., parse JSON response, save user data, etc.)
                    // You can use connection.getInputStream() to read the response body
                    // For example:
                    // InputStream inputStream = connection.getInputStream();
                    // String responseBody = readInputStream(inputStream);
                    // inputStream.close();

                    //TODO shrani podatke o uporabniko v TAVAPlication class- pod mapo common
                    // app.user = new AppUser(username);
                    return true;
                } else {
                    // Failed login
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
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }
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
