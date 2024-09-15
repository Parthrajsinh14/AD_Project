package com.example.College_Details;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class BranchList extends AppCompatActivity {

    BranchAdapter branchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_branch_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerViewBranches);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<BranchModel> options =
                new FirebaseRecyclerOptions.Builder<BranchModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("branch"),BranchModel.class )
                        .build();

        branchAdapter = new BranchAdapter(options);
        recyclerView.setAdapter(branchAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        branchAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        branchAdapter.stopListening();
    }
}