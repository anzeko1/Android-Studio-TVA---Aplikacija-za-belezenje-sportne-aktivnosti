package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.example.tva_projekt.R;
import com.example.tva_projekt.dataObjects.ActivityFormObject;
import com.example.tva_projekt.dataObjects.ActivityObject;
import com.example.tva_projekt.dataObjects.AppUser;
import com.example.tva_projekt.retrofit.ApiClient;
import com.example.tva_projekt.retrofit.RetrofitService;
import com.google.gson.Gson;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterActivity extends AppCompatActivity {
    EditText activityName = null;
    EditText activityType = null;
    EditText activityLength = null;
    EditText description = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_activity);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.select_activities);
        String[] activities = getResources().getStringArray(R.array.activities_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
        textView.setAdapter(adapter);

    }
    public void enterActivityInput(View view) {
        //Pridobitev podatkov iz obrasca
        activityName = (EditText) findViewById(R.id.editActivityName);
        activityType = (EditText) findViewById(R.id.select_activities);
        activityLength = (EditText) findViewById(R.id.editDuration);
        description = (EditText) findViewById(R.id.editActivityDetails);

        //sprememba podatkov v string
        String stringActivityName = activityName.getText().toString();
        String stringActivityType = activityType.getText().toString();
        String stringActivityLength = activityLength.getText().toString();
        String stringDescription = description.getText().toString();
        //pridobivanje trenutnega časa
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formatedDate = dateFormat.format(date);
        String activityTypeRecord = "manualRecord";
        String idUser = "test";
        //vnos podatkov v funkcijo
        retrofitRequest(idUser, stringActivityName, stringActivityType, activityTypeRecord, date, stringActivityLength, stringDescription);

    }
    public void retrofitRequest(String idUser, String activityName, String activityType, String activityTypeRecord, Date activityDate, String activityLength, String description) {
        ActivityFormObject activityFormObject = new ActivityFormObject(
                idUser,
                activityName,
                activityType,
                activityTypeRecord,
                activityDate,
                activityLength,
                description
        );
        //tukaj spodaj vse convertira objekt v json obliko da se lahko pošlje
        Gson gson = new Gson();
        String jsonAppUser = gson.toJson(activityFormObject);
        RetrofitService retrofitService = ApiClient.getRetrofit().create(RetrofitService.class);
        Call<ActivityFormObject> registerUser = retrofitService.insertActivityForm(activityFormObject);
        registerUser.enqueue(new Callback<ActivityFormObject>() {
            //če je vse vredu se vrne response
            @Override
            public void onResponse(Call<ActivityFormObject> call, Response<ActivityFormObject> response) {
                if(response.body() != null) {
                    ActivityFormObject responseFromAPI = response.body();
                    String responseString = "Response:" + responseFromAPI.getResponse();
                    Log.d("Pretty Printed JSON :", responseString);
                } else {
                    Log.d("Null", "Response: " + response.body());
                }
            }
            //če pride do napake v requestu nam tukaj vrže error
            @Override
            public void onFailure(Call<ActivityFormObject> call, Throwable t) {
                Log.v("Error", "Response: " + t);
            }
        });
    }
    public void closeEnterActivity(View view) {
        finish();
    }
}