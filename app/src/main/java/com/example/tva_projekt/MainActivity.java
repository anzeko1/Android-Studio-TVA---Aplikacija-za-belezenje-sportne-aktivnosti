package com.example.tva_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tva_projekt.dataObjects.User;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.sync.SyncConfiguration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* //to zakomentirano probavam za povezavo na bazo
        Realm.init(this);

        AppConfiguration appConfig = new AppConfiguration.Builder("\"application-0-ubsgk\"").build();
        App app = new App(appConfig);
        app.login(Credentials.anonymous());


        String realmName = "tva_projekt";
        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).build();
        Realm backgroundThreadRealm = Realm.getInstance(config);

        User user = new User("test", "test", "test", "test", 22);
        backgroundThreadRealm.executeTransaction (transactionRealm -> {
            transactionRealm.insert(user);
        });
        */
    }
    //ob kliku na Enter Activity preusmeri na aktivnost Enter Activity
    public void enterActivity(View view) {
        Intent intent = new Intent(MainActivity.this, EnterActivity.class);
        startActivity(intent);
    }

}