package com.example.tva_projekt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.common.TVAapplication;
import com.example.tva_projekt.retrofit.ApiClient;
import com.example.tva_projekt.retrofit.LoginDataObject;
import com.example.tva_projekt.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        getUserId(username, password);
    }

    public void getUserId(String username, String password) {
        LoginDataObject loginDataObject = new LoginDataObject(username, password);
        //tukaj spodaj vse convertira objekt v json obliko da se lahko pošlje
        RetrofitService retrofitService = ApiClient.getRetrofit().create(RetrofitService.class);
        Call<LoginDataObject> enterActivity = retrofitService.getUserId(loginDataObject);
        enterActivity.enqueue(new Callback<LoginDataObject>() {
            //če je vse vredu se vrne response
            @Override
            public void onResponse(Call<LoginDataObject> call, Response<LoginDataObject> response) {
                if(response.body() != null) {
                    LoginDataObject responseFromAPI = response.body();
                    if(responseFromAPI.getIdUser() != null) {
                        String responseIdUser = responseFromAPI.getIdUser();
                        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        saveLoginStatus(true, username);
                        editor.putString("idUser", responseIdUser);
                        editor.apply();

                        Toast.makeText(LoginActivity.this, "You logged in as: " + username, Toast.LENGTH_SHORT).show();
                        Log.d("Pretty Printed JSON :", "idUser: " + responseIdUser);

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                        Log.d("Error:", "Wrong username or password");
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    Log.d("Null", "Response: " + response.body());
                }
            }
            @Override
            public void onFailure(Call<LoginDataObject> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "There was a error trying to log in", Toast.LENGTH_SHORT).show();
                Log.v("Error", "Response: " + t);
            }
        });
    }
    private void saveLoginStatus(boolean isLoggedIn, String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.putString("username", username);
        editor.apply();

        Log.d("Shared Preferences", "Username: " + username);
    }
    public void registerActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    public void closeLoginActivity(View view) {
    finish();
}
}

/*
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
                String response = connection.getResponseMessage();
                System.out.println(response);
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
*/

