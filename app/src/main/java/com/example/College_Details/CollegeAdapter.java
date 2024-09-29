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

public class CollegeAdapter extends FirebaseRecyclerAdapter<CollegeModel, CollegeAdapter.ViewHolder> {

    // Define view types
    private static final int VIEW_TYPE_ADMIN = 1;
    private static final int VIEW_TYPE_COLLEGE = 2;
    private int layoutType;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     * @param layoutType Layout type to use (1 for admin layout, 2 for normal college layout)
     */
    public CollegeAdapter(@NonNull FirebaseRecyclerOptions<CollegeModel> options, int layoutType) {
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        // Inflate the correct layout based on the view type
        if (viewType == VIEW_TYPE_ADMIN) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_college_admin, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_college, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull CollegeModel model) {
        holder.name.setText(model.getName());
        holder.fees.setText("Fees :- "+model.getFees());
        holder.branch.setText(model.getBranch());
        String url = model.getLink();

        // Set the indicator only if it's the admin layout
        if (layoutType == VIEW_TYPE_ADMIN) {
            holder.indicator.setText(">");
        }

        holder.itemView.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        });

        if(holder.edit != null){
            holder.edit.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "Edit Clicked", Toast.LENGTH_SHORT).show();
            });
        }
        if(holder.delete != null){
            holder.delete.setOnClickListener(view -> {
                Toast.makeText(view.getContext(), "Delete Clicked", Toast.LENGTH_SHORT).show();
            });
        }
    }

    // ViewHolder class for managing views
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, fees, branch, indicator;
        Button edit = null,delete=null;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCollegeName);
            fees = itemView.findViewById(R.id.textViewCollegeFees);
            branch = itemView.findViewById(R.id.textViewCollegeBranches);
            edit = itemView.findViewById(R.id.buttonEdit);
            delete = itemView.findViewById(R.id.buttonDelete);
            // Only find the indicator in the admin layout
            if (itemView.findViewById(R.id.textViewIndicator) != null) {
                indicator = itemView.findViewById(R.id.textViewIndicator);
            }
        }
    }
}
