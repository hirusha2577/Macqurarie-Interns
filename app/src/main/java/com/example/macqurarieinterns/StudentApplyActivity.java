package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.macqurarieinterns.Adapter.ApplyAdapter;
import com.example.macqurarieinterns.Model.Apply;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class StudentApplyActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReference1;

    RecyclerView recyclerView;
    ApplyAdapter myAdapter;
    ArrayList<Apply> applyList;
    Context context;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_apply);

        Log.i("info", "start");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Job Apply List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        userId = firebaseUser.getUid();

        context = getApplicationContext();

        recyclerView = findViewById(R.id.applyList);
//        database = FirebaseDatabase.getInstance().getReference(Clients.class.getSimpleName());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        applyList = new ArrayList<>();
        myAdapter = new ApplyAdapter(this, applyList);
        recyclerView.setAdapter(myAdapter);

        loadAllApply();

    }

    private void loadAllApply() {
        Log.i("info", "Load apply start");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Apply");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applyList.clear();

                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Log.i("info", "in data snapshot");
                    Apply apply = dataSnapshot.getValue(Apply.class);
                    applyList.add(apply);

                }
                myAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}