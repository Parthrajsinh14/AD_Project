package com.example.College_Details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DashBoard extends AppCompatActivity {

    private CardView collegeList,branchList,universityList,keyDates,admissionSteps,profile;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dash_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        collegeList = findViewById(R.id.college_list);
        branchList = findViewById(R.id.branch_list);
        universityList = findViewById(R.id.university_list);
        keyDates = findViewById(R.id.key_dates);
        admissionSteps = findViewById(R.id.admission_steps);
        profile = findViewById(R.id.profile);

        collegeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashBoard.this,CollegeList.class);
                startActivity(intent);
            }
        });
        branchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashBoard.this,BranchList.class);
                startActivity(intent);
            }
        });
        universityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashBoard.this,UniversityList.class);
                startActivity(intent);
            }
        });
        keyDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashBoard.this,KeyDates.class);
                startActivity(intent);
            }
        });
        admissionSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashBoard.this,AdmissionSteps.class);
                startActivity(intent);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(DashBoard.this,Profile.class);
                startActivity(intent);
            }
        });
    }
}