package com.example.College_Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashBoard extends AppCompatActivity {

    private CardView collegeList,branchList,universityList,keyDates,admissionSteps,profile;
    private Intent intent;
    private DatabaseReference databaseReference;
    private String name,email,mobile;
    private double spi;
    FirebaseAuth mauth;

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

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        collegeList = findViewById(R.id.college_list);
        branchList = findViewById(R.id.branch_list);
        universityList = findViewById(R.id.university_list);
        keyDates = findViewById(R.id.key_dates);
        admissionSteps = findViewById(R.id.admission_steps);
        profile = findViewById(R.id.profile);
        mauth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mauth.getCurrentUser();

        if(currentUser != null){
            fetchUser(currentUser.getEmail());
        } else {
            name = " ";
            email = " ";
            mobile = " ";
            spi = 0;
        }



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
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("mobile", mobile);
                intent.putExtra("spi", spi);
                startActivity(intent);
            }
        });
    }

    private  void fetchUser(String findEmail){
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userFound = false;

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    if(user!=null && user.getEmail().equals(findEmail)) {
                        name = user.getName();
                        email = user.getEmail();
                        mobile = user.getMobile();
                        spi = user.getSpi();

                        Toast.makeText(DashBoard.this, "Data Fetched Successfully", Toast.LENGTH_LONG).show();
                        userFound = true;
                        break;
                    }
                }
                if(!userFound){
                    Toast.makeText(DashBoard.this, "No user found with this email "+findEmail, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashBoard.this, "Error Fetching Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}