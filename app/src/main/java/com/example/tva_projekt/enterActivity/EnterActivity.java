package com.example.tva_projekt.enterActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.R;
import com.example.tva_projekt.checkConnection.NetworkUtil;
import com.example.tva_projekt.dataObjects.ActivityFormObject;
import com.example.tva_projekt.dataObjects.ActivityRealmObject;
import com.example.tva_projekt.retrofit.ApiClient;
import com.example.tva_projekt.retrofit.GetPredefinedActivitiesResult;
import com.example.tva_projekt.retrofit.RetrofitService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterActivity extends AppCompatActivity {
    EditText activityName = null;
    EditText activityType = null;
    EditText activityLength = null;
    EditText description = null;
    private Button enterHistory;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_activity);
        enterHistory = findViewById(R.id.seeEnteredActivities);
        if(isLoggedIn()){
            enterHistory.setVisibility(View.GONE);
        } else {
            enterHistory.setVisibility(View.VISIBLE);
        }
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.select_activities);
        String[] activities = getResources().getStringArray(R.array.activities_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities);
        textView.setAdapter(adapter);
        Realm.init(this);

        RealmConfiguration config = new RealmConfiguration.
                Builder().
                deleteRealmIfMigrationNeeded()
                .allowWritesOnUiThread(true).
                build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public void getPredefinedActivity(View view) {
        activityType = (EditText) findViewById(R.id.select_activities);
        activityLength = (EditText) findViewById(R.id.editDuration);
        description = (EditText) findViewById(R.id.editActivityDetails);
        String stringActivityType = activityType.getText().toString();

        if(stringActivityType.matches("")){
            System.out.println("It is null");
        } else {
            RetrofitService retrofitService = ApiClient.getRetrofit().create(RetrofitService.class);
            Call<List<GetPredefinedActivitiesResult>> getActivity = retrofitService.getPredefinedActivities(stringActivityType);
            getActivity.enqueue(new Callback<List<GetPredefinedActivitiesResult>>() {
                @Override
                public void onResponse(Call<List<GetPredefinedActivitiesResult>> getActivity, Response<List<GetPredefinedActivitiesResult>> response) {
                    List<GetPredefinedActivitiesResult> adslist = response.body();
                    Integer length = adslist.get(0).getActivitylength();
                    String descriptionActivity = adslist.get(0).getDescription();
                    activityLength.setText(Integer.toString(length));
                    description.setText(descriptionActivity);
                    Toast.makeText(getApplicationContext(),"You seccessfully got the activity",Toast.LENGTH_SHORT).show();
                    //System.out.println(descriptionActivity + "-----");// + Sao2 + "-----" + telesnaTmeperatura);
                }

                @Override
                public void onFailure(Call<List<GetPredefinedActivitiesResult>> getActivity, Throwable t) {
                    Toast.makeText(EnterActivity.this, "There is no activity with that type", Toast.LENGTH_SHORT).show();
                    Log.v("example", "Error: " + t.getMessage().toString());
                }
            });
        }
    }


    public void enterActivityInput(View view) {
        //Pridobitev podatkov iz obrasca
        activityName = (EditText) findViewById(R.id.editActivityName);
        activityType = (EditText) findViewById(R.id.select_activities);
        activityLength = (EditText) findViewById(R.id.editDuration);
        description = (EditText) findViewById(R.id.editActivityDetails);

        String stringActivityName = activityName.getText().toString();
        String stringActivityType = activityType.getText().toString();
        String stringActivityLength = activityLength.getText().toString();
        String stringDescription = description.getText().toString();


        //pridobivanje trenutnega časa
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formatedDate = dateFormat.format(date);
        String activityTypeRecord = "manualRecord";
        String activityId = uuid();
        String idUser;
        if(isLoggedIn()) {
            idUser = getUsername();
        } else {
            idUser = "";
        }
        String status = NetworkUtil.getConnectivityStatusString(this);
        if(!isLoggedIn() || status.equals("No internet is available")){
            insertIntoRealmDatabase(idUser, stringActivityName, stringActivityType, activityTypeRecord, formatedDate, stringActivityLength, stringDescription, activityId);
        } else {
            retrofitRequest(idUser, stringActivityName, stringActivityType, activityTypeRecord, date, stringActivityLength, stringDescription, activityId);
        }

        activityName.getText().clear();
        activityType.getText().clear();
        activityLength.getText().clear();
        description.getText().clear();
    }
    public void retrofitRequest(String idUser, String activityName, String activityType, String activityTypeRecord, Date activityDate, String activityLength, String description, String activityRealmId) {
        ActivityFormObject activityFormObject = new ActivityFormObject(
                idUser,
                activityName,
                activityType,
                activityTypeRecord,
                activityDate,
                activityLength,
                description,
                activityRealmId
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
            @Override
            public void onFailure(Call<ActivityFormObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"There was a error when trying to insert activity",Toast.LENGTH_SHORT).show();
                Log.v("Error", "Response: " + t);
            }
        });
    }

    public void closeEnterActivity(View view) {
        finish();
    }
    public void viewEnteredHistory(View view) {
        Intent intent = new Intent(EnterActivity.this, RealmActivityHistory.class);
        startActivity(intent);
    }

    public void insertIntoRealmDatabase(String idUser, String activityName, String activityType, String activityTypeRecord, String activityDate, String activityLength, String description, String uuid) {

        final ActivityRealmObject activityRealmObject = new ActivityRealmObject();
        Number currentId = realm.where(ActivityRealmObject.class).max("id");
        long nextId;
        if(currentId==null){
            nextId=1;
        }
        else{
            nextId=currentId.intValue()+1;
        }

        activityRealmObject.setId(nextId);
        activityRealmObject.setActivityId(uuid);
        activityRealmObject.setActivityName(activityName);
        activityRealmObject.setActivityType(activityType);
        activityRealmObject.setActivityTypeRecord(activityTypeRecord);
        activityRealmObject.setActivityDate(activityDate);
        activityRealmObject.setActivityLength(activityLength);
        activityRealmObject.setDescription(description);

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(activityRealmObject);
            }
        });


    }
    public String uuid(){
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        //System.out.println("UUID: " + uuid);
        return uuid;
    }
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            return status;
        }
        return status;
    }
    private boolean isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
    private String getUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("username", "");
    }
}