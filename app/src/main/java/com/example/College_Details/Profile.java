package com.example.College_Details;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView nameValue,emailValue,mobileValue,spiValue;
    Button logout;
    //DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Profile");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Optional: to show the back button
        }

        nameValue = findViewById(R.id.nameValue);
        emailValue = findViewById(R.id.emailValue);
        mobileValue = findViewById(R.id.mobileValue);
        spiValue = findViewById(R.id.spiValue);
        logout = findViewById(R.id.btnLogout);

        //databaseReference = FirebaseDatabase.getInstance().getReference("users");

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String mobile = intent.getStringExtra("mobile");
        double spi = intent.getDoubleExtra("spi", 0);

        setProfile(name,email,mobile,spi);
        //fetchUser("harshil@gmail.com");

    }

//    private  void fetchUser(String email){
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                boolean userFound = false;
//
//                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    UserModel user = dataSnapshot.getValue(UserModel.class);
//                    if(user!=null && user.getEmail().equals(email)) {
//                        nameValue.setText(user.getName());
//                        emailValue.setText(user.getEmail());
//                        mobileValue.setText(user.getMobile());
//                        spiValue.setText(String.valueOf(user.getSpi()));
//                        break;
//                    }
//                }
//                if(!userFound){
//                    //Toast.makeText(Profile.this, "No user found with this email", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(Profile.this, "Error Fetching Data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    public void setProfile(String name,String email,String mobile,double spi){
        if(name!=null){
            nameValue.setText(name);
            emailValue.setText(email);
            mobileValue.setText(mobile);
            spiValue.setText(String.valueOf(spi));
        } else {
            Toast.makeText(this, "Error in fetching Data", Toast.LENGTH_SHORT).show();
        }


    }

}