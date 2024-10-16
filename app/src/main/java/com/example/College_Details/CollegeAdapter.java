package com.example.College_Details;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;

import java.util.HashMap;
import java.util.Map;

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
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext())
                                .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup_college))
                                .setExpanded(true,1200)
                                .create();

                View dailogView = dialogPlus.getHolderView();
                EditText name = dailogView.findViewById(R.id.updateName);
                EditText fees = dailogView.findViewById(R.id.updateFees);
                EditText branch = dailogView.findViewById(R.id.updateBranch);
                EditText link = dailogView.findViewById(R.id.updateLink);
                Button update = dailogView.findViewById(R.id.btnUpdate);

                name.setText(model.getName());
                fees.setText(String.valueOf(model.getFees()));
                branch.setText(model.getBranch());
                link.setText(model.getLink());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> data = new HashMap<>();
                        data.put("name",name.getText().toString());
                        data.put("fees",Integer.parseInt(fees.getText().toString()));
                        data.put("branch",branch.getText().toString());
                        data.put("link",link.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("college")
                                .child(getRef(holder.getBindingAdapterPosition()).getKey()).updateChildren(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Failed to Update Data", Toast.LENGTH_SHORT).show();
                                    }
                                });



                    }
                });
                dialogPlus.show();




                dialogPlus.show();
                Toast.makeText(view.getContext(), "Edit Clicked", Toast.LENGTH_SHORT).show();

            });
        }
        if(holder.delete != null){
            holder.delete.setOnClickListener(view -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are You Sure?");
                builder.setMessage("Deleted Data can't be retrieved.");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("college")
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
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, fees, branch, indicator;
        Button edit,delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewCollegeName);
            fees = itemView.findViewById(R.id.textViewCollegeFees);
            branch = itemView.findViewById(R.id.textViewCollegeBranches);
            edit = itemView.findViewById(R.id.buttonEdit);
            delete = itemView.findViewById(R.id.buttonDelete);
            indicator = itemView.findViewById(R.id.textViewIndicator);
        }
    }
}
