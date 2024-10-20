package com.example.College_Details;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddUniversity extends AppCompatActivity {

    EditText name,colleges,link;
    Button add,cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_university);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        name = findViewById(R.id.addName);
        colleges = findViewById(R.id.addColleges);
        link = findViewById(R.id.addLink);
        add = findViewById(R.id.btnAdd);
        cancel = findViewById(R.id.btnCancel);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uniName = name.getText().toString();
                String collegeCount = colleges.getText().toString();
                String url = link.getText().toString();

                if(!uniName.isEmpty() && !collegeCount.isEmpty() && !url.isEmpty()){
                    insertData(uniName,collegeCount,url);
                    name.setText("");
                    colleges.setText("");
                    link.setText("");
                } else {
                    Toast.makeText(AddUniversity.this, "Please provide all data", Toast.LENGTH_SHORT).show();
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void insertData(String uniName,String collegeCount,String url){
        Map<String,Object> data = new HashMap<>();
        data.put("name",uniName);
        data.put("college",Integer.parseInt(collegeCount));
        data.put("link",url);

        FirebaseDatabase.getInstance().getReference().child("university").push()
                .setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddUniversity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddUniversity.this, "Failed to Insert Data", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}