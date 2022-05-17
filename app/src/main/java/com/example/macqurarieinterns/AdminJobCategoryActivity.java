package com.example.macqurarieinterns;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Adapter.JobCategoryAdapter;
import com.example.macqurarieinterns.Model.JobCategory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AdminJobCategoryActivity extends AppCompatActivity {
    // EditText and buttons.
    private EditText job_type;
    private Button btn_add;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    JobCategory jobType;

    JobCategoryAdapter adapter; // Create Object of the Adapter class
    ArrayList<JobCategory> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_job_category);

        job_type=findViewById(R.id.job_type);
        btn_add=findViewById(R.id.btn_add);

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("JobCategory");

        jobType=new JobCategory();

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String job_cat=job_type.getText().toString();
                if (TextUtils.isEmpty(job_cat)){
                    Toast.makeText(AdminJobCategoryActivity.this, "Please add Job Category.", Toast.LENGTH_SHORT).show();
                }else{
                    addJobCategory(job_cat);
                }
            }
        });

        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.job_category_recycler);
        recyclerView.setHasFixedSize(true);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        list =new ArrayList<>();
        adapter= new JobCategoryAdapter(this ,
                list);
        recyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    JobCategory cat = dataSnapshot.getValue(JobCategory.class);
                        list.add(cat);


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(AdminJobCategoryActivity.this,AdminMainActivity.class);
            }

        });



    }
    private void addJobCategory(String job_category){

        final String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", timestamp);
        hashMap.put("name", job_category);


        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        finish();
                        startActivity(getIntent());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdminJobCategoryActivity.this, "Failed!"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });






//        jobType.setName(job_category);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                databaseReference.setValue(jobType);
//                Toast.makeText(AdminJobCategoryActivity.this, "data added", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(AdminJobCategoryActivity.this, "Failed", Toast.LENGTH_SHORT).show();
//
//            }
//        });

    }

}