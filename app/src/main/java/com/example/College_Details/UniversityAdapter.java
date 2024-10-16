package com.example.College_Details;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

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

        // Handle the Edit button
        if (holder.edit != null) {
            holder.edit.setOnClickListener(view -> {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup_university))
                        .setExpanded(true,1000) // Adjust as needed
                        .create();

                View dialogView = dialogPlus.getHolderView();
                EditText name = dialogView.findViewById(R.id.updateName);
                EditText colleges = dialogView.findViewById(R.id.updateColleges);
                EditText link = dialogView.findViewById(R.id.updateLink);
                Button update = dialogView.findViewById(R.id.btnUpdate);

                // Set the text fields with the model data
                name.setText(model.getName());
                colleges.setText(String.valueOf(model.getCollege()));
                link.setText(model.getLink());

                dialogPlus.show();

                // Listen for layout changes to adjust for the keyboard
                update.setOnClickListener(v -> {
                    Map<String, Object> data = new HashMap<>();
                    data.put("name", name.getText().toString());
                    data.put("college", Integer.parseInt(colleges.getText().toString()));
                    data.put("link", link.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("university")
                            .child(getRef(holder.getBindingAdapterPosition()).getKey()).updateChildren(data)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                dialogPlus.dismiss();
                            })
                            .addOnFailureListener(e -> Toast.makeText(holder.name.getContext(), "Data Update Failed", Toast.LENGTH_SHORT).show());
                });
            });
        }

        // Handle the Delete button
        if (holder.delete != null) {
            holder.delete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted Data can't be retrieved.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("university")
                                .child(getRef(holder.getBindingAdapterPosition()).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(), "You cancelled deletion", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            });

        }
    }

    // ViewHolder class for managing views
    class viewHolder extends RecyclerView.ViewHolder {

        TextView name, indicator, colleges;
        Button edit, delete;

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
