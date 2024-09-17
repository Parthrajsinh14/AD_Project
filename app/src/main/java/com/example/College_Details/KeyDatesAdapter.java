package com.example.College_Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class KeyDatesAdapter extends FirebaseRecyclerAdapter<KeyDatesModel,KeyDatesAdapter.viewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public KeyDatesAdapter(@NonNull FirebaseRecyclerOptions<KeyDatesModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull KeyDatesModel model) {
        holder.name.setText(model.getName());
        holder.date.setText(model.getDate());
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_key_dates,parent,false);

        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView name,date;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewKeyDateName);
            date = itemView.findViewById(R.id.textViewKeyDates);
        }
    }
}
