package com.example.macqurarieinterns;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.macqurarieinterns.Adapter.CompanyAdapter;
import com.example.macqurarieinterns.Model.Company;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminCompanyActivity extends AppCompatActivity {
    private TextView deactivateBtn;
    CompanyAdapter adapter; // Create Object of the Adapter class
    DatabaseReference mbase; // Create object of the
    ArrayList<Company> list;

    // Firebase Realtime Database
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_company);
        RecyclerView recyclerView= (RecyclerView) findViewById(R.id.company_recycle);
        recyclerView.setHasFixedSize(true);
        // To display the Recycler view linearly
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mbase   = FirebaseDatabase.getInstance().getReference("Company");


        list =new ArrayList<>();
        adapter= new CompanyAdapter(this ,
                list);
        recyclerView.setAdapter(adapter);

        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Company company =dataSnapshot.getValue(Company.class);
                    if(company.getStatus().equals("1")) {
                        list.add(company);
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Company");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             moveActivity(AdminCompanyActivity.this,AdminMainActivity.class);
            }
        });

    }

}