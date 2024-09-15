package com.example.College_Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class BranchAdapter extends FirebaseRecyclerAdapter<BranchModel,BranchAdapter.viewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public BranchAdapter(@NonNull FirebaseRecyclerOptions<BranchModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull BranchModel model) {
        holder.name.setText(model.getName());
        String seatNumber = model.seat + " Seats";
        String collegeNumber = model.college + " Colleges";

        holder.seats.setText(seatNumber);
        holder.colleges.setText(collegeNumber);
        holder.indicator.setText(">");
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_branch,parent,false);

        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView name,indicator,seats,colleges;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewBranchName);
            seats = itemView.findViewById(R.id.textViewBranchSeats);
            colleges = itemView.findViewById(R.id.textViewBranchColleges);
            indicator = itemView.findViewById(R.id.textViewIndicator);
        }
    }
}
