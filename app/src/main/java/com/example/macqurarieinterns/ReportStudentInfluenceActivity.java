package com.example.macqurarieinterns;

import static com.example.macqurarieinterns.Function.MyIntent.moveActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ReportStudentInfluenceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_student_influence);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Student Influence Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Vacancy");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int all_vacancy= (int) dataSnapshot.getChildrenCount();
                TextView tv_vacancy=findViewById(R.id.no_of_vacancy);
                tv_vacancy.setText(Integer.toString(all_vacancy));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Have to wait for the other members part to complete other 3 fields in order to calculate

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(ReportStudentInfluenceActivity.this,AdminMainActivity.class);
            }
        });

    }
}