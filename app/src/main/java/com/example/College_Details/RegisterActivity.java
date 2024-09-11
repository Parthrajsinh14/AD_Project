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
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText Username;
    private EditText Password;
    Button Reg;
    private TextView loginPage;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        sharedPreferences = getSharedPreferences("login_details",MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("isLoggedIn",false);

        if(loggedIn){
            intent = new Intent(RegisterActivity.this,DashBoard.class);
            startActivity(intent);
            finish();
        }

        Username = findViewById(R.id.username1);
        Password = findViewById(R.id.password1);
        loginPage = findViewById(R.id.loginPage);
        Reg = findViewById(R.id.register);
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
                String username = Username.getText().toString();
                String password = Password.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {

                    //Using temporary Data

                    if (username.equals("admin") && password.equals("admin")) {
                        // Proceed to the next activity or dashboard
                        editor.putString("username",username);
                        editor.putBoolean("isLoggedIn",true);
                        editor.commit();
                        Toast.makeText(RegisterActivity.this, "Registered Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, DashBoard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                    }



                    // Handle the signup logic here
//                    DBHandler dbHandler = new DBHandler(RegisterActivity.this);
//                    dbHandler.insertUserDetails(username, password);
//                    Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
//                    intent = new Intent(RegisterActivity.this, MainActivity.class);
//                    startActivity(intent);
                    // You can also start another activity here if needed
                }
            }
        });

    }
}