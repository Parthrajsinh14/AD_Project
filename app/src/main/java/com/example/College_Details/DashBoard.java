package com.example.College_Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    private CardView collegeList, branchList, universityList, keyDates, admissionSteps, profile;
    private Intent intent;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private DatabaseReference databaseReference;
    private String name, email;
    private Long mobile;
    private Double spi;
    private Boolean admin;
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

        // Initialize database reference and UI elements
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        collegeList = findViewById(R.id.college_list);
        branchList = findViewById(R.id.branch_list);
        universityList = findViewById(R.id.university_list);
        keyDates = findViewById(R.id.key_dates);
        admissionSteps = findViewById(R.id.admission_steps);
        profile = findViewById(R.id.profile);

        mauth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mauth.getCurrentUser();
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // Fetch user data if user is logged in
        if (currentUser != null) {
            fetchUser(currentUser.getEmail());
        } else {
            name = " ";
            email = " ";
            mobile = 0L;
            spi = 0.0;
        }

        // Setting click listeners for different card views
        collegeList.setOnClickListener(view -> {
            if (admin != null && admin) {
                intent = new Intent(DashBoard.this, CollegeList_Admin.class);
            } else {
                intent = new Intent(DashBoard.this, CollegeList.class);
            }
            startActivity(intent);
        });

        branchList.setOnClickListener(view -> {
            if (admin != null && admin) {
                intent = new Intent(DashBoard.this, BranchList_Admin.class);
            } else {
                intent = new Intent(DashBoard.this, BranchList.class);
            }
            startActivity(intent);
        });

        universityList.setOnClickListener(view -> {
            if (admin != null && admin) {
                intent = new Intent(DashBoard.this, UniversityList_Admin.class);
            } else {
                intent = new Intent(DashBoard.this, UniversityList.class);
            }
            startActivity(intent);
        });

        keyDates.setOnClickListener(view -> {
            if (admin != null && admin) {
                intent = new Intent(DashBoard.this, KeyDates_Admin.class);
            } else {
                intent = new Intent(DashBoard.this, KeyDates.class);
            }
            startActivity(intent);
        });

        admissionSteps.setOnClickListener(view -> {
            intent = new Intent(DashBoard.this, AdmissionSteps.class);
            startActivity(intent);
        });

        profile.setOnClickListener(view -> {
            intent = new Intent(DashBoard.this, Profile.class);
            intent.putExtra("name", name != null ? name : "Unknown");
            intent.putExtra("email", email != null ? email : "No Email");
            String mobileStr = (mobile != null) ? mobile.toString() : "0";
            intent.putExtra("mobile", mobileStr);
            double spiValue = (spi != null) ? spi : 0.0;
            intent.putExtra("spi", spiValue);
            startActivity(intent);
        });
    }

    // Method to fetch user data from Firebase
    private void fetchUser(String findEmail) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean userFound = false;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    UserModel user = dataSnapshot.getValue(UserModel.class);
                    Log.d("UserData", "User: " + user);

                    // Log the user data for debugging
                    if (user != null) {
                        Log.d("UserData", "Email: " + user.getEmail() + ", Admin: " + user.getAdmin());
                    }

                    if (user != null && user.getEmail() != null && user.getEmail().equals(findEmail)) {
                        name = user.getName() != null ? user.getName() : "Unknown";
                        email = user.getEmail();
                        mobile = user.getMobile() != null ? user.getMobile() : 0L;
                        spi = user.getSpi() != null ? user.getSpi() : 0.0;
                        admin = user.getAdmin() != null ? user.getAdmin() : false;

                        // Log admin status
                        Log.d("AdminStatus", "Admin: " + admin);

                        Toast.makeText(DashBoard.this, "Mobile : " + mobile, Toast.LENGTH_SHORT).show();
                        userFound = true;

                        // Update SharedPreferences to store fetched values
                        editor = sharedPreferences.edit();
                        editor.putBoolean("isFetched", true);
                        editor.putBoolean("isAdmin", admin);
                        editor.apply();
                        break;
                    }
                }

                if (!userFound) {
                    Toast.makeText(DashBoard.this, "No user found with email: " + findEmail, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashBoard.this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
