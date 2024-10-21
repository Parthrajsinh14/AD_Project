package com.example.College_Details;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CollegeList extends AppCompatActivity {
    CollegeAdapter collegeAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_college_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerViewColleges);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CollegeModel> options =
                new FirebaseRecyclerOptions.Builder<CollegeModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("college"),CollegeModel.class)
                        .build();

        collegeAdapter = new CollegeAdapter(options,2);
        recyclerView.setAdapter(collegeAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        collegeAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) menuItem.getActionView();


        assert searchView != null;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                collegeSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                collegeSearch(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }

    private void collegeSearch(String text){
        FirebaseRecyclerOptions<CollegeModel> options =
                new FirebaseRecyclerOptions.Builder<CollegeModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("college").orderByChild("name").startAt(text).endAt(text+"~"),CollegeModel.class)
                        .build();

        collegeAdapter = new CollegeAdapter(options,2);
        collegeAdapter.startListening();
        recyclerView.setAdapter(collegeAdapter);
    }

}