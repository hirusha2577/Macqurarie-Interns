package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.macqurarieinterns.Adapter.PostAdapter;
import com.example.macqurarieinterns.Model.Company;
import com.example.macqurarieinterns.Model.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class ReportCompanyRegistrationActivity extends AppCompatActivity {

    private TextView requested, rejected, accepted, pending;
    private  int requested_count, rejected_count, accepted_count, pending_count;

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
        pending = findViewById(R.id.pending);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Company").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                  requested_count= (int)dataSnapshot.getChildrenCount();
                  requested.setText(String.valueOf(requested_count));
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

                rejected_count = (int)dataSnapshot.getChildrenCount();
                rejected.setText(String.valueOf(rejected_count));
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

                accepted_count = (int)dataSnapshot.getChildrenCount();
                accepted.setText(String.valueOf(accepted_count));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference("Company");
        Query query2 = databaseReference3.orderByChild("status").equalTo("0");
        query2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                pending_count = (int)dataSnapshot.getChildrenCount();
                pending.setText(String.valueOf(pending_count));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
