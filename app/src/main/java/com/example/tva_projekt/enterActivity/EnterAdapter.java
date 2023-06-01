package com.example.tva_projekt.enterActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tva_projekt.R;
import com.example.tva_projekt.dataObjects.ActivityRealmObject;

import java.util.ArrayList;

import io.realm.Realm;

public class EnterAdapter extends RecyclerView.Adapter<EnterAdapter.ViewHolder>{
    private ArrayList<RealmDatabaseItem> realmList;
    //public static class ViewHolder extends RecyclerView.ViewHolder {
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView activityName;
        public TextView date;
        public TextView duration;
        public TextView activityId;
        private ImageButton imageButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activityName);
            date = itemView.findViewById(R.id.date);
            duration = itemView.findViewById(R.id.duration);
            activityId = itemView.findViewById(R.id.activityId);

            itemView.findViewById(R.id.imageButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String activityToDeleteId = activityId.getText().toString();
                    String activityNameRealm = activityName.getText().toString();
                    Realm realm = Realm.getDefaultInstance();
                    ActivityRealmObject dataModel = realm.where(ActivityRealmObject.class).equalTo("activityRealmId", activityToDeleteId).findFirst();
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            Toast.makeText(view.getContext(), "You deleted activity: " + activityNameRealm, Toast.LENGTH_SHORT).show();
                            dataModel.deleteFromRealm();
                        }
                    });
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), getItemCount());
                }
            });

        }

    }


    public EnterAdapter(ArrayList<RealmDatabaseItem> realmItemsList) {
        this.realmList = realmItemsList;
        notifyDataSetChanged();
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
        RealmDatabaseItem currentItem = realmList.get(position);
        holder.activityName.setText(currentItem.getActivityName());
        holder.date.setText(currentItem.getDate());
        holder.duration.setText(currentItem.getDuration());
        holder.activityId.setText(currentItem.getActivityId());

    }

    //definiramo kolko itemov bo v našem listu
    @Override
    public int getItemCount() {
        return realmList.size();
    }
}
