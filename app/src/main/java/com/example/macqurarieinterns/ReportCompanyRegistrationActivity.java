package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class ReportCompanyRegistrationActivity extends AppCompatActivity {

    private TextView requested, rejected, accepted, percentage;
    private long requested_count, rejected_count, accepted_count, percentage_count;

    String txt_requested;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_company_registration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Company Registration Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(ReportCompanyRegistrationActivity.this,AdminMainActivity.class);
            }
        });

        requested = findViewById(R.id.requested);
        rejected = findViewById(R.id.rejected);
        accepted = findViewById(R.id.accepted);
        percentage = findViewById(R.id.percentage);

//      System.out.println("/////////////////////////////////////////////////////////////");
//        System.out.println(txt_requested);
//        System.out.println("/////////////////////////////////////////////////////////////");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Company").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                 long count= dataSnapshot.getChildrenCount();
                 requested.setText(Long.toString(count));
                 txt_requested = Long.toString(count);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Company");
        Query query = databaseReference1.orderByChild("status").equalTo("2");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long count= dataSnapshot.getChildrenCount();
                rejected.setText(Long.toString(count));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("Company");
        Query query1 = databaseReference2.orderByChild("status").equalTo("1");
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                long count= dataSnapshot.getChildrenCount();
                accepted.setText(Long.toString(count));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}