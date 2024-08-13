package com.example.College_Details;



import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        //errorMessageTextView = findViewById(R.id.errorMessage);
        Button loginButton = findViewById(R.id.loginButton);
        reg=(TextView)findViewById(R.id.Register);
        guestlog=(TextView) findViewById(R.id.Guest);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateLogin(username, password)) {
                    // Proceed to the next activity or dashboard
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, DashBoard.class);
                    startActivity(intent);
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
                intent=new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        guestlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Login As Guest.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

    }

    private boolean validateLogin(String username, String password) {
        // Replace with real authentication logic
        DBHandler dbHandler = new DBHandler(this);

        boolean isValidUser = dbHandler.checkUser(username, password);
        if (isValidUser) {
            return true;
        } else {
            return false;
        }
    }
}