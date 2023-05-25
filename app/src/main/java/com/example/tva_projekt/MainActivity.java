package com.example.tva_projekt;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.tva_projekt.dataObjects.AppUser;
import com.example.tva_projekt.retrofit.ApiClient;
import com.example.tva_projekt.retrofit.RetrofitService;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Prvo se kreira objekt v tem primeri appUser - sedaj so ntor ročno vnešeni podatki
        //ob registraciji se bodo potem uporabili podatki iz obrazca
        /*
        AppUser appUser = new AppUser(
                "test",
                "test",
                "test@gmail.com",
                "test",
                22
        );
        //tukaj spodaj vse convertira objekt v json obliko da se lahko pošlje
        Gson gson = new Gson();
        String jsonAppUser = gson.toJson(appUser);
        RetrofitService retrofitService = ApiClient.getRetrofit().create(RetrofitService.class);
        Call<AppUser> registerUser = retrofitService.registerUser(appUser);
        registerUser.enqueue(new Callback<AppUser>() {
            //če je vse vredu se vrne response
            @Override
            public void onResponse(Call<AppUser> call, Response<AppUser> response) {
                if(response.body() != null) {
                    AppUser responseFromAPI = response.body();
                    String responseString = "Response:" + responseFromAPI.getResponse();
                    Log.d("Pretty Printed JSON :", responseString);
                } else {
                    Log.d("Null", "Response: " + response.body());
                }
            }
            //če pride do napake v requestu nam tukaj vrže error
            @Override
            public void onFailure(Call<AppUser> call, Throwable t) {
                Log.v("Error", "Response: " + t);
            }
        });
        */
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

}