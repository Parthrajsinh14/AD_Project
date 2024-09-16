    package com.example.College_Details;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    private EditText Username;
    private EditText Password;
    Button Reg;
    private TextView loginPage;
    Intent intent;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onStart() {
        //Logic if user is already logged in
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            Intent intent = new Intent(getApplicationContext(), DashBoard.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);


        Username = findViewById(R.id.username1);
        mAuth = FirebaseAuth.getInstance();
        Password = findViewById(R.id.password1);
        loginPage = findViewById(R.id.loginPage);
        Reg = findViewById(R.id.register);
        sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Username.getText().toString();
                String password = Password.getText().toString();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {



                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //FirebaseUser user = mAuth.getCurrentUser();
                                editor.putString("email",email);
                                editor.commit();
                                Toast.makeText(RegisterActivity.this, "User Registered Successfully", Toast.LENGTH_SHORT).show();
                                intent = new Intent(RegisterActivity.this,DashBoard.class);
                                startActivity(intent);
                                finish();
                            } else {
                                String error = task.getException().getMessage();
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Register failed. "+error,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}