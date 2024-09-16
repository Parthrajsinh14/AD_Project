package com.example.College_Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class CollegeAdapter extends FirebaseRecyclerAdapter<CollegeModel,CollegeAdapter.viewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CollegeAdapter(@NonNull FirebaseRecyclerOptions<CollegeModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull CollegeModel model) {
        holder.name.setText(model.getName());
        holder.fees.setText(String.valueOf(model.getFees()));
        holder.branch.setText(model.getBranch());
        holder.indicator.setText(">");
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_college,parent,false);

        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView name,fees,branch,indicator;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewCollegeName);
            fees = itemView.findViewById(R.id.textViewCollegeFees);
            branch = itemView.findViewById(R.id.textViewCollegeBranches);
            indicator = itemView.findViewById(R.id.textViewIndicator);
        }
    }
}
