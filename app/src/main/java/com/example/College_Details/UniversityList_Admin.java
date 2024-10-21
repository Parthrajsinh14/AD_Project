package com.example.College_Details;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class UniversityList_Admin extends AppCompatActivity {

    UniversityAdapter universityAdapter;
    RecyclerView recyclerView;
    FloatingActionButton addUniversity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(  this);
        setContentView(R.layout.activity_university_list_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerViewUniversity);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addUniversity = findViewById(R.id.addUniversity);

        addUniversity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UniversityList_Admin.this, AddUniversity.class));
            }
        });

        FirebaseRecyclerOptions<UniversityModel> options =
                new FirebaseRecyclerOptions.Builder<UniversityModel>()
                    .setQuery(FirebaseDatabase.getInstance().getReference()
                        .child("university"),UniversityModel.class )
                        .build();

        universityAdapter = new UniversityAdapter(options,1);
        recyclerView.setAdapter(universityAdapter);
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
                searchUniversity(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchUniversity(s);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void searchUniversity(String text){
        FirebaseRecyclerOptions<UniversityModel> options =
                new FirebaseRecyclerOptions.Builder<UniversityModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("university").orderByChild("name").startAt(text).endAt(text+"~"),UniversityModel.class)
                        .build();


        universityAdapter = new UniversityAdapter(options,1);
        universityAdapter.startListening();
        recyclerView.setAdapter(universityAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        universityAdapter.startListening();
    }
}