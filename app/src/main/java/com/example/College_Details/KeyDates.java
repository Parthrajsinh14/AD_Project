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

public class KeyDates extends AppCompatActivity {

    KeyDatesAdapter keyDatesAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_key_dates);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        recyclerView = findViewById(R.id.recyclerViewKeyDates);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<KeyDatesModel> options =
                new FirebaseRecyclerOptions.Builder<KeyDatesModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("keyDates"),KeyDatesModel.class )
                        .build();

        keyDatesAdapter = new KeyDatesAdapter(options);
        recyclerView.setAdapter(keyDatesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        keyDatesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        keyDatesAdapter.stopListening();
    }
}