package com.example.College_Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    TextView nameValue,emailValue,mobileValue,spiValue;
    Button logout;
    FloatingActionButton edit;
    FirebaseAuth mauth;
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseUser currentUser;


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
        edit = findViewById(R.id.editProfile);
        logout = findViewById(R.id.btnLogout);
        mauth = FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser();

        if(currentUser == null){
            setProfile("NO NAME","---","---",0.0);
            logout.setText("LOGIN");
        } else {
            intent = getIntent();
            String name = intent.getStringExtra("name");
            String email = intent.getStringExtra("email");
            String mobile = intent.getStringExtra("mobile");
            double spi = intent.getDoubleExtra("spi", 0);
            setProfile(name,email,mobile,spi);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser==null){
                    Intent intent1 = new Intent(Profile.this, MainActivity.class);
                    startActivity(intent1);
                    return;
                }
                final DialogPlus dialogPlus = DialogPlus.newDialog(Profile.this)
                        .setContentHolder(new ViewHolder(R.layout.update_popup_profile))
                        .setExpanded(true,1000)
                        .create();

                View dialogview = dialogPlus.getHolderView();
                EditText name = dialogview.findViewById(R.id.updateName);
                EditText number = dialogview.findViewById(R.id.updateMobile);
                EditText spi = dialogview.findViewById(R.id.updateSpi);
                Button update = dialogview.findViewById(R.id.btnUpdate);

                name.setText(nameValue.getText().toString());
                number.setText(mobileValue.getText().toString());
                spi.setText(spiValue.getText().toString());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> data = new HashMap<>();
                        data.put("name",name.getText().toString());
                        data.put("mobile",Long.parseLong(number.getText().toString()));
                        data.put("spi",Double.parseDouble(spi.getText().toString()));
                        String email = currentUser.getEmail();
                        email = email.replace(".","-");
                        FirebaseDatabase.getInstance().getReference().child("users")
                                .child(email).updateChildren(data)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Profile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        setProfile(name.getText().toString(),emailValue.getText().toString(),number.getText().toString(),Double.parseDouble(spi.getText().toString()));
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Profile.this, "Data Updated Failed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });


                dialogPlus.show();
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser!=null){
                    mauth.signOut();
                }
                intent = new Intent(Profile.this,MainActivity.class);
                sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
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