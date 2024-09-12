package com.example.College_Details;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView errorMessageTextView;
    Intent intent;
    private TextView reg,guestlog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("login_details",MODE_PRIVATE);
        boolean loggedIn = sharedPreferences.getBoolean("isLoggedIn",false);

        if(loggedIn){
            intent = new Intent(MainActivity.this,DashBoard.class);
            startActivity(intent);
            finish();
        }

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        //errorMessageTextView = findViewById(R.id.errorMessage);
        Button loginButton = findViewById(R.id.loginButton);
        reg=(TextView)findViewById(R.id.Register);
        guestlog=(TextView) findViewById(R.id.Guest);
        editor = sharedPreferences.edit();



        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();


                if (username.equals("admin") && password.equals("admin")) {
                    // Proceed to the next activity or dashboard
                    editor.putString("username",username);
                    editor.putBoolean("isLoggedIn",true);
                    editor.commit();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(intent);
                    finish();
                } else {
                    //errorMessageTextView.setText("Invalid username or password.");
                    //errorMessageTextView.setVisibility(View.VISIBLE);
                    Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Work after firebase connection


                intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        guestlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Login As Guest.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });

    }
}