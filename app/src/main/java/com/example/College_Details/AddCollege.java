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

public class AddCollege extends AppCompatActivity {
    EditText name,fees,branch,link;
    Button add,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_college);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name = findViewById(R.id.addName);
        fees = findViewById(R.id.addFees);
        branch = findViewById(R.id.addBranch);
        link = findViewById(R.id.addLink);
        add = findViewById(R.id.btnAdd);
        cancel = findViewById(R.id.btnCancel);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String collegeName = name.getText().toString();
                String collegeFees = fees.getText().toString();
                String collegeBranches = branch.getText().toString();
                String url = link.getText().toString();

                if(!collegeName.isEmpty()
                    && !collegeFees.isEmpty()
                    && !collegeBranches.isEmpty()
                    && !url.isEmpty()
                ) {
                    insert(collegeName,collegeFees,collegeBranches,url);
                } else {
                    Toast.makeText(AddCollege.this, "Provide all required data", Toast.LENGTH_SHORT).show();
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
    private  void insert(String name,String fees,String branch,String link){
        Map<String,Object> data = new HashMap<>();

        data.put("name",name);
        data.put("fees",Integer.parseInt(fees));
        data.put("branch",branch);
        data.put("link",link);

        FirebaseDatabase.getInstance().getReference().child("college")
                .push().setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddCollege.this, "College Added Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCollege.this, "Error while adding college", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}