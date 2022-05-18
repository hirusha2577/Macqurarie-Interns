package com.example.macqurarieinterns;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Adapter.StudentAdapter;
import com.example.macqurarieinterns.Model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminStudentActivity extends AppCompatActivity {
    StudentAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    ArrayList<Student> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_student);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Student");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.manage_student_recycle);
        recyclerView.setHasFixedSize(true);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mbase   = FirebaseDatabase.getInstance().getReference("Student");


        list =new ArrayList<>();
        adapter= new StudentAdapter(this ,
                list);
        recyclerView.setAdapter(adapter);

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Student student =dataSnapshot.getValue(Student.class);
                    if(student.getStatus().equals("1")) {
                        list.add(student);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(AdminStudentActivity.this,AdminMainActivity.class);
            }

        });

    }
}