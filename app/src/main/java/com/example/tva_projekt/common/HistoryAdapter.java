package com.example.tva_projekt.common;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tva_projekt.HistoryModel;
import com.example.tva_projekt.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {
    private OnRecyclerViewCallback callback;
    private List<HistoryModel> data;

    public HistoryAdapter(OnRecyclerViewCallback callback, List<HistoryModel> data){
        this.callback = callback;
        this.data = data;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new HistoryHolder(view);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<HistoryModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public HistoryModel getItem(int position) {
        return data.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        HistoryModel model = data.get(position);
        holder.name.setText(model.getActivityName());
        holder.date.setText(model.getActivityDate());
        holder.duration.setText(model.getActivityDuration());
        if (model.getActivityName().equals("Running")){
            holder.image.setImageResource(R.drawable.running);
        } else if (model.getActivityName().equals("Cycling")){
            holder.image.setImageResource(R.drawable.cycling);
        } else if (model.getActivityName().equals("Walking")){
            holder.image.setImageResource(R.drawable.walking);
        }
    }

    public class HistoryHolder extends RecyclerView.ViewHolder{
        private final ImageView image;
        private final TextView name;
        private final TextView date;
        private final TextView duration;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.imageView);
            this.name = itemView.findViewById(R.id.tvName);
            this.date = itemView.findViewById(R.id.tvDate);
            this.duration = itemView.findViewById(R.id.tvDuration);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.Callback(getAdapterPosition());
                }
            });
        }
    }
}
