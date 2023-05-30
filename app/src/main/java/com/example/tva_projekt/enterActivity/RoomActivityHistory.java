package com.example.tva_projekt.enterActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tva_projekt.R;

import java.util.ArrayList;

public class RoomActivityHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_history_recycler_view);
        TextView text1 = (TextView)findViewById(R.id.textView);
        text1.setText("hello");
        //tukaj se naredi lista za example item
        ArrayList<RoomDatabaseItem> roomDatabaseItem = new ArrayList<>();
        roomDatabaseItem.add(new RoomDatabaseItem(R.drawable.ic_android, "Line 1", "Line 2"));
        roomDatabaseItem.add(new RoomDatabaseItem(R.drawable.ic_android, "Line 3", "Line 4"));
        roomDatabaseItem.add(new RoomDatabaseItem(R.drawable.ic_android, "Line 5", "Line 6"));
    }
}