package com.example.tva_projekt.enterActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tva_projekt.R;

import java.util.ArrayList;

public class EnterAdapter extends RecyclerView.Adapter<EnterAdapter.ViewHolder>{
    private ArrayList<RealmDatabaseItem> roomList;
    //potrebuje view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
    //tukaj bomo ustvarili spremenljivke za napise v xmlju
        public TextView activityName;
        public TextView date;
        public TextView duration;

        //tukaj povežemo spremenljivke z idiji iz layouta
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activityName);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
        }
    }


    public EnterAdapter(ArrayList<RealmDatabaseItem> roomItemsList) {
        this.roomList = roomItemsList;
    }

    //pošljemo layout našemu adapterju
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exaple_room_history, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //tukaj dodamo spremenljivkam podatke
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RealmDatabaseItem currentItem = roomList.get(position);
        holder.activityName.setText(currentItem.getActivityName());
        holder.date.setText(currentItem.getDate());
        holder.duration.setText(currentItem.getDuration());
    }

    //definiramo kolko itemov bo v našem listu
    @Override
    public int getItemCount() {
        return roomList.size();
    }
}
