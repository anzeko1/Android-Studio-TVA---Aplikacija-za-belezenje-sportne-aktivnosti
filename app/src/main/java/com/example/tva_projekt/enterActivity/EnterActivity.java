package com.example.tva_projekt.enterActivity;

import static android.net.NetworkCapabilities.NET_CAPABILITY_VALIDATED;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.R;
import com.example.tva_projekt.dataObjects.ActivityFormObject;
import com.example.tva_projekt.dataObjects.User;
import com.example.tva_projekt.retrofit.ApiClient;
import com.example.tva_projekt.retrofit.RetrofitService;
import com.example.tva_projekt.enterActivity.roomDatabase.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterActivity extends AppCompatActivity {
    EditText activityName = null;
    EditText activityType = null;
    EditText activityLength = null;
    EditText description = null;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_activity);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.select_activities);
        String[] activities = getResources().getStringArray(R.array.activities_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
        textView.setAdapter(adapter);
        /*
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                System.out.println("connected");
                super.onAvailable(network);

                Toast.makeText(getApplicationContext(),"You are connected",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(@NonNull Network network) {
                System.out.println("disconected");
                super.onLost(network);

                Toast.makeText(getApplicationContext(),"You are disconected",Toast.LENGTH_SHORT).show();
            }
*/
        /*
        System.out.println(isInternetAvailable());

        if(isInternetAvailable() == "connected") {
            System.out.println("You are connected to internet");
        } else if (isInternetAvailable() == "disconnected") {
            System.out.println("You are disconnected");
        }
        */


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
        User user = new User();
        user.idUser = idUser;
        user.activityName = stringActivityName;
        user.activityType = stringActivityType;
        user.activityTypeRecord = activityTypeRecord;
        user.activityDate = date;
        user.activityLength = stringActivityLength;
        user.description = stringDescription;

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.activityFormDao().insertActivity(user);
        //vnos podatkov v funkcijo
        retrofitRequest(idUser, stringActivityName, stringActivityType, activityTypeRecord, date, stringActivityLength, stringDescription);
        activityName.getText().clear();
        activityType.getText().clear();
        activityLength.getText().clear();
        description.getText().clear();
        //AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        List<User> activityList = db.activityFormDao().getAll();
        //getData();
        for (int i = 0; i < activityList.size(); i++) {
            Log.v("example", "response: " + (activityList.get(i)));
        }
        //loadActivityRoom();

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
        RetrofitService retrofitService = ApiClient.getRetrofit().create(RetrofitService.class);
        Call<ActivityFormObject> enterActivity = retrofitService.insertActivityForm(activityFormObject);
        enterActivity.enqueue(new Callback<ActivityFormObject>() {
            //če je vse vredu se vrne response
            @Override
            public void onResponse(Call<ActivityFormObject> call, Response<ActivityFormObject> response) {
                if(response.body() != null) {
                    ActivityFormObject responseFromAPI = response.body();
                    String responseString = "Response:" + responseFromAPI.getResponse();
                    Toast.makeText(getApplicationContext(),"You seccessfully entered the activity",Toast.LENGTH_SHORT).show();
                    Log.d("Pretty Printed JSON :", responseString);
                } else {
                    Toast.makeText(getApplicationContext(),"There was a error when trying to insert activity",Toast.LENGTH_SHORT).show();
                    Log.d("Null", "Response: " + response.body());
                }
            }
            //če pride do napake v requestu nam tukaj vrže error
            @Override
            public void onFailure(Call<ActivityFormObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"There was a error when trying to insert activity",Toast.LENGTH_SHORT).show();
                Log.v("Error", "Response: " + t);
            }
        });
    }

    public boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities nc = cm.getNetworkCapabilities(cm.getActiveNetwork());

        return nc.hasCapability(NET_CAPABILITY_VALIDATED);
        //return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void closeEnterActivity(View view) {
        finish();
    }
    private void getData() {
        userList = new ArrayList<>();
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        userList = db.activityFormDao().getAll();

    }

}