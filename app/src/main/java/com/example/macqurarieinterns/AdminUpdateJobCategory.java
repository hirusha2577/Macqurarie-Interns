package com.example.macqurarieinterns;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.macqurarieinterns.Model.JobCategory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AdminUpdateJobCategory extends AppCompatActivity {

    private Button btnUpdate;
    private EditText job_category;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    JobCategory jobType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_job_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Update Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        Toast.makeText(this, "Job Id :"+id+" is Confirmed", Toast.LENGTH_SHORT).show();

        job_category=findViewById(R.id.edit_job_type);
        job_category.setText(extras.getString("name"));;
        btnUpdate=findViewById(R.   id.btn_update);
        firebaseDatabase = FirebaseDatabase.getInstance();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = firebaseDatabase.getReference("JobCategory").child(id);
                databaseReference.child("name").setValue(job_category.getText().toString());

                finish();
                moveActivity(AdminUpdateJobCategory.this,AdminJobCategoryActivity.class);

            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(AdminUpdateJobCategory.this,AdminMainActivity.class);
            }

        });
    }
}