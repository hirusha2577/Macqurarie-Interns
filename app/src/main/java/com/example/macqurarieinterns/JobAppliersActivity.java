package com.example.macqurarieinterns;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.macqurarieinterns.Adapter.VacancyAdapter;
import com.example.macqurarieinterns.Adapter.VacancyApplyCompanyAdapter;
import com.example.macqurarieinterns.Model.Vacancy;
import com.example.macqurarieinterns.Model.VacancyApply;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

public class JobAppliersActivity extends AppCompatActivity {

    private String id;

    private DatabaseReference databaseReference;

    List<VacancyApply> vacancyApplyList;
    RecyclerView recyclerview_vacancyApply;
    VacancyApplyCompanyAdapter vacancyApplyCompanyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_appliers);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Appliers List");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("vacancy_id");



        recyclerview_vacancyApply = findViewById(R.id.job_vacancy_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerview_vacancyApply.setLayoutManager(layoutManager);

        vacancyApplyList = new ArrayList<>();

        vacancyApplyCompany();
    }

    private void vacancyApplyCompany() {
        databaseReference = FirebaseDatabase.getInstance().getReference("VacancyApply");
        Query query = databaseReference.orderByChild("vacancy_id").equalTo(id);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                vacancyApplyList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    VacancyApply vacancyApply = snapshot.getValue(VacancyApply.class);
                    vacancyApplyList.add(vacancyApply);
                    vacancyApplyCompanyAdapter = new VacancyApplyCompanyAdapter(getApplicationContext(),vacancyApplyList);
                    recyclerview_vacancyApply.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
                    recyclerview_vacancyApply.setAdapter(vacancyApplyCompanyAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JobAppliersActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}