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
import com.google.firebase.auth.AuthResult;
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

public class Profile_Admin extends AppCompatActivity {

    TextView nameValue,emailValue,mobileValue;
    Button logout;
    FloatingActionButton add;
    FirebaseAuth mauth;
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_admin);
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
        add = findViewById(R.id.addAdmin);
        logout = findViewById(R.id.btnLogout);
        mauth = FirebaseAuth.getInstance();
        currentUser = mauth.getCurrentUser();

        if(currentUser == null){
            setProfile("NO NAME","---","---");
            logout.setText("LOGIN");
        } else {
            intent = getIntent();
            String name = intent.getStringExtra("name");
            String email = intent.getStringExtra("email");
            String mobile = intent.getStringExtra("mobile");
            setProfile(name,email,mobile);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentUser==null){
                    Intent intent1 = new Intent(Profile_Admin.this, MainActivity.class);
                    startActivity(intent1);
                    return;
                }
                final DialogPlus dialogPlus = DialogPlus.newDialog(Profile_Admin.this)
                        .setContentHolder(new ViewHolder(R.layout.popup_add_admin))
                        .setExpanded(true,1200)
                        .create();

                View dialogview = dialogPlus.getHolderView();
                EditText name = dialogview.findViewById(R.id.adminName);
                EditText number = dialogview.findViewById(R.id.adminMobile);
                EditText email = dialogview.findViewById(R.id.adminEmail);
                EditText password = dialogview.findViewById(R.id.adminPass);

                Button add = dialogview.findViewById(R.id.btnAdd);

                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> data = new HashMap<>();
                        data.put("name",name.getText().toString());
                        data.put("mobile",Long.parseLong(number.getText().toString()));
                        data.put("email",email.getText().toString());
                        data.put("admin",true);
                        data.put("spi",0.0);

                        mauth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        String id = email.getText().toString();
                                        id = id.replace(".","-");

                                        FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(id).setValue(data)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(Profile_Admin.this, "Admin Registered Successfully", Toast.LENGTH_SHORT).show();
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Profile_Admin.this, "Error while registration", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Profile_Admin.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
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
                intent = new Intent(Profile_Admin.this,MainActivity.class);
                sharedPreferences = getSharedPreferences("MyPrefs",MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(intent);
                finish();
            }
        });

    }


    public void setProfile(String name,String email,String mobile){
        if(name!=null){
            nameValue.setText(name);
            emailValue.setText(email);
            mobileValue.setText(mobile);
        } else {
            Toast.makeText(this, "Error in fetching Data", Toast.LENGTH_SHORT).show();
        }
    }

}