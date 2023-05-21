package com.example.tva_projekt;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tva_projekt.dataObjects.AppUser;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;
import io.realm.mongodb.Credentials;
import io.realm.mongodb.sync.SyncConfiguration;
import io.realm.mongodb.User;
// MongoDB Service Packages
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoDatabase;
import io.realm.mongodb.mongo.MongoCollection;
// Utility Packages
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;


public class MainActivity extends AppCompatActivity {
    Realm uiThreadRealm;
    MongoClient mongoClient;
    MongoDatabase mongoDatabase;
    MongoCollection<AppUser> mongoCollection;
    //MongoCollection<Document> mongoCollection;
    User user;
    App app;
    String AppId = "application-0-ubsgk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to spodaj do konca funkcije je zapisana poveza in insert podatkov.
        //Zaenkrat probavam tukaj da se lahko potem naprej prenese
        Realm.init(this);
        app = new App(new AppConfiguration.Builder(AppId).build());
        app.loginAsync(Credentials.anonymous(), new App.Callback<User>() {
            @Override
            public void onResult(App.Result<User> result) {
                initializeMongoDB();

                AppUser appUser = new AppUser(
                        new ObjectId(),
                        "test",
                        "test",
                        "test@gmail.com",
                        "test",
                        22);
                mongoCollection.insertOne(appUser).getAsync(task -> {
                    if (task.isSuccess()) {
                        Log.v("EXAMPLE", "successfully inserted a document with id: " + task.get().getInsertedId());
                    } else {
                        Log.v("Example", "uporabnik:" + appUser);
                        Log.e("EXAMPLE", "failed to insert documents with: " + task.getError().getErrorMessage());
                    }
                });

                //Document document = new Document().append("testId", "testId").append("userName", "test").append("password", "test").append("email", "test@gmail.com").append("gender", "test").append("dob", 22);
                /*
                AppUser appUser = new AppUser(
                        new ObjectId(),
                        "test",
                        "test",
                        "test@gmail.com",
                        "test",
                        22);
                */
                /*
                mongoCollection.insertOne(document).getAsync(task -> {
                    if (task.isSuccess()) {
//                        BsonObjectId insertedId = result.get().getInsertedId().asObjectId();
                        Log.v("adding", "result");
                        //Toast.makeText(MainActivity.this, "Inserted successfully", Toast.LENGTH_LONG).show();
                    } else {
                        Log.v("adding", "result failed" + task.getError().toString());
                        //Toast.makeText(MainActivity.this, "Error " + result.getError(), Toast.LENGTH_LONG).show();
                    }
                });
                */
            }
        });


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

    //vzpostavitev povezave na mongodb
    private void initializeMongoDB() {
        user = app.currentUser();
        mongoClient = user.getMongoClient("mongodb-atlas");
        mongoDatabase = mongoClient.getDatabase("tva_projekt");
        CodecRegistry pojoCodecRegistry = fromRegistries(AppConfiguration.DEFAULT_BSON_CODEC_REGISTRY,
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        mongoCollection = mongoDatabase.getCollection("user", AppUser.class).withCodecRegistry(pojoCodecRegistry);
        //mongoCollection = mongoDatabase.getCollection("user");
        //mongoCollection = mongoDatabase.getCollection("user", AppUser.class);
    }

}