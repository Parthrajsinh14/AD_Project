package com.example.College_Details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UniversityAdapter extends FirebaseRecyclerAdapter<UniversityModel,UniversityAdapter.viewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UniversityAdapter(@NonNull FirebaseRecyclerOptions<UniversityModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull UniversityModel model) {
        holder.name.setText(model.getName());
        String collegeNumber = model.college + " Colleges";
        holder.colleges.setText(collegeNumber);
        holder.indicator.setText(">");
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_university,parent,false);

        return new viewHolder(view);
    }

    class viewHolder extends RecyclerView.ViewHolder{

        TextView name,indicator,colleges;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textViewUniversityName);
            colleges = itemView.findViewById(R.id.textViewUniversityCollege);
            indicator = itemView.findViewById(R.id.textViewIndicator);
        }
    }
}
