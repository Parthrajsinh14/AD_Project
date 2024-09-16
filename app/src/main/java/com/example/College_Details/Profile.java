package com.example.College_Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
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

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    TextView nameValue,emailValue,mobileValue,spiValue;
    Button logout;
    FirebaseAuth mauth;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;

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
        mauth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        editor = sharedPreferences.edit();


        intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        String mobile = intent.getStringExtra("mobile");
        double spi = intent.getDoubleExtra("spi", 0);

        setProfile(name,email,mobile,spi);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mauth.signOut();
                editor.clear();
                editor.commit();
                intent = new Intent(Profile.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }


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