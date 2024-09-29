package com.example.College_Details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UniversityAdapter extends FirebaseRecyclerAdapter<UniversityModel, UniversityAdapter.viewHolder> {

    // Define view types
    private static final int VIEW_TYPE_ADMIN = 1;
    private static final int VIEW_TYPE_UNIVERSITY = 2;
    private int layoutType;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param layoutType Type of layout to use (1 for admin layout, 2 for university layout)
     */
    public UniversityAdapter(@NonNull FirebaseRecyclerOptions<UniversityModel> options, int layoutType) {
        super(options);
        this.layoutType = layoutType;
    }

    @Override
    public int getItemViewType(int position) {
        // Return the layout type passed in the constructor
        return layoutType;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // Inflate the correct layout based on the view type
        if (viewType == VIEW_TYPE_ADMIN) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_university_admin, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_university, parent, false);
        }
        return new viewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewHolder holder, int position, @NonNull UniversityModel model) {
        holder.name.setText(model.getName());
        String collegeNumber = model.college + " Colleges";
        holder.colleges.setText(collegeNumber);

        holder.edit.setOnClickListener(view -> {

        });

        // Set the indicator only if it's the admin layout
        if (layoutType == VIEW_TYPE_ADMIN) {
            holder.indicator.setText(">");
        }

        String url = model.getLink();
        holder.itemView.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });
        if(holder.edit != null){
            holder.edit.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "Edit CLicked", Toast.LENGTH_SHORT).show();
            });
        }
        if(holder.delete != null){
            holder.delete.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "Deletes Clicked", Toast.LENGTH_SHORT).show();
            });
        }
    }

    // ViewHolder class for managing views
    class viewHolder extends RecyclerView.ViewHolder {

        TextView name, indicator, colleges;
        Button edit,delete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewUniversityName);
            colleges = itemView.findViewById(R.id.textViewUniversityCollege);
            edit = itemView.findViewById(R.id.buttonEdit);
            delete = itemView.findViewById(R.id.buttonDelete);

            // Only find the indicator in the admin layout
            if (itemView.findViewById(R.id.textViewIndicator) != null) {
                indicator = itemView.findViewById(R.id.textViewIndicator);
            }
        }
    }
}
