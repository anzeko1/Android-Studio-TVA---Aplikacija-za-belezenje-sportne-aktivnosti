package com.example.tva_projekt.enterActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tva_projekt.R;
import com.example.tva_projekt.dataObjects.ActivityRealmObject;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class RealmActivityHistory extends AppCompatActivity {
    //Realm realm;
    private ArrayList<RealmDatabaseItem> realmDatabaseItem;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_history_recycler_view);
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<ActivityRealmObject> query = realm.where(ActivityRealmObject.class);
        RealmResults<ActivityRealmObject> activities = query.findAll();
        ArrayList<RealmDatabaseItem> realmDatabaseItem = new ArrayList<>();
        if (activities != null) {

            for (int i = 0; i < activities.size(); i++) {
                System.out.println(activities.get(i).getActivityName());
                realmDatabaseItem.add(new RealmDatabaseItem(R.drawable.ic_android, activities.get(i).getActivityName(), activities.get(i).getActivityDate(), activities.get(i).getActivityLength()));
            }
        } else {
            Log.v("Error: ", "Activities are null");
        }
        realm.close();
        recyclerView = findViewById(R.id.roomHistoryRecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new EnterAdapter(realmDatabaseItem);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void closeEnterActivity(View view) {
        finish();
    }
}