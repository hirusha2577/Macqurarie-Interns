package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ReportStudentRegistrationActivity extends AppCompatActivity {

    private TextView available,active, percentage;
    private long active_count, percentage_count;

    String txt_active, txt_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_student_registration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Student Registration Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(ReportStudentRegistrationActivity.this,AdminMainActivity.class);
            }
        });


        available = findViewById(R.id.rejected);
        active = findViewById(R.id.accepted);
        percentage = findViewById(R.id.percentage);

//      System.out.println("/////////////////////////////////////////////////////////////");
//        System.out.println(txt_requested);
//        System.out.println("/////////////////////////////////////////////////////////////");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long count= dataSnapshot.getChildrenCount();
                available.setText(Long.toString(count));
                txt_available = Long.toString(count);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Student");
        Query query1 = databaseReference2.orderByChild("status").equalTo("1");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long count= dataSnapshot.getChildrenCount();
                active.setText(Long.toString(count));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}